package com.idc.mapper;


import com.idc.model.IdcConnector;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_CONNECTOR:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcConnectorMapper extends SuperMapper<IdcConnector, Long> {
    List<Map<String,Object>> queryIdcConnectorInfoListPage(PageBean<T> page);

    List<Map<String, Object>> queryAllIdcConnectorInfoList(IdcConnector idcConnector);

    List<Map<String,Object>> getAllIdcConnectorInfo();

    Map<String,Object> getMapModelById(int id);

    int updateByPrimaryKeySelective(IdcConnector idcConnector);

    void saveIdcConnector(IdcConnector idcConnector);

    /*获取已绑定的端子信息 通过机架ID*/
    List<IdcConnector> queryBindedConnectorList(PageBean<T> page);

    /*通过端子IDS修改端子状态*/
    void updateStatusByConnIds(Map<String,Object> map) throws Exception;
}

