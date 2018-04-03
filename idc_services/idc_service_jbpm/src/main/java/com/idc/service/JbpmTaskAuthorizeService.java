package com.idc.service;

import com.idc.model.JbpmResourceChildshape;
import com.idc.model.JbpmResourceModel;
import com.idc.model.JbpmResourceModelVo;
import com.idc.model.JbpmTaskAuthorize;
import system.data.supper.service.SuperService;

import java.util.List;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>JBPM_TASK_AUTHORIZE:任务授权表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface JbpmTaskAuthorizeService extends SuperService<JbpmTaskAuthorize, Integer>{
	/**
	 * 创建信息
	 * @param jbpmResourceModel
	 * @param jbpmResourceChildshapeList
	 * @throws Exception
	 */
	void createAuthorize(JbpmResourceModel jbpmResourceModel, List<JbpmResourceChildshape> jbpmResourceChildshapeList) throws Exception;

	/**
	 * 通过roleId：获取信息
	 * @return
	 */
	List<JbpmResourceModelVo> queryActModelWithRoleKeyRoleId(String key, Long roleId) ;
}
