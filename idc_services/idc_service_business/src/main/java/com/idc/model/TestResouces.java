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
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	

	
 	
	
	
		",lxr = "+this.lxr+",email = "+this.email+",time = "+this.time+" ]"; 

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