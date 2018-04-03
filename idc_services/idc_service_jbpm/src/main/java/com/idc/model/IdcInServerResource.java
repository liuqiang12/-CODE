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
 * <b>功能：业务表</b>IDC_IN_SERVER_RESOURCE:在服资源<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 09,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcInServerResource implements Serializable {
		
	public static final String tableName = "IDC_IN_SERVER_RESOURCE";
	    
    @Id@GeneratedValue 	
 	private Long id; //   主键
 				
	private Long resourceId; //   资源ID 
	 				
	private String resourceCategory; //   资源类型 
	 				
	private Long ticketInstId; //   工单实例ID 
	 				
	private Long prodInstId; //   产品实例ID  PROD_INST_ID 
	 				
	private Timestamp createTime; //   创建时间 
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="主键" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public Long getResourceId() {
	    return this.resourceId;
	}
 	
	@Column(name = "RESOURCE_ID" , columnDefinition="资源ID" , nullable =  false, length = 22)
	public void setResourceId(Long resourceId) {
	    this.resourceId=resourceId;
	}
 	
	public String getResourceCategory() {
	    return this.resourceCategory;
	}
 	
	@Column(name = "RESOURCE_CATEGORY" , columnDefinition="资源类型" , nullable =  false, length = 3)
	public void setResourceCategory(String resourceCategory) {
	    this.resourceCategory=resourceCategory;
	}
 	
	public Long getTicketInstId() {
	    return this.ticketInstId;
	}
 	
	@Column(name = "TICKET_INST_ID" , columnDefinition="工单实例ID" , nullable =  false, length = 22)
	public void setTicketInstId(Long ticketInstId) {
	    this.ticketInstId=ticketInstId;
	}
 	
	public Long getProdInstId() {
	    return this.prodInstId;
	}
 	
	@Column(name = "PROD_INST_ID" , columnDefinition="产品实例ID  PROD_INST_ID" , nullable =  false, length = 22)
	public void setProdInstId(Long prodInstId) {
	    this.prodInstId=prodInstId;
	}
 	
	public Timestamp getCreateTime() {
	    return this.createTime;
	}
 	
	@Column(name = "CREATE_TIME" , columnDefinition="创建时间" , nullable =  false, length = 11)
	public void setCreateTime(Timestamp createTime) {
	    this.createTime=createTime;
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
			return  "idcInServerResource [id = "+this.id+",resourceId = "+this.resourceId+",resourceCategory = "+this.resourceCategory+",ticketInstId = "+this.ticketInstId+",prodInstId = "+this.prodInstId+
		",createTime = "+this.createTime+" ]"; 
	}
	

 }