package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_role_data_permission:角色数据权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysLnRoleDataPermission implements Serializable {
		
	public static final String tableName = "sys_ln_role_data_permission";
	    
    @Id@GeneratedValue 	
 	private Integer userId; //   使用对象ID   可以是角色ID 用户Id 组ID  当前默认角色
 				
	private Integer userType=0; //   0 角色 1 用户  2 组。。。
	 				
	private Integer permType=0; //   0 区域 1 机构 2 部门.。。
	 		    
    @Id@GeneratedValue 	
 	private Integer permId; //   
 	
/************************get set method**************************/
 	
	public Integer getUserId() {
	    return this.userId;
	}
 	
	@Column(name = "user_id" , columnDefinition="使用对象ID   可以是角色ID 用户Id 组ID  当前默认角色" , nullable =  false)
	public void setUserId(Integer userId) {
	    this.userId=userId;
	}
 	
	public Integer getUserType() {
	    return this.userType;
	}
 	
	@Column(name = "user_type" , columnDefinition="0 角色 1 用户  2 组。。。" , nullable =  true)
	public void setUserType(Integer userType) {
	    this.userType=userType;
	}
 	
	public Integer getPermType() {
	    return this.permType;
	}
 	
	@Column(name = "perm_type" , columnDefinition="0 区域 1 机构 2 部门.。。" , nullable =  true)
	public void setPermType(Integer permType) {
	    this.permType=permType;
	}
 	
	public Integer getPermId() {
	    return this.permId;
	}
 	
	@Column(name = "perm_id" , nullable =  false)
	public void setPermId(Integer permId) {
	    this.permId=permId;
	}

	
	
	@Override
	public String toString() {
			return  "sysLnRoleDataPermission [userId = "+this.userId+",userType = "+this.userType+",permType = "+this.permType+",permId = "+this.permId+" ]"; 
	}
	

 }