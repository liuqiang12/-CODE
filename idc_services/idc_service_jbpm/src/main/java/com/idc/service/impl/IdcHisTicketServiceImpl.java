package com.idc.service.impl;

import com.idc.mapper.IdcHisTicketMapper;
import com.idc.model.IdcHisTicket;
import com.idc.model.IdcHisTicketInitVo;
import com.idc.service.IdcHisTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET:历史工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcHisTicketService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcHisTicketServiceImpl extends SuperServiceImpl<IdcHisTicket, Long> implements IdcHisTicketService {

	@Autowired
	private IdcHisTicketMapper idcHisTicketMapper;

	@Override
	public Long queryProdInstIdByTicketInstId(Long ticketInstId) {

		return idcHisTicketMapper.queryProdInstIdByTicketInstId(ticketInstId);
	}

	@Override
	public Map<String,Object> queryTicketByCondition(Map<String,Object> condition){
		return idcHisTicketMapper.queryTicketByCondition(condition);
	}

	@Override
	public List<Map<String,Object>> getContractListPageData(Long prodInstId){
		List<Map<String,Object>> hisTicketMapList = idcHisTicketMapper.getContractListPageData(prodInstId);
		return hisTicketMapList;
	}

	/**
	 * 查询预受理工单的相关信息
	 * @param initId
	 * @return
	 */
	@Override
	public IdcHisTicketInitVo queryPreTicketInfo(String initId){
		return idcHisTicketMapper.queryPreTicketInfo(initId);
	}

	//通过客户id查找客户已经完成的工单
	@Override
	public List<Map<String, Object>> queryEndTicketByCustomerId(PageBean page,Map<String, Object> map) {
		page.setParams(map);
		return idcHisTicketMapper.queryEndTicketByCustomerIdListPage(page);
	}
	//通过客户id查找客户已经完成的工单的总数
	@Override
	public Long queryEndTicketByCustomerIdCount(Long cusId) {
		return idcHisTicketMapper.queryEndTicketByCustomerIdCount(cusId);
	}

	@Override
	public List<IdcHisTicket> getHistTicketDataIntoRedis() {
		return idcHisTicketMapper.getHistTicketDataIntoRedis();
	}

	@Override
	public IdcHisTicket getIdcHisTicketById(Long ticketInstId) {
		return idcHisTicketMapper.getIdcHisTicketById(ticketInstId);
	}

	@Override
	public String getIdcHisTicketCategoryById(String ticketInstId) {
		return idcHisTicketMapper.getIdcHisTicketCategoryById(ticketInstId);
	}

	@Override
	public String getProdCategoryById(String ticketInstId) {
		return idcHisTicketMapper.getProdCategoryById(ticketInstId);
	}

	@Override
	public IdcHisTicket getIdcHisTicketByIdTicketInstId(Long ticketInstId) {
		return idcHisTicketMapper.getIdcHisTicketByIdTicketInstId(ticketInstId);
	}

	@Override
	public IdcHisTicket getIdcHisTicketByIdTicketInstIdForPerission(Long ticketInstId, Integer loginId) {
		return idcHisTicketMapper.getIdcHisTicketByIdTicketInstIdForPerission(ticketInstId,loginId);
	}

	/*@Override
	public Long customerIdCount(Long customerid) {
		return idcHisTicketMapper.customerIdCount(customerid);
	}*/
}
