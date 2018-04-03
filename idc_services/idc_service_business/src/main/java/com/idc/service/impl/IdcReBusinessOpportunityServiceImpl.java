package com.idc.service.impl;

import com.idc.mapper.IdcReBusinessOpportunityMapper;
import com.idc.model.IdcReBusinessOpportunity;
import com.idc.service.IdcReBusinessOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.Date;
import java.util.UUID;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RE_BUSINESS_OPPORTUNITY:业务商机表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 15,2018 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@Service("idcReBusinessOpportunityService")
public class IdcReBusinessOpportunityServiceImpl extends SuperServiceImpl<IdcReBusinessOpportunity, String> implements IdcReBusinessOpportunityService {
	@Autowired
	private IdcReBusinessOpportunityMapper idcReBusinessOpportunityMapper;
}
