package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;

public class FanCapBean {
	private int index;//风扇模块索引
	private int statusValue;//风扇状态
	private Date recordTime;
	private int routId;
	
	
	public FanCapBean() {
		super();
		this.index=0;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getStatusValue() {
		return statusValue;
	}
	
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
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
