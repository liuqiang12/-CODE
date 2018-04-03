package com.JH.dgather.frame.eventmanager.bean;

import java.util.Date;

import com.JH.dgather.frame.util.DateFormate;

public class WarnEventBean extends BaseEventBean {
	private int alarmLevelId; // 引用Net_KPIAlarmLevelConfig表的id字段. 该属性对应该告警的某个阀值
	private double comparaValue; // 与上次相比的差异值,负值表示下降，正值表示增加，主要为I/O、SDH、路由业务设计
//	private String flagValue; // 巡检元素的标志号，（如：当巡检cpu时，该标志号标志巡检的具体是哪一个cpu，因为一个设备可能存在多个cpu）。
	private String alarmTime; // 告警时间
	private int alarmSend = 0; // 发送状态： 0 未发送 1已经发送 2发送失败
	private Date alarmSendTime; // 告警信息发送时间
	private String alarmRemark; // 告警备注信息
	private boolean saveHis;

	public WarnEventBean(int objId, int alarmType,int portId, int alarmlevel,String objname, String alarmInfo) {
		super(objId, alarmType,portId, alarmlevel, objname,alarmInfo);
//		flagValue = "";
		alarmRemark = "";
	}



	public int getAlarmLevelId() {
		return alarmLevelId;
	}

	public void setAlarmLevelId(int alarmLevelId) {
		this.alarmLevelId = alarmLevelId;
	}


	public double getComparaValue() {
		return comparaValue;
	}

	public void setComparaValue(double comparaValue) {
		this.comparaValue = comparaValue;
	}

	public void setComparaValue(long comparaValue) {
		this.setComparaValue((double) comparaValue);
	}

	public void setComparaValue(float comparaValue) {
		this.setComparaValue((double) comparaValue);
	}

	public void setComparaValue(int comparaValue) {
		this.setComparaValue((double) comparaValue);
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = DateFormate.dateToStr(alarmTime, "yyyy-MM-dd HH:mm:ss");
		;
	}

	public int getAlarmSend() {
		return alarmSend;
	}

	public void setAlarmSend(int alarmSend) {
		if (this.alarmSend == 0)
			this.alarmSend = alarmSend;
	}

	public Date getAlarmSendTime() {
		return alarmSendTime;
	}

	public void setAlarmSendTime(Date alarmSendTime) {
		this.alarmSendTime = alarmSendTime;
	}

	public String getAlarmRemark() {
		return alarmRemark;
	}

	public void setAlarmRemark(String alarmRemark) {
		this.alarmRemark = alarmRemark;
	}

	public boolean isSaveHis() {
		return saveHis;
	}

	public void setSaveHis(boolean saveHis) {
		this.saveHis = saveHis;
	}




}
