package com.idc.service.impl;

import com.idc.mapper.AssetBaseinfoMapper;
import com.idc.model.AssetBaseinfo;
import com.idc.service.AssetBaseinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>ASSET_BASEINFO:数据字典<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 02,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("assetBaseinfoService")
public class AssetBaseinfoServiceImpl extends SuperServiceImpl<AssetBaseinfo, Long> implements AssetBaseinfoService {
	@Autowired
	private AssetBaseinfoMapper mapper;

	@Override
	public AssetBaseinfo getIDCName(String parentCode) {
		AssetBaseinfo idc = mapper.getIDCName(parentCode);

		return idc;
	}

	/**
	 * 业务字段情况
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<AssetBaseinfo> queryComboboxData(String parentCode){
		return mapper.queryComboboxData(parentCode);
	}

	public List<AssetBaseinfo> getIntentionIdcCode(){
		return mapper.getIntentionIdcCode();
	}

	@Override
	public List<AssetBaseinfo> getRootByParentCodeLike_(String parentCodeLke){
		return mapper.getRootByParentCodeLike_(parentCodeLke);
	}
	@Override
	public List<AssetBaseinfo> getChildrenByParend(String parentCode){
		return mapper.getChildrenByParend(parentCode);
	}

	@Override
	public List<Map<String, Object>> checkboxOpenMSG() {
		return mapper.checkboxOpenMSG();
	}

	@Override
	public List<AssetBaseinfo> queryComboboxDataIntoRedis() {

		return mapper.queryComboboxDataIntoRedis();
	}


}
