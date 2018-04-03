package com.idc.service.impl;

import com.idc.mapper.SysUserGroupMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.LnUsergrpRole;
import com.idc.model.LogType;
import com.idc.model.SysUserGroup;
import com.idc.service.LnUsergrpRoleService;
import com.idc.service.SysOperateLogService;
import com.idc.service.SysUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;
import utils.typeHelper.StringHelper;

import java.sql.Timestamp;
import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_user_group:用户组<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysUserGroupService")
public class SysUserGroupServiceImpl extends SuperServiceImpl<SysUserGroup, Integer> implements SysUserGroupService {
    @Autowired
    private SysUserGroupMapper mapper;
    @Autowired
    private LnUsergrpRoleService lnUsergrpRoleService;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private SysOperateLogService sysOperateLogService;

    @Override
    public List<Integer> queryListIdByObjectByUserId(Integer userId) {
        List<SysUserGroup> sysUserGroups = queryListByObjectByUserId(userId);
        List<Integer> result = new ArrayList<Integer>();
        for (SysUserGroup sysUserGroup : sysUserGroups) {
            result.add(sysUserGroup.getId());
        }
        return result;
    }

    @Override
    public List<SysUserGroup> queryListByObjectByUserId(Integer userId) {
        return mapper.queryListByObjectByUserId(userId);
    }

    @Override
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<SysUserGroup> sysUserGroups = mapper.queryList();
        for (SysUserGroup userGroup : sysUserGroups) {
            TreeNode t = new TreeNode();
            t.setId(userGroup.getId() + "");
            t.setName(userGroup.getName());
            t.setSelf(userGroup);
            t.setParentId(userGroup.getParentId() == null ? null : userGroup.getParentId().toString());
            treeNodes.add(t);
        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
        for (TreeNode treeNode : treeNodes1) {
            treeNode.setOpen(true);
        }
        return treeNodes1;
    }

    /***
     * 验证数据是否重复
     *
     * @param isadd 是否添加
     * @param user
     * @return
     */
    private boolean valiName(boolean isadd, SysUserGroup user) {
        Map<String, Object> pmap = new HashMap<>();
        pmap.put("name", user.getName().trim());
        if (!isadd) {
            SysUserGroup old = getModelById(user.getId());
            if (old.getName().equals(user.getName())) {//如果名字有修改 验证
                return true;
            }
        }
        List<SysUserGroup> modelByMap = queryListByMap(pmap);//通过名字查询到了 就返回失败
        if (modelByMap != null && modelByMap.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public ExecuteResult saveOrUpdate(SysUserGroup user, Integer[] roleids) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        if (user == null || !StringHelper.getInstance().isValid(user.getName())) {
            executeResult.setMsg("没有组名信息");
            return executeResult;
        }
        boolean isadd = true;
        if (user.getId() != null && user.getId() > 0) {
            isadd = false;
        }
        boolean b = valiName(isadd, user);
        if (!b) {
            executeResult.setMsg("组名" + user.getName() + "重复 ");
            return executeResult;
        }
        if (isadd) {
            user.setCreateTime(new Timestamp(new Date().getTime()));
            insert(user);
        } else {
            updateByObject(user);
        }
        List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
        if(roleids!=null && roleids.length>0){
            for (Integer roleid : roleids) {
                LnUsergrpRole ln = new LnUsergrpRole();
                ln.setUgrpId(user.getId());
                ln.setRoleId(roleid);
                lnUsergrpRoles.add(ln);
            }
        }else{
            LnUsergrpRole ln = new LnUsergrpRole();
            ln.setUgrpId(user.getId());
            lnUsergrpRoles.add(ln);
        }
        BindGroupAndRole(lnUsergrpRoles);
        executeResult.setState(true);
        executeResult.setMsg("保存成功");
        return executeResult;
    }

    /***
     * 绑定
     * @param lnUsergrpRoles
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean BindGroupAndRole(List<LnUsergrpRole> lnUsergrpRoles) throws Exception {
          cleanBindByGroup(lnUsergrpRoles);
          if(lnUsergrpRoles!=null && lnUsergrpRoles.size()>0 && lnUsergrpRoles.get(0).getRoleId() != null ){
              mapper.bindGroupAndRole(lnUsergrpRoles);
          }
          return true;
    }

    /***
     * 清理绑定关系
     * @param lnUsergrpRoles
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean cleanBindByGroup(List<LnUsergrpRole> lnUsergrpRoles) throws Exception {
        mapper.cleanBindByGroup(lnUsergrpRoles);
        return true;
    }

    /***
     * 根据组id清理角色与组的绑定关系
     * @param list
     * @return
     * @throws Exception
     */
    public boolean deleteGroupRoleByIdList(List<Integer> list) throws Exception{
        if(list != null && list.size()>0){
            List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
            for(Integer groupid:list){
                LnUsergrpRole ln = new LnUsergrpRole();
                ln.setUgrpId(groupid);
                lnUsergrpRoles.add(ln);
            }
            cleanBindByGroup(lnUsergrpRoles);
        }
     return true;
    }

    /**
     * 根据用户ID获取用户所在组信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysUserGroup> getListByUserId(Integer userId) {
        return mapper.getListByUserId(userId);
    }

    /*删除组的同时，删除与角色的绑定关系*/
    @Override
    @Transactional
    public void deleteGroupsAndRoles(List<Integer> list) throws Exception {
        //删除组与角色关系
        sysUserGroupService.deleteGroupRoleByIdList(list);
        sysOperateLogService.insert(LogType.DELETE, "删除组与角色绑定信息,组ID:"+list.toString());
        //删除组
        sysUserGroupService.deleteByList(list);
        sysOperateLogService.insert(LogType.DELETE, "删除组信息,组ID:"+list.toString());
    }
}
