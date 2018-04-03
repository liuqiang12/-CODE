package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_LOCAL_IP:IDC_ISP_LOCAL_IP<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcIspLocalIp implements Serializable {
		
	public static final String tableName = "IDC_ISP_LOCAL_IP";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键ID
 				
	private Long fkHhidId; //   IDC_ISP_LOCAL_SERVER_HHID 
	 	
/************************get set method**************************/
 	
	public Long getAid() {
	    return this.aid;
	}
 	
	@Column(name = "AID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setAid(Long aid) {
	    this.aid=aid;
	}
 	
	public Long getFkHhidId() {
	    return this.fkHhidId;
	}
 	
	@Column(name = "FK_HHID_ID" , columnDefinition="IDC_ISP_LOCAL_SERVER_HHID" , nullable =  false, length = 22)
	public void setFkHhidId(Long fkHhidId) {
	    this.fkHhidId=fkHhidId;
	}

	
	
	@Override
	public String toString() {
			return  "idcIspLocalIp [aid = "+this.aid+",fkHhidId = "+this.fkHhidId+" ]"; 
	}
	

 }