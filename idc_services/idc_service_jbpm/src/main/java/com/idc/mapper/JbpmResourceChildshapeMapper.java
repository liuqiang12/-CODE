package com.idc.mapper;

import com.idc.model.JbpmResourceChildshape;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>JBPM_RESOURCE_CHILDSHAPE:JBPM_RESOURCE_CHILDSHAPE<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface JbpmResourceChildshapeMapper extends SuperMapper<JbpmResourceChildshape, Long>{
	/**
	 *
	 * @param jbpmResourceChildshape
	 * @throws Exception
	 */
    void createJbpmResourceChildshape(JbpmResourceChildshape jbpmResourceChildshape) throws Exception;

	/**
	 *
	 * @return
	 * @throws Exception
	 */
    List<Long> getJbpmResourceChildshapaIds(Long resourceId) throws Exception;
	void deleteByResource(Long resourceId) throws Exception;
}

 