package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketResourceMapper;
import com.idc.mapper.IdcRunTicketResourceMapper;
import com.idc.model.*;
import com.idc.service.*;
import com.idc.utils.ResourceEnum;
import com.idc.utils.ServiceApplyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此时添加动态代理：针对保存方法进行处理
 */
@Service("idcrRunTicketResourceService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcrRunTicketResourceServiceImpl extends SuperServiceImpl<IdcRunTicketResource, Long>
		implements IdcRunTicketResourceService {
	@Autowired
	private IdcRunTicketResourceMapper idcRunTicketResourceMapper;
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
	@Autowired
	private NetPortService netPortService;
	/**
	 * 查询工单信息列表
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<IdcRunTicketResource> queryTicketRackResourceListExtraPage(PageBean<IdcRunTicketResource> page, Map<String, Object> map) {
		page.setParams(map);
		List<IdcRunTicketResource> list = idcRunTicketResourceMapper.queryTicketRackResourceListPage(page);
		/*分页显示*/

		return list;
	}
	@Override
	public List<IdcRunTicketResource> queryTicketResourceListExtraPage(PageBean<IdcRunTicketResource> page, Map<String, Object> map) {
		page.setParams(map);
		List<IdcRunTicketResource> list = idcRunTicketResourceMapper.queryTicketResourceListPage(page);
		/*分页显示*/

		return list;
	}


	/**
	 * 查询工单信息列表
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIdcMcbVo> queryTicketRackMCBResourceListExtraPage(PageBean<TicketIdcMcbVo> page, Map<String,Object> map) {
		page.setParams(map);
		List<TicketIdcMcbVo> list = idcRunTicketResourceMapper.queryTicketRackMCBResourceListPage(page);
		/*分页显示*/

		return list;
	}

	/**
	 * 查询工单信息列表
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketPortVo> queryTicketPortResourceListExtraPage(PageBean<TicketPortVo> page, Map<String, Object> map) {
		page.setParams(map);
		List<TicketPortVo> list = idcRunTicketResourceMapper.queryTicketPortResourceListPage(page);
		/*分页显示*/

		return list;
	}

	/**
	 * 根据工单查询端口信息
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketServerVo> queryTicketServerResourceListExtraPage(PageBean<TicketServerVo> page, Map<String, Object> map) {
		page.setParams(map);
		List<TicketServerVo> list = idcRunTicketResourceMapper.queryTicketServerResourceListPage(page);
		/*分页显示*/

		return list;
	}

	/**
	 * 根据工单查询ip信息
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIPVo> queryTicketIPSesourceListExtraPage(PageBean<TicketIPVo> page, Map<String, Object> map) {
		page.setParams(map);
		List<TicketIPVo> list = idcRunTicketResourceMapper.queryTicketIpResourceListPage(page);
		/*分页显示*/

		return list;
	}

	@Override
	public void editPortAssignation(Map<String, Object> paramMap) throws Exception {
		/*  第一步：修改工单资源表里面的信息  */
		String ticketInstId = String.valueOf(paramMap.get("ticketInstId"));   //工单id
		String resourceId = String.valueOf(paramMap.get("resourceId"));   //资源id
		String inputValues = String.valueOf(paramMap.get("inputValues"));   //手动输入的分配带宽值
		String resourceCategory = String.valueOf(paramMap.get("resourceCategory"));    //手动输入的分配带宽值

		//修改运行资源表
		idcRunTicketResourceMapper.updatePortResourceAssignation(ticketInstId,resourceId,resourceCategory,inputValues);
		//修改历史资源表
		idcHisTicketResourceMapper.updatePortResourceAssignation(ticketInstId,resourceId,resourceCategory,inputValues);

		/*只有 分配带宽 才需要修改资源那边的信息*/
		if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
			paramMap.put("assignation",inputValues);
			paramMap.put("portid",resourceId);
			/*  第二步：调用资源接口，修改资源方面的信息  */
			List<Map<String,Object>> listMap=new ArrayList<>();
			listMap.add(paramMap);
			netPortService.updateNetPortAssignations(listMap);
		}
	}

	@Override
	public Boolean portHaveAssignation(String ticketInstId) throws Exception {
		Boolean aBoolean = idcRunTicketResourceMapper.portHaveAssignation(ticketInstId);
		return aBoolean;
	}

	/*
             * 所有资源操作： 单条单条的删除
             * 机架机位、端口带宽、IP、主机  单条单条的删除合体方法
             * 20171019改造 WCG
             * */
	@Override
	public void deleteTicketResource(Map<String,Object> paramMap, ApplicationContext applicationContext) throws Exception {

		String resourceCategory = String.valueOf(paramMap.get("resourceCategory"));
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();

		//删除机架的时候，不仅仅是删除机架！！！还要删除机架上面关联的机位、MCB
		if(resourceCategory != null && ServiceApplyEnum.机架.value().equals(resourceCategory)){
			logger.debug("工单机架列表中点击[删除].................");
			remoteMapList = getRackResourceInfo(paramMap);

		}else if(resourceCategory != null && ServiceApplyEnum.MCB.value().equals(resourceCategory)){
			//*单独的MCB资源释放信息*//
			remoteMapList = getRackMCBResourceInfoByResourceId(paramMap);
		}else if(resourceCategory != null && ServiceApplyEnum.IP租用.value().equals(resourceCategory)){
			//*单独的IP资源释放信息*//*
			remoteMapList = getIPResourceInfoByResourceId(paramMap);
		}else if(resourceCategory != null && ServiceApplyEnum.主机租赁.value().equals(resourceCategory)){
			//*单独的IP资源释放信息*//*
			remoteMapList = getServerResourceInfoByResourceId(paramMap);
		}else if(resourceCategory != null && ServiceApplyEnum.端口带宽.value().equals(resourceCategory)){
			//*单独的IP资源释放信息*//*
			remoteMapList = getPortResourceInfoByResourceId(paramMap);
		}

		/********************************删除操作集中区[删除完成后给出提示信息]**********************************/
		/* 删除情况，如果是有父工单ID。则需要修改状态成-1标志成[删除状态] */

		idcRunTicketResourceMapper.calldDeleteTicketResource(paramMap);
		String rowcountStr = String.valueOf(paramMap.get("rowcount"));
		if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!成功处理!!!!!!!!!!msg:["+paramMap.get("msg")+"]");
		}else{
			//throw new Exception("!!!!!!!!!!!!!!!!!!!msg:["+paramMap.get("msg")+"]");
		}

		Integer rackOrRackUnit =(paramMap.get("rackOrRackUnit") != null&&!"".equals(paramMap.get("rackOrRackUnit"))) ? Integer.valueOf(paramMap.get("rackOrRackUnit").toString())  : null; //它允许为空

		for (Map<String,Object> wcgMaps:remoteMapList ) {
			String resourceCategoryWCG = wcgMaps.get("resourceCategory").toString();
			if( !(rackOrRackUnit != null && resourceCategoryWCG.equals(ServiceApplyEnum.机架.value()) && rackOrRackUnit.equals(ResourceEnum.按照机位分.value()) ) ){
				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,wcgMaps);
				applicationContext.publishEvent(remoteSysResourceEvent);
			}
		}
	}

	/**
	 * 删除机架情况
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> getRackResourceInfo(Map<String,Object> paramMap){
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		/*if(paramMap.get("rackOrracunit").equals(ResourceEnum.按照机位分.value().toString())){
			logger.debug("按照的是机位分资源，故删除的时候，直接组装机位的相关信息............");
		}*/
		logger.debug("按照的是机架分资源............");
		Map<String,Object> remoteMap = new HashMap<String,Object>();
		remoteMap.put("id",paramMap.get("resourceid"));//资源ID
		remoteMap.put("status",ResourceEnum.机架空闲.value());//资源状态
		remoteMap.put("ticketId",paramMap.get("ticketInstId"));//工单ID
		remoteMap.put("resourceCategory",paramMap.get("resourceCategory"));//资源类型，机架还是机位
		remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),paramMap.get("ticketInstId"));
		remoteMapList.add(remoteMap);

		/***mcb也需要释放相应的资源***/
		List<Map<String,Object>> remoteMCBMapList = getRackMCBResourceInfo(String.valueOf(paramMap.get("resourceid")),String.valueOf(paramMap.get("ticketInstId")));
		if(remoteMCBMapList != null && !remoteMCBMapList.isEmpty()){
			remoteMapList.addAll(remoteMCBMapList);
		}

 		/***U位也需要释放相应的资源***/
		List<Map<String,Object>> remoteUnitMapList = getRackUnitResourceInfo(String.valueOf(paramMap.get("resourceid")),String.valueOf(paramMap.get("ticketInstId")));
		if(remoteUnitMapList != null && !remoteUnitMapList.isEmpty()){
			remoteMapList.addAll(remoteUnitMapList);
		}
		return remoteMapList;
	}
	/**
	 * U位
	 * @param rackId
	 * @param ticketInstId
	 * @return
	 */
	public List<Map<String,Object>> getRackUnitResourceInfo(String rackId,String ticketInstId){
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		Map<String,Object> unitParamMap = new HashMap<String,Object>();
		unitParamMap.put("rackId",rackId);
		unitParamMap.put("ticketInstId",ticketInstId);

		List<Long> unitLongList = idcRunTicketResourceMapper.getUnitResourceIdsByTicketId(unitParamMap);

		if(unitLongList != null && !unitLongList.isEmpty()){
			for(int i = 0 ; i< unitLongList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",unitLongList.get(i));//资源ID
				remoteMap.put("status",ResourceEnum.U位空闲.value());//资源状态
				remoteMap.put("ticketId",ticketInstId);
				remoteMap.put("resourceCategory",ServiceApplyEnum.U位.value());
				remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),ticketInstId);
				remoteMap.put("rackId",rackId);
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}
	/**
	 * MCB
	 * @param rackId
	 * @param ticketInstId
	 * @return
	 */
	public List<Map<String,Object>> getRackMCBResourceInfo(String rackId,String ticketInstId){
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();

		List<Long> mcbLongList = idcRunTicketResourceMapper.getMCBIdByRackIdAndTicket(rackId,ticketInstId);
		if(mcbLongList != null && !mcbLongList.isEmpty()){
			for(int i = 0 ; i< mcbLongList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",mcbLongList.get(i));//资源ID
				remoteMap.put("status",ResourceEnum.MCB空闲.value());//资源状态
				remoteMap.put("ticketId",ticketInstId);
				remoteMap.put("resourceCategory",ServiceApplyEnum.MCB.value());
				remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),ticketInstId);
				remoteMap.put("rackId",rackId);
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}
	public List<Map<String,Object>> getRackMCBResourceInfoByResourceId(Map<String,Object> paramMap){
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		List<Map> mcbLongList = idcRunTicketResourceMapper.getResourceIdsByTicketId(paramMap);
		if(mcbLongList != null && !mcbLongList.isEmpty()){
			for(int i = 0 ; i< mcbLongList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",mcbLongList.get(i).get("RESOURCEID"));//资源ID
				remoteMap.put("status",ResourceEnum.MCB空闲.value());//资源状态
				remoteMap.put("ticketId",String.valueOf(paramMap.get("ticketInstId")));
				remoteMap.put("resourceCategory",ServiceApplyEnum.MCB);
				remoteMap.put("rackId",mcbLongList.get(i).get("RACKID"));
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}
	public List<Map<String,Object>> getIPResourceInfoByResourceId(Map<String,Object> paramMap){
		paramMap.put("resourceCategory",ServiceApplyEnum.IP租用.value());
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> ipMapList = idcRunTicketResourceMapper.getIpResourceIdsByTicketId(paramMap);

		String ticketCategory=null;  //工单类型
		if(paramMap.get("ticketCategory") != null){
			ticketCategory = paramMap.get("ticketCategory").toString();
		}

		if(ipMapList != null && !ipMapList.isEmpty()){
			for(int i = 0 ; i< ipMapList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",ipMapList.get(i).get("RESOURCEID"));//资源ID

				//如果是下线流程，应该是把IP的状态改为等待回收状态 。600下线状态
				if(ticketCategory != null && ticketCategory.equals("600")){
					remoteMap.put("status",ResourceEnum.IP空闲.value());//资源状态
				}else{
					remoteMap.put("status",ResourceEnum.IP空闲.value());//资源状态
				}

				remoteMap.put("ticketId",String.valueOf(paramMap.get("ticketInstId")));
				remoteMap.put("resourceCategory",ServiceApplyEnum.IP租用.value());
				remoteMap.put("ipType",ipMapList.get(i).get("IPTYPE"));
				remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),paramMap.get("ticketInstId"));
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}
	public List<Map<String,Object>> getServerResourceInfoByResourceId(Map<String,Object> paramMap){
		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		List<Map> ipLongList = idcRunTicketResourceMapper.getResourceIdsByTicketId(paramMap);
		if(ipLongList != null && !ipLongList.isEmpty()){
			for(int i = 0 ; i< ipLongList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",ipLongList.get(i).get("RESOURCEID"));//资源ID
				remoteMap.put("status",ResourceEnum.主机空闲.value());//资源状态
				remoteMap.put("ticketId",String.valueOf(paramMap.get("ticketInstId")));
				remoteMap.put("resourceCategory",ServiceApplyEnum.主机租赁.value());
				remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),String.valueOf(paramMap.get("ticketInstId")));
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}

	public List<Map<String,Object>> getPortResourceInfoByResourceId(Map<String,Object> paramMap){

		List<Map<String,Object>> remoteMapList = new ArrayList<Map<String,Object>>();
		List<Map> ipMapList = idcRunTicketResourceMapper.getResourceIdsByTicketId(paramMap);
		if(ipMapList != null && !ipMapList.isEmpty()){
			for(int i = 0 ; i< ipMapList.size(); i++){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("id",ipMapList.get(i).get("RESOURCEID"));//资源ID
				remoteMap.put("status",ResourceEnum.端口带宽空闲.value());//资源状态
				remoteMap.put("ticketId",String.valueOf(paramMap.get("ticketInstId")));
				remoteMap.put("resourceCategory",ServiceApplyEnum.端口带宽.value());
				remoteMap.put(ServiceApplyEnum.修改需要工单ID.value(),String.valueOf(paramMap.get("ticketInstId")));
				remoteMapList.add(remoteMap);
			}
		}
		return remoteMapList;
	}

	/**
	 * IP资源查询分页功能
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIPVo> queryIpResourceListPage(PageBean<TicketIPVo> page,Map<String,Object> map){
		page.setParams(map);
		List<TicketIPVo> list = idcRunTicketResourceMapper.queryIpResourceListPage(page);
		return list;
	}
	/**
	 * 查询机架列表信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<TicketIdcRackVo> queryRackResourceListPage(PageBean<TicketIdcRackVo> page,Map<String,Object> map){
		page.setParams(map);
		List<TicketIdcRackVo> list = idcRunTicketResourceMapper.queryRackResourceListPage(page);
		return list;
	}
	@Override
	public String loadRoomsWithTicket(Long ticketInstId){
		return idcRunTicketResourceMapper.loadRoomsWithTicket(ticketInstId);
	}

	@Override
	public List<String> loadRackIDWithTicket(Long ticketInstId){
		return idcRunTicketResourceMapper.loadRackIDWithTicket(ticketInstId);
	}

	@Override
	public List<IdcRunTicketResource> getIdcRunTicketResourceByCustomerId(Long ticketInstId) {
		return idcRunTicketResourceMapper.getIdcRunTicketResourceByCustomerId(ticketInstId);
	}

	@Override
	public String getSingleRackId(Long customerId) {
		return idcRunTicketResourceMapper.getSingleRackId(customerId);
	}

	@Override
	public Long getSingleBandWidth(Long ticketInstId) {
		return idcRunTicketResourceMapper.getSingleBandWidth(ticketInstId);
	}

	@Override
	public List<IdcRunTicketResource> loadIpTranList(Long ticketInstId) {
		return idcRunTicketResourceMapper.loadIpTranList(ticketInstId);
	}


}


