package com.idc.mapper;

import system.data.page.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by WCG on 2017/9/18.
 */
public interface TicketJbpmReportMapper {
    //资源报表
    List<Map<String,Object>> getTicketReportListPage(PageBean pageBean);

    //IP地址使用统计表
    List<Map<String,Object>> getIpTicketReportListPage(PageBean pageBean);

    //资源回收报表
    List<Map<String,Object>> getRecycleTicketReportListPage(PageBean pageBean);
}
