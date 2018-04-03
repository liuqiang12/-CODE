package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.runnable.ResourceRunnable;
import com.idc.service.FTPService;
import com.idc.service.IdcContractService;
import com.idc.utils.CategoryEnum;
import com.idc.utils.JbpmStatusEnum;
import com.idc.utils.ResourceEnum;
import com.idc.utils.ServiceApplyEnum;
import modules.utils.ResponseJSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.data.page.PageBean;
import utils.DevContext;
import utils.LOCALMAC;
import utils.plugins.excel.Guid;
import utils.typeHelper.FTPUtils;
import utils.typeHelper.MapHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_CONTRACT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcContractService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcContractServiceImpl extends JbpmEntranceServiceImpl implements IdcContractService {
	private static final Log log = LogFactory.getLog(IdcContractServiceImpl.class);
	@Autowired
	private IdcContractMapper idcContractMapper;
	@Autowired
	private IdcNetServiceinfoMapper idcNetServiceinfoMapper;
	@Autowired
	private AssetAttachmentinfoMapper assetAttachmentinfoMapper;
	@Autowired
	private FTPService ftpService;
	@Autowired
	private IdcRunTicketResourceMapper idcRunTicketResourceMapper;
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
	@Autowired
	private IdcRunTicketMapper idcRunTicketMapper;
	@Autowired
	private IdcHisTicketMapper idcHisTicketMapper;
 	/*查询有关数据*/

	@Override
	public List<Map<String,Object>> queryContractRemainingDays() throws Exception {
		return idcContractMapper.queryContractRemainingDays();
	}

	@Override
	public List<IdcContract> queryGridListPage(PageBean<IdcContract> page, IdcContract idcContract) {
		//条件情况
		page.setParams(MapHelper.queryCondition(idcContract));
		return idcContractMapper.queryGridListPage(page);
	}
	//合同信息查询
	@Override
	public List<Map<String,Object>> queryContractByUserListPage(PageBean<IdcContract> page,Map<String,Object> paramsMap) {
		//条件情况
		page.setParams(paramsMap);
		return idcContractMapper.queryContractByUserListPage(page);
	}

	public void alertBusinessChange(ApplicationContext applicationContext,Map<String,Object> paramsBusiness) throws Exception{
		//拿到变更后的资源，并且把状态改为占用状态
		System.out.println("------start---占用----处理 {机架  端口  ip  主机   mcb状态}----------------");
		List<Map<String,Object>> ticketResourceList = idcHisTicketResourceMapper.getResourceById(String.valueOf(paramsBusiness.get("ticketInstId")));    //查的是历史表idcHidTicketResource
		if(ticketResourceList.size()>0){
			for (Map<String,Object> resource:ticketResourceList){
				Map<String,Object> remoteParams=new HashMap<>();
				remoteParams.put("customerId",resource.get("CUSTOMERID"));
				remoteParams.put("customerName",resource.get("CUSTOMERNAME"));
				remoteParams.put("ticketId",resource.get("TICKETINSTID"));
				remoteParams.put("resourceCategory",resource.get("CATEGORY"));

				if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.机架.value())){
					remoteParams.put("status",ResourceEnum.机架占用.value());
					remoteParams.put("id",resource.get("RESOURCEID"));
				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.端口带宽.value())){
					remoteParams.put("status",ResourceEnum.端口带宽占用.value());
					remoteParams.put("id",resource.get("RESOURCEID"));
				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.IP租用.value())){
					remoteParams.put("status",ResourceEnum.IP已用.value());
					remoteParams.put("id",resource.get("RESOURCEID"));
					remoteParams.put("ipType",resource.get("IPTYPE"));
				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.主机租赁.value())){
					remoteParams.put("status",ResourceEnum.主机占用.value());
					remoteParams.put("id",resource.get("RESOURCEID"));
				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.增值业务.value())){


				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.U位.value())){


				}else if(resource.get("CATEGORY").toString().equals(ServiceApplyEnum.MCB.value())){
					remoteParams.put("status",ResourceEnum.MCB占用.value());
					remoteParams.put("id",resource.get("RESOURCEID"));
					//remoteParams.put("id",resourceList.get("RACKID"));
				}
				System.out.println("========远程调用=========开始修改资源状态======开始==========");
				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteParams);
				applicationContext.publishEvent(remoteSysResourceEvent);
				System.out.println("========远程调用=========开始修改资源状态======结束==========");
			}
		}

		System.out.println("------end----占用---处理 {机架  端口  ip  主机   mcb状态}----------------");

		System.out.println("------start--占用-----单独处理 {机位状态}----------------");
		List<Map<String,Object>> resourceRackUnitList = idcRunTicketResourceMapper.getResourceRackUnitById(String.valueOf(paramsBusiness.get("ticketInstId")));    //查的是历史表idcHidTicketResource
		if(resourceRackUnitList.size()>0){
			for (Map<String,Object> rackUnitResource:resourceRackUnitList){
				Map<String,Object> remoteParams=new HashMap<>();
				remoteParams.put("customerId",rackUnitResource.get("CUSTOMERID"));
				remoteParams.put("customerName",rackUnitResource.get("CUSTOMERNAME"));
				remoteParams.put("ticketId",rackUnitResource.get("TICKETINSTID"));
				remoteParams.put("resourceCategory",rackUnitResource.get("CATEGORY"));

				if(rackUnitResource.get("CATEGORY").toString().equals(ServiceApplyEnum.机架.value())){
					remoteParams.put("status",ResourceEnum.U位占用.value());
					remoteParams.put("id",rackUnitResource.get("RESOURCEID"));
					//remoteParams.put("rackId",rackUnitResourceList.get("RACKID"));
				}
				System.out.println("========远程调用=========开始修改资源状态======开始==========");
				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteParams);
				applicationContext.publishEvent(remoteSysResourceEvent);
				System.out.println("========远程调用=========开始修改资源状态======结束==========");
			}
		}
		System.out.println("------end---占用----单独处理 {机位状态}----------------");
	}

	/*
        *！！业务说明：
        * 100预堪预占流程，资源状态是预占状态。但是当你选择资源的时候，资源的状态就已经是预占状态了，所有不需要再修改状态
        * 200开通流程，并且是评价这一步，要把资源改成 占用状态
        * 600下线流程，并且是评价这一步，要把资源改成 空闲状态
        *
        * ！！方法说明:修改资源状态的时候：
        * 							 第一：要在评分这一步进行修改资源的状态；
        * 							 第二：要在开通一个新流程的时候修改资源的工单id
        * */
	@Override
	public void alterResourceStatus_A(ApplicationContext applicationContext,Long after_TicketInstId,Map<String,Object> params) throws Exception{
		/* *
		* 预堪预占：不需要修改资源
		* 业务开通：申请的第一步：修改资源的工单ID，最后评分一步，把资源的预占状态改为占用状态
		* 业务变更：申请的第一步：修改资源的工单ID
		* 变更开通：申请的第一步：修改资源的工单ID，最后一步修改资源的状态为占用状态，把业务变更之前的资源全部释放改为空闲状态
		* 停机：申请的第一步：修改资源的工单ID
		* 复通：申请的第一步：修改资源的工单ID
		* 下线：申请的第一步：修改资源的工单ID ，最后评分一步，把资源的预占状态改为空闲状态
		*
		* */
		IdcRunTicket idcHisTicket = idcRunTicketMapper.getTicketObjMapByTicketInstId(after_TicketInstId);  //此查询的是历史表
		String ticketCategory = idcHisTicket.getTicketCategory();  //得到工单的流程状态
		String Before_ticketStatus_Int =params != null ? params.get("Before_ticketStatus_Int").toString() : "";  //提交事务之前工单的状态，-1是驳回状态
		String Before_ticketInstId =(params != null && params.get("Before_ticketInstId")!= null) ? params.get("Before_ticketInstId").toString() : "";
		String Before_formKey =(params != null && params.get("Before_formKey")!= null) ? params.get("Before_formKey").toString() : "";

		//判断是否是最后一步，最后一步才需要修改工单的状态。 isLastStepTicket=true 代表最后一步
		Boolean isLastStepTicket = false;
		Boolean result = idcRunTicketMapper.getIsLastStepTicket(Before_formKey);   //result=false 代表最后一步
		if(!result){
			isLastStepTicket=true;
		}

		//判断是否是第一步，第一步才需要修改状态的工单的ID。 isFirstStepTicket=true 代表第一步
		Boolean isFirstStepTicket = false;
		if(Before_formKey != null && Before_formKey != ""){
			isFirstStepTicket = idcRunTicketMapper.getIsFirstStepTicket(Before_formKey);
		}

		if(!Before_ticketStatus_Int.equals("-1") && ( isLastStepTicket || isFirstStepTicket) && !ticketCategory.equals(CategoryEnum.预堪预占流程.value() ) ){
			System.out.println("-----------调用方法修改资源的工单ID或者资源的状态--------------------");
			alterResourceStatus_B(applicationContext,ticketCategory,after_TicketInstId,params);
			System.out.println("-----------调用方法修改资源的工单ID或者资源的状态--------------------");
		}

		System.out.println("-----------如果是开通变更流程，那么就还有一步处理，把预占的资源改成占用状态--------------------");
		if(!Before_ticketStatus_Int.equals("-1") && isLastStepTicket && ticketCategory.equals(CategoryEnum.开通变更流程.value())){
			Map<String,Object> paramsBusiness=new HashMap<>();
			paramsBusiness.put("ticketInstId",Before_ticketInstId);
			alertBusinessChange(applicationContext,paramsBusiness);
		}

	}

	@Override
	public void alterResourceStatus_B(ApplicationContext applicationContext,String ticketCategory,Long ticketInstId,Map<String,Object> params) throws Exception{
		if(params == null){
			params=new HashMap<>();
		}
		Boolean resourceFree = false;  //是否把资源改成空闲状态，false不改为空闲状态
		if(ticketCategory.equals(CategoryEnum.把资源改成空闲状态.value())){
			resourceFree=true;
		}

		//得到工单的客户信息
		Map<String,Object> customerMse = idcHisTicketMapper.getCustomerByTicketInstId(ticketInstId);

		if(customerMse == null){
			new Exception("此工单有问题！！！");
		}

		//因为U位没有保存到工单资源表中，所以要查U位修改U位的状态要调用接口查询U位资源表
		/*------------------start---------修改U位状态是单独的哦-------------------------------------------------*/
		//得到当前工单所有的机架的id
		System.out.println("=================单独修改U位状态======开始==========");
		List<Long> rackIdList = idcRunTicketResourceMapper.getRackIdResourceByTicket(ticketInstId);  //此查的是历史表
		if( rackIdList.size()>0 ){
			/*
				如果是开通流程，ticketInstId是新开通流程的工单ID，因为机位ID没有保存到工单资源表中，所有新的工单ID在rackunit表中就查询不
				到任何机位信息，只有拿到此工单的预占流程的工单ID，才能在工单表中查到机位信息
			*/
			Long rackUnitTicketID = params.get("parentTicketInstId") != null ? Long.valueOf(String.valueOf(params.get("parentTicketInstId"))) : ticketInstId;
			for (Long rackId:rackIdList) {

				/*下面的判断说明：如果工单被删除，那么就要回到父工单的资源状态，而机位信息存储在资源表里面，没有存在工单表里面，所以只能拿最新的
				工单id到资源表里面去查询，其他的比如机架这些，在工单的资源表里面存的有，所有就不会有这些问题
				*/
				if(params != null && params.get("ticketid_parent") != null){
					rackUnitTicketID=Long.valueOf(params.get("ticketInstId").toString());
				}

				List<Long> rackUnitResource = idcRunTicketResourceMapper.getRackUnitResourceByTicket(rackUnitTicketID,rackId); //此查的是历史表
				for (Long rackUnitId:rackUnitResource) {
					Map<String,Object> rackUnitMap=new HashMap<>();
					Integer rackUnitStatus = getResourceStatus(ticketCategory, ServiceApplyEnum.U位.value(),params);
					rackUnitMap.put("id",rackUnitId);
					rackUnitMap.put("status",rackUnitStatus);
					rackUnitMap.put("resourceCategory",ServiceApplyEnum.U位.value());
					if(resourceFree){
						rackUnitMap.remove("ticketId");
					}else{
						rackUnitMap.put("ticketId",ticketInstId);
					}
					rackUnitMap.put("customerName",customerMse.get("CUSTOMER_NAME").toString());
					rackUnitMap.put("customerId",customerMse.get("CUSTOMER_ID").toString());
					rackUnitMap.put("rackId",rackId);

					RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,rackUnitMap);
					applicationContext.publishEvent(remoteSysResourceEvent);
				}
			}
		}
		System.out.println("=================单独修改U位状态======结束==========");
 		/*------------------end---------修改U位状态是单独的哦-------------------------------------------------*/

		params.put("customerName",customerMse.get("CUSTOMER_NAME").toString());
		params.put("customerId",customerMse.get("CUSTOMER_ID").toString());
		params.put("ticketId",ticketInstId);
		if(resourceFree){
			params.remove("ticketId");
		}else{
			params.put("ticketId",ticketInstId);
		}

		//从资源表中得到机架、商品带宽、ip、主机、MCB 它们的资源信息。
		List<Map<String, Object>> ticketResource = idcRunTicketResourceMapper.getResourceByTicket(ticketInstId); //此查的是历史表
		if(ticketResource != null && ticketResource.size()>0){
			System.out.println("==start=========修改 机架、商品带宽、ip、主机、MCB的资源状态=========");
			alterResourceStatus_C(applicationContext,ticketResource,params,resourceFree,ticketInstId);
			System.out.println("==end=========修改 机架、商品带宽、ip、主机、MCB的资源状态=========");

		}

	}
	/**修改资源所有的资源信息
    * */
	@Override
	public void alterResourceStatus_C(ApplicationContext applicationContext,List<Map<String, Object>> ticketResourceList,Map<String,Object> params,Boolean resourceFree,Long ticketInstId) throws Exception{
		//得到需要修改的资源的个数
		Integer resourceStatusCount=0;
		if(ticketResourceList != null && ticketResourceList.size()>0){
			//得到需要修改的资源的个数
			resourceStatusCount=Integer.valueOf(ticketResourceList.size());
		}
		//reslet
		ResourceRunnable runnable=new ResourceRunnable(applicationContext,ticketResourceList,params,resourceFree,ticketInstId,resourceStatusCount,idcContractService);

		//ResourceStatusCount ticket =new ResourceStatusCount();
		Thread t1=new Thread(runnable,"线程1");
		Thread t2=new Thread(runnable,"线程2");
		Thread t3=new Thread(runnable,"线程3");
		Thread t4=new Thread(runnable,"线程4");

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		/*new Thread(runnable).start();
		new Thread(runnable).start();*/
		System.out.println("xxxxxxxxxxxxx");

	}


	//多线程修改资源状态！
	@Override
	public void runnableStatus(ApplicationContext applicationContext,Map<String, Object> resourceMaps,Map<String,Object> params,Boolean resourceFree,Long ticketInstId) throws Exception{
			String resourceCategory = resourceMaps.get("RESOURCECATEGORY").toString();//资源的状态
			String ticketCategoryNew =null;
			if(resourceFree){
				ticketCategoryNew = CategoryEnum.把资源改成空闲状态.value();//工单的状态
			}else{
				ticketCategoryNew = resourceMaps.get("TICKECAGETORY").toString();//工单的状态
			}
			Long resourceId = Long.valueOf(resourceMaps.get("RESOURCEID").toString());//工单的id
			params.put("id",resourceId);

			if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("ipType",resourceMaps.get("IP_TYPE"));
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.增值业务.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.U位.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}else if(resourceCategory.equals(ServiceApplyEnum.MCB.value())){
				Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
				params.put("resourceCategory",resourceCategory);
				params.put("status",resourceStatus);
			}

			if((resourceMaps.get("RACK_OR_RACKUNIT") == null) || ((resourceMaps.get("RACK_OR_RACKUNIT") != null) && resourceMaps.get("RACK_OR_RACKUNIT").toString().equals(ResourceEnum.按照机架分.value().toString()))){
				System.out.println("========远程调用=========开始修改资源状态======开始==========");
				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,params);
				applicationContext.publishEvent(remoteSysResourceEvent);
				System.out.println("========远程调用=========开始修改资源状态======结束==========");
			}
	}

/*

	@Override
	public void runnableStatus(ApplicationContext applicationContext,List<Map<String, Object>> ticketResource,Map<String,Object> params,Boolean resourceFree,Long ticketInstId) throws Exception{
		if(ticketResource != null && ticketResource.size()>0){
			//得到机位的资源信息
			for (Map<String, Object> maps : ticketResource ) {
				String resourceCategory = maps.get("RESOURCECATEGORY").toString();//资源的状态
				String ticketCategoryNew =null;
				if(resourceFree){
					ticketCategoryNew = CategoryEnum.把资源改成空闲状态.value();//工单的状态
				}else{
					ticketCategoryNew = maps.get("TICKECAGETORY").toString();//工单的状态
				}
				Long resourceId = Long.valueOf(maps.get("RESOURCEID").toString());//工单的id
				params.put("id",resourceId);

				if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("ipType",maps.get("IP_TYPE"));
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.增值业务.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.U位.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}else if(resourceCategory.equals(ServiceApplyEnum.MCB.value())){
					Integer resourceStatus = getResourceStatus(ticketCategoryNew, resourceCategory,params);
					params.put("resourceCategory",resourceCategory);
					params.put("status",resourceStatus);
				}
				System.out.println("========远程调用=========开始修改资源状态======开始==========");
				RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,params);
				applicationContext.publishEvent(remoteSysResourceEvent);
				System.out.println("========远程调用=========开始修改资源状态======结束==========");
			}
		}
	}

*/


	/**
	得到资源的状态
    * */
	public Integer getResourceStatus(String ticketCategory,String resourceCategory,Map<String,Object> params) throws Exception{
		String Before_ticketStatus_Int =(params !=null && params.get("Before_ticketStatus_Int") != null ) ? params.get("Before_ticketStatus_Int").toString() : "";
		String Before_formKey =(params != null && params.get("Before_formKey")!= null) ? params.get("Before_formKey").toString() : "";

		//判断是否是最后一步，最后一步才需要修改工单的状态。 isLastStepTicket=true 代表最后一步
		Boolean isLastStepTicket=false;
		if(Before_formKey != null && !Before_formKey.equals("")){
			Boolean result = idcRunTicketMapper.getIsLastStepTicket(Before_formKey);  //result=false代表就是最后一步
			if( !result){
                isLastStepTicket=true;
            }
		}

		/*下面的字段判断是否是删除或者作废工单*/
		Boolean isRubbishOrDelete=params !=null && params.get("isRubbishOrDelete") != null && params.get("isRubbishOrDelete").toString().equals("isRubbishOrDelete");

		/*判断很重要，否则会把状态改错*/
			if(	 ((ticketCategory.equals(CategoryEnum.把资源改成空闲状态.value()) || isLastStepTicket)  || isRubbishOrDelete )
				 	&&
				 !Before_ticketStatus_Int.equals("-1")
			){

			//预堪预占流程要把状态改成预占状态
			if(ticketCategory != null && ticketCategory.equals(CategoryEnum.预堪预占流程.value())){
				if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
					return ResourceEnum.机架预占.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
					return ResourceEnum.端口带宽预占.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
					return ResourceEnum.IP分配占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
					return ResourceEnum.主机预占.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.增值业务.value())){
					return ResourceEnum.增值业务预占.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.U位.value())){
					return ResourceEnum.U位预占.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.MCB.value())){
					return ResourceEnum.MCB预占.value();
				}

			//ticketCategory:预勘 200:开通 400:停机 500:复通 600:下线 700:变更开通 800:临时测试,900:业务变更
			//开通流程，业务变更，变更开通，停机，复通要把状态改成占用状态
			}else if(ticketCategory != null &&
					( ticketCategory.equals(CategoryEnum.开通流程.value()) ||
							ticketCategory.equals(CategoryEnum.停机流程.value()) ||
							ticketCategory.equals(CategoryEnum.复通流程.value()) ||
							ticketCategory.equals(CategoryEnum.业务变更流程.value()) )){
				if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
					return ResourceEnum.机架占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
					return ResourceEnum.端口带宽占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
					return ResourceEnum.IP已用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
					return ResourceEnum.主机占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.增值业务.value())){
					return ResourceEnum.增值业务占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.U位.value())){
					return ResourceEnum.U位占用.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.MCB.value())){
					return ResourceEnum.MCB占用.value();
				}

			//下线流程要把状态改成空闲状态    删除或作废也需要把状态改为空闲状态，ticketCategory=10000代表删除或作废
			}else if(ticketCategory != null &&
					(ticketCategory.equals(CategoryEnum.下线流程.value())) || ticketCategory.equals(CategoryEnum.把资源改成空闲状态.value())
					){
				if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
					return ResourceEnum.机架空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
					return ResourceEnum.端口带宽空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
					return ResourceEnum.IP空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
					return ResourceEnum.主机空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.增值业务.value())){
					return ResourceEnum.增值业务空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.U位.value())){
					return ResourceEnum.U位空闲.value();
				}else if(resourceCategory.equals(ServiceApplyEnum.MCB.value())){
					return ResourceEnum.MCB空闲.value();
				}

			//删除或作废也需要把状态改为空闲状态，ticketCategory=10000代表删除或作废
			}
		}
		return null;
	}
	@Override
	public void handOpenTicketWithContractInto(ApplicationContext context, IdcContract idcContract,IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws Exception {

		/* 合同处理 */
		SysUserinfo principal = (SysUserinfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//判断是否是属于变更开通的情况
		String ticketCategoryFrom = idcContract.getIdcRunTicket().getTicketCategoryFrom();
		String ticketCategoryTo = idcContract.getIdcRunTicket().getTicketCategoryTo();
		if(idcContract.getId() != null){
			//保存  idcNetServiceInfo
			//contractHandlerData(context,idcContract,idcContract.getIdcNetServiceinfo(),request,Integer.valueOf(idcContract.getIdcRunTicket().getProdCategory()),true);

			if(ticketCategoryFrom != null && ticketCategoryTo != null
					&& "900".equals(ticketCategoryFrom) && "700".equals(ticketCategoryTo)){
				/* 属于业务变更后 ====》 变更开通过程 */
				/*  先创建工单 */
				Map<String,Object> variables = new HashMap<String,Object>();
				variables.put("prodInstId",idcContract.getIdcRunTicket().getProdInstId());
				variables.put("ticketCategoryFrom",idcContract.getIdcRunTicket().getTicketCategoryFrom());
				variables.put("ticketCategoryTo",idcContract.getIdcRunTicket().getTicketCategoryTo());
				variables.put("parentId",idcContract.getIdcRunTicket().getTicketInstId());
				variables.put("prodCategory",idcContract.getIdcRunTicket().getProdCategory());

				ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
				if(responseJSON.isSuccess()){
					Map<String,Object> responseJSONMap = (Map)responseJSON.getResult();
					Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));

					wrapperContract(idcContract,ticketInstId,principal);
					/*查询工单是否重复*/
					Boolean isHasContract = idcContractMapper.getIsHasContract(ticketInstId);
					if(!isHasContract){
						//update
						Long contractId = idcContractMapper.insertIdcContract(idcContract);
						log.debug("保存合同其他信息");
						if(contractId != null){
							handNetServiceinfo(idcContract,ticketInstId,contractId);
						}
					}else{
						idcContractMapper.updateIdcContractByTicket(idcContract);
						handNetServiceinfo(idcContract,ticketInstId,idcContract.getId());
					}
				}else{
					log.debug("工单创建失败");
				}

			}else{
				/******** 通用方法 *********/
				log.debug(" 通用方法！");
				IdcRunTicket idcRunTicket = idcRunTicketMapper.getIdcRunTicketByIdTicketInstId(idcRunProcCment.getTicketInstId());
				if(idcRunTicket.getTicketStatus() == JbpmStatusEnum.流程驳回或不同意.value() && (
						idcRunTicket.getFormKey().equalsIgnoreCase("open_accept_apply_form")||
						idcRunTicket.getFormKey().equalsIgnoreCase("open_change_accept_apply_form")
				)){
					//如果是预勘流程驳回，则可以修改业务信息:
					log.debug("驳回不同意，重新提交流程时，需要修改的合同信息。。。。。。。。。。");
					//修改合同信息
					log.debug("合同修改");
					IdcReCustomer idcReCustomer = idcContract.getIdcReCustomer();
					idcContract.setCustomerId(idcReCustomer.getId());
					idcContract.setCustomerName(idcReCustomer.getName());
					idcContract.setApplyId(String.valueOf(principal.getId()));
					idcContract.setApplyName(principal.getNick());//
					idcContract.setProdInstId(idcRunProcCment.getProdInstId());
					if(idcContract.getProdInstId() == null){
						idcContract.setProdInstId(idcContract.getIdcRunTicket().getProdInstId());
					}
					idcContractMapper.updateIdcContract(idcContract);
					//然后修改基础信息
					handNetServiceinfo(idcContract,idcContract.getTicketInstId(),idcContract.getId());

				}
				log.debug(".执行流程引擎的方法............start");
				/********************************* 任务节点完成 ***********************************************/
				log.debug("工单预制状态:==状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束");

				Map<String,Object> completeParamMap = settingJbpmParam(idcRunProcCment,idcRunTicket);
				completeParamMap.put("taskId",idcRunTicket.getTaskId());
				completeTaskNode(completeParamMap);//审批流程
				log.debug(".执行流程引擎的方法............end");
			}

		}else{
			log.debug("第一次申请，第一次保存合同！！！");

			/*  先创建工单 */
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("prodInstId",idcContract.getIdcRunTicket().getProdInstId());
			variables.put("ticketCategoryFrom",idcContract.getIdcRunTicket().getTicketCategoryFrom());
			variables.put("ticketCategoryTo",idcContract.getIdcRunTicket().getTicketCategoryTo());
			variables.put("parentId",idcContract.getIdcRunTicket().getTicketInstId());
			variables.put("prodCategory",idcContract.getIdcRunTicket().getProdCategory());

			ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
			if(responseJSON.isSuccess()){
				Map<String,Object> responseJSONMap = (Map)responseJSON.getResult();
				Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
				wrapperContract(idcContract,ticketInstId,principal);
				/*查询工单是否重复*/
				Boolean isHasContract = idcContractMapper.getIsHasContract(ticketInstId);
				if(!isHasContract){

					idcContract.setProdInstId(idcContract.getIdcRunTicket().getProdInstId());
					if(idcContract.getProdInstId() == null){
						idcContract.setProdInstId(idcRunProcCment.getProdInstId());
					}
					if(idcContract.getProdInstId() == null){
 						new Exception("订单ID不能为空！！！");
 					}
					Long contractId = idcContractMapper.insertIdcContract(idcContract);
					log.debug("保存合同其他信息");
					if(contractId != null){
						handNetServiceinfo(idcContract,ticketInstId,contractId);
					}
				}else{
					log.debug("合同已经存在。。。。。");
				}
			}else{
				log.debug("工单创建失败");
			}

			contractHandlerDataFirst(context,idcContract,idcContract.getIdcNetServiceinfo(),request,Integer.valueOf(idcContract.getIdcRunTicket().getProdCategory()),false);
		}
	}
	@Override
	public void handOpenTicketWithContractInto_Self(ApplicationContext context, IdcContract idcContract,IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws Exception {
		/* 合同处理 */
		SysUserinfo principal = (SysUserinfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//判断是否是属于变更开通的情况
		String ticketCategoryFrom = idcContract.getIdcRunTicket().getTicketCategoryFrom();
		String ticketCategoryTo = idcContract.getIdcRunTicket().getTicketCategoryTo();

		log.debug("第一次申请，第一次保存合同！！！");
			/*  先创建工单 */
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("prodInstId",idcContract.getIdcRunTicket().getProdInstId());
		variables.put("ticketCategoryFrom",idcContract.getIdcRunTicket().getTicketCategoryFrom());
		variables.put("ticketCategoryTo",idcContract.getIdcRunTicket().getTicketCategoryTo());
		variables.put("parentId",idcContract.getIdcRunTicket().getTicketInstId());
		variables.put("prodCategory",idcContract.getIdcRunTicket().getProdCategory());

		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
		if(responseJSON.isSuccess()){
			Map<String,Object> responseJSONMap = (Map)responseJSON.getResult();
			Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
			/*wrapperContract(idcContract,ticketInstId,principal);
			IdcReCustomer idcReCustomer = idcContract.getIdcReCustomer();
			idcContract.setCustomerId(idcReCustomer.getId());
			idcContract.setCustomerName(idcReCustomer.getName());*/
			idcContract.setTicketInstId(ticketInstId);
			idcContract.setApplyId(String.valueOf(principal.getId()));
			idcContract.setApplyName(principal.getNick());

			IdcNetServiceinfo idcNetServiceinfo = idcContract.getIdcNetServiceinfo();
			idcNetServiceinfo.setTicketInstId(ticketInstId);
			idcNetServiceinfo.setCustomerName(idcContract.getCustomerName());
			idcNetServiceinfo.setCustomerId(idcContract.getCustomerId());
			idcNetServiceinfo.setContractId(null);
			idcNetServiceinfoMapper.deleteByContractId(ticketInstId);
			idcNetServiceinfoMapper.insertIdcNetServiceinfo(idcNetServiceinfo);

 		}else{
			log.debug("工单创建失败");
		}
	}

	public void handNetServiceinfo(IdcContract idcContract,Long ticketInstId,Long contractId) throws Exception{
		IdcNetServiceinfo idcNetServiceinfo = idcContract.getIdcNetServiceinfo();
		idcNetServiceinfo.setTicketInstId(ticketInstId);
		idcNetServiceinfo.setCustomerName(idcContract.getCustomerName());
		idcNetServiceinfo.setCustomerId(idcContract.getCustomerId());
		idcNetServiceinfo.setContractId(contractId);
		idcNetServiceinfoMapper.deleteByContractId(ticketInstId);
		idcNetServiceinfoMapper.insertIdcNetServiceinfo(idcNetServiceinfo);
	}
	public void wrapperContract(IdcContract idcContract,Long ticketInstId,SysUserinfo principal) throws Exception{
		IdcReCustomer idcReCustomer = idcContract.getIdcReCustomer();
		idcContract.setCustomerId(idcReCustomer.getId());
		idcContract.setCustomerName(idcReCustomer.getName());
		idcContract.setTicketInstId(ticketInstId);
		idcContract.setApplyId(String.valueOf(principal.getId()));
		idcContract.setApplyName(principal.getNick());
	}

	/**
	 * 申请操作：
	 * 			业务变更申请、变更开通申请、停机申请、下线申请、复通申请等。不包括开通申请
	 * */
	@Override
	public void createTicketApply(ApplicationContext context,Map<String,Object> params) throws Exception {

		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(params));////开通工单常见工单过程。。。。。

		Map<String, Object> responseJSONMap = (Map) responseJSON.getResult();
		Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));

	}


	@Override
	public Long contractHandlerData(ApplicationContext context,IdcContract idcContract,IdcNetServiceinfo idcNetServiceinfo,HttpServletRequest request,Integer prodCategory,Boolean isRejectTicket) throws  Exception{
		//创建了工单:返回该工单
		/*此时我通过合同编号和合同名称判断如果两个都为null 则表示没有合同信息*/
		if(prodCategory != null && prodCategory == 1L){
			if( idcContract.getId() != null){
				idcContractMapper.updateIdcContract(idcContract);
			}else{
				idcContractMapper.insertIdcContract(idcContract);
			}
			if(idcContract.getId() == null || 0 == idcContract.getId() ){
				//通过流程实例ID查询合同信息:此处可能会出错。到时候再改:还有名称、编码
				idcContract = idcContractMapper.getContractByTicketInstId(idcContract.getTicketInstId());
			}
		}
		//如果是驳回的工单，修改合同信息，调用存储过程操作更新是否是dns信息
		if(isRejectTicket){
			if(idcContract.getIdcNetServiceinfo().getTicketInstId() == null ){
				idcContract.getIdcNetServiceinfo().setTicketInstId(idcContract.getIdcRunTicket().getTicketInstId());
			}
			//如果是驳回，就需要把之前的合同的有DNS或者没有DNS信息给清除
			idcNetServiceinfoMapper.updateByDNS(idcNetServiceinfo.getTicketInstId());
		}
		//idcNetServiceinfoVo.setIns_id(idcContract.getTicketInstId());
		idcNetServiceinfoMapper.mergeInto(idcNetServiceinfo);

		return (idcContract != null ? idcNetServiceinfo.getTicketInstId() : 0L);
	}

	public Long contractHandlerDataFirst(ApplicationContext context,IdcContract idcContract,IdcNetServiceinfo idcNetServiceinfo,HttpServletRequest request,Integer prodCategory,Boolean isRejectTicket) throws  Exception{
		/*此时我通过合同编号和合同名称判断如果两个都为null 则表示没有合同信息*/
		if(prodCategory != null && prodCategory == 1L){
			if(idcContract.getId() == null || 0 == idcContract.getId() ){
				//通过流程实例ID查询合同信息:此处可能会出错。到时候再改:还有名称、编码
				idcContract = idcContractMapper.getContractByTicketInstId(idcContract.getTicketInstId());
			}
		}
 		return (idcContract != null ? idcContract.getTicketInstId() : 0L);
	}

	@Override
	public void insertAttachInfo(ApplicationContext context,AssetAttachmentinfo assetAttachmentinfo) throws Exception {
		assetAttachmentinfoMapper.insertAttachInfo(assetAttachmentinfo);
	}


	@Override
	public IdcContract getModelByContractId(Long id) {
		return idcContractMapper.getModelByContractId(id);
	}

	@Override
	public void deleteRecordById(Long id) throws Exception {
		idcContractMapper.deleteRecordById(id);
	}
	/**
	 * 通过工单实例获取合同信息
	 * @param ticketInstId
	 * @return
	 */
	@Override
	public IdcContract getContractByTicketInstId(Long ticketInstId){
		return idcContractMapper.getContractByTicketInstId(ticketInstId);
	}

	@Override
	public String querySonTicketIsEnd(String ticketInstId,String prodInstId){
		String res = idcContractMapper.querySonTicketIsEnd(ticketInstId,prodInstId);
		return res;
	}

	@Override
	public Long verifyContractRepeat(String contractNo){
		Long res = idcContractMapper.verifyContractRepeat(contractNo);
		return res;
	}

	@Override
	public List<IdcContract> getIdcContractDataIntoRedis() {
		return idcContractMapper.getIdcContractDataIntoRedis();
	}
}
