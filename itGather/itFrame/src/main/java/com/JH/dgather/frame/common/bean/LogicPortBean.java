package com.JH.dgather.frame.common.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.reflect.ReflectUtil;

/**
 * 逻辑端口bean
 * @author yangDS
 *
 */
public class LogicPortBean {
	private int deviceId;
	private long portIndex;
	private String logicPortName;
	private long logicPortIndex;
	private String ipAdddress;
	private String alias;
	private float bandwidth;
	private String mac;
	private int portActive;
	
	public String getAlias() {
		return this.alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public int getDeviceId() {
		
		return deviceId;
	}
	
	public void setDeviceId(int deviceId) {
		
		this.deviceId = deviceId;
	}
	
	public long getPortIndex() {
		
		return portIndex;
	}
	
	public void setPortIndex(long portIndex) {
		
		this.portIndex = portIndex;
	}
	
	public String getLogicPortName() {
		if (logicPortName == null)
			return new String("");
		else
			return logicPortName;
	}
	
	public void setLogicPortName(String logicPortName) {
		if (logicPortName != null && logicPortName.length() > 20) {
			this.logicPortName = logicPortName.substring(0, 20);
		}
		else {
			this.logicPortName = logicPortName;
		}
		
	}
	
	public long getLogicPortIndex() {
		
		return logicPortIndex;
	}
	
	public void setLogicPortIndex(long logicPortIndex) {
		
		this.logicPortIndex = logicPortIndex;
	}
	
	public String getIpAdddress() {
		if (ipAdddress == null)
			return new String("");
		else
			return ipAdddress;
	}
	
	public void setIpAdddress(String ipAdddress) {
		
		this.ipAdddress = ipAdddress;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof LogicPortBean) {
			
			ArrayList<String> fieldsName = new ArrayList<String>() {
				{
					add("deviceId");
					add("logicPortName");
					add("logicPortIndex");
					add("ipAdddress");
				}
			};
			LogicPortBean temp = (LogicPortBean) obj;
			return ReflectUtil.beanCompare(this, temp, fieldsName);
			
		}
		return false;
		
	}
	
	public float getBandwidth() {
		return bandwidth;
	}
	
	public void setBandwidth(float bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public String getMac() {
		return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public int getPortActive() {
		return portActive;
	}
	
	public void setPortActive(int portActive) {
		this.portActive = portActive;
	}
	
}
