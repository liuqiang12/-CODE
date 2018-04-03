package com.idc.service;


import com.idc.model.*;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_RESOURCE:历史的工单资源中间表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketResourceService extends SuperService<IdcHisTicketResource, Long>{
	/**
	 * 查询工单信息列表
	 * @param page
	 * @param map
	 * @return
	 */
    List<IdcHisTicketResource> queryTicketRackResourceListExtraPage(PageBean<IdcHisTicketResource> page, Map<String, Object> map) ;
	List<IdcHisTicketResource> queryTicketResourceListExtraPage(PageBean<IdcHisTicketResource> page, Map<String, Object> map) ;

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
	/**
	 * 查询可用的主机设备信息 分页情况
	 * @param page
	 * @param map
	 * @return
	 */
    List<WinServerVo> queryServerResourceListPage(PageBean<WinServerVo> page, Map<String, Object> map);

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

	/**
	 * 通过资源ID 查询资源名称
	 * @param resourceId
	 * @return
	 */
	String getNameByResourceId(Long resourceId, String categroy);


	/*	NetPort 通过客户的名称查询  工单id+资源端口名称*/
	List<Map<String,Object>> queryTicketIdAndResourceNameByNetPort(String customerName, Integer rowNum) throws Exception;
	List<Map<String,Object>> queryTicketIdAndResourceNameByNetPortPage(PageBean<T> page, Object param) throws Exception;

	/*	Rack 通过客户的名称查询  工单id+资源端口名称*/
	List<Map<String,Object>> queryTicketIdAndResourceNameByRack(String customerName, Integer rowNum) throws Exception;
	List<Map<String,Object>> queryTicketIdAndResourceNameByRackPage(PageBean<T> page, Object param) throws Exception;

	/*	IP 通过客户的名称查询  工单id+资源端口名称*/
	List<Map<String,Object>> queryTicketIdAndResourceNameByIP(String customerName, Integer rowNum) throws Exception;
	List<Map<String,Object>> queryTicketIdAndResourceNameByIPPage(PageBean<T> page, Object param) throws Exception;


}
