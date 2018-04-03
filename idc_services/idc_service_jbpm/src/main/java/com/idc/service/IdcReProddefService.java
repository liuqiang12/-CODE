package com.idc.service;


import com.idc.model.*;
import system.data.supper.service.SuperService;

import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RE_PRODDEF:产品模型分类表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReProddefService extends SuperService<IdcReProddef, Long>{
	/**
	 *
	 * @param category:类型
	 * @param prodInstId:服务实例[产品实例]
	 * @return
	 */
    Map<String,Object> getModelByCategory(String category, Long prodInstId, Long ticketInstId);

	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReRackModel getModelRackToBeanByCategory(String category, Long prodInstId, Long ticketInstId);

	void updateFirstDemandByTicketId(Map<String, Object> idcTicketFirstDemandMap);

	void updateTestJbpmDemand(Map<String,Object> idcTickeTestDemandMap);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcRePortModel getModelPortToBeanByCategory(String category, Long prodInstId, Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReIpModel getModelIpToBeanByCategory(String category, Long prodInstId, Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReServerModel getModelServerToBeanByCategory(String category, Long prodInstId, Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReNewlyModel getModelAddNewlyToBeanByCategory(String category, Long prodInstId, Long ticketInstId);
	/**
	 * 根据订单ID查询订单服务类型
	 * @param prodInstId
	 * @return
	 */
    ServiceApplyImgStatus getSelfModelByProdInstId(Long prodInstId);

}
