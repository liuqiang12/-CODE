package com.idc.service.impl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.SysOperateMapper;
import com.idc.model.SysOperate;
import com.idc.service.SysOperateService;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_operate:功能模块的操作权限<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysOperateService")
public class SysOperateServiceImpl extends SuperServiceImpl<SysOperate, Integer> implements SysOperateService {
	@Autowired
	private SysOperateMapper mapper;

    @Override
    public List<SysOperate> getListByPermitId(List<Integer> permitids) {
        Set set = new HashSet();
        List newList = new ArrayList();
        set.addAll(permitids);
        newList.addAll(set);
        return mapper.getListByPermitId(newList);
    }
    @Override
    public List<SysOperate> getListByPermit(Integer id) {
        return mapper.getListByPermit(id);
    }


    @Override
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<SysOperate> sysOperates = mapper.queryList();
        for (SysOperate sysOperate : sysOperates) {
            TreeNode t = new TreeNode();
            t.setId(sysOperate.getId()+"");
            t.setName(sysOperate.getName());
            t.setSelf(sysOperate);
            t.setParentId(sysOperate.getParentId()==null?null:sysOperate.getParentId().toString());
            treeNodes.add(t);
        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
        for (TreeNode treeNode : treeNodes1) {
            treeNode.setOpen(true);
        }
        return treeNodes1;
    }
    /**
	 *   Special code can be written here 
	 */
}
