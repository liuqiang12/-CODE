package com.idc.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mylove on 2017/12/5.
 */
public class NetCapPort implements Serializable {
    private String id;
    private long portid;
    private double outflow;
    private double inflow;
    private boolean isUserInsert;
    private Timestamp RecordTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPortid() {
        return portid;
    }

    public void setPortid(long portid) {
        this.portid = portid;
    }

    public double getOutflow() {
        return outflow;
    }

    public void setOutflow(double outflow) {
        this.outflow = outflow;
    }

    public double getInflow() {
        return inflow;
    }

    public void setInflow(double inflow) {
        this.inflow = inflow;
    }

    public Timestamp getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        RecordTime = recordTime;
    }

    public boolean isUserInsert() {
        return isUserInsert;
    }

    public void setIsUserInsert(boolean isUserInsert) {
        this.isUserInsert = isUserInsert;
    }
}
