package com.idc.service.impl;

import com.idc.mapper.IdcIspBasicInfoMapper;
import com.idc.mapper.IdcIspInterfaceMapper;
import com.idc.mapper.IdcLocationMapper;
import com.idc.model.*;
import com.idc.service.IdcIspBasicInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_ISP_BASIC_INFO:基础数据上报_接口<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcIspBasicInfoService")
public class IdcIspBasicInfoServiceImpl extends SuperServiceImpl<BasicInfo, Long> implements IdcIspBasicInfoService {
	@Autowired
	private IdcIspBasicInfoMapper mapper;
	@Autowired
	private IdcIspInterfaceMapper idcIspInterfaceMapper;
	@Autowired
	private IdcLocationMapper idcLocationMapper;
	public List<Map<String,Object>> loadAllFrameInfo(){
		return idcLocationMapper.loadAllFrameInfo();
	}

	/**
	 * 获取修改的相关数据:根据工单信息
	 * @param ticketInstId
	 * @return
	 */
	@Override
	public List<BasicInfo> loadInterfaceData(Long ticketInstId,Long idcIspStatus,Integer idcIspFlag){
		/**
		 * 1：获取BasicInfo:新增的相关数据
		 * 		获取newInfo相关数据
		 */
		List<BasicInfo> basicInfos = new ArrayList<BasicInfo>();
		if(idcIspStatus != null && idcIspStatus == 0){//新增的相关数据
			basicInfos = mapper.loadInterfaceAddData(ticketInstId,idcIspStatus,idcIspFlag);//固化数据一次性查出来：basicinfo、newInfo[idcOfficer/emergencyContact]
			if(basicInfos != null && !basicInfos.isEmpty()){
				Iterator<BasicInfo> it = basicInfos.iterator();
				while(it.hasNext()){
					//获取newInfo
					BasicInfo basicInfo = it.next();
					InterfaceInfo interfaceInfo = basicInfo.getNewInfo();
					//设置houseInfos
					List<HouseInfo> houseInfos = idcIspInterfaceMapper.selectHouseInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());//固化数据一次性查出来:houseInfo/
					interfaceInfo.setAddHouseInfos(houseInfos);
					//设置userInfos
					List<UserInfo> userInfos = idcIspInterfaceMapper.selectUserInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());
					interfaceInfo.setUserInfos(userInfos);
				}
			}
		}if(idcIspStatus != null && idcIspStatus == 9999){//初始化的时候生成所有的文件信息
			basicInfos = mapper.loadInterfaceAddData(-1000000l,-1000000l,1);//固化数据一次性查出来：basicinfo、newInfo[idcOfficer/emergencyContact]
			if(basicInfos != null && !basicInfos.isEmpty()){
				Iterator<BasicInfo> it = basicInfos.iterator();
				while(it.hasNext()){
					//获取newInfo
					BasicInfo basicInfo = it.next();
					InterfaceInfo interfaceInfo = basicInfo.getNewInfo();
					//设置houseInfos:获取所有的
					List<HouseInfo> houseInfos = idcIspInterfaceMapper.selectHouseInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());//固化数据一次性查出来:houseInfo/
					interfaceInfo.setAddHouseInfos(houseInfos);
					//设置userInfos
					List<UserInfo> userInfos = idcIspInterfaceMapper.selectUserInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());
					interfaceInfo.setUserInfos(userInfos);
				}
			}
		}else if(idcIspStatus != null && idcIspStatus == 1){//修改的相关数据
			basicInfos = mapper.loadInterfaceUpdateData(ticketInstId,idcIspStatus);//固化数据一次性查出来：basicinfo、newInfo[idcOfficer/emergencyContact]
			if(basicInfos != null && !basicInfos.isEmpty()){
				Iterator<BasicInfo> it = basicInfos.iterator();
				while(it.hasNext()){
					//获取newInfo
					BasicInfo basicInfo = it.next();
					InterfaceInfo interfaceInfo = basicInfo.getUpdateInfo();
					//获取相应的updateData信息
					UpdateData updateData = interfaceInfo.getUpdateData();
					//然后更加updateData的主键获取其下的所有的houseinfo 和 userinfo
					//设置houseInfos
					List<HouseInfo> houseInfos = idcIspInterfaceMapper.selectHouseInfoForUpdateByFkNewInfoId(updateData.getAid(),updateData.getTicketInstId());//固化数据一次性查出来:houseInfo/
					updateData.setHouseInfos(houseInfos);
					//设置userInfos
					List<UserInfo> userInfos = idcIspInterfaceMapper.selectUserInfoForUpdateByFkNewInfoId(updateData.getAid(),updateData.getTicketInstId());

					updateData.setUserInfos(userInfos);
				}
			}
		}else if(idcIspStatus != null && idcIspStatus == 2) {//删除的相关数据
			basicInfos = mapper.loadInterfaceDeleteData(ticketInstId,idcIspStatus);//固化数据一次性查出来：basicinfo、newInfo[idcOfficer/emergencyContact]
			if(basicInfos != null && !basicInfos.isEmpty()){
				Iterator<BasicInfo> it = basicInfos.iterator();
				while(it.hasNext()){
					//获取newInfo
					BasicInfo basicInfo = it.next();
					InterfaceInfo interfaceInfo = basicInfo.getDeleteInfo();
					//获取相应的updateData信息
					DeleteData deleteData = interfaceInfo.getDeleteData();
					//然后更加updateData的主键获取其下的所有的houseinfo 和 userinfo
					//设置house
					List<HouseInfo> houseInfos = idcIspInterfaceMapper.selectHouseInfoForDeleteByFkNewInfoId(deleteData.getAid(),deleteData.getTicketInstId());//固化数据一次性查出来:houseInfo/
					deleteData.setHouseInfos(houseInfos);
					//设置user
					List<UserInfo> userInfos = idcIspInterfaceMapper.selectUserInfoForDeleteByFkNewInfoId(deleteData.getAid(),deleteData.getTicketInstId());
					//通过遍历获取userInfos
					if(userInfos != null && !userInfos.isEmpty()){
						Iterator<UserInfo> userInfoIt = userInfos.iterator();
						while(userInfoIt.hasNext()){
							UserInfo userInfoTmp = userInfoIt.next();
							List<ServiceData> serviceDataList = userInfoTmp.getServiceData();
							if(serviceDataList != null && !serviceDataList.isEmpty()){
								Iterator<ServiceData> serviceDataIt = serviceDataList.iterator();
								while(serviceDataIt.hasNext()){
									ServiceData serviceDataTmp = serviceDataIt.next();
									List<Long> userverIdItems = idcIspInterfaceMapper.getUserverItems(serviceDataTmp.getAid(),1);
									List<Long> udoMainIdItems = idcIspInterfaceMapper.getUserverItems(serviceDataTmp.getAid(),2);
									List<Long> usHhIdItems = idcIspInterfaceMapper.getUserverItems(serviceDataTmp.getAid(),3);
									if(userverIdItems != null && !userverIdItems.isEmpty()){
										serviceDataTmp.setServiceId(userverIdItems);
									}
									if(udoMainIdItems != null && !udoMainIdItems.isEmpty()){
										serviceDataTmp.setDomainId(udoMainIdItems);
									}
									if(usHhIdItems != null && !usHhIdItems.isEmpty()){
										serviceDataTmp.setHhId(usHhIdItems);
									}
								}
							}
						}
					}
					deleteData.setUserInfos(userInfos);
				}
			}
		}else if(idcIspStatus != null && idcIspStatus == -1000000l) {//参数相关数据
			basicInfos = mapper.loadInterfaceAddData(ticketInstId,idcIspStatus,idcIspFlag);//固化数据一次性查出来：basicinfo、newInfo[idcOfficer/emergencyContact]
			if(basicInfos != null && !basicInfos.isEmpty()){
				Iterator<BasicInfo> it = basicInfos.iterator();
				while(it.hasNext()){
					//获取newInfo
					BasicInfo basicInfo = it.next();
					InterfaceInfo interfaceInfo = basicInfo.getNewInfo();
					//设置houseInfos
					List<HouseInfo> houseInfos = idcIspInterfaceMapper.selectHouseInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());//固化数据一次性查出来:houseInfo/
					interfaceInfo.setAddHouseInfos(houseInfos);
					//设置userInfos
					List<UserInfo> userInfos = idcIspInterfaceMapper.selectUserInfoForAddByFkNewInfoId(interfaceInfo.getAid(),interfaceInfo.getTicketInstId());
					interfaceInfo.setUserInfos(userInfos);
				}
			}
		}

		 return basicInfos;
	}
	@Override
	public List<BasicInfo> loadDeleteByTicketId(Long ticketInstId){
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadDeleteInfo(basicInfos,ticketInstId);
		return basicInfos;
	}
	@Override
	public List<BasicInfo> loadDeleteHouseInfoByTicketId(Long ticketInstId){
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadDeleteInfo(basicInfos,ticketInstId);
		return basicInfos;
	}


	@Override
	public Boolean getIsHaveChangeNum(Long ticketInstId) {
		return idcIspInterfaceMapper.getIsHaveChangeNum(ticketInstId);
	}
	@Override
	public Boolean getIsHaveChangeWithIpNum(Long ticketInstId) {
		return idcIspInterfaceMapper.getIsHaveChangeWithIpNum(ticketInstId);
	}

	/**
	 * 修改的相关节点
	 * @param ticketInstId
	 * @return
	 */
	@Override
	public List<BasicInfo> loadUpdatInfoByTicketId(Long ticketInstId){
		System.out.println("数据中心");
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadUpdateInfo(basicInfos,ticketInstId);
		return basicInfos;
	}
	@Override
	public List<BasicInfo> loadUpdatHouseInfoByTicketId(Long ticketInstId){
		System.out.println("数据中心");
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadHouseUpdateInfo(basicInfos,ticketInstId);
		return basicInfos;
	}

	public void deleteAllData(List<BasicInfo> basicInfos){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo delectInfo = new InterfaceInfo();
			delectInfo.setIdcId("A2.B1.B2-20100001");
			delectInfo.setDeleteData(loadAllDeleteData());
			/******************** userinfo **************************/
			//updateInfo.setUserInfos(loadUserinfo());
			basicInfo.setDeleteInfo(delectInfo);
		}
	}
	/*所有的删除节点信息*/
	public DeleteData loadAllDeleteData(){
		DeleteData deleteData = new DeleteData();
		/*删除house*/
		deleteData.setHouseInfos(loadHouseInfoForDelete(null));
		deleteData.setUserInfos(loadUserinfoForDelete());
		return deleteData;
	}

	public void loadDeleteInfo(List<BasicInfo> basicInfos,Long ticketInstId){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo delectInfo = new InterfaceInfo();
			delectInfo.setIdcId("A2.B1.B2-20100001");
			delectInfo.setDeleteData(loadDeleteDataForHouse(ticketInstId));
			/******************** userinfo **************************/
			//updateInfo.setUserInfos(loadUserinfo());
			basicInfo.setDeleteInfo(delectInfo);
		}
	}
	public void loadHouseUpdateInfo(List<BasicInfo> basicInfos,Long ticketInstId){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo updateInfo = new InterfaceInfo();
			updateInfo.setIdcId("A2.B1.B2-20100001");
			updateInfo.setIdcName("中国（西部）云计算中心");
			updateInfo.setIdcAdd("四川成都双流区物联二路中国西部云计算中心");
			updateInfo.setIdcZip("610000");
			updateInfo.setCorp("赵大春");
			//设置idcOffer  网络信息安全责任人信息
			updateInfo.setIdcOfficer(loadIdcOfficer());
			// emergencyContact 单位应急联系人信息与联系方式
			updateInfo.setEmergencyContact(loadEmergencyContact());
			updateInfo.setUpdateData(loadHouseUpdateData(ticketInstId));
			/******************** userinfo **************************/
			//updateInfo.setUserInfos(loadUserinfo());
			basicInfo.setUpdateInfo(updateInfo);
		}
	}
	public void loadUpdateInfo(List<BasicInfo> basicInfos,Long ticketInstId){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo updateInfo = new InterfaceInfo();
			updateInfo.setIdcId("A2.B1.B2-20100001");
			updateInfo.setUpdateData(loadUpdateData(ticketInstId));
			/******************** userinfo **************************/
			//updateInfo.setUserInfos(loadUserinfo());
			basicInfo.setUpdateInfo(updateInfo);
		}
	}

	public DeleteData loadDeleteDataForHouse(Long ticketInstId){
		DeleteData deleteData = new DeleteData();
		//增加的机架信息
		List<HouseInfo> houseInfos = loadHouseInfoForDelete(ticketInstId);
		//List<UserInfo> userInfos = loadUserinfoByTicketForDelete(ticketInstId);
		//deleteData.setUserInfos(userInfos);
		deleteData.setHouseInfos(houseInfos);
		return deleteData;
	}
	public DeleteData loadDeleteData(Long ticketInstId){
		DeleteData deleteData = new DeleteData();
		//增加的机架信息
		//List<HouseInfo> houseInfos = loadHouseInfoForDelete();
		List<UserInfo> userInfos = loadUserinfoByTicketForDelete(ticketInstId);
		deleteData.setUserInfos(userInfos);
		//deleteData.setHouseInfos(houseInfos);
		return deleteData;
	}
	public UpdateData loadHouseUpdateData(Long ticketInstId){
		UpdateData updateData = new UpdateData();
		//增加的机架信息
		List<HouseInfo> houseInfos = loadHouseInfoByTicketToUpdate_House(ticketInstId);
		//这里直接将用户中的所有数据都提出来
		 List<UserInfo> userInfos = loadUserinfoByTicketToUpdate(ticketInstId);
		 updateData.setUserInfos(userInfos);
		updateData.setHouseInfos(houseInfos);

		return updateData;
	}
	public UpdateData loadUpdateData(Long ticketInstId){
		UpdateData updateData = new UpdateData();
		//增加的机架信息
		List<HouseInfo> houseInfos = loadHouseInfoByTicketToUpdate(ticketInstId);
		//这里直接将用户中的所有数据都提出来
		//List<UserInfo> userInfos = loadUserinfoByTicketToUpdate(ticketInstId);
		//updateData.setUserInfos(userInfos);
		updateData.setHouseInfos(houseInfos);
		return updateData;
	}
	@Override
	public List<BasicInfo> loadNewInfoByTicketId(Long ticketInstId) {
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadNewInfoByTicketId(basicInfos,ticketInstId);
		return basicInfos;
	}
	@Override
	public List<BasicInfo> loadAddByTicketId(Long ticketInstId){
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadAddForHouseByTicketId(basicInfos,ticketInstId);
		return basicInfos;
	}
	@Override
	public void insertTest(byte[] zipByte, String aesEncode) {
		idcIspInterfaceMapper.insertTest(zipByte,aesEncode);
	}

	@Override
	public List<BasicInfo> loadInterfaceAllData() {
		//获取所有的需要生成的xml信息
		/*
		* 获取所有的数据中心数据
		* */
		List<BasicInfo> basicInfos = loadBasicInfos();
		/*然后获取数据中心的基础属性*/
		loadNewInfo(basicInfos);
		return basicInfos;
	}

	public void loadAddForHouseByTicketId(List<BasicInfo> basicInfos,Long ticketInstId){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo newInfo = new InterfaceInfo();
			newInfo.setIdcId("A2.B1.B2-20100001");
			newInfo.setIdcName("中国（西部）云计算中心");
			newInfo.setIdcAdd("四川成都双流区物联二路中国西部云计算中心");
			newInfo.setIdcZip("610000");
			newInfo.setCorp("赵大春");
			//设置idcOffer  网络信息安全责任人信息
			newInfo.setIdcOfficer(loadIdcOfficer());
			// emergencyContact 单位应急联系人信息与联系方式
			newInfo.setEmergencyContact(loadEmergencyContact());
			//这里需要通过数据库查询统计数据中心数量:默认肯定是一个
			newInfo.setHouseCount(1l);
			//填写数据中心的基本信息节点
			newInfo.setAddHouseInfos(loadHouseInfoByTicketToAdd(ticketInstId));
			/******************** userinfo **************************/
			newInfo.setUserInfos(loadUserinfoByTicketId(ticketInstId));
			basicInfo.setNewInfo(newInfo);
		}
	}

	public void loadNewInfoByTicketId(List<BasicInfo> basicInfos,Long ticketInstId){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo newInfo = new InterfaceInfo();
			newInfo.setIdcId("A2.B1.B2-20100001");
			/******************** userinfo **************************/
			newInfo.setUserInfos(loadUserinfoByTicketId(ticketInstId));
			basicInfo.setNewInfo(newInfo);
		}
	}
	public void loadNewInfo(List<BasicInfo> basicInfos){
		Iterator<BasicInfo> it = basicInfos.iterator();
		while(it.hasNext()){
			BasicInfo basicInfo = it.next();
			//获取新增的数据
			InterfaceInfo newInfo = new InterfaceInfo();
			newInfo.setIdcId("A2.B1.B2-20100001");
			newInfo.setIdcName("中国（西部）云计算中心");
			newInfo.setIdcAdd("四川成都双流区物联二路中国西部云计算中心");
			newInfo.setIdcZip("610000");
			newInfo.setCorp("赵大春");
			//设置idcOffer  网络信息安全责任人信息
			newInfo.setIdcOfficer(loadIdcOfficer());
			// emergencyContact 单位应急联系人信息与联系方式
			newInfo.setEmergencyContact(loadEmergencyContact());
			//这里需要通过数据库查询统计数据中心数量:默认肯定是一个
			newInfo.setHouseCount(1l);
			//填写数据中心的基本信息节点
			newInfo.setAddHouseInfos(loadHouseInfo());
			/******************** userinfo **************************/
			newInfo.setUserInfos(loadUserinfo());
			basicInfo.setNewInfo(newInfo);
		}
	}

	public List<UserInfo> loadUserinfoByTicketForDelete(Long ticketInstId){
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<Map<String,Object>> list = idcLocationMapper.loadUserInfos(ticketInstId);
		for(int i = 0 ;i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			UserInfo userInfo = new UserInfo();
			userInfo.setAid(String.valueOf(map.get("AID")));
			userInfo.setUserId(Long.valueOf(String.valueOf(map.get("ID"))));

			userInfo.setServiceInfos(loadServiceInfosForDelete(userInfo,ticketInstId));
			userInfos.add(userInfo);
		}
		return userInfos;
	}

	/*
		作为修改的方法
	 */
	public List<UserInfo> loadUserinfoByTicketToUpdate(Long ticketInstId){
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<Map<String,Object>> list = idcLocationMapper.loadUserInfos(ticketInstId);
		for(int i = 0 ;i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			UserInfo userInfo = new UserInfo();
			userInfo.setAid(String.valueOf(map.get("AID")));
			userInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			userInfo.setNature(Integer.valueOf(String.valueOf(map.get("NATURE"))));
			Info info = new Info();

			info.setUnitName(String.valueOf(map.get("UNITNAME")));
			info.setUnitNature(Integer.valueOf(String.valueOf(map.get("UNITNATURE"))));
			info.setIdType(Integer.valueOf(String.valueOf(map.get("IDTYPE"))));
			info.setIdNumber(String.valueOf(map.get("IDNUMBER")));
			info.setAdd(String.valueOf(map.get("ADDR")));
			info.setZipCode(String.valueOf(map.get("ZIPCODE")));
			info.setRegisterTime(String.valueOf(map.get("CREATETIME")));
			info.setIdcOfficer(loadIdcOfficerForUser());
			//获取该用户下的所有的服务信息
			info.setHousesHoldInfos(loadHousesHoldInfosForInfo(ticketInstId));
			//info.setServiceInfos(loadServiceInfosForHouse(userInfo,ticketInstId));
			//此时提供一般用户信息
			userInfo.setInfo(info);

			userInfos.add(userInfo);
		}
		return userInfos;
	}


	public List<UserInfo> loadUserinfoForDelete(){
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<Map<String,Object>> list = idcLocationMapper.loadUserInfos(null);
		for(int i = 0 ;i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(Long.valueOf(String.valueOf(map.get("ID"))));

			//获取该用户下的所有的服务信息
			//某个客户 可能出现多个服务
			userInfo.setServiceInfos(loadServiceInfoForDelete(userInfo));
			userInfos.add(userInfo);
		}
		return userInfos;
	}

	/*
		该用户和该用户的所有的服务
	 */
	public List<UserInfo> loadUserinfoByTicketId(Long ticketInstId){
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<Map<String,Object>> list = idcLocationMapper.loadUserInfosByTicketId(ticketInstId);
		for(int i = 0 ;i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			UserInfo userInfo = new UserInfo();
			userInfo.setAid(String.valueOf(map.get("AID")));
			userInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			userInfo.setNature(Integer.valueOf(String.valueOf(map.get("NATURE"))));
			Info info = new Info();

			info.setUnitName(String.valueOf(map.get("UNITNAME")));
			info.setUnitNature(Integer.valueOf(String.valueOf(map.get("UNITNATURE"))));
			info.setIdType(Integer.valueOf(String.valueOf(map.get("IDTYPE"))));
			info.setIdNumber(String.valueOf(map.get("IDNUMBER")));
			info.setAdd(String.valueOf(map.get("ADDR")));
			info.setZipCode(String.valueOf(map.get("ZIPCODE")));
			info.setRegisterTime(String.valueOf(map.get("CREATETIME")));
			//officer
			IdcOfficer officer = new IdcOfficer();
			officer.setName(String.valueOf(map.get("NAME")));
			officer.setIdType(Integer.valueOf(String.valueOf(map.get("CONTACT_IDTYPE"))));
			officer.setId(String.valueOf(map.get("CONTACT_ID")));
			officer.setTel(String.valueOf(map.get("TEL")));
			officer.setMobile(String.valueOf(map.get("MOBILE")));
			officer.setEmail(String.valueOf(map.get("EMAIL")));
			info.setIdcOfficer(officer);
			//提供互联网用户
			info.setServiceInfos(loadServiceInfosForAdd(userInfo,ticketInstId));
			userInfo.setInfo(info);
			//获取该用户下的所有的服务信息
			//某个客户 可能出现多个服务
			userInfos.add(userInfo);
		}
		return userInfos;
	}
	public List<UserInfo> loadUserinfo(){
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<Map<String,Object>> list = idcLocationMapper.loadUserInfos(null);
		for(int i = 0 ;i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			UserInfo userInfo = new UserInfo();
			userInfo.setAid(String.valueOf(map.get("AID")));
			userInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			userInfo.setNature(Integer.valueOf(String.valueOf(map.get("NATURE"))));
			Info info = new Info();

			info.setUnitName(String.valueOf(map.get("UNITNAME")));
			info.setUnitNature(Integer.valueOf(String.valueOf(map.get("UNITNATURE"))));
			info.setIdType(Integer.valueOf(String.valueOf(map.get("IDTYPE"))));
			info.setIdNumber(String.valueOf(map.get("IDNUMBER")));
			info.setAdd(String.valueOf(map.get("ADDR")));
			info.setZipCode(String.valueOf(map.get("ZIPCODE")));
			info.setRegisterTime(String.valueOf(map.get("CREATETIME")));
			//officer
			IdcOfficer officer = new IdcOfficer();
			officer.setName(String.valueOf(map.get("NAME")));
			officer.setIdType(Integer.valueOf(String.valueOf(map.get("CONTACT_IDTYPE"))));
			officer.setId(String.valueOf(map.get("CONTACT_ID")));
			officer.setTel(String.valueOf(map.get("TEL")));
			officer.setMobile(String.valueOf(map.get("MOBILE")));
			officer.setEmail(String.valueOf(map.get("EMAIL")));
			info.setIdcOfficer(officer);
			userInfo.setInfo(info);
			//获取该用户下的所有的服务信息
			//某个客户 可能出现多个服务
			userInfo.setServiceInfos(loadServiceInfos(userInfo,null));
			userInfos.add(userInfo);
		}
		return userInfos;
	}
	public List<ServiceInfo> loadServiceInfoForDelete(UserInfo userInfo){
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),null);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setAid(Long.valueOf(String.valueOf(map.get("SERVICE_AID"))));

			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));

			serviceInfo.setDomainId(Long.valueOf(String.valueOf(map.get("ICPDOMAIN_ID"))));

			List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
			for(int h = 0; h < idcLocations.size(); h++){
				Map<String,Object> idcLocations_map = idcLocations.get(i);
				serviceInfo.setHhId(Long.valueOf(String.valueOf(idcLocations_map.get("HHID"))));//只有一个
			}
			serviceInfos.add(serviceInfo);
		}
		return serviceInfos;
	}

	public List<ServiceInfo> loadServiceInfosForDelete(UserInfo userInfo,Long ticketInstId){
		ticketInstId = null;
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),ticketInstId);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));
			//客户对应的所有域名信息
			String icpdomain_id = String.valueOf(map.get("ICPDOMAIN_ID"));
			if(icpdomain_id != null && !"".equals(icpdomain_id)){
				serviceInfo.setDomainId(Long.valueOf(icpdomain_id));
			}
			List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
			for(int h = 0; h < idcLocations.size(); h++){
				Map<String,Object> idcLocations_map = idcLocations.get(h);
				serviceInfo.setHhId(Long.valueOf(String.valueOf(idcLocations_map.get("HHID"))));
			}
			serviceInfos.add(serviceInfo);
		}
		return serviceInfos;
	}

	public List<ServiceInfo> loadServiceInfosByTicketId(UserInfo userInfo,Long ticketInstId){
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),ticketInstId);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setAid(Long.valueOf(String.valueOf(map.get("SERVICE_AID"))));

			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));
			String serviceContent = String.valueOf(map.get("SERVICECONTENT"));
			String[] serviceContents = serviceContent.split(",");
			List<Integer> serviceContentInt = new ArrayList<Integer>();
			for(int s = 0 ; s < serviceContents.length ; s++){
				String sc = serviceContents[s];
				if(sc != null && !"".equals(sc) && !"null".equalsIgnoreCase(sc) && sc.trim().length() != 0){
					System.out.println(sc);
					serviceContentInt.add(Integer.valueOf(sc));
				}
			}
			serviceInfo.setServiceContents(serviceContentInt);
			serviceInfo.setRegType(Integer.valueOf(String.valueOf(map.get("REGTYPE"))));
			serviceInfo.setRegId(String.valueOf(map.get("REGID")));
			serviceInfo.setSetMode(String.valueOf(map.get("SETMODE")));
			serviceInfo.setBusiness(String.valueOf(map.get("BUSINESS")));
			//获取相应的域名信息
			Domain domain = new Domain();
			domain.setId(Long.valueOf(String.valueOf(map.get("ICPDOMAIN_ID"))));
			domain.setName(String.valueOf(map.get("ICPDOMAIN")));
			serviceInfo.setDomain(domain);
			//该服务获取对应的ip地址或IP地址段:
			serviceInfo.setHousesHoldInfos(loadHousesHoldInfos(serviceInfo,ticketInstId));
			serviceInfos.add(serviceInfo);
		}
		return serviceInfos;
	}

	public List<ServiceInfo> loadServiceInfosForHouse(UserInfo userInfo,Long ticketInstId){
		ticketInstId = null;
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),ticketInstId);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setAid(Long.valueOf(String.valueOf(map.get("SERVICE_AID"))));

			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));
			String serviceContent = String.valueOf(map.get("SERVICECONTENT"));
			String[] serviceContents = serviceContent.split(",");
			List<Integer> serviceContentInt = new ArrayList<Integer>();
			for(int s = 0 ; s < serviceContents.length ; s++){
				String sc = serviceContents[s];
				if(sc != null && !"".equals(sc) && !"null".equalsIgnoreCase(sc) && sc.trim().length() != 0){
					//System.out.println(sc);
					serviceContentInt.add(Integer.valueOf(sc));
				}
			}
			serviceInfo.setServiceContents(serviceContentInt);
			if(map.get("REGTYPE") !=null && !"null".equals(String.valueOf(map.get("REGTYPE")))){
				serviceInfo.setRegType(Integer.valueOf(String.valueOf(map.get("REGTYPE"))));
				serviceInfo.setRegId(String.valueOf(map.get("REGID")));
				serviceInfo.setSetMode(String.valueOf(map.get("SETMODE")));
				serviceInfo.setBusiness(String.valueOf(map.get("BUSINESS")));
				//获取相应的域名信息
				Domain domain = new Domain();
				domain.setId(Long.valueOf(String.valueOf(map.get("ICPDOMAIN_ID"))));
				domain.setName(String.valueOf(map.get("ICPDOMAIN")));
				serviceInfo.setDomain(domain);
				//该服务获取对应的ip地址或IP地址段:
				serviceInfo.setHousesHoldInfos(loadHousesHoldInfosForHouse(serviceInfo,ticketInstId));
				serviceInfos.add(serviceInfo);
			}
		}
		return serviceInfos;
	}

	public List<ServiceInfo> loadServiceInfosForAdd(UserInfo userInfo,Long ticketInstId){
		ticketInstId = null;
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),ticketInstId);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setAid(Long.valueOf(String.valueOf(map.get("SERVICE_AID"))));
			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));
			String serviceContent = String.valueOf(map.get("SERVICECONTENT"));
			String[] serviceContents = serviceContent.split(",");
			List<Integer> serviceContentInt = new ArrayList<Integer>();
			for(int s = 0 ; s < serviceContents.length ; s++){
				String sc = serviceContents[s];
				if(sc != null && !"".equals(sc) && !"null".equalsIgnoreCase(sc) && sc.trim().length() != 0){
					//System.out.println(sc);
					serviceContentInt.add(Integer.valueOf(sc));
				}
			}
			serviceInfo.setServiceContents(serviceContentInt);
			if(map.get("REGTYPE") !=null){
				serviceInfo.setRegType(Integer.valueOf(String.valueOf(map.get("REGTYPE"))));
				serviceInfo.setRegId(String.valueOf(map.get("REGID")));
				serviceInfo.setSetMode(String.valueOf(map.get("SETMODE")));
				serviceInfo.setBusiness(String.valueOf(map.get("BUSINESS")));
				//获取相应的域名信息
				Domain domain = new Domain();
				domain.setId(Long.valueOf(String.valueOf(map.get("ICPDOMAIN_ID"))));
				domain.setName(String.valueOf(map.get("ICPDOMAIN")));
				serviceInfo.setDomain(domain);
				//该服务获取对应的ip地址或IP地址段:
				serviceInfo.setHousesHoldInfos(loadHousesHoldInfosForInfo(ticketInstId));
				serviceInfos.add(serviceInfo);
			}
		}
		return serviceInfos;
	}
	public List<ServiceInfo> loadServiceInfos(UserInfo userInfo,Long ticketInstId){
		ticketInstId = null;
		List<Map<String,Object>> serviceInfoMaps =  idcLocationMapper.loadServiceInfos(userInfo.getAid(),ticketInstId);
		List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
		for(int i = 0 ;i < serviceInfoMaps.size(); i++){
			Map<String,Object> map = serviceInfoMaps.get(i);
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setAid(Long.valueOf(String.valueOf(map.get("SERVICE_AID"))));

			serviceInfo.setServiceId(Long.valueOf(String.valueOf(map.get("SERVICEID"))));
			String serviceContent = String.valueOf(map.get("SERVICECONTENT"));
			String[] serviceContents = serviceContent.split(",");
			List<Integer> serviceContentInt = new ArrayList<Integer>();
			for(int s = 0 ; s < serviceContents.length ; s++){
				String sc = serviceContents[s];
				if(sc != null && !"".equals(sc) && !"null".equalsIgnoreCase(sc) && sc.trim().length() != 0){
					//System.out.println(sc);
					serviceContentInt.add(Integer.valueOf(sc));
				}
			}
			serviceInfo.setServiceContents(serviceContentInt);
			if(map.get("REGTYPE") !=null){
				serviceInfo.setRegType(Integer.valueOf(String.valueOf(map.get("REGTYPE"))));
			}

			serviceInfo.setRegId(String.valueOf(map.get("REGID")));
			serviceInfo.setSetMode(String.valueOf(map.get("SETMODE")));
			serviceInfo.setBusiness(String.valueOf(map.get("BUSINESS")));
			//获取相应的域名信息
			Domain domain = new Domain();
			domain.setId(Long.valueOf(String.valueOf(map.get("ICPDOMAIN_ID"))));
			domain.setName(String.valueOf(map.get("ICPDOMAIN")));
			serviceInfo.setDomain(domain);
			//该服务获取对应的ip地址或IP地址段:
			serviceInfo.setHousesHoldInfos(loadHousesHoldInfosForInfo(ticketInstId));
			serviceInfos.add(serviceInfo);
		}
		return serviceInfos;
	}


	public List<HousesHoldInfo> loadHousesHoldInfosForInfo(Long ticketInstId){
		//获取所有的数据中心
		List<HousesHoldInfo> housesHoldInfos = new ArrayList<HousesHoldInfo>();
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		for(int i = 0; i < idcLocations.size(); i++){
			Map<String,Object> map = idcLocations.get(i);
			HousesHoldInfo housesHoldInfo = new HousesHoldInfo();
			housesHoldInfo.setHhId(Long.valueOf(String.valueOf(map.get("HHID"))));
			housesHoldInfo.setHouseId(Long.valueOf(String.valueOf(map.get("HOUSEID"))));
			housesHoldInfo.setBandWidth(1000L);
			//然后配置相应的iptrans
			housesHoldInfo.setIpSegs(loadIpSegFormHouse(ticketInstId));
			housesHoldInfo.setDistributeTime(String.valueOf(map.get("TIME_STAMP")));
			housesHoldInfos.add(housesHoldInfo);
		}
		return housesHoldInfos;
	}

	/*
		数据中心分配的IP
	 */
	public List<IpAttrForHouse> loadIpSegFormHouse(Long ticketInstId){
		List<IpAttrForHouse> list = new ArrayList<IpAttrForHouse>();
		IpAttrForHouse ipSegInfo = new IpAttrForHouse();
		ipSegInfo.setIpId(4432l);
		ipSegInfo.setStartIp("223.87.178.132");
		ipSegInfo.setEndIp("223.87.178.132");
		list.add(ipSegInfo);
		return list;
	}
	public List<HousesHoldInfo> loadHousesHoldInfosForHouse(ServiceInfo serviceInfo,Long ticketInstId){
		//获取所有的数据中心
		List<HousesHoldInfo> housesHoldInfos = new ArrayList<HousesHoldInfo>();
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		for(int i = 0; i < idcLocations.size(); i++){
			Map<String,Object> map = idcLocations.get(i);
			HousesHoldInfo housesHoldInfo = new HousesHoldInfo();
			housesHoldInfo.setHhId(Long.valueOf(String.valueOf(map.get("HHID"))));
			housesHoldInfo.setHouseId(Long.valueOf(String.valueOf(map.get("HOUSEID"))));
			//设置资源分配时间  distributetime
			//从本业务中选择一个机架ID
			List<Map<String,Object>> loadFrameInfoLimitFirst = idcLocationMapper.loadFrameInfoLimitFirst(serviceInfo.getAid(),ticketInstId);
			if(loadFrameInfoLimitFirst != null && !loadFrameInfoLimitFirst.isEmpty()){
				Map<String,Object> frameInfoLimitFirst = loadFrameInfoLimitFirst.get(0);
				housesHoldInfo.setFrameInfoId(String.valueOf(frameInfoLimitFirst.get("ID")));
				housesHoldInfo.setBandWidth(100l);
				//然后配置相应的iptrans
				housesHoldInfo.setIpTrans(loadIpTran(serviceInfo,ticketInstId));
				housesHoldInfo.setDistributeTime(String.valueOf(frameInfoLimitFirst.get("DISTRIBUTETIME")));
			}
			housesHoldInfos.add(housesHoldInfo);
		}
		return housesHoldInfos;
	}
	public List<HousesHoldInfo> loadHousesHoldInfos(ServiceInfo serviceInfo,Long ticketInstId){
		//获取所有的数据中心
		List<HousesHoldInfo> housesHoldInfos = new ArrayList<HousesHoldInfo>();
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		/*for(int i = 0; i < idcLocations.size(); i++ ){
			Map<String,Object> map = idcLocations.get(i);
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
			houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
			List<GatewayInfo> gatewayInfolist = loadGatewayInfoForHouse(houseInfo);

			for(GatewayInfo gatewayInfo : gatewayInfolist){
				houseInfo = new HouseInfo();
				houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
				houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
				houseInfo.setGatewayId(gatewayInfo.getId());
				houseInfo.setIpSegId(gatewayInfo.getGatewayIp());
				housesHoldInfos.add(houseInfo);
			}
		}*/

		for(int i = 0; i < idcLocations.size(); i++){
			Map<String,Object> map = idcLocations.get(i);
			HousesHoldInfo housesHoldInfo = new HousesHoldInfo();
			housesHoldInfo.setHhId(Long.valueOf(String.valueOf(map.get("HHID"))));
			housesHoldInfo.setHouseId(Long.valueOf(String.valueOf(map.get("HOUSEID"))));
			//设置资源分配时间  distributetime

			//从本业务中选择一个机架ID
			List<Map<String,Object>> loadFrameInfoLimitFirst = idcLocationMapper.loadFrameInfoLimitFirst(serviceInfo.getAid(),ticketInstId);
			if(loadFrameInfoLimitFirst != null && !loadFrameInfoLimitFirst.isEmpty()){
				Map<String,Object> frameInfoLimitFirst = loadFrameInfoLimitFirst.get(0);
				housesHoldInfo.setFrameInfoId(String.valueOf(frameInfoLimitFirst.get("ID")));
 				housesHoldInfo.setBandWidth(100l);
 				//然后配置相应的iptrans
				housesHoldInfo.setIpTrans(loadIpTran(serviceInfo,ticketInstId));
				housesHoldInfo.setDistributeTime(String.valueOf(frameInfoLimitFirst.get("DISTRIBUTETIME")));
			}
			housesHoldInfos.add(housesHoldInfo);
		}
		return housesHoldInfos;
	}
	public List<IpTran> loadIpTran(ServiceInfo serviceInfo,Long ticketInstId){
		ticketInstId = null;
		List<Map<String,Object>> ipTranList = idcLocationMapper.loadIpTrans(serviceInfo.getAid(),ticketInstId);
		List<IpTran> ipTrans = new ArrayList<IpTran>();
		for(int i = 0 ;i < ipTranList.size(); i++){
			Map<String,Object> map = ipTranList.get(i);
			IpTran ipTran = new IpTran();
			IpAttr internetIp = new IpAttr();
			internetIp.setStartIp(String.valueOf(map.get("STARTIP")));
			internetIp.setEndIp(String.valueOf(map.get("ENDIP")));
			ipTran.setInternetIp(internetIp);
			ipTrans.add(ipTran);
		}
		return ipTrans;
	}
	public List<HouseInfo> loadHouseInfoByTicketToUpdate_House(Long ticketInstId){
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		for(int i = 0; i < idcLocations.size(); i++ ){
			Map<String,Object> map = idcLocations.get(i);
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
			houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
			houseInfo.setHouseName(String.valueOf(map.get("NAME")));
			houseInfo.setHouseType(String.valueOf(map.get("NATURE")));
			houseInfo.setHouseProvince(Long.valueOf(String.valueOf(map.get("HOUSEPROVINCE"))));
			houseInfo.setHouseCity(Long.valueOf(String.valueOf(map.get("HOUSECITY"))));
			houseInfo.setHouseCounty(Long.valueOf(String.valueOf(map.get("HOUSECOUNTY"))));
			houseInfo.setHouseAdd(String.valueOf(map.get("ADDRESS")));
			houseInfo.setHouseZip(String.valueOf(map.get("ZIPCODE")));
			houseInfo.setIdcOfficer(loadIdcOfficerForHouse());
			//这里需要设置相应的联系人和链路信息
			houseInfo.setGatewayInfos(loadGatewayInfoForHouse(houseInfo));
			houseInfo.setIpSegInfos(loadIpSegInfoForHouse(houseInfo));
			//然后获取所有的机架信息
			houseInfo.setFrameInfos(loadFrameInfoForHouse(houseInfo,ticketInstId));

			list.add(houseInfo);
		}
		return list;

	}
	public List<HouseInfo> loadHouseInfoByTicketToUpdate(Long ticketInstId){
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		for(int i = 0; i < idcLocations.size(); i++ ){
			Map<String,Object> map = idcLocations.get(i);
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
			houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
			houseInfo.setHouseName(String.valueOf(map.get("NAME")));
			houseInfo.setHouseType(String.valueOf(map.get("NATURE")));
			houseInfo.setHouseProvince(Long.valueOf(String.valueOf(map.get("HOUSEPROVINCE"))));
			houseInfo.setHouseCity(Long.valueOf(String.valueOf(map.get("HOUSECITY"))));
			houseInfo.setHouseCounty(Long.valueOf(String.valueOf(map.get("HOUSECOUNTY"))));
			houseInfo.setHouseAdd(String.valueOf(map.get("ADDRESS")));
			houseInfo.setHouseZip(String.valueOf(map.get("ZIPCODE")));
			//这里需要设置相应的联系人和链路信息
			houseInfo.setGatewayInfos(loadGatewayInfo(houseInfo));
			houseInfo.setIpSegInfos(loadIpSegInfo(houseInfo));
			//然后获取所有的机架信息
			houseInfo.setFrameInfos(loadFrameInfo(houseInfo,ticketInstId));

			list.add(houseInfo);
		}
		return list;

	}

	public List<HouseInfo> loadHouseInfoForDelete(Long ticketInstId){
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		if(false){
			//这个是方式1
			/*for(int i = 0; i < idcLocations.size(); i++ ){
				Map<String,Object> map = idcLocations.get(i);
				HouseInfo houseInfo = new HouseInfo();
				houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
				houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
				houseInfo.setGatewayInfos(loadGatewayInfoForHouse(houseInfo));
				houseInfo.setIpSegInfos(loadIpSegInfoForHouse(houseInfo));
				list.add(houseInfo);
			}*/
		}else{
			//按照文档方式处理
			for(int i = 0; i < idcLocations.size(); i++ ){
				Map<String,Object> map = idcLocations.get(i);
				HouseInfo houseInfo = new HouseInfo();
				houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
				houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
				List<GatewayInfo> gatewayInfolist = loadGatewayInfoForHouse(houseInfo);

				for(GatewayInfo gatewayInfo : gatewayInfolist){
					houseInfo = new HouseInfo();
					houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
					houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
					houseInfo.setGatewayId(gatewayInfo.getId());
					houseInfo.setIpSegId(gatewayInfo.getGatewayIp());
					list.add(houseInfo);
				}
			}
		}

		return list;

	}
	public List<HouseInfo> loadHouseInfoByTicketToAdd(Long ticketInstId){
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		for(int i = 0; i < idcLocations.size(); i++ ){
			Map<String,Object> map = idcLocations.get(i);
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
			houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
			houseInfo.setHouseName(String.valueOf(map.get("NAME")));
			houseInfo.setHouseType(String.valueOf(map.get("NATURE")));
			houseInfo.setHouseProvince(Long.valueOf(String.valueOf(map.get("HOUSEPROVINCE"))));
			houseInfo.setHouseCity(Long.valueOf(String.valueOf(map.get("HOUSECITY"))));
			houseInfo.setHouseCounty(Long.valueOf(String.valueOf(map.get("HOUSECOUNTY"))));
			houseInfo.setHouseAdd(String.valueOf(map.get("ADDRESS")));
			houseInfo.setHouseZip(String.valueOf(map.get("ZIPCODE")));
			//这里需要设置相应的联系人和链路信息
			houseInfo.setIdcOfficer(loadIdcOfficer());
			houseInfo.setGatewayInfos(loadGatewayInfo(houseInfo));
			houseInfo.setIpSegInfos(loadIpSegInfo(houseInfo));
			//然后获取所有的机架信息
			houseInfo.setFrameInfos(loadFrameInfoForHouse(houseInfo,ticketInstId));

			list.add(houseInfo);
		}
		return list;

	}
	public List<HouseInfo> loadHouseInfo(){
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		for(int i = 0; i < idcLocations.size(); i++ ){
			Map<String,Object> map = idcLocations.get(i);
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setAid(Long.valueOf(String.valueOf(map.get("ID"))));
			houseInfo.setHouseId(String.valueOf(map.get("HOUSEID")));
			houseInfo.setHouseName(String.valueOf(map.get("NAME")));
			houseInfo.setHouseType(String.valueOf(map.get("NATURE")));
			houseInfo.setHouseProvince(Long.valueOf(String.valueOf(map.get("HOUSEPROVINCE"))));
			houseInfo.setHouseCity(Long.valueOf(String.valueOf(map.get("HOUSECITY"))));
			houseInfo.setHouseCounty(Long.valueOf(String.valueOf(map.get("HOUSECOUNTY"))));
			houseInfo.setHouseAdd(String.valueOf(map.get("ADDRESS")));
			houseInfo.setHouseZip(String.valueOf(map.get("ZIPCODE")));
			//这里需要设置相应的联系人和链路信息
			houseInfo.setIdcOfficer(loadIdcOfficer());
			houseInfo.setGatewayInfos(loadGatewayInfo(houseInfo));
			houseInfo.setIpSegInfos(loadIpSegInfo(houseInfo));
			//然后获取所有的机架信息
			houseInfo.setFrameInfos(loadFrameInfo(houseInfo,null));

			list.add(houseInfo);
		}
		return list;

	}

	/**获取所有已经占用了的机架信息**/
	public  List<FrameInfo> loadFrameInfoByTicketId(HouseInfo houseInfo,Long ticketInstId){
		List<Map<String,Object>> loadFrameInfos = new ArrayList<Map<String,Object>>();
		//所有的机架情况
		loadFrameInfos = idcLocationMapper.loadFrameByHouseIdAndTicketId(houseInfo.getAid(),ticketInstId);
		List<FrameInfo> frameInfos = new ArrayList<FrameInfo>();
		for(int i = 0 ; i< loadFrameInfos.size(); i++){
			Map<String,Object> map = loadFrameInfos.get(i);
			FrameInfo frameInfo = new FrameInfo();
			frameInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			frameInfo.setUseType(String.valueOf(map.get("USETYPE")));
			frameInfo.setDistribution(String.valueOf(map.get("DISTRIBUTION")));
			frameInfo.setOccupancy(String.valueOf(map.get("OCCUPANCY")));
			frameInfo.setFrameName(String.valueOf(map.get("FRAMENAME")));
			frameInfos.add(frameInfo);
		}
		return frameInfos;
	}
	/**获取所有已经占用了的机架信息**/
	public  List<FrameInfo> loadFrameInfoForHouse(HouseInfo houseInfo,Long ticketInstId){
		List<Map<String,Object>> loadFrameInfos = new ArrayList<Map<String,Object>>();
		ticketInstId = null;
		if(ticketInstId != null && ticketInstId != 0l){
			loadFrameInfos = idcLocationMapper.loadFrameByHouseIdAndTicketId(houseInfo.getAid(),ticketInstId);
		}else{
			loadFrameInfos = idcLocationMapper.loadFrameByHouseId(houseInfo.getAid());
		}

		List<FrameInfo> frameInfos = new ArrayList<FrameInfo>();
		for(int i = 0 ; i< loadFrameInfos.size(); i++){
			Map<String,Object> map = loadFrameInfos.get(i);
			FrameInfo frameInfo = new FrameInfo();
			frameInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			frameInfo.setUseType(String.valueOf(map.get("USETYPE")));
			frameInfo.setDistribution(String.valueOf(map.get("DISTRIBUTION")));
			frameInfo.setOccupancy(String.valueOf(map.get("OCCUPANCY")));
			frameInfo.setFrameName(String.valueOf(map.get("FRAMENAME")));
			frameInfos.add(frameInfo);
		}
		return frameInfos;
	}
	/**获取所有已经占用了的机架信息**/
	public  List<FrameInfo> loadFrameInfo(HouseInfo houseInfo,Long ticketInstId){
		List<Map<String,Object>> loadFrameInfos = new ArrayList<Map<String,Object>>();
		ticketInstId = null;
		if(ticketInstId != null && ticketInstId != 0l){
			loadFrameInfos = idcLocationMapper.loadFrameByHouseIdAndTicketId(houseInfo.getAid(),ticketInstId);
		}else{
			loadFrameInfos = idcLocationMapper.loadFrameByHouseId(houseInfo.getAid());
		}

		List<FrameInfo> frameInfos = new ArrayList<FrameInfo>();
		for(int i = 0 ; i< loadFrameInfos.size(); i++){
			Map<String,Object> map = loadFrameInfos.get(i);
			FrameInfo frameInfo = new FrameInfo();
			frameInfo.setId(Long.valueOf(String.valueOf(map.get("ID"))));
			frameInfo.setUseType(String.valueOf(map.get("USETYPE")));
			frameInfo.setDistribution(String.valueOf(map.get("DISTRIBUTION")));
			frameInfo.setOccupancy(String.valueOf(map.get("OCCUPANCY")));
			frameInfo.setFrameName(String.valueOf(map.get("FRAMENAME")));
			frameInfos.add(frameInfo);
		}
		return frameInfos;
	}

	public List<IpSegInfo> loadIpSegInfoForHouse(HouseInfo houseInfo){
		List<IpSegInfo> ipSegInfos = new ArrayList<IpSegInfo>();
		IpSegInfo ipSegInfo1 = new IpSegInfo();
		ipSegInfo1.setId(5000000001l+houseInfo.getAid()+300);
		ipSegInfo1.setStartIp("183.223.116.0");
		ipSegInfo1.setEndIp("183.223.116.255");
		ipSegInfo1.setType(0);
		ipSegInfo1.setUser("眉山市东坡区安全生产监督管理局");
		ipSegInfo1.setIdNumber("66275920-0");
		ipSegInfo1.setUseTime("2017-06-25");
		ipSegInfo1.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo1.setAllocationUnit("中国移动通信集团四川有限公司");

		IpSegInfo ipSegInfo2 = new IpSegInfo();
		ipSegInfo2.setId(5000000001l+houseInfo.getAid()+301);
		ipSegInfo2.setStartIp("183.223.117.0");
		ipSegInfo2.setEndIp("183.223.117.0");
		ipSegInfo2.setType(999);
		ipSegInfo1.setUser("眉山市彭山区工商行政管理和质量技术监督局");
		ipSegInfo1.setIdNumber("115117230085943768");
		ipSegInfo2.setUseTime("2017-06-25");
		ipSegInfo2.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo2.setAllocationUnit("中国移动通信集团四川有限公司");

		IpSegInfo ipSegInfo3 = new IpSegInfo();
		ipSegInfo3.setId(5000000001l+houseInfo.getAid()+302);
		ipSegInfo3.setStartIp("183.223.118.0");
		ipSegInfo3.setEndIp("183.223.118.0");
		ipSegInfo3.setType(999);
		ipSegInfo1.setUser("中共眉山市彭山区委政法委员会");
		ipSegInfo1.setIdNumber("11511723008594069F");
		ipSegInfo3.setUseTime("2017-06-25");
		ipSegInfo3.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo3.setAllocationUnit("中国移动通信集团四川有限公司");


		IpSegInfo ipSegInfo4 = new IpSegInfo();
		ipSegInfo4.setId(5000000001l+houseInfo.getAid()+4);
		ipSegInfo4.setStartIp("183.223.119.0");
		ipSegInfo4.setEndIp("183.223.119.0");
		ipSegInfo4.setType(1);
		ipSegInfo1.setUser("眉山市万方电子有限公司");
		ipSegInfo1.setIdNumber("91511402MA62J1LL1G");
		ipSegInfo4.setUseTime("2017-06-25");
		ipSegInfo4.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo4.setAllocationUnit("中国移动通信集团四川有限公司");
		ipSegInfos.add(ipSegInfo1);
		ipSegInfos.add(ipSegInfo2);
		ipSegInfos.add(ipSegInfo3);
		ipSegInfos.add(ipSegInfo4);
		return ipSegInfos;
	}
	public List<IpSegInfo> loadIpSegInfo(HouseInfo houseInfo){
		List<IpSegInfo> ipSegInfos = new ArrayList<IpSegInfo>();
		IpSegInfo ipSegInfo1 = new IpSegInfo();
		ipSegInfo1.setId(5000000001l+houseInfo.getAid()+1);
		ipSegInfo1.setStartIp("183.223.116.0");
		ipSegInfo1.setEndIp("183.223.116.255");
		ipSegInfo1.setType(0);
		ipSegInfo1.setUseTime("2017-06-25");
		ipSegInfo1.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo1.setAllocationUnit("中国移动通信集团四川有限公司");

		IpSegInfo ipSegInfo2 = new IpSegInfo();
		ipSegInfo2.setId(5000000001l+houseInfo.getAid()+2);
		ipSegInfo2.setStartIp("183.223.117.0");
		ipSegInfo2.setEndIp("183.223.117.0");
		ipSegInfo2.setType(0);
		ipSegInfo2.setUseTime("2017-06-25");
		ipSegInfo2.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo2.setAllocationUnit("中国移动通信集团四川有限公司");

		IpSegInfo ipSegInfo3 = new IpSegInfo();
		ipSegInfo3.setId(5000000001l+houseInfo.getAid()+3);
		ipSegInfo3.setStartIp("183.223.118.0");
		ipSegInfo3.setEndIp("183.223.118.0");
		ipSegInfo3.setType(0);
		ipSegInfo3.setUseTime("2017-06-25");
		ipSegInfo3.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo3.setAllocationUnit("中国移动通信集团四川有限公司");


		IpSegInfo ipSegInfo4 = new IpSegInfo();
		ipSegInfo4.setId(5000000001l+houseInfo.getAid()+4);
		ipSegInfo4.setStartIp("183.223.119.0");
		ipSegInfo4.setEndIp("183.223.119.0");
		ipSegInfo4.setType(0);
		ipSegInfo4.setUseTime("2017-06-25");
		ipSegInfo4.setSourceUnit("中国移动通信集团四川有限公司");
		ipSegInfo4.setAllocationUnit("中国移动通信集团四川有限公司");
		ipSegInfos.add(ipSegInfo1);
		ipSegInfos.add(ipSegInfo2);
		ipSegInfos.add(ipSegInfo3);
		ipSegInfos.add(ipSegInfo4);
		return ipSegInfos;
	}

	public List<GatewayInfo> loadGatewayInfoForHouse(HouseInfo houseInfo){
		List<GatewayInfo> gatewayInfos = new ArrayList<GatewayInfo>();

		GatewayInfo gatewayInfo1 = new GatewayInfo();
		gatewayInfo1.setId(5000000000l+houseInfo.getAid()+150);
		gatewayInfo1.setBandWidth(800000l);
		gatewayInfo1.setLinkType(3);
		gatewayInfo1.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo1.setGatewayIp("183.223.118.137");

		GatewayInfo gatewayInfo2 = new GatewayInfo();
		gatewayInfo2.setId(5000000000l+houseInfo.getAid()+151);
		gatewayInfo2.setBandWidth(800000l);
		gatewayInfo2.setLinkType(3);
		gatewayInfo2.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo2.setGatewayIp("183.223.118.141");

		GatewayInfo gatewayInfo3 = new GatewayInfo();
		gatewayInfo3.setId(5000000000l+houseInfo.getAid()+152);
		gatewayInfo3.setBandWidth(800000l);
		gatewayInfo3.setLinkType(3);
		gatewayInfo3.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo3.setGatewayIp("221.183.50.57");
		gatewayInfos.add(gatewayInfo1);
		gatewayInfos.add(gatewayInfo2);
		gatewayInfos.add(gatewayInfo3);
		return gatewayInfos;
	}
	public List<GatewayInfo> loadGatewayInfo(HouseInfo houseInfo){
		List<GatewayInfo> gatewayInfos = new ArrayList<GatewayInfo>();

		GatewayInfo gatewayInfo1 = new GatewayInfo();
		gatewayInfo1.setId(5000000000l+houseInfo.getAid()+1);
		gatewayInfo1.setBandWidth(800000l);
		gatewayInfo1.setLinkType(3);
		gatewayInfo1.setAccessUnit("中国移动通信集团四川分公司");
		gatewayInfo1.setGatewayIp("183.223.118.137");

		GatewayInfo gatewayInfo2 = new GatewayInfo();
		gatewayInfo2.setId(5000000000l+houseInfo.getAid()+2);
		gatewayInfo2.setBandWidth(800000l);
		gatewayInfo2.setLinkType(3);
		gatewayInfo2.setAccessUnit("中国移动通信集团四川分公司");
		gatewayInfo2.setGatewayIp("183.223.118.141");

		GatewayInfo gatewayInfo3 = new GatewayInfo();
		gatewayInfo3.setId(5000000000l+houseInfo.getAid()+3);
		gatewayInfo3.setBandWidth(800000l);
		gatewayInfo3.setLinkType(3);
		gatewayInfo3.setAccessUnit("中国移动通信集团四川分公司");
		gatewayInfo3.setGatewayIp("221.183.50.57");
		gatewayInfos.add(gatewayInfo1);
		gatewayInfos.add(gatewayInfo2);
		gatewayInfos.add(gatewayInfo3);
		return gatewayInfos;
	}
	public IdcOfficer loadEmergencyContact(){
		IdcOfficer idcOfficer = new IdcOfficer();
		idcOfficer.setName("黄晨炜");
		idcOfficer.setIdType(2);
		idcOfficer.setId("510105198602123010");
		idcOfficer.setTel("0000-0000000");
		idcOfficer.setMobile("13730842162");
		idcOfficer.setEmail("13730842162@139.com");
		return idcOfficer;
	}

	public IdcOfficer loadIdcOfficerForHouse(){
		IdcOfficer idcOfficer = new IdcOfficer();
		idcOfficer.setName("黄晨炜");
		idcOfficer.setIdType(2);
		idcOfficer.setId("510105198602123010");
		idcOfficer.setTel("0000-0000000");
		idcOfficer.setMobile("13730842162");
		idcOfficer.setEmail("13730842162@139.com");
		return idcOfficer;
	}
	//设置 网络信息安全责任人信息
	public IdcOfficer loadIdcOfficer(){
		IdcOfficer idcOfficer = new IdcOfficer();
		idcOfficer.setName("郭智");
		idcOfficer.setIdType(2);
		idcOfficer.setId("210302198709063020");
		idcOfficer.setTel("0000-0000000");
		idcOfficer.setMobile("13709016370");
		idcOfficer.setEmail("13709016370@139.com");
		return idcOfficer;
	}
	public IdcOfficer loadIdcOfficerForUser(){
		IdcOfficer idcOfficer = new IdcOfficer();
		idcOfficer.setName("赵明峰");
		idcOfficer.setIdType(2);
		idcOfficer.setId("511321198104036119");
		idcOfficer.setTel("0000-0000000");
		idcOfficer.setMobile("13628063916");
		idcOfficer.setEmail("13628063916@139.com");
		return idcOfficer;
	}
	public List<BasicInfo> loadBasicInfos(){
		//获取数据中心数据
		List<BasicInfo> basicInfos = new ArrayList<BasicInfo>();
		List<Map<String,Object>> idcLocations = idcLocationMapper.loadLocations();
		for(int i = 0 ;i < idcLocations.size(); i++){
			Map<String,Object> map = idcLocations.get(i);//上报时间就是当前时间
			BasicInfo basicInfo = new BasicInfo();
			basicInfo.setProvID(String.valueOf(map.get("PROVID")));
			basicInfo.setTimeStamp(String.valueOf(map.get("TIME_STAMP")));
			basicInfos.add(basicInfo);
		}
		return basicInfos;
	}

	@Override
	public Long createBasicInfo(BasicInfo basicInfo) throws Exception{
		return mapper.createBasicInfo(basicInfo);
	}

	@Override
	public Long createNewInfo(InterfaceInfo newInfo) throws Exception {
		return mapper.createNewInfo(newInfo);
	}
	@Override
	public Long createHouseInfo(HouseInfo houseInfo) throws Exception{
		return mapper.createHouseInfo(houseInfo);
	}
	@Override
	public Long createGatewayInfo(GatewayInfo gatewayInfo) throws Exception{
		return mapper.createGatewayInfo(gatewayInfo);
	}

	@Override
	public Long createUserInfo(UserInfo userInfo) throws Exception {
		return mapper.createUserInfo(userInfo);
	}

	@Override
	public Long createInfo(Info info) throws Exception {
		return mapper.createInfo(info);
	}
	@Override
	public Long createServiceInfo(ServiceInfo serviceInfo) throws Exception {
		return mapper.createServiceInfo(serviceInfo);
	}


	@Override
	public Long createIpSegInfo(IpSegInfo ipSegInfo) throws Exception{
		return mapper.createIpSegInfo(ipSegInfo);
	}

	@Override
	public Long createHousesHoldInfo(HousesHoldInfo housesHoldInfo) throws Exception {
		return null;
	}

	@Override
	public void createFrameInfo(FrameInfo frameInfo) throws Exception {
		mapper.createFrameInfo(frameInfo);
	}
	@Override
	public Long createDomain(Domain domain) throws Exception{
		return mapper.createDomain(domain);
	}
	@Override
	public Long getHouseInfoIdByTicketInstId(Long ticketInstId) {
		return mapper.getHouseInfoIdByTicketInstId(ticketInstId);
	}



	@Override
	public Boolean getBaseParamIsAsset(Long ticketInstId){
		return mapper.getBaseParamIsAsset(ticketInstId);
	}


	/**
	 * 创建的情况
	 * @param jsonStr
	 * @throws Exception
	 */
	@Override
	public void createInterfaceData(String jsonStr) throws Exception {
		if(jsonStr != null && !"".equals(jsonStr)) {
			//将JSON字符串转换成jsonArray
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			for(int i = 0 ;i < jsonArray.size(); i++){
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				//首先填写默认的配置文件
				/*
				* 新增机架情况
				* */

			}
		}
	}
}
