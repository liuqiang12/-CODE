package com.idc.service.impl;

import com.idc.mapper.SysOrganizationMapper;
import com.idc.mapper.SysRegionMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.SysOrganization;
import com.idc.model.SysOrganizationTreeClosure;
import com.idc.model.SysRegion;
import com.idc.service.SysOrganizationService;
import com.idc.service.SysOrganizationTreeClosureService;
import com.idc.service.SysRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_organization:机构信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 11,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysOrganizationService")
public class SysOrganizationServiceImpl extends SuperServiceImpl<SysOrganization, Integer> implements SysOrganizationService {
    @Autowired
    private SysOrganizationMapper mapper;
    @Autowired
    private SysRegionService sysRegionService;
    @Autowired
    private SysRegionMapper sysRegionMapper;
    @Autowired
    private SysOrganizationTreeClosureService sysOrganizationTreeClosureService;

    @Override
    public List<TreeNode> getTree(String type, Integer pid) {
        //所有区域

        if (pid == null) {
            List<SysRegion> rootNodes = sysRegionMapper.getRootNodes();
        }
        List<SysRegion> sysRegions = sysRegionService.queryList();
        List<SysOrganization> sysOrganizations = mapper.queryList();//所有机构
//        List<TreeNode> tree = sysRegionService.getTree(isShowOrg, defaultLevel, Pid);
//        if("region".equals(type)){///区域下的
//            mapper.findBy()
//        }else if("".equals(type)){
//
//        }
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
//        List<SysUserGroup> sysUserGroups = mapper.queryList();
//        for (SysUserGroup userGroup : sysUserGroups) {
//            TreeNode t = new TreeNode();
//            t.setId(userGroup.getId() + "");
//            t.setName(userGroup.getName());
//            t.setSelf(userGroup);
//            t.setParentId(userGroup.getParentId() == null ? null : userGroup.getParentId().toString());
//            treeNodes.add(t);
//        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
        for (TreeNode treeNode : treeNodes1) {
            treeNode.setOpen(true);
        }
        return treeNodes1;
    }

    @Override
    public List<SysOrganization> getRootNodesByRegion(Integer region) {
        return mapper.getRootNodesByRegion(region);
    }

    @Override
    public List<TreeNode> getTree(Integer defaultLevel, Integer pid, boolean isSimpDate) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<SysOrganization> sysOrganizations = mapper.queryList();
        Map<String, Object> qmap = new HashMap<>();
        qmap.put("distance", 1);
        List<SysOrganizationTreeClosure> sysOrganizationTreeClosures = sysOrganizationTreeClosureService.queryListByMap(qmap);
        Map<String, String> id_Pid = new HashMap<String, String>();
        for (SysOrganizationTreeClosure sysRegionTreeClosure : sysOrganizationTreeClosures) {
            if (sysRegionTreeClosure.getDistance() == 1)
                id_Pid.put(sysRegionTreeClosure.getDescendant() + "", sysRegionTreeClosure.getAncestor() == null ? "" : sysRegionTreeClosure.getAncestor() + "");
        }
        for (SysOrganization sysOrganization : sysOrganizations) {
            TreeNode t = new TreeNode();
            t.setId(sysOrganization.getId() + "");
            t.setName(sysOrganization.getName());
            t.setSelf(sysOrganization);
            t.setParentId(id_Pid.get(sysOrganization.getId() + ""));
            treeNodes.add(t);
        }
        if (isSimpDate) {
            return treeNodes;
        } else {
            List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
            for (TreeNode treeNode : treeNodes1) {
                treeNode.setOpen(true);
            }
            return treeNodes1;
        }
    }

    @Override
    @Transactional
    public ExecuteResult saveOrUpdate(SysOrganization sysOrganization, Integer porgid) throws Exception {
        boolean isEditParent = true;
        if (sysOrganization.getId() == null) {
            sysOrganization.setParentId(porgid);
            insert(sysOrganization);
            SysOrganizationTreeClosure t = new SysOrganizationTreeClosure();
            t.setAncestor(sysOrganization.getId());
            t.setDescendant(sysOrganization.getId());
            t.setDistance(0);
            if (porgid == null || porgid.intValue() == 0) {
                t.setIsrootnode(0);
            }
            sysOrganizationTreeClosureService.insert(t);
        } else {
            SysOrganization old = getModelById(sysOrganization.getId());
            if (porgid.equals(old.getParentId())) {
                isEditParent = false;//没有修改关系树
            } else {
                SysOrganizationTreeClosure t = new SysOrganizationTreeClosure();
                t.setDescendant(old.getId());
                sysOrganizationTreeClosureService.deleteByObject(t);
                //添加自己
                t = new SysOrganizationTreeClosure();
                t.setAncestor(sysOrganization.getId());
                t.setDescendant(sysOrganization.getId());
                t.setDistance(0);
                if (porgid == null || porgid.intValue() == 0) {
                    t.setIsrootnode(0);
                }
                sysOrganizationTreeClosureService.insert(t);
            }
            sysOrganization.setParentId(porgid);
            updateByObject(sysOrganization);
        }
        // 如果修改了关系树
        if (isEditParent) {
            if (porgid != null && porgid > 0) {
                SysOrganizationTreeClosure t = new SysOrganizationTreeClosure();
                t.setDescendant(porgid);
                //新的父亲的所有上级节点
                List<SysOrganizationTreeClosure> sysOrganizationTreeClosures = sysOrganizationTreeClosureService.queryListByObject(t);
                List<SysOrganizationTreeClosure> newinsert = new ArrayList<>();
                for (SysOrganizationTreeClosure sysOrganizationTreeClosure : sysOrganizationTreeClosures) {
                    //新的子节点上原来的基础上+1
                    t = new SysOrganizationTreeClosure();
                    t.setAncestor(sysOrganizationTreeClosure.getAncestor());
                    t.setDescendant(sysOrganization.getId());
                    t.setDistance(sysOrganizationTreeClosure.getDistance() + 1);
                    sysOrganizationTreeClosureService.insert(t);
                }
            }
        }

        return null;
    }

    @Override
    @Transactional
    public void deleteByIdAndDelteleTree(int id) throws Exception {
        SysOrganizationTreeClosure t = new SysOrganizationTreeClosure();
        t.setAncestor(id);
        List<Integer> delobjs =new ArrayList<>();
        //所有的子节点
        List<SysOrganizationTreeClosure> sysOrganizationTreeClosures = sysOrganizationTreeClosureService.queryListByObject(t);
        for (SysOrganizationTreeClosure sysOrganizationTreeClosure : sysOrganizationTreeClosures) {
            //删除所有与之关联的
            SysOrganizationTreeClosure newsysOrganizationTreeClosure =new SysOrganizationTreeClosure();
            newsysOrganizationTreeClosure.setDescendant(sysOrganizationTreeClosure.getDescendant());
            sysOrganizationTreeClosureService.deleteByObject(newsysOrganizationTreeClosure);
            delobjs.add(sysOrganizationTreeClosure.getDescendant());

        }
        for (Integer delobj : delobjs) {
            deleteById(delobj);
        }
    }
    /**
     *   Special code can be written here
     */
}
