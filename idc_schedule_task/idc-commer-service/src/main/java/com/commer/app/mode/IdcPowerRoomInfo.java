package com.commer.app.mode;


import java.util.Date;

/**
 * IdcPowerRoomInfo entity. @author MyEclipse Persistence Tools
 */

public class IdcPowerRoomInfo implements java.io.Serializable {

	// Fields



	private String idcPowerRoomId;
	private String idcPowerRoomCode;
	private String idcPowerRoomName;
	private String idcPowerBuidingId;
	private Double idcAmout;
	private String idcStatus;
	private Date idcStartTime;
	private Date idcEndTime;
	private Date idcCreateTime;
	private Double idcBeforeDiff;
	private Double idcAirAdjustAmout;
	private Double idcDeviceAmout;
	private Double idcAirAdjustBeforeDiff;
	private Double idcDeviceBeforeDiff;
	private Double idcOtherAmout;
	private Double idcOtherBeforeDiff;

	// Constructors


	/** default constructor */
	public IdcPowerRoomInfo() {
	}

	/** minimal constructor */
	public IdcPowerRoomInfo(String idcPowerRoomId) {
		this.idcPowerRoomId = idcPowerRoomId;
	}

	/** full constructor */
	public IdcPowerRoomInfo(String idcPowerRoomId, String idcPowerRoomCode,
							String idcPowerRoomName, String idcPowerBuidingId, Double idcAmout,
							String idcStatus, Date idcStartTime, Date idcEndTime,
							Date idcCreateTime, Double idcBeforeDiff,
							Double idcAirAdjustAmout, Double idcDeviceAmout,
							Double idcAirAdjustBeforeDiff, Double idcDeviceBeforeDiff) {
		this.idcPowerRoomId = idcPowerRoomId;
		this.idcPowerRoomCode = idcPowerRoomCode;
		this.idcPowerRoomName = idcPowerRoomName;
		this.idcPowerBuidingId = idcPowerBuidingId;
		this.idcAmout = idcAmout;
		this.idcStatus = idcStatus;
		this.idcStartTime = idcStartTime;
		this.idcEndTime = idcEndTime;
		this.idcCreateTime = idcCreateTime;
		this.idcBeforeDiff = idcBeforeDiff;
		this.idcAirAdjustAmout = idcAirAdjustAmout;
		this.idcDeviceAmout = idcDeviceAmout;
		this.idcAirAdjustBeforeDiff = idcAirAdjustBeforeDiff;
		this.idcDeviceBeforeDiff = idcDeviceBeforeDiff;
	}

	// Property accessors

	public Double getIdcOtherAmout() {
		return idcOtherAmout;
	}

	public void setIdcOtherAmout(Double idcOtherAmout) {
		this.idcOtherAmout = idcOtherAmout;
	}

	public Double getIdcOtherBeforeDiff() {
		return idcOtherBeforeDiff;
	}

	public void setIdcOtherBeforeDiff(Double idcOtherBeforeDiff) {
		this.idcOtherBeforeDiff = idcOtherBeforeDiff;
	}

	public String getIdcPowerRoomId() {
		return this.idcPowerRoomId;
	}

	public void setIdcPowerRoomId(String idcPowerRoomId) {
		this.idcPowerRoomId = idcPowerRoomId;
	}

	public String getIdcPowerRoomCode() {
		return this.idcPowerRoomCode;
	}

	public void setIdcPowerRoomCode(String idcPowerRoomCode) {
		this.idcPowerRoomCode = idcPowerRoomCode;
	}

	public String getIdcPowerRoomName() {
		return this.idcPowerRoomName;
	}

	public void setIdcPowerRoomName(String idcPowerRoomName) {
		this.idcPowerRoomName = idcPowerRoomName;
	}

	public String getIdcPowerBuidingId() {
		return this.idcPowerBuidingId;
	}

	public void setIdcPowerBuidingId(String idcPowerBuidingId) {
		this.idcPowerBuidingId = idcPowerBuidingId;
	}

	public Double getIdcAmout() {
		return this.idcAmout;
	}

	public void setIdcAmout(Double idcAmout) {
		this.idcAmout = idcAmout;
	}

	public String getIdcStatus() {
		return this.idcStatus;
	}

	public void setIdcStatus(String idcStatus) {
		this.idcStatus = idcStatus;
	}

	public Date getIdcStartTime() {
		return this.idcStartTime;
	}

	public void setIdcStartTime(Date idcStartTime) {
		this.idcStartTime = idcStartTime;
	}

	public Date getIdcEndTime() {
		return this.idcEndTime;
	}

	public void setIdcEndTime(Date idcEndTime) {
		this.idcEndTime = idcEndTime;
	}

	public Date getIdcCreateTime() {
		return this.idcCreateTime;
	}

	public void setIdcCreateTime(Date idcCreateTime) {
		this.idcCreateTime = idcCreateTime;
	}

	public Double getIdcBeforeDiff() {
		return this.idcBeforeDiff;
	}

	public void setIdcBeforeDiff(Double idcBeforeDiff) {
		this.idcBeforeDiff = idcBeforeDiff;
	}

	public Double getIdcAirAdjustAmout() {
		return this.idcAirAdjustAmout;
	}

	public void setIdcAirAdjustAmout(Double idcAirAdjustAmout) {
		this.idcAirAdjustAmout = idcAirAdjustAmout;
	}

	public Double getIdcDeviceAmout() {
		return this.idcDeviceAmout;
	}

	public void setIdcDeviceAmout(Double idcDeviceAmout) {
		this.idcDeviceAmout = idcDeviceAmout;
	}

	public Double getIdcAirAdjustBeforeDiff() {
		return this.idcAirAdjustBeforeDiff;
	}

	public void setIdcAirAdjustBeforeDiff(Double idcAirAdjustBeforeDiff) {
		this.idcAirAdjustBeforeDiff = idcAirAdjustBeforeDiff;
	}

	public Double getIdcDeviceBeforeDiff() {
		return this.idcDeviceBeforeDiff;
	}

	public void setIdcDeviceBeforeDiff(Double idcDeviceBeforeDiff) {
		this.idcDeviceBeforeDiff = idcDeviceBeforeDiff;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IdcPowerRoomInfo that = (IdcPowerRoomInfo) o;

		if (!idcPowerRoomId.equals(that.idcPowerRoomId)) return false;
		if (!idcPowerRoomCode.equals(that.idcPowerRoomCode)) return false;
		if (!idcPowerRoomName.equals(that.idcPowerRoomName)) return false;
		if (!idcPowerBuidingId.equals(that.idcPowerBuidingId)) return false;
		if (!idcAmout.equals(that.idcAmout)) return false;
		if (!idcStatus.equals(that.idcStatus)) return false;
		if (!idcStartTime.equals(that.idcStartTime)) return false;
		if (!idcEndTime.equals(that.idcEndTime)) return false;
		if (!idcCreateTime.equals(that.idcCreateTime)) return false;
		if (!idcBeforeDiff.equals(that.idcBeforeDiff)) return false;
		if (!idcAirAdjustAmout.equals(that.idcAirAdjustAmout)) return false;
		if (!idcDeviceAmout.equals(that.idcDeviceAmout)) return false;
		if (!idcAirAdjustBeforeDiff.equals(that.idcAirAdjustBeforeDiff)) return false;
		if (!idcDeviceBeforeDiff.equals(that.idcDeviceBeforeDiff)) return false;
		if (!idcOtherAmout.equals(that.idcOtherAmout)) return false;
		return idcOtherBeforeDiff.equals(that.idcOtherBeforeDiff);
	}

	@Override
	public int hashCode() {
		int result = idcPowerRoomId.hashCode();
		result = 31 * result + idcPowerRoomCode.hashCode();
		result = 31 * result + idcPowerRoomName.hashCode();
		result = 31 * result + idcPowerBuidingId.hashCode();
		result = 31 * result + idcAmout.hashCode();
		result = 31 * result + idcStatus.hashCode();
		result = 31 * result + idcStartTime.hashCode();
		result = 31 * result + idcEndTime.hashCode();
		result = 31 * result + idcCreateTime.hashCode();
		result = 31 * result + idcBeforeDiff.hashCode();
		result = 31 * result + idcAirAdjustAmout.hashCode();
		result = 31 * result + idcDeviceAmout.hashCode();
		result = 31 * result + idcAirAdjustBeforeDiff.hashCode();
		result = 31 * result + idcDeviceBeforeDiff.hashCode();
		result = 31 * result + idcOtherAmout.hashCode();
		result = 31 * result + idcOtherBeforeDiff.hashCode();
		return result;
	}
}