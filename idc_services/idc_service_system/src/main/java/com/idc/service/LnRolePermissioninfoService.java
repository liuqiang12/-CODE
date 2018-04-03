package com.idc.service;

import com.idc.model.LnRolePermissioninfo;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_ln_role_permissioninfo:角色权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface LnRolePermissioninfoService extends SuperService<LnRolePermissioninfo, Integer>{
    void deleteByListByRoles(List<String> strings);
    /**
	 *   Special code can be written here 
	 */
}
