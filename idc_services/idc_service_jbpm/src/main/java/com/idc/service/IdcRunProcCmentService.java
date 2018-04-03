package com.idc.service;


import com.idc.model.IdcReProduct;
import com.idc.model.IdcRunProcCment;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RUN_PROC_CMENT:当前工单意见信息IDC_RUN_TICKET_PROC_COMMENT<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunProcCmentService {
	/**
	 * 通过流程实例ID 和 任务ID 获取审批意见信息
	 * @param procInstId
	 * @param taskid
	 * @return
	 */
    IdcRunProcCment queryCommentByProcInstIdAndTaskId(Long procInstId, String taskid);
	/**
	 *
	 * @param ticketInstId:工单ID
	 * @return
	 */
    List<IdcRunProcCment> queryRunProcCommentQueryData(String ticketInstId);

	/**
	 * 当工单结束时，处理审批意见信息
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	Long jbpmEndComment(IdcRunProcCment idcRunProcCment) throws Exception;

	/**
	 * 保存历史审批信息
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	void mergeDataInto(ApplicationContext context, Map map, IdcRunProcCment idcRunProcCment, Map<String, Object> createTicketMap) throws Exception;
	/**
	 * 处理当中主要是由审批记录作为负载体
	 * @return
	 * @throws Exception
	 */
    Long handlerRunTikcetProcess(ApplicationContext context, IdcRunProcCment idcRunProcCment, IdcReProduct idcReProduct) throws Exception;
	//完成流程代理过程

	Map<String,Object> rubbishOrDeleteTicket(IdcRunProcCment idcRunProcCment, ApplicationContext applicationContext, Map<String, Object> params) throws Exception;

	/**
	 * 将审批的东西都保存到缓冲中
	 * @return
	 */
	List<IdcRunProcCment> getIdcRunProcCmentDataIntoRedis();
	List<Long> getIdsByProdIdAndTicketId(Long prodInstId, Long ticketInstId);

	Boolean deleteTicketQuerySon(Long ticketInstId, Long prodInstId);

	IdcRunProcCment queryByTicketInstId(Long ticketInstId);

}
