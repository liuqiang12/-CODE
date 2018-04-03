package com.idc.mapper;

 

import com.idc.model.IdcContract;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_CONTRACT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcContractMapper extends SuperMapper<IdcContract, Long>{
	List<IdcContract> queryGridListPage(PageBean<IdcContract> page);


	//查询合同到期还剩余不足七天的工单号和具体剩余的天数
	List<Map<String,Object>> queryContractRemainingDays();

	//合同信息查询
	List<Map<String,Object>> queryContractByUserListPage(PageBean page);


	/**
	 * 合体方法
	 * @param idcContract
	 * @throws Exception
	 */
    void mergeInto(IdcContract idcContract) throws Exception;

	/**
	 * 通过ID获取相应的实体类
	 * @param id
	 * @return
	 */
    IdcContract getModelByContractId(Long id);

	/**
	 * 通过ID删除的实体类
	 * @param id
	 * @throws Exception
	 */
    void deleteRecordById(Long id) throws Exception;
	/**
	 * 通过工单实例获取合同信息
	 * @param ticketInstId
	 * @return
	 */
    IdcContract getContractByTicketInstId(Long ticketInstId);

	List<IdcContract> getIdcContractDataIntoRedis();

	Long verifyContractRepeat(String contractNo);

	String querySonTicketIsEnd(@Param("prodInstId") String prodInstId, @Param("ticketInstId") String ticketInstId);

	Boolean getIsHasContract(Long ticketInstId);

	Long insertIdcContract(IdcContract idcContract) throws Exception;

	void updateIdcContract(IdcContract idcContract) throws Exception;

	void updateIdcContractByTicket(IdcContract idcContract) throws Exception;


}

 