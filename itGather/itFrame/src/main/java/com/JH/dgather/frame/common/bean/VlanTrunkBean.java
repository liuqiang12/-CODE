package com.JH.dgather.frame.common.bean;

/**
 * vlan信息
 * 
 * @author yangDS
 *
 */
public class VlanTrunkBean {
	
	private int deviceId;
	private String vlanName;
	private String portName;
	private int portCode;
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getVlanName() {
		return vlanName;
	}
	
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}
	
	public String getPortName() {
		return portName;
	}
	
	public void setPortName(String portName) {
		this.portName = portName;
	}
	
	public int getPortCode() {
		return portCode;
	}
	
	public void setPortCode(int portCode) {
		this.portCode = portCode;
	}
	
}
