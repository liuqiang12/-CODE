package com.idc.service;

import com.idc.model.LogType;
import com.idc.model.SysOperateLog;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>sys_operate_log:用户操作日志<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 09,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOperateLogService extends SuperService<SysOperateLog, Integer>{
    /***
     * 插入日志系统
     * @param logType
     * @param desc
     */
    void insert(LogType logType, String desc);

    List<Map<String,Object>> queryListMap();

    List<Map<String,Object>> queryListMapPage(EasyUIPageBean page, Map<String, Object> queryMap);
    /**
	 *   Special code can be written here 
	 */
}
