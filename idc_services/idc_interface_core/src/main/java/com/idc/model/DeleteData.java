package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/8/22.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "houseInfos",
        "userInfos"})
public class DeleteData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_DELETE_INFO";
    private Long aid;
    private Long fkNewInfoId;
    private Long ticketInstId;

    private List<HouseInfo> houseInfos;
    private List<UserInfo> userInfos;
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
    public String getTableName() {
        return tableName;
    }
    @XmlElement(name = "house", nillable = false )
    @JsonProperty
    public List<HouseInfo> getHouseInfos() {
        return houseInfos;
    }

    public void setHouseInfos(List<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
    }
    @XmlElement(name = "user", nillable = false )
    @JsonProperty
    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
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
