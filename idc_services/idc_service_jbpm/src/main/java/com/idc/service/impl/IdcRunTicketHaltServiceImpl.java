package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketHaltMapper;
import com.idc.mapper.IdcRunTicketHaltMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idc.model.*;
import com.idc.service.ActJBPMService;
import modules.utils.ResponseJSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcRunTicketHaltMapper;
import com.idc.service.IdcRunTicketHaltService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_HALT:运行时下线工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRunTicketHaltService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunTicketHaltServiceImpl extends JbpmEntranceServiceImpl implements IdcRunTicketHaltService {
	@Autowired
	private IdcRunTicketHaltMapper mapper;
	@Autowired
	private IdcRunTicketHaltMapper idcRunTicketHaltMapper;
	@Autowired
	private IdcHisTicketHaltMapper idcHisTicketHaltMapper;
	@Autowired
	private ActJBPMService actJBPMService;
	/**
	 *
	 * @param idcRunProcCment
	 * @param createTicketMap
	 * @throws Exception
	 */
	public void mergeDataInto(ApplicationContext context,Map map, IdcRunProcCment idcRunProcCment, Map<String,Object> createTicketMap) throws  Exception{
		/*这里先创建订单*/
		if(createTicketMap != null && !createTicketMap.isEmpty()){
			Long prodInstId =  Long.valueOf(String.valueOf(createTicketMap.get("prodInstId")));
			Integer prodCategory =  Integer.valueOf(String.valueOf(createTicketMap.get("prodCategory")));
			String catalog =  String.valueOf(createTicketMap.get("category"));

			ResponseJSON responseJSON = actJBPMService.singleCreateTicket(context,map,prodInstId,prodCategory,null,null,catalog,idcRunProcCment.getTicketInstId());
			Boolean isSuccess= responseJSON.isSuccess();
			if(isSuccess){
				Long ticketInstId =  Long.valueOf(String.valueOf(responseJSON.getResult()));
				idcRunProcCment.setTicketInstId(ticketInstId);
				haltHandlerData(idcRunProcCment);
			}
		}
	}

	@Override
	public void handHaltTicket(ApplicationContext context, IdcRunProcCment idcRunProcCment,IdcRunTicketHalt idcRunTicketHalt) throws Exception {
		/*创建下线工单。首先修改下线信息:首先开启工单。然后*/
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("prodInstId",idcRunTicketHalt.getIdcRunTicket().getProdInstId());
		variables.put("ticketCategoryFrom",idcRunTicketHalt.getIdcRunTicket().getTicketCategoryFrom());
		variables.put("ticketCategoryTo",idcRunTicketHalt.getIdcRunTicket().getTicketCategoryTo());
		variables.put("parentId",idcRunTicketHalt.getIdcRunTicket().getTicketInstId());
		variables.put("prodCategory",idcRunTicketHalt.getIdcRunTicket().getProdCategory());

		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
		if(responseJSON.isSuccess()) {
			Map<String, Object> responseJSONMap = (Map) responseJSON.getResult();
			Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
			//然后直接update。下线信息
			idcRunTicketHalt = idcRunProcCment.getIdcRunTicketHalt();
			idcRunTicketHalt.setTicketInstId(ticketInstId);
			idcRunTicketHaltMapper.updateByIdcRunTicketHalt(idcRunTicketHalt);
			IdcHisTicketHalt idcHisTicketHalt = new IdcHisTicketHalt();
			BeanUtils.copyProperties(idcRunTicketHalt,idcHisTicketHalt);
			idcHisTicketHaltMapper.updateByIdcHisTicketHalt(idcHisTicketHalt);
		}

	}

	@Override
	public IdcRunTicketHalt queryIdcTicketHalt(Long ticketInstId) {
		return idcRunTicketHaltMapper.queryIdcTicketHalt(ticketInstId);
	}

	/**
	 * 停机信息展示
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	public void haltHandlerData(IdcRunProcCment idcRunProcCment) throws  Exception{
		IdcRunTicketHalt idcRunTicketHalt=new IdcRunTicketHalt();
		idcRunTicketHalt.setTicketInstId(idcRunProcCment.getTicketInstId());
		//添加备注信息对应idcRunProcCment的applydesc字段
		idcRunTicketHalt.setApplydesc(idcRunProcCment.getApplydesc());
		//添加退订原因haltreason对应idcRunProcCment的comment字段
		idcRunTicketHalt.setHaltreason(idcRunProcCment.getComment());

		IdcHisTicketHalt idcHisTicketHalt=new IdcHisTicketHalt();
		//把复通运行表的数据复制到停机历史表中
		BeanUtils.copyProperties(idcRunTicketHalt,idcHisTicketHalt);
		//开始持久化到数据库中
		idcRunTicketHaltMapper.updateByData(idcRunTicketHalt);
		idcHisTicketHaltMapper.updateByData(idcHisTicketHalt);
	}
}
