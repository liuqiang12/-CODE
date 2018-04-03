package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 链路信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "bandWidth",
        "linkType",
        "accessUnit",
        "gatewayIp"})
public class GatewayInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String tableName = "IDC_ISP_GATEWAY_INFO";
    private Long aid;
    private Long fkHouseInfoId;
    private Long id;//机房互联网出入口（含专线）ID，由ISMS定义，当前机房中唯一
    private Long bandWidth;//机房互联网出入口带宽(单位：Mbit/s)
    private Integer linkType;//机房互联网出入口类型：1，电信；2，联通；3，移动；4，铁通；9，其他
    private String accessUnit;//接入单位指为ISMS覆盖的业务链路的上联单位（为IDC/ISP经营者提供接入的接入单位）
    private String gatewayIp;//机房互联网出入口网关IP地址
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
    @XmlElement(name = "bandWidth" )
    public Long getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Long bandWidth) {
        this.bandWidth = bandWidth;
    }
    @JsonProperty
    @XmlElement(name = "linkType" )
    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }
    @JsonProperty
    @XmlElement(name = "accessUnit" )
    public String getAccessUnit() {
        return accessUnit;
    }

    public void setAccessUnit(String accessUnit) {
        this.accessUnit = accessUnit;
    }
    @JsonProperty
    @XmlElement(name = "gatewayIp" )
    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
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
