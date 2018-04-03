package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>LOCAL_ISP_IFRAME:机架信息和LOCAL_ISP_HOUSEHOLD表中是对应关系<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class LocalIspIframe implements Serializable {
		
	public static final String tableName = "LOCAL_ISP_IFRAME";
	    
    @Id@GeneratedValue 	
 	private Long aid; //   主键
 				
	private Long hhId; //   FRAMEINFO_ID 
	 				
	private Integer useType; //   1-自用2-出租  
	 				
	private Integer distribution; //   1-未分配2-已分配 
	 				
	private Integer occupancy; //   1-未占用2-已占用 
	 				
	private String frameName; //   机架/机位名称

	private String houseId; //   数据中心

/************************get set method**************************/
 	
	public Long getAid() {
	    return this.aid;
	}
 	
	@Column(name = "AID" , columnDefinition="主键" , nullable =  false, length = 22)
	public void setAid(Long aid) {
	    this.aid=aid;
	}
 	
	public Long getHhId() {
	    return this.hhId;
	}
 	
	@Column(name = "HH_ID" , columnDefinition="FRAMEINFO_ID" , nullable =  false, length = 22)
	public void setHhId(Long hhId) {
	    this.hhId=hhId;
	}
 	
	public Integer getUseType() {
	    return this.useType;
	}
 	
	@Column(name = "USE_TYPE" , columnDefinition="1-自用2-出租 " , nullable =  false, length = 22)
	public void setUseType(Integer useType) {
	    this.useType=useType;
	}
 	
	public Integer getDistribution() {
	    return this.distribution;
	}
 	
	@Column(name = "DISTRIBUTION" , columnDefinition="1-未分配2-已分配" , nullable =  false, length = 22)
	public void setDistribution(Integer distribution) {
	    this.distribution=distribution;
	}
 	
	public Integer getOccupancy() {
	    return this.occupancy;
	}
 	
	@Column(name = "OCCUPANCY" , columnDefinition="1-未占用2-已占用" , nullable =  false, length = 22)
	public void setOccupancy(Integer occupancy) {
	    this.occupancy=occupancy;
	}
 	
	public String getFrameName() {
	    return this.frameName;
	}
 	
	@Column(name = "FRAME_NAME" , columnDefinition="机架/机位名称" , nullable =  false, length = 128)
	public void setFrameName(String frameName) {
	    this.frameName=frameName;
	}

	@Column(name = "HOUSE_ID")
	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Override
	public String toString() {
			return  "localIspIframe [aid = "+this.aid+",hhId = "+this.hhId+",useType = "+this.useType+",distribution = "+this.distribution+",occupancy = "+this.occupancy+
		",frameName = "+this.frameName+" ]"; 
	}
	

 }