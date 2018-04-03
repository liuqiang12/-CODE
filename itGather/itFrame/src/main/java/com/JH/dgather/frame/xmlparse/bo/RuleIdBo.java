package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * ruleId标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class RuleIdBo implements IBase {

	/**
	 * 规则名称，必须包含属性
	 */
	private String name;
	
	/**
	 * ruleId内容值
	 */
	private String ruleIdValue;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuleIdValue() {
		return ruleIdValue;
	}

	public void setRuleIdValue(String ruleIdValue) {
		this.ruleIdValue = ruleIdValue;
	}

	@Override
	public String getTagNameByObject() {
		return "ruleId";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ruleId标签 [name属性=");
		builder.append(name);
		builder.append(", ruleIdValue标签内容值=");
		builder.append(ruleIdValue);
		builder.append("]");
		return builder.toString();
	}
}
