package com.idc.mapper;



import java.math.BigDecimal;
import java.util.Map;

import com.idc.model.IdcRunTicketChange;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcHisTicketChange;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_CHANGE:历史变更工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketChangeMapper extends SuperMapper<IdcHisTicketChange, Long>{
	/**
	 *
	 * @param idcHisTicketChange
	 * @throws Exception
	 */
    void updateByData(IdcHisTicketChange idcHisTicketChange) throws Exception;
	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
	Map<String,Object> getDataByCategoryTicketInstId(Long ticketInstId);

}

 