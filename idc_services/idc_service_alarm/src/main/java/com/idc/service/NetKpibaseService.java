package com.idc.service;

import com.google.common.collect.ImmutableMap;
import com.idc.model.NetKpibase;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>NET_KPIBASE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetKpibaseService extends SuperService<NetKpibase, Long> {
    ImmutableMap<Long, NetKpibase> getKpiBaseMap();
    /**
     *   Special code can be written here
     */
}
