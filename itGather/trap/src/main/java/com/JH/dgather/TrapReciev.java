package com.JH.dgather;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.server.LogMessageEvent;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import com.JH.dgather.comm.Util;
import com.JH.dgather.comm.DateFormate;

/**
 * SNMP Trap
 *
  */
public class TrapReciev implements CommandResponder {
	private MultiThreadedMessageDispatcher dispatcher;
	private Snmp snmp = null;
	private Address listenAddress;
	private ThreadPool threadPool;
	MessageDispatcher md;
	private static final int trapagentNUM = 50;//开启trap消息接收agent的数量
	private static final String serviceAddress = "udp:127.0.0.1/162";
	Logger logger = Logger.getLogger(TrapReciev.class);
	private Db db = null;
	private HashMap<String,DeviceInfo> devHs =null;
	
	public TrapReciev() {
	    org.apache.log4j.LogManager.resetConfiguration();
	    org.apache.log4j.PropertyConfigurator.configure(GloabVar.appDir+"/log4j.properties");
	}
 
	private void serviceinit() throws UnknownHostException {
		md = new MessageDispatcher();//消息分发器
		MessageListener listener = new MessageListener(devHs);//处理消息的侦听器
		md.addMessageListener(listener);
		//启动SNMP服务
		try{
		startSnmpService();
		}catch(Exception e){
			logger.error("启动trap 接收代理失败！！");
			System.out.println("启动trap 接收代理失败！！");
		}
	}
 
	private void startSnmpService() throws IOException {
		threadPool = ThreadPool.create("TrapPool", trapagentNUM);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty(
				"snmp4j.listenAddress",serviceAddress));
		TransportMapping transport;
		if (listenAddress instanceof UdpAddress) {
			transport = new DefaultUdpTransportMapping(
					(UdpAddress) listenAddress);
		} else {
			transport = new DefaultTcpTransportMapping(
					(TcpAddress) listenAddress);
		}
		snmp = new Snmp(dispatcher, transport);
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
				MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		snmp.listen();
		
	}

	public void run() {
		System.out.println("***** Trap Receiver run *****");
		try {
			dataInit();//将设备信息数据加载到内存
			serviceinit();//启动服务 
			snmp.addCommandResponder(this);
			System.out.println("***** begin recieveTrap message  *****");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
 /*
  * 加载可以推送消息的设备清单，仅清单内的设备发送的消息才接收
  */
	private void dataInit() {
		db = new Db();
		//初始可接收消息的设备
		this.devHs = initDeviceInfo();
	}
	private HashMap<String, DeviceInfo> initDeviceInfo() {
		//提取可接收消息的设备
		return db.getTrapDevice();
	}

	@SuppressWarnings("unchecked")
	public void processPdu(CommandResponderEvent event) {
		MessageEvent msg;
		 PDU pdu =  event.getPDU();
		Address address = event.getPeerAddress();
		System.out.println("***** ResponderEvent:"+address.toString()+"*****");
		logger.debug("***** ResponderEvent:"+address.toString()+"*****");
		if (event == null || event.getPDU() == null) {
			logger.info("[Warn] ResponderEvent or PDU is null");
			return;
		}
		Vector<VariableBinding> vbVect  =  (Vector<VariableBinding>) event.getPDU().getVariableBindings();
		for (VariableBinding vb : vbVect) {
			logger.debug("收到消息:"+vb.getOid() + " = " + vb.getVariable().toString());
			logger.debug("收到消息:"+vb.getOid() + " = " + Util.getChineseStr(vb.getVariable().toString()));
			msg = constructMsg(address.toString(),vb);
			if(msg==null)
				continue;
			md.fireMessageEvent(msg);//分发消息
		}
	}
 

	
	private MessageEvent constructMsg(String hostip,VariableBinding vb) {
//		if(devHs.get(hostip)==null)
//			return null;
		MessageEvent msgevent = new MessageEvent();
		msgevent.setSourceIP(hostip.split("/")[0]);
		msgevent.setSourcePort(hostip.split("/")[1]);
		msgevent.setTime(DateFormate.dateToStr(new Date(),null));//生成“yyyy-mm-dd hh:ss”格式的时间
		msgevent.setMessage(vb.getVariable().toString());
		return msgevent;
	}

	public static void main(String[] args) {
		TrapReciev trapReceiver = new TrapReciev();
		trapReceiver.run();
	}
}





