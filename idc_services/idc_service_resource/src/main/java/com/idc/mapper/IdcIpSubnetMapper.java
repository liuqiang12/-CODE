package com.idc.mapper;

import com.idc.model.IdcIpSubnet;
import com.idc.model.IdcSubnetCountVo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_IPSUBNET:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 16,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcIpSubnetMapper extends SuperMapper<IdcIpSubnet, Long> {
    List<IdcSubnetCountVo> countUsageBySubnet();

    List<IdcIpSubnet> queryListByIdIn(List<String> list);

    /***
     * 按组汇总
     * @return
     */
    List<IdcSubnetCountVo> sumUsageBySubnet();
    /**
     * 工单使用
     * @throws Exception
     */
    void foreachUpdateInfo(List<Map<String, Object>> resourceList) throws  Exception;

    void singleUpdateInfoById(@Param("resourceId") Long resourceId, @Param("status") Long status) throws Exception;

    List<String> getChildId(String updateIds);
}

 