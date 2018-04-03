package com.idc.mapper;



import com.idc.model.IdcRunTicketRecover;
import system.data.supper.mapper.SuperMapper;

import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>4
 * <b>功能：业务表</b>IDC_RUN_TICKET_RECOVER:运行时复通工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketRecoverMapper extends SuperMapper<IdcRunTicketRecover, Long>{
	/**
	 *   Special code can be written here 
	 */
	void updateByData(IdcRunTicketRecover idcRunTicketRecover) throws Exception;

	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
	void updateByIdcRunTicketRecover(IdcRunTicketRecover idcRunTicketRecover) throws Exception;
	IdcRunTicketRecover queryByTicketInstId(Long ticketInstId);

}

 