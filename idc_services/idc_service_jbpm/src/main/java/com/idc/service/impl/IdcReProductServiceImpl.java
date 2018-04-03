package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.ActJBPMService;
import com.idc.service.IdcContractService;
import com.idc.service.IdcReProductService;
import com.idc.service.IdcRunProcCmentService;
import com.idc.utils.CategoryEnum;
import com.idc.utils.JbpmStatusEnum;
import com.idc.utils.ProcTicketStatusEnum;
import modules.utils.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import utils.DevContext;
import utils.typeHelper.MapHelper;

import java.util.*;

@Service("idcReProductService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcReProductServiceImpl extends JbpmEntranceServiceImpl implements IdcReProductService {
	private Logger logger = LoggerFactory.getLogger(IdcReProductServiceImpl.class);
	@Autowired
	private IdcReProductMapper idcReProductMapper;
	@Autowired
	private IdcReProddefMapper idcReProddefMapper;//模型
	@Autowired
	private ActJBPMService actJBPMService;
	@Autowired
	private IdcRunTicketPauseMapper idcRunTicketPauseMapper;
	@Autowired
	private IdcHisTicketPauseMapper idcHisTicketPauseMapper;

	@Autowired
	private IdcRunProcCmentService idcRunProcCmentService;

	@Autowired
	private IdcContractService idcContractService;

	@Autowired
	private IdcJbpmTaskNodeMapper idcJbpmTaskNodeMapper;

	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;

	@Autowired
	private IdcRunTicketResourceMapper idcRunTicketResourceMapper;


	@Override
	public List<IdcReProduct> queryGridFilterListPage(PageBean<IdcReProduct> page, IdcReProduct idcReProduct) {
		//分页情况
		page.setParams(MapHelper.queryCondition(idcReProduct));
		return idcReProductMapper.queryGridFilterListPage(page);
	}
	@Override
	public ProductCategory getProductCategoryByProdInstId(String prodInstId,Long ticketInstId){
		return idcReProductMapper.getProductCategoryByProdInstId(prodInstId,ticketInstId);
	}
	/**
	 * 通过ID获取实体类信息
	 * @param id
	 * @return
	 */
	@Override
	public IdcReProduct getModelByProductId(Long id){
		return idcReProductMapper.getModelByProductId(id);
	}

	@Override
	public String getIdcNameByTicketID(String ticketInstId){
		return idcReProductMapper.getIdcNameByTicketID(ticketInstId);
	}
	@Override
	public IdcReProduct getFilterModelByProductId(Long id){
		return idcReProductMapper.getFilterModelByProductId(id);
	}

    @Override
    public IdcReProduct getModelWithCatalogModelByProductId(Long id) {
        return idcReProductMapper.getModelWithCatalogModelByProductId(id);
    }

	public Long procductDefModelHandlerData(IdcReProduct idcReProduct,Long ticketInstId,Long prodInstId) throws  Exception{
		Map<String,Object> definedArrayMap = wapperDefModelMap(idcReProduct,prodInstId,ticketInstId);
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

	public void copyIdcReProddef(Long newTicketInstId,Long oldTicketInstId) throws  Exception{
		Map<String,Object> params=new HashMap();
		params.put("newTicketInstId",newTicketInstId);
		params.put("oldTicketInstId",oldTicketInstId);
		idcReProductMapper.copyIdcReProddef(params);
		String rowcountStr = String.valueOf(params.get("rowcount"));
		String isEXISTStr = String.valueOf(params.get("isEXIST"));

		/*if(rowcountStr != null && !"0".equals(rowcountStr)){
			logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+params.get("msg")+"]");
			return Long.valueOf(DevContext.SUCCESS);
		}else{
			*//*如果是已经存在则*//*
			logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+params.get("msg")+"]");
			if(isEXISTStr != null && "1".equals(isEXISTStr)){
				return DevContext.EXIST;
			}else{
				throw new Exception();
			}
		}*/
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

	public Map<String,Object> wapperCreateTicketModelMap(CreateTicketModel createTicketModel){
		Map<String,Object> definedArrayMap = new HashMap<String,Object>();

		return definedArrayMap;
	}


	public Map<String,Object> wapperDefModelMap(IdcReProduct idcReProduct,Long prodInstId,Long ticketInstId){
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
				//idcReProduct.getIdcReRackModel().setTicketInstId(ticketInstId);
				idcReRackList.add(idcReProduct.getIdcReRackModel());
				definedArrayMap.put("rackIitemList",idcReRackList);
			}
			if(idcReProduct.getIdcRePortModel() != null){
				//idcReProduct.getIdcRePortModel().setTicketInstId(ticketInstId);
				idcRePortList.add(idcReProduct.getIdcRePortModel());
				definedArrayMap.put("portIitemList",idcRePortList);
			}
			if(idcReProduct.getIdcReIpModel() != null){
				//idcReProduct.getIdcReIpModel().setTicketInstId(ticketInstId);
				idcReIpList.add(idcReProduct.getIdcReIpModel());
				definedArrayMap.put("ipIitemList",idcReIpList);
			}
			if(idcReProduct.getIdcReServerModel() != null){
				//idcReProduct.getIdcReServerModel().setTicketInstId(ticketInstId);
				idcServerList.add(idcReProduct.getIdcReServerModel());
				definedArrayMap.put("serverIitemList",idcServerList);
			}
			if(idcReProduct.getIdcReNewlyModel() != null){
				//idcReProduct.getIdcReNewlyModel().setTicketInstId(ticketInstId);
				idcReNewlyList.add(idcReProduct.getIdcReNewlyModel());
				definedArrayMap.put("newlyIitemList",idcReNewlyList);
			}
			definedArrayMap.put("itemList",idcReProductList);
		}
		return definedArrayMap;
	}

	@Override
	public Long createJbpmEntranceForCreateTicet(IdcReProduct idcReProduct,ApplicationContext context ) throws Exception{
		Long  prodInstId  = procductHandlerData(idcReProduct);
		if(prodInstId !=  null && DevContext.EXIST == prodInstId){
			return DevContext.EXIST;//直接返回说明是存在相同数据
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		logger.debug(" 创建订单，同时创建工单并且开启工单的过程。。。。。。。。。。。。。 ");
		Map<String,Object> createTicketParams = new HashMap<String,Object>();
		createTicketParams.put("ticketCategoryFrom", CategoryEnum.预堪预占流程.value());
		createTicketParams.put("ticketCategoryTo", CategoryEnum.预堪预占流程.value());
		createTicketParams.put("prodInstId",prodInstId);
		CreateTicketModel createTicketModel = idcReProductMapper.getCreateTicketModelByProdId(createTicketParams);
		logger.debug("以下参数更加不同流程设置，而不同。。。。。");
		createTicketModel.setParentTicketId(null);//因为是预勘预占流程
		createTicketModel.setFormKeyFrom("pre_accept_apply_form");
		createTicketModel.setFormKeyTo("pre_accept_apply_form");
		createTicketModel.setProcessdefinitonkeyFrom("idc_ticket_pre_accept");
		createTicketModel.setProcessdefinitonkeyTo("idc_ticket_pre_accept");
		createTicketModel.setProcticketStatus(String.valueOf(ProcTicketStatusEnum.预勘中.value()));
		createTicketModel.setApplyUserName(idcReProduct.getApplyerName());
		createTicketModel.setApplyUserId(Integer.valueOf(idcReProduct.getApplyId()));
		createTicketModel.setTicketStatus(JbpmStatusEnum.流程初始化.value());
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			paramMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}
		paramMap.put("auditName",idcReProduct.getApplyerName());/*审批人名字*/
		paramMap.put("auditId",idcReProduct.getApplyId());/*审批人ID*/

		jbpmTicketEntrancet(context,paramMap);
		return null;
	}

	@Override
	public Map<String,Object> createJbpmEntrance(IdcReProduct idcReProduct,ApplicationContext context ) throws Exception{
		Map<String,Object> result=new HashMap<>();

		Long  prodInstId  = procductHandlerData(idcReProduct);
		if(prodInstId !=  null && DevContext.EXIST == prodInstId){
			result.put("EXIST",DevContext.EXIST);
			return result;//直接返回说明是存在相同数据
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		logger.debug(" 创建订单，同时创建工单并且开启工单的过程。。。。。。。。。。。。。 ");
		Map<String,Object> createTicketParams = new HashMap<String,Object>();
		createTicketParams.put("ticketCategoryFrom", CategoryEnum.预堪预占流程.value());
		createTicketParams.put("ticketCategoryTo", CategoryEnum.预堪预占流程.value());
		createTicketParams.put("prodInstId",prodInstId);
		createTicketParams.put("prodCategory",idcReProduct.getProdCategory());
		CreateTicketModel createTicketModel = idcReProductMapper.getCreateTicketModelByProdId(createTicketParams);
		logger.debug("以下参数更加不同流程设置，而不同。。。。。");
		createTicketModel.setParentTicketId(null);//因为是预勘预占流程

		Integer prodCategory = idcReProduct.getProdCategory();   //订单类别:1普通业务0自有业务
		if(prodCategory == 1){
			createTicketModel.setFormKeyFrom("pre_accept_apply_form");
			createTicketModel.setFormKeyTo("pre_accept_apply_form");
			createTicketModel.setProcessdefinitonkeyFrom("idc_ticket_pre_accept");
			createTicketModel.setProcessdefinitonkeyTo("idc_ticket_pre_accept");
		}else if(prodCategory == 0){
			createTicketModel.setFormKeyFrom("self_pre_accept_apply_form");
			createTicketModel.setFormKeyTo("self_pre_accept_apply_form");
			createTicketModel.setProcessdefinitonkeyFrom("idc_ticket_self_pre_accept");
			createTicketModel.setProcessdefinitonkeyTo("idc_ticket_self_pre_accept");
		}

		createTicketModel.setApplyUserName(idcReProduct.getApplyerName());
		createTicketModel.setApplyUserId(Integer.valueOf(idcReProduct.getApplyId()));
		createTicketModel.setTicketStatus(JbpmStatusEnum.流程初始化.value());
		List<CreateTicketModel> createTicketModelList = new ArrayList<CreateTicketModel>();
		if(createTicketModel != null){
			createTicketModelList.add(createTicketModel);
			paramMap.put("jbpmTicketPropertyItems",createTicketModelList);
		}
		paramMap.put("auditName",idcReProduct.getApplyerName());/*审批人名字*/
		paramMap.put("auditId",idcReProduct.getApplyId());/*审批人ID*/
		paramMap.put("rechageProductInfo",true);
		ResponseJSON responseJSON = jbpmTicketEntrancet(context,paramMap);
		if(responseJSON.isSuccess()) {
			Map<String, Object> responseJSONMap = (Map) responseJSON.getResult();
			Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------start");
			/**
			 * １:创建工单的时候需要创建业务信息.....
			 */
			//然后在保存业务类型
			procductDefModelHandlerData(idcReProduct,ticketInstId,prodInstId);
			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------end");
			result.put("prodInstId",prodInstId);
			result.put("ticketInstId",ticketInstId);

		}
		result.put("EXIST",Long.valueOf(DevContext.SUCCESS));
		return result;
	}

	/*方法说明：*/
	CreateTicketModel GetCreateTicketModel(Map<String,Object> createTicketParams,
										   IdcReProduct idcReProduct,
										   Long prodInstId,
										   Long ticketInstId,
										   Long ticketCategoryFrom,
										   Long ticketCategoryTo,
										   Long prodCategory){

		CreateTicketModel createTicketModel = idcReProductMapper.getCreateTicketModelByProdId(createTicketParams);
		logger.debug("以下参数更加不同流程设置，而不同。。。。。");

		String applyerName = idcReProduct.getApplyerName();
		Integer applyId = Integer.valueOf(idcReProduct.getApplyId());
		createTicketModel.setApplyUserName(applyerName);
		createTicketModel.setApplyUserId(applyId);

		if(ticketCategoryFrom!= null && ticketCategoryTo != null){
			String processdefinitonkeyFrom=idcJbpmTaskNodeMapper.getProcessdefinitonkey(ticketCategoryFrom,prodCategory);
			String processdefinitonkeyTo=idcJbpmTaskNodeMapper.getProcessdefinitonkey(ticketCategoryTo,prodCategory);

			createTicketModel.setProcessdefinitonkeyFrom(processdefinitonkeyFrom);
			createTicketModel.setProcessdefinitonkeyTo(processdefinitonkeyTo);
			createTicketModel.setProcticketStatus(String.valueOf(ProcTicketStatusEnum.已预勘.value()));
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程初始化.value());
			createTicketModel.setFormKeyFrom("XXXXXXpre_accept_apply_form");
			createTicketModel.setFormKeyTo("XXXXXXopen_accept_apply_form");
			createTicketModel.setParentTicketId(ticketInstId);//

		}else{
			//下面是开通预占工单

			createTicketModel.setProcessdefinitonkeyFrom("idc_ticket_pre_accept");
			createTicketModel.setProcessdefinitonkeyTo("idc_ticket_pre_accept");
			createTicketModel.setProcticketStatus(String.valueOf(ProcTicketStatusEnum.预勘中.value()));
			createTicketModel.setTicketStatus(JbpmStatusEnum.流程初始化.value());
			createTicketModel.setFormKeyFrom("pre_accept_apply_form");
			createTicketModel.setFormKeyTo("pre_accept_apply_form");
			createTicketModel.setParentTicketId(null);//因为是预勘预占流程
		}

		return createTicketModel;

	}

    /**
	 * 合体方法
	 * @param idcReProduct
	 * @throws Exception
	 */
	@Override
	public Long mergeInto(IdcReProduct idcReProduct,Map<String,Object> createTicketMap, ApplicationContext applicationContext) throws Exception{
		//如果需要保存。同时保存订单信息：PROJECT_QUERY_USE_REDIS
		Long  prodInstId  = procductHandlerData(idcReProduct);
		if(prodInstId !=  null && DevContext.EXIST == prodInstId){
			//直接返回说明是存在相同数据
			return DevContext.EXIST;
		}
		//驳回的单子
		Boolean isRejectTicket = idcReProduct.getIsRejectTicket();
		if(!isRejectTicket && createTicketMap != null && !createTicketMap.isEmpty()){
			//这里是创建工单情况
			logger.debug("创建了相应的订单后开始创建工单.....");
			Integer prodCategory =  Integer.valueOf(String.valueOf(createTicketMap.get("prodCategory")));
			String catalog =  String.valueOf(createTicketMap.get("category"));

			Map map=new HashMap();
			map.put("nick",idcReProduct.getApplyerName());

			ResponseJSON responseJSON = null;
			try {
				responseJSON = actJBPMService.singleCreateTicket(applicationContext,map,prodInstId,prodCategory,null,null,catalog,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Boolean isSuccess= responseJSON.isSuccess();
		}
		return Long.valueOf(DevContext.SUCCESS);
	}

	@Override
	public void updateLSCS(Date lscsStartTime, Date lscsEntTime,Long prodInstId) throws Exception {
		if(lscsStartTime != null && lscsEntTime != null ){
			idcReProductMapper.updateLSCS(lscsStartTime,lscsEntTime,prodInstId);
		}
	}

	/**
	 * 通过ID删除的实体类
	 * @param id
	 * @throws Exception
	 */
	public void deleteRecordById(Long id) throws Exception{
		/**
		 * 1：首先需要删除订单的所有服务
		 * 2：然后删除订单
		 */
		idcReProddefMapper.deleteWithProcedureByProdInstId(id);
		/*
			还需要删除客户需求:[订单历史、正在运行的订单、订单资源中间表]
			后续做
		 */
		idcReProductMapper.deleteRecordById(id);
	}

	@Override
	public void updateRcordByIdWithIsActive(Long id, Integer isActive) throws Exception {
		idcReProductMapper.updateRcordByIdWithIsActive(id,isActive);
	}

	@Override
	public Map<String,Object> getActModelByKey(String modelkey) {
		return idcReProductMapper.getActModelByKey(modelkey);
	}

	/**
	 * 获取模型设计的二进制图片
	 * @param editorSourceExtraValueId
	 * @return
	 */
	public ActJBPM getActBytesByEditorSourceExtraValueId(String editorSourceExtraValueId){
		return idcReProductMapper.getActBytesByEditorSourceExtraValueId(editorSourceExtraValueId);
	}

	@Override
	public void pauseFormSaveOrUpdate(ApplicationContext context, IdcRunTicketPause idcRunTicketPause, IdcRunProcCment idcRunProcCment) throws Exception{

		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("prodInstId",idcRunTicketPause.getIdcRunTicket().getProdInstId());
		variables.put("ticketCategoryFrom",idcRunTicketPause.getIdcRunTicket().getTicketCategoryFrom());
		variables.put("ticketCategoryTo",idcRunTicketPause.getIdcRunTicket().getTicketCategoryTo());
		variables.put("parentId",idcRunTicketPause.getIdcRunTicket().getTicketInstId());
		variables.put("prodCategory",idcRunTicketPause.getIdcRunTicket().getProdCategory());

		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
		if(responseJSON.isSuccess()){
			Map<String, Object> responseJSONMap = (Map) responseJSON.getResult();
			Long ticketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));
			//idcRunTicketPause.setTicketInstId(ticketInstId);
			idcRunTicketPause = idcRunProcCment.getIdcRunTicketPause();
			idcRunTicketPause.setTicketInstId(ticketInstId);
			IdcHisTicketPause idcHisTicketPause= new IdcHisTicketPause();
			BeanUtils.copyProperties(idcRunTicketPause,idcHisTicketPause);
			idcHisTicketPause.setTicketInstId(ticketInstId);
			idcRunTicketPauseMapper.updateByData(idcRunTicketPause);
			idcHisTicketPauseMapper.updateByData(idcHisTicketPause);

		}
	}

	@Override
	public Long handWithIdcReProduct(ApplicationContext context,Map<String,Object> variables,IdcReProduct idcReProduct) throws Exception{
		/*  先创建工单 */
		/*Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("prodInstId",idcReProduct.getIdcRunTicket().getProdInstId());
		variables.put("ticketCategoryFrom",idcReProduct.getIdcRunTicket().getTicketCategoryFrom());
		variables.put("ticketCategoryTo",idcReProduct.getIdcRunTicket().getTicketCategoryTo());
		variables.put("parentId",idcReProduct.getIdcRunTicket().getTicketInstId());
		variables.put("prodCategory",idcReProduct.getIdcRunTicket().getProdCategory());*/
		variables.put("rechageProductInfo",false);
		ResponseJSON responseJSON = jbpmTicketEntrancet(context,jbpmTicketEntrancetParam(variables));////开通工单常见工单过程。。。。。
		if(responseJSON.isSuccess()){
			Map<String,Object> responseJSONMap = (Map)responseJSON.getResult();
			Long oldTicketInstId = Long.valueOf(String.valueOf(variables.get("ticketInstId")));   //发起流程时旧工单的ID
			Long newTicketInstId = Long.valueOf(String.valueOf(responseJSONMap.get("ticketInstId")));   //发起申请成功后返回的新工单的工单ID
			//这里进行处理。。。。
			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------start");
			//然后在保存业务类型
			//procductDefModelHandlerData(idcReProduct,ticketInstId,idcReProduct.getIdcRunTicket().getProdInstId());
			copyIdcReProddef(newTicketInstId,oldTicketInstId);


			System.out.println("------start--------测试查询用------请删除--------------");
			List < IdcReProddef > test_AAIdcReProddef = idcReProddefMapper.getAllByTicketInstId(newTicketInstId);
			List<IdcHisTicketResource> test_AAIdcHisTicketResource = idcHisTicketResourceMapper.getAllByTicketInstId(newTicketInstId);
			System.out.println("------end--------测试查询用------请删除--------------");

			idcHisTicketResourceMapper.updateResource(newTicketInstId);//历史资源表把旧的需求资源的状态改为-1
		    idcRunTicketResourceMapper.updateResource(newTicketInstId);//运行资源表把旧的需求资源的状态改为-1

			System.out.println("------start--------测试查询用------请删除--------------");
			List<IdcHisTicketResource> test_BBIdcHisTicketResource = idcHisTicketResourceMapper.getAllByTicketInstId(newTicketInstId);
			System.out.println("------end--------测试查询用------请删除--------------");

			System.out.println(">>>>>>>>>>>>创建工单对应的业务信息--------------end");

			System.out.println("----申请业务变更流程，单独走方法修改资源的工单ID");

			Map<String,Object> params=new HashMap<>();
			params.put("parentTicketInstId",variables.get("parentId"));
			idcContractService.alterResourceStatus_B(context,CategoryEnum.业务变更流程.value(),newTicketInstId,params);
		}
		return Long.valueOf(DevContext.SUCCESS);
	}
}
