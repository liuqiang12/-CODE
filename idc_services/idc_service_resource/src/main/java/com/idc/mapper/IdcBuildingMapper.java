package com.idc.mapper;

import com.idc.model.IdcBuilding;
import com.idc.model.IdcBuildingVo;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>idc_building:IDC机楼<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcBuildingMapper  extends SuperMapper<IdcBuilding, Integer> {

    public List<Map<String,Object>> queryBuildingInfoListPage(PageBean page);

    public List<Map<String,Object>> queryAllBuildingInfoList();

    public  int updateByPrimaryKeySelective(IdcBuilding record);

    void saveBuilding(IdcBuilding idcBuilding);

    List<Map<String,Object>> getAllIdcBuildingInfo();

    Map<String,Object> getMapModelById(int id);

    void insertBuildingList(List<IdcBuildingVo> list);

    /*资源报表----获取各个机楼中机架的各种统计数据*/
    List<Map<String, Object>> queryResourceReportInfo(Map<String, Object> map);

    List<Map<BigDecimal,BigDecimal>> getRoomByIds(List<IdcBuilding> idcBuildings);

    //通过数据中心IDs获取所有机楼
    List<IdcBuilding> queryListByLocationIdList(List<String> list);

    /*根据机楼ID获取各个机楼的机房数量*/
    List<Map<String, Object>> getRoomNumByIds(List<String> list);

    /*删除机架  对机架进行软删除*/
    void updateBuildingToInvalidByIds(List<String> list) throws Exception;

    /*查询满足条件的机楼信息   包括已删除的数据*/
    IdcBuilding getBuildingModelByMap(Map<String,Object> map);

}
