package com.idc.service;

import com.idc.model.IdcRack;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>idc_rack:IDC机架<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 23,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRackService extends SuperService<IdcRack, Integer>{

    List<Map<String,Object>> queryIdcRackInfoListPage(PageBean page, Object param);

    List<Map<String,Object>> queryAllIdcRackInfoList();

    List<Map<String,Object>> getAllRackInfo(IdcRack idcRack);

    Map<String,Object> getMapModelById(int id);

    /***
     * 获取机架型号
     * @return
     */
    List<Map<String, Object>> getRackModel();

    List<Map<String, Object>> getInternetexport();
    //获取PDF架
    List<Map<String, Object>> queryPdfRackInfoList(IdcRack idcRack);
    List<Map<String, Object>> queryPdfRackInfoListPage(PageBean page, Object param);
    //获取ODF架
    List<Map<String, Object>> queryOdfRackInfoList(IdcRack idcRack);
    List<Map<String, Object>> queryOdfRackInfoListPage(PageBean page, Object param);
    //获取所有的ODF架   用于导出
    List<Map<String, Object>> getAllOdfRackInfoList(IdcRack idcRack);
    //获取资源分配的ODF架
    List<Map<String, Object>> queryDistributionOdfRackInfoListPage(PageBean page, Object param);
    /* 绑定端口数据 */
    void deviceBindNetPort(Long portIdA, String portIds, Long deviceId, Integer roomId, String rackIds) throws Exception;

    /*绑定端子数据*/
    void bindRackAndConnector(String ids, Integer roomId, String rackIds, Integer odfRackId, Long portIdA) throws Exception;

    //根据机架ID集合获取机架
    List<Map<String, Object>> getRackListByRackIds(PageBean page, Object param);

    /*获取资源分配的pdf架列表*/
    List<IdcRack> distributionPdfRackList(PageBean page, Object param);

    //导入Excel
    void importIdcRackByExcel(List<List<Object>> list, String type) throws Exception;

    void insertOrUpdate(IdcRack idcRack, Integer rackId, Integer mcbNum) throws Exception;

    /*修改机架状态 map中key:id-机架ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号,*/
    void updateRackStatusByRackIds(List<Map<String, Object>> list) throws Exception;

    /*统计数据中心客户架总数及使用率*/
    Map<String, Object> getCustomerRackNum(Long locationId);

    /*获取设备上下架的机架*/
    List<Map<String, Object>> upordownForDeviceRackList(PageBean page, Object param);

    /*自动维护机位信息*/
    void autoVindicateRackunit() throws Exception;

    /*获取客户所有占用机架*/
    List<Map<String, Object>> queryUseredRackByCustomerId(Map map);

    /*获取客户所有占用机架  page*/
    List<Map<String, Object>> queryUseredRackByCustomerIdPage(PageBean page, Object param);

    /*通过机房ID获取该机房所有模块*/
    List<Map<String, Object>> queryModuleByRoomId(Long roomId);

    /*删除机架  对机架进行软删除*/
    void updateRackToInvalidByIds(List<String> list) throws Exception;

    /*通过机架名称获取机架信息*/
    IdcRack queryRackInfoByName(String name);

    /*通过机架IDS获取机架*/
    List<IdcRack> queryRackListByIds(Map<String,Object> map);

    /*通过数据中心ID查询所有的客户柜*/
    List<IdcRack> queryRackListByLocationId(Map<String,Object> map);
}
