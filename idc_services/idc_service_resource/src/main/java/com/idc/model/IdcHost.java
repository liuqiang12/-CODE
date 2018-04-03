package com.idc.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HOST:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcHost implements Serializable {
		
	public static final String tableName = "IDC_HOST";
	    
    @Id@GeneratedValue 	
 	private Long deviceid; //   设备标识
 				
	private String devicetype; //   设备类型 
	 				
	private String os; //   操作系统 
	 				
	private String cpusize; //   CPU描述 
	 				
	private Long memsize; //   内存大小 
	 				
	private Long disksize; //   硬盘大小 
	 				
	private String userid; //   操作用户 

    private String sysdescr; //   设备描述

    private String ipaddress;//设备IP

    /************************get set method**************************/
 	
	public Long getDeviceid() {
	    return this.deviceid;
	}
 	
	@Column(name = "DEVICEID" , columnDefinition="设备标识" , nullable =  false, length = 22)
	public void setDeviceid(Long deviceid) {
	    this.deviceid=deviceid;
	}
 	
	public String getDevicetype() {
	    return this.devicetype;
	}
 	
	@Column(name = "DEVICETYPE" , columnDefinition="设备类型" , nullable =  false, length = 50)
	public void setDevicetype(String devicetype) {
	    this.devicetype=devicetype;
	}
 	
	public String getOs() {
	    return this.os;
	}
 	
	@Column(name = "OS" , columnDefinition="操作系统" , nullable =  false, length = 50)
	public void setOs(String os) {
	    this.os=os;
	}
 	
	public String getCpusize() {
	    return this.cpusize;
	}
 	
	@Column(name = "CPUSIZE" , columnDefinition="CPU描述" , nullable =  false, length = 50)
	public void setCpusize(String cpusize) {
	    this.cpusize=cpusize;
	}
 	
	public Long getMemsize() {
	    return this.memsize;
	}
 	
	@Column(name = "MEMSIZE" , columnDefinition="内存大小" , nullable =  false, length = 22)
	public void setMemsize(Long memsize) {
	    this.memsize=memsize;
	}
 	
	public Long getDisksize() {
	    return this.disksize;
	}
 	
	@Column(name = "DISKSIZE" , columnDefinition="硬盘大小" , nullable =  false, length = 22)
	public void setDisksize(Long disksize) {
	    this.disksize=disksize;
	}
 	
	public String getUserid() {
	    return this.userid;
	}
 	
	@Column(name = "USERID" , columnDefinition="操作用户" , nullable =  false, length = 50)
	public void setUserid(String userid) {
	    this.userid=userid;
	}
 	
	public String getSysdescr() {
	    return this.sysdescr;
	}
 	
	@Column(name = "SYSDESCR" , columnDefinition="设备描述" , nullable =  false, length = 500)
	public void setSysdescr(String sysdescr) {
	    this.sysdescr=sysdescr;
	}

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

	@Override
	public String toString() {
			return  "idcHost [deviceid = "+this.deviceid+",devicetype = "+this.devicetype+",os = "+this.os+",cpusize = "+this.cpusize+",memsize = "+this.memsize+
                    ",disksize = " + this.disksize + ",userid = " + this.userid + ",sysdescr = " + this.sysdescr + ",serverIpaddress=" + this.ipaddress + " ]";
    }
	

 }