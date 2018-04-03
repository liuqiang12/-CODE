package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>LOCAL_ISP_SERVER:用户服务，当然外键挂用户ID<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LocalIspServer implements Serializable {
		
	public static final String tableName = "LOCAL_ISP_SERVER";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键ID
 				
	private Long serviceNodeId; //   服务节点ID[该服务ID与域名ID保持一致] 
	 				
	private String serviceContent; //   填写服务的内容，见8.6节 
	 				
	private String regType; //   网站登记备案类型，见8.9节 
	 				
	private String regId; //   网站备案号或许可证号 
	 				
	private String setMode; //   接入方式，见8.1节 
	 				
	private String business; //   1-  IDC业务2-  ISP业务
 
	 				
	private Long domainId; //   域名ID:触发器直接修改值 
	 				
	private String domainName; //   域名名称
	private List<ISPDomain> domains;
	 				
	private Long fkIspCustomerId; //   FK_ISP_CUSTOMER_ID

	private String serviceRegTime;//服务开通时间

	/*虚拟机信息*/
	private String virtualhostName;//虚拟机名称
	private String virtualhostState;//虚拟机状态
	private String virtualhostType;//1-共享式/2-专用式/3-云虚拟
	private String virtualhostAddress;//虚拟机网络地址
	private String virtualhostManagementAddress;//虚拟机网络地址

	private Integer nature;
	private Long bandWidth;//带宽
	private String icpaccesstype;//1代表是具有虚拟主机
	

	private List<LocalIspHousehold> localIspHouseholdList;//占用机房信息
	 	
/************************get set method**************************/

	public Long getAid() {
	    return this.aid;
	}
 	
	@Column(name = "AID" , columnDefinition="主键ID" , nullable =  false, length = 22)
	public void setAid(Long aid) {
	    this.aid=aid;
	}
 	
	public Long getServiceNodeId() {
	    return this.serviceNodeId;
	}
 	
	@Column(name = "SERVICE_NODE_ID" , columnDefinition="服务节点ID[该服务ID与域名ID保持一致]" , nullable =  false, length = 22)
	public void setServiceNodeId(Long serviceNodeId) {
	    this.serviceNodeId=serviceNodeId;
	}
 	
	public String getServiceContent() {
	    return this.serviceContent;
	}
 	
	@Column(name = "SERVICE_CONTENT" , columnDefinition="填写服务的内容，见8.6节" , nullable =  false, length = 120)
	public void setServiceContent(String serviceContent) {
	    this.serviceContent=serviceContent;
	}
 	
	public String getRegType() {
	    return this.regType;
	}
 	
	@Column(name = "REG_TYPE" , columnDefinition="网站登记备案类型，见8.9节" , nullable =  false, length = 256)
	public void setRegType(String regType) {
	    this.regType=regType;
	}
 	
	public String getRegId() {
	    return this.regId;
	}
 	
	@Column(name = "REG_ID" , columnDefinition="网站备案号或许可证号" , nullable =  false, length = 256)
	public void setRegId(String regId) {
	    this.regId=regId;
	}
 	
	public String getSetMode() {
	    return this.setMode;
	}
 	
	@Column(name = "SET_MODE" , columnDefinition="接入方式，见8.1节" , nullable =  false, length = 256)
	public void setSetMode(String setMode) {
	    this.setMode=setMode;
	}
 	
	public String getBusiness() {
	    return this.business;
	}
 	
	@Column(name = "BUSINESS" , columnDefinition="1-  IDC业务2-  ISP业务 " , nullable =  false, length = 256)
	public void setBusiness(String business) {
	    this.business=business;
	}
 	
	public Long getDomainId() {
	    return this.domainId;
	}
 	
	@Column(name = "DOMAIN_ID" , columnDefinition="域名ID:触发器直接修改值" , nullable =  false, length = 22)
	public void setDomainId(Long domainId) {
	    this.domainId=domainId;
	}
 	
	public String getDomainName() {
	    return this.domainName;
	}
 	
	@Column(name = "DOMAIN_NAME" , columnDefinition="域名名称" , nullable =  false, length = 512)
	public void setDomainName(String domainName) {
	    this.domainName=domainName;
	}
 	
	public Long getFkIspCustomerId() {
	    return this.fkIspCustomerId;
	}
 	
	@Column(name = "FK_ISP_CUSTOMER_ID" , columnDefinition="FK_ISP_CUSTOMER_ID" , nullable =  false, length = 22)
	public void setFkIspCustomerId(Long fkIspCustomerId) {
	    this.fkIspCustomerId=fkIspCustomerId;
	}

	
	
	@Override
	public String toString() {
			return  "localIspServer [aid = "+this.aid+",serviceNodeId = "+this.serviceNodeId+",serviceContent = "+this.serviceContent+",regType = "+this.regType+",regId = "+this.regId+
		",setMode = "+this.setMode+",business = "+this.business+",domainId = "+this.domainId+",domainName = "+this.domainName+",fkIspCustomerId = "+this.fkIspCustomerId+
		" ]"; 
	}

	public String getServiceRegTime() {
		return serviceRegTime;
	}

	public void setServiceRegTime(String serviceRegTime) {
		this.serviceRegTime = serviceRegTime;
	}

	public List<LocalIspHousehold> getLocalIspHouseholdList() {
		return localIspHouseholdList;
	}

	public void setLocalIspHouseholdList(List<LocalIspHousehold> localIspHouseholdList) {
		this.localIspHouseholdList = localIspHouseholdList;
	}

	public String getVirtualhostName() {
		return virtualhostName;
	}

	public void setVirtualhostName(String virtualhostName) {
		this.virtualhostName = virtualhostName;
	}

	public String getVirtualhostState() {
		return virtualhostState;
	}

	public void setVirtualhostState(String virtualhostState) {
		this.virtualhostState = virtualhostState;
	}

	public String getVirtualhostType() {
		return virtualhostType;
	}

	public void setVirtualhostType(String virtualhostType) {
		this.virtualhostType = virtualhostType;
	}

	public String getVirtualhostAddress() {
		return virtualhostAddress;
	}

	public void setVirtualhostAddress(String virtualhostAddress) {
		this.virtualhostAddress = virtualhostAddress;
	}

	public String getVirtualhostManagementAddress() {
		return virtualhostManagementAddress;
	}

	public void setVirtualhostManagementAddress(String virtualhostManagementAddress) {
		this.virtualhostManagementAddress = virtualhostManagementAddress;
	}

	public Integer getNature() {
		return nature;
	}

	public void setNature(Integer nature) {
		this.nature = nature;
	}

	public Long getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(Long bandWidth) {
		this.bandWidth = bandWidth;
	}

	public List<ISPDomain> getDomains() {
		return domains;
	}

	public void setDomains(List<ISPDomain> domains) {
		this.domains = domains;
	}

	public String getIcpaccesstype() {
		return icpaccesstype;
	}

	public void setIcpaccesstype(String icpaccesstype) {
		this.icpaccesstype = icpaccesstype;
	}
}