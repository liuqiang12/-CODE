package com.idc.mapper;

import com.idc.model.SysRoleinfo;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_roleinfo:系统角色信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 16,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysRoleinfoMapper extends SuperMapper<SysRoleinfo, Integer>{
    List<SysRoleinfo> queryListByObjectByGroupId(List<Integer> groups);

    List<SysRoleinfo> queryListByObjectByUserId(Integer userId);

    void bindRoleAndPermission(Integer roleid, Integer PermId);

   List<SysRoleinfo> queryListFilterByObject(SysRoleinfo sysRoleinfo);

    List<SysRoleinfo> getRolAddGroup(Integer userId);
    String getRegionsWithUserId(Integer userId);
    String getGroupsWithUserId(Integer userId);

    List<SysRoleinfo> getRoleinfosByUserId(Integer userId);
    String getRolesWithUserId(Integer userId);

    /**
     * 获取用户对应的角色
     * @return
     */
    List<Map> getAllRoleinfos();

    /**
     *用户 区域信息
     * @return
     */
    List<Map> getAllRegions();

    /**
     * 获取有用的觉得
     * @return
     */
    List<Map> getRoles();

    /*通过组ID获取所有的角色*/
    List<SysRoleinfo> queryRoleListByGroupId(PageBean<T> page);
}

 