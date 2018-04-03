package com.idc.service.impl;

import com.idc.mapper.IdcDeviceMapper;
import com.idc.mapper.IdcHisTicketResourceMapper;
import com.idc.mapper.IdcRackMapper;
import com.idc.mapper.NetPortMapper;
import com.idc.model.*;
import com.idc.service.IdcHisTicketResourceService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_RESOURCE:历史的工单资源中间表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcHisTicketResourceService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcHisTicketResourceServiceImpl extends SuperServiceImpl<IdcHisTicketResource, Long> implements IdcHisTicketResourceService {
	@Autowired
	private IdcHisTicketResourceMapper idcHisTicketResourceMapper;
	@Autowired
	private IdcDeviceMapper idcDeviceMapper;
	@Autowired
	private IdcRackMapper idcRackMapper;
	@Autowired
    private NetPortMapper netPortMapper;

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByNetPort(String customerName,Integer rowNum) throws Exception {
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByNetPort(customerName,rowNum);
	}

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByNetPortPage(PageBean<T> page, Object param) throws Exception {
		page.setParams(MapHelper.queryCondition(param));
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByNetPortPage(page);
	}

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByRack(String customerName,Integer rowNum) throws Exception {
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByRack(customerName,rowNum);
	}

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByRackPage(PageBean<T> page, Object param) throws Exception {
		page.setParams(MapHelper.queryCondition(param));
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByRackPage(page);
	}

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByIP(String customerName,Integer rowNum) throws Exception {
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByIP(customerName,rowNum);

	}

	@Override
	public List<Map<String, Object>> queryTicketIdAndResourceNameByIPPage(PageBean<T> page, Object param) throws Exception {
		page.setParams(MapHelper.queryCondition(param));
		return idcHisTicketResourceMapper.queryTicketIdAndResourceNameByIPPage(page);
	}

	@Override
	public List<IdcHisTicketResource> queryTicketRackResourceListExtraPage(PageBean<IdcHisTicketResource> page, Map<String,Object> map){
		page.setParams(map);
		List<IdcHisTicketResource> list = idcHisTicketResourceMapper.queryTicketRackResourceListPage(page);
		/*分页显示*/

		return list;
	}

	@Override
	public List<IdcHisTicketResource> queryTicketResourceListExtraPage(PageBean<IdcHisTicketResource> page, Map<String, Object> map) {
		page.setParams(map);
		List<IdcHisTicketResource> list = idcHisTicketResourceMapper.queryTicketResourceListPage(page);
		/*分页显示*/

		return list;
	}

	/**
	 * 查询工单信息列表
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIdcMcbVo> queryTicketRackMCBResourceListExtraPage(PageBean<TicketIdcMcbVo> page, Map<String,Object> map) {
		page.setParams(map);
		List<TicketIdcMcbVo> list = idcHisTicketResourceMapper.queryTicketRackMCBResourceListPage(page);
		/*分页显示*/

		return list;
	}
	/**
	 * 查询工单信息列表
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketPortVo> queryTicketPortResourceListExtraPage(PageBean<TicketPortVo> page, Map<String,Object> map) {
		page.setParams(map);
		List<TicketPortVo> list = idcHisTicketResourceMapper.queryTicketPortResourceListPage(page);
		/*分页显示*/

		return list;
	}
	/**
	 * 根据工单查询端口信息
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketServerVo> queryTicketServerResourceListExtraPage(PageBean<TicketServerVo> page, Map<String,Object> map){
		page.setParams(map);
		List<TicketServerVo> list = idcHisTicketResourceMapper.queryTicketServerResourceListPage(page);
		/*分页显示*/

		return list;
	}
	/**
	 * 根据工单查询ip信息
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIPVo> queryTicketIPSesourceListExtraPage(PageBean<TicketIPVo> page, Map<String,Object> map){
		page.setParams(map);
		List<TicketIPVo> list = idcHisTicketResourceMapper.queryTicketIpResourceListPage(page);
		/*分页显示*/

		return list;
	}

	@Override
	public List<WinServerVo> queryServerResourceListPage(PageBean<WinServerVo> page, Map<String, Object> map) {
		page.setParams(map);
		List<WinServerVo> list = idcHisTicketResourceMapper.queryServerResourceListPage(page);
		/*分页显示*/

		return list;
	}

	/**
	 * IP资源查询分页功能
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TicketIPVo> queryIpResourceListPage(PageBean<TicketIPVo> page,Map<String,Object> map){
		page.setParams(map);
		List<TicketIPVo> list = idcHisTicketResourceMapper.queryIpResourceListPage(page);
		return list;
	}
	/**
	 * 查询机架列表信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<TicketIdcRackVo> queryRackResourceListPage(PageBean<TicketIdcRackVo> page,Map<String,Object> map){
		page.setParams(map);
		List<TicketIdcRackVo> list = idcHisTicketResourceMapper.queryRackResourceListPage(page);
		return list;
	}

	@Override
	public String getNameByResourceId(Long resourceId,String categroy) {
		//类型区分  如果是机房
		if("100".equals(categroy)){
			return idcHisTicketResourceMapper.getRackNameByResourceId(resourceId);
		}else{
			return null;
		}
	}
}


