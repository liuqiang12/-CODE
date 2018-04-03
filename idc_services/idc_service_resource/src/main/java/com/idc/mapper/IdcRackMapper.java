package com.idc.mapper;

import com.idc.model.IdcRack;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>idc_rack:IDC机架<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRackMapper extends SuperMapper<IdcRack, Integer>{
    List<Map<String,Object>> queryAllIdcRackInfoList();

    List<Map<String,Object>> queryIdcRackInfoListPage(PageBean page);

    List<Map<String,Object>> getAllRackInfo(IdcRack idcRack);

    Map<String,Object> getMapModelById(int id);

    List<Map<String,Object>> getRackModel();

    List<Map<String,Object>> getInternetexport();

    List<Map<String,Object>> queryPdfRackInfoList(IdcRack idcRack);

    /**
     * 工单使用
     * @throws Exception
     */
    void foreachUpdateInfo(List<Map<String, Object>> resourceList) throws  Exception;
    /**
     * 工单使用 修改资源状态
     * @param resourceId
     * @param status
     * @throws Exception
     */
    void singleUpdateInfoById(@Param("resourceId") Long resourceId, @Param("status") Long status) throws  Exception;

    List<Map<String,Object>> queryOdfRackInfoList(IdcRack idcRack);

    List<Map<String,Object>> queryPdfRackInfoListPage(PageBean page);

    List<Map<String,Object>> queryOdfRackInfoListPage(PageBean page);

    List<Map<String,Object>> getAllOdfRackInfoList(IdcRack idcRack);

    List<Map<String,Object>> queryDistributionOdfRackInfoListPage(PageBean page);

    List<Map<String, Object>> getRackListByRackIds(PageBean page);

    List<IdcRack> queryListByRackIds(List<String> list);

    List<IdcRack> queryListByRoomIds(List<String> list);

    /*获取资源分配的pdf架列表*/
    List<IdcRack> distributionPdfRackList(PageBean page);

    /*修改机架状态 map中key:id-机架ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号,*/
    void updateRackStatusByRackIds(List<Map<String, Object>> list) throws Exception;

    /*统计数据中心机架数及使用率*/
    Map<String, Object> getCustomerRackNum(Long locationId);

    List<Map<String, Object>> upordownForDeviceRackList(PageBean page);

    /*获取客户所有占用机架*/
    List<Map<String, Object>> queryUseredRackByCustomerId(Map map);

    /*获取客户所有占用机架  page*/
    List<Map<String, Object>> queryUseredRackByCustomerIdPage(PageBean page);

    /*通过机房ID获取该机房所有模块*/
    List<Map<String, Object>> queryModuleByRoomId(Long roomId);

    /*删除机架  对机架进行软删除*/
    void updateRackToInvalidByIds(List<String> list) throws Exception;

    /*通过机架名称获取机架信息*/
    IdcRack queryRackInfoByName(@Param("name") String name);

    /*通过机架IDS获取机架*/
    List<IdcRack> queryRackListByIds(Map<String,Object> map);

    /*通过数据中心ID查询所有的客户柜*/
    List<IdcRack> queryRackListByLocationId(Map<String,Object> map);

}