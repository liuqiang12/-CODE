package com.idc.service;

import com.idc.model.LnUsergrpRole;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_ln_usergrp_role:用户组和角色关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface LnUsergrpRoleService extends SuperService<LnUsergrpRole, Integer>{

    /*解除角色与组的绑定*/
    void deleteByLnUsergrpRoles(List<LnUsergrpRole> lnUsergrpRoles) throws Exception;
}
