package com.idc.service.impl;

import com.idc.model.RemoteSysResourceEvent;
import com.idc.model.ip.ALLCTYPE;
import com.idc.service.*;
import com.idc.utils.ResourceEnum;
import com.idc.utils.ServiceApplyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/8/24.
 * 监听资源信息表:控制相应的数据
 * 资源远程调用:目前只是【针对于工单结束的情况.......】
 */
@Component("remoteSysResourceEventListener")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class RemoteSysResourceEventListener implements ApplicationListener {
    private Logger logger = LoggerFactory.getLogger(RemoteSysResourceEventListener.class);
    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private IdcRackunitService idcRackunitService;
    @Autowired
    private IdcIpinfoService ipinfoService;
    @Autowired
    private IdcDeviceService idcDeviceService;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private IdcMcbService idcMcbService;
    @Override
    public void onApplicationEvent(ApplicationEvent event){
        if(event instanceof RemoteSysResourceEvent){
            RemoteSysResourceEvent remoteSysResourceEvent = (RemoteSysResourceEvent)event;
            //此时需要修改相应的数据
            Map<String,Object> remoteMap = (Map<String,Object>)remoteSysResourceEvent.target;
            try {
                if(remoteMap == null || !remoteMap.containsKey("resourceCategory")){
                    throw new Exception("必须传递资源类型[resourceCategory]参数，如机架、机位、IP等");
                }

                /*资源接口修改*/
                String resourceCategory = String.valueOf(remoteMap.get("resourceCategory"));
                if(ServiceApplyEnum.机架.value().equals(resourceCategory)){
                    remoteUpdateRackAttributes(remoteMap);
                }else if(ServiceApplyEnum.U位.value().equals(resourceCategory)){
                    remoteUpdateRackUnitAttributes(remoteMap);
                }else if(ServiceApplyEnum.IP租用.value().equals(resourceCategory)){
                    remoteUpdateIpAttributes(remoteMap);
                }else if(ServiceApplyEnum.主机租赁.value().equals(resourceCategory)){
                    remoteUpdateServerAttributes(remoteMap);
                }else if(ServiceApplyEnum.端口带宽.value().equals(resourceCategory)){
                    remoteUpdatePortAttributes(remoteMap);
                }else if(ServiceApplyEnum.MCB.value().equals(resourceCategory)){
                    remoteUpdateMCBAttributes(remoteMap);
                }else if(ServiceApplyEnum.增值业务.value().equals(resourceCategory)){
                    System.out.println("不操作任何资源。。。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * mcb状态修改
     * @param remoteMap
     */
    public void remoteUpdateMCBAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[MCB]的状态," +
                "MCB ID是["+remoteMap.get("id")+"]," +
                "工单ID是["+remoteMap.get("ticketId")+"]"+
                "资源状态是["+remoteMap.get("status")+"]" +
                "客户ID是["+remoteMap.get("customerId")+"]" +
                "客户名称是["+remoteMap.get("customerName")+"]"+
                "PDF  ID["+remoteMap.get("pdfId")+"]");
        if( remoteMap.get("status")!=null && remoteMap.get("status").toString().equals(ResourceEnum.MCB空闲.value().toString()) )
        {
            logger.debug("判断：如果把资源信息改成空闲，那么资源就不需要有任何的客户信息，所以就需要把客户信息清空");
            if(remoteMap.containsKey("customerId")){remoteMap.remove("customerId");remoteMap.put("customerId",0);}
            if(remoteMap.containsKey("customerName")){remoteMap.remove("customerName");remoteMap.put("customerName","  ");}
            if(remoteMap.containsKey("ticketId")){remoteMap.remove("ticketId");remoteMap.put("ticketId",0);}
        }
        List<Map<String,Object>> mcbList = new ArrayList<>();
        try {
            mcbList.add(remoteMap);
            idcMcbService.updateMcbStatusByMcbIds(mcbList,null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[idcDeviceService.updateDeviceStatusByDeviceIds]是否出错");
        }

    }
    public void remoteUpdatePortAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[端口带宽]的状态," +
                "机架的ID是["+remoteMap.get("id")+"]," +
                "工单ID是["+remoteMap.get("ticketId")+"]"+
                "资源状态是["+remoteMap.get("status")+"]" +
                "客户ID是["+remoteMap.get("customerId")+"]" +
                "客户名称是["+remoteMap.get("customerName")+"]");
        if( remoteMap.get("status")!=null && remoteMap.get("status").toString().equals(ResourceEnum.端口带宽空闲.value().toString()) )
        {
            logger.debug("判断：如果把资源信息改成空闲，那么资源就不需要有任何的客户信息，所以就需要把客户信息清空");
            if(remoteMap.containsKey("customerId")){remoteMap.remove("customerId");remoteMap.put("customerId",0);}
            if(remoteMap.containsKey("customerName")){remoteMap.remove("customerName");remoteMap.put("customerName","  ");}
            if(remoteMap.containsKey("ticketId")){remoteMap.remove("ticketId");remoteMap.put("ticketId",0);}
        }
        List<Map<String,Object>> portList = new ArrayList<>();
        try {
            portList.add(remoteMap);
            //设备Id。。。。
            netPortService.updatePortStatusByPortIds(portList, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[idcDeviceService.updateDeviceStatusByDeviceIds]是否出错");
        }

    }
    public void remoteUpdateServerAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[主机]的状态," +
                "机架的ID是["+remoteMap.get("id")+"]," +
                "工单ID是["+remoteMap.get("ticketId")+"]"+
                "资源状态是["+remoteMap.get("status")+"]" +
                "客户ID是["+remoteMap.get("customerId")+"]" +
                "客户名称是["+remoteMap.get("customerName")+"]");
        if(remoteMap.get("status")!=null && remoteMap.get("status").toString().equals(ResourceEnum.主机空闲.value().toString()))
        {
            logger.debug("判断：如果把资源信息改成空闲，那么资源就不需要有任何的客户信息，所以就需要把客户信息清空");
            if(remoteMap.containsKey("customerId")){remoteMap.remove("customerId");remoteMap.put("customerId",0);}
            if(remoteMap.containsKey("customerName")){remoteMap.remove("customerName");remoteMap.put("customerName","  ");}
            if(remoteMap.containsKey("ticketId")){remoteMap.remove("ticketId");remoteMap.put("ticketId",0);}
        }

        List<Map<String,Object>> hostList = new ArrayList<>();
        try {
            hostList.add(remoteMap);
            idcDeviceService.updateDeviceStatusByDeviceIds(hostList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[idcDeviceService.updateDeviceStatusByDeviceIds]是否出错");
        }

    }

    public void remoteUpdateIpAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[IP]的状态," +
        "机架的ID是["+remoteMap.get("id")+"]," +
        "资源状态是["+remoteMap.get("status")+"]" +
        "工单ID是["+remoteMap.get("ticketId")+"]"+
        "客户ID是["+remoteMap.get("customerId")+"]"+
        "客户名称是["+remoteMap.get("customerName")+"]");

        ALLCTYPE allctype = null;//资源的状态
        Integer resource_status=999999;
        String customerId ="0";
        String customerName="  ";
        Long ticketId = 0L;
        String ipType="";

        resource_status = remoteMap.get("status") != null ? Integer.valueOf(String.valueOf(remoteMap.get("status"))) : 999999;
        ipType =remoteMap.get("ipType") !=null ? String.valueOf(remoteMap.get("ipType")) : "";
        customerId =remoteMap.get("customerId") != null ? String.valueOf(remoteMap.get("customerId")) : "0";
        customerName =remoteMap.get("customerName") != null ? String.valueOf(remoteMap.get("customerName")) : "  ";

        ticketId = remoteMap.get("ticketId") != null ? Long.valueOf(String.valueOf(remoteMap.get("ticketId"))) : 0L;
        if(resource_status == 1){
            allctype = ALLCTYPE.USED;//占用
        }else if(resource_status == 0){
            allctype = ALLCTYPE.FREE;//空闲
        }else if(resource_status == 2){
            allctype = ALLCTYPE.ALLCUSED;//分配占用
        }else if(resource_status == 3){
            allctype = ALLCTYPE.WAITRECY;//等待回收
        }

        if(remoteMap.get("status")!=null && remoteMap.get("status").toString().equals(ResourceEnum.IP空闲.value().toString())){
            customerId = "";
            customerName = "";
            ticketId = null;
        }

        try {
           Boolean isSubnet = Boolean.TRUE;
            String subnet = ipType;
           if(ipType.equals("ipaddress")){
               subnet = null;
           }
            Long releaseReourceByTicketId = null;
            if (remoteMap.get(ServiceApplyEnum.修改需要工单ID.value()) != null) {
                releaseReourceByTicketId = Long.valueOf(String.valueOf(remoteMap.get(ServiceApplyEnum.修改需要工单ID.value())));
            }
            ipinfoService.updateIpInfoStatus(
                    Long.valueOf(String.valueOf(remoteMap.get("id"))),
                    subnet,
                    allctype,
                    ticketId,
                    customerId,
                    customerName,
                    releaseReourceByTicketId);
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[ipinfoService.allc]是否出错");
         }
    }
    public void remoteUpdateRackUnitAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[机位]的状态," +
            "机位的ID是["+remoteMap.get("id")+"]," +
            "工单ID是["+remoteMap.get("ticketId")+"]" +
            "资源状态是["+remoteMap.get("status")+"]" +
            "客户ID是["+remoteMap.get("customerId")+"]" +
            "客户名称是["+remoteMap.get("customerName")+"]" +
            "机架rackId是["+remoteMap.get("rackId")+"]");
        List<Map<String,Object>> rackUnitList = new ArrayList<>();
        if(remoteMap.get("status") != null && remoteMap.get("status").toString().equals(ResourceEnum.U位空闲.value().toString()))
        {
            logger.debug("判断：如果把资源信息改成空闲，那么资源就不需要有任何的客户信息，所以就需要把客户信息清空");
            if(remoteMap.containsKey("customerId")){remoteMap.remove("customerId");remoteMap.put("customerId",0);}
            if(remoteMap.containsKey("customerName")){remoteMap.remove("customerName");remoteMap.put("customerName","  ");}
            if(remoteMap.containsKey("ticketId")){remoteMap.remove("ticketId");remoteMap.put("ticketId",0);}
        }
        rackUnitList.add(remoteMap);
        try {
            idcRackunitService.updateRackunitStatusByIds(rackUnitList,Long.valueOf(String.valueOf(remoteMap.get("rackId"))));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[idcRackunitService.updateRackStatusByRackIds]是否出错");
        }
    }


    public void remoteUpdateRackAttributes(Map<String,Object> remoteMap){
        System.out.println("开始调用修改系统资源中[机架]的状态," +
                "机架的ID是["+remoteMap.get("id")+"]," +
                "工单ID是["+remoteMap.get("ticketId")+"]" +
                "资源状态是["+remoteMap.get("status")+"]" +
                "客户ID是["+remoteMap.get("customerId")+"]" +
                "客户名称是["+remoteMap.get("customerName")+"]");
            List<Map<String,Object>> rackList = new ArrayList<>();
            if( remoteMap.get("status") != null && remoteMap.get("status").toString().equals(ResourceEnum.机架空闲.value().toString()) )
            {
                logger.debug("判断：如果把资源信息改成空闲，那么资源就不需要有任何的客户信息，所以就需要把客户信息清空");
                if(remoteMap.containsKey("customerId")){remoteMap.remove("customerId");remoteMap.put("customerId",0);}
                if(remoteMap.containsKey("customerName")){remoteMap.remove("customerName");;remoteMap.put("customerName","   ");}
                if(remoteMap.containsKey("ticketId")){remoteMap.remove("ticketId");remoteMap.put("ticketId",0);}
            }

            rackList.add(remoteMap);
            try {
                idcRackService.updateRackStatusByRackIds(rackList);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("兄弟报错了,请注意追溯一下参数或者远程调用的接口[idcRackService.updateRackStatusByRackIds]是否出错");
            }
        }
}
