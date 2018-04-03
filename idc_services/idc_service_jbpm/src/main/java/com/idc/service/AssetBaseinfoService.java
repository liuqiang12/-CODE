package com.idc.service;

import com.idc.model.AssetBaseinfo;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;

public interface AssetBaseinfoService extends SuperService<AssetBaseinfo, Long>{
    /**
     * 业务字段情况
     * @param parentCode
     * @return
     */
    List<AssetBaseinfo> queryComboboxData(String parentCode);

    AssetBaseinfo getIDCName(String parentCode);

    List<AssetBaseinfo> getIntentionIdcCode();

    List<AssetBaseinfo> getRootByParentCodeLike_(String parentCodeLke_);

    List<AssetBaseinfo> getChildrenByParend(String parentCode);

    List<Map<String,Object>> checkboxOpenMSG();

    /**
     *
     * @return
     */
    List<AssetBaseinfo> queryComboboxDataIntoRedis();
}
