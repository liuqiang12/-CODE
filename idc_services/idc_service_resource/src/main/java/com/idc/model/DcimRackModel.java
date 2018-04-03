package com.idc.model;import java.math.BigDecimal;import java.io.Serializable;import java.math.BigDecimal;import java.util.Date;import java.sql.Timestamp;import java.sql.Clob;import java.text.SimpleDateFormat;import javax.persistence.GeneratedValue;import javax.persistence.Column;import javax.persistence.Id;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>DCIM_RACK_MODEL:机架规格<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Aug 01,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class DcimRackModel implements Serializable {    public static final String tableName = "DCIM_RACK_MODEL";    private Long id; //    @Id    @GeneratedValue    private String name; //   名称    private String code; //   编码    private Integer manufacture; //   品牌    private Long length; //   宽度(mm)    private Long width; //   深度(mm)    private Long height; //   高度(mm)    private String powerinfo; //    private String memo; //   备注    private Integer type; //   类别    private Integer uheight; //   U数    private Long devicewidth; //   设备安装宽度(英寸)    /************************get set method**************************/    public Long getId() {        return this.id;    }    @Column(name = "ID", nullable = false, length = 22)    public void setId(Long id) {        this.id = id;    }    public String getName() {        return this.name;    }    @Column(name = "NAME", columnDefinition = "名称", nullable = false, length = 64)    public void setName(String name) {        this.name = name;    }    public String getCode() {        return this.code;    }    @Column(name = "CODE", columnDefinition = "编码", nullable = false, length = 64)    public void setCode(String code) {        this.code = code;    }    public Integer getManufacture() {        return this.manufacture;    }    @Column(name = "MANUFACTURE", columnDefinition = "品牌", nullable = false, length = 22)    public void setManufacture(Integer manufacture) {        this.manufacture = manufacture;    }    public Long getLength() {        return this.length;    }    @Column(name = "LENGTH", columnDefinition = "宽度(mm)", nullable = false, length = 22)    public void setLength(Long length) {        this.length = length;    }    public Long getWidth() {        return this.width;    }    @Column(name = "WIDTH", columnDefinition = "深度(mm)", nullable = false, length = 22)    public void setWidth(Long width) {        this.width = width;    }    public Long getHeight() {        return this.height;    }    @Column(name = "HEIGHT", columnDefinition = "高度(mm)", nullable = false, length = 22)    public void setHeight(Long height) {        this.height = height;    }    public String getPowerinfo() {        return this.powerinfo;    }    @Column(name = "POWERINFO", nullable = false, length = 64)    public void setPowerinfo(String powerinfo) {        this.powerinfo = powerinfo;    }    public String getMemo() {        return this.memo;    }    @Column(name = "MEMO", columnDefinition = "备注", nullable = false, length = 64)    public void setMemo(String memo) {        this.memo = memo;    }    public Integer getType() {        return this.type;    }    @Column(name = "TYPE", columnDefinition = "类别", nullable = false, length = 22)    public void setType(Integer type) {        this.type = type;    }    public Integer getUheight() {        return this.uheight;    }    @Column(name = "UHEIGHT", columnDefinition = "U数", nullable = false, length = 22)    public void setUheight(Integer uheight) {        this.uheight = uheight;    }    public Long getDevicewidth() {        return this.devicewidth;    }    @Column(name = "DEVICEWIDTH", columnDefinition = "设备安装宽度(英寸)", nullable = false, length = 22)    public void setDevicewidth(Long devicewidth) {        this.devicewidth = devicewidth;    }    @Override    public String toString() {        return "dcimRackModel [id = " + this.id + ",name = " + this.name + ",code = " + this.code + ",manufacture = " + this.manufacture + ",length = " + this.length +                ",width = " + this.width + ",height = " + this.height + ",powerinfo = " + this.powerinfo + ",memo = " + this.memo + ",type = " + this.type +                ",uheight = " + this.uheight + ",devicewidth = " + this.devicewidth + " ]";    }}