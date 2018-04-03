package com.JH.dgather.frame.eventalarmsender.db.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;

;

public class AlarmInfoBean {
	private int sendId;
	private int sendType;
	private int sendResult;
	private Date sendTime;
	private String sendMembers;
	private String unSendMembers;
	private int alarmType;
	private int configid;

	//发送信息，最大支持65535字节,此处只存储30000个字符
	private StringBuffer sendInfo;

	private ConcurrentLinkedQueue<WarnEventBean> warnEventBeanLs;
	//add by xdwang 2013-1-7
	private ConcurrentLinkedQueue<UnwarnEventBean> unwarnEventBeanLs;
	private List<String> alarmLs;
	private List<String> unalarmLs;
	//webservice发送结果
	private List<WebServiceAlarmSentBean> wsAlarmSentBeanList;

	public List<WebServiceAlarmSentBean> getWsAlarmSentBeanList() {
		return wsAlarmSentBeanList;
	}

	public void setWsAlarmSentBeanList(
			List<WebServiceAlarmSentBean> wsAlarmSentBeanList) {
		this.wsAlarmSentBeanList = wsAlarmSentBeanList;
	}

	public AlarmInfoBean(int alarmType) {
		sendId = -1;
		sendType = -1;
		sendResult = -1;
		sendMembers = "";
		unSendMembers = "";
		this.alarmType = alarmType;
		sendInfo = new StringBuffer("");
		alarmLs = new ArrayList<String>();
		unalarmLs = new ArrayList<String>();
	}

	/**
	 * @return the sendId
	 */
	public int getSendId() {
		return sendId;
	}

	/**
	 * @param sendId the sendId to set
	 */
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	/**
	 * @return the sendType
	 */
	public int getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}

	/**
	 * @return the sendResult
	 */
	public int getSendResult() {
		return sendResult;
	}

	/**
	 * @param sendResult the sendResult to set
	 */
	public void setSendResult(int sendResult) {
		this.sendResult = sendResult;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the sendMembers
	 */
	public String getSendMembers() {
		return sendMembers;
	}

	/**
	 * @param sendMembers the sendMembers to set
	 */
	public void setSendMembers(String sendMembers) {
		this.sendMembers = sendMembers;
	}

	/**
	 * @return the unSendMembers
	 */
	public String getUnSendMembers() {
		return unSendMembers;
	}

	/**
	 * @param unSendMembers the unSendMembers to set
	 */
	public void setUnSendMembers(String unSendMembers) {
		this.unSendMembers = unSendMembers;
	}

	/**
	 * @return the alarmType
	 */
	public int getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public ConcurrentLinkedQueue<WarnEventBean> getWarnEventBeanLs() {
		return warnEventBeanLs;
	}

	public void setWarnEventBeanLs(ConcurrentLinkedQueue<WarnEventBean> warnEventBeanLs) {
		this.warnEventBeanLs = warnEventBeanLs;
	}

	//	public void addSendInfo(String info) {
	//		if (this.sendInfo.length() >= 30000) {
	//			;
	//		} else {
	//			this.sendInfo.append(info);
	//			this.sendInfo.append("\r\n");
	//		}
	//	}

	public void setUnwarnEventBeanLs(ConcurrentLinkedQueue<UnwarnEventBean> unwarnEventBeanLs) {
		this.unwarnEventBeanLs = unwarnEventBeanLs;
	}

	public String getInfo() {
		return this.sendInfo.toString();
	}

	public int getConfigid() {
		return configid;
	}

	public void setConfigid(int configid) {
		this.configid = configid;
	}

	public String getSendInfo(int flag) {
		if (flag == 0) {
			if (warnEventBeanLs != null) {
				for (WarnEventBean bean : this.warnEventBeanLs) {
					this.sendInfo.append("【").append(bean.getAlarmTime()).append("】").append("  ");
						this.sendInfo.append("【").append(bean.getObjname()).append("】").append("发生");
					if (bean.getAlarmlevel() == 0) {
						this.sendInfo.append("【轻微】");
					} else if (bean.getAlarmlevel() == 1) {
						this.sendInfo.append("【一般】");
					} else if (bean.getAlarmlevel() == 2) {
						this.sendInfo.append("【严重】");
					} else {
						this.sendInfo.append("【关键】");
					}
					this.sendInfo.append("告警:").append("【" + bean.getAlarmInfo() + "】");
					this.alarmLs.add(sendInfo.toString());
					this.sendInfo.append("\r\n");
				}
			}
		} else {
			if (unwarnEventBeanLs != null) {
				for (UnwarnEventBean bean : this.unwarnEventBeanLs) {
						this.sendInfo.append("【").append(bean.getObjname()).append("】");
					this.sendInfo.append("告警恢复: ").append("【" + bean.getAlarmInfo() + "】");
					this.unalarmLs.add(sendInfo.toString());
					this.sendInfo.append("\r\n");
				}
			}
		}
		return this.sendInfo.toString();
	}
	public List<String> getAlarmLs() {
		return this.alarmLs;
	}
	
	public List<String> getUnalarmLs() {
		return this.unalarmLs;
	}
}
