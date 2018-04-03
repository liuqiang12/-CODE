package com.idc.mapper;

import java.util.List;

import com.idc.model.LnUsergrpRole;
import com.idc.model.SysUserGroup;
import system.data.supper.mapper.SuperMapper;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_user_group:用户组<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysUserGroupMapper extends SuperMapper<SysUserGroup, Integer>{
    List<SysUserGroup> queryListByObjectByUserId(Integer userId);

    void bindGroupAndRole(List<LnUsergrpRole> lnUsergrpRoles);

    void cleanBindByGroup(List<LnUsergrpRole> lnUsergrpRoles);

    List<SysUserGroup> getListByUserId(Integer userId);

    /**
	 *   Special code can be written here 
	 */
}

 