package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 新增用户数据的占用机房信息，每个用户对应一个记录
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "nature",
        "info","userId","serviceInfos","serviceData","hhId"})
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_USER_INFO";
    private String aid;
    private Long fkNewInfoId;
    private Long fkUpdatedataId;
    private Long id;//用户唯一ID，由IDC/ISP经营者产生，在本单位中唯一


    private Integer nature;//1、	提供互联网应用服务的用户 2、其他用户
    private Info info;//提供互联网应用服务的用户信息
    private String unitName;//单位名称（个人填写个人姓名）
    private Integer unitNature;//单位属性1军队\2政府机关\3事业单位\4企业\5个人\6社会团体\999其他
    private Integer idType;//单位证件类型1	工商营业执照号码2	身份证3	组织机构代码证书4	事业法人证书5	军队代号6	社团法人证书7	护照8	军官证9	台胞证999	其他
    private String idNumber;//证件号
    private String add;//单位地址
    private String zipCode;//邮政编码

    private Long userId;//用户唯一ID，由IDC/ISP经营者产生，在本单位中唯一:删除的时候使用
    private List<ServiceInfo> serviceInfos;//服务信息
    private List<ServiceData> serviceData;//删除的时候使用
    private Long hhId;//删除的时候使用:其他用户填写

    private Long ticketInstId; //   工单实例ID
    private String registerTime;//注册时间

    @XmlElement(name = "idcId" )
    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement(name = "nature" )
    @JsonProperty
    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }
    @XmlElement(name = "info" )
    @JsonProperty
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
    @XmlTransient
    @JsonProperty
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
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
    @XmlElement(name = "userId" )
    @JsonProperty
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @XmlElement(name = "hhId" )
    @JsonProperty
    public Long getHhId() {
        return hhId;
    }

    public void setHhId(Long hhId) {
        this.hhId = hhId;
    }
    @XmlElement(name = "serviceInfo" )
    @JsonProperty
    public List<ServiceInfo> getServiceInfos() {
        return serviceInfos;
    }

    public void setServiceInfos(List<ServiceInfo> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }

    @XmlElement(name = "service" )
    @JsonProperty
    public List<ServiceData> getServiceData() {
        return serviceData;
    }
    public void setServiceData(List<ServiceData> serviceData) {
        this.serviceData = serviceData;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getUnitNature() {
        return unitNature;
    }

    public void setUnitNature(Integer unitNature) {
        this.unitNature = unitNature;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
