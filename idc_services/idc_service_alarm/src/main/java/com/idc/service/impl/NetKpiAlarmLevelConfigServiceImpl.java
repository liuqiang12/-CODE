package com.idc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.NetKpiAlarmLevelConfigMapper;
import com.idc.model.NetKpiAlarmLevelConfig;
import com.idc.service.NetKpiAlarmLevelConfigService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_KPIALARMLEVELCONFIG:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service
public class NetKpiAlarmLevelConfigServiceImpl extends SuperServiceImpl<NetKpiAlarmLevelConfig, Long> implements NetKpiAlarmLevelConfigService {
	@Autowired
	private NetKpiAlarmLevelConfigMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
