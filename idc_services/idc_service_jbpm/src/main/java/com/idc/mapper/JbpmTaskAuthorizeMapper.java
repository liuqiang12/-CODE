package com.idc.mapper;

import com.idc.model.JbpmResourceModelVo;
import com.idc.model.JbpmTaskAuthorize;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>JBPM_TASK_AUTHORIZE:任务授权表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface JbpmTaskAuthorizeMapper extends SuperMapper<JbpmTaskAuthorize, Integer>{
	/**
	 *
	 * @param jbpmTaskAuthorize
	 * @throws Exception
	 */
	void createJbpmTaskAuthorize(JbpmTaskAuthorize jbpmTaskAuthorize) throws Exception;
	void deleteByResource(Long resourceId) throws Exception;
	List<JbpmResourceModelVo> queryActModelWithRoleKeyRoleId(JbpmResourceModelVo jbpmResourceModelVo) ;
}

 