package com.idc.service;

import com.idc.model.JbpmResourceChildshape;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：资源分配报表</b> <br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface TicketJbpmReportService extends SuperService<JbpmResourceChildshape, Long> {
    //资源报表
    List<Map<String,Object>> getTicketReportListPage(PageBean pageBean, Map<String, Object> map);

    //ip地址使用统计表
    List<Map<String,Object>> getIpTicketReportListPage(PageBean pageBean, Map<String, Object> map);


    //资源回收报表
    List<Map<String,Object>> getRecycleTicketReportListPage(PageBean pageBean, Map<String, Object> map);



}
