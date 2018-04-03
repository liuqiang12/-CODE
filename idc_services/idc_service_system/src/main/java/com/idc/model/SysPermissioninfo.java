package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_permissioninfo:系统权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")

public class SysPermissioninfo implements Serializable {

    public static final String tableName = "sys_permissioninfo";

    @Id
    @GeneratedValue
    private Integer id; //

    private String permissionType; //   权限类型:1000菜单权限;1001功能模块的操作权限;1002文件的修改权限;1003页面元素的可见性控制

    private Timestamp createTime; //   创建时间

    private String createTimeStr; //   创建时间 [日期格式化后的字符串]

    private String name;


    /************************
     * get set method
     **************************/

    public Integer getId() {
        return this.id;
    }

    @Column(name = "id", nullable = false)
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionType() {
        return this.permissionType;
    }

    @Column(name = "permission_type", columnDefinition = "权限类型:1000菜单权限;1001功能模块的操作权限;1002文件的修改权限;1003页面元素的可见性控制", nullable = true, length = 100)
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    @Column(name = "create_time", columnDefinition = "创建时间", nullable = false)
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(Timestamp createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(createTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.createTimeStr = date_;
    }


    @Override
    public String toString() {
        return "sysPermissioninfo [id = " + this.id + ",permissionType = " + this.permissionType + ",createTime = " + this.createTime + " ]";
    }


}
