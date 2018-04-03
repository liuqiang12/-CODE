package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_user_role:用户角色关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LnUserRole implements Serializable {
		
	public static final String tableName = "sys_ln_user_role";
	    
    @Id@GeneratedValue 	
 	private Integer userId; //   用户ID
 		    
    @Id@GeneratedValue 	
 	private Integer roleId; //   角色id
 	
/************************get set method**************************/
 	
	public Integer getUserId() {
	    return this.userId;
	}
 	
	@Column(name = "user_id" , columnDefinition="用户ID" , nullable =  false)
	public void setUserId(Integer userId) {
	    this.userId=userId;
	}
 	
	public Integer getRoleId() {
	    return this.roleId;
	}
 	
	@Column(name = "role_id" , columnDefinition="角色id" , nullable =  false)
	public void setRoleId(Integer roleId) {
	    this.roleId=roleId;
	}

	
	
	@Override
	public String toString() {
			return  "lnUserRole [userId = "+this.userId+",roleId = "+this.roleId+" ]"; 
	}
	

 }