package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_usergrp_role:用户组和角色关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LnUsergrpRole implements Serializable {
		
	public static final String tableName = "sys_ln_usergrp_role";
	    
    @Id@GeneratedValue 	
 	private Integer roleId; //   角色ID
 		    
    @Id@GeneratedValue 	
 	private Integer ugrpId; //   用户组ID
 	
/************************get set method**************************/
 	
	public Integer getRoleId() {
	    return this.roleId;
	}
 	
	@Column(name = "role_id" , columnDefinition="角色ID" , nullable =  false)
	public void setRoleId(Integer roleId) {
	    this.roleId=roleId;
	}
 	
	public Integer getUgrpId() {
	    return this.ugrpId;
	}
 	
	@Column(name = "ugrp_id" , columnDefinition="用户组ID" , nullable =  false)
	public void setUgrpId(Integer ugrpId) {
	    this.ugrpId=ugrpId;
	}

	
	
	@Override
	public String toString() {
			return  "lnUsergrpRole [roleId = "+this.roleId+",ugrpId = "+this.ugrpId+" ]"; 
	}
	

 }