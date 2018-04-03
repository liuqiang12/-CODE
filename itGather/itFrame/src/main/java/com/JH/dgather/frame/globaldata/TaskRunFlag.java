package com.JH.dgather.frame.globaldata;

/**
 * 任务结果定义
 * @author muyp
 *
 */
public class TaskRunFlag {
	public static final int WAITINGFINAL = -1;  //等待失败	
	public static final int WAITING = 0;  //等待
	public static final int DOING = 1;	//执行
	public static final int SUCCESS = 2;//成功
	public static final int FINAL = 3;//失败
	public static final int SUCCESS_PARTY = 4;
	public static final int TIMEOUT = 5;//超时被中断
}
