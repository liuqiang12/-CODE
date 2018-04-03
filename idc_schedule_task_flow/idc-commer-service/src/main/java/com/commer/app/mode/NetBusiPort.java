package com.commer.app.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/11/21.
 */
public class NetBusiPort implements Serializable {

    private Long id;
    private String busiportname;
    private List<Long> portids = new ArrayList<Long>();
    private List<Long> groups = new ArrayList<Long>();
    private Long deviceid;
    private String desc;
    private String customer;
    private Double bandwidth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusiportname() {
        return busiportname;
    }

    public void setBusiportname(String busiportname) {
        this.busiportname = busiportname;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Double bandwidth) {
        this.bandwidth = bandwidth;
    }

    public List<Long> getPortids() {
        return portids;
    }
    public void addPortid(Long portid) {
        this.portids.add(portid);
    }
    public void setPortids(List<Long> portids) {
        this.portids = portids;
    }

    public List<Long> getGroups() {
        return groups;
    }

    public void setGroups(List<Long> groups) {
        this.groups = groups;
    }
}
