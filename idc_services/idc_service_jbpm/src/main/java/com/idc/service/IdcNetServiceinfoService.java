package com.idc.service;


import com.idc.model.IdcNetServiceinfo;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_NET_SERVICEINFO:IDC_NET_SERVICEINFO<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcNetServiceinfoService extends SuperService<IdcNetServiceinfo, Long>{
	/**
	 * 获取服务信息
	 * @param ticketInstId
	 * @return
	 */
    IdcNetServiceinfo getIdcNetServiceinfoByTicketInstId(Long ticketInstId);
	List<IdcNetServiceinfo> getIdcNetServiceinfoByCustomerId(Long customerId);

	void insertByLSCS(IdcNetServiceinfo idcNetServiceinfo) throws Exception;
}
