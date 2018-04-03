package com.idc.service;

import java.util.List;

import com.idc.model.*;
import system.data.page.EasyUIPageBean;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_PHYSICS_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcPhysicsHostsService {
    /***
     * 获取IdcPhysicsHosts
     *
     * @param pageBean
     * @param idcPhysicsHosts
     * @param name
     * @param roomId
     * @param rackId
     * @return
     */
    List<IdcPhysicsHosts> queryListPageByMap(EasyUIPageBean pageBean, String type,IdcPhysicsHosts idcPhysicsHosts, String name, String roomId, Long rackId);

    IdcPhysicsHosts getModelById(String id);

    ExecuteResult saveOrupdate(IdcDevice idcDevice, IdcHost idcHost, IdcPhysicsHosts idcPhysicsHosts);

    /**
     *   Special code can be written here
     */
}
