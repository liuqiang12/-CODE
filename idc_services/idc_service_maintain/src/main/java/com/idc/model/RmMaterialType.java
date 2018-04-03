package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>RM_MATERIAL_TYPE:物资类别表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class RmMaterialType implements Serializable {

    public static final String tableName = "RM_MATERIAL_TYPE";

    @Id
    @GeneratedValue
    private String rmMaterialTypeId; //   物资类别ID

    private String rmMaterialTypeCode; //   物资编码

    private String rmMaterialTypeName; //   物资名称

    private String note; //   备注

    /************************get set method**************************/

    public String getRmMaterialTypeId() {
        return this.rmMaterialTypeId;
    }

    @Column(name = "RM_MATERIAL_TYPE_ID", columnDefinition = "物资类别ID", nullable = false, length = 100)
    public void setRmMaterialTypeId(String rmMaterialTypeId) {
        this.rmMaterialTypeId = rmMaterialTypeId;
    }

    public String getRmMaterialTypeCode() {
        return this.rmMaterialTypeCode;
    }

    @Column(name = "RM_MATERIAL_TYPE_CODE", columnDefinition = "物资编码", nullable = false, length = 32)
    public void setRmMaterialTypeCode(String rmMaterialTypeCode) {
        this.rmMaterialTypeCode = rmMaterialTypeCode;
    }

    public String getRmMaterialTypeName() {
        return this.rmMaterialTypeName;
    }

    @Column(name = "RM_MATERIAL_TYPE_NAME", columnDefinition = "物资名称", nullable = false, length = 32)
    public void setRmMaterialTypeName(String rmMaterialTypeName) {
        this.rmMaterialTypeName = rmMaterialTypeName;
    }

    public String getNote() {
        return this.note;
    }

    @Column(name = "NOTE", columnDefinition = "备注", nullable = false, length = 32)
    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "rmMaterialType [rmMaterialTypeId = " + this.rmMaterialTypeId + ",rmMaterialTypeCode = " + this.rmMaterialTypeCode + ",rmMaterialTypeName = " + this.rmMaterialTypeName + ",note = " + this.note + " ]";
    }


}