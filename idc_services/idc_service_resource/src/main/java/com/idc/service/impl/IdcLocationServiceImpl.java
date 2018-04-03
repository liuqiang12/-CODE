package com.idc.service.impl;

import com.idc.mapper.IdcLocationMapper;
import com.idc.model.IdcLocation;
import com.idc.service.IdcLocationService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>idc_location:IDC数据中心<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcLocationService")
public class IdcLocationServiceImpl extends SuperServiceImpl<IdcLocation, Integer> implements IdcLocationService{
    @Autowired
    private IdcLocationMapper idcLocationMapper;

    @Override
    public List<Map<String, Object>> getAllIcLocationInfo() {
        return idcLocationMapper.getAllIcLocationInfo();
    }

    @Override
    public void saveIdcLocation(IdcLocation location) {
        idcLocationMapper.saveIdcLocation(location);
    }

    @Override
    public int updateByPrimaryKeySelective(IdcLocation idcLocation) {
        return idcLocationMapper.updateByPrimaryKeySelective(idcLocation);
    }

    /*资源分配资源列表*/
    @Override
    public List<Map<String, Object>> queryDistributionResourceList(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcLocationMapper.queryDistributionResourceList(page);
    }

    /*资源分配资源列表   导出*/
    @Override
    public List<Map<String, Object>> queryAllDistributionResourceList() {
        return idcLocationMapper.queryAllDistributionResourceList();
    }

    /*IP占用列表*/
    @Override
    public List<Map<String, Object>> queryDistributionIpList(PageBean<T> page, Object param) {
        //先将数据保存到表idc_ip_report_temp中   在去读取
        idcLocationMapper.insertIpReportData();
        page.setParams(MapHelper.queryCondition(param));
        return idcLocationMapper.queryDistributionIpList(page);
    }

    /*IP占用列表  导出*/
    @Override
    public List<Map<String, Object>> queryAllDistributionIpList() {
        return idcLocationMapper.queryAllDistributionIpList();
    }

    /*资源回收列表*/
    @Override
    public List<Map<String, Object>> recycleResourceReport(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return idcLocationMapper.recycleResourceReport(page);
    }

    /*资源回收列表 导出*/
    @Override
    public List<Map<String, Object>> recycleAllResourceReport() {
        return idcLocationMapper.recycleAllResourceReport();
    }

    @Override
    public Map<String,Object> getIdcLocationResourceRatio(){
        return idcLocationMapper.getIdcLocationResourceRatio();
    }
}
