package com.idc.service;


import com.idc.model.IdcHisProcCment;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HIS_PROC_CMENT:历史工单意见信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisProcCmentService extends SuperService<IdcHisProcCment, Long>{
	/**
	 *
	 *
	 * @param ticketInstId:工单ID
	 * @return
	 */
	List<IdcHisProcCment> queryHisProcCommentQueryData(String ticketInstId);
	List<Long> getIdsByProdIdAndTicketId(Long prodInstId, Long ticketInstId);
}
