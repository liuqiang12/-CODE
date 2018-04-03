package com.JH.itBusi.business.flux.db.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;


public class DeviceAndPort {
	private DeviceInfoBean device;//设备信息
	private ArrayList<String> lsPort;//端口名
	
	public DeviceInfoBean getDevice() {
		return device;
	}
	
	public void setDevice(DeviceInfoBean device) {
		this.device = device;
	}
	
	public ArrayList<String> getLsPort() {
		return lsPort;
	}
	
	public void setLsPort(ArrayList<String> lsPort) {
		this.lsPort = lsPort;
	}
}
