package org.activiti.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.bpmn.model.DataGrid;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.idc.service.TestResoucesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import system.data.msg.ajax.AjaxJson;
import system.data.page.PageBean;
import system.data.supper.action.BaseController;

import org.activiti.process.utils.HistoryProcessInstanceDiagramCmd;
import com.idc.model.TestResouces;


/**
 * @Description: (工作流程定义与实例等资源处理类)
 *
 */
@Controller
@RequestMapping("/activiti")
public class ActivitiAction extends BaseController{

	private static final Logger logger = Logger.getLogger(ActivitiAction.class);
	
	@Autowired
	protected RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
    private IdentityService identityService;
	
	@Autowired
	private TestResoucesService resoucesService;
	/**
	 * 画流程图
	 */
	@RequestMapping("drawProcessList.do")
	public ModelAndView drawProcessList(HttpServletRequest request) {
		return new ModelAndView("activiti/drawProcessList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 */

	@RequestMapping("datagrid.do")
	public void datagrid(HttpServletRequest request, HttpServletResponse response) {
		
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = query.list();
		
		StringBuffer rows = new StringBuffer();
		int i = 0;
		for(ProcessDefinition pi : list){
			i++;
			rows.append("{'id':"+i+",'processDefinitionId':'"+pi.getId() +"','startPage':'"+pi.getDescription()+"','resourceName':'"+pi.getResourceName()+"','deploymentId':'"+pi.getDeploymentId()+"','key':'"+pi.getKey()+"','name':'"+pi.getName()+"','version':'"+pi.getVersion()+"','isSuspended':'"+pi.isSuspended()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+query.count()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 流程定义列表
	 */
	@RequestMapping(params = "queryProcessList.do")
	public ModelAndView queryProcessList(HttpServletRequest request) {
		return new ModelAndView("activiti/process/queryProcesslist");
	}
	
	/**
     * 请假流程启动
     */
	@RequestMapping("resouce_process.do")
	@ResponseBody
	public AjaxJson resouce_process(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		//请假流程启动
//		 leaveDao.save(entity);
        logger.debug("save entity: {}");
        
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        // 创建发布配置对象
//        DeploymentBuilder builder = repositoryService.createDeployment();
//        // 获得上传文件的输入流程
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/resouce_process.rar");
//        ZipInputStream zipInputStream = new ZipInputStream(in);
//        // 设置发布信息
//        builder.name("预占勘察流程").addZipInputStream(zipInputStream);// 添加部署规则的显示别名
//        // 完成发布
//        builder.deploy();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/resouce_process.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("占用资源流程")//添加部署名称
                .addZipInputStream(zipInputStream)//完成zip文件的部署
                .deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
		String message = "流程启动成功";
		j.setMsg(message);
		return j;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/dblistPage.do")
	public void resouceCtlAction_ResBuildTable(PageBean<TestResouces> page,TestResouces testResouces) throws Exception {
		//代办任务
		String userId = "1";
		TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).active().list();//.taskCandidateGroup("hr").active().list();
		
		StringBuffer rows = new StringBuffer();
		//获取代办任务
		for(Task t : tasks){
			// query testresouce with taskid
			TestResouces tempResouces = new TestResouces();
			tempResouces.setTaskid(t.getId());
			tempResouces = resoucesService.getModelByObject(tempResouces);
			rows.append("{'name':'"+t.getName() +"','taskId':'"+t.getId()+"','gdcode':'"+tempResouces.getGdcode()+"','id':'"+tempResouces.getId()+"','lxr':'"+tempResouces.getLxr()+"','usercode':'"+tempResouces.getUsercode()+"','processInstanceId':'"+t.getProcessInstanceId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+tasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
		
		
//		resoucesService.queryListPage(page,testResouces);
//		return page;
	}
	@RequestMapping("applyStart.do")
	@ResponseBody
	public AjaxJson applyStart(HttpServletRequest request,TestResouces resouces) {
		AjaxJson j = new AjaxJson();
		
		//请假流程启动
//		 leaveDao.save(entity);
		logger.debug("save entity: {}");
		
		String businessKey = "1";
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId("1");
            Map<String, Object> variables = new HashMap<String, Object>();
            processInstance = runtimeService.startProcessInstanceByKey("resouce_process_id", businessKey, variables);
            String processInstanceId = processInstance.getId();
            logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}");
            resouces.setStatus("审批中");
            //update busness
            resoucesService.updateByObject(resouces);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            identityService.setAuthenticatedUserId(null);
        }
		String message = "流程启动成功";
		j.setMsg(message);
		return j;
	}
	
	 
	/**
	 * easyui 运行中流程列表页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "queryRunningProcessList.do")
	public ModelAndView queryRunningProcessList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		return new ModelAndView("activiti/process/queryRunningProcessList");
	}
	
	/**
	 * 我的流程定义
	 */
	@RequestMapping(params = "myProcessList")
	public ModelAndView myProcessList(HttpServletRequest request) {
			return new ModelAndView("jeecg/activiti/my/myProcessList");
	}
	
	
	
	/**
	 * 流程启动表单选择
	 */
	@RequestMapping("startPageSelect")
	public ModelAndView startPageSelect(HttpServletRequest request) {
			//目前规定 资源占用流程
			return new ModelAndView("activiti/process/resouce_process");
	}
	
	
	 
	/**
	 * easyui 运行中流程列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "runningProcessDataGrid")
	public void runningProcessDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		/*List<HistoricProcessInstance> historicProcessInstances = historyService
                .createHistoricProcessInstanceQuery()
                .unfinished().list();*/
		 ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
	     List<ProcessInstance> list = processInstanceQuery.list();
		
		StringBuffer rows = new StringBuffer();
		for(ProcessInstance hi : list){
			rows.append("{'id':"+hi.getId()+",'processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getProcessInstanceId()+"','activityId':'"+hi.getActivityId()+"'},");
		}
		
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+list.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	
	/**
	 * 读取工作流定义的图片或xml
	 * @throws Exception
	 */
	@RequestMapping("resourceRead")
    public void resourceRead(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
	
	/**
	 * 读取带跟踪的流程图片
	 * @throws Exception
	 */
	@RequestMapping("traceImage")
    public void traceImage(@RequestParam("processInstanceId") String processInstanceId,
    		HttpServletResponse response) throws Exception {
    	
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine(); 
        InputStream is = processEngine.getManagementService().executeCommand(
                cmd);
        
        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
        	response.getOutputStream().write(b, 0, len);
        }
    }
	
	/**
	 * easyui 流程历史页面
	 * @param request
	 */

	@RequestMapping(params = "viewProcessInstanceHistory")
	public ModelAndView viewProcessInstanceHistory(@RequestParam("processInstanceId") String processInstanceId,
			HttpServletRequest request, HttpServletResponse respone,Model model) {
		
		model.addAttribute("processInstanceId", processInstanceId);
		
		return new ModelAndView("jeecg/activiti/process/viewProcessInstanceHistory");
	}
	
	/**
	 * easyui 流程历史数据获取
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "taskHistoryList")
	public void taskHistoryList(@RequestParam("processInstanceId") String processInstanceId,
			HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		
        List<HistoricTaskInstance> historicTasks = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).list();
        
        StringBuffer rows = new StringBuffer();
        for(HistoricTaskInstance hi : historicTasks){
			rows.append("{'name':'"+hi.getName()+"','processInstanceId':'"+hi.getProcessInstanceId() +"','startTime':'"+hi.getStartTime()+"','endTime':'"+hi.getEndTime()+"','assignee':'"+hi.getAssignee()+"','deleteReason':'"+hi.getDeleteReason()+"'},");
        	//System.out.println(hi.getName()+"@"+hi.getAssignee()+"@"+hi.getStartTime()+"@"+hi.getEndTime());
        }
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicTasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	
	/**
     * 删除部署的流程，级联删除流程实例
     * @param deploymentId 流程部署ID
     */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(@RequestParam("deploymentId") String deploymentId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		repositoryService.deleteDeployment(deploymentId, true);
		
		String message = "删除成功";
		j.setMsg(message);
		return j;
	}
	
	
	 
	
	/**
	 * easyui 待领任务页面
	 */
	@RequestMapping(params = "waitingClaimTask")
	public ModelAndView waitingClaimTask() {
		
		return new ModelAndView("jeecg/activiti/process/waitingClaimTask");
	}
	
	/**
	 * easyui AJAX请求数据 待领任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "waitingClaimTaskDataGrid")
	public void waitingClaimTaskDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = "hruser";
		TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).active().list();//.taskCandidateGroup("hr").active().list();
		
		StringBuffer rows = new StringBuffer();
		for(Task t : tasks){
			rows.append("{'name':'"+t.getName() +"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+tasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * easyui 待办任务页面
	 */
	@RequestMapping(params = "claimedTask")
	public ModelAndView claimedTask() {
		
		return new ModelAndView("jeecg/activiti/process/claimedTask");
	}
	
	/**
	 * easyui AJAX请求数据 待办任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "claimedTaskDataGrid")
	public void claimedTaskDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = "leaderuser";
		TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		
		StringBuffer rows = new StringBuffer();
		for(Task t : tasks){
			rows.append("{'name':'"+t.getName() +"','description':'"+t.getDescription()+"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"','processInstanceId':'"+t.getProcessInstanceId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+tasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * easyui 已办任务页面
	 */
	@RequestMapping(params = "finishedTask")
	public ModelAndView finishedTask() {
		
		return new ModelAndView("jeecg/activiti/process/finishedTask");
	}
	
	/**
	 * easyui AJAX请求数据 已办任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "finishedTaskDataGrid")
	public void finishedTask(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = "leaderuser";
		List<HistoricTaskInstance> historicTasks = historyService
                .createHistoricTaskInstanceQuery().taskAssignee(userId)
                .finished().list();
		
		StringBuffer rows = new StringBuffer();
		for(HistoricTaskInstance t : historicTasks){
			rows.append("{'name':'"+t.getName() +"','description':'"+t.getDescription()+"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"','processInstanceId':'"+t.getProcessInstanceId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicTasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}

	/**
     * 签收任务
     * @param taskId
     */
	@RequestMapping("claimTask")
	@ResponseBody
	public AjaxJson claimTask(@RequestParam("taskId") String taskId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		String userId = "leaderuser";
		
		TaskService taskService = processEngine.getTaskService();
        taskService.claim(taskId, userId);
		
		String message = "签收成功";
		j.setMsg(message);
		return j;
	}
	
		// -----------------------------------------------------------------------------------
		// 以下各函数可以提成共用部件 (Add by Quainty)
		// -----------------------------------------------------------------------------------
		public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			try {
				PrintWriter pw=response.getWriter();
				pw.write(jObject.toString());
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
