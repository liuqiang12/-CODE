package com.JH.itBusi.comm.db;

public class KPIAlarmLevelConfig {
	private int ID;
	private int kpiID;
	private long thresholdLimit;// 阀值上限
	private long thresholdLowLimit;// 阀值下限
	private int alarmLevel;// 告警级别，从低到高分为0,1,2级（0=一般；1=轻微；3=严重）
	private String description;
	private String levelDetail;
	private Integer deviceID;// 如果为null，则表示是全局配置

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public KPIAlarmLevelConfig() {

	}

	public KPIAlarmLevelConfig(KPIAlarmLevelConfig cp) {
		this.ID = cp.ID;
		this.kpiID = cp.kpiID;
		this.thresholdLimit = cp.thresholdLimit;
		this.thresholdLowLimit = cp.thresholdLowLimit;
		this.alarmLevel = cp.alarmLevel;
		this.levelDetail = cp.levelDetail;
		this.deviceID = cp.deviceID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public int getKpiID() {
		return kpiID;
	}

	public void setKpiID(int kpiID) {
		this.kpiID = kpiID;
	}

	public long getThresholdLimit() {
		return thresholdLimit;
	}

	public void setThresholdLimit(long thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	public long getThresholdLowLimit() {
		return thresholdLowLimit;
	}

	public void setThresholdLowLimit(long thresholdLowLimit) {
		this.thresholdLowLimit = thresholdLowLimit;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevelDetail() {
		return levelDetail;
	}

	public void setLevelDetail(String levelDetail) {
		this.levelDetail = levelDetail;
	}

	@Override
	public String toString() {
		return "告警阀值配置：deviceID=" + deviceID + ",kpiID=" + kpiID
				+ ",thresholdLimit=" + thresholdLimit + ",thresholdLowLimit="
				+ thresholdLowLimit + ",alarmLevel=" + alarmLevel + ",ID=" + ID;
	}
}