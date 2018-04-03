package com.idc.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>test_resouces:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Mar 15,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class TestResouces implements Serializable {
		
	public static final String tableName = "test_resouces";
	    
    @Id@GeneratedValue 	
 	private Integer id; //   
    private String taskid; //   
    private String processInstanceId; //   
 				
	private String gdcode; //    
	 				
	private String usercode; //    
	 				
	private String status; //    
	 				
	private String handuser; //    
	 				
	private String lxr; //    
	 				
	private String email; //    
	 				
	private Date time; //    
	
	private String timeStr; //    [日期格式化后的字符串]
	 	
/************************get set method**************************/
 		public Integer getId() {	    return this.id;	}
 		@Column(name = "id" , nullable =  false)	public void setId(Integer id) {	    this.id=id;	}
 		public String getGdcode() {	    return this.gdcode;	}
 		@Column(name = "gdcode" , nullable =  true, length = 50)	public void setGdcode(String gdcode) {	    this.gdcode=gdcode;	}
 		public String getUsercode() {	    return this.usercode;	}
 		@Column(name = "usercode" , nullable =  true, length = 80)	public void setUsercode(String usercode) {	    this.usercode=usercode;	}
 		public String getStatus() {	    return this.status;	}
 		@Column(name = "status" , nullable =  true, length = 50)	public void setStatus(String status) {	    this.status=status;	}
 		public String getHanduser() {	    return this.handuser;	}
 		@Column(name = "handuser" , nullable =  true, length = 150)	public void setHanduser(String handuser) {	    this.handuser=handuser;	}
 		public String getLxr() {	    return this.lxr;	}
 		@Column(name = "lxr" , nullable =  true, length = 50)	public void setLxr(String lxr) {	    this.lxr=lxr;	}
 		public String getEmail() {	    return this.email;	}
 		@Column(name = "email" , nullable =  true, length = 500)	public void setEmail(String email) {	    this.email=email;	}
 		public Date getTime() {	    return this.time;	}
 		@Column(name = "time" , nullable =  true)	public void setTime(Date time) {	    this.time=time;	}

		public String getTimeStr() {	    return this.timeStr;	}
 		public void setTimeStr(Date time) {		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		String date_ = null;		try {			date_ = sdf.format(time);		} catch (Exception e) {			e.printStackTrace();		}		this.timeStr = date_;	}
	
		@Override	public String toString() {			return  "testResouces [id = "+this.id+",gdcode = "+this.gdcode+",usercode = "+this.usercode+",status = "+this.status+",handuser = "+this.handuser+
		",lxr = "+this.lxr+",email = "+this.email+",time = "+this.time+" ]"; 	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	

 }