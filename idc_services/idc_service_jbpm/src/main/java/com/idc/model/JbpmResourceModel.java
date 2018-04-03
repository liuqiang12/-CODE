package com.idc.model;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>JBPM_RESOURCE_MODEL:JBPM_RESOURCE_MODEL<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class JbpmResourceModel implements Serializable {
		
	public static final String tableName = "JBPM_RESOURCE_MODEL";
	    
    @Id@GeneratedValue 	
 	private Long id; //   主键ID
 				
	private String modelId; //   MODEL_ID 
	 				
	private String name; //   流程名字 
	 				
	private String key; //   KEY 
	 				
	private Long editorSourceValueId; //   EDITOR_SOURCE_VALUE_ID 
	 				
	private Long editorSourceExtraValueId; //   EDITOR_SOURCE_EXTRA_VALUE_ID
	List<JbpmResourceChildshape> jbpmResourceChildshapes;//基本信息

	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public String getModelId() {
	    return this.modelId;
	}
 	
	@Column(name = "MODEL_ID" , columnDefinition="MODEL_ID" , nullable =  false, length = 80)
	public void setModelId(String modelId) {
	    this.modelId=modelId;
	}
 	
	public String getName() {
	    return this.name;
	}
 	
	@Column(name = "NAME" , columnDefinition="流程名字" , nullable =  false, length = 480)
	public void setName(String name) {
	    this.name=name;
	}
 	
	public String getKey() {
	    return this.key;
	}
 	
	@Column(name = "KEY" , columnDefinition="KEY" , nullable =  false, length = 400)
	public void setKey(String key) {
	    this.key=key;
	}
 	
	public Long getEditorSourceValueId() {
	    return this.editorSourceValueId;
	}
 	
	@Column(name = "EDITOR_SOURCE_VALUE_ID" , columnDefinition="EDITOR_SOURCE_VALUE_ID" , nullable =  false, length = 22)
	public void setEditorSourceValueId(Long editorSourceValueId) {
	    this.editorSourceValueId=editorSourceValueId;
	}
 	
	public Long getEditorSourceExtraValueId() {
	    return this.editorSourceExtraValueId;
	}
 	
	@Column(name = "EDITOR_SOURCE_EXTRA_VALUE_ID" , columnDefinition="EDITOR_SOURCE_EXTRA_VALUE_ID" , nullable =  false, length = 22)
	public void setEditorSourceExtraValueId(Long editorSourceExtraValueId) {
	    this.editorSourceExtraValueId=editorSourceExtraValueId;
	}

	
	
	@Override
	public String toString() {
			return  "jbpmResourceModel [id = "+this.id+",modelId = "+this.modelId+",name = "+this.name+",key = "+this.key+",editorSourceValueId = "+this.editorSourceValueId+
		",editorSourceExtraValueId = "+this.editorSourceExtraValueId+" ]"; 
	}
	

 }