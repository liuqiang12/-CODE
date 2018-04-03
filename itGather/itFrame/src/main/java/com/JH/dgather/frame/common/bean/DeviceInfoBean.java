/*
 * @(#)DeviceInfoBean.java 01/29/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.bean;


import org.apache.commons.lang.builder.ToStringBuilder;

import com.JH.dgather.frame.common.snmp.SnmpUserTargetBean;


public class DeviceInfoBean {
	private int routId; // ID编号
	private int deviceClass; // 设备类别 1. 网络设备；2.安全设备；3. 通信设备
	private int routType; // 设备类型
	private String routName; // 路由器名称
	private String iPAddress; // IP地址
	private String sysDescr;//设备描述
	//private String aliasName;//设备别名
	private String loginUser; // 登录用户名
	private String loginPsw; // 登录密码
	/**
	 * 登录方式-0：telnet协议；1：SSH协议
	 */
	private int loginModel;
	private String privilegePsw;// 特权密码
	private boolean isPriviledge;// 是否是特权模式
	private int telnetPort; // telnet端口
	private int snmpPort; // snmp端口
	private String snmpCommunity;// snmp团体字
	/**
	 * 厂家
	 */
	private int factory; // 厂家ID
	private int ownerprovince; // 归属地域
	private int parentroutid; // 上级路由设备
	private int snmpVersion; // SNMP版本
	private String modelName; // 型号名称
	private String deviceVersion = "*.*"; // 版本
	private String telnetVPNparm;// 带VPN跳转的时候的VPN参数

	// add by xdwang 2012-12-7 增加snmp set团体字
	private String snmpSetCommunity; // SNMP set团体字
	// add by xdwang 2013-1-30 增加设备运行时间
	private double sysUpTime = 0;

	private int telnetJumpId = 0;

	private SnmpUserTargetBean userTargetBean;// snmp v3的版本用户密码秘钥信息

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public void setObjSnmpVersion(Object snmpVersion) {
		if (snmpVersion == null) {
			this.snmpVersion = -1;
		} else {
			try {
				this.snmpVersion = Integer.parseInt(snmpVersion.toString());
			} catch (Exception e) {
				this.snmpVersion = -1;
			}
		}
	}

	public int getRoutId() {
		return routId;
	}

	public void setRoutId(int routId) {
		this.routId = routId;
	}

	public int getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(int deviceClass) {
		this.deviceClass = deviceClass;
	}

	public String getRoutName() {
		return routName;
	}

	public void setRoutName(String routName) {
		this.routName = routName;
	}

	public String getIPAddress() {
		return iPAddress;
	}

	public void setIPAddress(String address) {
		iPAddress = address;
	}

	public String getLoginUser() {

		return loginUser;
	}

	public void setLoginUser(String loginUser) {

		this.loginUser = loginUser;
	}

	public String getLoginPsw() {

		return loginPsw;
	}

	public void setLoginPsw(String loginPsw) {

		this.loginPsw = loginPsw;
	}

	public String getPrivilegePsw() {

		return privilegePsw;
	}

	public void setPrivilegePsw(String privilegePsw) {

		this.privilegePsw = privilegePsw;

		if (privilegePsw != null && privilegePsw.trim().length() != 0)
			setPriviledge(true);
	}

	public void setisPriviledge(boolean isPriviledge) {
		this.isPriviledge = isPriviledge;
	}

	public boolean isPriviledge() {
		return isPriviledge;
	}

	public void setPriviledge(boolean isPriviledge) {
		this.isPriviledge = isPriviledge;
	}

	public int getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(int telnetPort) {
		this.telnetPort = telnetPort;
	}

	public int getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
	}

	public String getSnmpCommunity() {
		return snmpCommunity;
	}

	public void setSnmpCommunity(String snmpCommunity) {
		this.snmpCommunity = snmpCommunity;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

	public int getDeviceId() {
		return this.routId;
	}

	public String getDeviceIp() {
		return this.iPAddress;
	}

	public int getDeviceType() {
		return this.routType;
	}

	public int getRoutType() {
		return routType;
	}

	public void setRoutType(int routType) {
		this.routType = routType;
	}

	public String getDeviceName() {
		return this.routName;
	}

	public String getPassword() {
		return this.loginPsw;
	}

	public String getPriviledge() {
		return this.privilegePsw;
	}

	public String getUsername() {
		return this.loginUser;
	}

	public int getOwnerprovince() {
		return ownerprovince;
	}

	public void setOwnerprovince(int ownerprovince) {
		this.ownerprovince = ownerprovince;
	}

	public int getParentroutid() {
		return parentroutid;
	}

	public void setParentroutid(int parentroutid) {
		this.parentroutid = parentroutid;
	}

	public int getSnmpVersion() {
		return this.snmpVersion;
	}

	public String getModelName() {
		if (modelName == null)
			return modelName;
		return modelName.toLowerCase();
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the loginModel
	 */
	public int getLoginModel() {
		return loginModel;
	}

	/**
	 * @param loginModel
	 *            the loginModel to set
	 */
	public void setLoginModel(int loginModel) {
		this.loginModel = loginModel;
	}

	/**
	 * @return the deviceVersion
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * @param deviceVersion
	 *            the deviceVersion to set
	 */
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getTelnetVPNparm() {
		return telnetVPNparm;
	}

	public void setTelnetVPNparm(String telnetVPNparm) {
		this.telnetVPNparm = telnetVPNparm;
	}

	public String getSnmpSetCommunity() {
		return snmpSetCommunity;
	}

	public void setSnmpSetCommunity(String snmpSetCommunity) {
		this.snmpSetCommunity = snmpSetCommunity;
	}

	public double getSysUpTime() {
		return sysUpTime;
	}

	public void setSysUpTime(double sysUpTime) {
		this.sysUpTime = sysUpTime;
	}

	/*
	 * 
	 * @return the telnetJumpId
	 */
	public int getTelnetJumpId() {
		return telnetJumpId;
	}

	/*

	 * 
	 * @param telnetJumpId the telnetJumpId to set
	 */
	public void setTelnetJumpId(int telnetJumpId) {
		this.telnetJumpId = telnetJumpId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("snmpPort", this.snmpPort)
				.append("snmpVersion", this.snmpVersion)
				.append("loginModel", this.loginModel)
				.append("routType", this.routType)
				.append("deviceName", this.getDeviceName())
				.append("loginUser", this.loginUser)
				.append("routName", this.routName)
				.append("username", this.getUsername())
				.append("password", this.getPassword())
				.append("modelName", this.modelName)
				.append("deviceId", this.getDeviceId())
				.append("parentroutid", this.parentroutid)
				.append("snmpCommunity", this.snmpCommunity)
				.append("deviceIp", this.getDeviceIp())
				.append("ownerprovince", this.ownerprovince)
				.append("routId", this.routId)
				.append("loginPsw", this.loginPsw)
				.append("deviceClass", this.deviceClass)
				.append("telnetPort", this.telnetPort)
				.append("deviceType", this.getDeviceType())
				.append("priviledge", this.getPriviledge())
				.append("IPAddress", this.getIPAddress())
				.append("priviledge", this.isPriviledge())
				.append("factory", this.factory)
				.append("privilegePsw", this.privilegePsw).toString();
	}

	public SnmpUserTargetBean getUserTargetBean() {
		return userTargetBean;
	}

	public void setUserTargetBean(SnmpUserTargetBean userTargetBean) {
		this.userTargetBean = userTargetBean;
	}

	public String getSysDescr() {
		return sysDescr;
	}

	public void setSysDescr(String sysDescr) {
		this.sysDescr = sysDescr;
	}

//	public String getAliasName() {
//		return aliasName;
//	}
//
//	public void setAliasName(String aliasName) {
//		this.aliasName = aliasName;
//	}

}
