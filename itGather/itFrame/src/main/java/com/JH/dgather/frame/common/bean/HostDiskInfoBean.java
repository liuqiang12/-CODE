package com.JH.dgather.frame.common.bean;

/**
 * @author yangDS
 *
 */
public class HostDiskInfoBean {
	
	private int deviceId;
	private int taskId;
	private String raidStyle;//磁盘阵列？
	private int totalSize;//硬盘总大小
	private String diskDescr;//硬盘描述
	private String partionDescr;//分区描述
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public String getRaidStyle() {
		return raidStyle;
	}
	
	public void setRaidStyle(String raidStyle) {
		this.raidStyle = raidStyle;
	}
	
	public int getTotalSize() {
		return totalSize;
	}
	
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	public String getDiskDescr() {
		return diskDescr;
	}
	
	public void setDiskDescr(String diskDescr) {
		this.diskDescr = diskDescr;
	}
	
	public String getPartionDescr() {
		return partionDescr;
	}
	
	public void setPartionDescr(String partionDescr) {
		this.partionDescr = partionDescr;
	}
	
}
