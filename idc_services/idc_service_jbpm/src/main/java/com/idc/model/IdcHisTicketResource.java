package com.idc.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_RESOURCE:历史的工单资源中间表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcHisTicketResource extends TicketIdcRackVo implements Serializable {

	public static final String tableName = "IDC_HIS_TICKET_RESOURCE";

    @Id@GeneratedValue 	
 	private Long id; //   ID
	private Long ticketInstId; //   工单实例ID
	private Long resourceid; //   资源ID
	private Integer status; //   资源状态
	private Integer statusPre; //   资源状态

	private Integer manual; //   添加方式，Dict:1,手工添加;0,自动关联
	private Integer rev; //   版本号
	private Date createTime; //   创建时间
	private String createTimeStr; //   创建时间 [日期格式化后的字符串]
	private String category;//类型:100机架机位200端口带宽300ip租用400主机租赁500增值业务 600代表机位（u位）
	private String ticketCategory;//100预勘/预占 200开通 300变更  400暂停  500复通  600下线

	private Long rack_Id;//这个专门为机位关联的机架id，删除机架要删除相关所有的机位   WCG_ADD

	private String manualName;
	private String statusName;
	private Date endTime; //   结束时间
	private String ipType;//
	private String endip;//IP的结束地址
	private String begip;//ip的开始地址:数据库中使用的是IP_NAME字段存储
	private Long deviceidId;
	private String pduPowertype;	/*用电类型*/
	private Integer ratedelectricenergy;  /*额定电量*/
	private Integer uheight;	/*U位数*/
	private String usedRackUnIt;//占用的U位,r冗余，以逗号隔开，同时也是IP资源的 网关地址字段
	private Long prodInstId;//产品id     WCG
	private Long belongId;//所属资源ID

	private String resourceStatus;//资源的状态情况
	private String resourcename;//资源名称
	private String belongName;//资源名称
	private String mcbBelongName;
	private Long parentTicketInstId;
	private String ticketCategoryFrom;


	//一对一
	private TicketIdcRackVo ticketIdcRackVo;//机架机位信息
	private List<TicketIdcMcbVo> ticketIdcMcbVos;//mcb信息


	private String ticketRackGrid;  //机架列表中的序列化，冗余,同时也是IP资源的子网掩码字段
	private TicketRackGrid ticketRackGridObj;
	private String ipName;
	private String portbandwidth;
	private String portassigation;

	private String OS;   		//主机租赁：操作系统
	private String vendor;     //主机租赁字段：厂商
	private String ownerType;  //主机租赁字段：产权性质

	private String customerId;
	private String customerName;

	private String rackOrRackUnit;  //

	/************************get set method**************************/

	public String getRackOrRackUnit() {
		return rackOrRackUnit;
	}

	public void setRackOrRackUnit(String rackOrRackUnit) {
		this.rackOrRackUnit = rackOrRackUnit;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String OS) {
		this.OS = OS;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
 	
	public Long getProdInstId() {
		return prodInstId;
	}

	public void setProdInstId(Long prodInstId) {
		this.prodInstId = prodInstId;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getPduPowertype() {
		return pduPowertype;
	}

	public void setPduPowertype(String pduPowertype) {
		this.pduPowertype = pduPowertype;
	}

	public Integer getRatedelectricenergy() {
		return ratedelectricenergy;
	}

	public void setRatedelectricenergy(Integer ratedelectricenergy) {
		this.ratedelectricenergy = ratedelectricenergy;
	}

	public Integer getUheight() {
		return uheight;
	}

	public void setUheight(Integer uheight) {
		this.uheight = uheight;
	}

	public String getUsedRackUnIt() {
		return usedRackUnIt;
	}

	public void setUsedRackUnIt(String usedRackUnIt) {
		this.usedRackUnIt = usedRackUnIt;
	}


	public Long getRack_Id() {
	return rack_Id;
}

	public void setRack_Id(Long rack_Id) {
		this.rack_Id = rack_Id;
	}


	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="ID" , nullable =  false, length = 22)
	public void setId(Long id) {
	    this.id=id;
	}
 	
	public Long getTicketInstId() {
	    return this.ticketInstId;
	}
 	
	@Column(name = "TICKET_INST_ID" , columnDefinition="工单实例ID" , nullable =  false, length = 22)
	public void setTicketInstId(Long ticketInstId) {
	    this.ticketInstId=ticketInstId;
	}
 	
	public Long getResourceid() {
	    return this.resourceid;
	}
 	
	@Column(name = "RESOURCEID" , columnDefinition="资源ID" , nullable =  false, length = 22)
	public void setResourceid(Long resourceid) {
	    this.resourceid=resourceid;
	}
 	
	public Integer getStatus() {
	    return this.status;
	}
 	
	@Column(name = "STATUS" , columnDefinition="资源状态" , nullable =  false, length = 22)
	public void setStatus(Integer status) {
	    this.status=status;
	}
 	
	public Integer getManual() {
	    return this.manual;
	}
 	
	@Column(name = "MANUAL" , columnDefinition="添加方式，Dict:1,手工添加;0,自动关联" , nullable =  false, length = 22)
	public void setManual(Integer manual) {
	    this.manual=manual;
	}
 	
	public Integer getRev() {
	    return this.rev;
	}
 	
	@Column(name = "REV" , columnDefinition="版本号" , nullable =  false, length = 22)
	public void setRev(Integer rev) {
	    this.rev=rev;
	}
 	
	public Date getCreateTime() {
	    return this.createTime;
	}
 	
	@Column(name = "CREATE_TIME" , columnDefinition="创建时间" , nullable =  false, length = 11)
	public void setCreateTime(Date createTime) {
	    this.createTime=createTime;
	}

	
	public String getCreateTimeStr() {
	    return this.createTimeStr;
	}
 	
	public void setCreateTimeStr(Date createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date_ = null;
		try {
			date_ = sdf.format(createTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.createTimeStr = date_;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public TicketIdcRackVo getTicketIdcRackVo() {
		return ticketIdcRackVo;
	}

	public void setTicketIdcRackVo(TicketIdcRackVo ticketIdcRackVo) {
		this.ticketIdcRackVo = ticketIdcRackVo;
	}

	public List<TicketIdcMcbVo> getTicketIdcMcbVos() {
		return ticketIdcMcbVos;
	}

	public void setTicketIdcMcbVos(List<TicketIdcMcbVo> ticketIdcMcbVos) {
		this.ticketIdcMcbVos = ticketIdcMcbVos;
	}

	public String getManualName() {
		return manualName;
	}

	public void setManualName(String manualName) {
		this.manualName = manualName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}



	public String getTicketCategory() {
		return ticketCategory;
	}

	public void setTicketCategory(String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public Integer getStatusPre() {
		return statusPre;
	}

	public void setStatusPre(Integer statusPre) {
		this.statusPre = statusPre;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Long getDeviceidId() {
		return deviceidId;
	}

	public void setDeviceidId(Long deviceidId) {
		this.deviceidId = deviceidId;
	}

	public String getTicketRackGrid() {
		return ticketRackGrid;
	}

	public void setTicketRackGrid(String ticketRackGrid) {
		this.ticketRackGrid = ticketRackGrid;
	}

	public TicketRackGrid getTicketRackGridObj() {
		return ticketRackGridObj;
	}

	public void setTicketRackGridObj(TicketRackGrid ticketRackGridObj) {
		this.ticketRackGridObj = ticketRackGridObj;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public Long getBelongId() {
		return belongId;
	}

	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}

	public String getMcbBelongName() {
		return mcbBelongName;
	}

	public void setMcbBelongName(String mcbBelongName) {
		this.mcbBelongName = mcbBelongName;
	}

	public String getIpName() {
		return ipName;
	}

	public void setIpName(String ipName) {
		this.ipName = ipName;
	}

	public String getPortbandwidth() {
		return portbandwidth;
	}

	public void setPortbandwidth(String portbandwidth) {
		this.portbandwidth = portbandwidth;
	}

	public String getPortassigation() {
		return portassigation;
	}

	public void setPortassigation(String portassigation) {
		this.portassigation = portassigation;
	}

	public String getEndip() {
		return endip;
	}

	public void setEndip(String endip) {
		this.endip = endip;
	}

	public String getBegip() {
		return begip;
	}

	public void setBegip(String begip) {
		this.begip = begip;
	}

	public Long getParentTicketInstId() {
		return parentTicketInstId;
	}

	public void setParentTicketInstId(Long parentTicketInstId) {
		this.parentTicketInstId = parentTicketInstId;
	}

	public String getTicketCategoryFrom() {
		return ticketCategoryFrom;
	}

	public void setTicketCategoryFrom(String ticketCategoryFrom) {
		this.ticketCategoryFrom = ticketCategoryFrom;
	}
}