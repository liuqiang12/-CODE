package com.JH.dgather.frame.eventmanager.bean;

public class UnwarnEventBean extends BaseEventBean {
	private String flagValue;
	public UnwarnEventBean(int objId, int alarmType,long portid,  String alarmInfo) {
		super(objId, alarmType, portid,-1,null, alarmInfo);
	}
	
	public String getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}
	
	
}
