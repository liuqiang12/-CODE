package com.idc.service;
import java.math.BigDecimal;
import java.util.List;

import system.data.page.EasyUIPageBean;
import system.data.supper.service.SuperService;

import com.idc.model.IdcVmHosts;



/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_VM_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcVmHostsService extends SuperService<IdcVmHosts, String>{
    void queryListVoPage(EasyUIPageBean pageBean, IdcVmHosts idcVmHosts);
    /**
	 *   Special code can be written here 
	 */
}
