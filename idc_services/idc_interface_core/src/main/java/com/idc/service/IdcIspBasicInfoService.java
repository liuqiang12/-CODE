package com.idc.service;

import com.idc.model.*;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_ISP_BASIC_INFO:基础数据上报_接口<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIspBasicInfoService extends SuperService<BasicInfo, Long>{
	/**
	 * 获取修改的相关数据:根据工单信息
	 * @param ticketInstId
	 * @param idcIspFlag:1 代表是参数信息       0 代表不是参数信息
	 * @return
	 */
	List<BasicInfo> loadInterfaceData(Long ticketInstId,Long idcIspStatus,Integer idcIspFlag);
	List<BasicInfo> loadInterfaceAllData();
	List<BasicInfo> loadUpdatInfoByTicketId(Long ticketInstId);
	List<BasicInfo> loadUpdatHouseInfoByTicketId(Long ticketInstId);

	List<BasicInfo> loadDeleteByTicketId(Long ticketInstId);
	List<BasicInfo> loadDeleteHouseInfoByTicketId(Long ticketInstId);

	Boolean getIsHaveChangeNum(Long ticketInstId);
	Boolean getIsHaveChangeWithIpNum(Long ticketInstId);
	Boolean getBaseParamIsAsset(Long ticketInstId);
	void createInterfaceData(String jsonStr) throws Exception;

	Long createBasicInfo(BasicInfo basicInfo) throws Exception;

	Long createNewInfo(InterfaceInfo newInfo) throws Exception;

	Long createHouseInfo(HouseInfo houseInfo) throws Exception;

	Long createGatewayInfo(GatewayInfo gatewayInfo) throws Exception;

	Long createUserInfo(UserInfo userInfo) throws Exception;
	Long createInfo(Info info) throws Exception;
	Long createServiceInfo(ServiceInfo serviceInfo) throws Exception;
	Long createDomain(Domain domain) throws Exception;
	Long createIpSegInfo(IpSegInfo ipSegInfo) throws Exception;

	Long createHousesHoldInfo(HousesHoldInfo housesHoldInfo) throws Exception;
	void createFrameInfo(FrameInfo frameInfo) throws Exception;
	Long getHouseInfoIdByTicketInstId(Long ticketInstId);
	List<BasicInfo> loadNewInfoByTicketId(Long ticketInstId);
	List<BasicInfo> loadAddByTicketId(Long ticketInstId);
	void insertTest(byte[] zipByte, String aesEncode);
	List<Map<String,Object>> loadAllFrameInfo();
}
