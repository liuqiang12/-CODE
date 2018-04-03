package com.JH.dgather.frame.gathercontrol.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.worker.IWorker;


public abstract class RootFutureTask {

	private boolean success = true;
	private long startTime = 0;
	private int timeout = 0;
	private FutureTask<ExecutiveResult> futureTask;
	String deviceName = "";
	IWorker worker;
	//add by xdwang 2012-11-22
	//确保在WORKER超时的时候，CONTROLL能正常获取其执行状态
	public ExecutiveResult workerResult = null;
	private Logger logger = Logger.getLogger(RootFutureTask.class);
	
	public RootFutureTask(IWorker worker) {
		this.worker = worker;
		futureTask = new FutureTask<ExecutiveResult>((Callable<ExecutiveResult>) worker);
		}

	public RootFutureTask(IWorker worker, long startTime, int timeout) {
		this.worker = worker;
		this.startTime = startTime;
		this.timeout = timeout;
		futureTask = new FutureTask<ExecutiveResult>((Callable<ExecutiveResult>) worker);
	}
	
	public FutureTask<ExecutiveResult> getFutureTask() {
		return futureTask;
	}

	public long getStartTime() {
		return startTime;
	}

	public int getTimeout() {
		return timeout;
	}


	public IWorker getWorker() {
		return worker;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setWorkerResult(ExecutiveResult workerResult) {
		this.workerResult = workerResult;
	}

	public ExecutiveResult getWorkerResult() {
		return workerResult;
	}
	
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public void close() {
		try {
			worker.close(deviceName);
		} catch (Exception e) {
			logger.error("关闭worker中的 su或telnet对象时，发生异常！", e);
		}
	}

}
