package com.idc.mapper;

import com.idc.model.AssetBaseinfo;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>ASSET_BASEINFO:数据字典<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface AssetBaseinfoMapper extends SuperMapper<AssetBaseinfo, Long>{
    /**
     * 业务字段情况
     * @param parentCode
     * @return
     */
    List<AssetBaseinfo> queryComboboxData(String parentCode);

    AssetBaseinfo getIDCName(String parentCode);

    List<AssetBaseinfo> getIntentionIdcCode();

    List<AssetBaseinfo> getRootByParentCodeLike_(String parentCodeLke);

    List<AssetBaseinfo> getChildrenByParend(String parentCode);

    /**
     *
     * @return
     */
    List<AssetBaseinfo> queryComboboxDataIntoRedis();


    List<Map<String,Object>> checkboxOpenMSG();
}

 