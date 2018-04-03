package com.JH.dgather.frame.eventalarmsender.db.bean;

public class WebServiceAlarmSentBean {
	private String name;
	private String wsdlLocation;
	private int sentResult;
	private String errorMsg;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWsdlLocation() {
		return wsdlLocation;
	}
	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}
	public int getSentResult() {
		return sentResult;
	}
	public void setSentResult(int sentResult) {
		this.sentResult = sentResult;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
