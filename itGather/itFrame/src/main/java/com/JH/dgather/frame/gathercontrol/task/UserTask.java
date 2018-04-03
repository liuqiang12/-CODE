package com.JH.dgather.frame.gathercontrol.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.JH.dgather.frame.gathercontrol.task.bean.PeriodTaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.gathercontrol.task.rule.TaskRule;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

public class UserTask {
	/*任务优先级，进入等待执行队列前临时计算的，分为1、2、3级
	 * 
	 */
	private int priority = 1;
	/*对应数据库中“任务管理表”
	 * 
	 */
	private TaskBean userTaskBean;
	/*任务执行对象
	 * 
	 */
	private HashMap<Integer, TaskObjBean> hmObj = null;
	/*
	 * 当前任务所属任务规则，则规则解析器指定
	 */
	private TaskRule taskRule = null;
	/*
	 *任务的周期规则 
	 */
	private PeriodTaskBean periodTask = null;
	/**
	 * 采集结果保存文件
	 */
	private String resultFile = "";
	/**
	 * 任务监听器列表
	 */
	protected ArrayList<UserTaskListener> listeners = new ArrayList<UserTaskListener>();
	
	/**
	 * 执行对象成功的个数
	 */
	private int hmObjSuccessSize = 0;
	
	/**
	 * 任务的结束时间
	 */
	private Date taskEndTime;
	/**
	 * 任务的执行时间
	 * 用于入库使用
	 */
	private Date taskExecTime;
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public TaskBean getUserTaskBean() {
		return this.userTaskBean;
	}
	
	public void setUserTaskBean(TaskBean userTaskBean) {
		this.userTaskBean = userTaskBean;
	}
	
	public HashMap<Integer, TaskObjBean> getHmObj() {
		return hmObj;
	}
	
	public void setHmObj(HashMap<Integer, TaskObjBean> hmObj) {
		this.hmObj = hmObj;
	}
	
	/*
	 * 增加对象到任务中
	 */
	public void addObj(TaskObjBean taskObj) {
		if (this.hmObj == null)
			this.hmObj = new HashMap<Integer, TaskObjBean>();
		this.hmObj.put(taskObj.getGoid(), taskObj);
	}
	
	public PeriodTaskBean getPeriodTask() {
		return periodTask;
	}
	
	public void setPeriodTask(PeriodTaskBean periodTask) {
		this.periodTask = periodTask;
	}
	
	/*
	 * 提取任务类别，周期/临时
	 */
	public int getTaskType() {
		return this.userTaskBean.getTaskType();
	}
	
	public int getGatherClass() {
		return this.userTaskBean.getGatherClass();
	}
	
	public int getTaskId() {
		return this.userTaskBean.getTaskId();
	}
	
	public String getUserId(){
		return this.userTaskBean.getUserId();
	}
	public int getRunFlag() {
		return this.userTaskBean.getRunFlag();
	}
	
	public void setExecTime(Date d) {
		this.userTaskBean.setExecTime(d);
	}
	
	public Date getExecTime() {
		return this.userTaskBean.getExecTime();
	}
	
	public TaskRule getTaskRule() {
		return taskRule;
	}
	
	public void setTaskRule(TaskRule taskRule) {
		this.taskRule = taskRule;
	}
	
	public String getTaskName() {
		return this.userTaskBean.getTaskName();
	}
	
	public String getResultFile() {
		return resultFile;
	}
	
	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}
	
	public void setRunFlag(int runFlag) {
		this.userTaskBean.setRunFlag(runFlag);
		//如果任务状态为：成功/失败/部分成功，则更新任务结果对象
		if (runFlag > TaskRunFlag.DOING) {
			this.userTaskBean.getTaskRunResult().setGorSign(runFlag);
		}
	}
	
	/**
	 * 增加一条任务日志
	 * @param log 日志信息
	 */
	public void addTaskLog(String log) {
		this.userTaskBean.getTaskRunResult().addInfo(log);
	}
	
	/**
	 * @return the listeners
	 */
	public ArrayList<UserTaskListener> getListeners() {
		return listeners;
	}
	
	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(ArrayList<UserTaskListener> listeners) {
		this.listeners = listeners;
	}
	
	/**
	 * @return the hmObjSuccessSize
	 */
	public int getHmObjSuccessSize() {
		return hmObjSuccessSize;
	}
	
	/**
	 * @param hmObjSuccessSize the hmObjSuccessSize to set
	 */
	public void setHmObjSuccessSize(int hmObjSuccessSize) {
		this.hmObjSuccessSize = hmObjSuccessSize;
	}
	
	/**
	 * @return the taskEndTime
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	
	/**
	 * @param taskEndTime the taskEndTime to set
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	
	/**
	 * @return the taskExecTime
	 */
	public Date getTaskExecTime() {
		return taskExecTime;
	}
	
	/**
	 * @param taskExecTime the taskExecTime to set
	 */
	public void setTaskExecTime(Date taskExecTime) {
		this.taskExecTime = taskExecTime;
	}
	
}
