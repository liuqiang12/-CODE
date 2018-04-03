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
 * <b>功能：业务表</b>IDC_netlink:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcNetlink implements Serializable {
		
	public static final String tableName = "IDC_netlink";
	    
    @Id@GeneratedValue 	
 	private String id; //   
 				
	private BigDecimal srcid; //   ¿¿¿ID 
	 				
	private String srcip; //   ¿¿¿IP 
	 				
	private String desip; //   ¿¿¿¿IP 
	 				
	private BigDecimal desid; //   ¿¿¿¿ID 
	 				
	private String linename; //   ¿¿¿¿ 
	 				
	private String description; //   ¿¿¿¿ 
	 	
/************************get set method**************************/
 		public String getId() {	    return this.id;	}
 		@Column(name = "ID" , nullable =  false, length = 36)	public void setId(String id) {	    this.id=id;	}
 		public BigDecimal getSrcid() {	    return this.srcid;	}
 		@Column(name = "SRCID" , columnDefinition="¿¿¿ID" , nullable =  false)	public void setSrcid(BigDecimal srcid) {	    this.srcid=srcid;	}
 		public String getSrcip() {	    return this.srcip;	}
 		@Column(name = "SRCIP" , columnDefinition="¿¿¿IP" , nullable =  true, length = 32)	public void setSrcip(String srcip) {	    this.srcip=srcip;	}
 		public String getDesip() {	    return this.desip;	}
 		@Column(name = "DESIP" , columnDefinition="¿¿¿¿IP" , nullable =  true, length = 32)	public void setDesip(String desip) {	    this.desip=desip;	}
 		public BigDecimal getDesid() {	    return this.desid;	}
 		@Column(name = "DESID" , columnDefinition="¿¿¿¿ID" , nullable =  false)	public void setDesid(BigDecimal desid) {	    this.desid=desid;	}
 		public String getLinename() {	    return this.linename;	}
 		@Column(name = "LINENAME" , columnDefinition="¿¿¿¿" , nullable =  false, length = 32)	public void setLinename(String linename) {	    this.linename=linename;	}
 		public String getDescription() {	    return this.description;	}
 		@Column(name = "DESCRIPTION" , columnDefinition="¿¿¿¿" , nullable =  true, length = 32)	public void setDescription(String description) {	    this.description=description;	}

	
		@Override	public String toString() {			return  "idcNetlink [id = "+this.id+",srcid = "+this.srcid+",srcip = "+this.srcip+",desip = "+this.desip+",desid = "+this.desid+
		",linename = "+this.linename+",description = "+this.description+" ]"; 	}
	

 }