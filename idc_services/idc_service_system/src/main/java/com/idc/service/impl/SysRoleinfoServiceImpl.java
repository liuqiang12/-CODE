package com.idc.service.impl;

import com.idc.mapper.LnRolePermissioninfoMapper;
import com.idc.mapper.SysRoleinfoMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.LnRolePermissioninfo;
import com.idc.model.SysPermissioninfo;
import com.idc.model.SysRoleinfo;
import com.idc.service.SysRoleinfoService;
import com.idc.service.SysUserGroupService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;
import utils.typeHelper.StringHelper;

import java.sql.Timestamp;
import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_roleinfo:系统角色信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 16,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysRoleinfoService")
public class SysRoleinfoServiceImpl extends SuperServiceImpl<SysRoleinfo, Integer> implements SysRoleinfoService {
    @Autowired
    private SysRoleinfoMapper mapper;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private LnRolePermissioninfoMapper lnRolePermissioninfoMapper;

    @Override
    public List<Integer> getRoleIdsListByUserIdAddGroup(Integer userId) {
        List<SysRoleinfo> roleListByUserId = getRoleListByUserIdAddGroup(userId);
        List<Integer> roleids = new ArrayList<>();
        for (SysRoleinfo sysRoleinfo : roleListByUserId) {
            roleids.add(sysRoleinfo.getId());
        }
        return roleids;
    }
//    @Override
//    public List<SysRoleinfo> getRoleListByUserId(Integer userId) {
//        List<SysRoleinfo> roleListByUserId = getRoleListByUserId(userId);
//        List<Integer> roleids = new ArrayList<>();
//        for (SysRoleinfo sysRoleinfo : roleListByUserId) {
//            roleids.add(sysRoleinfo.getId());
//        }
//        return roleids;
//    }
    @Override
    public List<SysRoleinfo> getRoleListByUserId(Integer userId) {
        return getRoleListByUserIdAddGroup(userId,false);
    }
    @Override
    public List<SysRoleinfo> getRoleListByUserIdAddGroup(Integer userId) {
        return getRoleListByUserIdAddGroup(userId,true);
    }

    /***
     * 是否叠加组
     * @param userId
     * @param isAddGroupRole
     * @return
     */
    public List<SysRoleinfo> getRoleListByUserIdAddGroup(Integer userId,boolean isAddGroupRole) {
        Set<SysRoleinfo> roles = new HashSet<>();
        if(isAddGroupRole){
            List<Integer> groups = sysUserGroupService.queryListIdByObjectByUserId(userId);//获取组

            if (groups.size() > 0)
                roles.addAll(mapper.queryListByObjectByGroupId(groups));//获取组角色和用户角色叠加
        }
        roles.addAll(mapper.queryListByObjectByUserId(userId));
        return new ArrayList<>(roles);
    }
    @Override
    public List<SysRoleinfo> queryListByObjectByGroupId(List<Integer> groupids) {
        List<SysRoleinfo> roleinfos = mapper.queryListByObjectByGroupId(groupids);//获取组
        return roleinfos;
    }

    @Override
    @Transactional
    public boolean bindRoleAndPermission(SysRoleinfo sysRoleinfo, List<SysPermissioninfo> permissioninfos) throws Exception {
        lnRolePermissioninfoMapper.deleteById(sysRoleinfo.getId());
        for (SysPermissioninfo permissioninfo : permissioninfos) {
            LnRolePermissioninfo ln = new LnRolePermissioninfo();
            ln.setPermitId(permissioninfo.getId());
            ln.setRoleId(sysRoleinfo.getId());
            lnRolePermissioninfoMapper.insert(ln);
        }
        return true;
//        try {
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
    }
    /***
     * 验证数据是否重复
     * @param isadd 是否添加
     * @param user
     * @return
     */
    private boolean valiName(boolean isadd,SysRoleinfo user){
        Map<String,Object> pmap = new HashMap<>();
        pmap.put("name",user.getName().trim());
        if(!isadd){
            SysRoleinfo old = getModelById(user.getId());
            if(old.getName().equals(user.getName())){//如果名字有修改 验证
                return true;
            }
        }
        List<SysRoleinfo> modelByMap =queryListByMap(pmap);//通过名字查询到了 就返回失败
        return !(modelByMap != null && modelByMap.size() > 0);
    }
    @Override
    public ExecuteResult saveOrUpdate(SysRoleinfo role) {
        ExecuteResult executeResult = new ExecuteResult();
        if(role==null|| !StringHelper.getInstance().isValid(role.getName())){
            executeResult.setState(false);
            executeResult.setMsg("没有角色名信息");
            return executeResult;
        }
        boolean isadd=true;
        if(role.getId()!=null&&role.getId()>0){
            isadd=false;
        }
        boolean b = valiName(isadd, role);
        if(!b){
            executeResult.setState(false);
            executeResult.setMsg("角色名"+role.getName()+"重复 ");
            return executeResult;
        }
        try {
            if(isadd){
                role.setCreateTime(new Timestamp(new Date().getTime()));
                insert(role);
            }else{
                updateByObject(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存角色失败",e);
            executeResult.setMsg("保存角色失败");
            executeResult.setE(e);
            return executeResult;
        }
        executeResult.setState(true);
        executeResult.setMsg("保存角色成功");
        return executeResult;
    }

    @Override
    public List<SysRoleinfo> queryListFilterByObject(SysRoleinfo sysRoleinfo) {
        return mapper.queryListFilterByObject(sysRoleinfo);
    }

    @Override
    public List<SysRoleinfo> getRolAddGroup(Integer userId) {
        return mapper.getRolAddGroup(userId);
    }

    @Override
    public String getRegionsWithUserId(Integer userId) {
        return mapper.getRegionsWithUserId(userId);
    }

    @Override
    public String getGroupsWithUserId(Integer userId) {
        return mapper.getGroupsWithUserId(userId);
    }

    @Override
    public String getRolesWithUserId(Integer userId) {
        return mapper.getRolesWithUserId(userId);
    }

    @Override
    public List<SysRoleinfo> getRoleinfosByUserId(Integer userId) {
        return mapper.getRoleinfosByUserId(userId);
    }

    /*通过组ID获取所有的角色*/
    @Override
    public List<SysRoleinfo> queryRoleListByGroupId(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryRoleListByGroupId(page);
    }
}
