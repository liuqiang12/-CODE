package com.JH.itBusi;

import java.util.concurrent.CountDownLatch;

import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.observer.BusMapObServer;
import zk.comm.ZkStartUp;
import zk.observer.ServiceMapObservable;
import org.apache.log4j.Logger;

/**
 * Created by 54074 on 2017-10-25.
 */
public class BootStrap {
    Logger logger = Logger.getLogger(BootStrap.class);

    public BootStrap() {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
               System.out.println("begin shutdown");
               //关闭前需要处理的事务
               System.out.println("Shutdown Gather!");
            }

        });	
	    org.apache.log4j.LogManager.resetConfiguration();
	    org.apache.log4j.PropertyConfigurator.configure(GloableDataArea.appDir+"/log4j.properties");
		getApplicationLogTitle();
   	

    }
	protected void getApplicationLogTitle() {
		logger.info("******************************************************");
		logger.info("* 采集服务已经启动");
		logger.info("******************************************************");
	}
	

    public static void main(String[] args) throws Exception {
       CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkStartUp zkStartUp = new ZkStartUp();
        ServiceMapObservable observable = new ServiceMapObservable();
        BusMapObServer busObServer = new BusMapObServer();
        observable.addObserver(busObServer);
        zkStartUp.zkInit(observable);
//
//        if ("1".equals(Config.instance().getMaster())) {
//            Map<String, Object> m = new HashMap<String, Object>();
//            m.put("isWork", false);
//            m.put("isLock", true);
//            m.put("nameNode", Config.instance().getSelfNode());
//            observable.setData(m);
//        }
        countDownLatch.await();
    }
}
