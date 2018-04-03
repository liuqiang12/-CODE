package com.idc.mapper;


import com.idc.model.IdcRunTicket;
import com.idc.model.TicketResourceAllocation;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET:运行工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketMapper extends SuperMapper<IdcRunTicket, Long>{
	/**
	 * 生成运行时工单
	 * @param idcRunTicket
	 * @return
	 * @throws Exception
	 */
    void createRunTicketInst(IdcRunTicket idcRunTicket) throws  Exception;

    /**
	 * 作废或者删除单子，直接调用存储过程
	 * @return
	 * @throws Exception
	 */
    void rubbishTicketDeleteCall(Map<String, Object> params) throws  Exception;

	IdcRunTicket getIdcRunTicketByIdTicketInstIdForPerission(@Param("ticketInstId") Long ticketInstId, @Param("loginId") Integer loginId);

	Boolean isRejectByTicketID(String ticketInstId);

	/**
	 * 删除或者作废单子 之前需要查询到工单的资源，然后对其修改
	 * @return
	 * @throws Exception
	 */
    List<Map<String,Object>> rubbishDeleteAfterQuery(Long ticketInstId) throws  Exception;

	/**
	 * 修改实例ID和流程实例Id
	 * @param ticketInstId:工单实例ID
	 * @param procInstId:流程实例ID
	 * @return
	 * @throws Exception
	 */
    void updateTicketInstIdAndProcInstById(@Param("id") Long ticketInstId, @Param("procInstId") String procInstId) throws  Exception;

	/**
	 * 清除运行时的工单信息
	 * @param ticketInstId
	 * @throws Exception
	 */
    void callClearRunTicket(Long ticketInstId) throws Exception;

	/**
	 * 获取当前运行工单信息
	 * @param ticketInstId
	 * @return
	 */
    IdcRunTicket getNextIdcRunTicketByTicketInstId(@Param("category") String category, @Param("ticketInstId") Long ticketInstId);
	/**
	 * 获取当前运行工单信息
	 * @param ticketInstId
	 * @return
	 */
	Map<String,Object> getCustomerByTicketInstId(Long ticketInstId);

	/**
	 * 获取当前工单的序列号
	 * @param id
	 * @return
	 */
    String getSerialNumber(Long id);

	/**
	 * 拷贝资源到新的工单中
	 * @param prodInstId
	 * @param ticketInstId
	 */
	void callProcTicketContractCopyRes(@Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId, @Param("category") String category);

	/**
	 * 创建工单信息

	 * @throws Exception
	 */
    void callCreateCategoryTicket(Map<String, Object> proc_map_params) throws Exception;

	void updateTaskByTicketIdAndProcInstId(@Param("ticketInstId") Long ticketInstId, @Param("procInstId") Long procInstId, @Param("TASK_ID") String TASK_ID, @Param("TASK_NAME") String TASK_NAME, @Param("FORM_KEY") String FORM_KEY, @Param("PROC_DEF_ID") String PROC_DEF_ID, @Param("PROCESSDEFINITONKEY") String PROCESSDEFINITONKEY) throws Exception;

	/**
	 * 创建工单信息
	 */
    void callCreateInitTicket(Map<String, Object> proc_init_map_params) throws Exception;

	void callTicketTaskNodeCompleteData(Map<String, Object> ticketTaskNodeCompleteMap) throws Exception;
	void callTicketTaskComplete(Map<String, Object> ticketTaskCompleteMap) throws Exception;

	IdcRunTicket getIdcRunTicketByIdTicketInstId(Long TicketInstId);

	//通过申请人的名称查询此申请人所有的发起的工单
	List<Map<String,Object>> queryApplyTicketForCustomerView(Map map);

	/**
	 *
	 * @param ticketInstId
	 * @return
	 */
	IdcRunTicket getTicketObjMapByTicketInstId(Long ticketInstId);

	/**
	 * 将正在运行的工单都放入到redis中
	 * @return
	 */
    List<IdcRunTicket> getRunTicketDataIntoRedis();

	List<IdcRunTicket> getIdcRunTicketByProductId(Long productId);

	void callRubbishOrDeleteTicket(Map<String, Object> map) throws Exception;

	List<Long> getResourceIdsByTicketId(Map<String, Object> map);

	List<Long> getMCBByRackId(Long rackId);

	List<Map<String,Object>> getIpResourceIdsByTicketId(Map<String, Object> map);

	String getTicketCategoryByProdInstId(@Param("prodInstId") String prodInstId, @Param("ticketInstId") Long ticketInstId);

	List<Map<String,Object>> getAllResourceByProdInstId(String ticketInstId);

	List<Map<String,Object>> getCandidateEmail(String ticketInstId);

	String getTicketCategoryById(String ticketInstId);

	String getFormkeyByTicketId(String ticketInstId);

	Long getTicketResourceCount(Long ticketInstId);
	void callJbpmTicketEntrancet(Map<String, Object> map) throws Exception;

	void callUpdateORCopyTicketInfo(Map<String, Object> map) throws Exception;

	String getTaskName(String ticketInstId);

	Boolean getIsLastStepTicket(String FormKey);

	Boolean getIsFirstStepTicket(String FormKey);

	TicketResourceAllocation getTicketResourceAllocation(String formKey);

	Boolean getIsHasOpenTicket(@Param("ticketInstId") Long ticketInstId, @Param("ticketStatus") Integer ticketStatus);

	Boolean getIsHasOpenTicketEdit(@Param("ticketInstId") Long ticketInstId, @Param("ticketStatus") Integer ticketStatus);

}


