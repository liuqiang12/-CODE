package com.idc.service;


import com.idc.model.IdcRunTicket;
import com.idc.model.TicketResourceAllocation;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET:运行工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketService extends SuperService<IdcRunTicket, Long>{
	/**
	 *   Special code can be written here
	 */
	IdcRunTicket getIdcRunTicketByIdTicketInstId(Long TicketInstId);
	IdcRunTicket getIdcRunTicketByIdTicketInstIdForPerission(Long ticketInstId, Integer loginId);

	/**
	 * 将所有的历史工单都保存到缓存中
	 * @return
	 */
	List<IdcRunTicket> getRunTicketDataIntoRedis();
	List<IdcRunTicket> getIdcRunTicketByProductId(Long productId);
	String getTicketCategoryById(String ticketInstId);

	String getFormkeyByTicketId(String ticketInstId);

	Long getTicketResourceCount(Long ticketInstId);
	TicketResourceAllocation getTicketResourceAllocation(String formKey);
	Boolean getIsLastStepTicket(String FormKey);
	Boolean getIsHasOpenTicket(Long ticketInstId, Integer ticketStatus);
	Boolean getIsHasOpenTicketEdit(Long ticketInstId, Integer ticketStatus);

}
