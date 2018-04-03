package com.idc.model;import javax.persistence.Column;import javax.persistence.GeneratedValue;import javax.persistence.Id;import java.io.Serializable;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>LOCAL_ISP_IP_SEG:LOCAL_ISP_IP_SEG<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Dec 04,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class LocalIspIpSeg implements Serializable {			public static final String tableName = "LOCAL_ISP_IP_SEG";	        @Id@GeneratedValue 	 	private Long id; //   ID 					private Long ipId; //   IP地址段序号 	 					private String startIp; //   START_IP 	 					private String endIp; //   END_IP 	 					private Integer type; //   TYPE_ 	 					private String user; //   USER_ 	 					private Integer idType; //   使用人的证件类型 	 					private String idNumber; //   对应证件号码 	 					private String sourceUnit; //   来源单位 	 					private String allocationUnit; //   分配单位 	 					private String useTime; //   该IP段分配给使用人的时间或使用方式转变的时间，采用yyyy-MM-dd格式	private String houseId;//  数据中心ID	 	/************************get set method**************************/ 		public Long getId() {	    return this.id;	} 		@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)	public void setId(Long id) {	    this.id=id;	} 		public Long getIpId() {	    return this.ipId;	} 		@Column(name = "IP_ID" , columnDefinition="IP地址段序号" , nullable =  false, length = 22)	public void setIpId(Long ipId) {	    this.ipId=ipId;	} 		public String getStartIp() {	    return this.startIp;	} 		@Column(name = "START_IP" , columnDefinition="START_IP" , nullable =  false, length = 64)	public void setStartIp(String startIp) {	    this.startIp=startIp;	} 		public String getEndIp() {	    return this.endIp;	} 		@Column(name = "END_IP" , columnDefinition="END_IP" , nullable =  false, length = 64)	public void setEndIp(String endIp) {	    this.endIp=endIp;	} 		public Integer getType() {	    return this.type;	} 		@Column(name = "TYPE_" , columnDefinition="TYPE_" , nullable =  false, length = 22)	public void setType(Integer type) {	    this.type=type;	} 		public String getUser() {	    return this.user;	} 		@Column(name = "USER_" , columnDefinition="USER_" , nullable =  false, length = 128)	public void setUser(String user) {	    this.user=user;	} 		public Integer getIdType() {	    return this.idType;	} 		@Column(name = "ID_TYPE" , columnDefinition="使用人的证件类型" , nullable =  false, length = 22)	public void setIdType(Integer idType) {	    this.idType=idType;	} 		public String getIdNumber() {	    return this.idNumber;	} 		@Column(name = "ID_NUMBER" , columnDefinition="对应证件号码" , nullable =  false, length = 32)	public void setIdNumber(String idNumber) {	    this.idNumber=idNumber;	} 		public String getSourceUnit() {	    return this.sourceUnit;	} 		@Column(name = "SOURCE_UNIT" , columnDefinition="来源单位" , nullable =  false, length = 128)	public void setSourceUnit(String sourceUnit) {	    this.sourceUnit=sourceUnit;	} 		public String getAllocationUnit() {	    return this.allocationUnit;	} 		@Column(name = "ALLOCATION_UNIT" , columnDefinition="分配单位" , nullable =  false, length = 128)	public void setAllocationUnit(String allocationUnit) {		this.allocationUnit=allocationUnit;	} 		public String getUseTime() {	    return this.useTime;	} 		@Column(name = "USE_TIME" , columnDefinition="该IP段分配给使用人的时间或使用方式转变的时间，采用yyyy-MM-dd格式" , nullable =  false, length = 10)	public void setUseTime(String useTime) {	    this.useTime=useTime;	}	@Column(name = "HOUSE_ID" , columnDefinition="当前机房ID，由IDC或ISP经营者产生，在本单位中唯一" , nullable =  false, length = 128)	public String getHouseId() {		return houseId;	}	public void setHouseId(String houseId) {		this.houseId = houseId;	}	@Override	public String toString() {			return  "localIspIpSeg [id = "+this.id+",ipId = "+this.ipId+",startIp = "+this.startIp+",endIp = "+this.endIp+",type = "+this.type+		",user = "+this.user+",idType = "+this.idType+",idNumber = "+this.idNumber+",sourceUnit = "+this.sourceUnit+",allocationUnit = "+this.allocationUnit+		",useTime = "+this.useTime+" ]"; 	}	 }