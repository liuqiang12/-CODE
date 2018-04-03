package com.idc.mapper;

import com.idc.model.ActJBPM;
import com.idc.model.CreateTicketModel;
import com.idc.model.IdcReProduct;
import com.idc.model.ProductCategory;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_RE_PRODUCT:IDC_RE_产品实例表[修改成订单申请管理]<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 14,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcReProductMapper extends SuperMapper<IdcReProduct, Long>{
    //显示部分字段信息
    List<IdcReProduct> queryGridFilterListPage(PageBean<IdcReProduct> page);

    /**
     * 通过ID获取实体类信息
     * @param id
     * @return
     */
    IdcReProduct getModelByProductId(Long id);

    String getIdcNameByTicketID(String ticketInstId);

    IdcReProduct getFilterModelByProductId(Long id);
    /**
     * 合体方法
     * @param idcReProduct
     * @throws Exception
     */
    void mergeInto(IdcReProduct idcReProduct) throws Exception;
    ProductCategory getProductCategoryByProdInstId(@Param("prodInstId") String prodInstId, @Param("ticketInstId") Long ticketInstId);

    /**
     * 修改订单实例Id
     * @param id
     */
    void updateProdInstIdById(Long id) throws Exception;
    /**
     * 通过ID删除的实体类
     * @param id
     * @throws Exception
     */
    void deleteRecordById(Long id) throws Exception;

    /**
     * 通过ID获取实体类信息[附带模型实体类]
     * @param id
     * @return
     */
    IdcReProduct getModelWithCatalogModelByProductId(Long id);
    void updateRcordByIdWithIsActive(@Param("id") Long id, @Param("isActive") Integer isActive) throws  Exception;

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

    /**
     * 通过模型ID修改相应模型的部署ID值
     * @param modelId
     * @throws Exception
     */
    void updateActModelDeployIdByModelId(@Param("modelId") String modelId, @Param("deploymentId") String deploymentId) throws  Exception;

    /**
     * 通过模型ID修改相应模型的部署ID值
     * @param bytearrayId
     * @throws Exception
     */
    void updateActByteArrayDeployIdByByteId(@Param("bytearrayId") String bytearrayId, @Param("deploymentId") String deploymentId) throws  Exception;

    /**
     * 通过模型ID修改相应模型的部署ID值
     * @param bytearrayId
     * @throws Exception
     */
    void updateActByteArrayDeployIdByImageId(@Param("bytearrayId") String bytearrayId, @Param("deploymentId") String deploymentId) throws  Exception;

    /**
     *
     * @param editorSourceExtraValueId:model中的字段，对应模型设计图像
     * @param deploymentId：部署ID
     * @param imageName:部署后的文件名称
     * @throws Exception
     */
    void updateActByteArrayBytesById(@Param("editorSourceExtraValueId") String editorSourceExtraValueId, @Param("deploymentId") String deploymentId, @Param("imageName") String imageName) throws Exception;

    /**
     * 获取订单ID
     * @param idcReProduct
     * @return
     */
    Long findId(IdcReProduct idcReProduct);

    /**
     * 修改订单状态
     * @param prodInstId
     * @throws Exception
     */
    void updateProdStatusByProdInstIdAndStatus(@Param("prodInstId") Long prodInstId, @Param("procticketStatus") long procticketStatus) throws Exception;

    /**
     * @param
     * @return
     * @throws Exception
     */
    void callProcSaveProductArry(Map<String, Object> definedArrayMap) throws Exception;
    void callProcSaveProductDefModelArry(Map<String, Object> definedArrayMap) throws Exception;

    void copyIdcReProddef(Map<String, Object> definedArrayMap) throws Exception;

    CreateTicketModel getCreateTicketModelByProdId(Map<String, Object> createTicketParams);
    void updateLSCS(@Param("lscsStartTime")Date lscsStartTime, @Param("lscsEntTime")Date lscsEntTime,@Param("prodInstId")Long prodInstId) throws Exception;

}

 