package com.idc.service.impl;

import com.idc.mapper.SysMenuinfoMapper;
import com.idc.model.SysMenuinfo;
import com.idc.model.SysPermissioninfo;
import com.idc.model.SysUserinfo;
import com.idc.service.SysMenuinfoService;
import com.idc.service.SysPermissioninfoService;
import com.idc.service.SysUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_menuinfo:菜单基本信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 17,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysMenuinfoService")
public class SysMenuinfoServiceImpl extends SuperServiceImpl<SysMenuinfo, Integer> implements SysMenuinfoService {
    @Autowired
    private SysMenuinfoMapper mapper;
    @Autowired
    private SysPermissioninfoService sysPermissioninfoService;
    @Autowired
    SysUserinfoService sysUserinfoService;

    /**
     * Special code can be written here
     */

    @Override
    public List<SysMenuinfo> getUrlAuthorities() {
        // TODO Auto-generated method stub
        return mapper.getUrlAuthorities();
    }


    @Override
    public List<SysMenuinfo> getMenuByPermi(List<SysPermissioninfo> allResourceByUserId) {
        return mapper.getMenuByPermi(allResourceByUserId);
    }

    /**
     * 获取菜单先关数据太慢     *
     * @param username
     * @return
     */
    @Override
    public List<TreeNode> getUrlAuthoritiesTree(String  username) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        SysUserinfo userinfo = sysUserinfoService.queryUserAndRoleListThroughUsername(username);
        List<SysMenuinfo> urlAuthorities=new ArrayList<>();
        if(userinfo.getSysRoleinfoTypes().contains("admin")){//管理员
            urlAuthorities = queryList();
        }else{
            /* 这里需要优化：通过用户id获取用户所分菜单信息. */
           /* List<SysPermissioninfo> allResourceByUserId = sysPermissioninfoService.getAllResourceByUserId(userinfo.getId());//所有资源
            if (allResourceByUserId != null && !allResourceByUserId.isEmpty()) {
               urlAuthorities = getMenuByPermi(allResourceByUserId);
            }*/
            urlAuthorities = sysPermissioninfoService.getUrlAuthoritiesForMenu(userinfo.getId());
        }
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (SysMenuinfo urlAuthority : urlAuthorities) {
            if (urlAuthority.getEnabled()!=null&&urlAuthority.getEnabled() != 1) {
                continue;
            }
            TreeNode node = new TreeNode();
            node.setParentId(urlAuthority.getParentId() == null ? "" : urlAuthority.getParentId() + "");
            node.setId(urlAuthority.getId() + "");
            node.setName(urlAuthority.getName());
            node.setSelf(urlAuthority);
            nodes.add(node);
        }
        treeNodes = TreeBuilder.buildListToTree(nodes);
        return treeNodes;
    }

    @Override
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<SysMenuinfo> sysMenuinfos = mapper.queryList();
        for (SysMenuinfo sysMenuinfo : sysMenuinfos) {
            TreeNode t = new TreeNode();
            t.setId(sysMenuinfo.getId() + "");
            t.setName(sysMenuinfo.getName());
            t.setSelf(sysMenuinfo);
            t.setParentId(sysMenuinfo.getParentId() == null ? null : sysMenuinfo.getParentId().toString());
            treeNodes.add(t);
        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
        for (TreeNode treeNode : treeNodes1) {
            treeNode.setOpen(true);
        }
        return treeNodes1;
    }

    @Override
    public List<SysMenuinfo> getMenuByPermiIds(List<Integer> pids) {
        List<SysPermissioninfo> sysPermissioninfos = new ArrayList<>();
        for (Integer pid : pids) {
            SysPermissioninfo sysPermissioninfo = new SysPermissioninfo();
            sysPermissioninfo.setId(pid);
            sysPermissioninfos.add(sysPermissioninfo);
        }
        return getMenuByPermi(sysPermissioninfos);
    }

    @Override
    public List<SysMenuinfo> getUrlAuthoritiesForMenu(Integer userid) {
        return mapper.getUrlAuthoritiesForMenu(userid);
    }
}
