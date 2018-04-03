package com.idc.service;

import system.data.supper.service.SuperService;

import com.idc.model.IdcHost;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_HOST:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHostService extends SuperService<IdcHost, Long>{
	/**
	 *   Special code can be written here 
	 */
    //导入主机设备
    void importIdcHostByExcel(List<List<Object>> list) throws Exception;
}
