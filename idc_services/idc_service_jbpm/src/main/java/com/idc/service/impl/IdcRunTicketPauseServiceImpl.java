package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketRecoverMapper;
import com.idc.mapper.IdcRunTicketPauseMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.idc.mapper.IdcRunTicketRecoverMapper;
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

import com.idc.mapper.IdcRunTicketPauseMapper;
import com.idc.service.IdcRunTicketPauseService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_PAUSE:运行时停机工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRunTicketPauseService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunTicketPauseServiceImpl extends SuperServiceImpl<IdcRunTicketPause, Long> implements IdcRunTicketPauseService {
	@Autowired
	private IdcRunTicketPauseMapper mapper;
	@Autowired
	private ActJBPMService actJBPMService;
	@Autowired
	private IdcRunTicketRecoverMapper idcRunTicketRecoverMapper;//复通运行表
	@Autowired
	private IdcHisTicketRecoverMapper idcHisTicketRecoverMapper;//复通历史表
	@Autowired
	private IdcRunTicketPauseMapper idcRunTicketPauseMapper;//复通历史表

	public void mergeDataInto(ApplicationContext applicationContext,Map map, IdcRunProcCment idcRunProcCment, Map<String,Object> createTicketMap) throws Exception{
		/*这里先创建订单*/
		if(createTicketMap != null && !createTicketMap.isEmpty()){
			Long prodInstId =  Long.valueOf(String.valueOf(createTicketMap.get("prodInstId")));
			Integer prodCategory =  Integer.valueOf(String.valueOf(createTicketMap.get("prodCategory")));
			String catalog =  String.valueOf(createTicketMap.get("category"));

			ResponseJSON responseJSON = actJBPMService.singleCreateTicket(applicationContext,map,prodInstId,prodCategory,null,null,catalog,idcRunProcCment.getTicketInstId());
			Boolean isSuccess= responseJSON.isSuccess();
			if(isSuccess){
				Long ticketInstId =  Long.valueOf(String.valueOf(responseJSON.getResult()));
				idcRunProcCment.setTicketInstId(ticketInstId);
				recoverHandlerData(idcRunProcCment);
			}
		}else{
			recoverHandlerData(idcRunProcCment);
		}
	}
	/**
	 * 停机信息展示
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	public void recoverHandlerData(IdcRunProcCment idcRunProcCment) throws  Exception{
		/*自行处理*/
		IdcRunTicketRecover idcRunTicketRecover=new IdcRunTicketRecover();
		idcRunTicketRecover.setTicketInstId(idcRunProcCment.getTicketInstId());
		//添加开始复通时间 recovertimeStr
		idcRunTicketRecover.setRecovertime(idcRunProcCment.getReservestart());
		//添加备注信息
		idcRunTicketRecover.setApplydesc(idcRunProcCment.getComment());

		IdcHisTicketRecover idcHisTicketRecover=new IdcHisTicketRecover();
		//把停机运行表的数据复制到停机历史表中
		BeanUtils.copyProperties(idcRunTicketRecover,idcHisTicketRecover);
		//开始持久化到数据库中
		idcRunTicketRecoverMapper.updateByData(idcRunTicketRecover);
		idcHisTicketRecoverMapper.updateByData(idcHisTicketRecover);
	}

	@Override
	public IdcRunTicketPause queryByTicketInstId(Long ticketInstId){
		return idcRunTicketPauseMapper.queryByTicketInstId(ticketInstId);
	}

}
