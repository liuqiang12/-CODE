package com.idc.mapper;

import com.idc.model.*;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_BASIC_INFO:基础数据上报_接口<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIspBasicInfoMapper extends SuperMapper<BasicInfo, Long>{
	/**
	 * 获取修改的相关数据:根据工单信息
	 * @param ticketInstId  idcIspFlag
	 * @return
	 */
	List<BasicInfo> loadInterfaceAddData(@Param("ticketInstId") Long ticketInstId, @Param("idcIspStatus") Long idcIspStatus,@Param("idcIspFlag") Integer idcIspFlag);
	List<BasicInfo> loadAssetBasicInfo(@Param("idcIspFlag") Integer idcIspFlag);
	List<BasicInfo> loadInterfaceUpdateData(@Param("ticketInstId") Long ticketInstId, @Param("idcIspStatus") Long idcIspStatus);
	List<BasicInfo> loadInterfaceDeleteData(@Param("ticketInstId") Long ticketInstId, @Param("idcIspStatus") Long idcIspStatus);
	Long createBasicInfo(BasicInfo basicInfo) throws Exception;
	Long createNewInfo(InterfaceInfo newInfo) throws Exception;
	Long createHouseInfo(HouseInfo houseInfo) throws Exception;
	Long createGatewayInfo(GatewayInfo gatewayInfo) throws Exception;
	Long createUserInfo(UserInfo userInfo) throws Exception;
	Long createInfo(Info info) throws Exception;

	Long createServiceInfo(ServiceInfo serviceInfo) throws Exception;

	Long createIpSegInfo(IpSegInfo ipSegInfo) throws Exception;

	Long createHousesHoldInfo(HousesHoldInfo housesHoldInfo) throws Exception;

	Boolean getBaseParamIsAsset(Long ticketInstId);
	void createFrameInfo(FrameInfo frameInfo) throws Exception;
	Long createDomain(Domain domain) throws Exception;
	Long getHouseInfoIdByTicketInstId(Long ticketInstId);
}

 