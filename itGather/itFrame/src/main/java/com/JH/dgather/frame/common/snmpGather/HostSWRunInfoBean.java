package com.JH.dgather.frame.common.snmpGather;

/**
 * 主机进程信息
 * 
 * @author yangDS
 *
 */
public class HostSWRunInfoBean {
	
	private String hrSWRunIndex;//进程索引
	private String hrSWRunName;//进程名称
	private String hrSWRunPath;//产生进程的程序所在路径
	private String hrSWRunParameters;//进程运行参数
	private String hrSWRunType;//进程类型
	private String hrSWRunStatus;//进程状态
	
	public String getHrSWRunIndex() {
		return hrSWRunIndex;
	}
	
	public void setHrSWRunIndex(String hrSWRunIndex) {
		this.hrSWRunIndex = hrSWRunIndex;
	}
	
	public String getHrSWRunName() {
		return hrSWRunName;
	}
	
	public void setHrSWRunName(String hrSWRunName) {
		this.hrSWRunName = hrSWRunName;
	}
	
	public String getHrSWRunPath() {
		return hrSWRunPath;
	}
	
	public void setHrSWRunPath(String hrSWRunPath) {
		this.hrSWRunPath = hrSWRunPath;
	}
	
	public String getHrSWRunParameters() {
		return hrSWRunParameters;
	}
	
	public void setHrSWRunParameters(String hrSWRunParameters) {
		this.hrSWRunParameters = hrSWRunParameters;
	}
	
	public String getHrSWRunType() {
		return hrSWRunType;
	}
	
	public void setHrSWRunType(String hrSWRunType) {
		this.hrSWRunType = hrSWRunType;
	}
	
	public String getHrSWRunStatus() {
		return hrSWRunStatus;
	}
	
	public void setHrSWRunStatus(String hrSWRunStatus) {
		this.hrSWRunStatus = hrSWRunStatus;
	}
	
}
