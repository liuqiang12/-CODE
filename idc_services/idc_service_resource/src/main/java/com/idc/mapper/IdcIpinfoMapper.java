package com.idc.mapper;

import com.idc.model.IdcIpInfo;
import com.idc.model.IdcIpSubnet;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_IPINFO:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIpinfoMapper extends SuperMapper<IdcIpInfo, Long> {
    List<Long> getSeqs(long length  );

    void updateIpBySubnet(IdcIpSubnet subnetip);

    List<IdcIpSubnet> getAllocationIP(ArrayList<String> strings);
    /**
     * 工单使用
     * @throws Exception
     */
    void foreachUpdateInfo(List<Map<String, Object>> resourceList) throws  Exception;

    void singleUpdateInfoById(@Param("resourceId") Long resourceId, @Param("status") Long status) throws Exception;

    /***
     * 统计IP 使用信息
     * @param localId
     * @return
     */
    Map<String, Long> countUsed(@Param("localId")Long localId);

    void recoveryByids(@Param("ids")ArrayList<String> ipids);

    void recoveryBySubnetids(@Param("subnetids")List<String> ipids);

    /*获取客户所有占用IP 起始IP*/
    List<Map<String, Object>> queryUseredIPByCustomerId(Map map);

    /*获取客户所有占用IP 起始IP  page*/
    List<Map<String, Object>> queryUseredIPByCustomerIdPage(PageBean page);

    /*将客户占用IP数据写入idc_ipinof_temp表中   每次调用都会清空此表  用于展现客户视图*/
    void saveCustomerUsedIpToIpintoTemp(@Param("cusId") Long cusId) throws Exception;

    /*根据客户ID获取ipinfo信息*/
    List<Map<String, Object>> queryUseredIPinfoByCustomerIdPage(PageBean page);

    void updateBuSubnet(@Param("subNetId")long subNetId, @Param("status")long status,@Param("ticketid") Long ticketid, @Param("userId")String userId,@Param("remark") String remark);

    /*资源分配修改IP状态  idType  subnet  按网段分配     为空  则为单个分配*/
    void updateIpInfoStatus(@Param("id") long id, @Param("idType") String idType, @Param("status") long status, @Param("ticketid") Long ticketid, @Param("userId") String userId, @Param("remark") String remark, @Param("oldTicketId") Long oldTicketId) throws Exception;

}

 