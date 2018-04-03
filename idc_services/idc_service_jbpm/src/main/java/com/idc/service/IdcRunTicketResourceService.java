package com.idc.service;


import com.idc.model.*;
import org.springframework.context.ApplicationContext;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


public interface IdcRunTicketResourceService extends SuperService<IdcRunTicketResource, Long>{
	/**
	 * 查询工单信息列表
	 * @param page
	 * @param map
	 * @return
	 */
    List<IdcRunTicketResource> queryTicketRackResourceListExtraPage(PageBean<IdcRunTicketResource> page, Map<String, Object> map) ;
	List<IdcRunTicketResource> queryTicketResourceListExtraPage(PageBean<IdcRunTicketResource> page, Map<String, Object> map) ;


	/**
	 * 查询工单信息列表
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketIdcMcbVo> queryTicketRackMCBResourceListExtraPage(PageBean<TicketIdcMcbVo> page, Map<String, Object> map) ;

	/**
	 * 根据工单查询端口信息
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketPortVo> queryTicketPortResourceListExtraPage(PageBean<TicketPortVo> page, Map<String, Object> map);


	/**
	 * 根据工单查询端口信息
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketServerVo> queryTicketServerResourceListExtraPage(PageBean<TicketServerVo> page, Map<String, Object> map);

	/**
	 * 根据工单查询ip信息
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketIPVo> queryTicketIPSesourceListExtraPage(PageBean<TicketIPVo> page, Map<String, Object> map);


   /* void foreachSaveInfo(String winServerVoListStr,Map<String,Object> map) throws Exception;*/

	void deleteTicketResource(Map<String, Object> paramMap, ApplicationContext applicationContext) throws  Exception;

	Boolean portHaveAssignation(String ticketInstId) throws  Exception;

	void editPortAssignation(Map<String, Object> paramMap) throws  Exception;


	/**
	 * IP资源查询分页功能
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketIPVo> queryIpResourceListPage(PageBean<TicketIPVo> page, Map<String, Object> map);

	/**
	 * 查询机架列表信息
	 * @param page
	 * @param map
	 * @return
	 */
    List<TicketIdcRackVo> queryRackResourceListPage(PageBean<TicketIdcRackVo> page, Map<String, Object> map);
	String loadRoomsWithTicket(Long ticketInstId);
	List<String> loadRackIDWithTicket(Long ticketInstId);

	List<IdcRunTicketResource> getIdcRunTicketResourceByCustomerId(Long ticketInstId);
	String getSingleRackId(Long customerId);
	Long getSingleBandWidth(Long ticketInstId);
	List<IdcRunTicketResource> loadIpTranList(Long ticketInstId);
}
