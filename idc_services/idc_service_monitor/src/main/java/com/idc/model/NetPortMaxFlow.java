package com.idc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * Created by mylove on 2017/7/11.
 */
public class NetPortMaxFlow extends NetPort {
    private double maxFlow;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp RecordTime;

    public double getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(double maxFlow) {
        this.maxFlow = maxFlow;
    }

    public Timestamp getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        RecordTime = recordTime;
    }
}
