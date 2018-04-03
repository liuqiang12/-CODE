package com.JH.itBusi.business.flux.worker;

//import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.gathercontrol.worker.RootWorker;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.DateFormate;
import com.JH.dgather.frame.util.OIDHelper;
import com.JH.dgather.frame.util.StringUtil;
import com.JH.itBusi.business.flux.result.FluxGatherResult;
import com.JH.itBusi.comm.db.PortCapBean;
import com.JH.itBusi.globaldata.GloablePara;

public class CapGatherPortOctetsWorker extends RootWorker {
	private Logger logger = Logger.getLogger(CapGatherPortOctetsWorker.class);

	private DeviceInfoBean device = null; // 设备信息
//	private HashMap<Long, String[]> exePortHm = null; // 当前任务需要采集的端口列表，KEY:
														// 端口索引, VALUE:
														// 0位表示端口ID，1位表示端口名称
	//private HashMap<Long, PortCapBean> exePortActive = null; // 当前任务需要采集的端口，在端口信息表的状态，用于判断DOWN告警，KEY:端口索引,
															// VALUE：状态值

	private HashMap<Long, PortCapBean> gatherPortOctetsHm = null; // 采集端口的数据列表,
																	// KEY:
																	// 端口索引，
																	// VALUE：有数据且数据正常的端口列表
	private HashMap<Long, PortCapBean> oldPortOctetsHm = null; // 端口上一次采集的数据列表,
																// KEY:
																// 端口索引，VALUE：上一次数据列表,
																// 数据来自net_cap_port_curr
	private List<PortCapBean> updateStatusPortList = null;//保存需要更新状态的端口
	
	private EventHandle handle = null;
	private TaskBean tb = null;
	private long snmpResposeTimeout = 2 * 1000l;
	private long snmpTimeout = 5 * 60 * 1000l;
	private int retries = 2;

	public CapGatherPortOctetsWorker(ExecutiveController controller, ExecutiveResult workerResult,DeviceInfoBean device, 
			HashMap<Long, PortCapBean> gatherPortOctetsHm,HashMap<Long, PortCapBean> oldgatherPortOctetsHm,List<PortCapBean> updateports, EventHandle handle2) {
		super(controller, workerResult);
		this.device = device;
		this.gatherPortOctetsHm = gatherPortOctetsHm;
		this.oldPortOctetsHm = oldgatherPortOctetsHm;
		this.updateStatusPortList = updateports;
		this.handle = handle2;
		logger.info("端口利用率任务初始化！");
	}

	/**
	 * 初始化相关数据
	 * 
	 * @throws Exception
	 */
	private void doInit() throws Exception {
		String snmpCommunity = this.device.getSnmpCommunity();
		if (snmpCommunity == null || "".equals(snmpCommunity)) {
			throw new Exception("设备: " + this.device.getDeviceName() + "( " + this.device.getIPAddress() + " ) 没有设置SNMP GET团体字！");
		}
		boolean suFlag = GloableDataArea.su.canSNMP(device);
		int alarmtype=-1;
		if(GloableDataArea.getKpiBase()==null)
			alarmtype =55;
			
		// 设备SNMP状态的告警
		logger.info("suFlag："+suFlag+";版本："+device.getSnmpVersion());
		if (!suFlag || device.getSnmpVersion() == -1) {
			WarnEventBean warnBean = handle.addWarnEventBean(device.getRoutId(),alarmtype,-1,5, device.getDeviceName(),
					"设备SNMP无法连接!");
			throw new Exception("设备: " + this.device.getDeviceName() + "( " + this.device.getIPAddress() + ") 访问SNMP异常");
		} else {
			handle.addUnwarnEventBean(device.getRoutId(),alarmtype,-1,"");
		}

	}

	// 根据设备的sysUptime判断，设备是否有重启
	// 判断方式：
	// 1. 设备信息表（net_routinfo)表示sysuptime字段为空或为0，固定做一次端口检索，判断portIndex是否正常
	// 2. 如果当前sysUptime小于上一次的值，表示设备有重启，产生一个事件并做一次端口检索，判断portIndex是否正常
/*	private boolean checkDeviceSysUptime() throws Exception {
		ColumnBean column = null;
		double nowSysUptime = 0;
		boolean isCheckDevicePorts = false;
		HashMap<String, Long> ifDescHm = null; // KEY为端口名，VALUE为端口索引
		HashMap<String, Long> ifNameHm = null; // KEY为端口名，VALUE为端口索引
		HashMap<Long, Integer> ifOperStatusHm = null; // KEY为端口索引，VALUE为端口状态
		HashMap<String, PortCapBean> phyLs = null;
		HashMap<String, PortCapBean> logicLs = null;

		try {
			column = OIDHelper.getColumnBean(device.getFactory(), device.getModelName(), "Net_Element_If_Info", "sysUptime");// 系统运行时间
			if (column != null) {
				OIDbean oidb = GloableDataArea.su.get(device,column.getOid() + ".0", true, PDU.GET,  0, 2 * 1000l, 30 * 1000l);
				if (oidb != null) {
					nowSysUptime = transSysUptime(oidb.getValue());
					if (this.device.getSysUpTime() == 0 || nowSysUptime < this.device.getSysUpTime()) {
						isCheckDevicePorts = true;
					}
					this.device.setSysUpTime(nowSysUptime);
				}
			}
			logger.info("是否重启过："+isCheckDevicePorts);
			// 开始验证所有端口
			if (isCheckDevicePorts) {
				ifDescHm = new HashMap<String, Long>();
				ifNameHm = new HashMap<String, Long>();
				ifOperStatusHm = new HashMap<Long, Integer>();
				phyLs = new HashMap<String, PortCapBean>();
				logicLs = new HashMap<String, PortCapBean>();

				// 第一步，到设备上提取所有端口，利用ifDesc跟ifName
				getPortsInfo(ifDescHm, ifNameHm, ifOperStatusHm);

				// 第二步: 从数据库中读取所有端口信息，物理端口与逻辑端口
				this.capDataUtil.getDevicePortsList(device.getDeviceId(), phyLs, logicLs);
				// 第三步：更新端口索引
				for (Entry<String, PortCapBean> entry : phyLs.entrySet()) {
					PortCapBean port = (PortCapBean) entry.getValue();
					if (ifDescHm.containsKey(port.getPortName())) {
						port.setPortIndex(ifDescHm.get(port.getPortName()));
						if (ifOperStatusHm.containsKey(ifDescHm.get(port.getPortName())))
							port.setPortStatus(ifOperStatusHm.get(ifDescHm.get(port.getPortName())));
					} else if (ifNameHm.containsKey(port.getPortName())) {
						port.setPortIndex(ifNameHm.get(port.getPortName()));
						if (ifOperStatusHm.containsKey(ifDescHm.get(port.getPortName())))
							port.setPortStatus(ifOperStatusHm.get(ifDescHm.get(port.getPortName())));
					}
				}
				for (Entry<String, PortCapBean> entry : logicLs.entrySet()) {
					PortCapBean port = (PortCapBean) entry.getValue();
					if (ifDescHm.containsKey(port.getPortName())) {
						port.setPortIndex(ifDescHm.get(port.getPortName()));
						if (ifOperStatusHm.containsKey(ifDescHm.get(port.getPortName())))
							port.setPortStatus(ifOperStatusHm.get(ifDescHm.get(port.getPortName())));
					} else if (ifNameHm.containsKey(port.getPortName())) {
						port.setPortIndex(ifNameHm.get(port.getPortName()));
						if (ifOperStatusHm.containsKey(ifDescHm.get(port.getPortName())))
							port.setPortStatus(ifOperStatusHm.get(ifDescHm.get(port.getPortName())));
					}
				}
				// 第四步，更新端口相关的所有数据
				this.capDataUtil.updateDatas(this.getTaskObjBean().getTaskid(), device.getDeviceId(), phyLs, logicLs);
			}
		} catch (Exception e) {
			logger.error(" ", e);
		} finally {
			column = null;
			nowSysUptime = 0;
			ifDescHm = null;
			ifNameHm = null;
			phyLs = null;
			logicLs = null;
		}
		return isCheckDevicePorts;
	}*/



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
	 * 数据采集
	 * 
	 * @throws Exception
	 */
	private void doGather() throws Exception {
		if (gatherPortOctetsHm != null && gatherPortOctetsHm.size() > 0) {
			// STEP 1: 提取需要采集指标
			int factory = this.device.getFactory();
			String model = this.device.getModelName();
			ColumnBean ifInOctets = OIDHelper.getColumnBean(factory, model, "Net_Element_If_Info", "ifHCInOctets");
			ColumnBean ifOutOctets = OIDHelper.getColumnBean(factory, model, "Net_Element_If_Info", "ifHCOutOctets");
			ColumnBean ifOperStatus = OIDHelper.getColumnBean(factory, model, "Net_Element_If_Info", "ifOperStatus");
			ColumnBean ifAdminStatus = OIDHelper.getColumnBean(factory, model, "Net_Element_If_Info", "ifAdminStatus");
			if (ifInOctets == null || ifOutOctets == null || ifOperStatus == null || ifAdminStatus == null) {
				throw new Exception("无法到 TableColumnOID_v3.xml中提取MIB OID！提取的OID有：ifHCInOctets, ifHCOutOctets, ifOperStatus, ifAdminStatus，ifInNUcastPkts");
			}
			
			// STEP 2: 将任务需要执行端口，利用SNMP GET方式采集数据
			long portIndex = 0l;
			OIDbean oidb = null;

			boolean bCorrect = true;
			for (Entry<Long, PortCapBean> entry : gatherPortOctetsHm.entrySet()) {
				bCorrect = true;
				PortCapBean gatherPort =entry.getValue();
				portIndex = gatherPort.getPortIndex();
				// 采集端口状态
				oidb = GloableDataArea.su.get(device,ifOperStatus.getOid() + "." + portIndex,  true, PDU.GET,  5, 2 * 1000l, 30 * 1000l);
				OIDbean adminStatusOID = GloableDataArea.su.get(device,ifAdminStatus.getOid() + "." + portIndex,  true, PDU.GET,  5, 2 * 1000l, 30 * 1000l);
				
				// 未采集到端口状态，则此次数据无效
				logger.info("获取的对象："+oidb);
				logger.info("获取的对象："+adminStatusOID);
				if (oidb == null || adminStatusOID == null 
						|| !oidb.isSuccess() || !adminStatusOID.isSuccess()) {
					bCorrect = false;
					gatherPort.setIfInnucastPkts(-1);
					gatherPort.setInputFluxCount(-1L);
					gatherPort.setOutputFluxCount(-1L);
				} else {
					if (!StringUtil.isNumberString(oidb.getValue())) {
						bCorrect = false;
					} else {
						gatherPort.setPortStatus(Integer.parseInt(oidb.getValue()));
					}
					
					if (!StringUtil.isNumberString(adminStatusOID.getValue())) {
						bCorrect = false;
					} else {
						gatherPort.setAdminStatus(Integer.parseInt(adminStatusOID.getValue()));
					}
					gatherPort.setDaoFlag(0);//默认为0
					// 如果端口DOWN，则不需要采集后续指标
					if (gatherPort.getAdminStatus() != 1) {
						gatherPort.setDaoFlag(4);
						gatherPort.setIfInnucastPkts(-1);
						gatherPort.setInputFluxCount(-1L);
						gatherPort.setOutputFluxCount(-1L);
						gatherPort.setOutputFlux(-1);
						gatherPort.setInputFlux(-1);
					} else {
						// 采集入流量
						oidb = GloableDataArea.su.get(device,ifInOctets.getOid() + "." + portIndex, true, PDU.GET,  5, 2 * 1000l, 30 * 1000l);
						logger.info("获取的对象："+oidb);
						if (oidb == null || !StringUtil.isNumberString(oidb.getValue())) {
							gatherPort.setInputFluxCount(-1L);
							gatherPort.setInputFlux(-1);
							gatherPort.setDaoFlag(3);
							logger.error(device.getDeviceIp()+"设备的"+portIndex+"端口数据入流量没采集到。");
						} else {
							gatherPort.setInputFluxCount(Long.parseLong(oidb.getValue()));
							gatherPort.setRecordTime(new java.util.Date());
						}

						// 采集出流量
						oidb = GloableDataArea.su.get(device,ifOutOctets.getOid() + "." + portIndex, true, PDU.GET,  5, 2 * 1000l, 30 * 1000l);
						logger.info("获取的对象："+oidb);
						if (oidb == null || !StringUtil.isNumberString(oidb.getValue())) {
							gatherPort.setOutputFluxCount(-1L);
							gatherPort.setOutputFlux(-1);
							gatherPort.setDaoFlag(3);
							logger.error(device.getDeviceIp()+"设备的"+portIndex+"端口数据出流量没采集到。");
						} else {
							try{
							gatherPort.setOutputFluxCount(Long.parseLong(oidb.getValue()));
							}catch(Exception e){logger.error(e);}
						}

					}
				}
					if (bCorrect)
					gatherPortOctetsHm.put(portIndex, gatherPort);
			}
		}
	}

	/**
	 * 数据分析
	 * 
	 * @throws Excepiton
	 */
	private void doAnalyse() throws Exception {
		PortCapBean portCapBean = null;
		PortCapBean prePortCapBean = null;
		long portIndex = 0;
		double portBandWidth = 0; // 转成Byte, 数据存储的是Mbps
		long CurRecordTimeL = (new Date()).getTime();
		// 分析数据流量
		// 将采集并数据正确的端口流量数据与上一次数据换算得流量
		logger.info("开始分析！");
		for (Entry<Long, PortCapBean> entry : gatherPortOctetsHm.entrySet()) {
			portCapBean = entry.getValue();
			if((portCapBean.getAdminStatus()==-1)||(portCapBean.getPortStatus()==-1))//端口状态都未采集到
				continue;
			portCapBean.setRecordTimeL(CurRecordTimeL);//写入采集时间
			portIndex = entry.getKey();
			portBandWidth = portCapBean.getBandWidth() * 1024 * 1024; // 转换成bps
			// 上一次出/入流量数据
			if(oldPortOctetsHm==null)
				prePortCapBean = null;
			else
				prePortCapBean = oldPortOctetsHm.get(portIndex);
		//	PortCapBean dbPortCapBean = this.exePortActive.get(portIndex);
			// 判断端口是否发送DOWN告警
			//如果端口当前状态与数据库的状态不一致，则更新数据库数据
//			if(portCapBean.getAdminStatus() != dbPortCapBean.getAdminStatus() || 
//					portCapBean.getPortStatus() != dbPortCapBean.getPortStatus()){
//				updateStatusPortList.add(portCapBean);
//			}
			//如果端口当前操作状态与数据库不一致，则需要生成告警
			if ((prePortCapBean!=null)&&(portCapBean.getPortStatus() != prePortCapBean.getPortStatus())){
				this.updateStatusPortList.add(portCapBean);
				if (portCapBean.getPortStatus() != PortCapBean.IFSTATUS_UP) {
					WarnEventBean warnBean = handle.addWarnEventBean(this.device.getDeviceId(), GloableDataArea.getKpiBase().get("portstatus"), portCapBean.getPortId(), this.device.getDeviceId(),
							device.getDeviceName(), device.getIPAddress());
					handle.addWarnEventBean(device.getRoutId(),  GloableDataArea.getKpiBase().get("portstatus"),portCapBean.getPortId(),5, device.getDeviceName(),
							portCapBean.getPortName() + "端口DOWN");
					warnBean.setAlarmTime(portCapBean.getRecordTime());
					warnBean.setAlarmInfo(portCapBean.getPortName() + "端口DOWN");
					warnBean.setAlarmlevel(5);
					warnBean.setPortId(portCapBean.getPortId());
					warnBean.setAlarmLevelId(0);
				} else {
					if (portCapBean.getPortStatus() == PortCapBean.IFSTATUS_UP) { // 端口UP状态
						this.createUnwarnEventBean(device.getDeviceId(), "portstatus", portCapBean.getPortId(), device.getDeviceId(), device.getDeviceName(), device.getIPAddress(),
								portCapBean.getPortStatus());
					}
				}
			}
			//如果端口当前管理状态不是up，则不继续处理
			if(portCapBean.getAdminStatus() != PortCapBean.IFSTATUS_UP){
				portCapBean.setDaoFlag(3);
				continue;
			}
			// STEP2： 分析端口的出/入流量
			// 判断是否有上一次数据，如果没有，则表示，当前采集数据为第一次数据

			if (prePortCapBean==null) {
				portCapBean.setInputFlux(-1);
				portCapBean.setOutputFlux(-1);
				portCapBean.setDaoFlag(1);
			} else {
				// 当前出/入流量数据
				Long curOutputFluxCount = portCapBean.getOutputFluxCount();
				Long curInputFluxCount = portCapBean.getInputFluxCount();
				Long preOutputFluxCount = prePortCapBean.getOutputFluxCount();
				Long preInputFluxCount = prePortCapBean.getInputFluxCount();
				long preGatherTimeL = prePortCapBean.getRecordTimeL();
				if ((curOutputFluxCount < preOutputFluxCount)||(curInputFluxCount < preInputFluxCount) ){
					logger.error("[非error], 出或者入流量数据产生回滚！设备: " + this.device.getDeviceName() + ", 端口名: " + portCapBean.getPortName());
					portCapBean.setDaoFlag(2);
					portCapBean.setInputFlux(-1);
					portCapBean.setOutputFlux(-1);
				
				}
				 else {
				// 计算两次采集点的流量及时间
				Long inputFluxCount = curInputFluxCount - preInputFluxCount;
				Long outputFluxCount = curOutputFluxCount - preOutputFluxCount;
				long gatherTime = (CurRecordTimeL-preGatherTimeL)/1000;
				// 计算两次采集的瞬时流量单位为 Byte
				long inputFlux = inputFluxCount / gatherTime ;
				long outputFlux = outputFluxCount / gatherTime;
				// 判断是否大于带宽，如果大于，则只修改数据库的SNMP数据
				if (inputFlux * 8 > portBandWidth || outputFlux * 8 > portBandWidth) {
					portCapBean.setDaoFlag(2);
					portCapBean.setInputFlux(-1);
					portCapBean.setOutputFlux(-1);
					logger.debug("=======================================================");
					logger.debug("[非error], 数据大于带宽！设备: " + this.device.getDeviceName() + ", 端口名: " + portCapBean.getPortName());
					logger.debug("本次入流量采集值(Byte): " + curInputFluxCount + ", 采集时间: " + DateFormate.dateToStr(portCapBean.getRecordTime(), "yyyy-MM-dd HH:mm:ss"));
					logger.debug("本次出流量采集值(Byte): " + curOutputFluxCount );
					logger.debug("上次入流量采集值(Byte): " + preInputFluxCount + ", 采集时间: " + DateFormate.dateToStr(prePortCapBean.getRecordTime(), "yyyy-MM-dd HH:mm:ss"));
					logger.debug("上次出流量采集值(Byte): " + preOutputFluxCount );
					logger.debug("端口带宽(bps): " + portBandWidth);
					logger.debug("入流量(bps): " + inputFlux * 8);
					logger.debug("出流量(bps): " + outputFlux * 8);
				} else {
					portCapBean.setInputFlux((long) inputFlux);
					portCapBean.setOutputFlux((long) outputFlux);
					portCapBean.setDaoFlag(0);
					if (portBandWidth <= 0)
						continue;

					double inputUsage = inputFlux * 8 / portBandWidth * 100; // 入利用率
					double outputUsage = outputFlux * 8 / portBandWidth * 100; // 出利用率
					// 保留2位小数
					String floatStr = new DecimalFormat("#########.##").format(inputUsage);
					inputUsage = Double.valueOf(floatStr);
					floatStr = new DecimalFormat("#########.##").format(outputUsage);
					outputUsage = Double.valueOf(floatStr);
				}
			 }
			}
		}
		
	}

	private String getLevelDetailByLevel(int level) {
		String levelDetailStr = null;
		switch (level) {
		case 0:
			levelDetailStr = "轻微";
			break;
		case 1:
			levelDetailStr = "一般";
			break;
		case 2:
			levelDetailStr = "严重";
			break;
		default:
			logger.error("日常巡检：根据告警级别数字未能找到对应的级别文字描述");
			levelDetailStr = "日常巡检：根据告警级别数字未能找到对应的级别文字描述";
			break;
		}

		return levelDetailStr;
	}

	@Override
	public ExecutiveResult execute() {
		FluxGatherResult capGatherResult = (FluxGatherResult) this.getWorkerResult();
		try {
			doInit(); // 初始化
			doGather(); // 数据采集
			doAnalyse(); // 数据分析
			//oldPortOctetsHm = gatherPortOctetsHm;//将最新数据复制到GloablePara.PortOctets_curr_Hs
			//doUpdatePortStauts(updateStatusPortList); // 数据保存
		} catch (Exception e) {
			logger.error("执行异常！", e);
			capGatherResult.setSuccessSize(0);
			capGatherResult.setRunTask(TaskRunFlag.FINAL);
		}
		return capGatherResult;
	}

//	private void doUpdatePortStauts(List<PortCapBean> updateStatusPortList) {
//		this.capDataUtil.updatePortStatus(updateStatusPortList);
//	}

	private void createUnwarnEventBean(int objId, String kpiKey, int portId, int deviceId, String deviceName, String deviceIp, int capValue) {
		try {
			handle.addUnwarnEventBean(objId, GloableDataArea.getKpiBase().get(kpiKey), portId,"");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void createUnwarnEventBean(int objId, String kpiKey, int portId, int deviceId, String deviceName, String deviceIp, double capValue) {
		try {
			handle.addUnwarnEventBean(objId, GloableDataArea.getKpiBase().get(kpiKey), portId,  "");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
 
}
