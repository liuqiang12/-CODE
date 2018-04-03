package com.JH.dgather.frame.xmlparse.bo;

import java.util.Map;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * version标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class VersionBo implements IBase {
	
	/**
	 * 版本编号*.*表示通过所有版本，必须包含属性
	 */
	private String value;
	
	/**
	 * version标签下ruleId标签集合
	 */
	private Map<String, RuleIdBo> ruleIdBoMap;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, RuleIdBo> getRuleIdBoMap() {
		return ruleIdBoMap;
	}

	public void setRuleIdBoMap(Map<String, RuleIdBo> ruleIdBoMap) {
		this.ruleIdBoMap = ruleIdBoMap;
	}

	@Override
	public String getTagNameByObject() {
		return "version";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("version标签 [value属性=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}
