package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.RuleIdBo;
import com.JH.dgather.frame.xmlparse.bo.VersionBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

public class VersionTagServiceImpl implements IBaseService {
	private Logger logger = Logger.getLogger(VersionTagServiceImpl.class);

	// version对应的element对象
	private Element versionEL = null;

	public VersionTagServiceImpl(Element element) {
		versionEL = element;
	}

	public IBase parse() {

		VersionBo versionBo = null;

		// 获取version标签参数信息，获取失败返回null
		try {
			versionBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 根据version规则对配置的属性进行检查
		if (!checkTagAttribute(versionBo)) {
			return null;
		}

		// 关联version与子标签ruleId，如果成功返回true
		if (connSubTag(versionBo)) {
			return versionBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的version节点信息中获取标签属性
	 * 
	 * @return version标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private VersionBo getTagAttribute() throws TagException {
		VersionBo versionBo = new VersionBo();

		// 遍历version节点的所有属性
		for (Iterator it = versionEL.attributeIterator(); it.hasNext();) {
			Attribute attr = (Attribute) it.next();
			if ("value".equals(attr.getName())) {
				versionBo.setValue(attr.getValue());
			} else {
				throw new TagException("version 标签不允许包含属性: " + attr.getName());
			}
		}
		return versionBo;
	}

	/**
	 * 检查标签中的参数配置是否符合规则
	 * 
	 * @param versionBo
	 *            version标签业务对象
	 * @return true:检查成功 false:检查失败
	 */
	private boolean checkTagAttribute(VersionBo versionBo) {
		// value为必配属性
		if (null == versionBo.getValue() || "".equals(versionBo.getValue())) {
			logger.error("version 标签中value属性值不能为空!");
			return false;
		}
		return true;
	}

	/**
	 * 关联子标签
	 * 
	 * @param versionBo
	 *            version标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean connSubTag(VersionBo versionBo) {
		Map<String, RuleIdBo> ruleIdBoMap = new HashMap<String, RuleIdBo>();

		// 遍历version节点下子节点信息
		for (Iterator it = versionEL.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签为ruleId时，进行处理
			if ("ruleId".equals(element.getName())) {
				IBaseService baseService = new RuleIdTagServiceImpl(element);
				RuleIdBo ruleIdBo = (RuleIdBo) baseService.parse();
				// 如果ruleId标签解析成功，则保存信息到内存
				if (null != ruleIdBo) {
					String key = ruleIdBo.getName();
					// 如果version标签下的ruleId标签存在重复的信息，则提示配置错误，解析失败
					if (null == key || ruleIdBoMap.containsKey(key)) {
						logger.error("version value = "
								+ versionBo.getValue()
								+ "，获取ruleId信息出现重复配置，请确认配置文件的正确性，ruleId name = "
								+ key);
						return false;

					} else {
						ruleIdBoMap.put(key, ruleIdBo);
					}
				}
				// ruleId标签解析失败
				else {
					return false;
				}
			} else {
				logger.error("version value = " + versionBo.getValue()
						+ "，标签下不允许包含子标签: " + element.getName());
			}
		}

		// 当model标签下存在version标签时，ruleIdBoMap大于0，解析成功
		if (0 < ruleIdBoMap.size()) {
			versionBo.setRuleIdBoMap(ruleIdBoMap);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出ruleId节点信息，" + versionBo);
			return false;
		}
	}
}
