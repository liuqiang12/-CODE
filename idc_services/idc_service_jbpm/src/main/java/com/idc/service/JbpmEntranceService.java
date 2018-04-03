package com.idc.service;


import modules.utils.ResponseJSON;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public interface JbpmEntranceService {
	/**
	 * 创建工单相关的方法
	 * @param applicationContext
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseJSON jbpmTicketEntrancet(ApplicationContext applicationContext, Map map) throws  Exception;
}
