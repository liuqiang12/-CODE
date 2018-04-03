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
public class UserverItem  implements Serializable {
    private static final long serialVersionUID = 1L;
    //删除的时候使用
    private final String tableName = "IDC_ISP_USER_SERVICE";
    private Long aid;
    private Long fkServiceId;
    private Long itemId;
    private Long itemFlag;

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
    public String getTableName() {
        return tableName;
    }
    @XmlTransient
    @JsonProperty
    public Long getFkServiceId() {
        return fkServiceId;
    }

    public void setFkServiceId(Long fkServiceId) {
        this.fkServiceId = fkServiceId;
    }
    @XmlTransient
    @JsonProperty
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    @XmlTransient
    @JsonProperty
    public Long getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Long itemFlag) {
        this.itemFlag = itemFlag;
    }
}
