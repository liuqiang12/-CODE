package com.idc.mapper;

import com.idc.model.SysMenuinfo;
import com.idc.model.SysPermissioninfo;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_menuinfo:菜单基本信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 17,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysMenuinfoMapper extends SuperMapper<SysMenuinfo, Integer>{

	/**
	 *   Special code can be written here 
	 */
	List<SysMenuinfo> getUrlAuthorities();

    List<SysMenuinfo> getMenuByUser();

    List<SysMenuinfo> getMenuByPermi(List<SysPermissioninfo> allResourceByUserId);

    Set<String> getSysRoleinfoTypes(Integer menuid);
	List<Map> getAllMenu();
	List<SysMenuinfo> getUrlAuthoritiesForMenu(Integer userid);
}

 