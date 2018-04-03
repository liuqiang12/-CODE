package com.idc.mapper;

import com.idc.model.SysRegion;
import com.idc.model.TreeModel;

import system.data.supper.mapper.SuperMapper;

import java.util.List;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_region:区域表信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 22,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysRegionMapper extends SuperMapper<SysRegion, Integer>{
    List<TreeModel> queryFirstNode();

    List<TreeModel> queryNodes(Integer nodeid);

    /***
     * 获取根节点集合
     * @return
     */
    List<SysRegion> getRootNodes();

    /***
     * 通过父节点获取子节点
     * @param nodeid
     * @return
     */
    List<SysRegion> getNodesByPid(Integer nodeid);
//    List<SysRegion> queryListPageByPid(PageBean<SysRegion> pageBean);
    /**
	 *   Special code can be written here 
	 */

	List<TreeModel> getHomeCityNodes();

	List<TreeModel> getNextCityNodes(Integer id);

    List<SysRegion> getRegionListByUserId(Integer userId);
}

 