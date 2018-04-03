/*
 * @(#)TaskQueue.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.task.bean.PeriodTaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.gathercontrol.task.db.DataUtil;
import com.JH.dgather.frame.globaldata.TaskCntTypeFrame;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.DateFormate;

/**
 * <code>TaskQueue</code> 任务队列 放入任务队列中的任务以任务的优先级来排序，高优先级的任务优先执行
 * 维护一个全局列表，持有当前系统中所有的任务工作线程，以便能知道当前活动工作线程的总数 也能强制杀死工作线程(线程执行超时的处理逻辑已在采集控制器中)

 */
public class TaskQueue {
	// 存放任务的队列，可考虑采用其它类型实现；
	private LinkedList<UserTask> waitingTaskLs = new LinkedList<UserTask>();
	private Map<Integer,UserTask> waitingTaskMap = new Hashtable<Integer, UserTask>();
	private LinkedList<UserTask> runningTaskLs = new LinkedList<UserTask>();
	Logger logger = Logger.getLogger(TaskQueue.class.getName());
	DataUtil du = null;

	public TaskQueue() {
		du = new DataUtil();
	}

	/**
	 * add by xdwang 2012-2-28 当重启底层服务后，刷新任务，将正在执行的临时任务重置为失败状态，正在执行的周期任务重置为等待状态
	 */
	public void reflushTask() {
		LinkedList<TaskBean> tList = du.getDoingTask();
		// 增加此步的目的是为了让周期任务的下次执行时间能正确通过计算获得
		LinkedList<UserTask> uTaskLs = new LinkedList<UserTask>();
		if (tList != null) {
			/**
			 * 筛选出周期任务
			 */
			for (TaskBean bean : tList) {
				if (bean.getTaskType() == 1) {
					UserTask uTask = new UserTask();
					uTask.setUserTaskBean(bean);
					uTaskLs.add(uTask);
				} else {
					bean.setRunFlag(TaskRunFlag.FINAL);
				}
			}
			/**
			 * 查找周期任务时间信息
			 */
			du.getPeriodTaskInfo(uTaskLs);
			for (UserTask userTask : uTaskLs) {
				userTask.setTaskEndTime(new Date());
				if (userTask.getRunFlag() == TaskRunFlag.WAITING) {
					userTask.getUserTaskBean().setExecTime(calculateTaskExectime(userTask));
				} else {
					userTask.getUserTaskBean().setExecTime(new Date());
				}
			}

			du.reflush_TaskManager(tList);
			tList = null;
			uTaskLs = null;
		}
	}

	// 采集任务并加入taskList
	public void loadTask() {
		gatherTask();
	}

	/**
	 * 从数据库或者其它任务采集接口获取任务
	 * 
	 * @return 任务
	 */
	private void gatherTask() {
		//设置执行时间为null的任务运行时间
		du.updateExecTimeforNullUserTask();
		LinkedList<TaskBean> userTaskLs = du.getUserTask();
		if (userTaskLs == null || userTaskLs.size() == 0) {
			return;
		} else
			logger.debug("提取任务数量: " + userTaskLs.size());

		/**
		 * 修改bug：modified by chenxh 20160928
		 *   由于提取任务的时候waitingTaskLs没有上锁，而polltask中会不断从waitingTaskLs取出任务执行
		 * 因此，当还没有来得及修改数据库中waitingTaskLs中的任务状态的情况时，而waitingTaskLs已经被polltask把任务取走了，
		 * 从而导致此任务又会再一次被提取出来执行！
		 */
		List<UserTask> tempUserTaskList = new ArrayList<UserTask>(userTaskLs.size());
		for (TaskBean task : userTaskLs) {
			UserTask userTask = contructTask(task);
			if (userTask != null) {
				tempUserTaskList.add(userTask);
			} else {
				logger.debug("任务: " + task.getTaskName() + " 没有需要执行的对象！");
			}
		}
		if(tempUserTaskList.size() > 0){
			synchronized (waitingTaskLs) {
				for (UserTask userTask : tempUserTaskList) {
					waitingTaskLs.add(userTask);
					userTask.addTaskLog("任务被提取.存入于等待任务列表(TaskQueue.waitingTaskLs)内。");
					logger.debug("向waitingTaskLs列表中增加一个任务，任务名称为：" + userTask.getTaskName() + ", 任务类型: " + userTask.getTaskType());
				}
				/**
				 * modify by xdwang 2012-2-16 将判断条件userTaskLs.size() >
				 * 0改为waitingTaskLs.size() > 0
				 * 原因是：从net_taskmanager中提取出来的任务，如是周期任务，但是当前周期任务的公共采集对象为0
				 * （公共采集对象的执行时间都大于周期任务的执行时间）， 所以周期任务应该被标记为等待状态
				 */
				
				/**
				 * 任务从数据库内提取出来，并修改任务状态。
				 */
				du.updateNetTaskManagerRunFlag(tempUserTaskList, TaskRunFlag.DOING);
			}
		}
		}

	/*
	 * 根据taskbean构造完整的任务信息 需要根据任务产生规则生成任务
	 */
	private UserTask contructTask(TaskBean taskbean) {
		UserTask task = new UserTask();
		task.setUserTaskBean(taskbean);
		if(task.getTaskType()==1)//周期任务需要提取信息
			du.getPeriodTask(task);
//		HashMap<Integer, TaskObjBean> taskObjHs = null;
//		taskObjHs = du.getGatherObj(task);
//		if (taskObjHs == null || taskObjHs.size() == 0) {
//			return task;
//		}
//		logger.info("提取任务: " + task.getTaskName() + " 的采集对象长度为: " + taskObjHs.size());
//		task.setHmObj(taskObjHs);
		return task;
	}

	/*
	 * 判断某任务是否正在执行，需将周期和临时任务分开判断
	 */
	private HashMap<Integer, TaskObjBean> getNotExistTask(TaskBean taskbean, HashMap<Integer, TaskObjBean> taskObjHs) {
		synchronized (runningTaskLs) {
			if (runningTaskLs.size() > 0) {
				for (UserTask task : runningTaskLs) {
					if (task.getUserTaskBean().getGatherClass() != TaskCntTypeFrame.FluxGather && task.getUserTaskBean().getGatherClass() == taskbean.getGatherClass()) {// 相同类型任务存在
						// 判断是否相同对象
						for (Entry<Integer, TaskObjBean> entry : taskObjHs.entrySet()) {
							if (task.getHmObj().get(entry.getKey()) != null)// 表示相同的
								taskObjHs.remove(entry.getKey());
						}
					}
				}
			}
		}
		// 针对等待列表再做一次判断，是否有些代码多余，需整改
		/**
		 * modify by xdwang 2012-2-21
		 * 性能采集都是相同的gatherclass，根据net_kpiconfig来区分具体采集的类型 所以暂时屏蔽此段代码，再做考虑
		 */
		synchronized (waitingTaskLs) {
			if (waitingTaskLs.size() > 0) {
				for (UserTask task : waitingTaskLs) {
					if (task.getUserTaskBean().getGatherClass() != TaskCntTypeFrame.FluxGather && task.getUserTaskBean().getGatherClass() == taskbean.getGatherClass()) {// 相同类型任务存在
						// 判断是否相同对象
						for (Entry<Integer, TaskObjBean> entry : taskObjHs.entrySet()) {
							if (task.getHmObj().get(entry.getKey()) != null) {// 表示相同的
								taskObjHs.remove(entry.getKey());
							}
						}
					}
				}
			}
		}
		return taskObjHs;
	}

	/**
	 * 任务销毁,从taskList中销毁已经完成的任务，同时做数据持久动作
	 * 
	 * @throws IOException
	 */
	public void destroyTask() throws IOException {
		// 用于存放结果的任务列表
		ArrayList<UserTask> overTaskls = new ArrayList<UserTask>();
		// 将任务从runningTaskList删除，
		synchronized (runningTaskLs) {// ？考虑同步限制不能太长，否则执行效率低
			logger.info("正在运行的任务列表大小：" + runningTaskLs.size());
			Iterator<UserTask> iuserTaskList = runningTaskLs.iterator();
			while (iuserTaskList.hasNext()) {
				UserTask ut = iuserTaskList.next();
				if (ut.getRunFlag() >= TaskRunFlag.SUCCESS) {
					overTaskls.add(ut);
					ut.addTaskLog(ut.getTaskName() + " 任务从列表中销毁，任务结果:" + ut.getRunFlag());
					iuserTaskList.remove();
					logger.info(ut.getTaskName() + " 任务从列表中销毁，任务结果:" + ut.getRunFlag());
				}
			}
		}

		FileManager fileManager = new FileManager();
		// 将结束的任务结果入库
		if (overTaskls == null || overTaskls.size() == 0)
			return;
		/**
		 * 增加对任务是否是"部分成功"进行判断
		 */
		//当更新数据库失败时，需要把执行完成的任务重新投入运行任务队列，以便下次销毁任务时重新完成入库
		//added by chenxh 20140819
		ArrayList<UserTask> tmpUpdateFailedTaskls = new ArrayList<UserTask>();

 		for (Iterator iterator = overTaskls.iterator(); iterator
				.hasNext();) {
			UserTask task = (UserTask) iterator.next();
	/*muyp modify 20161209 没有对象概念
	 * 		if (task.getHmObjSuccessSize() == 0) {
				task.addTaskLog("任务下所有设备全部执行失败！");
				logger.info("任务下所有设备全部执行失败！任务被标记为失败！");
				task.setRunFlag(TaskRunFlag.FINAL);
			} else if((task.getHmObj()!=null)&&(task.getHmObj().size() > task.getHmObjSuccessSize())) {
				task.addTaskLog("被标记为'部分成功'状态！总采集对象模型数量: " + task.getHmObj().size() + ", 执行成功的数量: " + task.getHmObjSuccessSize());
				logger.info("任务: " + task.getTaskName() + " 状态被修改为'部分成功'!总采集对象模型数量: " + task.getHmObj().size() + ", 执行成功的数量: " + task.getHmObjSuccessSize());
				task.setRunFlag(TaskRunFlag.SUCCESS_PARTY);
			}*/
			// 更新任务的结束时间
			task.setTaskEndTime(new Date());
			task.setTaskExecTime(task.getExecTime());
			/**
			 * 更新周期任务自动调度时间
			 */
			if (task.getTaskType() == 1) {// 周期性任务才做下次执行时间运算
				task.getUserTaskBean().setExecTime(calculateTaskExectime(task));
				if((task.getPeriodTask().getDynamicCycle()==1)&&(task.getHmObj()!=null)&&(task.getHmObj().size()>0))
				calculateObjExectime(task.getHmObj(), task.getPeriodTask());// 计算对象执行时间
			}
			try{
		//		task.setResultFile(fileManager.getResultFilePath(task.getTaskName()));
			}catch(Exception e){
				logger.equals("记录执行结果到文件失败");
			}
			task.addTaskLog("任务结果入库。");
			
			try {//muyp 2015/7/20 add if 只有动态周期的任务才需要填写对象执行时间
				if((task.getTaskType()==1)&&(task.getPeriodTask().getDynamicCycle()==1)&&(task.getHmObj()!=null)&&(task.getHmObj().size()>0))
					updateObjExectimeToDb(task);// 更新周期性任务对象的下次执行时间
				updateTaskToDb(task);// 更新任务状态、下次执行时间到数据库
			} catch (SQLException e) {
				logger.error(task.getTaskName()+"销毁任务时，更新任务任务状态失败.",e);
				tmpUpdateFailedTaskls.add(task);
				iterator.remove();
			}
		}
		
		for (UserTask failedUpdateTask : tmpUpdateFailedTaskls) {
			logger.error(failedUpdateTask.getTaskName()+"由于更新任务任务状态失败，重新放入运行队列中，等待下一次更新状态.");
			synchronized (runningTaskLs) {
				runningTaskLs.add(failedUpdateTask);
			}
		}
		saveTaskResultToDb(overTaskls);
		//fileManager.saveXMLFile(overTaskls);// 将对象采集结果存储到文件
		
		overTaskls.removeAll(overTaskls);
		overTaskls = null;
		fileManager = null;
	}

//	/**
//	 * 任务销毁,从taskList中销毁已经完成的任务，同时做数据持久动作
//	 * 
//	 * @throws IOException
//	 */
//	public void destroyTask(UserTask task) throws IOException {
//		// logger.info("正在运行的任务列表大小："+runningTaskLs.size());
//		// 将任务从runningTaskList删除，
//		synchronized (runningTaskLs) {// ？考虑同步限制不能太长，否则执行效率低
//			if (task.getRunFlag() >= TaskRunFlag.SUCCESS) {
//				task.addTaskLog(task.getTaskName() + " 任务从列表中销毁，任务结果:" + task.getRunFlag());
//				runningTaskLs.remove(task);
//				logger.info(task.getTaskName() + " 任务从列表中销毁，任务结果:" + task.getRunFlag());
//			} else
//				return;
//		}
//
//		FileManager fileManager = new FileManager();
//		// 将结束的任务结果入库
//		/**
//		 * Add by xdwang 2012-2-2 增加对任务是否是"部分成功"进行判断
//		 */
//		if (task.getHmObjSuccessSize() == 0) {
//			task.addTaskLog("任务下所有设备全部执行失败！");
//			logger.info("任务下所有设备全部执行失败！任务被标记为失败！");
//			task.setRunFlag(TaskRunFlag.FINAL);
//		} else if (task.getHmObj().size() > task.getHmObjSuccessSize()) {
//			task.addTaskLog("被标记为'部分成功'状态！总采集对象模型数量: " + task.getHmObj().size() + ", 执行成功的数量: " + task.getHmObjSuccessSize());
//			logger.info("任务: " + task.getTaskName() + " 状态被修改为'部分成功'!总采集对象模型数量: " + task.getHmObj().size() + ", 执行成功的数量: " + task.getHmObjSuccessSize());
//			task.setRunFlag(TaskRunFlag.SUCCESS_PARTY);
//		}
//
//		// 更新任务的结束时间
//		task.setTaskEndTime(new Date());
//		task.setTaskExecTime(task.getExecTime());
//		/**
//		 * 更新周期任务自动调度时间
//		 */
//		if (task.getTaskType() == 1) {// 周期性任务才做下次执行时间运算
//			task.getUserTaskBean().setExecTime(calculateTaskExectime(task));
//			calculateObjExectime(task.getHmObj(), task.getPeriodTask());// 计算对象执行时间
//		}
//		task.setResultFile(fileManager.getResultFilePath(task.getTaskName()));
//
//		task.addTaskLog("任务结果入库。");
//
//		ArrayList<UserTask> overTaskLs = new ArrayList<UserTask>();
//		overTaskLs.add(task);
//
//		updateObjExectimeToDb(overTaskLs);// 更新周期性任务对象的下次执行时间
//		updateTaskToDb(overTaskLs);// 更新任务状态、下次执行时间到数据库
//		saveTaskResultToDb(overTaskLs);
//		fileManager.saveXMLFile(overTaskLs);// 将对象采集结果存储到文件
//		task = null;
//		overTaskLs = null;
//	}

	/*
	 * 存储任务采集结果到Net_GatherTaskResult表
	 */
	private void saveTaskResultToDb(ArrayList<UserTask> overTaskLs) {
	//	du.saveTaskResult(overTaskLs);
	}

	/*
	 * 更新Net_TaskManager表
	 */
	private void updateTaskToDb(UserTask task) throws SQLException {
		du.updateNet_TaskManager(task);
	}

	/*
	 * 更新对象的下次执行时间，更新表为Net_GatherPublicObject
	 */
	private void updateObjExectimeToDb(UserTask task) throws SQLException {
	//	du.updateObjToDb(task);

	}

	/*
	 * 针对周期性任务中的对象，计算对象下次执行时间
	 */
	private void calculateObjExectime(HashMap<Integer, TaskObjBean> hmObj, PeriodTaskBean periodTask) {
		TaskObjBean taskObj = null;
		// 0表示采集对象启用"时间自动调度"功能
		if (periodTask.getDynamicCycle() == 0) {
			for (Entry<Integer, TaskObjBean> entry : hmObj.entrySet()) {
				taskObj = entry.getValue();
				taskObj.setGatherIntervalTime(calculateIntervalTime(taskObj.getResult(), taskObj.getGatherIntervalTime(), periodTask.getIncreaseTime(), periodTask.getIncreaseMaxTime(), periodTask.getGatherIntervalTime()));
				taskObj.setExecTime(DateFormate.AddMinute(new Date(), taskObj.getGatherIntervalTime()));
			}
		}
		/**
		 * add by xdwang 2012-2-24
		 */
		else {
			for (Entry<Integer, TaskObjBean> entry : hmObj.entrySet()) {
				taskObj = entry.getValue();
				taskObj.setGatherIntervalTime(periodTask.getGatherIntervalTime());
				taskObj.setExecTime(DateFormate.AddMinute(new Date(), taskObj.getGatherIntervalTime()));
			}
		}
	}

	/**
	 * <code>calculateIntervalTime</code> 计算对象下次采集频率 计算流程说明：
	 * 判断当前周期任务是否启动"自动调度"标记 a. 未启动"自动调度" 1. 下一次周期任务的执行时间 = 当前周期任务的结束时间 +
	 * 间隔时间(分钟) b. 启动"自动调度" 1. 如果当前周期任务未发生告警信息 本次间隔时间 = ((上一次间隔时间 + 递增时间) >
	 * 最大间隔时间) ? 最大间隔时间 : (上一次间隔时间 + 递增时间) 2. 如果当前周期任务发生告警 本次间隔时间 = 周期任务的间隔时间
	 * 
	 * 3. 下一次周期任务的执行时间 = 当前周期任务的结束时间 + 本次间隔时间(分钟)
	 * 
	 * 注意：周期任务表net_gathertask_period中的信息是不变的 需要改变的是
	 * 周期任务公共对象采集表net_gatherpublicobject中的信息(本次间隔时间--->GatherIntervalTime)
	 */
	private int calculateIntervalTime(int result, int gatherIntervalTime, int increaseTime, int increaseMaxTime, int increaseMixTime) {
		int intervalTime = gatherIntervalTime;
		if (result == TaskRunFlag.SUCCESS) {// 如果本次执行成功则将采集周期变长
			intervalTime = gatherIntervalTime + increaseTime;
			if (intervalTime > increaseMaxTime)
				intervalTime = increaseMaxTime;
		} else {// 否将采集周期变短
			intervalTime = gatherIntervalTime - increaseTime;
			if (intervalTime < increaseMixTime)
				intervalTime = increaseMixTime;
		}

		return intervalTime;
	}

	/*
	 * 计算周期性任务的下次执行时间，对于按时采集的任务，下次执行时间=当前时间+间隔时间
	 */
	private Date calculateTaskExectime(UserTask task) {
		PeriodTaskBean periodTask = task.getPeriodTask();
		// 当前周期任务的结束时间
		//Date endTime = task.getTaskEndTime();
		Date execTime = task.getExecTime();//null;
		String tmpDate = "";
		// 任务结束时间
		String gatherEndTime = "";
		String gatherStartTime = "";

		switch (periodTask.getGatherPeriods()) {
		case 1: // 自定义 muyp modify 2015/7/20 解决跨天后执行时间执行不正常
			// 下一次执行时间 = 结束时间 + 间隔时间
			try{
				//用执行时间做下次采集的计算基础
			execTime = DateFormate.AddMinute(execTime, periodTask.getGatherIntervalTime());
			String s = DateFormate.dateTodateStr(new Date()) + " 24:00";
			logger.info("execTime="+DateFormate.dateToStr(execTime)+"||s="+s);
			Date maxTime =DateFormate.strToDate(s);//当天最大时间
			if(execTime.after(maxTime)){//跨天了
				tmpDate = DateFormate.dateTodateStr(execTime);
			}else
				tmpDate = DateFormate.dateTodateStr(new Date());
			if (periodTask.getGatherPeriodsEndTime() == null || periodTask.getGatherPeriodsEndTime().trim().equals("")) {
				gatherEndTime = tmpDate + " 24:00";// 如果没有定义结束时间则默认为24：00)
			} else {
				gatherEndTime = tmpDate + " " + periodTask.getGatherPeriodsEndTime();
			}
			// 任务起始时间
			if (periodTask.getGatherPeriodsTime() == null || periodTask.getGatherPeriodsTime().trim().equals("")) {
				gatherStartTime = tmpDate + " 00:00";// 如果没有定义起始时间则默认为00：00)
			} else {
				gatherStartTime = tmpDate + " " + periodTask.getGatherPeriodsTime();
			}
			logger.info("gatherEndTime="+gatherEndTime+"||gatherStartTime="+gatherStartTime);
			Date periodEndTime = DateFormate.strToDate(gatherEndTime, "yyyy-MM-dd HH:mm");
			Date periodStartTime = DateFormate.strToDate(gatherStartTime, "yyyy-MM-dd HH:mm");
			// 如果执行时间小于起始时间，那么执行时间为起始时间。
			if (execTime.before(periodStartTime)) {
				execTime = periodStartTime;
			}

			// 通过计算得到的下一次执行时间大于 周期任务的结束时间
			// 那么下一次执行时间为第二天的 周期任务的起始时间
			if (execTime.after(periodEndTime)) {
//				tmpDate = DateFormate.getDateAdd(new Date(), 1) + " " + periodTask.getGatherPeriodsTime();
//				execTime = DateFormate.strToDate(tmpDate);
				execTime  =DateFormate.addDate(periodStartTime, 1);
			}
			}catch(Exception e){logger.error("计算日期失败",e);}
			break;
		case 0:// 按天采集
			String p = periodTask.getGatherPeriodsTime();
			String t = DateFormate.getShortTime(new Date());
			String[] pa = p.split(":");
			String[] ta = t.split(":");
			boolean b = false;
			if(Integer.parseInt(ta[0])>Integer.parseInt(pa[0])){
				b = true;
			}else if(Integer.parseInt(ta[0])==Integer.parseInt(pa[0])){
				if(Integer.parseInt(ta[1])>=Integer.parseInt(pa[1])){
					b = true;
				}else{
					b = false;
				}
			}else{
				b = false;
			}
			if(b){
				tmpDate = DateFormate.getDateAdd(new Date(), 1) + " " + periodTask.getGatherPeriodsTime();
				execTime = DateFormate.strToDate(tmpDate);
			}else{
				tmpDate = DateFormate.getDateAdd(new Date(), 0) + " " + periodTask.getGatherPeriodsTime();
				execTime = DateFormate.strToDate(tmpDate);
			}
			break;
		case 2:// 按周采集
			tmpDate = DateFormate.getDateAdd(new Date(), 7) + " " + periodTask.getGatherPeriodsTime();
			execTime = DateFormate.strToDate(tmpDate);
			break;
		case 3:// 按月采集
			tmpDate = DateFormate.dateToStr(DateFormate.AddMonth(new Date(), 1), "yyyy-MM");
			execTime = DateFormate.strToDate(tmpDate + "-" + periodTask.getGatherPeriodsDay() + " " + periodTask.getGatherPeriodsTime());
			break;
		case 4:// 按时采集,需考虑采集结束时间段,如果计算出的时间正好在限制时间段内，则下次执行时间则从开始时间决定
			execTime = DateFormate.AddMinute(new Date(), periodTask.getGatherIntervalTime());
			tmpDate = DateFormate.dateToStr(execTime, "HH:mm");
			if (gatherEndTime.compareTo(periodTask.getGatherPeriodsTime()) > 0)// 结束时间大于启动时间
			{
				if (tmpDate.compareTo(gatherEndTime) < 0 && tmpDate.compareTo(periodTask.getGatherPeriodsTime()) > 0) {
					tmpDate = DateFormate.dateToStr(new Date(), "yyyy-MM-dd") + " " + periodTask.getGatherPeriodsTime();
					execTime = DateFormate.strToDate(tmpDate);
				}
			} else// 结束时间小于启动时间
			{
				if (tmpDate.compareTo(gatherEndTime) > 0 || tmpDate.compareTo(periodTask.getGatherPeriodsTime()) < 0) {
					tmpDate = DateFormate.dateToStr(DateFormate.addDate(new Date(), 1), "yyyy-MM-dd") + " " + periodTask.getGatherPeriodsTime();
					execTime = DateFormate.strToDate(tmpDate);
				}

			}
			break;
		default:
			break;
		}
		return execTime;
	}

	/**
	 * 从taskList中提取合适的任务来分发
	 * 
	 * @param taskNum
	 *            任务分发
	 */
	public UserTask pollTask() {
		UserTask ut = null;
		synchronized (waitingTaskLs) {
			if (waitingTaskLs.size() > 0) {
				ut = waitingTaskLs.getFirst();
				waitingTaskLs.removeFirst();
				waitingTaskMap.remove(ut.getTaskId());
				synchronized (runningTaskLs){
				  runningTaskLs.add(ut);
				}
				ut.addTaskLog("任务从等待列表中提取，存放过执行任务列表(TaskQueue.runningTaskLs)中。");
			}
		}
		return ut;
	}

	/**
	 * 提取某种状态的任务
	 * 
	 * @param runFlag
	 * @return 任务信息
	 */
//	public ArrayList<UserTask> getTaskByRunFlag(int runFlag) {
//		ArrayList<UserTask> tmpUserTaskLs = null;
//		synchronized (waitingTaskLs) {
//			for (UserTask ut : waitingTaskLs) {
//				if ((ut.getUserTaskBean().getRunFlag()) == runFlag) {
//					tmpUserTaskLs.add(ut);
//				}
//			}
//		}
//		return tmpUserTaskLs;
//	}

	public int getWaitingTaskSize() {
		synchronized (waitingTaskLs) {
			return this.waitingTaskLs.size();
		}
	}

	public int getRunningTaskSize() {
		synchronized (runningTaskLs) {
			return this.runningTaskLs.size();
		}
	}

}
