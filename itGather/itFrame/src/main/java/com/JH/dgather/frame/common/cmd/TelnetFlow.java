package com.JH.dgather.frame.common.cmd;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.cmd.bean.TelnetFlowBean;
import com.JH.dgather.frame.common.db.DataUtil;

/**
 * telnet登陆流程
 * @author Administrator
 *暂时不考虑2级跳转，如果考虑，系统需调整代码，数据结构能够满足
 */
public class TelnetFlow {
	private Logger logger = Logger.getLogger(TelnetFlow.class);
	/**
	 * 初始化TELNET登陆信息，只知道设备ID情况下使用该方法
	 * @param deviceId = 设备ID
	 * @return 登陆流程数据
	 */
	public DataUtil du = null;
	
	public TelnetFlow() {
		du = new DataUtil();
	}
	
	public TelnetFlowBean initTelnetFlow(int deviceId) {
		DeviceInfoBean device = du.getRoutById(deviceId);
		System.out.println("设备信息："+device.toString());
		TelnetFlowBean tfb = initTelnetFlow(device);
		return tfb;
	}
	
	/**
	 * 已知设备详细信息后提取登陆信息
	 * @param device
	 * @return
	 */
	public TelnetFlowBean initTelnetFlow(DeviceInfoBean device) {
		TelnetFlowBean telnetFlowBean = new TelnetFlowBean();
		telnetFlowBean.setDevice(device);
		try {
			//Modify by gamesdoa 2012/6/6 修改VPN的获取
			//telnetFlowBean.setVPNparam(DataUtil.getDataUtil().getTelnetVPN(conn, device.getDeviceId()));
			logger.info("VPN:"+device.getTelnetVPNparm());
			telnetFlowBean.setVPNparam(device.getTelnetVPNparm());
			telnetFlowBean.setFlowList(du.getTelnetFlowPoint(device.getDeviceId()));
			logger.info("-------------:"+device.getParentroutid());
			DeviceInfoBean jumpDevice = du.getTelnetJumpDevice(device.getDeviceId());
			logger.info("-------------jumpDevice:"+jumpDevice);
			if (jumpDevice != null) {//需要跳转,获取跳转设备登陆信息
				TelnetFlowBean jumpTelnetFlowBean = new TelnetFlowBean();
				jumpTelnetFlowBean.setDevice(jumpDevice);
				jumpTelnetFlowBean.setFlowList(du.getTelnetFlowPoint(jumpDevice.getDeviceId()));
				telnetFlowBean.setJumpFlow(jumpTelnetFlowBean);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return telnetFlowBean;
	}
	
	//测试登陆
	public static void main(String args[]) {	
		
		TelnetFlow tfb = new TelnetFlow();
		TelnetFlowBean flow = tfb.initTelnetFlow(2);//271
		CMDService t = null;
		CMDManager cmdManager = null;
		try {
			cmdManager = new CMDManager(flow);
			t = cmdManager.getCMDService();
			t.land(flow);
			
			String s = t.exeCmd("dis cur", 5);
			System.out.print(s);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally{

			cmdManager.destroy("测试");
			t.disconnect("测试");
		}
	}
}
