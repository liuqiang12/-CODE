/*
 * @(#)TaskObjectBean.java 01/09/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.bean;

import java.util.Date;
import java.util.HashMap;


/**
 * @author muyp
 *         采集对象bean综合net_gatherpublicobject和net_gathertempobject，同时考虑了采集结果信息
 */
public class TaskObjBean {
	private int gatherId; // 采集对象ID，数据库自增值
	/**
	 * 任务ID，对应taskBean中的taskid
	 */
	private int taskid;
	/**
	 * 采集对象ID，如果为设备则对应设备的deviceid,如果为链路则对应链路的lineid
	 */
	private int goid;
	/**
	 * 采集对象,存放采集对象信息
	 */
	private Object gatherObj;

	/**
	 * 对象采集间隔时间，每次采集完成后需做修改（当然也需要判断是否需要修改），单位：分钟
	 */
	private int gatherIntervalTime;
	/**
	 * 等待执行时间，每次采集完成需做修改
	 */
	private Date execTime;
	/**
	 * 端口名
	 */
	private HashMap<Integer, HashMap<Long, String[]>> portNameMap = new HashMap<Integer, HashMap<Long, String[]>>();
	/**
	 * 对象采集结果，0=成功；1=失败, -1表示未执行；
	 */
	private int result = -1;
	/**
	 * 失败消息
	 */
	private String errorMsg = "";

	private String extendInfo="";
	/**
	 * @return the gatherId
	 */
	public int getGatherId() {
		return gatherId;
	}

	/**
	 * @param gatherId
	 *            the gatherId to set
	 */
	public void setGatherId(int gatherId) {
		this.gatherId = gatherId;
	}

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public int getGoid() {
		return goid;
	}

	public void setGoid(int goid) {
		this.goid = goid;
	}

	public int getGatherIntervalTime() {
		return gatherIntervalTime;
	}

	public void setGatherIntervalTime(int gatherIntervalTime) {
		this.gatherIntervalTime = gatherIntervalTime;
	}

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public HashMap<Integer, HashMap<Long, String[]>> getPortNameLs() {
		return portNameMap;
	}

	public void setPortNameMap(HashMap<Integer, HashMap<Long, String[]>> portName) {
		this.portNameMap = portName;
	}

	/**
	 * 增加端口
	 * 
	 * @param portName
	 */
	public void addPortName(int routId, long portIndex, int portid, String portName) {
		HashMap<Long, String[]> portNameMap = this.portNameMap.get(routId);
		if (portNameMap == null || portNameMap.size() == 0)
			portNameMap = new HashMap<Long, String[]>();
		portNameMap.put(portIndex, new String[] { String.valueOf(portid), portName });
		this.portNameMap.put(routId, portNameMap);
	}

	/**
	 * @return the gatherObj
	 */
	public Object getGatherObj() {
		return gatherObj;
	}

	/**
	 * @param gatherObj
	 *            the gatherObj to set
	 */
	public void setGatherObj(Object gatherObj) {
		this.gatherObj = gatherObj;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg += "[ " + errorMsg + "],  ";
	}

	/*
	 * gatherId taskid goid gatherIntervalTime execTime result errorMsg
	 */
	public String toString() {
		return "gatherId:" + gatherId + ";taskid:" + taskid + ";goid:" + goid + ";gatherIntervalTime:" + gatherIntervalTime + ";execTime:" + execTime + ";result:" + result + ";errorMsg:" + errorMsg;
	}
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2013-8-14
	 * @return the extendInfo
	 */
	public String getExtendInfo() {
		return extendInfo;
	}

	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2013-8-14
	 * @param extendInfo the extendInfo to set
	 */
	public void setExtendInfo(String extendInfo) {
		if(extendInfo==null)
			extendInfo="";
		this.extendInfo = extendInfo;
	}
}
