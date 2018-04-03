package com.idc.mapper;

import system.data.supper.mapper.SuperMapper;
import com.idc.model.SysDepartment;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_department:部门信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 04,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysDepartmentMapper extends SuperMapper<SysDepartment, Integer>{
	/**
	 *   Special code can be written here 
	 */
    List<SysDepartment> getDepartmentListByUserId(Integer userId);
}

 