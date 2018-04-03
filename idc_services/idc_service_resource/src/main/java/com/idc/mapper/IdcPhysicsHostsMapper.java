package com.idc.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import system.data.page.EasyUIPageBean;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcPhysicsHosts;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_PHYSICS_HOSTS:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcPhysicsHostsMapper extends SuperMapper<IdcPhysicsHosts, String>{
    List<IdcPhysicsHosts> queryListPageByMap(EasyUIPageBean page);

    void cleanPool(Long idcResourcesPoolId);

    void updatePoolByIds(@Param("poolid")Long idcResourcesPoolId, @Param("hostids")List<String> hostids);
    /**
	 *   Special code can be written here 
	 */
}

 