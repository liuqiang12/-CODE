package com.idc.service;
import com.idc.model.SysOperate;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_operate:功能模块的操作权限<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOperateService extends SuperService<SysOperate, Integer>{

    List<SysOperate> getListByPermitId(List<Integer> permitids);
    List<SysOperate> getListByPermit(Integer id);

    List<TreeNode> getTree();


    /**
	 *   Special code can be written here 
	 */
}
