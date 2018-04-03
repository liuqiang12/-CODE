package com.idc.mapper;

import com.idc.model.PermType;
import com.idc.model.SysPermissioninfo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_permissioninfo:系统权限表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysPermissioninfoMapper extends SuperMapper<SysPermissioninfo, Integer>{
     List<SysPermissioninfo> queryPermissionListByRoleId(List<Integer> roleids);

    List<SysPermissioninfo> getPermissioninfoByTypeAndId(@Param("type") PermType type, @Param("ids") List<Integer> ids);

    void bind(@Param("type") int type, @Param("permitid") Integer permitid, @Param("id") Integer id);
    /**
	 *   Special code can be written here 
	 */
}

 