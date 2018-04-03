/*
 * @(#)ExecutiveController.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.global.GlobalThreadPool;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

/**
 * <code>ExecutiveController</code> 执行数据采集和分析的控制器接口类
  * @version 1.0, 12/23/2011 muyp modify 2012-01
 */
public abstract class ExecutiveController extends Observable {
	private Logger logger = Logger.getLogger(ExecutiveController.class);
	public UserTask userTask = null;

	private int threadMaxRunSec = 3000;// 任务执行的最长时间，单位秒

	public ExecutiveController(UserTask userTask) {
		this.threadMaxRunSec = 3000;
		this.userTask = userTask;
	}
	
	
	public ExecutiveController(UserTask userTask, int threadMaxRunSec) {
		this.userTask = userTask;
		this.threadMaxRunSec = threadMaxRunSec;
	}

	/**
	 * <code>execute</code> 数据采集和分析的执行方法
	 * 
	 * @return 执行结果对象
	 */
	public abstract ExecutiveResult execute();

	// private static int THREADMAXRUNSec = 3000;//任务执行的最长时间，单位秒
	/**
	 * 初始化数据
	 */
	protected abstract void doInit();

	/**
	 * 任务完成后执行的操作,由具体子类执行
	 */
	protected abstract void doFinish(int runFlag);

	private List<ICSFutureTask> futureList = new ArrayList<ICSFutureTask>();
	//private ArrayList<RootWorker> taskList = new ArrayList<RootWorker>();
	private List<ExecutiveResult> taskResultLs = new ArrayList<ExecutiveResult>();
	
	public void close() {
		futureList = null;
		taskResultLs = null;
	}
	/**
	 * 将task添加到任务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public void addTaskToPool(RootWorker worker) {
		ICSFutureTask icsFutureTask = new ICSFutureTask(worker, System.currentTimeMillis(), threadMaxRunSec);
		
		futureList.add(icsFutureTask);
		//taskList.add(worker);
	}

	/**
	 * muyp 启动被控制的所有任务
	 * 
	 * @param min分钟
	 *            整个任务执行最长时间，超出时间则跳出
	 * @throws InterruptedException
	 */
	protected List<ExecutiveResult> startTask(int minut) {
		this.threadMaxRunSec = minut*60;
		GlobalThreadPool.loggerPoolSize("[" + this.userTask.getTaskName() + "]work加入线程池前");
		int size = futureList.size();
		if (size == 0) {
			return null;
		}

		for (ICSFutureTask futureTask : futureList) {
			try {
				GlobalThreadPool.executeWorkingThreadInPool(futureTask);
			} catch (Exception e) {
				futureTask.setSuccess(false);
				continue;
			}
		}

		GlobalThreadPool.loggerPoolSize("[" + this.userTask.getTaskName() + "]work加入线程池后");
		// 设置任务握有各个下属worker的future
		//GlobalThreadPool.MAIN_TASK_MAP.get(this.userTask.getTaskId()).setSubFutureList(futureList);

		return getTaskResult();
	}

	//解决并发取worker结果，如有问题，可直接恢复下方用"/**/"注释的代码
	protected List<ExecutiveResult> getTaskResult() {
		// logger.info(":::::::任务执行的最长时间:" + this.threadMaxRunSec);
		logger.info("worker 内的任务ID:" + this.userTask.getTaskId() + " futureList.size(): " + futureList.size());
		long sT = System.currentTimeMillis();
		while (futureList.size() > 0) {
			List<ICSFutureTask> removeLs = new ArrayList<ICSFutureTask>();

			Iterator<ICSFutureTask> iterator = futureList.iterator();
			ExecutiveResult eResult = null;
			while (iterator.hasNext()) {
				ICSFutureTask fT = iterator.next();
				//worker结束
				if (fT.getFutureTask().isDone()) {
					eResult = GlobalThreadPool.getGatherResult(fT);
					if (eResult != null) {
						taskResultLs.add(eResult);
						fT.getWorkerResult().setRunTask(TaskRunFlag.SUCCESS);
					}
					removeLs.add(fT);
				} else {
					//判断是否超时
					if ((System.currentTimeMillis() - fT.getStartTime()) / 1000 >= fT.getTimeout()) {
						//超时，中断worker
						fT.getFutureTask().cancel(true);
						fT.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
						fT.getWorkerResult().setSuccessSize(0);
						fT.close();
						logger.error("[[[WORKER CALCEL]]], 默认超时时间：" + fT.getTimeout() + ", WORKER执行起始时间: " + fT.getStartTime());
						removeLs.add(fT);
					}
				}
			}
			futureList.removeAll(removeLs);
			removeLs = null;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if ((System.currentTimeMillis() - sT) / 1000 >= 1) {
				sT = System.currentTimeMillis();
				
			}
		}
		return taskResultLs;
	}

	/*
	protected List<ExecutiveResult> getTaskResult() {
		// logger.info(":::::::任务执行的最长时间:" + this.threadMaxRunSec);
		logger.info("worker 内的任务ID:" + this.userTask.getTaskId());
		for (ICSFutureTask workerFuture : futureList) {
			ExecutiveResult result=null;
			try {
				result = GlobalThreadPool.getGatherResult(workerFuture);
			} catch (Exception e) {
				result=null;
				logger.error("work运行异常：", e);
			}
			if (result != null) {
				taskResultLs.add(result);
			}
			this.setChanged();
			this.notifyObservers("XX已经执行");
		}

		GlobalThreadPool.loggerPoolSize("[" + this.userTask.getTaskName() + "]执行结束后");
		return taskResultLs;
	}
	*/

	public int getTaskResultSize() {
		logger.info("任务执行完成的结果数：" + this.taskResultLs.size());
		Iterator<ExecutiveResult> it = this.taskResultLs.iterator();
		ExecutiveResult task;
		int successSize = 0;
		while (it.hasNext()) {
			task = it.next();
			if (task.getSuccessSize() == 0) {
				task.setRunTask(TaskRunFlag.FINAL);
			} else {
				task.setRunTask(TaskRunFlag.SUCCESS);
			}
			successSize += task.getSuccessSize();
		}
		return successSize;
	}

	//
	//	public void setThreadMaxRunSec(int threadMaxRunSec) {
	//		this.threadMaxRunSec = threadMaxRunSec;
	//	}

	public List<ICSFutureTask> getFutureList() {
		return futureList;
	}

}