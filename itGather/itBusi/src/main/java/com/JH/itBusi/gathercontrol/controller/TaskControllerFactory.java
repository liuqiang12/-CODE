/*
 * @(#)TaskControllerFactory.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.itBusi.gathercontrol.controller;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.exception.NotFoundControllerException;
import com.JH.dgather.frame.gathercontrol.exception.NotFoundTaskRuleException;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskGcBean;
import com.JH.dgather.frame.gathercontrol.task.rule.TaskRule;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.LoadClass;



//
public class TaskControllerFactory {
	private Logger logger = Logger.getLogger(TaskControllerFactory.class);

	/**
	 * <code>getGatherController</code> 获取任务控制器对象
	 * 
	 * @param taskRule
	 *            任务规则实例
	 * @return 任务控制器实例
	 * @throws NotFoundControllerException
	 *             如同未找到相应的控制器，则抛出此异常
	 * @throws NotFoundTaskRuleException 
	 */
	public ExecutiveController getGatherController(UserTask task)
			throws NullPointerException, NotFoundControllerException, NotFoundTaskRuleException {
		if (task == null)
			throw new NullPointerException("任务对象(UserTask)为null!");

		TaskRule taskRule = task.getTaskRule();
		if (taskRule == null)
			throw new NullPointerException("任务规则为null!");
		else
			logger.info("本次处理taskRule："+taskRule.toString());
		if(GloableDataArea.taskGcMap !=null && GloableDataArea.taskGcMap.size()>0){
			TaskGcBean bean = GloableDataArea.taskGcMap.get(task.getGatherClass());
			if(bean !=null){
				ExecutiveController con = LoadClass.newClass(bean.getController(), task);
				if(con == null){
					throw new NotFoundControllerException("初始化当前任务规则的控制器为空！规则对象 "
							+ taskRule.toString());
				}else{
					return con;
				}
			}else{
				throw new NotFoundControllerException("未找到当前任务规则的控制器！规则对象 "
						+ taskRule.toString());
			}
		}else{
			throw new NotFoundTaskRuleException("未找到系统可执行任务配置！请检查采集程序的sys.properties里面的taskgc属性是否指向了正确的taskgc.xml文件以及确认taskgc.xml文件内的配置是否正确！");
		}

	}
}
