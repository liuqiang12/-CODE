package com.JH.dgather.frame.common.cmd.bean;

import java.util.ArrayList;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;

/**
 * telnet登陆流程数据结构
 * @author muyp
 *
 */
public class TelnetFlowBean {
	private DeviceInfoBean  device;//登陆设备
	private String VPNparam;//VPN参数,如果为空则认为不需要VPN登陆
	private TelnetFlowBean jumpFlow ;//跳转流程
	private ArrayList<FlowPointBean> flowList;//登陆流程
	public DeviceInfoBean getDevice() {
		return device;
	}
	public void setDevice(DeviceInfoBean device) {
		this.device = device;
	}
	public ArrayList<FlowPointBean> getFlowList() {
		return flowList;
	}
	public void setFlowList(ArrayList<FlowPointBean> flowList) {
		this.flowList = flowList;
	}
	public TelnetFlowBean getJumpFlow() {
		return jumpFlow;
	}
	public void setJumpFlow(TelnetFlowBean jumpFlow) {
		this.jumpFlow = jumpFlow;
	}
	public String getVPNparam() {
		return VPNparam;
	}
	public void setVPNparam(String nparam) {
		VPNparam = nparam;
	}

}
