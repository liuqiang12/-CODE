package com.idc.mapper;

import com.idc.model.SysDictionary;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_dictionary:系统业务字典表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 07,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysDictionaryMapper extends SuperMapper<SysDictionary, Integer>{

	/**
	 *   Special code can be written here 
	 */
	List<SysDictionary> queryListByParentCode(SysDictionary dictionary);

	List<SysDictionary> queryListByParentCode_dsinfo(SysDictionary dictionary);

	List<SysDictionary> queryListByParentCode_dsinfo_qb(SysDictionary dictionary);
}

 