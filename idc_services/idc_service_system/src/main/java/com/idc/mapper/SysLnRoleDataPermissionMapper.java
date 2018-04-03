package com.idc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.SysLnRoleDataPermission;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_ln_role_data_permission:角色数据权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysLnRoleDataPermissionMapper extends SuperMapper<SysLnRoleDataPermission, Integer>{
    List<SysLnRoleDataPermission> get(@Param("usertype") int type, @Param("userids") List<Integer> roleids);
    /**
	 *   Special code can be written here 
	 */
}

 