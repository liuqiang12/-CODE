package com.idc.service.impl;

import com.idc.mapper.NetTopoviewobjMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.NetTopoViewObj;
import com.idc.service.NetTopoviewobjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_TOPOVIEWOBJ:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("netTopoviewobjService")
public class NetTopoviewobjServiceImpl extends SuperServiceImpl<NetTopoViewObj, Long> implements NetTopoviewobjService {
    @Autowired
    private NetTopoviewobjMapper mapper;

    @Override
    public ExecuteResult add(NetTopoViewObj obj) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            NetTopoViewObj query = new NetTopoViewObj();
            query.setViewid(obj.getViewid());
            query.setObjtype(obj.getObjtype());
            query.setObjfid(obj.getObjfid());
            query.setExt(query.getName());
            List<NetTopoViewObj> netTopoViewObjs = mapper.queryListByObject(query);
            if (netTopoViewObjs != null && netTopoViewObjs.size() > 0) {
                executeResult.setMsg("对象已经存在");
                return executeResult;
            }
            mapper.insert(obj);
            executeResult.setMsg(obj.getObjpid().toString());
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            executeResult.setMsg("添加节点失败");
        }
        return executeResult;
    }
    /**
     *   Special code can be written here
     */
}
