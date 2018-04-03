package com.idc.model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_menuinfo:菜单基本信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 21,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class SysMenuinfo implements Serializable {
		
	public static final String tableName = "sys_menuinfo";
	    
    @Id@GeneratedValue 	
 	private Integer id; //   主键
 				
	private String url; //   菜单地址 
	 				
	private Integer enabled; //   激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除 
	 				
	private String name; //   名称 
	 				
	private Integer parentId; //   父级节点id:采用闭包设计方案时parent_id就不适用 
	 				
	private String description; //   描述

    private String icon;
	 				
	private Integer treeType; //   结构类型:0邻接表1路径枚举2嵌套表3闭包表 
	 				
	private Timestamp createTime; //   创建时间 
	
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]

    private Integer sort; //   创建时间 [日期格式化后的字符串]
	 	
	private Set<String> sysRoleinfoTypes;//角色权限中最重要的就是角色类型且是唯一

    private List<SysMenuinfo> submenus = new ArrayList<SysMenuinfo>();//对象子菜单
	
/************************get set method**************************/
 	
	public Integer getId() {
	    return this.id;
	}
 	
	@Column(name = "id" , columnDefinition="主键" , nullable =  false)
	public void setId(Integer id) {
	    this.id=id;
	}
 	
	public String getUrl() {
	    return this.url;
	}
 	
	@Column(name = "url" , columnDefinition="菜单地址" , nullable =  true, length = 200)
	public void setUrl(String url) {
	    this.url=url;
	}
 	
	public Integer getEnabled() {
	    return this.enabled;
	}
 	
	@Column(name = "enabled" , columnDefinition="激活状态:1激活、0未激活、2已锁状态、3可删除、4不可删除" , nullable =  true)
	public void setEnabled(Integer enabled) {
	    this.enabled=enabled;
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

    public Integer getSort() {
        return sort;
    }
    @Column(name = "sort" , columnDefinition="排序")
    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
	public String toString() {
			return  "sysMenuinfo [id = "+this.id+",url = "+this.url+",enabled = "+this.enabled+",name = "+this.name+",parentId = "+this.parentId+
		",description = "+this.description+",treeType = "+this.treeType+",createTime = "+this.createTime+" ]"; 
	}

	public Set<String> getSysRoleinfoTypes() {
		return sysRoleinfoTypes;
	}

	public void setSysRoleinfoTypes(Set<String> sysRoleinfoTypes) {
		this.sysRoleinfoTypes = sysRoleinfoTypes;
	}


 }