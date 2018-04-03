package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>JBPM_RESOURCE_CHILDSHAPE:JBPM_RESOURCE_CHILDSHAPE<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class JbpmResourceChildshape implements Serializable {
		
	public static final String tableName = "JBPM_RESOURCE_CHILDSHAPE";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private Long resourceId; //   RESOURCE_ID 
	 				
	private String taskId; //   VARCHA 
	 				
	private String taskName; //   TASK_NAME

	private String formKey;//formkey
	private List<String> groups;
	private List<JbpmTaskAuthorize> jbpmTaskAuthorizes;//角色信息
	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public Long getResourceId() {
	    return this.resourceId;
	}
 	
	@Column(name = "RESOURCE_ID" , columnDefinition="RESOURCE_ID" , nullable =  false, length = 22)
	public void setResourceId(Long resourceId) {
	    this.resourceId=resourceId;
	}
 	
	public String getTaskId() {
	    return this.taskId;
	}
 	
	@Column(name = "TASK_ID" , columnDefinition="VARCHA" , nullable =  false, length = 400)
	public void setTaskId(String taskId) {
	    this.taskId=taskId;
	}
 	
	public String getTaskName() {
	    return this.taskName;
	}
 	
	@Column(name = "TASK_NAME" , columnDefinition="TASK_NAME" , nullable =  false, length = 600)
	public void setTaskName(String taskName) {
	    this.taskName=taskName;
	}

	
	
	@Override
	public String toString() {
			return  "jbpmResourceChildshape [id = "+this.id+",resourceId = "+this.resourceId+",taskId = "+this.taskId+",taskName = "+this.taskName+" ]"; 
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}