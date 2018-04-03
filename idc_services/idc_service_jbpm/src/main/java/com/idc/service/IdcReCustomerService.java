package com.idc.service;

import com.idc.model.IdcReCustomer;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>IDC_RE_CUSTOMER:客户类型信息<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */

public interface IdcReCustomerService extends SuperService<IdcReCustomer, Long>{
	/*
		抽取了一部分的数据显示
	 */
	/*public List<IdcReCustomer> queryGridFilterListPage(PageBean<IdcReCustomer> page, IdcReCustomer IdcReCustomer) ;*/

	/**
	 * 管理界面
	 * @param page
	 * @param IdcReCustomer
	 * @return
	 */
    List<IdcReCustomer> queryMngGridFilterListPage(PageBean<IdcReCustomer> page, IdcReCustomer IdcReCustomer) ;

	IdcReCustomer getFilterModelByReCustomerId(Long id);
	/*通过ID获取相应的实体类*/
    IdcReCustomer getModelByReCustomerId(Long id);
	/* 修改和删除的合体 */
    void mergeInto(IdcReCustomer idcReCustomer) throws Exception;

	/*通过id删除合同*/
    void deleteRecordById(Long id) throws Exception;

	/*通过id删除合同*/
    Long verifyAlias(String newAlias) throws Exception;

	/**
	 * 获取移动客户
	 * @return
	 */
	IdcReCustomer getModelByChinaData();

	List<Map<String,Object>> queryAllCustomer();

	//通过工单id查询客户的所有信息
	List<Map<String,Object>> queryCustomerByTicketInstId(List pamas);
	List<IdcReCustomer> getAllCustomer();

}
