package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;

/*
 * 电源性能数据
 */
public class PwrCapBean {
	private int index;//电源索引号
	private int statusValue;//电源状态
	private Date recordTime;
	private int routId;
	
	public PwrCapBean() {
		
		super();
		this.index = 0;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getStatus() {
		return statusValue;
	}
	
	public void setStatus(int status) {
		this.statusValue = status;
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
	
}
