package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>RM_TMP_REGISTER:登录人员表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class RmTmpRegister implements Serializable {

    public static final String tableName = "RM_TMP_REGISTER";

    @Id
    @GeneratedValue
    private String rmTmpRegisterId; //   临时登记人ID

    private String rmTmpInOutId; //   临时出入主键(外键)

    private String rmTmpRegisterName; //   姓名

    private String rmTmpRegisterCard; //   证件号

    private String rmTmpRegisterPhone; //   电话

    private String rmIsFingerPrint; //   是否录过指纹

    /************************get set method**************************/

    public String getRmTmpInOutId() {
        return this.rmTmpInOutId;
    }

    @Column(name = "RM_TMP_IN_OUT_ID", columnDefinition = "临时出入主键", nullable = false, length = 100)
    public void setRmTmpInOutId(String rmTmpInOutId) {
        this.rmTmpInOutId = rmTmpInOutId;
    }

    public String getRmTmpRegisterId() {
        return this.rmTmpRegisterId;
    }

    @Column(name = "RM_TMP_REGISTER_ID", columnDefinition = "临时登记人ID", nullable = false, length = 32)
    public void setRmTmpRegisterId(String rmTmpRegisterId) {
        this.rmTmpRegisterId = rmTmpRegisterId;
    }

    public String getRmTmpRegisterName() {
        return this.rmTmpRegisterName;
    }

    @Column(name = "RM_TMP_REGISTER_NAME", columnDefinition = "姓名", nullable = false, length = 32)
    public void setRmTmpRegisterName(String rmTmpRegisterName) {
        this.rmTmpRegisterName = rmTmpRegisterName;
    }

    public String getRmTmpRegisterCard() {
        return this.rmTmpRegisterCard;
    }

    @Column(name = "RM_TMP_REGISTER_CARD", columnDefinition = "证件号", nullable = false, length = 20)
    public void setRmTmpRegisterCard(String rmTmpRegisterCard) {
        this.rmTmpRegisterCard = rmTmpRegisterCard;
    }

    public String getRmTmpRegisterPhone() {
        return this.rmTmpRegisterPhone;
    }

    @Column(name = "RM_TMP_REGISTER_PHONE", columnDefinition = "电话", nullable = false, length = 16)
    public void setRmTmpRegisterPhone(String rmTmpRegisterPhone) {
        this.rmTmpRegisterPhone = rmTmpRegisterPhone;
    }

    public String getRmIsFingerPrint() {
        return this.rmIsFingerPrint;
    }

    @Column(name = "RM_IS_FINGER_PRINT", columnDefinition = "是否录过指纹", nullable = false, length = 4)
    public void setRmIsFingerPrint(String rmIsFingerPrint) {
        this.rmIsFingerPrint = rmIsFingerPrint;
    }


    @Override
    public String toString() {
        return "rmTmpRegister [rmTmpInOutId = " + this.rmTmpInOutId + ",rmTmpRegisterId = " + this.rmTmpRegisterId + ",rmTmpRegisterName = " + this.rmTmpRegisterName + ",rmTmpRegisterCard = " + this.rmTmpRegisterCard + ",rmTmpRegisterPhone = " + this.rmTmpRegisterPhone +
                ",rmIsFingerPrint = " + this.rmIsFingerPrint + " ]";
    }


}