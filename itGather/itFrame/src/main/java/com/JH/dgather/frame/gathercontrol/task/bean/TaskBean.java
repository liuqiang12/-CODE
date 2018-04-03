/*
 * @(#)TaskBean.java 01/09/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <code>TaskBean</code> 任务Bean，与对应任务信息表一一对应
 * @version 1.0, 01/09/2012
 */
public class TaskBean {
	/**
	 * 任务ID
	 */
	private int taskId;
	/**
	 * 采集类型
	 */
	private int gatherClass;
	
	//采集的类型中的某指标
	private int kpiId;

	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务类型. 0: 临时(默认) 1：周期
	 */
	private int taskType;
	/**
	 * 定义用户, sys_user.userId
	 */
	private String userId;
	/**
	 * 定义时间
	 */
	private Date configTime;
	/**
	 * 执行时间, 用户未指定时间，默认当前时间
	 */
	private Date execTime;
	/**
	 * 运行标志
	 * 0．等待执行（默认）；1．正在执行；2．采集成功；3．采集失败；4、部分成功
	 */
	private int runFlag;
	/**
	 * 任务标志
	 * 0.未处理；1.已处理
	 */
	private int taskFlag;

	/**
	 * 扩展字段
	 */
	private String taskextend; 
	/**
	 * 任务执行结果，只是针对任务而言，对采集对象无关
	 */
	private TaskRunResult taskRunResult = new TaskRunResult();
	/**
	 * 如果任务类型为“周期任务”，则PeriodTaskBean中包含周期任务自调度时间信息
	 */
	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}
	
	/**
	 * @return the gatherClass
	 */
	public int getGatherClass() {
		return gatherClass;
	}
	
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * @return the taskType
	 */
	public int getTaskType() {
		return taskType;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the configTime
	 */
	public Date getConfigTime() {
		return configTime;
	}
	
	/**
	 * @param configTime the configTime to set
	 */
	public void setConfigTime(Date configTime) {
		this.configTime = configTime;
	}
	
	/**
	 * @return the execTime
	 */
	public Date getExecTime() {
		return execTime;
	}
	
	/**
	 * @param execTime the execTime to set
	 */
	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}
	
	/**
	 * @return the runFlag
	 */
	public int getRunFlag() {
		return runFlag;
	}
	
	/**
	 * @param runFlag the runFlag to set
	 */
	public void setRunFlag(int runFlag) {
		this.runFlag = runFlag;
	}
	
	/**
	 * @return the taskFlag
	 */
	public int getTaskFlag() {
		return taskFlag;
	}
	
	/**
	 * @param taskFlag the taskFlag to set
	 */
	public void setTaskFlag(int taskFlag) {
		this.taskFlag = taskFlag;
	}
	
	/**
//	 * @return the periodTaskBean
//	 */
//	public PeriodTaskBean getPeriodTaskBean() {
//		return periodTaskBean;
//	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("taskType", this.taskType).append("taskFlag", this.taskFlag).append(
				"gatherClass", this.gatherClass).append("configTime", this.configTime).append("runFlag", this.runFlag).append(
				"taskName", this.taskName).append("taskId", this.taskId).append("execTime", this.execTime).append("userId",
				this.userId).append("taskextend",this.taskextend).toString();
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setGatherClass(int gatherClass) {
		this.gatherClass = gatherClass;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	
	public TaskRunResult getTaskRunResult() {
		return this.taskRunResult;
		
	}

	public String getTaskextend() {
		return taskextend;
	}

	public void setTaskextend(String taskextend) {
		this.taskextend = taskextend;
	}

	public int getKpiId() {
		return kpiId;
	}

	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}
	
}
