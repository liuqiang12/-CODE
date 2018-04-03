package com.JH.dgather.frame.common.snmpGather;

public class NetFanEntityBean {
	
	/**
	 * 设备id
	 */
	private int deviceId;
	/**
	 * 风扇id,取值来源于fanIndex
	 */
	private int fanId;
	/**
	 * 风扇索引
	 */
	private String fanIndex;
	/**
	 * 风扇状态描述
	 */
	private String fanStatusDescr;
	/**
	 * 风扇状态
	 */
	private String fanStatus;
	
	private int status;
	
	public int getStatus() {
		try {
			status = Integer.parseInt(this.fanStatus);
		} catch (Exception e) {
			
		}
		
		return status;
	}
	
	public void setStatus(int status) {
		
		this.status = status;
	}
	
	public int getFanId() {
		try {
			fanId = Integer.parseInt(fanIndex.trim());
		} catch (Exception e) {
			return 0;
		}
		return fanId;
	}
	
	public void setFanId(int fanId) {
		
		this.fanId = fanId;
	}
	
	public String getFanIndex() {
		
		return fanIndex;
	}
	
	public void setFanIndex(String fanIndex) {
		
		this.fanIndex = fanIndex;
	}
	
	public String getFanStatusDescr() {
		
		return fanStatusDescr;
	}
	
	public void setFanStatusDescr(String fanStatusDescr) {
		
		this.fanStatusDescr = fanStatusDescr;
	}
	
	public String getFanStatus() {
		
		return fanStatus;
	}
	
	public void setFanStatus(String fanState) {
		
		this.fanStatus = fanState;
	}
	
	public int getDeviceId() {
		
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		
		this.deviceId = deviceId;
	}
	
}
