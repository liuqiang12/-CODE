package com.JH.dgather.frame.gathercontrol.controller;
/*author muyp
 * 针对不以用户任务模式执行，而是以设备对象方式执行
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.exception.BusinessException;
import com.JH.dgather.frame.gathercontrol.global.GlobalThreadPool;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker4Obj;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
//import com.zone.ics.frame.gathercontrol.task.bean.TaskBean;
public abstract class ExeCtrl4Obj extends Observable{
	private Logger logger = Logger.getLogger(ExecutiveController.class);

	private int threadMaxRunSec = 300;// 任务执行的最长时间，单位秒要求5分钟内完成采集
	
	public ExeCtrl4Obj(int threadMaxRunSec) {
		this.threadMaxRunSec = threadMaxRunSec;
	}

	/**
	 * <code>execute</code> 数据采集和分析的执行方法
	 * 
	 * @return 执行结果对象
	 * @throws BusinessException 
	 */
	public abstract ExecutiveResult execute() throws BusinessException;

	// private static int THREADMAXRUNSec = 3000;//任务执行的最长时间，单位秒
	/**
	 * 初始化数据
	 * @throws BusinessException 
	 */
	protected abstract void doInit() throws BusinessException;

	/**
	 * 任务完成后执行的操作,由具体子类执行
	 */
	protected abstract void doFinish(int runFlag);

	private List<ICSFutureTask4Obj> futureList = new ArrayList<ICSFutureTask4Obj>();
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
	public void addTaskToPool(RootWorker4Obj worker) {
		ICSFutureTask4Obj icsFutureTask = new ICSFutureTask4Obj(worker, System.currentTimeMillis(), threadMaxRunSec);
		
		futureList.add(icsFutureTask);
	}

	/**
	 * muyp 启动被控制的所有任务
	 * 
	 * @param hours
	 *            整个任务执行最长时间，超出时间则跳出
	 * @throws InterruptedException
	 */
	protected List<ExecutiveResult> startTask(long hours) {
		int size = futureList.size();
		if (size == 0) {
			return null;
		}

		for (ICSFutureTask4Obj futureTask : futureList) {
			try {
				FutureTask<ExecutiveResult> futurTask = futureTask.getFutureTask();
				GlobalThreadPool.executeWorkingThreadInPool(futurTask);
			} catch (Exception e) {
				futureTask.setSuccess(false);
				continue;
			}
		}

		return getTaskResult();
	}

	//解决并发取worker结果，如有问题，可直接恢复下方用"/**/"注释的代码
	protected List<ExecutiveResult> getTaskResult() {
		long sT = System.currentTimeMillis();
		while (futureList.size() > 0) {
			List<ICSFutureTask4Obj> removeLs = new ArrayList<ICSFutureTask4Obj>();

			Iterator<ICSFutureTask4Obj> iterator = futureList.iterator();
			ExecutiveResult eResult = null;
			while (iterator.hasNext()) {
				ICSFutureTask4Obj fT = iterator.next();
				//worker结束
				if (fT.getFutureTask().isDone()) {
					eResult = GlobalThreadPool.getGatherResult(fT);
					if (eResult != null) {
						taskResultLs.add(eResult);
						//fT.getWorkerResult().setRunTask(TaskRunFlag.SUCCESS);
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


	public List<ICSFutureTask4Obj> getFutureList() {
		return futureList;
	}

}
