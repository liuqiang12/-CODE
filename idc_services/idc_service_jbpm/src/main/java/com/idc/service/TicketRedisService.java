package com.idc.service;

import com.idc.model.IdcRunProcCment;

/**
 * 所有有关工单的业务上层业务接口集中区
 */
public interface TicketRedisService{
	/**
	 * 帆布相应的数据到相应的监听中
	 * @throws Exception
	 */
    Long handTicketComment(IdcRunProcCment idcRunProcCment) throws Exception;
}
