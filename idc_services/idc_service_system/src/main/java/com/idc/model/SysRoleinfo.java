package com.idc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_roleinfo:系统角色信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 21,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysRoleinfo  implements GrantedAuthority {
		
	public static final String tableName = "sys_roleinfo";
	
	public static String PREFIX = "";//CodeResourceUtil.getRolePrefix();
	    
    @Id@GeneratedValue 	
 	private Integer id; //   主键
	private Integer roleId;//
	private Integer userId;//用户ID

 				
	private Integer enabled; //   激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除 
	 				
	private String type; //   角色类型:USER/DBA/ADMIN/ROOT等必须唯一 
	 				
	private String name; //   名称 
	 				
	private Integer parentId; //   父级节点id:采用闭包设计方案时parent_id就不适用 
	 				
	private String description; //   描述

	private String role_key; //   新增字段
	 				
	private Integer treeType; //   结构类型:0邻接表1路径枚举2嵌套表3闭包表 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Timestamp createTime; //   创建时间 
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	 	
/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "id" , columnDefinition="主键" , nullable =  false)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public Integer getEnabled() {
	    return this.enabled;
	}
 	
	@Column(name = "enabled" , columnDefinition="激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除" , nullable =  true)
	public void setEnabled(Integer enabled) {
	    this.enabled=enabled;
	}
 	
	public String getType() {
	    return this.type;
	}
 	
	@Column(name = "type" , columnDefinition="角色类型:USER/DBA/ADMIN/ROOT等必须唯一" , nullable =  true, length = 10)
	public void setType(String type) {
	    this.type=type;
	}
 	
	public String getName() {
	    return this.name;
	}
 	
	@Column(name = "name" , columnDefinition="名称" , nullable =  true, length = 200)
	public void setName(String name) {
	    this.name=name;
	}
 	
	public Integer getParentId() {
	    return this.parentId;
	}
 	
	@Column(name = "parent_id" , columnDefinition="父级节点id:采用闭包设计方案时parent_id就不适用" , nullable =  true)
	public void setParentId(Integer parentId) {
	    this.parentId=parentId;
	}
 	
	public String getDescription() {
	    return this.description;
	}
 	
	@Column(name = "description" , columnDefinition="描述" , nullable =  true, length = 300)
	public void setDescription(String description) {
	    this.description=description;
	}
 	
	public Integer getTreeType() {
	    return this.treeType;
	}
 	
	@Column(name = "tree_type" , columnDefinition="结构类型:0邻接表1路径枚举2嵌套表3闭包表" , nullable =  true)
	public void setTreeType(Integer treeType) {
	    this.treeType=treeType;
	}
 	
	public Timestamp getCreateTime() {
	    return this.createTime;
	}
 	
	@Column(name = "create_time" , columnDefinition="创建时间" , nullable =  false)
	public void setCreateTime(Timestamp createTime) {
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
			return  "sysRoleinfo [id = "+this.id+",enabled = "+this.enabled+",type = "+this.type+",name = "+this.name+",parentId = "+this.parentId+
                    ",description = " + this.description + ",treeType = " + this.treeType + ",createTime = " + this.createTime + ",roleKey=" + this.role_key + " ]";
    }
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return PREFIX + this.type;
	}
	 /** 
     * 初始化用户权限 
     *@author baozhichao 2013-12-19下午5:46:36     
     * @return 
     */  
    public static SysRoleinfo getRoleUser(){  
    	SysRoleinfo ro = new SysRoleinfo();  
        ro.setType("USER");  
        ro.setName("普通用户");  
        return ro;  
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRole_key() {
		return role_key;
	}

	public void setRole_key(String role_key) {
		this.role_key = role_key;
	}
}