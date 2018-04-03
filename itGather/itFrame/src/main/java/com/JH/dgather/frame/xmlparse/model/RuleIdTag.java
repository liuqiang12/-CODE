package com.JH.dgather.frame.xmlparse.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class RuleIdTag extends BaseTag {
	private Logger logger = Logger.getLogger(RuleIdTag.class);
	
	private String name = "";
	private String value = "";
	
	public RuleIdTag(Element ruleIdElm, BaseTag parent) throws TagException {
		super("ruleId", ruleIdElm, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "name: 规则名称，用于业务层获取规则ID\r\n";
		errMsg += "【不可包含子标签】";
		/**
		 * 判断ruleId属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("name")) {
					this.name = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//name为必配属性
			if (this.name == null || this.name.equals("")) {
				logger.error(this.getTagName() + "中name属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中name属性值为空!");
			}
		}
		else {
			logger.error(errMsg);
			throw new TagException(errMsg);
		}
		/**
		 * 判断ruleId标签的子标签
		 * 规则：
		 * 不得包含子标签
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			logger.error(this.getTagName() + " 标签不得包含任何子标签!");
			throw new TagException(this.getTagName() + " 标签不得包含任何子标签!");
		}
		
		this.value = this.getElement().getText();
		if (this.value == null || "".equals(this.value)) {
			logger.error(this.getTagName() + " 标签必须有内容!");
			throw new TagException(this.getTagName() + " 标签必须有内容!");
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
}
