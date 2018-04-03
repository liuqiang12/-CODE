package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;

public class TemperatureCapBean {
	private int index;//模块索引
	private int TemperatureValue = -1;//温度值，可能是状态值、默认设置为-1表明未采集到
	private Date recordTime;
	private int routId;
	
	
	public TemperatureCapBean() {
		super();
		this.index = 0;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public int getTemperatureValue() {
		return TemperatureValue;
	}
	public void setTemperatureValue(int temperatureValue) {
		TemperatureValue = temperatureValue;
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
