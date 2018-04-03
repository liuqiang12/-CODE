package com.JH.dgather.frame.xmlparse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.xmlparse.bo.GatherClassBo;
import com.JH.dgather.frame.xmlparse.bo.RuleBo;
import com.JH.dgather.frame.xmlparse.bo.RuleIdBo;
import com.JH.dgather.frame.xmlparse.service.IBaseService;
import com.JH.dgather.frame.xmlparse.service.impl.GatherClassTagServiceImpl;

/**
 * systemRules配置文件解析处理类
 * 
 * @author zhengbin
 * @version 1.0 2013/9/17
 * 
 */
public class SystemRulesParse extends XmlParse {
	private static Logger logger = Logger.getLogger(SystemRulesParse.class);

	/**
	 * 用于存储SystemRules.xml解析信息
	 */
	private static final Map<String, GatherClassBo> SYSTEM_RULES_INFO = new HashMap<String, GatherClassBo>();

	/**
	 * 初始化配置解析
	 * 
	 * @return
	 */
	@Override
	public boolean initXmlParse(String propertiesName) {
		// "systemRules","syslogRules"
		String fileDir = PropertiesHandle.getResuouceInfo(propertiesName);
		return cacheXMLLoad(fileDir);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean parseXml(Document document) {
		// 获取root根节点
		Element rootElm = document.getRootElement();
		// 遍历根节点信息
		for (Iterator it = rootElm.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			// 当解析的标签tag为gatherclas时，对tag进行处理
			if ("gatherclass".equals(element.getName())) {
				IBaseService baseService = new GatherClassTagServiceImpl(
						element);
				GatherClassBo gatherClassBo = (GatherClassBo) baseService
						.parse();
				// 如果gatherClasstag解析成功，则保存信息到内存中
				if (null != gatherClassBo) {
					String key = gatherClassBo.getValue();
					// 如果key已经存在相同配置，则报错
					if (null == key || SYSTEM_RULES_INFO.containsKey(key)) {
						logger.error("获取gatherCalss信息出现重复配置，请确认配置文件的正确性，gatherClasstag value = "
								+ key);
						return false;
					} else {
						SYSTEM_RULES_INFO.put(key, gatherClassBo);
					}
				} else {
					// gatherclasstag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else {
				logger.error("root 不允许包含标签：" + element.getName());
				return false;
			}
		}
		// 成功解析出gatherclass子节点信息时，表示配置解析成功
		if (0 < SYSTEM_RULES_INFO.size()) {
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出gatherclass节点信息");
			return false;
		}
	}

	/**
	 * 通过gatherclass与ruleId标签信息获取对应的rule标签信息
	 * 
	 * @param ruleIdBo
	 *            ruleId标签业务对象
	 * @param gatherclass
	 *            gatherclass
	 * @return rule标签业务对象
	 */
	public static RuleBo getRuleByRuleIdAndGc(RuleIdBo ruleIdBo,
			String gatherclass) {
		// 判断ruleIdBo与ruleId是否为空
		if (null == ruleIdBo) {
			logger.error("ruleId标签信息为空，无法获取对应的ruleId！");
			return null;
		}
		if (null == gatherclass || "".equals(gatherclass)) {
			logger.error("采集类型gatherclass不能为空！");
			return null;
		}

		// 正则表达式匹配ruleId标签内容值，并获取对应的rule标签的Id
		String ruleId = null;
		Pattern pa = Pattern.compile("\\$\\{rules\\.(.+?)}$");
		Matcher ma = pa.matcher(ruleIdBo.getRuleIdValue());
		if (ma.find()) {
			ruleId = ma.group(1);
			if (logger.isDebugEnabled()) {
				logger.debug(ruleIdBo + "中解析出rule标签的ID为 " + ruleId);
			}
		} else {
			logger.error("ruleId标签的内容值不符合${rules.xxxxx}, 例如: ${rules.11-001}。"
					+ ruleIdBo);
			return null;
		}

		GatherClassBo gatherClassBo = SYSTEM_RULES_INFO.get(gatherclass);
		if (null == gatherClassBo) {
			logger.error("无法从配置内存信息中找到采集类型value为" + gatherclass
					+ "的gatherclass标签!");
			return null;
		}

		return GatherClassTagServiceImpl.getRuleById(gatherClassBo, ruleId);
	}
}
