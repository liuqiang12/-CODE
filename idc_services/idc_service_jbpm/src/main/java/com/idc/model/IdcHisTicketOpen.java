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
 * <b>功能：业务表</b>IDC_HIS_TICKET_OPEN:历史开通工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcHisTicketOpen implements Serializable {
		
	public static final String tableName = "IDC_HIS_TICKET_OPEN";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Long ticketInstId; //   工单实例ID 
	 				
	private String applydesc; //   备注[申请描述] 
	 				
	private String occupycomment; //   施工描述 
	 				
	private Integer rev; //   版本号 
	 				
	private Timestamp createTime; //   创建时间 
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public Long getTicketInstId() {
	    return this.ticketInstId;
	}
 	
	@Column(name = "TICKET_INST_ID" , columnDefinition="工单实例ID" , nullable =  false, length = 22)
	public void setTicketInstId(Long ticketInstId) {
	    this.ticketInstId=ticketInstId;
	}
 	
	public String getApplydesc() {
	    return this.applydesc;
	}
 	
	@Column(name = "APPLYDESC" , columnDefinition="备注[申请描述]" , nullable =  false, length = 512)
	public void setApplydesc(String applydesc) {
	    this.applydesc=applydesc;
	}
 	
	public String getOccupycomment() {
	    return this.occupycomment;
	}
 	
	@Column(name = "OCCUPYCOMMENT" , columnDefinition="施工描述" , nullable =  false, length = 512)
	public void setOccupycomment(String occupycomment) {
	    this.occupycomment=occupycomment;
	}
 	
	public Integer getRev() {
	    return this.rev;
	}
 	
	@Column(name = "REV" , columnDefinition="版本号" , nullable =  false, length = 22)
	public void setRev(Integer rev) {
	    this.rev=rev;
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
			return  "idcHisTicketOpen [id = "+this.id+",ticketInstId = "+this.ticketInstId+",applydesc = "+this.applydesc+",occupycomment = "+this.occupycomment+",rev = "+this.rev+
		",createTime = "+this.createTime+" ]"; 
	}
	

 }