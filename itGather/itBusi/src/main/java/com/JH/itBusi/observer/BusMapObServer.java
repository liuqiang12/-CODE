package com.JH.itBusi.observer;

import com.JH.dgather.frame.gathercontrol.task.TaskQueue;
import com.JH.dgather.frame.gathercontrol.task.TaskQueueManager;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.itBusi.PortOctetsInit;
import com.JH.itBusi.comm.DataUtil;
import com.JH.itBusi.gathercontrol.task.TaskDispatch;
import com.JH.itBusi.globaldata.GloablePara;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Observable;

/**
 * Created by 54074 on 2017-10-25.
 */
public class BusMapObServer implements java.util.Observer {
    Logger logger = Logger.getLogger(BusMapObServer.class);
    // 初始化任务队列
    private TaskQueue taskQueue = null;
	private DataUtil db;
	private TaskQueueManager taskManager;
	private TaskDispatch taskDispatch;
    public BusMapObServer() throws TagException {
        taskQueue = new TaskQueue();
        GloableDataArea.init();
    }

    public void startService()
    {
        try {
        	PortOctetsInit portOctetsInitThread = new PortOctetsInit();//初始化GloablePara.PortOctets_curr_Hs
			portOctetsInitThread.run();
			 taskManager =new TaskQueueManager(taskQueue);
			 new Thread(taskManager,"TaskQueueManagerThread").start();
			 taskDispatch = new TaskDispatch(taskQueue);
			 new Thread(taskDispatch, "TaskDispatchThread");
        } catch (Exception e) {
            logger.error("启动服务异常!", e);
        }
    }
    public void stopService()
    {
        System.out.println("开始停止任务！");
        if(taskManager!=null)
        	taskManager.stopTaskmanager();
        if(taskDispatch!=null)
        	taskDispatch.stopDispatch();
        doPortDataSave();
    }
    
	//保存数据
	protected void doPortDataSave() {
		db = new DataUtil();
		if(GloablePara.PortOctets_curr_Hs!=null)
			db.savePortCurr(GloablePara.PortOctets_curr_Hs);
		System.out.println("数据保存执行完毕");
	}
   
    
/*
    @Override
    public void update(Observable o, Object env) {
        System.out.println("观察者被调用");
        Map ev= (Map) env;
        System.out.println("锁持有状态"+ev.get("isLock"));
        System.out.println("业务运行状态"+ev.get("isWork"));
        System.out.println("业务节点"+ev.get("nameNode"));
        boolean isLock=((Boolean)ev.get("isLock")).booleanValue();
        boolean isWork=((Boolean)ev.get("isLock")).booleanValue();
        if(ev.get("nameNode")!=null) {
            String namenode = ev.get("nameNode").toString();
        }
*/
    @Override
    public void update(Observable o, Object env) {
        System.out.println("有节点状态改变");
        Map ev= (Map) env;
        System.out.println("锁持有状态"+ev.get("isLock"));
        System.out.println("业务运行状态"+ev.get("isWork"));
        System.out.println("业务节点"+ev.get("nameNode"));
        boolean isLock=((Boolean)ev.get("isLock")).booleanValue();
        boolean isWork=((Boolean)ev.get("isWork")).booleanValue();
        if(ev.get("nameNode")!=null) {
            String namenode = ev.get("nameNode").toString();
        }
        if(isLock&&(!isWork))
        	startService();
        if((!isLock)&&isWork)
        	stopService();
    }
}
