package com.idc.service;


import com.idc.model.IdcHisTicketRecover;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_RECOVER:历史复通工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketRecoverService extends SuperService<IdcHisTicketRecover, Long>{
	/**
	 *   Special code can be written here 
	 */
    IdcHisTicketRecover queryByTicketInstId(Long ticketInstId);
}
