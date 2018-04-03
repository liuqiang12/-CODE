package com.idc.model;

import com.idc.utils.FlowUtil;

import java.math.BigDecimal;

/**
 * Created by mylove on 2017/7/11.
 */
public class NetPortFlowCap extends NetPort {
    private double maxinflow;
    private double maxoutflow;
    private double mininflow;
    private double minoutflow;
    private double avgoutflow;
    private double avginflow;
    private double avginflowUR;
    private double avgoutflowUR;
    private double maxinflowUR;
    private double maxoutflowUR;


    private double mininflowUR;
    private double minoutflowUR;
    private String recordtime;

    private double maxinflowMbps;
    private double maxoutflowMbps;
    private double mininflowMbps;
    private double minoutflowMbps;
    private double avgoutflowMbps;
    private double avginflowMbps;

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public double getMaxinflow() {
        return maxinflow;
    }

    public void setMaxinflow(double maxinflow) {
        this.maxinflow = maxinflow;
    }

    public double getMaxoutflow() {
        return maxoutflow;
    }

    public void setMaxoutflow(double maxoutflow) {
        this.maxoutflow = maxoutflow;
    }

    public double getMininflow() {
        return mininflow;
    }

    public void setMininflow(double mininflow) {
        this.mininflow = mininflow;
    }

    public double getMinoutflow() {
        return minoutflow;
    }

    public void setMinoutflow(double minoutflow) {
        this.minoutflow = minoutflow;
    }

    public double getAvgoutflow() {
        return avgoutflow;
    }

    public void setAvgoutflow(double avgoutflow) {
        this.avgoutflow = avgoutflow;
    }

    public double getAvginflow() {
        return avginflow;
    }

    public void setAvginflow(double avginflow) {
        this.avginflow = avginflow;
    }

    public double getAvginflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getAvginflowMbps(), this.getBandwidth().doubleValue());
    }

    public void setAvginflowUR(double avginflowUR) {
        this.avginflowUR = avginflowUR;
    }

    public double getAvgoutflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getAvgoutflowMbps(), this.getBandwidth().doubleValue());
    }

    public void setAvgoutflowUR(double avgoutflowUR) {
        this.avgoutflowUR = avgoutflowUR;
    }

    public double getMaxinflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getMaxinflowMbps(), this.getBandwidth().doubleValue());
    }

    public void setMaxinflowUR(double maxinflowUR) {
        this.maxinflowUR = maxinflowUR;
    }

    public double getMaxoutflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getMaxoutflowMbps(), this.getBandwidth().doubleValue());
    }

    public double getMininflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getMininflowMbps(), this.getBandwidth().doubleValue());
    }

    public double getMinoutflowUR() {
        return getBandwidth() == null ? 0 : FlowUtil.bandWidthUsage(this.getMinoutflowMbps(), this.getBandwidth().doubleValue());
    }

    public void setMaxoutflowUR(double maxoutflowUR) {
        this.maxoutflowUR = maxoutflowUR;
    }


    public double getMaxinflowMbps() {
        return FlowUtil.bytesToMbps(this.maxinflow);
    }

    public double getMaxoutflowMbps() {
        return FlowUtil.bytesToMbps(this.maxoutflow);
    }

    public double getMininflowMbps() {
        return FlowUtil.bytesToMbps(this.mininflow);
    }

    public double getMinoutflowMbps() {
        return FlowUtil.bytesToMbps(this.minoutflow);
    }

    public double getAvgoutflowMbps() {
        return FlowUtil.bytesToMbps(this.avgoutflow);
    }

    public double getAvginflowMbps() {
        return FlowUtil.bytesToMbps(this.avginflow);
    }
}
