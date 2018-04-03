package com.JH.dgather;
/*
 * 可以被接收消息的设备信息
 */
public class DeviceInfo {
	String ipaddress;//ip地址
	String deviceName;//设备名
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
}
