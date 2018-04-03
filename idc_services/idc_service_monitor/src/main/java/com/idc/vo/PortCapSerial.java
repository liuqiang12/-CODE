package com.idc.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by mylove on 2018/1/23.
 */
public class PortCapSerial implements Serializable {
    private String id;
    private Long Portid;
    private double inputFlux;
    private double outputFlux;
    private Timestamp recordTime;
    private Timestamp addTime;
    private String portname;
    private Long routid;
    private Long adminStatus;
    private BigDecimal bandwidth; //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPortid() {
        return Portid;
    }

    public void setPortid(Long portid) {
        Portid = portid;
    }

    public Double getInputFlux() {
        return inputFlux;
    }

    public void setInputFlux(Double inputFlux) {
        this.inputFlux = inputFlux;
    }

    public Double getOutputFlux() {
        return outputFlux;
    }

    public void setOutputFlux(Double outputFlux) {
        this.outputFlux = outputFlux;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public Long getRoutid() {
        return routid;
    }

    public void setRoutid(Long routid) {
        this.routid = routid;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(BigDecimal bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Long getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Long adminStatus) {
        this.adminStatus = adminStatus;
    }
}
