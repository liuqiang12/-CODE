package com.idc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/27.
 * 工单分配资源时需要的MCB模型
 */
public class TicketIdcMcbVo implements Serializable{
    //---------- rack start------------
    private String customerRackId; /*机架ID*/
    private String powerStatus; /*mcb的使用状态*/
    private String powerStatusName; /*使用状态*/
    private String mcbNo; /*mcb编号*/
    private String pdfInstalledId; /*被安装的PDF架Id*/
    private String pdfName; /*pdf名称*/
    private String pdfPowerType; /*pdf的电源类型*/
    private String pdfPowerTypeName; /*pdf的电源类型名称*/
    private String status;
    private String manualName;
    private String ticketInstId;
    private String category;
    private String mcbId;
    private String customerRackName;//客户机架名称
    private String ratedelectricenergy; //   额定电量KWH_电力信息end
    private String mcbName; //   mcb的名称

    //---------- rack end------------

    public String getMcbName() {
        return mcbName;
    }

    public void setMcbName(String mcbName) {
        this.mcbName = mcbName;
    }

    public String getCustomerRackId() {
        return customerRackId;
    }

    public void setCustomerRackId(String customerRackId) {
        this.customerRackId = customerRackId;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }

    public String getPowerStatusName() {
        return powerStatusName;
    }

    public void setPowerStatusName(String powerStatusName) {
        this.powerStatusName = powerStatusName;
    }

    public String getMcbNo() {
        return mcbNo;
    }

    public void setMcbNo(String mcbNo) {
        this.mcbNo = mcbNo;
    }

    public String getPdfInstalledId() {
        return pdfInstalledId;
    }

    public void setPdfInstalledId(String pdfInstalledId) {
        this.pdfInstalledId = pdfInstalledId;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfPowerType() {
        return pdfPowerType;
    }

    public void setPdfPowerType(String pdfPowerType) {
        this.pdfPowerType = pdfPowerType;
    }

    public String getPdfPowerTypeName() {
        return pdfPowerTypeName;
    }

    public void setPdfPowerTypeName(String pdfPowerTypeName) {
        this.pdfPowerTypeName = pdfPowerTypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManualName() {
        return manualName;
    }

    public void setManualName(String manualName) {
        this.manualName = manualName;
    }

    public String getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(String ticketInstId) {
        this.ticketInstId = ticketInstId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMcbId() {
        return mcbId;
    }

    public void setMcbId(String mcbId) {
        this.mcbId = mcbId;
    }

    public String getCustomerRackName() {
        return customerRackName;
    }

    public void setCustomerRackName(String customerRackName) {
        this.customerRackName = customerRackName;
    }

    public String getRatedelectricenergy() {
        return ratedelectricenergy;
    }

    public void setRatedelectricenergy(String ratedelectricenergy) {
        this.ratedelectricenergy = ratedelectricenergy;
    }
}
