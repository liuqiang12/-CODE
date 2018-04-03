package com.idc.action.operatelog;

import com.idc.model.LogType;
import com.idc.service.SysOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/11/28.
 */
@Controller
@RequestMapping("/log")
public class SysOperaLogAction extends BaseController {
    @Autowired
    private SysOperateLogService sysOperateLogService;
    private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
//    @SystemLog(description = "获取日志信息")
    public String index() {
        return "operatelog/index";
    }

//    @RequestMapping("form.do")
//    public String index(Integer userid,ModelMap m) {
//        SysUserinfo user = null;
//        if (userid != null) {
//            user = sysUserinfoService.getModelById(userid);
//            if(user!=null){
//                if(user.getRegion()!=null){
//                    SysRegion sysRegion =sysRegionService.getModelById(user.getRegion());
//                    m.addAttribute("region", sysRegion);
//                }
//                 List<SysUserGroup> sysUserGroup = sysUserGroupService.queryListByObjectByUserId(user.getId());
//                 m.addAttribute("groups", sysUserGroup);
//                 List<SysRoleinfo> roleinfos = sysRoleinfoService.getRoleListByUserId(user.getId());
//                m.addAttribute("roles", roleinfos);
//            }else{
//                user = new SysUserinfo();
//            }
//
//        }
//        m.addAttribute("user", user);
//        return "userinfo/form";
//    }

//    @RequestMapping("save.do")
//    @ResponseBody
//    public ExecuteResult save(SysUserinfoBean user,String oldpassword,Integer[] groupid,Integer[] roleids) {
//        ExecuteResult executeResult = new ExecuteResult();
//        SysUserinfo sysUserinfo = new SysUserinfo();
//        try {
//            utils.PropertyUtils.copyProperties(sysUserinfo,user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("用户转换失败");
//        }
//        if (sysUserinfo != null) {
//            try {
//                    String newpassword = sysUserinfo.getPassword();//
//                    if(newpassword.equals("******")){
//                        sysUserinfo.setPassword(oldpassword);
//                    }else{
//                        EncryptUtil util = new EncryptUtil();
//                        String hashedPassword = util.encode(sysUserinfo.getPassword());
//                        sysUserinfo.setPassword(hashedPassword);
//                    }
//                    sysUserinfo.setCredentialsnonexpired(true);
//                    sysUserinfo.setAccountnonexpired(true);
//                    sysUserinfo.setEnabled(true);
//                    sysUserinfo.setIsDepartLeading(true);
//                    sysUserinfo.setAuthenticated(true);
////                    sysUserinfo.setCreateTime(new Timestamp(new Date().getTime()));
//                    executeResult = sysUserinfoService.saveOrUpdate(sysUserinfo,groupid==null?new Integer[0]:groupid,roleids==null?(new Integer[0]):roleids);
//                    sysOperateLogService.insert(LogType.ADD, "添加用户信息"+sysUserinfo);
////                    executeResult = sysUserinfoService.bindUserAndGroup(sysUserinfo,groupid);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("保存用户失败:",e);
//                executeResult.setMsg("保存用户失败");
//            }
//
//        }else {
//            executeResult.setMsg("保存失败,没有用户信息");
//            model.addAttribute("msg", "保存失败,没有用户信息");
//        }
//        return executeResult;
//    }
//
    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page,String key) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("description",key);
        List<Map<String,Object>> list = new ArrayList<>();
        if(page==null||page.getPage()<0){//所有
            list= sysOperateLogService.queryListMap();
            easyUIData.setTotal(list.size());
//            easyUIData.setRows(list);
        }else{
//            PageBean<SysUserinfo> pageBean = new PageBean<SysUserinfo>();
//            pageBean.setPageNo(pageNumber==null?0:pageNumber);
//            pageBean.setPageSize(pageSize==null?10:pageSize);
            list = sysOperateLogService.queryListMapPage(page, queryMap);
//            model.addAttribute("total", page.getTotalRecord());
            easyUIData.setTotal(page.getTotalRecord());
//            easyUIData.setRows(page.getItems());
        }
        for (Map<String, Object> objectMap : list) {
            try {
                Object type = objectMap.get("TYPE");
                if(type!=null){
                    objectMap.put("typestr", LogType.getNameByvalue(Integer.parseInt(type+"")));
                }
                Timestamp timestamp= (Timestamp) objectMap.get("CREATE_TIME");
                if(timestamp!=null){
                    String format = sdf.format(timestamp);
                    objectMap.put("createtimestr",format);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        easyUIData.setRows(list);
//        model.addAttribute("rows", list);
//        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
//        objectObjectHashMap.putAll(model);
        return easyUIData;
    }
//    @ResponseBody
//    @RequestMapping(value = "/delete.do")
//    public ExecuteResult delete(int id) {
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            SysUserinfo modelById = sysUserinfoService.getModelById(id);
//            sysUserinfoService.deleteByObject(modelById);
//            executeResult.setState(true);
//            sysOperateLogService.insert(LogType.DELETE, "删除用户信息,用户:"+modelById);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("删除信息失败:",e);
//            executeResult.setMsg("删除信息失败");
//        }
//        return executeResult;
//    }
//
//    /***
//     * 绑定用户组关系
//     * @param
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/bindusergroup.do")
//    public ExecuteResult bindUserGroup(String userid,String groupids) {
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            List<String> users = Arrays.asList(userid.split(","));
//            List<String> groups = Arrays.asList(groupids.split(","));
//            executeResult = sysUserinfoService.bindUserAndGroup(users, groups);
//
//            //操作日志
//            List<SysOperateLog> logs = new ArrayList<>();
//            for (String user : users) {
//                SysUserinfo userbean = sysUserinfoService.getModelById(Integer.parseInt(user));
//                for (String group : groups) {
//                    SysUserGroup modelById1 = sysUserGroupService.getModelById(Integer.parseInt(group));
//                    SysOperateLog s= new SysOperateLog();
//                    s.setType(BigDecimal.valueOf(LogType.OTHER.getIvalue()));
//                    s.setDescription("用户【"+userbean.getName()+"】绑定到组【"+modelById1.getName()+"】");
//                    logs.add(s);
//                }
//            }
//            sysOperateLogService.insertList(logs);
//            //sysOperateLogService.insert(LogType.OTHER, "绑定用户和组[用户ID："+userid+"   组ID:"+groupids+"]");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("绑定信息失败:",e);
//            executeResult.setMsg("绑定信息失败");
//        }
//        return executeResult;
//    }
//    /***
//     * 绑定角色资源权限
//     * ,分割
//     * @return
//     */
//    @RequestMapping(value = "/bindrole.do")
//    @ResponseBody
//    public ExecuteResult bindRole(String ids,String roleids){
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            executeResult = sysUserinfoService.bindUserAndRole(Arrays.asList(ids.split(",")), Arrays.asList(roleids.split(",")));
//            sysOperateLogService.insert(LogType.OTHER, "绑定用户和角色[用户ID："+ids+"   角色ID:"+roleids+"]");
//        } catch (Exception e) {
//            e.printStackTrace();
//            executeResult.setMsg("绑定失败");
//        }
//        return executeResult;
//    }

}
