package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 服务信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "serviceId",
        "serviceContents",
        "regType",
        "regId",
        "setMode",
        "business",
        "domain",
        "domainId",
        "hhId",
        "housesHoldInfos"
})
public class ServiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_SERVICE_INFO";
    private Long aid;
    private Long fkUserExtendId;
    private Long serviceId;//服务编号，该用户内唯一
    private List<Integer> serviceContents;//填写服务的内容，见8.6节:此时属于多个
    private String serviceContent;//填写服务的内容，见8.6节:此时属于多个

    private Integer regType;//网站登记备案类型，见8.9节
    private String regId;//网站备案号或许可证号
    private String setMode;//接入方式，见8.1节
    private String business;//1-IDC业务/2-ISP业务
    private Domain domain;//域名信息
    private List<HousesHoldInfo> housesHoldInfos;
    private Long ticketInstId; //   工单实例ID
    private Long customerId;//客户信息
    private Long customerIdTmp;//客户信息
    private Long domainId;
    private String bandWidth;
    private String domainName;
    private Long hhId;
    private String nature;//是否是域名
    private  UserInfo userInfo;//对应的用户信息
    private List<IpAttr> ipAttrList;//服务对应的ip信息
    private String randomSingleRackId ;//随机的机架ID
    private String distributeTime;//资源分配时间
    private String serviceRegTime;//服务开通时间
    //虚拟机信息
    private String virtualhostName;
    private Integer virtualhostState;
    private Integer virtualhostType;
    private String virtualhostAddress;
    private String virtualhostManagementAddress;
    @XmlElement(name = "serviceId" )
    @JsonProperty
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    @XmlElement(name = "serviceContent" )
    @JsonProperty
    public List<Integer> getServiceContents() {
        return serviceContents;
    }

    public void setServiceContents(List<Integer> serviceContents) {
        this.serviceContents = serviceContents;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }
    @XmlTransient
    @JsonProperty
    public String getServiceContent() {
        return serviceContent;
    }

    @XmlElement(name = "regType",nillable = false )
    @JsonProperty
    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }
    @XmlElement(name = "regId",nillable = false )
    @JsonProperty
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
    @XmlElement(name = "setMode" )
    @JsonProperty
    public String getSetMode() {
        return setMode;
    }

    public void setSetMode(String setMode) {
        this.setMode = setMode;
    }
    @XmlElement(name = "business" )
    @JsonProperty
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
    @XmlElement(name = "domain" )
    public Domain getDomain() {
        return domain;
    }
    @XmlElement(name = "housesHoldInfo" )
    @JsonProperty
    public List<HousesHoldInfo> getHousesHoldInfos() {
        return housesHoldInfos;
    }

    public void setHousesHoldInfos(List<HousesHoldInfo> housesHoldInfos) {
        this.housesHoldInfos = housesHoldInfos;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /*public HousesHoldInfo getHousesHoldInfo() {
        return housesHoldInfo;
    }

    public void setHousesHoldInfo(HousesHoldInfo housesHoldInfo) {
        this.housesHoldInfo = housesHoldInfo;
    }*/
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
    public Long getFkUserExtendId() {
        return fkUserExtendId;
    }

    public void setFkUserExtendId(Long fkUserExtendId) {
        this.fkUserExtendId = fkUserExtendId;
    }
    @XmlTransient
    @JsonProperty
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }
    @XmlElement(name = "domainId" )
    @JsonProperty
    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }
    @XmlElement(name = "hhId" )
    @JsonProperty
    public Long getHhId() {
        return hhId;
    }

    public void setHhId(Long hhId) {
        this.hhId = hhId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Long getCustomerIdTmp() {
        return customerIdTmp;
    }

    public void setCustomerIdTmp(Long customerIdTmp) {
        this.customerIdTmp = customerIdTmp;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<IpAttr> getIpAttrList() {
        return ipAttrList;
    }

    public void setIpAttrList(List<IpAttr> ipAttrList) {
        this.ipAttrList = ipAttrList;
    }

    public String getRandomSingleRackId() {
        return randomSingleRackId;
    }

    public void setRandomSingleRackId(String randomSingleRackId) {
        this.randomSingleRackId = randomSingleRackId;
    }

    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String getServiceRegTime() {
        return serviceRegTime;
    }

    public void setServiceRegTime(String serviceRegTime) {
        this.serviceRegTime = serviceRegTime;
    }

    public String getVirtualhostName() {
        return virtualhostName;
    }

    public void setVirtualhostName(String virtualhostName) {
        this.virtualhostName = virtualhostName;
    }

    public Integer getVirtualhostState() {
        return virtualhostState;
    }

    public void setVirtualhostState(Integer virtualhostState) {
        this.virtualhostState = virtualhostState;
    }

    public Integer getVirtualhostType() {
        return virtualhostType;
    }

    public void setVirtualhostType(Integer virtualhostType) {
        this.virtualhostType = virtualhostType;
    }

    public String getVirtualhostAddress() {
        return virtualhostAddress;
    }

    public void setVirtualhostAddress(String virtualhostAddress) {
        this.virtualhostAddress = virtualhostAddress;
    }

    public String getVirtualhostManagementAddress() {
        return virtualhostManagementAddress;
    }

    public void setVirtualhostManagementAddress(String virtualhostManagementAddress) {
        this.virtualhostManagementAddress = virtualhostManagementAddress;
    }
}
