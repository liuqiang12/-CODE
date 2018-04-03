package com.idc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idc.utils.FlowUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mylove on 2017/7/11.
 */
public class NetPortFlow extends NetPort {
    private double outflow;
    private double inflow;
    private double outflowMbps;
    private double inflowMbps;
    private boolean isUserInsert;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp RecordTime;
    private String deviceName;
    private String recordTimeStr;

    public double getOutflow() {
        return outflow;
    }

    public void setOutflow(double outflow) {
        this.outflow = outflow;
    }

    public double getInflow() {
        return inflow;
    }

    public double getOutflowMbps() {
        return FlowUtil.bytesToMbps(this.outflow);
//        return outflow * 8 / 1000 / 1000;
    }

    public double getInflowMbps() {
        return FlowUtil.bytesToMbps(this.inflow);
//        return inflow * 8 / 1000 / 1000;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getRecordTimeStr() {
        if (this.recordTimeStr == null) {
            setRecordTimeStr(this.getRecordTime());
        }
        return this.recordTimeStr;
    }

    public void setRecordTimeStr(Date alarmtime) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            if (alarmtime != null) {
                date_ = DateFormatUtils.format(alarmtime, "yyyy-MM-dd HH:mm:ss");
                //date_ = sdf.format(alarmtime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.recordTimeStr = date_;
    }

    public boolean isUserInsert() {
        return isUserInsert;
    }

    public void setIsUserInsert(boolean isUserInsert) {
        this.isUserInsert = isUserInsert;
    }

    @Override
    public String toString() {
        return super.toString() + "[RecordTimeStr:" + this.getRecordTimeStr() + ",outflow:" + this.outflow + ",inflow:" + this.inflow + "]";
    }
}
