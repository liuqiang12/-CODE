package com.JH.dgather.frame.gathercontrol.task.BusinessBean;
/*
 * 对应Net_TraceRout_Result表
 */
public class Net_TraceRout_Result {
	private int resultId; //主键ID
	private int taskId; //任务ID
	private int traceId; //tracertID
	private String execTime; //执行时间
	private int execResult; //采集结果
	private String resultFile; //结果文件
	private int comparId; //对比ID
	private int comparResult; //对比结果
	private String failMsg; //失败原因
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTraceId() {
		return traceId;
	}
	public void setTraceId(int traceId) {
		this.traceId = traceId;
	}
	public String getExecTime() {
		return execTime;
	}
	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}
	public int getExecResult() {
		return execResult;
	}
	public void setExecResult(int execResult) {
		this.execResult = execResult;
	}
	public String getResultFile() {
		return resultFile;
	}
	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}
	public int getComparId() {
		return comparId;
	}
	public void setComparId(int comparId) {
		this.comparId = comparId;
	}
	public int getComparResult() {
		return comparResult;
	}
	public void setComparResult(int comparResult) {
		this.comparResult = comparResult;
	}
	public String getFailMsg() {
		return failMsg;
	}
	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
	
}
