package com.idc.service.impl;

import com.idc.mapper.IdcPhysicsHostsMapper;
import com.idc.mapper.IdcResourcesPoolMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcPhysicsHosts;
import com.idc.model.IdcResourcesPool;
import com.idc.service.IdcPhysicsHostsService;
import com.idc.service.IdcResourcesPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RESOURCES_POOL:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcResourcesPoolService")
public class IdcResourcesPoolServiceImpl extends SuperServiceImpl<IdcResourcesPool, String> implements IdcResourcesPoolService {
    @Autowired
    private IdcResourcesPoolMapper mapper;
//    @Autowired
//    private IdcPhysicsHostsService idcPhysicsHostsService;
    @Autowired
    private IdcPhysicsHostsMapper idcPhysicsHostsMapper;
    @Override
    public ExecuteResult saveOrupdate(IdcResourcesPool idcResourcesPool, String hostids) {
        ExecuteResult executeResult =new ExecuteResult();
        //add
        try {
            String[] hosts = hostids.split(",");
            Double idcResourcesDiskSize = 0D,idcResourcesMemSize = 0D;
            Long idcResourcesCpuNum = 0L;
//            List<IdcPhysicsHosts> hostarrs = new ArrayList<>();
            for (String host : hosts) {
                IdcPhysicsHosts modelById = idcPhysicsHostsMapper.getModelById(host);
                if(modelById!=null){
                    idcResourcesDiskSize+=(modelById.getIdcDiskAvailableSize()==null?0:modelById.getIdcDiskAvailableSize().doubleValue());
                    idcResourcesMemSize+=(modelById.getIdcMemAvailableSize()==null?0:modelById.getIdcMemAvailableSize().doubleValue());
                    idcResourcesCpuNum+=(modelById.getIdcPhysicsHostsCpuCore()==null?0:modelById.getIdcPhysicsHostsCpuCore().longValue());
                }
            }
            idcResourcesPool.setIdcMaxNum(BigDecimal.valueOf(hosts.length).longValue());
            idcResourcesPool.setIdcResourcesDiskSize(BigDecimal.valueOf(idcResourcesDiskSize));
            idcResourcesPool.setIdcResourcesMemSize(BigDecimal.valueOf(idcResourcesMemSize));
            idcResourcesPool.setIdcResourcesCpuNum(idcResourcesCpuNum);
            if (idcResourcesPool.getIdcResourcesPoolId() == null) {
                mapper.insert(idcResourcesPool);
                idcPhysicsHostsMapper.updatePoolByIds(idcResourcesPool.getIdcResourcesPoolId(), Arrays.asList(hostids.split(",")));
            }else{
                idcPhysicsHostsMapper.cleanPool(idcResourcesPool.getIdcResourcesPoolId());
            }
            executeResult.setState(true);
        }catch (Exception e){
            logger.error("",e);
            executeResult.setMsg("error");
        }
        return executeResult;
    }
    /**
     *   Special code can be written here
     */
}
