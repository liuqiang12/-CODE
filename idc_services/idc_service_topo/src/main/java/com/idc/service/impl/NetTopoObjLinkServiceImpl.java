package com.idc.service.impl;

import com.idc.mapper.NetTopoObjLinkMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.NetTopoObjLink;
import com.idc.service.NetTopoObjLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_TOPO_OBJ_LINK:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("netTopoObjLinkService")
public class NetTopoObjLinkServiceImpl extends SuperServiceImpl<NetTopoObjLink, Long> implements NetTopoObjLinkService {
    @Autowired
    private NetTopoObjLinkMapper mapper;

    @Override
    public ExecuteResult add(NetTopoObjLink link) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            NetTopoObjLink query = new NetTopoObjLink();
            query.setSrcdeviceid(link.getSrcdeviceid());
            query.setDesdeviceid(link.getDesdeviceid());
            query.setSrcid(link.getSrcid());
            query.setDesid(link.getDesid());
            query.setDesportname(link.getDesportname());
            query.setSrcportname(link.getSrcportname());

            NetTopoObjLink queryref = new NetTopoObjLink();
            queryref.setSrcdeviceid(link.getSrcdeviceid());
            queryref.setDesdeviceid(link.getDesdeviceid());
            queryref.setSrcid(link.getDesid());
            queryref.setDesid(link.getSrcid());
            queryref.setDesportname(link.getSrcportname());
            queryref.setSrcportname(link.getDesportname());
            List<NetTopoObjLink> netTopoObjLinks = mapper.queryListByObject(query);
            List<NetTopoObjLink> netTopoObjLinksref = mapper.queryListByObject(queryref);
            if (netTopoObjLinks.size() > 0 || netTopoObjLinksref.size() > 0) {
                executeResult.setMsg("链路已经存在");
                return executeResult;
            }
            mapper.insert(link);
            executeResult.setState(true);
            executeResult.setMsg(link.getTopoLinkID().toString());
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("添加链路失败");
        }
        return executeResult;
    }
    /**
     *   Special code can be written here
     */
}
