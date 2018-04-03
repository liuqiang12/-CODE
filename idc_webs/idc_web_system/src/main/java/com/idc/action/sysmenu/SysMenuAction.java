package com.idc.action.sysmenu;

import com.idc.model.ExecuteResult;
import com.idc.model.SysMenuinfo;
import com.idc.service.SysMenuinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.PageBean;
import system.data.supper.action.BaseController;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author mylove
 */
@Controller
@RequestMapping("/sysmenu")
public class SysMenuAction extends BaseController {
    @Autowired
    private SysMenuinfoService sysMenuinfoService;

    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index(ModelMap modelMap) {
        List<SysMenuinfo> list = sysMenuinfoService.queryList();
        modelMap.put("list",list);
        return "sysmenu/index";
    }

    /***
     * 切换状态
     * @param id
     * @return
     */
    @RequestMapping("switch.do")
    @ResponseBody
    public ExecuteResult switchState(int id){
        ExecuteResult result = new ExecuteResult();
        try {
            SysMenuinfo modelById = sysMenuinfoService.getModelById(id);
            modelById.setEnabled((modelById.getEnabled()==null||modelById.getEnabled()!=1)?1:0);
            sysMenuinfoService.updateByObject(modelById);
            result.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("切换状态失败");
        }
        return result;
    }
//
//    @RequestMapping("form.do")
//    public String index(Integer groupid, Integer pid, ModelMap m) {
//        SysUserGroup group = null;
//        if (groupid != null) {//编辑
//            group = sysUserGroupService.getModelById(groupid);
//            pid = group.getParentId();
//        } else {
//            group = new SysUserGroup();
//        }
//        //获取上级
//        if (pid != null) {
//            SysUserGroup tmpgroup = sysUserGroupService.getModelById(pid);
//            m.addAttribute("pgroup", tmpgroup);
//        }
//        m.addAttribute("group", group);
//        return "sysusergroup/form";
//    }
//
//    @RequestMapping("save.do")
//    @ResponseBody
//    public ExecuteResult save(SysUserGroup user) {
//        ExecuteResult executeResult = new ExecuteResult();
//        Map<String, Object> result = new HashMap<>();
//        model = new ModelMap();
//        model.addAttribute("status", true);
//        if (user != null) {
//            executeResult = sysUserGroupService.saveOrUpdate(user);
//        } else {
//            executeResult.setMsg("没有组信息");
//        }
//        return executeResult;
//    }

    @RequestMapping("list.do")
    @ResponseBody
    public Map<String, Object> list(Integer pageNumber, Integer pageSize, String menuname, Integer menupid) {
        PageBean<SysMenuinfo> pageBean = new PageBean<SysMenuinfo>();
        pageBean.setPageNo(pageNumber == null ? 0 : pageNumber - 1);
        pageBean.setPageSize(pageSize);
        SysMenuinfo s = new SysMenuinfo();
        s.setName(menuname);
        s.setParentId(menupid);
        List<SysMenuinfo> list = sysMenuinfoService.queryListPage(pageBean, s);
        model.addAttribute("rows", list);
        model.addAttribute("total", pageBean.getTotalRecord());
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.putAll(model);
        return objectObjectHashMap;
    }

//    @ResponseBody
//    @RequestMapping(value = "/delete.do")
//    public ExecuteResult delete(int id) {
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            sysUserGroupService.deleteById(id);
//            executeResult.setState(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("删除信息失败:",e);
//            executeResult.setMsg("删除信息失败");
//        }
//        return executeResult;
//    }
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<TreeNode> tree = sysMenuinfoService.getTree();
        return tree;
    }
}
