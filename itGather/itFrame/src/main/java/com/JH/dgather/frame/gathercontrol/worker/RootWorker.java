package com.JH.dgather.frame.gathercontrol.worker;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.cmd.CMDManager;
import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

public abstract class RootWorker implements IWorker {
	private Logger logger = Logger.getLogger(RootWorker.class);
	private TaskObjBean taskObj;
	private ExecutiveController controller;
	private boolean isCatchException = false; // 是否抛出异常

	//public SnmpUtil su = null;
	public CMDService tl = null;
	public CMDManager cmdManager =null;
	// add by xdwang 2012-11-22
	// 确保在 worker超时时，能正常告诉controller
	private ExecutiveResult workerResult = null;

	public RootWorker(ExecutiveController controller, TaskObjBean taskObj, ExecutiveResult workerResult) {
		super();
		this.controller = controller;
		this.taskObj = taskObj;
		this.workerResult = workerResult;
		this.workerResult.setRunTask(TaskRunFlag.DOING);

		this.controller.addTaskToPool(this);
	}

	public RootWorker(ExecutiveController controller, ExecutiveResult workerResult) {
		super();
		this.controller = controller;
		this.taskObj = null;
		this.workerResult = workerResult;
		this.workerResult.setRunTask(TaskRunFlag.DOING);

		this.controller.addTaskToPool(this);
	}

	public TaskObjBean getTaskObjBean() {
		return this.taskObj;
	}

	@Override
	public ExecutiveResult call() {
		/**
		 * 默认此次线程为失败状态 当所有业务正常执行后 将runFlag设置为success
		 */
		int runFlag = TaskRunFlag.FINAL;
		String errMsg = "";
		try {
			// 开始执行任务
			try {
				ExecutiveResult executeResult = execute();
				// if (!isCatchException) {
				// if (executeResult == null) {
				// runFlag = TaskRunFlag.FINAL;
				// } else {
				// runFlag = TaskRunFlag.SUCCESS;
				// }
				// } else
				// runFlag = TaskRunFlag.FINAL;
				runFlag = executeResult.getRunTask();
				return executeResult;
			} catch (Exception e) {
				runFlag = TaskRunFlag.FINAL;
				errMsg = e.getMessage();
				logger.error("call 开始执行", e);
			}
		} finally {
			if (runFlag == TaskRunFlag.FINAL) {
				String errorMessage = "对象执行失败 " + errMsg;
				if (taskObj != null)
					taskObj.setErrorMsg(errorMessage);
			}

			if (taskObj != null)
				taskObj.setResult(runFlag);
//			else {
//				logger.error("---------------------对象为null！--------------------");
//			}
		}
		return null;
	}

	/**
	 * 具体执行方法由子类实现
	 */
	public abstract ExecutiveResult execute();

	/**
	 * 需要约定用户，当任务执行失败，直接使用此方法，告诉底层任务失败的原因
	 * 
	 * @param errMsg
	 */
	public void exceptionFinish(String errMsg) {
		isCatchException = true;
		taskObj.setResult(TaskRunFlag.FINAL);
		logger.error("异常失败，原因: " + errMsg);
		taskObj.setErrorMsg(errMsg);
	}

	public void setWorkerMessage(String msg) {
		taskObj.setErrorMsg(msg);
	}

	/**
	 * @return the taskObj
	 */
	public TaskObjBean getTaskObj() {
		return taskObj;
	}

	/**
	 * @return the controller
	 */
	public ExecutiveController getController() {
		return controller;
	}

	public void setRunFlag(int flag) {
		this.workerResult.setRunTask(flag);
	}

	public ExecutiveResult getWorkerResult() {
		return workerResult;
	}

	public void close(String deviceName) {
		
		if (tl != null) {
			tl.disconnect(deviceName);
			tl = null;
			//logger.info("----------tl不为null，执行disconnect方法-----------");
		}
		if (cmdManager != null) {
			cmdManager.destroy(deviceName);
			cmdManager = null;
			//logger.info("----------tl不为null，执行disconnect方法-----------");
		}
	}

}
