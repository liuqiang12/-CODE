package com.JH.dgather.frame.gathercontrol.worker;

import org.apache.log4j.Logger;




import com.JH.dgather.frame.gathercontrol.controller.ExeCtrl4Obj;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.globaldata.TaskRunFlag;

/*author muyp
 * 针对不以用户任务模式执行，而是以设备对象方式执行
 */
public abstract class RootWorker4Obj implements IWorker {
	private Logger logger = Logger.getLogger(RootWorker4Obj.class);
	private ExeCtrl4Obj controller;
	private boolean isCatchException = false; // 是否抛出异常

	//public SnmpUtil su = null;
	// add by xdwang 2012-11-22
	// 确保在 worker超时时，能正常告诉controller
	private ExecutiveResult workerResult = null;


	public RootWorker4Obj(ExeCtrl4Obj controller, ExecutiveResult workerResult) {
		super();
		this.controller = controller;
		this.workerResult = workerResult;
		this.workerResult.setRunTask(TaskRunFlag.DOING);

		this.controller.addTaskToPool(this);
	}


	@Override
	public ExecutiveResult call() {
		/**
		 * 默认此次线程为失败状态 当所有业务正常执行后 将runFlag设置为success
		 */
		int runFlag = TaskRunFlag.FINAL;
		String errMsg = "";
		ExecutiveResult executeResult = null;
		try {
			// 开始执行任务
			try {
				executeResult = execute();
				runFlag = executeResult.getRunTask();
				return executeResult;
			} catch (Exception e) {
				runFlag = TaskRunFlag.FINAL;
				errMsg = e.getMessage();
				logger.error("work执行故障", e);
			}
		} finally {
//			if (runFlag == TaskRunFlag.FINAL) {
//				String errorMessage = "对象执行失败 " + errMsg;
//			}

		return executeResult;
	}
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
		logger.error("异常失败，原因: " + errMsg);
	}

//	public void setWorkerMessage(String msg) {
//		taskObj.setErrorMsg(msg);
//	}
//
	/**
	 * @return the taskObj
	 */
//	public TaskObjBean getTaskObj() {
//		return taskObj;
//	}

	/**
	 * @return the controller
	 */
	public ExeCtrl4Obj getController() {
		return controller;
	}

	public void setRunFlag(int flag) {
		this.workerResult.setRunTask(flag);
	}

	public ExecutiveResult getWorkerResult() {
		return workerResult;
	}

	public void close(String deviceName) {
		
	}

}

