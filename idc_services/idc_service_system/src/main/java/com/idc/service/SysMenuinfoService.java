package com.idc.service;
import com.idc.model.SysMenuinfo;
import com.idc.model.SysPermissioninfo;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_menuinfo:菜单基本信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 17,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysMenuinfoService extends SuperService<SysMenuinfo, Integer>{

	/**
	 *   Special code can be written here 
	 */
	/**
	 * 获取url对应的所有权限
	 * @return
	 */
	List<SysMenuinfo> getUrlAuthorities();

    List<SysMenuinfo> getMenuByPermi(List<SysPermissioninfo> allResourceByUserId);

    /***
     * 获取树形的菜单
     * @return
     */

    List<TreeNode> getUrlAuthoritiesTree(String username);

    List<TreeNode> getTree();

    /***
     * 资源IDs
     * @param pids
     * @return
     */
    List<SysMenuinfo> getMenuByPermiIds(List<Integer> pids);
	List<SysMenuinfo> getUrlAuthoritiesForMenu(Integer userid);

}
