package com.JH.dgather.frame.gathercontrol.task.BusinessBean;

import java.util.ArrayList;

/*
 * 对应Net_TraceInfo表
 */
public class Net_TraceInfo {
	private int traceId; //tracert标识
	private String traceName; //tracert名称
	private int deviceId; //源设备ID
	private String srcIp; //源设备IP
	private int desId; // 目的设备ID
	private String desIp; // 目的设备IP
	private int traceType; // tracert类别
	private String vpnPara; //vpn参数
	private String description; // tracert描述
	private String createUser; // 定义用户
	private Net_TraceRout_Result traceRoutResult;//采集结果
	private ArrayList<String> realTrace;//将隐藏IP替换后tracert 的实际路由
	private ArrayList<Net_TraceRout_Detail> traceRout_DetailLs;//将路由装换成实际物理设备端口连接
	private String routName;//源设备名称
	private String routIp;// 源设备IP
	public String getRoutIp() {
		return routIp;
	}
	public void setRoutIp(String routIp) {
		this.routIp = routIp;
	}
	public String getRoutName() {
		return routName;
	}
	public void setRoutName(String routName) {
		this.routName = routName;
	}
	public int getTraceId() {
		return traceId;
	}
	public void setTraceId(int traceId) {
		this.traceId = traceId;
	}
	public String getTraceName() {
		return traceName;
	}
	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public String getSrcIp() {
		return srcIp;
	}
	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
	public int getDesId() {
		return desId;
	}
	public void setDesId(int desId) {
		this.desId = desId;
	}
	public String getDesIp() {
		return desIp;
	}
	public void setDesIp(String desIp) {
		this.desIp = desIp;
	}
	public int getTraceType() {
		return traceType;
	}
	public void setTraceType(int traceType) {
		this.traceType = traceType;
	}
	public String getVpnPara() {
		return vpnPara;
	}
	public void setVpnPara(String vpnPara) {
		this.vpnPara = vpnPara;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public ArrayList<String> getRealTrace() {
		return realTrace;
	}
	public void setRealTrace(ArrayList<String> realTrace) {
		this.realTrace = realTrace;
	}
	public ArrayList<Net_TraceRout_Detail> getTraceRout_DetailLs() {
		return traceRout_DetailLs;
	}
	public void setTraceRout_DetailLs(ArrayList<Net_TraceRout_Detail> traceRout_DetailLs) {
		this.traceRout_DetailLs = traceRout_DetailLs;
	}
	public Net_TraceRout_Result getTraceRoutResult() {
		return traceRoutResult;
	}
	public void setTraceRoutResult(Net_TraceRout_Result traceRoutResult) {
		this.traceRoutResult = traceRoutResult;
	}

}
