package com.JH.itBusi.business.deviceCap.analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.itBusi.business.deviceCap.db.bean.FanCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.PortCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.PwrCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.TemperatureCapBean;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;



public class CapAnalyse {
	Logger logger = Logger.getLogger(CapAnalyse.class.getName());

	public CapAnalyse(DeviceCapData deviceCapData, HashMap<String, HashMap<String, Object>> lsTmpDataStore, TaskBean tb) {

		int factory = deviceCapData.getDevice().getFactory();
		// 存放cpu巡检分析结果
		ArrayList<Integer> cpu = new ArrayList<Integer>();
		// 存放mem巡检分析结果
		ArrayList<Integer> mem = new ArrayList<Integer>();
		// 存放风扇巡检分析结果
		ArrayList<FanCapBean> lsFan = new ArrayList<FanCapBean>();
		// 存放电源巡检分析结果
		ArrayList<PwrCapBean> lsPwr = new ArrayList<PwrCapBean>();
		// 存放温度巡检分析结果
		ArrayList<TemperatureCapBean> lsTmperature = new ArrayList<TemperatureCapBean>();
		// 存放端口巡检分析结果
		HashMap<String, PortCapBean> hsPortCapData = new HashMap<String, PortCapBean>();
		// 目前仅根据厂家进行区别分析，应该细化到设备型号，先根据型号分析，如果没有相应的则采用default
		switch (factory) {
		// 华为
		case 0:
			new com.JH.itBusi.business.deviceCap.analyse.huawei.AnalyseDefault(lsTmpDataStore, cpu, mem, lsFan, lsPwr, lsTmperature, tb);
			break;
		// 思科
		case 1:
			new com.JH.itBusi.business.deviceCap.analyse.cisco.AnalyseDefault(lsTmpDataStore, cpu, mem, lsFan, lsPwr, lsTmperature, tb);
			break;
		// h3com
		case 61:
			new com.JH.itBusi.business.deviceCap.analyse.h3com.AnalyseDefault(lsTmpDataStore, cpu, mem, lsFan, lsPwr, lsTmperature, tb);
			break;
		default:
			new com.JH.itBusi.business.deviceCap.analyse.AnalyseDefault(lsTmpDataStore, cpu, mem, lsFan, lsPwr, lsTmperature, tb);
			break;
		}
		if (cpu.size() > 0)
			deviceCapData.setCpu(cpu.get(0));
		if (lsPwr.size() > 0)
			deviceCapData.setHsPwrStatus(lsPwr);
		if (lsFan.size() > 0)
			deviceCapData.setHsFanStatus(lsFan);
		if (lsTmperature.size() > 0)
			deviceCapData.setHsTemperature(lsTmperature);
		if (mem.size() > 0)
			deviceCapData.setMem(mem.get(0));
			logger.info("-------------------------》hsPortCapData size:  " + hsPortCapData.size());
		}
}
