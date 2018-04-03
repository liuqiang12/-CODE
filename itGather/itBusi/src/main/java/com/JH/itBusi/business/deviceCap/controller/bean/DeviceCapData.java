package com.JH.itBusi.business.deviceCap.controller.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.util.DateUtil;
import com.JH.itBusi.business.deviceCap.db.bean.FanCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.PwrCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.TemperatureCapBean;


public class DeviceCapData extends ExecutiveResult {
	/* add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2012-6-13
	 */
	private static final long serialVersionUID = -1778114628682393215L;
	private DeviceInfoBean device;
	private int deviceId;
	private ArrayList<PwrCapBean> pwrStatus;//电源状态，String=电源模块号，Integer=电源状态值
	private ArrayList<FanCapBean> fanStatus;//风扇状态
	private ArrayList<TemperatureCapBean> temperature;//温度
	private int cpu = -1;//记录CPU值、默认设置为-1表明未采集到
	private int mem = -1;//内存值、默认设置为-1表明未采集到
	private String execTime;

	public DeviceCapData(int successSize) {
		super(successSize);
	}

	public DeviceCapData() {
		super(1);
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public ArrayList<FanCapBean> getFanStatus() {
		return fanStatus;
	}

	public void setHsFanStatus(ArrayList<FanCapBean> fanStatus) {
		this.fanStatus = fanStatus;
	}

	public void setFanStatus(FanCapBean fanCap) {
		if (this.fanStatus == null)
			this.fanStatus = new ArrayList<FanCapBean>();
		this.fanStatus.add(fanCap);
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}


	public ArrayList<PwrCapBean> getPwrStatus() {
		return pwrStatus;
	}

	public void setHsPwrStatus(ArrayList<PwrCapBean> hsPwr) {
		this.pwrStatus = hsPwr;
	}

	public void setPwrStatus(PwrCapBean pwrCap) {
		if (pwrStatus == null)
			pwrStatus = new ArrayList<PwrCapBean>();
		this.pwrStatus.add(pwrCap);
	}

	public ArrayList<TemperatureCapBean> getTemperature() {
		return temperature;
	}

	public void setHsTemperature(ArrayList<TemperatureCapBean> temperature) {
		this.temperature = temperature;
	}

	public void setTemperature(TemperatureCapBean temperatureCap) {
		if (this.temperature == null)
			this.temperature = new ArrayList<TemperatureCapBean>();
		this.temperature.add(temperatureCap);
	}

	public DeviceInfoBean getDevice() {
		return device;
	}

	public void setDevice(DeviceInfoBean device) {
		this.device = device;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		if (execTime == null || "".equals(execTime)) {
			execTime = DateUtil.getDateTime();
		} else {
			this.execTime = execTime;
		}
	}

}