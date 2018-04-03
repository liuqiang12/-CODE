package com.idc.action.userinfo;

import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.page.PageBean;
import system.data.supper.action.BaseController;
import utils.typeHelper.EncryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by mylove on 2016/11/28.
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoAction extends BaseController {
    @Autowired
    private SysUserinfoService sysUserinfoService;
    @Autowired
    private SysRegionService sysRegionService;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index() {
        return "userinfo/index";
    }

    @RequestMapping("form.do")
    public String index(Integer userid,ModelMap m) {
        SysUserinfo user = new SysUserinfo();
        if (userid != null) {
            user = sysUserinfoService.getModelById(userid);
            List<SysUserGroup> sysUserGroup = sysUserGroupService.queryListByObjectByUserId(user.getId());
            m.addAttribute("groups", sysUserGroup);
            List<SysRoleinfo> roleinfos = sysRoleinfoService.getRoleListByUserId(user.getId());
            m.addAttribute("roles", roleinfos);
            List<SysDepartment> departments = sysDepartmentService.getDepartmentListByUserId(user.getId());
            m.addAttribute("departments", departments);
            List<SysRegion> regions = sysRegionService.getRegionListByUserId(user.getId());
            m.addAttribute("regions", regions);
        }
        m.addAttribute("user", user);
        return "userinfo/form";
    }

    /*当前登录用户信息*/
    @RequestMapping("getSysUserinfo.do")
    public String getSysUserinfo(ModelMap m,String flag) {
        SysUserinfo user = getPrincipal();
        if (user != null) {
            List<SysUserGroup> sysUserGroup = sysUserGroupService.queryListByObjectByUserId(user.getId());
            m.addAttribute("groups", sysUserGroup);
            List<SysRoleinfo> roleinfos = sysRoleinfoService.getRoleListByUserId(user.getId());
            m.addAttribute("roles", roleinfos);
            List<SysDepartment> departments = sysDepartmentService.getDepartmentListByUserId(user.getId());
            m.addAttribute("departments", departments);
            List<SysRegion> regions = sysRegionService.getRegionListByUserId(user.getId());
            m.addAttribute("regions", regions);
        }
        m.addAttribute("user", user);
        m.addAttribute("flag", flag);
        return "userinfo/form";
    }
    /*  activiti时使用 */
     /*  activiti时使用 */
    @RequestMapping("sysUserinfoGridPageToAct.do")
    public String sysUserinfoGridPageToAct() {
        return "sysUserinfo/sysUserinfoGridToAct";
    }

    /**
     * acitivit时使用
     * @param sysUserinfo
     * @return
     */
    @RequestMapping("sysUserinfoGridDataToAct.do")
    @ResponseBody
    public PageBean<SysUserinfo> sysUserinfoGridToAct(PageBean<SysUserinfo> pageBean,SysUserinfo sysUserinfo) {
        /*查询人员信息的所有信息*/
        sysUserinfoService.queryListPage(pageBean,sysUserinfo);
        return pageBean;
    }

    @RequestMapping("save.do")
    @ResponseBody
    public ExecuteResult save(SysUserinfoBean user, String oldpassword, Integer[] groupid, Integer[] roleids, Integer[] departmentIds, Integer[] regions) {
        ExecuteResult executeResult = new ExecuteResult();
        SysUserinfo sysUserinfo = new SysUserinfo();
        try {
            utils.PropertyUtils.copyProperties(sysUserinfo,user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户转换失败");
        }
        if (sysUserinfo != null) {
            try {
                    String newpassword = sysUserinfo.getPassword();//
                    if(newpassword.equals("******")){
                        sysUserinfo.setPassword(oldpassword);
                    }else{
                        EncryptUtil util = new EncryptUtil();
                        String hashedPassword = util.encode(sysUserinfo.getPassword());
                        sysUserinfo.setPassword(hashedPassword);
                    }
                    sysUserinfo.setCredentialsnonexpired(true);
                    sysUserinfo.setAccountnonexpired(true);
                    sysUserinfo.setEnabled(true);
                    sysUserinfo.setIsDepartLeading(true);
                    sysUserinfo.setAuthenticated(true);
                    sysUserinfo.setCreateTime(new Timestamp(new Date().getTime()));
                    sysUserinfo.setLoseEfficacyTime(new Timestamp(new Date().getTime()+90*24*60*60*1000L));
                executeResult = sysUserinfoService.saveOrUpdate(sysUserinfo, groupid == null ? new Integer[0] : groupid,
                        roleids == null ? (new Integer[0]) : roleids, departmentIds == null ? (new Integer[0]) : departmentIds, regions == null ? (new Integer[0]) : regions);
                    sysOperateLogService.insert(LogType.ADD, "添加用户信息"+sysUserinfo);
//                    executeResult = sysUserinfoService.bindUserAndGroup(sysUserinfo,groupid);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("保存用户失败:",e);
                executeResult.setState(false);
                executeResult.setMsg("保存用户失败");
            }

        }else {
            executeResult.setState(false);
            executeResult.setMsg("保存失败,没有用户信息");
            model.addAttribute("msg", "保存失败,没有用户信息");
        }
        return executeResult;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String username, String nick) {
        EasyUIData easyUIData = new EasyUIData();
        SysUserinfo s= new SysUserinfo();
        s.setUsername(username);
        s.setNick(nick);
        List<Map<String, Object>> list = new ArrayList<>();
        if(page==null||page.getPage()<0){//所有
            list = sysUserinfoService.queryListMap(s);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
            list = sysUserinfoService.queryListPageMap(page, s);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            String[] arr = ids.split(",");
            int id;
            for(int i = 0;i<arr.length;i++){
                id = Integer.parseInt(arr[i]);
                SysUserinfo modelById = sysUserinfoService.getModelById(id);
                sysUserinfoService.deleteById(id);
                executeResult.setState(true);
                sysOperateLogService.insert(LogType.DELETE, "删除用户信息,用户:"+modelById);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:",e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }

    /***
     * 绑定用户组关系
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindusergroup.do")
    public ExecuteResult bindUserGroup(String userid,String groupids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> users = Arrays.asList(userid.split(","));
            List<String> groups = Arrays.asList(groupids.split(","));
            executeResult = sysUserinfoService.bindUserAndGroup(users, groups);

            //操作日志
            List<SysOperateLog> logs = new ArrayList<>();
            for (String user : users) {
                SysUserinfo userbean = sysUserinfoService.getModelById(Integer.parseInt(user));
                for (String group : groups) {
                    SysUserGroup modelById1 = sysUserGroupService.getModelById(Integer.parseInt(group));
                    SysOperateLog s= new SysOperateLog();
                    s.setType(BigDecimal.valueOf(LogType.OTHER.getIvalue()));
                    s.setDescription("用户【"+userbean.getName()+"】绑定到组【"+modelById1.getName()+"】");
                    s.setCreateTime(new Timestamp(new Date().getTime()));
                    s.setUserId(Integer.parseInt(user));
                    logs.add(s);
                }
            }
            sysOperateLogService.insertList(logs);
            //sysOperateLogService.insert(LogType.OTHER, "绑定用户和组[用户ID："+userid+"   组ID:"+groupids+"]");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("绑定信息失败:",e);
            executeResult.setMsg("绑定信息失败");
        }
        return executeResult;
    }
    /***
     * 绑定角色资源权限
     * ,分割
     * @return
     */
    @RequestMapping(value = "/bindrole.do")
    @ResponseBody
    public ExecuteResult bindRole(String ids,String roleids){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            executeResult = sysUserinfoService.bindUserAndRole(Arrays.asList(ids.split(",")), Arrays.asList(roleids.split(",")));
            sysOperateLogService.insert(LogType.OTHER, "绑定用户和角色[用户ID："+ids+"   角色ID:"+roleids+"]");
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("绑定失败");
        }
        return executeResult;
    }
    /***
     * 修改密码
     * ,分割
     * @return
     */
    @RequestMapping(value = "/changepwd.do")
    @ResponseBody
    public ExecuteResult changePwd(String oldpwd,String newpwd){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            SysUserinfo currUser = sysUserinfoService.getCurrUser();
            String password = currUser.getPassword();
            EncryptUtil util = new EncryptUtil();
            if(!util.matches(oldpwd, password)){
                executeResult.setMsg("原密码不正确");
                return executeResult;
            }
            String hashedPassword_new = util.encode(newpwd);
            SysUserinfo modelById = sysUserinfoService.getModelById(currUser.getId());
            modelById.setPassword(hashedPassword_new);
            executeResult = sysUserinfoService.saveOrUpdate(modelById, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("修改密码失败");
        }
        return executeResult;
    }

    /*打开绑定用户与组关系页面*/
    @RequestMapping("/bindUserAndGroup")
    public String bindUserAndGroup() {
        return "/userinfo/bindUserAndGroupForm";
    }

    @RequestMapping("/queryUserListByGroupId")
    @ResponseBody
    public EasyUIData queryUserListByGroupId(EasyUIPageBean page, String username, String nick, String groupId, String isbind) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("username", username);
        mapQ.put("nick", nick);
        mapQ.put("groupId", groupId);
        //用于查询存在当前组或者不存在当前组的用户    unbind:不存在   其他表示存在
        mapQ.put("isbind", isbind);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page == null || page.getPage() < 0) {
            list = sysUserinfoService.queryUserListByObjectMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = sysUserinfoService.queryUserListPageByObjectMap(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    @RequestMapping("/preUpdatePassword")
    public String preUpdatePassword(){
        return "/userinfo/updatePassword";
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public ExecuteResult updatePassword(String oldpassword,String password){
        ExecuteResult executeResult = new ExecuteResult();
        Integer userId = getPrincipal().getId();
        String losc = getPrincipal().getPassword();
        EncryptUtil util = new EncryptUtil();
        if(!util.matches(oldpassword,losc)){
            executeResult.setMsg("原密码不正确");
            executeResult.setState(false);
        }else{
            try{
                String hashedPassword_new = util.encode(password);
                SysUserinfo modelById = sysUserinfoService.getModelById(userId);
                modelById.setPassword(hashedPassword_new);
                modelById.setLoseEfficacyTime(new Timestamp(new Date().getTime()+90*24*60*60*1000L));
                sysUserinfoService.updateByObject(modelById);
                executeResult.setMsg("修改密码成功");
                executeResult.setState(true);
            }catch (Exception e){
                executeResult.setMsg("修改密码失败");
                executeResult.setState(false);
                e.printStackTrace();
            }
        }
        return executeResult;
    }
}
