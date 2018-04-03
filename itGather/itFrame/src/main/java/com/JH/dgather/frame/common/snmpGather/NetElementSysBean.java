package com.JH.dgather.frame.common.snmpGather;

/**
 * snmp采集回sysTable的映射bean
 * 
 *
 */
public class NetElementSysBean {
	private String sysDescr;
	private String sysUpTime;
	private String sysContact;
	private String sysLocation;
	private String sysObjectId;
	private String sysName;
		public String getSysName() {
		
		return sysName;
	}
	
	public void setSysName(String sysName) {
		
		this.sysName = sysName;
	}
	
	public String getSysObjectId() {
		
		return sysObjectId;
	}
	
	public void setSysObjectId(String sysObjectId) {
		if (sysObjectId == null)
			return;
		sysObjectId = sysObjectId.trim();
		if (sysObjectId.startsWith(".")) {
			this.sysObjectId = sysObjectId.substring(1);
		}
		else {
			this.sysObjectId = sysObjectId;
		}
	}
	
	public String getSysDescr() {
		return sysDescr;
	}
	
	public void setSysDescr(String sysDescr) {
		this.sysDescr = sysDescr;
	}
	
	public String getSysUpTime() {
		return sysUpTime;
	}
	
	public void setSysUpTime(String sysUpTime) {
		this.sysUpTime = sysUpTime;
	}
	
	public String getSysContact() {
		return sysContact;
	}
	
	public void setSysContact(String sysContact) {
		this.sysContact = sysContact;
	}
	
	public String getSysLocation() {
		return sysLocation;
	}
	
	public void setSysLocation(String sysLocation) {
		this.sysLocation = sysLocation;
	}
	
}
