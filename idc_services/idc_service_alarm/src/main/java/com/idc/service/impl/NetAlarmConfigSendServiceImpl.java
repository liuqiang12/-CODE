package com.idc.service.impl;


import com.idc.mapper.AlarmConfigSendMapper;
import com.idc.model.NetKpiAlarmSendConfig;
import com.idc.service.NetAlarmConfigSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>alarm_config_send:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service
public class NetAlarmConfigSendServiceImpl extends SuperServiceImpl<NetKpiAlarmSendConfig, Integer> implements NetAlarmConfigSendService {
	@Autowired
	private AlarmConfigSendMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
