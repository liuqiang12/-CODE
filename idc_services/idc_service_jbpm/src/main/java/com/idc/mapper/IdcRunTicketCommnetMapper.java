package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcRunTicketCommnet;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_COMMNET:运行时当前工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRunTicketCommnetMapper extends SuperMapper<IdcRunTicketCommnet, Long>{
	/**
	 * 运行时的工单信息
	 * @param idcRunTicketCommnet
	 * @throws Exception
	 */
    void createRunTicketCommnet(IdcRunTicketCommnet idcRunTicketCommnet) throws  Exception;
	/**
	 *
	 * @param ticketInstId
	 */
    IdcRunTicketCommnet queryTikectCommentByTicketInstId(Long ticketInstId);

	/**
	 * 修改工单评价信息
	 * @param idcRunTicketCommnet
	 * @throws Exception
	 */
    void mergeInto(IdcRunTicketCommnet idcRunTicketCommnet) throws Exception;
}

 