package com.bpm;

import com.idc.service.TicketJbpmReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WCG on 2017/9/15.
 *
 * 资源报表的controller
 */

@Controller
@RequestMapping("/ticketJbpmReportController")
public class TicketJbpmReportController {

    @Autowired
    private TicketJbpmReportService ticketJbpmReportService;

    //ip地址使用统计报表页面
    @RequestMapping("/ipResourceReport.do")
    public String ipResourceReport(HttpServletRequest request) {
        return "jbpm/report/ipResourceReport";
    }
    //ip地址使用统计报表数据
    @RequestMapping("/ipResourceReportData.do")
    @ResponseBody
    public PageBean ipResourceReportData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;

        Map<String,Object> params=new HashMap<String, Object>();
        //查询的条件
        if(request.getParameter("busName") != null){
            params.put("busName",request.getParameter("busName"));
        }

        ticketJbpmReportService.getIpTicketReportListPage(result,params);

        return result;
    }

    //资源分配报表页面
    @RequestMapping("/preResourceReport.do")
    public String preResourceReport(HttpServletRequest request) {
        return "jbpm/report/preResourceReport";
    }
    //资源分配报表数据
    @RequestMapping("/preResourceReportData.do")
    @ResponseBody
    public PageBean preResourceReportData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> params=new HashMap<String, Object>();
        //查询的条件
        if(request.getParameter("busName") != null){
            params.put("busName",request.getParameter("busName"));
        }
        if(request.getParameter("customerName") != null){
            params.put("customerName",request.getParameter("customerName"));
        }
        List<Map<String, Object>> ticketReportListPage = ticketJbpmReportService.getTicketReportListPage(result, params);

        return result;
    }

    //资源回收报表页面
    @RequestMapping("/recycleResourceReport.do")
    public String recycleResourceReport(HttpServletRequest request) {
        return "jbpm/report/recycleResourceReport";
    }
    //资源回收报表数据
    @RequestMapping("/recycleResourceReportData.do")
    @ResponseBody
    public PageBean recycleResourceReportData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> params=new HashMap<String, Object>();
        //查询的条件
        if(request.getParameter("busName") != null){
            params.put("busName",request.getParameter("busName"));
        }
        if(request.getParameter("customerName") != null){
            params.put("customerName",request.getParameter("customerName"));
        }
        ticketJbpmReportService.getRecycleTicketReportListPage(result,params);

        return result;
    }
}
