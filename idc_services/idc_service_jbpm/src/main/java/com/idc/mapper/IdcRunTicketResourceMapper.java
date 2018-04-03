package com.idc.mapper;


import com.idc.model.*;
import org.apache.ibatis.annotations.Param;
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
public interface IdcRunTicketResourceMapper extends SuperMapper<IdcRunTicketResource, Long>{
	/**
	 * 查询工单信息列表
	 * @param page
	 * @return
	 */
    List<IdcRunTicketResource> queryTicketRackResourceListPage(PageBean<IdcRunTicketResource> page);
	List<IdcRunTicketResource> queryTicketResourceListPage(PageBean<IdcRunTicketResource> page);

	void deleteMCBResourceByTicket(String ticketInstId) throws Exception;

	void deleteResourceByResourceCategory(@Param("resourceCategory") String resourceCategory, @Param("ticketInstId") String ticketInstId) throws Exception;
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

	/**
	 * 删除机架，同时删除机架对应的u位
	 * @param resourceId
	 * @throws Exception
	 */
    void deleteServerByIdCall(@Param("resourceId") Long resourceId, @Param("ticketInstId") Long ticketInstId) throws Exception;

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

	void mergeInto(IdcRunTicketResource idcRunTicketResource) throws Exception;

	/**
	 * 查询id
	 * @param winServerVo
	 * @return
	 * @throws Exception
	 */
    String findId(WinServerVo winServerVo) throws Exception;
	/**
	 * 修改状态是可用
	 * @param deviceId
	 * @throws Exception
	 */
	void singleUpdateInfoById(@Param("resourceId") Long deviceId, @Param("status") Long status) throws Exception;
	String loadRoomsWithTicket(Long ticketInstId);

	List<String> loadRackIDWithTicket(Long ticketInstId);

	/**
	 *
	 * @param ticketResourceId
	 * @param resourceId
	 * @throws Exception
	 */
	void callTicketResourceStatus(@Param("ticketResourceId") Long ticketResourceId,
                                  @Param("resourceId") Long resourceId,
                                  @Param("status") Integer status,
                                  @Param("force") Integer force,
                                  @Param("category") String category) throws Exception;

	String loadReCustomerName(@Param("ticketInstId") Long ticketInstId);

	/**
	 * @param paramMap
	 */
	void calldDeleteTicketResource(Map<String, Object> paramMap);

	List<Long> getUnitIdsByRackId(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId);

	void updatePortResourceAssignation(@Param("ticketInstId") String ticketInstId,
                                       @Param("resourceId") String resourceId,
                                       @Param("resourceCategory") String resourceCategory,
                                       @Param("inputValues") String inputValues);

	Boolean portHaveAssignation(String ticketInstId);

	List<Long> getMCBIdsByRackId(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId);

	List<Long> getMCBIdByRackIdAndTicket(@Param("rackId") String rackId, @Param("ticketInstId") String ticketInstId);

	List<Map<String,Object>> getMCBIdsByResourceid(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId, @Param("ticketCategory") String ticketCategory);

	List<Long> getIPIdsByResourceid(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId);

	List<Map> getResourceIdsByTicketId(Map<String, Object> paramMap);

	List<Long> getUnitResourceIdsByTicketId(Map<String, Object> paramMap);

	List<Map<String,Object>> getIpResourceIdsByTicketId(Map<String, Object> map);

	List<Long> getServerIdsByResourceid(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId);

	List<Long> getPortIdsByResourceid(@Param("resourceid") String resourceid, @Param("ticketInstId") String ticketInstId);

	List<Map<String,Object>> getResourceByTicket(@Param("ticketInstId") Long ticketInstId);

	List<Long> getRackIdResourceByTicket(@Param("ticketInstId") Long ticketInstId);

	List<Long> getRackUnitResourceByTicket(@Param("ticketInstId") Long ticketInstId, @Param("rackId") Long rackId);

	String getRackorracunit(Map<String, Object> paramMap);


	List<IdcRunTicketResource> getIdcRunTicketResourceByCustomerId(Long ticketInstId);
	String getSingleRackId(Long customerId);
	Long getSingleBandWidth(Long ticketInstId);
	List<IdcRunTicketResource> loadIpTranList(Long ticketInstId);
	List<Map<String,Object>> getResourceById(String ticketInstId);

	List<Map<String,Object>> getResourceRackUnitById(String ticketInstId);

	List<Map<String,Object>> getReleaseResource(String ticketInstId);

	List<Map<String,Object>> getReleaseResourceRackUnit(String ticketInstId);

	void updateResource(Long ticketInstId) throws Exception;


}

 