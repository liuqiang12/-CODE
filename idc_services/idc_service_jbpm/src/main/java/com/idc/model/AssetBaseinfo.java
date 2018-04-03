package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>ASSET_BASEINFO:数据字典<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class AssetBaseinfo implements Serializable {
		
	public static final String tableName = "ASSET_BASEINFO";
	    
    @Id@GeneratedValue 	
 	private Long id; //   ID
 				
	private String code; //   CODE 
	 				
	private String name; //   NAME 
	 				
	private String parentCode; //   PARENT_CODE 
	 				
	private String remark; //   备注

	private String label;
	private String value;
	private Boolean selected = Boolean.FALSE;//选中


	 	
/************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public String getCode() {
	    return this.code;
	}
 	
	@Column(name = "CODE" , columnDefinition="CODE" , nullable =  false, length = 12)
	public void setCode(String code) {
	    this.code=code;
	}
 	
	public String getName() {
	    return this.name;
	}
 	
	@Column(name = "NAME" , columnDefinition="NAME" , nullable =  false, length = 400)
	public void setName(String name) {
	    this.name=name;
	}
 	
	public String getParentCode() {
	    return this.parentCode;
	}
 	
	@Column(name = "PARENT_CODE" , columnDefinition="PARENT_CODE" , nullable =  false, length = 12)
	public void setParentCode(String parentCode) {
	    this.parentCode=parentCode;
	}
 	
	public String getRemark() {
	    return this.remark;
	}
 	
	@Column(name = "REMARK" , columnDefinition="备注" , nullable =  false, length = 1020)
	public void setRemark(String remark) {
	    this.remark=remark;
	}

	@Override
	public String toString() {
			return  "assetBaseinfo [id = "+this.id+",code = "+this.code+",name = "+this.name+",parentCode = "+this.parentCode+",remark = "+this.remark+
		" ]"; 
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}