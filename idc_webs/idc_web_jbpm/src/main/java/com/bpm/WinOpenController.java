package com.bpm;

import com.idc.service.IdcHisTicketResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.PageBean;
import system.data.supper.action.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/6/29.
 */
@Controller
@RequestMapping("/winOpenController")
public class WinOpenController extends BaseController {
    @Autowired
    private IdcHisTicketResourceService idcHisTicketResourceService;//工单资源服务
    /**
     * 弹出框 start
     */
    //-------------主机-----------------start
    @RequestMapping("/serverLayout.do")
    public String serverLayout(HttpServletRequest request) {
        return "jbpm/winOpen/serverLayout";
    }
    @RequestMapping("/serverGridData.do")
    @ResponseBody
    public PageBean ticketServerResourceQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
       /* map.put("ticketInstId",ticketInstId);
        map.put("category",category);*/
        String serverDeviceName = request.getParameter("serverDeviceName");
        String nodeParam = request.getParameter("nodeParam");

        map.put("serverDeviceName",serverDeviceName);
        map.put("nodeParam",nodeParam);
        List<String> statusList = new ArrayList<String>();
        statusList.add("20");//40代表空闲，20代表可用
        statusList.add("40");//40代表空闲，20代表可用
        map.put("statusList",statusList);
        idcHisTicketResourceService.queryServerResourceListPage(result,map);
        return result;
    }
    //--------------主机----------------end
//-------------IP-----------------start
    @RequestMapping("/ipGridPanel.do")
    public String ipGridPanel(HttpServletRequest request) {
        return "jbpm/winOpen/ipGridPanel";
    }
    @RequestMapping("/ipGridPanelData.do")
    @ResponseBody
    public PageBean ipGridPanelData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
       /* map.put("ticketInstId",ticketInstId);
        map.put("category",category);*/
        String ipIpaddress = request.getParameter("ipIpaddress");
        map.put("ipIpaddress",ipIpaddress);
        List<String> statusList = new ArrayList<String>();
        statusList.add("20");
        statusList.add("30");
        map.put("statusList",statusList);
        idcHisTicketResourceService.queryIpResourceListPage(result,map);
        return result;
    }
    //--------------IP----------------end
//-------------机架-----------------start
    @RequestMapping("/rackGridPanel.do")
    public String rackGridPanel(HttpServletRequest request,org.springframework.ui.Model model) {
        String rackOrracunit = request.getParameter("rackOrracunit");
        String locationId = request.getParameter("locationId");
        model.addAttribute("rackOrracunit",rackOrracunit);
        model.addAttribute("locationId",locationId);
        return "jbpm/winOpen/rackGridPanel";
    }

    @RequestMapping("/rackGridPanel_Temporary.do")
    public String rackGridPanel_Temporary(HttpServletRequest request) {
        return "jbpm/winOpen/rackGridPanel_Temporary";
    }

    @RequestMapping("/rackGridPanelData.do")
    @ResponseBody
    public PageBean rackGridPanelData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
       /* map.put("ticketInstId",ticketInstId);
        map.put("category",category);*/
        String rackName = request.getParameter("rackName");
        String nodeParam = request.getParameter("nodeParam");
        String rackOrracunit = request.getParameter("rackOrracunit");
        String locationId = request.getParameter("locationId");

        map.put("nodeParam",nodeParam);
        map.put("rackName",rackName);
        map.put("rackOrracunit",rackOrracunit);
        map.put("locationId",locationId);
//        List<String> statusList = new ArrayList<String>();
//        statusList.add("20");
//        statusList.add("40");
//        map.put("statusList",statusList);
        idcHisTicketResourceService.queryRackResourceListPage(result,map);
        return result;
    }
    //--------------机架----------------end

    /**
     * 弹出框 end
     */
}
