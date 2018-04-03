package com.JH.itBusi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.JH.itBusi.observer.BusMapObServer;

import org.apache.log4j.*;

import zk.comm.ZkStartUp;
import zk.conf.Config;
import zk.observer.ServiceMapObservable;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.gathercontrol.task.TaskQueue;
import com.JH.dgather.frame.gathercontrol.task.TaskQueueManager;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.itBusi.comm.DataUtil;
import com.JH.itBusi.gathercontrol.task.TaskDispatch;
//import zk.comm.ZkStartUp;
//import zk.conf.Config;
//import zk.observer.ServiceMapObservable;
import com.JH.itBusi.globaldata.GloablePara;


public class MainClass {
    Logger logger = Logger.getLogger(MainClass.class);
	private DataUtil db;
	// 初始化任务队列
	private TaskQueue taskQueue = null;
	private PortOctetsInit portOctetsInitThread = null;
	/*
	 * 
	 */
	protected void getApplicationLogTitle() {
		logger.info("******************************************************");
		logger.info("* 采集服务已经启动");
		logger.info("******************************************************");
	}
	

	public MainClass() {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
               System.out.println("begin shutdown");
                doPortDataSave();
                System.out.println("Shutdown Gather!");
            }

        });	

	    org.apache.log4j.LogManager.resetConfiguration();
	    org.apache.log4j.PropertyConfigurator.configure(GloableDataArea.appDir+"/log4j.properties");
		getApplicationLogTitle();
			// 初始化数据全
		try {
			taskQueue = new TaskQueue();
			GloableDataArea.init();
			portOctetsInitThread = new PortOctetsInit();//初始化GloablePara.PortOctets_curr_Hs
			portOctetsInitThread.run();
			new Thread(new TaskQueueManager(taskQueue),
					"TaskQueueManagerThread").start();
			new Thread(new TaskDispatch(taskQueue), "TaskDispatchThread")
					.start();
		} catch (TagException e) {
			logger.error("启动服务异常!", e);
		}

	}
//保存数据
	protected void doPortDataSave() {
		db = new DataUtil();
		if(GloablePara.PortOctets_curr_Hs!=null)
			db.savePortCurr(GloablePara.PortOctets_curr_Hs);
		System.out.println("执行完毕");
	}
	

	public static void main(String[] args) throws InterruptedException {
		final MainClass main = new MainClass();
       /* 	CountDownLatch countDownLatch = new CountDownLatch(1);
		ZkStartUp zkStartUp = new ZkStartUp();
		ServiceMapObservable observable = new ServiceMapObservable();
		BusMapObServer busObServer = new BusMapObServer();
		observable.addObserver(busObServer);
		zkStartUp.zkInit(observable);

		if ("1".equals(Config.instance().getMaster())) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("isWork", false);
			m.put("isLock", true);
			m.put("nameNode", Config.instance().getSelfNode());
			observable.setData(m);
		}
		countDownLatch.await();*/

	}



}
