package com.idc.mapper;


import com.idc.model.HouseInfo;
import com.idc.model.InterfaceInfo;
import com.idc.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_ISP_INTERFACE_INFO:新增数据上报_接口<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIspInterfaceMapper extends SuperMapper<InterfaceInfo, Long>{
	//新增情况
	List<HouseInfo> selectHouseInfoForAddByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	List<UserInfo> selectUserInfoForAddByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	//修改情况
	List<HouseInfo> selectHouseInfoForUpdateByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	List<UserInfo> selectUserInfoForUpdateByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	/*删除情况*/
	List<HouseInfo> selectHouseInfoForDeleteByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	List<UserInfo> selectUserInfoForDeleteByFkNewInfoId(@Param("fkNewInfoId") Long fkNewInfoId,@Param("ticketInstId") Long ticketInstId);
	List<Long> getUserverItems(@Param("fkServiceId") Long fkServiceId,@Param("itemFlag") Integer itemFlag);
	Boolean getIsHaveChangeNum(Long ticketInstId);
	Boolean getIsHaveChangeWithIpNum(Long ticketInstId);
	void insertTest(@Param("zipByte") byte[] zipByte, @Param("aesEncode") String aesEncode);

}

 