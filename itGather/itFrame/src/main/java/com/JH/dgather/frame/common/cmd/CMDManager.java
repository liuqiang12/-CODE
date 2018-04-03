package com.JH.dgather.frame.common.cmd;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.cmd.bean.TelnetFlowBean;
import com.JH.dgather.frame.common.exception.TelnetException;

public class CMDManager {
	Logger logger = Logger.getLogger(CMDManager.class.getName());
	private CMDService cmdService;
	//private static Integer ALLOWLANNUM = 1;//允许同时登录设备账户量,某种情况下可让这个数据成变量
	//HashMap<Integer,ArrayList<CMDService>> serviceLs 考虑可能不同设备可并发登录量不同，但原则上系统不能针对一个设备有太多登录
	//private static HashMap<Integer,ArrayList<CMDService>> serviceLs = new HashMap<Integer,ArrayList<CMDService>>();
	//用于快速提取serviceLs中的值
	//private static HashMap<CMDService,Integer> serviceLsTmp = new HashMap<CMDService,Integer>();
	/*public static CMDService getCMDService(TelnetFlowBean telnetFlowInfo) throws NullPointerException, TelnetException {
		if (telnetFlowInfo == null) {
			throw new NullPointerException("TelnetFlowBean为Null!");
		}
		
		DeviceInfoBean device = getLastJumpFlow(telnetFlowInfo).getDevice();
		if(ifAllowLanded(device.getDeviceId()))//如果已经登录则不允许再次登录
			throw new TelnetException("系统已经登录到"+device.getDeviceName()+"设备，等待退出后才能继续登录！");
		CMDService cmdService;
		switch (device.getLoginModel()) {
		case 0:
			cmdService = new TelnetService();
			break;
		case 1:
			cmdService = new SSHService();
			break;
		default:
			cmdService = null;
		}
		if(cmdService!=null){
			cmdService.land(telnetFlowInfo);
			recordCmdService(device.getDeviceId(),cmdService);
			
		}
		return cmdService;
	}*/
	/**
	 * 记录已经登录的设备
	 * @param cmdService2
	 */
	/*private static void recordCmdService(Integer deviceId,CMDService cmdService2) {
		//分别以deviceid和cmdService2为关键字存储两个HASH
		serviceLsTmp.put(cmdService2, deviceId);
		if(serviceLs.get(deviceId)==null){
			ArrayList<CMDService> services = new ArrayList<CMDService>();
			services.add(cmdService2);
			serviceLs.put(deviceId, services);
		}else
		{
			serviceLs.get(deviceId).add(cmdService2);
		}
	}*/
	/**
	 * 
	 * @param device
	 * @return 是否有设备登录
	 * 判断要登录的设备是否正在被系统使用，禁止针对一台设备同时>1次的登录
	 */
	/*private static boolean ifAllowLanded(Integer deviceId) {
		if(serviceLs.get(deviceId)==null)
			return true;
		else{
			Iterator<CMDService> services = serviceLs.get(deviceId).iterator();
			//临时再次检查是否有连接已经关闭但是为做资源释放的
			while(services.hasNext()){
				if(!services.next().isConnect)
					services.remove();
			}
			if(serviceLs.get(deviceId).size()>=ALLOWLANNUM)
				return false;
			else{
			if(serviceLs.get(deviceId).size()==0)//如果已经没有登录则取消记录
				serviceLs.remove(deviceId);
			return true;
			}
		}
		
	}*/
/**
 * 销毁CmdService
 * @param cmdService
 * @throws IOException 
 * @throws TelnetException 
 * 
 */
	/*public static void destroyCmdService(CMDService cmdService) throws TelnetException, IOException{
		try{
			Iterator<CMDService> services = serviceLs.get(serviceLsTmp.get(cmdService)).iterator();
			while(services.hasNext()){
				if(services.next()==cmdService){
					services.remove();
					break;
				}
			}
		}catch(Exception e){
			logger.error("在清除cmdService时没找到相应记录");
		}
		cmdService.disconnect();

	}*/
	
	public CMDManager(TelnetFlowBean telnetFlowInfo) throws NullPointerException {
		if (telnetFlowInfo == null) {
			throw new NullPointerException("TelnetFlowBean为Null!");
		}
		
		DeviceInfoBean device = getLastJumpFlow(telnetFlowInfo).getDevice();
		
		switch (device.getLoginModel()) {
		case 0:
			cmdService = new TelnetService();
			break;
		case 1:
			cmdService = new SSHService();
			break;
		default:
			cmdService = null;
		}
	}
	
	public CMDService getCMDService() {
		return this.cmdService;
	}
	
	public void destroy(String deviceName){
		cmdService.disconnect(deviceName);
		cmdService = null;
	}
	
	private TelnetFlowBean getLastJumpFlow(TelnetFlowBean telnetFlowInfo) {
		TelnetFlowBean jumpTelnetFlowTmp = telnetFlowInfo.getJumpFlow();
		TelnetFlowBean jumpTelnetFlow = null;
		while (jumpTelnetFlowTmp != null) {
			jumpTelnetFlow = jumpTelnetFlowTmp;
			jumpTelnetFlowTmp = jumpTelnetFlow.getJumpFlow();
		}
		if (jumpTelnetFlow != null)
			return jumpTelnetFlow;
		else
			return telnetFlowInfo;
	}
	
}
