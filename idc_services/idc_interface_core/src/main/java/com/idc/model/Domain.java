package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 域名信息
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "name"
})
public class Domain implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_DOMAIN";
    private Long aid;
    private Long fkServiceInfoId;
    private Long id;//域名的ID，该应用服务内唯一
    private String  name;//	对应该域名ID的域名
    private Long ticketInstId; //   工单实例ID

    @XmlElement(name = "id" )
    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement(name = "name" )
    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Long getFkServiceInfoId() {
        return fkServiceInfoId;
    }

    public void setFkServiceInfoId(Long fkServiceInfoId) {
        this.fkServiceInfoId = fkServiceInfoId;
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
