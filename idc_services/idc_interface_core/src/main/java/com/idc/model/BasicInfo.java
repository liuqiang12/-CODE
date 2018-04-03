
package com.idc.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 接口数据源组装类
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(name = "basicInfo", propOrder = { "provID", "newInfo","updateInfo","deleteInfo", "timeStamp" })
/*@XmlSchema(xmlns={@XmlNs(prefix=TicketFTPNameSpace.basicInfo_PREFIX, namespaceURI= TicketFTPNameSpace.basicInfo_URI)})*/
public class BasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "IDC_ISP_BASIC_INFO";
    private Long aid;
    private String provID;//省份编号：默认值280
    private InterfaceInfo newInfo;//新增的实体类time_stamp//新增数据
    private InterfaceInfo updateInfo;//修改数据
    private InterfaceInfo deleteInfo;//删除的数据
    private String timeStamp;//上报数据的时间
    private Long ticketInstId; //   工单实例ID
    private Integer idcIspStatus;//状态

    @XmlElement(name = "provID" )
    @JsonProperty
    public String getProvID() {
        return provID;
    }

    public void setProvID(String provID) {
        this.provID = provID;
    }
    @XmlElement(name = "newInfo",nillable = false)
    @JsonProperty
    public InterfaceInfo getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(InterfaceInfo newInfo) {
        this.newInfo = newInfo;
    }
    @XmlElement(name = "timeStamp",required = true,nillable = true)
    @JsonProperty
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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

    @XmlElement(name = "updateInfo",nillable = false)
    @JsonProperty
    public InterfaceInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(InterfaceInfo updateInfo) {
        this.updateInfo = updateInfo;
    }
    @XmlElement(name = "deleteInfo",nillable = false)
    @JsonProperty
    public InterfaceInfo getDeleteInfo() {
        return deleteInfo;
    }

    public void setDeleteInfo(InterfaceInfo deleteInfo) {
        this.deleteInfo = deleteInfo;
    }
    @XmlTransient
    @JsonProperty
    public Integer getIdcIspStatus() {
        return idcIspStatus;
    }

    public void setIdcIspStatus(Integer idcIspStatus) {
        this.idcIspStatus = idcIspStatus;
    }
}
