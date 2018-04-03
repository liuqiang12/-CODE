package com.JH.dgather.frame.common.snmpGather;

/**
 * 
 * 主机cpu负载信息表
 * 
 * @author yangDS
 *
 */
public class HostProcessorLoadInfoBean {
	
	private String hrDeviceIndex;//对应主机组件索引
	private String hrProcessorFrwID;//硬件 productID
	private String hrProcessorLoad;//cpu负载
	
	/**
	 * @return
	 */
	public String getHrDeviceIndex() {
		return hrDeviceIndex;
	}
	
	public void setHrDeviceIndex(String hrDeviceIndex) {
		this.hrDeviceIndex = hrDeviceIndex;
	}
	
	public String getHrProcessorFrwID() {
		return hrProcessorFrwID;
	}
	
	public void setHrProcessorFrwID(String hrProcessorFrwID) {
		this.hrProcessorFrwID = hrProcessorFrwID;
	}
	
	public String getHrProcessorLoad() {
		return hrProcessorLoad;
	}
	
	public void setHrProcessorLoad(String hrProcessorLoad) {
		this.hrProcessorLoad = hrProcessorLoad;
	}
	
}