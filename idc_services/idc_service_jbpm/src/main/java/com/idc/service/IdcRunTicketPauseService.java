package com.idc.service;


import com.idc.model.IdcRunProcCment;
import com.idc.model.IdcRunTicketPause;
import org.springframework.context.ApplicationContext;
import system.data.supper.service.SuperService;

import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_PAUSE:运行时停机工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketPauseService extends SuperService<IdcRunTicketPause, Long>{
	void mergeDataInto(ApplicationContext applicationContext, Map map, IdcRunProcCment idcRunProcCment, Map<String, Object> createTicketMap) throws Exception;

	IdcRunTicketPause queryByTicketInstId(Long ticketInstId);


}
