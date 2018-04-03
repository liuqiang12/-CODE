package com.idc.model;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * U位资源信息情况.......
 */
@SuppressWarnings("serial")
public class TicketUnitGrid implements Serializable {
	public static final String tableName = "IDC_RUN_TICKET_UNIT";

	@Id
	@GeneratedValue
	private String id; //ID:序列号UUID  Guid
	private Long ticketInstId; //   工单实例ID
	private Long ticketResouceId;//工单资源ID
	private String targetId;//资源id
	private String targetName;//资源对象名称
	private String belongName;//所属对象名称
	private String belongId;//所属对象Id
	private String uheight;//u位数
	private String usedRackUnIt;//占用U位
	private String pduPowertype;//电源类型
	private String ratedelectricenergy;//额定电量
	private String renttype;//出租类型
	private String status;//状态
	private String statusName;//状态名称

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public String getUheight() {
		return uheight;
	}

	public void setUheight(String uheight) {
		this.uheight = uheight;
	}

	public String getUsedRackUnIt() {
		return usedRackUnIt;
	}

	public void setUsedRackUnIt(String usedRackUnIt) {
		this.usedRackUnIt = usedRackUnIt;
	}

	public String getPduPowertype() {
		return pduPowertype;
	}

	public void setPduPowertype(String pduPowertype) {
		this.pduPowertype = pduPowertype;
	}

	public String getRatedelectricenergy() {
		return ratedelectricenergy;
	}

	public void setRatedelectricenergy(String ratedelectricenergy) {
		this.ratedelectricenergy = ratedelectricenergy;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getBelongId() {
		return belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}

	public String getRenttype() {
		return renttype;
	}

	public void setRenttype(String renttype) {
		this.renttype = renttype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTicketInstId() {
		return ticketInstId;
	}

	public void setTicketInstId(Long ticketInstId) {
		this.ticketInstId = ticketInstId;
	}

	public Long getTicketResouceId() {
		return ticketResouceId;
	}

	public void setTicketResouceId(Long ticketResouceId) {
		this.ticketResouceId = ticketResouceId;
	}
}