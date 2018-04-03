package com.JH.dgather.frame.common.snmpGather;

public class NetPowerEntityBean {
	/**
	 * 设备id
	 */
	private int deviceId;
	/**
	 * 电源id，取值来源于supplyIndex
	 */
	private int powerId;
	/**
	 * 电源索引
	 */
	private String supplyIndex;
	/**
	 * 电源状态描述
	 */
	private String supplyStatusDescr;
	/**
	 * 电源状态
	 */
	private String supplyState;
	/**
	 * 电源类型名称（交流，直流）
	 */
	private String supplySource;
	
	private int status;
	
	public int getStatus() {
		try {
			status = Integer.parseInt(this.supplyState);
		} catch (Exception e) {
			
		}
		
		return status;
	}
	
	public void setStatus(int status) {
		
		this.status = status;
	}
	
	public String getSupplyIndex() {
		return supplyIndex;
	}
	
	public void setSupplyIndex(String supplyIndex) {
		this.supplyIndex = supplyIndex;
	}
	
	public String getSupplyStatusDescr() {
		return supplyStatusDescr;
	}
	
	public void setSupplyStatusDescr(String supplyStatusDescr) {
		this.supplyStatusDescr = supplyStatusDescr;
	}
	
	public String getSupplyState() {
		return supplyState;
	}
	
	public void setSupplyState(String supplyState) {
		this.supplyState = supplyState;
	}
	
	public String getSupplySource() {
		return supplySource;
	}
	
	public void setSupplySource(String supplySource) {
		this.supplySource = supplySource;
	}
	
	public int getPowerId() {
		
		try {
			powerId = Integer.parseInt(supplyIndex.trim());
		} catch (Exception e) {
			return 0;
		}
		
		return powerId;
	}
	
	public void setPowerId(int powerId) {
		
		this.powerId = powerId;
	}
	
	public int getDeviceId() {
		
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		
		this.deviceId = deviceId;
	}
}
