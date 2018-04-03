package com.idc.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.SysDictionaryMapper;
import com.idc.model.SysDictionary;
import com.idc.service.SysDictionaryService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_dictionary:系统业务字典表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 07,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl extends SuperServiceImpl<SysDictionary, Integer> implements SysDictionaryService {
	@Autowired
	private SysDictionaryMapper mapper;
	/**
	 *   Special code can be written here 
	 */

	@Override
	public List<SysDictionary> queryListByParentCode(SysDictionary dictionary) {
		// TODO Auto-generated method stub
		return mapper.queryListByParentCode(dictionary);
	}
	@Override
	public List<SysDictionary> queryListByParentCode_dsinfo(
			SysDictionary dictionary) {
		// TODO Auto-generated method stub
		return mapper.queryListByParentCode_dsinfo(dictionary);
	}
	@Override
	public List<SysDictionary> queryListByParentCode_dsinfo_qb(
			SysDictionary dictionary) {
		// TODO Auto-generated method stub
		return mapper.queryListByParentCode_dsinfo_qb(dictionary);
	}
}
