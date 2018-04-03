package com.idc.mapper;



import com.idc.model.IdcHisProcCment;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_PROC_CMENT:历史工单意见信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisProcCmentMapper extends SuperMapper<IdcHisProcCment, Long>{
	/**
	 * 合体方法
	 * @param idcHisProcCment
	 * @throws Exception
	 */
	void mergeInto(IdcHisProcCment idcHisProcCment) throws Exception;
	void insertIntoProcCment(IdcHisProcCment idcHisProcCment) throws Exception;


	//通过ticketInstId查询备注信息
	Map<String,Object> getDataByTicketInstId(Long ticketInstId);
	/**
	 *
	 * @param ticketInstId:工单ID
	 * @return
	 */
	List<IdcHisProcCment> queryHisProcCommentQueryData(@Param("ticketInstId") String ticketInstId);

	List<Long> getIdsByProdIdAndTicketId(@Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);

	void callReturnRubbishOrDeleteTicketID(Map<String, Object> params);

	Long deleteTicketQuerySon(@Param("ticketInstId") Long ticketInstId, @Param("prodInstId") Long prodInstId);
}

 