package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>NET_TOPOVIEWOBJ:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class NetTopoViewObj implements Serializable {
		
	public static final String tableName = "NET_TOPOVIEWOBJ";
	    
    @Id@GeneratedValue 	
 	private Long objpid; //   
 				
	private String icon; //    
	 				
	private Long objfid; //    
	 				
	private Long objtype; //    
	 				
	private Long subviewid; //    
	 				
	private Long viewid; //    
	 				
	private String viewposition; //

    private String name;

	private String ext;
	 	
/************************get set method**************************/
 	
	public Long getObjpid() {
	    return this.objpid;
	}
 	
	@Column(name = "OBJPID" , nullable =  false, length = 22)
	public void setObjpid(Long objpid) {
	    this.objpid=objpid;
	}
 	
	public String getIcon() {
	    return this.icon;
	}
 	
	@Column(name = "ICON" , nullable =  false, length = 765)
	public void setIcon(String icon) {
	    this.icon=icon;
	}
 	
	public Long getObjfid() {
	    return this.objfid;
	}
 	
	@Column(name = "OBJFID" , nullable =  false, length = 22)
	public void setObjfid(Long objfid) {
	    this.objfid=objfid;
	}
 	
	public Long getObjtype() {
	    return this.objtype;
	}
 	
	@Column(name = "OBJTYPE" , nullable =  false, length = 22)
	public void setObjtype(Long objtype) {
	    this.objtype=objtype;
	}
 	
	public Long getSubviewid() {
	    return this.subviewid;
	}
 	
	@Column(name = "SUBVIEWID" , nullable =  false, length = 22)
	public void setSubviewid(Long subviewid) {
	    this.subviewid=subviewid;
	}
 	
	public Long getViewid() {
	    return this.viewid;
	}
 	
	@Column(name = "VIEWID" , nullable =  false, length = 22)
	public void setViewid(Long viewid) {
	    this.viewid=viewid;
	}
 	
	public String getViewposition() {
	    return this.viewposition;
	}
 	
	@Column(name = "VIEWPOSITION" , nullable =  false, length = 765)
	public void setViewposition(String viewposition) {
	    this.viewposition=viewposition;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}


	@Override
	public String toString() {
			return  "netTopoviewobj [objpid = "+this.objpid+",icon = "+this.icon+",objfid = "+this.objfid+",objtype = "+this.objtype+",subviewid = "+this.subviewid+
		",viewid = "+this.viewid+",viewposition = "+this.viewposition+" ]"; 
	}
	

 }