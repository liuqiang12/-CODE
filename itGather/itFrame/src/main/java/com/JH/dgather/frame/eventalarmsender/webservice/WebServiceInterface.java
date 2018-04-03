package com.JH.dgather.frame.eventalarmsender.webservice;

import java.util.Collection;

import com.JH.dgather.frame.eventalarmsender.webservice.config.WebService;
import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;

public interface WebServiceInterface {

	void setWebService(WebService webService);

	/**
	 * 
	 * @param warnBeanLs
	 * @return 0=失败，1=成功
	 * @throws Exception
	 */
	int sendWarnEvent(Collection<WarnEventBean> warnBeanLs) throws Exception;

	int sendUnWarnEvent(Collection<UnwarnEventBean> unwarnBeanls)
			throws Exception;
}
