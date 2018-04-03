/*
 * @(#)GlobalThreadPool.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.global;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.gathercontrol.controller.RootFutureTask;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

public class GlobalThreadPool {
	private static final Logger logger = Logger.getLogger(GlobalThreadPool.class);
	public static int MAX_WORKING_THREAD_COUNT = 200;
	static {
		try {
			MAX_WORKING_THREAD_COUNT = Integer.valueOf(PropertiesHandle.getResuouceInfo("thread.max.working"));
		} catch (Exception e) {
			MAX_WORKING_THREAD_COUNT = 200;
			logger.error("ziyuan.properties中未找到ics.thread.max.working属性，默认设置线程最大数为200");
		}

	}
	public static ExecutorService serverThreadPool = Executors.newFixedThreadPool(MAX_WORKING_THREAD_COUNT);

	private static void shutdown() {
		serverThreadPool.shutdown();
	}

	public boolean isShutdown() {
		return serverThreadPool.isShutdown();
	}

	public static int getActiveCount() {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) serverThreadPool;
		return pool.getActiveCount();
	}

	public static void loggerPoolSize(String taskName) {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) serverThreadPool;
		logger.info("-----------------------" + taskName + "----------------------\n" + taskName + ";当前活跃线程数：" + pool.getActiveCount() + ";当前线程数：" + pool.getPoolSize() + ";当前线程池最大支持线程数：" + pool.getLargestPoolSize() + "\n-----------------------" + taskName + "----------------------");
	}

	public static boolean tooManyWorkingThreads() {
		return getActiveCount() >= MAX_WORKING_THREAD_COUNT;
	}

	/**
	 * add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-11-6
	 * @param worker
	 *            worker
	 * @param workerTimeout
	 *            超时时长
	 * @return
	 */
	public static void executeWorkingThreadInPool(RootFutureTask futureTask) {
		serverThreadPool.execute(futureTask.getFutureTask());

	}
	public static void executeWorkingThreadInPool(
			FutureTask<ExecutiveResult> futureTask) {
		serverThreadPool.execute(futureTask);
	}

	//modify by xdwang 2012-12-5
	//解决并发取worker结果，如有问题，可直接恢复下方用"/**/"注释的代码
	public static ExecutiveResult getGatherResult(RootFutureTask futureTask) {
		ExecutiveResult result = null;
		try {
			result = futureTask.getFutureTask().get();
			//futureTask.getWorkerResult().setRunTask(TaskRunFlag.SUCCESS);
			futureTask.getWorkerResult().setRunTask(result.getRunTask());//muyp modify 2016-3-14
		} catch (InterruptedException e) {
			logger.error("worker InterruptedException:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} catch (ExecutionException e) {
			logger.error("worker ExecutionException:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} catch (Exception e) {
			logger.error("worker Exception:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} finally {
			futureTask.close();
		}
		return result;
	}
	/*
	public static ExecutiveResult getGatherResult(ICSFutureTask futureTask) {
		ExecutiveResult result = null;
		try {
			long currTime = System.currentTimeMillis();
			long timeout = 1;
			//表示当前worker任务执行的时间已经超过了限制的超时时间，所以不管WORKER是否执行完，都应该进行超时处理
			if ((currTime - futureTask.getStartTime()) / 1000 > futureTask.getTimeout()) {
				timeout = 1;
			}
			//表示当前worker执行时间未超过限制的超时时间，所以应该再等待部分时间。
			else {
				timeout = futureTask.getTimeout() - (currTime - futureTask.getStartTime()) / 1000;
			}

			//logger.info("worker timeout:" + timeout + " 秒;thread[" + Thread.currentThread().getName() + "]开始");
			result = futureTask.getFutureTask().get(timeout, TimeUnit.SECONDS);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.SUCCESS);
			//logger.info("worker timeout:" + timeout + " 秒;thread[" + Thread.currentThread().getName() + "]结束");
		} catch (InterruptedException e) {
			logger.error("worker InterruptedException:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} catch (ExecutionException e) {
			logger.error("worker ExecutionException:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} catch (TimeoutException e) {
			logger.error("worker TimeoutException:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.TIMEOUT);
			futureTask.getWorkerResult().setSuccessSize(0);
		} catch (Exception e) {
			logger.error("worker Exception:", e);
			futureTask.getFutureTask().cancel(true);
			futureTask.getWorkerResult().setRunTask(TaskRunFlag.FINAL);
			futureTask.getWorkerResult().setSuccessSize(0);
		} finally {
			futureTask.close();
		}
		return result;
	}
	*/


}
