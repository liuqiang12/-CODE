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
 * <b>功能：业务表</b>JBPM_TASK_AUTHORIZE:任务授权表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class JbpmTaskAuthorize implements Serializable {
		
	public static final String tableName = "JBPM_TASK_AUTHORIZE";
	    
    @Id@GeneratedValue 	
 	private Integer id; //   ID
 				
	private Long userTaskId; //   USER_TASK_ID
	private Long resourceId; //   resource_id
	 				
	private String targetId; //   通常是角色ID

	private String targetName;//角色名称


/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public Long getUserTaskId() {
	    return this.userTaskId;
	}
 	
	@Column(name = "USER_TASK_ID" , columnDefinition="USER_TASK_ID" , nullable =  false, length = 22)
	public void setUserTaskId(Long userTaskId) {
	    this.userTaskId=userTaskId;
	}
 	
	public String getTargetId() {
	    return this.targetId;
	}
 	
	@Column(name = "TARGET_ID" , columnDefinition="通常是角色ID" , nullable =  false, length = 10)
	public void setTargetId(String targetId) {
	    this.targetId=targetId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
			return  "jbpmTaskAuthorize [id = "+this.id+",userTaskId = "+this.userTaskId+",targetId = "+this.targetId+" ]"; 
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
}