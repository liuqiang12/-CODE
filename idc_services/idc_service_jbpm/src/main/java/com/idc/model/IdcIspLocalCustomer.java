package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_LOCAL_CUSTOMER:上报的客户表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcIspLocalCustomer implements Serializable {
		
	public static final String tableName = "IDC_ISP_LOCAL_CUSTOMER";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键ID
 				
	private Long randomServerId; //   随机服务ID: 
	 	
/************************get set method**************************/
 	
	public Long getAid() {
	    return this.aid;
	}
 	
	@Column(name = "AID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setAid(Long aid) {
	    this.aid=aid;
	}
 	
	public Long getRandomServerId() {
	    return this.randomServerId;
	}
 	
	@Column(name = "RANDOM_SERVER_ID" , columnDefinition="随机服务ID:" , nullable =  false, length = 22)
	public void setRandomServerId(Long randomServerId) {
	    this.randomServerId=randomServerId;
	}

	
	
	@Override
	public String toString() {
			return  "idcIspLocalCustomer [aid = "+this.aid+",randomServerId = "+this.randomServerId+" ]"; 
	}
	

 }