//package com.idc.service.impl;
//
//import com.idc.model.IdcIpInfo;
//import com.idc.model.IdcIpSubnet;
//import org.apache.log4j.Logger;
//
//import java.sql.Connection;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.Callable;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * 创建网段或导入网段时创建ip资源信息
// * @author TangWei
// *
// */
//public class AddIPThread implements Callable{
//    private  long startId;
//    private  long endId  ;
//    private  IdcIpSubnet idcIpSubnet;
//    protected Logger logger = Logger.getLogger(getClass());
//    private   Connection connection = null;
//    public AddIPThread(long begin, long end, IdcIpSubnet idcIpSubnet, Connection connection){
//        this.startId = begin;
//        this.endId = end;
//        this.idcIpSubnet = idcIpSubnet;
//        this.connection = connection;
//    }
//
//    public void run() {
//        if (startId>endId) {
//            logger.error("******************:begin>end");
//            return;
//        }
//
//        logger.info("开始启动IP地址数据保存线程，本次涉及到的子网ID 从"+startId+" 到 "+endId);
//
//        if (startId == 0 || endId == 0) {
//            logger.error("******************:startId == 0 || endId == 0");
//            return;
//        }
//
//        for(long i=startId;i<endId;i++){
//            BlockingQueue<IdcIpInfo> subNetsQueue=new LinkedBlockingQueue<IdcIpInfo>();
//            //new DataSaveThread(subNetsQueue,subIdToBusi).start();
//        }
////        NetSubnetDBFun dbFun=new NetSubnetDBFun();
////        List<NetSubnet_Reality> listSubnet = dbFun.querySubnet(arraySubId);
////        if(listSubnet==null || listSubnet.size()==0){
////            logger.error("******************:listSubnet==null || listSubnet.size()==0");
////            return ;
////        }
////        int len=listSubnet.size();
////        if(len>10)// 最大同时启动10个线程进行数据入库
////            len=10;
////        BlockingQueue<NetSubnet_Reality> subNetsQueue=new LinkedBlockingQueue<NetSubnet_Reality>(listSubnet);
////        listSubnet=null;
////        for(int i=0;i<len;i++) {
////            new DataSaveThread(subNetsQueue,subIdToBusi).start();
////        }
//    }
//
//    @Override
//    public Object call() throws Exception {
//
//        return null;
//    }
//}
