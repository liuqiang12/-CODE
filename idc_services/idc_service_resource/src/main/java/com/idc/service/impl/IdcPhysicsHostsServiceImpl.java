package com.idc.service.impl;

import com.idc.mapper.IdcPhysicsHostsMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcDevice;
import com.idc.model.IdcHost;
import com.idc.model.IdcPhysicsHosts;
import com.idc.service.IdcPhysicsHostsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.EasyUIPageBean;
import utils.typeHelper.MapHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_PHYSICS_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcPhysicsHostsService")
public class IdcPhysicsHostsServiceImpl implements IdcPhysicsHostsService {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private IdcPhysicsHostsMapper mapper;


    @Override
    public List<IdcPhysicsHosts> queryListPageByMap(EasyUIPageBean pageBean,String type, IdcPhysicsHosts idcPhysicsHosts, String name, String roomId, Long rackId) {
        Map<String, Object> query = MapHelper.queryCondition(idcPhysicsHosts);
        query.put("name", name);
        query.put("rackId", rackId);
        if("select".equals(type)){
            query.put("actiontype", type);
        }
        pageBean.setParams(query);
        return mapper.queryListPageByMap(pageBean);
    }

    @Override
    public IdcPhysicsHosts getModelById(String id) {
        return mapper.getModelById(id);
    }

    @Override
    public ExecuteResult saveOrupdate(IdcDevice idcDevice, IdcHost idcHost, IdcPhysicsHosts idcPhysicsHosts) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (idcHost != null) {
//                idcPhysicsHosts.setIdcPhysicsHostDiskSize(idcHost.getDisksize()==null?null:idcHost.getDisksize().doubleValue());
////                idcPhysicsHosts.setIdcPhysicsHostsCpuCore(idcHost.getCpusize());
//                idcPhysicsHosts.setIdcPhysicsHostsMemSize(idcHost.getMemsize()==null?null:idcHost.getMemsize().doubleValue());
                idcPhysicsHosts.setIdcPhysicsHostsName(idcDevice.getName());
                idcPhysicsHosts.setIdcDeviceid(idcDevice.getDeviceid().toString());
                idcPhysicsHosts.setIdcPhysicsHostsId(idcHost.getDeviceid().toString());
                idcPhysicsHosts.setIdcMemAvailableSize(new BigDecimal(idcPhysicsHosts.getIdcPhysicsHostsMemSize()-idcPhysicsHosts.getIdcReserveMemSize()));
                idcPhysicsHosts.setIdcDiskAvailableSize(new BigDecimal(idcPhysicsHosts.getIdcPhysicsHostDiskSize()-idcPhysicsHosts.getIdcReserveDiskSize()));
                mapper.insert(idcPhysicsHosts);
            } else {
                IdcPhysicsHosts updateObj = mapper.getModelById(idcPhysicsHosts.getIdcPhysicsHostsId());
                updateObj.setIdcReserveDiskSize(idcPhysicsHosts.getIdcReserveDiskSize());
                updateObj.setIdcReserveCpuCore(idcPhysicsHosts.getIdcReserveCpuCore());
                updateObj.setIdcReserveMemSize(idcPhysicsHosts.getIdcReserveMemSize());
                updateObj.setIdcMemAvailableSize(new BigDecimal(updateObj.getIdcPhysicsHostsMemSize() - updateObj.getIdcReserveMemSize()));
                updateObj.setIdcDiskAvailableSize(new BigDecimal(updateObj.getIdcPhysicsHostDiskSize()-updateObj.getIdcReserveDiskSize()));
                mapper.updateByObject(updateObj);
            }
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("编辑主机失败");
        }
        return executeResult;
    }
    /**
     *   Special code can be written here
     */
}
