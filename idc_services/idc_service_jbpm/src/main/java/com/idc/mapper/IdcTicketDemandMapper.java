package com.idc.mapper;

import com.idc.model.IdcTicketDemand;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_TICKET_DEMAND:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 11,2018 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcTicketDemandMapper extends SuperMapper<IdcTicketDemand, Long>{
    /**
     *   Special code can be written here
     */
    void updateOrMerge(IdcTicketDemand idcTicketObject);

    IdcTicketDemand queryAllByTicketInstId(String ticketInstId);

    List<Map<String, Object>> queryHisDemand(String ticketInstId);



}

