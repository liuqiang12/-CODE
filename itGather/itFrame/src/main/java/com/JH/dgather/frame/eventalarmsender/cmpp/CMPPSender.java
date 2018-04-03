package com.JH.dgather.frame.eventalarmsender.cmpp;

import org.apache.log4j.Logger;

public class CMPPSender {
	private static Logger logger = Logger.getLogger(CMPPSender.class);
	private static CMPPSender instance = new CMPPSender();
	private SMProxySend proxySend = new SMProxySend();

	public CMPPSender() {

	}

	public void start() {
		proxySend.start();
	}

	public static CMPPSender getInstance() {
		return instance;
	}

	public boolean sendMessage(String tel, String content) {
		logger.info("[CMPPSender]进入发送:\r\n		发送手机：" + tel + "\r\n		发送内容："
				+ content);
		if (tel == null || tel.trim().equals("")) {
			logger.error("[CMPPSender]发送结束。由于发送手机号码为空，发送结果：失败");
			return false;
		}
		boolean result = false;
		try {
			result = proxySend.sendMessage(tel, content);
		} catch (Exception e) {
			logger.error("[CMPPSender]发送异常：", e);
		}
		logger.info("[CMPPSender]发送结束。发送结果：" + (result ? "成功" : "失败"));
		return result;
	}

}
