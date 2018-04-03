package com.idc.mapper;



import java.math.BigDecimal;
import java.util.Map;

import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcRunTicketPreAccept;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_PRE_ACCEPT:运行时预受理工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketPreAcceptMapper extends SuperMapper<IdcRunTicketPreAccept, Long>{
	/**
	 * 创建运行时的预受理工单
	 * @param idcRunTicketPreAccept
	 * @return
	 * @throws Exception
	 */
    void createRunTicketPreAcceptInst(IdcRunTicketPreAccept idcRunTicketPreAccept) throws  Exception;

	/**
	 * 修改相关属性
	 * @param idcRunTicketPreAccept
	 * @throws Exception
	 */
    void updateByData(IdcRunTicketPreAccept idcRunTicketPreAccept) throws  Exception;

	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
}

 