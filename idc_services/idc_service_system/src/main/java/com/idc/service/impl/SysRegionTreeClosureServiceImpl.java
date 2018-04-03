package com.idc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.SysRegionTreeClosureMapper;
import com.idc.model.SysRegionTreeClosure;
import com.idc.service.SysRegionTreeClosureService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_region_tree_closure:区域树形闭包<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysRegionTreeClosureService")
public class SysRegionTreeClosureServiceImpl extends SuperServiceImpl<SysRegionTreeClosure, Integer> implements SysRegionTreeClosureService {
	@Autowired
	private SysRegionTreeClosureMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
