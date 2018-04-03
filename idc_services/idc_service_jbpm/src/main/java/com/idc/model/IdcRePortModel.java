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
 * <b>功能：业务表</b>IDC_RE_PORT_MODEL:IDC_RE_PORT_MODEL端口带宽产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcRePortModel implements Serializable {
		
	public static final String tableName = "IDC_RE_PORT_MODEL";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Integer rev; //   版本号 
	 				
	private String portMode; //   带宽模式类别1:B类独享 
	 				
	private Long bandwidth; //   带宽1:10M,2:100M,3:10/100BASET,4:100/1000BASET,5:10/100/1000BASET,6:1G,7:1.25G,8:2.5G,9:10G,10:40G,11:100G,12:250GE,......STM-256
	 				
	private Long num; //   数量

	private Long ticketInstId;//工单实例
	 				
	private String desc; //   描述 
	 				
	private Date createTime; //   创建时间
	private String spec;
	
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
 	
	@Column(name = "PORT_MODE" , columnDefinition="带宽模式类别1:B类独享" , nullable =  false, length = 2)
	public void setPortMode(String portMode) {
	    this.portMode=portMode;
	}
 	
	public Long getBandwidth() {
	    return this.bandwidth;
	}
 	
	@Column(name = "BANDWIDTH" , columnDefinition="带宽1:10M,2:100M,3:10/100BASET,4:100/1000BASET,5:10/100/1000BASET,6:1G,7:1.25G,8:2.5G,9:10G,10:40G,11:100G,12:250GE,......STM-256" , nullable =  false, length = 22)
	public void setBandwidth(Long bandwidth) {
	    this.bandwidth=bandwidth;
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
			return  "idcRePortModel [id = "+this.id+",rev = "+this.rev+",portMode = "+this.portMode+",bandwidth = "+this.bandwidth+",num = "+this.num+
		",desc = "+this.desc+",createTime = "+this.createTime+" ]"; 
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Long getTicketInstId() {
		return ticketInstId;
	}

	public void setTicketInstId(Long ticketInstId) {
		this.ticketInstId = ticketInstId;
	}
}