/*
 * @(#)CollectTask.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task;

import org.apache.log4j.Logger;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.globaldata.GloableDataArea;

/**
 * <code>CollectTask</code> 收集待执行的任务
 * 定期从数据库中取任务，判断其是否需要立即执行
 *  与TaskDispatch类形成“生产者-消费者”模式
 * @author Wang Xuedan
 * @version 1.0, 12/23/2011
 */
public class TaskQueueManager implements Runnable {
	private Logger logger = Logger.getLogger(TaskQueueManager.class);

	private long interval = 10;
	private TaskQueue taskQueue = null;
	private boolean runflag =true;
	public TaskQueueManager(TaskQueue taskQueue) {
		super();
		try {
			interval = Long.parseLong(PropertiesHandle.getResuouceInfo("interval"));
		} catch (Exception e) {
			logger.error("读取时间间隔错误,请检查配置文件properties是否有interval的配置,系统默认以10秒间隔执行");
			interval = 10;//测试用
		}
		interval = interval * 1000;
		this.taskQueue = taskQueue;
	}

	/**
	 * 从数据表中取到某一待执行任务后，在此任务记录上打上一个正在执行此任务的标志，防止其它共享此数据库的程序也来争着执行此任务。
	 * 当这个系统需要分布式部署时才有必要使用这个方法
	 * 仅凭一个任务被执行的标志还不够，应该有一个时间戳，以任务执行超时后，能重新再被调度执行
	 * 当任务被成功地执行后，入库结果时，应同时改写此标志为成功执行。
	 * @param task 任务对象
	 */
	private void writeExecutingFlag(UserTask task) throws NullPointerException {
		if (task == null)
			throw new NullPointerException("任务对象为null!");
	}

	@Override
	public void run() {
		taskQueue.reflushTask();
		while (runflag) {
		//while(GloableDataArea.runflag){
			logger.info("刷新任务!");
			taskQueue.loadTask();
			try {
				taskQueue.destroyTask();
			} catch (Exception e) {
				logger.error("销毁任务时失败：", e);
			}
			try {
				logger.debug("开始休眠" + this.interval / 1000 + "秒");
				Thread.sleep(this.interval);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	
	public void stopTaskmanager(){
		runflag = false;
	}
}
