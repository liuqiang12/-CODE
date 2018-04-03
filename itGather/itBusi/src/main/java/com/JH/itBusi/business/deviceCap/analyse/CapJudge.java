package com.JH.itBusi.business.deviceCap.analyse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;
import com.JH.itBusi.business.deviceCap.db.CapDataUtil;
import com.JH.itBusi.business.deviceCap.db.bean.TemperatureCapBean;
import com.JH.itBusi.comm.AlarmLevelUtil;
import com.JH.itBusi.comm.db.KPIAlarmLevelConfig;



public class CapJudge {
	private ArrayList<DeviceCapData> lsDeviceCapData;
	private TaskBean tb;
	private ArrayList<KPIAlarmLevelConfig> alcList;// 用来存放所有的KPI指标,有可能有0,1,2 3种
	// muyp modify 2011-12-08 用来存放广播包告警定义，由于广播包同属于端口但又又不同告警定义因此做特殊处理
	private ArrayList<KPIAlarmLevelConfig> alcList_broadPkt;
	Logger logger = Logger.getLogger(CapJudge.class.getName());
	private EventHandle eventHandle;
	private CapDataUtil capDataUitl;
	private String execTime;
	private AlarmLevelUtil alutil = null;

	public CapJudge(ArrayList<DeviceCapData> lsDeviceCapData, TaskBean tb, Collection<KPIAlarmLevelConfig> collection, CapDataUtil dataUitl, EventHandle handle, String execTime) {
		this.lsDeviceCapData = lsDeviceCapData;
		this.tb = tb;
		this.alcList = (ArrayList<KPIAlarmLevelConfig>) collection;
		this.capDataUitl = dataUitl;
		this.eventHandle = handle;
		this.execTime = execTime;
		alutil = new AlarmLevelUtil();
	}

	public void doCapJudge() {
		try {
			KPIAlarmLevelConfig klc = null;
			Iterator<DeviceCapData> it = lsDeviceCapData.iterator();
			DeviceCapData deviceCap;
			StringBuffer alarmInfo ;
			logger.debug("获取关键字："+GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()));
			if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("ProcessValue")){
				while (it.hasNext()) {
					deviceCap = it.next();
					klc = alutil.alarmLevelJudge(deviceCap.getCpu(), alcList, deviceCap.getDeviceId());
					if (klc != null) {
						alarmInfo = new StringBuffer("目前CPU使用率为").append(deviceCap.getCpu()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append(",处于告警门限[").append(klc.getThresholdLowLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append("-");
						if (klc.getThresholdLimit() == 0)
							alarmInfo.append("无上限");
						else
							alarmInfo.append(klc.getThresholdLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId()));
						alarmInfo.append("]之间,告警级别为").append(klc.getLevelDetail());

						WarnEventBean warnBean = eventHandle.addWarnEventBean(deviceCap.getDeviceId(), GloableDataArea.getKpiBase().get("processvalue"), 0, deviceCap.getDeviceId(), deviceCap.getDevice().getDeviceName(), deviceCap.getDevice().getIPAddress());
						warnBean.setAlarmTime(execTime);
						warnBean.setAlarmInfo(alarmInfo.toString());
						warnBean.setAlarmlevel(klc.getAlarmLevel());
						warnBean.setAlarmLevelId(klc.getID());

					} else {
						this.createUnwarnEventBean(deviceCap.getDeviceId(), "processvalue", -1, deviceCap.getDevice().getIPAddress()+"CPU利用率："+ deviceCap.getCpu());

					}
				}
			}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("UsedValue")){
				while (it.hasNext()) {
					deviceCap = it.next();
					klc = alutil.alarmLevelJudge(deviceCap.getMem(), alcList, deviceCap.getDeviceId());
					if (klc != null) {

						alarmInfo = new StringBuffer("目前内存使用率为").append(deviceCap.getMem()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append(",处于告警门限[").append(klc.getThresholdLowLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append("-");
						if (klc.getThresholdLimit() == 0)
							alarmInfo.append("无上限");
						else
							alarmInfo.append(klc.getThresholdLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId()));
						alarmInfo.append("]之间,告警级别为").append(klc.getLevelDetail());

						WarnEventBean warnBean = eventHandle.addWarnEventBean(deviceCap.getDeviceId(), GloableDataArea.getKpiBase().get("usedvalue"), 0, deviceCap.getDeviceId(), deviceCap.getDevice().getDeviceName(), deviceCap.getDevice().getIPAddress());
						warnBean.setAlarmTime(execTime);
						warnBean.setAlarmInfo(alarmInfo.toString());
						warnBean.setAlarmlevel(klc.getAlarmLevel());
						warnBean.setAlarmLevelId(klc.getID());
					} else {
						this.createUnwarnEventBean(deviceCap.getDeviceId(), "usedvalue", -1, deviceCap.getDevice().getIPAddress()+"内存利用率："+deviceCap.getMem());

					}
				}
			}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("PWRStatus")){
				// power TODO 没找到告警方法
			}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("FanStatus")){
				// fan TODO 没找到告警方法
			}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("Temperature")){
				while (it.hasNext()) {
					deviceCap = it.next();
					ArrayList<TemperatureCapBean> list = deviceCap.getTemperature();
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getTemperatureValue() >= 10) {
								klc = alutil.alarmLevelJudge(list.get(i).getTemperatureValue(), alcList, deviceCap.getDeviceId());
								if (klc != null) {

									alarmInfo = new StringBuffer("目前温度为").append(list.get(i).getTemperatureValue()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append(",处于告警门限[").append(klc.getThresholdLowLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId())).append("-");
									if (klc.getThresholdLimit() == 0)
										alarmInfo.append("无上限");
									else
										alarmInfo.append(klc.getThresholdLimit()).append(GloableDataArea.getKpiBaseUnit(tb.getKpiId()));
									alarmInfo.append("]之间,告警级别为").append(klc.getLevelDetail());

									WarnEventBean warnBean = eventHandle.addWarnEventBean(deviceCap.getDeviceId(), GloableDataArea.getKpiBase().get("temperature"), -1, deviceCap.getDeviceId(), deviceCap.getDevice().getDeviceName(),alarmInfo.toString());
									warnBean.setAlarmTime(execTime);
									warnBean.setAlarmInfo(alarmInfo.toString());
									warnBean.setAlarmlevel(klc.getAlarmLevel());
									warnBean.setAlarmLevelId(klc.getID());
								} else {
									this.createUnwarnEventBean(deviceCap.getDeviceId(), "temperature", -1, deviceCap.getDevice().getIPAddress()+"温度："+list.get(i).getTemperatureValue());

								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("日常性能解析异常：", e);
		}
	}

	/**
	 * 本次无告警 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-6-13
	 * @param objId
	 * @param kpiKey
	 * @param portName
	 * @param deviceId
	 */
	private void createUnwarnEventBean(int objId, String kpiKey, int portId, String info) {
		try {
			eventHandle.addUnwarnEventBean(objId, GloableDataArea.getKpiBase().get(kpiKey), portId, "");
		} catch (Exception e) {

		}
	}


}
