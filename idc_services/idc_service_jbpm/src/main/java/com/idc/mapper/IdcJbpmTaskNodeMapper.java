package com.idc.mapper;


import com.idc.model.IdcJbpmTaskNode;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_JBPM_TASKNODE:流程中的任务节点配置<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 26,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcJbpmTaskNodeMapper extends SuperMapper<IdcJbpmTaskNode, Long>{

    String getProcessdefinitonkey(@Param("ticketCategory") Long ticketCategory, @Param("prodCategory") Long prodCategory);







}

 