package com.JH.itBusi.gathercontrol.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.controller.ICSFutureTask;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.itBusi.gathercontrol.controller.TaskControllerFactory;
import com.JH.itBusi.gathercontrol.task.rule.TaskParseFactory;



public class TaskUtil {
	private Logger logger = Logger.getLogger(TaskUtil.class);
	private UserTask task = null;
	private ExecutiveController taskController = null;
	private TaskControllerFactory tcf;

	public TaskUtil(UserTask task) {
		this.task = task;
	}

	public void parseTask() throws Exception {
		if (task == null) {
			throw new Exception("任务对象为NULL!");
		}

		if (task.getTaskRule() == null) {
			try {
				TaskParseFactory.parse(task);
			} catch (Exception e) {
				logger.error(task.getUserTaskBean().getTaskName() + "根据任务的类型，解析任务规则时异常：", e);
				throw new Exception(task.getUserTaskBean().getTaskName() + "根据任务的类型，解析任务规则时异常：");
			}
		}
		tcf = new TaskControllerFactory();
		taskController = tcf.getGatherController(task);
	}

	public ExecutiveController getTaskController() {
		return taskController;
	}

	//任务摧毁，将任务控制器中所有关联的worker的FutureTask进程关闭
	public void destroy() {
		if (this.taskController != null) {
			List<ICSFutureTask> futureList = this.taskController.getFutureList();
			if (futureList.size() > 0) {
				for (ICSFutureTask fTask : futureList) {
					try {
						fTask.close();
					} catch (Exception e) {
						logger.error("关闭work异常：", e);
					}
					if (fTask.getFutureTask() != null) {
						fTask.getFutureTask().cancel(true);
					}
				}
			}
			
			this.taskController.close();
			this.taskController = null;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("aaaa[12][]".indexOf("[1]"));
	}
}
