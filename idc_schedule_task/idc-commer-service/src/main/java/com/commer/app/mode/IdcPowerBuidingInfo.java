package com.commer.app.mode;

import java.sql.Timestamp;

/**
 * IdcPowerBuidingInfo entity. @author MyEclipse Persistence Tools
 */

public class IdcPowerBuidingInfo implements java.io.Serializable {

	// Fields

	private String idcPowerBuidingId;
	private String idcPowerBuidingCode;
	private String idcPowerBuidingName;
	private String idcPowerBuidingArea;
	private Double idcAmout;
	private Timestamp idcStartTime;
	private Timestamp idcEndTime;
	private Timestamp idcCreateTime;
	private Double idcBeforeDiff;
	private Double idcAirAdjustAmout;
	private Double idcDeviceAmout;
	private Double idcAirAdjustBeforeDiff;
	private Double idcDeviceBeforeDiff;
	private String idcStatus;

	// Constructors

	/** default constructor */
	public IdcPowerBuidingInfo() {
	}

	/** minimal constructor */
	public IdcPowerBuidingInfo(String idcPowerBuidingId) {
		this.idcPowerBuidingId = idcPowerBuidingId;
	}

	/** full constructor */
	public IdcPowerBuidingInfo(String idcPowerBuidingId,
			String idcPowerBuidingCode, String idcPowerBuidingName,
			String idcPowerBuidingArea, Double idcAmout,
			Timestamp idcStartTime, Timestamp idcEndTime,
			Timestamp idcCreateTime, Double idcBeforeDiff,
			Double idcAirAdjustAmout, Double idcDeviceAmout,
			Double idcAirAdjustBeforeDiff, Double idcDeviceBeforeDiff,
			String idcStatus) {
		this.idcPowerBuidingId = idcPowerBuidingId;
		this.idcPowerBuidingCode = idcPowerBuidingCode;
		this.idcPowerBuidingName = idcPowerBuidingName;
		this.idcPowerBuidingArea = idcPowerBuidingArea;
		this.idcAmout = idcAmout;
		this.idcStartTime = idcStartTime;
		this.idcEndTime = idcEndTime;
		this.idcCreateTime = idcCreateTime;
		this.idcBeforeDiff = idcBeforeDiff;
		this.idcAirAdjustAmout = idcAirAdjustAmout;
		this.idcDeviceAmout = idcDeviceAmout;
		this.idcAirAdjustBeforeDiff = idcAirAdjustBeforeDiff;
		this.idcDeviceBeforeDiff = idcDeviceBeforeDiff;
		this.idcStatus = idcStatus;
	}

	// Property accessors

	public String getIdcPowerBuidingId() {
		return this.idcPowerBuidingId;
	}

	public void setIdcPowerBuidingId(String idcPowerBuidingId) {
		this.idcPowerBuidingId = idcPowerBuidingId;
	}

	public String getIdcPowerBuidingCode() {
		return this.idcPowerBuidingCode;
	}

	public void setIdcPowerBuidingCode(String idcPowerBuidingCode) {
		this.idcPowerBuidingCode = idcPowerBuidingCode;
	}

	public String getIdcPowerBuidingName() {
		return this.idcPowerBuidingName;
	}

	public void setIdcPowerBuidingName(String idcPowerBuidingName) {
		this.idcPowerBuidingName = idcPowerBuidingName;
	}

	public String getIdcPowerBuidingArea() {
		return this.idcPowerBuidingArea;
	}

	public void setIdcPowerBuidingArea(String idcPowerBuidingArea) {
		this.idcPowerBuidingArea = idcPowerBuidingArea;
	}

	public Double getIdcAmout() {
		return this.idcAmout;
	}

	public void setIdcAmout(Double idcAmout) {
		this.idcAmout = idcAmout;
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

	public String getIdcStatus() {
		return this.idcStatus;
	}

	public void setIdcStatus(String idcStatus) {
		this.idcStatus = idcStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IdcPowerBuidingInfo that = (IdcPowerBuidingInfo) o;

		if (idcPowerBuidingId != null ? !idcPowerBuidingId.equals(that.idcPowerBuidingId) : that.idcPowerBuidingId != null) {
			return false;
		}
		if (idcPowerBuidingCode != null ? !idcPowerBuidingCode.equals(that.idcPowerBuidingCode) : that.idcPowerBuidingCode != null) {
			return false;
		}
		if (idcPowerBuidingName != null ? !idcPowerBuidingName.equals(that.idcPowerBuidingName) : that.idcPowerBuidingName != null) {
			return false;
		}
		if (idcPowerBuidingArea != null ? !idcPowerBuidingArea.equals(that.idcPowerBuidingArea) : that.idcPowerBuidingArea != null) {
			return false;
		}
		if (idcAmout != null ? !idcAmout.equals(that.idcAmout) : that.idcAmout != null) {
			return false;
		}
		if (idcStartTime != null ? !idcStartTime.equals(that.idcStartTime) : that.idcStartTime != null) {
			return false;
		}
		if (idcEndTime != null ? !idcEndTime.equals(that.idcEndTime) : that.idcEndTime != null) {
			return false;
		}
		if (idcCreateTime != null ? !idcCreateTime.equals(that.idcCreateTime) : that.idcCreateTime != null) {
			return false;
		}
		if (idcBeforeDiff != null ? !idcBeforeDiff.equals(that.idcBeforeDiff) : that.idcBeforeDiff != null) {
			return false;
		}
		if (idcAirAdjustAmout != null ? !idcAirAdjustAmout.equals(that.idcAirAdjustAmout) : that.idcAirAdjustAmout != null) {
			return false;
		}
		if (idcDeviceAmout != null ? !idcDeviceAmout.equals(that.idcDeviceAmout) : that.idcDeviceAmout != null) {
			return false;
		}
		if (idcAirAdjustBeforeDiff != null ? !idcAirAdjustBeforeDiff.equals(that.idcAirAdjustBeforeDiff) : that.idcAirAdjustBeforeDiff != null) {
			return false;
		}
		if (idcDeviceBeforeDiff != null ? !idcDeviceBeforeDiff.equals(that.idcDeviceBeforeDiff) : that.idcDeviceBeforeDiff != null) {
			return false;
		}
		return idcStatus != null ? idcStatus.equals(that.idcStatus) : that.idcStatus == null;
	}

	@Override
	public int hashCode() {
		int result = idcPowerBuidingId != null ? idcPowerBuidingId.hashCode() : 0;
		result = 31 * result + (idcPowerBuidingCode != null ? idcPowerBuidingCode.hashCode() : 0);
		result = 31 * result + (idcPowerBuidingName != null ? idcPowerBuidingName.hashCode() : 0);
		result = 31 * result + (idcPowerBuidingArea != null ? idcPowerBuidingArea.hashCode() : 0);
		result = 31 * result + (idcAmout != null ? idcAmout.hashCode() : 0);
		result = 31 * result + (idcStartTime != null ? idcStartTime.hashCode() : 0);
		result = 31 * result + (idcEndTime != null ? idcEndTime.hashCode() : 0);
		result = 31 * result + (idcCreateTime != null ? idcCreateTime.hashCode() : 0);
		result = 31 * result + (idcBeforeDiff != null ? idcBeforeDiff.hashCode() : 0);
		result = 31 * result + (idcAirAdjustAmout != null ? idcAirAdjustAmout.hashCode() : 0);
		result = 31 * result + (idcDeviceAmout != null ? idcDeviceAmout.hashCode() : 0);
		result = 31 * result + (idcAirAdjustBeforeDiff != null ? idcAirAdjustBeforeDiff.hashCode() : 0);
		result = 31 * result + (idcDeviceBeforeDiff != null ? idcDeviceBeforeDiff.hashCode() : 0);
		result = 31 * result + (idcStatus != null ? idcStatus.hashCode() : 0);
		return result;
	}
}