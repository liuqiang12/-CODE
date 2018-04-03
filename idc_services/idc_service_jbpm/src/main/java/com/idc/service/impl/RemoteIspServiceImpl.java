package com.idc.service.impl;

import com.idc.listener.PageParser;
import com.idc.mapper.IdcIspInfoMapper;
import com.idc.model.*;
import com.idc.service.FTPService;
import com.idc.service.RemoteIspService;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.DevContext;
import utils.typeHelper.DateHelper;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("remoteIspService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class RemoteIspServiceImpl implements RemoteIspService {
	private Logger logger = LoggerFactory.getLogger(RemoteIspServiceImpl.class);
	@Autowired
	private IdcIspInfoMapper idcIspInfoMapper;
	@Autowired
	private FTPService ftpService;
	public void getOtherService(LocalIspCustomer other_customer,String serviceIds){
		String[] service_ids = serviceIds.split(",");
		String[] ticket_ids = other_customer.getTicketIds().split(",");
		List<LocalIspHousehold> households = new ArrayList<LocalIspHousehold>();
		for(int i = 0 ;i < service_ids.length; i++){
			String serviceId = service_ids[i];
			String ticketId = ticket_ids[i];
			/*通过功能ID获取相应服务*/
			Integer wwwRackCount = idcIspInfoMapper.getCountRack(ticketId);
			//idcIspInfoMapper.callIpSection(ticketId);
			Integer wwwIPCount = idcIspInfoMapper.getCountIP(ticketId);
			/* 调用IP信息目的是获取相应的ip段落 */
			if(wwwIPCount != null && wwwIPCount != 0 ){
				/*然后获取所有的服务*/
				List<LocalIspServer> ispServers =  idcIspInfoMapper.getServerIspByServerId(serviceId);//通过服务ID获取相关服务数据:前提先查询机房数量和IP数量
				/*** 循环服务   获取 LocalIspHousehold***/
				Iterator<LocalIspServer> server_it = ispServers.iterator();

				while(server_it.hasNext()){
					LocalIspServer localIspServer = server_it.next();
					//拼装housesHoldInfo信息
					List<LocalIspHousehold> households_tmp = idcIspInfoMapper.getHouseInfoByTicketId(ticketId);
					if(households_tmp != null && !households_tmp.isEmpty()){
						for(int t = 0;t < households_tmp.size(); t++){
							households.add(households_tmp.get(t));
						}
					}
				}
			}
		}
		//目前将服务的所有 households追加出来。。。。。。
		//服务开通时间怎么处理........ 其他用户[目前设置当前时间] ..........
		if(households != null && !households.isEmpty()){
			other_customer.setLocalIspHouseholdList(households);
		}
	}
	public void getWWWService(LocalIspCustomer www_customer,String serviceIds){
		String[] service_ids = serviceIds.split(",");
		String[] ticket_ids = www_customer.getTicketIds().split(",");
		List<LocalIspServer> ispServers = new ArrayList<LocalIspServer>();
		for(int i = 0 ;i < service_ids.length; i++){
			String serviceId = service_ids[i];
			String ticketId = ticket_ids[i];
			/*通过功能ID获取相应服务*/
			Integer wwwRackCount = idcIspInfoMapper.getCountRack(ticketId);
			//idcIspInfoMapper.callIpSection(ticketId);
			Integer wwwIPCount = idcIspInfoMapper.getCountIP(ticketId);
			/* 调用IP信息目的是获取相应的ip段落 */
			if(wwwIPCount != null && wwwIPCount != 0 ){
				/*然后获取所有的服务*/
				List<LocalIspServer> ispServers_tmp =  idcIspInfoMapper.getServerIspByServerId(serviceId);//通过服务ID获取相关服务数据:前提先查询机房数量和IP数量
				/*** 循环服务   获取 LocalIspHousehold***/
				Iterator<LocalIspServer> server_it = ispServers_tmp.iterator();
				while(server_it.hasNext()){
					LocalIspServer localIspServer = server_it.next();
					//瓶装相应的域名信息
					List<ISPDomain> domains =  idcIspInfoMapper.loadDomainByTicketId(ticketId);
					localIspServer.setDomains(domains);
					//拼装housesHoldInfo信息
					List<LocalIspHousehold> households = idcIspInfoMapper.getHouseInfoByTicketId(ticketId);
					if(households != null && !households.isEmpty()){
						localIspServer.setLocalIspHouseholdList(households);
					}
					ispServers.add(localIspServer);
				}
			}
		}
		if(ispServers != null && !ispServers.isEmpty()){
			www_customer.setLocalIspServerList(ispServers);
		}
	}

	/**
	 * 上报客户信息
	 * @return
	 */
	@Override
	public List<LocalIspCustomer> loadIspCustomerList() {
		VelocityContext context = new VelocityContext();
		/* 循环加入数据中心 */
		List<String> houseIds = idcIspInfoMapper.getFindHouseIds();
		if(houseIds != null && !houseIds.isEmpty()) {
			for (int i = 0; i < houseIds.size(); i++) {
				String houseId = houseIds.get(i);
				context.put("houseId",houseId);
				/**获取[互联网客户]**/
				List<LocalIspCustomer> www_customers = idcIspInfoMapper.loadCustomers(houseId,1);
				/*  获取循环客户获取服务下对应的ip数和机架数  */
				Iterator<LocalIspCustomer> www_it = www_customers.iterator();
				while(www_it.hasNext()){
					LocalIspCustomer www_customer = www_it.next();
					String serviceIds = www_customer.getServiceIds();
					getWWWService(www_customer,serviceIds);/* 获取相应的数据。然后循环获取服务信息 */
				}
				/**获取[其他客户]**/
				List<LocalIspCustomer> other_customers = idcIspInfoMapper.loadCustomers(houseId,2);
				Iterator<LocalIspCustomer> other_it = other_customers.iterator();
				while(other_it.hasNext()){
					LocalIspCustomer other_customer = other_it.next();
					String serviceIds = other_customer.getServiceIds();
					//设置:最后一个服务的时间
					getOtherService(other_customer,serviceIds);/* 获取相应的数据。然后循环获取服务信息 */
				}
				/* 判断是否需要进行域名用户和其他用户 */
				context.put("www_customers",www_customers);
				context.put("other_customers",other_customers);
				if(www_customers != null && !www_customers.isEmpty()){
					context.put("has_www_customer",1);
				}else{
					context.put("has_www_customer",0);
				}
				if(other_customers != null && !other_customers.isEmpty()){
					context.put("has_other_customer",1);
				}else{
					context.put("has_other_customer",0);
				}

				getIspSolidDataInfo(context);
				PageParser.WriterPage(context,"AddUserTemplateNew.xml", DevContext.LOCAL_ISP_TEMPFIELPATH, DateHelper.getNowFDate()+".xml");
			}
		}
		return null;
	}

	@Override
	public void delUserDataXml() {
		List<String> houseIds = idcIspInfoMapper.getFindHouseIds();
		if(houseIds != null && !houseIds.isEmpty()) {
			for (int k = 0; k < houseIds.size(); k++) {

				String houseId = houseIds.get(k);
				List<LocalIspCustomer> customers = idcIspInfoMapper.loadCustomersWithAll(houseId);
				VelocityContext context = new VelocityContext();
				if(customers != null && !customers.isEmpty()){
					context.put("localIspCustomerList",customers);
					context.put("houseId",houseId);
					getIspSolidDataInfo(context);
				}
				PageParser.WriterPage(context,"DelUserTemplateNew.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,DateHelper.getNowFDate()+".xml");
			}
		}
	}

	@Override
	public void loadIspResourceDelete() {
		System.out.println("=================== 生成ISP删除文件[包括三个数据中心] =====================");

		List<String> houseIds = idcIspInfoMapper.getFindHouseIds();
		if(houseIds != null && !houseIds.isEmpty()) {
			for (int i = 0; i < houseIds.size(); i++) {
				String houseId = houseIds.get(i);
				List<LocalIspIpSeg> LocalIspIpSegList = idcIspInfoMapper.loadLocalIspIpSeg(houseId);
				VelocityContext context = new VelocityContext();
				if(LocalIspIpSegList != null && !LocalIspIpSegList.isEmpty()){
					//context.put("LocalIspIpSegList",LocalIspIpSegList);
					context.put("houseId",houseId);
					getIspSolidDataInfo(context);
					PageParser.WriterPage(context,"DelHouseTemplateNew.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,DateHelper.getNowFDate()+".xml");
				}
			}
		}
	}

	/*****
	 * 这个是需要走流程，调用的
	 */
	@Override
	public void updateResourceData() {
		//调用call
		idcIspInfoMapper.callIspResourcePro();
		/**
		 * 获取相应的ip信息
		 */
		List<LocalIspIpSeg> LocalIspIpSegList = idcIspInfoMapper.loadLocalIspIpSeg(null);
		/**
		 * 加载相应的机架数据
		 */
		List<LocalIspIframe> frameInfoList = idcIspInfoMapper.loadLocalIspIframe(null);
		VelocityContext context = new VelocityContext();
		if(LocalIspIpSegList != null && !LocalIspIpSegList.isEmpty()){
			context.put("localIspIpSegList",LocalIspIpSegList);
		}
		if(frameInfoList!= null & !frameInfoList.isEmpty()){
			context.put("frameInfoList",frameInfoList);
		}
		getIspSolidDataInfo(context);
		PageParser.WriterPage(context,"updateHouseTemplateNew.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,getPreFileStr()+"[AddHouseTemplateNew].xml");
	}

	@Override
	public void callProc_resource_hs(Long ticketInstId, Integer ticketStatus) {
		idcIspInfoMapper.callProc_resource_hs(ticketInstId,ticketStatus);
	}

	@Override
	public void callProcResourceBh(Long ticketInstId, Integer ticketStatus) {
		idcIspInfoMapper.callProcResourceBh(ticketInstId,ticketStatus);
	}


	@Override
	public void loadIspResourceUpload() {
		System.out.println("=================首先生成删除文件。然后在调用新增方法=================");
		/*** customer_section_func ***/
        idcIspInfoMapper.callCustSectionPro();
		loadIspResourceDelete();
		//调用call
		idcIspInfoMapper.callIspResourcePro();
		// 获取所有的数据中心
		List<String> houseIds = idcIspInfoMapper.getFindHouseIds();
		if(houseIds != null && !houseIds.isEmpty()){
			for(int i = 0 ;i < houseIds.size(); i++){
				String houseId = houseIds.get(i);
				/**
				 * 获取相应的ip信息
				 */
				List<LocalIspIpSeg> LocalIspIpSegList = idcIspInfoMapper.loadLocalIspIpSeg(houseId);//分配的IP信息
				//List<LocalIspIpInfo> LocalIspIpSegList = idcIspInfoMapper.getIspIpInfoListAllByHouseId(null);
				//Iterator<LocalIspIpInfo> ipIt = LocalIspIpSegList.iterator();
				/*while(ipIt.hasNext()){
					LocalIspIpInfo localIspIpInfo = ipIt.next();
					localIspIpInfo.setSourceUnit("四川移动分公司");
					localIspIpInfo.setAllocationUnit("四川移动分公司");
				}*/
				/**
				 * 加载相应的机架数据
				 */
				List<LocalIspIframe> frameInfoList = idcIspInfoMapper.loadLocalIspIframe(houseId);
				VelocityContext context = new VelocityContext();
				if(LocalIspIpSegList != null && !LocalIspIpSegList.isEmpty()){
					context.put("localIspIpSegList",LocalIspIpSegList);
				}
				if(frameInfoList!= null & !frameInfoList.isEmpty()){
					context.put("frameInfoList",frameInfoList);
				}
				context.put("houseId",houseId);
				getIspSolidDataInfo(context);
                /** 固定的网管信息  **/
				context.put("gatewayInfoList",loadGatewayInfoByHouseId(houseId));



				System.out.println("--------------- 新增资源信息[ip资源和机架资源] ------------------");
				PageParser.WriterPage(context,"AddHouseTemplateNew.xml", DevContext.LOCAL_ISP_TEMPFIELPATH,DateHelper.getNowFDate()+".xml");
				/** [修改相应的数据]  **/
			}
		}
	}
    public List<GatewayInfo> loadGatewayInfoByHouseId(String houseId) {
        List<GatewayInfo> gatewayInfos = idcIspInfoMapper.loadGateByHouseId(houseId);
        return gatewayInfos;
    }
	public List<GatewayInfo> loadGatewayInfoForHouse(){
        List<GatewayInfo> gatewayInfos = new ArrayList<GatewayInfo>();
        GatewayInfo gatewayInfo1 = new GatewayInfo();
		gatewayInfo1.setId(5000000000l+2+150);
		gatewayInfo1.setBandWidth(800000l);
		gatewayInfo1.setLinkType(3);
		gatewayInfo1.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo1.setGatewayIp("183.223.118.137");

		GatewayInfo gatewayInfo2 = new GatewayInfo();
		gatewayInfo2.setId(5000000000l+2+151);
		gatewayInfo2.setBandWidth(800000l);
		gatewayInfo2.setLinkType(3);
		gatewayInfo2.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo2.setGatewayIp("183.223.118.141");

		GatewayInfo gatewayInfo3 = new GatewayInfo();
		gatewayInfo3.setId(5000000000l+2+152);
		gatewayInfo3.setBandWidth(800000l);
		gatewayInfo3.setLinkType(3);
		gatewayInfo3.setAccessUnit("中国移动通信集团四川有限公司");
		gatewayInfo3.setGatewayIp("221.183.50.57");
		gatewayInfos.add(gatewayInfo1);
		gatewayInfos.add(gatewayInfo2);
		gatewayInfos.add(gatewayInfo3);
		return gatewayInfos;
	}

	public String getPreFileStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDateStr = sdf.format(new Date());
		return currentDateStr;
	}
	//固化的相关数据。。。
	public void getIspSolidDataInfo(VelocityContext context){
		String houseId = String.valueOf(context.get("houseId"));
		IspSolidData ispSolidData = idcIspInfoMapper.loadIspSolidData(houseId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		context.put("timeStamp",sdf.format(new Date()));
		context.put("ispSolidData",ispSolidData);
	}

}

