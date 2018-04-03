
package com.idc.mapper;



import com.idc.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_HIS_TICKET_RESOURCE:历史的工单资源中间表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcHisTicketResourceMapper extends SuperMapper<IdcHisTicketResource, Long>{
	/**
	 * 查询工单信息列表
	 * @param page
	 * @return
	 */
    List<IdcHisTicketResource> queryTicketRackResourceListPage(PageBean<IdcHisTicketResource> page);
	List<IdcHisTicketResource> queryTicketResourceListPage(PageBean<IdcHisTicketResource> page);

	Boolean isAllotResource(String ticketInstId);

	List<IdcHisTicketResource> getAllByTicketInstId(Long ticketInstId);

	void updatePortResourceAssignation(@Param("ticketInstId") String ticketInstId,
                                       @Param("resourceId") String resourceId,
                                       @Param("resourceCategory") String resourceCategory,
                                       @Param("inputValues") String inputValues);

	Map<String,Object> queryAllByTicketIdAndResourceId(@Param("ticketInstId") Long ticketInstId, @Param("resourceId") Long resourceId) throws  Exception;

	/**
	 * 查询工单信息列表
	 * @param page
	 * @return
	 */
    List<TicketIdcMcbVo> queryTicketRackMCBResourceListPage(PageBean<TicketIdcMcbVo> page);
	/**
	 * 更具工单查询端口信息[分页]
	 * @param page
	 * @return
	 */
    List<TicketPortVo> queryTicketPortResourceListPage(PageBean<TicketPortVo> page);
	/**
	 * 根据工单查询端口信息
	 * @param page
	 * @return
	 */
    List<TicketServerVo> queryTicketServerResourceListPage(PageBean<TicketServerVo> page);
	/**
	 * 根据工单查询端口信息
	 * @param page
	 * @return
	 */
    List<TicketIPVo> queryTicketIpResourceListPage(PageBean<TicketIPVo> page);

	/**
	 * 查询可用的主机设备
	 * @param page
	 * @return
	 */
    List<WinServerVo> queryServerResourceListPage(PageBean<WinServerVo> page);
	/**
	 * 批量保存
	 * @param winServerVoList
	 * @throws Exception
	 */
    void foreachSaveInfo(List<WinServerVo> winServerVoList) throws Exception;

	void deleteServerById(@Param("id") Long id) throws Exception;

	void delResource(@Param("ticketInstId") Long ticketInstId, @Param("resourceId") Long resourceId, @Param("resourceCategory") String resourceCategory) throws Exception;

	/**
	 * IP资源查询分页功能
	 * @param page
	 * @return
	 */
    List<TicketIPVo> queryIpResourceListPage(PageBean<TicketIPVo> page);
	/**
	 * 查询机架列表信息
	 * @param page
	 * @return
	 */
    List<TicketIdcRackVo> queryRackResourceListPage(PageBean<TicketIdcRackVo> page);

	/**
	 * 合体方法
	 * @param idcHisTicketResource
	 * @throws Exception
	 */
    void mergeInto(IdcHisTicketResource idcHisTicketResource) throws  Exception;
	String getRackNameByResourceId(Long resourceId);


    /*	NetPort 通过客户的名称查询  工单id+资源端口名称*/
    List<Map<String,Object>> queryTicketIdAndResourceNameByNetPort(@Param("cusName") String cusName, @Param("rowNum") Integer rowNum) throws Exception;
    List<Map<String,Object>> queryTicketIdAndResourceNameByNetPortPage(PageBean<T> page) throws Exception;

    /*	Rack 通过客户的名称查询  工单id+资源端口名称*/
    List<Map<String,Object>> queryTicketIdAndResourceNameByRack(@Param("cusName") String cusName, @Param("rowNum") Integer rowNum) throws Exception;
    List<Map<String,Object>> queryTicketIdAndResourceNameByRackPage(PageBean<T> page) throws Exception;

    /*	IP 通过客户的名称查询  工单id+资源端口名称*/
    List<Map<String,Object>> queryTicketIdAndResourceNameByIP(@Param("cusName") String cusName, @Param("rowNum") Integer rowNum) throws Exception;
    List<Map<String,Object>> queryTicketIdAndResourceNameByIPPage(PageBean<T> page) throws Exception;
    /**
     */
	void callUpdateTicketResourceAttribute(Map<String, Object> map) throws Exception;

	void updateResource(Long ticketInstId) throws Exception;


	List<Map<String,Object>> queryAllResourceByTicketInstId(@Param("ticketInstId") String ticketInstId, @Param("resourceCategory") String resourceCategory) throws Exception;

	void callResouceAbout(Map<String, Object> map) throws Exception;

	void updateRackUnitFree(@Param("rackId") String rackId, @Param("ticketInstId") String ticketInstId) throws Exception;

	List<Map<String,Object>> findMCBByRackTicket(String ticketInstId) throws Exception;

	void deleteResourceByResourceCategory(@Param("resourceCategory") String resourceCategory, @Param("ticketInstId") String ticketInstId) throws Exception;

	void deleteMCBResourceByTicket(String ticketInstId) throws Exception;

	List<Map<String,Object>> queryResourceByResource(@Param("ticketInstId") Long ticketInstId, @Param("isDeleteTicket") Long isDeleteTicket) throws Exception;

	List<Map<String,Object>> getResourceById(String ticketInstId);

	List<IdcHisTicket> getContractListPageData(Map<String, Object> params) throws Exception;


}

 