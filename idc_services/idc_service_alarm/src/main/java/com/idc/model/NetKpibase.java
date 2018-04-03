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
 * <b>功能：业务表</b>NET_KPIBASE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class NetKpibase implements Serializable {
		
	public static final String tableName = "NET_KPIBASE";
	    
    @Id@GeneratedValue 	
 	private Long kpiid; //   
 				
	private Long gatherclass; //    
	 				
	private Long kpiclass; //    
	 				
	private String kpikey; //    
	 				
	private String kpiname; //    
	 				
	private String kpiunit; //    
	 				
	private String kpitable; //    
	 				
	private String kpifield; //    
	 				
	private Long deviceclass; //    
	 				
	private Long kpigroup; //    
	 				
	private String kpides; //    
	 				
	private String extend; //    
	 	
/************************get set method**************************/
 		public Long getKpiid() {	    return this.kpiid;	}
 		@Column(name = "KPIID" , nullable =  false, length = 22)	public void setKpiid(Long kpiid) {	    this.kpiid=kpiid;	}
 		public Long getGatherclass() {	    return this.gatherclass;	}
 		@Column(name = "GATHERCLASS" , nullable =  false, length = 22)	public void setGatherclass(Long gatherclass) {	    this.gatherclass=gatherclass;	}
 		public Long getKpiclass() {	    return this.kpiclass;	}
 		@Column(name = "KPICLASS" , nullable =  false, length = 22)	public void setKpiclass(Long kpiclass) {	    this.kpiclass=kpiclass;	}
 		public String getKpikey() {	    return this.kpikey;	}
 		@Column(name = "KPIKEY" , nullable =  false, length = 150)	public void setKpikey(String kpikey) {	    this.kpikey=kpikey;	}
 		public String getKpiname() {	    return this.kpiname;	}
 		@Column(name = "KPINAME" , nullable =  false, length = 90)	public void setKpiname(String kpiname) {	    this.kpiname=kpiname;	}
 		public String getKpiunit() {	    return this.kpiunit;	}
 		@Column(name = "KPIUNIT" , nullable =  false, length = 60)	public void setKpiunit(String kpiunit) {	    this.kpiunit=kpiunit;	}
 		public String getKpitable() {	    return this.kpitable;	}
 		@Column(name = "KPITABLE" , nullable =  false, length = 150)	public void setKpitable(String kpitable) {	    this.kpitable=kpitable;	}
 		public String getKpifield() {	    return this.kpifield;	}
 		@Column(name = "KPIFIELD" , nullable =  false, length = 150)	public void setKpifield(String kpifield) {	    this.kpifield=kpifield;	}
 		public Long getDeviceclass() {	    return this.deviceclass;	}
 		@Column(name = "DEVICECLASS" , nullable =  false, length = 22)	public void setDeviceclass(Long deviceclass) {	    this.deviceclass=deviceclass;	}
 		public Long getKpigroup() {	    return this.kpigroup;	}
 		@Column(name = "KPIGROUP" , nullable =  false, length = 22)	public void setKpigroup(Long kpigroup) {	    this.kpigroup=kpigroup;	}
 		public String getKpides() {	    return this.kpides;	}
 		@Column(name = "KPIDES" , nullable =  false, length = 765)	public void setKpides(String kpides) {	    this.kpides=kpides;	}
 		public String getExtend() {	    return this.extend;	}
 		@Column(name = "EXTEND" , nullable =  false, length = 765)	public void setExtend(String extend) {	    this.extend=extend;	}

	
		@Override	public String toString() {			return  "netKpibase [kpiid = "+this.kpiid+",gatherclass = "+this.gatherclass+",kpiclass = "+this.kpiclass+",kpikey = "+this.kpikey+",kpiname = "+this.kpiname+
		",kpiunit = "+this.kpiunit+",kpitable = "+this.kpitable+",kpifield = "+this.kpifield+",deviceclass = "+this.deviceclass+",kpigroup = "+this.kpigroup+
		",kpides = "+this.kpides+",extend = "+this.extend+" ]"; 	}
	

 }