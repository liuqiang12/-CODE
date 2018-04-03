package com.bpm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.idc.model.ActJBPM;
import com.idc.model.JbpmResourceChildshape;
import com.idc.model.JbpmResourceModel;
import com.idc.model.JbpmTaskAuthorize;
import com.idc.service.ActJBPMService;
import com.idc.service.IdcReProductService;
import com.idc.service.JbpmResourceModelService;
import com.idc.service.JbpmTaskAuthorizeService;
import modules.utils.ResponseJSON;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.process.utils.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import system.data.page.PageBean;
import utils.DevContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by DELL on 2017/6/6.
 * 模型控制器
 */
@Controller
@RequestMapping("/modelController")
public class ModelController {
    private Logger logger = LoggerFactory.getLogger(ModelController.class);
    /* 必备的引入类 start*/
    /*  */
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

    /**
     * =================== model settings start ===================
     **/
    /* 必备的引入类 end*/
    @Autowired
    private JbpmTaskAuthorizeService jbpmTaskAuthorizeService;
    @Autowired
    private JbpmResourceModelService jbpmResourceModelService;
    @Autowired
    private IdcReProductService idcReProductService;
    @Autowired
    private ActJBPMService actJBPMService;
    @Autowired
    private RestTemplate restTemplate;/*接口调用模板*/
    /*新增模型界面弹出框  singleCreateModel*/
    @RequestMapping("/singleCreateModel.do")
    public String singleCreateModel(HttpServletRequest request) {
        return "activiti/repository/model/singleCreateModel";
    }

    /*修改模型界面*/
    @RequestMapping("/singleQueryModel.do")
    public String singleUpdateModel(HttpServletRequest request, org.springframework.ui.Model model) {
        String modelId = request.getParameter("modelId");

        try {
            //测试使用
            String workflowName = ServletRequestUtils.getStringParameter(request,"processId");
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(modelId) && StringUtils.isNotBlank(modelId)) {
            //获取相应的model信息
            Model actModel = repositoryService.getModel(modelId);
            model.addAttribute("actModel", actModel);
            model.addAttribute("modelId", modelId);
        }
        return "activiti/repository/model/singleQueryModel";
    }

    /* 模型设计界面 */
    @RequestMapping("/modeler.do")
    public String getEditor(HttpServletRequest request, org.springframework.ui.Model model) {
        String modelId = request.getParameter("modelId");
        if (StringUtils.isNotEmpty(modelId) && StringUtils.isNotBlank(modelId)) {
            model.addAttribute("modelId", modelId);
        }
        return "activiti/repository/model/modeler";
    }

    /*获取所有的流程模型*/
    @RequestMapping("/modelQueryGrid.do")
    public String modelQueryGrid(HttpServletRequest request) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>call开始调用接口  start<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String method = ServletRequestUtils.getStringParameter(request,"method","get");

        if ("get".equals(method)) {
            System.out.println("================查询方法===================");
            String resultStr = restTemplate.getForObject(DevContext.REST_URL+"jbpm/getHelloMessageEncode",String.class,"参数11111111");
            //getForEntity与getForObject的区别是可以获取返回值和状态、头等信息
            ResponseEntity<String> re = restTemplate.getForEntity(DevContext.REST_URL+"jbpm/getHelloMessageEncode",String.class, "呜呜呜呜");
            System.out.println("getForEntity与getForObject的区别是可以获取返回值和状态、头等信息");
            System.out.println(re.getStatusCode());
            System.out.println(re.getBody());
        }else if("post".equals(method)){
            /*System.out.println("================新增方法===================");
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Auth-Token", "e348bc22-5efa-4299-9142-529f07a18ac9");

            MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
            postParameters.add("owner", "11");
            postParameters.add("subdomain", "aoa");
            postParameters.add("comment", "");

            HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<MultiValueMap<String, String>>(postParameters, headers);

            ParseResultVo exchange = null;
            try {
                exchange = restTemplate.postForObject("http://l-dnsutil1.ops.beta.cn6.qunar.com:10085/v1/cnames/tts.piao",  requestEntity, ParseResultVo.class);
                logger.info(exchange.toString());
            } catch (RestClientException e) {
                logger.info("。。。。");
            }*/
           /* HttpHeaders headers = new HttpHeaders();
            MediaType mediaType= MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(mediaType);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            headers.add("X-Auth-Token", UUID.randomUUID().toString());

            JSONObject jsonObj = new JSONObject();
            *//*MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
            postParameters.add("id", "啊啊啊");
            postParameters.add("name", "部版本");*//*

            String value = DigestUtils.sha1Hex(DigestUtils.sha1Hex("xxxxxxxxxxxxxx"));
            jsonObj.put("id", "啊啊啊");
            jsonObj.put("value", value);
            jsonObj.put("reportTime", (new Date()).getTime());
            jsonObj.put("name", "部版本");


            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj.toString(), headers);
            JSONObject res = new JSONObject();
            res = restTemplate.postForObject(DevContext.REST_URL+"jbpm/insert_xxx", requestEntity,
                    JSONObject.class);*/

           /* String reqJsonStr = "{\"id\":227,\"name\":\"updateCC\", \"group\":\"UPDATE\",\"content\":\"updateCT\", \"order\":9}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            Form form = new Form();
            form.add("vcid", "2");
            form.add("dialogid","DID0001");
            form.add("flowno","FL0001");
            form.add("skillno","2");
            form.add("priority","222");*/
            /*说明：
            1）url: 请求地址；
            2）method: 请求类型(如：POST,PUT,DELETE,GET)；
            3）requestEntity: 请求实体，封装请求头，请求内容
            4）responseType: 响应类型，根据服务接口的返回类型决定
            5）uriVariables: url中参数变量值*/
            /*HttpEntity<String> entity = new HttpEntity<String>(reqJsonStr,headers);
            ResponseEntity<Map> resp = restTemplate.exchange(
                    DevContext.REST_URL+"jbpm/insert_xxx",
                    HttpMethod.PUT,
                    entity,
                    Map.class
            );*/

        }else if("delete".equals(method)){
            System.out.println("================删除方法===================");
            restTemplate.delete(DevContext.REST_URL+"jbpm/getHelloMessageEncode","aaa");
        }else if("put".equals(method)){
            System.out.println("================修改方法===================");
            restTemplate.put(DevContext.REST_URL+"jbpm/getHelloMessageEncode","aaa");
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>call调用接口    end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return "activiti/repository/model/modelQuery";
    }

    @RequestMapping("/modelQueryGridData.do")
    @ResponseBody
    public PageBean data(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        // 获取所有创建的流程模型
        List<Model> modelList = repositoryService.createModelQuery().listPage(result.getFirstResultNo() - 1, 100);
        /*过滤查询*/
        List<Model> modelListFilterParams = new ArrayList<Model>();
        //过滤的条件有
        String name = request.getParameter("name");

        //进行过滤条件
        if (StringUtils.isNotBlank(name) && StringUtils.isNotEmpty(name)) {
            Iterator<Model> it = modelList.iterator();
            while (it.hasNext()) {
                Model entityModel = it.next();
                String nametmp = entityModel.getName();
                if (nametmp.contains(name)) {
                    modelListFilterParams.add(entityModel);
                }
            }
            result.setTotalRecord(modelListFilterParams.size());
            result.setItems(modelListFilterParams);
        } else {
            result.setTotalRecord((int) repositoryService.createModelQuery().count());
            result.setItems(modelList);
        }
        return result;
    }

    /*创建流程*/
    @RequestMapping(value = "/create.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> create(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String id = request.getParameter("id");
        try {
            String name = request.getParameter("name"),/*流程名称*/
                    key = request.getParameter("key"),/*流程KEY*/
                    description = request.getParameter("category");/*流程类型[预受理、开通、停用等]*/
            map.put("succes", true);

            // 元数据信息
            if (name == null || "".equals(name))
                name = "name_empty";
            if (key == null || "".equals(key))
                key = "key_empty";
            if (description == null || "".equals(description))
                description = "description_empty";
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, org.apache.commons.lang3.StringUtils.defaultString(description));
             /*创建模型*/
            //如果模型ID没有
            Model newModel = repositoryService.newModel();

            newModel.setMetaInfo(modelObjectNode.toString());
            newModel.setName(name);
            newModel.setKey(key);
            newModel.setCategory(description);
            /* 模型入库 */
            repositoryService.saveModel(newModel);
            // 创建所必须的JSON数据
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");

            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");

            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));
            id = newModel.getId();
        } catch (Exception e) {
            map.put("succes", false);
            map.put("msg", "创建模型失败!");
            e.printStackTrace();
        }
        map.put("msg", "创建模型成功!");
        return map;
    }
    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "export/modelId.do")
    public void export(HttpServletResponse response) {
        String modelId = "25001";
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }


    /**
     * =================== model settings end ===================
     **/
    //发布流程
    @RequestMapping("/deploy/{modelId}/ctr.do")
    @ResponseBody
    public ResponseJSON deploy(@PathVariable String modelId) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            Model modelData = repositoryService.getModel(modelId);

            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            if(bpmnModel != null) {
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
                JbpmResourceModel jbpmResourceModel = new JbpmResourceModel();/*资源类型*/
                List<JbpmResourceChildshape> jbpmResourceChildshapeList = new ArrayList<JbpmResourceChildshape>();/*资源类型*/
                List<JbpmTaskAuthorize> jbpmTaskAuthorizeList = new ArrayList<JbpmTaskAuthorize>();/*资源类型*/
                ObjectNode modelJson = (ObjectNode) objectMapper.readTree(modelData.getMetaInfo());

                String editorSourceExtraValueId =  ((ModelEntity)modelData).getEditorSourceExtraValueId();//图片
                String editorSourceValueId = ((ModelEntity)modelData).getEditorSourceValueId();//json格式文件

                jbpmResourceModel.setModelId(modelId);
                jbpmResourceModel.setName(modelData.getName());
                jbpmResourceModel.setEditorSourceExtraValueId(Long.valueOf(((ModelEntity)modelData).getEditorSourceExtraValueId()));
                jbpmResourceModel.setEditorSourceValueId(Long.valueOf(((ModelEntity)modelData).getEditorSourceValueId()));
                jbpmResourceModel.setKey(modelData.getKey());

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();

                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String processName = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";

                Deployment deployment = repositoryService.createDeployment()
                        .name(modelData.getName()).addBpmnModel(processName,bpmnModel)//.addString(processName, new String(bpmnBytes, "UTF-8"))
                        .deploy();

                ActJBPM actJBPM = idcReProductService.getActBytesByEditorSourceExtraValueId(editorSourceExtraValueId);

                actJBPMService.callActUpdateResource(modelId,editorSourceValueId,editorSourceExtraValueId,modelData.getKey()+"."+modelData.getKey()+".png",deployment.getId());
                for(FlowElement e : flowElements) {
                    if(e instanceof UserTask){
                        System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());

                        /*====================下周上午只需要保存这个三个数据到数据库中
                        JBPM_RESOURCE_MODEL
                        JBPM_RESOURCE_CHILDSHAPE
                        JBPM_TASK_AUTHORIZE
                        ======================================*/
                        JbpmResourceChildshape jbpmResourceChildshape = new JbpmResourceChildshape();
                        jbpmResourceChildshape.setTaskName(e.getName());
                        jbpmResourceChildshape.setTaskId(e.getId());
                        jbpmResourceChildshape.setFormKey(((UserTask) e).getFormKey());
                        /* -用户- */
                        List<String> groups = ((UserTask) e).getCandidateGroups();

                        jbpmResourceChildshape.setGroups(groups);
                        jbpmResourceChildshapeList.add(jbpmResourceChildshape);

                    }
                }
                /********          保存save            ********/
                jbpmTaskAuthorizeService.createAuthorize(jbpmResourceModel,jbpmResourceChildshapeList);
                result.setMsg("部署成功，部署ID=" + deployment.getId());
            }

        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/test.do")
    @ResponseBody
    public ResponseJSON test() throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            List list =  jbpmTaskAuthorizeService.queryActModelWithRoleKeyRoleId("idc_customer_manager",22l);
            result.setMsg("查询数据");
        } catch (Exception e) {
            logger.error("查询失败", e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping("/processDefinitionQuery.do")
    public String processDefinitionQuery(HttpServletRequest request, PageBean result) {
        return "activiti/repository/processDefinition/processDefinitionQuery";
    }
    //启动流程
    @RequestMapping("/processDefinitionQueryGridData.do")
    @ResponseBody
    public PageBean processDefinitionQueryGridData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        // 获取所有创建的流程模型
        List<ProcessDefinition> modelList = repositoryService.createProcessDefinitionQuery().listPage(result.getFirstResultNo() - 1, result.getMaxResultNo());
        List<Map<String, Object>> rstLst = new ArrayList<Map<String, Object>>();
        Map<String, Object> data;
        for (ProcessDefinition definition : modelList) {
            data = new HashMap<String, Object>();
            data.put("id", definition.getId());
            data.put("name", definition.getName());
            data.put("key", definition.getKey());
            data.put("category", definition.getCategory());
            data.put("description", definition.getDescription());
            data.put("version", definition.getVersion());
            data.put("resourceName", definition.getResourceName());
            data.put("dgrmResourceName", definition.getDiagramResourceName());
            rstLst.add(data);
        }
        result.setTotalRecord((int) repositoryService.createProcessDefinitionQuery().count());
        result.setItems(rstLst);
        return result;
    }
    //通过流程key 启动流程

    @RequestMapping("/startProcessByKey/{key}")
    @ResponseBody
    public ResponseJSON startProcessByKey(@PathVariable String key) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            // 设置流程任务中使用表达式会用到的表达式参数
            Map variables = new HashMap();
            variables.put("applyUserName", "fozzie");
            ProcessInstance instance = runtimeService.startProcessInstanceByKey(key);
            result.setMsg("启动成功，实例ID=" + instance.getId());
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", key, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    //查看图片信息
    @RequestMapping("/processDefinitionImage/{processDefId}")
    public String processDefinitionImage(HttpServletRequest request, @PathVariable String processDefId, org.springframework.ui.Model model) {
        model.addAttribute("processDefId",processDefId);
        return "activiti/repository/images/processDefinitionImage";
    }
    @RequestMapping("/processDefinitionImageShow/{processDefId}")
    @ResponseBody
    public void processDefinitionImageShow(HttpServletRequest request,HttpServletResponse response,@PathVariable String processDefId) throws IOException {

        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
        String diagramResourceName = procDef.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(
                procDef.getDeploymentId(), diagramResourceName);
        //获取了流就返回
        OutputStream out = response.getOutputStream();
        int len;
        byte[] data = new byte[1024];        //可以根据实际情况调整，建议使用1024，即每次读1KB
        while ((len = imageStream.read(data)) != -1) {
            out.write(data, 0, len);                    //建议不要直接用os.write(bt)
        }
        out.flush();
        out.close();
        imageStream.close();

    }
    //获取所有的活动节点信息

    @RequestMapping("/queryTaskQuery.do")
    public String queryTaskQuery(HttpServletRequest request) {
        return "activiti/repository/task/queryTaskQuery";
    }
    @RequestMapping("/queryTaskQueryData.do")
    @ResponseBody
    public PageBean queryTaskQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        // 获取所有创建的流程模型
        List<ProcessDefinition> modelList = repositoryService.createProcessDefinitionQuery().listPage(result.getFirstResultNo() - 1, result.getMaxResultNo());
        //客户经理申请
        /*TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroup("type_customer_manager@id_22");*/
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroup("government_idc_manager@id_23");
        List<Task> tasks = taskQuery.listPage(0, 10);
        List<Map<String, Object>> rstLst = new ArrayList<Map<String, Object>>();
        Map<String, Object> data;
        for (Task task : tasks) {
            data = new HashMap<String, Object>();
            data.put("id", task.getId());
            data.put("name", task.getName());
            data.put("category", task.getCategory());
            data.put("createTime", task.getCreateTime());
            data.put("description", task.getDescription());
            rstLst.add(data);
        }
        result.setTotalRecord((int) repositoryService.createProcessDefinitionQuery().count());
        result.setItems(rstLst);
        return result;
    }
    //完成任务
    @RequestMapping("/completeByTaskId/{taskId}")
    @ResponseBody
    public ResponseJSON completeByTaskId(@PathVariable String taskId) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            // 设置流程任务中使用表达式会用到的表达式参数
            taskService.complete(taskId);
            result.setMsg("完成任务，任务ID=" + taskId);
        } catch (Exception e) {
            logger.error("根据完成任务失败：modelId={}", taskId, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    //跳转到高亮流程图
    @RequestMapping("/viewHisProcessImageByTaskId/{taskId}")
    public String viewHisProcessImageByTaskId(HttpServletRequest request,HttpServletResponse response,@PathVariable String taskId,org.springframework.ui.Model model) throws IOException {
        model.addAttribute("taskId",taskId);
        return "activiti/repository/task/viewHisProcessImageByTaskId";
    }
    //跳转到高亮流程图
    @RequestMapping("/processDefinitionImageShow/{taskId}")
    @ResponseBody
    public void viewHisProcessImageShowByTaskId(HttpServletRequest request,HttpServletResponse response,@PathVariable String taskId) throws IOException {
        //高亮流程图

    }
    @RequestMapping("/getActivitiProccessImageByprocessInstanceId/{processInstanceId}")
    public String getActivitiProccessImageByprocessInstanceId(HttpServletRequest request,HttpServletResponse response,@PathVariable String processInstanceId,org.springframework.ui.Model model) throws IOException {
        model.addAttribute("processInstanceId",processInstanceId);

        return "activiti/repository/processDefinition/activitiProccessImageByprocessInstanceId";
    }
    /**
     * 获取流程图像，已执行节点和流程线高亮显示
     */
    @RequestMapping("/actPocImageShowByProcessInstanceId/{processInstanceId}")
    public void actPocImageShowByProcessInstanceId(@PathVariable String processInstanceId, HttpServletResponse response) throws Exception {
        logger.info("[开始]-获取流程图图像");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();

            if (historicProcessInstance == null) {
                throw new Exception("获取流程实例ID[" + processInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                // 获取流程图图像字符流
                InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);


                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            logger.error("【异常】-获取流程图失败！" + e.getMessage());
            throw new Exception("获取流程图失败！" + e.getMessage());
        }
    }
    /*
        显示图片 processInstanceId:45001
                processDefinitionId:idc_run_ticket_pre_accept:1:42538
                processDefinitionKey:idc_run_ticket_pre_accept
     */
    @RequestMapping("/diagramInfo/{processInstanceId}/{processDefinitionId}/{processDefinitionKey}")
    public String diagramInfo(HttpServletRequest request,HttpServletResponse response,@PathVariable String processInstanceId,@PathVariable String processDefinitionId,@PathVariable String processDefinitionKey,org.springframework.ui.Model model) throws IOException {
        model.addAttribute("processDefinitionId",processDefinitionId);
        model.addAttribute("processInstanceId",processInstanceId);
        model.addAttribute("processDefinitionKey",processDefinitionKey);
        return "activiti/repository/images/diagramInfo";
    }

}
