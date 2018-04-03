package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketPreAcceptMapper;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcHisTicketPreAcceptMapper;
import com.idc.model.IdcHisTicketPreAccept;
import com.idc.service.IdcHisTicketPreAcceptService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_PRE_ACCEPT:历史预受理工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcHisTicketPreAcceptService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcHisTicketPreAcceptServiceImpl extends SuperServiceImpl<IdcHisTicketPreAccept, Long> implements IdcHisTicketPreAcceptService {
	@Autowired
	private IdcHisTicketPreAcceptMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
