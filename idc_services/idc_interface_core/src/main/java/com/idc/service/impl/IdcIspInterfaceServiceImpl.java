package com.idc.service.impl;

import com.idc.mapper.IdcIspInterfaceMapper;
import com.idc.model.InterfaceInfo;
import com.idc.service.IdcInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_ISP_INTERFACE_INFO:新增数据上报_接口<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcIspNewInfoService")
public class IdcIspInterfaceServiceImpl extends SuperServiceImpl<InterfaceInfo, Long> implements IdcInterfaceService {
	@Autowired
	private IdcIspInterfaceMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
