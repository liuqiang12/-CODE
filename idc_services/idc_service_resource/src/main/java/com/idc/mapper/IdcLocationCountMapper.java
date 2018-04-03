package com.idc.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcLocationCount;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_LOCATION_COUNT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 08,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLocationCountMapper extends SuperMapper<IdcLocationCount, Long>{

    long getRoomCount(Integer localId);

    long getBuildCount(Integer localId);

    long getOdfCount(Integer localId);

    long getPdfCount(Integer localId);

    List<Map<String,Object>> getRoomFreeUsgeTOP();

    //查询所有的政企业务的好评
    List<Map<String, Object>>  getGoodEvaluate();

    //查询待处理工单
    List<Map<String, Object>> getUntreatedTicket();

    //查询有域名的用户
    Integer getDNS();
    //查询没有域名的用户
    Integer getNotDNS();

    //查询最近6个月每个月新增的工单
    List<Map<String, Object>> onlineTicketList();

    Map<String, Object> getIdcResourceCountByCustomerId(@Param("cusId") Long cusId);

    /*将各个机房统计值更新到对应机房*/
    void reloadRoomStatistics() throws Exception;

}

 