package com.idc.service.impl;

import com.idc.mapper.IdcReNewlyModelMapper;
import com.idc.model.IdcReNewlyModel;
import com.idc.service.IdcReNewlyModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RE_NEWLY_MODEL:IDC_RE_NEWLY_BUSINESS_MODEL新增业务产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcReNewlyModelService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcReNewlyModelServiceImpl extends SuperServiceImpl<IdcReNewlyModel, Long> implements IdcReNewlyModelService {
	@Autowired
	private IdcReNewlyModelMapper idcReNewlyModelMapper;
	/**
	 *   Special code can be written here 
	 */
	public IdcReNewlyModel getModelByTicketInstId(Long ticketInstId){
		return idcReNewlyModelMapper.getModelByTicketInstId(ticketInstId);
	}


}
