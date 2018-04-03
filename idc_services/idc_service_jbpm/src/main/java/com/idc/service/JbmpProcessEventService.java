package com.idc.service;

import com.idc.model.IdcRunProcCment;
import com.idc.utils.TASKNodeURL;

/**
 * Created by DELL on 2017/8/29.
 * 动态代理
 */
public interface JbmpProcessEventService {
    /**
     * 预受理工单
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketPreAccept(IdcRunProcCment idcRunProcCment, TASKNodeURL taskNodeURL) throws Exception;

    /**
     * 停机工单
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketPause(IdcRunProcCment idcRunProcCment, TASKNodeURL taskNodeURL) throws Exception;

    /**
     * 复通工单
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketRecover(IdcRunProcCment idcRunProcCment, TASKNodeURL taskNodeURL) throws Exception;

    /**
     * 业务变更
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketChange(IdcRunProcCment idcRunProcCment, TASKNodeURL taskNodeURL) throws Exception;

    /**
     * 业务下线
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketHalt(IdcRunProcCment idcRunProcCment, TASKNodeURL taskNodeURL) throws Exception;

    /**
     * 流程同意入口
     * @param idcRunProcCment
     * @throws Exception
     */
    void handerTicketProcMain(IdcRunProcCment idcRunProcCment) throws Exception;

}
