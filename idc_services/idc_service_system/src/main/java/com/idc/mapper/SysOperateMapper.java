package com.idc.mapper;

import com.idc.model.SysOperate;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_operate:功能模块的操作权限<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOperateMapper extends SuperMapper<SysOperate, Integer>{
    List<SysOperate> getListByPermitId(List<Integer> permitids);

	/***
	 *
	 * @param id
	 * @return
	 */
    List<SysOperate> getListByPermit(Integer id);
    /**
	 *   Special code can be written here 
	 */
	/**
	 * 返回所有的用户操作权限
	 * @return
	 */
	List<Map> getAllSysOperate();
}

 