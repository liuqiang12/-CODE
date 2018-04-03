package com.idc.action.organization;

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

import java.util.*;

/***
 * @author mylove
 */
@Controller
@RequestMapping("/organization")
public class OrganizationAction extends BaseController {
    @Autowired
    SysOrganizationService sysOrganizationService;
    @Autowired
    SysOrganizationTreeClosureService sysOrganizationTreeClosureService;
//    @Autowired
//    private SysUserGroupService sysUserGroupService;
    @Autowired
    private SysRegionService sysRegionService;
//    @Autowired
//    private SysRoleinfoService sysRoleinfoService;
//    @Autowired
//    private SysOperateLogService sysOperateLogService;
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
    public String form(Integer orgid, Integer pid, ModelMap m) {
        SysOrganization sysOrganization = null;
        if (orgid != null) {//编辑
            sysOrganization = sysOrganizationService.getModelById(orgid);
            SysOrganizationTreeClosure t = new SysOrganizationTreeClosure();
            t.setDescendant(orgid);
            t.setDistance(1);
            SysOrganizationTreeClosure modelByObject = sysOrganizationTreeClosureService.getModelByObject(t);
            if(modelByObject!=null)
            pid = modelByObject.getAncestor();
//            List<SysRoleinfo> roleinfos = sysRoleinfoService.queryListByObjectByGroupId(Arrays.asList(group.getId()));
//            m.addAttribute("roles", roleinfos);
        } else {
        }
        //获取上级
        if (pid != null) {
            SysOrganization tmpgroup = sysOrganizationService.getModelById(pid);
            m.addAttribute("porg", tmpgroup);
        }
        m.addAttribute("org", sysOrganization);
        return "organization/form";
    }

    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysOrganization sysOrganization,Integer porgid) {
        ExecuteResult executeResult = new ExecuteResult();
        Map<String, Object> result = new HashMap<String, Object>();
        if (sysOrganization != null) {
            try {
                sysOrganizationService.saveOrUpdate(sysOrganization,porgid);
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
    public EasyUIData list(EasyUIPageBean pageBean, String name, Integer region) {
        List<TreeNode> tree = sysOrganizationService.getTree(0, null,true);
        List<Map<String,Object>> rows = new ArrayList<>();
        Map<String,Object> map;
        Map<Object, SysRegion> RegionMap = sysRegionService.getRegionMapBy("id");//.queryList();
//        try {
//            Map<Object, SysRegion> RegionMap = ListHelper.ListToMap(sysRegions, "getId");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        for (TreeNode treeNode : tree) {
            if(treeNode.getId().equals(treeNode.getParentId())){
                continue;
            }
            SysRegion sysRegion = RegionMap.get(((SysOrganization) treeNode.getSelf()).getRegionId());
            map =new HashMap<>();
            map.put("id",treeNode.getId());
            map.put("name",treeNode.getName());
            map.put("region",((SysOrganization) treeNode.getSelf()).getRegionId());
            map.put("regionName",sysRegion==null?"":sysRegion.getName());
            map.put("description", ((SysOrganization) treeNode.getSelf()).getDescription());
            map.put("_parentId", treeNode.getParentId());
            rows.add(map);
        }
        EasyUIData e = new EasyUIData();
        e.setRows(rows);
        return e;
    }

    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(int id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            sysOrganizationService.deleteByIdAndDelteleTree(id);

            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree(String pidkey) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        String type="";
        Integer pid=0;
        List<TreeNode> tree = sysOrganizationService.getTree(type, pid);
        return tree;
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
