package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcReServerModel;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_SERVER_MODEL:IDC_RE_SERVER_MODEL主机租凭产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReServerModelMapper extends SuperMapper<IdcReServerModel, Long>{
	/**
	 * 保存
	 * @param idcReServerModel
	 * @throws Exception
	 */
    void insertServerModel(IdcReServerModel idcReServerModel) throws  Exception;

    void updateModel(IdcReServerModel idcReServerModel) throws  Exception;

	IdcReServerModel getModelByTicketInstId(Long ticketInstId);
}

 