/*
 * @(#)TaskRuleExecutiveResultCallBack.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.rule;

import java.util.concurrent.Callable;

import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;

/**
 * <code>TaskRuleExecutiveResultCallBack</code> 用于回调的任务结果类, 具有返回值的线程
 * @author Wang Xuedan
 * @version 1.0, 12/23/2011
 */
public class TaskRuleExecutiveResultCallBack implements Callable<ExecutiveResult> {
	protected ExecutiveResult executiveResult;
	
	public void saveExecutiveResult(ExecutiveResult executiveResult) {
		this.executiveResult = executiveResult;
	}
	
	@Override
	public ExecutiveResult call() throws Exception {
		while (executiveResult == null) {
			Thread.sleep(100);
		}
		return this.executiveResult;
	}
	
}
