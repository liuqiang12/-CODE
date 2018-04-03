package com.JH.itBusi.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.comm.db.KPIAlarmLevelConfig;
/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2013-9-12
 */

public class AlarmLevelUtil {
	public Logger logger = Logger.getLogger(AlarmLevelUtil.class.getName());
	public KPIAlarmLevelConfig alarmLevelJudge(float value, List<KPIAlarmLevelConfig> list) {
		return alarmLevelJudge(value, list,null);
	}
	/**
	 * 判断采集值是否在设定阀值内
	 * 
	 * @param value
	 *            采集值
	 * @param deviceID 
	 * @return 带告警级别文字描述的KPIAlarmLevelConfig
	 */
	public KPIAlarmLevelConfig alarmLevelJudge(float value, List<KPIAlarmLevelConfig> list, Integer deviceID) {
		KPIAlarmLevelConfig kpiAlarmLevelConfig = null;

		// 值为-1表明未采集到、约定俗成
		if (value < 0 ) {
			return kpiAlarmLevelConfig;
		}

		if(list==null || list.size()==0){
			return kpiAlarmLevelConfig;
		}
		
		boolean hasAlarmForDevice = false;
		if(deviceID != null){
			hasAlarmForDevice = hasAlarmLevelForThisDevice(list,deviceID);
		}
		for (KPIAlarmLevelConfig kpiAlarmLevelCfg : list) {
			boolean isSkip = false;
			if (hasAlarmForDevice) {
				// 如果此阀值不是当前设备的，则跳过
				if (kpiAlarmLevelCfg.getDeviceID() == null
						|| !kpiAlarmLevelCfg.getDeviceID().equals(deviceID)) {
					isSkip = true;
				}
			} else {
				// 如果此阀值是设备特有的，则跳过
				if (kpiAlarmLevelCfg.getDeviceID() != null) {
					isSkip = true;
				}
			}

			if (isSkip) {
				continue;
			}
			
			boolean isAlarm = false;
			// 阀值上限
			long thresholdLimit = kpiAlarmLevelCfg.getThresholdLimit();
			// 阀值下限
			long thresholdLowLimit = kpiAlarmLevelCfg.getThresholdLowLimit();
			// 阀值上限为0、即未设置上限
			if (thresholdLimit == 0) {
				// 采集值大于等于阀值下限,告警
				if (value >= thresholdLowLimit) {
					isAlarm = true;
				}
			} else {
				// 采集值在阀值区间
				if (thresholdLowLimit <= value && value <= thresholdLimit) {
					isAlarm = true;
				}
			}

			if (isAlarm) {
				kpiAlarmLevelCfg.setLevelDetail(getLevelDetailByLevel(kpiAlarmLevelCfg.getAlarmLevel()));
				kpiAlarmLevelConfig = new KPIAlarmLevelConfig(kpiAlarmLevelCfg);
				return kpiAlarmLevelConfig;
			}
		}

		return kpiAlarmLevelConfig;
	}
	
	private boolean hasAlarmLevelForThisDevice(List<KPIAlarmLevelConfig> list, Integer deviceID) {
		for (KPIAlarmLevelConfig kpiAlarmLevelCfg : list) {
			if (kpiAlarmLevelCfg.getDeviceID() != null
					&& kpiAlarmLevelCfg.getDeviceID().equals(deviceID)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 根据告警级别数字返回告警级别文字描述
	 * 
	 * @param level
	 *            告警级别，从低到高分为0,1,2级（0=轻微；1=一般；2=严重）
	 * @return
	 */
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
	
	public void eventInfo(EventHandle eventHandle, KPIAlarmLevelConfig klc, String modelName, double currValue, int kpiId, String kpiKey, int deviceId, String deviceName, String deviceIp,
			int portId, String execTime) {
		if (klc != null) {
			StringBuffer alarmInfo = new StringBuffer("目前" + modelName + "为").append(currValue).append(GloableDataArea.getKpiBaseUnit(kpiId)).append(",处于告警门限[").append(klc.getThresholdLowLimit())
					.append(GloableDataArea.getKpiBaseUnit(kpiId)).append("-");
			if (klc.getThresholdLimit() == 0)
				alarmInfo.append("无上限");
			else
				alarmInfo.append(klc.getThresholdLimit()).append(GloableDataArea.getKpiBaseUnit(kpiId));
			alarmInfo.append("]之间,告警级别为").append(klc.getLevelDetail());

			WarnEventBean warnBean = eventHandle.addWarnEventBean(deviceId, GloableDataArea.getKpiBase().get(kpiKey), 0, deviceId, deviceName,alarmInfo.toString());
			warnBean.setAlarmTime(execTime);
			warnBean.setAlarmInfo(alarmInfo.toString());
			warnBean.setAlarmlevel(klc.getAlarmLevel());
			warnBean.setAlarmLevelId(klc.getID());
		} else {
			try {
				eventHandle.addUnwarnEventBean(deviceId, GloableDataArea.getKpiBase().get(kpiKey), portId, deviceIp+":"+ currValue);
			} catch (Exception e) {

			}
		}
	}
	/*
	public static void main(String[] args) {
		List<KPIAlarmLevelConfig> list = new ArrayList<KPIAlarmLevelConfig>();
		KPIAlarmLevelConfig c = new KPIAlarmLevelConfig();
		c.setDeviceID(1);
		c.setAlarmLevel(1);
		c.setThresholdLimit(20);
		c.setThresholdLowLimit(13);
		list.add(c);
		
		c = new KPIAlarmLevelConfig();
		c.setDeviceID(null);
		c.setAlarmLevel(2);
		c.setThresholdLimit(10);
		c.setThresholdLowLimit(1);
		list.add(c);
		
		System.out.println(new AlarmLevelUtil().alarmLevelJudge(2, list, 1));
		
	}*/
}
