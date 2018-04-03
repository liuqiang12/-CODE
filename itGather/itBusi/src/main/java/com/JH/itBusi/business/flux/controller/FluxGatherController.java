package com.JH.itBusi.business.flux.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.eventmanager.service.EventQueue;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.business.flux.db.PortDataUtil;
import com.JH.itBusi.business.flux.db.bean.PortFluxHis;
import com.JH.itBusi.business.flux.result.FluxGatherResult;
import com.JH.itBusi.business.flux.worker.CapGatherPortOctetsWorker;
import com.JH.itBusi.comm.DataUtil;
import com.JH.itBusi.comm.db.KPIAlarmLevelConfig;
import com.JH.itBusi.comm.db.PortCapBean;
import com.JH.itBusi.globaldata.GloablePara;
import com.JH.itBusi.globaldata.TaskCntType;
import com.JH.itBusi.util.DateFormate;



public class FluxGatherController extends ExecutiveController{


	private Logger logger = Logger.getLogger(FluxGatherController.class);
	// 性能采集模块数据库操作类
	private DataUtil dataUtil = null;
	private PortDataUtil portDataUtil = null;
	private ArrayList<DeviceInfoBean> devicels ;
	private com.JH.dgather.frame.common.db.DataUtil du = null;
	// 保存采集回的性能数据
	//private ArrayList<DeviceCapData> lsDeviceCapData = new ArrayList<DeviceCapData>();
	// 集中告警模块
	private EventHandle handle = null;
	private EventQueue eventQueue;
	private Collection<KPIAlarmLevelConfig> collection = null;
	private HashMap<Long, PortFluxHis> portFluxHis = null;

	private String execTime;
//	private Collection<KPIAlarmLevelConfig> collection = null;
	private HashMap<Integer, HashMap<Long, PortCapBean>> lsGatherPortOctets = new HashMap<Integer, HashMap<Long, PortCapBean>>();
	private HashMap<Integer, List<PortCapBean>> updatePortHs = new HashMap<Integer, List<PortCapBean>>();
	private UserTask userTask;
    private Date exectime;
	public FluxGatherController(UserTask task) {
		super(task, 300);
		dataUtil = new DataUtil();
		this.userTask = task;
		du = new com.JH.dgather.frame.common.db.DataUtil();
		portDataUtil = new PortDataUtil();
		eventQueue = new EventQueue();
		handle = new EventHandle(this.userTask.getGatherClass());
		collection = dataUtil.getAlarmLevelConfig(this.userTask.getUserTaskBean().getKpiId());
	}

	/**
	 * 初始化工作，从数据库内读取相关数据
	 */
	private void doInitWork() {
		//默认提取所有设备采集， 获取需要采集的设备信息列表，为采集做准备
		devicels = du.getAllRout();
		//获取需要采集的端口信息
		lsGatherPortOctets = portDataUtil.getPortResInfo();
		//提取端口历史的流量数据，为当此流量计算做准备
		if(GloablePara.PortOctets_curr_Hs==null)//异常，未能初始数据成功
			GloablePara.PortOctets_curr_Hs = portDataUtil.getPortOctets();
			//oldLsGatherPortOctets = portDataUtil.getPortOctets();
		
	}

	/*
	 * (non-Javadoc) 进行门限判断和入库
	 */

	@Override
	public FluxGatherResult execute(){
		Date d ;
		while(true){
			d = new Date();
		 int m = DateFormate.getMinuteOfDate(d);
		 if(m%5==0)
			 break;
		 try{
		 Thread.sleep(5000);
		 }catch(Exception e){}
		}
		this.exectime =  DateFormate.dateToDate(d,"yyyy-MM-dd HH:mm");//当前执行时间
		userTask.setExecTime(this.exectime);//回写执行时间，为准确计算下次执行时间
		FluxGatherResult result = new FluxGatherResult(0);
		try {
			doInitWork();
			doPortFluxCruiseTask();
			doSave();
			GloablePara.PortOctets_curr_Hs = this.lsGatherPortOctets;
			updatePort();
			try{
			saveToRedis();
			}catch(Exception e){
				logger.error("save to redis fail",e);
			}
			//this.eventQueue.toPublish(handle);//发送告警
			result.setSuccessSize(1);
		} catch (Exception e) {
			logger.error(this.userTask.getTaskName() + "端口性能采集[" + this.userTask.getUserTaskBean().getTaskName() + "]", e);
		} finally {
			handle.destroy();
			handle = null;
		}
		return result;
	}
	//更改端口信息，这里仅仅更改端口状态
	private void updatePort() {
		this.portDataUtil.portStatusSave(this.updatePortHs);
		
	}

//数据保存到redis
	private void saveToRedis() {
		this.portDataUtil.portCurrFluxSave(this.lsGatherPortOctets,this.exectime);
		
	}

	private void doSave() {
		if(this.lsGatherPortOctets.size()>0)
		this.portDataUtil.portFluxSave(this.lsGatherPortOctets,this.exectime);

	}

	@Override
	protected void doFinish(int runFlag) {
	}

	@Override
	protected void doInit() {
		logger.info(">>>> doInit");
	}
	private void doPortFluxCruiseTask() throws Exception {
		if (devicels.size() > 0) {
			List<CapGatherPortOctetsWorker> capGatherWorkerList = new ArrayList<CapGatherPortOctetsWorker>();
			for (DeviceInfoBean device : devicels) {
				// 启动流量采集任务子任务
				try {
					HashMap<Long, PortCapBean> gatherPortOctetsHm = lsGatherPortOctets.get(device.getDeviceId());
					if(gatherPortOctetsHm==null)
						continue;
					HashMap<Long, PortCapBean> oldgatherPortOctetsHm = GloablePara.PortOctets_curr_Hs.get(device.getDeviceId());
					List<PortCapBean>  updateportLs = new ArrayList<PortCapBean>();
					updatePortHs.put(device.getDeviceId(), updateportLs);
					CapGatherPortOctetsWorker worker = new CapGatherPortOctetsWorker(this, new FluxGatherResult(),device,gatherPortOctetsHm,oldgatherPortOctetsHm,updateportLs,handle);
					capGatherWorkerList.add(worker);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			try {
				this.startTask(4);
			} catch (Exception e) {
				capGatherWorkerList = null;
				logger.error(this.userTask.getTaskName() + "性能采集任务[" + this.userTask.getTaskName() + "]分发失败:", e);
				throw e;
			}
		}
	}


}
