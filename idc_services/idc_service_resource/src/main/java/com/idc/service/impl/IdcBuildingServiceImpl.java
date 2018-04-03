package com.idc.service.impl;


import com.idc.mapper.IdcBuildingMapper;
import com.idc.model.IdcBuilding;
import com.idc.model.IdcBuildingVo;
import com.idc.service.IdcBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>idc_building:IDC机楼<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcBuildingService")
public class IdcBuildingServiceImpl extends SuperServiceImpl<IdcBuilding, Integer> implements IdcBuildingService {

    @Autowired
    private  IdcBuildingMapper idcBuildingMapper;

    @Override
    public List<Map<String, Object>> queryBuildingInfoListPage(PageBean page, Object param) {
        //这里讲查询条件进行处理
        page.setParams(MapHelper.queryCondition(param));
        return idcBuildingMapper.queryBuildingInfoListPage(page);
    }

    @Override
    public List<Map<String, Object>> queryAllBuildingInfoList() {
        return idcBuildingMapper.queryAllBuildingInfoList();
    }

    @Override
    public int updateByPrimaryKeySelective(IdcBuilding record) {
        return idcBuildingMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void saveBuilding(IdcBuilding idcBuilding) {
        idcBuildingMapper.saveBuilding(idcBuilding);
    }

    @Override
    public List<Map<String, Object>> getAllIdcBuildingInfo() {
        return idcBuildingMapper.getAllIdcBuildingInfo();
    }

    @Override
    public Map<String, Object> getMapModelById(int id) {
        return idcBuildingMapper.getMapModelById(id);
    }

    @Override
    public void insertBuildingList(List<IdcBuildingVo> list) {
        idcBuildingMapper.insertBuildingList(list);
    }

    /*资源报表----获取各个机楼中机架的各种统计数据*/
    @Override
    public List<Map<String, Object>> queryResourceReportInfo(Map<String, Object> map) {
        return idcBuildingMapper.queryResourceReportInfo(map);
    }

    @Override
    public List<Map<BigDecimal, BigDecimal>> getRoomByIds(List<IdcBuilding> idcBuildings) {
        return idcBuildingMapper.getRoomByIds(idcBuildings);
    }

    //通过数据中心IDs获取所有机楼
    @Override
    public List<IdcBuilding> queryListByLocationIdList(List<String> list) {
        return idcBuildingMapper.queryListByLocationIdList(list);
    }

    /*根据机楼ID获取各个机楼的机房数量*/
    @Override
    public List<Map<String, Object>> getRoomNumByIds(List<String> list) {
        return idcBuildingMapper.getRoomNumByIds(list);
    }

    /*删除机架  对机架进行软删除*/
    @Override
    public void updateBuildingToInvalidByIds(List<String> list) throws Exception {
        idcBuildingMapper.updateBuildingToInvalidByIds(list);
    }

    /*查询满足条件的机楼信息   包括已删除的数据*/
    @Override
    public IdcBuilding getBuildingModelByMap(Map<String, Object> map) {
        return idcBuildingMapper.getBuildingModelByMap(map);
    }
}
