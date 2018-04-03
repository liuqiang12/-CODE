package com.idc.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by DELL on 2017/6/27.
 * 弹出框的主机设备信息
 */
public class WinServerVo implements Serializable{
    //---------- server start------------
    private String serverDeviceId;/*设备id*/
    private String serverOs; /*操作系统*/
    private String serverCpusize; /*主频大小*/
    private String serverMemsize; /*内存大小*/
    private String serverDisksize; /*硬盘大小*/

    private String serverDeviceName;/*设备名称*/
    private String serverDeviceRackId;/*所属机架ID*/
    private String serverDeviceModel;/*设备规格*/
    private String serverDeviceStatusName;/*使用状态*/
    private String serverDeviceVendorName;/*厂商*/
    private String serverDeviceOwner;/*联系人*/
    private String serverOwnertypeName;/*产权性质*/
    private String serverRackName;/*所属机架名称*/

    //额外设置属性
    private Long ticketInstId; //   工单实例ID
    private Long resourceid; //   资源ID
    private Integer status; //   资源状态:改成预占  状态
    private Integer manual; //   添加方式，Dict:1,手工添加;0,自动关联
    private String category;//类型:100机架机位200端口带宽300ip租用400主机租赁500增值业务
    private String ticketCategory;//100预勘/预占 200开通 300变更  400暂停  500复通  600下线
    private String ipType;//IP类型：ip网段：ipsubnet、ip地址：ipaddress
    private String customerId;//客户id

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String customerName;//客户名称
    private Boolean isIpPort = Boolean.FALSE;   //判断是否是端口带宽这个操作


    @Id@GeneratedValue
    private String id;//这个id是运行时的资源工单中间表id
    //---------- server end  ------------

    public String getServerDeviceId() {
        return serverDeviceId;
    }

    public void setServerDeviceId(String serverDeviceId) {
        this.serverDeviceId = serverDeviceId;
    }

    public String getServerDeviceName() {
        return serverDeviceName;
    }

    public void setServerDeviceName(String serverDeviceName) {
        this.serverDeviceName = serverDeviceName;
    }

    public String getServerDeviceRackId() {
        return serverDeviceRackId;
    }

    public void setServerDeviceRackId(String serverDeviceRackId) {
        this.serverDeviceRackId = serverDeviceRackId;
    }

    public String getServerDeviceModel() {
        return serverDeviceModel;
    }

    public void setServerDeviceModel(String serverDeviceModel) {
        this.serverDeviceModel = serverDeviceModel;
    }

    public String getServerDeviceStatusName() {
        return serverDeviceStatusName;
    }

    public void setServerDeviceStatusName(String serverDeviceStatusName) {
        this.serverDeviceStatusName = serverDeviceStatusName;
    }

    public String getServerDeviceVendorName() {
        return serverDeviceVendorName;
    }

    public void setServerDeviceVendorName(String serverDeviceVendorName) {
        this.serverDeviceVendorName = serverDeviceVendorName;
    }

    public String getServerDeviceOwner() {
        return serverDeviceOwner;
    }

    public void setServerDeviceOwner(String serverDeviceOwner) {
        this.serverDeviceOwner = serverDeviceOwner;
    }

    public String getServerOwnertypeName() {
        return serverOwnertypeName;
    }

    public void setServerOwnertypeName(String serverOwnertypeName) {
        this.serverOwnertypeName = serverOwnertypeName;
    }

    public String getServerRackName() {
        return serverRackName;
    }

    public void setServerRackName(String serverRackName) {
        this.serverRackName = serverRackName;
    }

    public String getServerOs() {
        return serverOs;
    }

    public void setServerOs(String serverOs) {
        this.serverOs = serverOs;
    }

    public String getServerCpusize() {
        return serverCpusize;
    }

    public void setServerCpusize(String serverCpusize) {
        this.serverCpusize = serverCpusize;
    }

    public String getServerMemsize() {
        return serverMemsize;
    }

    public void setServerMemsize(String serverMemsize) {
        this.serverMemsize = serverMemsize;
    }

    public String getServerDisksize() {
        return serverDisksize;
    }

    public void setServerDisksize(String serverDisksize) {
        this.serverDisksize = serverDisksize;
    }

    public Long getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId = ticketInstId;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getManual() {
        return manual;
    }

    public void setManual(Integer manual) {
        this.manual = manual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public Boolean getIsIpPort() {
        return this.isIpPort;
    }

    public void setIsIpPort(Boolean isIpPort) {
        this.isIpPort = isIpPort;
    }
}
