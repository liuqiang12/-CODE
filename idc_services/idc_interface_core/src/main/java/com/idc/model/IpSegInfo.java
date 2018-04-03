package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * IP地址段信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "startIp",
        "endIp",
        "type",
        "user",
        "idNumber",
        "useTime",
        "sourceUnit",
        "allocationUnit"})
public class IpSegInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_IPSEG_INFO";
    private Long aid;
    private Long fkHouseInfoId;
    private Long id;//IP地址段资源的编号，由ISMS定义，当前机房中唯一
    private String startIp;//开始IP
    private String endIp;//结束IP
    private Integer type;//IP地址使用方式,包括：0-静态；1-动态；2-保留
    private String user;//使用人或者单位
    private String idNumber;//对应的使用人的证件号码
    private String idType;//

    private String useTime;//分配时间
    private String sourceUnit;//来源单位
    private String allocationUnit;//分配单位
    private Long ticketInstId; //   工单实例ID

    @XmlElement(name = "id" )
    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
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
    @XmlElement(name = "type" )
    @JsonProperty
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @XmlElement(name = "useTime" )
    @JsonProperty
    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    @XmlElement(name = "sourceUnit" )
    @JsonProperty
    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }
    @XmlElement(name = "allocationUnit" )
    @JsonProperty
    public String getAllocationUnit() {
        return allocationUnit;
    }

    public void setAllocationUnit(String allocationUnit) {
        this.allocationUnit = allocationUnit;
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
    public Long getFkHouseInfoId() {
        return fkHouseInfoId;
    }

    public void setFkHouseInfoId(Long fkHouseInfoId) {
        this.fkHouseInfoId = fkHouseInfoId;
    }
    @XmlTransient
    @JsonProperty
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }
    @XmlElement(name = "user" )
    @JsonProperty
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    @XmlElement(name = "idNumber" )
    @JsonProperty
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
