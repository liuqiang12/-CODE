package com.idc.service;

import com.idc.model.IdcConnector;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_CONNECTOR:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcConnectorService extends SuperService<IdcConnector, Long> {
    List<Map<String, Object>> queryAllIdcConnectorInfoList(IdcConnector idcConnector);

	List<Map<String,Object>> queryIdcConnectorInfoListPage(PageBean<T> page, Object param);

    List<Map<String,Object>> getAllIdcConnectorInfo();

    Map<String,Object> getMapModelById(int id);

    int updateByPrimaryKeySelective(IdcConnector idcConnector);

	void saveIdcConnector(IdcConnector idcConnector);

    /*获取已绑定的端子信息 通过机架ID*/
    List<IdcConnector> queryBindedConnectorList(PageBean<T> page, Object param);

    /*通过端子IDS修改端子状态*/
    void updateStatusByConnIds(Map<String,Object> map) throws Exception;
}
