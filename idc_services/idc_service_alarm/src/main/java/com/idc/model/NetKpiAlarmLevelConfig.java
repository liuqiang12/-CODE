package com.idc.model;import java.math.BigDecimal;import java.io.Serializable;import java.math.BigDecimal;import java.util.Date;import java.sql.Timestamp;import java.sql.Clob;import java.text.SimpleDateFormat;import javax.persistence.GeneratedValue;import javax.persistence.Column;import javax.persistence.Id;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>NET_KPIALARMLEVELCONFIG:${tableData.tableComment}<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Aug 02,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class NetKpiAlarmLevelConfig implements Serializable {			public static final String tableName = "NET_KPIALARMLEVELCONFIG";	        @Id@GeneratedValue 	 	private Long id; //    					private Long kpiid; //    	 					private Long thresholdlimit; //    	 					private Long thresholdlowlimit; //    	 					private String keystr; //    	 					private Long alarmlevel; //    	 					private Long alarmclass; //    	 					private Long resourceid; //    	 					private Long delflag; //    	 					private String description; //    	 	/************************get set method**************************/ 		public Long getId() {	    return this.id;	} 		@Column(name = "ID" , nullable =  false, length = 22)	public void setId(Long id) {	    this.id=id;	} 		public Long getKpiid() {	    return this.kpiid;	} 		@Column(name = "KPIID" , nullable =  false, length = 22)	public void setKpiid(Long kpiid) {	    this.kpiid=kpiid;	} 		public Long getThresholdlimit() {	    return this.thresholdlimit;	} 		@Column(name = "THRESHOLDLIMIT" , nullable =  false, length = 22)	public void setThresholdlimit(Long thresholdlimit) {	    this.thresholdlimit=thresholdlimit;	} 		public Long getThresholdlowlimit() {	    return this.thresholdlowlimit;	} 		@Column(name = "THRESHOLDLOWLIMIT" , nullable =  false, length = 22)	public void setThresholdlowlimit(Long thresholdlowlimit) {	    this.thresholdlowlimit=thresholdlowlimit;	} 		public String getKeystr() {	    return this.keystr;	} 		@Column(name = "KEYSTR" , nullable =  false, length = 128)	public void setKeystr(String keystr) {	    this.keystr=keystr;	} 		public Long getAlarmlevel() {	    return this.alarmlevel;	} 		@Column(name = "ALARMLEVEL" , nullable =  false, length = 22)	public void setAlarmlevel(Long alarmlevel) {	    this.alarmlevel=alarmlevel;	} 		public Long getAlarmclass() {	    return this.alarmclass;	} 		@Column(name = "ALARMCLASS" , nullable =  false, length = 22)	public void setAlarmclass(Long alarmclass) {	    this.alarmclass=alarmclass;	} 		public Long getResourceid() {	    return this.resourceid;	} 		@Column(name = "RESOURCEID" , nullable =  false, length = 22)	public void setResourceid(Long resourceid) {	    this.resourceid=resourceid;	} 		public Long getDelflag() {	    return this.delflag;	} 		@Column(name = "DELFLAG" , nullable =  false, length = 22)	public void setDelflag(Long delflag) {	    this.delflag=delflag;	} 		public String getDescription() {	    return this.description;	} 		@Column(name = "DESCRIPTION" , nullable =  false, length = 128)	public void setDescription(String description) {	    this.description=description;	}			@Override	public String toString() {			return  "netKpialarmlevelconfig [id = "+this.id+",kpiid = "+this.kpiid+",thresholdlimit = "+this.thresholdlimit+",thresholdlowlimit = "+this.thresholdlowlimit+",keystr = "+this.keystr+		",alarmlevel = "+this.alarmlevel+",alarmclass = "+this.alarmclass+",resourceid = "+this.resourceid+",delflag = "+this.delflag+",description = "+this.description+		" ]"; 	}	 }