package com.idc.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcInServerResourceMapper;
import com.idc.model.IdcInServerResource;
import com.idc.service.IdcInServerResourceService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_IN_SERVER_RESOURCE:在服资源<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 09,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcInServerResourceService")
public class IdcInServerResourceServiceImpl extends SuperServiceImpl<IdcInServerResource, Long> implements IdcInServerResourceService {
	@Autowired
	private IdcInServerResourceMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
