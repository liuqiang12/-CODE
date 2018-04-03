package com.JH.dgather.frame.eventalarmsender.cmpp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.snmp4j.smi.OctetString;

import com.huawei.insa2.comm.cmpp.message.CMPPCancelMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPCancelRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;

//时间：20081213发送短信
//
public class SMProxySend {
	private static Logger logger = Logger.getLogger(SMProxySend.class);
	// 从xml中读取配置信息
	private Args argsconn; // 读取连接信息
	private Args argssumbit; // 读取提交信息
	// 基本提交信息参数说明
	private int pk_Total = 1;
	private int pk_Number = 1;
	private int registered_Delivery = 1;
	private int msg_Level = 1;
	private String service_Id = "";
	private int fee_UserType = 2;
	private String fee_Terminal_Id = "";
	private int tp_Pid = 0;
	private int tp_Udhi = 0;
	private int msg_Fmt = 15;
	private String msg_Src = "";
	private String fee_Type = "02";
	private String fee_Code = "000";
	private String src_Terminal_Id = "";
	private String reserve = "";
	/** 短信收发接口 */
	public SMProxyRec myProxy = null;
	private Cfg cfg = null;
	private boolean isLoadSuccessfully;
	private volatile boolean isConnected;
	private int reconnect_interval = 10;

	public SMProxySend() {
		// 连接配置信息
		try {
			cfg = new Cfg("Smproxy.xml", false);
			argsconn = cfg.getArgs("CMPPConnect");
			reconnect_interval = argsconn.get("reconnect-interval", 10);
			argssumbit = cfg.getArgs("CMPPSubmitMessage");
			pk_Total = argssumbit.get("pk_Total", 1);
			pk_Number = argssumbit.get("pk_Number", 1);
			registered_Delivery = argssumbit.get("registered_Delivery", 1);
			msg_Level = argssumbit.get("msg_Level", 1);
			service_Id = argssumbit.get("service_Id", "");
			fee_UserType = argssumbit.get("fee_UserType", 2);
			fee_Terminal_Id = argssumbit.get("fee_Terminal_Id", "");
			tp_Pid = argssumbit.get("tp_Pid", 1);
			tp_Udhi = argssumbit.get("tp_Udhi", 1);
			msg_Fmt = argssumbit.get("msg_Fmt", 15);
			msg_Src = argssumbit.get("msg_Src", "");
			fee_Type = argssumbit.get("fee_Type", "02");
			fee_Code = argssumbit.get("fee_Code", "000");
			src_Terminal_Id = argssumbit.get("src_Terminal_Id", "");
			reserve = argssumbit.get("reserve", "");
			isLoadSuccessfully = true;
			logger.error("读取CMPP配置文件Smproxy.xml成功。");
		} catch (IOException e) {
			logger.error("读取CMPP配置文件Smproxy.xml失败：", e);
		}
	}

	public void start() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!isConnected()) {
					isConnected = false;
					logger.info("CMPP连接不可用，准备重新连接...");
					isConnected = connect();
				}
			}
		}, 0, reconnect_interval * 1000);
	}

	// 基本参数设置
	private boolean connect() {
		if (!isLoadSuccessfully)
			return false;
		try {
			myProxy = new SMProxyRec(this, argsconn);
			logger.info("CMPP启动连接成功!");
			return true;
		} catch (Exception e) {
			logger.error("CMPP启动连接失败!", e);
		}
		return false;
	}

	private boolean isConnected() {
		try {
			return myProxy != null && myProxy.getConnState() == null;
		} catch (Exception e) {
			logger.error("CMPP查询连接状态异常：", e);
		}
		return false;
	}

	/**
	 * 华为短信发送类处理（公用类）
	 * 
	 * @param 短信类
	 *            ，包括手机号，内容，序号
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 */
	public boolean sendMessage(String mobiles, String msgContent)
			throws Exception {
		logger.debug("开始发送短信，号码：" + mobiles);
		if (!isConnected) {
			logger.error("CMPP连接不可用！发送短信失败！");
			return false;
		}

		boolean result = false;
		String[] mobileArray = mobiles.split(",");
		List<String> telList = new ArrayList<String>();
		for (int i = 0; i < mobileArray.length; i++) {
			if (mobileArray[i].trim() != "") {
				telList.add(mobileArray[i].trim());
			}
		}
		if (telList.size() == 0)
			return result;
		String[] dest_Terminal_Id = new String[telList.size()];
		for (int i = 0; i < telList.size(); i++) {
			dest_Terminal_Id[i] = telList.get(i);
		}
		// 存活有效期
		Date valid_Time = new Date(System.currentTimeMillis()
				+ (long) 0xa4cb800); // new
										// Date();//
		// 定时发送时间
		Date at_Time = null;// new Date(System.currentTimeMillis() + (long)
		// 0xa4cb800); //new Date();
		// 用户手机上显示为短消息的主叫号码
		// src_Terminal_Id=src_Terminal_Id+"001";

		// 初始化提交信息
		if (msgContent.getBytes().length <= 140) {
			logger.debug("作为一条短信发送。。。。");
			CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total,
					pk_Number, registered_Delivery, msg_Level, service_Id,
					fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi, msg_Fmt,
					msg_Src, fee_Type, fee_Code, valid_Time, at_Time,
					src_Terminal_Id, dest_Terminal_Id, msgContent.getBytes(),
					reserve);

			try {
				logger.debug("提交cmpp。。。。");
				CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy
						.send(submitMsg);
				if (submitRepMsg.getResult() == 0) {
					result = true;
					logger.info("CMPP发送短信成功msgid:"
							+ submitRepMsg.getMsgId().toString()
							+ "\t SequenceId:" + submitRepMsg.getSequenceId());
				} else {
					logger.error("发送失败,cmpp反馈代码：" + submitRepMsg.getResult()
							+ "，号码：" + mobiles);
				}

			} catch (IOException e) {
				logger.error("CMPP ERROR 发送失败,号码：" + mobiles + "内容："
						+ msgContent, e);
			}
		} else {
			logger.debug("拆分多条短信发送。。。。");
			// 采用UCS2编码格式
			int maxMessageLen = 140;
			byte[] contents = msgContent.getBytes("UnicodeBigUnmarked");
			int messageUCS2Len = contents.length;
			// 计算短信数量
			int count = messageUCS2Len / (maxMessageLen - 6);
			int rest = messageUCS2Len % (maxMessageLen - 6);
			if (rest > 0) {
				count++;
			}
			byte index = 1;
			for (int i = 0; i < count; i++) {
				byte[] smsHead = new byte[6];
				// 6位协议头格式：05 00 03 XX MM NN
				smsHead[0] = 0x05;
				// 表示剩余协议头的长度
				smsHead[1] = 0x00; // 这个值在GSM 03.40规范9.2.3.24.1中规定
				smsHead[2] = 0x03; // 这个值表示剩下短信标识的长度
				smsHead[3] = 0x00; // 这批短信的唯一标志(被拆分的多条短信,此值必需一致)。
				smsHead[4] = (byte) count; // 这批短信的数量
				smsHead[5] = index; // 当前短信是这批短信中第几条
				byte[] msgBytes = null;
				if (i != (count - 1)) {
					msgBytes = new byte[140];
					System.arraycopy(smsHead, 0, msgBytes, 0, 6);
					System.arraycopy(contents, i * (maxMessageLen - 6),
							msgBytes, 6, maxMessageLen - 6);
				} else {
					msgBytes = new byte[6 + rest];
					System.arraycopy(smsHead, 0, msgBytes, 0, 6);
					System.arraycopy(contents, i * (maxMessageLen - 6),
							msgBytes, 6, rest);
				}
				// 此处发送短信时，需要将TP_udhi字段设置为1，表示短信内容中含有协议头信息
				CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total,
						pk_Number, registered_Delivery, msg_Level, service_Id,
						fee_UserType, fee_Terminal_Id, tp_Pid,
						1, // 表示有协议头
						0x08, // UCS2格式编码
						msg_Src, fee_Type, fee_Code, valid_Time, at_Time,
						src_Terminal_Id, dest_Terminal_Id,//
						msgBytes// 本次发送内容
						, reserve);
				try {
					logger.debug("发送第" + (index) + "条短信。。。");
					CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy
							.send(submitMsg);
					if (submitRepMsg.getResult() == 0) {
						result = true;
						logger.info("CMPP发送短信成功msgid:"
								+ submitRepMsg.getMsgId().toString()
								+ "\t SequenceId:"
								+ submitRepMsg.getSequenceId());
					} else {
						logger.error("发送第" + index + "条短信失败，cmpp反馈代码："
								+ submitRepMsg.getResult() + ",号码：" + mobiles);
					}
				} catch (IOException e) {
					logger.error("CMPP ERROR 短信的第(" + index + "条)发送失败,号码："
							+ mobiles + ",内容：" + msgContent, e);
					return false;
				}
				index++;
			}
			logger.debug("发送完成,号码：" + mobiles);
		}
		return result;
	}

	class MsgSubmitResult {
		private Map<OctetString, CMPPSubmitRepMessage> repMap = new HashMap<OctetString, CMPPSubmitRepMessage>();
		private volatile boolean responseReceived = false;
		private volatile boolean sendSuccessfully = false;
		private long sentTime;

		public void setSentTime(long sentTime) {
			this.sentTime = sentTime;
		}

		public void put(CMPPSubmitRepMessage submitRepMsg) {
			OctetString msgid = OctetString.fromByteArray(submitRepMsg
					.getMsgId());
			repMap.put(msgid, submitRepMsg);
		}

		public boolean waitResponseTimeout() {
			return System.currentTimeMillis() - sentTime >= 5000;
		}

		public synchronized void updateDeliverMsg(OctetString msgid,
				CMPPDeliverMessage deliverMsg) {
			boolean stat = !"DELIVRD".equalsIgnoreCase(new String(deliverMsg
					.getStat()));
			repMap.remove(msgid);
			if (repMap.size() == 0 || !stat) {
				sendSuccessfully = stat;
				responseReceived = true;
				this.notifyAll();
			}
		}
	}

	public boolean sendMobile(String mobile, String msgContent)
			throws Exception {
		if (!isConnected) {
			logger.error("CMPP连接不可用！发送短信失败！");
			return false;
		}

		boolean result = false;
		String[] dest_Terminal_Id = { mobile };
		// 存活有效期
		Date valid_Time = new Date(System.currentTimeMillis()
				+ (long) 0xa4cb800); // new
										// Date();//
		// 定时发送时间
		Date at_Time = null;// new Date(System.currentTimeMillis() + (long)
		// 0xa4cb800); //new Date();
		// 用户手机上显示为短消息的主叫号码
		// src_Terminal_Id=src_Terminal_Id+"001";

		// 初始化提交信息
		if (msgContent.getBytes().length <= 140) {
			CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total,
					pk_Number, registered_Delivery, msg_Level, service_Id,
					fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi, msg_Fmt,
					msg_Src, fee_Type, fee_Code, valid_Time, at_Time,
					src_Terminal_Id, dest_Terminal_Id, msgContent.getBytes(),
					reserve);

			try {
				CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy
						.send(submitMsg);
				if (submitRepMsg.getResult() == 0) {
					result = true;
					logger.info("CMPP发送短信成功msgid:"
							+ submitRepMsg.getMsgId().toString()
							+ "\t SequenceId:" + submitRepMsg.getSequenceId());

					MsgSubmitResult msgSubmitResult = new MsgSubmitResult();
					msgSubmitResult.setSentTime(System.currentTimeMillis());
					msgSubmitResult.put(submitRepMsg);
					putSentMsgQueue(msgSubmitResult);
					try {
						while (!msgSubmitResult.responseReceived
								&& !msgSubmitResult.waitResponseTimeout()) {
							synchronized (msgSubmitResult) {
								try {
									msgSubmitResult.wait(10);
								} catch (Exception e) {
								}
							}
						}
						return msgSubmitResult.sendSuccessfully;
					} finally {
						removeSentMsgQueue(msgSubmitResult);
					}
				}
			} catch (IOException e) {
				logger.error("CMPP ERROR 发送失败:" + msgContent, e);
			}
		} else {
			MsgSubmitResult msgSubmitResult = new MsgSubmitResult();
			// 采用UCS2编码格式
			int maxMessageLen = 140;
			byte[] contents = msgContent.getBytes("UnicodeBigUnmarked");
			int messageUCS2Len = contents.length;
			// 计算短信数量
			int count = messageUCS2Len / (maxMessageLen - 6);
			int rest = messageUCS2Len % (maxMessageLen - 6);
			if (rest > 0) {
				count++;
			}
			byte index = 1;
			for (int i = 0; i < count; i++) {
				byte[] smsHead = new byte[6];
				// 6位协议头格式：05 00 03 XX MM NN
				smsHead[0] = 0x05;
				// 表示剩余协议头的长度
				smsHead[1] = 0x00; // 这个值在GSM 03.40规范9.2.3.24.1中规定
				smsHead[2] = 0x03; // 这个值表示剩下短信标识的长度
				smsHead[3] = 0x00; // 这批短信的唯一标志(被拆分的多条短信,此值必需一致)。
				smsHead[4] = (byte) count; // 这批短信的数量
				smsHead[5] = index; // 当前短信是这批短信中第几条
				byte[] msgBytes = null;
				if (i != (count - 1)) {
					msgBytes = new byte[140];
					System.arraycopy(smsHead, 0, msgBytes, 0, 6);
					System.arraycopy(contents, i * (maxMessageLen - 6),
							msgBytes, 6, maxMessageLen - 6);
				} else {
					msgBytes = new byte[6 + rest];
					System.arraycopy(smsHead, 0, msgBytes, 0, 6);
					System.arraycopy(contents, i * (maxMessageLen - 6),
							msgBytes, 6, rest);
				}
				// 此处发送短信时，需要将TP_udhi字段设置为1，表示短信内容中含有协议头信息
				CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total,
						pk_Number, registered_Delivery, msg_Level, service_Id,
						fee_UserType, fee_Terminal_Id, tp_Pid,
						1, // 表示有协议头
						0x08, // UCS2格式编码
						msg_Src, fee_Type, fee_Code, valid_Time, at_Time,
						src_Terminal_Id, dest_Terminal_Id,//
						msgBytes// 本次发送内容
						, reserve);
				try {
					CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy
							.send(submitMsg);
					if (submitRepMsg.getResult() == 0) {
						logger.info("CMPP发送短信成功msgid:"
								+ submitRepMsg.getMsgId().toString()
								+ "\t SequenceId:"
								+ submitRepMsg.getSequenceId());
						msgSubmitResult.setSentTime(System.currentTimeMillis());
						msgSubmitResult.put(submitRepMsg);
						putSentMsgQueue(msgSubmitResult);
					}
				} catch (IOException e) {
					logger.error("CMPP ERROR 短信的第(" + index + "条)发送失败："
							+ msgContent, e);
					return false;
				}
				index++;
			}
			//
			try {
				while (!msgSubmitResult.responseReceived
						&& !msgSubmitResult.waitResponseTimeout()) {
					synchronized (msgSubmitResult) {
						try {
							msgSubmitResult.wait(10);
						} catch (Exception e) {
						}
					}
				}
				return msgSubmitResult.sendSuccessfully;
			} finally {
				removeSentMsgQueue(msgSubmitResult);
			}
		}
		return result;
	}

	private final Map<OctetString, MsgSubmitResult> pendingRequests = new Hashtable<OctetString, MsgSubmitResult>(
			50);

	private void putSentMsgQueue(MsgSubmitResult msgSubmitResult) {
		for (OctetString octetString : msgSubmitResult.repMap.keySet()) {
			pendingRequests.put(octetString, msgSubmitResult);
		}
	}

	private void removeSentMsgQueue(MsgSubmitResult msgSubmitResult) {
		synchronized (msgSubmitResult) {
			for (OctetString octetString : msgSubmitResult.repMap.keySet()) {
				pendingRequests.remove(octetString);
			}
		}
	}

	/**
	 * 对于Proxy分发短信的接收
	 * 
	 * @param msg
	 */
	public void ProcessRecvDeliverMsg(CMPPMessage msg) {/*
														CMPPDeliverMessage deliverMsg = (CMPPDeliverMessage) msg;

														if (deliverMsg.getRegisteredDeliver() == 0) {

														} else {
														logger.info(new StringBuffer("CMPP 3收到状态报告消息： stat=")
														.append(new String(deliverMsg.getStat()))
														.append("dest_termID=")
														.append(new String(deliverMsg.getDestTerminalId()))
														.append(";destterm=")
														.append(new String(deliverMsg.getDestnationId()))
														.append(";serviceid=")
														.append(new String(deliverMsg.getServiceId()))
														.append(";tppid=").append(deliverMsg.getTpPid())
														.append(";tpudhi=").append(deliverMsg.getTpUdhi())
														.append(";msgfmt").append(deliverMsg.getMsgFmt())
														.append(";srctermid=")
														.append(new String(deliverMsg.getSrcterminalId()))
														.append(";deliver=")
														.append(deliverMsg.getRegisteredDeliver()));
														updateSubmitResult(deliverMsg);
														}
														*/
	}

	private void updateSubmitResult(CMPPDeliverMessage deliverMsg) {
		OctetString msgid = OctetString.fromByteArray(deliverMsg.getMsgId());
		MsgSubmitResult msgSubmitResult = pendingRequests.remove(msgid);
		if (msgSubmitResult != null) {
			msgSubmitResult.updateDeliverMsg(msgid, deliverMsg);
		}
	}

	public void Terminate() {
		logger.info("CMPP SMC下发终断消息");
		myProxy.close();
		myProxy = null;
	}

	public void SearchMsg() {
		// 删除短信
		byte[] msg_id = "2".getBytes();
		CMPPCancelMessage cancelMsg = new CMPPCancelMessage(msg_id);
		try {
			CMPPCancelRepMessage cancelRepMsg = (CMPPCancelRepMessage) myProxy
					.send(cancelMsg);
			logger.info("CMPP getCommandId" + cancelRepMsg.getCommandId()
					+ "\t getSuccessId" + cancelRepMsg.getSuccessId()
					+ "\tgetSequenceId: " + cancelRepMsg.getSequenceId());
		} catch (IOException e1) {
			logger.info("CMPP ERROR", e1);
		}
		String stateDesc = myProxy.getConnState();
		logger.info("CMPP " + stateDesc);
	}

	/**
	 * 关闭连接
	 */
	public void Close() {
		// 查询SMProxy与ISMG的TCP连接状态
		// String stateDesc = myProxy.getConnState();
		myProxy.close();
		myProxy = null;
	}

	// 测试方法
	public static void main(String[] args) {
		int i = 0;
		String tel = null, msg = null;
		while (i < args.length) {
			String param = args[i++];
			if (param.equals("-tel")) {
				tel = (args[i++]);
			} else if (param.equals("-msg")) {
				msg = (args[i++]);
			}
		}

		System.out.println(">>>>>>>>准备发送消息：[" + msg + "]到" + tel);

		SMProxySend proxySend = new SMProxySend();
		proxySend.isConnected = proxySend.connect();
		try {
			System.out.println("发送结果：" + proxySend.sendMessage(tel, msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
