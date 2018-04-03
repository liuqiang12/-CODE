package com.idc.mapper;



import com.idc.model.*;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_PRODDEF:产品模型分类表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReProddefMapper extends SuperMapper<IdcReProddef, Long>{
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	Map<String,Object> getModelByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);

	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReRackModel getModelRackToBeanByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcRePortModel getModelPortToBeanByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReIpModel getModelIpToBeanByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReServerModel getModelServerToBeanByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);
	/**
	 *
	 * @param category
	 * @param prodInstId
	 * @return
	 */
	IdcReNewlyModel getModelAddNewlyToBeanByCategory(@Param("category") String category, @Param("prodInstId") Long prodInstId, @Param("ticketInstId") Long ticketInstId);

	/**
	 *
	 * @param prodInstId
	 * @return
	 */
    void deleteWithProcedureByProdInstId(Long prodInstId) throws  Exception;

	/**
	 * 保存实体类  返回主键目的是保存子表
	 * @param idcReProddef
	 * @return
	 * @throws Exception
	 */
    void insertModel(IdcReProddef idcReProddef) throws  Exception;

	/**
	 * 根据订单ID查询服务类型
	 * @param prodInstId
	 * @return
	 */
    ServiceApplyImgStatus getSelfModelByProdInstId(Long prodInstId);

	List<IdcReProddef> getAllByTicketInstId(Long ticketInstId);

}

 