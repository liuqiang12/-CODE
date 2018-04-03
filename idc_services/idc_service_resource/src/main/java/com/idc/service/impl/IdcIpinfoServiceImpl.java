package com.idc.service.impl;

import com.google.common.collect.Lists;
import com.idc.mapper.IdcIpinfoMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpInfo;
import com.idc.model.IdcIpSubnet;
import com.idc.model.ip.ALLCTYPE;
import com.idc.service.IdcIpinfoService;
import com.idc.service.ReLoadInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_IPINFO:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcIpinfoService")
public class IdcIpinfoServiceImpl extends SuperServiceImpl<IdcIpInfo, Long> implements IdcIpinfoService {
    @Autowired
    private IdcIpinfoMapper mapper;
    @Autowired
    @Qualifier("reLoadIPImpl")
    private ReLoadInterface reLoadInterface;

    @Override
    public boolean addIpTherd(Long begipnum, Long endipnum, Long id) {
        return false;
    }

    @Override
    public List<IdcIpSubnet> getAllocationIP(String ids) {
        return mapper.getAllocationIP(Lists.newArrayList(ids.split(",")));
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult allocation(IdcIpInfo ipInfo) {
        ExecuteResult executeResult = new ExecuteResult();
        IdcIpInfo modelById = mapper.getModelById(ipInfo.getId());
        if (modelById.getStatus() != null && modelById.getStatus() > 0L) {
            executeResult.setMsg("该IP不是空闲的");
            return executeResult;
        }
        modelById.setRemark(ipInfo.getRemark());
        try {
            modelById.setUsetype(ipInfo.getUsetype());
            modelById.setStatus(1L);
            mapper.updateByObject(modelById);
            executeResult.setState(true);
            reLoadInterface.reloadCount();
            logger.info("分配IP" + ipInfo.getIpaddress() + "成功");
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("分配IP失败");
        }
        return executeResult;
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult recovery(IdcIpInfo ipInfo) {
        ExecuteResult executeResult = new ExecuteResult();
        IdcIpInfo modelById = mapper.getModelById(ipInfo.getId());
        if (modelById.getStatus() == null || modelById.getStatus() == 0L) {
            executeResult.setMsg("该IP尚未分配");
            return executeResult;
        }
        modelById.setRemark(ipInfo.getRemark());
        try {
            modelById.setStatus(0L);
            modelById.setUsetype(null);
            mapper.updateByObject(modelById);
            executeResult.setState(true);
            reLoadInterface.reloadCount();
            logger.info("回收IP" + ipInfo.getIpaddress() + "成功");
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("回收IP失败");
        }
        return executeResult;
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult recoveryByids(ArrayList<String> ipids) {
        mapper.recoveryByids(ipids);
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setState(true);
        reLoadInterface.reloadCount();
        return executeResult;
    }

    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult recoveryBySubnetids(List<String> subnetids) {
        mapper.recoveryBySubnetids(subnetids);
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setState(true);
        reLoadInterface.reloadCount();
        return executeResult;
    }
    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult allc(long id, ALLCTYPE allctype,Long ticketid,String userId, String remark) {
        ExecuteResult allc = new ExecuteResult();
        IdcIpInfo ipInfo = mapper.getModelById(id);

        if(allctype != null){
            ipInfo.setStatus(allctype.getType());
        }
        if(remark != null){
            ipInfo.setRemark(remark);
        }
        if(userId != null){
            ipInfo.setUserId(userId);
        }
        if(ticketid != null){
            ipInfo.setTicket(ticketid);
        }
        try {
            mapper.updateByObject(ipInfo);
            allc.setState(true);
            reLoadInterface.reloadCount();
            logger.info("修改IP" + ipInfo.getIpaddress() + "状态成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allc;
    }
    @Override
    public ExecuteResult allc(long id, boolean isSubnet, ALLCTYPE allctype, Long ticketid, String userId, String remark) {
        if(isSubnet){
            return allcBySubnet(id,allctype,ticketid,userId,remark);
        }
        return allc(id, allctype, ticketid, userId, remark);
    }
    @Override
//    @CacheEvict(value = "5M", key = "'CountByState'", allEntries = true)
    public ExecuteResult allcBySubnet(long subNetId, ALLCTYPE allctype,Long ticketid,String userId, String remark) {
        ExecuteResult allc = new ExecuteResult();

        Long resourceStatus=allctype != null ? allctype.getType() : null;  //资源的状态

        try {
            mapper.updateBuSubnet(subNetId,resourceStatus,ticketid,userId,remark);
            allc.setState(true);
            reLoadInterface.reloadCount();
            logger.info("修改子网" +subNetId+ "状态成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allc;
    }

    /*获取客户所有占用IP 起始IP*/
    @Override
    public List<Map<String, Object>> queryUseredIPByCustomerId(Map map) {
        return mapper.queryUseredIPByCustomerId(map);
    }

    /*获取客户所有占用IP 起始IP page*/
    @Override
    public List<Map<String, Object>> queryUseredIPByCustomerIdPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryUseredIPByCustomerIdPage(page);
    }

    /*将客户占用IP数据写入idc_ipinof_temp表中   每次调用都会清空此表  用于展现客户视图*/
    @Override
    public void saveCustomerUsedIpToIpintoTemp(Long cusId) throws Exception {
        mapper.saveCustomerUsedIpToIpintoTemp(cusId);
    }

    /*根据客户ID获取ipinfo信息*/
    @Override
    public List<Map<String, Object>> queryUseredIPinfoByCustomerIdPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryUseredIPinfoByCustomerIdPage(page);
    }

    /*资源分配修改IP状态*/
    @Override
    public void updateIpInfoStatus(long id, String idType, ALLCTYPE allctype, Long ticketid, String userId, String remark, Long oldTicketId) throws Exception {
        Long resourceStatus = allctype != null ? allctype.getType() : null;  //资源的状态
        System.out.println("===id:"+id+"==idType:"+idType+"==ticketid:"+ticketid+"===userId:"+userId+"===remark:"+remark+"===oldTicketId:"+oldTicketId);
        mapper.updateIpInfoStatus(id, idType, resourceStatus, ticketid, userId, remark, oldTicketId);
        //重新统计IP信息
        reLoadInterface.reloadCount();
    }
}
