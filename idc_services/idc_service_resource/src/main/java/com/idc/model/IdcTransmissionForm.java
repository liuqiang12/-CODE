package com.idc.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_TRANSMISSION_FORM:传输单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Sep 28,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcTransmissionForm implements Serializable {

    public static final String tableName = "IDC_TRANSMISSION_FORM";

    @Id
    @GeneratedValue
    private Long idcTransmissionId; //   主键ID

    private String idcTransmissionCode; //   编码

    private String note; //   备注

    /************************get set method**************************/

    public Long getIdcTransmissionId() {
        return this.idcTransmissionId;
    }

    @Column(name = "IDC_TRANSMISSION_ID", columnDefinition = "主键ID", nullable = false, length = 32)
    public void setIdcTransmissionId(Long idcTransmissionId) {
        this.idcTransmissionId = idcTransmissionId;
    }

    public String getIdcTransmissionCode() {
        return this.idcTransmissionCode;
    }

    @Column(name = "IDC_TRANSMISSION_CODE", columnDefinition = "编码", nullable = false, length = 50)
    public void setIdcTransmissionCode(String idcTransmissionCode) {
        this.idcTransmissionCode = idcTransmissionCode;
    }

    public String getNote() {
        return this.note;
    }

    @Column(name = "NOTE", columnDefinition = "备注", nullable = false, length = 200)
    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "idcTransmissionForm [idcTransmissionId = " + this.idcTransmissionId + ",idcTransmissionCode = " + this.idcTransmissionCode + ",note = " + this.note + " ]";
    }


}