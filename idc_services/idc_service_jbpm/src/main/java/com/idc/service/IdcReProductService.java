package com.idc.service;

import com.idc.model.*;
import org.springframework.context.ApplicationContext;
import system.data.page.PageBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IdcReProductService  {
    /**
     * 管理界面
     * @param page
     * @param idcReProduct
     * @return
     */
    List<IdcReProduct> queryGridFilterListPage(PageBean<IdcReProduct> page, IdcReProduct idcReProduct) ;

    /**
     * 通过ID获取实体类信息
     * @param id
     * @return
     */
    IdcReProduct getModelByProductId(Long id);
    IdcReProduct getFilterModelByProductId(Long id);
    ProductCategory getProductCategoryByProdInstId(String prodInstId, Long ticketInstId);


    /**
     * 通过ID获取实体类信息[返回值附带类型实体]
     * @param id
     * @return
     */
    IdcReProduct getModelWithCatalogModelByProductId(Long id);


    /**
     * 合体方法
     * @param idcReProduct
     * @throws Exception
     */
    Long mergeInto(IdcReProduct idcReProduct, Map<String, Object> createTicketMap, ApplicationContext applicationContext) throws Exception;

    String getIdcNameByTicketID(String ticketInstId) throws Exception;

    /**
     *
     * @param idcReProduct
     * @param applicationContext
     * @return
     * @throws Exception
     */

    Map<String,Object> createJbpmEntrance(IdcReProduct idcReProduct, ApplicationContext applicationContext) throws Exception;
    Long createJbpmEntranceForCreateTicet(IdcReProduct idcReProduct, ApplicationContext applicationContext) throws Exception;
    /**
     * 通过ID删除的实体类
     * @param id
     * @throws Exception
     */
    void deleteRecordById(Long id) throws Exception;

    /**
     *
     * @throws Exception
     */
    void updateRcordByIdWithIsActive(Long id, Integer isActive) throws  Exception;

    void updateLSCS(Date lscsStartTime, Date lscsEntTime,Long prodInstId) throws  Exception;

    /**
     * 通过模型key获取模型ID
     * @param modelkey
     * @return
     */
    Map<String,Object> getActModelByKey(String modelkey);

    /**
     * 获取模型设计的二进制图片
     * @param editorSourceExtraValueId
     * @return
     */
    ActJBPM getActBytesByEditorSourceExtraValueId(String editorSourceExtraValueId);

    Long handWithIdcReProduct(ApplicationContext applicationContext, Map<String, Object> variables, IdcReProduct idcReProduct)  throws Exception;

    void pauseFormSaveOrUpdate(ApplicationContext context, IdcRunTicketPause idcRunTicketPause, IdcRunProcCment idcRunProcCment)  throws Exception;

}
