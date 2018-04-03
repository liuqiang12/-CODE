package com.idc.service.impl;

import com.idc.mapper.IdcReIpModelMapper;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcReIpModelMapper;
import com.idc.model.IdcReIpModel;
import com.idc.service.IdcReIpModelService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RE_IP_MODEL:IP租用产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcReIpModelService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcReIpModelServiceImpl extends SuperServiceImpl<IdcReIpModel, Long> implements IdcReIpModelService {
	@Autowired
	private IdcReIpModelMapper idcReIpModelMapper;
	/**
	 *   Special code can be written here 
	 */

	public IdcReIpModel getModelByTicketInstId(Long ticketInstId){
		return idcReIpModelMapper.getModelByTicketInstId(ticketInstId);
	}

}
