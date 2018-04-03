package com.idc.service;


import com.idc.model.IdcHisTicketCommnet;
import com.idc.model.IdcRunTicketCommnet;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_COMMNET:历史工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketCommnetService extends SuperService<IdcHisTicketCommnet, Long>{
	/**
	 * 历史工单信息
	 * @param idcHisTicketCommnet
	 * @throws Exception
	 */
    void createHisTicketCommnet(IdcHisTicketCommnet idcHisTicketCommnet) throws  Exception;

	/**
	 *
	 * @param ticketInstId
	 */
	IdcHisTicketCommnet queryHisTikectCommentByTicketInstId(Long ticketInstId);
}
