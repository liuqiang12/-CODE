package com.idc.mapper;



import com.idc.model.IdcHisTicketHalt;
import system.data.supper.mapper.SuperMapper;

import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_HALT:历史下线工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketHaltMapper extends SuperMapper<IdcHisTicketHalt, Long>{
	/**
	 *   Special code can be written here 
	 */
	void updateByData(IdcHisTicketHalt idcHisTicketHalt) throws  Exception;

	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
	void updateByIdcHisTicketHalt(IdcHisTicketHalt idcHisTicketHalt) throws Exception;
	IdcHisTicketHalt queryIdcTicketHalt(Long ticketInstId);
}

 