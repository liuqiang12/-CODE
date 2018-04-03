package com.idc.mapper;



import java.math.BigDecimal;
import java.util.Map;

import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcRunTicketPause;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_PAUSE:运行时停机工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketPauseMapper extends SuperMapper<IdcRunTicketPause, Long>{
	/**
	 *   Special code can be written here
	 */
	void updateByData(IdcRunTicketPause idcRunTicketPause) throws  Exception;

	Map<String,Object> getDataByTicketInstId(Long ticketInstId);

	IdcRunTicketPause queryByTicketInstId(Long ticketInstId);


}

 