package com.idc.service;


import com.idc.model.IdcRunProcCment;
import com.idc.model.IdcRunTicketRecover;
import org.springframework.context.ApplicationContext;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_RECOVER:运行时复通工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketRecoverService {
	/**
	 *   Special code can be written here 
	 */
	void handWithRecover(ApplicationContext context, IdcRunProcCment idcRunProcCment, IdcRunTicketRecover idcRunTicketRecover) throws Exception;
	IdcRunTicketRecover queryByTicketInstId(Long ticketInstId);
}
