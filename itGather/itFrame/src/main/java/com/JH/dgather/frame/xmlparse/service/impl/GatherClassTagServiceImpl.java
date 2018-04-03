package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.bo.GatherClassBo;
import com.JH.dgather.frame.xmlparse.bo.RuleBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中gatherclass标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class GatherClassTagServiceImpl implements IBaseService {
	private static Logger logger = Logger
			.getLogger(GatherClassTagServiceImpl.class);

	// gatherclass对应的element对象
	private Element gatherClassEl = null;

	public GatherClassTagServiceImpl(Element gcElement) {
		gatherClassEl = gcElement;
	}

	@Override
	public IBase parse() {

		GatherClassBo gatherClassBo = null;
		// 获取标签参数信息，获取失败返回null
		try {
			gatherClassBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 如果未获取必填参数，则返回null
		if (!isGetRequiredAtt(gatherClassBo)) {
			return null;
		}

		// 判断是否关联gatherclass与rule成功，如果成功，返回关联关系
		if (isConnSubTag(gatherClassBo)) {
			return gatherClassBo;
		} else {
			return null;
		}

	}

	/**
	 * 从xml的gatherclass节点信息中获取标签属性
	 * 
	 * @return gatherClass标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private GatherClassBo getTagAttribute() throws TagException {
		GatherClassBo gatherClassBo = new GatherClassBo();
		// 遍历gatherClass节点的所有属性
		for (Iterator it = gatherClassEl.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			if ("value".equals(attribute.getName())) {
				gatherClassBo.setValue(attribute.getValue());
			} else if ("name".equals(attribute.getName())) {
				gatherClassBo.setName(attribute.getValue());
			} else {
				throw new TagException("gatherclass 标签不允许包含属性: "
						+ attribute.getName());
			}
		}
		return gatherClassBo;
	}

	/**
	 * 是否从标签属性中获取到必填参数
	 * 
	 * @param gatherClassBo
	 *            gatherclass标签业务对象
	 * @return true:成功获取必填参数 false:未获取到必填参数
	 */
	private boolean isGetRequiredAtt(GatherClassBo gatherClassBo) {
		// gatherclass标签的value为必配属性
		if (null == gatherClassBo || null == gatherClassBo.getValue()
				|| "".equals(gatherClassBo.getValue())) {
			logger.error("gatherclass 标签中value属性为必填项不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 是否关联子标签
	 * 
	 * @param gatherClassBo
	 *            gatherclass标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean isConnSubTag(GatherClassBo gatherClassBo) {

		Map<String, RuleBo> ruleMap = new HashMap<String, RuleBo>();

		// 遍历gatherclass节点下子节点信息
		for (Iterator it = gatherClassEl.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签tag为rule时，对tag进行处理
			if ("rule".equals(element.getName())) {
				IBaseService baseService = new RuleTagServiceImpl(element);
				// 对rule标签节点进行处理
				RuleBo ruleBo = (RuleBo) baseService.parse();

				// 如果ruleTag解析成功，则保存信息到内存中
				if (null != ruleBo) {
					String key = ruleBo.getId();

					// 如果ruleMap中不存在key，则新增
					if (!ruleMap.containsKey(key)) {
						ruleMap.put(key, ruleBo);
					}
					// ruleMap中存在相同的key
					else {
						logger.error("获取rule信息出现重复配置，请确认配置文件的正确性，id = " + key);
						return false;
					}

				} else {
					// ruleTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("gatherclass 不允许包含标签：" + element.getName());
				return false;
			}
		}

		// 当gatherclass标签下存在rule标签时，解析结果大于0，解析成功
		if (0 < ruleMap.size()) {
			// 关联父子标签
			gatherClassBo.setRuleBoMap(ruleMap);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出rule节点信息，" + gatherClassBo);
			return false;
		}
	}

	/**
	 * 根据gatherClass标签信息以及ruleid获取对应的rule标签信息
	 * 
	 * @param gatherClassBo
	 *            gatherclass标签业务对象
	 * @param ruleId
	 *            rule标签唯一标识
	 * @return rule标签业务对象
	 */
	public static RuleBo getRuleById(GatherClassBo gatherClassBo, String ruleId) {
		// 判断gatherClassBo与ruleId是否为空
		if (null == gatherClassBo) {
			logger.error("gatherClass标签信息为空，无法获取对应的rule信息！");
			return null;
		}
		if (null == ruleId || "".equals(ruleId)) {
			logger.error("rule标签的id为空，无法获取对应的rule信息！");
			return null;
		}
		Map<String, RuleBo> ruleBoMap = gatherClassBo.getRuleBoMap();
		// 遍历gatherclass标签关联的rule标签信息
		for (Entry<String, RuleBo> entry : ruleBoMap.entrySet()) {
			RuleBo ruleBo = entry.getValue();
			// 当ruleId与获取的Id匹配时直接返回rule标签业务对象
			if (ruleId.equals(ruleBo.getId())) {
				return ruleBo;
			}
		}

		logger.error("无法从gatherClass标签信息中获取对应的rule信息！ruleId: " + ruleId);
		return null;
	}
}
