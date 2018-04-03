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
@XmlType(propOrder = {"serviceId","domainId","hhId"})
public class ServiceData  implements Serializable {
    private static final long serialVersionUID = 1L;
    //删除的时候使用
    private final String tableName = "IDC_ISP_USER_SERVICE";
    private Long aid;
    private Long fkUserInfoId;

    private List<Long> serviceId;
    private List<Long> domainId;
    private List<Long> hhId;
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
    @XmlElement(name = "serviceId" )
    @JsonProperty
    public List<Long> getServiceId() {
        return serviceId;
    }

    public void setServiceId(List<Long> serviceId) {
        this.serviceId = serviceId;
    }
    @XmlElement(name = "domainId" )
    @JsonProperty
    public List<Long> getDomainId() {
        return domainId;
    }

    public void setDomainId(List<Long> domainId) {
        this.domainId = domainId;
    }
    @XmlElement(name = "hhId" )
    @JsonProperty
    public List<Long> getHhId() {
        return hhId;
    }

    public void setHhId(List<Long> hhId) {
        this.hhId = hhId;
    }
}
