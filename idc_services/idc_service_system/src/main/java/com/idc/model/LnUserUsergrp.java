package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_user_usergrp:用户与用户组关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LnUserUsergrp implements Serializable {
		
	public static final String tableName = "sys_ln_user_usergrp";
	    
    @Id@GeneratedValue 	
 	private Integer userId; //   用户ID
 		    
    @Id@GeneratedValue 	
 	private Integer ugrpId; //   用户组ID
 	
/************************get set method**************************/
 	
	public Integer getUserId() {
	    return this.userId;
	}
 	
	@Column(name = "user_id" , columnDefinition="用户ID" , nullable =  false)
	public void setUserId(Integer userId) {
	    this.userId=userId;
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
			return  "lnUserUsergrp [userId = "+this.userId+",ugrpId = "+this.ugrpId+" ]"; 
	}
	

 }