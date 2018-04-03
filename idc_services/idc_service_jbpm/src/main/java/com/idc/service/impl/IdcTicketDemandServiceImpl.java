package com.idc.service.impl;

import com.idc.mapper.IdcTicketDemandMapper;
import com.idc.model.IdcTicketDemand;
import com.idc.service.IdcTicketDemandService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_TICKET_DEMAND:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 11,2018 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcTicketDemandService")
public class IdcTicketDemandServiceImpl extends SuperServiceImpl<IdcTicketDemand, Long> implements IdcTicketDemandService {
    @Autowired
    private IdcTicketDemandMapper idcTicketDemandMapper;
    /**
     *   Special code can be written here
     */
    @Override
    public void updateIdcTicketDemandByTicketId(Map<String,Object> idcTicketDemandMap){
        String idName = String.valueOf(idcTicketDemandMap.get("idName"));
        String idValue = String.valueOf(idcTicketDemandMap.get("idValue"));
        String ticketInstId = String.valueOf(idcTicketDemandMap.get("ticketInstId"));
        String prodInstId = String.valueOf(idcTicketDemandMap.get("prodInstId"));

        //组装对象
        IdcTicketDemand idcTicketDemand = assembleObject(idName, idValue);
        idcTicketDemand.setTicketInstId(Long.valueOf(ticketInstId));
        idcTicketDemand.setProdInstId(Long.valueOf(prodInstId));
        idcTicketDemand.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //保存对象
        idcTicketDemandMapper.updateOrMerge(idcTicketDemand);
    }

    @Override
    public IdcTicketDemand queryAllByTicketInstId(String ticketInstId) {
        IdcTicketDemand idcTicketDemand = idcTicketDemandMapper.queryAllByTicketInstId(ticketInstId);
        return idcTicketDemand;
    }

    @Override
    public List<Map<String, Object>> queryHisDemand(String prodInstId) {

        return idcTicketDemandMapper.queryHisDemand(prodInstId);
    }

    public IdcTicketDemand assembleObject(String idName,String idValue){
        IdcTicketDemand idcTicketDemandJSON=new IdcTicketDemand();
        if(idName != null && idName.equalsIgnoreCase("rack_Spec")){
            idcTicketDemandJSON.setRack_Spec(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("rack_Num")){
            idcTicketDemandJSON.setRack_Num(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("rack_supplyType")){
            idcTicketDemandJSON.setRack_supplyType(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("rack_desc")){
            idcTicketDemandJSON.setRack_desc(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("port_portMode")){
            idcTicketDemandJSON.setPort_portMode(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("port_bandwidth")){
            idcTicketDemandJSON.setPort_bandwidth(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("port_num")){
            idcTicketDemandJSON.setPort_num(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("port_desc")){
            idcTicketDemandJSON.setPort_desc(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("ip_portMode")){
            idcTicketDemandJSON.setIp_portMode(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("ip_num")){
            idcTicketDemandJSON.setIp_num(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("ip_desc")){
            idcTicketDemandJSON.setIp_desc(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("server_typeMode")){
            idcTicketDemandJSON.setServer_typeMode(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("server_specNumber")){
            idcTicketDemandJSON.setServer_specNumber(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("server_num")){
            idcTicketDemandJSON.setServer_num(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("server_desc")){
            idcTicketDemandJSON.setServer_desc(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("newly_name")){
            idcTicketDemandJSON.setNewly_name(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("newly_category")){
            idcTicketDemandJSON.setNewly_category(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("newly_desc")){
            idcTicketDemandJSON.setNewly_desc(idValue);
        }
        if(idName != null && idName.equalsIgnoreCase("rack_Other")){
            idcTicketDemandJSON.setOther_Msg(idValue);
        }
        return idcTicketDemandJSON;
    }
}
