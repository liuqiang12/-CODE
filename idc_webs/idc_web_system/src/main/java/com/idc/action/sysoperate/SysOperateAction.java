package com.idc.action.sysoperate;

import com.idc.model.ExecuteResult;
import com.idc.model.SysMenuinfo;
import com.idc.model.SysOperate;
import com.idc.service.SysOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.supper.action.BaseController;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/***
 * @author mylove
 */
@Controller
@RequestMapping("/sysoperate")
public class SysOperateAction extends BaseController {
    @Autowired
    private SysOperateService sysOperateService;
//
//    /***
//     * 进入界面
//     *
//     * @return
//     */
//    @RequestMapping("index.do")
//    public String index(ModelMap modelMap) {
//        List<SysMenuinfo> list = sysMenuinfoService.queryList();
//        modelMap.put("list",list);
//        return "sysmenu/index";
//    }
//

    /***
     * 切换状态
     * @param id
     * @return
     */
    @RequestMapping("switch.do")
    @ResponseBody
    public ExecuteResult switchState(int id) {
        ExecuteResult result = new ExecuteResult();
        try {
            SysOperate modelById = sysOperateService.getModelById(id);
            modelById.setEnabled((modelById.getEnabled() == null || modelById.getEnabled() != 1) ? 1 : 0);
            sysOperateService.updateByObject(modelById);
            result.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("切换状态失败");
        }
        return result;
    }
//
//    @RequestMapping("list.do")
//    @ResponseBody
//    public Map<String, Object> list(Integer pageNumber, Integer pageSize, String menuname, Integer menupid) {
//        PageBean<SysMenuinfo> pageBean = new PageBean<SysMenuinfo>();
//        pageBean.setPageNo(pageNumber == null ? 0 : pageNumber - 1);
//        pageBean.setPageSize(pageSize);
//        SysMenuinfo s = new SysMenuinfo();
//        s.setName(menuname);
//        s.setParentId(menupid);
//        List<SysMenuinfo> list = sysMenuinfoService.queryListPage(pageBean, s);
//        model.addAttribute("rows", list);
//        model.addAttribute("total", pageBean.getTotalRecord());
//        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
//        objectObjectHashMap.putAll(model);
//        return objectObjectHashMap;
//    }
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<TreeNode> tree = sysOperateService.getTree();
        return tree;
    }
}
