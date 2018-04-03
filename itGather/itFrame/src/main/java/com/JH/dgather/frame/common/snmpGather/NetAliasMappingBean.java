package com.JH.dgather.frame.common.snmpGather;

/**
 * smmp中entity中的port 与iftable中的映射关系
 * 
 * @author yangDS
 *
 */
public class NetAliasMappingBean {
	private String entAliasMappingIdentifier;
	private String entPhysicalIndex;
	private String ifIndex;
	
	public String getEntAliasMappingIdentifier() {
		return entAliasMappingIdentifier;
	}
	
	public void setEntAliasMappingIdentifier(String entAliasMappingIdentifier) {
		this.entAliasMappingIdentifier = entAliasMappingIdentifier;
	}
	
	public String getEntPhysicalIndex() {
		return entPhysicalIndex;
	}
	
	public void setEntPhysicalIndex(String entPhysicalIndex) {
		this.entPhysicalIndex = entPhysicalIndex;
	}
	
	public String getIfIndex() {
		return ifIndex;
	}
	
	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}
}
