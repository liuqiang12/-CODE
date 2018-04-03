package com.idc.model;import javax.persistence.Column;import javax.persistence.GeneratedValue;import javax.persistence.Id;import java.io.Serializable;import java.text.SimpleDateFormat;import java.util.Date;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>RM_MATERIAL_DETAIL:物资出入库详情表<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Nov 03,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class RmMaterialDetail implements Serializable {    public static final String tableName = "RM_MATERIAL_DETAIL";    @Id    @GeneratedValue    private String rmMaterialDetailId; //   物资出入详情ID    private String rmMaterialTypeId; //   物资类别ID    private String rmMaterialCustomer; //   客户名称    private Long rmMaterialNum; //   数量    private Date rmMaterialInOutTime; //   出入时间    private String rmMaterialInOutTimeStr; //   出入时间 [日期格式化后的字符串]    private String rmMaterialInOutType; //   出入类型    private String rmMaterialCode; //   运单号    private String rmMaterialDisposeMan; //   处理人    private String note; //   备注    private Date rmCreateTime; //   创建时间    private String rmCreateTimeStr; //   创建时间 [日期格式化后的字符串]    private String rmCreateUser; //   创建人    /************************get set method**************************/    public String getRmMaterialDetailId() {        return this.rmMaterialDetailId;    }    @Column(name = "RM_MATERIAL_DETAIL_ID", columnDefinition = "物资出入详情ID", nullable = false, length = 100)    public void setRmMaterialDetailId(String rmMaterialDetailId) {        this.rmMaterialDetailId = rmMaterialDetailId;    }    public String getRmMaterialTypeId() {        return this.rmMaterialTypeId;    }    @Column(name = "RM_MATERIAL_TYPE_ID", columnDefinition = "物资类别ID", nullable = false, length = 32)    public void setRmMaterialTypeId(String rmMaterialTypeId) {        this.rmMaterialTypeId = rmMaterialTypeId;    }    public String getRmMaterialCustomer() {        return this.rmMaterialCustomer;    }    @Column(name = "RM_MATERIAL_CUSTOMER", columnDefinition = "客户名称", nullable = false, length = 32)    public void setRmMaterialCustomer(String rmMaterialCustomer) {        this.rmMaterialCustomer = rmMaterialCustomer;    }    public Long getRmMaterialNum() {        return this.rmMaterialNum;    }    @Column(name = "RM_MATERIAL_NUM", columnDefinition = "数量", nullable = false, length = 22)    public void setRmMaterialNum(Long rmMaterialNum) {        this.rmMaterialNum = rmMaterialNum;    }    public Date getRmMaterialInOutTime() {        return this.rmMaterialInOutTime;    }    @Column(name = "RM_MATERIAL_IN_OUT_TIME", columnDefinition = "出入时间", nullable = false, length = 7)    public void setRmMaterialInOutTime(Date rmMaterialInOutTime) {        this.rmMaterialInOutTime = rmMaterialInOutTime;    }    public String getRmMaterialInOutType() {        return this.rmMaterialInOutType;    }    @Column(name = "RM_MATERIAL_IN_OUT_TYPE", columnDefinition = "出入类型", nullable = false, length = 4)    public void setRmMaterialInOutType(String rmMaterialInOutType) {        this.rmMaterialInOutType = rmMaterialInOutType;    }    public String getRmMaterialCode() {        return this.rmMaterialCode;    }    @Column(name = "RM_MATERIAL_CODE", columnDefinition = "运单号", nullable = false, length = 32)    public void setRmMaterialCode(String rmMaterialCode) {        this.rmMaterialCode = rmMaterialCode;    }    public String getRmMaterialDisposeMan() {        return this.rmMaterialDisposeMan;    }    @Column(name = "RM_MATERIAL_DISPOSE_MAN", columnDefinition = "处理人", nullable = false, length = 32)    public void setRmMaterialDisposeMan(String rmMaterialDisposeMan) {        this.rmMaterialDisposeMan = rmMaterialDisposeMan;    }    public String getNote() {        return this.note;    }    @Column(name = "NOTE", columnDefinition = "备注", nullable = false, length = 32)    public void setNote(String note) {        this.note = note;    }    public Date getRmCreateTime() {        return this.rmCreateTime;    }    @Column(name = "RM_CREATE_TIME", columnDefinition = "创建时间", nullable = false, length = 7)    public void setRmCreateTime(Date rmCreateTime) {        this.rmCreateTime = rmCreateTime;    }    public String getRmCreateUser() {        return this.rmCreateUser;    }    @Column(name = "RM_CREATE_USER", columnDefinition = "创建人", nullable = false, length = 32)    public void setRmCreateUser(String rmCreateUser) {        this.rmCreateUser = rmCreateUser;    }    public String getRmMaterialInOutTimeStr() {        return this.rmMaterialInOutTimeStr;    }    public void setRmMaterialInOutTimeStr(Date rmMaterialInOutTime) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String date_ = null;        try {            date_ = sdf.format(rmMaterialInOutTime);        } catch (Exception e) {            e.printStackTrace();        }        this.rmMaterialInOutTimeStr = date_;    }    public String getRmCreateTimeStr() {        return this.rmCreateTimeStr;    }    public void setRmCreateTimeStr(Date rmCreateTime) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String date_ = null;        try {            date_ = sdf.format(rmCreateTime);        } catch (Exception e) {            e.printStackTrace();        }        this.rmCreateTimeStr = date_;    }    @Override    public String toString() {        return "rmMaterialDetail [rmMaterialDetailId = " + this.rmMaterialDetailId + ",rmMaterialTypeId = " + this.rmMaterialTypeId + ",rmMaterialCustomer = " + this.rmMaterialCustomer + ",rmMaterialNum = " + this.rmMaterialNum + ",rmMaterialInOutTime = " + this.rmMaterialInOutTime +                ",rmMaterialInOutType = " + this.rmMaterialInOutType + ",rmMaterialCode = " + this.rmMaterialCode + ",rmMaterialDisposeMan = " + this.rmMaterialDisposeMan + ",note = " + this.note + ",rmCreateTime = " + this.rmCreateTime +                ",rmCreateUser = " + this.rmCreateUser + " ]";    }}