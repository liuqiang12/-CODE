package com.idc.mapper;



import java.math.BigDecimal;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcReNewlyModel;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_NEWLY_MODEL:IDC_RE_NEWLY_BUSINESS_MODEL新增业务产品申请表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReNewlyModelMapper extends SuperMapper<IdcReNewlyModel, Long>{
	/**
	 * 保存实体类
	 * @param idcReNewlyModel
	 * @throws Exception
	 */
    void insertNewlyModel(IdcReNewlyModel idcReNewlyModel) throws  Exception;

    void updateModel(IdcReNewlyModel idcReNewlyModel) throws  Exception;

	IdcReNewlyModel getModelByTicketInstId(Long ticketInstId);

}

 