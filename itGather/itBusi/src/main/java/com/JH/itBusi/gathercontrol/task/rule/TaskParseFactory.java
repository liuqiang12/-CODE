/*
 * @(#)TaskParseFactory.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.itBusi.gathercontrol.task.rule;

import com.JH.dgather.frame.gathercontrol.exception.NotFoundTaskRuleException;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskGcBean;
import com.JH.dgather.frame.gathercontrol.task.rule.TaskRule;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.LoadClass;


public class TaskParseFactory {

	/**
	 * <code>parse</code> 根据任务的类型，解析任务规则
	 * 
	 * @param task
	 *            需要解析的任务对象
	 * @return void
	 * @throws NotFoundTaskRuleException
	 */
	public static void parse(UserTask task) throws NullPointerException,
			NotFoundTaskRuleException {
		if (task == null)
			throw new NullPointerException("解析的任务为null!");
		
		if(GloableDataArea.taskGcMap !=null && GloableDataArea.taskGcMap.size()>0){
			TaskGcBean bean = GloableDataArea.taskGcMap.get(task.getGatherClass());
			if(bean !=null){
				task.setTaskRule((TaskRule)LoadClass.newClass(bean.getRule()));
			}else{
				throw new NotFoundTaskRuleException("未找到当前任务的规则，任务类型 "
						+ task.getGatherClass() + " 任务对象 " + UserTask.class);
			}
		}else{
			throw new NotFoundTaskRuleException("未找到系统可执行任务配置！请检查采集程序的ziyuan.properties里面的taskgc属性是否指向了正确的taskgc.xml文件以及确认taskgc.xml文件内的配置是否正确！");
		}
	}
}
