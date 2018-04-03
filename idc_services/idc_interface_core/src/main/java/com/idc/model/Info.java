package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 提供互联网应用服务的用户信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "unitName",
        "unitNature",
        "idType",
        "idNumber",
        "idcOfficer",
        "add",
        "zipCode",
        "housesHoldInfos",
        "serviceInfos",
        "registerTime",
        "serviceRegTime"})
public class Info implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_USER_EXTEND";
    private Long aid;
    private Long fkUserInfoId;
    private Long fkOfficerId;
    private String unitName;//单位名称（个人填写个人姓名）
    private Integer unitNature;//单位属性1军队\2政府机关\3事业单位\4企业\5个人\6社会团体\999其他
    private Integer idType;//单位证件类型1	工商营业执照号码2	身份证3	组织机构代码证书4	事业法人证书5	军队代号6	社团法人证书7	护照8	军官证9	台胞证999	其他
    private String idNumber;//证件号
    private IdcOfficer idcOfficer;//网络信息安全责任人信息
    private String add;//单位地址
    private String zipCode;//邮政编码
    private List<ServiceInfo> serviceInfos;//服务信息:[明天就行将serviceInfo链接进来]
    private String registerTime;//用户的注册时间，采用yyyy-MM-dd格式
    private Long ticketInstId; //   工单实例ID
    private List<HousesHoldInfo> housesHoldInfos;
    private String serviceRegTime;//
    //private List<ServiceData> serviceData;

    @XmlElement(name = "housesHoldInfo" )
    @JsonProperty
    public List<HousesHoldInfo> getHousesHoldInfos() {
        return housesHoldInfos;
    }

    public void setHousesHoldInfos(List<HousesHoldInfo> housesHoldInfos) {
        this.housesHoldInfos = housesHoldInfos;
    }
    @XmlElement(name = "serviceRegTime" )
    @JsonProperty
    public String getServiceRegTime() {
        return serviceRegTime;
    }

    public void setServiceRegTime(String serviceRegTime) {
        this.serviceRegTime = serviceRegTime;
    }

    @XmlElement(name = "unitName" )
    @JsonProperty
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    @XmlElement(name = "unitNature" )
    @JsonProperty
    public Integer getUnitNature() {
        return unitNature;
    }

    public void setUnitNature(Integer unitNature) {
        this.unitNature = unitNature;
    }
    @JsonProperty
    @XmlElement(name = "idType" )
    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }
    @XmlElement(name = "idNumber" )
    @JsonProperty
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    @XmlElement(name = "officer" )
    @JsonProperty
    public IdcOfficer getIdcOfficer() {
        return idcOfficer;
    }

    public void setIdcOfficer(IdcOfficer idcOfficer) {
        this.idcOfficer = idcOfficer;
    }
    @XmlElement(name = "add" )
    @JsonProperty
    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
    @XmlElement(name = "zipCode" )
    @JsonProperty
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    @XmlElement(name = "serviceInfo" )
    @JsonProperty
    public List<ServiceInfo> getServiceInfos() {
        return serviceInfos;
    }

    public void setServiceInfos(List<ServiceInfo> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }
    @XmlElement(name = "registerTime" )
    @JsonProperty
    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
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
    public Long getFkUserInfoId() {
        return fkUserInfoId;
    }

    public void setFkUserInfoId(Long fkUserInfoId) {
        this.fkUserInfoId = fkUserInfoId;
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
    public Long getFkOfficerId() {
        return fkOfficerId;
    }

    public void setFkOfficerId(Long fkOfficerId) {
        this.fkOfficerId = fkOfficerId;
    }


    /*@XmlElement(name = "serviceData" )
    @JsonProperty
    public List<ServiceData> getServiceData() {
        return serviceData;
    }

    public void setServiceData(List<ServiceData> serviceData) {
        this.serviceData = serviceData;
    }*/
}
