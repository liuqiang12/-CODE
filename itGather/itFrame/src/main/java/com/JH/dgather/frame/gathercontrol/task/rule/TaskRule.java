/*
 * @(#)TaskRule.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.rule;

/**
 * <code>TaskRule</code> 任务规则接口 不同种类的任务对应有不同的任务规则数据
 * @author Wang Xuedan
 * @version 1.0 12/23/2011
 */
public abstract class TaskRule {
	/*
	 * 用于回调的任务结果对象
	 * <code>transient</code>是Java语言的关键字，用来表示一个域不是该对象串行化的一部分。当一个对象被串行化的时候，transient型变量的值不包括在串行化的表示中，然而非transient型的变量是被包括进去的。
	 */
	transient private TaskRuleExecutiveResultCallBack taskRuleExecutiveResultCallBack = null;
	
	public TaskRuleExecutiveResultCallBack getTaskRuleExecutiveResultCallBack() {
		return taskRuleExecutiveResultCallBack;
	}
	
	public void setTaskRuleExecutiveResultCallBack(TaskRuleExecutiveResultCallBack taskRuleExecutiveResultCallBack) {
		this.taskRuleExecutiveResultCallBack = taskRuleExecutiveResultCallBack;
	}
	
}
