package com.idc.action.usergroup;

import com.idc.model.*;
import com.idc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.StringUtils;
import utils.tree.TreeNode;

import java.util.*;

/***
 * @author mylove
 */
@Controller
@RequestMapping("/usergroup")
public class UserGroupAction extends BaseController {
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private SysRegionService sysRegionService;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysUserinfoService sysUserinfoService;
    @Autowired
    private LnUserUsergrpService lnUserUsergrpService;
    @Autowired
    private LnUsergrpRoleService lnUsergrpRoleService;
    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index() {
        return "sysusergroup/index";
    }

    @RequestMapping("form.do")
    public String form(Integer groupid, Integer pid, ModelMap m) {
        SysUserGroup group = null;
        if (groupid != null) {//编辑
            group = sysUserGroupService.getModelById(groupid);
            pid = group.getParentId();
            List<SysRoleinfo> roleinfos = sysRoleinfoService.queryListByObjectByGroupId(Arrays.asList(group.getId()));
            m.addAttribute("roles", roleinfos);
        } else {
            group = new SysUserGroup();
        }
        //获取上级
        if (pid != null) {
            SysUserGroup tmpgroup = sysUserGroupService.getModelById(pid);
            m.addAttribute("pgroup", tmpgroup);
        }
        m.addAttribute("group", group);
        return "sysusergroup/form";
    }

    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysUserGroup user,Integer[] roleids) {
        ExecuteResult executeResult = new ExecuteResult();
        Map<String, Object> result = new HashMap<String, Object>();
        model = new ModelMap();
        model.addAttribute("status", true);
        if (user != null) {
            try {
                executeResult = sysUserGroupService.saveOrUpdate(user,roleids);
                sysOperateLogService.insert(LogType.ADD, "添加组信息"+user);
            } catch (Exception e) {
                logger.error("",e);
                executeResult.setMsg("保存失败，请检查重试");
            }
        } else {
            executeResult.setMsg("没有组信息");
        }
        return executeResult;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean pageBean, String groupname, Integer groupPid) {
        SysUserGroup s = new SysUserGroup();
        s.setName(groupname);
        s.setParentId(groupPid);
        if (pageBean != null && pageBean.getPage() > -1) {
            List<SysUserGroup> list = sysUserGroupService.queryListPage(pageBean, s);
            return new EasyUIData(pageBean);
        } else {
            List<SysUserGroup> list = sysUserGroupService.queryList();
            EasyUIData easyUIData = new EasyUIData();
            easyUIData.setRows(list);
            return easyUIData;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<Integer> idList = StringUtils.ArrayToIntArray(StringUtils.StringToArray(ids));
            sysUserGroupService.deleteGroupsAndRoles(idList);
            executeResult.setState(true);
            executeResult.setMsg("删除信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setState(false);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<TreeNode> tree = sysUserGroupService.getTree();
        return tree;
    }
    /***
     * 绑定角色资源权限
     * ,分割
     * @return
     */
    @RequestMapping(value = "/bindrole.do")
    @ResponseBody
    public ExecuteResult bindByGroup(String ids,String roleids){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
            List<Integer> igroups = StringUtils.ArrayToIntArray(StringUtils.StringToArray(ids));
            List<Integer> iroleids = StringUtils.ArrayToIntArray(StringUtils.StringToArray(roleids));
            for (Integer igroup : igroups) {
                if(iroleids!=null && iroleids.size()>0){
                    for (Integer iroleid : iroleids) {
                        LnUsergrpRole l =new LnUsergrpRole();
                        l.setRoleId(iroleid);
                        l.setUgrpId(igroup);
                        lnUsergrpRoles.add(l);
                    }
                }else{
                    LnUsergrpRole l =new LnUsergrpRole();
                    l.setUgrpId(igroup);
                    lnUsergrpRoles.add(l);
                }
            }
            sysUserGroupService.BindGroupAndRole(lnUsergrpRoles);
            executeResult.setState(true);
            sysOperateLogService.insert(LogType.OTHER, "绑定组和角色[组ID："+ids+"   角色ID:"+roleids+"]");
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("绑定失败");
        }
        return executeResult;
    }

    @RequestMapping("/preUnbindUserList")
    public String preUnbindUserList(Integer groupId, ModelMap map, String isbind) {
        map.addAttribute("groupId", groupId);
        map.addAttribute("isbind", isbind);
        return "sysusergroup/userList";
    }

    @RequestMapping("/bindUserToGroup")
    @ResponseBody
    public ExecuteResult bindUserToGroup(String ids, Integer groupId) {
        ExecuteResult executeResult = new ExecuteResult();
        if (ids != null && !"".equals(ids) && groupId != null) {
            List<String> list = Arrays.asList(ids.split(","));
            List<LnUserUsergrp> lnUserUsergrps = new ArrayList<>();
            for (String id : list) {
                LnUserUsergrp lnUserUsergrp = new LnUserUsergrp();
                lnUserUsergrp.setUserId(Integer.parseInt(id));
                lnUserUsergrp.setUgrpId(groupId);
                lnUserUsergrps.add(lnUserUsergrp);
            }
            try {
                lnUserUsergrpService.insertList(lnUserUsergrps);
                executeResult.setState(true);
                executeResult.setMsg("添加成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("添加失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("添加失败，没有用户信息或组信息");
        }
        return executeResult;
    }

    //解除用户与组的绑定关系
    @RequestMapping("/unbindUserToGroup")
    @ResponseBody
    public ExecuteResult unbindUserToGroup(String ids, Integer groupId) {
        ExecuteResult executeResult = new ExecuteResult();
        if (ids != null && !"".equals(ids) && groupId != null) {
            List<String> list = Arrays.asList(ids.split(","));
            List<LnUserUsergrp> lnUserUsergrps = new ArrayList<>();
            for (String id : list) {
                LnUserUsergrp lnUserUsergrp = new LnUserUsergrp();
                lnUserUsergrp.setUserId(Integer.parseInt(id));
                lnUserUsergrp.setUgrpId(groupId);
                lnUserUsergrps.add(lnUserUsergrp);
            }
            try {
                lnUserUsergrpService.deleteByLnUserUsergrps(lnUserUsergrps);
                executeResult.setState(true);
                executeResult.setMsg("移除成员成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("移除成员失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("移除成员失败，没有用户信息或组信息");
        }
        return executeResult;
    }

    /*通过用户ID获取该用户所归属的组细信息*/
    @RequestMapping("/queryUserGroupListByUserId")
    @ResponseBody
    public List<SysUserGroup> queryUserGroupListByUserId(Integer userId) {
        return sysUserGroupService.getListByUserId(userId);
    }

    /*打开当前用户组的角色列表*/
    @RequestMapping("/preUnbindRoleList")
    public String preUnbindRoleList(Integer groupId, ModelMap map, String isbind) {
        map.addAttribute("groupId", groupId);
        map.addAttribute("isbind", isbind);
        return "sysusergroup/roleList";
    }

    /*绑定角色与组*/
    @RequestMapping("/bindRoleToGroup")
    @ResponseBody
    public ExecuteResult bindRoleToGroup(String ids, Integer groupId) {
        ExecuteResult executeResult = new ExecuteResult();
        if (ids != null && !"".equals(ids) && groupId != null) {
            List<String> list = Arrays.asList(ids.split(","));
            List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
            for (String id : list) {
                LnUsergrpRole lnUsergrpRole = new LnUsergrpRole();
                lnUsergrpRole.setRoleId(Integer.parseInt(id));
                lnUsergrpRole.setUgrpId(groupId);
                lnUsergrpRoles.add(lnUsergrpRole);
            }
            try {
                lnUsergrpRoleService.insertList(lnUsergrpRoles);
                executeResult.setState(true);
                executeResult.setMsg("添加成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("添加失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("添加失败，没有角色信息或组信息");
        }
        return executeResult;
    }

    //解除角色与组的绑定关系
    @RequestMapping("/unbindRoleToGroup")
    @ResponseBody
    public ExecuteResult unbindRoleToGroup(String ids, Integer groupId) {
        ExecuteResult executeResult = new ExecuteResult();
        if (ids != null && !"".equals(ids) && groupId != null) {
            List<String> list = Arrays.asList(ids.split(","));
            List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
            for (String id : list) {
                LnUsergrpRole lnUsergrpRole = new LnUsergrpRole();
                lnUsergrpRole.setRoleId(Integer.parseInt(id));
                lnUsergrpRole.setUgrpId(groupId);
                lnUsergrpRoles.add(lnUsergrpRole);
            }
            try {
                lnUsergrpRoleService.deleteByLnUsergrpRoles(lnUsergrpRoles);
                executeResult.setState(true);
                executeResult.setMsg("移除角色成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("移除角色失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("移除角色失败，没有角色信息或组信息");
        }
        return executeResult;
    }
}
