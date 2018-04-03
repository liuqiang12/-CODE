package com.idc.action.sysdepartment;

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
import utils.tree.TreeNode;
import utils.typeHelper.ListHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author mylove
 */
@Controller
@RequestMapping("/sysdepartment")
public class SysDepartmentAction extends BaseController {
    @Autowired
    SysDepartmentService sysDepartmentService;
    @Autowired
    SysDepartmentTreeClosureService sysDepartmentTreeClosureService;
    @Autowired
    SysOrganizationService sysOrganizationService;
    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index() {
        return "organization/index";
    }

    @RequestMapping("form.do")
    public String form(Integer dptid, Integer pid, ModelMap m) {
        SysDepartment sysDepartment = null;
        if (dptid != null) {//编辑
            sysDepartment = sysDepartmentService.getModelById(dptid);
            SysDepartmentTreeClosure t = new SysDepartmentTreeClosure();
            t.setDescendant(dptid);
            t.setDistance(1);
            SysDepartmentTreeClosure modelByObject = sysDepartmentTreeClosureService.getModelByObject(t);
            if(modelByObject!=null)
                pid = modelByObject.getAncestor();
        }
        //获取上级
        if (pid != null) {
            SysDepartment pdpt = sysDepartmentService.getModelById(pid);
            m.addAttribute("pdpt", pdpt);
        }
        m.addAttribute("dpt", sysDepartment);
        return "sysdpt/form";
    }
    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysDepartment sysDepartment,Integer pdptid) {
        ExecuteResult executeResult = new ExecuteResult();
        if (sysDepartment != null) {
            try {
                sysDepartmentService.saveOrUpdate(sysDepartment,pdptid);
                executeResult.setState(true);
            } catch (Exception e) {
                logger.error("",e);
                executeResult.setMsg("保存失败，请检查重试");
            }
        } else {
            executeResult.setMsg("没有信息");
        }
        return executeResult;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean pageBean, String name, String orgid) {
        List<TreeNode> tree = sysDepartmentService.getTree(0, orgid,true);
        List<Map<String,Object>> rows = new ArrayList<>();
        Map<String,Object> map;
        List<SysOrganization> sysOperateLogs = sysOrganizationService.queryList();
        Map<Object, SysOrganization> orgMap = new HashMap<>();
        try {
            orgMap = ListHelper.ListToMap(sysOperateLogs, "id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (TreeNode treeNode : tree) {
            if(treeNode.getId().equals(treeNode.getParentId())){
                continue;
            }
            SysOrganization sysOrganization = orgMap.get(((SysDepartment) treeNode.getSelf()).getOrgId());
            map =new HashMap<>();
            map.put("id",treeNode.getId());
            map.put("name",treeNode.getName());
            map.put("orgid",((SysDepartment) treeNode.getSelf()).getOrgId());
            map.put("orgName",sysOrganization==null?"":sysOrganization.getName());
            map.put("description", ((SysDepartment) treeNode.getSelf()).getDescription());
            map.put("_parentId", treeNode.getParentId());
            rows.add(map);
        }
        EasyUIData e = new EasyUIData();
        e.setRows(rows);
        return e;
    }
    @RequestMapping("/list1")
    @ResponseBody
    public EasyUIData list1(EasyUIPageBean page,String name) {
        EasyUIData easyUIData = new EasyUIData();
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setName(name);
        List<SysDepartment> list = new ArrayList<>();
        if(page==null||page.getPage()<0){//所有
            list= sysDepartmentService.queryList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
            list = sysDepartmentService.queryListPage(page, sysDepartment);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(int id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            sysDepartmentService.deleteByIdAndDelteleTree(id);
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }
//    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree(String pidkey) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        String type="";
        Integer pid=0;
//        List<TreeNode> tree = sysOrganizationService.getTree(type, pid);
        return null;
    }
//    /***
//     * 绑定角色资源权限
//     * ,分割
//     * @return
//     */
//    @RequestMapping(value = "/bindrole.do")
//    @ResponseBody
//    public ExecuteResult bindByGroup(String ids,String roleids){
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            List<LnUsergrpRole> lnUsergrpRoles = new ArrayList<>();
//            List<Integer> igroups = StringUtils.ArrayToIntArray(StringUtils.StringToArray(ids));
//            List<Integer> iroleids = StringUtils.ArrayToIntArray(StringUtils.StringToArray(roleids));
//            for (Integer igroup : igroups) {
//                for (Integer iroleid : iroleids) {
//                    LnUsergrpRole l =new LnUsergrpRole();
//                    l.setRoleId(iroleid);
//                    l.setUgrpId(igroup);
//                    lnUsergrpRoles.add(l);
//                }
//            }
//            sysUserGroupService.BindGroupAndRole(lnUsergrpRoles);
//            executeResult.setState(true);
//            sysOperateLogService.insert(LogType.OTHER, "绑定组和角色[组ID："+ids+"   角色ID:"+roleids+"]");
//        } catch (Exception e) {
//            e.printStackTrace();
//            executeResult.setMsg("绑定失败");
//        }
//        return executeResult;
//    }
}
