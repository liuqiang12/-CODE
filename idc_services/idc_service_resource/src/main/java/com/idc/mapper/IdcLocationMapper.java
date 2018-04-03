package com.idc.mapper;

import com.idc.model.IdcLocation;
import com.idc.model.IdcLocationCount;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>idc_location:IDC数据中心<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLocationMapper extends SuperMapper<IdcLocation, Integer> {

    List<Map<String,Object>> getAllIcLocationInfo();

    void saveIdcLocation(IdcLocation location);

    int updateByPrimaryKeySelective(IdcLocation idcLocation);

    IdcLocationCount getLocalCountByLocationId(Integer locationId);

    /*统计数据中心带宽总数及使用率*/
    Map<String, Object> getTotalBandWidthNum(Long locationId);
    //接口使用   刘强
    List<Map<String,Object>> loadLocations();
    List<Map<String,Object>> loadFrameByHouseId(Long aid);
    List<Map<String,Object>> loadFrameByHouseIdAndTicketId(@Param("aid") Long aid, @Param("ticketInstId") Long ticketInstId);

    List<Map<String,Object>> loadUserInfos(@Param("ticketInstId")Long ticketInstId);
    List<Map<String,Object>> loadUserInfosByTicketId(@Param("ticketInstId")Long ticketInstId);

    List<Map<String,Object>> loadServiceInfos(@Param("aid") String aid,@Param("ticketInstId")Long ticketInstId);
    List<Map<String,Object>> loadIpTrans(@Param("aid") Long aid,@Param("ticketInstId")Long ticketInstId);

    List<Map<String,Object>> loadFrameInfoLimitFirst(@Param("aid")Long aid,@Param("ticketInstId")Long ticketInstId);

    /*资源分配资源列表*/
    List<Map<String, Object>> queryDistributionResourceList(PageBean<T> page);

    /*资源分配资源列表   导出*/
    List<Map<String, Object>> queryAllDistributionResourceList();

    /*Ip占用列表*/
    List<Map<String, Object>> queryDistributionIpList(PageBean<T> page);

    /*IP占用列表  导出*/
    List<Map<String, Object>> queryAllDistributionIpList();

    //先将数据保存到表idc_ip_report_temp中   在去读取
    void insertIpReportData();

    /*资源回收列表*/
    List<Map<String, Object>> recycleResourceReport(PageBean<T> page);

    /*资源回收列表 导出*/
    List<Map<String, Object>> recycleAllResourceReport();
    List<Map<String,Object>> loadAllFrameInfo();

    /*获取机架、核心端口、ip、出口带宽利用率   不区分数据中心*/
    Map<String,Object> getIdcLocationResourceRatio();
}