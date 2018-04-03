package com.JH.dgather.frame.eventalarmsender.cmpp;

import org.apache.log4j.Logger;

import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SMProxy;

public class SMProxyRec extends SMProxy {
	private static Logger logger = Logger.getLogger(SMProxyRec.class);
	private SMProxySend demo;

	public SMProxyRec(SMProxySend proxySend, Args args) {
		super(args);
		this.demo = proxySend;
	}

	public CMPPMessage onDeliver(CMPPDeliverMessage msg) {
		logger.info("启动SMProxyRec接收短信\r\n");
		demo.ProcessRecvDeliverMsg(msg);
		return super.onDeliver(msg);
	}

	public void OnTerminate() {
		demo.Terminate();
	}
}
