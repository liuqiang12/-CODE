package com.idc.service;

import com.idc.model.SysLnRoleDataPermission;
import system.data.supper.service.SuperService;

import java.util.List;



/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_ln_role_data_permission:角色数据权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysLnRoleDataPermissionService extends SuperService<SysLnRoleDataPermission, Integer>{
    List<SysLnRoleDataPermission> get(int type, List<Integer> roleids);

    void deleteBy(int type, List<String> strings) throws Exception;

    /***
     *
     * @param type 0  role 1 user 2 group
     * @param roleid type=0 roleid  type=1 userid type=2 groupid
     * @return
     */
    List<SysLnRoleDataPermission> queryListByTypeRole(int type, Integer roleid);
    /**
	 *   Special code can be written here 
	 */
}
