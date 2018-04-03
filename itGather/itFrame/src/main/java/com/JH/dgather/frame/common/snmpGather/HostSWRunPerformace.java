package com.JH.dgather.frame.common.snmpGather;

/**
 * 
 * 主机进程占用信息
 * 
 * @author yangDS
 *
 */
public class HostSWRunPerformace {
	
	private String hrSWRunIndex;//进程索引 对应进程信息bean中的索引
	private String hrSWRunPerfCPU;//进程cpu的占用
	private String hrSWRunPerfMem;//进程内存占用
	
	public String getHrSWRunIndex() {
		return hrSWRunIndex;
	}
	
	public void setHrSWRunIndex(String hrSWRunIndex) {
		this.hrSWRunIndex = hrSWRunIndex;
	}
	
	public String getHrSWRunPerfCPU() {
		return hrSWRunPerfCPU;
	}
	
	public void setHrSWRunPerfCPU(String hrSWRunPerfCPU) {
		this.hrSWRunPerfCPU = hrSWRunPerfCPU;
	}
	
	public String getHrSWRunPerfMem() {
		return hrSWRunPerfMem;
	}
	
	public void setHrSWRunPerfMem(String hrSWRunPerfMem) {
		this.hrSWRunPerfMem = hrSWRunPerfMem;
	}
	
}
