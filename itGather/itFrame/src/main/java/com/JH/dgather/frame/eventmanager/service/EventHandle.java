package com.JH.dgather.frame.eventmanager.service;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;

public class EventHandle {
	public Logger logger = Logger.getLogger(EventHandle.class.toString());
	private int gatherClass;

	private ConcurrentLinkedQueue<WarnEventBean> warnBeanLs;
	private ConcurrentLinkedQueue<UnwarnEventBean> unwarnBeanls;

	//private boolean isPublish = false;
	public EventHandle(int gatherClass) {
		this.gatherClass = gatherClass;

		warnBeanLs = new ConcurrentLinkedQueue<WarnEventBean>();
		unwarnBeanls = new ConcurrentLinkedQueue<UnwarnEventBean>();
		//isPublish = false;
	}

	public int getGatherClass() {
		return gatherClass;
	}

	/**
	 * <code>addWarnEventBean</code> 增加一条告警事件Bean
	 * 
	 * @param warnEventBean
	 *            告警事件Bean
	 */
	public void addWarnEventBean(WarnEventBean warnEventBean, boolean justTest) {
		if (warnEventBean != null) {
			this.warnBeanLs.offer(warnEventBean);
		}
	}
	
	public void addUnWarnEventBean(UnwarnEventBean unwarnEventBean) {
		if(unwarnEventBean != null) {
			this.unwarnBeanls.offer(unwarnEventBean);
		}
	}

	public WarnEventBean addWarnEventBean(int objId, int alarmType, int portId,int alarmlevel, String objname,String alarmInfo) {
		WarnEventBean warnEventBean = new WarnEventBean(objId, alarmType,portId, alarmlevel, objname,alarmInfo);
		warnEventBean.setAlarmTime(new java.util.Date());
		if(this.warnBeanLs==null)
			this.warnBeanLs = new ConcurrentLinkedQueue<WarnEventBean>();
		this.warnBeanLs.offer(warnEventBean);
		return warnEventBean;
	}

	
	/**
	 * <code>addUnwarnEventBean</code> 增加一条非告警事件Bean
	 * 
	 * @param unwarnEventBean
	 *            非告警事件Bean
	 */

	public UnwarnEventBean addUnwarnEventBean(int objId, int alarmType,int portId, String alarmInfo) {
		UnwarnEventBean unwarnEventBean = new UnwarnEventBean(objId, alarmType,portId, alarmInfo);
		if(this.unwarnBeanls==null)
			unwarnBeanls = new ConcurrentLinkedQueue<UnwarnEventBean>();	
		this.unwarnBeanls.add(unwarnEventBean);
		return unwarnEventBean;
	}
	
	
	public ConcurrentLinkedQueue<WarnEventBean> getWarnBeanLs() {
		return warnBeanLs;
	}

	public ConcurrentLinkedQueue<UnwarnEventBean> getUnwarnBeanls() {
		return unwarnBeanls;
	}


	//销毁
	public void destroy() {
		if (warnBeanLs != null && warnBeanLs.peek()!=null) {
			warnBeanLs.clear();
		}
		warnBeanLs = null;

		if (unwarnBeanls != null && unwarnBeanls.peek()!=null) {
			unwarnBeanls.clear();
		}
		unwarnBeanls = null;
	}
}
