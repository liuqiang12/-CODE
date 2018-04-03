package com.JH.dgather.frame.common.reflect.bean;

/**
 * 
 * 端口类型（以snmp方式采集出的type值）与应用中的实际名称对应
 * @author yangDS
 *
 */
public class PortTypeMappingBean {
	private String type;
	private String name;
	private String shortName;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
