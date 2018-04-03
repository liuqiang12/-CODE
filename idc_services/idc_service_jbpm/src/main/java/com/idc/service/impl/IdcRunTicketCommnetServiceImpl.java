package com.idc.service.impl;

import com.idc.mapper.IdcRunTicketCommnetMapper;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcRunTicketCommnetMapper;
import com.idc.model.IdcRunTicketCommnet;
import com.idc.service.IdcRunTicketCommnetService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_COMMNET:运行时当前工单反馈意见表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRunTicketCommnetService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunTicketCommnetServiceImpl extends SuperServiceImpl<IdcRunTicketCommnet, Long> implements IdcRunTicketCommnetService {
	@Autowired
	private IdcRunTicketCommnetMapper mapper;
	/**
	 * 运行时的工单信息
	 * @param idcRunTicketCommnet
	 * @throws Exception
	 */
	public void createRunTicketCommnet(IdcRunTicketCommnet idcRunTicketCommnet) throws  Exception{
		mapper.createRunTicketCommnet(idcRunTicketCommnet);
	}
	/**
	 *
	 * @param ticketInstId
	 */
	@Override
	public IdcRunTicketCommnet queryTikectCommentByTicketInstId(Long ticketInstId){
		return mapper.queryTikectCommentByTicketInstId(ticketInstId);
	}
}
