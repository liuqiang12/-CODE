package com.idc.service;


import com.idc.model.IdcRunTicketCommnet;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_COMMNET:运行时当前工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketCommnetService extends SuperService<IdcRunTicketCommnet, Long>{
	/**
	 * 运行时的工单信息
	 * @param idcRunTicketCommnet
	 * @throws Exception
	 */
    void createRunTicketCommnet(IdcRunTicketCommnet idcRunTicketCommnet) throws  Exception;

	/**
	 *
	 * @param ticketInstId
	 */
    IdcRunTicketCommnet queryTikectCommentByTicketInstId(Long ticketInstId);
}
