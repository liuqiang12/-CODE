package com.JH.dgather.frame.common.snmpGather;

import java.util.ArrayList;

import com.JH.dgather.frame.common.reflect.ReflectUtil;
import com.JH.dgather.frame.util.StringUtil;

/**
 * snmp采集回ipAddEnt 的映射bean
 * 
 * @author yang
 *
 */
public class NetElementIpAddrBean {
	/**
	 * 设备的一个ip地址
	 */
	private String ipAdEntAddr;
	/**
	 * ip地址对应的设备接口索引
	 */
	private String ipAdEntIfIndex;
	/**
	 * 该ip对应的子网掩码
	 */
	private String ipAdEntNetMask;
	/**
	 * 子网号
	 */
	
	private String ipNetNo;
	
	/**
	 * 端口索引
	 */
	private int portIndex;
	
	/**
	 * ip与mask的简写如 192.168.70.1 255.255.255.0 则ipShortType值为192.168.70.0/24
	 * 
	 */
	private String ipShortType;
	
	private int deviceId;
	
	public int getDeviceId() {
		
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		
		this.deviceId = deviceId;
	}
	
	public String getIpShortType() {
		
		return StringUtil.getNetIpByIpAndNetmask(this.getIpAdEntAddr(), this.getIpAdEntNetMask());
	}
	
	public void setIpShortType(String ipShortType) {
		
		this.ipShortType = ipShortType;
	}
	
	public String getIpNetNo() {
		
		if (ipShortType == null)
			return null;
		else
			ipNetNo = ipShortType.substring(0, ipShortType.lastIndexOf("/"));
		return ipNetNo;
	}
	
	public void setIpNetNo(String ipNetNo) {
		
		this.ipNetNo = ipNetNo;
	}
	
	public String getIpAdEntAddr() {
		return ipAdEntAddr;
	}
	
	public void setIpAdEntAddr(String ipAdEntAddr) {
		this.ipAdEntAddr = ipAdEntAddr;
	}
	
	public String getIpAdEntIfIndex() {
		return ipAdEntIfIndex;
	}
	
	public void setIpAdEntIfIndex(String ipAdEntIfIndex) {
		this.ipAdEntIfIndex = ipAdEntIfIndex;
	}
	
	public String getIpAdEntNetMask() {
		return ipAdEntNetMask;
	}
	
	public void setIpAdEntNetMask(String ipAdEntNetMask) {
		this.ipAdEntNetMask = ipAdEntNetMask;
	}
	
	public boolean equals(Object obj) {
		NetElementIpAddrBean temp = null;
		if (obj == null)
			return false;
		ArrayList<String> compareFields = new ArrayList<String>() {
			{
				add("ipAdEntAddr");
				add("ipAdEntIfIndex");
				add("ipAdEntNetMask");
				add("portIndex");
				add("deviceId");
			}
		};
		
		if (obj instanceof NetElementIpAddrBean) {
			temp = (NetElementIpAddrBean) obj;
			return ReflectUtil.beanCompare(this, temp, compareFields);
		}
		return false;
		
	}
	
	public int getPortIndex() {
		try {
			portIndex = Integer.parseInt(this.getIpAdEntIfIndex());
		} catch (Exception e) {
			portIndex = -1;
		}
		return portIndex;
	}
	
	public void setPortIndex(int portIndex) {
		
		this.portIndex = portIndex;
	}
	
}
