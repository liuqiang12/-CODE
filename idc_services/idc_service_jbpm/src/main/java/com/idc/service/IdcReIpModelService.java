package com.idc.service;


import com.idc.model.IdcReIpModel;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RE_IP_MODEL:IP租用产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReIpModelService extends SuperService<IdcReIpModel, Long>{
	/**
	 *   Special code can be written here 
	 */
	public IdcReIpModel getModelByTicketInstId(Long ticketInstId);

}
