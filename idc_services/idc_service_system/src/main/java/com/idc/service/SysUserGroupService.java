package com.idc.service;

import com.idc.model.ExecuteResult;
import com.idc.model.LnUsergrpRole;
import com.idc.model.SysUserGroup;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_user_group:用户组<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysUserGroupService extends SuperService<SysUserGroup, Integer> {
    List<Integer> queryListIdByObjectByUserId(Integer userId);

    List<SysUserGroup> queryListByObjectByUserId(Integer userId);

    List<TreeNode> getTree();

    /***
     * 更新或保存
     *
     * @param user
     * @return
     */

    ExecuteResult saveOrUpdate(SysUserGroup user, Integer[] roleids) throws Exception;

    @Transactional
    boolean BindGroupAndRole(List<LnUsergrpRole> lnUsergrpRoles) throws Exception;

    boolean deleteGroupRoleByIdList(List<Integer> list) throws Exception;

    List<SysUserGroup> getListByUserId(Integer userId);

    /*删除组的同时，删除与角色的绑定关系*/
    void deleteGroupsAndRoles(List<Integer> list) throws Exception;
}
