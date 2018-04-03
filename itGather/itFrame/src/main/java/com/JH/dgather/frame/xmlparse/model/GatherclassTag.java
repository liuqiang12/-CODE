package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class GatherclassTag extends BaseTag {
	private Logger logger = Logger.getLogger(GatherclassTag.class);
	
	private HashMap<String, FlowTag> flowTagHm = new HashMap<String, FlowTag>();
	private HashMap<String, RuleTag> ruleTagHm = new HashMap<String, RuleTag>();
	
	private String value = "";
	private String name = "";
	
	public GatherclassTag(Element element, BaseTag parent) throws TagException {
		super("gatherclass", element, parent);
		this.parse();
	}
	
	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【必须包含属性】\r\n";
		errMsg += "value: 采集类型\r\n";
		errMsg += "【可选属性】\r\n";
		errMsg += "name: 采集名称";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<rule> <flow>";
		
		/**
		 * 判断gatherclass标签的属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			for (Iterator it = propLs.iterator(); it.hasNext();) {
				Attribute attr = (Attribute) it.next();
				if (attr.getName().equals("value")) {
					this.value = attr.getValue();
				}
				else if (attr.getName().equals("name")) {
					this.name = attr.getValue();
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
			
			//value为必配属性
			if (this.value == null || "".equals(this.value)) {
				logger.error(this.getTagName() + "中value属性值为空!");
				logger.error(errMsg);
				throw new TagException(this.getTagName() + "中value属性值为空!");
			}
		}
		else {
			logger.error(this.getTagName() + "标签必须包含value属性值!");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + "标签必须包含value属性值!");
		}
		
		/**
		 * 判断gatherclass标签中的子标签
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("flow")) {
					FlowTag flowTag = new FlowTag(em, this);
					this.flowTagHm.put(flowTag.getId(), flowTag);
				}
				else if (em.getName().equals("rule")) {
					RuleTag ruleTag = new RuleTag(em, this);
					this.ruleTagHm.put(ruleTag.getId(), ruleTag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		}
		else {
			logger.error(this.getTagName() + " 标签内未定义子标签！");
			logger.error(errMsg);
		}
	}
	
	/**
	 * <code>getFlowById</code> 根据flow id获取flow的内容
	 * @param flowid
	 * @return
	 */
	public FlowTag getFlowById(String flowid) throws TagException {
		FlowTag flowTag = null;
		if (flowid == null || flowid.equals("")) {
			throw new TagException("flow id为null或为空!");
		}
		flowTag = this.flowTagHm.get(flowid);
		
		return flowTag;
	}
	
	/**
	 * <code>getRuleById</code> 根据rule id获取rule的内容
	 * @param ruleId
	 * @return
	 * @throws TagException
	 */
	public RuleTag getRuleById(String ruleId) throws TagException {
		RuleTag ruleTag = null;
		if(ruleId == null || "".equals(ruleId)) {
			throw new TagException("rule id为null或为空!");
		}
		ruleTag = this.ruleTagHm.get(ruleId);
		
		return ruleTag;
	}
	
	/**
	 * @return the flowTagHm
	 */
	public HashMap<String, FlowTag> getFlowTagHm() {
		return flowTagHm;
	}
	
	/**
	 * @return the ruleTagHm
	 */
	public HashMap<String, RuleTag> getRuleTagHm() {
		return ruleTagHm;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
