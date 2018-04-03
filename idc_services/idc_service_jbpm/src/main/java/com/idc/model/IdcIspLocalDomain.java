package com.idc.model;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_LOCAL_DOMAIN:上报的客户的域名情况<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcIspLocalDomain implements Serializable {
		
	public static final String tableName = "IDC_ISP_LOCAL_DOMAIN";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键ID
 				
	private Long fkCustomerId; //   用户外键ID[IDC_ISP_LOCAL_CUSTOMER] 
	 	
/************************get set method**************************/
 	
	public Long getAid() {
	    return this.aid;
	}
 	
	@Column(name = "AID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setAid(Long aid) {
	    this.aid=aid;
	}
 	
	public Long getFkCustomerId() {
	    return this.fkCustomerId;
	}
 	
	@Column(name = "FK_CUSTOMER_ID" , columnDefinition="用户外键ID[IDC_ISP_LOCAL_CUSTOMER]" , nullable =  false, length = 22)
	public void setFkCustomerId(Long fkCustomerId) {
	    this.fkCustomerId=fkCustomerId;
	}

	
	
	@Override
	public String toString() {
			return  "idcIspLocalDomain [aid = "+this.aid+",fkCustomerId = "+this.fkCustomerId+" ]"; 
	}
	

 }