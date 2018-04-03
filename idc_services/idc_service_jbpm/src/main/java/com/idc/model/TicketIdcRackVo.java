package com.idc.model;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/6/27.
 * 工单分配资源时需要的机架模型
 */
public class TicketIdcRackVo  implements Serializable{

    //---------- rack start------------
    private String rackId; /*机架ID*/
    private String rackName; /*机架名称*/
    private String rackMode; /*机架型号*/
    private String machineroomId; /*机房ID*/
    private String machineroomName; /*所属机房*/
    private String directionName; /*机架方向*/
    private String rackSize; /*机架尺寸*/
    private String manufactureId; /*品牌*/
    private String rackStatusName;
    private String rackStatus;

    private String renttype; //出租类型，1代表按机位，0代表按机架

    //---------- rack end------------
    List<TicketIdcMcbVo> ticketIdcMcbVos =  new ArrayList<TicketIdcMcbVo>();


    public String getRenttype() {
        return renttype;
    }

    public void setRenttype(String renttype) {
        this.renttype = renttype;
    }


    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getRackMode() {
        return rackMode;
    }

    public void setRackMode(String rackMode) {
        this.rackMode = rackMode;
    }

    public String getMachineroomId() {
        return machineroomId;
    }

    public void setMachineroomId(String machineroomId) {
        this.machineroomId = machineroomId;
    }

    public String getMachineroomName() {
        return machineroomName;
    }

    public void setMachineroomName(String machineroomName) {
        this.machineroomName = machineroomName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getRackSize() {
        return rackSize;
    }

    public void setRackSize(String rackSize) {
        this.rackSize = rackSize;
    }

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId;
    }

    public List<TicketIdcMcbVo> getTicketIdcMcbVos() {
        return ticketIdcMcbVos;
    }

    public void setTicketIdcMcbVos(List<TicketIdcMcbVo> ticketIdcMcbVos) {
        this.ticketIdcMcbVos = ticketIdcMcbVos;
    }

    public String getRackStatusName() {
        return rackStatusName;
    }

    public void setRackStatusName(String rackStatusName) {
        this.rackStatusName = rackStatusName;
    }

    public String getRackStatus() {
        return rackStatus;
    }

    public void setRackStatus(String rackStatus) {
        this.rackStatus = rackStatus;
    }

}
