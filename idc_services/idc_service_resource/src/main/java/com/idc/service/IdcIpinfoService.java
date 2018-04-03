package com.idc.service;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpInfo;
import com.idc.model.IdcIpSubnet;
import com.idc.model.ip.ALLCTYPE;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_IPINFO:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIpinfoService extends SuperService<IdcIpInfo, Long> {
    boolean addIpTherd(Long begipnum, Long endipnum, Long id);

    List<IdcIpSubnet> getAllocationIP(String ids);

    ExecuteResult allocation(IdcIpInfo ipInfo);

    ExecuteResult recovery(IdcIpInfo ipInfo);

    ExecuteResult recoveryByids(ArrayList<String> ipids);

    ExecuteResult recoveryBySubnetids(List<String> subnetids);

    ExecuteResult allc(long id, ALLCTYPE allctype, Long ticketid, String userId, String remark);

//    ExecuteResult allc(long id, ALLCTYPE accltype, String remark);


    /*将客户占用IP数据写入idc_ipinof_temp表中   每次调用都会清空此表  用于展现客户视图*/
    void saveCustomerUsedIpToIpintoTemp(Long cusId) throws Exception;

    /***
     *
     * @param id
     * @param isSubnet 是否子网
     * @param allctype
     * @param ticketid
     * @param userId
     * @param remark
     * @return
     */
    ExecuteResult allc(long id, boolean isSubnet, ALLCTYPE allctype, Long ticketid, String userId, String remark);

    /***
     * 子网分配
     * @param subnetid
     * @param allctype
     * @param ticketid
     * @param userId
     * @param remark
     * @return
     */
    ExecuteResult allcBySubnet(long subnetid, ALLCTYPE allctype, Long ticketid, String userId, String remark);

    /*获取客户所有占用IP 起始IP*/
    List<Map<String, Object>> queryUseredIPByCustomerId(Map map);

    /*获取客户所有占用IP 起始IP page*/
    List<Map<String, Object>> queryUseredIPByCustomerIdPage(PageBean page, Object param);

    /*根据客户ID获取ipinfo信息*/
    List<Map<String, Object>> queryUseredIPinfoByCustomerIdPage(PageBean page, Object param);

    /*资源分配修改IP状态*/
    void updateIpInfoStatus(long id, String idType, ALLCTYPE allctype, Long ticketid, String userId, String remark, Long oldTicketId) throws Exception;
}
