/*
 * @(#)Worker.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.worker;

import java.util.concurrent.Callable;

import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;

/**
 * <code>Worker</code> 数据采集和分析的工作线程 具有返回值的线程
 * @author Wang Xuedan
 * @version 1.0, 12/23/2011
 */
public interface IWorker extends Callable<ExecutiveResult> {
	public void close(String deviceName) throws Exception;
}
