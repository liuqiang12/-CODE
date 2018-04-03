package com.idc.model;

/**
 * IdcPdfDayPowerInfo entity. @author MyEclipse Persistence Tools
 */

public class IdcOuterCode implements java.io.Serializable {
    private String id;
    private String outerCode;
    private String syscode;
    private int outerCodeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public int getOuterCodeType() {
        return outerCodeType;
    }

    public void setOuterCodeType(int outerCodeType) {
        this.outerCodeType = outerCodeType;
    }
}