package com.idc.service;
import java.util.List;
import java.util.Map;

import system.data.page.EasyUIData;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import com.idc.model.IdcRackunit;



/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RACKUNIT:机位<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRackunitService extends SuperService<IdcRackunit, Long>{
    List<Map<String, Object>> queryListByRackID(int id);

    void online(int startu, int uheight, long rackid, long deviceid) throws Exception;

    void down(int rackid, int deviceid) throws Exception;

    List<Map<String, Object>> queryIdcRackunitInfoListPage(PageBean page, Object param);

    List<Map<String, Object>> queryIdcRackunitInfoList(IdcRackunit idcRackunit);

    /*修改机位状态 map中key:id-机位ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
    void updateRackunitStatusByIds(List<Map<String, Object>> list, Long rackId) throws Exception;

    /*解除客户与机位的绑定关系*/
    void unbindCustomerWithRackunit(Map<String, Object> map) throws Exception;

    int getRackunitNumByUheightAndRack(Integer rackId, Integer uheight, Integer uinstall);

    void updateStatusByRackId(Long rackId, Integer status) throws Exception;

    /*查看客户分配的机位*/
    List<Map<String, Object>> queryUseredRackunitinfoByCustomerIdPage(PageBean page, Object param);

}
