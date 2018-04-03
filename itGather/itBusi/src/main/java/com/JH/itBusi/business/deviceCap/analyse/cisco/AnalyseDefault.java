package com.JH.itBusi.business.deviceCap.analyse.cisco;

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

	public AnalyseDefault(HashMap<String, HashMap<String, Object>> lsDataStore, ArrayList<Integer> cpu, ArrayList<Integer> mem, ArrayList<FanCapBean> fan, ArrayList<PwrCapBean> pwr,
			ArrayList<TemperatureCapBean> tmperature, TaskBean tb) {
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
				doPwrAnalyse(pwr);
			} catch (Exception e) {
				logger.error("日常性能巡检,电源分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("FanStatus")){
			try {
				doFanAnalyse(fan);
			} catch (Exception e) {
				logger.error("日常性能巡检,风扇分解出错", e);
			}
		}else if(GloableDataArea.getKpiBaseIdKey().get(tb.getKpiId()).equalsIgnoreCase("Temperature")){
			try {
				doTemperatureAnalyse(tmperature);
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
		int cpunumber = 0;
		long cpuprocessl = 0;
		Integer tmpCpu = -1;
		if (hsCpuprocess == null)
			return -1;
		for (Entry<String, Object> enc : hsCpuprocess.entrySet()) {
			try {
				cpuprocessl += Long.parseLong((String) enc.getValue());
				cpunumber += 1;
			} catch (Exception e) {
				logger.error("CPU性能异常", e);
				return -1;
			}

		}
		if (cpunumber > 0) {
			tmpCpu = Math.round(cpuprocessl / cpunumber);
			if (tmpCpu >= 100)
				tmpCpu = 100;
			cpu.add(tmpCpu);
		}
		return tmpCpu;

	}

	/*
	 * 内存分析,以前仅处理满足isProcessor（）这个条件的内存，现在修改为所有内存加起来处理
	 */
	public int doMemAnalyse(ArrayList<Integer> mem) {
		HashMap<String, Object> hsMemoryUtilization = lsDataStore.get("memoryUtilization");// 内存利用率
		if (hsMemoryUtilization == null || hsMemoryUtilization.size() == 0) {
			HashMap<String, Object> hsMemUsed = lsDataStore.get("memoryPoolUsed");// 内存利用量
			HashMap<String, Object> hsMemFree = lsDataStore.get("memoryPoolFree");// 内存利用量
			HashMap<String, Object> hsMemName = lsDataStore.get("memoryPoolName");
			long memoryusedl = 0;
			long memoryfreel = 0;
			int memoryprocess = 0;
			if ((hsMemUsed == null) || (hsMemFree == null) || (hsMemName == null))
				return -1;

			
			for (Entry<String, Object> ent : hsMemName.entrySet()) {
				String index = ent.getKey();
				// if(isProcessor((String)ent.getValue())){//仅对processor内存做统计

				if ((!hsMemUsed.containsKey(index)) || (!hsMemFree.containsKey(index)))
					continue;
				
				memoryusedl = memoryusedl + Long.parseLong((String) hsMemUsed.get(index));
				memoryfreel = memoryfreel + Long.parseLong((String) hsMemFree.get(index));
			}
			if ((memoryusedl + memoryfreel) == 0) {
				memoryprocess = 0;
			} else {
				memoryprocess = Math.round(memoryusedl * 100 / (memoryusedl + memoryfreel));
			}

			// tmpMem = memoryprocessTmp;
			if (memoryprocess >= 100) {
				memoryprocess = 100;
			}
			mem.add(memoryprocess);
			return memoryprocess;
		} else {// 思科的N系列的设备直接获取内存利用率
			int memoryprocess = 0;

			for (Entry<String, Object> ent : hsMemoryUtilization.entrySet()) {
				memoryprocess = Integer.parseInt(hsMemoryUtilization.get(ent.getKey()).toString());
			}

			// tmpMem = memoryprocessTmp;
			if (memoryprocess >= 100) {
				memoryprocess = 100;
			}
			mem.add(memoryprocess);
			return memoryprocess;
		}

	}

	// 判断是否是processor内存
	@SuppressWarnings("unused")
	private boolean isProcessor(String str) {
		if (str.equalsIgnoreCase("processor") || str.equalsIgnoreCase("DRAM"))
			return true;
		else
			return false;
	}

	/*
	 * 电源分析
	 */
	public ArrayList<PwrCapBean> doPwrAnalyse(ArrayList<PwrCapBean> lsPwr) {
		HashMap<String, Object> hsPwr = lsDataStore.get("supplyState");// 电源
		if (hsPwr == null)
			return null;
		int index;
		for (Entry<String, Object> ent : hsPwr.entrySet()) {
			PwrCapBean pwr = new PwrCapBean();
			try {
				index = Integer.parseInt(ent.getKey());
			} catch (Exception e) {
				index = 0;
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
				index = 0;
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
		if (hsTemperature == null) {
			hsTemperature = lsDataStore.get("temperature_statu");// 温度状态
			if (hsTemperature == null)
				return null;
		}
		int index;
		for (Entry<String, Object> ent : hsTemperature.entrySet()) {
			TemperatureCapBean temperature = new TemperatureCapBean();
			try {
				index = Integer.parseInt(ent.getKey());
			} catch (Exception e) {
				index = 0;
			}
			temperature.setIndex(index);
			temperature.setTemperatureValue(Integer.parseInt((String) ent.getValue()));
			lsTemperature.add(temperature);
		}
		return lsTemperature;
	}
}
