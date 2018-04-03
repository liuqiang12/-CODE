package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gaowei on 2017/5/31.
 */
public class ZbMachineroomVo implements Serializable {

    @Excel(name="归属机楼编码")
    private String buildingid;//gb

    @Excel(name="所在楼层")
    private String floor;//zj

    @Excel(name = "机房编码")
    private String sitenamesn;

    @Excel(name = "机房名称")
    private String sitename;

    @Excel(name = "机房类别")
    private String roomcategory;

    @Excel(name = "机房等级")
    private String level;

    @Excel(name = "业务类型")
    private String usefor;

    @Excel(name = "备注")
    public  String remark;

    @Excel(name = "机房面积")
    private BigDecimal area;

    @Excel(name = "宽度(地板数)")
    private String widthM;

    @Excel(name = "长度(地板数)")
    private String heightM;

    @Excel(name = "楼层高度(米)")
    private String floorheight;

    @Excel(name = "地板承重")
    private String floormaximum;//zj

    @Excel(name = "活动地板高度")
    private String mobileboards;//zj

    @Excel(name = "空调备用")
    private String airreserve;

    @Excel(name = "制冷方式")//zj
    private String refrigerationmethod;

    @Excel(name = "总设计电量(KVA)")
    private String designelectricity;

    @Excel(name = "机房出口带宽")
    private String totalbandwidth;


    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSitenamesn() {
        return sitenamesn;
    }

    public void setSitenamesn(String sitenamesn) {
        this.sitenamesn = sitenamesn;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getDesignelectricity() {
        return designelectricity;
    }

    public void setDesignelectricity(String designelectricity) {
        this.designelectricity = designelectricity;
    }

    public String getFloorheight() {
        return floorheight;
    }

    public void setFloorheight(String floorheight) {
        this.floorheight = floorheight;
    }

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(String buildingid) {
        this.buildingid = buildingid;
    }

    public String getUsefor() {
        return usefor;
    }

    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }

    public String getRoomcategory() {
        return roomcategory;
    }

    public void setRoomcategory(String roomcategory) {
        this.roomcategory = roomcategory;
    }

    public String getTotalbandwidth() {
        return totalbandwidth;
    }

    public void setTotalbandwidth(String totalbandwidth) {
        this.totalbandwidth = totalbandwidth;
    }

    public String getWidthM() {
        return widthM;
    }

    public void setWidthM(String widthM) {
        this.widthM = widthM;
    }

    public String getHeightM() {
        return heightM;
    }

    public void setHeightM(String heightM) {
        this.heightM = heightM;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFloormaximum() {
        return floormaximum;
    }

    public void setFloormaximum(String floormaximum) {
        this.floormaximum = floormaximum;
    }

    public String getMobileboards() {
        return mobileboards;
    }

    public void setMobileboards(String mobileboards) {
        this.mobileboards = mobileboards;
    }

    public String getAirreserve() {
        return airreserve;
    }

    public void setAirreserve(String airreserve) {
        this.airreserve = airreserve;
    }

    public String getRefrigerationmethod() {
        return refrigerationmethod;
    }

    public void setRefrigerationmethod(String refrigerationmethod) {
        this.refrigerationmethod = refrigerationmethod;
    }
}
