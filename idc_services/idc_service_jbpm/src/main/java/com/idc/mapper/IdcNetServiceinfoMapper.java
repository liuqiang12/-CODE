package com.idc.mapper;


import com.idc.model.IdcNetServiceinfo;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_NET_SERVICEINFO:IDC_NET_SERVICEINFO<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcNetServiceinfoMapper extends SuperMapper<IdcNetServiceinfo, Long>{
	/**
	 * 获取服务信息
	 * @param ticketInstId
	 * @return
	 */
    IdcNetServiceinfo getIdcNetServiceinfoByTicketInstId(Long ticketInstId);
	/**
	 * 合体方法
	 * @throws Exception
	 */
    void mergeInto(IdcNetServiceinfo idcNetServiceinfo) throws Exception;

    void updateByDNS(Long id) throws Exception;
    void deleteByContractId(Long conractId) throws Exception;
	void insertIdcNetServiceinfo(IdcNetServiceinfo idcNetServiceinfo) throws Exception;
	List<IdcNetServiceinfo> getIdcNetServiceinfoByCustomerId(Long customerId);


}

 