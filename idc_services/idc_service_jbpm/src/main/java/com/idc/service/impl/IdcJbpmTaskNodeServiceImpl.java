package com.idc.service.impl;

import com.idc.mapper.IdcJbpmTaskNodeMapper;
import com.idc.model.IdcJbpmTaskNode;
import com.idc.service.IdcJbpmTaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_JBPM_TASKNODE:流程中的任务节点配置<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 26,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcJbpmTaskNodeService")
public class IdcJbpmTaskNodeServiceImpl extends SuperServiceImpl<IdcJbpmTaskNode, Long> implements IdcJbpmTaskNodeService {
	@Autowired
	private IdcJbpmTaskNodeMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
