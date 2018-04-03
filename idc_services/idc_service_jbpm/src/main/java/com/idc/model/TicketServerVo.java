package com.idc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/27.
 * 工单分配资源时需要的主机租赁模型
 */
public class TicketServerVo extends IdcHisTicketResource implements Serializable{
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
}
