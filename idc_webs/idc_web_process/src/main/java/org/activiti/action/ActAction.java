package org.activiti.action;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import system.data.supper.action.BaseController;
/**
 * process handler
 * @author DELL
 *
 */
@Controller
@RequestMapping("/activiti_process")
public class ActAction extends BaseController{

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
	/**
	 * 流程引擎的整体过程 
	 * 1:部署 
	 * 2:发布 
	 * 3:开始走流程[代办任务、完成任务、历史任务、会签任务、驳回任务、任务结束]
	 */
	 /**
	  * 部署
	  */
	public void deployByZip(HttpServletRequest request) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/resouce_process.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("占用资源流程")//添加部署名称
                .addZipInputStream(zipInputStream)//完成zip文件的部署
                .deploy();//完成部署
        logger.debug("部署ID："+deployment.getId());
        logger.debug("部署名称:"+deployment.getName());
	}
	/**
	 * 发布
	 */
	public void publish(HttpServletRequest request){
		
	}
}
