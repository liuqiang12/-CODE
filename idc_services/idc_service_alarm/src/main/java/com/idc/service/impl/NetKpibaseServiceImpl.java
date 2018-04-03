package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.NetKpibaseMapper;
import com.idc.model.NetKpibase;
import com.idc.service.NetKpibaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_KPIBASE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service
public class NetKpibaseServiceImpl extends SuperServiceImpl<NetKpibase, Long> implements NetKpibaseService {
    @Autowired
    private NetKpibaseMapper mapper;

    @Override
    public ImmutableMap<Long, NetKpibase> getKpiBaseMap() {
        List<NetKpibase> netKpibases = mapper.queryList();
        return Maps.uniqueIndex(netKpibases, new Function<NetKpibase, Long>() {
            @Override
            public Long apply(NetKpibase netKpibase) {
                return netKpibase.getKpiid();
            }
        });
    }
    /**
     *   Special code can be written here
     */
}
