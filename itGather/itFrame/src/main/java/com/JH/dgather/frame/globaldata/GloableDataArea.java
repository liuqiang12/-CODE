package com.JH.dgather.frame.globaldata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.reflect.bean.TableBean;
import com.JH.dgather.frame.common.xml.helper.TableColumnOIDXMlHelper;
import com.JH.dgather.frame.eventalarmsender.cmpp.CMPPSender;
import com.JH.dgather.frame.util.LoadClass;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.common.snmp.SnmpUtil;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskGcBean;

/**
 * 全局数据区(每一个task对应一个数据区域)
 * 
 * 
 */
public class GloableDataArea {
	
	private static final Logger logger = Logger.getLogger(GloableDataArea.class);
	
	public static int distributedNode = 0;//分布式部署节点编号，默认0，数据库中会将所有设备归属节点默认为0
	
	public static boolean runflag = true;//系统启动停止标志
	
	private static HashMap<Integer, HashMap<String, ArrayList<TableBean>>> oidInfo;
	
	public static String exeResultPath;// LOG保存路径
	
	//add by gamesdoa 2012/6/4 kpibase
	private static HashMap<Integer,String> kpiBaseIdName=new HashMap<Integer,String>();
	private static HashMap<String,Integer> kpiBase=new HashMap<String, Integer>();
	private static HashMap<String,String> kpiBaseName=new HashMap<String, String>();
	private static HashMap<Integer,Integer[]> kpiBaseGCC=new HashMap<Integer,Integer[]>();
	private static HashMap<Integer,String> kpiBaseIdKey=new HashMap<Integer,String>();
	private static HashMap<Integer,String> kpiBaseUnit=new HashMap<Integer,String>();
	public static Map<Integer,TaskGcBean> taskGcMap = null;
	
	private static HashMap<Integer,Integer> taskWorkTimeOut = new HashMap<Integer, Integer>();
	public static HashMap<Integer,ConcurrentLinkedQueue<Integer>> taskQueueMap = new HashMap<Integer,ConcurrentLinkedQueue<Integer>>();

	// 启动事件告警发送
	public static String eventAlarmSend = "false";
	//是否为运营商版本，true是运营商，否则为非运营商，默认为运营商
	public static String carrierVersion = "true";
	
	public static String ftpServerIp="";
	public static int ftpServerPort=21;
	public static String ftpServerUser="";
	public static String ftpServerPassword="";
	
	//ping参数
	//超时毫秒
	public static long pingTimeout = 800;
	//重传次数
	public static int pingRetries = 2;
	//是否不ping测
	public static boolean ignorePing = false;
	//public static XMLParseProcess xmlProcess;
	//snmp参数
	public static SnmpUtil su = null;
	
	//telnet tcp 参数
	public static int SoTimeout = 15000;// SOCKET阻塞读取数据延迟时间10秒钟
	public static int SoReceiveBufferSize = 1024*20;
	public static boolean SoTcpNoDelay = true;
	
	//新疆部署方式的采集点所属区域
	public static Integer areaid = null;
	public static final int ALARM_SENDTYPE_CMPP = 3;
	public static String appDir;
	static{
		appDir = initAppDir();
	//	logger.info("系统路径"+appDir );
		System.out.println(appDir);
	}
	private static String initAppDir() {
		//取当前路径的上级路径
		String currentDir = GloableDataArea.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		if(currentDir.indexOf("/lib")!=-1){
			currentDir = currentDir.split("/lib")[0];
		}else if(currentDir.indexOf("/target")!=-1){
			currentDir = currentDir.split("/target")[0];
		}else{
			currentDir = currentDir.substring(0,currentDir.lastIndexOf("/"));
		}
		return currentDir;
	}
	public static void main(String[] s){
		System.out.println(appDir);
	}

	public static void init() throws TagException {
		logger.info("初始全局对象区域...");
		oidInfo = TableColumnOIDXMlHelper.initGloadbleOidPool();
//初始化任务加载
		try {
			String taskgc = appDir+"/taskgc.xml";
			taskGcMap = LoadClass.parseXmlData(taskgc);
		} catch (Exception e) {
			taskGcMap = null;
		}
		if(taskGcMap == null || taskGcMap.size() ==0){
			logger.error("未找到系统可执行任务配置！请检查采集程序的sys.properties里面的taskgc属性是否指向了正确的taskgc.xml文件以及确认taskgc.xml文件内的配置是否正确！");
			return;
		}
//初始化KPIBeas 
		GloableDataDB gdd = new GloableDataDB();
		gdd.initGloadbleKpiBase(kpiBase,kpiBaseIdKey,kpiBaseName,kpiBaseIdName,kpiBaseGCC);

		//设置数据库GLOBAL event_scheduler为ON开启状态；
		//gdd.selectEventScheduler();
		try{
			distributedNode = Integer.parseInt( PropertiesHandle.getResuouceInfo("distributedNode"));
		}catch(Exception e){
			logger.error("没有定义分布式节点号");
		}
		try {
			exeResultPath = PropertiesHandle.getResuouceInfo("exeResultPath");
		} catch (Exception e) {
			logger.error("读取高级寻检LOG保存路径出错,请检查配置文件properties是否有exeResultPath的配置,系统默认以D:/exeResultPath/为保存目录");
			exeResultPath = "../exeResultPath/";
		}finally{
			if(exeResultPath==null)
				exeResultPath = "D:/exeResultPath/";
		}
		
		try {
			eventAlarmSend = PropertiesHandle.getResuouceInfo("eventAlarmSend").trim();
		} catch (Exception e) {
			eventAlarmSend = "false";
		}finally{
			if(eventAlarmSend==null)
				eventAlarmSend = "false";
		}
		
		
		try {
			ftpServerIp = PropertiesHandle.getResuouceInfo("ftp.server.ip");
		} catch (Exception e) {
			ftpServerIp = "";
		}finally{
			if(ftpServerIp==null)
				ftpServerIp = "";
		}
		
		
		try {
			ftpServerPort = Integer.parseInt(PropertiesHandle.getResuouceInfo("ftp.server.port"));
		} catch (Exception e) {
			ftpServerPort = 21;
		}
		
		
		try {
			ftpServerUser = PropertiesHandle.getResuouceInfo("ftp.server.user");
		} catch (Exception e) {
			ftpServerUser = "";
		}finally{
			if(ftpServerUser==null)
				ftpServerUser = "";
		}
		
		try {
			ftpServerPassword = PropertiesHandle.getResuouceInfo("ftp.server.password");
		} catch (Exception e) {
			ftpServerPassword = "";
		}finally{
			if(ftpServerPassword==null)
				ftpServerPassword = "";
		}
		
//		if(ftpServerIp.equals(""))
//			gdd.initFTPInfo();
		try {
			long tmplv = Long.parseLong(PropertiesHandle.getResuouceInfo("pingTimeout"));
			if( tmplv > 0 ){
				pingTimeout = tmplv;	
			}
		} catch (Exception e) {
			logger.warn("没有配置参数pingTimeout或者配置不正确！使用默认值。");
		}
		
		
		
		try {
			su = new SnmpUtil();
		} catch (Exception e) {
			su = null;
		}
	
		//init 各个任务的worker超时时间
		getAndSetTaskWorkTimeOut();
//		xmlProcess = new XMLParseProcess();
//		xmlProcess.parseFactoryAndRuleId();
//		xmlProcess.parseSystemRules();
		
	}
	
	/**
	 * 
	 * 返回系統中所有可用的oid信息
	 * 
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, ArrayList<TableBean>>> getOIDCollection() {
		return oidInfo;
	}
	
	private static void duelTaskTimeOut(int gc,String key,HashMap<Integer,Integer> timeOut){
		int time=3000;
		String timeStr = PropertiesHandle.getResuouceInfo(key);
		try {
			time = Integer.parseInt(timeStr);
		} catch (Exception e) {
			time = 3000;
		}
		timeOut.put(gc, time);
	}
	
	private static void getAndSetTaskWorkTimeOut(){
//		duelTaskTimeOut(8,"ics.task.worker.artificialhand.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(9,"ics.task.worker.cap.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(1,"ics.task.worker.configAnalyse.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(6,"ics.task.worker.configBackUp.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(12,"ics.task.worker.lightDbm.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(11,"ics.task.worker.lineAnalyse.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(22,"ics.task.worker.porttypegather.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(98,"ics.task.worker.resourcecollect.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(5,"ics.task.worker.resourceConfig.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(99,"ics.task.worker.resourceGather.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(19,"ics.task.worker.routetable.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(24,"ics.task.worker.routprotocol.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(26,"ics.task.worker.vlan.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(27,"ics.task.worker.tracert.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(28,"ics.task.worker.remoteping.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(29,"ics.task.worker.accesscontrol.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(32,"ics.task.worker.clockcheck.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(35,"ics.task.worker.ipam.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(36,"ics.task.worker.arp.timeout",taskWorkTimeOut);
//		duelTaskTimeOut(20,"ics.task.worker.account.timeout",taskWorkTimeOut); 
//		duelTaskTimeOut(30,"ics.task.worker.ponCompliancy.timeout",taskWorkTimeOut);
	}
	
	
	/**
	 * 返回系统kpibase信息 
	 * add @author gamesdoa
	 * @email gamesdoa@gmail.com
	 * @date 2012-6-4
	 * @return HashMap<String, Integer> String型key统一转换为小写读取
	 */
	public static HashMap<String, Integer> getKpiBase() {
		return kpiBase;
	}
	public static HashMap<String, String> getKpiBaseName() {
		return kpiBaseName;
	}

	public static HashMap<Integer, Integer> getTaskWorkTimeOut() {
		return taskWorkTimeOut;
	}

	public static HashMap<Integer, String> getKpiBaseIdName() {
		return kpiBaseIdName;
	}

	public static HashMap<Integer, Integer[]> getKpiBaseGCC() {
		return kpiBaseGCC;
	}
	
	public static String getKpiBaseUnit(int kpiId) {
		return kpiBaseUnit.get(kpiId);
	}

	public static HashMap<Integer, String> getKpiBaseIdKey() {
		return kpiBaseIdKey;
	}
	
}
