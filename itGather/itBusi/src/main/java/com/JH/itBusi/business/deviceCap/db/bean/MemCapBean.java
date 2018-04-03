package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;

public class MemCapBean {
	private Date recordTime;
	private int routId;
	private String memCode;
	private int usedValue;
	
	public MemCapBean() {
		memCode = "";
	}
	
	/**
	 * @return the recordTime
	 */
	public Date getRecordTime() {
		return recordTime;
	}
	
	/**
	 * @param recordTime the recordTime to set
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	/**
	 * @return the routId
	 */
	public int getRoutId() {
		return routId;
	}
	
	/**
	 * @param routId the routId to set
	 */
	public void setRoutId(int routId) {
		this.routId = routId;
	}
	
	/**
	 * @return the memCode
	 */
	public String getMemCode() {
		return memCode;
	}
	
	/**
	 * @param memCode the memCode to set
	 */
	public void setMemCode(String memCode) {
		this.memCode = memCode;
	}
	
	/**
	 * @return the usedValue
	 */
	public int getUsedValue() {
		return usedValue;
	}
	
	/**
	 * @param usedValue the usedValue to set
	 */
	public void setUsedValue(int usedValue) {
		this.usedValue = usedValue;
	}
	
}
