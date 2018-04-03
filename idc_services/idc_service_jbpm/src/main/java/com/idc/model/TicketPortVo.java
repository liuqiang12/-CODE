package com.idc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/27.
 * 工单分配资源时需要的端口带宽模型
 */
public class TicketPortVo extends IdcHisTicketResource implements Serializable{
    //---------- port start------------
    private String portId; /*端口ID*/
    private String portName; /*端口名称*/
    private String portActiveName; /*端口状态*/
    private String portMediaTypeName; /*端口光电特性*/
    private String portAdminStatusName; /*操作状态名称*/
    private String portBandWidth; /*端口带宽*/
    private String portAssigation; /*端口分派带宽*/
    private String portIp; /*端口对应的IP*/
    private String portMac; /*端口对应的mac*/
    private String portNetmask; /*端口对应的子网掩码*/
    private String rackId;/*所属机架ID*/

    private String deviceName; /*设备名称*/
    private String routname; /*路由名称*/
    //---------- port end  ------------

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortActiveName() {
        return portActiveName;
    }

    public void setPortActiveName(String portActiveName) {
        this.portActiveName = portActiveName;
    }

    public String getPortMediaTypeName() {
        return portMediaTypeName;
    }

    public void setPortMediaTypeName(String portMediaTypeName) {
        this.portMediaTypeName = portMediaTypeName;
    }

    public String getPortAdminStatusName() {
        return portAdminStatusName;
    }

    public void setPortAdminStatusName(String portAdminStatusName) {
        this.portAdminStatusName = portAdminStatusName;
    }

    public String getPortBandWidth() {
        return portBandWidth;
    }

    public void setPortBandWidth(String portBandWidth) {
        this.portBandWidth = portBandWidth;
    }

    public String getPortAssigation() {
        return portAssigation;
    }

    public void setPortAssigation(String portAssigation) {
        this.portAssigation = portAssigation;
    }

    public String getPortIp() {
        return portIp;
    }

    public void setPortIp(String portIp) {
        this.portIp = portIp;
    }

    public String getPortMac() {
        return portMac;
    }

    public void setPortMac(String portMac) {
        this.portMac = portMac;
    }

    public String getPortNetmask() {
        return portNetmask;
    }

    public void setPortNetmask(String portNetmask) {
        this.portNetmask = portNetmask;
    }

    @Override
    public String getRackId() {
        return rackId;
    }

    @Override
    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRoutname() {
        return routname;
    }

    public void setRoutname(String routname) {
        this.routname = routname;
    }
}
