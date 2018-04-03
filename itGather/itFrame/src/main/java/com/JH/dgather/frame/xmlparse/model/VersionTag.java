package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class VersionTag extends BaseTag {
	private Logger logger = Logger.getLogger(VersionTag.class);
	
	private HashMap<String, RuleIdTag> ruleIdHm = new HashMap<String, RuleIdTag>();
	
	private String value = "";
	
	public VersionTag(Element versionElm, BaseTag parent) throws TagException {
		super("version", versionElm, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "value: 版本编号(*.*表示通过所有版本)\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "无";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<ruleId>";
		
		/**
		 * 判断version标签属性值
		 * 规则:
		 * 必须包含属性: value: 版本编号
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("value")) {
					this.value = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//value为必配属性
			if (this.value == null || this.value.equals("")) {
				logger.error(this.getTagName() + "中value属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中value属性值为空!");
			}
		}
		else {
			logger.error(errMsg);
			throw new TagException(errMsg);
		}
		
		/**
		 * 判断version标签中的子标签
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("ruleId")) {
					RuleIdTag ruleIdTag = new RuleIdTag(em, this);
					String key = ruleIdTag.getName();
					this.ruleIdHm.put(key, ruleIdTag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
	}
	
	/**
	 * @return the ruleIdHm
	 */
	public HashMap<String, RuleIdTag> getRuleIdHm() {
		return ruleIdHm;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
}
