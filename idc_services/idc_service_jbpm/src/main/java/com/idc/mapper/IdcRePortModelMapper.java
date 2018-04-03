package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcRePortModel;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_PORT_MODEL:IDC_RE_PORT_MODEL端口带宽产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcRePortModelMapper extends SuperMapper<IdcRePortModel, Long>{
	/**
	 *
	 * @param idcRePortModel
	 * @throws Exception
	 */
    void insertPortModel(IdcRePortModel idcRePortModel) throws  Exception;

    void updateModel(IdcRePortModel idcRePortModel) throws  Exception;

	IdcRePortModel getModelByTicketInstId(Long ticketInstId);

}

 