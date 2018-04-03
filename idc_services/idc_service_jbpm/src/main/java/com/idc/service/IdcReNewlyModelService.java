package com.idc.service;


import com.idc.model.IdcReNewlyModel;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RE_NEWLY_MODEL:IDC_RE_NEWLY_BUSINESS_MODEL新增业务产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReNewlyModelService extends SuperService<IdcReNewlyModel, Long>{
	/**
	 *   Special code can be written here 
	 */
	public IdcReNewlyModel getModelByTicketInstId(Long ticketInstId);

}
