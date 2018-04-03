package com.idc.action.permissioninfo;

import com.idc.model.*;
import com.idc.service.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by mylove on 2016/12/8.
 */
@Controller
@RequestMapping(value = "/permissioninfo")
public class PermissioninfoAction {
    private static final Logger logger = LoggerFactory.getLogger(PermissioninfoAction.class);
    @Autowired
    private SysOperateService sysOperateService;
    @Autowired
    private SysMenuinfoService sysMenuinfoService;
    @Autowired
    private SysPermissioninfoService sysPermissioninfoService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysLnRoleDataPermissionService sysLnRoleDataPermissionService;
    public static void main(String[] args) {
        //ToDo
    }

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

    @RequestMapping("form.do/{type}/{id}")
    public String index(@PathVariable Integer type,@PathVariable Integer id,ModelMap modelMap) {
        System.out.println(type);
        System.out.println(id);
//        SysUserinfo user = null;
//        if (userid != null) {
//            user = sysUserinfoService.getModelById(userid);
//            if(user!=null){
//                if(user.getRegion()!=null){
//                    SysRegion sysRegion =sysRegionService.getModelById(user.getRegion());
//                    m.addAttribute("region", sysRegion);
//                }
//                List<SysUserGroup> sysUserGroup = sysUserGroupService.queryListByObjectByUserId(user.getId());
//                m.addAttribute("groups", sysUserGroup);
//                List<SysRoleinfo> roleinfos = sysRoleinfoService.getRoleListByUserId(user.getId());
//                m.addAttribute("roles", roleinfos);
//            }else{
//                user = new SysUserinfo();
//            }
//
//        }
        modelMap.addAttribute("type", type);
        return "permissioninfo/form";
    }
    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysMenuinfo menuinfo,SysOperate permissioninfo,Integer type) {
        ExecuteResult executeResult = new ExecuteResult();
        if(type==null){
            executeResult.setMsg("没有找到要保存的资源类型");
            return executeResult;
        }else{
            try {
                sysPermissioninfoService.save(menuinfo,permissioninfo,type.intValue());
                executeResult.setState(true);
            } catch (Exception e) {
                e.printStackTrace();
                executeResult.setMsg("保存资源失败");
            }
        }
        return executeResult;
    }
    @RequestMapping(value = "/permbyrole.do")
    @ResponseBody
    public Map<String, Object> getPermissioninfoByRole(int roleid) {
        Map<String, Object> result = new HashMap<>();
        List<SysPermissioninfo> sysPermissioninfos = sysPermissioninfoService.queryPermissionListByRoleId(Arrays.asList(roleid));
        List<Integer> pids = new ArrayList<Integer>();
        for (SysPermissioninfo sysPermissioninfo : sysPermissioninfos) {
            pids.add(sysPermissioninfo.getId());
        }
        List<SysOperate> sysoperates =new ArrayList<>();
        List<SysMenuinfo> sysmenuinfos =new ArrayList<>();
        if (!pids.isEmpty()) {
            sysoperates = sysOperateService.getListByPermitId(pids);
            sysmenuinfos = sysMenuinfoService.getMenuByPermiIds(pids);
        }
        result.put("sysoperates", sysoperates);
        result.put("sysmenuinfos", sysmenuinfos);
        //角色的数据集合
        List<SysLnRoleDataPermission> sysLnRoleDataPermissions =  sysLnRoleDataPermissionService.get(0,Arrays.asList(roleid));
        result.put("dataperms", sysLnRoleDataPermissions);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    /***
     * 绑定角色资源权限
     * ,分割
     * @return
     */
    @RequestMapping(value = "/bindByRole.do")
    @ResponseBody
    public ExecuteResult bindByRole(String roleids,String menuids,String operaids){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            sysPermissioninfoService.bindByRole(roleids,menuids,operaids);
            executeResult.setState(true);

        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("绑定失败");
        }
        return executeResult;
    }
    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(int type ,int id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            sysPermissioninfoService.delete(type,id);
            executeResult.setState(true);
            sysOperateLogService.insert(LogType.DELETE, "删除资源,ID:"+id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }
}
