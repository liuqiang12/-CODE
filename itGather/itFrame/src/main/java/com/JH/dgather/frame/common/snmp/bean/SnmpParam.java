package com.JH.dgather.frame.common.snmp.bean;

/**
 * 定义SNMP访问需要具备的参数
 * @author muyp
 *
 */
public class SnmpParam {
	private String ip;
	private int port;
	private String snmpCommunity;
	private int snmpVersion;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getSnmpCommunity() {
		return snmpCommunity;
	}
	
	public void setSnmpCommunity(String snmpCommunity) {
		this.snmpCommunity = snmpCommunity;
	}
	
	public int getSnmpVersion() {
		return snmpVersion;
	}
	
	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}
}
