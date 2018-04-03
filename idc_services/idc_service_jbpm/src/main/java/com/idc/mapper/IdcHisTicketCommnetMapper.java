package com.idc.mapper;



import java.math.BigDecimal;

import com.idc.model.IdcRunTicketCommnet;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcHisTicketCommnet;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_COMMNET:历史工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketCommnetMapper extends SuperMapper<IdcHisTicketCommnet, Long>{
	/**
	 * 历史工单信息
	 * @param idcHisTicketCommnet
	 * @throws Exception
	 */
    void createHisTicketCommnet(IdcHisTicketCommnet idcHisTicketCommnet) throws  Exception;

	/**
	 * 修改工单评价信息
	 * @param idcHisTicketCommnet
	 * @throws Exception
	 */
	void mergeInto(IdcHisTicketCommnet idcHisTicketCommnet) throws Exception;

	IdcHisTicketCommnet queryHisTikectCommentByTicketInstId(Long ticketInstId);
}

 