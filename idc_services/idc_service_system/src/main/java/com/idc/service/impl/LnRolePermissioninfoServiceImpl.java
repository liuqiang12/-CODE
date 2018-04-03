package com.idc.service.impl;

import com.idc.mapper.LnRolePermissioninfoMapper;
import com.idc.model.LnRolePermissioninfo;
import com.idc.service.LnRolePermissioninfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_ln_role_permissioninfo:角色权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("lnRolePermissioninfoService")
public class LnRolePermissioninfoServiceImpl extends SuperServiceImpl<LnRolePermissioninfo, Integer> implements LnRolePermissioninfoService {
	@Autowired
	private LnRolePermissioninfoMapper mapper;

    @Override
    public void deleteByListByRoles(List<String> strings) {
        mapper.deleteByListByRoles(strings);
    }
    /**
	 *   Special code can be written here 
	 */
}
