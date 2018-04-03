package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 人员信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "name",
        "idType",
        "id",
        "tel",
        "mobile",
        "email"})
public class IdcOfficer implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_COFFICER_INFO";
    private Long aid;
    private String name;//名称
    private Integer idType;//人员的证件类型
    private String id;//对应的证件号码
    private String tel;//固定电话
    private String mobile;//移动电话
    private String email;//email地址
    private String category;//类型:1单位的网络安全责任人信息与联系方式;2单位应急联系人信息与联系方式
    private Long ticketInstId; //   工单实例ID

    @XmlElement(name = "name" )
    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "idType",nillable =false )
    @JsonProperty
    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }
    @XmlElement(name = "id",nillable =false )
    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlElement(name = "tel",nillable =false )
    @JsonProperty
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    @XmlElement(name = "mobile" )
    @JsonProperty
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @XmlElement(name = "email",nillable =false )
    @JsonProperty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
