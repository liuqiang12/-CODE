package com.idc.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_IP_MODEL:IP租用产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcReIpModel implements Serializable {
		
	public static final String tableName = "IDC_RE_IP_MODEL";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Integer rev; //   版本号 
	 				
	private String portMode; //   IP性质:1静态,2动态;3保留 
	 				
	private Long num; //   数量 
	 				
	private String desc; //   描述 

	private Long ticketInstId;//工单实例
	private Date createTime; //   创建时间
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	 	
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
 	
	public String getPortMode() {
	    return this.portMode;
	}
 	
	@Column(name = "PORT_MODE" , columnDefinition="IP性质:1静态,2动态;3保留" , nullable =  false, length = 2)
	public void setPortMode(String portMode) {
	    this.portMode=portMode;
	}
 	
	public Long getNum() {
	    return this.num;
	}
 	
	@Column(name = "NUM" , columnDefinition="数量" , nullable =  false, length = 22)
	public void setNum(Long num) {
	    this.num=num;
	}
 	
	public String getDesc() {
	    return this.desc;
	}
 	
	@Column(name = "DESC" , columnDefinition="描述" , nullable =  false, length = 510)
	public void setDesc(String desc) {
	    this.desc=desc;
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
			return  "idcReIpModel [id = "+this.id+",rev = "+this.rev+",portMode = "+this.portMode+",num = "+this.num+",desc = "+this.desc+
		",createTime = "+this.createTime+" ]"; 
	}

	public Long getTicketInstId() {
		return ticketInstId;
	}

	public void setTicketInstId(Long ticketInstId) {
		this.ticketInstId = ticketInstId;
	}
}