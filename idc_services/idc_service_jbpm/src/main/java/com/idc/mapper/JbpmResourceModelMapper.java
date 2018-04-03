package com.idc.mapper;

import com.idc.model.JbpmResourceModel;
import system.data.supper.mapper.SuperMapper;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>JBPM_RESOURCE_MODEL:JBPM_RESOURCE_MODEL<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface JbpmResourceModelMapper extends SuperMapper<JbpmResourceModel, Long>{
	void createJbpmResourceModel(JbpmResourceModel jbpmResourceModel) throws Exception;
	Long getJbpmResourceModelId(String name);

	/**
	 *
	 * @param name
	 * @throws Exception
	 */
    void callClearJbpmResourceModel(String name) throws Exception;
	void deleteByResource(Long resourceId) throws Exception;
}

 