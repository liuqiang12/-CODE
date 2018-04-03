/*
 * @(#)TaskDispatch.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.itBusi.gathercontrol.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.global.GlobalThreadPool;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.task.TaskQueue;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.rule.TaskRule;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

/**
 * <code>TaskDispatch</cde> 任务调度 从任务队列中取任务、解析任务规则、执行任务、分析结果 
 * 与CollectTask类形成“生产者-消费者”模式，只不过我们不需要对任务队列写线程同步方法，因为我们不需要限制队列的上限。
 * 
 */
public class TaskDispatch implements Runnable {
	private Logger logger = Logger.getLogger(TaskDispatch.class);

	private TaskQueue taskQueue = null;

	private ExecutorService taskThreadPool = Executors.newFixedThreadPool(50);
	/*
	 * 线程每次获取任务的时间延迟, 目前是2秒一次
	 */
	private static final long GET_TASK_INTERVAL = 1000 * 2;
	private boolean stop = false;

	public TaskDispatch(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void stopDispatch() {
		stop = true;
	}

	// 如果一个相同的任务规则正被解析执行，则返回true
	public boolean alreadyExecuteTaskRule(TaskRule rule) throws NullPointerException {
		if (rule == null)
			throw new NullPointerException("任务规则为null!");

		return false;
	}

	@Override
	public void run() {
		while (!stop) {
			//while(GloableDataArea.runflag){
			try {
				if (GlobalThreadPool.tooManyWorkingThreads() == false) {
					UserTask task = taskQueue.pollTask();
					if (task != null) {
						logger.info("从任务队列中取得一个任务，开始调度..."+task);
						new Thread(new DispatchController(task), "DispatchController_" + task.getTaskId()).start();
						// dispatch(task);
					} else {
						;// logger.debug("任务队列中没有任务！");
					}
				} else {
					logger.info("超出最大线程池！");
				}
			} catch (Exception e) {
				logger.error("", e);
			} finally {
				try {
					Thread.sleep(GET_TASK_INTERVAL);
				} catch (InterruptedException e) {
					logger.error("任务等待加载异常：", e);
				}
			}
		}
	}

	private class TaskCallDispatch implements Callable<Boolean> {
		ExecutiveController taskController = null;

		public TaskCallDispatch(ExecutiveController taskController) {
			this.taskController = taskController;
		}

		private boolean dispatch(ExecutiveController taskController) {
			if (taskController == null) {
				logger.error("任务controller对象为null");
				return false;
			}
			boolean rtn = false;

			ExecutiveResult executiveResult = null;
			try {
				executiveResult = taskController.execute();
				if (executiveResult != null) {
					logger.debug("数据采集完成。");

					rtn = true;
				} else {
					rtn = false;
					// "数据采集结果为null");
				}
			} catch (Exception e) {
				rtn = false;
				logger.error("任务执行异常：", e);
			}
			return rtn;
		}

		@Override
		public Boolean call() throws Exception {
			return dispatch(taskController);
		}

	}

	private class DispatchController implements Runnable {
		//private static final long TIME_OUT_SECONDS = 60 * 60 * 2;// 任务执行超时为2小时
		private UserTask task;

		public DispatchController(UserTask task) {
			this.task = task;
		}

		@Override
		public void run() {
			boolean taskFlag = false;
			String taskMsg = "";
			// 创建任务公用类，记录任务对象所涉及的futureTask及控制器controller
			TaskUtil tUtil = new TaskUtil(task);
			try {
				tUtil.parseTask();
				FutureTask<Boolean> future = new FutureTask<Boolean>(new TaskCallDispatch(tUtil.getTaskController()));
				logger.debug("开始执行任务："+task.getTaskName());
				taskThreadPool.execute(future);
				try {
					while (true) {
						if(future.isDone()){
							logger.debug("开始获取执行结果："+task.getTaskName());
							taskFlag = future.get();
							taskMsg += "任务执行结束。";
							logger.debug(task.getTaskName()+"任务正常执行完毕！获取结果成功");
							break;
						}else if (future.isCancelled()) {
							taskFlag = false;
							future.cancel(true);
							logger.error(task.getTaskName()+"任务发生中断.");
						}else{
							try {
								logger.debug("任务："+task.getTaskName()+"未执行结束，等待");
								Thread.sleep(100);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
					
				} catch (InterruptedException e) {
					taskFlag = false;
					future.cancel(true);
					logger.error(task.getTaskName()+"任务发生中断异常：", e);
				} catch (ExecutionException e) {
					taskFlag = false;
					future.cancel(true);
					logger.error(task.getTaskName()+"任务发生执行异常：", e);
				} catch (Exception e) {
					taskFlag = false;
					future.cancel(true);
					logger.error(task.getTaskName()+"任务发生其它异常：", e);
				}
				
				
				/**
				 * 将采集对象结果数量设置进任务对象内，用于销毁任务前，对任务是否是部分成功状态进行判断
				 */
				// "将采集对象结果数量设置进任务对象内, size: " +
				// taskController.getTaskResultSize());
				logger.info("任务执行成功的对象个数是："+tUtil.getTaskController().getTaskResultSize());
				task.setHmObjSuccessSize(tUtil.getTaskController().getTaskResultSize());

				task.addTaskLog(taskMsg);
				if (taskFlag) {
					task.setRunFlag(TaskRunFlag.SUCCESS);
					logger.debug("任务执行成功!");
				} else if (task.getHmObjSuccessSize() > 0) {
					task.setRunFlag(TaskRunFlag.SUCCESS_PARTY);
					logger.debug("任务执行成功!");
				} else {
					logger.debug("任务执行失败!");
					task.setRunFlag(TaskRunFlag.FINAL);
				}
			} catch (Exception e) {
				taskMsg = "解析任务控制器异常！" + e.getMessage();
				task.addTaskLog(taskMsg);
				task.setRunFlag(TaskRunFlag.FINAL);
				logger.error("解析任务控制器异常！", e);
			} finally {
				tUtil.destroy();
				tUtil = null;
			}
		}
	}

}

