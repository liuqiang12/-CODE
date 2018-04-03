package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketRecoverMapper;
import com.idc.mapper.IdcRunTicketRecoverMapper;
import com.idc.model.IdcHisTicketRecover;
import com.idc.model.IdcRunProcCment;
import com.idc.model.IdcRunTicketRecover;
import com.idc.service.IdcContractService;
import com.idc.service.IdcRunTicketRecoverService;
import modules.utils.ResponseJSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RUN_TICKET_RECOVER:运行时复通工单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRunTicketRecoverService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunTicketRecoverServiceImpl extends JbpmEntranceServiceImpl implements IdcRunTicketRecoverService {
	@Autowired
	private IdcRunTicketRecoverMapper idcRunTicketRecoverMapper;
	@Autowired
	private IdcHisTicketRecoverMapper idcHisTicketRecoverMapper;
	@Autowired
	private IdcContractService idcContractService;

	@Override
	public void handWithRecover(ApplicationContext context, IdcRunProcCment idcRunProcCment, IdcRunTicketRecover idcRunTicketRecover) throws Exception {
/*创建下线工单。首先修改下线信息:首先开启工单。然后*/
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("prodInstId",idcRunTicketRecover.getIdcRunTicket().getProdInstId());
		variables.put("ticketCategoryFrom",idcRunTicketRecover.getIdcRunTicket().getTicketCategoryFrom());
		variables.put("ticketCategoryTo",idcRunTicketRecover.getIdcRunTicket().getTicketCategoryTo());
		variables.put("parentId",idcRunTicketRecover.getIdcRunTicket().getTicketInstId());
		variables.put("prodCategory",idcRunTicketRecover.getIdcRunTicket().getProdCategory());

		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
		if(responseJSON.isSuccess()) {
			Map<String, Object> responseJSONMap = (Map) responseJSON.getResult();
			Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
			//然后直接update。下线信息
			idcRunTicketRecover = idcRunProcCment.getIdcRunTicketRecover();
			idcRunTicketRecover.setTicketInstId(ticketInstId);
			idcRunTicketRecoverMapper.updateByIdcRunTicketRecover(idcRunTicketRecover);
			IdcHisTicketRecover idcHisTicketRecover = new IdcHisTicketRecover();
			BeanUtils.copyProperties(idcRunTicketRecover,idcHisTicketRecover);
			idcHisTicketRecoverMapper.updateByIdcHisTicketRecover(idcHisTicketRecover);

		}
	}

	@Override
	public IdcRunTicketRecover queryByTicketInstId(Long ticketInstId) {
		return idcRunTicketRecoverMapper.queryByTicketInstId(ticketInstId);
	}

	/**
	 *   Special code can be written here 
	 */
}
