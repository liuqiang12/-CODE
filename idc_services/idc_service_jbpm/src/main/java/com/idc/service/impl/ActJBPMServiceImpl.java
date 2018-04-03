package com.idc.service.impl;

import com.bpm.ProcessCoreService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.*;
import com.idc.utils.*;
import modules.utils.ResponseJSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import system.RestUtil;
import system.data.page.PageBean;
import system.data.supper.service.JavaSerializer;
import system.data.supper.service.RedisManager;
import system.rest.ResultObject;
import utils.DevContext;
import utils.DevLog;
import utils.typeHelper.GsonUtil;
import utils.typeHelper.MapHelper;

import java.io.ByteArrayInputStream;
import java.util.*;

@Service("actJBPMService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ActJBPMServiceImpl extends RemoteSysResourceServiceImpl implements ActJBPMService {
    private Logger logger = LoggerFactory.getLogger(ActJBPMServiceImpl.class);
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
    /* 必备的流程引入类 end*/
    /* 业务服务注入 start */
    @Autowired
    private IdcReProductMapper idcReProductMapper;
    @Autowired
    private IdcRunTicketMapper idcRunTicketMapper;//运行时工单
    @Autowired
    private IdcReCustomerMapper idcReCustomerMapper;//客户信息
    @Autowired
    private IdcRunTicketCommnetService idcRunTicketCommnetService;//运行时的工单意见表
    @Autowired
    private IdcHisTicketCommnetService idcHisTicketCommnetService;//历史工单意见表
    @Autowired
    private IdcHisTicketMapper idcHisTicketMapper;//历史工单
    @Autowired
    private IdcRunTicketPreAcceptMapper idcRunTicketPreAcceptMapper;//预受理工单
    @Autowired
    private IdcRunTicketChangeMapper idcRunTicketChangeMapper;//变更工单
    @Autowired
    private IdcHisTicketPreAcceptMapper idcHisTicketPreAcceptMapper;//预受理历史工单
    @Autowired
    private IdcHisTicketChangeMapper idcHisTicketChangeMapper;
    @Autowired
    private ActJBPMMapper actJBPMMapper;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private JavaSerializer serializableMethod;//序列化方式
    @Autowired
    private IdcRunTicketPauseMapper idcRunTicketPauseMapper;
    @Autowired
    private IdcHisTicketPauseMapper idcHisTicketPauseMapper;
    @Autowired
    private IdcRunTicketRecoverMapper idcRunTicketRecoverMapper;
    @Autowired
    private IdcHisTicketRecoverMapper idcHisTicketRecoverMapper;
    @Autowired
    private IdcRunTicketHaltMapper idcRunTicketHaltMapper;  //下线
    @Autowired
    private IdcHisTicketHaltMapper idcHisTicketHaltMapper;  //下线
    @Autowired
    private IdcHisProcCmentMapper idcHisProcCmentMapper;
    @Autowired
    private IdcRunProcCmentMapper idcRunProcCmentMapper;
    @Autowired
    private RestTemplate restTemplate;/*接口调用模板*/
    @Autowired
    private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
    @Autowired
    private IdcRunTicketResourceMapper idcRunTicketResourceMapper;

    public IdcHisProcCmentMapper getIdcHisProcCmentMapper() {
        return idcHisProcCmentMapper;
    }


    public List<Map<String, Object>> getDataCentre() {
        return actJBPMMapper.getDataCentre();
    }

    public List<Map<String, Object>> getBuildingByLocationID(Long locationID) {
        return actJBPMMapper.getBuildingByLocationID(locationID);
    }

    public List<Map<String, Object>> getMachineRoomByBuildingID(Long buildingID) {
        return actJBPMMapper.getMachineRoomByBuildingID(buildingID);
    }

    public List<Map<String, Object>> getDataCheckTree() {
        List<Map<String, Object>> dataCheck=new ArrayList<>();

        //得到所有的数据中心
        List<Map<String, Object>> dataCentre = actJBPMMapper.getDataCentre();
        for (Map<String, Object> dataCentreMap : dataCentre) {
            //数据中心ID
            Long locationID = Long.valueOf(String.valueOf(dataCentreMap.get("ID")));
            //数据中心名字
            String locationName = String.valueOf(dataCentreMap.get("NAME"));
            //树根
            Map<String,Object> firstTree=new HashMap<>();
            firstTree.put("id",locationID);
            firstTree.put("text","数据中心"+locationName);

            //组装子树,机房
            List<Map<String, Object>> childrenMaps = assembleSonTree(locationID);

            firstTree.put("children",childrenMaps);
            //组装第一级别的树，树根
            dataCheck.add(firstTree);
        }

        return dataCheck;
    }

    public List<Map<String, Object>> assembleSonTree(Long locationID) {

        //所有的一级子树：机楼。通过数据中心ID得到该数据中心的所有机楼
        List<Map<String, Object>> building = actJBPMMapper.getBuildingByLocationID(locationID);

        List<Map<String, Object>> sonTree = new ArrayList<>();  //所有的一级子树：机楼。（把building有用的两个字段拿出来）

        Map<String,Object> sonTreeMaps=new HashMap<>(); // 一级子树的其中一个树

        for (Map<String, Object> buildingMap : building) {
            Long buildingID = Long.valueOf(String.valueOf(buildingMap.get("ID")));  //机房ID
            String buildingName = String.valueOf(buildingMap.get("NAME"));   //机房名
            sonTreeMaps.put("id",buildingID);
            sonTreeMaps.put("text","机楼: "+buildingName);

            //所有的二级子树：机房。通过机楼ID得到该机楼的所有机房
            List<Map<String, Object>> machineRoom = actJBPMMapper.getMachineRoomByBuildingID(buildingID);
            List<Map<String, Object>> roomList = new ArrayList<>();  //所有的二级子树：机房。（把machineRoom有用的两个字段拿出来）
            for (Map<String, Object> machineRoomMap: machineRoom) {
                Map<String, Object> roomMap=new HashMap<>();
                String sitename = machineRoomMap.get("SITENAME").toString();  //机房名称
                String machineRoomID = machineRoomMap.get("ID").toString();//机房ID
                roomMap.put("id",machineRoomID);
                roomMap.put("text","机房: "+sitename);

                roomList.add(roomMap);
            }
            sonTreeMaps.put("children",roomList);
            sonTree.add(sonTreeMaps);
        }
        return sonTree;
    }

    @Override
    public List<Map<String,Object>> ticketOnServer(PageBean pageBean, Map<String, Object> map) {
        pageBean.setParams(map);
        List<Map<String, Object>> maps = actJBPMMapper.ticketOnServerListPage(pageBean);
        return maps;
    }

    /*
    *wcg
    * 通过机房id查询到所有的资源的信息
    *
    * */
    @Override
    public Map<String, Object> getResourceStatusByRoomID(Long roomID) {
        Map<String,Object> statusMap=new HashMap<>();

        /*得到资源的状态*/
        List<Map<String, Object>> rackStatusList = actJBPMMapper.getRackStatusByRoomID(roomID);
        List<Map<String, Object>> rackUnitStatusList = actJBPMMapper.getRackUnitStatusByRoomID(roomID);
        List<Map<String, Object>> portStatusList = actJBPMMapper.getPortStatusByRoomID(roomID);
        List<Map<String, Object>> ipStatusList = actJBPMMapper.getIPStatusByRoomID();
        List<Map<String, Object>> serverStatusList = actJBPMMapper.getServerStatusByRoomID(roomID);
        List<Map<String, Object>> MCBStatusList = actJBPMMapper.getMCBStatusByRoomID(roomID);

         /*  资源的状态请查看 ResourceEnum枚举*/
        for (Map<String, Object> rackStatus:rackStatusList) {
            //添加空闲状态的资源的数量
            if(rackStatus.get("STATUS") != null && rackStatus.get("STATUS").toString().equals("40")){
                statusMap.put("rackStatusFree",rackStatus.get("STATUSCOUNT"));
            }
            //添加占用状态的资源的数量
            if(rackStatus.get("STATUS") != null && rackStatus.get("STATUS").toString().equals("60")){
                statusMap.put("rackStatusUse",rackStatus.get("STATUSCOUNT"));
            }
        }

         /*  资源的状态请查看 ResourceEnum枚举*/
        for (Map<String, Object> rackUnitStatus:rackUnitStatusList) {
            //添加空闲状态的资源的数量
            if(rackUnitStatus.get("STATUS") != null && rackUnitStatus.get("STATUS").toString().equals("20")){
                statusMap.put("rackUnitStatusFree",rackUnitStatus.get("STATUSCOUNT"));
            }
            //添加占用状态的资源的数量
            if(rackUnitStatus.get("STATUS") != null && rackUnitStatus.get("STATUS").toString().equals("60")){
                statusMap.put("rackUnitStatusUse",rackUnitStatus.get("STATUSCOUNT"));
            }
        }

         /*  资源的状态请查看 ResourceEnum枚举*/
        for (Map<String, Object> portStatus:portStatusList) {
            //添加空闲状态的资源的数量
            if(portStatus.get("PORTACTIVE") != null && portStatus.get("PORTACTIVE").toString().equals("2")){
                statusMap.put("portStatusFree",portStatus.get("STATUSCOUNT"));
            }
            //添加占用状态的资源的数量
            if(portStatus.get("PORTACTIVE") != null && portStatus.get("PORTACTIVE").toString().equals("1")){
                statusMap.put("portStatusUse",portStatus.get("STATUSCOUNT"));
            }
        }

         /*  资源的状态请查看 ResourceEnum枚举*/
        for (Map<String, Object> ipStatus:ipStatusList) {
            //添加空闲状态的资源的数量
            if(ipStatus.get("STATUS") != null && ipStatus.get("STATUS").toString().equals("0")){
                statusMap.put("ipStatusFree",ipStatus.get("STATUSCOUNT"));
            }
            //添加占用状态的资源的数量
            if(ipStatus.get("STATUS") != null && ipStatus.get("STATUS").toString().equals("1")){
                statusMap.put("ipStatusUse",ipStatus.get("STATUSCOUNT"));
            }
        }

         /*  资源的状态请查看 ResourceEnum枚举*/
        for (Map<String, Object> serverStatus:serverStatusList) {
            //添加空闲状态的资源的数量
            if(serverStatus.get("STATUS") != null && serverStatus.get("STATUS").toString().equals("40")){
                statusMap.put("serverStatusFree",serverStatus.get("STATUSCOUNT"));
            }
            //添加占用状态的资源的数量
            if(serverStatus.get("STATUS") != null && serverStatus.get("STATUS").toString().equals("60")){
                statusMap.put("serverStatusUse",serverStatus.get("STATUSCOUNT"));
            }
        }
        return statusMap;
    }


    public void setIdcHisProcCmentMapper(IdcHisProcCmentMapper idcHisProcCmentMapper) {
        this.idcHisProcCmentMapper = idcHisProcCmentMapper;
    }

    public IdcRunProcCmentMapper getIdcRunProcCmentMapper() {
        return idcRunProcCmentMapper;
    }

    public void setIdcRunProcCmentMapper(IdcRunProcCmentMapper idcRunProcCmentMapper) {
        this.idcRunProcCmentMapper = idcRunProcCmentMapper;
    }
    /* 业务服务注入 end */

    @Override
    public Map<String,Object> customerViewData(String loginUserID) throws Exception {

        return actJBPMMapper.customerViewData(loginUserID);
    }

    /**
     * 通过订单Id创建工单信息
     * @param prodInstId
     * @param ticketInstId_init :由这个工单创建的工单
     * @return
     * @throws Exception
     */
    @Override
    public ResponseJSON singleCreateTicket(ApplicationContext applicationContext,Map map, Long prodInstId, Integer prodCategory, Long parentId, Long initId, String catalog, Long ticketInstId_init) throws Exception{
        /*ResponseJSON result = new ResponseJSON();
        //部署预勘流程图
        JBPMModelKEY jbpmModelKEY = JBPMModelKEY.getCurrentWithCatalog(catalog,Integer.valueOf(prodCategory));
        //直接修改部署的图片
        System.out.println("调用代理JbpmDeployProxy:目的是将该工单保存到redis缓存中:该工单prodInstId:["+prodInstId+"].....................start");
        ActJBPMService proxy = (ActJBPMService)new TicketRedisProxy().bind(this,applicationContext);

        //下面的工单id是进入下一个流程后的新的工单的id
        Long ticketInstId = proxy.jbpmTicketDeploy(map,prodInstId,prodCategory,parentId,initId,jbpmModelKEY,null,ticketInstId_init);

        logger.error("---------------------------更新资源表的工单id------------------------------");
        idcContractService.alterResourceStatus_A(applicationContext,ticketInstId);

        System.out.println("调用代理JbpmDeployProxy:目的是将该工单保存到redis缓存中:该工单id:["+ticketInstId+"].....................end");
        result.setResult(ticketInstId);
        result.setMsg("创建成功!");
        result.setSuccess(true);
        return result;*/
        return null;
    }
    /**
     * 获取代办任务
     *  @param map    key: groupid
     *                需要传递两个userkey------------[username_xxxx@id_xxxxx]
     *                          groupkey------------[role_key_xxxx@id_xxxxx]
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>> jbpmAllTaskQueryListPage(PageBean pageBean, Map<String, Object> map){
 /* 查询分页信息 */
        pageBean.setParams(map);
        return actJBPMMapper.jbpmAllTaskQueryListPage(pageBean);
    }
    /**
     * 通过订单Id创建工单信息
     * @param prodInstId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseJSON singleBusinessCreateTicket(Long prodInstId,String catalog,String prodCategory) throws  Exception{
        ResponseJSON result = new ResponseJSON();
        //这里创建的工单时由传递的 catalog 决定
        //部署预勘流程图
        //JBPMModelKEY jbpmModelKEY = JBPMModelKEY.getCurrentWithCatalog(catalog,Integer.valueOf(prodCategory));

        //return result;
        return null;
    }

    /**
     * 流程引擎的部署就近原则
     * @param jbpmModelKEY
     * @throws Exception
     */
    @Override
    public void jbpmCommentDelopy(JBPMModelKEY jbpmModelKEY) throws  Exception{
        /**
         * idcReProductMapper:只是用来借用的持久层【最终还是查找的流程引擎的表】
         */
        Map<String,Object> modelMap = idcReProductMapper.getActModelByKey(jbpmModelKEY.value());
        String modelId = (String)modelMap.get("MODELID");
        String editorSourceExtraValueId = (String)modelMap.get("EDITORSOURCEEXTRAVALUEID");//图片
        String editorSourceValueId = (String)modelMap.get("EDITORSOURCEVALUEID");//json格式文件
        String deploymentId = (String)modelMap.get("DEPLOYMENTID");
        if(deploymentId == null || "".equals(deploymentId)){
            //------------------------------------- 部署流程 start------------------------------------------
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();

            //byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
            // 获取图片二进制  通过模型对应的editorSourceExtraValueId值在act_ge_bytearray 查找
            ActJBPM actJBPM = idcReProductMapper.getActBytesByEditorSourceExtraValueId(editorSourceExtraValueId);

            ByteArrayInputStream in = new ByteArrayInputStream(actJBPM.getBpmBytes());
            String processName = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";

            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName()).addBpmnModel(processName, bpmnModel)//.addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
            //调用存储过程修改相应有关流程参数信息
            actJBPMMapper.callActUpdateResource(modelId,editorSourceValueId,editorSourceExtraValueId,jbpmModelKEY.value()+"."+jbpmModelKEY.value()+".png",deployment.getId());
            //------------------------------------- 部署流程 end------------------------------------------
        }
    }

    /**
     * jbpm工单部署情况
     * @param prodInstId:订单实例
     * @param jbpmModelKEY:流程定义参数
     * @throws Exception
     */
    public Long jbpmTicketDeploy(Map map,Long prodInstId,Integer prodCategory,Long parentId,Long initId,JBPMModelKEY jbpmModelKEY,IdcRunTicket idcRunTicket,Long ticketInstId_init) throws Exception{
        //------------------------------------- 创建工单 start------------------------------------------
        System.out.println(">>>>>>>>>>>>创建工单表信息--------------start");
        Map<String,Object> proc_init_map_params = new HashMap<String,Object>();
        proc_init_map_params.put("prodInstId",prodInstId);
        proc_init_map_params.put("prodCategory",prodCategory);
        proc_init_map_params.put("parentId",parentId);
        proc_init_map_params.put("initId",initId);
        proc_init_map_params.put("catalog",jbpmModelKEY.catalog());
        proc_init_map_params.put("ticketInstId_init",ticketInstId_init);

        /*下面的存储过程创建新的工单信息，并且把当前的工单信息复制到新的工单里面去。只是表idc_run/his_ticket*/
        idcRunTicketMapper.callCreateInitTicket(proc_init_map_params);
        System.out.println(">>>>>>>>>>>>创建工单表信息--------------end");
        //返回工单id
        Long ticketInstId = Long.valueOf(String.valueOf(proc_init_map_params.get("ticketInstId")));
        System.out.println("=================================ticketInstId=["+ticketInstId+"]=================================");
        //------------------------------------- 创建工单end------------------------------------------
        //---------------------------------------流程实例化 start-----------------------------------------
        String BUSINESS_KEY = "prodInstId:"+prodInstId+",ticketInstId:"+ticketInstId;
        String processDefinitionKey = jbpmModelKEY.value();
        /*项目启动的时候需要控制一下监听*/
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("taskListener","taskListener");
        ProcessInstance processInstance = runtimeService//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey,BUSINESS_KEY);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID    101
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID   helloworld:1:4
        //---------------------------------------流程实例化 end-----------------------------------------
        //------------------------------------- 工单修改且 创建历史工单和预受理工单 start------------------------------------------
        ProcTicketStatusEnum procTicketStatusEnum = ProcTicketStatusEnum.getCurrentEnumWithCatalog(jbpmModelKEY.catalog(),"0");
        if(procTicketStatusEnum == null){
            throw new Exception(ProcTicketStatusEnum.class+"没有找到catalog["+jbpmModelKEY.catalog()+"]对应的值");
        }
        Map<String,Object> proc_map_params = new HashMap<String,Object>();
        proc_map_params.put("prodInstId",prodInstId);
        proc_map_params.put("ticketInstId",ticketInstId);
        proc_map_params.put("procInstId",processInstance.getId());
        proc_map_params.put("category",jbpmModelKEY.catalog());
        proc_map_params.put("procticketStatus",procTicketStatusEnum.value());
        System.out.println("callCreateCategoryTicket:主要是拷贝流程之前的有关资源数据到当前工单中....................start");
        idcRunTicketMapper.callCreateCategoryTicket(proc_map_params);
        System.out.println("callCreateCategoryTicket:主要是拷贝流程之前的有关资源数据到当前工单中....................end");
        //------------------------------------- 工单修改且 创建历史工单和预受理工单 end------------------------------------------
        /*                     通过工单id获取任务ID完成第一步工单申请动作 [add]                     */
        List<IdcRunTicket> listing = (ArrayList<IdcRunTicket>)proc_map_params.get("ticket_cursor");
        IdcRunTicket idcRunTicketing = listing.get(0);
        //获取正在运行的工单
        completeTaskNode(idcRunTicketing,1,"");//完成第一步：申请任务节点
        /*通过工单id获取任务ID完成第一步工单申请动作 [end]*/
        initProcCment(ticketInstId,prodInstId,processInstance.getId(),String.valueOf(map.get("nick")));
        return ticketInstId;
    }
    @Override
    public void initProcCment(Long ticketInstId,Long prodInstId,String procInstId,String author) throws  Exception{

        if(author == null ){
            throw new Exception("申请人为空.......");
        }
        if(ticketInstId == null ){
            throw new Exception("工单为空.......");
        }
        if(prodInstId == null ){
            throw new Exception("订单为空.......");
        }
        System.out.println("[工单启动的时候]==============初始化第一步创建信息...............start");
        System.out.println("工作流第一步，需要初始化的参数信息:[工单ID="+ticketInstId+"、订单ID="+prodInstId+"、流程实例ID="+procInstId+"、创建人名称="+author+"、]");
        IdcRunProcCment saveApply=new IdcRunProcCment();
        saveApply.setTicketInstId(ticketInstId);
        saveApply.setProdInstId(prodInstId);
        saveApply.setProcInstId(Long.valueOf(procInstId));
        saveApply.setCreateTime(new Date());
        saveApply.setCreateTimeStr(new Date());
        saveApply.setComment("无");
        saveApply.setAuthor(author);
        saveApply.setStatus(1);
        saveApply.setActName("申请");

        //actJBPMMapper.jbpmRunTicketTaskListPage(new PageBean());
        //把当前运行表的内容复制到历史表里面去
        IdcHisProcCment idcHisProcCment=new IdcHisProcCment();
        BeanUtils.copyProperties(saveApply,idcHisProcCment);
        idcRunProcCmentMapper.mergeInto(saveApply);
        idcHisProcCmentMapper.mergeInto(idcHisProcCment);
        System.out.println("[工单启动的时候]==============初始化第一步创建信息...............end");
    }

    @Override
    public void callJbpmIntoRedis(Map<String, Object> procJbpmMap) {

        actJBPMMapper.callJbpmIntoRedis(procJbpmMap);

    }

    @Override
    public void callJbpmLinkedIntoRedis(Map<String, Object> procJbpmMap) {

        actJBPMMapper.callJbpmLinkedIntoRedis(procJbpmMap);

    }


    @Override
    public void callSingleTicketJbpmIntoRedis(Map<String, Object> procJbpmMap) {
        actJBPMMapper.callSingleTicketJbpmIntoRedis(procJbpmMap);
    }
    @Override
    public void callSingleTicketJbpmIntoLinkedRedis(Map<String, Object> procJbpmMap) {
        actJBPMMapper.callSingleTicketJbpmIntoLinkedRedis(procJbpmMap);
    }

    @Override
    public List<IdcRunTicket> managerViewTips() {
        List<IdcRunTicket> respose_expire_ticketList = new ArrayList<IdcRunTicket>();
        try {
            String redisKey = "";//目前是配置,暂时没有用
            IdcRunTicket idcRunTicket = new IdcRunTicket();

            //ResultObject ro = RestUtil.get(restTemplate,DevContext.REST_URL+"jbpm/commingExpireTicket",ResultObject.class,null);
            DevLog.debug("------- REDIS是否已经启动，或者REST链接是否配对.........请检查原因 -------");
            ResponseEntity<ResultObject> resp = RestUtil.exchange(restTemplate, DevContext.REST_URL+"jbpm/commingExpireTicket",idcRunTicket);
            //*返回状态和结果*//
            if(1==2 && resp.getStatusCodeValue() == 200 && resp.getBody().getStatus()){
                List<String> expire_ticketList = (List<String>)resp.getBody().getResultObj();
                /* expire_ticketList包含了所有的过期工单，请先查询20条数据推送到前端 */
                Iterator<String> expireIt = expire_ticketList.iterator();
                Integer COMMING_EXPIRE_TICKET_COUNT = DevContext.COMMING_EXPIRE_TICKET_COUNT;
                while(expireIt.hasNext()){
                    if(COMMING_EXPIRE_TICKET_COUNT <= 0){
                        break;
                    }
                    if(COMMING_EXPIRE_TICKET_COUNT > 0){
                        IdcRunTicket expire_idcRunTicket= GsonUtil.json2Object(expireIt.next(),IdcRunTicket.class);
                        respose_expire_ticketList.add(expire_idcRunTicket);
                    }
                    COMMING_EXPIRE_TICKET_COUNT--;
                }
            }else{
                DevLog.debug("------- REDIS是否已经启动，或者REST链接是否配对.........请检查原因 -------");
                //throw new Exception();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            //throw new Exception();
        }
        JSONArray json = JSONArray.fromObject(respose_expire_ticketList);
        String str = json.toString();
        return respose_expire_ticketList;

    }

    @Override
    public Map<String, Object> getIdcRunTicketFormRedis(String redis_key, int pageNo, int pageSize) throws Exception{
        Map<String, Object> map = new HashMap<String,Object>();
        List<String> items = redisManager.findKeysForPage(redis_key,pageNo,pageSize);
        int total = redisManager.getPatternKeyCount(redis_key);
        List<Map<String,Object>> ticketItems = new ArrayList<Map<String,Object>>();
        if(items != null && items.size() > 0){
            int idx = items.size()-1;
            do{
                String user_xh = items.get(idx);
                if (user_xh != null && !"".equals(user_xh)){
                    //获取对应的主键ID
                    //JBPM_SYS_USERINFO:USER_1_政企_PA_PA_20170908_005
                    String redis_serialNumber = DevContext.getLastSplitText(user_xh);
                    //获取对应的工单ID值
                    String IDC_RUN_TICKET_EXT_KEY  = DevContext.IDC_RUN_TICKET_EXT+":serialNumber:"+redis_serialNumber;
                    //获取redis   value
                    //判断是否存在相应的key
                    Boolean isExists = redisManager.exists(IDC_RUN_TICKET_EXT_KEY);
                    if(isExists){
                        //获取对应的value
                        Set<String> list = redisManager.smembers(IDC_RUN_TICKET_EXT_KEY);
                        if(list == null || list.size() == 0 ){
                            logger.error(IDC_RUN_TICKET_EXT_KEY+"没有对应的数据，请检查一下是否redis中对应有相应的数据。。。。");
                        }
                        Iterator<String> listIt = list.iterator();
                        while(listIt.hasNext()){
                            String redis_ticketId = DevContext.getLastSplitText(listIt.next());
                            Map<String,Object> idcRunTicketMap = getIdcRunTicketMapFromBinaryJedisCache(Long.valueOf(redis_ticketId));
                            if(idcRunTicketMap != null){
                                ticketItems.add(idcRunTicketMap);
                            }else{
                                logger.error("oracle中的数据和redis中的数据不同步，或者后台资源没有进行维护。请注意查看redis【IDC_RUN_TICKET】");
                            }
                        }
                    }else{
                        logger.error("oracle中的数据和redis中的数据不同步，或者后台资源没有进行维护。请注意查看redis【DevContext.IDC_RUN_TICKET_EXT:serialNumber:*】");
                    }
                }
                idx--;
            }while( idx >= 0 );
        }
        if(ticketItems != null && !ticketItems.isEmpty()){
            map.put("items",ticketItems);
            map.put("total",total);
        }else{
            map.put("items",new ArrayList<IdcRunTicket>());
            map.put("total",0);
        }
        return map;
    }
    public Map<String,Object> getIdcRunTicketMapFromBinaryJedisCache(Long ticketInstId) throws Exception{
        //通过工单ID获取正在运行的工单
        byte[] runTicketBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RUN_TICKET_TASK,String.valueOf(ticketInstId));
        if(runTicketBinary != null){
            Map<String,Object> map = (Map<String,Object>)serializableMethod.unserialize(runTicketBinary);
            return map;
        }
        return null;
    }
    public IdcRunTicket getIdcRunTicketFromBinaryJedisCache(Long ticketInstId) throws Exception{
        //通过工单ID获取正在运行的工单
        byte[] runTicketBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RUN_TICKET,String.valueOf(ticketInstId));
        if(runTicketBinary != null){
            IdcRunTicket idcRunTicket = (IdcRunTicket)serializableMethod.unserialize(runTicketBinary);
            return idcRunTicket;
        }
        return null;
    }
    /**
     * 完成任务
     * @param idcRunTicket
     * @param status:是否驳回的状态0 代表驳回   1代表同意
     * @param comment
     * @throws Exception
     */
    public void completeTaskNode(IdcRunTicket idcRunTicket, Integer status, String comment) throws  Exception{
        if(comment == null){comment = "";  }
        /*完成当前任务 start*/
        /************* 流程节点处理 同意与不同意:即有网关节点的时候  start**************/
        Long procInstId = idcRunTicket.getProcInstId();
        Map<String,Object> ticketTaskCompleteMap = new HashMap<String,Object>();
        ticketTaskCompleteMap.put("procInstId",idcRunTicket.getProcInstId());
        ticketTaskCompleteMap.put("ticketInstId",idcRunTicket.getTicketInstId());
        ticketTaskCompleteMap.put("ticket_status_flag",-1);
        //如果是1代表同意取反是驳回
        ticketTaskCompleteMap.put("apply_status",status);//apply_status是驳回状态
        ticketTaskCompleteMap.put("comment",comment);
        /*是否同意：status：且不同意的态度:*/
        /* 如果两个状态都是评价 则就是结束状态1:第一步idc3 */
        /** 历史工单则需要先修改 **/
        SysUserinfo principal = (SysUserinfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //判断该任务是否有条件:  start
        List<PvmTransition> outgoingTransitions = processCoreService.getOutgoingTransitions(String.valueOf(procInstId));
        if(outgoingTransitions != null && !outgoingTransitions.isEmpty()){
            Map<String,Object> variables = new HashMap<String,Object>();
            variables.put("apply_status",status);//智能做法：根据上面匹配出参数key
            //任务完成，设置本签收人员
			/*Authentication.setAuthenticatedUserId("liuqiang");//设置审核人*/
            Authentication.setAuthenticatedUserId("user_key_"+principal.getName()+"@id_"+principal.getId()+"");//设置审核人
            taskService.addComment(idcRunTicket.getTaskId(),String.valueOf(procInstId),comment);
            taskService.complete(idcRunTicket.getTaskId(),variables);
        }else{
            //表示没有参数设置
            //任务完成，设置本签收人员
			/*Authentication.setAuthenticatedUserId("liuqiang");//设置审核人*/
            Authentication.setAuthenticatedUserId("user_key_"+principal.getName()+"@id_"+principal.getId()+"");//设置审核人
            taskService.addComment(idcRunTicket.getTaskId(),String.valueOf(procInstId), comment);
            taskService.complete(idcRunTicket.getTaskId());
            //ActivityImpl endActivity = findActivitiImpl(idcRunTicket.getTaskId(), "end");
        }
        /************* 流程节点处理 同意与不同意:即有网关节点的时候  end**************/
        ticketTaskCompleteMap.put("ticket_status_flag",1);
        //
        idcRunTicketMapper.callTicketTaskComplete(ticketTaskCompleteMap);
        /* 完成申请任务后修改工单的当前任务id、当前任务名称等  end*/
		/*完成当前任务 end*/
    }
    @Override
    public List<Map<String,Object>> jbpmTaskQueryListPage(PageBean page, Map<String, Object> map){
        /* 查询分页信息 */
        page.setParams(map);
        return actJBPMMapper.jbpmTaskQueryListPage(page);
    }

    @Override
    public List<IdcRunTicket> jbpmRunTicketTaskListPage(PageBean page, Map<String, Object> map){
        /* 查询分页信息 */
        page.setParams(map);
        List<IdcRunTicket> maps = actJBPMMapper.jbpmRunTicketTaskListPage(page);
        return maps;
    }
    @Override
    public List<Map<String,Object>> jbpmManagerViewListPage(PageBean page, Map<String, Object> map){
        /* 查询分页信息 */
        page.setParams(map);
        return actJBPMMapper.jbpmManagerViewListPage(page);
    }
    @Override
    public List<Map<String,Object>> allTicketCountPageData(Map<String, Object> map){

        return actJBPMMapper.allTicketCountPageData(map);
    }

    @Override
    public List<IdcHisTicket> jbpmHisTicketTaskListPage(PageBean page, Map<String, Object> map){
        /* 查询分页信息 */
        page.setParams(map);
        List<IdcHisTicket> idcHisTickets = actJBPMMapper.jbpmHisTicketTaskListPage(page);
        return idcHisTickets;
    }
    /**
     * 获取代办额外信息
     */
    @Override
    public Map<String,Object> jbpmTaskExtraInfo(Map<String,Object> map) {
        return actJBPMMapper.jbpmTaskExtraInfo(map);
    }

    /**
     * 已办任务
     * @param pageBean
     * @param viewAuthorHisTask
     * @return
     */
    @Override
    public List<ViewAuthorHisTask> jbpmHisTaskQueryListPage(PageBean<ViewAuthorHisTask> pageBean, ViewAuthorHisTask viewAuthorHisTask) {
        pageBean.setParams(MapHelper.queryCondition(viewAuthorHisTask));
        return actJBPMMapper.jbpmHisTaskQueryListPage(pageBean);
    }
    @Override
    public Map<String,Object> getCategoryWithHisTicketMeta(String processDefinitionKey, Long ticketInstId)   {
        Map<String,Object> map = new HashMap<String,Object>();
        if(JBPMModelKEY.预占预勘察流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_预占预勘察流程.toString().equals(processDefinitionKey)){
            map = idcHisTicketPreAcceptMapper.getDataByTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.开通流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_开通流程.toString().equals(processDefinitionKey)){
				/*自行处理occupycomment*/
            map = idcHisTicketChangeMapper.getDataByCategoryTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.停机流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_停机流程.toString().equals(processDefinitionKey)){

            //得到开始停机时间和结束停机 时间
            map=idcHisTicketPauseMapper.getDataByTicketInstId(ticketInstId);

        }else if(JBPMModelKEY.复通流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_复通流程.toString().equals(processDefinitionKey)){
            //得到复通时间和和备注
            map=idcHisTicketRecoverMapper.getDataByTicketInstId(ticketInstId);

        } else if(JBPMModelKEY.业务变更流程.toString().equals(processDefinitionKey)||JBPMModelKEY.自有业务_业务变更流程.toString().equals(processDefinitionKey)){
				/*自行处理*/
            map = idcHisTicketChangeMapper.getDataByTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.开通变更流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_开通变更流程.toString().equals(processDefinitionKey)){
				/*自行处理*/
        }else if(JBPMModelKEY.下线流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_下线流程.toString().equals(processDefinitionKey)){
            //得到下线的原因和备注
            map=idcHisTicketHaltMapper.getDataByTicketInstId(ticketInstId);
        }
        return map;
    }
    @Override
    public Map<String,Object> getCategoryTicketMeta(String processDefinitionKey, Long ticketInstId)   {
        Map<String,Object> map = new HashMap<String,Object>();
        if(JBPMModelKEY.预占预勘察流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_预占预勘察流程.toString().equals(processDefinitionKey)){
            map = idcRunTicketPreAcceptMapper.getDataByTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.开通流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_开通流程.toString().equals(processDefinitionKey)){
				/*自行处理occupycomment*/
            map = idcRunTicketChangeMapper.getDataByCategoryTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.停机流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_停机流程.toString().equals(processDefinitionKey)){

            //得到开始停机时间和结束停机 时间
            map=idcRunTicketPauseMapper.getDataByTicketInstId(ticketInstId);

        }else if(JBPMModelKEY.复通流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_复通流程.toString().equals(processDefinitionKey)){
            //得到复通时间和和备注
            map=idcRunTicketRecoverMapper.getDataByTicketInstId(ticketInstId);

        } else if(JBPMModelKEY.业务变更流程.toString().equals(processDefinitionKey)||JBPMModelKEY.自有业务_业务变更流程.toString().equals(processDefinitionKey)){
				/*自行处理*/
            map = idcRunTicketChangeMapper.getDataByTicketInstId(ticketInstId);
        }else if(JBPMModelKEY.开通变更流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_开通变更流程.toString().equals(processDefinitionKey)){
				/*自行处理*/
        }else if(JBPMModelKEY.下线流程.toString().equals(processDefinitionKey) || JBPMModelKEY.自有业务_下线流程.toString().equals(processDefinitionKey)){
            //得到下线的原因和备注
            map=idcRunTicketHaltMapper.getDataByTicketInstId(ticketInstId);
        }
        return map;
    }
    /**
     * 调用修改流程引擎中的相关数据
     * @param modelId
     * @param editorSourceValueId
     * @param editorSourceExtraValueId
     * @param imageName
     * @param deploymentId
     * @throws Exception
     */
    @Override
    public void callActUpdateResource(String modelId,
                               String editorSourceValueId,
                               String editorSourceExtraValueId,
                               String imageName,
                               String deploymentId) throws Exception{
         actJBPMMapper.callActUpdateResource(modelId,editorSourceValueId,editorSourceExtraValueId,imageName,deploymentId);
    }
    @Override
    public Map<String,Object> getTaskIdAdnTaskName(Long procInstId){
        return actJBPMMapper.getTaskIdAdnTaskName(procInstId);
    }
    /**
     * 修改订单当前工单的状态和工单的状态
     * @param prodInstId
     * @param procticketStatus
     * @param ticketInstId
     */
    @Override
    public void callProcTicketStatus(Long prodInstId, Long procticketStatus,Long ticketInstId) throws Exception{
        //此存储过程仅仅是修改了表IDC_RE_PRODUCT、idc_his_ticket、idc_run_ticket的单子的状态
        actJBPMMapper.callProcTicketStatus(prodInstId,procticketStatus,ticketInstId);
    }

    @Override
    public Map<String, Object> callApplyResourceNum(Map<String, Object> map) {
        return actJBPMMapper.callApplyResourceNum(map);
    }

    public void callJbpmProcessProcJbpmApply(Map<String,Object> procJbpmApplyMap){
        //actJBPMMapper.callJbpmProcessProcJbpmApply(procJbpmApplyMap);
    }

    @Override
    public void callJbpmProcessProcJbpmOnlyRead(Map<String, Object> procJbpmApplyMap) {
        //actJBPMMapper.callJbpmProcessProcJbpmOnlyRead(procJbpmApplyMap);
    }

    public void callJbpmProcessProcJbpmHis(Map<String,Object> procJbpmApplyMap){
       // actJBPMMapper.callJbpmProcessProcJbpmHis(procJbpmApplyMap);
    }

    public void callJbpmProcessProcJbpmPreCreateData(Map<String,Object> procJbpmApplyMap){
        actJBPMMapper.callJbpmProcessProcJbpmPreCreateData(procJbpmApplyMap);
    }
    @Override
    public List<Map<String,Object>> jbpmLinkedHisTicketTaskListPage(PageBean pageBean, Map<String, Object> map){
        /* 查询分页信息 */
        pageBean.setParams(map);
        return actJBPMMapper.jbpmLinkedHisTicketTaskListPage(pageBean);
    }

    @Override
    public List<Map<String, Object>> ticketSatisfactionReportData(Map<String, Object> map) {
        List<Map<String, Object>> maps = actJBPMMapper.ticketSatisfactionReportData(map);
        return maps;
    }

    @Override
    public Map<String, Object> queryApplyerById(Long applyerId) {

        return actJBPMMapper.queryApplyerById(applyerId);
    }

    /*查看按照机位分配的工单，没有选择机位的资源。安装机位分配，选择了机架必须分配机位*/
    @Override
    public Long queryRackIsHaveUnit(Map<String, Object> map){
        return actJBPMMapper.queryRackIsHaveUnit(map);
    }
    @Override
    public Integer queryRackIsHaveUnitNEW(Long ticketInstId){
        return actJBPMMapper.queryRackIsHaveUnitNEW(ticketInstId);
    }

    @Override
    public void removeAllResourceMake(ApplicationContext applicationContext,String ticketInstId, String resourceCategory) throws Exception {

        //查询到所有的资源，不包括U位
        List<Map<String, Object>> maps = idcHisTicketResourceMapper.queryAllResourceByTicketInstId(ticketInstId,resourceCategory);

        for (Map<String, Object> resourceMap : maps) {
            Map<String,Object> remoteMap=new HashMap<>();
            String resourceId = String.valueOf(resourceMap.get("RESOURCEID"));
            String rackId = String.valueOf(resourceMap.get("RACK_ID"));
            String rack_or_rackunit = String.valueOf(resourceMap.get("RACK_OR_RACKUNIT"));
            String ip_type = String.valueOf(resourceMap.get("IP_TYPE"));

            if(resourceCategory.equals(ServiceApplyEnum.机架.value())){
                remoteMap.put("status", ResourceEnum.机架空闲.value());
                //如果是机架，并且是按照机位分配的，那么就还需要还要修改机位的状态为空闲
                if(rack_or_rackunit != null && rack_or_rackunit.equals(ResourceEnum.按照机位分.value().toString())){
                    idcHisTicketResourceMapper.updateRackUnitFree(rackId,ticketInstId);
                }
                //查询机架是否有关联MCB(一般都自带2个),如果有MCB，那么也要删除所有的MCB
                List<Map<String, Object>> mcbIdList = idcHisTicketResourceMapper.findMCBByRackTicket(ticketInstId);
                if(mcbIdList != null && mcbIdList.size()>0){
                    Map<String,Object> remoteMapMCB=new HashMap<>();
                    for (Map<String, Object> mcbMap : mcbIdList) {
                        String resourceid = String.valueOf(mcbMap.get("RESOURCEID"));
                        remoteMapMCB.put("status", ResourceEnum.MCB空闲.value());
                        remoteMapMCB.put("ticketId",ticketInstId);
                        remoteMapMCB.put("customerId","");
                        remoteMapMCB.put("customerName","");
                        remoteMapMCB.put("resourceCategory",ServiceApplyEnum.MCB.value());
                        remoteMapMCB.put("rackId",rackId);
                        remoteMapMCB.put("ipType",ip_type);
                        remoteMapMCB.put("id",resourceid);
                        RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteMapMCB);
                        applicationContext.publishEvent(remoteSysResourceEvent);
                    }
                }
                //上面修改完状态后，就要删除资源表的MCB
                //删除工单资源表的资源
                idcHisTicketResourceMapper.deleteMCBResourceByTicket(ticketInstId);
                idcRunTicketResourceMapper.deleteMCBResourceByTicket(ticketInstId);
            }else if(resourceCategory.equals(ServiceApplyEnum.端口带宽.value())){
                remoteMap.put("status", ResourceEnum.端口带宽空闲.value());
            }else if(resourceCategory.equals(ServiceApplyEnum.IP租用.value())){
                remoteMap.put("status", ResourceEnum.IP空闲.value());
            }else if(resourceCategory.equals(ServiceApplyEnum.主机租赁.value())){
                remoteMap.put("status", ResourceEnum.主机空闲.value());
            }

            remoteMap.put("ticketId",ticketInstId);
            remoteMap.put("customerId","");
            remoteMap.put("customerName","");
            remoteMap.put("resourceCategory",resourceCategory);
            remoteMap.put("rackId",rackId);
            remoteMap.put("ipType",ip_type);
            remoteMap.put("id",resourceId);
            if(rack_or_rackunit == null || !(rack_or_rackunit != null && rack_or_rackunit.equals(ResourceEnum.按照机位分.value()))){
                RemoteSysResourceEvent remoteSysResourceEvent = new RemoteSysResourceEvent(applicationContext,remoteMap);
                applicationContext.publishEvent(remoteSysResourceEvent);
            }
        }

        //删除工单资源表的资源
        idcHisTicketResourceMapper.deleteResourceByResourceCategory(resourceCategory,ticketInstId);
        idcRunTicketResourceMapper.deleteResourceByResourceCategory(resourceCategory,ticketInstId);
    }

    @Override
    public ResponseJSON resourceSetting(String resourceSettingData) throws Exception {
        if(resourceSettingData != null && !"".equals(resourceSettingData)){
            JSONArray arrayVoJson = JSONArray.fromObject(resourceSettingData);
            Collection collection=JSONArray.toCollection(arrayVoJson);
            if(collection!=null && !collection.isEmpty())
            {
                Iterator it=collection.iterator();
                while(it.hasNext())
                {
                    JSONObject jsonObj=JSONObject.fromObject(it.next());
                    IdcRunTicketResource idcRunTicketResource=(IdcRunTicketResource) JSONObject.toBean(jsonObj,IdcRunTicketResource.class);
                    if(idcRunTicketResource.getCategory().equals(ServiceApplyEnum.机架.value())){
                        idcRunTicketResource.setRack_Id(idcRunTicketResource.getResourceid());
                    }

                    //通过工单Id。。。
                    IdcRunTicket ticketObj = idcRunTicketMapper.getIdcRunTicketByIdTicketInstId(idcRunTicketResource.getTicketInstId());
                    /*客户必须有*/
                    Long customerId = ticketObj.getCustomerId();
                    String customerName = ticketObj.getCustomerName();
                    if(customerId == null ){
                        throw new Exception("客户ID不能是空!");
                    }
                    if (customerName == null || "".equals(customerName)){
                        logger.error("客户名称是空，可能会影响显示.但是对流程没影响。。。。:故重新查询获取名称");
                        customerName = idcReCustomerMapper.getIdcReCustomerNameById(customerId);
                    }
                    logger.debug("开始保存相关的数据到工单资源表信息[IDC_RUN_TICKET_RESOURCE/IDC_HIS_TICKET_RESOURCE]  start");

                    //此方法仅仅是保存资源到工单资源表中。但是如果机架自带了MCB，那么此方法虽然可以保存MCB到工单资源表，但是不能修改资源表的MCB的状态
                    ticketSetting(idcRunTicketResource);

                    Map<String,Object> paramMap = new HashMap<String,Object>();
                    /*业务说明：分配方式为机位分配时，rackOrracunit=66002，首先要选择机架，但是先择机架不需要修改机架的状态！！！*/
                    paramMap.put("rackOrracunit",jsonObj.get("rackOrracunit"));
                    /*业务说明：如果按机架分配，资源id就是机架id。如果按MCB或者U位分配，那么机架id就是单独的rackId了*/
                    paramMap.put("rackId",idcRunTicketResource.getRackId());
                    /*业务说明：如果按IP分配，就有按照IP网段或者按照单个IP地址分配的区别了*/
                    paramMap.put("ipType",idcRunTicketResource.getIpType());
                    paramMap.put("category",idcRunTicketResource.getTicketCategory());
                    paramMap.put("status",idcRunTicketResource.getStatus());
                    paramMap.put("resourceid",idcRunTicketResource.getResourceid());
                    paramMap.put("resourceCategory",idcRunTicketResource.getCategory());
                    paramMap.put("rackOrRackUnit",idcRunTicketResource.getRackOrRackUnit());
                    handTicketResourceAndRemoteCall(idcRunTicketResource.getTicketInstId(),paramMap);
                }
            }

        }
        return null;
    }

    @Override
    public Boolean getIExistsOtherTicket(Long ticketInstId) {
        return actJBPMMapper.getIExistsOtherTicket(ticketInstId);
    }

    @Override
    public String getExistsOtherTicket(Long ticketInstId) {
        return actJBPMMapper.getExistsOtherTicket(ticketInstId);
    }

    @Override
    public String getTabsTitleName(String ticketCategory, String prodCategory) {
        return actJBPMMapper.getTabsTitleName(ticketCategory,prodCategory);
    }

    public Integer ticketUnitSetting(IdcRunTicketResource idcRunTicketResource) throws Exception{
        logger.debug("创建工单资源中间表数据:机位。。。。");
        //然后保存相应的数据[此时需要保存正在运行时的工单资源信息]
        Map<String,Object> definedArrayMap = wapperMap(idcRunTicketResource);
        if(definedArrayMap == null || definedArrayMap.isEmpty()){
            return null;
        }/*占用的U位*/
        actJBPMMapper.callProcTicketResourceSaveArry(definedArrayMap);
        String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
        if(rowcountStr != null && !"0".equals(rowcountStr)){
            logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
            return DevContext.SUCCESS;
        }else{
			/*如果是已经存在则*/
            logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
            throw new Exception();
        }
    }

    public Long ticketSetting(IdcRunTicketResource idcRunTicketResource) throws Exception{
        Map<String,Object> definedArrayMap = wapperMap(idcRunTicketResource);
        if(definedArrayMap == null || definedArrayMap.isEmpty()){
            return null;
        }
        actJBPMMapper.callProcTicketResourceSaveArry(definedArrayMap);
        String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
        if(rowcountStr != null && !"0".equals(rowcountStr)){
            logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
            return Long.valueOf(DevContext.SUCCESS);
        }else{
			/*如果是已经存在则*/
            logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
            throw new Exception();
        }
    }
    public Long ticketRackSetting(IdcRunTicketResource idcRunTicketResource) throws Exception{
        logger.debug("创建工单资源中间表数据:机架。。。。");
        /*增加一个字段。展示列表信息*/
        //TicketRackGrid ticketRackGrid =  actJBPMMapper.getTicketRackGridInfoByResourceId(idcRunTicketResource.getResourceid());
        //然后序列化
        //String ticketRackGridStr = GsonUtil.object2Json(ticketRackGrid);
        //idcRunTicketResource.setTicketRackGrid(ticketRackGridStr);
        //然后保存相应的数据[此时需要保存正在运行时的工单资源信息]
        Map<String,Object> definedArrayMap = wapperMap(idcRunTicketResource);
        if(definedArrayMap == null || definedArrayMap.isEmpty()){
            return null;
        }
        actJBPMMapper.callProcTicketResourceSaveArry(definedArrayMap);
        String rowcountStr = String.valueOf(definedArrayMap.get("rowcount"));
        if(rowcountStr != null && !"0".equals(rowcountStr)){
            logger.debug("!!!!!!!!!保存成功!!!!!!!!!!:成功msg:["+definedArrayMap.get("msg")+"]");
            logger.debug("[保存机架的同时，需要将具有的MCB也一并保存到资源表中]");

            return Long.valueOf(String.valueOf(definedArrayMap.get("obj_meta_curr_id")));
        }else{
			/*如果是已经存在则*/
            logger.error("!!!!!!!!!保存失败!!!!!!!!!!:失败msg:["+definedArrayMap.get("msg")+"]");
            throw new Exception();
        }
    }

    public Map<String,Object> wapperMap(IdcRunTicketResource idcRunTicketResource){
        Map<String,Object> definedArrayMap = new HashMap<String,Object>();
        List<IdcRunTicketResource> idcRunTicketResourceList = new ArrayList<IdcRunTicketResource>();
        if(idcRunTicketResource != null ){
            idcRunTicketResourceList.add(idcRunTicketResource);
            definedArrayMap.put("itemList",idcRunTicketResourceList);
        }
        return definedArrayMap;
    }


}
