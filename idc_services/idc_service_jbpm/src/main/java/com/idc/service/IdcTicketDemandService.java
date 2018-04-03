package com.idc.service;

import com.idc.model.IdcTicketDemand;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_TICKET_DEMAND:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 11,2018 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcTicketDemandService extends SuperService<IdcTicketDemand, Long>{
    /**
     *   Special code can be written here
     */

    void updateIdcTicketDemandByTicketId(Map<String,Object> idcTicketDemandMap);

    IdcTicketDemand queryAllByTicketInstId(String ticketInstId);

    List<Map<String,Object>> queryHisDemand(String prodInstId);

}
