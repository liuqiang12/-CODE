package com.idc.action.ipinfo;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpInfo;
import com.idc.service.IdcIpinfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

/**
 * Created by mylove on 2017/6/16.
 */
@Controller
@RequestMapping("/resource/ipinfo")
public class IpInfoAction {
    @Autowired
    private IdcIpinfoService ipinfoService;
    private static final Log logger = LogFactory.getLog(IpInfoAction.class);

    @RequestMapping("/{id}")
    public String form(@PathVariable long id, ModelMap modelMap) {
        IdcIpInfo idcIpSubnet = new IdcIpInfo();
        if (id > 0) {
            idcIpSubnet = ipinfoService.getModelById(id);
        }
        modelMap.put("idcIpSubnet", idcIpSubnet);
        modelMap.put("id", id);
        return "ipsubnet/info";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String actionType,IdcIpInfo idcIpInfo) {
        EasyUIData easyUIData = new EasyUIData();
        if("flowallc".equals(actionType)){
            idcIpInfo.setStatus(0L);
        }
        ipinfoService.queryListPage(page, idcIpInfo);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    //    @RequestMapping("/toallocation/{ids}")
//    public String toAllocation(@PathVariable String ids, ModelMap modelMap) {
//        List<IdcIpSubnet> idcIpSubnets = ipinfoService.getAllocationIP(ids);
//        modelMap.put("idcIpSubnets", idcIpSubnets);
//        return "ipinfo/toallocation";
//    }
    @RequestMapping("/recovery")
    @ResponseBody
    public ExecuteResult recovery(IdcIpInfo ipInfo, ModelMap modelMap) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            return ipinfoService.recovery(ipInfo);
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("回收子网失败");
        }
        return executeResult;
    }

    @RequestMapping("/allocation")
    @ResponseBody
    public ExecuteResult allocation(IdcIpInfo ipInfo, ModelMap modelMap) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            return ipinfoService.allocation(ipInfo);
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("分配子网失败");
        }
        return executeResult;
    }
//
//    @RequestMapping("/del")
//    @ResponseBody
//    public ExecuteResult del(String ids) {
//        ExecuteResult executeResult = new ExecuteResult();
//        try {
//            if (ids == null || "".equals(ids)) {
//                executeResult.setMsg("传入ID错误");
//                return executeResult;
//            }
//            idcIpsubnetService.deleteByList(Arrays.asList(ids.split(",")));
//            executeResult.setState(true);
//        } catch (Exception e) {
//            logger.error("", e);
//            executeResult.setMsg("删除子网失败");
//        }
//
//        return executeResult;
//    }
//
//    @RequestMapping("/splitsubnet/{id}")
//
//    public String splitsubnet(@PathVariable long id, ModelMap map) {
//        IdcIpSubnet idcIpSubnet = idcIpsubnetService.getModelById(id);
//        map.put("idcIpSubnet", idcIpSubnet);
//        return "ipsubnet/split";
//    }
//
//    @RequestMapping("/savesplit")
//    @ResponseBody
//    public ExecuteResult saveSplit(long pid, String rows, ModelMap map) {
//        JSONArray jArray= JSONArray.fromObject(rows);
//        Collection collection = JSONArray.toCollection(jArray, IdcIpSubnet.class);
//        List<IdcIpSubnet> subnetips = new ArrayList<IdcIpSubnet>();
//        subnetips.addAll(collection);
//        ExecuteResult executeResult = idcIpsubnetService.split(pid,subnetips);
//        return  executeResult;
//    }
}
