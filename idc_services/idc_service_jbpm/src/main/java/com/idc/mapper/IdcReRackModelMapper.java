package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcReRackModel;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_RACK_MODEL:IDC_RE_RACK机架机位产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReRackModelMapper extends SuperMapper<IdcReRackModel, Long>{
	/**
	 * 保存扩展信息
	 * @param idcReRackModel
	 * @throws Exception
	 */
    void insertRackModel(IdcReRackModel idcReRackModel) throws  Exception;

    void updateModel(IdcReRackModel idcReRackModel) throws  Exception;

	IdcReRackModel getModelByTicketInstId(Long ticketInstId);
}

 