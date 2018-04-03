package com.JH.dgather.frame.gathercontrol.controller;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.worker.RootWorker;

/**
 * @author gamesdoa
 * @param <V>
 * @email gamesdoa@gmail.com
 * @date 2012-11-5
 * @modify 2012-11-13 by xdwang
 */

public class ICSFutureTask extends RootFutureTask {
	private Logger logger = Logger.getLogger(ICSFutureTask.class);

//	private RootWorker worker;

	//String deviceName = "";

	public ICSFutureTask(RootWorker worker) {
		super(worker);
		logger.debug("ICSFutureTask初始化");
		this.workerResult = worker.getWorkerResult();
	}

	/*
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-11-6
	 * @param callable
	 * 
	 * @param startTime 启动时间
	 * @param timeout 超时时间长度
	 */
	public ICSFutureTask(RootWorker worker, long startTime, int timeout) {
		super(worker, startTime,timeout);
		this.workerResult = worker.getWorkerResult();
	}





}
