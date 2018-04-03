package com.idc.action.netport;

import com.idc.model.ExecuteResult;
import com.idc.model.NetPort;
import com.idc.service.IdcRackService;
import com.idc.service.NetPortService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.tree.TreeNode;

import java.util.*;

/**
 * Created by Administrator on 2017/5/31.
 */
@Controller
@RequestMapping("/netport")
public class NetPortAction extends BaseController {
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private IdcRackService idcRackService;


    @RequestMapping("/selectPortTree")
    public String selectPortTree(ModelMap map,Long deviceId,Integer roomId,Integer rackId){
        return "netport/selectPortTree";
    }

    @RequestMapping("/netportList.do")
    public String getNetportList(ModelMap map,Long deviceId,Integer roomId,Integer rackId){
        map.addAttribute("deviceId",deviceId);
        map.addAttribute("roomId",roomId);
        map.addAttribute("rackId",rackId);
        // List<Map<String, Object>> list = getNetPortListByRackId(rackId);
        // map.addAttribute("netPorts", list);
        return "netport/netportList";
    }
    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String name, Long deviceId,String status) {
        EasyUIData easyUIData = new EasyUIData();
        NetPort netPort = new NetPort();
        netPort.setPortname(name);
        netPort.setDeviceid(deviceId);
        netPort.setStatus(status==null?null:Long.valueOf(status));
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(page==null||page.getPage()<0){//所有
            list = netPortService.queryListByObjectMap(netPort);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
            list = netPortService.queryListPageMap(page, netPort);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    @RequestMapping("/netportDetails.do")
    public String getNetportDetails(Long portid,ModelMap map,String buttonType){
        logger.debug("==================根据主键加载设备信息================");
        NetPort netPort = null;
        try {
            netPort = netPortService.getModelById(portid);
        }catch (Exception e){
            e.printStackTrace();
            netPort = new NetPort();
        }
        //添加日志
        map.addAttribute("buttonType",buttonType);
        map.addAttribute("netPort", netPort);
        map.addAttribute("portid",portid);
        return "netport/info";
    }
    //查询设备列表
    @RequestMapping("/getNetDeviceModel.do")
    @ResponseBody
    public List<Map<String, Object>> getNetDeviceModel(){
        List<Map<String, Object>> result = netPortService.getNetDeviceModel();
        return result;
    }

    /**
     * 绑定关联关系device--idcLink
     * @return
     */
    @RequestMapping("/bindConnectionToIdcLink.do")
    @ResponseBody
    public ExecuteResult bindConnectionToIdcLink(Long portIdA, String portIds, Long deviceId, Integer roomId, String rackIds) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            idcRackService.deviceBindNetPort(portIdA, portIds, deviceId, roomId, rackIds);
            executeResult.setState(true);
            executeResult.setMsg("绑定成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("绑定失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    /**
     * 根据rack_id获取端口列表
     *
     * @param rackIds
     * @return
     */
    @RequestMapping("/getNetPortListByRackId")
    @ResponseBody
    public List<Map<String, Object>> getNetPortListByRackId(String rackIds) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (rackIds != null && !"".equals(rackIds)) {
            list = netPortService.getNetPortListByRackId(rackIds);
        }
        return list;
    }
    @RequestMapping("/distributionPortList.do")
    @ResponseBody
    public List<Map<String, Object>> distributionPortList(String name, Long deviceId) {
        NetPort netPort = new NetPort();
        netPort.setPortname(name);
        netPort.setDeviceid(deviceId);
        netPort.setStatus(40L);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = netPortService.queryListByObjectMap(netPort);
        return list;
    }

    //获取端口列表
    @RequestMapping("/getNetportsByRoomId")
    @ResponseBody
    public List<Map<String, Object>> getNetportsByRoomId(String roomId, String name) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> mapQ = new HashMap<>();
        mapQ.put("roomId",roomId);
        mapQ.put("name",name);
        list = netPortService.getNetportsByRoomId(mapQ);
        return list;
    }

    //准备分配端口
    @RequestMapping("/distributionNetPort")
    public String distributionNetPort(String roomIds, String rackIds,String locationId,String status, ModelMap map) {
        roomIds = "";//先不做房限制
        map.addAttribute("roomIds", roomIds);
        map.addAttribute("rackIds", rackIds);
        map.addAttribute("locationId", locationId);
        map.addAttribute("status", status);
        return "netport/distributionNetPortList";
    }

    /**
     * 获取资源数
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree.do")
    public List<TreeNode> getTree(String roomIds,String locationId) {
        List<TreeNode> tree = netPortService.getTree(roomIds,locationId);
        return tree;
    }

    /**
     * 根据端口ID更新端口状态
     *
     * @param portIds
     * @param status
     */
    @RequestMapping("/updatePortStatus")
    public ExecuteResult updatePortStatusByPortIds(String portIds, Integer status) {
        ExecuteResult executeResult = new ExecuteResult();
        if (portIds != null && !"".equals(portIds) && status != null) {
            try {
                netPortService.updatePortStatusByPortIds(null, null);
                executeResult.setState(true);
                executeResult.setMsg("修改端口状态成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("修改端口状态失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("参数错误：portIds=" + portIds + ",status=" + status);
        }
        return executeResult;
    }

    /*通过端口ID获取端口列表*/
    @RequestMapping("/preQueryBindedPortList")
    public String preQueryBindedPortList(String portIds, ModelMap map) {
        map.put("portIds", portIds);
        return "/netport/bindedNetPortList";
    }

    /*通过端口ID获取端口列表*/
    @RequestMapping("/queryBindedPortList")
    @ResponseBody
    public EasyUIData queryBindedPortList(EasyUIPageBean page, String portIds, String name) {
        EasyUIData easyUIData = new EasyUIData();
        if (page != null || page.getPage() > 0) {
            Map<String, Object> mapQ = new HashedMap();
            mapQ.put("name", name);
            if (portIds != null && !"".equals(portIds)) {
                List<String> portIdList = Arrays.asList(portIds.split(","));
                mapQ.put("portIds", portIdList);
            }
            List<NetPort> list = netPortService.queryBindedPortList(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

}
