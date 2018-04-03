package com.idc.mapper;

import com.idc.model.IdcRackunit;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RACKUNIT:机位<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRackunitMapper extends SuperMapper<IdcRackunit, Long> {
    /**
     * Special code can be written here
     */
    public List<Map<String, Object>> queryListByRackID(int id);

    /***
     * 开始结束
     * @param startu
     * @param
     * @param deviceid
     */
    void online(@Param("startu")int startu, @Param("endu")int endu, @Param("rackid")long rackid, @Param("deviceid")long deviceid);

    void down( @Param("rackid")int rackid, @Param("deviceid")int deviceid);

    List<Map<String, Object>> queryIdcRackunitInfoListPage(PageBean page);

    List<Map<String, Object>> queryIdcRackunitInfoList(IdcRackunit idcRackunit);

    void singleUpdateInfoById(Long resourcdID,Long status);

    /*修改机位状态 map中key:id-机位ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
    void updateRackunitStatusByIds(List<Map<String, Object>> list) throws Exception;

    /*解除客户与机位的绑定关系*/
    void unbindCustomerWithRackunit(Map<String, Object> map) throws Exception;

    int getRackunitNumByUheightAndRack(@Param("rackId") Integer rackId, @Param("uheight") Integer uheight, @Param("uinstall") Integer uinstall);

    /*自动维护机位信息*/
    void autoVindicateRackunit();

    /*修改机位状态  并清空客户和工单信息*/
    void updateStatusByRackId(@Param("rackId") Long rackId, @Param("status") Integer status);

    /*查看客户分配的机位*/
    List<Map<String, Object>> queryUseredRackunitinfoByCustomerIdPage(PageBean page);

    Map<String, Object> queryUnitTotalAndFreeUnitByRackId(Long rackId);

    /*修改整个机架机位的状态，并记录客户、工单信息*/
    void updateStatusByParamsMap(List<Map<String, Object>> list) throws Exception;

}

 