package com.idc.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mylove on 2017/9/19.
 */
public class PowerEnv implements Serializable {
    private long IDC_PDF_DAY_POWER_INFO_ID;
    private String IDC_ROOM_CODE;
    private String IDC_ONLY_CODE;
    private String IDC_MCB_CODE;
    private long IDC_AMOUT;
    private long IDC_STATUS;
    private Timestamp IDC_CREATE_TIME;
    private Timestamp IDC_START_TIME;
    private Timestamp IDC_END_TIME;

    public long getIDC_PDF_DAY_POWER_INFO_ID() {
        return IDC_PDF_DAY_POWER_INFO_ID;
    }

    public void setIDC_PDF_DAY_POWER_INFO_ID(long IDC_PDF_DAY_POWER_INFO_ID) {
        this.IDC_PDF_DAY_POWER_INFO_ID = IDC_PDF_DAY_POWER_INFO_ID;
    }

    public String getIDC_ROOM_CODE() {
        return IDC_ROOM_CODE;
    }

    public void setIDC_ROOM_CODE(String IDC_ROOM_CODE) {
        this.IDC_ROOM_CODE = IDC_ROOM_CODE;
    }

    public String getIDC_ONLY_CODE() {
        return IDC_ONLY_CODE;
    }

    public void setIDC_ONLY_CODE(String IDC_ONLY_CODE) {
        this.IDC_ONLY_CODE = IDC_ONLY_CODE;
    }

    public String getIDC_MCB_CODE() {
        return IDC_MCB_CODE;
    }

    public void setIDC_MCB_CODE(String IDC_MCB_CODE) {
        this.IDC_MCB_CODE = IDC_MCB_CODE;
    }

    public long getIDC_AMOUT() {
        return IDC_AMOUT;
    }

    public void setIDC_AMOUT(long IDC_AMOUT) {
        this.IDC_AMOUT = IDC_AMOUT;
    }

    public long getIDC_STATUS() {
        return IDC_STATUS;
    }

    public void setIDC_STATUS(long IDC_STATUS) {
        this.IDC_STATUS = IDC_STATUS;
    }

    public Timestamp getIDC_CREATE_TIME() {
        return IDC_CREATE_TIME;
    }

    public void setIDC_CREATE_TIME(Timestamp IDC_CREATE_TIME) {
        this.IDC_CREATE_TIME = IDC_CREATE_TIME;
    }

    public Timestamp getIDC_START_TIME() {
        return IDC_START_TIME;
    }

    public void setIDC_START_TIME(Timestamp IDC_START_TIME) {
        this.IDC_START_TIME = IDC_START_TIME;
    }

    public Timestamp getIDC_END_TIME() {
        return IDC_END_TIME;
    }

    public void setIDC_END_TIME(Timestamp IDC_END_TIME) {
        this.IDC_END_TIME = IDC_END_TIME;
    }
}
