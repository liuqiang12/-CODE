package com.commer.app.mode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * IdcPdfDayPowerInfo entity. @author MyEclipse Persistence Tools
 */

public class IdcPdfDayPowerInfo implements java.io.Serializable {

	// Fields

	private String idcPdfDayPowerInfoId;
	private String idcRoomCode;
	private String idcPdfCode;
	private String idcMcbCode;
	private Date idcStartTime;
	private Double idcAmout;
	private String idcStatus;
	private Date idcCreateTime;
	private Date idcEndTime;
	private String idcOnlyCode;
	private Double idcBeforeDiff;

	// Constructors

	/** default constructor */
	public IdcPdfDayPowerInfo() {
	}

	/** minimal constructor */
	public IdcPdfDayPowerInfo(String idcPdfDayPowerInfoId) {
		this.idcPdfDayPowerInfoId = idcPdfDayPowerInfoId;
	}

	/** full constructor */
	public IdcPdfDayPowerInfo(String idcPdfDayPowerInfoId, String idcRoomCode,
			String idcPdfCode, String idcMcbCode, Date idcStartTime,
			Double idcAmout, String idcStatus, Date idcCreateTime,
							  Date idcEndTime, String idcOnlyCode, Double idcBeforeDiff) {
		this.idcPdfDayPowerInfoId = idcPdfDayPowerInfoId;
		this.idcRoomCode = idcRoomCode;
		this.idcPdfCode = idcPdfCode;
		this.idcMcbCode = idcMcbCode;
		this.idcStartTime = idcStartTime;
		this.idcAmout = idcAmout;
		this.idcStatus = idcStatus;
		this.idcCreateTime = idcCreateTime;
		this.idcEndTime = idcEndTime;
		this.idcOnlyCode = idcOnlyCode;
		this.idcBeforeDiff = idcBeforeDiff;
	}

	// Property accessors

	public String getIdcPdfDayPowerInfoId() {
		return this.idcPdfDayPowerInfoId;
	}

	public void setIdcPdfDayPowerInfoId(String idcPdfDayPowerInfoId) {
		this.idcPdfDayPowerInfoId = idcPdfDayPowerInfoId;
	}

	public String getIdcRoomCode() {
		return this.idcRoomCode;
	}

	public void setIdcRoomCode(String idcRoomCode) {
		this.idcRoomCode = idcRoomCode;
	}

	public String getIdcPdfCode() {
		return this.idcPdfCode;
	}

	public void setIdcPdfCode(String idcPdfCode) {
		this.idcPdfCode = idcPdfCode;
	}

	public String getIdcMcbCode() {
		return this.idcMcbCode;
	}

	public void setIdcMcbCode(String idcMcbCode) {
		this.idcMcbCode = idcMcbCode;
	}

	public Date getIdcStartTime() {
		return this.idcStartTime;
	}

	public void setIdcStartTime(Timestamp idcStartTime) {
		this.idcStartTime = idcStartTime;
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

	public Date getIdcCreateTime() {
		return this.idcCreateTime;
	}

	public void setIdcCreateTime(Date idcCreateTime) {
		this.idcCreateTime = idcCreateTime;
	}

	public Date getIdcEndTime() {
		return this.idcEndTime;
	}

	public void setIdcEndTime(Timestamp idcEndTime) {
		this.idcEndTime = idcEndTime;
	}

	public String getIdcOnlyCode() {
		return this.idcOnlyCode;
	}

	public void setIdcOnlyCode(String idcOnlyCode) {
		this.idcOnlyCode = idcOnlyCode;
	}

	public Double getIdcBeforeDiff() {
		return this.idcBeforeDiff;
	}

	public void setIdcBeforeDiff(Double idcBeforeDiff) {
		this.idcBeforeDiff = idcBeforeDiff;
	}

}