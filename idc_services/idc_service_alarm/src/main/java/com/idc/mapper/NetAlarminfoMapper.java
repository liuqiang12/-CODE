package com.idc.mapper;

import com.idc.model.NetAlarminfo;
import org.apache.ibatis.annotations.Param;
import system.data.page.EasyUIPageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>NET_ALARMINFO_CURR:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetAlarminfoMapper extends SuperMapper<NetAlarminfo, Long>{

    NetAlarminfo getById(@Param("id")int id, @Param("iscurralarm")boolean iscurralarm);

    Long getMaxId();
    void insertHis(NetAlarminfo his);

    void deleteCurrById(int id);

    List<NetAlarminfo> queryCurrListPage(EasyUIPageBean pageBean);
    /**
	 *   Special code can be written here 
	 */
}

 