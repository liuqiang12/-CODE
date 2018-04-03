package com.JH.dgather.frame.common.cmd.bean;

import java.util.HashMap;

public class TelnetWorkingArea {
	private TelnetWorkingArea area = new TelnetWorkingArea();
	private HashMap<String, WorkingAreaBean> telnetHash = new HashMap<String, WorkingAreaBean>();
	
	private TelnetWorkingArea() {
		
	}
	
	public TelnetWorkingArea getInstance() {
		return area;
	}
	
	//key是jumpip
	
	public synchronized Boolean hasJumpIp(String jumpip) {
		return telnetHash.containsKey(jumpip);
	}
	
	public synchronized WorkingAreaBean removeJumpIp(String jumpip) {
		return telnetHash.remove(jumpip);
	}
	
	public synchronized WorkingAreaBean addJumpIp(String jumpip, WorkingAreaBean wab) {
		return telnetHash.put(jumpip, wab);
	}
	
	public synchronized WorkingAreaBean getLock(String jumpip) {
		return telnetHash.get(jumpip);
	}
}
