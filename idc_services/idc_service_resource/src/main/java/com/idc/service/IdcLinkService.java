package com.idc.service;

import com.idc.model.IdcLink;
import com.idc.model.LinkNode;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_LINK:连接表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcLinkService extends SuperService<IdcLink, Long> {

    /*资源树链接*/
    List<LinkNode> getTree(String type, long id);

    /*资源树*/
    Map<String,Object> getRackResourceTree(Long rackId);

    /*资源分配-资源树*/
    Map<String,Object> getDistributionTree(String type, String ids, Long roomId);

    List<Map<String, Object>> queryListByObjectMap(IdcLink idcLink);

    /*获取链路信息*/
    List<Map<String, Object>> queryLinksByAZ(String aKey, String zKey, String name, String portStr,Long wlRackId);

    /*清楚链路关系时  清除与端子的绑定*/
    void unBindConnectToLink(List list) throws Exception;

    /*通过AZ端端口删除链路   如：352352_352352*/
    void deleteLinkByAZPortStrList(List list) throws Exception;

    /*跟据端子ID，获取上行和下行端口信息*/
    Map<String,Object> queryUpAndDownPortInfo(Long id);
}
