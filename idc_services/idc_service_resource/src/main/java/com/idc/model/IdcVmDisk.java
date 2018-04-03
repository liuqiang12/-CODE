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
 * <b>功能：业务表</b>IDC_VM_DISK:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcVmDisk implements Serializable {
		
	public static final String tableName = "IDC_VM_DISK";
			
	private String idcVmHostsId;     
	 				
	private String idcVmDiskType;     
	 		    
    @Id@GeneratedValue 	
 	private String idcVmDiskId;    
 				
	private BigDecimal idcVmDiskAvailableSize;     
	 				
	private BigDecimal idcVmDiskUseSize;     
	 				
	private String idcResourcesPoolId;     
	 	
/************************get set method**************************/
 	
	public String getIdcVmHostsId() {
	    return this.idcVmHostsId;
	}
 	
	@Column(name = "IDC_VM_HOSTS_ID" , nullable =  false, length = 64)
	public void setIdcVmHostsId(String idcVmHostsId) {
	    this.idcVmHostsId=idcVmHostsId;
	}
 	
	public String getIdcVmDiskType() {
	    return this.idcVmDiskType;
	}
 	
	@Column(name = "IDC_VM_DISK_TYPE" , nullable =  false, length = 16)
	public void setIdcVmDiskType(String idcVmDiskType) {
	    this.idcVmDiskType=idcVmDiskType;
	}
 	
	public String getIdcVmDiskId() {
	    return this.idcVmDiskId;
	}
 	
	@Column(name = "IDC_VM_DISK_ID" , nullable =  false, length = 32)
	public void setIdcVmDiskId(String idcVmDiskId) {
	    this.idcVmDiskId=idcVmDiskId;
	}
 	
	public BigDecimal getIdcVmDiskAvailableSize() {
	    return this.idcVmDiskAvailableSize;
	}
 	
	@Column(name = "IDC_VM_DISK_AVAILABLE_SIZE" , nullable =  false, length = 22)
	public void setIdcVmDiskAvailableSize(BigDecimal idcVmDiskAvailableSize) {
	    this.idcVmDiskAvailableSize=idcVmDiskAvailableSize;
	}
 	
	public BigDecimal getIdcVmDiskUseSize() {
	    return this.idcVmDiskUseSize;
	}
 	
	@Column(name = "IDC_VM_DISK_USE_SIZE" , nullable =  false, length = 22)
	public void setIdcVmDiskUseSize(BigDecimal idcVmDiskUseSize) {
	    this.idcVmDiskUseSize=idcVmDiskUseSize;
	}
 	
	public String getIdcResourcesPoolId() {
	    return this.idcResourcesPoolId;
	}
 	
	@Column(name = "IDC_RESOURCES_POOL_ID" , nullable =  false, length = 32)
	public void setIdcResourcesPoolId(String idcResourcesPoolId) {
	    this.idcResourcesPoolId=idcResourcesPoolId;
	}

	
	
	@Override
	public String toString() {
			return  "idcVmDisk [idcVmHostsId = "+this.idcVmHostsId+",idcVmDiskType = "+this.idcVmDiskType+",idcVmDiskId = "+this.idcVmDiskId+",idcVmDiskAvailableSize = "+this.idcVmDiskAvailableSize+",idcVmDiskUseSize = "+this.idcVmDiskUseSize+
		",idcResourcesPoolId = "+this.idcResourcesPoolId+" ]"; 
	}
	

 }