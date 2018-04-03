package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.bo.MatcherBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中matcher标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class MatcherTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(MatcherTagServiceImpl.class);

	// matcher标签对应的element对象
	private Element matcherEl = null;

	public MatcherTagServiceImpl(Element element) {
		matcherEl = element;
	}

	@Override
	public IBase parse() {

		MatcherBo matcherBo = null;

		// 获取matcher标签参数
		try {
			matcherBo = getTagAttribute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 获取必填参数以及matcher标签内容
		if (!isGetRequiredAtt(matcherBo)) {
			return null;
		}
		return matcherBo;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param matcherBo
	 *            matcher标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(MatcherBo matcherBo) {
		// buffer属性为必配属性，不得为空
		if (null == matcherBo.getBuffer() || "".equals(matcherBo.getBuffer())) {
			logger.error("matcher 标签中buffer属性为必填项不能为空!");
			return false;
		}

		// 获取matcher标签下的内容
		if (null == matcherEl.getText() || "".equals(matcherEl.getText())) {
			logger.error("matcher 标签的内容不能为空。");
			return false;
		}

		matcherBo.setMatcherValue(matcherEl.getText());
		return true;
	}

	/**
	 * 从xml的matcher节点信息中获取标签属性
	 * 
	 * @return matcher标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private MatcherBo getTagAttribute() throws TagException {
		MatcherBo matcherBo = new MatcherBo();

		// 遍历matcher节点的所有属性
		for (Iterator it = matcherEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("buffer".equals(attribute.getName())) {
				matcherBo.setBuffer(attribute.getValue());
			} else {
				throw new TagException("matcher 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return matcherBo;
	}

	/**
	 * 执行matcher标签
	 * 
	 * @param handle
	 *            操作类
	 * @param matcherBo
	 *            matcher业务对象
	 * @param result
	 *            上一级回显
	 * @return 执行结果 matcher匹配结果
	 */
	public static String process(OperaterHandle handle, MatcherBo matcherBo,
			String result) {
		// 匹配正则表达式过滤回显结果
		String resultStr = null;
		// 使用matcher标签内容正则表达式匹配result字符串数据
		Pattern pa = Pattern.compile(matcherBo.getMatcherValue());
		Matcher ma = pa.matcher(result);

		// 如果能找到匹配正则的数据，则获取第一个捕获组的数据，如果没有捕获组则报错
		if (ma.find()) {
			resultStr = ma.group(1);
		} else {
			resultStr = result;
		}
		try {
			handle.addBuffer(matcherBo.getBuffer(), resultStr);
		} catch (DataStorageException e) {
			logger.error("添加matcher 标签执行结果到全局参数时发生异常！", e);
			return null;
		}
		return resultStr;
	}
}
