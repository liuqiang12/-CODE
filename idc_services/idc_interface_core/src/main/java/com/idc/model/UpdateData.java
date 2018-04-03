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
@XmlType(propOrder = {
        "houseInfos",
        "userInfos"})
public class UpdateData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_UPDATEDATA";
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
    @XmlElement(name = "houseInfo", nillable = false )
    @JsonProperty
    public List<HouseInfo> getHouseInfos() {
        return houseInfos;
    }

    public void setHouseInfos(List<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
    }
    @XmlElement(name = "userInfo", nillable = false )
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
