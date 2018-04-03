package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;

/**
 * CPU数据BEAN，对应net_cap_cpu表
 * @author Administrator
 *
 */
public class CpuCapBean {
	private int routId;
	private String moduleCode;
	private int processValue;
	private Date recordTime;
	
	public CpuCapBean() {
		moduleCode = "";
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
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}
	
	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	/**
	 * @return the processValue
	 */
	public int getProcessValue() {
		return processValue;
	}
	
	/**
	 * @param processValue the processValue to set
	 */
	public void setProcessValue(int processValue) {
		this.processValue = processValue;
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
	
}
