package com.JH.itBusi.business.resource.worker;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.bean.PortInfoBean;
import com.JH.dgather.frame.common.reflect.bean.ColumnBean;
import com.JH.dgather.frame.common.reflect.bean.TableBean;
import com.JH.dgather.frame.common.snmp.bean.OIDbean;
import com.JH.dgather.frame.common.snmpGather.NetElementInterfaceBean;
import com.JH.dgather.frame.common.snmpGather.NetElementSysBean;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker;
import com.JH.dgather.frame.globaldata.DataCache;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.OIDHelper;
import com.JH.itBusi.business.resource.db.Bean.DeviceData;
import com.JH.itBusi.util.StringUtil;



public class ResGatherWorker extends RootWorker {
	Logger logger = Logger.getLogger(ResGatherWorker.class);
	private ArrayList<TableBean> oids;
	HashMap<Integer,DeviceData> deviceDataHs;//采集成功的设备，则创建DeviceData，放入里面
	DeviceData deviceData;
	private DataCache dc;

	private DeviceInfoBean deviceInfoBean;
	private HashMap<String, String> ifIndexMap = new HashMap<String, String>();
	private HashMap<String, Object> ifTable;

	private long snmpResponsTimeout = 1500l;
	private long snmpTimeout = 10*60*1000l;
	

	public ResGatherWorker(ExecutiveController controller,
			DeviceInfoBean device, HashMap<Integer,DeviceData> devicedataHs, ExecutiveResult workerResult) {
		super(controller, workerResult);
		this.deviceDataHs = devicedataHs;
		this.deviceInfoBean = device;
		this.oids = new ArrayList<TableBean>();
	}
	
	private void getConfigInfoBySNMP() throws UnknownHostException, SocketException {
		String deviceName = deviceInfoBean.getDeviceName();
		String deviceIp = deviceInfoBean.getDeviceIp();

		String message = deviceName + "[" + deviceIp + "]的硬件配置信息采集开始...";
		String endMessage = deviceName + "[" + deviceIp + "]的硬件配置信息采集结束...";

		logger.debug(message);
		// 取得需要采集的oid
		getOIDs();
		//采集端口索引
	//	getPortIndex();
		// 采集并存储
		OIDHelper.getOIDinfoAndSaveInCache(oids, deviceInfoBean, dc,3,1500l,snmpTimeout);
		logger.debug(endMessage);
	}
	private void getPortIndex(){
		ColumnBean column = null;
		// 取得端口的index
		column = OIDHelper.getColumnBean(deviceInfoBean.getFactory(), deviceInfoBean.getModelName(), "Net_Element_If_Info", "ifIndex");
		if (column != null) {
			
			HashMap<String, Object> ifIndex = new HashMap<String, Object>();
			getSnmpInfobyOID(column, ifIndex, true, false);
			logger.info("ifIndex:"+ifIndex);
			Iterator<Entry<String, Object>> it = ifIndex.entrySet().iterator();
			Entry<String, Object> entry;
			while (it.hasNext()) {
				entry = it.next();
				ifIndexMap.put(entry.getValue().toString(), entry.getKey());
			}
		}
	}
	
	private void getSnmpInfobyOID(ColumnBean column, HashMap<String, Object> hm, boolean isSubString, boolean isLastFlag) {
		logger.info("开始执行指标"+column.getName()+"采集工作");
		List<OIDbean> beans = null;
		logger.info("开始采集Oid: " + column.getOid() + ";name:" + column.getName());

		try {
			/**
			 * 为了对应OID的GET和GETNEXT的定义错误 优先GET,如果GET不出结果,则用GETNEXT进行WALK
			 */
			beans = GloableDataArea.su.walk(deviceInfoBean, column.getOid(),3,snmpResponsTimeout,snmpTimeout);
			if ((beans != null)) {
				logger.info("采集到数据长度为 ：" + beans.size() + ", column: " + column.getName());
				if (beans != null && beans.size() > 0) {
					Iterator<OIDbean> it = beans.iterator();
					OIDbean bean;
					if (isSubString) {
						while (it.hasNext()) {
							bean = it.next();
							logger.info("Oid: " + bean.getOid() + ";value:" + bean.getValue());
							hm.put(bean.getOid().substring(bean.getOid().lastIndexOf(".") + 1), bean.getValue());
						}
					} else if (isLastFlag) {// 需要截取尾部的信息
						String[] oid;
						while (it.hasNext()) {
							bean = it.next();
							logger.info("Oid: " + bean.getOid() + ";value:" + bean.getValue());
							oid = bean.getOid().split("\\.");
							hm.put(oid[oid.length - 3] + "." + oid[oid.length - 2], bean.getValue());
						}
					} else {
						String[] oid;
						while (it.hasNext()) {
							bean = it.next();
							logger.info("Oid: " + bean.getOid() + ";value:" + bean.getValue());
							oid = bean.getOid().split("\\.");
							hm.put(oid[oid.length - 2] + "." + oid[oid.length - 1], bean.getValue());
						}
					}
				}
				logger.info("column.getName(): " + column.getName());
			} else {
				logger.warn("未采到数据！columnName: " + column.getName());
			}
		} catch (Exception e) {
			logger.error("ONU指标："+column.getName()+"巡检,设备:" + deviceInfoBean.getDeviceName() + "执行性能采集出现错误：", e);
		}

		logger.debug("ONU指标："+column.getName()+"巡检,设备：" + deviceInfoBean.getDeviceName() + "采集结束...");
	}

	
	/**
	 * get the information of oid that will be used
	 */
	@SuppressWarnings("serial")
	private void getOIDs() {
		int factory = deviceInfoBean.getFactory();
		String model = deviceInfoBean.getModelName();
		TableBean table = null;
		ArrayList<String> columnNames = new ArrayList<String>() ;
		columnNames.add("ifIndex");
		columnNames.add("ifDescr");
		columnNames.add("ifType");
		columnNames.add("ifPhysAddress");
		columnNames.add("ifAdminStatus");
		columnNames.add("ifOperStatus");
		columnNames.add("ifSpeed");
		columnNames.add("ifHighSpeed");
		columnNames.add("ifAlias");
		columnNames.add("ifName");
		columnNames.add("ifLastChange");

		table = OIDHelper.getOIDTableWithSpecifiedColumn(factory, model, "Net_Element_If_Info", columnNames);
		logger.info("Net_Element_If_Info:"+table);
		if(table!=null)
			oids.add(table);

		columnNames.clear();
		columnNames.add("sysDescr");
		columnNames.add("sysUpTime");
		columnNames.add("sysObjectId");
		columnNames.add("sysName");
		if(table!=null)
		table = OIDHelper.getOIDTableWithSpecifiedColumn(factory, model, "net_system", columnNames);
		logger.info("net_system:"+table);
		oids.add(table);
	}
	
	private void analyseConfigInfo() {
		ifTable = (HashMap<String, Object>) dc.getTable("Net_Element_If_Info", true);
		logger.info("ifTable:"+ifTable);
		genDeviceInfo();
		logger.debug(deviceInfoBean.getIPAddress() + "分析开始");
		doIfTableAnalyse();
		logger.debug(deviceInfoBean.getIPAddress() + "分析结束");
	}

	/**
	 * 解析ifTable中的数据
	 */
	public void doIfTableAnalyse() {
		if (ifTable == null)
			return;
		NetElementInterfaceBean ifBean = null;
		
		logger.debug(deviceInfoBean.getDeviceName()+"资源采集ifTable的列表大小:"+ifTable.size());
		for (Entry<String, Object> entry : ifTable.entrySet()) {
			ifBean = (NetElementInterfaceBean) entry.getValue();
			if (ifBean == null)
				continue;
			if(ifBean.getIfIndex()==null)
				continue;
			logger.info("设备端口信息："+ifBean);
			try{
			if(ifBean.getIfName().startsWith("Consol")||ifBean.getIfName().startsWith("NULL")
					||ifBean.getIfName().startsWith("Loop")||ifBean.getIfName().startsWith("InLoop"))
				continue;
			}catch(Exception e){
				logger.error("ifBean.getIfName()提取异常，设备为"+deviceInfoBean.getDeviceIp(),e);
				continue;
			}
			int ifType = -1;
			try {
				ifType = Integer.parseInt(ifBean.getIfType());
			} catch (Exception e) {
				ifType = -1;
			}
			PortInfoBean pib = new PortInfoBean();
			String index = ifBean.getIfIndex();
			logger.debug("端口解析时端口的索引："+index);
			pib.setPortIndex(Long.parseLong(index));
			pib.setIfType(ifType);
			pib.setDeviceId(deviceInfoBean.getDeviceId());
			pib.setSlotsNo(-1);
			pib.setBoardNo(-1);
			pib.setBoardIndex(-1);
			pib.setPortCode(-1);
			pib.setPortActive(Integer.parseInt(ifBean.getIfOperStatus() == null ? "-1" : ifBean.getIfOperStatus()));
			pib.setAdminStatus(Integer.parseInt(ifBean.getIfAdminStatus() == null ? "-1" : ifBean.getIfAdminStatus()));
			pib.setLastChange(ifBean.getIfLastChange());
			
			pib.setMac(ifBean.getIfPhysAddress());
			switch (deviceInfoBean.getFactory()) {
			case 71:// muyp modify 2011-07-08 如果是爱立信设备则需单独做处理
				if(ifBean.getIfName() != null){
				  pib.setPortName(ifBean.getIfName().replace("ethernet ", ""));
				}
				break;
			default:
				pib.setPortName(ifBean.getIfName());
				break;
			}
			//pib.setNote(ifBean.getIfDescr());// muyp modify 2011-07-18
			pib.setDescr(ifBean.getNote());
			//别名字段有可能为null
			if(ifBean.getIfAlias() != null){
			  pib.setAlias(ifBean.getIfAlias().replaceAll("\"", "").trim());
			}
			String speed = ifBean.getIfSpeed();
			String ifHighSpeed = ifBean.getIfHighSpeed();
			String bandwidthNum;
			if(ifHighSpeed != null && !"0".equals(ifHighSpeed.trim())&& !"".equals(ifHighSpeed.trim())){
				bandwidthNum = ifHighSpeed;
			} else {
				if(speed==null)//没有带宽,不正确
					continue;
				else
				bandwidthNum = speed;
			}
			
			if (!StringUtil.isNumberString(bandwidthNum)) {
				if(bandwidthNum!=null && bandwidthNum.contains("~")){
					bandwidthNum = bandwidthNum.substring(bandwidthNum.indexOf("~")+1, bandwidthNum.length());
					bandwidthNum = bandwidthNum.replaceAll("(?!\\.)\\D", "");// 如果含有非数字的字符，则替换为空
					int length = bandwidthNum.length();
					bandwidthNum = bandwidthNum.substring(0, 1);
					for (int i=1;i<length;i++) {
						bandwidthNum +="0";
					}
				}
			}
			logger.info("设备id=" + deviceInfoBean.getDeviceId() + "端口:" + ifBean.getIfDescr() + "采集到的速率为:" +  bandwidthNum);
			
			double spd = 0;
			if (speed != null || ifHighSpeed != null) {
				try {
					if(ifHighSpeed != null && !"0".equals(ifHighSpeed.trim())){
						spd = Double.parseDouble(bandwidthNum);
					} else {
						spd = Double.parseDouble(bandwidthNum) / (1000 * 1000);
					}
				} catch (Exception e) {
					logger.error("", e);
				}
			}

			DecimalFormat df = new DecimalFormat("0.000");
			String size = df.format(spd);
			pib.setBandWidth(Float.valueOf(size));
			logger.info("设备id=" + deviceInfoBean.getDeviceId() + "端口:" + ifBean.getIfDescr() + "采集到的端口带宽为" + Float.parseFloat(size) + "M");
			//muyp off 20180113
//			String index = ifIndexMap.get(ifBean.getIfIndex()) == null ? ifBean.getIfIndex() : ifIndexMap.get(ifBean.getIfIndex());
//			if(index==null||index.equals(""))
//				continue;
//			logger.debug("端口解析时端口的索引："+index);
//			pib.setPortIndex(Long.parseLong(index));
/*
			try {
				String entIndex = entAliasLogicalIndexMap.get(index);
				if(entIndex!=null && !entIndex.equals("")){
					pib.setEntAliasLogicalIndex(entIndex);
					//设置光口类别
					//LC、SC、LX、SX、ZX、1310、850、1550、nm
					EntPhysicalEntityBean bean = entMap.get(entIndex);
					if(bean!=null){
						String entPhysicalDescr = bean.getEntPhysicalDescr();
						logger.info("entPhysicalDescr:"+entPhysicalDescr);
						if(entPhysicalDescr.contains("LC")||entPhysicalDescr.contains("SC")||entPhysicalDescr.contains("LX")||
								entPhysicalDescr.contains("SX")||entPhysicalDescr.contains("ZX")||entPhysicalDescr.contains("1310")||
								entPhysicalDescr.contains("850")||entPhysicalDescr.contains("1550")||entPhysicalDescr.contains("nm")){
							pib.setMediaType("fiber");
						}else{
							String entPhysicalSerialNum = bean.getEntPhysicalSerialNum();
							String entPhysicalMfgName = bean.getEntPhysicalMfgName();
							logger.info("entPhysicalSerialNum:"+entPhysicalSerialNum+";entPhysicalMfgName:"+entPhysicalMfgName);
							if((entPhysicalSerialNum!=null && !entPhysicalSerialNum.equals("")) || (entPhysicalMfgName!=null && !entPhysicalMfgName.equals("")) ){
								pib.setMediaType("fiber");
							}else{
								String entPhysicalIsFRU = bean.getEntPhysicalIsFRU();
								logger.info("entPhysicalIsFRU:"+entPhysicalIsFRU);
								if(entPhysicalIsFRU!=null && entPhysicalIsFRU.equals("true")){
									pib.setMediaType("fiber");
								}else{
									pib.setMediaType("Cable");
								}
							}
						}
					}else{
						pib.setMediaType("Cable");
					}
				}else{
					pib.setMediaType("Cable");
				}
			} catch (Exception e) {
				
			}
			try {
				pib.setDot1dBasePort(((NetElementFdbPortRelationBean)fdbPortTable.get(index)).getDot1dBasePort());
			} catch (Exception e) {
				
			}*/
			deviceData.addPort(pib);
		}
	}
	 /* 生成设备信息
	 */
	public void genDeviceInfo() {
		HashMap<String, Object> sysTable = (HashMap<String, Object>) dc.getTable("net_system", true);
		Collection<Object> c = sysTable.values();
		Iterator<Object> it = c.iterator();
		if (it.hasNext()) {
			Object sO = it.next();
			if (sO instanceof NetElementSysBean) {
				NetElementSysBean sys = (NetElementSysBean) sO;
				deviceData.setSysDescr(sys.getSysDescr());
				deviceData.setDeviceName(sys.getSysName());
				String sysObjectId = sys.getSysObjectId();
//通过sysObjectId判断设备归属厂家，这里考虑不需要了
				/*if (sysObjectId == null)
					return;
				logger.debug("DeviceName:"+sys.getSysName()+";sysObjectId:"+sysObjectId);
				DeviceTypeBean dtb = DeviceTypeXMLHelper.deviceTypeMapping.get(sysObjectId);
				
				if (dtb != null) {
					logger.debug(">>>>DeviceName:"+sys.getSysName()+";sysObjectId:"+sysObjectId
							+";dtb.getMainClass().getValue():"+dtb.getMainClass().getValue() 
							+";dtb.getType().getValue():"+dtb.getType().getValue());
					//其设备为思科3750

					deviceData.setFactory(Integer.parseInt(dtb.getFactory().getValue()));
					deviceData.setModel(dtb.getModel());
					if(dtb.getMainClass().getValue().equals("1")||dtb.getMainClass().getValue().equals("2")||dtb.getMainClass().getValue().equals("3"))
						device.setDeviceClass(0);
					else if(dtb.getMainClass().getValue().equals("5"))
						device.setDeviceClass(1);
					else
						device.setDeviceClass(2);
					device.setRoutType(getType(Integer.parseInt(dtb.getMainClass().getValue()),Integer.parseInt(dtb.getType().getValue())));

				} else {
					device.setFactory(this.deviceInfoBean.getFactory());
					device.setModel(this.deviceInfoBean.getModelName());
					device.setDeviceClass(this.deviceInfoBean.getDeviceClass());
					device.setRoutType(this.deviceInfoBean.getDeviceType());
				}*/

			}
		}
	}


	@Override
	public ExecutiveResult execute() {
		logger.debug("设备：" + deviceInfoBean.getDeviceName()+"使用thread[" + Thread.currentThread().getName()+"]执行任务");
		try {
			boolean suFlag = GloableDataArea.su.canSNMP(deviceInfoBean);
			if (!suFlag || deviceInfoBean.getSnmpVersion() == -1){
				logger.error("deviceInfoBean[" + deviceInfoBean.getDeviceName() + "]'s version not in snmp v1 v2");
				logger.error("deviceInfoBean：" + deviceInfoBean);
				throw new Exception(deviceInfoBean.getIPAddress() + "访问SNMP异常；使用thread[" + Thread.currentThread().getName()+"]执行任务");
			}
			logger.debug("device[" + deviceInfoBean.getDeviceName() + "]'s version is:"+deviceInfoBean.getSnmpVersion());
			doInitWork();
			getConfigInfoBySNMP();
			analyseConfigInfo();
			this.setRunFlag(TaskRunFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("执行异常!", e);
			this.setRunFlag(TaskRunFlag.FINAL);
			this.getWorkerResult().setSuccessSize(0);
		} 
		logger.debug("资源采集任务：设备：" + deviceInfoBean.getDeviceName()+"thread[" + Thread.currentThread().getName()+"]执行完成，准备释放线程！");
		return this.getWorkerResult();
	}

	private void doInitWork() {
		//进入初始化则认为设备可采集，因此创建设备数据实例等待回传		
		deviceData = new DeviceData();
		deviceDataHs.put(deviceInfoBean.getDeviceId(),deviceData);
		dc = new DataCache();
		
	}

}
