package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gaowei on 2017/5/27.
 */
public class IdcRackVo implements Serializable {


    @Excel(name = "所属机房编码")
    private String roomid;//gb

    @Excel(name = "所属VIP机房/机笼编码")
    private String roomareaid;//gb

    @Excel(name = "机架编码")
    private String code;

    @Excel(name = "机架名称")
    private String name;

    @Excel(name = "机架类型")
    private String businesstypeId;

    @Excel(name = "业务状态")
    private String status;//gb

    @Excel(name = "出租类型")
    private Short renttype;

    @Excel(name = "用途")
    private String usefor;//gb

    @Excel(name = "机架型号")
    private BigDecimal rackmodelid;

    @Excel(name = "机架尺寸")
    private String sizeType;//zj

    @Excel(name = "x")
    private BigDecimal x;

    @Excel(name = "y")
    private BigDecimal y;

    @Excel(name = "机架方向")
    private String direction;//gb

    @Excel(name = "机位顺序")
    private String arrangement;//gb

    @Excel(name = "机架U数")
    private String height;//gb

    @Excel(name = "剩余U数")
    private String leftheight;//zj

    @Excel(name = "用电类型")
    private String electrictype;

    @Excel(name = "额定电量")
    private Long ratedelectricenergy;

    @Excel(name = "已用电量（%）")
    private Long useratedelectricenergy;//zj

    @Excel(name = "工单编号")
    private String gdcode;//zj

    @Excel(name = "所属产品")
    private String productcode;//zj

    @Excel(name = "合同号")
    private String htcode;//zj

    @Excel(name = "所属客户")
    private String khcode;//zj


    public String getKhcode() {
        return khcode;
    }

    public void setKhcode(String khcode) {
        this.khcode = khcode;
    }

    public String getHtcode() {
        return htcode;
    }

    public void setHtcode(String htcode) {
        this.htcode = htcode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getGdcode() {
        return gdcode;
    }

    public void setGdcode(String gdcode) {
        this.gdcode = gdcode;
    }

    public Long getUseratedelectricenergy() {
        return useratedelectricenergy;
    }

    public void setUseratedelectricenergy(Long useratedelectricenergy) {
        this.useratedelectricenergy = useratedelectricenergy;
    }

    public Long getRatedelectricenergy() {
        return ratedelectricenergy;
    }

    public void setRatedelectricenergy(Long ratedelectricenergy) {
        this.ratedelectricenergy = ratedelectricenergy;
    }


    public String getElectrictype() {
        return electrictype;
    }

    public void setElectrictype(String electrictype) {
        this.electrictype = electrictype == null ? null : electrictype.trim();
    }

    public String getLeftheight() {
        return leftheight;
    }

    public void setLeftheight(String leftheight) {
        this.leftheight = leftheight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String getSizeType() {
        return sizeType;
    }

    public void setSizeType(String sizeType) {
        this.sizeType = sizeType;
    }

    public BigDecimal getRackmodelid() {
        return rackmodelid;
    }

    public void setRackmodelid(BigDecimal rackmodelid) {
        this.rackmodelid = rackmodelid;
    }

    public String getUsefor() {
        return usefor;
    }

    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }

    public Short getRenttype() {
        return renttype;
    }

    public void setRenttype(Short renttype) {
        this.renttype = renttype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomareaid() {
        return roomareaid;
    }

    public void setRoomareaid(String roomareaid) {
        this.roomareaid = roomareaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public String getBusinesstypeId() {
        return businesstypeId;
    }

    public void setBusinesstypeId(String businesstypeId) {
        this.businesstypeId = businesstypeId == null ? null : businesstypeId.trim();
    }
    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}
