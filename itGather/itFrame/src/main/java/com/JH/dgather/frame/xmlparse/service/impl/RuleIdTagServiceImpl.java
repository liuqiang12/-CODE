package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.RuleIdBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

public class RuleIdTagServiceImpl implements IBaseService {
	private Logger logger = Logger.getLogger(RuleIdTagServiceImpl.class);

	// ruleId对应的element对象
	private Element ruleIdEl = null;

	public RuleIdTagServiceImpl(Element element) {
		ruleIdEl = element;
	}

	@Override
	public IBase parse() {

		RuleIdBo ruleIdBo = null;

		// 获取ruleId标签参数信息，获取失败返回null
		try {
			ruleIdBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据ruleId规则对配置的信息进行检查
		if (!checkTagInfo(ruleIdBo)) {
			return null;
		}

		// 判断ruleId标签是否存在子标签，如果存在则异常返回null
		if (isExistSubTag()) {
			return null;
		} else {
			return ruleIdBo;
		}
	}

	/**
	 * 从xml的ruleId节点信息中获取标签属性
	 * 
	 * @return ruleId标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private RuleIdBo getTagAttribute() throws TagException {
		RuleIdBo ruleIdBo = new RuleIdBo();

		// 遍历ruleId节点的所有属性
		for (Iterator it = ruleIdEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("name".equals(attribute.getName())) {
				ruleIdBo.setName(attribute.getValue());
			} else {
				throw new TagException("ruleId 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		ruleIdBo.setRuleIdValue(ruleIdEl.getText());
		return ruleIdBo;

	}

	/**
	 * 检查标签中的信息配置是否符合规则
	 * 
	 * @param ruleIdBo
	 *            ruleId标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagInfo(RuleIdBo ruleIdBo) {
		// name为必配属性
		if (null == ruleIdBo.getName() || "".equals(ruleIdBo.getName())) {
			logger.error("ruleId 标签中name属性值不能为空!");
			return false;
		}

		// ruleId内容值不能为null或""
		if (null == ruleIdBo.getRuleIdValue()
				|| "".equals(ruleIdBo.getRuleIdValue())) {
			logger.error("ruleId 标签内容值不正确!");
			return false;
		}
		return true;
	}

	/**
	 * 判断ruleId标签是否包含子标签
	 * 
	 * @param ruleIdBo
	 *            ruleId标签业务对象
	 * @return true：包含其他子标签，false：不包含其他子标签
	 */
	@SuppressWarnings("rawtypes")
	private boolean isExistSubTag() {
		List tagls = ruleIdEl.elements();
		if (tagls.size() > 0) {
			logger.error("ruleId 标签不得包含任何子标签!");
			return true;
		}
		return false;
	}
}
