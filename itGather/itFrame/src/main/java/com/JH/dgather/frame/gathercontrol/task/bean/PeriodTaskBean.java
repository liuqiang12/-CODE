/*
 * @(#)PeriodTaskBean.java 01/09/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

/**

 */
public class PeriodTaskBean {
	/**
	 * 任务Id
	 */
	private int taskId;
	/**
	 * 采集周期
	 * 0:临时; 1:按天采集; 2: 按周采集; 3: 按月采集; 4: 按时采集
	 */
	private int gatherPeriods;
	/**
	 * 采集日
	 * 选择按周采集，该值标识周几
	 * 选择按月采集，该值标识几号
	 */
	private int gatherPeriodsDay;
	/**
	 * 采集开始时间，默认"00:00"
	 */
	private String gatherPeriodsTime;
	/**
	 * 采集结束时间，默认"00:00"
	 */
	private String gatherPeriodsEndTime;
	/**
	 * 间隔时间，单位: 分钟
	 */
	private int gatherIntervalTime;
	/**
	 * 是否记录标记. 0：记录（默认）。1：不记录
	 */
	private int recordFlag;
	/**
	 * 采集状态。0：激活（默认）；1：禁用
	 */
	private int status;
	/**
	 * 周期任务是否开启自动调度功能,1.表示开启
	 */
	private int dynamicCycle;
	/**
	 * 间隔周期递增值，单位:分钟
	 */
	private int increaseTime;
	/**
	 * 间隔周期最大值，单位:分钟
	 */
	private int increaseMaxTime;
	
	/**
	 * <code>PeriodTaskBean</code> 构造方法
	 * @param taskId: 任务ID
	 */
	public PeriodTaskBean(int taskId) {
		this.taskId = taskId;
		gatherPeriods = -1;
		gatherPeriodsDay = -1;
		gatherPeriodsTime = "00:00";
		gatherPeriodsEndTime = "00:00";
		gatherIntervalTime = 0;
		recordFlag = 0;
		status = 0;
		
		dynamicCycle = 0;
		increaseTime = 0;
		increaseMaxTime = 0;
	}
	
	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}
	
	/**
	 * @return the gatherPeriods
	 */
	public int getGatherPeriods() {
		return gatherPeriods;
	}
	
	/**
	 * @param gatherPeriods the gatherPeriods to set
	 */
	public void setGatherPeriods(int gatherPeriods) {
		this.gatherPeriods = gatherPeriods;
	}
	
	/**
	 * @return the gatherPeriodsDay
	 */
	public int getGatherPeriodsDay() {
		return gatherPeriodsDay;
	}
	
	/**
	 * @param gatherPeriodsDay the gatherPeriodsDay to set
	 */
	public void setGatherPeriodsDay(int gatherPeriodsDay) {
		this.gatherPeriodsDay = gatherPeriodsDay;
	}
	
	/**
	 * @return the gatherPeriodsStartTime
	 */
	public String getGatherPeriodsTime() {
		return gatherPeriodsTime;
	}
	
	/**
	 * @param gatherPeriodsStartTime the gatherPeriodsStartTime to set
	 */
	public void setGatherPeriodsTime(String gatherPeriodsTime) {
		this.gatherPeriodsTime = gatherPeriodsTime;
	}
	
	/**
	 * @return the gatherPeriodsEndTime
	 */
	public String getGatherPeriodsEndTime() {
		return gatherPeriodsEndTime;
	}
	
	/**
	 * @param gatherPeriodsEndTime the gatherPeriodsEndTime to set
	 */
	public void setGatherPeriodsEndTime(String gatherPeriodsEndTime) {
		this.gatherPeriodsEndTime = gatherPeriodsEndTime;
	}
	
	/**
	 * @return the gatherIntervalTime
	 */
	public int getGatherIntervalTime() {
		return gatherIntervalTime;
	}
	
	/**
	 * @param gatherIntervalTime the gatherIntervalTime to set
	 */
	public void setGatherIntervalTime(int gatherIntervalTime) {
		this.gatherIntervalTime = gatherIntervalTime;
	}
	
	/**
	 * @return the recordFlag
	 */
	public int getRecordFlag() {
		return recordFlag;
	}
	
	/**
	 * @param recordFlag the recordFlag to set
	 */
	public void setRecordFlag(int recordFlag) {
		this.recordFlag = recordFlag;
	}
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * @return the dynamicCycle
	 */
	public int getDynamicCycle() {
		return dynamicCycle;
	}
	
	/**
	 * @param dynamicCycle the dynamicCycle to set
	 */
	public void setDynamicCycle(int dynamicCycle) {
		this.dynamicCycle = dynamicCycle;
	}
	
	/**
	 * @return the increaseTime
	 */
	public int getIncreaseTime() {
		return increaseTime;
	}
	
	/**
	 * @param increaseTime the increaseTime to set
	 */
	public void setIncreaseTime(int increaseTime) {
		this.increaseTime = increaseTime;
	}
	
	/**
	 * @return the increaseMaxTime
	 */
	public int getIncreaseMaxTime() {
		return increaseMaxTime;
	}
	
	/**
	 * @param increaseMaxTime the increaseMaxTime to set
	 */
	public void setIncreaseMaxTime(int increaseMaxTime) {
		this.increaseMaxTime = increaseMaxTime;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("increaseMaxTime", this.increaseMaxTime).append("gatherPeriods",
				this.gatherPeriods).append("recordFlag", this.recordFlag).append("gatherPeriodsEndTime",
				this.gatherPeriodsEndTime).append("gatherPeriodsDay", this.gatherPeriodsDay).append("status", this.status)
				.append("gatherPeriodsStartTime", this.gatherPeriodsTime).append("dynamicCycle", this.dynamicCycle).append(
						"gatherIntervalTime", this.gatherIntervalTime).append("taskId", this.taskId).append("increaseTime",
						this.increaseTime).toString();
	}
	
}
