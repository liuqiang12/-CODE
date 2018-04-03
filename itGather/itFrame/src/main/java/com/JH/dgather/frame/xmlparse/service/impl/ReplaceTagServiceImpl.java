package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.ReplaceBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中replace标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class ReplaceTagServiceImpl implements IBaseService {
	private Logger logger = Logger.getLogger(ReplaceTagServiceImpl.class);

	// replace标签对应的element对象
	private Element replaceEl = null;

	public ReplaceTagServiceImpl(Element element) {
		replaceEl = element;
	}

	@Override
	public IBase parse() {

		ReplaceBo replaceBo = null;

		// 获取replace标签参数和内容值
		try {
			replaceBo = getTagAttAndText();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 获取必填参数以及replace标签内容
		if (!isGetRequiredInfo(replaceBo)) {
			return null;
		}
		return replaceBo;
	}

	/**
	 * 从xml的replace节点信息中获取标签属性和内容值
	 * 
	 * @return replace标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private ReplaceBo getTagAttAndText() throws TagException {
		ReplaceBo replaceBo = new ReplaceBo();

		// 遍历replace节点的所有属性
		for (Iterator it = replaceEl.attributeIterator(); it.hasNext();) {
			Attribute attr = (Attribute) it.next();
			if ("type".equals(attr.getName())) {
				replaceBo.setType(attr.getValue());
			} else if ("replacement".equals(attr.getName())) {
				replaceBo.setReplacement(attr.getValue());
			} else {
				throw new TagException("replace 标签不允许包含属性: " + attr.getName());
			}
		}
		replaceBo.setReplaceValue(replaceEl.getText());
		return replaceBo;
	}

	/**
	 * 是否从标签属性中获取到必填信息
	 * 
	 * @param replaceBo
	 *            replace标签业务对象
	 * @return true:成功获取必填信息 false:未获取到必填信息
	 */
	private boolean isGetRequiredInfo(ReplaceBo replaceBo) {
		// type属性只能为regex或string并区分大小写
		if (!"regex".equals(replaceBo.getType())
				&& !"string".equals(replaceBo.getType())) {
			logger.error("replace 标签中type属性的值只能为'regex'或'string'，并区分大小写!");
			return false;
		}

		// replacement为必配属性
		if (null == replaceBo.getReplacement()
				|| "".equals(replaceBo.getReplacement())) {
			logger.error("replace 标签中replacement为必配属性不能为空!");
			return false;
		}

		// replaceValue内容值必须有
		if (null == replaceBo.getReplaceValue()
				|| "".equals(replaceBo.getReplaceValue())) {
			logger.error("replace 标签的内容不能为空。");
			return false;
		}
		return true;
	}
}
