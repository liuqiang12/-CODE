package com.idc.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JbpmResourceModelVo implements Serializable {
	private String id;
	private String parentId;
	 				
	private String name; //   流程名字 
	 				
	private String taskName; //   KEY
	 				
	private String roleName;//角色名称

	private String targetId;//角色ID

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}