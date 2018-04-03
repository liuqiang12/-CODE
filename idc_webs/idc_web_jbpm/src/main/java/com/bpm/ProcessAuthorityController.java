package com.bpm;

import com.idc.model.JbpmResourceModelVo;
import com.idc.service.JbpmTaskAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
@RequestMapping("/processAuthority")
public class ProcessAuthorityController {
    @Autowired
    private JbpmTaskAuthorizeService jbpmTaskAuthorizeService;

    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree(String key, Long roleId) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<JbpmResourceModelVo> tree = jbpmTaskAuthorizeService.queryActModelWithRoleKeyRoleId(key, roleId);
        for (JbpmResourceModelVo item : tree) {
            TreeNode t = new TreeNode();
            t.setId(item.getId() + "");
            t.setName(item.getName());
            t.setSelf(item);
            t.setParentId(item.getParentId() == null ? null : item.getParentId().toString());
            treeNodes.add(t);
        }
        List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
        for (TreeNode treeNode : treeNodes1) {
            treeNode.setOpen(true);
        }
        return treeNodes1;
    }
}
