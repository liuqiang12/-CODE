package com.idc.model;

import java.io.Serializable;
import javax.persistence.Column;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>SYS_LN_USER_DEPARTMENT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysLnUserDepartment implements Serializable {

    public static final String tableName = "SYS_LN_USER_DEPARTMENT";

    private Long userId; //   用户ID

    private Long dptId; //   部门ID

    /************************get set method**************************/

    public Long getUserId() {
        return this.userId;
    }

    @Column(name = "USER_ID", columnDefinition = "用户ID", nullable = false, length = 22)
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDptId() {
        return this.dptId;
    }

    @Column(name = "DPT_ID", columnDefinition = "部门ID", nullable = false, length = 22)
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }


    @Override
    public String toString() {
        return "sysLnUserDepartment [userId = " + this.userId + ",dptId = " + this.dptId + " ]";
    }


}