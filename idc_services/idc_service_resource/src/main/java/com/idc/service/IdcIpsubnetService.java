package com.idc.service;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpSubnet;
import com.idc.model.IdcSubnetCountVo;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_IPSUBNET:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIpsubnetService extends SuperService<IdcIpSubnet, Long> {
    com.idc.model.ExecuteResult insertOrUpdate(IdcIpSubnet subnet);

    ExecuteResult split(long pid, List<IdcIpSubnet> subnetips);

    List<IdcIpSubnet> queryListAndCountPage(PageBean pageBean, Object o);

    /***
     * 统计信息
     *
     * @return
     */
    List<IdcSubnetCountVo> getCountByState();

    ExecuteResult merger(IdcIpSubnet idcIpSubnet, String delNetSegs, String updateIds, String targetNetSeg);

    @Transactional
    ExecuteResult recoverySubnet(List<String> ids);
    /**
     *   Special code can be written here
     */
}
