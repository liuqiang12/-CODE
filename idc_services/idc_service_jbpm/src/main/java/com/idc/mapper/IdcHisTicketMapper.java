package com.idc.mapper;



import com.idc.model.IdcHisTicket;
import com.idc.model.IdcHisTicketInitVo;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET:历史工单表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketMapper extends SuperMapper<IdcHisTicket, Long>{
	/**
	 * 创建历史工单信息
	 * @param idcHisTicket
	 * @throws Exception
	 */
    void createHisTicketInst(IdcHisTicket idcHisTicket) throws  Exception;

	/**
	 * 通过条件查询工单的所以信息
	 * @param condition
	 * @throws Exception
	 */
    Map<String,Object> queryTicketByCondition(Map<String, Object> condition);

	/**
	 * 通过工单实例 修改流程实例ID
	 * @param ticketInstId
	 * @param procInstId
	 * @throws Exception
	 */
    void updateHisTicketProcInstById(@Param("id") Long ticketInstId, @Param("procInstId") String procInstId) throws  Exception;

	/**
	 * 根据工单id 更新历史工单的结束时间
	 * @param ticketInstId
	 * @throws Exception
	 */
    void updateEndTimeByTicketInstId(@Param("ticketInstId") Long ticketInstId, @Param("status") Integer status, @Param("rubbish") Integer rubbish) throws  Exception;

	/**
	 * 根据流程实例修改
	 * @param ticketInstId
	 * @param procInstId
	 * @throws Exception
	 */
    void updateTicketInstIdAndProcInstById(@Param("id") Long ticketInstId, @Param("procInstId") String procInstId) throws  Exception;
	/**
	 * 查询预受理工单的相关信息
	 * @param initId
	 * @return
	 */
    IdcHisTicketInitVo queryPreTicketInfo(String initId);

	Map<String,Object> getTicketInstIdByCatalogAndProdInstId(@Param("catalog") String catalog, @Param("prodInstId") Long prodInstId);
	void updateTaskByTicketIdAndProcInstId(@Param("ticketInstId") Long ticketInstId, @Param("procInstId") Long procInstId, @Param("TASK_ID") String TASK_ID, @Param("TASK_NAME") String TASK_NAME, @Param("FORM_KEY") String FORM_KEY, @Param("PROC_DEF_ID") String PROC_DEF_ID, @Param("PROCESSDEFINITONKEY") String PROCESSDEFINITONKEY) throws Exception;
	IdcHisTicket getIdcHisTicketByIdTicketInstIdForPerission(@Param("ticketInstId") Long ticketInstId, @Param("loginId") Integer loginId);

	//通过客户id查找客户已经完成的工单
	List<Map<String,Object>> queryEndTicketByCustomerIdListPage(PageBean page);

	List<Map<String,Object>> getContractListPageData(Long prodInstId);

	//通过客户id查找客户已经完成的工单的总数
	Long queryEndTicketByCustomerIdCount(Long cusId);

	Long queryProdInstIdByTicketInstId(Long ticketInstId);

	/*//通过客户id查找客户已经完成的工单的总数
	Long customerIdCount(Long customerid);*/

	/**
	 *
	 * @param ticketInstId
	 * @return
	 */
	IdcHisTicket getTicketObjMapByTicketInstId(Long ticketInstId);

	/**
	 * 把所有的历史工单都保存到缓存中。。。
	 * @return
	 */
	List<IdcHisTicket> getHistTicketDataIntoRedis();

	IdcHisTicket getIdcHisTicketById(Long ticketInstId);

	String getIdcHisTicketCategoryById(String ticketInstId);

	String getProdCategoryById(String ticketInstId);

	String getTicketCategoryByTicket(Long ticketInstId);

	IdcHisTicket getIdcHisTicketByIdTicketInstId(Long ticketInstId);

	Map<String,Object> getCustomerByTicketInstId(Long ticketInstId);

	void updateByDeleteTicket(@Param("ticketInstId") String ticketInstId, @Param("rubbishOrDeleteMSG") String rubbishOrDeleteMSG) throws  Exception;

}

 