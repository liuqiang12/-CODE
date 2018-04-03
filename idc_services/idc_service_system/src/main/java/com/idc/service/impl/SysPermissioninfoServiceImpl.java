package com.idc.service.impl;

import com.idc.mapper.SysPermissioninfoMapper;
import com.idc.model.*;
import com.idc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.authority.security.ConfigAttributeHelper;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_permissioninfo:系统权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysPermissioninfoService")
public class SysPermissioninfoServiceImpl extends SuperServiceImpl<SysPermissioninfo, Integer> implements SysPermissioninfoService {
    @Autowired
    private SysPermissioninfoMapper mapper;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    @Autowired
    private SysMenuinfoService sysMenuinfoService;
    @Autowired
    private SysOperateService sysOperateService;
    @Autowired
    private SysRegionService sysRegionService;
    @Autowired
    private LnRolePermissioninfoService lnRolePermissioninfoService;
    @Autowired
    private SysLnRoleDataPermissionService sysLnRoleDataPermissionService;

    @Override
    public List<SysPermissioninfo> getAllResourceByUserId(Integer userId) {
        List<Integer> roleids = sysRoleinfoService.getRoleIdsListByUserIdAddGroup(userId);
        return roleids != null && !roleids.isEmpty() ? queryPermissionListByRoleId(roleids) : null;
    }

    @Override
    public List<SysMenuinfo> getUrlAuthorities() {
        return sysMenuinfoService.getUrlAuthorities();
    }

    /**
     * 通过角色获取权限信息
     * @param roleids
     * @return
     */
    @Override
    public List<SysPermissioninfo> queryPermissionListByRoleId(List<Integer> roleids) {
        return mapper.queryPermissionListByRoleId(roleids);
    }

    @Override
    public List<String> getAllKeysByUserId(Integer id) {
        List<SysPermissioninfo> allResourceByUserId = getAllResourceByUserId(id);
        List<Integer> ids = new ArrayList<Integer>();
//        List<String>keys =  mapper.getKey(ids);
        return null;
    }

    @Override
    public List<SysMenuinfo> getUrlAuthoritiesForMenu(Integer userid) {
        return sysMenuinfoService.getUrlAuthoritiesForMenu(userid);
    }

    @Override
    public List<SysPermissioninfo> getPermissioninfoByTypeAndId(PermType type, List<Integer> ids) {
        List<SysPermissioninfo> allResourceByUserId = new ArrayList<>();
        try {
            if (ids == null || ids.size() == 0) {
                return allResourceByUserId;
            }
            allResourceByUserId = mapper.getPermissioninfoByTypeAndId(type, ids);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取" + type + "资源失败", e);
        }
        return allResourceByUserId;
    }

    @Override
    @Transactional
    public void bindByRole(String roleids, String menuids, String operaids) throws Exception {
        List<SysPermissioninfo> alls = new ArrayList<>();
        lnRolePermissioninfoService.deleteByListByRoles(StringUtils.StringToArray(roleids));
        alls.addAll(getPermissioninfoByTypeAndId(PermType.MENU, StringUtils.ArrayToIntArray(StringUtils.StringToArray(menuids))));
        alls.addAll(getPermissioninfoByTypeAndId(PermType.BUTTON, StringUtils.ArrayToIntArray(StringUtils.StringToArray(operaids))));
        List<LnRolePermissioninfo> lns = new ArrayList<>();
        //暂时就区域信息
        //List<SysLnRoleDataPermission> lnRoleDataPermissions = new ArrayList<>();
        //List<String> regionidList = StringUtils.StringToArray(regionids);
        LnRolePermissioninfo s;
        SysLnRoleDataPermission lnRoleDataPermission;
        for (String role : StringUtils.StringToArray(roleids)) {
            for (SysPermissioninfo all : alls) {
                s = new LnRolePermissioninfo();
                s.setRoleId(Integer.parseInt(role));
                s.setPermitId(all.getId());
                lns.add(s);
            }
//            for (String region : regionidList) {
//                lnRoleDataPermission = new SysLnRoleDataPermission();
//                lnRoleDataPermission.setPermId(Integer.valueOf(region));
//                lnRoleDataPermission.setUserId(Integer.parseInt(role));
//                lnRoleDataPermission.setUserType(0);
//                lnRoleDataPermission.setPermType(0);
//                lnRoleDataPermissions.add(lnRoleDataPermission);
//            }
        }
        sysLnRoleDataPermissionService.deleteBy(0, Arrays.asList(roleids));
        if (lns.size() > 0)
            lnRolePermissioninfoService.insertList(lns);
//        if (lnRoleDataPermissions.size() > 0)
//            sysLnRoleDataPermissionService.insertList(lnRoleDataPermissions);
        ConfigAttributeHelper.isModify=true;
        //TODO 保存数据的权限
    }

    @Override
    @Transactional
    public void save(SysMenuinfo menuinfo, SysOperate operate, int type) throws Exception {
        SysPermissioninfo s = new SysPermissioninfo();
        s.setPermissionType(type == 0 ? "1000" : (type == 1 ? "1003" : ""));
        s.setCreateTime(new Timestamp(new Date().getTime()));
        mapper.insert(s);
        Integer id = null;
        if (type == 0) {
            menuinfo.setCreateTime(new Timestamp(new Date().getTime()));
            sysMenuinfoService.insert(menuinfo);
            id = menuinfo.getId();
        }
        if (type == 1) {
            operate.setCreateTime(new Timestamp(new Date().getTime()));
            sysOperateService.insert(operate);
            id = operate.getId();
        }
        mapper.bind(type, s.getId(), id);
    }

    @Override

    public void delete(int type, int id) {

    }

    @Override
    public List<Long> getRegionsByRole(Integer roleid) throws Exception {

        List<Long> ids = new ArrayList<>();
        if (roleid == null || roleid.intValue() == 0) {
            return new ArrayList<>();
        }
        SysRoleinfo modelById = sysRoleinfoService.getModelById(roleid);
        if ("admin".equals(modelById.getType()) || "ADMIN".equals(modelById.getType())) {
            List<SysRegion> regions = sysRegionService.queryList();
            for (SysRegion region : regions) {
                ids.add(region.getId());
            }
        } else {
            List<SysLnRoleDataPermission> sysLnRoleDataPermissions = sysLnRoleDataPermissionService.queryListByTypeRole(0, roleid);
            for (SysLnRoleDataPermission sysLnRoleDataPermission : sysLnRoleDataPermissions) {
                if (sysLnRoleDataPermission.getPermType() == null || 0 == sysLnRoleDataPermission.getPermType().intValue()) {
                    ids.add(sysLnRoleDataPermission.getPermId().longValue());
                }
            }
        }
        return ids;
    }
}
