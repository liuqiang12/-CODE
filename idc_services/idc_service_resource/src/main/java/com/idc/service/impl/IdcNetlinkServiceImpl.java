package com.idc.service.impl;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcNetlinkMapper;
import com.idc.model.IdcNetlink;
import com.idc.service.IdcNetlinkService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_netlink:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcNetlinkService")
public class IdcNetlinkServiceImpl extends SuperServiceImpl<IdcNetlink, String> implements IdcNetlinkService {
	@Autowired
	private IdcNetlinkMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
