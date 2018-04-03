package com.idc.service;

import com.idc.model.*;

import java.util.List;

/**
 * 所有有关工单的业务上层业务接口集中区
 */
public interface LocalTicketRedisService {
	/**
	 *
	 * @param idcRunTicketList
	 * @throws Exception
	 */
	void runTicketIntoRedis(List<IdcRunTicket> idcRunTicketList) throws Exception;
	/**
	 *
	 * @param idcHisTicketList
	 * @throws Exception
	 */
	void hisTicketIntoRedis(List<IdcHisTicket> idcHisTicketList) throws Exception;

	/**
	 *
	 * @param contractList
	 * @throws Exception
	 */
	void contractIntoRedis(List<IdcContract> contractList) throws Exception;
	/**
	 *
	 * @param runProcCmentList
	 * @throws Exception
	 */
	void runProcCmentIntoRedis(List<IdcRunProcCment> runProcCmentList) throws Exception;
	/**
	 *
	 * @param hisProcCmentList
	 * @throws Exception
	 */
	void hisProcCmentIntoRedis(List<IdcHisProcCment> hisProcCmentList) throws Exception;
	/**
	 *
	 * @param runTicketResourceList
	 * @throws Exception
	 */
	void runTicketResourceIntoRedis(List<IdcRunTicketResource> runTicketResourceList) throws Exception;
	/**
	 *
	 * @param hisTicketResourceList
	 * @throws Exception
	 */
	void hisTicketResourceIntoRedis(List<IdcHisTicketResource> hisTicketResourceList) throws Exception;

}
