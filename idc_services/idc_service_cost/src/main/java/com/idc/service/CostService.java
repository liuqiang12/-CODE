package com.idc.service;

import com.idc.model.*;
import com.idc.vo.CostRackFullVO;
import com.idc.vo.CostRoomVO;

/**
 * 成本接口
 *
 * @author mylove
 *         Created by mylove on 2017/10/27.
 */
public interface CostService {
    /***
     * 通过机架ID 获取成本信息
     *
     * @param rackid
     * @param time
     * @return
     */
    CostRackFullVO getCostByRack(long rackid, String time);

    CostRoomVO getCostByRoom(long roomid);

    ExecuteResult saveCostFixed(CostFixed costFixedEntity);

    CostFixed getCostFixedByBuild(String buildid);

    CostDynamic getDynamicByBuild(String buildid, String time);

    ExecuteResult saveBuildDyn(CostDynamic costDynamic, CostAnalysisRoom analysisRoom);

    /***
     * 获取分析表 通过ID
     *
     * @param costRoomId
     * @return
     */
    CostAnalysisRoom getAnalyByRoom(String costRoomId);

    /***
     * @param rackid
     * @param time
     * @param costEnergy
     * @return
     */
    @Deprecated
    ExecuteResult saveRackDyn(long rackid, String time, CostAnalysisRack costEnergy);

    /***
     * 通过客户获取成本信息
     * 获取客户的所有机架 合并成本
     *
     * @param customerid
     * @param time
     * @return
     */
    CostRackFullVO getByCustomer(long customerid, String time);

    /***
     * 保存客户收入信息
     * 客户机架收入保存到COST_ROOM_BASE
     * 客户宽带收入保存到分析表
     *
     * @param customId
     * @param time
     * @param costAnalysisRack
     * @return
     */
    ExecuteResult saveCostCustomer(long customId, String time, CostAnalysisRack costAnalysisRack);

    /***
     * 通过机架获取 收入信息
     * 获取 机架所属客户
     * 获取客户收入/客户机架数
     *
     * @param rackid
     * @param time
     * @return
     */
    CostRackFullVO getInCome(long rackid, String time);
}
