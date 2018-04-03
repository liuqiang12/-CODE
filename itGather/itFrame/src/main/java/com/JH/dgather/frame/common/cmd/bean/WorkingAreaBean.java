package com.JH.dgather.frame.common.cmd.bean;

public class WorkingAreaBean {
	private String IP;
	private String jumpIP;
	private int gatherClass;
	private boolean used = false;
	
	public synchronized boolean isUsed() {
		return used;
	}
	
	public synchronized void setUsed(boolean used) {
		this.used = used;
	}
	
	public String getIP() {
		return IP;
	}
	
	public void setIP(String ip) {
		IP = ip;
	}
	
	public String getJumpIP() {
		return jumpIP;
	}
	
	public void setJumpIP(String jumpIP) {
		this.jumpIP = jumpIP;
	}
	
	public int getGatherClass() {
		return gatherClass;
	}
	
	public void setGatherClass(int gatherClass) {
		this.gatherClass = gatherClass;
	}
}
