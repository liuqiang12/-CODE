package com.idc.mapper;



import java.math.BigDecimal;
import java.util.Map;

import com.idc.model.IdcRunTicketPreAccept;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcHisTicketPreAccept;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_PRE_ACCEPT:历史预受理工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketPreAcceptMapper extends SuperMapper<IdcHisTicketPreAccept, Long>{
	/**
	 * 保存历史预受理工单
	 * @param idcHisTicketPreAccept
	 * @throws Exception
	 */
    void createIdcHisTicketPreAccept(IdcHisTicketPreAccept idcHisTicketPreAccept) throws  Exception;
	/**
	 * 修改相关属性
	 * @param idcHisTicketPreAccept
	 * @throws Exception
	 */
    void updateByData(IdcHisTicketPreAccept idcHisTicketPreAccept) throws  Exception;
	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
}

 