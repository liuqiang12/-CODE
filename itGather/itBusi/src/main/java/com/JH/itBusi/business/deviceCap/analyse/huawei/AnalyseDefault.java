package com.JH.itBusi.business.deviceCap.analyse.huawei;

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
	 * CPU性能分析 取所有值中最大的呈现
	 */
	public int doCpuAnalyse(ArrayList<Integer> cpu) {
		HashMap<String, Object> hsCpuprocess = lsDataStore.get("cpuprocess");// CPU流量
		
		int cpuprocess = 0;
		Integer tmpCpu = 0;
		if (hsCpuprocess == null) {
			logger.info("hsCpuprocess对象为空，SNMP没采集到CPU信息");
			return -1;
		}
		logger.info(Thread.currentThread().getName()+"CPU利用率的大小："+hsCpuprocess.size()+";值："+hsCpuprocess);
		for (Entry<String, Object> enc : hsCpuprocess.entrySet()) {
			try {
				cpuprocess = Integer.parseInt(enc.getValue().toString());
				
				logger.info(Thread.currentThread().getName()+"本次cpu的利用率值为："+cpuprocess+"；上次的cpu值为："+tmpCpu);
				if (cpuprocess > tmpCpu)
					tmpCpu = cpuprocess;
			} catch (Exception e) {
				logger.error("CPU转换异常:"+enc.getValue(), e);
				return -1;
			}
		}
		cpu.add(tmpCpu);
		return tmpCpu;
	}
	
	/*
	 * 内存分析
	 */
	public int doMemAnalyse(ArrayList<Integer> mem) {
		/*
		 * memoryPoolSize
           memoryPoolLargestFree
		 */
		HashMap<String, Object> hsMemSize = lsDataStore.get("memoryPoolSize");// 内存总值
		HashMap<String, Object> hsMemUsed = lsDataStore.get("memoryPoolUsed");// 已使用内存
		//HashMap<String, Object> hsMemFree = lsDataStore.get("memoryPoolLargestFree");// 剩余内存
		HashMap<String, Object> hsMemFree = lsDataStore.get("memoryPoolFree");// 剩余内存
		long memoryfree;
		long memorysize;
		int memoryprocess = 0;
		int memoryprocessTmp = 0;
		int memorynumber = 0;
		Integer tmpMem = -1;
		if (hsMemUsed == null){
			if(hsMemSize == null || hsMemFree == null){
				return -1;
			}else{
				for (Entry<String, Object> ent : hsMemSize.entrySet()) {
					String index = ent.getKey();
					logger.debug("hsMemSize" + (String) ent.getValue() + "hsMemFree" + (String) hsMemFree.get(index));
					memorysize = Long.parseLong((String) ent.getValue());
					memoryfree = Long.parseLong((String) hsMemFree.get(index));
					memoryprocessTmp = Math.round((memorysize - memoryfree) * 100 / memorysize);
					memoryprocess += memoryprocessTmp;
					memorynumber += 1;
				}
				if (memorynumber > 0) {
					tmpMem = Math.round(memoryprocess / memorynumber);
					if (tmpMem >= 100) {
						tmpMem = 100;
					}
					mem.add(tmpMem);
				}
			}
		}
		if ((hsMemSize == null) || (hsMemFree == null)) {
			// 当做H3C的处理,取所有内存中的最大值
			for (Entry<String, Object> ent : hsMemUsed.entrySet()) {
				memoryprocessTmp = Integer.parseInt((String) ent.getValue());
				if (memoryprocessTmp > memoryprocess)
					memoryprocess = memoryprocessTmp;
			}
			if (memoryprocess >= 100) {
				memoryprocess = 100;
			}
			mem.add(memoryprocess);
		} else {
			// 当做HUAWEI产品处理
			for (Entry<String, Object> ent : hsMemSize.entrySet()) {
				String index = ent.getKey();
				logger.debug("hsMemSize" + (String) ent.getValue() + "hsMemUsed" + (String) hsMemUsed.get(index) + "hsMemFree"
						+ (String) hsMemFree.get(index));
				memorysize = Long.parseLong((String) ent.getValue());
				memoryfree = Long.parseLong((String) hsMemFree.get(index));
				memoryprocessTmp = Math.round((memorysize - memoryfree) * 100 / memorysize);
				memoryprocess += memoryprocessTmp;
				memorynumber += 1;
			}
			if (memorynumber > 0) {
				tmpMem = Math.round(memoryprocess / memorynumber);
				if (tmpMem >= 100) {
					tmpMem = 100;
				}
				mem.add(tmpMem);
			}
		}
		return tmpMem;
	}
	
	/*
	 * 电源分析
	 */
	public ArrayList<PwrCapBean> doPwrAnalyse(ArrayList<PwrCapBean> lsPwr) {
		HashMap<String, Object> hsPwr = lsDataStore.get("supplyState");
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
			try {
				pwr.setStatus(Integer.parseInt((String) ent.getValue()));
				lsPwr.add(pwr);
			} catch (Exception e) {
				logger.error("电源分析异常："+ent.getValue(),e);
			}
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
			try {
				fan.setStatusValue(Integer.parseInt((String) ent.getValue()));
			} catch (Exception e) {
				logger.error("风扇分析异常", e);
			}
			lsFan.add(fan);
		}
		return lsFan;
	}
	
	/*
	 * 温度分析
	 */
	public ArrayList<TemperatureCapBean> doTemperatureAnalyse(ArrayList<TemperatureCapBean> lsTemperature) {
		HashMap<String, Object> hsTemperature = lsDataStore.get("temperature");// 温度
		if (hsTemperature == null)
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
