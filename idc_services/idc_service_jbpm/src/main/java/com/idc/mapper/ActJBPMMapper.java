package com.idc.mapper;

import com.idc.model.IdcHisTicket;
import com.idc.model.IdcRunTicket;
import com.idc.model.TicketRackGrid;
import com.idc.model.ViewAuthorHisTask;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/6/23.
 */
public interface ActJBPMMapper {

    /**
     * 获取代办任务
     *            两个参数   userkey------------[username_xxxx@id_xxxxx]
     *                     groupkey------------[role_key_xxxx@id_xxxxx]
     */
    List<Map<String,Object>> jbpmTaskQueryListPage(PageBean pageBean) ;/**

     /**
     * 获取代办任务
     *            两个参数   userkey------------[username_xxxx@id_xxxxx]
     *                     groupkey------------[role_key_xxxx@id_xxxxx]
     */
    List<Map<String,Object>> jbpmAllTaskQueryListPage(PageBean pageBean) ;
    List<IdcRunTicket> jbpmRunTicketTaskListPage(PageBean page);
    List<IdcHisTicket> jbpmHisTicketTaskListPage(PageBean page);
    List<Map<String,Object>> jbpmManagerViewListPage(PageBean page);

    List<Map<String,Object>> ticketSatisfactionReportData(Map<String, Object> map);

   // Map<String, Object> allTicketCountPageData(Map<String, Object> map);
    List<Map<String,Object>> allTicketCountPageData(Map<String,Object> map);

    List<Map<String,Object>> getDataCentre();

    List<Map<String,Object>> getBuildingByLocationID(Long locationID);

    List<Map<String,Object>> getMachineRoomByBuildingID(Long buildingID);
    /**
     * 获取代办额外信息
     */
    Map<String,Object> jbpmTaskExtraInfo(Map<String, Object> map);
     /**
     * 客户经理视图查询待处理工单和发起的工单
     */
    Map<String,Object> findTicketPendingQuickly(Map<String, Object> map);
    /**
     * 客户经理视图查询发起的工单
     */
    Map<String,Object> findTicketWaitQuickly(Map<String, Object> map);


    List<Map<String,Object>> getRackStatusByRoomID(@Param("roomID") Long roomID);
    List<Map<String,Object>> getRackUnitStatusByRoomID(@Param("roomID") Long roomID);
    List<Map<String,Object>> getPortStatusByRoomID(@Param("roomID") Long roomID);
    List<Map<String,Object>> getIPStatusByRoomID();
    List<Map<String,Object>> getServerStatusByRoomID(@Param("roomID") Long roomID);
    List<Map<String,Object>> getMCBStatusByRoomID(@Param("roomID") Long roomID);


    List<Map<String,Object>> ticketOnServerListPage(PageBean pageBean) ;

    /**
     *
     */
    Map<String,Object> callApplyResourceNum(Map<String, Object> map);
    /**
     * 已办任务
     * @param pageBean
     * @return
     */
    List<ViewAuthorHisTask> jbpmHisTaskQueryListPage(PageBean<ViewAuthorHisTask> pageBean) ;

    /**
     * 调用修改流程引擎中的相关数据
     * @param modelId
     * @param editorSourceValueId
     * @param editorSourceExtraValueId
     * @param imageName
     * @param deploymentId
     * @throws Exception
     */
    void callActUpdateResource(@Param("modelId") String modelId,
                               @Param("editorSourceValueId") String editorSourceValueId,
                               @Param("editorSourceExtraValueId") String editorSourceExtraValueId,
                               @Param("imageName") String imageName,
                               @Param("deploymentId") String deploymentId) throws Exception;

    Map<String,Object> getTaskIdAdnTaskName(Long procInstId);
    /**
     * 修改订单当前工单的状态和工单的状态
     * @param prodInstId
     * @param procticketStatus
     * @param ticketInstId
     */
    void callProcTicketStatus(@Param("prodInstId") Long prodInstId, @Param("procticketStatus") Long procticketStatus, @Param("ticketInstId") Long ticketInstId) throws Exception;
    void callJbpmProcessProcJbpmApply(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmOnlyRead(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmHis(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmPreCreateData(Map<String, Object> procJbpmApplyMap);

    List<Map<String,Object>> jbpmLinkedHisTicketTaskListPage(PageBean pageBean);

    Map<String,Object> queryApplyerById(Long applyerId);

    Map<String,Object> customerViewData(String loginUserID);

    void callJbpmIntoRedis(Map<String, Object> procJbpmMap);
    void callJbpmLinkedIntoRedis(Map<String, Object> procJbpmMap);


    /*查看按照机位分配的工单，没有选择机位的资源。安装机位分配，选择了机架必须分配机位*/
    Long queryRackIsHaveUnit(Map<String, Object> map);
    Integer queryRackIsHaveUnitNEW(Long ticketInstId);

    void callSingleTicketJbpmIntoRedis(Map<String, Object> procJbpmMap);
    void callSingleTicketJbpmIntoLinkedRedis(Map<String, Object> procJbpmMap);

    TicketRackGrid getTicketRackGridInfoByResourceId(Long resourceid);
    void callProcTicketResourceSaveArry(Map<String, Object> definedArrayMap) throws Exception;
    Boolean getIExistsOtherTicket(Long ticketInstId);
    String getExistsOtherTicket(Long ticketInstId);
    String getTabsTitleName(@Param("ticketCategory") String ticketCategory, @Param("prodCategory") String prodCategory);
}
