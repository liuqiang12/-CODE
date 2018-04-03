package com.idc.mapper;

import java.util.List;
import java.util.Map;

import com.idc.model.SysOrganization;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>sys_organization:机构信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Dec 11,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface SysOrganizationMapper extends SuperMapper<SysOrganization, Integer>{
    List<SysOrganization> getRootNodesByRegion(@Param("region") Integer region);
    List<SysOrganization> getNodesByPid(Integer pid);

    List<Map<String,Object>> TreeClosures();
    /**
	 *   Special code can be written here 
	 */
}

 