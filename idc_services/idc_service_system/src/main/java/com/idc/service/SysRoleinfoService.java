package com.idc.service;

import com.idc.model.ExecuteResult;
import com.idc.model.SysPermissioninfo;
import com.idc.model.SysRoleinfo;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_roleinfo:系统角色信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 16,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysRoleinfoService extends SuperService<SysRoleinfo, Integer>{
//    List<Integer> getRoleIdsListByUserId(Integer userId);

    List<Integer> getRoleIdsListByUserIdAddGroup(Integer userId);

    List<SysRoleinfo> getRoleListByUserId(Integer userId);

    List<SysRoleinfo> getRoleListByUserIdAddGroup(Integer userId);

    List<SysRoleinfo> queryListByObjectByGroupId(List<Integer> groupids);

    boolean bindRoleAndPermission(SysRoleinfo sysRoleinfo, List<SysPermissioninfo> permissioninfo) throws Exception;

    ExecuteResult saveOrUpdate(SysRoleinfo role);
    //liuqiang add
    List<SysRoleinfo> queryListFilterByObject(SysRoleinfo sysRoleinfo) ;
    List<SysRoleinfo> getRolAddGroup(Integer userId);
    String getRegionsWithUserId(Integer userId);
    String getGroupsWithUserId(Integer userId);
    String getRolesWithUserId(Integer userId);

    List<SysRoleinfo> getRoleinfosByUserId(Integer userId);

    /*通过组ID获取所有的角色*/
    List<SysRoleinfo> queryRoleListByGroupId(PageBean<T> page, Object param);
}
