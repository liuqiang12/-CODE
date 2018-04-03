package com.JH.dgather.frame.eventalarmsender.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.eventalarmsender.bean.UserBean;
import com.JH.dgather.frame.eventalarmsender.cmpp.CMPPSender;
import com.JH.dgather.frame.eventalarmsender.db.DataUtil;
import com.JH.dgather.frame.eventalarmsender.db.bean.AlarmInfoBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.RuleBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.ServiceBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.WebServiceAlarmSentBean;
import com.JH.dgather.frame.eventalarmsender.mail.MailTransferUtil;
import com.JH.dgather.frame.eventalarmsender.service.northif.AlarmNorthIfManager;
import com.JH.dgather.frame.eventalarmsender.service.northif.AlarmNorthIfResponse;
import com.JH.dgather.frame.eventalarmsender.sms.SMSTransferUtil;
import com.JH.dgather.frame.eventalarmsender.util.Dom4jRead;
import com.JH.dgather.frame.eventalarmsender.webservice.WebServiceResponse;
import com.JH.dgather.frame.eventalarmsender.webservice.WebServiceSender;
import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.DateUtil;

public class EventAlarmSenderProcess/* implements Runnable*/{
	public Logger logger = Logger.getLogger(EventAlarmSenderProcess.class);

	private HashMap<Integer, RuleBean> ruleHm = null; // 规则集合

	private boolean isDebug = false;

	private DataUtil du = null;
	private Dom4jRead dr = null;
	private MailTransferUtil eMailSender = null;

	public EventAlarmSenderProcess() {
		// 获取当前数据集合
		try {
			du = new DataUtil();
			dr = new Dom4jRead();
			eMailSender = new MailTransferUtil();
			// 提取发送规则
			ruleHm = du.getAllRules();
			if (isDebug) {
				logger.info("获取所有规则列表，长度: " + ruleHm.size());
				for (Entry<Integer, RuleBean> bean : ruleHm.entrySet()) {
					logger.info(bean.getValue().toString());
				}
			}
		} catch (Exception e) {
			logger.error("EventAlarmSenderProcess:", e);
		}
	}

	/**
	 * 判断并执行规则
	 * 
	 * @param beanLs
	 * @throws Exception
	 */
	public void ruleProcess(EventHandle bean) throws Exception {
		// 获取邮件短信服务信息
		HashMap<Integer, ServiceBean> serviceHm;
		// 记录发送,key为规则ID
		HashMap<Integer, EventHandle> sendHm = new HashMap<Integer, EventHandle>();
		try {
			if (this.ruleHm == null || this.ruleHm.size() == 0) {
				return;
			}

			serviceHm = dr.iterateWholeXML(PropertiesHandle.getResuouceInfo("mailService"));
			// 判断当前信息告警属于哪种规则
			// 并筛选发送的数据信息
			// logger.error("*******  提取规则长度：" + this.ruleHm.size());
			int id = 1;
			// String lineSr = "";
			String deviceSr = "";
			for (Entry<Integer, RuleBean> rBean : this.ruleHm.entrySet()) {
				deviceSr = "";
				// lineSr = "";
				RuleBean ruleBean = rBean.getValue();
				//logger.error("**** 当前执行 " + id + " 规则, " + ruleBean.getConfigId());
				// 采集类型必须相同
				if (ruleBean.getAlarmType() == bean.getGatherClass()) {
						deviceSr = ruleBean.getDeviceIds();
					if(deviceSr == null){
						deviceSr = "";
					}
					logger.info("**** 发送规则中，设备列表：" + deviceSr);
					EventHandle senderB;
					if (sendHm.containsKey(ruleBean.getConfigId())) {
						senderB = sendHm.get(ruleBean.getConfigId());
					} else {
						senderB = new EventHandle(bean.getGatherClass());
						sendHm.put(ruleBean.getConfigId(), senderB);
					}

					List<String> deviceIdList = new ArrayList<String>();
					for (String devId : deviceSr.split(",")) {
						deviceIdList.add(devId);
					}
					String objId = "";
					String aLevel = "";
					for (WarnEventBean alarmBean : bean.getWarnBeanLs()) {
						if (alarmBean.isSend()) {
							objId = Integer.toString(alarmBean.getObjId());
							aLevel = Integer.toString(alarmBean.getAlarmlevel());
							// 不做发送规则判断，直接发送
							senderB.addWarnEventBean(alarmBean, false);
						}
					}
					for (UnwarnEventBean unBean : bean.getUnwarnBeanls()) {
						if (unBean.isSend()) {
							//deviceId = Integer.toString(unBean.getDeviceId());
							objId = Integer.toString(unBean.getObjId());
							aLevel = Integer.toString(unBean.getAlarmlevel());
							//boolean isSend = false;
							// 不做发送规则判断，直接发送
							senderB.addUnWarnEventBean(unBean);
						}
					}
				} else {
					logger.info("规则与当前任务类型不符合：gatherclass: " + bean.getGatherClass() + ", rule: " + ruleBean.getAlarmType());
					continue;
				}
				ruleBean = null;
			}

			if (isDebug) {
				logger.info("当前需要发送邮件/短信的数量为: " + sendHm.size());
				int i = 1;
				for (Entry<Integer, EventHandle> sBean : sendHm.entrySet()) {
					logger.info("第 " + i + " 组数据，符合规则ID为： " + sBean.getKey());
					logger.info("发送的alarmBean长度为： " + sBean.getValue().getWarnBeanLs().size());
					i++;
				}
			}

			send(sendHm, serviceHm);
			// 更新告警事件的发送时间
			du.updateCurrAlarmInfo(sendHm);
		} catch (Exception e) {
			logger.error("执行规则是异常！", e);
			throw e;
		} finally {
			bean.destroy();
		}
	}
	
	private boolean isEmailSMSSendType(int sendType){
		return sendType == 0 || sendType == 1 || sendType == 2;
	}
	
	private boolean isCMPPSendType(int sendType){
		return sendType == GloableDataArea.ALARM_SENDTYPE_CMPP;
	}

	private AlarmInfoBean sendWarnEvent(EventHandle businessBean, java.util.Date nowD, HashMap<Integer, ServiceBean> serviceHm, RuleBean ruleBean) {
		if (businessBean.getWarnBeanLs().size() > 0) {
			AlarmInfoBean alarmInfoBean = new AlarmInfoBean(
					ruleBean.getAlarmType());
			alarmInfoBean.setConfigid(ruleBean.getConfigId());
			alarmInfoBean.setWarnEventBeanLs(businessBean.getWarnBeanLs());
			String content = alarmInfoBean.getSendInfo(0);
			String sendMem = "";
			String unSendMem = "";
			if (isEmailSMSSendType(ruleBean.getSendType())) {
				ServiceBean serviceBean = serviceHm.get(ruleBean.getSendType());
				String mailHost = serviceBean.getServiceAPIConn();
				String from = serviceBean.getServicAPIIde();
				String title = DateUtil.getChineseTimeStr(nowD) + " 发生告警信息";

				// 确定需要发送人员的EMAIL；
				String[] userIdsAry = ruleBean.getUserIds().split(",");
				HashMap<String, UserBean> userHm = du.getUserInfo(userIdsAry);

				logger.info("------------邮件发送信息------------------------------------");
				logger.info("HOST: " + mailHost);
				logger.info("from: " + from);
				logger.info("title: " + title);
				logger.info(content);
				logger.info("------------------------------------------------------------");

				SMSTransferUtil smsUtil = null;
				smsUtil = new SMSTransferUtil();

				for (String user : userIdsAry) {
					try {
						if (userHm.containsKey(user)) {
							logger.info("收件人: " + user + ", 信息: " + userHm.get(user).toString());
							/*MailTransferUtil mailUtil = null;
							SMSTransferUtil smsUtil = null;*/
							switch (ruleBean.getSendType()) {
							case 1://SEND_MAIL
								/*mailUtil = */
								int flag = eMailSender.sendMessage(serviceBean, userHm.get(user).getMail(), "", title, content);
								logger.error("[发送结果]: " + flag + ", title: " + title);
								break;
							case 0://SEND_SMS
								logger.info("[准备发送短信");
								for (String cnt : alarmInfoBean.getAlarmLs()) {
									smsUtil.smsSender(userHm.get(user).getTel(), cnt);
								}
								break;
							case 2://SEND_MAIL_AND_SMS
								eMailSender.sendMessage(serviceBean, userHm.get(user).getMail(), "", title, content);
								for (String cnt : alarmInfoBean.getAlarmLs()) {
									smsUtil.smsSender(userHm.get(user).getTel(), cnt);
								}
								break;
							default:
								logger.error("此发送类型不符，类型为: " + serviceBean.getANSType());
								break;
							}
							// 表示发送成功
							sendMem += user + ",";
							//
						} else {
								// 表示发送失败
							logger.info("没有此收件人的邮箱地址，请确认sys_user中包含此用户信息, user: " + user);
							unSendMem += user + ",";
						}
					} catch (Exception e) {
						logger.error("发送邮件失败：", e);
						unSendMem += user + ",";
					}
				}

			String otherUser = du.getUserEmail(ruleBean.getConfigId());
			if(otherUser!=null){	
				for (String user : otherUser.split(",")) {
					logger.info("收件人: " + user);
					try {
							/*MailTransferUtil mailUtil = null;
							SMSTransferUtil smsUtil = null;*/
							switch (serviceBean.getANSType()) {
							case 1://SEND_MAIL
								/*mailUtil = */
								int flag = eMailSender.sendMessage(serviceBean, user, "", title, content);
								logger.error("[发送结果]: " + flag + ", title: " + title);
								break;
							case 0://SEND_SMS
								//logger.error("[准备发送短信");
								/*for (String cnt : alarmInfoBean.getAlarmLs()) {
									smsUtil.smsSender(userHm.get(user).getTel(), cnt);
								}
								break;*/
							case 2://SEND_MAIL_AND_SMS
								/*eMailSender.sendMessage(mailHost, from, username, password, userHm.get(user).getMail(), "", title, content);
								for (String cnt : alarmInfoBean.getAlarmLs()) {
									smsUtil.smsSender(userHm.get(user).getTel(), cnt);
								}
								break;*/
							default:
								logger.error("此发送类型不符，类型为: " + serviceBean.getANSType());
								break;
							}
							// 表示发送成功
							sendMem += user + ",";
							//
					} catch (Exception e) {
						logger.error("发送邮件失败：", e);
						unSendMem += user + ",";
					}
				}
			  }
			} else if(isCMPPSendType(ruleBean.getSendType())) {
				if (CMPPSender.getInstance().sendMessage(ruleBean.getTelnum(),
						content)) {
					sendMem = ruleBean.getTelnum();
				} else {
					unSendMem = ruleBean.getTelnum();
				}
			}
			// webservice 接口
			logger.info("[WEBSERVICE]开始调用webservice告警接口");
			List<WebServiceResponse> webServiceResponses = WebServiceSender
					.getInstance().sendWarnEvent(businessBean.getWarnBeanLs());
			logger.info("[WEBSERVICE]调用webservice告警发送结果：" + webServiceResponses);

			for (WarnEventBean wb : businessBean.getWarnBeanLs()) {
				wb.setAlarmSend(1);
			}
			//mq
			logger.info("[AlarmNorthIfManager]开始调用告警接口");
			List<AlarmNorthIfResponse> mqResponses = AlarmNorthIfManager.getInstance().sendWarnEvent(businessBean.getWarnBeanLs());
			logger.info("[AlarmNorthIfManager]调用告警发送结果：" + mqResponses);
			
			alarmInfoBean.setSendMembers(sendMem);
			alarmInfoBean.setUnSendMembers(unSendMem);
			alarmInfoBean.setSendResult(0);
			alarmInfoBean.setSendTime(nowD);
			alarmInfoBean.setSendType(ruleBean.getSendType());
			// 设置webservice发送结果
			if (webServiceResponses != null && webServiceResponses.size() > 0) {
				if(alarmInfoBean.getWsAlarmSentBeanList() == null){
					alarmInfoBean.setWsAlarmSentBeanList(new ArrayList<WebServiceAlarmSentBean>());
				}
				for (WebServiceResponse response : webServiceResponses) {
					WebServiceAlarmSentBean wsAlarmSentBean = new WebServiceAlarmSentBean();
					alarmInfoBean.getWsAlarmSentBeanList().add(wsAlarmSentBean);
					wsAlarmSentBean.setErrorMsg(response.getErrorMsg());
					wsAlarmSentBean.setName(response.getWebService().name);
					wsAlarmSentBean.setSentResult(response.getSentResult());
					wsAlarmSentBean.setWsdlLocation(response.getWebService().wsdlLocation);
				}
			}
			// 设置mq发送结果
			if (mqResponses != null && mqResponses.size() > 0) {
				if(alarmInfoBean.getWsAlarmSentBeanList() == null){
					alarmInfoBean.setWsAlarmSentBeanList(new ArrayList<WebServiceAlarmSentBean>());
				}
				for (AlarmNorthIfResponse response : mqResponses) {
					WebServiceAlarmSentBean wsAlarmSentBean = new WebServiceAlarmSentBean();
					alarmInfoBean.getWsAlarmSentBeanList().add(wsAlarmSentBean);
					wsAlarmSentBean.setErrorMsg(response.getErrorMsg());
					wsAlarmSentBean.setName(response.getName());
					wsAlarmSentBean.setSentResult(response.getSentResult());
					wsAlarmSentBean.setWsdlLocation(response.getUri());
				}
			}
			return alarmInfoBean;
		} else
			return null;
	}

	private AlarmInfoBean sendUnWarnEvent(EventHandle businessBean, java.util.Date nowD, HashMap<Integer, ServiceBean> serviceHm, RuleBean ruleBean) {
		if (businessBean.getUnwarnBeanls().size() > 0) {
			AlarmInfoBean alarmInfoBean = new AlarmInfoBean(
					ruleBean.getAlarmType());
			alarmInfoBean.setConfigid(ruleBean.getConfigId());
			alarmInfoBean.setUnwarnEventBeanLs(businessBean.getUnwarnBeanls());
			String content = alarmInfoBean.getSendInfo(1);
			String sendMem = "";
			String unSendMem = "";
			if (isEmailSMSSendType(ruleBean.getSendType())) {
				ServiceBean serviceBean = serviceHm.get(ruleBean.getSendType());
				String mailHost = serviceBean.getServiceAPIConn();
				String from = serviceBean.getServicAPIIde();
				String title = DateUtil.getChineseTimeStr(nowD) + " 事件恢复信息";
				// 确定需要发送人员的EMAIL；
				String[] userIdsAry = ruleBean.getUserIds().split(",");
				HashMap<String, UserBean> userHm = du.getUserInfo(userIdsAry);

				logger.info("------------邮件发送信息------------------------------------");
				logger.info("HOST: " + mailHost);
				logger.info("from: " + from);
				logger.info("title: " + title);
				logger.info(content);
				logger.info("------------------------------------------------------------");
				SMSTransferUtil smsUtil = null;
				smsUtil = new SMSTransferUtil();

			for (String user : userIdsAry) {
				try {
					if (userHm.containsKey(user)) {
						logger.info("收件人: " + user + ", 信息: " + userHm.get(user).toString());
						/*MailTransferUtil mailUtil = null;
						SMSTransferUtil smsUtil = null;*/
						switch (ruleBean.getSendType()) {
						case 1://SEND_MAIL
							/*mailUtil = */
							eMailSender.sendMessage(serviceBean, userHm.get(user).getMail(), "", title, content);
							break;
						case 0://SEND_SMS
							for (String cnt : alarmInfoBean.getUnalarmLs()) {
								smsUtil.smsSender(userHm.get(user).getTel(), cnt);
							}
						break;
					case 2://SEND_MAIL_AND_SMS
							eMailSender.sendMessage(serviceBean, userHm.get(user).getMail(), "", title, content);
							for (String cnt : alarmInfoBean.getUnalarmLs()) {
								smsUtil.smsSender(userHm.get(user).getTel(), cnt);
							}
						break;
						default:
							logger.error("此发送类型不符，类型为: " + serviceBean.getANSType());
							break;
						}
						// 表示发送成功
						sendMem += user + ",";
						//
					} else {
						// 表示发送失败
						logger.info("没有此收件人的邮箱地址，请确认sys_user中包含此用户信息, user: " + user);
							unSendMem += user + ",";
						}
					} catch (Exception e) {
						logger.error("发送邮件失败：", e);
						unSendMem += user + ",";
					}
				}
			} else if(isCMPPSendType(ruleBean.getSendType())) {
				if (CMPPSender.getInstance().sendMessage(ruleBean.getTelnum(),
						content)) {
					sendMem = ruleBean.getTelnum();
				} else {
					unSendMem = ruleBean.getTelnum();
				}
			}
			// webservice 接口
			logger.info("[WEBSERVICE]开始调用webservice恢复告警接口");
			List<WebServiceResponse> webServiceResponses = WebServiceSender
					.getInstance().sendUnWarnEvent(businessBean.getUnwarnBeanls());
			logger.info("[WEBSERVICE]调用webservice恢复告警发送结果："+webServiceResponses);
			//mq
			List<AlarmNorthIfResponse> mqResponses = AlarmNorthIfManager.getInstance().sendUnWarnEvent(businessBean.getUnwarnBeanls());
			
			alarmInfoBean.setSendMembers(sendMem);
			alarmInfoBean.setUnSendMembers(unSendMem);
			alarmInfoBean.setSendResult(0);
			alarmInfoBean.setSendTime(nowD);
			alarmInfoBean.setSendType(ruleBean.getSendType());
			// 设置webservice发送结果
			if (webServiceResponses != null && webServiceResponses.size() > 0) {
				if(alarmInfoBean.getWsAlarmSentBeanList() == null){
					alarmInfoBean.setWsAlarmSentBeanList(new ArrayList<WebServiceAlarmSentBean>());
				}
				for (WebServiceResponse response : webServiceResponses) {
					WebServiceAlarmSentBean wsAlarmSentBean = new WebServiceAlarmSentBean();
					alarmInfoBean.getWsAlarmSentBeanList().add(wsAlarmSentBean);
					wsAlarmSentBean.setErrorMsg(response.getErrorMsg());
					wsAlarmSentBean.setName(response.getWebService().name);
					wsAlarmSentBean.setSentResult(response.getSentResult());
					wsAlarmSentBean.setWsdlLocation(response.getWebService().wsdlLocation);
				}
			}
			
			// 设置mq发送结果
			if (mqResponses != null && mqResponses.size() > 0) {
				if(alarmInfoBean.getWsAlarmSentBeanList() == null){
					alarmInfoBean.setWsAlarmSentBeanList(new ArrayList<WebServiceAlarmSentBean>());
				}
				for (AlarmNorthIfResponse response : mqResponses) {
					WebServiceAlarmSentBean wsAlarmSentBean = new WebServiceAlarmSentBean();
					alarmInfoBean.getWsAlarmSentBeanList().add(wsAlarmSentBean);
					wsAlarmSentBean.setErrorMsg(response.getErrorMsg());
					wsAlarmSentBean.setName(response.getName());
					wsAlarmSentBean.setSentResult(response.getSentResult());
					wsAlarmSentBean.setWsdlLocation(response.getUri());
				}
			}
			
			return alarmInfoBean;
		} else {
			return null;
		}
	}

	/**
	 * <code>send</code> 执行发送任务
	 * 
	 * @param sendBean
	 */
	private void send(HashMap<Integer, EventHandle> sendBean, HashMap<Integer, ServiceBean> serviceHm) {
		int id = 1;
		java.util.Date nowD = new java.util.Date();
		List<AlarmInfoBean> aiLs = new ArrayList<AlarmInfoBean>();
		//logger.error(">>>>>>>>>>>>>>>>>> 开始执行发送!,发送数据长度：" + sendBean.size());
		for (Entry<Integer, EventHandle> bean : sendBean.entrySet()) {
			//logger.error(">>>>>>>>>>>>>>>>>> 告警长度：" + bean.getValue().getWarnBeanLs().size());
			//logger.error(">>>>>>>>>>>>>>>>>> 告警恢复长度：" + bean.getValue().getUnwarnBeanls().size());
			RuleBean ruleBean = this.ruleHm.get(bean.getKey());
			EventHandle businessBean = bean.getValue();
			AlarmInfoBean alarmInfoBean = sendWarnEvent(businessBean, nowD, serviceHm, ruleBean);
			if (alarmInfoBean != null)
				aiLs.add(alarmInfoBean);
			alarmInfoBean = sendUnWarnEvent(businessBean, nowD, serviceHm, ruleBean);
			if (alarmInfoBean != null)
				aiLs.add(alarmInfoBean);
		}
		du.insertAlarmInfoList(aiLs);
	}

	/**
	 * 以规则为单位，去告警集合中筛选匹配当前规则的告警信息，再根据规则进行执行发送任务
	 * 
	 * 规则定期与数据库进行更新，比如12小时更新一次。
	 */
	/*public void run() {
		this.ruleUpdateStartTime = System.currentTimeMillis();
		//while (isRunning) {
			try {
				// 将等待的信息提取，存到执行的信息中
				if (waitingLs.peek() != null) {
					while (waitingLs.peek() != null) {
						runningLs.add(waitingLs.poll());
					}
				}

				if (runningBusinessBean == null && runningLs.peek() != null) {
					logger.info("获取一个发送事件！");
					runningBusinessBean = runningLs.poll();
					ruleProcess(runningBusinessBean);
					logger.info("完成发送事件!");
					runningBusinessBean.destroy();
					runningBusinessBean = null;
				}

				updateRules();
				Thread.sleep(sleep_time);
			} catch (Exception e) {
				logger.error("发送邮件异常：", e);
			}
		}
	}*/
}
