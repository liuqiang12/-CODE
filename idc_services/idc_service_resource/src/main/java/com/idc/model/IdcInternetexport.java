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
 * <b>功能：业务表</b>IDC_INTERNETEXPORT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcInternetexport implements Serializable {
		
	public static final String tableName = "IDC_INTERNETEXPORT";
	    
    @Id@GeneratedValue 	
 	private Long id; //   
 				
	private String name; //    
	 				
	private Long locationId; //    
	 				
	private String bandwidth; //    
	 				
	private String gatewayip; //    
	 				
	private Long iptotal; //    
	 				
	private Long ipused; //    
	 				
	private Long ipusage; //    
	 				
	private Long coreporttotal; //    
	 				
	private Long coreportused; //    
	 				
	private Long coreportusage; //    
	 				
	private Long accessporttotal; //    
	 				
	private Long accessportused; //    
	 				
	private Long accessportusage; //    
	 	
/************************get set method**************************/
 		public Long getId() {	    return this.id;	}
 		@Column(name = "ID" , nullable =  false, length = 22)	public void setId(Long id) {	    this.id=id;	}
 		public String getName() {	    return this.name;	}
 		@Column(name = "NAME" , nullable =  false, length = 50)	public void setName(String name) {	    this.name=name;	}
 		public Long getLocationId() {	    return this.locationId;	}
 		@Column(name = "LOCATION_ID" , nullable =  false, length = 22)	public void setLocationId(Long locationId) {	    this.locationId=locationId;	}
 		public String getBandwidth() {	    return this.bandwidth;	}
 		@Column(name = "BANDWIDTH" , nullable =  false, length = 100)	public void setBandwidth(String bandwidth) {	    this.bandwidth=bandwidth;	}
 		public String getGatewayip() {	    return this.gatewayip;	}
 		@Column(name = "GATEWAYIP" , nullable =  false, length = 255)	public void setGatewayip(String gatewayip) {	    this.gatewayip=gatewayip;	}
 		public Long getIptotal() {	    return this.iptotal;	}
 		@Column(name = "IPTOTAL" , nullable =  false, length = 22)	public void setIptotal(Long iptotal) {	    this.iptotal=iptotal;	}
 		public Long getIpused() {	    return this.ipused;	}
 		@Column(name = "IPUSED" , nullable =  false, length = 22)	public void setIpused(Long ipused) {	    this.ipused=ipused;	}
 		public Long getIpusage() {	    return this.ipusage;	}
 		@Column(name = "IPUSAGE" , nullable =  false, length = 22)	public void setIpusage(Long ipusage) {	    this.ipusage=ipusage;	}
 		public Long getCoreporttotal() {	    return this.coreporttotal;	}
 		@Column(name = "COREPORTTOTAL" , nullable =  false, length = 22)	public void setCoreporttotal(Long coreporttotal) {	    this.coreporttotal=coreporttotal;	}
 		public Long getCoreportused() {	    return this.coreportused;	}
 		@Column(name = "COREPORTUSED" , nullable =  false, length = 22)	public void setCoreportused(Long coreportused) {	    this.coreportused=coreportused;	}
 		public Long getCoreportusage() {	    return this.coreportusage;	}
 		@Column(name = "COREPORTUSAGE" , nullable =  false, length = 22)	public void setCoreportusage(Long coreportusage) {	    this.coreportusage=coreportusage;	}
 		public Long getAccessporttotal() {	    return this.accessporttotal;	}
 		@Column(name = "ACCESSPORTTOTAL" , nullable =  false, length = 22)	public void setAccessporttotal(Long accessporttotal) {	    this.accessporttotal=accessporttotal;	}
 		public Long getAccessportused() {	    return this.accessportused;	}
 		@Column(name = "ACCESSPORTUSED" , nullable =  false, length = 22)	public void setAccessportused(Long accessportused) {	    this.accessportused=accessportused;	}
 		public Long getAccessportusage() {	    return this.accessportusage;	}
 		@Column(name = "ACCESSPORTUSAGE" , nullable =  false, length = 22)	public void setAccessportusage(Long accessportusage) {	    this.accessportusage=accessportusage;	}

	
		@Override	public String toString() {			return  "idcInternetexport [id = "+this.id+",name = "+this.name+",locationId = "+this.locationId+",bandwidth = "+this.bandwidth+",gatewayip = "+this.gatewayip+
		",iptotal = "+this.iptotal+",ipused = "+this.ipused+",ipusage = "+this.ipusage+",coreporttotal = "+this.coreporttotal+",coreportused = "+this.coreportused+
		",coreportusage = "+this.coreportusage+",accessporttotal = "+this.accessporttotal+",accessportused = "+this.accessportused+",accessportusage = "+this.accessportusage+" ]"; 	}
	

 }