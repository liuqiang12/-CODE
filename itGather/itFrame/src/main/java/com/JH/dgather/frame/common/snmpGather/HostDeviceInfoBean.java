package com.JH.dgather.frame.common.snmpGather;

/**
 * 主机硬件信息表
 * 
 * @author yangDS
 *
 */
public class HostDeviceInfoBean {
	private String hrDeviceIndex;//主机 组件index
	private String hrDeviceType;//主机 组件类型
	private String hrDeviceDescr;//主机 组件描述
	private String hrDeviceStatus;//主机 组件状态
	private String hrDeviceErrors;//主机 组件错误？(数？)
	
	public String getHrDeviceIndex() {
		return hrDeviceIndex;
	}
	
	public void setHrDeviceIndex(String hrDeviceIndex) {
		this.hrDeviceIndex = hrDeviceIndex;
	}
	
	public String getHrDeviceType() {
		return hrDeviceType;
	}
	
	public void setHrDeviceType(String hrDeviceType) {
		this.hrDeviceType = hrDeviceType;
	}
	
	public String getHrDeviceDescr() {
		return hrDeviceDescr;
	}
	
	public void setHrDeviceDescr(String hrDeviceDescr) {
		this.hrDeviceDescr = hrDeviceDescr;
	}
	
	public String getHrDeviceStatus() {
		return hrDeviceStatus;
	}
	
	public void setHrDeviceStatus(String hrDeviceStatus) {
		this.hrDeviceStatus = hrDeviceStatus;
	}
	
	public String getHrDeviceErrors() {
		return hrDeviceErrors;
	}
	
	public void setHrDeviceErrors(String hrDeviceErrors) {
		this.hrDeviceErrors = hrDeviceErrors;
	}
	
}
