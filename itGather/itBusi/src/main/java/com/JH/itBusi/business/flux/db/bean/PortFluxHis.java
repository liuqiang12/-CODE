package com.JH.itBusi.business.flux.db.bean;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2012-11-13
 */

public class PortFluxHis {

	private String execTime;
	private Long inputFlux;
	private Double inputFluxCount;

	private Long outputFlux;
	private Double outputFluxCount;

	private String upinputRecordTime;
	private String upoutputRecordTime;
	private Double upinputfluxCount;
	private Double upoutputfluxcount;

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String date) {
		this.execTime = date;
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

	public Long getInputFlux() {
		return inputFlux;
	}

	public void setInputFlux(Long inputFlux) {
		this.inputFlux = inputFlux;
	}

	public Long getOutputFlux() {
		return outputFlux;
	}

	public void setOutputFlux(Long outputFlux) {
		this.outputFlux = outputFlux;
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

}
