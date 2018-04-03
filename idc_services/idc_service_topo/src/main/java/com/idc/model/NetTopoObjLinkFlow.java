package com.idc.model;

import com.idc.utils.FlowUtil;

/**
 * Created by mylove on 2017/7/31.
 */
public class NetTopoObjLinkFlow extends NetTopoObjLink {
    private double inflow;
    private double outflow;
    private String devicename;
    private String sidedevicename;
    private int childLinks;
    private double outflowMbps;
    private double inflowMbps;
    public double getInflow() {
        return inflow;
    }
    public double getOutflowMbps() {
        return FlowUtil.bytesToMbps(outflow);
    }

    public double getInflowMbps() {
        return FlowUtil.bytesToMbps(inflow);
    }
    public void setInflow(double inflow) {
        this.inflow = inflow;
    }

    public double getOutflow() {
        return outflow;
    }

    public void setOutflow(double outflow) {
        this.outflow = outflow;
    }

    public int getChildLinks() {
        return childLinks;
    }

    public void setChildLinks(int childLinks) {
        this.childLinks = childLinks;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }
}
