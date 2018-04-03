package com.idc.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_MCB:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcMcb implements Serializable {
		
	public static final String tableName = "IDC_MCB";
	    
    @Id@GeneratedValue 	
 	private Long id; //   设备标识

	private String name;
 				
	private Integer pwrPwrstatus; //   MCB使用状态 
	 				
	private String pwrMcbno; //   MCBNO 
	 				
	private Long pwrInstalledrackId; //   PDF机架

	private Long pwrServicerackId;//客户机架

    private String sysdescr; //   设备描述

    private Long customerid;//客户ID

    private String customername;//客户名称

    private Long ticketId;//工单ID

    private Integer isdelete;//是否删除 0-未删除  1-已删除

    /************************get set method**************************/
 	
	public Long getId() {
	    return this.id;
	}
 	
	@Column(name = "ID" , columnDefinition="MCB标识" , nullable =  false)
	public void setId(Long id) {
	    this.id=id;
	}

	public String getName() {
		return name;
	}
	@Column(name = "NAME" , columnDefinition="MCB名称" , nullable =  false)
	public void setName(String name) {
		this.name = name;
	}

	public Integer getPwrPwrstatus() {
	    return this.pwrPwrstatus;
	}
 	
	@Column(name = "PWR_PWRSTATUS" , columnDefinition="MCB使用状态" )
	public void setPwrPwrstatus(Integer pwrPwrstatus) {
	    this.pwrPwrstatus=pwrPwrstatus;
	}
 	
	public String getPwrMcbno() {
	    return this.pwrMcbno;
	}
 	
	@Column(name = "PWR_MCBNO" , columnDefinition="MCBNO" , length = 64)
	public void setPwrMcbno(String pwrMcbno) {
	    this.pwrMcbno=pwrMcbno;
	}
 	
	public Long getPwrInstalledrackId() {
	    return this.pwrInstalledrackId;
	}
 	
	@Column(name = "PWR_INSTALLEDRACK_ID" , columnDefinition="PDF机架")
	public void setPwrInstalledrackId(Long pwrInstalledrackId) {
	    this.pwrInstalledrackId=pwrInstalledrackId;
	}

	public Long getPwrServicerackId() {
		return pwrServicerackId;
	}
	@Column(name = "PWR_SERVICERACK_ID" , columnDefinition="客户机架")
	public void setPwrServicerackId(Long pwrServicerackId) {
		this.pwrServicerackId = pwrServicerackId;
	}

	public String getSysdescr() {
	    return this.sysdescr;
	}
 	
	@Column(name = "SYSDESCR" , columnDefinition="设备描述" ,length = 500)
	public void setSysdescr(String sysdescr) {
	    this.sysdescr=sysdescr;
	}

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

	@Override
	public String toString() {
			return  "idcMcb [id = "+this.id+",pwrPwrstatus = "+this.pwrPwrstatus+",pwrMcbno = "+this.pwrMcbno+",pwrInstalledrackId = "+this.pwrInstalledrackId+"pwrServicerackId = "+this.pwrServicerackId+",sysdescr = "+this.sysdescr+
                    ",customerid=" + this.customerid + ",customername=" + this.customername + ",ticketId=" + this.ticketId + ",isdelete=" + this.isdelete + " ]";
    }

 }