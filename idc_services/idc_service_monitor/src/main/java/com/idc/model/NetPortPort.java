package com.idc.model;

import java.io.Serializable;

/**
 * Created by mylove on 2017/7/17.
 */
public class NetPortPort implements Serializable {
    private long id;
    private long portid;
    private String busiPortName;
    private String descr;

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

    public String getBusiPortName() {
        return busiPortName;
    }

    public void setBusiPortName(String busiPortName) {
        this.busiPortName = busiPortName;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
