package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 每个数据中心对应一个记录
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(name = "houseInfo", propOrder = {
        "houseId",
        "houseName",
        "houseType",
        "houseProvince",
        "houseCity",
        "houseCounty",
        "houseAdd",
        "houseZip",
        "idcOfficer",
        "gatewayInfos",
        "ipSegInfos",
        "frameInfos","gatewayId","ipSegId"})
public class HouseInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_HOUSE_INFO";
    private Long aid;
    private Long fkNewInfoId;
    private Long fkUpdatedataId;
    private Long fkIdcOfficerId;
    private String houseId;//当前数据中心ID，由IDC或ISP经营者产生，在本单位中唯一
    private String houseName;//数据中心的名称
    private String houseType;//数据中心性质1租用、2自建、999其它
    private Long houseProvince;//数据中心所在省或直辖市的行政区划数字代码
    private Long houseCity;//数据中心所在市或区（县）的行政区划数字代码
    private Long houseCounty;//数据中心所在县的行政区划数字代码
    private String houseAdd;//IDC/ISP机房的通信地址
    private String houseZip;//对应机房通信地址的邮编
    private IdcOfficer idcOfficer;//机房网络信息安全责任人信息
    private List<GatewayInfo> gatewayInfos;//链路信息
    private List<IpSegInfo> ipSegInfos;//IP地址段信息
    private List<FrameInfo> frameInfos;//机架信息
    private Long ticketInstId; //   工单实例ID
    private Long gatewayId;
    private String ipSegId;
    //获取相关的用户

    @XmlElement(name = "houseId" )
    @JsonProperty
    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
    @XmlElement(name = "houseName",nillable =false )
    @JsonProperty
    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
    @XmlElement(name = "houseType" )
    @JsonProperty
    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    @XmlElement(name = "houseProvince" )
    @JsonProperty
    public Long getHouseProvince() {
        return houseProvince;
    }

    public void setHouseProvince(Long houseProvince) {
        this.houseProvince = houseProvince;
    }
    @XmlElement(name = "houseCity" )
    @JsonProperty
    public Long getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(Long houseCity) {
        this.houseCity = houseCity;
    }
    @XmlElement(name = "houseCounty",nillable =false )
    @JsonProperty
    public Long getHouseCounty() {
        return houseCounty;
    }

    public void setHouseCounty(Long houseCounty) {
        this.houseCounty = houseCounty;
    }
    @XmlElement(name = "houseAdd" )
    @JsonProperty
    public String getHouseAdd() {
        return houseAdd;
    }

    public void setHouseAdd(String houseAdd) {
        this.houseAdd = houseAdd;
    }
    @XmlElement(name = "houseZip" )
    @JsonProperty
    public String getHouseZip() {
        return houseZip;
    }

    public void setHouseZip(String houseZip) {
        this.houseZip = houseZip;
    }
    @XmlElement(name = "houseOfficer", required = true,nillable =false )
    @JsonProperty
    public IdcOfficer getIdcOfficer() {
        return idcOfficer;
    }

    public void setIdcOfficer(IdcOfficer idcOfficer) {
        this.idcOfficer = idcOfficer;
    }
    @XmlElement(name = "gatewayInfo" )
    @JsonProperty
    public List<GatewayInfo> getGatewayInfos() {
        return gatewayInfos;
    }

    public void setGatewayInfos(List<GatewayInfo> gatewayInfos) {
        this.gatewayInfos = gatewayInfos;
    }
    @XmlElement(name = "ipSegInfo" )
    @JsonProperty
    public List<IpSegInfo> getIpSegInfos() {
        return ipSegInfos;
    }

    public void setIpSegInfos(List<IpSegInfo> ipSegInfos) {
        this.ipSegInfos = ipSegInfos;
    }
    @XmlElement(name = "frameInfo" )
    @JsonProperty
    public List<FrameInfo> getFrameInfos() {
        return frameInfos;
    }

    public void setFrameInfos(List<FrameInfo> frameInfos) {
        this.frameInfos = frameInfos;
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
    public Long getFkNewInfoId() {
        return fkNewInfoId;
    }

    public void setFkNewInfoId(Long fkNewInfoId) {
        this.fkNewInfoId = fkNewInfoId;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkIdcOfficerId() {
        return fkIdcOfficerId;
    }

    public void setFkIdcOfficerId(Long fkIdcOfficerId) {
        this.fkIdcOfficerId = fkIdcOfficerId;
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
    public String getTableName() {
        return tableName;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkUpdatedataId() {
        return fkUpdatedataId;
    }

    public void setFkUpdatedataId(Long fkUpdatedataId) {
        this.fkUpdatedataId = fkUpdatedataId;
    }
    @XmlElement(name = "gatewayId" )
    @JsonProperty
    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }
    @XmlElement(name = "ipSegId" )
    @JsonProperty
    public String getIpSegId() {
        return ipSegId;
    }

    public void setIpSegId(String ipSegId) {
        this.ipSegId = ipSegId;
    }
}
