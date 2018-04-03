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
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	

	
	
		",idcVmHostsVersion = "+this.idcVmHostsVersion+",idcVmHostsStutas = "+this.idcVmHostsStutas+",idcVmHostsAccount = "+this.idcVmHostsAccount+",idcVmHostsPwd = "+this.idcVmHostsPwd+",idcVmHostsMemSize = "+this.idcVmHostsMemSize+
		",idcVmHostsVcpuSize = "+this.idcVmHostsVcpuSize+",idcBillId = "+this.idcBillId+",idcUserId = "+this.idcUserId+",idcDeviceid = "+this.idcDeviceid+" ]"; 
	

 }