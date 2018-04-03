package com.idc.action.monitor;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIPageBean;
import utils.tree.TreeBuilder;
import utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
@Controller
@RequestMapping("/busiPort")
public class BusiPortAction {
    private static final Logger logger = LoggerFactory.getLogger(BusiPortAction.class);
    @Autowired
    private BusPortService busPortService;
    @Autowired
    private NetPortFlowService netPortFlowService;
    @Autowired
    public IdcReCustomerService idcReCustomerService;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private IdcDeviceService idcDeviceService;
    @RequestMapping("/index")
    public String index() {
        return "busiport/index";
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<NetBusiPortFlow> list(EasyUIPageBean page, String key, String gids) {
        List<String> gidarr= new ArrayList<>();
        if(StringUtils.isNotEmpty(gids)){
            gidarr.addAll(Arrays.asList(gids.split(",")));
        }
        List<NetBusiPortFlow> maps = busPortService.queryListPage(key, gidarr);
        return maps;
    }

    @ResponseBody
    @RequestMapping("/getcustomer")
    public List<Map<String, Object>> getCustomer() {
        List<Map<String, Object>> maps = idcReCustomerService.queryAllCustomer();
        for (Map<String, Object> map : maps) {
            map.put("id", map.get("ID"));
            map.put("text", map.get("NAME"));
        }
        return maps;
    }
    @RequestMapping("/reloadflow/{id}/{startTime}/{endTime}")
    @ResponseBody
    public int reloadFlow(@PathVariable final String id, @PathVariable final String  startTime,  @PathVariable final String  endTime, ModelMap map) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if("all".equals(id)){
//                        netPortFlowService.ZipBusiFlow(startTime,endTime);
//                    }else{
//                        netPortFlowService.ZipBusiFlow(Long.parseLong(id),startTime,endTime);
//                    }
//                } catch (NumberFormatException e) {
//                   logger.error("刷新数据错误{},{},{}",id,startTime,endTime,e);
//                }
//
//            }
//        }).run();
        try {
            if("all".equals(id)){
                netPortFlowService.ZipBusiFlow(startTime,endTime);
            }else{
                netPortFlowService.ZipBusiFlow(Long.parseLong(id),startTime,endTime);
            }
        } catch (NumberFormatException e) {
            logger.error("刷新数据错误{},{},{}",id,startTime,endTime,e);
        }
        return 0;
    }
    @RequestMapping("/info/{action}/{id}")
    public String info(@PathVariable String id, @PathVariable String action, ModelMap map) {
        if (!"0".equals(id)) {
            List<IdcDevice> idcDevices = idcDeviceService.queryList();
            ImmutableMap<String, IdcDevice> deviceMap = Maps.uniqueIndex(idcDevices, new Function<IdcDevice, String>() {
                @Override
                public String apply(IdcDevice idcDevice) {
                    return idcDevice.getDeviceid().toString();
                }
            });
            NetBusiPort netBusiPort = busPortService.queryById(id);
            if(netBusiPort!=null){
                List<NetBusiGroup> netBusiGroups = busPortService.groupList(false);
                ImmutableMap<String, NetBusiGroup> stringNetBusiGroupImmutableMap = Maps.uniqueIndex(netBusiGroups, new Function<NetBusiGroup, String>() {
                    @Override
                    public String apply(NetBusiGroup netBusiGroup) {
                        return netBusiGroup.getId();
                    }
                });
                List<NetBusiGroup> groups = new ArrayList<>();
                for (Long aLong : netBusiPort.getGroups()) {
                    NetBusiGroup netBusiGroup = stringNetBusiGroupImmutableMap.get(aLong.toString());
                    if(netBusiGroup!=null){
                        groups.add(netBusiGroup);
                    }
                }
                map.put("groups", groups);
                map.put("customer", netBusiPort.getCustomer());
                map.put("descr", netBusiPort.getDesc());
                map.put("businame", netBusiPort.getBusiportname());
                map.put("bandwidth", netBusiPort.getBandwidth());
                List<Long> portids = netBusiPort.getPortids();
                List<NetPort> ports = new ArrayList<>();
                for (Long portid : portids) {
                    NetPort modelById = netPortService.getModelById(portid);
                    IdcDevice idcDevice = deviceMap.get(modelById.getDeviceid().toString());
                    if(idcDevice!=null){
                        modelById.setCustomername(idcDevice.getName());
                    }
                    ports.add(modelById);
                }
                map.put("ports", ports);

            }
//            List<Map<String, Object>> maps = (List<Map<String, Object>>) netBusiPorts;
//            map.put("ports", maps);
//            if (maps.size() > 0) {
//                map.put("groupids", maps.get(0).get("GROUPID"));
//                map.put("customer", maps.get(0).get("CUSTOMER"));
//                map.put("descr", maps.get(0).get("DESCR"));
//                map.put("businame", maps.get(0).get("BUSIPORTNAME"));
//            }
        } else {
            map.put("businame", "");
        }
        return "busiport/info";
    }

    @RequestMapping("/bindPort")
    @ResponseBody
    public ExecuteResult bindPort(NetBusiPort netBusiPort,String action, String ports,String groupIds) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> portidarr = new ArrayList<>();
            List<String> grouparr = new ArrayList<>();
            if (ports != null && !"".equals(ports.trim())) {
                portidarr = Arrays.asList(ports.split(","));
            }
            if (groupIds != null && !"".equals(groupIds.trim())) {
                grouparr = Arrays.asList(groupIds.split(","));
            }
            executeResult = busPortService.saveBusiInfo(netBusiPort,portidarr,grouparr);
            return executeResult;
        } catch (Exception e) {
            logger.error("",e);
            executeResult.setMsg("保存失败");
        }
        return executeResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ExecuteResult delete(String typeid) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            executeResult = busPortService.deleteBy(typeid.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("删除失败");
        }
        return executeResult;
    }

    @RequestMapping("/deal/{busiPortNames}")
//    @ResponseBody
    public String deal(@PathVariable String busiPortNames, ModelMap map) {
        return "busiport/deal";
    }

    @RequestMapping("/dealflow")
    @ResponseBody
    public List<NetPortFlow> dealflow(String portid) {
        List<NetPortFlow> flows = busPortService.deal(portid);
        return flows;
    }

    @RequestMapping("/group/save")
    @ResponseBody
    public ExecuteResult saveGroup(NetBusiGroup netBusiGroup) {
        return busPortService.saveGroup(netBusiGroup);
    }

    @RequestMapping("/group/delete/{id}")
    @ResponseBody
    public ExecuteResult delGroup(@PathVariable String id) {
        return busPortService.delGroup(id);
    }

    @RequestMapping("/group/add")
    public String addGroup() {
        return "busiport/editgroup";
    }

    @RequestMapping("/group/list")
    @ResponseBody
    public List<NetBusiGroup> groupList(Integer isSimple) {
        List<NetBusiGroup> netBusiGroups = busPortService.groupList(isSimple!=null&&isSimple==1);
        for (NetBusiGroup netBusiGroup : netBusiGroups) {
            netBusiGroup.setOpen(true);
        }
        return netBusiGroups;
    }
    @RequestMapping("/listbytree")
    @ResponseBody
    public List<TreeNode> ListByTree(Integer isSimple) {
        List<NetBusiPort> list = busPortService.queryList();
        List<NetBusiGroup> netBusiGroups = busPortService.groupList(false);
        List<TreeNode> nodes = new ArrayList<>();
        for (NetBusiGroup netBusiGroup : netBusiGroups) {
            TreeNode tmp = new TreeNode();
            tmp.setId("g_"+netBusiGroup.getId());
            tmp.setParentId("g_" + netBusiGroup.getParentId());
            tmp.setName(netBusiGroup.getName()+"【业务分组】");
            tmp.setOpen(true);
            nodes.add(tmp);
        }
        for (NetBusiPort netBusiPort : list) {
            int size = netBusiPort.getGroups().size();
            if(size>0){
                for (Long g : netBusiPort.getGroups()) {
                    TreeNode tmp = new TreeNode();
                    tmp.setId(netBusiPort.getId().toString());
                    tmp.setParentId("g_" + g);
                    tmp.setName(netBusiPort.getBusiportname());
                    nodes.add(tmp);
                }
            }else{
                TreeNode tmp = new TreeNode();
                tmp.setId(netBusiPort.getId().toString());
                tmp.setParentId("g_");
                tmp.setName(netBusiPort.getBusiportname());
                nodes.add(tmp);
            }
        }
        List<TreeNode> treeNodes = TreeBuilder.formatTree(nodes);
        return treeNodes;
    }
}
