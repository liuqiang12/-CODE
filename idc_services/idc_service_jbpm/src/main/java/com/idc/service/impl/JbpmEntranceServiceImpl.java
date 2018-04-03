package com.idc.service.impl;

import com.bpm.ActivitiUtil;
import com.bpm.ProcessCoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.IdcContractService;
import com.idc.service.JbpmEntranceService;
import com.idc.utils.CategoryEnum;
import com.idc.utils.JbpmStatusEnum;
import com.idc.utils.SendEmailEnum;
import com.idc.utils.TASKNodeURL;
import modules.utils.ResponseJSON;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import utils.DevContext;
import utils.SendEmailUtils;
import utils.typeHelper.GsonUtil;

import java.util.*;
import java.util.concurrent.*;

/**
 * 流程创建入口
 */
@Service("jbpmEntranceService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class JbpmEntranceServiceImpl extends RemoteIspServiceImpl implements JbpmEntranceService {
	private Logger logger = LoggerFactory.getLogger(JbpmEntranceServiceImpl.class);
	@Autowired
	private IdcRunTicketMapper idcRunTicketMapper;
	@Autowired
	private IdcHisTicketMapper idcHisTicketMapper;
	@Autowired
	private IdcReProductMapper idcReProductMapper;
	/* 必备的流程引入类 start*/
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProcessCoreService processCoreService;//流程服务
	@Autowired
	public IdcContractService idcContractService;//
	@Autowired
	private IdcReProddefMapper idcReProddefMapper;//
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;//
	@Autowired
	private IdcRunTicketResourceMapper idcRunTicketResourceMapper;//
	@Autowired
	private ProcessEngine processEngine;//
    /* 必备的流程引入类 end*/
	/**
	 * 创建相应的工单
	 * @param applicationContext
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseJSON jbpmTicketEntrancet(ApplicationContext applicationContext, Map map) throws Exception {
		ResponseJSON result = new ResponseJSON();
		System.out.println(">>>>>>>>>>>>创建工单表信息------不涉及资源信息--------start");
		idcRunTicketMapper.callJbpmTicketEntrancet(map);
		System.out.println(">>>>>>>>>>>>创建工单表信息------不涉及资源信息--------end");
		/*启动流程实例...............*/
		logger.debug("启动流程实例...............");
		jbpmTicketDeploy(map);
		result.setMsg("创建成功!");
		//然后在保存业务类型
		//创建工单的时候，需要将客户信息复制一遍
		/* 如果需要复制则:判断是否是具有重写 */
		if(map.get("parentId") != null && !map.containsKey("rechageProductInfo") && (map.get("ticketCategoryFrom") != null && !map.get("ticketCategoryFrom").equals(CategoryEnum.业务变更流程))){

			//第一次申请不会走这里面
			map.put("ticketInstId",map.get("ticketInstId"));
			map.put("parentTicketInstId",map.get("parentId"));
			map.put("prodInstId",map.get("prodInstId"));
			procductDefModelHandlerData(map);
		}

		result.setSuccess(true);
		result.setResult(map);

		System.out.println("----进入新建工单的方法，准备调用修改资源的方法！！--------------------------");
		System.out.println("----修改资源状态、或修改资源的工单ID 通用方法------开始");
		Long ticketInstId_New = Long.valueOf(String.valueOf(((Map)result.getResult()).get("ticketInstId")));
		IdcRunTicket idcRunTicket = idcRunTicketMapper.getTicketObjMapByTicketInstId(ticketInstId_New);  //此查询的是历史表
		Map<String,Object> params=new HashMap<>();
        params.putAll(map);
		params.put("Before_ticketStatus_Int",idcRunTicket.getTicketStatus());
		/*下面的 -1 非常重要！！！，因为申请工单，申请成功后，新的工单已经是第二步了，但是要改资源的工单ID啊，所以要-1*/
		params.put("Before_formKey_Int", TASKNodeURL.numbuerWithFormKey(idcRunTicket.getFormKey())-1);

		/*申请 业务变更流程注意：申请  业务变更流程 ，不能走下面的修改资源信息的方法，因为申请业务变更流程，需求表的信息还没有修改，导致下面alterResourceStatus_A方法
		* 里面查询出来的数据是错误的，把资源修改出错 */
		if(!(map.get("ticketCategoryTo") != null && String.valueOf(map.get("ticketCategoryTo")).equals(CategoryEnum.业务变更流程.value()))){
			idcContractService.alterResourceStatus_A(applicationContext,ticketInstId_New,params);
			System.out.println("----修改资源状态、或修改资源的工单ID 通用方法------结束");
		}
		return result;
	}
	public Long procductDefModelHandlerData(Map<String,Object> definedArrayMap) throws Exception{
		//Long parentTicketInstId,Long prodInstId,Long currentTicketInstId
		/*if(definedArrayMap == null || definedArrayMap.isEmpty()){
			return null;
		}*/
		//idcReProductMapper.callProcSaveProductDefModelArry(definedArrayMap);
		String ticketInstId = String.valueOf(definedArrayMap.get("ticketInstId"));
		String parentTicketInstId = String.valueOf(definedArrayMap.get("parentTicketInstId"));
		String prodInstId = String.valueOf(definedArrayMap.get("prodInstId"));

		Map<String,Object> proddefMaps = new HashMap<String,Object>();
		proddefMaps.put("newTicketInstId",ticketInstId);
		proddefMaps.put("oldTicketInstId",parentTicketInstId);
		proddefMaps.put("prodInstId",prodInstId);
		idcReProductMapper.copyIdcReProddef(proddefMaps);

		/*String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
		String isEXISTStr = String.valueOf(definedArrayMap.get("isEXIST"));*/

		/*if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
			return Long.valueOf(DevContext.SUCCESS);
		}else{
			*//*如果是已经存在则*//*
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
			if(isEXISTStr != null && "1".equals(isEXISTStr)){
				return DevContext.EXIST;
			}else{
				throw new Exception();
			}
		}*/
		return Long.valueOf(DevContext.SUCCESS);

	}
	/**
	 * 创建工单必须的参数配置
	 */
	public Map<String,Object> jbpmTicketEntrancetParam(Map<String,Object> map) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		logger.debug(" 创建订单，同时创建工单并且开启工单的过程。。。。。。。。。。。。。 ");
		SysUserinfo principal = (SysUserinfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CreateTicketModel createTicketModel= GetCreateTicketModel(map,principal);
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			paramMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}
		paramMap.putAll(map);
		paramMap.put("auditName",principal.getNick());/*审批人名字*/
		paramMap.put("auditId",principal.getId());/*审批人ID*/
		return paramMap ;
	}
	CreateTicketModel GetCreateTicketModel(Map<String,Object> createTicketParams,SysUserinfo principal){
		CreateTicketModel createTicketModel = idcReProductMapper.getCreateTicketModelByProdId(createTicketParams);
		createTicketModel.setApplyUserName(principal.getNick());
		createTicketModel.setApplyUserId(principal.getId());
		createTicketModel.setParentTicketId(Long.valueOf(String.valueOf(createTicketParams.get("parentId"))));
		createTicketModel.setTicketStatus(JbpmStatusEnum.流程初始化.value());
		return createTicketModel;

	}
	/**
	 * jbpm工单部署情况
	 * @throws Exception
	 */
	public Long jbpmTicketDeploy(Map map) throws Exception{
		String ticketInstId = String.valueOf(map.get("ticketInstId"));
		logger.debug("=================================新开启的工单Id:ticketInstId=["+ticketInstId+"]=================================");
		List<CreateTicketModel> createTicketModelList = (List<CreateTicketModel>)map.get("jbpmTicketPropertyItems");
		CreateTicketModel jbpmTicketProperty = createTicketModelList.get(0);
		String BUSINESS_KEY = "prodInstId:"+jbpmTicketProperty.getProdInstId()+",ticketInstId:"+ticketInstId;
		String processDefinitionKey = jbpmTicketProperty.getProcessdefinitonkeyTo();

		//---------------------------------------流程实例化 start-----------------------------------------
        /*项目启动的时候需要控制一下监听*/
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("taskListener","taskListener");
		ProcessInstance processInstance = runtimeService//与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey,BUSINESS_KEY);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		logger.debug("流程实例ID:"+processInstance.getId());//流程实例ID    101
		logger.debug("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID   helloworld:1:4

		//---------------------------------------流程实例化 end-----------------------------------------
		//------------------------------------- 工单修改且 创建历史工单和预受理工单 start------------------------------------------
		/* 修改工单情况 */
		map.put("procInstId",processInstance.getId());
		map.put("processDefinitionId",processInstance.getProcessDefinitionId());
		idcRunTicketMapper.callUpdateORCopyTicketInfo(map);   //复制资源表信息
		//------------------------------------- 工单修改且 创建历史工单和预受理工单 end------------------------------------------
        /*                     通过工单id获取任务ID完成第一步工单申请动作 [add]                     */
        /****                              完成第一步                                  ****/
        Map<String,Object> completeParamMap = new HashMap<String,Object>();
		/********************************* 完成 ***********************************************/
		completeParamMap.putAll(map);
		updateJbpmParam(completeParamMap,JbpmStatusEnum.流程同意.value());//修改流程的状态。同意
		completeParamMap.put("status", JbpmStatusEnum.审批同意.value());//第一步处于同意状态
		completeParamMap.put("comment", DevContext.JBPM_DEFAULT_COMMON);/*同意*/
		completeParamMap.put("pre_taskId",map.get("taskId"));
		/**** 查询正在运行的工单的任务名称 ***/
		String pre_taskName = idcRunTicketMapper.getTaskName(ticketInstId);
		//IdcRunTicket asdf = idcRunTicketMapper.getTicketObjMapByTicketInstId(Long.valueOf(ticketInstId));
		completeParamMap.put("pre_taskName",pre_taskName);
		completeTaskNode(completeParamMap);//完成第一步：申请任务节点

		return null;
	}
	public Map<String,Object> updateJbpmParam(Map<String,Object> map,Integer ticketStatus){
		Set<Map.Entry<String, Object>> mapSet = map.entrySet();
		Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> mapTmp = it.next();
			if("jbpmTicketPropertyItems".equals(mapTmp.getKey())){
				((List<CreateTicketModel>)mapTmp.getValue()).get(0).setTicketStatus(ticketStatus);
				/******        ticketStatus:1         *****/
				logger.debug("******   工单只有同意和不同意。同意则获取下一个form。同意。则         *****");
			}
		}
		return map;
	}
	public Map<String,Object> settingHisJbpmParam(IdcRunProcCment idcRunProcCment, IdcHisTicket idcHisTicket){
		Map<String,Object> completeParamMap = new HashMap<String,Object>();
		CreateTicketModel createTicketModel = new CreateTicketModel();
		//其中还有一个可以直接作为判断操作:删除操作
		if(idcRunProcCment.getOperationSign() != null && "isDelete".equalsIgnoreCase(idcRunProcCment.getOperationSign())){
			createTicketModel.setTicketStatus(JbpmStatusEnum.删除到回收站.value());/******删除作废，直接******/
		}
		if(idcRunProcCment.getOperationSign() != null && "isRubbish".equalsIgnoreCase(idcRunProcCment.getOperationSign())){
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程作废.value());/******作废，直接******/
		}
		createTicketModel.setTicketCategoryTo(idcHisTicket.getTicketCategory());
		createTicketModel.setProdInstId(String.valueOf(idcHisTicket.getProdInstId()));
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();/*** 删除到回收站的情况，必须外界传递参数:后面调整 ***/
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			completeParamMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}
		/*配置信息*/

		completeParamMap.put("status", 1);//审批状态;同意 or 不同意
		completeParamMap.put("comment", idcRunProcCment.getComment());/*审批内容*/
		completeParamMap.put("pre_taskId",idcHisTicket.getTaskId());
		completeParamMap.put("pre_taskName",idcHisTicket.getTaskName());/**** 查询正在运行的工单的任务名称 ***/
		completeParamMap.put("auditName",idcRunProcCment.getAuthor());/*审批人名字*/
		completeParamMap.put("auditId",idcRunProcCment.getAuthorId());/*审批人ID*/
		completeParamMap.put("processDefinitionId",idcHisTicket.getProcDefId());/*流程定义ID*/
		completeParamMap.put("ticketInstId",idcHisTicket.getId());/*工单ID*/
		completeParamMap.put("procInstId",idcHisTicket.getProcInstId());/*流程ID*/
		completeParamMap.put("satisfaction",idcRunProcCment.getStarNum());/*只有最后一步需要评分*/
		return completeParamMap;
	}
	public Map<String,Object> settingJbpmParam(IdcRunProcCment idcRunProcCment, IdcRunTicket idcRunTicket){
		Map<String,Object> completeParamMap = new HashMap<String,Object>();
		CreateTicketModel createTicketModel = new CreateTicketModel();
		if (idcRunProcCment.getStatus() == JbpmStatusEnum.审批同意.value()){
			createTicketModel.setTicketStatus(JbpmStatusEnum.审批同意.value());
		}else if(idcRunTicket.getTicketStatus() == JbpmStatusEnum.流程同意.value() && idcRunProcCment.getStatus() == 0 ){
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程驳回或不同意.value());
		}else if(idcRunTicket.getTicketStatus() == JbpmStatusEnum.流程驳回或不同意.value() && idcRunProcCment.getStatus() == 0 ){
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程作废.value());
		}
		Boolean isLastStepTicket = idcRunTicketMapper.getIsLastStepTicket(idcRunTicket.getFormKey());//false最后一步
		if(!isLastStepTicket && idcRunTicket.getTicketStatus() == JbpmStatusEnum.审批同意.value()){
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程结束.value());
		}
		createTicketModel.setTicketCategoryTo(idcRunTicket.getTicketCategory());
		/*** 删除到回收站的情况，必须外界传递参数:后面调整 ***/
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			completeParamMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}

		completeParamMap.put("status", idcRunProcCment.getStatus());//审批状态;同意 or 不同意
		completeParamMap.put("comment", idcRunProcCment.getComment());/*审批内容*/
		completeParamMap.put("pre_taskId",idcRunTicket.getTaskId());
		completeParamMap.put("pre_taskName",idcRunTicket.getTaskName());/**** 查询正在运行的工单的任务名称 ***/
		completeParamMap.put("auditName",idcRunProcCment.getAuthor());/*审批人名字*/
		completeParamMap.put("auditId",idcRunProcCment.getAuthorId());/*审批人ID*/
		completeParamMap.put("processDefinitionId",idcRunTicket.getProcDefId());/*流程定义ID*/
		completeParamMap.put("ticketInstId",idcRunTicket.getId());/*工单ID*/
		completeParamMap.put("procInstId",idcRunTicket.getProcInstId());/*流程ID*/
		completeParamMap.put("satisfaction",idcRunProcCment.getStarNum());/*只有最后一步需要评分*/
		return completeParamMap;
	}
	public Map<String,Object> settingJbpmParam11(CreateTicketModel createTicketModel ){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			paramMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}
		return paramMap;
	}

	/**
	 * 完成任务
	 * @param //idcRunTicket
	 * @param //status:是否驳回的状态0 代表驳回   1代表同意
	 * @param //comment
	 * @throws Exception
	 */
	public void completeTaskNode(Map<String,Object> completeParamMap) throws  Exception{
		String taskId = String.valueOf(completeParamMap.get("taskId"));
		String procInstId = String.valueOf(completeParamMap.get("procInstId"));
		logger.debug("---流程实例procInstId:["+procInstId+"],目的是获取下一个流程的任务TaskNode信息---");
		logger.debug("---工单属于同意吗?1:代表同意   0代表不同意---"+completeParamMap.get("status"));
		String comment = String.valueOf(completeParamMap.get("comment"));
		String status = String.valueOf(completeParamMap.get("status"));
		SysUserinfo principal = (SysUserinfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/********   审核人名称  和   审核人ID  ************/
		String auditName = principal.getNick();
		String auditId = String.valueOf(principal.getId());

        /*完成当前任务 start*/
		try {
			if(completeParamMap.get("jbpmTicketPropertyItems") != null){
                /********** 判断该单子是否是结束状态或者删除状态。如果是则将流程结束 ***********/
                List<CreateTicketModel> createTicketModelList = (List<CreateTicketModel>)completeParamMap.get("jbpmTicketPropertyItems");
                CreateTicketModel createTicketModel = createTicketModelList.get(0);
                if(createTicketModel != null){
                    Integer ticketStatus = createTicketModel.getTicketStatus();
                    /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
                    if(ticketStatus == JbpmStatusEnum.流程作废.value() ){
                        logger.debug("-流程作废，需要查询结束流程------------------------------");
                        processCoreService.endProcess(taskId);
                    }else if(ticketStatus == JbpmStatusEnum.删除到回收站.value()){
                        logger.debug("-删除到回收站:1、如果流程已经结束。则直接删除正在运行的工单。同时修改历史工单状态" +
                                "2、如果流程未结束。则直接结束流程------------------------------");
                        /**判断流程是否结束，查询正在执行的执行对象表*/
                        ProcessInstance rpi = runtimeService
                                .createProcessInstanceQuery()//创建流程实例查询对象
                                .processInstanceId(procInstId)
                                .singleResult();
                        if(rpi == null){
                            logger.debug("---------------流程已经结束---------------");
                        }else{
                            processCoreService.endProcess(taskId);
                        }
                    }else{
                        logger.debug("--------------------正常操作------------------------------");
                        /************* 流程节点处理 同意与不同意:即有网关节点的时候  start**************/
                        //判断该任务是否有条件:  start
                        List<PvmTransition> outgoingTransitions = processCoreService.getOutgoingTransitions(procInstId);
                        if(outgoingTransitions != null && !outgoingTransitions.isEmpty()){
                            Map<String,Object> variables = new HashMap<String,Object>();
                            variables.put("apply_status",status);//智能做法：根据上面匹配出参数key
                            //任务完成，设置本签收人员
                            Authentication.setAuthenticatedUserId(auditId);//设置审核人
                            taskService.addComment(taskId,procInstId,comment);
                            taskService.complete(taskId,variables);
                        }else{
                            //任务完成，设置本签收人员
                            Authentication.setAuthenticatedUserId(auditId);//设置审核人
                            taskService.addComment(taskId,procInstId, comment);
                            taskService.complete(taskId);
                        }
                        /************* 流程节点处理 同意与不同意:即有网关节点的时候  end**************/
                        /*完成当前任务 end*/
                    }
                }
            }
		} catch (Exception e) {
			logger.debug("兄弟，流程报错了！！，可能是网络或者其他原因导致流程走了工单没有跟上，具体报错信息如下：");
			e.printStackTrace();
			logger.debug("下面尝试继续走工单！！");
			completeTicket(completeParamMap,auditName,auditId,procInstId,taskId);
			return;
		}

		logger.debug("流程走成功了，下面开始走工单！！");
		completeTicket(completeParamMap,auditName,auditId,procInstId,taskId);
	}

	/*
	* 走工单
	* */
	public void completeTicket(Map<String,Object> completeParamMap,String auditName,String auditId,String procInstId,String taskId) throws  Exception{

		/*
		* 上面的流程走成功了,如果下面的工单报错,没有跟上,就要手动让上面的流程回退到上一步流程里面去
		* */
		try {
			completeParamMap.put("auditName",auditName);
			completeParamMap.put("auditId",auditId);
			completeParamMap.remove("msg");
			completeParamMap.remove("rowcount");
			/****************** 需要传递的参数有
			 *   流程实例ID,审批人名称,审批人ID，工单状态。还需要得到【formKey】
			 * ***********************/
		/*  这里只需要判断该单子是否是：结束[只需要修改工单历史表信息]。[作废:只需要修改工单历史表信息]。删除工单[只需要修改历史表信息]。 */
			idcRunTicketMapper.callTicketTaskComplete(completeParamMap);
		} catch (Exception e) {
			logger.debug("流程走下去了，但是工单没有跟上，原因如下：");
			e.printStackTrace();
			logger.debug("开始把已经走下去的流程手动回滚到上一步！！！");
			//开始回滚流程
			ActivitiUtil.TaskRollBack(taskId);
		}


		/*
		*
		* 申请、审批完成后，就要给下一个节点的所有 有权限的审批人发送邮件，提醒他审批相关工单！！
		* */

		//工单ID
		String ticketInstId = String.valueOf(completeParamMap.get("ticketInstId"));

		//得到下级审批人的邮件
		List<Map<String, Object>> candidateEmailList = idcRunTicketMapper.getCandidateEmail(String.valueOf(ticketInstId));
		//审批人
		StringBuilder approverList=new StringBuilder();
		for (Map<String, Object> maps: candidateEmailList) {
			if(maps.get("NICK") != null && maps.get("NICK") != ""){
				approverList.append(maps.get("NICK"));
				approverList.append(",");
			}
		}

		if(approverList != null && approverList.length()>0){
			//把最后一个逗号去掉
			approverList.deleteCharAt(approverList.length()-1);
		}

		try {
			if(1 == 1){
                IdcRunTicket ticket = idcRunTicketMapper.getTicketObjMapByTicketInstId(Long.valueOf(ticketInstId));
                if(candidateEmailList != null && candidateEmailList.size()>0){
                    for (Map<String, Object> candidateEmail : candidateEmailList) {
                        if(candidateEmail != null
                                && candidateEmail.get("EMAIL")!=null
                                && candidateEmail.get("EMAIL")!=""
                                && candidateEmail.get("NICK")!=null
                                && candidateEmail.get("NICK") != ""){
                            // 设置发件人地址、收件人地址和邮件标题
                            String email_Title="业务名称为： 《  "+ticket.getBusname()+"  》 的工单需要您审批！";
                            // 设置邮件内容
                            String email_Text="尊敬的 " + candidateEmail.get("NICK")+" 。"
                                    + "业务名称为 ："
                                    +ticket.getBusname()
                                    +"。 此工单属于："
                                    +CategoryEnum.getTicketName(ticket.getTicketCategory())
                                    +" 。 此工单审批人包括有："
                                    +approverList
                                    +" 。 您可以自己审批或者和此名单中的其他人协商审批，请及时处理！"
                                    + "立即查看： http://223.85.57.86:88/idc/login.jsp"
                                    +" 。 ";
                            System.out.println("准备发邮件了！！！邮件标题是："+email_Title);
                            System.out.println("准备发邮件了！！！邮件内容是："+email_Text);

                            //收件人邮箱
                            String receiver_email = String.valueOf(candidateEmail.get("EMAIL"));
                            System.out.println("c："+receiver_email);
                            SendEmailUtils.sendEmail(DevContext.EMAIL_ADRESS_FROM,
									receiver_email,
                                    email_Title,
                                    email_Text,
                                    DevContext.EMAIL_USER,
                                    DevContext.EMAIL_PASSWORD,
                                    DevContext.SERVER_MAIL_SMTP_HOST,
                                    DevContext.SERVER_MAIL_SMTP_AUTH,
                                    DevContext.SERVER_MAIL_SMTP_PORT,
                                    Boolean.valueOf(DevContext.SERVER_MAIL_SMTP_SSL_ENABLE));
                        }
                    }
                }
            }
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("发送邮件错误！！！");
		}


		/**
		 * 开启调用ISP接口信息
		 * 1：开通完成
		 * 2：开通变更结束
		 */
		try{
			loadIspThread(completeParamMap);
		}catch (Exception e){
			logger.debug("ISP调用失败。请查看数据源文件是否格式或者内容正确:File=[]");
		}
		/*资源回收情况*/
		try{
			loadRecoverThread(completeParamMap);
		}catch (Exception e){
			logger.debug("修改资源回收报错啦！！！！！！！！！");
		}
	}

	/**
	 * 资源回收信息
	 */
	public void loadRecoverThread(final Map<String,Object> completeParamMap){
		ExecutorService rootISPThread = Executors.newCachedThreadPool();//线程池里面的线程数会动态变化，并可在线程线被移除前重用
		rootISPThread.execute(new Runnable() {    //接受一个Runnable实例
			public void run() {
				try{
					ExecutorService executorService = Executors.newCachedThreadPool();
					List<Future<String>> resultList = new ArrayList<Future<String>>();

					List<CreateTicketModel> createTicketModelList = (List<CreateTicketModel>)completeParamMap.get("jbpmTicketPropertyItems");
					CreateTicketModel createTicketModel = createTicketModelList.get(0);
					if(createTicketModel != null) {
						Integer ticketStatus = createTicketModel.getTicketStatus();
						Long ticketInstId = Long.valueOf(String.valueOf(completeParamMap.get("ticketInstId")));
						logger.debug("======工单Id:ticketInstId=["+ticketInstId+"]、工单状态:ticketStatus=["+ticketStatus+"]======");
					/*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
						IdcHisTicket idcHisTicket = idcHisTicketMapper.getIdcHisTicketByIdTicketInstId(ticketInstId);
						if(idcHisTicket != null){
							System.out.println(idcHisTicket.getSerialNumber());
						/*开启两个线程任务并执行：删除资源数据*/
							//使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
							Future<String> future = executorService.submit(new RemoteTaskWithResult(ticketInstId,ticketStatus));
							//将任务执行结果存储到List中
							resultList.add(future);
							//遍历任务的结果
							for (Future<String> fs : resultList){
								try{
									while(!fs.isDone()) {//Future返回如果没有完成，则一直循环等待，直到Future返回完成
										System.out.println(fs.get());     //打印各个线程（任务）执行的结果
									}
								}catch(InterruptedException e){
									e.printStackTrace();
								}catch(ExecutionException e){
									e.printStackTrace();
								}finally{
									//启动一次顺序关闭，执行以前提交的任务，但不接受新任务
									executorService.shutdown();
								}
							}
						}

					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		rootISPThread.shutdown();
	}

	/**
	 * 通过工单ID。获取isp接口所需要的数据。
	 */
	public void loadIspThread(final Map<String,Object> completeParamMap){
		ExecutorService rootISPThread = Executors.newCachedThreadPool();//线程池里面的线程数会动态变化，并可在线程线被移除前重用
		rootISPThread.execute(new Runnable() {    //接受一个Runnable实例
			public void run() {
				try{
					ExecutorService executorService = Executors.newCachedThreadPool();
					List<Future<String>> resultList = new ArrayList<Future<String>>();

					List<CreateTicketModel> createTicketModelList = (List<CreateTicketModel>)completeParamMap.get("jbpmTicketPropertyItems");
					CreateTicketModel createTicketModel = createTicketModelList.get(0);
					if(createTicketModel != null) {
						Integer ticketStatus = createTicketModel.getTicketStatus();
						Long ticketInstId = Long.valueOf(String.valueOf(completeParamMap.get("ticketInstId")));
						logger.debug("======工单Id:ticketInstId=["+ticketInstId+"]、工单状态:ticketStatus=["+ticketStatus+"]======");
					/*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
						IdcHisTicket idcHisTicket = idcHisTicketMapper.getIdcHisTicketByIdTicketInstId(ticketInstId);
						if(idcHisTicket != null){
							System.out.println(idcHisTicket.getSerialNumber());
						/*开启两个线程任务并执行：删除资源数据*/
							//使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
							Future<String> resourcefuture = executorService.submit(new RemoteResourceWithResult(ticketInstId,ticketStatus));
							Future<String> future = executorService.submit(new RemoteTaskWithResult(ticketInstId,ticketStatus));

							//将任务执行结果存储到List中
							resultList.add(future);
							resultList.add(resourcefuture);
							//遍历任务的结果
							for (Future<String> fs : resultList){
								try{
									while(!fs.isDone()) {//Future返回如果没有完成，则一直循环等待，直到Future返回完成
										System.out.println(fs.get());     //打印各个线程（任务）执行的结果
									}
								}catch(InterruptedException e){
									e.printStackTrace();
								}catch(ExecutionException e){
									e.printStackTrace();
								}finally{
									//启动一次顺序关闭，执行以前提交的任务，但不接受新任务
									executorService.shutdown();
								}
							}
						}

					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		rootISPThread.shutdown();
	}
}

class RemoteTaskWithResult implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(RemoteTaskWithResult.class);
	private static RestTemplate restTemplate = new RestTemplate();
	private Long  ticketInstId;
	private Integer ticketStatus;

	public RemoteTaskWithResult(Long ticketInstId,Integer ticketStatus){
		this.ticketInstId = ticketInstId;
		this.ticketStatus = ticketStatus;
	}

	/**
	 * 任务的具体过程，一旦任务传给ExecutorService的submit方法，
	 * 则该方法自动在一个线程上执行
	 */
	public String call() throws Exception {
		try{
			/*
		* 要返回值。则必须使用rest接口调用:bean监听没有返回值。该类不能自动注入。目前只能采用rest调用实现
		* */
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ticketInstId",ticketInstId);
			map.put("ticketStatus",ticketStatus);
		/*工单结束 删除 作废情况调用方法*/
			//更新资源信息
			//if(ticketStatus == JbpmStatusEnum.流程作废.value() || ticketStatus == JbpmStatusEnum.删除到回收站.value() || ticketStatus == JbpmStatusEnum.流程结束.value()){
				ResponseJSON responseJSON = restTemplate.postForObject(DevContext.REST_URL+"isp/ispResourceUpload.do",GsonUtil.object2Json(map), ResponseJSON.class);
				System.out.println(responseJSON.getResult());
				logger.debug("======call()方法被自动调用 工单Id:ticketInstId=["+ticketInstId+"]======");
			//}
			//该返回结果将被Future的get方法得到
			return "call()方法被自动调用，任务返回的结果是：" + ticketInstId + "    " + Thread.currentThread().getName();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}

class RemoteResourceWithResult implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(RemoteTaskWithResult.class);
	private static RestTemplate restTemplate = new RestTemplate();
	private Long  ticketInstId;
	private Integer ticketStatus;

	public RemoteResourceWithResult(Long ticketInstId,Integer ticketStatus){
		this.ticketInstId = ticketInstId;
		this.ticketStatus = ticketStatus;
	}

	/**
	 * 任务的具体过程，一旦任务传给ExecutorService的submit方法，
	 * 则该方法自动在一个线程上执行
	 */
	public String call() throws Exception {
		try{
			/*
		* 要返回值。则必须使用rest接口调用:bean监听没有返回值。该类不能自动注入。目前只能采用rest调用实现
		* */
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ticketInstId",ticketInstId);
			map.put("ticketStatus",ticketStatus);
			/*工单结束 删除 作废情况调用方法*/
			//更新资源信息
			if(ticketStatus == JbpmStatusEnum.流程结束.value() || ticketStatus == JbpmStatusEnum.流程驳回或不同意.value()){
				//call存储过程
				ResponseJSON responseJSON = restTemplate.postForObject(DevContext.REST_URL+"isp/recoverRsourceUpload.do",GsonUtil.object2Json(map), ResponseJSON.class);
				System.out.println(responseJSON.getResult());
				logger.debug("======call()方法被自动调用 工单Id:ticketInstId=["+ticketInstId+"]======");
			}
			//该返回结果将被Future的get方法得到
			return "call()方法被自动调用，任务返回的结果是：" + ticketInstId + "    " + Thread.currentThread().getName();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}



