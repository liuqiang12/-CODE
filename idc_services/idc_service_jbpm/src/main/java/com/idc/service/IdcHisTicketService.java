package com.idc.service;


import com.idc.model.IdcHisTicket;
import com.idc.model.IdcHisTicketInitVo;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET:历史工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketService extends SuperService<IdcHisTicket, Long>{
	/**
	 * 查询预受理工单的相关信息
	 * @param initId
	 * @return
	 */
    IdcHisTicketInitVo queryPreTicketInfo(String initId);

    Map<String,Object> queryTicketByCondition(Map<String, Object> condition);

    //通过客户id查找客户已经完成的工单
	List<Map<String,Object>> queryEndTicketByCustomerId(PageBean page, Map<String, Object> map);

    //通过客户id查找客户已经完成的工单
	Long queryEndTicketByCustomerIdCount(Long cusId);

	/**
	 * 将所有的历史工单都保存到缓存中
	 * @return
	 */
	List<IdcHisTicket> getHistTicketDataIntoRedis();
	IdcHisTicket getIdcHisTicketById(Long ticketInstId);
	String getIdcHisTicketCategoryById(String ticketInstId);
	IdcHisTicket getIdcHisTicketByIdTicketInstId(Long TicketInstId);
	IdcHisTicket getIdcHisTicketByIdTicketInstIdForPerission(Long ticketInstId, Integer loginId);

	List<Map<String,Object>> getContractListPageData(Long prodInstId);

	Long queryProdInstIdByTicketInstId(Long ticketInstId);

	String getProdCategoryById(String ticketInstId);

}
