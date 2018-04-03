package com.idc.mapper;



import com.idc.model.IdcRunTicketHalt;
import system.data.supper.mapper.SuperMapper;

import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_HALT:运行时下线工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketHaltMapper extends SuperMapper<IdcRunTicketHalt, Long>{
	/**
	 *   Special code can be written here 
	 */
	void updateByData(IdcRunTicketHalt idcRunTicketHalt) throws  Exception;

	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
	void updateByIdcRunTicketHalt(IdcRunTicketHalt idcRunTicketHalt) throws Exception;
	/**/
	IdcRunTicketHalt queryIdcTicketHalt(Long ticketInstId);

}

 