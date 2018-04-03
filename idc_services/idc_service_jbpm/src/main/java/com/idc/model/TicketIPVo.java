package com.idc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/27.
 * 工单分配资源时需要的ip租用模型
 */
public class TicketIPVo extends IdcHisTicketResource implements Serializable{
    //---------- IP start------------
    private String ipId;/*ip主键*/
    private String ipIpaddress;/*ip地址*/
    private String ipSubnetip;/*子网*/
    private String ipMaskstr;/*掩码*/
    private String ipStatus;
    private String ipStatusName;/*使用状态*/
    private String ipType;//类型  ip网段：ipsubnet、ip地址：ipaddress
    //---------- IP end  ------------

    public String getIpId() {
        return ipId;
    }

    public void setIpId(String ipId) {
        this.ipId = ipId;
    }

    public String getIpIpaddress() {
        return ipIpaddress;
    }

    public void setIpIpaddress(String ipIpaddress) {
        this.ipIpaddress = ipIpaddress;
    }

    public String getIpSubnetip() {
        return ipSubnetip;
    }

    public void setIpSubnetip(String ipSubnetip) {
        this.ipSubnetip = ipSubnetip;
    }

    public String getIpMaskstr() {
        return ipMaskstr;
    }

    public void setIpMaskstr(String ipMaskstr) {
        this.ipMaskstr = ipMaskstr;
    }

    public String getIpStatus() {
        return ipStatus;
    }

    public void setIpStatus(String ipStatus) {
        this.ipStatus = ipStatus;
    }

    public String getIpStatusName() {
        return ipStatusName;
    }

    public void setIpStatusName(String ipStatusName) {
        this.ipStatusName = ipStatusName;
    }

    @Override
    public String getIpType() {
        return ipType;
    }

    @Override
    public void setIpType(String ipType) {
        this.ipType = ipType;
    }
}
