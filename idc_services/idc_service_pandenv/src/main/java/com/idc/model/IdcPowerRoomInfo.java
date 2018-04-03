package com.idc.model;

import java.sql.Timestamp;

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
	private Timestamp idcStartTime;
	private Timestamp idcEndTime;
	private Timestamp idcCreateTime;
	private Double idcBeforeDiff;
	private Double idcAirAdjustAmout;
	private Double idcDeviceAmout;
	private Double idcAirAdjustBeforeDiff;
	private Double idcDeviceBeforeDiff;
    private String sysCode;
    private String outerCode;

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
							String idcStatus, Timestamp idcStartTime, Timestamp idcEndTime,
							Timestamp idcCreateTime, Double idcBeforeDiff,
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

	public Timestamp getIdcStartTime() {
		return this.idcStartTime;
	}

	public void setIdcStartTime(Timestamp idcStartTime) {
		this.idcStartTime = idcStartTime;
	}

	public Timestamp getIdcEndTime() {
		return this.idcEndTime;
	}

	public void setIdcEndTime(Timestamp idcEndTime) {
		this.idcEndTime = idcEndTime;
	}

	public Timestamp getIdcCreateTime() {
		return this.idcCreateTime;
	}

	public void setIdcCreateTime(Timestamp idcCreateTime) {
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
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof IdcPowerRoomInfo)) {
			return false;
		}
		IdcPowerRoomInfo castOther = (IdcPowerRoomInfo) other;

		return ((this.getIdcPowerRoomId() == castOther.getIdcPowerRoomId()) || (this
				.getIdcPowerRoomId() != null
				&& castOther.getIdcPowerRoomId() != null && this
				.getIdcPowerRoomId().equals(castOther.getIdcPowerRoomId())))
				&& ((this.getIdcPowerRoomCode() == castOther
						.getIdcPowerRoomCode()) || (this.getIdcPowerRoomCode() != null
						&& castOther.getIdcPowerRoomCode() != null && this
						.getIdcPowerRoomCode().equals(
								castOther.getIdcPowerRoomCode())))
				&& ((this.getIdcPowerRoomName() == castOther
						.getIdcPowerRoomName()) || (this.getIdcPowerRoomName() != null
						&& castOther.getIdcPowerRoomName() != null && this
						.getIdcPowerRoomName().equals(
								castOther.getIdcPowerRoomName())))
				&& ((this.getIdcPowerBuidingId() == castOther
						.getIdcPowerBuidingId()) || (this
						.getIdcPowerBuidingId() != null
						&& castOther.getIdcPowerBuidingId() != null && this
						.getIdcPowerBuidingId().equals(
								castOther.getIdcPowerBuidingId())))
				&& ((this.getIdcAmout() == castOther.getIdcAmout()) || (this
						.getIdcAmout() != null
						&& castOther.getIdcAmout() != null && this
						.getIdcAmout().equals(castOther.getIdcAmout())))
				&& ((this.getIdcStatus() == castOther.getIdcStatus()) || (this
						.getIdcStatus() != null
						&& castOther.getIdcStatus() != null && this
						.getIdcStatus().equals(castOther.getIdcStatus())))
				&& ((this.getIdcStartTime() == castOther.getIdcStartTime()) || (this
						.getIdcStartTime() != null
						&& castOther.getIdcStartTime() != null && this
						.getIdcStartTime().equals(castOther.getIdcStartTime())))
				&& ((this.getIdcEndTime() == castOther.getIdcEndTime()) || (this
						.getIdcEndTime() != null
						&& castOther.getIdcEndTime() != null && this
						.getIdcEndTime().equals(castOther.getIdcEndTime())))
				&& ((this.getIdcCreateTime() == castOther.getIdcCreateTime()) || (this
						.getIdcCreateTime() != null
						&& castOther.getIdcCreateTime() != null && this
						.getIdcCreateTime()
						.equals(castOther.getIdcCreateTime())))
				&& ((this.getIdcBeforeDiff() == castOther.getIdcBeforeDiff()) || (this
						.getIdcBeforeDiff() != null
						&& castOther.getIdcBeforeDiff() != null && this
						.getIdcBeforeDiff()
						.equals(castOther.getIdcBeforeDiff())))
				&& ((this.getIdcAirAdjustAmout() == castOther
						.getIdcAirAdjustAmout()) || (this
						.getIdcAirAdjustAmout() != null
						&& castOther.getIdcAirAdjustAmout() != null && this
						.getIdcAirAdjustAmout().equals(
								castOther.getIdcAirAdjustAmout())))
				&& ((this.getIdcDeviceAmout() == castOther.getIdcDeviceAmout()) || (this
						.getIdcDeviceAmout() != null
						&& castOther.getIdcDeviceAmout() != null && this
						.getIdcDeviceAmout().equals(
								castOther.getIdcDeviceAmout())))
				&& ((this.getIdcAirAdjustBeforeDiff() == castOther
						.getIdcAirAdjustBeforeDiff()) || (this
						.getIdcAirAdjustBeforeDiff() != null
						&& castOther.getIdcAirAdjustBeforeDiff() != null && this
						.getIdcAirAdjustBeforeDiff().equals(
								castOther.getIdcAirAdjustBeforeDiff())))
				&& ((this.getIdcDeviceBeforeDiff() == castOther
						.getIdcDeviceBeforeDiff()) || (this
						.getIdcDeviceBeforeDiff() != null
						&& castOther.getIdcDeviceBeforeDiff() != null && this
						.getIdcDeviceBeforeDiff().equals(
								castOther.getIdcDeviceBeforeDiff())));
	}

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    @Override
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdcPowerRoomId() == null ? 0 : this.getIdcPowerRoomId()
						.hashCode());
		result = 37
				* result
				+ (getIdcPowerRoomCode() == null ? 0 : this
						.getIdcPowerRoomCode().hashCode());
		result = 37
				* result
				+ (getIdcPowerRoomName() == null ? 0 : this
						.getIdcPowerRoomName().hashCode());
		result = 37
				* result
				+ (getIdcPowerBuidingId() == null ? 0 : this
						.getIdcPowerBuidingId().hashCode());
		result = 37 * result
				+ (getIdcAmout() == null ? 0 : this.getIdcAmout().hashCode());
		result = 37 * result
				+ (getIdcStatus() == null ? 0 : this.getIdcStatus().hashCode());
		result = 37
				* result
				+ (getIdcStartTime() == null ? 0 : this.getIdcStartTime()
						.hashCode());
		result = 37
				* result
				+ (getIdcEndTime() == null ? 0 : this.getIdcEndTime()
						.hashCode());
		result = 37
				* result
				+ (getIdcCreateTime() == null ? 0 : this.getIdcCreateTime()
						.hashCode());
		result = 37
				* result
				+ (getIdcBeforeDiff() == null ? 0 : this.getIdcBeforeDiff()
						.hashCode());
		result = 37
				* result
				+ (getIdcAirAdjustAmout() == null ? 0 : this
						.getIdcAirAdjustAmout().hashCode());
		result = 37
				* result
				+ (getIdcDeviceAmout() == null ? 0 : this.getIdcDeviceAmout()
						.hashCode());
		result = 37
				* result
				+ (getIdcAirAdjustBeforeDiff() == null ? 0 : this
						.getIdcAirAdjustBeforeDiff().hashCode());
		result = 37
				* result
				+ (getIdcDeviceBeforeDiff() == null ? 0 : this
						.getIdcDeviceBeforeDiff().hashCode());
		return result;
	}

}