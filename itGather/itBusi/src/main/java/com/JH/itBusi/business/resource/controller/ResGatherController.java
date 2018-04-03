package com.JH.itBusi.business.resource.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.snmp4j.User;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.bean.PortInfoBean;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.DataCache;
import com.JH.itBusi.business.resource.db.ResDataUtil;
import com.JH.itBusi.business.resource.db.Bean.DeviceData;
import com.JH.itBusi.business.resource.result.ResGatherResult;
import com.JH.itBusi.business.resource.worker.ResGatherWorker;


public class ResGatherController extends ExecutiveController{
	private Logger logger = Logger.getLogger(ResGatherController.class);
	ResDataUtil  resdu = null; 
	private com.JH.dgather.frame.common.db.DataUtil du = null;
	private ArrayList<DeviceInfoBean> devicels ;//数据库中的设备信息
	private ArrayList<DeviceInfoBean> updateDevLs ;//数据库中的设备信息
//	private ConcurrentHashMap<DeviceData, DataCache> data = new ConcurrentHashMap<DeviceData, DataCache>();
	private HashMap<Integer,DeviceData> data = new HashMap<Integer,DeviceData>();//采集到的数据放入里面，未采集到的则不进入
	private ArrayList<PortInfoBean> updatePortLs,insertPortLs,delPortLs;//<deviceid,<portindex,portinfobean>>保存采集的端口
	private HashMap<Integer,HashMap<Long,PortInfoBean>> portLs_old;//<deviceid,<portindex,portinfobean>>保存数据库的端口
	public ResGatherController(UserTask userTask) {
		super(userTask);
		resdu = new ResDataUtil();
		du = new com.JH.dgather.frame.common.db.DataUtil();
	}
	@Override
	public ExecutiveResult execute() {
		ResGatherResult result = new ResGatherResult(0); 
		try {
			doInit();
			doResGather();
			doSave();
			result.setSuccessSize(1);
		} catch (Exception e) {
			logger.error("执行失败!", e);
				}
		return result;
	}

	/* * 获取设备采集对象
	 */
	private ArrayList<DeviceInfoBean> initTaskObject(int taskId) {
		//HashMap<Integer, TaskObjBean> hmResObject = new HashMap<Integer, TaskObjBean>();
		if (userTask.getTaskType() == 0)// 如果是临时任务，需要先查找临时对象中是否有定义
		{
			return resdu.getResTaskTmpdev(userTask.getTaskId());
			
		} else {
			// 如果没有临时对象，从模型中获取对象
			return du.getAllRout();
		}
	}
	//初始端口信息
	private HashMap<Integer, HashMap<Long, PortInfoBean>> initPort_old(
			int taskId) {
		if (userTask.getTaskType() == 0)// 如果是临时任务，需要先查找临时对象中是否有定义
		{
			String s ="";
			for(DeviceInfoBean dev : devicels){
				s = s+dev.getDeviceId()+",";
			}
			s = s.substring(0,s.length()-1);//去掉最后的“,”
			return this.resdu.getPortInfo(s);	
		}else{
			return this.resdu.getPortInfo();//获取所有已存在的端口，为端口信息对比作准备
		}
	}

	@Override
	protected void doInit() {
		// 获取任务对象
		devicels = initTaskObject(userTask.getTaskId());// 获取任务对象
		portLs_old = initPort_old(userTask.getTaskId());
	}
	
	private void doResGather() throws Exception {
		logger.info("当前采集模型的长度: " + this.devicels.size());
		if (this.devicels.size() > 0) {
			List<ResGatherWorker> workerLs = new ArrayList<ResGatherWorker>();
			for (DeviceInfoBean device : devicels) {
				ResGatherWorker worker = new ResGatherWorker(this, device,data, new ResGatherResult());
				workerLs.add(worker);
			}
			// 启动任务
			this.startTask(30);
		}
	}
	
	private void doSave() throws Exception {
		auditDevInfo();//审计设备信息的变更情况
		auditPortInfo();//审计端口的变更情况
		if(updateDevLs!=null)
		resdu.updateDevice(updateDevLs);//更新需要更新的设备
		if(updatePortLs.size()>0)
		resdu.updatePort(updatePortLs);//更新需要更新的端口
		if(insertPortLs.size()>0)
		resdu.insertPort(insertPortLs);//增加需要增加端口
		resdu.delPort(delPortLs);//删除需要删除的端口,因为历史信息保留的原因暂时不删除端口，可以考虑人工从界面操作
	}

	//分析端口信息的变更，分析出需要增加、修改、删除的端口
	private void auditPortInfo() {
		Collection<PortInfoBean>  portLs;
		HashMap<Long,PortInfoBean> oldportHs;
		PortInfoBean oldport;
		insertPortLs = new ArrayList<PortInfoBean>();
		updatePortLs = new ArrayList<PortInfoBean>();
		delPortLs = new  ArrayList<PortInfoBean>();
		if(portLs_old==null){//从来未采集到端口的
			for(Entry<Integer,DeviceData> entry:data.entrySet()){
				portLs = entry.getValue().getLsPort();
				for(PortInfoBean port: portLs)
					insertPortLs.add(port);
		}
			return;
		}

		
		for(Entry<Integer,DeviceData> entry:data.entrySet()){
			portLs = entry.getValue().getLsPort();
			if(portLs==null){//可能这个设备端口信息都未采集到
				portLs_old.remove(entry.getKey());//把整个端口移除避免认为是删除端口
				continue;
			}
			oldportHs = portLs_old.get(entry.getKey());
			for(PortInfoBean port: portLs){
				if(oldportHs==null){
					insertPortLs.add(port);
					continue;
				}
				//oldport = oldportHs.get(port.getPortIndex());//index 相等，认为端口名字就相等
				oldport = oldportHs.remove(port.getPortIndex());//已经找到，则从oldportHs中移除，剩下的就属于需要删除的，
				if(oldport==null){
					insertPortLs.add(port);
				}else{ 
					try{
					if(oldport.getPortName().equals(port.getPortName())&&oldport.getAlias().equals(port.getAlias())&&oldport.getDescr().equals(port.getDescr())){
						if(oldport.getAdminStatus()==port.getAdminStatus()&&oldport.getPortActive()==port.getPortActive()&&
								oldport.getBandWidth()==port.getBandWidth()&&oldport.getIfType()==port.getIfType())
							continue;
					}
					}catch(Exception e){
						logger.error("端口验证问题portid="+oldport.getPortid(),e);
					}
//					if(oldport.getAlias().equals(port.getAlias())&&oldport.getDescr().equals(port.getDescr())&&
//							oldport.getAdminStatus()==port.getAdminStatus()&&oldport.getPortActive()==port.getPortActive()&&
//							oldport.getBandWidth()==port.getBandWidth()&&oldport.getIfType()==port.getIfType())
//							continue;
//					else{
					logger.error(port.getDeviceId()+"的端口"+port.getPortIndex()+"有变更" );
						updatePortLs.add(port);
						port.setPortid(oldport.getPortid());//用portid做条件效率高
					//}
				}
			}
			//oldportHs中剩余的端口是要删除的
			if((oldportHs!=null)&&oldportHs.size()>0)
				for(Entry<Long,PortInfoBean> delport:oldportHs.entrySet()){
					delPortLs.add(delport.getValue());
			}
		}		
	}
	//分析设备信息的变更，从而提取出需要update的设备信息
	private void auditDevInfo() {
		DeviceData devicedt;
		DeviceInfoBean devicenew;
		String s;
		for(DeviceInfoBean device : devicels){
			devicedt = data.get(device.getDeviceId());
			if(devicedt==null)
				continue;
			s = device.getDeviceName()+";"+device.getSysDescr();
			if(!s.equalsIgnoreCase(devicedt.getDeviceName()+";"+devicedt.getSysDescr())){
				if(updateDevLs==null)
					updateDevLs = new ArrayList<DeviceInfoBean>();
				devicenew = new DeviceInfoBean();
				devicenew.setRoutId(device.getDeviceId());
				devicenew.setRoutName(devicedt.getDeviceName());
				devicenew.setSysDescr(devicedt.getSysDescr());
				updateDevLs.add(devicenew);
			}
				
		}
	}
	@Override
	protected void doFinish(int runFlag) {
		// TODO Auto-generated method stub
		
	}

}
