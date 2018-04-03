package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.ActJBPMService;
import com.idc.service.IdcContractService;
import com.idc.service.IdcRunProcCmentService;
import com.idc.service.TicketRedisService;
import com.idc.utils.*;
import modules.utils.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import utils.DevContext;

import java.util.*;

/***************************************************************************************
 * *过程说明:[流程过程]
 * *[请求地址:jbpm/handlerRunTikcetProcess start]
 * *1:当前工单意见信息[IDC_RUN_TICKET_PROC_COMMENT]、前工单意见历史信息[IDC_HIS_TICKET_PROC_COMMENT]
 * *2:保存工单关联表信息[IDC_RUN_TICKET_PRE_ACCEPT、IDC_RUN_TICKET_CHANGE、IDC_RUN_TICKET_HALT、IDC_RUN_TICKET_RECOVER]、保存工单关联历史表信息[IDC_HIS_TICKET_PRE_ACCEPT、IDC_HIS_TICKET_CHANGE、IDC_HIS_TICKET_HALT、IDC_HIS_TICKET_RECOVER]
 * *[请求地址:jbpm/handlerRunTikcetProcess 返回后...]
 * *3:修改IDC_RE_PRODUCT、idc_his_ticket、idc_run_ticket订单状态
 * *4
 * *	4.1 IDC_HIS_TICKET_COMMNET:历史工单反馈意见表
 * *		4.1.1如果工单结束,则需要修改IDC_HIS_TICKET的属性END_TIME、APPROVAL_STATUS、IS_RUBBISH属性值
 * *		     修改IDC_RE_PRODUCT、idc_his_ticket、idc_run_ticket订单状态
 * *		     清除正在运行的工单所有表信息
 * *        4.1.2如果工单没有结束,则修改该工单的意见IDC_HIS_TICKET_COMMNET和工单的状态.处理流程相关信息workflow
 * *		开通完成后，需要修改资源状态：已经占用
 * *		下线完成后，需要修改资源状态：释放资源
 * ***************************************************************************************/
@Service("idcRunProcCmentService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcRunProcCmentServiceImpl extends JbpmEntranceServiceImpl
		implements IdcRunProcCmentService,TicketRedisService {
	private Logger logger = LoggerFactory.getLogger(IdcRunProcCmentServiceImpl.class);
	/***************************************************************************************
	 *		jbpm-core利用流程变换工单的核心代码区[###入口方法handlerRunTikcetProcess###] start
	 ***************************************************************************************/
	@Autowired
	private IdcRunProcCmentMapper idcRunProcCmentMapper;
	@Autowired
	private IdcHisProcCmentMapper idcHisProcCmentMapper;
	@Autowired
	private IdcHisTicketCommnetMapper idcHisTicketCommnetMapper;
	@Autowired
	private IdcHisTicketMapper idcHisTicketMapper;
	@Autowired
	private ActJBPMService actJBPMService;
	@Autowired
	private IdcRunTicketPauseMapper idcRunTicketPauseMapper;     //停机运行表
	@Autowired
	private IdcHisTicketPauseMapper idcHisTicketPauseMapper;    //停机历史表
	@Autowired
	private IdcReProductMapper idcReProductMapper;
	@Autowired
	private RestTemplate restTemplate;/*接口调用模板*/
	@Autowired
	private IdcContractService idcContractService;/*接口调用模板*/

	@Autowired
	private IdcRunTicketResourceMapper idcRunTicketResourceMapper;
	@Autowired
	private IdcRunTicketMapper idcRunTicketMapper;
	@Autowired
	private IdcRunTicketHaltMapper idcRunTicketHaltMapper;
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
	@Autowired
	private IdcHisTicketHaltMapper idcHisTicketHaltMapper;
	public ActJBPMService getLocalActJBPMService(){
		return this.actJBPMService;
	}

	public void handlerCommnet(IdcRunProcCment idcRunProcCment,IdcRunTicket idcRunTicket) throws  Exception{
        /*任务审批意见 start*/
		idcRunProcCment.setActName(idcRunTicket.getTaskName());
		idcRunProcCment.setTaskId(Long.valueOf(String.valueOf(idcRunTicket.getTaskId())));
		//comment  author  status
		idcRunProcCmentMapper.insertIntoProcCment(idcRunProcCment);
		IdcHisProcCment idcHisProcCment = new IdcHisProcCment();
		Collection<String> excludes = new ArrayList<String>();
		excludes.add("id");
		BeanUtils.copyProperties(idcRunProcCment,idcHisProcCment,excludes.toArray(new String[excludes.size()]));
		idcHisProcCmentMapper.insertIntoProcCment(idcHisProcCment);
	}
	public Map<String,Object> wapperMap(IdcReProduct idcReProduct){
		Map<String,Object> definedArrayMap = new HashMap<String,Object>();
		List<IdcReProduct> idcReProductList = new ArrayList<IdcReProduct>();
		List<IdcReRackModel> idcReRackList = new ArrayList<IdcReRackModel>();
		List<IdcRePortModel> idcRePortList = new ArrayList<IdcRePortModel>();
		List<IdcReIpModel> idcReIpList = new ArrayList<IdcReIpModel>();
		List<IdcReServerModel> idcServerList = new ArrayList<IdcReServerModel>();
		List<IdcReNewlyModel> idcReNewlyList = new ArrayList<IdcReNewlyModel>();
		if(idcReProduct != null ){
			idcReProductList.add(idcReProduct);
			if(idcReProduct.getIdcReRackModel() != null){
				idcReRackList.add(idcReProduct.getIdcReRackModel());
				definedArrayMap.put("rackIitemList",idcReRackList);
			}
			if(idcReProduct.getIdcRePortModel() != null){
				idcRePortList.add(idcReProduct.getIdcRePortModel());
				definedArrayMap.put("portIitemList",idcRePortList);
			}
			if(idcReProduct.getIdcReIpModel() != null){
				idcReIpList.add(idcReProduct.getIdcReIpModel());
				definedArrayMap.put("ipIitemList",idcReIpList);
			}
			if(idcReProduct.getIdcReServerModel() != null){
				idcServerList.add(idcReProduct.getIdcReServerModel());
				definedArrayMap.put("serverIitemList",idcServerList);
			}
			if(idcReProduct.getIdcReNewlyModel() != null){
				idcReNewlyList.add(idcReProduct.getIdcReNewlyModel());
				definedArrayMap.put("newlyIitemList",idcReNewlyList);
			}
			definedArrayMap.put("itemList",idcReProductList);
		}
		return definedArrayMap;
	}

	public Long procductHandlerData(IdcReProduct idcReProduct) throws  Exception{
//---------------操作订单总表 start------------------
		//---------- 该地方进行存储过程保存数据。目的是效率问题。传递的参数是数组 -------------//
		Map<String,Object> definedArrayMap = wapperMap(idcReProduct);
		if(definedArrayMap == null || definedArrayMap.isEmpty()){
			return null;
		}
		idcReProductMapper.callProcSaveProductArry(definedArrayMap);
		String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
		String isEXISTStr = String.valueOf(definedArrayMap.get("isEXIST"));

		if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
			return Long.valueOf(String.valueOf(definedArrayMap.get("product_obj_meta_curr_id")));
		}else{
			/*如果是已经存在则*/
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
			if(isEXISTStr != null && "1".equals(isEXISTStr)){
				return DevContext.EXIST;
			}else{
				throw new Exception();
			}
		}
	}
	@Override
	public Long handlerRunTikcetProcess(ApplicationContext context,IdcRunProcCment idcRunProcCment,IdcReProduct idcReProduct) throws Exception {

		logger.debug("============================ticketInstId["+idcRunProcCment.getTicketInstId()+"]==============");
		IdcRunTicket idcRunTicket = idcRunTicketMapper.getIdcRunTicketByIdTicketInstId(idcRunProcCment.getTicketInstId());

		Map<String,Object> resourceStatusMap=new HashMap<>();//专门用来改资源状态的
		resourceStatusMap.put("Before_ticketStatus_Int",idcRunTicket.getTicketStatus());  //判断是否是驳回的状态
		resourceStatusMap.put("Before_formKey_Int",TASKNodeURL.numbuerWithFormKey(idcRunTicket.getFormKey()));  //判断当前流程是第几步
		resourceStatusMap.put("ticketCategory",idcRunTicket.getTicketCategory());  //判断当前流程是预堪还是开通还是其他。。。。。
		resourceStatusMap.put("Before_ticketInstId",idcRunTicket.getTicketInstId());  //判断当前流程是预堪还是开通还是其他。。。。。
		resourceStatusMap.put("Before_formKey",idcRunTicket.getFormKey());  //判断当前流程是预堪还是开通还是其他。。。。。

		if(idcRunTicket.getTicketStatus() == JbpmStatusEnum.流程驳回或不同意.value() &&
				(	idcRunTicket.getFormKey().equalsIgnoreCase("pre_accept_apply_form") ||
				 	idcRunTicket.getFormKey().equalsIgnoreCase("business_change_accept_apply_form"))	){
			//如果是预勘流程驳回，则可以修改业务信息:
			logger.debug("驳回不同意，重新提交流程时，需要修改的订单信息。。。。。。。。。。");
			Long  prodInstId  = procductHandlerData(idcReProduct);
			if(prodInstId !=  null && DevContext.EXIST == prodInstId){
				return DevContext.EXIST;//直接返回说明是存在相同数据
			}
			 //然后更加工单ID和订单ID先删除后保存信息
			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------start");
			//然后在保存业务类型
			procductDefModelHandlerData_local(idcReProduct,idcRunTicket.getId(),prodInstId);
			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------end");

		} else if(idcRunTicket.getTicketStatus() == JbpmStatusEnum.流程驳回或不同意.value()
				&& (idcRunTicket.getFormKey().equalsIgnoreCase("self_halt_accept_apply_form") || idcRunTicket.getFormKey().equalsIgnoreCase("halt_accept_apply_form"))){
			logger.debug("驳回不同意，重新提交流程时，需要修改的[停机]信息。。。。。。。。。。");
			//然后直接update。下线信息
			IdcRunTicketHalt idcRunTicketHalt = idcRunProcCment.getIdcRunTicketHalt();
			idcRunTicketHalt.setTicketInstId(idcRunProcCment.getTicketInstId());
			idcRunTicketHaltMapper.updateByIdcRunTicketHalt(idcRunTicketHalt);
			IdcHisTicketHalt idcHisTicketHalt = new IdcHisTicketHalt();
			BeanUtils.copyProperties(idcRunTicketHalt,idcHisTicketHalt);
			idcHisTicketHaltMapper.updateByIdcHisTicketHalt(idcHisTicketHalt);
		}

		System.out.println("-----------如果是预占流程，分配资源分配这一步，并且分配资源后把此工单驳回了，那么也要释放资源！--------------------");
		if(idcRunTicket.getTicketCategory().equals(CategoryEnum.预堪预占流程.value())){
			//判断此工单是否分配了资源,true代表分配了资源，false代表没有分配资源
			Boolean allotResource = idcHisTicketResourceMapper.isAllotResource(idcRunTicket.getTicketInstId().toString());
			//如果分配了资源，并且是预占流程，并且被驳回了，那么就需要把资源全部释放
			if(idcRunProcCment.getStatus().equals(JbpmStatusEnum.审批不同意.value()) && allotResource){
				Map<String,Object> paramsBusiness=new HashMap<>();
				Map<String,Object> params=new HashMap<>();
				paramsBusiness.put("ticketInstId",idcRunTicket.getTicketInstId().toString());
				System.out.println("-----------调用方法修改资源的工单ID或者资源的状态--------------------");
				idcContractService.alterResourceStatus_B(context,CategoryEnum.把资源改成空闲状态.value(),idcRunTicket.getTicketInstId(),params);
			}
		}

		logger.debug(".执行流程引擎的方法............start");
		/********************************* 任务节点完成 ***********************************************/
		logger.debug("工单预制状态:==状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束");
		/**
		 * 1：如果当前工单是同意 且 status=0 修改工单状态为-1
		 * 2：如果当前工单是同意 且 status=1 修改工单状态为1
		 * 3：如果当前工单是驳回-1 且status=0 修改状态为-2
		 * 4：如果当前工单是驳回-1 且status=1 修改状态为1
		 * 5：如果当前工单的formKey是最后一步骤 且status=1 修改状态为2结束
		 * 6:删除到回收站的情况:[后面修改]
		 */
		Map<String,Object> completeParamMap = settingJbpmParam(idcRunProcCment,idcRunTicket);
		completeParamMap.put("taskId",idcRunTicket.getTaskId());
		completeTaskNode(completeParamMap);//审批流程
		logger.debug(".执行流程引擎的方法............end");

		System.out.println("----进入走流程的方法,调用修改资源的方法!!!!!!!!!!!!!!!--------------");
		System.out.println("----修改资源状态、或修改资源的工单ID 通用方法------开始");
		idcContractService.alterResourceStatus_A(context,idcRunProcCment.getTicketInstId(),resourceStatusMap);
		System.out.println("----修改资源状态、或修改资源的工单ID 通用方法------结束");

		System.out.println("----调用ISP--------------测试使用------开始");
		/*BasicInfoEvent basicInfoEvent = new BasicInfoEvent(context,completeParamMap);
		context.publishEvent(basicInfoEvent);*/
		System.out.println("----调用ISP--------------测试使用------结束");

		return null;

	}
	public Long procductDefModelHandlerData_local(IdcReProduct idcReProduct,Long ticketInstId,Long prodInstId) throws  Exception{
		Map<String,Object> definedArrayMap = wapperDefModelMap_Local(idcReProduct,prodInstId,ticketInstId);
		if(definedArrayMap == null || definedArrayMap.isEmpty()){
			return null;
		}
		idcReProductMapper.callProcSaveProductDefModelArry(definedArrayMap);
		String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
		String isEXISTStr = String.valueOf(definedArrayMap.get("isEXIST"));

		if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
			return Long.valueOf(DevContext.SUCCESS);
		}else{
			/*如果是已经存在则*/
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
			if(isEXISTStr != null && "1".equals(isEXISTStr)){
				return DevContext.EXIST;
			}else{
				throw new Exception();
			}
		}
	}

	public Map<String,Object> wapperDefModelMap_Local(IdcReProduct idcReProduct,Long prodInstId,Long ticketInstId){
		Map<String,Object> definedArrayMap = new HashMap<String,Object>();
		List<IdcReProduct> idcReProductList = new ArrayList<IdcReProduct>();
		List<IdcReRackModel> idcReRackList = new ArrayList<IdcReRackModel>();
		List<IdcRePortModel> idcRePortList = new ArrayList<IdcRePortModel>();
		List<IdcReIpModel> idcReIpList = new ArrayList<IdcReIpModel>();
		List<IdcReServerModel> idcServerList = new ArrayList<IdcReServerModel>();
		List<IdcReNewlyModel> idcReNewlyList = new ArrayList<IdcReNewlyModel>();
		if(idcReProduct != null ){
			idcReProductList.add(idcReProduct);
			if(idcReProduct.getIdcReRackModel() != null){
				idcReRackList.add(idcReProduct.getIdcReRackModel());
				definedArrayMap.put("rackIitemList",idcReRackList);
			}
			if(idcReProduct.getIdcRePortModel() != null){
				idcRePortList.add(idcReProduct.getIdcRePortModel());
				definedArrayMap.put("portIitemList",idcRePortList);
			}
			if(idcReProduct.getIdcReIpModel() != null){
				idcReIpList.add(idcReProduct.getIdcReIpModel());
				definedArrayMap.put("ipIitemList",idcReIpList);
			}
			if(idcReProduct.getIdcReServerModel() != null){
				idcServerList.add(idcReProduct.getIdcReServerModel());
				definedArrayMap.put("serverIitemList",idcServerList);
			}
			if(idcReProduct.getIdcReNewlyModel() != null){
				idcReNewlyList.add(idcReProduct.getIdcReNewlyModel());
				definedArrayMap.put("newlyIitemList",idcReNewlyList);
			}
			definedArrayMap.put("prodInstId",prodInstId);
			definedArrayMap.put("ticketInstId",ticketInstId);
		}
		return definedArrayMap;
	}

	public void handProcTicketStatus(IdcRunProcCment idcRunProcCment) throws Exception{
		System.out.println("[jbpm_process_back.PROC_TICKET_STATUS]流程工单状态:10草稿；20预勘中，21已经预勘；30开通中，31已经开通；40停机中，41已经停机；50下线中，51已经下线；60变更预勘、61变更预勘结束、70变更开通中、71变更开通结束、80复通流程、81复通完成,、90测试中、91测试完成、100业务变更中、101业务变更完成");
		System.out.println("修改的sql是[update IDC_RE_PRODUCT=PROCTICKET_STATUS||update idc_his_ticket set PROCTICKET_STATUS||update idc_run_ticket set PROCTICKET_STATUS]");
		if(TASKNodeURL.numbuerWithFormKey(idcRunProcCment.getFormKey()) != null &&
				TASKNodeURL.numbuerWithFormKey(idcRunProcCment.getFormKey()) == 1){
			System.out.println(idcRunProcCment.getFormKey()+"证明该步骤是流程中的第一个任务..............");
			handUpdateProduct(idcRunProcCment,"0");
		}
	}

	public Long handTicketComment(IdcRunProcCment idcRunProcCment) throws Exception{
		//如果是不同意走这一步
		if(idcRunProcCment.getIsRejectTicket() && idcRunProcCment.getStatus() == 0){
			System.out.println("是将被反驳的单子工单[工单号="+idcRunProcCment.getTicketInstId()+"]结束");
			handTicketFeed(idcRunProcCment,1);
			System.out.println("修改订单状态......................");
			handUpdateProduct(idcRunProcCment,"1");
			System.out.println("清除正在运行的工单所有表信息......................");
			idcRunTicketMapper.callClearRunTicket(idcRunProcCment.getTicketInstId());
		}else{
			//流程同意就走这一步
			System.out.println("1:完成工单当前任务[工单号="+idcRunProcCment.getTicketInstId()+",当前任务ID="+String.valueOf(idcRunProcCment.getTaskId())+"]");
			//此处需要使用动态代理。目的是完成任务前必须判断是否是结束工单。工单后，完成引擎功能。
			IdcRunProcCmentService workflowHandForComment = (IdcRunProcCmentService)new WorkflowProxy().bind(this);
			workflowHandForComment.jbpmEndComment(idcRunProcCment);

		}
		return idcRunProcCment.getTicketInstId();
	}

	@Override
	public Boolean deleteTicketQuerySon(Long ticketInstId,Long prodInstId){

		//son是此工单的儿子工单的数量，如果有子工单，那么必须要删除子工单才能删除父工单
		Long son = idcHisProcCmentMapper.deleteTicketQuerySon(ticketInstId, prodInstId);
		return son <= 0;
	}

	/**
	 * 作废工单或者删除工单。
	 * 删除的时候
	 * 1：删除工单只能顺序删除。不能删除其它。(界面提示:存在开通工单编号)
	 * 2：作废工单,
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> rubbishOrDeleteTicket(IdcRunProcCment idcRunProcCment,ApplicationContext applicationContext,Map<String, Object> params) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(params == null || !params.containsKey("ticketInstId") || !params.containsKey("operationSign") ){
			throw new Exception("传递的参数必须包含工单ID=========[ticketInstId]");
		}

  		/*#####################组装需要修改的资源相关信息:由于资源表本来就数据就不多。就直接查询##################*/
		String operationSign=params.get("operationSign").toString();
		String ticketInstId = String.valueOf(params.get("ticketInstId"));  //工单id
		logger.debug("start>>>>>>>>>>>>>>>>>>修改工单的资源状态！！");
		alterResourceStatus(applicationContext,ticketInstId,operationSign,params.get("ticketCategory").toString());  //拿到相关工单的资源
		logger.debug("end>>>>>,,>>>>>>>>>>>>修改工单的资源状态！！");

		//这里只是做验证操作:是否可以删除
 		/*调用存储工程的时候还需要有验证过程..........*/
		idcRunTicketMapper.callRubbishOrDeleteTicket(params);
		String rowcountStr = String.valueOf(params.get("rowcount"));
		if(rowcountStr != null && String.valueOf(DevContext.EXIST).equals(rowcountStr)){
			logger.debug("删除msg:["+params.get("msg")+"]");
			resultMap.put("msg",params.get("msg"));
			resultMap.put("resultFlag",DevContext.EXIST);
			return resultMap;
		}else if(rowcountStr != null && String.valueOf(DevContext.ERROR).equals(rowcountStr)){
			/*如果是已经存在则*/
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+params.get("msg")+"]");
			logger.debug("删除msg:["+params.get("msg")+"]");
			resultMap.put("msg",params.get("msg"));
			resultMap.put("resultFlag",DevContext.ERROR);
			throw new Exception();
		}
		logger.debug(".执行流程引擎的方法............start");
		/********************************* 任务节点完成 ***********************************************/
		logger.debug("工单预制状态:==状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束");
		/**
		 * 1：如果当前工单是同意 且 status=0 修改工单状态为-1
		 * 2：如果当前工单是同意 且 status=1 修改工单状态为1
		 * 3：如果当前工单是驳回-1 且status=0 修改状态为-2
		 * 4：如果当前工单是驳回-1 且status=1 修改状态为1
		 * 5：如果当前工单的formKey是最后一步骤 且status=1 修改状态为2结束
		 * 6:删除到回收站的情况:[后面修改]
		 */
		logger.debug("通过工单ID，获取相应的工单数据");
		IdcHisTicket idcHisTicket = idcHisTicketMapper.getIdcHisTicketByIdTicketInstId(Long.valueOf(ticketInstId));
		Map<String,Object> completeParamMap = settingHisJbpmParam(idcRunProcCment,idcHisTicket);//settingJbpmParam(idcRunProcCment,idcRunTicket);
		completeParamMap.put("taskId",idcHisTicket.getTaskId());
		completeTaskNode(completeParamMap);//删除或者作废流程情况
		logger.debug(".执行流程引擎的方法............end");

		try {
			logger.debug("删除工单后，记录删除操作人的信息............start");
			idcHisTicketMapper.updateByDeleteTicket(ticketInstId,params.get("rubbishOrDeleteMSG").toString());
			logger.debug("删除工单后，记录删除操作人的信息............end");
		} catch (Exception e) {
			e.printStackTrace();
		}

		resultMap.put("msg",params.get("msg"));
		resultMap.put("resultFlag",DevContext.SUCCESS);
		return resultMap;
	}


	//修改工单的资源状态
	public void alterResourceStatus(ApplicationContext applicationContext,String ticketInstIdOld,String operationSign,String ticketCategoryOld) throws Exception{
		/*  判断是删除还是作废，如果是作废，资源要全部释放，如果是删除，是哪个流程删除？资源状态怎么怎么。。。。
		  * 储存过程解决！
		   * */

		if(ticketCategoryOld.equals(CategoryEnum.业务变更流程.value()) || ticketCategoryOld.equals(CategoryEnum.开通变更流程.value())){
			logger.debug(" ----当前流程是  业务变更流程 或者 开通变更流程，不修改资源状态，资源状态由人工手动处理！！！！。。。。。。。。。。----");

		}else {
			logger.debug(" ----作废、删除工单，修改资源的状态。。。。。。。。。。----");

			Map<String, Object> params = new HashMap<>();
			params.put("ticketInstId", ticketInstIdOld);
			params.put("operationSign", operationSign.equals("isDelete") ? 0 : 1);  //0:如果是删除工单,1:作废工单
			idcHisProcCmentMapper.callReturnRubbishOrDeleteTicketID(params);

			String tikcetInstId_Parent = params.get("ticketid_parent").toString();
			String ticketCategory_Parent = params.get("ticketcategory_parent").toString();

			params.put("isRubbishOrDelete", "isRubbishOrDelete");
			logger.debug("父工单的ticket_inst_id=" + tikcetInstId_Parent + "-------   父工单的ticketCategory：" + ticketCategory_Parent + "-------");
			logger.debug(" ----作废、删除工单，修改资源的状态。。。idcContractService.alterResourceStatus_B。。。。。。。----");
			//如果是删除或者作废 业务变更 的工单，要把此工单所有的资源全部清理干净，再恢复父亲工单的资源
			if (ticketCategoryOld.equals(CategoryEnum.业务变更流程.value())) {
				logger.debug("---start----业务变更流程删除作废，开始清空此业务变更工单[" + ticketInstIdOld + "]所有的资源-----------------------");
				idcContractService.alterResourceStatus_B(applicationContext, CategoryEnum.把资源改成空闲状态.value(), Long.valueOf(ticketInstIdOld), params);
				logger.debug("---end----业务变更流程删除作废，开始清空此业务变更工单[" + ticketInstIdOld + "]所有的资源-----------------------");
			}

			logger.debug("---start----流程删除作废后，开始恢复父工单的资源-----------------------");
			idcContractService.alterResourceStatus_B(applicationContext, ticketCategory_Parent, Long.valueOf(tikcetInstId_Parent), params);
			logger.debug("---end----流程删除作废后，恢复父工单的资源完成-----------------------");
		}
	}

	public void handTicketFeed(IdcRunProcCment idcRunProcCment, Integer isRubish) throws Exception{
		System.out.println("修改该工单的评价信息[*******"+idcRunProcCment.getStarNum()+"*******].....start");
		System.out.println("isRubish:代表是否是作废单子 1:作废  0相反");
		IdcHisTicketCommnet idcHisTicketCommnet = new IdcHisTicketCommnet();
		idcHisTicketCommnet.setTicketInstId(idcRunProcCment.getTicketInstId());
		idcHisTicketCommnet.setSatisfaction(idcRunProcCment.getStarNum());
		idcHisTicketCommnet.setFeedback(idcRunProcCment.getComment());
		idcHisTicketCommnet.setEndTime(new Date());
		idcHisTicketCommnetMapper.mergeInto(idcHisTicketCommnet);
		System.out.println("修改该工单的评价信息[*******"+idcRunProcCment.getStarNum()+"*******].....end");
		System.out.println("修改该工单的的结束时间、工单状态等 start");
		idcHisTicketMapper.updateEndTimeByTicketInstId(idcRunProcCment.getTicketInstId(),idcRunProcCment.getStatus(),isRubish);
		System.out.println("修改该工单的的结束时间、工单状态等 end");
	}
	public void handUpdateProduct(IdcRunProcCment idcRunProcCment,String status) throws Exception{
		System.out.println("修改历史工单、正在运行时工单、订单的状态 start");
		System.out.println("status是去取进行中的状态还是结束了的状态值.............");

		//此存储过程仅仅是修改了表IDC_RE_PRODUCT、idc_his_ticket、idc_run_ticket的单子的状态PROCTICKET_STATUS的状态
		actJBPMService.callProcTicketStatus(
				idcRunProcCment.getProdInstId(),
				ProcTicketStatusEnum.getCurrentEnumWithCatalog
						(
								JBPMModelKEY.JBPMModelKEYCurrentWithKey(idcRunProcCment.getProcessDefinitionKey()).catalog(),
								status
						).value(),
				idcRunProcCment.getTicketInstId()
		);
		System.out.println("修改历史工单、正在运行时工单、订单的状态 end");
	}
	/**
	 * 当工单结束时，处理审批意见信息：针对这个方法进行动态代理
	 * @param idcRunProcCment:
	 * @throws Exception
	 */
	@Override
	public Long jbpmEndComment(IdcRunProcCment idcRunProcCment) throws Exception{
		if(idcRunProcCment.getIsEndTicket() != null && idcRunProcCment.getIsEndTicket() == true
				&& idcRunProcCment.getStatus() != null && 1 == idcRunProcCment.getStatus()){
			/**/
			System.out.println("结束该工单------修改该工单的评价信息----结束该工单");
			handTicketFeed(idcRunProcCment,0);
			System.out.println("修改订单状态......................");
			handUpdateProduct(idcRunProcCment,"1");
			System.out.println("清除正在运行的工单所有表信息......................");
			idcRunTicketMapper.callClearRunTicket(idcRunProcCment.getTicketInstId());  //此仅仅是删除了运行工单当前的工单
			return idcRunProcCment.getTicketInstId();
		}
		return null;
	}

	/**
	 * 将审批的东西都保存到缓冲中
	 * @return
	 */
	@Override
	public List<IdcRunProcCment> getIdcRunProcCmentDataIntoRedis(){
		return idcRunProcCmentMapper.getIdcRunProcCmentDataIntoRedis();
	}

	@Override
	public List<Long> getIdsByProdIdAndTicketId(Long prodInstId, Long ticketInstId) {
		return idcRunProcCmentMapper.getIdsByProdIdAndTicketId(prodInstId,ticketInstId);
	}
	/***************************************************************************************
	 *						jbpm-core利用流程变换工单的核心代码区 end
	 ***************************************************************************************/
	/******************************其他**********************************/
	@Override
	public IdcRunProcCment queryCommentByProcInstIdAndTaskId(Long procInstId, String taskid) {
		return idcRunProcCmentMapper.queryCommentByProcInstIdAndTaskId(procInstId,taskid);
	}
	/**
	 *
	 * @param idcRunProcCment
	 * @param createTicketMap
	 * @throws Exception
	 */
	public void mergeDataInto(ApplicationContext context, Map map, IdcRunProcCment idcRunProcCment, Map<String,Object> createTicketMap) throws Exception{

		// 创建停机信息
		if(createTicketMap != null && !createTicketMap.isEmpty()){
			Long prodInstId =  Long.valueOf(String.valueOf(createTicketMap.get("prodInstId")));
			Integer prodCategory =  Integer.valueOf(String.valueOf(createTicketMap.get("prodCategory")));
			String catalog =  String.valueOf(createTicketMap.get("category"));
			ResponseJSON responseJSON = actJBPMService.singleCreateTicket(context,map,prodInstId,prodCategory,null,null,catalog,idcRunProcCment.getTicketInstId());
			Boolean isSuccess= responseJSON.isSuccess();
			if(isSuccess){
				Long ticketInstId =  Long.valueOf(String.valueOf(responseJSON.getResult()));
					/*创建合同和工单关联*/
				idcRunProcCment.setTicketInstId(ticketInstId);
				pauseHandlerData(idcRunProcCment);
			}
		}
	}

	/**
	 * 停机信息展示
	 * @param idcRunProcCment
	 * @throws Exception
	 */
	public void pauseHandlerData(IdcRunProcCment idcRunProcCment) throws  Exception{
		IdcRunTicketPause idcRunTicketPause=new IdcRunTicketPause();
		idcRunTicketPause.setTicketInstId(idcRunProcCment.getTicketInstId());

		idcRunTicketPause.setReservestart(idcRunProcCment.getReservestart());
		//添加结束停机时间
		idcRunTicketPause.setReserveend(idcRunProcCment.getReserveend());
		//添加停机类型是临时停机还是长期停机
		idcRunTicketPause.setPausetype(idcRunProcCment.getPauseTpye());
		//添加备注信息  idcRunTicketPause表的Applydesc信息和idcRunProcCment的Comment信息对应
		idcRunTicketPause.setApplydesc(idcRunProcCment.getComment());

		IdcHisTicketPause idcHisTicketPause=new IdcHisTicketPause();
		//把停机运行表的数据复制到停机历史表中
		BeanUtils.copyProperties(idcRunTicketPause,idcHisTicketPause);
		//开始持久化到数据库中
		idcRunTicketPauseMapper.updateByData(idcRunTicketPause);
		idcHisTicketPauseMapper.updateByData(idcHisTicketPause);
	}

	/**
	 *
	 * @param ticketInstId:工单ID
	 * @return
	 */
	@Override
	public List<IdcRunProcCment> queryRunProcCommentQueryData(String ticketInstId) {
		return idcRunProcCmentMapper.queryRunProcCommentQueryData(ticketInstId);
	}

	/**
	 *
	 */
	@Override
	public IdcRunProcCment queryByTicketInstId(Long ticketInstId) {
		return idcRunProcCmentMapper.queryByTicketInstId(ticketInstId);
	}

}

