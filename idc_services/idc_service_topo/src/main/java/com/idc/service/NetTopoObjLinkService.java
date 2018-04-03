package com.idc.service;
import java.math.BigDecimal;
import java.util.List;

import com.idc.model.ExecuteResult;
import system.data.supper.service.SuperService;

import com.idc.model.NetTopoObjLink;



/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>NET_TOPO_OBJ_LINK:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetTopoObjLinkService extends SuperService<NetTopoObjLink, Long>{
    ExecuteResult add(NetTopoObjLink link);
    /**
	 *   Special code can be written here 
	 */
}
