package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>RM_DEV_LIST:设备清单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class RmDevList implements Serializable {

    public static final String tableName = "RM_DEV_LIST";

    @Id
    @GeneratedValue
    private String rmDevListId; //   设备ID

    private String rmDevInOutId; //   设备出入ID

    private String rmDevName; //   设备名称

    private String rmDevCode; //   设备编号

    private String rmDevType; //   设备类别

    private String rmDevUnit; //   单位

    private Long rmDevNum; //   数量

    private String note; //   备注

    /************************get set method**************************/

    public String getRmDevListId() {
        return this.rmDevListId;
    }

    @Column(name = "RM_DEV_LIST_ID", columnDefinition = "设备ID", nullable = false, length = 100)
    public void setRmDevListId(String rmDevListId) {
        this.rmDevListId = rmDevListId;
    }

    public String getRmDevInOutId() {
        return this.rmDevInOutId;
    }

    @Column(name = "RM_DEV_IN_OUT_ID", columnDefinition = "设备出入ID", nullable = false, length = 32)
    public void setRmDevInOutId(String rmDevInOutId) {
        this.rmDevInOutId = rmDevInOutId;
    }

    public String getRmDevName() {
        return this.rmDevName;
    }

    @Column(name = "RM_DEV_NAME", columnDefinition = "设备名称", nullable = false, length = 32)
    public void setRmDevName(String rmDevName) {
        this.rmDevName = rmDevName;
    }

    public String getRmDevCode() {
        return this.rmDevCode;
    }

    @Column(name = "RM_DEV_CODE", columnDefinition = "设备编号", nullable = false, length = 32)
    public void setRmDevCode(String rmDevCode) {
        this.rmDevCode = rmDevCode;
    }

    public String getRmDevType() {
        return this.rmDevType;
    }

    @Column(name = "RM_DEV_TYPE", columnDefinition = "设备类别", nullable = false, length = 32)
    public void setRmDevType(String rmDevType) {
        this.rmDevType = rmDevType;
    }

    public String getRmDevUnit() {
        return this.rmDevUnit;
    }

    @Column(name = "RM_DEV_UNIT", columnDefinition = "单位", nullable = false, length = 32)
    public void setRmDevUnit(String rmDevUnit) {
        this.rmDevUnit = rmDevUnit;
    }

    public Long getRmDevNum() {
        return this.rmDevNum;
    }

    @Column(name = "RM_DEV_NUM", columnDefinition = "数量", nullable = false, length = 22)
    public void setRmDevNum(Long rmDevNum) {
        this.rmDevNum = rmDevNum;
    }

    public String getNote() {
        return this.note;
    }

    @Column(name = "NOTE", columnDefinition = "备注", nullable = false, length = 100)
    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "rmDevList [rmDevListId = " + this.rmDevListId + ",rmDevInOutId = " + this.rmDevInOutId + ",rmDevName = " + this.rmDevName + ",rmDevCode = " + this.rmDevCode + ",rmDevType = " + this.rmDevType +
                ",rmDevUnit = " + this.rmDevUnit + ",rmDevNum = " + this.rmDevNum + ",note = " + this.note + " ]";
    }


}