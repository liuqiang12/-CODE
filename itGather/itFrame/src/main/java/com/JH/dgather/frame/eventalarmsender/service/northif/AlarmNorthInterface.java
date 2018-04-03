package com.JH.dgather.frame.eventalarmsender.service.northif;

import java.util.Collection;

import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;

public interface AlarmNorthInterface {

	AlarmNorthIfResponse sendWarnEvent(Collection<WarnEventBean> warnBeanLs)
			throws Exception;

	AlarmNorthIfResponse sendUnWarnEvent(
			Collection<UnwarnEventBean> unwarnBeanls) throws Exception;

	void init() throws Exception;

	void start() throws Exception;

	void stop() throws Exception;

	String getName();

	String getUri();

}
