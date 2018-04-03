package com.JH.dgather.frame.common.snmpGather;

/**
 * 
 * 内存实体bean
 * @author yangDS
 *
 */
public class NetMemoryEntityBean {
	private String routId;
	private String memoryPoolName;
	private String memoryPoolAlternate;
	private String memoryPoolValid;
	private String memoryPoolUsed;
	private String memoryPoolFree;
	private String memoryPoolLargestFree;
	
	public String getRoutId() {
		return routId;
	}
	
	public void setRoutId(String routId) {
		this.routId = routId;
	}
	
	public String getMemoryPoolName() {
		return memoryPoolName;
	}
	
	public void setMemoryPoolName(String memoryPoolName) {
		this.memoryPoolName = memoryPoolName;
	}
	
	public String getMemoryPoolAlternate() {
		return memoryPoolAlternate;
	}
	
	public void setMemoryPoolAlternate(String memoryPoolAlternate) {
		this.memoryPoolAlternate = memoryPoolAlternate;
	}
	
	public String getMemoryPoolValid() {
		return memoryPoolValid;
	}
	
	public void setMemoryPoolValid(String memoryPoolValid) {
		this.memoryPoolValid = memoryPoolValid;
	}
	
	public String getMemoryPoolUsed() {
		return memoryPoolUsed;
	}
	
	public void setMemoryPoolUsed(String memoryPoolUsed) {
		this.memoryPoolUsed = memoryPoolUsed;
	}
	
	public String getMemoryPoolFree() {
		return memoryPoolFree;
	}
	
	public void setMemoryPoolFree(String memoryPoolFree) {
		this.memoryPoolFree = memoryPoolFree;
	}
	
	public String getMemoryPoolLargestFree() {
		return memoryPoolLargestFree;
	}
	
	public void setMemoryPoolLargestFree(String memoryPoolLargestFree) {
		this.memoryPoolLargestFree = memoryPoolLargestFree;
	}
	
}
