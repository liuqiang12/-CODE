package com.JH.dgather.frame.xmlparse.bo;

import java.util.Map;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * gatherclass标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class GatherClassBo implements IBase {

	// 必须包含属性,采集类型
	private String value;

	// 采集名称
	private String name;

	// gatherclass标签下支持的rule子标签集合，key=rule标签id
	private Map<String, RuleBo> ruleBoMap;

	// gatherclass标签下支持的flow子标签集合，key=flow标签id
	private Map<String, FlowBo> flowBoMap;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, RuleBo> getRuleBoMap() {
		return ruleBoMap;
	}

	public void setRuleBoMap(Map<String, RuleBo> ruleBoMap) {
		this.ruleBoMap = ruleBoMap;
	}

	public Map<String, FlowBo> getFlowBoMap() {
		return flowBoMap;
	}

	public void setFlowBoMap(Map<String, FlowBo> flowBoMap) {
		this.flowBoMap = flowBoMap;
	}

	@Override
	public String getTagNameByObject() {
		return "gatherclass";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("gatherclass标签 [value属性=");
		builder.append(value);
		builder.append(", name属性=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
