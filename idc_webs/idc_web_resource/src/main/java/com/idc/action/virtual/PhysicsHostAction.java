package com.idc.action.virtual;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcDevice;
import com.idc.model.IdcHost;
import com.idc.model.IdcPhysicsHosts;
import com.idc.service.IdcDeviceService;
import com.idc.service.IdcHostService;
import com.idc.service.IdcPhysicsHostsService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author mylove
 *         物理设备映射
 *         Created by mylove on 2017/10/16.
 */
@Controller
public class PhysicsHostAction {
    private static final Logger logger = LoggerFactory.getLogger(PhysicsHostAction.class);
    @Autowired
    private IdcPhysicsHostsService idcPhysicsHostsService;
    @Autowired
    private IdcHostService idcHostService;
    @Autowired
    private IdcDeviceService idcDeviceService;

    public static void main(String[] args) {
        //ToDo
    }

    @RequestMapping("/physicshost/form/{id}")
    public String form(@PathVariable String id, ModelMap map) {
        if (id != null && !"0".equals(id)) {
            IdcPhysicsHosts idcPhysicsHosts = idcPhysicsHostsService.getModelById(id);
            map.put("info", idcPhysicsHosts);
        }
        return "physicshost/form";
    }

    @RequestMapping("/physicshost/")
    public String index() {
        return "physicshost/index";
    }

    @RequestMapping("/physicshost/select")
    public String select(ModelMap map) {
        return "physicshost/select";
    }

    @RequestMapping("/physicshost/list")
    @ResponseBody
    public EasyUIData List(EasyUIPageBean pageBean,String type, IdcPhysicsHosts idcPhysicsHosts, String name, String roomId, Long rackId) {
        idcPhysicsHostsService.queryListPageByMap(pageBean,type, idcPhysicsHosts, name, roomId, rackId);
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setTotal(pageBean.getTotalRecord());
        easyUIData.setRows(pageBean.getItems());
        return easyUIData;
    }

    @RequestMapping("/physicshost/getHostInfo/{deviceid}")
    @ResponseBody
    public Map<String, Object> List(@PathVariable Long deviceid) {
        Map<String, Object> map = new HashMap<>();
        IdcHost host = idcHostService.getModelById(deviceid);
        IdcPhysicsHosts idcPhysicsHosts = idcPhysicsHostsService.getModelById(deviceid.toString());
        map.put("host", host);
        map.put("idcPhysicsHost", idcPhysicsHosts);
        return map;
    }

    @RequestMapping("/physicshost/save")
    @ResponseBody
    public ExecuteResult save(IdcPhysicsHosts idcPhysicsHosts, Long hostId) {
        IdcHost idcHost = null;
        IdcDevice idcDevice = null;
        if (hostId != null && hostId > 0) {
            idcHost = idcHostService.getModelById(hostId);
            idcDevice = idcDeviceService.getModelById(hostId);
        }
        ExecuteResult executeResult = idcPhysicsHostsService.saveOrupdate(idcDevice, idcHost, idcPhysicsHosts);
        return executeResult;
    }
}
