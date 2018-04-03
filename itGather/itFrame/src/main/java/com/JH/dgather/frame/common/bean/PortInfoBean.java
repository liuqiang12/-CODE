package com.JH.dgather.frame.common.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.reflect.ReflectUtil;

public class PortInfoBean {
	private int taskId;
	private int portid;//数据库中端口关键字
	private String portName;//端口名
	private int portActive;//物理状态
	private int adminStatus;//管理及协议状态
	private String sideDeviceName;//对端设备
	private String sidePortName;//对端端口
	private String ipAddress;//IP地址
	private String mac;//MAC地址
	private int deviceId;//设备编号
	private int slotsNo;//归属槽号
	private int boardIndex;//归属板索引
	private int boardNo;//归属板卡号
	private int portCode;//端口号
	private float bandWidth;//带宽单位M
	private int waveLength;//端口波长
	private String portModel;//端口型号
	private int transferDistance;//传输距离
	private float assignation;//分配带宽
	private long portIndex;
	private String descr;//端口描述，对应字段descr
	private String alias;
	private int ifType;//端口类型:VLAN ...
	private String portType;//0:物理端口；1:逻辑端口
	private String lastChange;//持续运行时长
	

	public String getAlias() {
		if( this.alias==null)
			return new String(""); 
		else
		return this.alias;
	}
	
	public void setAlias(String alias) {
		if(alias ==null){
			this.alias = new String("");
			return;
		}
		if (alias.length() >=100) {
			this.alias = alias.substring(0,100);
		}
		else {
			this.alias = alias;
		}
		
	}
	
	
	private String WQType;
	
	//private String note;
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public String getPortName() {
		if (portName == null)
			return new String("");
		else
			return portName;
	}
	
	public void setPortName(String portName) {
		
		if(portName ==null){
			this.portName = new String("");
			return;
		}
		if (portName.length() > 100) {
			this.portName = portName.substring(0, 100);
		}
		else {
			this.portName = portName;
		}
		
	}
	
	public String getStrPortActive() {
		switch (portActive) {
			case 1:
				return new String("在用");
			case 2:
				return new String("空闲");
			default:
				return new String("不确定状态");
		}
	}
	
	public int getPortActive() {
		return portActive;
	}
	
	public void setPortActive(int portActive) {
		this.portActive = portActive;
	}
	
	public String getSideDeviceName() {
		if (sideDeviceName == null)
			return new String("");
		else
			return sideDeviceName;
	}
	
	public void setSideDeviceName(String sideDeviceName) {
		if (sideDeviceName != null && sideDeviceName.length() > 50) {
			this.sideDeviceName = sideDeviceName.substring(0, 50);
		}
		else {
			this.sideDeviceName = sideDeviceName;
		}
		
	}
	
	public String getSidePortName() {
		if (sidePortName == null)
			return new String("");
		else
			return sidePortName;
	}
	
	public void setSidePortName(String sidePortName) {
		if (sidePortName != null && sidePortName.length() > 100) {
			this.sidePortName = sidePortName.substring(0, 100);
		}
		else {
			this.sidePortName = sidePortName;
		}
		
	}
	
	public String getIpAddress() {
		if (ipAddress == null)
			return new String("");
		else
			return ipAddress;
	}
	
	public void setIpAddress(String ipAdddress) {
		this.ipAddress = ipAdddress;
	}
	
	public String getMac() {
		if (mac == null)
			return new String("");
		else
			return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public float getBandWidth() {
		return bandWidth;
	}
	
	public void setBandWidth(float bandWidth) {
		this.bandWidth = bandWidth;
	}
	
	public int getBoardNo() {
		return boardNo;
	}
	
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getPortCode() {
		return portCode;
	}
	
	public void setPortCode(int portCode) {
		this.portCode = portCode;
	}
	
	public String getPortModel() {
		if (portModel == null)
			return new String("");
		else
			return portModel;
	}
	
	public void setPortModel(String portModel) {
		this.portModel = portModel;
	}
	
	public int getSlotsNo() {
		return slotsNo;
	}
	
	public void setSlotsNo(int slotsNo) {
		this.slotsNo = slotsNo;
	}
	
	public int getTransferDistance() {
		return transferDistance;
	}
	
	public void setTransferDistance(int transferDistance) {
		this.transferDistance = transferDistance;
	}
	
	public int getWaveLength() {
		return waveLength;
	}
	
	public void setWaveLength(int waveLength) {
		this.waveLength = waveLength;
	}
	
	public String getWQType() {
		if (WQType == null)
			return new String("");
		else
			return WQType;
	}
	
	public void setWQType(String type) {
		WQType = type;
	}
	
	public boolean equals(Object obj) {
		PortInfoBean temp = null;
		ArrayList<String> compareFields = new ArrayList<String>() {
			{
				add("deviceId");
				add("portName");
			}
		};
		if (obj instanceof PortInfoBean) {
			temp = (PortInfoBean) obj;
			
			return ReflectUtil.beanCompare(this, temp, compareFields);
		}
		else {
			return false;
		}
		
	}
	
	public int getBoardIndex() {
		
		return boardIndex;
	}
	
	public void setBoardIndex(int boardIndex) {
		
		this.boardIndex = boardIndex;
	}
	
	public long getPortIndex() {
		
		return portIndex;
	}
	
	public void setPortIndex(long portIndex) {
		
		this.portIndex = portIndex;
	}


	public float getAssignation() {
		return assignation;
	}

	public void setAssignation(float assignation) {
		this.assignation = assignation;
	}
	/**
	 * @return the portType
	 */
	public String getPortType() {
		return portType;
	}
	
	/**
	 * @param portType the portType to set
	 */
	public void setPortType(String portType) {
		this.portType = portType;
	}

	/*
	 * @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2014-9-24
	 *(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PortInfoBean [taskId=" + taskId + ", portName=" + portName + ", portActive=" + portActive + ", sideDeviceName=" + sideDeviceName + ", sidePortName=" + sidePortName + ", ipAddress="
				+ ipAddress + ", mac=" + mac + ", deviceId=" + deviceId + ", slotsNo=" + slotsNo + ", boardIndex=" + boardIndex + ", boardNo=" + boardNo + ", portCode=" + portCode + ", bandWidth="
				+ bandWidth + ", waveLength=" + waveLength + ", portModel=" + portModel + ", transferDistance=" + transferDistance + ", assignation=" + assignation + ", portIndex=" + portIndex
				+ ", descr=" + descr + ", alias=" + alias + ", portType=" + portType + ", WQType=" + WQType + "]";
	}

	public int getIfType() {
		return ifType;
	}

	public void setIfType(int ifType) {
		this.ifType = ifType;
	}

	public int getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getLastChange() {
		return lastChange;
	}

	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}

	public int getPortid() {
		return portid;
	}

	public void setPortid(int portid) {
		this.portid = portid;
	}	
	 public void setDescr(String descr ){
		 if(this.descr==null)
			 this.descr ="";
		 else
			 this.descr = descr;
	 }
	 public String getDescr(){
		 if(this.descr==null)
			 return new String(""); 
		 else
			 return this.descr;
	 }
}
