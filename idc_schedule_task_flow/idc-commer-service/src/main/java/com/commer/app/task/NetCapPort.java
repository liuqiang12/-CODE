package com.commer.app.task;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mylove on 2017/12/5.
 */
public class NetCapPort implements Serializable {
    private long id;
    private long portid;
    private double outflow;
    private double inflow;
    private Timestamp RecordTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
