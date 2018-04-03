package com.idc.mapper;

import com.idc.model.*;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * Created by DELL on 2017/6/23.
 */
public interface IdcIspInfoMapper  extends SuperMapper<Object, Long> {
    /**
     * 加载链路信息
     * @return
     */
    List<GatewayInfo> loadGatewayInfoList();

    /**
     * 加载机房ip信息
     * @return
     */
    List<IpSegInfo> loadIpSegInfoList();

    /**
     * 获取未上报的的机架信息
     * @param isSendIsp
     * @return
     */
    List<FrameInfo> loadFrameInfoList(Integer isSendIsp);

    /**
     * 获取固化数据
     * @return
     */
    IspSolidData loadIspSolidData(String houseId);

    /**
     * 获取固化的人员信息
     * @return
     */
    List<IdcOfficer> loadIdcOfficerList();

    /**
     * 获取客户数据
     * @return
     */
    List<UserInfo> loadReCustomerData();

    /**
     * 通过用户获取用户的域名服务
     * @param aid
     * @return
     */
    List<ServiceInfo> loadUserServerListByUserId(String aid);

    /**
     * 通过服务对应的工单获取对应的机房。如果没有机房。则生成随即ID
     * @param ticketInstId
     * @return
     */
    List<HousesHoldInfo> loadHousesHoldInfoList(Long ticketInstId);

    /**
     * 获取任意一个机架ID
     * @param ticketInstId
     * @return
     */
    String getLoadFrameInfoId(Long ticketInstId);

    /**
     * 获取IP信息
     * @param ticketInstId
     * @return
     */
    List<IpAttr> loadIpAttrList(@Param("ticketInstId") Long ticketInstId, @Param("status") Integer status);

    /**
     * 获取服务信息
     * @param ticketInstId
     * @return
     */
    List<ServiceInfo> loadNetServiceinfoList(Long ticketInstId);

    /**
     * 获取机房信息
     * @param ticketInstId
     * @return
     */
    List<HousesHoldInfo> loadHousesHoldInfoListForDelUser(Long ticketInstId);

    /**
     * 加载所有的分配IP
     * @param ticketInstId
     * @return
     */
    List<IpSegInfo> loadIpSegInfoForIpList(Long ticketInstId);

    /**
     * 获取用户信息
     * @param customerId
     * @return
     */
    UserInfo loadReCustomerDataById(Long customerId);

    /**
     * 获取随机的机架id
     * @param ticketInstId
     * @return
     */
    String getRandomSingleRackId(Long ticketInstId);

    /**
     * 获取机架信息
     * @param isSendIsp
     * @param ticketInstId
     * @return
     */
    List<FrameInfo> loadFrameInfoListByTicketInstId(@Param("isSendIsp") Integer isSendIsp, @Param("ticketInstId") Long ticketInstId, @Param("status") Integer status);
    List<ServiceInfo> loadNetServiceinfoListByTicketId(Long ticketInstId);
    List<ServiceInfo> loadNetServiceinfoListByCustomerId(String customerId);
    void callIspCustomerPro();
    void callIspResourcePro();
    void callCustSectionPro();
    void callIpSection(String ticketInstId);
    List<LocalIspCustomer> loadIspCustomerParamList(String houseId);
    List<LocalIspServer> loadLocalIspServerList(@Param("aid") Long aid, @Param("nature") Integer nature);
    List<LocalIspHousehold> loadLocalIspHousehold(Long aid);
    List<LocalIspIpInfo> loadLocalIspIpInfo(Long aid);
    List<LocalIspHousehold> loadLocalIspHouseholdList(Long aid);
    List<LocalIspIpInfo> loadAllLocalIspIpInfo();
    List<LocalIspHousehold> loadLocalIspHouseholdAll();
    List<LocalIspIpSeg> loadLocalIspIpSeg(String houseId);
    List<LocalIspIframe> loadLocalIspIframe(String houseId);

    Boolean getIsUploadStatus(Integer category);

    void updateIsUploadStatus(Integer category);
    void callProc_resource_hs(@Param("ticketInstId") Long ticketInstId, @Param("ticketStatus") Integer ticketStatus);
    void callProcResourceBh(@Param("ticketInstId") Long ticketInstId, @Param("ticketStatus") Integer ticketStatus);
    List<String> getFindHouseIds();
    List<LocalIspServer> getIspServerParamList(Long customerId);
    List<LocalIspHousehold>  getIspHouseHolde(Long serviceNodeId);
    List<LocalIspIpInfo>  getIspIpInfoList(@Param("serviceNodeId") Long serviceNodeId, @Param("frameInfoId") Long frameInfoId);
    List<LocalIspServer>  getIspServerParamListByHouseId(String houseId);
    List<LocalIspIpInfo>  getIspIpInfoListAllByHouseId(String houseId);
    /* 获取相应的客户信息 */
    List<LocalIspCustomer> loadCustomers(@Param("houseId")String houseId,@Param("nature")Integer nature);
    Integer getCountRack(String ticketId);
    Integer getCountIP(String ticketId);
    List<LocalIspServer> getServerIspByServerId(String serviceId);
    Long getDomainIdFromSeq();
    List<LocalIspHousehold> getHouseInfoByTicketId(String ticketId);
    List<ISPDomain> loadDomainByTicketId(String ticketId);
    List<LocalIspCustomer> loadCustomersWithAll(String houseId);
    List<GatewayInfo> loadGateByHouseId(String houseId);
}
