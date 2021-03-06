package com.idc.mapper;

import com.idc.model.IdcDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_DEVICE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 26,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcDeviceMapper extends SuperMapper<IdcDevice, Long>{

    List<Map<String,Object>> getAllDeviceInfos(Map map);

    List<Map<String, Object>> queryListByObjectMap(Map<String, Object> map);

    List<Map<String, Object>> queryListPageMap(PageBean<T> page);

    List<Map<String, Object>> getRackModel();

    /*批量保存*/
    void foreachUpdateInfo(List<IdcDevice> idcDeviceList) throws Exception;

    /*修改状态是可用*/
    void singleUpdateInfoByDeviceId(@Param("resourceId") Long deviceId, @Param("status") Long status) throws Exception;

    /*获取资源分配的设备列表（机房）*/
    List<Map<String,Object>> queryDistributionDeviceList(Map<String, Object> map);

    /*获取资源分配的设备列表（机房） 分页*/
    List<Map<String,Object>> queryDistributionDeviceListPage(PageBean<T> page);

    List<IdcDevice> queryListByRackIds(List<String> list);

    /*可通过数据中心机房IDS查询对应设备*/
    List<Map<String, Object>> queryListByRoomIds(Map<String,Object> map);

    /*修改设备状态 map中key:id-设备ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号,*/
    void updateDeviceStatusByDeviceIds(List<Map<String, Object>> list) throws Exception;

    List<Map<String, Object>> queryDeviceList(PageBean<T> page);

    /*删除设备  对设备进行软删除*/
    void updateDeviceToInvalidByIds(List<String> list) throws Exception;

    /*通过设备ID获取各个设备是否上架的信息*/
    List<Map<String, Object>> getDeviceNumByIds(List<String> list);

    /*通过设备名称获取设备*/
    IdcDevice queryIdcDeviceInfoByName(@Param("name") String name);

    /*通过数据中心ID获取所有的设备*/
    List<IdcDevice> queryDeviceListByLocationId(Map<String, Object> map);

}