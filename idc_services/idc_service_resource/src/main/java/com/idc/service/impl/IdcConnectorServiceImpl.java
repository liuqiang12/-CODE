package com.idc.service.impl;

import com.idc.mapper.IdcConnectorMapper;
import com.idc.model.IdcConnector;
import com.idc.service.IdcConnectorService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_CONNECTOR:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcConnectorService")
public class IdcConnectorServiceImpl extends SuperServiceImpl<IdcConnector, Long> implements IdcConnectorService {
	@Autowired
	private IdcConnectorMapper mapper;

	@Override
    public List<Map<String, Object>> queryAllIdcConnectorInfoList(IdcConnector idcConnector) {
        return mapper.queryAllIdcConnectorInfoList(idcConnector);
    }

	@Override
	public List<Map<String, Object>> queryIdcConnectorInfoListPage(PageBean<T> page, Object param) {
		//这里讲查询条件进行处理
		page.setParams(MapHelper.queryCondition(param));
		return mapper.queryIdcConnectorInfoListPage(page);
	}

	@Override
	public List<Map<String, Object>> getAllIdcConnectorInfo() {
		return mapper.getAllIdcConnectorInfo();
	}

	@Override
	public Map<String, Object> getMapModelById(int id) {
		return mapper.getMapModelById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(IdcConnector idcConnector) {
		return mapper.updateByPrimaryKeySelective(idcConnector);
	}

	@Override
	public void saveIdcConnector(IdcConnector idcConnector) {
		mapper.saveIdcConnector(idcConnector);
	}

    /*获取已绑定的端子信息 通过机架ID*/
    @Override
    public List<IdcConnector> queryBindedConnectorList(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryBindedConnectorList(page);
    }

	/*通过端子IDS修改端子状态*/
	@Override
	public void updateStatusByConnIds(Map<String,Object> map) throws Exception {
		mapper.updateStatusByConnIds(map);
	}
}

