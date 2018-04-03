package com.idc.action;

import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mylove on 2017/5/8.
 */
@Controller

public class ResourceAction {
    @Autowired
    private IdcLocationService idcLocationService;
    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @Autowired
    private IdcBuildingService idcBuildingService;
    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private IdcDeviceService idcDeviceService;
    @Autowired
    private ResourceViewService resourceViewService;
    @Autowired
    private IdcIpinfoService ipinfoService;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private IdcRackunitService idcRackunitService;
    @Autowired
    private IdcHisTicketService idcHisTicketService;
    @Autowired
    private BusPortService busPortService;
    @Autowired
    private NetPortFlowService netPortFlowService;
    @Autowired
    private IdcReCustomerService idcReCustomerService;


    @RequestMapping("/resource_center")
    public String resource_center(HttpServletRequest request, ModelMap map) {
        List<IdcLocation> idcLocations = idcLocationService.queryList();
        if(idcLocations.size()>0){
            for (IdcLocation idcLocation : idcLocations) {
                Integer localId = idcLocation.getId();
                IdcLocationCount dataViewCount = resourceViewService.getModelById(Long.valueOf(localId.toString()));
                resourceViewService.reloadCount(Long.parseLong(localId.toString()), dataViewCount);
            }
//            IdcLocation idcLocation = idcLocations.get(0);
//            Integer localId = idcLocation.getId();
//            IdcLocationCount dataViewCount = resourceViewService.queryList().get(0);
//            resourceViewService.reloadCount(Long.parseLong(localId.toString()), dataViewCount);
//            JSONObject jsonObject = JSONObject.fromObject(dataViewCount);
//            jsonObject.put("localName",idcLocationService.getModelById(localId).getName());
//            EasyUIPageBean pageBean = new EasyUIPageBean(1,1);
//            IdcBuilding idcBuilding = new IdcBuilding();
//            idcBuilding.setLocationid(localId);
//            List<IdcBuilding> idcBuildings = idcBuildingService.queryListPage(pageBean, idcBuilding);
//            jsonObject.put("buildtotal",pageBean.getTotalRecord());
//            long rooms = resourceViewService.getRoomCount(localId);
//            long builds =  resourceViewService.getBuildCount(localId);
//            long odfs =  resourceViewService.getOdfCount(localId);
//            long pdfs = resourceViewService.getPdfCount(localId);
//            //更新机房统计值
//            resourceViewService.reloadRoomStatistics();
//
//            jsonObject.put("buildtotal",builds);
//            jsonObject.put("odftotal",odfs);
//            jsonObject.put("pdftotal",pdfs);
//            jsonObject.put("roomtotal",rooms);
//
//
//            map.put("dataviewstr", jsonObject.toString());
//            map.put("dataview", jsonObject);
//            map.put("locationId", localId);
        }
        return "maincenter/resource_center";
    }


    @RequestMapping("/resource/{type}")
    public String Index(@PathVariable String type, ModelMap map) {
        String searchStr = "名称";
        if ("device".equals(type)) {
            searchStr = "名称/IP";
        }
        map.put("type", type);
        map.put("searchStr", searchStr);
        return "resource/index";
    }

    @RequestMapping("/resource/{type}/{id}")
    public String Info(@PathVariable String type, @PathVariable int id, ModelMap map) {
        map.put("id", id);
        return type + "/info";
    }

    @RequestMapping("/resource/{type}/{id}/{actionType}")
    public String View(@PathVariable String type, @PathVariable int id, @PathVariable String actionType, ModelMap map) {
        map.put("id", id);
        map.put("actionType", actionType);
        return type + "/info";
    }

    @RequestMapping("/resource/tree")
    @ResponseBody
    public List<ModelMap> Tree(String roomIds, String itype,String locationId, String deviceclass,String isShowRack) {
        List<ModelMap> result = new ArrayList<>();
        ModelMap tmp;
        /***************************************数据中心*************************/
        List<IdcLocation> idcLocations = new ArrayList<>();
        if(locationId!=null&&!"".equals(locationId)&&!"null".equals(locationId)){
            IdcLocation idcLocation = idcLocationService.getModelById(Integer.parseInt(locationId));
            idcLocations.add(idcLocation);
        }else{
            idcLocations = idcLocationService.queryList();
        }
        for (IdcLocation idcLocation : idcLocations) {
            tmp = new ModelMap();
            tmp.put("name", idcLocation.getName());
            tmp.put("id", "location_" + idcLocation.getId());
            tmp.put("open", "true");
            result.add(tmp);
        }
        if (itype != null) {
            int newItype = Integer.parseInt(itype);
            /***************************************机楼*************************/
            if (newItype > 0) {
                List<IdcBuilding> idcBuildings = new ArrayList<>();
                if(locationId!=null&&!"".equals(locationId)&&!"null".equals(locationId)){
                    List locationIdlist = new ArrayList();
                    locationIdlist.add(locationId);
                    idcBuildings = idcBuildingService.queryListByLocationIdList(locationIdlist);
                }else{
                    idcBuildings = idcBuildingService.queryList();
                }
                for (IdcBuilding idcBuilding : idcBuildings) {
                    tmp = new ModelMap();
                    tmp.put("open", "true");
                    String name=idcBuilding.getName();
                    name = getName(name,"_",2);
                    tmp.put("name", name);
                    tmp.put("id", "building_" + idcBuilding.getId());
                    tmp.put("pId", "location_" + idcBuilding.getLocationid());
                    result.add(tmp);
                }
            }
            /*************************封装查询条件start*************************/
            Map<String,Object> mapQ = new HashMap<>();
            List<String> roomIdList = new ArrayList<>();
            if(locationId!=null&&!"".equals(locationId)&&!"null".equals(locationId)){
                mapQ.put("locationId",locationId);
            }
            if (roomIds != null && !"".equals(roomIds) && !"null".equals(roomIds)) {
                roomIdList = Arrays.asList(roomIds.split("_"));
                mapQ.put("roomIdList",roomIdList);
            }
            /*************************封装查询条件end*************************/
            /***************************************机房*************************/
            if (newItype > 1) {
                List<ZbMachineroom> zbMachinerooms = null;
                zbMachinerooms = zbMachineroomService.queryZbMachineroomListByIds(mapQ);
                for (ZbMachineroom zbMachineroom : zbMachinerooms) {
                    tmp = new ModelMap();
                    String name=zbMachineroom.getSitename();
                    name = getName(name,"-",1);
                    tmp.put("name", name);
                    tmp.put("id", "idcroom_" + zbMachineroom.getId());
                    tmp.put("pId", "building_" + zbMachineroom.getBuildingid());
                    result.add(tmp);
                }
            }
            /***************************************机架*************************/
            if(isShowRack!=null&&"Y".equals(isShowRack)&&newItype > 2){
                List<String> businesstypeList = new ArrayList<String>();
                businesstypeList.add("equipment");
                businesstypeList.add("cabinet");
                mapQ.put("businesstypeList",businesstypeList);
                List<IdcRack> idcRacks = idcRackService.queryRackListByLocationId(mapQ);
                for (IdcRack idcRack : idcRacks) {
                    tmp = new ModelMap();
                    tmp.put("name", idcRack.getName());
                    tmp.put("id", "idcrack_" + idcRack.getId());
                    tmp.put("pId", "idcroom_" + idcRack.getRoomid());
                    result.add(tmp);
                }
            }
            /***************************************设备*************************/
            if (newItype > 3) {
                if(deviceclass!=null&&!"".equals(deviceclass)&&!"null".equals(deviceclass)){
                    mapQ.put("deviceclass",deviceclass);
                }
                mapQ.put("isvirtualdevice",1);
                List<Map<String,Object>> idcDevices = idcDeviceService.queryListByRoomIds(mapQ);
                for (Map<String,Object> deviceMap : idcDevices) {
                    tmp = new ModelMap();
                    tmp.put("name", deviceMap.get("NAME"));
                    tmp.put("id", "device_" + deviceMap.get("DEVICEID"));
                    if(isShowRack!=null&&"Y".equals(isShowRack)){
                        tmp.put("pId", "idcrack_" + deviceMap.get("RACK_ID"));
                    }else{
                        tmp.put("pId", "idcroom_" + deviceMap.get("MID"));
                    }
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    private String getName(String name,String rep,int length) {
        String[] split = name.split(rep);
        if(split.length>(length-1)){
            StringBuffer sb= new StringBuffer("");
            for (int i = length; i > 0; i--) {
                sb.append( split[split.length-i]);
                if(i>1){
                    sb.append(rep);
                }
            }
            name=  sb.toString();
        }
        return name;
    }

    //获取客户相关信息
    @RequestMapping("/resource/main_customer_info")
    public String getCustomerInfo(ModelMap map, Long customerId) {
        Map<String, Object> resourceNumInfos = null;
        List<Map<String, Object>> rackInfos = null;
        List<Map<String, Object>> ipInfos = null;
        List<Map<String, Object>> portInfos = null;
        Map<String,Object> portFlowMap = null;
        Long ticketNum = null;
        try {
            //预植数据到idc_ipinfo_temp 表中
            ipinfoService.saveCustomerUsedIpToIpintoTemp(customerId);
            //获取该客户相关资源数量
            resourceNumInfos = resourceViewService.getIdcResourceCountByCustomerId(customerId);
            //获取订单数
            ticketNum = idcHisTicketService.queryEndTicketByCustomerIdCount(customerId);
            //查询条件map
            Map queryMap = new HashedMap();
            queryMap.put("cusId", customerId);
            queryMap.put("rowNum", 8);//查询条数为rowNum-1
            //查询客户视图  table相关数据
            //占用机架
            rackInfos = idcRackService.queryUseredRackByCustomerId(queryMap);
            //占用Ip
            ipInfos = ipinfoService.queryUseredIPByCustomerId(queryMap);
            //占用端口
            portInfos = netPortService.queryUseredPortByCustomerId(queryMap);
            //装载客户业务端口流量峰值数据
//            portFlowMap = openCustomerPortFlowInfo(customerId.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("resourceNumInfos", resourceNumInfos);
        map.put("ticketNum", ticketNum);
        map.put("rackInfos", rackInfos);
        map.put("ipInfos", ipInfos);
        map.put("portInfos", portInfos);
        map.put("customerId", customerId);
//        map.put("portFlowMap",com.alibaba.fastjson.JSONObject.toJSON(portFlowMap));
        return "/mainpage/main_customer_info";
    }

    @RequestMapping("/resource/preQueryResourceInfos")
    public String preQueryResourceInfos(ModelMap map, String type, Long customerId) {
        map.put("customerId", customerId);
        map.put("type", type);
        return "/mainpage/ticket_resource_info";
    }

    @RequestMapping("/resource/queryResourceInfos")
    @ResponseBody
    public EasyUIData queryResourceInfos(EasyUIPageBean page, String name, String type, Long customerId) {
        EasyUIData easyUIData = new EasyUIData();
        List<Map<String, Object>> list = null;
        Map map = new HashedMap();
        map.put("selectKey", name);
        map.put("cusId", customerId);
        try {
            if (type != null && ("rack".equals(type) || "rackNum".equals(type))) {//机架table  机架列表
                list = idcRackService.queryUseredRackByCustomerIdPage(page, map);
            } else if (type != null && "ip".equals(type)) {//iptable
                list = ipinfoService.queryUseredIPByCustomerIdPage(page, map);
            } else if (type != null && "ipNum".equals(type)) {//ip列表
                list = ipinfoService.queryUseredIPinfoByCustomerIdPage(page, map);
            } else if (type != null && "rackunitNum".equals(type)) {//机位列表
                list = idcRackunitService.queryUseredRackunitinfoByCustomerIdPage(page, map);
            } else if (type != null && "port".equals(type)) {//端口table
                list = netPortService.queryUseredPortByCustomerIdPage(page, map);
            } else if (type != null && "ticketNum".equals(type)) {//订单列表
                list = idcHisTicketService.queryEndTicketByCustomerId(page, map);
            }
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return easyUIData;
    }

    /*通过客户ID获取客户业务端口峰值流量数据    最近一个月*/
    public Map<String,Object> openCustomerPortFlowInfo(String customerId) {
        Map<String, Object> resultMap = new HashedMap();
        Map<String,Object> returnmap = new HashedMap();
        //获取当前客户业务端口名称
//        List<String> portnameList = busPortService.queryPortNameListByCustomerId(customerId);
        //获取有流量的端口
        List<String> newPortnameList = new ArrayList<>();
        //获取当前客户业务端口最近30天流量峰值
        List<Map<String, Object>> list = netPortFlowService.queryCustomerMaxPortFlowByCustomerId(customerId);
        for(Map map : list){
            if(newPortnameList.toString().indexOf(String.valueOf(map.get("PORTNAME")))==-1){
                newPortnameList.add(String.valueOf(map.get("PORTNAME")));
            }
        }
        for(String name : newPortnameList){
            List<String> outFlows = new ArrayList<>();
            List<String> inFlows = new ArrayList<>();
            List<String> timeStrs = new ArrayList<>();
            for(Map map : list){
                if (String.valueOf(map.get("PORTNAME")).equals(name)) {
                    outFlows.add(String.valueOf(map.get("OUTFLOW")));
                    inFlows.add(String.valueOf(map.get("INFLOW")));
                    timeStrs.add(String.valueOf(map.get("RECORDTIME")));
                }
            }
            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("outFlows",outFlows);
            paramsMap.put("inFlows",inFlows);
            paramsMap.put("timeStrs",timeStrs);
            resultMap.put(name, paramsMap);
        }
//        for (String portName : portnameList) {
//            List<String> outFlows = new ArrayList<>();
//            List<String> inFlows = new ArrayList<>();
//            List<String> timeStrs = new ArrayList<>();
//            for (Map map : list) {
//                if (String.valueOf(map.get("PORTNAME")).equals(portName)) {
//                    outFlows.add(String.valueOf(map.get("OUTFLOW")));
//                    inFlows.add(String.valueOf(map.get("INFLOW")));
//                    timeStrs.add(String.valueOf(map.get("RECORDTIME")));
//                    if(newPortnameList.toString().indexOf(portName)==-1){
//                        newPortnameList.add(portName);
//                    }
//                }
//            }
//            Map<String,Object> paramsMap = new HashMap<>();
//            paramsMap.put("outFlows",outFlows);
//            paramsMap.put("inFlows",inFlows);
//            paramsMap.put("timeStrs",timeStrs);
//            resultMap.put(portName, paramsMap);
//        }
        returnmap.put("portnameList",newPortnameList);
        returnmap.put("resultMap",resultMap);
        return returnmap;
    }

    //获取客户相关信息 并打开客户信息视图
    @RequestMapping("/resource/main_customer_resource")
    public String getCustomerResource(ModelMap map, Long customerId) {
        Map<String, Object> resourceNumInfos = null;
        List<Map<String, Object>> rackInfos = null;
        List<Map<String, Object>> ipInfos = null;
        List<Map<String, Object>> portInfos = null;
//        Map<String,Object> portFlowMap = null;
        IdcReCustomer idcReCustomer = null;
        Long ticketNum = null;
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> list = idcReCustomerService.queryAllCustomer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> param = new HashedMap();
                param.put("value1", list.get(i).get("ID"));
                param.put("label", list.get(i).get("NAME"));
                param.put("value2", list.get(i).get("ADDR"));
                result.add(param);
            }
        }
        if(customerId!=null){
            try {
                //客户
                idcReCustomer = idcReCustomerService.getModelByReCustomerId(customerId);
                //预植数据到idc_ipinfo_temp 表中
                ipinfoService.saveCustomerUsedIpToIpintoTemp(customerId);
                //获取该客户相关资源数量
                resourceNumInfos = resourceViewService.getIdcResourceCountByCustomerId(customerId);
                //获取订单数
                ticketNum = idcHisTicketService.queryEndTicketByCustomerIdCount(customerId);
                //查询条件map
                Map queryMap = new HashedMap();
                queryMap.put("cusId", customerId);
                queryMap.put("rowNum", 8);//查询条数为rowNum-1
                //查询客户视图  table相关数据
                //占用机架
                rackInfos = idcRackService.queryUseredRackByCustomerId(queryMap);
                //占用Ip
                ipInfos = ipinfoService.queryUseredIPByCustomerId(queryMap);
                //占用端口
                portInfos = netPortService.queryUseredPortByCustomerId(queryMap);
                //装载客户业务端口流量峰值数据
//                portFlowMap = openCustomerPortFlowInfo(customerId.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("resourceNumInfos", resourceNumInfos);
        map.put("ticketNum", ticketNum);
        map.put("rackInfos", rackInfos);
        map.put("ipInfos", ipInfos);
        map.put("portInfos", portInfos);
        map.put("customerId", customerId);
        map.put("idcReCustomer",idcReCustomer);
//        map.put("portFlowMap",com.alibaba.fastjson.JSONObject.toJSON(portFlowMap));
        map.put("cusList", com.alibaba.fastjson.JSONObject.toJSON(result));
        return "/mainpage/main_customer_resource";
    }

    /*通过客户ID获取客户流量信息*/
    @RequestMapping("/resource/queryPortFlowInfoByCustomerId")
    @ResponseBody
    public Map<String,Object> queryPortFlowInfoByCustomerId(EasyUIPageBean page,String customerId){
        Map<String,Object> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
        String timeStr = sdf.format(new Date());
        Map<String,Object> mapQ = new HashMap<>();
        mapQ.put("customerId",customerId);
        mapQ.put("table","MV_NET_CAP_PORT_"+timeStr);
        /*获取业务端口的名称、ID*/
        List<Map<String,Object>> busiportList = netPortFlowService.queryBusinessPortNameByCustomerId(page,mapQ);
        List<String> portNameList = new ArrayList<>();
        List<String> busiportids = new ArrayList<>();
        for(Map param : busiportList){
            /*封装业务端口名称*/
            portNameList.add(param.get("PORTNAME").toString());
            busiportids.add(param.get("BUSIPORTID").toString());
        }
        mapQ.put("busiportids",busiportids);
        List<Map<String, Object>> list = netPortFlowService.queryCustomerMaxPortFlowByMap(mapQ);
        Map<String,Object> portFlowMap = new HashMap<>();
        for(String name : portNameList){
            List<String> outFlows = new ArrayList<>();
            List<String> inFlows = new ArrayList<>();
            List<String> timeStrs = new ArrayList<>();
            for(Map map : list){
                if (String.valueOf(map.get("PORTNAME")).equals(name)) {
                    outFlows.add(String.valueOf(map.get("OUTFLOW")));
                    inFlows.add(String.valueOf(map.get("INFLOW")));
                    timeStrs.add(String.valueOf(map.get("RECORDTIME")));
                }
            }
            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("outFlows",outFlows);
            paramsMap.put("inFlows",inFlows);
            paramsMap.put("timeStrs",timeStrs);
            portFlowMap.put(name, paramsMap);
        }
        resultMap.put("portNameList",portNameList);
        resultMap.put("portFlowMap",portFlowMap);
        resultMap.put("pageTotal",page.getTotalPage());
        return resultMap;
    }
}
