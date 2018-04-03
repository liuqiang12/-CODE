package com.idc.service;


import com.idc.model.IdcHisTicket;
import com.idc.model.IdcRunTicket;
import com.idc.model.ViewAuthorHisTask;
import com.idc.utils.JBPMModelKEY;
import modules.utils.ResponseJSON;
import org.springframework.context.ApplicationContext;
import system.data.page.PageBean;

import java.util.List;
import java.util.Map;


public interface ActJBPMService{
    /**
     * 通过订单Id创建工单信息
     * @param prodInstId
     * @return
     * @throws Exception
     * idcReProduct.getId(),idcReProduct.getProdCategory(),null,null,catalog,null
     */
    ResponseJSON singleCreateTicket(ApplicationContext applicationContext, Map map, Long prodInstId, Integer prodCategory, Long parentId, Long initId, String catalog, Long ticketInstId_init) throws  Exception;

    void removeAllResourceMake(ApplicationContext applicationContext, String ticketInstId, String resourceCategory) throws  Exception;

    Map<String,Object> customerViewData(String loginUserID) throws  Exception;
    /**
     * 通过订单Id创建工单信息
     * @param prodInstId
     * @return
     * @throws Exception
     */
    ResponseJSON singleBusinessCreateTicket(Long prodInstId, String catalog, String prodCategory) throws  Exception;
    void completeTaskNode(IdcRunTicket idcRunTicket, Integer status, String comment) throws  Exception;

    /**
     * 获取代办任务
     *  @param map    key: groupid
     *                需要传递两个userkey------------[username_xxxx@id_xxxxx]
     *                          groupkey------------[role_key_xxxx@id_xxxxx]
     * @throws Exception
     */
    List<Map<String,Object>> jbpmTaskQueryListPage(PageBean pageBean, Map<String, Object> map) ;

    List<IdcRunTicket> jbpmRunTicketTaskListPage(PageBean pageBean, Map<String, Object> map);

    List<IdcHisTicket> jbpmHisTicketTaskListPage(PageBean pageBean, Map<String, Object> map);

    List<Map<String,Object>> jbpmManagerViewListPage(PageBean pageBean, Map<String, Object> map);

    List<Map<String,Object>> ticketOnServer(PageBean pageBean, Map<String, Object> map);



    /**
     * 获取代办任务
     *  @param map    key: groupid
     *                需要传递两个userkey------------[username_xxxx@id_xxxxx]
     *                          groupkey------------[role_key_xxxx@id_xxxxx]
     * @throws Exception
     */
    List<Map<String,Object>> jbpmAllTaskQueryListPage(PageBean pageBean, Map<String, Object> map) ;


    /**
     * 获取代办额外信息
     */
    Map<String,Object> jbpmTaskExtraInfo(Map<String, Object> map);

    /**
     * jbpm工单部署情况
     * @param prodInstId:订单实例
     * @param jbpmModelKEY:流程定义参数
     * @param idcRunTicket:正在运行时的工单
     * @throws Exception
     */
    //void jbpmTicketDeploy(Long prodInstId, JBPMModelKEY jbpmModelKEY, IdcRunTicket idcRunTicket) throws Exception;
    Long jbpmTicketDeploy(Map map, Long prodInstId, Integer prodCategory, Long parentId, Long initId, JBPMModelKEY jbpmModelKEY, IdcRunTicket idcRunTicket, Long ticketInstId_init) throws Exception;

    /**
     * 流程引擎的部署就近原则
     * @param jbpmModelKEY
     * @throws Exception
     */
    /*void jbpmDelopy(JBPMModelKEY jbpmModelKEY) throws  Exception;*/

    void jbpmCommentDelopy(JBPMModelKEY jbpmModelKEY) throws  Exception;

    /**
     * 已办任务
     * @param pageBean
     * @param viewAuthorHisTask
     * @return
     */
    List<ViewAuthorHisTask> jbpmHisTaskQueryListPage(PageBean<ViewAuthorHisTask> pageBean, ViewAuthorHisTask viewAuthorHisTask);
    Map<String,Object> getCategoryTicketMeta(String processDefinitionKey, Long ticketInstId) ;
    Map<String,Object> getCategoryWithHisTicketMeta(String processDefinitionKey, Long ticketInstId) ;


    /**
     * 调用修改流程引擎中的相关数据
     * @param modelId
     * @param editorSourceValueId
     * @param editorSourceExtraValueId
     * @param imageName
     * @param deploymentId
     * @throws Exception
     */
    void callActUpdateResource(String modelId,
                               String editorSourceValueId,
                               String editorSourceExtraValueId,
                               String imageName,
                               String deploymentId) throws Exception;

    Map<String,Object> getTaskIdAdnTaskName(Long procInstId);

    /**
     * 修改订单当前工单的状态和工单的状态
     * @param prodInstId
     * @param procticketStatus
     * @param ticketInstId
     *
     */
    void callProcTicketStatus(Long prodInstId, Long procticketStatus, Long ticketInstId) throws Exception;
    void callJbpmProcessProcJbpmApply(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmOnlyRead(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmHis(Map<String, Object> procJbpmApplyMap);
    void callJbpmProcessProcJbpmPreCreateData(Map<String, Object> procJbpmApplyMap);
    List<Map<String,Object>> jbpmLinkedHisTicketTaskListPage(PageBean pageBean, Map<String, Object> map);

    List<Map<String,Object>> ticketSatisfactionReportData(Map<String, Object> map);

    List<Map<String,Object>> allTicketCountPageData(Map<String, Object> map);

    Map<String,Object> queryApplyerById(Long applyerId);

    Map<String,Object> callApplyResourceNum(Map<String, Object> map);

    void initProcCment(Long ticketInstId, Long prodInstId, String procInstId, String author) throws  Exception;
    /*查看按照机位分配的工单，没有选择机位的资源。安装机位分配，选择了机架必须分配机位*/
    Long queryRackIsHaveUnit(Map<String, Object> map);

    void callJbpmIntoRedis(Map<String, Object> procJbpmMap);
    void callJbpmLinkedIntoRedis(Map<String, Object> procJbpmMap);

    void callSingleTicketJbpmIntoRedis(Map<String, Object> procJbpmMap);
    void callSingleTicketJbpmIntoLinkedRedis(Map<String, Object> procJbpmMap);



    List<IdcRunTicket> managerViewTips();

    Map<String,Object> getIdcRunTicketFormRedis(String redis_key, int pageNo, int pageSize) throws Exception;
    IdcRunTicket getIdcRunTicketFromBinaryJedisCache(Long ticketInstId) throws Exception;
    Integer queryRackIsHaveUnitNEW(Long ticketInstId);

    /**
     * [流程中的数据统一保存到数据库中]
     */
    /**
     * 配置上架资源
     * @param resourceSettingData
     * @throws Exception
     */
    ResponseJSON resourceSetting(String resourceSettingData)throws Exception;

    Boolean getIExistsOtherTicket(Long ticketInstId);
    String getExistsOtherTicket(Long ticketInstId);
    String getTabsTitleName(String ticketCategory, String prodCategory);

    List<Map<String, Object>> getDataCentre();

    List<Map<String, Object>> getBuildingByLocationID(Long locationID);

    List<Map<String, Object>> getMachineRoomByBuildingID(Long buildingID);

    List<Map<String, Object>> getDataCheckTree();

    Map<String, Object> getResourceStatusByRoomID(Long roomID);

}
