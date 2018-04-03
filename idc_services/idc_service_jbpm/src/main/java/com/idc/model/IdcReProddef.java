package com.idc.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_PRODDEF:产品模型分类表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcReProddef implements Serializable {
		
	public static final String tableName = "IDC_RE_PRODDEF";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Integer rev; //   版本号 
	 				
	private String category; //   100:机架机位RACK/200端口带宽出租PORT/300IP租用IP/400主机租赁SERVER/500增值业务ADD
	 				
	private Long prodInstId; //   产品实例ID  PROD_INST_ID 
	 				
	private Date createTime; //   创建时间
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	private Long ticketInstId;//工单实例ID
	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public Integer getRev() {
	    return this.rev;
	}
 	
	@Column(name = "REV" , columnDefinition="版本号" , nullable =  false, length = 22)
	public void setRev(Integer rev) {
	    this.rev=rev;
	}
 	
	public String getCategory() {
	    return this.category;
	}
 	
	@Column(name = "CATEGORY" , columnDefinition="100:机架机位RACK/200端口带宽出租PORT/300IP租用IP/400主机租赁SERVER/500增值业务ADD" , nullable =  false, length = 6)
	public void setCategory(String category) {
	    this.category=category;
	}
 	
	public Long getProdInstId() {
	    return this.prodInstId;
	}
 	
	@Column(name = "PROD_INST_ID" , columnDefinition="产品实例ID  PROD_INST_ID" , nullable =  false, length = 22)
	public void setProdInstId(Long prodInstId) {
	    this.prodInstId=prodInstId;
	}
 	
	public Date getCreateTime() {
	    return this.createTime;
	}
 	
	@Column(name = "CREATE_TIME" , columnDefinition="创建时间" , nullable =  false, length = 11)
	public void setCreateTime(Date createTime) {
	    this.createTime=createTime;
	}

	
	public String getCreateTimeStr() {
	    return this.createTimeStr;
	}
 	
	public void setCreateTimeStr(Date createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date_ = null;
		try {
			date_ = sdf.format(createTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.createTimeStr = date_;
	}

	public Long getTicketInstId() {
		return ticketInstId;
	}

	public void setTicketInstId(Long ticketInstId) {
		this.ticketInstId = ticketInstId;
	}

	@Override
	public String toString() {
			return  "idcReProddef [id = "+this.id+",rev = "+this.rev+",category = "+this.category+",prodInstId = "+this.prodInstId+",createTime = "+this.createTime+
		" ]"; 
	}
	

 }