package com.idc.action.region;

import com.idc.model.*;
import com.idc.service.SysRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/11/28.
 */
@Controller
@RequestMapping("/sysregion")
public class RegionAction extends BaseController {
    @Autowired
    private SysRegionService sysRegionService;


    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index() {
        return "sysregion/index";
    }

    @RequestMapping("form.do")
    public String index(Integer userid, ModelMap m) {
        SysUserinfo user = null;
//        if (userid != null) {
//            user = sysUserinfoService.getModelById(userid);
//            if (user != null) {
//                if (user.getRegion() != null) {
//                    SysRegion sysRegion = sysRegionService.getModelById(user.getId());
//                    m.addAttribute("region", sysRegion);
//                }
//            } else {
//                user = new SysUserinfo();
//            }
//
//        }
        m.addAttribute("user", user);
        return "sysregion/form";
    }

    @RequestMapping("save.do")
    @ResponseBody
    public Map<String, Object> save(SysUserinfoBean user) {
        Map<String, Object> result = new HashMap<String, Object>();
//        model = new ModelMap();
//        model.addAttribute("status", true);
//        SysUserinfo sysUserinfo = new SysUserinfo();
//        try {
//            utils.PropertyUtils.copyProperties(sysUserinfo, user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("用户转换失败");
//        }
//        if (sysUserinfo != null) {
//            try {
//                Map<String, Object> queryobj = new HashMap<>();
//                queryobj.put("username", sysUserinfo.getName());
//                List<SysUserinfo> sysUserinfos = sysUserinfoService.queryListByMap(queryobj);
//                if (!sysUserinfos.isEmpty()) {
//                    model.addAttribute("status", false);
//                    model.addAttribute("msg", "用户已经存在");
//                } else {
//                    EncryptUtil util = new EncryptUtil();
//                    String hashedPassword = util.encode(sysUserinfo.getPassword());
//                    sysUserinfo.setPassword(hashedPassword);
//                    sysUserinfo.setCredentialsnonexpired(true);
//                    sysUserinfo.setAccountnonexpired(true);
//                    sysUserinfo.setEnabled(true);
//                    sysUserinfo.setIsDepartLeading(true);
//                    sysUserinfo.setAuthenticated(true);
//                    sysUserinfo.setCreateTime(new Timestamp(new Date().getTime()));
//                    sysUserinfoService.insert(sysUserinfo);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("保存用户失败:", e);
//                model.addAttribute("status", false);
//                model.addAttribute("msg", "保存用户失败");
//            }
//
//        } else {
//            model.addAttribute("status", false);
//            model.addAttribute("msg", "保存失败,没有用户信息");
//        }
//        result.putAll(model);
        return result;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean pageBean, String region_pid, String regionName) {
        List<TreeNode> tree = sysRegionService.getTree(true, 0, null,true);
        List<Map<String,Object>> rows = new ArrayList<>();
        Map<String,Object> map;
        for (TreeNode treeNode : tree) {
            if(treeNode.getId().equals(treeNode.getParentId())){
                continue;
            }
            map =new HashMap<>();
            map.put("id",treeNode.getId());
            map.put("name",treeNode.getName());
            if(treeNode.getSelf() instanceof SysRegion){
                map.put("remark", ((SysRegion) treeNode.getSelf()).getRemark());
                map.put("code", ((SysRegion) treeNode.getSelf()).getCode());
            }else if(treeNode.getSelf() instanceof SysOrganization){
                map.put("remark", ((SysOrganization) treeNode.getSelf()).getDescription());
            }

            map.put("_parentId", treeNode.getParentId());
            rows.add(map);
        }
//        Map<String,Object> objectMap = new HashMap<String, Object>();
//        sysRegionService.getTree()
//        if(region_pid!=null&&region_pid.startsWith("org_")){//组织
//
//        }
//        List<SysRegion> sysRegions  =sysRegionService.queryListPage(pageBean,null,regionName);
        EasyUIData e = new EasyUIData();
        e.setRows(rows);
//        e.setTotal(tree);
        return e;
    }
    @RequestMapping("/list1")
    @ResponseBody
    public EasyUIData list1(EasyUIPageBean page,String name) {
        EasyUIData easyUIData = new EasyUIData();
        SysRegion sysRegion = new SysRegion();
        sysRegion.setName(name);
        List<SysRegion> list = new ArrayList<>();
        if(page==null||page.getPage()<0){//所有
            list= sysRegionService.queryList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
            list = sysRegionService.queryListPage(page, sysRegion);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    @RequestMapping("/regionTree")
    @ResponseBody
    public List<TreeNode> regionTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        try {
            treeNodes = sysRegionService.getRegionTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeNodes;
    }
    /****
     *
     * @param isShowOrg 是否显示组织机构等
     * @param defaultLevel 默认展开等级
     * @param Pid 上级Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree(Boolean isShowOrg,Integer defaultLevel,Integer id,Boolean isrole) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<TreeNode> tree = sysRegionService.getTree(isShowOrg,defaultLevel,id,false,isrole);
        return tree;
    }

    /****
     *
     * @param isShowOrg 是否显示组织机构等
     * @param defaultLevel 默认展开等级
     * @param Pid 上级Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree_homeCity.do")
    public List<TreeModel> getTree_homeCity() {
    	TreeModel tree = sysRegionService.getTree_homeCity();
    	List<TreeModel> list = new ArrayList<TreeModel>();
    	list.add(tree);
    	return list;
    }
    /**
     * 多个根级节点
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree_homeCity_Qx.do")
    public List<TreeModel> tree_homeCity_Qx() {
    	List<TreeModel> list = sysRegionService.getTree_homeCity_Qx();
    	return list;
    }

    @ResponseBody
    @RequestMapping(value = "/load_ajaxData.do")
    public List<TreeModel> radiopanel_ajaxData() throws Exception {
    	List<TreeModel> list = new ArrayList<TreeModel>();
    	TreeModel treeModel = new TreeModel();
    	treeModel = sysRegionService.getBootstrapTree(treeModel,null);
    	//获取节点
    	return treeModel!=null?treeModel.getChildren():null;
    }


}
