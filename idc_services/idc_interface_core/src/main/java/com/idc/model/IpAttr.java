package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 互联网IP地址段
 * 对应私网 IP地址段
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "startIp",
        "endIp"
})
public class IpAttr implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_HOUSESHOLD_IPTRAN";
    private Long aid;
    private Long fkIptranId;
    private Integer ipCategory;
    private String startIp;//该地址段的互联网起始IP地址
    private String endIp;//该地址段的互联网起始IP地址
    private Long ticketInstId; //   工单实例ID
    private String distributeTime;//资源分配时间
    private Long ipId;

    @XmlElement(name = "startIp" )
    @JsonProperty
    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }
    @XmlElement(name = "endIp" )
    @JsonProperty
    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }
    @XmlTransient
    @JsonProperty
    public String getTableName() {
        return tableName;
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
    public Long getFkIptranId() {
        return fkIptranId;
    }

    public void setFkIptranId(Long fkIptranId) {
        this.fkIptranId = fkIptranId;
    }
    @XmlTransient
    @JsonProperty
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }
    @XmlTransient
    @JsonProperty
    public Integer getIpCategory() {
        return ipCategory;
    }

    public void setIpCategory(Integer ipCategory) {
        this.ipCategory = ipCategory;
    }

    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Long getIpId() {
        return ipId;
    }

    public void setIpId(Long ipId) {
        this.ipId = ipId;
    }
}
