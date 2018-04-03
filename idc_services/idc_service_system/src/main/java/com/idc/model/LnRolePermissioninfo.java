package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_role_permissioninfo:角色权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LnRolePermissioninfo implements Serializable {
		
	public static final String tableName = "sys_ln_role_permissioninfo";
	    
    @Id@GeneratedValue 	
 	private Integer permitId; //   权限ID
 		    
    @Id@GeneratedValue 	
 	private Integer roleId; //   角色ID
 	
/************************get set method**************************/
 	
	public Integer getPermitId() {
	    return this.permitId;
	}
 	
	@Column(name = "permit_id" , columnDefinition="权限ID" , nullable =  false)
	public void setPermitId(Integer permitId) {
	    this.permitId=permitId;
	}
 	
	public Integer getRoleId() {
	    return this.roleId;
	}
 	
	@Column(name = "role_id" , columnDefinition="角色ID" , nullable =  false)
	public void setRoleId(Integer roleId) {
	    this.roleId=roleId;
	}

	
	
	@Override
	public String toString() {
			return  "lnRolePermissioninfo [permitId = "+this.permitId+",roleId = "+this.roleId+" ]"; 
	}
	

 }