package com.idc.service;
import java.util.List;

import com.idc.model.ExecuteResult;
import com.idc.model.SysOrganization;
import system.data.supper.service.SuperService;

import utils.tree.TreeNode;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_organization:机构信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 11,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOrganizationService extends SuperService<SysOrganization, Integer>{
    List<TreeNode> getTree(String type, Integer pid);

    /***
     * 获取区域下的跟节点
     * @param region
     * @return
     */
    List<SysOrganization>  getRootNodesByRegion(Integer region);


    List<TreeNode> getTree(Integer defaultLevel, Integer pid, boolean isSimpDate);

    ExecuteResult saveOrUpdate(SysOrganization sysOrganization, Integer porgid) throws Exception;

    /***
     * 删除关系并且删除下级
     * @param id
     * @throws Exception
     */
    void deleteByIdAndDelteleTree(int id) throws Exception;
    /**
	 *   Special code can be written here 
	 */
}
