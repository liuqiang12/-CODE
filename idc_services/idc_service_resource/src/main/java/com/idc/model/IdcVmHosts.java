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
 * <b>功能：业务表</b>IDC_VM_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcVmHosts implements Serializable {
		
	public static final String tableName = "IDC_VM_HOSTS";
			
	private String idcResourcesPoolId; //    
	 		    
    @Id@GeneratedValue 	
 	private String idcVmHostsId; //   
 				
	private String idcVmHostsName; //    
	 				
	private String idcPhysicsHostsId; //    
	 				
	private String idcVmHostsSystem; //    
	 				
	private String idcVmHostsVersion; //    
	 				
	private String idcVmHostsStutas; //   1.创建成功.2初始化完成3.启动4.挂起5.关闭 
	 				
	private String idcVmHostsAccount; //    
	 				
	private String idcVmHostsPwd; //    
	 				
	private Long idcVmHostsMemSize; //    
	 				
	private Long idcVmHostsVcpuSize; //    
	 				
	private String idcBillId; //    
	 				
	private String idcUserId; //    
	 				
	private String idcDeviceid; //    
	 	
/************************get set method**************************/
 		public String getIdcResourcesPoolId() {	    return this.idcResourcesPoolId;	}
 		@Column(name = "IDC_RESOURCES_POOL_ID" , nullable =  false, length = 32)	public void setIdcResourcesPoolId(String idcResourcesPoolId) {	    this.idcResourcesPoolId=idcResourcesPoolId;	}
 		public String getIdcVmHostsId() {	    return this.idcVmHostsId;	}
 		@Column(name = "IDC_VM_HOSTS_ID" , nullable =  false, length = 32)	public void setIdcVmHostsId(String idcVmHostsId) {	    this.idcVmHostsId=idcVmHostsId;	}
 		public String getIdcVmHostsName() {	    return this.idcVmHostsName;	}
 		@Column(name = "IDC_VM_HOSTS_NAME" , nullable =  false, length = 64)	public void setIdcVmHostsName(String idcVmHostsName) {	    this.idcVmHostsName=idcVmHostsName;	}
 		public String getIdcPhysicsHostsId() {	    return this.idcPhysicsHostsId;	}
 		@Column(name = "IDC_PHYSICS_HOSTS_ID" , nullable =  false, length = 32)	public void setIdcPhysicsHostsId(String idcPhysicsHostsId) {	    this.idcPhysicsHostsId=idcPhysicsHostsId;	}
 		public String getIdcVmHostsSystem() {	    return this.idcVmHostsSystem;	}
 		@Column(name = "IDC_VM_HOSTS_SYSTEM" , nullable =  false, length = 64)	public void setIdcVmHostsSystem(String idcVmHostsSystem) {	    this.idcVmHostsSystem=idcVmHostsSystem;	}
 		public String getIdcVmHostsVersion() {	    return this.idcVmHostsVersion;	}
 		@Column(name = "IDC_VM_HOSTS_VERSION" , nullable =  false, length = 64)	public void setIdcVmHostsVersion(String idcVmHostsVersion) {	    this.idcVmHostsVersion=idcVmHostsVersion;	}
 		public String getIdcVmHostsStutas() {	    return this.idcVmHostsStutas;	}
 		@Column(name = "IDC_VM_HOSTS_STUTAS" , columnDefinition="1.创建成功.2初始化完成3.启动4.挂起5.关闭" , nullable =  false, length = 4)	public void setIdcVmHostsStutas(String idcVmHostsStutas) {	    this.idcVmHostsStutas=idcVmHostsStutas;	}
 		public String getIdcVmHostsAccount() {	    return this.idcVmHostsAccount;	}
 		@Column(name = "IDC_VM_HOSTS_ACCOUNT" , nullable =  false, length = 64)	public void setIdcVmHostsAccount(String idcVmHostsAccount) {	    this.idcVmHostsAccount=idcVmHostsAccount;	}
 		public String getIdcVmHostsPwd() {	    return this.idcVmHostsPwd;	}
 		@Column(name = "IDC_VM_HOSTS_PWD" , nullable =  false, length = 64)	public void setIdcVmHostsPwd(String idcVmHostsPwd) {	    this.idcVmHostsPwd=idcVmHostsPwd;	}
 		public Long getIdcVmHostsMemSize() {	    return this.idcVmHostsMemSize;	}
 		@Column(name = "IDC_VM_HOSTS_MEM_SIZE" , nullable =  false, length = 22)	public void setIdcVmHostsMemSize(Long idcVmHostsMemSize) {	    this.idcVmHostsMemSize=idcVmHostsMemSize;	}
 		public Long getIdcVmHostsVcpuSize() {	    return this.idcVmHostsVcpuSize;	}
 		@Column(name = "IDC_VM_HOSTS_VCPU_SIZE" , nullable =  false, length = 22)	public void setIdcVmHostsVcpuSize(Long idcVmHostsVcpuSize) {	    this.idcVmHostsVcpuSize=idcVmHostsVcpuSize;	}
 		public String getIdcBillId() {	    return this.idcBillId;	}
 		@Column(name = "IDC_BILL_ID" , nullable =  false, length = 64)	public void setIdcBillId(String idcBillId) {	    this.idcBillId=idcBillId;	}
 		public String getIdcUserId() {	    return this.idcUserId;	}
 		@Column(name = "IDC_USER_ID" , nullable =  false, length = 64)	public void setIdcUserId(String idcUserId) {	    this.idcUserId=idcUserId;	}
 		public String getIdcDeviceid() {	    return this.idcDeviceid;	}
 		@Column(name = "IDC_DEVICEID" , nullable =  false, length = 32)	public void setIdcDeviceid(String idcDeviceid) {	    this.idcDeviceid=idcDeviceid;	}

	
		@Override	public String toString() {			return  "idcVmHosts [idcResourcesPoolId = "+this.idcResourcesPoolId+",idcVmHostsId = "+this.idcVmHostsId+",idcVmHostsName = "+this.idcVmHostsName+",idcPhysicsHostsId = "+this.idcPhysicsHostsId+",idcVmHostsSystem = "+this.idcVmHostsSystem+
		",idcVmHostsVersion = "+this.idcVmHostsVersion+",idcVmHostsStutas = "+this.idcVmHostsStutas+",idcVmHostsAccount = "+this.idcVmHostsAccount+",idcVmHostsPwd = "+this.idcVmHostsPwd+",idcVmHostsMemSize = "+this.idcVmHostsMemSize+
		",idcVmHostsVcpuSize = "+this.idcVmHostsVcpuSize+",idcBillId = "+this.idcBillId+",idcUserId = "+this.idcUserId+",idcDeviceid = "+this.idcDeviceid+" ]"; 	}
	

 }