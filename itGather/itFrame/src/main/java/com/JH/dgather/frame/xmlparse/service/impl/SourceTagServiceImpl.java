package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.bo.SourceBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中source标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class SourceTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(SourceTagServiceImpl.class);

	// validate标签对应的element对象
	private Element sourceEl = null;

	public SourceTagServiceImpl(Element element) {
		sourceEl = element;
	}

	@Override
	public IBase parse() {

		SourceBo sourceBo = null;

		// 获取source标签信息，获取失败返回null
		try {
			sourceBo = getTagInfo();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据source规则对配置的信息进行检查，检查失败返回null
		if (!checkTagInfo(sourceBo)) {
			return null;
		}
		return sourceBo;
	}

	/**
	 * 从xml的source节点信息中获取标签信息
	 * 
	 * @return source标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private SourceBo getTagInfo() throws TagException {
		SourceBo sourceBo = new SourceBo();
		// 遍历sourceBo节点的所有属性
		for (Iterator it = sourceEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("name".equals(attribute.getName())) {
				sourceBo.setName(attribute.getValue());
			} else {
				throw new TagException("source 标签不允许包含属性: "
						+ attribute.getName());
			}
		}

		// 获取source标签内容
		sourceBo.setSourceValue(sourceEl.getText());
		return sourceBo;
	}

	/**
	 * 检查标签中的信息配置是否符合规则
	 * 
	 * @param sourceBo
	 *            source标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagInfo(SourceBo sourceBo) {
		// name 存储到全局数据仓库中的键值，必须唯一，不得重复
		if (null == sourceBo.getName() || "".equals(sourceBo.getName())) {
			logger.error("source 标签的name属性不能为空。");
			return false;
		}
		// source 内容信息不能为空
		if (null == sourceBo.getSourceValue()
				|| "".equals(sourceBo.getSourceValue())) {
			logger.error("source 标签的内容不能为空。");
			return false;
		}
		return true;
	}

	/**
	 * source标签执行
	 * 
	 * @param handle
	 *            操作类
	 * @param sourceBo
	 *            source标签业务对象
	 * @return 执行结果：true执行成功，false执行失败
	 */
	public static boolean process(OperaterHandle handle, SourceBo sourceBo) {
		if (logger.isDebugEnabled()) {
			logger.debug("执行SourceTag标签操作！");
		}
		try {
			handle.addSource(sourceBo.getName(), sourceBo.getSourceValue());
		} catch (DataStorageException e) {
			logger.error("添加source标签信息到数据仓库出现异常", e);
			return false;
		}
		return true;
	}
}
