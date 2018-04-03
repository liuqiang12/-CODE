package com.JH.dgather.frame.gathercontrol.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.worker.IWorker;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker4Obj;

public class ICSFutureTask4Obj extends RootFutureTask {
	private Logger logger = Logger.getLogger(ICSFutureTask4Obj.class);
	public ICSFutureTask4Obj(RootWorker4Obj worker) {
		super(worker);

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
	public ICSFutureTask4Obj(RootWorker4Obj worker, long startTime, int timeout) {
		super(worker, startTime,  timeout);
		this.workerResult = worker.getWorkerResult();
	}


}
