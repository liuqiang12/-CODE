package com.idc.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.SysLnRoleDataPermissionMapper;
import com.idc.model.SysLnRoleDataPermission;
import com.idc.service.SysLnRoleDataPermissionService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_ln_role_data_permission:角色数据权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysLnRoleDataPermissionService")
public class SysLnRoleDataPermissionServiceImpl extends SuperServiceImpl<SysLnRoleDataPermission, Integer> implements SysLnRoleDataPermissionService {
	@Autowired
	private SysLnRoleDataPermissionMapper mapper;

    @Override
    public List<SysLnRoleDataPermission> get(int type, List<Integer> roleids) {
            return mapper.get(type,roleids);
    }

    @Override
    public void deleteBy(int type, List<String> strings) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("userids",strings);
        map.put("usertype",type);
        mapper.deleteByMap(map);
    }

    @Override
    public List<SysLnRoleDataPermission> queryListByTypeRole(int type, Integer roleid) {
            Map<String,Object> map =new HashMap<>();
            map.put("usertype",type);
            map.put("userids",roleid);
            return queryListByMap(map);
    }
    /**
	 *   Special code can be written here 
	 */
}
