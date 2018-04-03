package com.idc.action.virtual;

import com.idc.model.*;
import com.idc.service.IdcResourcesPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.util.List;

/**
 * @author mylove
 *         虚拟池
 *         Created by mylove on 2017/10/16.
 */
@Controller
public class VirtualPoolAction {
    private static final Logger logger = LoggerFactory.getLogger(VirtualPoolAction.class);
    @Autowired
    private IdcResourcesPoolService idcResourcesPoolService;

    public static void main(String[] args) {
        //ToDo
    }

    @RequestMapping("/virtualpool/form/{id}")
    public String form(@PathVariable String id, ModelMap map) {
        if (id != null && !"0".equals(id)) {
            IdcResourcesPool idcResourcesPool = idcResourcesPoolService.getModelById(id);
            map.put("info", idcResourcesPool);
        }
        return "virtualpool/form";
    }

    @RequestMapping("/virtualpool/")
    public String index() {
        return "virtualpool/index";
    }

    @RequestMapping("/virtualpool/list")
    @ResponseBody
    public EasyUIData List(EasyUIPageBean pageBean, IdcResourcesPool resourcesPool) {
        List<IdcResourcesPool> idcResourcesPools = idcResourcesPoolService.queryList();
//        idcResourcesPoolService.queryListPage(pageBean, resourcesPool);
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setTotal(idcResourcesPools.size());
        easyUIData.setRows(idcResourcesPools);
        return easyUIData;
    }
    @RequestMapping("/virtualpool/save")
    @ResponseBody
    public ExecuteResult save(IdcResourcesPool idcPhysicsHosts, String hostids) {
        ExecuteResult executeResult = idcResourcesPoolService.saveOrupdate(idcPhysicsHosts,hostids);
        return executeResult;
    }
    @RequestMapping("/virtualpool/usedbybusi/{poolid}")
    public String usedByBusi(IdcResourcesPool idcPhysicsHosts, @PathVariable long  poolid) {
        return "virtualpool/busi";
    }
}
