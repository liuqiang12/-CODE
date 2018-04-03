package com.JH.dgather.frame.gathercontrol.task.BusinessBean;
/*
 * 对应Net_TraceRout_Detail表
 */
public class Net_TraceRout_Detail {
	private int detailId; //主键ID
	private int resultId; //结果ID
	private int hopNum; //步骤序号
	private String localDevice; //本端设备
	private String localPort; //本端端口
	private String des_Device; //对端设备
	private String des_Port; //对端端口
	private String description; //详细描述
	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getHopNum() {
		return hopNum;
	}
	public void setHopNum(int hopNum) {
		this.hopNum = hopNum;
	}
	public String getLocalDevice() {
		return localDevice;
	}
	public void setLocalDevice(String localDevice) {
		this.localDevice = localDevice;
	}
	public String getLocalPort() {
		return localPort;
	}
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}
	public String getDes_Device() {
		return des_Device;
	}
	public void setDes_Device(String des_Device) {
		this.des_Device = des_Device;
	}
	public String getDes_Port() {
		return des_Port;
	}
	public void setDes_Port(String des_Port) {
		this.des_Port = des_Port;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
