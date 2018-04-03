package com.idc.mapper;

import java.util.List;
import java.util.Map;

import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.SysOperateLog;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_operate_log:用户操作日志<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 09,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOperateLogMapper extends SuperMapper<SysOperateLog, Integer>{
	/**
	 *   Special code can be written here 
	 */
    public List<Map<String,Object>> queryListMap() ;
    public List<Map<String,Object>> queryListMapPage(PageBean page) ;
}

 