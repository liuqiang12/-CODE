package com.idc.service;

import com.idc.model.SysRegion;
import com.idc.model.TreeModel;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_region:区域表信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysRegionService extends SuperService<SysRegion, Integer>{
    List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer pid);

    List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer pid, boolean isSimpDate);

    List<SysRegion> queryListPage(PageBean<SysRegion> pageBean, Integer region_pid, String regionName);

//    List<SysRegion> queryListPage(PageBean<SysRegion> pageBean, Map<String,Object> queryMap, Integer region_pid);
    /**
	 *   Special code can be written here 
	 */

	TreeModel getBootstrapTree(TreeModel treeModel, Integer nodeid);

    Map<Object,SysRegion> getRegionMapBy(String name);

    List<SysRegion> getAllChild(Integer regionid);

	TreeModel getTree_homeCity();

    List<TreeNode> getTree(Boolean isShowOrg, Integer defaultLevel, Integer id, boolean b, Boolean isrole);
    List<Integer> getDataPermByUser(Integer userid);

    List<TreeModel> getTree_homeCity_Qx();

    List<SysRegion> getRegionListByUserId(Integer userId);

    List<TreeNode> getRegionTree() throws Exception;
}
