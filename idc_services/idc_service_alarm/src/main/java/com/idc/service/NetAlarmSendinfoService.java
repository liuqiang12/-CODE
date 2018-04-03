package com.idc.service;


import com.idc.model.NetAlarmSendinfo;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>alarm_sendinfo:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetAlarmSendinfoService extends SuperService<NetAlarmSendinfo, Integer> {
    List<NetAlarmSendinfo> queryListPageByMap(EasyUIPageBean pageBean, String keyword, String starttime, String endtime);
    /**
	 *   Special code can be written here 
	 */
}
