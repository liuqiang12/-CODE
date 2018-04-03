package com.idc.service.impl;

import com.idc.mapper.LnUsergrpRoleMapper;
import com.idc.model.LnUsergrpRole;
import com.idc.service.LnUsergrpRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_ln_usergrp_role:用户组和角色关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("lnUsergrpRoleService")
public class LnUsergrpRoleServiceImpl extends SuperServiceImpl<LnUsergrpRole, Integer> implements LnUsergrpRoleService {
	@Autowired
	private LnUsergrpRoleMapper mapper;

    /*解除角色与组的绑定*/
    @Override
    public void deleteByLnUsergrpRoles(List<LnUsergrpRole> lnUsergrpRoles) throws Exception {
        mapper.deleteByLnUsergrpRoles(lnUsergrpRoles);
    }
}
