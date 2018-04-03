package com.idc.service;


import com.idc.model.*;

import java.util.List;

public interface IdcIspInfoService{
	//链路信息
	List<GatewayInfo> loadGatewayInfoList();
	//ip信息
	List<IpSegInfo> loadIpSegInfoList();
	//获取未上报的机架信息
	List<FrameInfo> loadFrameInfoList(Integer isSendIsp);
	List<FrameInfo> loadFrameInfoListByTicketInstId(Integer isSendIsp, Long ticketInstId, Integer status);

	//获取固化数据
	IspSolidData loadIspSolidData();
	//获取固化数据:人员
	List<IdcOfficer> loadIdcOfficerList();
	//获取用户数据
	List<UserInfo> loadReCustomerData();
	//通过用户ID获取用户对应的服务。测试的服务针对于域名服务。
	List<ServiceInfo> loadUserServerListByUserId(String aid);
	List<HousesHoldInfo> loadHousesHoldInfoList(Long ticketInstId);

	List<HousesHoldInfo> loadHousesHoldInfoListForDelUser(Long ticketInstId);
	//获取机架情况：任意
	String getLoadFrameInfoId(Long ticketInstId);
	//获取ip信息：如果ip不存在则服务不存在
	List<IpAttr> loadIpAttrList(Long ticketInstId, Integer status);
	//获取服务信息
	List<ServiceInfo> loadNetServiceinfoList(Long ticketInstId);
	List<ServiceInfo> loadNetServiceinfoListByTicketId(Long ticketInstId);
	List<ServiceInfo> loadNetServiceinfoListByCustomerId(String aid);


	//获取ip地址
	List<IpSegInfo> loadIpSegInfoForIpList(Long ticketInstId);
	//获取用户信息
	UserInfo loadReCustomerDataById(Long customerId);
	//获取随机的机架ID
	String getRandomSingleRackId(Long ticketInstId);
}
