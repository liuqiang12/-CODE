package com.idc.model;import java.math.BigDecimal;import java.io.Serializable;import java.math.BigDecimal;import java.util.ArrayList;import java.util.Date;import java.sql.Timestamp;import java.sql.Clob;import java.text.SimpleDateFormat;import java.util.List;import javax.persistence.GeneratedValue;import javax.persistence.Column;import javax.persistence.Id;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>IDC_LINK:连接表<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Jun 01,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class IdcLink implements Serializable {    public static final String tableName = "IDC_LINK";    @Id    @GeneratedValue    private Long id; //    private String name; //   名称    private String code; //   编码    private Date createdate; //   创建日期    private String createdateStr; //   创建日期 [日期格式化后的字符串]    private Date updatedate; //   更新日期    private String updatedateStr; //   更新日期 [日期格式化后的字符串]    private String businesstypeId; //   类型    private Long aendportId; //   A端端口    private Long aenddeviceId; //   A端设备    private Long aendrackId; //   A端机架    private Long zendportId; //   Z端端口    private Long zenddeviceId; //   Z端设备    private Long zendrackId; //   Z端机架    private IdcDevice Zdevice;    private IdcConnector zconn;    private IdcRack zrack;    /************************     * get set method     **************************/    public Long getId() {        return this.id;    }    @Column(name = "ID", nullable = false, length = 22)    public void setId(Long id) {        this.id = id;    }    public String getName() {        return this.name;    }    @Column(name = "NAME", columnDefinition = "名称", nullable = false, length = 255)    public void setName(String name) {        this.name = name;    }    public String getCode() {        return this.code;    }    @Column(name = "CODE", columnDefinition = "编码", nullable = false, length = 255)    public void setCode(String code) {        this.code = code;    }    public Date getCreatedate() {        return this.createdate;    }    @Column(name = "CREATEDATE", columnDefinition = "创建日期", nullable = false, length = 7)    public void setCreatedate(Date createdate) {        this.createdate = createdate;    }    public Date getUpdatedate() {        return this.updatedate;    }    @Column(name = "UPDATEDATE", columnDefinition = "更新日期", nullable = false, length = 7)    public void setUpdatedate(Date updatedate) {        this.updatedate = updatedate;    }    public String getBusinesstypeId() {        return this.businesstypeId;    }    @Column(name = "BUSINESSTYPE_ID", columnDefinition = "类型", nullable = false, length = 255)    public void setBusinesstypeId(String businesstypeId) {        this.businesstypeId = businesstypeId;    }    public Long getAendportId() {        return this.aendportId;    }    @Column(name = "AENDPORT_ID", columnDefinition = "A端端口", nullable = false, length = 22)    public void setAendportId(Long aendportId) {        this.aendportId = aendportId;    }    public Long getAenddeviceId() {        return this.aenddeviceId;    }    @Column(name = "AENDDEVICE_ID", columnDefinition = "A端设备", nullable = false, length = 22)    public void setAenddeviceId(Long aenddeviceId) {        this.aenddeviceId = aenddeviceId;    }    public Long getAendrackId() {        return this.aendrackId;    }    @Column(name = "AENDRACK_ID", columnDefinition = "A端机架", nullable = false, length = 22)    public void setAendrackId(Long aendrackId) {        this.aendrackId = aendrackId;    }    public Long getZendportId() {        return this.zendportId;    }    @Column(name = "ZENDPORT_ID", columnDefinition = "Z端端口", nullable = false, length = 22)    public void setZendportId(Long zendportId) {        this.zendportId = zendportId;    }    public Long getZenddeviceId() {        return this.zenddeviceId;    }    @Column(name = "ZENDDEVICE_ID", columnDefinition = "Z端设备", nullable = false, length = 22)    public void setZenddeviceId(Long zenddeviceId) {        this.zenddeviceId = zenddeviceId;    }    public Long getZendrackId() {        return this.zendrackId;    }    @Column(name = "ZENDRACK_ID", columnDefinition = "Z端机架", nullable = false, length = 22)    public void setZendrackId(Long zendrackId) {        this.zendrackId = zendrackId;    }    public String getCreatedateStr() {        return this.createdateStr;    }    public void setCreatedateStr(Date createdate) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String date_ = null;        try {            date_ = sdf.format(createdate);        } catch (Exception e) {            e.printStackTrace();        }        this.createdateStr = date_;    }    public String getUpdatedateStr() {        return this.updatedateStr;    }    public void setUpdatedateStr(Date updatedate) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String date_ = null;        try {            date_ = sdf.format(updatedate);        } catch (Exception e) {            e.printStackTrace();        }        this.updatedateStr = date_;    }    public IdcDevice getZdevice() {        return Zdevice;    }    public void setZdevice(IdcDevice zdevice) {        Zdevice = zdevice;    }    public IdcConnector getZconn() {        return zconn;    }    public void setZconn(IdcConnector zconn) {        this.zconn = zconn;    }    public IdcRack getZrack() {        return zrack;    }    public void setZrack(IdcRack zrack) {        this.zrack = zrack;    }    @Override    public String toString() {        return "idcLink [id = " + this.id + ",name = " + this.name + ",code = " + this.code + ",createdate = " + this.createdate + ",updatedate = " + this.updatedate +                ",businesstypeId = " + this.businesstypeId + ",aendportId = " + this.aendportId + ",aenddeviceId = " + this.aenddeviceId + ",aendrackId = " + this.aendrackId + ",zendportId = " + this.zendportId +                ",zenddeviceId = " + this.zenddeviceId + ",zendrackId = " + this.zendrackId + " ]";    }}