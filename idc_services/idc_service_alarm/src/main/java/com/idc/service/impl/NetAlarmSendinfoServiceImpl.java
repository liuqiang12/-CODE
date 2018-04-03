package com.idc.service.impl;

import com.idc.mapper.AlarmSendinfoMapper;
import com.idc.model.NetAlarmSendinfo;
import com.idc.service.NetAlarmSendinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>alarm_sendinfo:<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 23,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service
public class NetAlarmSendinfoServiceImpl extends SuperServiceImpl<NetAlarmSendinfo, Integer> implements NetAlarmSendinfoService {
	@Autowired
	private AlarmSendinfoMapper mapper;

    @Override
    public List<NetAlarmSendinfo> queryListPageByMap(EasyUIPageBean pageBean, String keyword, String starttime, String endtime) {
        Map<String,Object> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("starttime",starttime);
        params.put("endtime",endtime);
        List<NetAlarmSendinfo> alarmInfos = queryListPage(pageBean, params);
        return alarmInfos;
    }
    /**
	 *   Special code can be written here 
	 */
}
