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
 * <b>功能：业务表</b>IDC_RUN_TICKET_COMMNET:运行时当前工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcRunTicketCommnet implements Serializable {
		
	public static final String tableName = "IDC_RUN_TICKET_COMMNET";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Long ticketInstId; //   工单实例ID 
	 				
	private Integer satisfaction; //   满意度 
	 				
	private String feedback; //   反馈意见 
	 				
	private Integer rev; //   版本号 
	 				
	private Date createTime; //   创建时间
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]

	private String remark;     //资源备注



	/************************get set method**************************/

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
 	
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
 	
	public Integer getSatisfaction() {
	    return this.satisfaction;
	}
 	
	@Column(name = "SATISFACTION" , columnDefinition="满意度" , nullable =  false, length = 22)
	public void setSatisfaction(Integer satisfaction) {
	    this.satisfaction=satisfaction;
	}
 	
	public String getFeedback() {
	    return this.feedback;
	}
 	
	@Column(name = "FEEDBACK" , columnDefinition="反馈意见" , nullable =  false, length = 512)
	public void setFeedback(String feedback) {
	    this.feedback=feedback;
	}
 	
	public Integer getRev() {
	    return this.rev;
	}
 	
	@Column(name = "REV" , columnDefinition="版本号" , nullable =  false, length = 22)
	public void setRev(Integer rev) {
	    this.rev=rev;
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


	@Override
	public String toString() {
		return "IdcRunTicketCommnet{" +
				"id=" + id +
				", ticketInstId=" + ticketInstId +
				", satisfaction=" + satisfaction +
				", feedback='" + feedback + '\'' +
				", rev=" + rev +
				", createTime=" + createTime +
				", createTimeStr='" + createTimeStr + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}