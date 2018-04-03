package com.idc.service.impl;

import com.idc.mapper.IdcRunTicketMapper;
import java.math.BigDecimal;
import java.util.List;

import com.idc.model.TicketResourceAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcRunTicketMapper;
import com.idc.model.IdcRunTicket;
import com.idc.service.IdcRunTicketService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET:运行工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRunTicketService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunTicketServiceImpl extends SuperServiceImpl<IdcRunTicket, Long> implements IdcRunTicketService {
	@Autowired
	private IdcRunTicketMapper idcRunTicketMapper;

	@Override
	public IdcRunTicket getIdcRunTicketByIdTicketInstId(Long TicketInstId) {
		return idcRunTicketMapper.getIdcRunTicketByIdTicketInstId(TicketInstId);
	}

	@Override
	public IdcRunTicket getIdcRunTicketByIdTicketInstIdForPerission(Long ticketInstId, Integer loginId) {
		return idcRunTicketMapper.getIdcRunTicketByIdTicketInstIdForPerission(ticketInstId,loginId);
	}

	/**
	 * 将所有的历史工单都保存到缓存中
	 * @return
	 */
	@Override
	public List<IdcRunTicket> getRunTicketDataIntoRedis(){
		return idcRunTicketMapper.getRunTicketDataIntoRedis();
	}

	@Override
	public List<IdcRunTicket> getIdcRunTicketByProductId(Long productId) {
		return idcRunTicketMapper.getIdcRunTicketByProductId(productId);
	}

	@Override
	public String getTicketCategoryById(String ticketInstId) {
		return idcRunTicketMapper.getTicketCategoryById(ticketInstId);
	}

	@Override
	public String getFormkeyByTicketId(String ticketInstId) {
		return idcRunTicketMapper.getFormkeyByTicketId(ticketInstId);
	}


	@Override
	public Long getTicketResourceCount(Long ticketInstId) {
		return idcRunTicketMapper.getTicketResourceCount(ticketInstId);
	}

	@Override
	public TicketResourceAllocation getTicketResourceAllocation(String formKey) {
		return idcRunTicketMapper.getTicketResourceAllocation(formKey);
	}

	@Override
	public Boolean getIsLastStepTicket(String FormKey) {
		return idcRunTicketMapper.getIsLastStepTicket(FormKey);
	}

	@Override
	public Boolean getIsHasOpenTicket(Long ticketInstId, Integer ticketStatus) {
		return idcRunTicketMapper.getIsHasOpenTicket(ticketInstId,ticketStatus);
	}

	@Override
	public Boolean getIsHasOpenTicketEdit(Long ticketInstId, Integer ticketStatus) {
		return idcRunTicketMapper.getIsHasOpenTicketEdit(ticketInstId,ticketStatus);
	}

}
