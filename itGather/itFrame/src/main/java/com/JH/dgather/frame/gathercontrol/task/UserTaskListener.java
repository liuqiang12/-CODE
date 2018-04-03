package com.JH.dgather.frame.gathercontrol.task;

import com.JH.dgather.frame.gathercontrol.exception.UserTaskException;


/**
 * 用户任务监听器接口
 * @author Administrator
 *
 */
public interface UserTaskListener {

	/**
	 * usertask开始执行
	 */
	void doTaskBegin();
	
	/**
	 * usertask执行结束
	 * 
	 * @param status 状态标志
	 */
	void doTaskOver(int status);
	
	/**
	 * userTask返回给客户端执行消息
	 * 
	 * @param message 消息
	 */
	void printTaskMessage(String message);
	/**
	 * userTask执行时出现异常
	 * 
	 * @param e
	 */
	void gotAnException(UserTaskException e);
	/**
	 * userTask返回数据
	 * 
	 * @param data
	 */
	void gotDataFromTask(Object data);


}
