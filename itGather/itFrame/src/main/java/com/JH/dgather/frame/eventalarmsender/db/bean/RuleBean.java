package com.JH.dgather.frame.eventalarmsender.db.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.JH.dgather.frame.globaldata.GloableDataArea;

public class RuleBean {
	public Logger logger = Logger.getLogger(RuleBean.class);
	private int configId;
	private String createUserName;
	private String deviceIds;  //如果是链路，存储的是lineid列表，否则为设备列表
	private String userIds;
	private String alarmLevel;
	private int alarmType;
	private int sendType;
	private int status;

	private String objIds;  //如果是链路，存储的是所有链路的源设备列表，否则为空
	
	private String telnum;
	
	private Map<String, String> sendKPIIdMap = new HashMap<String, String>();
	
	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public RuleBean() {
		configId = -1;
		this.createUserName = "";
		this.deviceIds = "";
		this.userIds = "";
		this.alarmLevel = "";
		this.alarmType = 0;
		this.sendType = 0;
		this.status = 0;
		objIds = "";
	}

	public String getObjIds() {
		return objIds;
	}

	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}

	/**
	 * @return the configId
	 */
	public int getConfigId() {
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(int configId) {
		this.configId = configId;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the deviceIds
	 */
	public String getDeviceIds() {
		return deviceIds;
	}

	/**
	 * @param deviceIds the deviceIds to set
	 */
	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}

	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return the alarmLevel
	 */
	public String getAlarmLevel() {
		return alarmLevel;
	}

	/**
	 * @param alarmLevel the alarmLevel to set
	 */
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = parseAlarmLevel(alarmLevel);
	}

	/**
	 * @return the alarmType
	 */
	public int getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the sendType
	 */
	public int getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(int sendType) {
		this.sendType = sendType;
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("alarmType", this.alarmType).append("sendType", this.sendType).append("configId", this.configId).append("status", this.status).append("userIds", this.userIds).append("deviceIds", this.deviceIds).append("createUserName", this.createUserName)
				.append("alarmLevel", this.alarmLevel).toString();
	}

	private String parseAlarmLevel(String level) {
		int len = level.length();
		String rtn = "";
		if (len == 4) {
			if (level.charAt(0) == '1') {
				rtn += "0,";
			}
			if (level.charAt(1) == '1') {
				rtn += "1,";
			}
			if (level.charAt(2) == '1') {
				rtn += "2,";
			}
			if (level.charAt(3) == '1') {
				rtn += "5,";
			}

		}
		return rtn;
	}

	public void putSendKPIId(String kpiid) {
		this.sendKPIIdMap.put(kpiid, kpiid);
	}

	public boolean acceptTheKPI(int kpiId) {
		if(sendType != GloableDataArea.ALARM_SENDTYPE_CMPP)
			return true;
		return this.sendKPIIdMap.containsKey(kpiId+"");
	}

}
