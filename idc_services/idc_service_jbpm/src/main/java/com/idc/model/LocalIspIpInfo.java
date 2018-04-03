package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>LOCAL_ISP_IP_INFO:ip信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LocalIspIpInfo implements Serializable {

	public static final String tableName = "LOCAL_ISP_IP_INFO";

	@Id@GeneratedValue
	private Long aid; //   主键ID

	private String startIp; //   IP地址开始 

	private String endIp; //   END_IP 

	private Long fkHouseholdId; //   FK_HOUSEHOLD_ID 

	private Long fkServerId; //   FK_SERVER_ID

	private Long ipId; //   IP主键ID

	private Integer type; //   类型IP地址使用方式，包括0-静态；1动态；2-保留


	private String user; //   使用该IP段的单位名称，如果是个人需要填写姓名

	private Long idType; //   使用人的证件类型，见8.3节

	private String idNumber; //   对应的使用人的证件号码

	private String sourceUnit; //   特定单个IP/IP段的来源，自有的填写自己的单位名称，用户携带的填写用户单位名称

	private String allocationUnit; //   IDC/ISP经营者所持有IP的上级分配单位：集团/省公司/市公司分配的写集团/省/市公司名称，自己申请的写ICANN、APNIC或其他地址分配单位的名称

	private String useTime; //   该IP段分配给使用人的时间或使用方式转变的时间，采用yyyy-MM-dd格式

	/************************get set method**************************/

	public Long getAid() {
		return this.aid;
	}

	@Column(name = "AID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setAid(Long aid) {
		this.aid=aid;
	}

	public String getStartIp() {
		return this.startIp;
	}

	@Column(name = "START_IP" , columnDefinition="IP地址开始" , nullable =  false, length = 256)
	public void setStartIp(String startIp) {
		this.startIp=startIp;
	}

	public String getEndIp() {
		return this.endIp;
	}

	@Column(name = "END_IP" , columnDefinition="END_IP" , nullable =  false, length = 256)
	public void setEndIp(String endIp) {
		this.endIp=endIp;
	}

	public Long getFkHouseholdId() {
		return this.fkHouseholdId;
	}

	@Column(name = "FK_HOUSEHOLD_ID" , columnDefinition="FK_HOUSEHOLD_ID" , nullable =  false, length = 22)
	public void setFkHouseholdId(Long fkHouseholdId) {
		this.fkHouseholdId=fkHouseholdId;
	}

	public Long getFkServerId() {
		return this.fkServerId;
	}

	@Column(name = "FK_SERVER_ID" , columnDefinition="FK_SERVER_ID" , nullable =  false, length = 22)
	public void setFkServerId(Long fkServerId) {
		this.fkServerId=fkServerId;
	}

	public Long getIpId() {
		return this.ipId;
	}

	@Column(name = "IP_ID" , columnDefinition="IP主键ID" , nullable =  false, length = 22)
	public void setIpId(Long ipId) {
		this.ipId=ipId;
	}

	public Integer getType() {
		return this.type;
	}

	@Column(name = "TYPE" , columnDefinition="类型IP地址使用方式，包括0-静态；1动态；2-保留" , nullable =  false, length = 22)
	public void setType(Integer type) {
		this.type=type;
	}

	public String getUser() {
		return this.user;
	}

	@Column(name = "USER" , columnDefinition="使用该IP段的单位名称，如果是个人需要填写姓名" , nullable =  false, length = 512)
	public void setUser(String user) {
		this.user=user;
	}

	public Long getIdType() {
		return this.idType;
	}

	@Column(name = "ID_TYPE" , columnDefinition="使用人的证件类型，见8.3节 " , nullable =  false, length = 22)
	public void setIdType(Long idType) {
		this.idType=idType;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	@Column(name = "ID_NUMBER" , columnDefinition="对应的使用人的证件号码 " , nullable =  false, length = 128)
	public void setIdNumber(String idNumber) {
		this.idNumber=idNumber;
	}

	public String getSourceUnit() {
		return this.sourceUnit;
	}

	@Column(name = "SOURCE_UNIT" , columnDefinition="特定单个IP/IP段的来源，自有的填写自己的单位名称，用户携带的填写用户单位名称" , nullable =  false, length = 512)
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit=sourceUnit;
	}

	public String getAllocationUnit() {
		return this.allocationUnit;
	}

	@Column(name = "ALLOCATION_UNIT" , columnDefinition="IDC/ISP经营者所持有IP的上级分配单位：集团/省公司/市公司分配的写集团/省/市公司名称，自己申请的写ICANN、APNIC或其他地址分配单位的名称 " , nullable =  false, length = 512)
	public void setAllocationUnit(String allocationUnit) {
		this.allocationUnit=allocationUnit;
	}

	public String getUseTime() {
		return this.useTime;
	}

	@Column(name = "USE_TIME" , columnDefinition="该IP段分配给使用人的时间或使用方式转变的时间，采用yyyy-MM-dd格式" , nullable =  false, length = 40)
	public void setUseTime(String useTime) {
		this.useTime=useTime;
	}



	@Override
	public String toString() {
		return  "localIspIpInfo [aid = "+this.aid+",startIp = "+this.startIp+",endIp = "+this.endIp+",fkHouseholdId = "+this.fkHouseholdId+",fkServerId = "+this.fkServerId+
				",ipId = "+this.ipId+",type = "+this.type+",user = "+this.user+",idType = "+this.idType+",idNumber = "+this.idNumber+
				",sourceUnit = "+this.sourceUnit+",allocationUnit = "+this.allocationUnit+",useTime = "+this.useTime+" ]";
	}


}