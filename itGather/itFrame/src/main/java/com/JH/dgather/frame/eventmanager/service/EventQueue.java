package com.JH.dgather.frame.eventmanager.service;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.eventalarmsender.service.EventAlarmSenderProcess;
import com.JH.dgather.frame.eventmanager.db.DataUtil;
import com.JH.dgather.frame.globaldata.GloableDataArea;

/**
 * <code> EventQueue</code> 事件队列
 * 
 * @author Wang Xuedan
 * 
 */
public class EventQueue {
	public Logger logger = Logger.getLogger(EventQueue.class.toString());

	private DataUtil du = null;

	public EventQueue() {
		du = new DataUtil();
	}

	//发送事件
	public void toPublish(EventHandle handle) {
		EventAlarmSenderProcess easp = null;
		try {
			logger.info(handle.getWarnBeanLs()+";"+handle.getUnwarnBeanls()+";"+handle.getGatherClass());
			du.process(handle.getWarnBeanLs(), handle.getUnwarnBeanls(), handle.getGatherClass());
			//判断是否发送
			if (GloableDataArea.eventAlarmSend != null && GloableDataArea.eventAlarmSend.equalsIgnoreCase("true")) {
				easp = new EventAlarmSenderProcess();
				try {
					easp.ruleProcess(handle);
				} catch (Exception e) {
					logger.error("告警发送异常：", e);
				}
			}
		} catch (Exception e) {
			logger.error("error: ", e);
		}finally{
			handle.destroy();
			easp = null;
		}
	}
}
