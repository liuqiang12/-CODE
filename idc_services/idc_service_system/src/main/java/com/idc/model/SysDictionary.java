package com.idc.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_dictionary:系统业务字典表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 07,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysDictionary implements Serializable {
		
	public static final String tableName = "sys_dictionary";
	    
    @Id@GeneratedValue 	
 	private Integer id; //   主键
 				
	private Integer pid; //   父id 
	 				
	private String code; //   编码 :[是固定的]
	 				
	private String name; //   名字 
	 				
	private String desc; //   描述 
	
	private Boolean selected = false;//默认被选中
	 	
/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "id" , columnDefinition="主键" , nullable =  false)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public Integer getPid() {
	    return this.pid;
	}
 	
	@Column(name = "pid" , columnDefinition="父id" , nullable =  true)
	public void setPid(Integer pid) {
	    this.pid=pid;
	}
 	
	public String getCode() {
	    return this.code;
	}
 	
	@Column(name = "code" , columnDefinition="编码" , nullable =  true, length = 80)
	public void setCode(String code) {
	    this.code=code;
	}
 	
	public String getName() {
	    return this.name;
	}
 	
	@Column(name = "name" , columnDefinition="名字" , nullable =  true, length = 50)
	public void setName(String name) {
	    this.name=name;
	}
 	
	public String getDesc() {
	    return this.desc;
	}
 	
	@Column(name = "desc" , columnDefinition="描述" , nullable =  true, length = 100)
	public void setDesc(String desc) {
	    this.desc=desc;
	}

	
	
	@Override
	public String toString() {
			return  "sysDictionary [id = "+this.id+",pid = "+this.pid+",code = "+this.code+",name = "+this.name+",desc = "+this.desc+
		" ]"; 
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	

 }