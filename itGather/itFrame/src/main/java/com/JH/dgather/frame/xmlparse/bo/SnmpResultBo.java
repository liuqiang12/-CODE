package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * snmpResult标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class SnmpResultBo implements IStepBase {

	private String name;

	// snmpResult标签内容
	private String snmpResultValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSnmpResultValue() {
		return snmpResultValue;
	}

	public void setSnmpResultValue(String snmpResultValue) {
		this.snmpResultValue = snmpResultValue;
	}

	@Override
	public String getTagNameByObject() {
		return "snmpResult";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("snmpResult标签 [name属性=");
		builder.append(name);
		builder.append(", snmpResultValue标签内容值=");
		builder.append(snmpResultValue);
		builder.append("]");
		return builder.toString();
	}
}
