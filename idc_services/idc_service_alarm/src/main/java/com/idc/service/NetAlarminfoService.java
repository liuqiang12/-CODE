package com.idc.service;

import com.idc.model.NetAlarmInfoVo;
import system.data.page.EasyUIPageBean;

import java.util.List;

/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>NET_ALARMINFO_CURR:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetAlarminfoService {

    List<NetAlarmInfoVo> queryListPageByMap(EasyUIPageBean pageBean, String keyword, Integer regionid, Long objid,boolean isCurrAlarm, String starttime, String endtime, Integer alarmtype);

    void deleteCurrAlarmById(int id);
}
