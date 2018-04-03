package com.idc.service;

import java.util.List;

import com.idc.model.*;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.SuperService;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>NET_TOPOVIEW:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetTopoviewService extends SuperService<NetTopoView, Long> {
    /***
     * 获取可选关联对象名字
     *
     * @param page
     * @param type 0 设备 2 子图
     * @param q    搜索名字
     * @return
     */
    List getObjs(EasyUIPageBean page, int type, String q);

    NetTopoView getModelDetailById(long viewid);

    ExecuteResult save(long viewId, List<NetTopoViewObj> nodes, List<NetTopoObjLink> links) throws Exception;

    /***
     * 获取链路流量
     *
     * @param netTopoView
     */
    List<NetTopoObjLinkFlow> getFlow(NetTopoView netTopoView);

    /***
     * 获取单挑链路的合并流量
     * 返回结果为通一设备同一目标设备下的所有端口链接 A-B B-A 流量会合并
     * @param srcid
     * @param desid
     * @return
     */
    NetTopoObjLinkFlow getLinkFlowMegir(long srcid, long desid);

    List<NetTopoObjLinkFlow> getLinkFlow(long srcid, long desid);

    List<NetTopoObjLinkFlow> getFlowByLinks(List<NetTopoObjLink> viewLinks);
    /**
     *   Special code can be written here
     */
}
