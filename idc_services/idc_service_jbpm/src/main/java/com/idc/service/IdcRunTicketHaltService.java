package com.idc.service;


import com.idc.model.IdcRunProcCment;
import com.idc.model.IdcRunTicketHalt;
import org.springframework.context.ApplicationContext;

import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_HALT:运行时下线工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketHaltService{
	/**
	 *
	 * @param idcRunProcCment
	 * @param createTicketMap
	 * @throws Exception
	 */
	void mergeDataInto(ApplicationContext context, Map map, IdcRunProcCment idcRunProcCment, Map<String, Object> createTicketMap) throws  Exception;
	void handHaltTicket(ApplicationContext context, IdcRunProcCment idcRunProcCment, IdcRunTicketHalt idcRunTicketHalt) throws Exception;
	IdcRunTicketHalt queryIdcTicketHalt(Long ticketInstId);
}
