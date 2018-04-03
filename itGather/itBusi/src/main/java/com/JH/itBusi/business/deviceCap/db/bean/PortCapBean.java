package com.JH.itBusi.business.deviceCap.db.bean;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/*
 * 端口性能表
 */
public class PortCapBean {
	public static final int IFSTATUS_UP = 1;
	public static final int IFSTATUS_DOWN = 2;
	private long id;
	private String portName;
	private long inputFlux = -1;// 入流量KB、默认设置为-1表明未采集到
	private long outputFlux = -1;// 出流量、默认设置为-1表明未采集到
	private int portStatus = 2;// 端口状态，1表示UP。2表示DOWN
	private int adminStatus = 2;// 端口状态，1表示UP。2表示DOWN
	private int portId;
	private long ifInnucastPkts = -1;// 入广播包 muyp modify
	private Date recordTime =  new  Date();
	private int routId;
	private Double inputFluxCount ;// 入流量KB、默认设置为-1表明未采集到
	private Double outputFluxCount ;// 出流量、默认设置为-1表明未采集到
	private long portIndex;
	private String execTime;
	private boolean isSaveHis = true;
//
	private String upinputRecordTime;
	private String upoutputRecordTime;
	private String inputRecordTime;
	private String outputRecordTime;

	private Double upinputfluxCount;
	private Double upoutputfluxcount;

	private String addtime;
	private int isInsert = 1; 	//确认当前记录是否新增

	private int daoFlag = 0;		//数据库操作标记位。0: 正常，1: 表示为第一次数据，只insert当前表，2：表示数据异常，只update当前表的SNMP采集数据信息, 3: 表示不作任何数据库处理， 4：端口DOWN

	public int getDaoFlag() {
		return daoFlag;
	}

	public void setDaoFlag(int daoFlag) {
		this.daoFlag = daoFlag;
	}

	public long getId() {
		return id;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(int isInsert) {
		this.isInsert = isInsert;
	}

	public long getInputFlux() {
		return inputFlux;
	}

	public void setInputFlux(long inputFlux) {
		this.inputFlux = inputFlux;
	}

	public long getOutputFlux() {
		return outputFlux;
	}

	public void setOutputFlux(long outputFlux) {
		this.outputFlux = outputFlux;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(int portStatus) {
		this.portStatus = portStatus;
	}

	public int getPortId() {
		return portId;
	}

	public void setPortId(int portId) {
		this.portId = portId;
	}

	public long getIfInnucastPkts() {
		return ifInnucastPkts;
	}

	public void setIfInnucastPkts(long ifInnucastPkts) {
		this.ifInnucastPkts = ifInnucastPkts;
	}

	/**
	 * @return the recordTime
	 */
	public Date getRecordTime() {
		return recordTime;
	}

	/**
	 * @param recordTime
	 *            the recordTime to set
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * @return the routId
	 */
	public int getRoutId() {
		return routId;
	}

	/**
	 * @param routId
	 *            the routId to set
	 */
	public void setRoutId(int routId) {
		this.routId = routId;
	}

	public Double getInputFluxCount() {
		return inputFluxCount;
	}

	public void setInputFluxCount(Double inputFluxCount) {
		this.inputFluxCount = inputFluxCount;
	}

	public Double getOutputFluxCount() {
		return outputFluxCount;
	}

	public void setOutputFluxCount(Double outputFluxCount) {
		this.outputFluxCount = outputFluxCount;
	}

	public long getPortIndex() {
		return portIndex;
	}

	public void setPortIndex(long portIndex) {
		this.portIndex = portIndex;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public boolean isSaveHis() {
		return isSaveHis;
	}

	public void setSaveHis(boolean isSaveHis) {
		if (this.isSaveHis)
			this.isSaveHis = isSaveHis;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("inputFlux", this.inputFlux).append("portStatus", this.portStatus).append("portId", this.portId).append("portName", this.portName).append("ifInnucastPkts", this.ifInnucastPkts).append("routId", this.routId).append("recordTime", this.recordTime)
				.append("outputFlux", this.outputFlux).toString();
	}

	public String getUpinputRecordTime() {
		return upinputRecordTime;
	}

	public void setUpinputRecordTime(String upinputRecordTime) {
		this.upinputRecordTime = upinputRecordTime;
	}

	public String getUpoutputRecordTime() {
		return upoutputRecordTime;
	}

	public void setUpoutputRecordTime(String upoutputRecordTime) {
		this.upoutputRecordTime = upoutputRecordTime;
	}

	public Double getUpinputfluxCount() {
		return upinputfluxCount;
	}

	public void setUpinputfluxCount(Double upinputfluxCount) {
		this.upinputfluxCount = upinputfluxCount;
	}

	public Double getUpoutputfluxcount() {
		return upoutputfluxcount;
	}

	public void setUpoutputfluxcount(Double upoutputfluxcount) {
		this.upoutputfluxcount = upoutputfluxcount;
	}

	public String getInputRecordTime() {
		return inputRecordTime;
	}

	public void setInputRecordTime(String inputRecordTime) {
		this.inputRecordTime = inputRecordTime;
	}

	public String getOutputRecordTime() {
		return outputRecordTime;
	}

	public void setOutputRecordTime(String outputRecordTime) {
		this.outputRecordTime = outputRecordTime;
	}

	public int getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

}
