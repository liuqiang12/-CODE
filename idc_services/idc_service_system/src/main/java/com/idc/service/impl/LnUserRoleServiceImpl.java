package com.idc.service.impl;

import com.idc.mapper.LnUserRoleMapper;
import com.idc.model.LnUserRole;
import com.idc.service.LnUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_ln_user_role:用户角色关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("lnUserRoleService")
public class LnUserRoleServiceImpl extends SuperServiceImpl<LnUserRole, Integer> implements LnUserRoleService {
	@Autowired
	private LnUserRoleMapper mapper;
	/**
	 *   Special code can be written here 
	 */
}
