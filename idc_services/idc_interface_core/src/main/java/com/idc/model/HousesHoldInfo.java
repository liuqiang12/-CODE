package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "hhId",
        "houseId",
        "frameInfoId",
        "bandWidth",
        "ipTrans",
        "ipSegs",
        "distributeTime"
})
public class HousesHoldInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_HOUSESHOLD_INFO";
    private Long aid;
    private Long fkServiceInfoId;
    private Long hhId;//占用机房信息的ID，由IDC/ISP经营者产生，在本单位中唯一
    private Long houseId;//占用机房ID，由IDC/ISP经营者产生，在本单位中唯一
    private String distributeTime;//资源分配时间的时间，采用yyyy-MM-dd的格式
    private Long bandWidth;//网络带宽（单位：Mbps)
    private String frameInfoId;//机房机架ID:初始化的时候，任意存放信息
    private List<IpAttrForHouse> ipSegs;//IP序列
    private List<IpTran> ipTrans;//IP地址转换信息
    private Long ticketInstId; //   工单实例ID
    @XmlElement(name = "ipSeg" )
    @JsonProperty
    public List<IpAttrForHouse> getIpSegs() {
        return ipSegs;
    }

    public void setIpSegs(List<IpAttrForHouse> ipSegs) {
        this.ipSegs = ipSegs;
    }
    @XmlElement(name = "hhId" )
    @JsonProperty
    public Long getHhId() {
        return hhId;
    }

    public void setHhId(Long hhId) {
        this.hhId = hhId;
    }
    @XmlElement(name = "houseId" )
    @JsonProperty
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
    @XmlElement(name = "distributeTime" )
    @JsonProperty
    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
    }
    @XmlElement(name = "bandWidth" )
    @JsonProperty
    public Long getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Long bandWidth) {
        this.bandWidth = bandWidth;
    }
    @XmlElement(name = "frameInfoId" )
    @JsonProperty
    public String getFrameInfoId() {
        return frameInfoId;
    }

    public void setFrameInfoId(String frameInfoId) {
        this.frameInfoId = frameInfoId;
    }
    @XmlElement(name = "ipTrans" )
    @JsonProperty
    public List<IpTran> getIpTrans() {
        return ipTrans;
    }

    public void setIpTrans(List<IpTran> ipTrans) {
        this.ipTrans = ipTrans;
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
    public Long getFkServiceInfoId() {
        return fkServiceInfoId;
    }

    public void setFkServiceInfoId(Long fkServiceInfoId) {
        this.fkServiceInfoId = fkServiceInfoId;
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
