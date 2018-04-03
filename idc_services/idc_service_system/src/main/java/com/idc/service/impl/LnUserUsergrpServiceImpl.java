package com.idc.service.impl;

import com.idc.mapper.LnUserUsergrpMapper;
import com.idc.model.LnUserUsergrp;
import com.idc.service.LnUserUsergrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>sys_ln_user_usergrp:用户与用户组关联表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 24,2016 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("lnUserUsergrpService")
public class LnUserUsergrpServiceImpl extends SuperServiceImpl<LnUserUsergrp, Integer> implements LnUserUsergrpService {
	@Autowired
	private LnUserUsergrpMapper mapper;

	@Override
	public void deleteByLnUserUsergrps(List<LnUserUsergrp> list) throws Exception {
		mapper.deleteByLnUserUsergrps(list);
	}

	/**
	 *   Special code can be written here 
	 */
}
