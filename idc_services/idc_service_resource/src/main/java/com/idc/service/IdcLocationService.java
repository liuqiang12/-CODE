package com.idc.service;

import com.idc.model.IdcLocation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>idc_location:IDC机楼<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLocationService extends SuperService<IdcLocation, Integer> {

    List<Map<String, Object>> getAllIcLocationInfo();

    void saveIdcLocation(IdcLocation location);

    int updateByPrimaryKeySelective(IdcLocation idcLocation);

    /*资源分配资源列表*/
    List<Map<String, Object>> queryDistributionResourceList(PageBean<T> page, Object param);

    /*资源分配资源列表   导出*/
    List<Map<String, Object>> queryAllDistributionResourceList();

    /*IP占用列表*/
    List<Map<String, Object>> queryDistributionIpList(PageBean<T> page, Object param);

    /*IP占用列表  导出*/
    List<Map<String, Object>> queryAllDistributionIpList();

    /*资源回收列表*/
    List<Map<String, Object>> recycleResourceReport(PageBean<T> page, Object param);

    /*资源回收列表 导出*/
    List<Map<String, Object>> recycleAllResourceReport();

    /*获取机架、核心端口、ip、出口带宽利用率   不区分数据中心*/
    Map<String,Object> getIdcLocationResourceRatio();
}
