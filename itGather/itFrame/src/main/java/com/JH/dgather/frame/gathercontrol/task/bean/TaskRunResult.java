/*
 * @(#)TaskRunResult.java 01/10/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.bean;

import java.util.ArrayList;
import java.util.List;

import com.JH.dgather.frame.util.DateFormate;

/**
 * <code>TaskRunResult</code> 任务运行结果
 * @author Wang Xuedan
 * @version 1.0, 01/10/2012
 */
public class TaskRunResult {
	/**
	 * 任务执行过程中，产生的日志信息
	 * 对应表：net_gathertaskresult中GorRemark字段
	 */
	private List<LogInfo> logInfo;
	/**
	 * 任务标记，对应net_taskmananger的runFlag;
	 * 2:全部成功, 3:失败, 4:部分成功
	 */
	private int gorSign = 0;
	/**
	 * 执行结束时间
	 */
	private String execEndTime = "";
	
	public TaskRunResult() {
		logInfo = new ArrayList<LogInfo>();
	}
	
	public void addInfo(String info) {
		logInfo.add(new LogInfo(info));
	}
	
	public String getInfoToString() {
		String rtn = "";
		for (LogInfo li : this.logInfo) {
			rtn += li.getLog() + "\r\n";
		}
		return rtn;
	}
	
	/**
	 * @return the logInfo
	 */
	public List<LogInfo> getLogInfo() {
		return logInfo;
	}
	
	/**
	 * @param logInfo the logInfo to set
	 */
	public void setLogInfo(List<LogInfo> logInfo) {
		this.logInfo = logInfo;
	}
	
	/**
	 * @return the gorSign
	 */
	public int getGorSign() {
		return gorSign;
	}
	
	/**
	 * @param gorSign the gorSign to set
	 */
	public void setGorSign(int gorSign) {
		this.gorSign = gorSign;
		/**
		 * 更新标记就代表任务已结束
		 */
		this.execEndTime = DateFormate.getDateTime();
	}
	
	/**
	 * @return the execEndTime
	 */
	public String getExecEndTime() {
		return execEndTime;
	}
	
}

class LogInfo {
	private String date;
	private String info;
	
	public LogInfo(String info) {
		this.date = DateFormate.getDateTime();
		if (info == null) {
			this.info = "";
		}
		else
			this.info = info;
	}
	
	public String getLog() {
		return "[" + this.date + "] " + this.info;
	}
}
