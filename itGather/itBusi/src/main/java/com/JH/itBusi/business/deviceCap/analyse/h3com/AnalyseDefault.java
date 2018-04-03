package com.JH.itBusi.business.deviceCap.analyse.h3com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.business.deviceCap.db.bean.FanCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.PwrCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.TemperatureCapBean;

public class AnalyseDefault {
	Logger logger = Logger.getLogger(AnalyseDefault.class.getName());
	public HashMap<String, HashMap<String, Object>> lsDataStore;
	
	public AnalyseDefault(HashMap<String, HashMap<String, Object>> lsDataStore, ArrayList<Integer> cpu, ArrayList<Integer> mem,
			ArrayList<FanCapBean> lsFan, ArrayList<PwrCapBean> lsPwr, ArrayList<TemperatureCapBean> lsTmperature, TaskBean tb) {
		this.lsDataStore = lsDataStore;
		if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("ProcessValue")){
			try {
				doCpuAnalyse(cpu);
			} catch (Exception e) {
				logger.error("日常性能巡检,CPU信息分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("UsedValue")){
			try {
				doMemAnalyse(mem);
			} catch (Exception e) {
				logger.error("日常性能巡检,内存信息分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("PWRStatus")){
			try {
				doPwrAnalyse(lsPwr);
			} catch (Exception e) {
				logger.error("日常性能巡检,电源分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("FanStatus")){
			try {
				doFanAnalyse(lsFan);
			} catch (Exception e) {
				logger.error("日常性能巡检,风扇分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("Temperature")){
			try {
				doTemperatureAnalyse(lsTmperature);
			} catch (Exception e) {
				logger.error("日常性能巡检,温度分解出错", e);
			}
		}
	}
	
	/*
	 * CPU性能分析
	 */
	public int doCpuAnalyse(ArrayList<Integer> cpu) {
		HashMap<String, Object> hsCpuprocess = lsDataStore.get("cpuprocess");// CPU流量
		int cpuprocess = 0;
		Integer tmpCpu = 0;
		if (hsCpuprocess == null) {
			//logger.error("提取cpuprocess为空");
			return -1;
		}
		for (Entry<String, Object> enc : hsCpuprocess.entrySet()) {
			try {
				cpuprocess = Integer.parseInt((String) enc.getValue());
				if (cpuprocess > tmpCpu)
					tmpCpu = cpuprocess;
			} catch (Exception e) {
				logger.error("日常性能巡检,CPU分解出错+(String) enc.getValue()", e);
				return -1;
			}
			
		}
		cpu.add(tmpCpu);
		return tmpCpu;
		
	}
	
	/*
	 * 内存分析 提取最大值
	 */
	public int doMemAnalyse(ArrayList<Integer> mem) {
		HashMap<String, Object> hsMemUsed = lsDataStore.get("memoryPoolUsed");// 内存利用量
		int memoryused;
		Integer tmpMem = -1;
		if (hsMemUsed == null)
			return -1;
		for (Entry<String, Object> ent : hsMemUsed.entrySet()) {
			memoryused = Integer.parseInt((String) ent.getValue());
			if (memoryused > tmpMem)
				tmpMem = memoryused;
		}
		if (tmpMem >= 100) {
			tmpMem = 100;
		}
		mem.add(tmpMem);
		return tmpMem;
	}
	
	/*
	 * 电源分析
	 */
	public ArrayList<PwrCapBean> doPwrAnalyse(ArrayList<PwrCapBean> lsPwr) {
		HashMap<String, Object> hsPwr = lsDataStore.get("supplyState");// 状态
		if (hsPwr == null)
			return null;
		int index;
		for (Entry<String, Object> ent : hsPwr.entrySet()) {
			PwrCapBean pwr = new PwrCapBean();
			try {
				index = Integer.parseInt(ent.getKey());
			} catch (Exception e) {
				index=0;
			}
			pwr.setIndex(index);
			pwr.setStatus(Integer.parseInt((String) ent.getValue()));
			lsPwr.add(pwr);
		}
		return lsPwr;
	}
	
	/*
	 * 风扇分析
	 */
	public ArrayList<FanCapBean> doFanAnalyse(ArrayList<FanCapBean> lsFan) {
		HashMap<String, Object> hsFan = lsDataStore.get("fanStatus");// 风扇
		if (hsFan == null)
			return null;
		int index;
		for (Entry<String, Object> ent : hsFan.entrySet()) {
			FanCapBean fan = new FanCapBean();
			try {
				index = Integer.parseInt(ent.getKey());
			} catch (Exception e) {
				index=0;
			}
			fan.setIndex(index);
			fan.setStatusValue(Integer.parseInt((String) ent.getValue()));
			lsFan.add(fan);
		}
		return lsFan;
	}
	
	/*
	 * 温度分析
	 */
	public ArrayList<TemperatureCapBean> doTemperatureAnalyse(ArrayList<TemperatureCapBean> lsTemperature) {
		HashMap<String, Object> hsTemperature = lsDataStore.get("temperature");// 温度
		if (lsTemperature == null)
			return null;
		int index;
		for (Entry<String, Object> ent : hsTemperature.entrySet()) {
			TemperatureCapBean temperature = new TemperatureCapBean();
			try {
				index = Integer.parseInt(ent.getKey());
			} catch (Exception e) {
				index=0;
			}
			temperature.setIndex(index);
			temperature.setTemperatureValue(Integer.parseInt((String) ent.getValue()));
			lsTemperature.add(temperature);
		}
		return lsTemperature;
	}
}
