package com.JH.itBusi.business.resource.db.Bean;

import java.util.ArrayList;
import java.util.Collection;

import org.snmp4j.User;

import com.JH.dgather.frame.common.bean.LogicPortBean;
import com.JH.dgather.frame.common.bean.PortInfoBean;



public class DeviceData {
	private int deviceId;
	private int deviceClass;
	private int routType;
	private String deviceName;
	private String iPAddress;
	private int factory;
	private String chassisSerial;
	private String deviceVersion;
	private String model;
	private String cpuSize;
	private double memSize;
	private String sysDescr;
	private String aliasName;//别名
	private String snmpCommunity;
	private int snmpPort;
	private int snmpVersion;
	private boolean ifSuccess;//是否采集成功
	
	public int getSnmpVersion() {
		
		return snmpVersion;
	}
	
	public void setSnmpVersion(int snmpVersion) {
		
		this.snmpVersion = snmpVersion;
	}
	
	private Collection<User> lsUser;
	private Collection<PortInfoBean> lsPort;
	private Collection<LogicPortBean> lsLogicPort;
	
	public Collection<LogicPortBean> getLsLogicPort() {
		
		return lsLogicPort;
	}
	
	public void setLsLogicPort(Collection<LogicPortBean> lsLogicPort) {
		
		this.lsLogicPort = lsLogicPort;
	}
	
	public void addLogicPort(LogicPortBean lPort) {
		if (lsLogicPort == null) {
			this.lsLogicPort = new ArrayList<LogicPortBean>();
		}
		
		if (!this.lsLogicPort.contains(lPort)) {
			this.lsLogicPort.add(lPort);
		}
	}
	
	
	public Collection<User> getLsUser() {
		return lsUser;
	}
	
	public void setLsUser(Collection<User> lsUser) {
		this.lsUser = lsUser;
	}
	
	public void addLsUser(User user) {
		this.lsUser.add(user);
	}
	
	
	public int getDeviceClass() {
		return deviceClass;
	}
	
	public void setDeviceClass(int deviceClass) {
		this.deviceClass = deviceClass;
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		if (deviceName != null && deviceName.length() > 50) {
			this.deviceName = deviceName.substring(0, 50);
		}
		else {
			this.deviceName = deviceName;
		}
		
	}
	
	public String getIPAddress() {
		return iPAddress;
	}
	
	public void setIPAddress(String address) {
		iPAddress = address;
	}
	
	public int getRoutType() {
		return routType;
	}
	
	public void setRoutType(int routType) {
		this.routType = routType;
	}
	
	public String getChassisSerial() {
		return chassisSerial;
	}
	
	public void setChassisSerial(String chassisSerial) {
		if (chassisSerial != null && chassisSerial.length() > 100) {
			this.chassisSerial = chassisSerial.substring(0, 100);
		}
		else {
			this.chassisSerial = chassisSerial;
		}
		
	}
	
	public String getCpuSize() {
		return cpuSize;
	}
	
	public void setCpuSize(String cpuSize) {
		this.cpuSize = cpuSize;
	}
	
	public String getDeviceVersion() {
		return deviceVersion;
	}
	
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	
	public int getFactory() {
		return factory;
	}
	
	public void setFactory(int factory) {
		this.factory = factory;
	}
	
	public double getMemSize() {
		return memSize;
	}
	
	public void setMemSize(double memSize) {
		this.memSize = memSize;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getSnmpCommunity() {
		return snmpCommunity;
	}
	
	public void setSnmpCommunity(String snmpCommunity) {
		this.snmpCommunity = snmpCommunity;
	}
	
	public String getSysDescr() {
		return sysDescr;
	}
	
	public void setSysDescr(String sysDescr) {
		if (sysDescr != null && sysDescr.length() > 500) {
			this.sysDescr = sysDescr.substring(0, 500);
		}
		else {
			this.sysDescr = sysDescr;
		}
		
	}
	
	
	public Collection<PortInfoBean> getLsPort() {
		
		return lsPort;
	}
	
	public void setLsPort(Collection<PortInfoBean> lsPort) {
		
		this.lsPort = lsPort;
	}
	
	public void addPort(PortInfoBean port) {
		if (lsPort == null || lsPort.size()==0) {
			lsPort = new ArrayList<PortInfoBean>();
		}
		
		if (!this.lsPort.contains(port))
			this.lsPort.add(port);
	}
	
	public int getSnmpPort() {
		
		return snmpPort;
	}
	
	public void setSnmpPort(int snmpPort) {
		
		this.snmpPort = snmpPort;
	}
	
	public boolean isIfSuccess() {
		return ifSuccess;
	}
	
	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}
