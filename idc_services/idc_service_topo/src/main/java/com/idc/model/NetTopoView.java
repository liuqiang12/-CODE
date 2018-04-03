package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>NET_TOPOVIEW:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class NetTopoView implements Serializable {
		
	public static final String tableName = "NET_TOPOVIEW";
	    
    @Id@GeneratedValue 	
 	private Long viewid; //   
 				
	private String background; //    
	 				
	private Long userid; //    
	 				
	private Long viewmode; //    
	 				
	private String viewname; //    
	 				
	private Long viewtype; //

    private List<NetTopoViewObj> viewObjs = new ArrayList<>();
    private List<NetTopoObjLink> viewLinks = new ArrayList<>();
	 	
/************************get set method**************************/
 	
	public Long getViewid() {
	    return this.viewid;
	}
 	
	@Column(name = "VIEWID" , nullable =  false, length = 22)
	public void setViewid(Long viewid) {
	    this.viewid=viewid;
	}
 	
	public String getBackground() {
	    return this.background;
	}
 	
	@Column(name = "BACKGROUND" , nullable =  false, length = 765)
	public void setBackground(String background) {
	    this.background=background;
	}
 	
	public Long getUserid() {
	    return this.userid;
	}
 	
	@Column(name = "USERID" , nullable =  false, length = 22)
	public void setUserid(Long userid) {
	    this.userid=userid;
	}
 	
	public Long getViewmode() {
	    return this.viewmode;
	}
 	
	@Column(name = "VIEWMODE" , nullable =  false, length = 22)
	public void setViewmode(Long viewmode) {
	    this.viewmode=viewmode;
	}
 	
	public String getViewname() {
	    return this.viewname;
	}
 	
	@Column(name = "VIEWNAME" , nullable =  false, length = 765)
	public void setViewname(String viewname) {
	    this.viewname=viewname;
	}
 	
	public Long getViewtype() {
	    return this.viewtype;
	}
 	
	@Column(name = "VIEWTYPE" , nullable =  false, length = 22)
	public void setViewtype(Long viewtype) {
	    this.viewtype=viewtype;
	}

	
	
	@Override
	public String toString() {
			return  "netTopoview [viewid = "+this.viewid+",background = "+this.background+",userid = "+this.userid+",viewmode = "+this.viewmode+",viewname = "+this.viewname+
		",viewtype = "+this.viewtype+" ]"; 
	}

    public List<NetTopoViewObj> getViewObjs() {
        return viewObjs;
    }

    public void setViewObjs(List<NetTopoViewObj> viewObjs) {
        this.viewObjs = viewObjs;
    }
    public void addViewObjs(NetTopoViewObj viewObj) {
        this.viewObjs .add( viewObj);
    }
    public List<NetTopoObjLink> getViewLinks() {
        return viewLinks;
    }
    public void addViewLinks(NetTopoObjLink viewLink) {
        this.viewLinks .add( viewLink);
    }
    public void setViewLinks(List<NetTopoObjLink> viewLinks) {
        this.viewLinks = viewLinks;
    }
}