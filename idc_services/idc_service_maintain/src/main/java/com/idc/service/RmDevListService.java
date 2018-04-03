package com.idc.service;

import com.idc.model.RmDevList;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>RM_DEV_LIST:设备清单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmDevListService extends SuperService<RmDevList, String> {

    /*通过设备进出申请单ID获取具体的进出设备信息列表*/
    List<RmDevList> queryRmDevListListByRmDevInOutFormId(String rmDevInOutFormId);

    /*通过设备出入申请单ID删除其对应的进出设备*/
    void deleteRmDevListByRmDevInOutFormId(String rmDevInOutFormId) throws Exception;

    /*通过设备出入申请单IDS删除其对应的进出设备*/
    void deleteRmDevListByRmDevInOutFormIds(List<String> list) throws Exception;
}
