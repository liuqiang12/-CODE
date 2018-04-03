package com.idc.service.impl;

import com.idc.mapper.SysUserinfoMapper;
import com.idc.model.*;
import com.idc.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.authority.security.ConfigAttributeHelper;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import system.systemlog.SystemLog;
import utils.typeHelper.MapHelper;
import utils.typeHelper.StringHelper;

import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_userinfo:系统用户信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Sep 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysUserinfoService")
public class SysUserinfoServiceImpl extends SuperServiceImpl<SysUserinfo, Integer> implements SysUserinfoService {
    @Autowired
    private SysUserinfoMapper mapper;
    @Autowired
    private LnUsergrpRoleService lnUsergrpRoleService;
    @Autowired
    private LnUserUsergrpService lnUserUsergrpService;
    @Autowired
    private LnUserRoleService lnUserRoleService;
    @Autowired
    private SysLnUserDepartmentService sysLnUserDepartmentService;
    @Autowired
    private SysLnUserRegionService sysLnUserRegionService;

    /**
     * Special code can be written here
     */

    @Override
//    @Secured("ROLE_admin")
    @SystemLog(description = "获取用户信息信息")
    public List<SysUserinfo> queryListPage(PageBean<SysUserinfo> pageBean, Object a) {
        pageBean.setParams(MapHelper.queryCondition(a));
        //真正执行查询分页
        return mapper.queryListPage(pageBean);
    }

    @Override
    public String queryUserAreaById(String userId) {
        return mapper.queryUserAreaById(userId);
    }

    @Override
    public SysUserinfo queryUserAndRoleListThroughUsername(String username) {
        // TODO Auto-generated method stub
        return mapper.queryUserAndRoleListThroughUsername(username);
    }

    @Override
    public SysUserinfo queryUserById(String id) {
        return mapper.queryUserById(id);
    }

    @Override
    public String queryApplyIDByTicket(String ticketInstId) {
        return mapper.queryApplyIDByTicket(ticketInstId);
    }

//
//    @Override
//    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//
//        PwdAuthenticationToken token = (PwdAuthenticationToken) auth;
//
//        if (token.getUserName() != null && token.getPassword() != null) {
//            if (!token.getUserName().equals("admin")) {
//                token.setErrCode("1");
//                return null;
//            }
//
//            if (!token.getPassword().equals("123456")) {
//                token.setErrCode("2");
//                return null;
//            }
//
//            SysUserinfo user = new SysUserinfo();
//            user.setUsername(token.getName());
//            user.setPassword(token.getPassword());
//            //认证成功
//            user.setAuthenticated(true);
//
//            /**写入权限*/
//            SysRoleinfo role = SysRoleinfo.getRoleUser();
//            System.out.println(role.getAuthority());
//            Set<GrantedAuthority> accesses = new HashSet<GrantedAuthority>();
//            accesses.add(role);
//
//            user.setAuthorities(accesses);
//
//            return user;
//        }
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<? extends Object> authentication) {
//        return authentication.equals(PwdAuthenticationToken.class);
//    }

    /***
     * 验证数据是否重复
     *
     * @param isadd 是否添加
     * @param user
     * @return
     */
    private boolean valiName(boolean isadd, SysUserinfo user) {
        Map<String, Object> pmap = new HashMap<>();
        pmap.put("username", user.getName().trim());
        if (!isadd) {
            SysUserinfo old = getModelById(user.getId());
            if (old.getName().equals(user.getName())) {//如果名字有修改 验证
                return true;
            }
        }
        List<SysUserinfo> modelByMap = queryListByMap(pmap);//通过名字查询到了 就返回失败
        return !(modelByMap != null && modelByMap.size() > 0);
    }

    @Override
    @Transactional
    public ExecuteResult saveOrUpdate(SysUserinfo user, Integer[] groups, Integer[] roles, Integer[] departmentIds, Integer[] regions) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        if (user == null || !StringHelper.getInstance().isValid(user.getName())) {
            executeResult.setState(false);
            executeResult.setMsg("没有用户信息");
            return executeResult;
        }
        boolean isadd = true;
        if (user.getId() != null && user.getId() > 0) {
            isadd = false;
        }
        boolean b = valiName(isadd, user);
        if (!b) {
            executeResult.setState(false);
            executeResult.setMsg("用户名" + user.getName() + "重复 ");
            return executeResult;
        }
        if (isadd) {
            insert(user);
        } else {
            updateByObject(user);
        }
        if (roles != null) {
            bindUserAndRole(Arrays.asList(user.getId()), Arrays.asList(roles));
        }
        if (groups != null) {
            bindUserAndGroup(Arrays.asList(user.getId()), Arrays.asList(groups));
        }
        if (departmentIds != null) {
            bindUserAndDepartment(Arrays.asList(user.getId()), Arrays.asList(departmentIds));
        }
        if (regions != null) {
            bindUserAndRegion(Arrays.asList(user.getId()), Arrays.asList(regions));
        }
        ConfigAttributeHelper.UserIsModify=true;
        executeResult.setState(true);
        executeResult.setMsg("保存成功");
        return executeResult;
    }

    //绑定用户与区域
    public void bindUserAndRegion(List userids, List regions) throws Exception {
        List<SysLnUserRegion> ls = new ArrayList<>();
        for (Object userid : userids) {
            Long iuserid = Long.parseLong(userid.toString());
            sysLnUserRegionService.deleteById(iuserid);
            for (Object region : regions) {
                Long regionId = Long.parseLong(region.toString());
                if (regionId.equals(0)) {
                    continue;
                }
                SysLnUserRegion ln = new SysLnUserRegion();
                ln.setUserId(iuserid);
                ln.setRegionId(regionId);
                ls.add(ln);
            }
        }
        if (ls.size() > 0) {
            sysLnUserRegionService.insertList(ls);
        }
    }

    //绑定用户与部门
    public void bindUserAndDepartment(List userids, List departmentIds) throws Exception {
        List<SysLnUserDepartment> ls = new ArrayList<>();
        for (Object userid : userids) {
            Long iuserid = Long.parseLong(userid.toString());
            sysLnUserDepartmentService.deleteById(iuserid);
            for (Object departmentId : departmentIds) {
                Long idepartmentId = Long.parseLong(departmentId.toString());
                if (idepartmentId.equals(0)) {
                    continue;
                }
                SysLnUserDepartment ln = new SysLnUserDepartment();
                ln.setUserId(iuserid);
                ln.setDptId(idepartmentId);
                ls.add(ln);
            }
        }
        if (ls.size() > 0) {
            sysLnUserDepartmentService.insertList(ls);
        }
    }
    @Override
    @Transactional
    public ExecuteResult bindUserAndGroup(List userids, List groupids) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        List<LnUserUsergrp> ls = new ArrayList<>();
        for (Object userid : userids) {
            Integer iuserid = Integer.parseInt(userid.toString());
            lnUserUsergrpService.deleteById(iuserid);
            for (Object groupid : groupids) {
                Integer igroupid = Integer.parseInt(groupid.toString());
                if (igroupid.equals(0)) {
                    continue;
                }
                LnUserUsergrp ln = new LnUserUsergrp();
                ln.setUgrpId(igroupid);
                ln.setUserId(iuserid);
                ls.add(ln);
            }
        }
        if (ls.size() > 0){
            lnUserUsergrpService.insertList(ls);
        }
        executeResult.setState(true);
        return executeResult;
    }


    @Override
    @Transactional
    public ExecuteResult bindUserAndRole(List userids, List roleids) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        List<LnUserRole> ls = new ArrayList<>();
        for (Object userid : userids) {
            Integer iuserid = Integer.parseInt(userid.toString());
            lnUserRoleService.deleteById(iuserid);
            for (Object roleid : roleids) {
                Integer iroleid = Integer.parseInt(roleid.toString());
                if (iroleid.equals(0)) {
                    continue;
                }
                LnUserRole ln = new LnUserRole();
                ln.setRoleId(iroleid);
                ln.setUserId(iuserid);
                ls.add(ln);
            }
        }
        if (ls.size() > 0)
            lnUserRoleService.insertList(ls);
        executeResult.setState(true);
        return executeResult;
    }

    @Override
    public Map<Integer, SysUserinfo> getUserMapById(){
        Map<Integer,SysUserinfo> result = new HashMap<>();
        List<SysUserinfo> sysUserinfos = queryList();
        for (SysUserinfo sysUserinfo : sysUserinfos) {
            result.put(sysUserinfo.getId(),sysUserinfo);
        }
        return result;
    }

    @Override
    public SysUserinfo getCurrUser() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            String password = userDetails.getPassword();
            Map<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);
            SysUserinfo modelByMap = getModelByMap(map);
            return modelByMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取当前用户失败",e);
        }
        return null;
    }
    @Override
    public List<Map<String, String>> getRoleToActByUserName(String userCode) {
        // TODO Auto-generated method stub
        return mapper.getRoleToActByUserName(userCode);
    }

    @Override
    public Map<String, String> getUserToActMapByUserName(String userCode) {
        // TODO Auto-generated method stub
        return mapper.getUserToActMapByUserName(userCode);
    }

    @Override
    public Map<String, String> getRoleToActByGroupCode(String groupCode) {
        // TODO Auto-generated method stub
        return mapper.getRoleToActByGroupCode(groupCode);
    }


    /**
     * 获取用户id
     * @param username
     * @param password
     * @return
     */
    @Override
    public String getIdByUserNameAndPassword(String username,String password) {
        return mapper.getIdByUserNameAndPassword(username,password);
    }

    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryListPageMap(page);
    }

    @Override
    public List<Map<String, Object>> queryListMap(SysUserinfo sysUserinfo) {
        return mapper.queryListMap(sysUserinfo);
    }

    @Override
    public List<Map> getAllUser() {
        return mapper.getAllUser();
    }

    @Override
    public List<SysUserinfo> getUserListByTicketUserIds(String user_ids) {
        return mapper.getUserListByTicketUserIds(user_ids);
    }

    /*查询满足条件的所有用户*/
    @Override
    public List<Map<String, Object>> queryUserListByObjectMap(Map<String, Object> map) {
        return mapper.queryUserListByObjectMap(map);
    }

    /*查询满足条件的所有用户 page*/
    @Override
    public List<Map<String, Object>> queryUserListPageByObjectMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryUserListPageByObjectMap(page);
    }

    @Override
    public List<String> getAdminNams() {
        return mapper.getAdminNams();
    }

    /*通过角色将相关用户信息维护到表JBPM_OA_AUTHOR*/
    @Override
    public void authorFromGroup() throws Exception {
        mapper.authorFromGroup();
    }
}
