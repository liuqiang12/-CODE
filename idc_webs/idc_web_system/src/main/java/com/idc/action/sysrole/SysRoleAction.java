package com.idc.action.sysrole;

import com.idc.model.ExecuteResult;
import com.idc.model.LogType;
import com.idc.model.SysOperateLog;
import com.idc.model.SysRoleinfo;
import com.idc.service.SysOperateLogService;
import com.idc.service.SysRoleinfoService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.StringUtils;

import java.util.*;

/***
 * @author mylove
 * 角色
 */
@Controller
@RequestMapping("/sysrole")
public class SysRoleAction extends BaseController {
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
//    @Autowired
//    private Sys sysRoleinfoService;
    @Autowired
    private SysOperateLogService sysOperateLogService;

    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index() {
        return "sysrole/index";
    }

    @RequestMapping("form.do")
    public String index(Integer id, ModelMap m) {
        SysRoleinfo sysRoleinfo = null;
        if (id != null) {//编辑
            sysRoleinfo = sysRoleinfoService.getModelById(id);
        } else {
            sysRoleinfo = new SysRoleinfo();
        }
        m.addAttribute("roleinfo", sysRoleinfo);
        return "sysrole/form";
    }

    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysRoleinfo role) {
        ExecuteResult executeResult = new ExecuteResult();
        if (role != null) {
            executeResult = sysRoleinfoService.saveOrUpdate(role);
            SysOperateLog t = new SysOperateLog();
            sysOperateLogService.insert(LogType.ADD,"添加角色信息"+role);
        } else {
            executeResult.setState(false);
            executeResult.setMsg("没有角色信息");
        }
        return executeResult;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean pageBean, String name,String type) {
        SysRoleinfo s = new SysRoleinfo();
        s.setName(name);
        s.setType(type);

        if(pageBean!=null&&pageBean.getPage()>-1){
            List<SysRoleinfo> list = sysRoleinfoService.queryListPage(pageBean, s);
            return new EasyUIData(pageBean);
        }else{
            List<SysRoleinfo> sysRoleinfos = sysRoleinfoService.queryList();
            EasyUIData easyUIData = new EasyUIData();
            easyUIData.setRows(sysRoleinfos);
            return easyUIData;
        }
    }
    /*  activiti时使用 */
     /*  activiti时使用 */
    @RequestMapping("sysRoleGridPageToAct.do")
    public String sysRoleGridPageToAct() {
         return "sysrole/sysRoleGridToAct";
    }

    /**
     * acitivit时使用
     * @param sysRoleinfo
     * @return
     */
    @RequestMapping("sysRoleGridDataToAct.do")
    @ResponseBody
    public List<SysRoleinfo> sysRoleGridToAct(SysRoleinfo sysRoleinfo) {
        /*查询角色信息的所有信息*/
        List<SysRoleinfo> list = sysRoleinfoService.queryListFilterByObject(sysRoleinfo);
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<Integer> idList = StringUtils.ArrayToIntArray(StringUtils.StringToArray(ids));
            sysRoleinfoService.deleteByList(idList);
            executeResult.setState(true);
            sysOperateLogService.insert(LogType.DELETE,"删除角色信息,角色ID:"+ids);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setMsg("删除信息失败,是否需要先解除相关绑定");
        }
        return executeResult;
    }

//    /***
//     * 绑定组角色资源权限
//     * ,分割
//     * @return
//     */
//    @RequestMapping(value = "/rolebygroup.do")
//    @ResponseBody
//    public ExecuteResult roleByGroup(Integer groupid,String roleids){
//
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            List<SysRoleinfo> roleinfos = sysRoleinfoService.queryListByObjectByGroupId(Arrays.asList(groupid));
////            sysPermissioninfoService.bindByRole(roleids,menuids,operaids);
//            executeResult.setState(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            executeResult.setMsg("绑定失败");
//        }
//        return executeResult;
//    }

    @RequestMapping(value = "/rolebytype.do")
    @ResponseBody
    public String roleByGroup(String type ,Integer id) {
        Map<String, Object> result = new HashMap<>();
        List<SysRoleinfo> roleinfos = new ArrayList<>();
        if("gr".equals(type)){
           roleinfos = sysRoleinfoService.queryListByObjectByGroupId(Arrays.asList(id));
        }else if("ur".equals(type)){
           roleinfos = sysRoleinfoService.getRoleListByUserId(id);
        }
        result.put("roles", roleinfos);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject.toString();
    }

    /*通过组ID获取所有的角色*/
    @RequestMapping("/queryRoleListByGroupId")
    @ResponseBody
    public EasyUIData queryRoleListByGroupId(EasyUIPageBean page, String name, Integer groupId, String isbind) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        mapQ.put("groupId", groupId);
        //用于查询存在当前组或者不存在当前组的角色    unbind:不存在   其他表示存在
        mapQ.put("isbind", isbind);
        List<SysRoleinfo> list = new ArrayList<>();
        if (page != null && page.getPage() > -1) {
            list = sysRoleinfoService.queryRoleListByGroupId(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        } else {
            list = sysRoleinfoService.queryList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }
        return easyUIData;
    }
}
