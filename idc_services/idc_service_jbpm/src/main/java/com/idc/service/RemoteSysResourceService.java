package com.idc.service;


import java.util.Map;

/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_PROC_CMENT:当前工单意见信息IDC_RUN_TICKET_PROC_COMMENT<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RemoteSysResourceService {
	/**
	 * 修改本地资源信息和远程调用修改资源状态信息
	 * @param ticketInstId
	 * @throws Exception
	 */
	void handTicketResourceAndRemoteCall(Long ticketInstId, Map<String, Object> params) throws Exception;
}
