package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * IP地址转换信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class IpTran implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long aid;
    private Long fkHousesholdInfoId;
    private Long ticketInstId;
    private IpAttr internetIp;
    private IpAttr netIp;
    private String startIp;//该地址段的互联网起始IP地址
    private String endIp;//该地址段的互联网起始IP地址
    @XmlTransient
    @JsonProperty
    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }
    @XmlTransient
    @JsonProperty
    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    @XmlTransient
    @JsonProperty
    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkHousesholdInfoId() {
        return fkHousesholdInfoId;
    }

    public void setFkHousesholdInfoId(Long fkHousesholdInfoId) {
        this.fkHousesholdInfoId = fkHousesholdInfoId;
    }
    @XmlElement(name = "internetIp" )
    @JsonProperty
    public IpAttr getInternetIp() {
        return internetIp;
    }

    public void setInternetIp(IpAttr internetIp) {
        this.internetIp = internetIp;
    }
    @XmlElement(name = "netIp",nillable =false )
    @JsonProperty
    public IpAttr getNetIp() {
        return netIp;
    }

    public void setNetIp(IpAttr netIp) {
        this.netIp = netIp;
    }

    @XmlTransient
    @JsonProperty
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }
}
