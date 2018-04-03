package com.idc.service;


import com.idc.model.AssetAttachmentinfo;
import com.idc.model.IdcContract;
import com.idc.model.IdcNetServiceinfo;
import com.idc.model.IdcRunProcCment;
import org.springframework.context.ApplicationContext;
import system.data.page.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_CONTRACT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcContractService {
	/**
	 * Special code can be written here
	 * queryGridListPage
	 */
	List<IdcContract> queryGridListPage(PageBean<IdcContract> page, IdcContract idcContract);

	List<Map<String, Object>> queryContractByUserListPage(PageBean<IdcContract> page, Map<String, Object> map);

	void handOpenTicketWithContractInto(ApplicationContext context, IdcContract idcContract, IdcRunProcCment idcRunProcCment, HttpServletRequest request) throws Exception;

	void handOpenTicketWithContractInto_Self(ApplicationContext context, IdcContract idcContract, IdcRunProcCment idcRunProcCment, HttpServletRequest request) throws Exception;

	/*通过ID获取相应的实体类*/
	IdcContract getModelByContractId(Long id);

	/*通过id删除合同*/
	void deleteRecordById(Long id) throws Exception;

	void createTicketApply(ApplicationContext context, Map<String, Object> params) throws Exception;

	/**
	 * 通过工单实例获取合同信息
	 *
	 * @param ticketInstId
	 * @return
	 */
	IdcContract getContractByTicketInstId(Long ticketInstId);

	/**
	 * 将合同的数据保存到redis中
	 *
	 * @return
	 */
	List<IdcContract> getIdcContractDataIntoRedis();

	Long verifyContractRepeat(String contractName);

	String querySonTicketIsEnd(String ticketInstId, String prodInstId);

	Long contractHandlerData(ApplicationContext context, IdcContract idcContract, IdcNetServiceinfo idcNetServiceinfo, HttpServletRequest request, Integer prodCategory, Boolean isRejectTicket) throws Exception;

	void insertAttachInfo(ApplicationContext context, AssetAttachmentinfo assetAttachmentinfo) throws Exception;

	void alterResourceStatus_A(ApplicationContext applicationContext, Long ticketInstId, Map<String, Object> params) throws Exception;

	void alterResourceStatus_B(ApplicationContext applicationContext, String ticketCategory, Long ticketInstId, Map<String, Object> params) throws Exception;

	void alterResourceStatus_C(ApplicationContext applicationContext, List<Map<String, Object>> ticketResource, Map<String, Object> params, Boolean resourceFree, Long ticketInstId) throws Exception;

	void runnableStatus(ApplicationContext applicationContext, Map<String, Object> resourceMaps, Map<String, Object> ticketMaps, Boolean resourceFree, Long ticketInstId) throws Exception;

	List<Map<String,Object>> queryContractRemainingDays() throws Exception;




	}
