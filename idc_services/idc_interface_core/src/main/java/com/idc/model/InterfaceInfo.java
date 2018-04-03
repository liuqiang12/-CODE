package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 接口数据源组装类
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "idcId",
        "idcName",
        "idcAdd",
        "idcZip",
        "corp",
        "idcOfficer",
        "emergencyContact",
        "houseCount" ,
        "updateData",
        "addHouseInfos",
        "userInfos",
        "deleteData"})
public class InterfaceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_INTERFACE_INFO";
    private Long aid;
    private Long fkCofficerInfoId;
    private Long fkEmergencyContact;
    private String idcId;//IDC/ISP经营者ID:固化字段
    private String idcName;//经营者名称，与许可证上名称一致:固化字段
    private String idcAdd;//经营者通信地址:固化字段
    private String idcZip;//对应经营者通信地址的邮编:固化字段
    private String corp;//企业法人
    private IdcOfficer idcOfficer;//网络信息安全责任人信息
    private IdcOfficer emergencyContact;//单位应急联系人信息与联系方式
    private Long houseCount;//新增机房数量

    private List<HouseInfo> addHouseInfos;
    private UpdateData updateData;//只有update的时候才有的属性
    private DeleteData deleteData;
    private List<UserInfo> userInfos;
    private Long basicInfoId;
    private Long ticketInstId; //   工单实例ID

    @XmlElement(name = "idcId" )
    @JsonProperty
    public String getIdcId() {
        return idcId;
    }

    public void setIdcId(String idcId) {
        this.idcId = idcId;
    }
    @JsonProperty
    @XmlElement(name = "idcName", nillable = false )
    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }
    @JsonProperty
    @XmlElement(name = "idcAdd", nillable = false )
    public String getIdcAdd() {
        return idcAdd;
    }

    public void setIdcAdd(String idcAdd) {
        this.idcAdd = idcAdd;
    }
    @JsonProperty
    @XmlElement(name = "idcZip", nillable = false )
    public String getIdcZip() {
        return idcZip;
    }

    public void setIdcZip(String idcZip) {
        this.idcZip = idcZip;
    }
    @XmlElement(name = "corp", nillable = false )
    @JsonProperty
    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }
    @XmlElement(name = "idcOfficer", nillable = false )
    @JsonProperty
    public IdcOfficer getIdcOfficer() {
        return idcOfficer;
    }

    public void setIdcOfficer(IdcOfficer idcOfficer) {
        this.idcOfficer = idcOfficer;
    }

    @XmlElement(name = "houseCount", nillable = false )
    @JsonProperty
    public Long getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Long houseCount) {
        this.houseCount = houseCount;
    }
    @XmlElement(name = "houseInfo", nillable = false )
    @JsonProperty
    public List<HouseInfo> getAddHouseInfos() {
        return addHouseInfos;
    }

    public void setAddHouseInfos(List<HouseInfo> addHouseInfos) {
        this.addHouseInfos = addHouseInfos;
    }

    @XmlElement(name = "userInfo", nillable = false )
    @JsonProperty
    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
    @XmlElement(name = "emergencyContact", nillable = false )
    @JsonProperty
    public IdcOfficer getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(IdcOfficer emergencyContact) {
        this.emergencyContact = emergencyContact;
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
    public Long getBasicInfoId() {
        return basicInfoId;
    }

    public void setBasicInfoId(Long basicInfoId) {
        this.basicInfoId = basicInfoId;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkCofficerInfoId() {
        return fkCofficerInfoId;
    }

    public void setFkCofficerInfoId(Long fkCofficerInfoId) {
        this.fkCofficerInfoId = fkCofficerInfoId;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkEmergencyContact() {
        return fkEmergencyContact;
    }

    public void setFkEmergencyContact(Long fkEmergencyContact) {
        this.fkEmergencyContact = fkEmergencyContact;
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
    @XmlElement(name = "updateData", nillable = false )
    @JsonProperty
    public UpdateData getUpdateData() {
        return updateData;
    }

    public void setUpdateData(UpdateData updateData) {
        this.updateData = updateData;
    }
    @XmlElement(name = "deleteData", nillable = false )
    @JsonProperty
    public DeleteData getDeleteData() {
        return deleteData;
    }

    public void setDeleteData(DeleteData deleteData) {
        this.deleteData = deleteData;
    }
}
