package com.JH.itBusi.business.deviceCap.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.snmp4j.PDU;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.reflect.bean.ColumnBean;
import com.JH.dgather.frame.common.snmp.bean.OIDbean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.DateUtil;
import com.JH.dgather.frame.util.OIDHelper;
import com.JH.itBusi.business.deviceCap.analyse.CapAnalyse;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;
//import com.JH.itBusi.business.deviceCap.result.CapGatherResult;

public class CapGatherWorker extends RootWorker {
	private Logger logger = Logger.getLogger(CapGatherWorker.class);
	// public static final String FLAG_OID = "1.3.6.1.2.1.1.2.0";// snmp
	private ExecutiveController parent;
	private DeviceInfoBean device = null;
	// 设备性能采集数据
	private DeviceCapData deviceCapData;
	// 采集设备和端口对象
	// private DeviceAndPort gatherDevice;
	// 设备oid对象
	private ArrayList<ColumnBean> lsOid = new ArrayList<ColumnBean>();

	// 采集临时数据保存区域,String=采集的字段名 ;String=OID的index Object=Oid值
	private HashMap<String, HashMap<String, Object>> lsDataStore = new HashMap<String, HashMap<String, Object>>();

	private long snmpResposeTimeout = 2 * 1000l;
	private long snmpTimeout = 5 * 60 * 1000l;
	private int retries = 2;
	private EventHandle handle = null;

	public CapGatherWorker(ExecutiveController controller, DeviceInfoBean device, DeviceCapData deviceCapData, ExecutiveResult cgResult,  EventHandle handle
			) {
		super(controller,  cgResult);

		this.device = device;
		this.deviceCapData = deviceCapData;
		parent = controller;
		this.handle = handle;
	}

	// 将SNMP采集的SYSUPTIME转换成double型
	// 采集的数据表格为：76 days, 11:11:11.20
	private double transSysUptime(String time) {
		double rtn = 0;
		if (time.indexOf("days") != -1) {
			String regex = "(\\d++)\\s+days,\\s+(\\d++):(\\d++):(\\d++)\\.(\\d++)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(time);
			if (m.find()) {
				String d = m.group(1);
				String h = m.group(2);
				String min = m.group(3);
				String s = m.group(4);
				String ms = m.group(5);

				rtn = Long.parseLong(d) * (24 * 3600 * 1000) + Long.parseLong(h) * (3600 * 1000) + Long.parseLong(min) * (60 * 1000) + Long.parseLong(s) * 1000 + Long.parseLong(ms);
			}
		} else {
			String regex = "(\\d++):(\\d++):(\\d++)\\.(\\d++)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(time);
			if (m.find()) {
				String h = m.group(1);
				String min = m.group(2);
				String s = m.group(3);
				String ms = m.group(4);

				rtn = Long.parseLong(h) * (3600 * 1000) + Long.parseLong(min) * (60 * 1000) + Long.parseLong(s) * 1000 + Long.parseLong(ms);
			}
		}
		return rtn;
	}

	/**
	 * 初始化工作
	 */
	private void doInitWork() {
		deviceCapData.setExecTime(DateUtil.getDateTime());
		// 设置deviceCapData中的设备信息
		deviceCapData.setDevice(this.device);
		// 设置deviceCapData中的设备id信息
		deviceCapData.setDeviceId(this.device.getDeviceId());
		// 初始设备oid
		getNeedGatherOID();
		/**
		 * 备注： 1. 当前设备在此任务中执行的端口
		 * this.getTaskObjBean().getPortNameLs().get(device.getDeviceId())
		 * 
		 */
	}

	/*
	 * 根据设备厂家、型号、以及需要的字段获取需要的采集的OID
	 */
	private void getNeedGatherOID() {
		logger.info("设备信息: " + device.toString() + ";thread[" + Thread.currentThread().getName());
		int factory = device.getFactory();
		String model = device.getModelName();

		ColumnBean column = null;
		/**
		 * 根据性能采集的类型，确定需要采集的OID列表
		 */
		logger.info("KPIID: " + this.parent.userTask.getUserTaskBean().getKpiId()+";KPIKEY："+GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()));
		if(GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()).equalsIgnoreCase("ProcessValue")){
			// cpu巡检
			column = OIDHelper.getColumnBean(factory, model, "Net_CPU", "cpuprocess");
			if (column != null && !"".equals(column.getOid())) {
				lsOid.add(column);
				// logger.error(factory+model+column.getName()+column.getOid());
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()).equalsIgnoreCase("UsedValue")){
			// 内存巡检
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryPoolSize");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryPoolLargestFree");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryPoolName");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryPoolUsed");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryPoolFree");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
			column = OIDHelper.getColumnBean(factory, model, "net_memory", "memoryUtilization");
			if (column != null && !"".equals(column.getOid()))
				lsOid.add(column);
		}else if(GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()).equalsIgnoreCase("PWRStatus")){
			 // 电源巡检
			column = OIDHelper.getColumnBean(factory, model, "net_PowerEntity", "supplyState");

			logger.info("电源OID：" + column);
			if (column != null && !"".equals(column.getOid())) {
				logger.info(column.getName() + ";" + column.getOid());
				lsOid.add(column);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()).equalsIgnoreCase("FanStatus")){
			// 风扇巡检
			column = OIDHelper.getColumnBean(factory, model, "net_FanEntity", "fanStatus");
			logger.info("风扇OID：" + column);
			if (column != null && !"".equals(column.getOid())) {
				logger.info(column.getName() + ";" + column.getOid());
				lsOid.add(column);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(this.parent.userTask.getUserTaskBean().getKpiId()).equalsIgnoreCase("Temperature")){
			// 温度巡检
			column = OIDHelper.getColumnBean(factory, model, "Net_temperature", "temperature");
			logger.info("温度OID：" + column);
			if (column != null && !"".equals(column.getOid())) {
				logger.info(column.getName() + ";" + column.getOid());
				lsOid.add(column);
			}
			column = OIDHelper.getColumnBean(factory, model, "Net_temperature", "temperature_statu");
			logger.info("温度状态OID：" + column);
			if (column != null && !"".equals(column.getOid())) {
				logger.info(column.getName() + ";" + column.getOid());
				lsOid.add(column);
			}
		}
	}

	/**
	 * 采集数据
	 */
	private void doGather() throws Exception {
		List<OIDbean> beans = null;
		String[] oid;
		OIDbean oidb = null;

			try {
				for (ColumnBean column : lsOid) {
					try {
						/**
						 * 为了对应OID的GET和GETNEXT的定义错误
						 * 优先GET,如果GET不出结果,则用GETNEXT进行WALK
						 */
						beans = GloableDataArea.su.walk(device, column.getOid(), retries, snmpResposeTimeout, snmpTimeout);
						if (beans != null && beans.size() > 0) {
							logger.info(this.parent.userTask.getTaskName() + "设备性能采集,设备：" + device.getDeviceName() + "采集到数据长度为 ：" + beans.size() + ", column: " + column.getName() + ";thread["
									+ Thread.currentThread().getName()); 
							HashMap<String, Object> hm = new HashMap<String, Object>();
							
							Iterator<OIDbean> it = beans.iterator();
							OIDbean bean;
							int oidIndex = 0;
							try {
								oidIndex = Integer.parseInt(column.getIndex());
							} catch (Exception e) {
								oidIndex = 0;
							}
							while (it.hasNext()) {
								bean = it.next();

								logger.info(bean.getOid() + ";" + bean.getValue());
								oid = bean.getOid().split("\\.");
								hm.put(oid[oid.length - 1 - oidIndex], bean.getValue());

							}
							logger.info("column.getName(): " + column.getName() + ";column.getIndex():" + column.getIndex() + ";thread[" + Thread.currentThread().getName() + "]");
							lsDataStore.put(column.getName(), hm);
						} else {
							logger.info(this.parent.userTask.getTaskName() + "日常性能巡检,设备：" + device.getDeviceName() + "未采到数据！OID：" + column.getOid() + ";columnName: " + column.getName() + ";thread["
									+ Thread.currentThread().getName());
						}
					} catch (Exception e) {
						logger.error(this.parent.userTask.getTaskName() + "日常性能巡检,设备:" + device.getDeviceName() + ";thread[" + Thread.currentThread().getName() + "执行性能采集出现错误：", e);
						continue;
					}
				}
			} catch (Exception e) {
				logger.error(this.parent.userTask.getTaskName() + "日常性能巡检,设备:" + device.getDeviceName() + ";thread[" + Thread.currentThread().getName() + "执行采集出现错误：", e);
			}
		
	}

	/**
	 * 数据分析
	 */
	private void doAnalyse() {
		logger.info(this.parent.userTask.getTaskName() + "开始执行数据分析,设备：" + device.getDeviceName() + "thread[" + Thread.currentThread().getName() + "]执行任务");
		new CapAnalyse(deviceCapData, lsDataStore, this.parent.userTask.getUserTaskBean());
	}

	@Override
	public ExecutiveResult execute() {
		logger.info(this.parent.userTask.getTaskName() + "设备：" + device.getDeviceName() + "使用thread[" + Thread.currentThread().getName() + "]执行任务");
		try {
			boolean suFlag = GloableDataArea.su.canSNMP(device);
			if (!suFlag || device.getSnmpVersion() == -1){
				logger.error("deviceInfoBean[" + device.getDeviceName() + "]'s version not in snmp v1 v2");
				logger.error("deviceInfoBean：" + device);
				throw new Exception(device.getIPAddress() + "访问SNMP异常；使用thread[" + Thread.currentThread().getName()+"]执行任务");
			}
			logger.debug("device[" + device.getDeviceName() + "]'s version is:"+device.getSnmpVersion());
		/**
		 * STEP1: 初始化任务
		 */
			doInitWork();
	/**
		 * STEP2: 采集数据
		 */
			doGather();
		} catch (Exception e) {
			logger.error(this.parent.userTask.getTaskName() + "采集性能采集数据失败，设备名称：{" + device.getDeviceName() + "}, 原因: " + e.getMessage());
			e.printStackTrace();
			exceptionFinish(e.getMessage());
			this.setRunFlag(TaskRunFlag.FINAL);
			this.getWorkerResult().setSuccessSize(0);
		}
		try{
			/**
			 * STEP3: 数据分析
			 */
	
			doAnalyse();
			this.setRunFlag(TaskRunFlag.SUCCESS);
			
		}catch(Exception e){
			logger.error(this.parent.userTask.getTaskName() + "采集性能采集数据分析失败，设备名称：{" + device.getDeviceName() + "}, 原因: " + e.getMessage());
			e.printStackTrace();
			exceptionFinish(e.getMessage());
			this.setRunFlag(TaskRunFlag.FINAL);
			this.getWorkerResult().setSuccessSize(0);
			
		}
		logger.info(this.parent.userTask.getTaskName() + "设备：" + device.getDeviceName() + "thread[" + Thread.currentThread().getName() + "]执行完成，准备释放线程！");
		return this.getWorkerResult();
}
}
		
