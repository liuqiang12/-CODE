package com.idc.service;

import com.idc.model.IdcRunProcCment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.rest.ResultObject;

/**
 * 流程审批中的工单处理接口方法
 */
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public interface JbpmProcessService {
    /**
     * 处理的方法
     * @param handlerRunTikcetParam
     * @return
     * @throws Exception
     */
    ResultObject handlerJbpmProcess(String handlerRunTikcetParam) throws Exception;

    /**
     * 将传递进来的参数转换成对象
     * @param handlerRunTikcetParam
     * @return
     * @throws Exception
     */
    IdcRunProcCment paramstoBean(String handlerRunTikcetParam) throws Exception;

    /**
     * 保存流程意见表
     * @param idcRunProcCment
     * @throws Exception
     */
    void handRunCommnetToMergeInto(IdcRunProcCment idcRunProcCment) throws  Exception;

    /**
     * 处理工单关联表信息
     * @param idcRunProcCment
     * @throws Exception
     */
    void handTicketLinkedInfo(IdcRunProcCment idcRunProcCment) throws Exception;

}
