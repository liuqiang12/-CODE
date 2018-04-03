package com.idc.service.impl;

import com.idc.mapper.SysDepartmentMapper;
import com.idc.model.SysDepartment;
import com.idc.model.SysDepartmentTreeClosure;
import com.idc.service.SysDepartmentService;
import com.idc.service.SysDepartmentTreeClosureService;
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
 * <b>功能：业务表</b>sys_department:部门信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 28,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysDepartmentService")
public class SysDepartmentServiceImpl extends SuperServiceImpl<SysDepartment, Integer> implements SysDepartmentService {
    @Autowired
    private SysDepartmentMapper mapper;
    @Autowired
    private SysDepartmentTreeClosureService sysDepartmentTreeClosureService;

    @Override
    public List<TreeNode> getTree(Integer pid, String orgid, boolean isSimpDate) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        Map<String, Object> qmap = new HashMap<>();
        qmap.put("orgids", orgid);
        List<SysDepartment> sysOrganizations = mapper.queryListByMap(qmap);

        qmap = new HashMap<>();
        qmap.put("distance", 1);
        List<SysDepartmentTreeClosure> sysOrganizationTreeClosures = sysDepartmentTreeClosureService.queryListByMap(qmap);
        Map<String, String> id_Pid = new HashMap<String, String>();
        for (SysDepartmentTreeClosure sysRegionTreeClosure : sysOrganizationTreeClosures) {
            if (sysRegionTreeClosure.getDistance() == 1)
                id_Pid.put(sysRegionTreeClosure.getDescendant() + "", sysRegionTreeClosure.getAncestor() == null ? "" : sysRegionTreeClosure.getAncestor() + "");
        }
        for (SysDepartment sysOrganization : sysOrganizations) {
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
    public void saveOrUpdate(SysDepartment sysDepartment, Integer pdptid) throws Exception {
        boolean isEditParent = true;
        if (sysDepartment.getId() == null) {
            sysDepartment.setParentId(pdptid);
            insert(sysDepartment);
            SysDepartmentTreeClosure t = new SysDepartmentTreeClosure();
            t.setAncestor(sysDepartment.getId());
            t.setDescendant(sysDepartment.getId());
            t.setDistance(0);
            sysDepartmentTreeClosureService.insert(t);
        } else {
            SysDepartment old = getModelById(sysDepartment.getId());
            if (pdptid.equals(old.getParentId())) {
                isEditParent = false;//没有修改关系树
            } else {
                SysDepartmentTreeClosure t = new SysDepartmentTreeClosure();
                t.setDescendant(old.getId());
                sysDepartmentTreeClosureService.deleteByObject(t);
                //添加自己
                t = new SysDepartmentTreeClosure();
                t.setAncestor(sysDepartment.getId());
                t.setDescendant(sysDepartment.getId());
                t.setDistance(0);
                sysDepartmentTreeClosureService.insert(t);
            }
            sysDepartment.setParentId(pdptid);
            updateByObject(sysDepartment);
        }
        // 如果修改了关系树
        if (isEditParent) {
            if (pdptid != null && pdptid > 0) {
                SysDepartmentTreeClosure t = new SysDepartmentTreeClosure();
                t.setDescendant(pdptid);
                //新的父亲的所有上级节点
                List<SysDepartmentTreeClosure> sysOrganizationTreeClosures = sysDepartmentTreeClosureService.queryListByObject(t);
                for (SysDepartmentTreeClosure sysOrganizationTreeClosure : sysOrganizationTreeClosures) {
                    //新的子节点上原来的基础上+1
                    t = new SysDepartmentTreeClosure();
                    t.setAncestor(sysOrganizationTreeClosure.getAncestor());
                    t.setDescendant(sysDepartment.getId());
                    t.setDistance(sysOrganizationTreeClosure.getDistance() + 1);
                    sysDepartmentTreeClosureService.insert(t);
                }
            }
        }
    }
    @Override
    @Transactional
    public void deleteByIdAndDelteleTree(int id) throws Exception {
        SysDepartmentTreeClosure t = new SysDepartmentTreeClosure();
        t.setAncestor(id);
        List<Integer> delobjs =new ArrayList<>();
        //所有的子节点
        List<SysDepartmentTreeClosure> sysOrganizationTreeClosures = sysDepartmentTreeClosureService.queryListByObject(t);
        for (SysDepartmentTreeClosure sysOrganizationTreeClosure : sysOrganizationTreeClosures) {
            //删除所有与之关联的
            SysDepartmentTreeClosure newsysOrganizationTreeClosure =new SysDepartmentTreeClosure();
            newsysOrganizationTreeClosure.setDescendant(sysOrganizationTreeClosure.getDescendant());
            sysDepartmentTreeClosureService.deleteByObject(newsysOrganizationTreeClosure);
            delobjs.add(sysOrganizationTreeClosure.getDescendant());
        }
        for (Integer delobj : delobjs) {
            deleteById(delobj);
        }
    }

    /**
     * 根据用户ID获取部门
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysDepartment> getDepartmentListByUserId(Integer userId) {
        return mapper.getDepartmentListByUserId(userId);
    }
    /**
     *   Special code can be written here
     */
}
