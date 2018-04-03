package com.idc.action;

import com.idc.bo.RoomPE;
import com.idc.model.*;
import com.idc.model.count.TrendBean;
import com.idc.service.*;
import com.idc.utils.FlowUtil;
import oracle.sql.TIMESTAMP;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by mylove on 2017/8/8.
 */
@Controller
public class ResourceIndexView {
    @Autowired
    private ResourceViewService resourceViewService;
    @Autowired
    private IdcLocationService idcLocationService;
    @Autowired
    private NetPortFlowService netPortFlowService;

    @Autowired
    private NetAlarminfoService netAlarminfoService;
    @Autowired
    private PowerEnvService powerEnvService;
    @Autowired
    private BusPortService busPortService;

    @RequestMapping("/main_manager")
    @ResponseBody
    public Map<String, Object> getResourceUsage(ModelMap map) {
        List<IdcLocationCount> idcLocationCounts = resourceViewService.queryList();
//        map.put("data",IdcLocationCount)
        return map;
    }

    /***
     * 获取待处理工单
     *
     * @param page
     * @return
     */
    @RequestMapping("/main_manager/getwaitticket")
    @ResponseBody
    public EasyUIData getWaitTicket(EasyUIPageBean page) {
        List<Map<String, Object>> ticketByWait = resourceViewService.getTicketByWait();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map ;
        for (Map<String, Object> objectMap : ticketByWait) {
            map =new HashMap<>();
            map.put("ordername", objectMap.get("BUSNAME"));
            TIMESTAMP create_time = (TIMESTAMP) objectMap.get("CREATE_TIME");
            map.put("createtime", create_time.toString());
//            map.put("createtime", DateFormatUtils.format(create_time,"yyyy-MM-dd HH:mm:ss"));
            result.add(map);
        }
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setTotal(ticketByWait.size());
        easyUIData.setRows(result);
        return easyUIData;
    }

    @RequestMapping("/main_manager/getidccount")
    @ResponseBody
    public List<Map<String, Object>> getidccount() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<IdcLocation> idcLocations = idcLocationService.queryList();
        for (IdcLocation idcLocation : idcLocations) {
            Map map = new HashMap<>();
            IdcLocationCount localCount = resourceViewService.getModelById(Long.parseLong(idcLocation.getId().toString()));
            if (localCount != null) {
                map.put("idccount", localCount);
                map.put("idcinfo", idcLocation);
                list.add(map);
            }
        }
        return list;
    }

    /*获取机架、核心端口、ip、出口带宽利用率   不区分数据中心*/
    @RequestMapping("/main_manager/getIdcLocationResourceRatio")
    @ResponseBody
    public Map<String,Object> getIdcLocationResourceRatio(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap = idcLocationService.getIdcLocationResourceRatio();
        List<NetBusiPortFlow> maps = busPortService.queryListPage("出口", null);
        List<IdcLocation> locationList = idcLocationService.queryList();
        Long bandWidthCount = 0L;
        if(locationList!=null&&locationList.size()>0){
            for(IdcLocation location:locationList){
                bandWidthCount += location.getTotalbandwidth();
            }
            resultMap.put("BANDWIDTHCOUNT",bandWidthCount);
        }
        if(maps!=null&&maps.size()>0){
            for(NetBusiPortFlow portFlow:maps){
                if("东区+西区+西云=7000G总出口(物理端口)".equals(portFlow.getPortname())){
                    resultMap.put("USEDBANDWIDTHNUM",(float)portFlow.getOutflowMbps());
                    break;
                }
            }
        }
        return resultMap;
    }

    /***
     * 好评
     *
     * @return
     */
    @RequestMapping("/main_manager/getgoodrate")
    @ResponseBody
    public Map<String, Object> getGoodRate() {
        Map map = new HashMap<>();
        Map<String, Long> goodRateBySelfBus = resourceViewService.getGoodRate(0);
        map.put("自有业务", goodRateBySelfBus);
        Map<String, Long> goodRate = resourceViewService.getGoodRate(1);
        map.put("政企业务", goodRate);
        //测试数据
//        goodRateBySelfBus = new HashMap<>();
//        goodRateBySelfBus.put("预受理", 40L);
//        goodRateBySelfBus.put("业务开通", 82L);
//        goodRateBySelfBus.put("业务变更", 71L);
//        goodRateBySelfBus.put("变更开通", 82L);
//        map.put("自有业务", goodRateBySelfBus);
//        goodRate = new HashMap<>();
//        goodRate.put("预受理", 50L);
//        goodRate.put("业务开通", 80L);
//        goodRate.put("业务变更", 24L);
//        goodRate.put("变更开通", 77L);
//        map.put("政企业务", goodRate);
        return map;
    }

    @RequestMapping("/main_manager/getuseraddtrend")
    @ResponseBody
    public List<Map<String, Object>> getUserAddTrend() {
        /*List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Map<String, Object>> map = new TreeMap<>();
        if (userAddTread != null)
            for (TrendBean trendBean : userAddTread) {
                String datestr = DateFormatUtils.format(trendBean.getTime(), "yyyy-MM");
                Map<String, Object> objectMap = map.get(datestr);
                if (objectMap == null) {
                    objectMap = new HashMap<>();
                }
                String type = trendBean.getType();
                objectMap.put(type, trendBean.getValue());
                map.put(datestr, objectMap);
            }
        for (String s : map.keySet()) {
            Map<String, Object> objectMap = map.get(s);
            objectMap.put("month", s);
            result.add(objectMap);
        }*/

        List<Map<String, Object>> result = resourceViewService.getUserAddTread();

        return result;
    }
    @RequestMapping("/main_manager/havdomainuser")
    @ResponseBody
    public Map<String, Long> havDomainUser() {
        Map<String, Long> havDomainUser = resourceViewService.getHavDomainUser();
        return havDomainUser;
    }


    @RequestMapping("/main_manager/getflowtop")
    @ResponseBody
    public List<Map<String,Object>> getPortFlowTopN() {
        //List<NetPortFlow> list = netPortFlowService.getOutFlowTopN();
        List<Map<String, Object>> portFlowTopN = new ArrayList<>();//resourceViewService.getPortFlowTopN();
        Map<String,Object> map;
        List<NetPortFlow> outFlowTopN = netPortFlowService.getOutFlowTopN();
        for (NetPortFlow netPortFlow : outFlowTopN) {
            map = new HashMap<>();
            map.put("portname",netPortFlow.getPortname());
            map.put("outflow", FlowUtil.bytesToMbps(netPortFlow.getOutflow()));
            portFlowTopN.add(map);
        }
        return portFlowTopN;
    }

    @RequestMapping("/main_manager/getalarm")
    @ResponseBody
    public EasyUIData getAlarm(EasyUIPageBean page) {
        //List<NetPortFlow> list = netPortFlowService.getOutFlowTopN();
        page = new EasyUIPageBean(1,20);
//        List<Map<String,Object>> result = new ArrayList<>();
//        Map<String,Object> map ;
        List<NetAlarmInfoVo> netAlarmInfoVos = netAlarminfoService.queryListPageByMap(page, null, null, null, true, null, null, null);
//        for (NetAlarmInfoVo netAlarmInfoVo : netAlarmInfoVos) {
//            map = new HashMap<>();
//            map.put("ordername",netAlarmInfoVo.getAlarminfo());
//            map.put("ordertype",netAlarmInfoVo.getObjName());
//            map.put("createtime",netAlarmInfoVo.getAlarmsendtimeStr());
//            result.add(map);
//        }
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setRows(netAlarmInfoVos);
        easyUIData.setTotal(page.getTotalRecord());
        return easyUIData;
    }
    @RequestMapping("/main_manager/getroomfreeusge")
    @ResponseBody
    public List<Map<String,Object>> getRoomFreeUsge() {
        //List<NetPortFlow> list = netPortFlowService.getOutFlowTopN();
        return resourceViewService.getRoomFreeUsgeTOP();
    }
    @RequestMapping("/main_manager/pueTop")
    @ResponseBody
    public Map<String,Object> pueTop() {
        List<RoomPE> peTopRoom = powerEnvService.getPETopRoom(10L);
        List<Double> datas = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (RoomPE roomPE : peTopRoom) {
            labels.add(roomPE.getRoomName());
            datas.add(roomPE.getPue());
        }
        Map<String,Object> map =new HashMap<>();
        map.put("sitenames",labels);
        map.put("datas",datas);
        return map;
    }
}
