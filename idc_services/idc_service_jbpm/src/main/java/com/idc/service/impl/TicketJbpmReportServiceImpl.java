package com.idc.service.impl;

import com.idc.mapper.TicketJbpmReportMapper;
import com.idc.model.IdcRunTicketOpen;
import com.idc.model.JbpmResourceChildshape;
import com.idc.service.TicketJbpmReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by WCG on 2017/9/18.
 */
@Service("ticketJbpmReportService")
public  class TicketJbpmReportServiceImpl extends SuperServiceImpl<JbpmResourceChildshape, Long> implements TicketJbpmReportService {

    @Autowired
    private TicketJbpmReportMapper ticketJbpmReportMapper;

    //资源报表
    @Override
    public List<Map<String, Object>> getTicketReportListPage(PageBean pageBean, Map<String, Object> map) {
        /* 查询分页信息 */
        pageBean.setParams(map);
        return ticketJbpmReportMapper.getTicketReportListPage(pageBean);
    }

    //ip地址统计表
    @Override
    public List<Map<String, Object>> getIpTicketReportListPage(PageBean pageBean, Map<String, Object> map) {
        pageBean.setParams(map);
        return ticketJbpmReportMapper.getIpTicketReportListPage(pageBean);
    }

    //资源回收报表
    @Override
    public List<Map<String, Object>> getRecycleTicketReportListPage(PageBean pageBean, Map<String, Object> map) {
        pageBean.setParams(map);
        return ticketJbpmReportMapper.getRecycleTicketReportListPage(pageBean);
    }
}
