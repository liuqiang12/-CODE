package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 机架信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "useType",
        "distribution",
        "occupancy",
        "frameName"})
public class FrameInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_FRAME_INFO";
    private Long aid;
    private Long fkHouseInfoId;
    private Long id;//机架信息的编码，由ISMS 定义
    private String useType;//使用类型:1-自用2-出租
    private String distribution;//分配状态:1-未分配2-已分配
    private String occupancy;//占用状态1-未占用2-已占用
    private String frameName;//机架/机位名称
    private Long ticketInstId; //   工单实例ID

    @JsonProperty
    @XmlElement(name = "id" )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty
    @XmlElement(name = "useType" )
    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }
    @JsonProperty
    @XmlElement(name = "distribution" )
    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
    @JsonProperty
    @XmlElement(name = "occupancy" )
    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }
    @JsonProperty
    @XmlElement(name = "frameName" )
    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }
    @JsonProperty
    @XmlTransient
    public String getTableName() {
        return tableName;
    }
    @JsonProperty
    @XmlTransient
    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }
    @JsonProperty
    @XmlTransient
    public Long getFkHouseInfoId() {
        return fkHouseInfoId;
    }

    public void setFkHouseInfoId(Long fkHouseInfoId) {
        this.fkHouseInfoId = fkHouseInfoId;
    }
    @JsonProperty
    @XmlTransient
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }
}
