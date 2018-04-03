package com.idc.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcReCustomerMapper;
import com.idc.model.IdcReCustomer;
import com.idc.service.IdcReCustomerService;
import utils.typeHelper.MapHelper;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RE_CUSTOMER:客户类型信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcReCustomerService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcReCustomerServiceImpl extends SuperServiceImpl<IdcReCustomer, Long> implements IdcReCustomerService {
	@Autowired
	private IdcReCustomerMapper mapper;

	//通过工单id查询客户的所有信息
	@Override
	public List<Map<String, Object>> queryCustomerByTicketInstId(List pamas) {
		return mapper.queryCustomerByTicketInstId(pamas);
	}

	@Override
	public List<IdcReCustomer> getAllCustomer() {
		return mapper.getAllCustomer();
	}

	@Override
	public List<Map<String,Object>> queryAllCustomer() {
		return mapper.queryAllCustomer();
	}

	/*@Override
	public List<IdcReCustomer> queryGridFilterListPage(PageBean<IdcReCustomer> page, IdcReCustomer IdcReCustomer) {
		//条件情况
		page.setParams(MapHelper.queryCondition(IdcReCustomer));
		return mapper.queryGridFilterListPage(page);
	}*/

	@Override
	public List<IdcReCustomer> queryMngGridFilterListPage(PageBean<IdcReCustomer> page, IdcReCustomer idcReCustomer) {
		//条件情况
		page.setParams(MapHelper.queryCondition(idcReCustomer));
		return mapper.queryMngGridFilterListPage(page);
	}

	@Override
	public IdcReCustomer getModelByReCustomerId(Long id) {
		return mapper.getModelByReCustomerId(id);
	}
	@Override
	public IdcReCustomer getFilterModelByReCustomerId(Long id) {
		return mapper.getFilterModelByReCustomerId(id);
	}

	@Override
	public Long verifyAlias(String newAlias) throws Exception {
		return mapper.verifyAlias(newAlias);
	}

	@Override
	public void mergeInto(IdcReCustomer idcReCustomer) throws Exception {
		mapper.mergeInto(idcReCustomer);
	}

	@Override
	public void deleteRecordById(Long id) throws Exception {
		mapper.deleteRecordById(id);
	}
	@Override
	public IdcReCustomer getModelByChinaData(){
		return mapper.getModelByChinaData();
	}

}
