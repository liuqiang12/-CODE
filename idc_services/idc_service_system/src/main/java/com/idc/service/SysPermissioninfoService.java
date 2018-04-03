package com.idc.service;

import com.idc.model.PermType;
import com.idc.model.SysMenuinfo;
import com.idc.model.SysOperate;
import com.idc.model.SysPermissioninfo;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_permissioninfo:系统权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysPermissioninfoService extends SuperService<SysPermissioninfo, Integer> {
    List<SysPermissioninfo> getAllResourceByUserId(Integer userid);
    List<SysMenuinfo> getUrlAuthorities();

    List<SysPermissioninfo> queryPermissionListByRoleId(List<Integer> roleids);

    List<String> getAllKeysByUserId(Integer id);
    List<SysMenuinfo> getUrlAuthoritiesForMenu(Integer userid);


    List<SysPermissioninfo> getPermissioninfoByTypeAndId(PermType type, List<Integer> ids);

    /***
     * 绑定角色资源
     * @param roleids
     * @param menuids
     * @param operaids
     * @throws Exception
     */
    void bindByRole(String roleids, String menuids, String operaids)throws Exception;

    void save(SysMenuinfo menuinfo, SysOperate op, int type) throws Exception;

    void delete(int type, int id);

    /****
     * TODO 通过角色获取用户有权限的区域
     * @param roleid
     * @return 区域ID
     * @throws Exception
     */
    List<Long> getRegionsByRole(Integer roleid) throws Exception;
    /**
     *   Special code can be written here
     */
}
