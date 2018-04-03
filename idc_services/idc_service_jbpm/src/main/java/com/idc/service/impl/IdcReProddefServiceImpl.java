package com.idc.service.impl;

import com.idc.mapper.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.idc.model.*;
import com.idc.utils.DemandContains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcReProddefMapper;
import com.idc.service.IdcReProddefService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RE_PRODDEF:产品模型分类表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcReProddefService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcReProddefServiceImpl extends SuperServiceImpl<IdcReProddef, Long> implements IdcReProddefService {
	@Autowired
	private IdcReProddefMapper mapper;
	@Autowired
	private IdcReRackModelMapper idcReRackModelMapper;
	@Autowired
	private IdcRePortModelMapper idcRePortModelMapper;
	@Autowired
	private IdcReIpModelMapper idcReIpModelMapper;
	@Autowired
	private IdcReServerModelMapper idcReServerModelMapper;
	@Autowired
	private IdcReNewlyModelMapper idcReNewlyModelMapper;

	@Override
	public void updateFirstDemandByTicketId(Map<String,Object> idcTicketDemandMap){
		String idName = String.valueOf(idcTicketDemandMap.get("idName"));
		String idValue = String.valueOf(idcTicketDemandMap.get("idValue"));
		String ticketInstId = String.valueOf(idcTicketDemandMap.get("ticketInstId"));
		String prodInstId = String.valueOf(idcTicketDemandMap.get("prodInstId"));
		/*！！！！！！关键来了！！！！！！！！！
		* 所有的idName这个字段，前面first_newl_name的‘first_newl_’这个都是重复的，只有
		* 它后面的名字才是和实体类对应的，所有要把前面的‘first_newl_’这11位去掉,而这前面的11位可以确定
		* 此字段是哪个的，是机架的？  IP的？  主机的？  端口的？ 还是增值业务的？
		* */
		String newIDValue = idName.substring(11, idName.length());   //
		String idNameType = idName.substring(0, 11);

		if(idNameType.equals(DemandContains.此字段属于机架.value())){
			updateRack(newIDValue, idValue, ticketInstId, prodInstId);
		}else if(idNameType.equals(DemandContains.此字段属于端口.value())){
			updatePort(newIDValue, idValue, ticketInstId, prodInstId);
		}else if(idNameType.equals(DemandContains.此字段属于IP.value())){
			updateIP(newIDValue, idValue, ticketInstId, prodInstId);
		}else if(idNameType.equals(DemandContains.此字段属于主机.value())){
			updateServer(newIDValue, idValue, ticketInstId, prodInstId);
		}else if(idNameType.equals(DemandContains.此字段属于增值业务.value())){
			updateNewLy(newIDValue, idValue, ticketInstId, prodInstId);
		}
	}

	@Override
	public void updateTestJbpmDemand(Map<String, Object> idcTickeTestDemandMap) {

		String idName = String.valueOf(idcTickeTestDemandMap.get("idName"));
		String idValue = String.valueOf(idcTickeTestDemandMap.get("idValue"));
		String ticketInstId = String.valueOf(idcTickeTestDemandMap.get("ticketInstId"));
		String prodInstId = String.valueOf(idcTickeTestDemandMap.get("prodInstId"));
		String newIDValue = idName.substring(11, idName.length());   //
		String idNameType = idName.substring(0, 11);


		IdcReServerModel idcReServerModel=new IdcReServerModel();
		if(idName != null && idName.equalsIgnoreCase("idcReServerModel_MEM")){
			idcReServerModel.setMEM(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_memory")){
			idcReServerModel.setMemory(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_OS")){
			idcReServerModel.setOS(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_typeMode")){
			idcReServerModel.setTypeMode(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_rackUnit")){
			idcReServerModel.setRackUnit(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_specNumber")){
			idcReServerModel.setSpecNumber(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_desc")){
			idcReServerModel.setDesc(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("idcReServerModel_CPU")){
			idcReServerModel.setCPU(idValue);
		}
		try {
			idcReServerModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcReServerModelMapper.updateModel(idcReServerModel);
		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	public void updateRack(String idName,String idValue,String ticketInstId,String prodInstId){
		IdcReRackModel idcReRackModel=new IdcReRackModel();
		if(idName != null && idName.equalsIgnoreCase("spec")){
			idcReRackModel.setSpec(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("rackNum")){
			idcReRackModel.setRackNum(Long.valueOf(idValue));
		}else if(idName != null && idName.equalsIgnoreCase("supplyType")){
			idcReRackModel.setSupplyType(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("desc")){
			idcReRackModel.setDesc(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("seatNum")){
			idcReRackModel.setSeatNum(Long.valueOf(idValue));
		}
		try {
			idcReRackModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcReRackModelMapper.updateModel(idcReRackModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updatePort(String idName,String idValue,String ticketInstId,String prodInstId){
		IdcRePortModel idcRePortModel=new IdcRePortModel();
		if(idName != null && idName.equalsIgnoreCase("portMode")){
			idcRePortModel.setPortMode(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("bandwidth")){
			idcRePortModel.setBandwidth(Long.valueOf(idValue));
		}else if(idName != null && idName.equalsIgnoreCase("num")){
			idcRePortModel.setNum(Long.valueOf(idValue));
		}else if(idName != null && idName.equalsIgnoreCase("desc")){
			idcRePortModel.setDesc(idValue);
		}
		try {
			idcRePortModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcRePortModelMapper.updateModel(idcRePortModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateIP(String idName,String idValue,String ticketInstId,String prodInstId){
		IdcReIpModel idcReIpModel=new IdcReIpModel();
		if(idName != null && idName.equalsIgnoreCase("portMode")){
			idcReIpModel.setPortMode(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("num")){
			idcReIpModel.setNum(Long.valueOf(idValue));
		}else if(idName != null && idName.equalsIgnoreCase("desc")){
			idcReIpModel.setDesc(idValue);
		}
		try {
			idcReIpModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcReIpModelMapper.updateModel(idcReIpModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateServer(String idName,String idValue,String ticketInstId,String prodInstId){
		IdcReServerModel idcReServerModel=new IdcReServerModel();
		if(idName != null && idName.equalsIgnoreCase("typeMode")){
			idcReServerModel.setTypeMode(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("specNumber")){
			idcReServerModel.setSpecNumber(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("num")){
			idcReServerModel.setNum(Long.valueOf(idValue));
		}else if(idName != null && idName.equalsIgnoreCase("desc")){
			idcReServerModel.setDesc(idValue);
		}
		try {
			idcReServerModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcReServerModelMapper.updateModel(idcReServerModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateNewLy(String idName,String idValue,String ticketInstId,String prodInstId){
		IdcReNewlyModel idcReNewlyModel=new IdcReNewlyModel();
		if(idName != null && idName.equalsIgnoreCase("name")){
			idcReNewlyModel.setName(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("category")){
			idcReNewlyModel.setCategory(idValue);
		}else if(idName != null && idName.equalsIgnoreCase("desc")){
			idcReNewlyModel.setDesc(idValue);
		}
		try {
			idcReNewlyModel.setTicketInstId(Long.valueOf(ticketInstId));
			idcReNewlyModelMapper.updateModel(idcReNewlyModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param category:类型
	 * @param prodInstId:服务实例[产品实例]
	 * @return
	 */
	public Map<String,Object> getModelByCategory(String category, Long prodInstId,Long ticketInstId){
		 return mapper.getModelByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public IdcReRackModel getModelRackToBeanByCategory(String category, Long prodInstId,Long ticketInstId) {
		return mapper.getModelRackToBeanByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public IdcRePortModel getModelPortToBeanByCategory(String category, Long prodInstId,Long ticketInstId) {
		return mapper.getModelPortToBeanByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public IdcReIpModel getModelIpToBeanByCategory(String category, Long prodInstId,Long ticketInstId) {
		return mapper.getModelIpToBeanByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public IdcReServerModel getModelServerToBeanByCategory(String category, Long prodInstId,Long ticketInstId) {
		return mapper.getModelServerToBeanByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public IdcReNewlyModel getModelAddNewlyToBeanByCategory(String category, Long prodInstId,Long ticketInstId) {
		return mapper.getModelAddNewlyToBeanByCategory(category,prodInstId,ticketInstId);
	}

	@Override
	public ServiceApplyImgStatus getSelfModelByProdInstId(Long prodInstId) {
		return mapper.getSelfModelByProdInstId(prodInstId);
	}
}
