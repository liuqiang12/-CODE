package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.*;
import com.idc.utils.*;
import modules.utils.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import system.RestUtil;
import system.data.supper.service.impl.SuperServiceImpl;
import system.rest.ResultObject;
import utils.DevContext;

import java.util.*;

@Service("remoteSysResourceService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class RemoteSysResourceServiceImpl implements RemoteSysResourceService,ApplicationContextAware  {
	private Logger logger = LoggerFactory.getLogger(RemoteSysResourceServiceImpl.class);
	@Override
	public void handTicketResourceAndRemoteCall(Long ticketInstId,Map<String,Object> params) throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ticketInstId",ticketInstId);
		IdcRunTicket ticketObj = idcRunTicketMapper.getIdcRunTicketByIdTicketInstId(ticketInstId);
		String ticketCategory = ticketObj.getTicketCategory();
		/*客户必须有*/
		Long customerId = ticketObj.getCustomerId();
		if(customerId == null ){
			throw new Exception("客户ID不能是空!");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ticketInstId",ticketInstId);
		/*必须传递工单的状态如:预勘流程、开通流程*/
		if(params == null || ticketInstId == null
				|| !params.containsKey("category")
				|| !params.containsKey("resourceCategory")
				|| !params.containsKey("status")
				|| !params.containsKey("resourceid")){
			throw new Exception("ticketInstId、category、resourceCategorystatus、ticketInstId、status、resourceid出现问题");
		}

 		map.putAll(params);
		idcHisTicketResourceMapper.callUpdateTicketResourceAttribute(map);
		String rowcountStr = String.valueOf(map.get("rowcount"));  //此值是从判断是否保存资源到工单资源表成功的标志
		if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+map.get("msg")+"]");
		}else {
			/*如果是已经存在则*/
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:[" + map.get("msg") + "]");
			throw new Exception();
		}

		String resourceCategory = String.valueOf(params.get("resourceCategory"));  //此值不能为空。资源的状态
		String rackOrRackUnit = String.valueOf(params.get("rackOrRackUnit"));  //分配机架的时候，资源是机架还是机位

		/*------------importance-----start-------------如果选择的是机架，并且是按照U位分配，就不需要修改机架的状态 -------------------------*/
		if(  !( resourceCategory.equals(ServiceApplyEnum.机架.value()) && rackOrRackUnit.equals(ResourceEnum.按照机位分.value().toString()) )  ){
			updateRemoteResoureStaus(ticketObj,map);
		}
		/*------------importance-----end-------------如果选择的是机架，并且是按照U位分配，就不需要修改机架的状态 -------------------------*/

		/*----importance----start----------如果选择机架，机架如果自带了MCB，需要修改状态-----------------------------------------------*/
		if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
			Map<String,String> MCBParams=new HashMap<>();
			MCBParams.put("customerName",ticketObj.getCustomerName());
			MCBParams.put("customerId",ticketObj.getCustomerId().toString());

			updateRackOwnMCB(Long.valueOf(params.get("resourceid").toString()),ticketInstId,MCBParams,ticketCategory);
		}
		/*------importance---end---------如果选择机架，机架如果自带了MCB，需要修改状态---------------------------------------*/

	}

	/**
	 * 开始循环修改
	 * @param idcRunTicket
	 * @throws Exception
	 */
	public void updateRemoteResoureStaus(IdcRunTicket idcRunTicket,Map<String,Object> params) throws Exception{
		/**
		 * 分一下两步走
		 * 1:没有保存到[工单资源表]的情况
		 *  通过[工单ID、资源ID，资源类型[机位]]作为参数传递(直接传递原始资源表接口)
		 * 2:保存到【工单资源表】的情况
		 * 	通过[工单ID、资源ID、资源类型(机架、IP等)]作为参数传递=定位资源(从工单资源中间表定位)
		 */
		String resourceCategory = String.valueOf(params.get("resourceCategory"));
		Map<String,Object> remoteMap = new HashMap<String,Object>();
		remoteMap.put("id",params.get("resourceid"));//资源ID
		remoteMap.put("status",params.get("remoteResourcestatus"));//资源状态
		remoteMap.put("ticketId",params.get("ticketInstId"));//工单ID
		remoteMap.put("customerId",idcRunTicket.getCustomerId());//客户ID
		remoteMap.put("customerName",idcRunTicket.getCustomerName());//客户ID
		remoteMap.put("resourceCategory",resourceCategory);//资源类型，机架还是机位
		remoteMap.put("rackId",params.get("rackId"));  //此值可以为空
		remoteMap.put("ipType",params.get("ipType"));  //此值可以为空

		RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteMap);
		applicationContext.publishEvent(remoteSysResourceEvent);

	}

	//修改机架自带的MCB的状态
	public void updateRackOwnMCB(Long rackId,Long ticketInstId,Map<String,String> MCBParams,String tikcetCategory) throws Exception{

		List<Long> MCBList = idcRunTicketMapper.getMCBByRackId(rackId);
		if(MCBList !=null && !MCBList.isEmpty()){
			for (Long MCBId: MCBList ){
				Map<String,Object> remoteMap = new HashMap<String,Object>();
				remoteMap.put("ticketId",ticketInstId);//工单ID
				remoteMap.put("rackId",rackId);//机架id
				remoteMap.put("id",MCBId);//资源ID
				remoteMap.put("customerId",MCBParams.get("customerId"));//客户ID
				remoteMap.put("customerName",MCBParams.get("customerName"));//客户ID
				remoteMap.put("resourceCategory",ServiceApplyEnum.MCB.value());//MCB信息

				if(tikcetCategory.equals(CategoryEnum.预堪预占流程.value())) {
					remoteMap.put("status",ResourceEnum.MCB预占.value());//MCB状态信息，因为是添加，所以可以写死
				}else{
					remoteMap.put("status",ResourceEnum.MCB占用.value());//MCB状态信息，因为是添加，所以可以写死
				}

				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteMap);
				applicationContext.publishEvent(remoteSysResourceEvent);
			}
		}

	}
	private static ApplicationContext applicationContext;

	@Autowired
	private IdcRunTicketMapper idcRunTicketMapper;
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		RemoteSysResourceServiceImpl.applicationContext = applicationContext;
	}
	public ApplicationContext getLocalApplicationContext() {
		return applicationContext;
	}

}

