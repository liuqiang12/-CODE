package com.idc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.IdcResourcesPoolMapper;
import com.idc.mapper.IdcVmDiskMapper;
import com.idc.model.IdcResourcesPool;
import com.idc.model.IdcVmDisk;
import com.idc.vo.IdcVmHostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcVmHostsMapper;
import com.idc.model.IdcVmHosts;
import com.idc.service.IdcVmHostsService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_VM_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcVmHostsService")
public class IdcVmHostsServiceImpl extends SuperServiceImpl<IdcVmHosts, String> implements IdcVmHostsService {
    @Autowired
    private IdcVmHostsMapper mapper;
    @Autowired
    private IdcResourcesPoolMapper idcResourcesPoolMapper;
    @Autowired
    private IdcVmDiskMapper idcVmDiskMapper;

    @Override
    public void queryListVoPage(EasyUIPageBean pageBean, IdcVmHosts idcVmHosts) {
        List<IdcVmHosts> list = queryListPage(pageBean, idcVmHosts);
        List<IdcResourcesPool> idcResourcesPools = idcResourcesPoolMapper.queryList();
        ImmutableMap<String, IdcResourcesPool> longIdcResourcesPoolImmutableMap = Maps.uniqueIndex(idcResourcesPools, new Function<IdcResourcesPool, String>() {
            @Override
            public String apply(IdcResourcesPool idcResourcesPool) {
                return idcResourcesPool.getIdcResourcesPoolId().toString();
            }
        });
        List<IdcVmHostVO> listVo = new ArrayList<>();
        for (IdcVmHosts vmHosts : list) {
            IdcVmDisk diskQuery = new IdcVmDisk();
            diskQuery.setIdcVmHostsId(vmHosts.getIdcVmHostsId());
            List<IdcVmDisk> idcVmDisks = idcVmDiskMapper.queryListByObject(diskQuery);
            IdcResourcesPool idcResourcesPool = longIdcResourcesPoolImmutableMap.get(vmHosts.getIdcResourcesPoolId());
            IdcVmHostVO vo = new IdcVmHostVO();
            vo.setCpuNum(vmHosts.getIdcVmHostsVcpuSize());
            vo.setMenSize(vmHosts.getIdcVmHostsMemSize().doubleValue());
            vo.setHostid(vmHosts.getIdcPhysicsHostsId());
            vo.setHostName(vmHosts.getIdcVmHostsName());
            vo.setIdcResourcesPool(idcResourcesPool);
            vo.setTicketId(vmHosts.getIdcBillId());
            vo.setTicketUser(vmHosts.getIdcUserId());
            vo.setIdcVmDisk(idcVmDisks);
            listVo.add(vo);
        }
        pageBean.setItems(listVo);
    }
    /**
     *   Special code can be written here
     */
}
