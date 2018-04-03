package com.idc.service.impl;

import com.idc.mapper.JbpmResourceChildshapeMapper;
import com.idc.model.JbpmResourceChildshape;
import com.idc.service.JbpmResourceChildshapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>JBPM_RESOURCE_CHILDSHAPE:JBPM_RESOURCE_CHILDSHAPE<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("jbpmResourceChildshapeService")
public class JbpmResourceChildshapeServiceImpl extends SuperServiceImpl<JbpmResourceChildshape, Long> implements JbpmResourceChildshapeService {
	@Autowired
	private JbpmResourceChildshapeMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
