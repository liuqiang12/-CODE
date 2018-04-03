package com.idc.service;

import com.idc.model.SysDepartment;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_department:部门信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 28,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysDepartmentService extends SuperService<SysDepartment, Integer> {
    List<TreeNode> getTree(Integer pid, String orgid, boolean isSimpDate);

    void saveOrUpdate(SysDepartment sysDepartment, Integer pdptid)throws Exception;

    @Transactional
    void deleteByIdAndDelteleTree(int id) throws Exception;

    List<SysDepartment> getDepartmentListByUserId(Integer userId);
    /**
     *   Special code can be written here
     */
}
