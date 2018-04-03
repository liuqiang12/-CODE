package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcReIpModel;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_IP_MODEL:IP租用产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReIpModelMapper extends SuperMapper<IdcReIpModel, Long>{
	/**
	 * 保存
	 * @param idcReIpModel
	 * @throws Exception
	 */
    void insertIpModel(IdcReIpModel idcReIpModel) throws  Exception;

    void updateModel(IdcReIpModel idcReIpModel) throws  Exception;

	IdcReIpModel getModelByTicketInstId(Long ticketInstId);

}

 