package com.JH.dgather;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;








import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.server.LogMessageEvent;

import redis.clients.jedis.Jedis;

import com.JH.dgather.comm.ConnMgr_redis;
import com.JH.dgather.comm.DateFormate;

/*
 * trap消息处理侦听器
 */
public class MessageListener extends Thread implements MessageListenerIF{

	Logger logger = Logger.getLogger(MessageListener.class);
	private static  LinkedList<MessageEvent>  waitingMsgLs= new LinkedList<MessageEvent>();
	private static  LinkedList<MessageEvent>  saveMsgLs= new LinkedList<MessageEvent>();
	private HashMap<String,DeviceInfo> devs =null; 
	private String rootPath = GloabVar.appDir+"/trap";
	private static final String alarmTable_curr = "alarm_curr";
	private static final String alarmTable = "alarm";
	Jedis redisconn;
	//初始化侦听器
	public MessageListener(){start();}
	public MessageListener(HashMap<String,DeviceInfo> devHs){
		this.devs = devHs;
		redisconn  = ConnMgr_redis.getJedis();
		start();
	};
	/*
	 * 初始设备信息，可以考虑放到系统的main里面
	 */


	public void run() {
		String filePath ="";
		while(true){
			Date newTime = new Date();
			String year = DateFormate.dateToStr(newTime, "yyyy");
			String monthStr = DateFormate.dateToStr(newTime, "MM");
			filePath = rootPath+"/" + year + "/" +monthStr;
			try {
			synchronized (waitingMsgLs) {
				if (!waitingMsgLs.isEmpty()){
//				{
//					waitingMsgLs.wait();
//				}
				saveMsgLs.addAll(waitingMsgLs);
				waitingMsgLs.clear();
			}}
			if (saveMsgLs.size() > 0) {
				HashMap<String,List<MessageEvent>> msgHs =new HashMap<String,List<MessageEvent>>();
				List<MessageEvent> msgLs;
				for(MessageEvent event : saveMsgLs){//将消息按照设备归类
					//sendEvent(event);
					msgLs = msgHs.get(event.getSourceIP());
					if(msgLs==null){
						msgLs = new ArrayList<MessageEvent>();
						msgHs.put(event.getSourceIP(), msgLs);
					}
					msgLs.add(event);
//					if(!filterMsg(event)){//是否需要过滤掉的信息
//						
					}
				for(Entry<String,List<MessageEvent>> entry : msgHs.entrySet())
					writeTrapInfo( entry.getValue(),entry.getKey(),filePath);
				saveMsgLs.clear();	
			}
			sleep(60000);//一分钟处理一次，避免频繁写数据
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	/*
	 * 实时发送消息，目前没有确定发送渠道
	 */
	private void sendEvent(MessageEvent event) {
		if(redisconn==null)
			redisconn = ConnMgr_redis.getJedis();
		//告警保存到alarm_curr表
		redisconn.set(alarmTable_curr,event.getSourceIP()+":"+event.getTime()+":"+event.getMessage());
		redisconn.hset(alarmTable,event.getSourceIP(),event.getSourceIP()+":"+event.getTime()+":"+event.getMessage());
		
	}
	public void actionPerformed(MessageEvent event) {
		logger.debug("收到消息："+event.getSourceIP()+":"+event.getMessage());
		waitingMsgLs.add(event);
	}
	//判读是否属于合法消息
	private boolean filterMsg(MessageEvent event) {
		
		return false;
	}
	public  boolean writeTrapInfo(List<MessageEvent> msgs,String ip, String path) {
		String deviceName ;
		try{
			deviceName = devs.get(ip).getDeviceName();
		}catch(Exception e){
			return false;
		}
		path +="/"+ deviceName + "/";
		logger.info("trap保存路径Ϊ"+path);
		FileWriter writer = null;
		PrintWriter pw = null;
		String dayStr = DateFormate.dateToStr(new Date(), "yyyyMMdd");
		try {
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();// 创建文件路径
			String fileName = "trap-" + deviceName + "-" +dayStr+ ".log";
			path += fileName;
			file = new File(path);
			logger.info("trap文件："+path);
			writer = new FileWriter(file, true);// true 
			pw = new PrintWriter(writer);
			Iterator<MessageEvent> it = msgs.iterator();
			MessageEvent lme;
			while (it.hasNext()) {
				lme = it.next();
				if(!filterMsg(lme))//判断消息是否应保存
					pw.println(lme.getTime() + ":" + lme.getMessage());
			}
			pw.flush();
			return true;
		} catch (IOException iox) {
			logger.error("trap保存异常" + iox.getMessage());
			return false;
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	

	
}
