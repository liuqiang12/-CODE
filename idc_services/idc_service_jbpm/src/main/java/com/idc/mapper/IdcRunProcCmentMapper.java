package com.idc.mapper;



import com.idc.model.IdcRunProcCment;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_PROC_CMENT:当前工单意见信息IDC_RUN_TICKET_PROC_COMMENT<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunProcCmentMapper extends SuperMapper<IdcRunProcCment, Long>{
	/**
	 * 通过流程实例ID 和 任务ID 获取审批意见信息
	 * @param procInstId
	 * @param taskid
	 * @return
	 */
	IdcRunProcCment queryCommentByProcInstIdAndTaskId(@Param("procInstId") Long procInstId, @Param("taskid") String taskid);
	/**
	 * 合体方法
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	void mergeInto(IdcRunProcCment idcRunProcCment) throws Exception;
	void insertIntoProcCment(IdcRunProcCment idcRunProcCment) throws Exception;

	/**
	 *
	 * @param ticketInstId:工单ID
	 * @return
	 */
	List<IdcRunProcCment> queryRunProcCommentQueryData(@Param("ticketInstId") String ticketInstId);


	Map<String,Object> getDataByTicketInstId(Long ticketInstId);

	List<IdcRunProcCment> getIdcRunProcCmentDataIntoRedis();

	List<Long> getIdsByProdIdAndTicketId(@Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);

	IdcRunProcCment queryByTicketInstId(Long ticketInstId);
}
