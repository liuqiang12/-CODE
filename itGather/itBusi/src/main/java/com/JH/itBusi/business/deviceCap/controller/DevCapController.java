package com.JH.itBusi.business.deviceCap.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.eventmanager.service.EventQueue;
import com.JH.dgather.frame.gathercontrol.controller.ExecutiveController;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.business.deviceCap.analyse.CapJudge;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;
import com.JH.itBusi.business.deviceCap.db.CapDataUtil;
import com.JH.itBusi.business.deviceCap.db.bean.PortCapBean;
import com.JH.itBusi.business.deviceCap.result.CapGatherResult;
import com.JH.itBusi.business.deviceCap.worker.CapGatherWorker;
import com.JH.itBusi.business.flux.db.bean.PortFluxHis;
import com.JH.itBusi.business.flux.worker.CapGatherPortOctetsWorker;
import com.JH.itBusi.comm.DataUtil;
import com.JH.itBusi.comm.db.KPIAlarmLevelConfig;
import com.JH.itBusi.globaldata.TaskCntType;

public class DevCapController extends ExecutiveController {
	private Logger logger = Logger.getLogger(DevCapController.class);
	// 性能采集模块数据库操作类
	private CapDataUtil capDataUtil = null;
	private DataUtil dataUtil = null;
	private com.JH.dgather.frame.common.db.DataUtil du = null;

	private ArrayList<DeviceInfoBean> devls = null;
	// 保存采集回的性能数据
	private ArrayList<DeviceCapData> lsDeviceCapData = new ArrayList<DeviceCapData>();
	// 集中告警模块
	private EventHandle handle = null;
	private HashMap<Long, PortFluxHis> portFluxHis = null;

	private String execTime;
	private EventQueue eventQueue;
	private Collection<KPIAlarmLevelConfig> collection = null;
	private HashMap<Integer, HashMap<Long, PortCapBean>> lsGatherPortOctets = new HashMap<Integer, HashMap<Long, PortCapBean>>();
	private UserTask userTask;
	public DevCapController(UserTask task) {
		//super(task, GloableDataArea.getTaskWorkTimeOut().get(TaskCntType.deviceCap));
		super(task,300);
		capDataUtil = new CapDataUtil();
		du = new com.JH.dgather.frame.common.db.DataUtil();
		dataUtil = new com.JH.itBusi.comm.DataUtil();
		this.userTask = task;
		handle = new EventHandle(this.userTask.getGatherClass());
	}

	/**
	 * 初始化工作，从数据库内读取相关数据
	 */
	private void doInitWork() {
		devls = initTaskObject(userTask.getTaskId());// 获取任务对象
		eventQueue = new EventQueue();
		collection = dataUtil.getAlarmLevelConfig(this.userTask.getUserTaskBean().getKpiId());
	}

	private ArrayList<DeviceInfoBean> initTaskObject(int taskId) {
		if (userTask.getTaskType() == 0)// 如果是临时任务，需要先查找临时对象中是否有定义
		{
			return capDataUtil.getCapTaskTmpdev(userTask.getTaskId());
			
		} else {
			// 如果没有临时对象，从模型中获取对象
			return du.getAllRout();
		}

	}

	/**
	 * 将任务分解到每台设备
	 * @throws Exception 
	 */
	private void doCruiseTask() throws Exception {
		//logger.info(this.userTask.getTaskName() + "获取可执行的采集对象长度： HmObj size = " + this.userTask.getHmObj().size());
		if (this.devls.size() > 0) {
			List<CapGatherWorker> capGatherWorkerList = new ArrayList<CapGatherWorker>();
			for (DeviceInfoBean device : devls) {
				// 用来存放设备性能采集结果
				DeviceCapData deviceCapData = new DeviceCapData();
				this.lsDeviceCapData.add(deviceCapData);
				
				CapGatherWorker worker = new CapGatherWorker(this, device, deviceCapData, new CapGatherResult(), handle);
				capGatherWorkerList.add(worker);
			}

			try {
				this.startTask(5);//要求5分钟内结束任务
			} catch (Exception e) {
				capGatherWorkerList = null;
				logger.error(this.userTask.getTaskName() + "性能采集任务[" + this.userTask.getTaskName() + "]分发失败:", e);
				throw e;
			}
		}
	}

	/*
	 * 统一保存性能数据
	 */
	private void doDataSave() {
		execTime = this.capDataUtil.saveCap(lsDeviceCapData, this.userTask.getUserTaskBean());
	}

	/*
	 * (non-Javadoc) 进行门限判断和入库
	 */
/*	private void doCapJudge() throws Exception {
		try {
//只有在设置了门限的时候才做下面的操作
			logger.info(this.userTask.getTaskName() + "通过KPIID获取各种门限的大小是：" + collection.size() + "；KPIId是:" + this.userTask.getUserTaskBean().getKpiId());
			if (collection.size() > 0) {
				// muyp modify 存储前先提取上次的性能数据，做告警分析用
				logger.debug("日常巡检任务{" + this.userTask.getUserTaskBean().getTaskName() + "}开始提取历史巡检数据巡检数据....");
				if (this.userTask.getGatherClass() == 33)
					this.capDataUtil.getPortCap_old(lsDeviceCapData);
				logger.debug("日常巡检任务{" + this.userTask.getUserTaskBean().getTaskName() + "}开始分析性能巡检数据....");
				CapJudge cj = new CapJudge(lsDeviceCapData, this.userTask.getUserTaskBean(), collection, this.capDataUtil, this.handle, this.execTime);
				//按照门限和VALUE获取得到告警,将告警生成集中告警模块
				cj.doCapJudge();
				logger.debug(this.userTask.getTaskName() + "日常巡检任务{" + this.userTask.getUserTaskBean().getTaskName() + "}分析性能巡检数据完成....");
			} else {
				logger.debug(this.userTask.getTaskName() + "指标ID：" + this.userTask.getUserTaskBean().getKpiId() + ";日常巡检任务{" + this.userTask.getUserTaskBean().getTaskName() + "}没有定义告警门限....");
			}
		} catch (Exception e) {
			logger.error(this.userTask.getTaskName() + "日常巡检任务{" + this.userTask.getUserTaskBean().getTaskName() + "}在分析告警的时候发生错误!", e);
			throw e;
		}finally{
			this.eventQueue.toPublish(this.handle);
		}
	}
*/
	@Override
	public CapGatherResult execute() {
		CapGatherResult result = new CapGatherResult(0);
		try {
			doInitWork();
			doCruiseTask();
			doDataSave();
			//doCapJudge();
			result.setSuccessSize(1);
			System.out.println("释放设备性能采集任务！");
		} catch (Exception e) {
			logger.error(this.userTask.getTaskName() + "设备性能采集[" + this.userTask.getUserTaskBean().getTaskName() + "]", e);
		}
		finally {
			handle.destroy();
			handle = null;
		}
		return result;
	}

	@Override
	protected void doFinish(int runFlag) {
	}

	@Override
	protected void doInit() {
		logger.info(">>>> doInit");
	}

}
