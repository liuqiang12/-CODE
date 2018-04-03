package com.idc.service;
import java.util.List;

import system.data.supper.service.SuperService;

import com.idc.model.SysDictionary;



/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_dictionary:系统业务字典表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 07,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysDictionaryService extends SuperService<SysDictionary, Integer>{

	/**
	 *   Special code can be written here 
	 */
	List<SysDictionary> queryListByParentCode(SysDictionary dictionary);

	List<SysDictionary> queryListByParentCode_dsinfo(SysDictionary dictionary);

	List<SysDictionary> queryListByParentCode_dsinfo_qb(SysDictionary dictionary);
}
