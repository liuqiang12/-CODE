package com.idc.mapper;

import com.idc.model.ZbMachineroom;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>zb_machineroom:IDC机房<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface ZbMachineroomMapper  extends SuperMapper<ZbMachineroom, Integer> {
    public List<Map<String,Object>> queryZbMachineroomgInfoListPage(PageBean page);
    public List<Map<String,Object>> queryAllZbMachineroomInfoList();
    public int updateByPrimaryKeySelective(ZbMachineroom record);

    void saveZbMachineroom(ZbMachineroom zb);

    List<Map<String,Object>> getAllZbMachineroomInfo(Map<String,Object> map);

    Map<String,Object> getMapModelById(int id);

    List<ZbMachineroom> queryListByRoomIds(List<String> list);

    Map<String, Object> getRoomStatistics(int roomId);

    /*通过机楼IDS获取机房*/
    List<ZbMachineroom> queryListByBuildingIds(List<String> list);

    /*获取当前机内所有模块*/
    List<Map<String, Object>> queryAllModuleByRoomId(int roomId);

    /*根据机房ID获取各个机房的机架数量*/
    List<Map<String, Object>> getRackNumByIds(List<String> list);

    /*删除机房    对机房进行软删除*/
    void updateRoomToInvalidByIds(List<String> list) throws Exception;

    /*通过机房名称获取机架*/
    ZbMachineroom queryZbMachineroomInfoBySitename(@Param("sitename") String sitename);

    /*通过机房Ids获取机房数据*/
    List<ZbMachineroom> queryZbMachineroomListByIds(Map<String, Object> map);

    /*通过数据中心Id查询所有的机房*/
    List<ZbMachineroom> queryZbMachineroomListByLocationId(@Param("locationId") Integer locationId);
}