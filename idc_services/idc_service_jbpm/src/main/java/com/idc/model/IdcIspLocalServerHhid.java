package com.idc.model;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_LOCAL_SERVER_HHID:上报的客户的服务ID:目的是保存HHID信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcIspLocalServerHhid implements Serializable {
		
	public static final String tableName = "IDC_ISP_LOCAL_SERVER_HHID";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键ID
 				
	private Long fkCustomerId; //   用户外键ID[IDC_ISP_LOCAL_CUSTOMER] 
	 				
	private Long frameinfoId; //   机架ID 
	 	
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
 	
	public Long getFrameinfoId() {
	    return this.frameinfoId;
	}
 	
	@Column(name = "FRAMEINFO_ID" , columnDefinition="机架ID" , nullable =  false, length = 22)
	public void setFrameinfoId(Long frameinfoId) {
	    this.frameinfoId=frameinfoId;
	}

	
	
	@Override
	public String toString() {
			return  "idcIspLocalServerHhid [aid = "+this.aid+",fkCustomerId = "+this.fkCustomerId+",frameinfoId = "+this.frameinfoId+" ]"; 
	}
	

 }