package com.idc.service;

import com.idc.model.IdcLocationCount;
import com.idc.model.TicketData;
import com.idc.model.count.TrendBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * 首页视图接口
 * Created by mylove on 2017/8/8.
 */
public interface ResourceViewService extends SuperService<IdcLocationCount,Long>{

//    /***
//     * @param localId 数据中心ID i 不传出所有数据中心
//     * @return 数据中心 >>状态名-数值 ,eg:{西区移动：[{空闲，120}，{占用，110}]}
//     * @author 李龙江实现
//     * @TODO 获取机柜各状态数据
//     */
//    DataView getRackUsage(Integer localId);
//
//    List<DataView> getRackUsage();
//
//    /***
//     * @param localId 数据中心ID i 不传出所有数据中心
//     * @return
//     * @TODO 获取IP各状态使用数据
//     */
//    DataView getIPUsage(Integer localId);
//
//    List<DataView> getIPUsage();
//
//
//    /***
//     * @param localId 数据中心ID i 不传出所有数据中心
//     * @return 状态名-数值。eg:{空闲：500，使用：200}
//     * @author 李龙江实现
//     * @TODO 带宽使用数据
//     */
//    DataView getBandWidthUsage(Integer localId);
//
//    List<DataView> getBandWidthUsage();
//
//    /***
//     * @param localId
//     * @return
//     * @author 李龙江实现
//     * @TODO 网络设备使用数据
//     */
//    DataView getNetDeviceUsage(Integer localId);
//
//    List<DataView> getNetDeviceUsage();
//
//    /***
//     * @param localId
//     * @return
//     * @author 李龙江实现
//     * @TODO 主机设备使用信息
//     */
//    DataView getIdcDeviceUsage(Integer localId);
//
//    List<DataView> getIdcDeviceUsage();
//
//    /***
//     * @param localId
//     * @return dataMap -key [机楼，机房,机架]
//     * @author 李龙江实现
//     * @TODO 空间资源总数
//     */
//    DataView getSpaceResourceNum(Integer localId);
//
//    List<DataView> getSpaceResourceNum();
//
//    /**
//     * @param localId
//     * @return dataMap -key [带宽，IP,网络设备，ODF]
//     * @author 李龙江实现
//     * @TODO 网络资源总数
//     */
//    DataView getNetResourceNum(Integer localId);
//
//    List<DataView> getNetResourceNum();
//
//    /***
//     * @param localId
//     * @return dataMap -key [PDF]
//     * @author李龙江实现
//     * @TODO 动力资源总数
//     */
//    DataView getPDFResourceNum(Integer localId);
//
//    List<DataView> getPDFResourceNum();
//
//    /***
//     * @param localId
//     * @return dataMap -key [主机]
//     * @author李龙江实现
//     * @TODO 主机资源总数
//     */
//    DataView getHostResourceNum(Integer localId);
//
//    List<DataView> getHostResourceNum();
//
//    /***
//     * @param localId
//     * @return dataMap -key 当月能耗
//     * @author 李龙江实现
//     * @TODO 获取能耗数据
//     */
//    DataView getEnergyNum(Integer localId);
//
//    List<DataView> getEnergyNum();

    /***
     * @param localId
     * @return
     * @TODO 获取数据中心统计信息
     * @auther 李龙江
     */
    IdcLocationCount getLocalCount(Integer localId);

    /***
     * 重新加载数据中心数据
     *
     * @param localId
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    void reloadCount(Integer localId);

    /***
     * @param ticketData 包含工单 分配或回收的资源 必须包含资源类型
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    void reloadCount(TicketData ticketData);

    /***
     * 直接全部刷新数据
     *
     * @param localId
     * @param ticketData
     */
    void reloadCount(Long localId, Object ticketData);

    /***
     * 更新指定的列为指定的值
     *
     * @param localId
     * @param idcLocationCount 更新的列
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    void reloadCountByColumn(Integer localId, IdcLocationCount idcLocationCount);

    long getRoomCount(Integer localId);

    long getBuildCount(Integer localId);

    long getOdfCount(Integer localId);

    long getPdfCount(Integer localId);

    /***
     * 获取客户占比
     * @return  key:有域名客户，其他客户
     */
    public Map<String,Long> getHavDomainUser();

    /***
     * 获取用户增加趋势
     * @return
     */
    public List<Map<String, Object>> getUserAddTread();

    /***
     * 获取各种业务分类好评数
     * @param type 0自有业务， 1政企业务
     * @return
     */
    public Map<String,Long> getGoodRate(int type);

    /***
     *待处理工单
     * @return
     */
    public List<Map<String,Object>> getTicketByWait();

    /**
     * 根据资源变更类型重新进行数据统计
     *
     * @param locationId
     * @param type
     * @throws Exception
     */
    public void reLoadCountByColumn(Integer locationId, String type) throws Exception;

    /***
     *  或取端口流量TOPN
     * @return
     */
    List<Map<String,Object>> getPortFlowTopN();

    /***
     * 获取机房空闲率TOPN
     * @return key:roomName ,freeUsage
     */
    public List<Map<String,Object>> getRoomFreeUsgeTOP();

    /*通过客户ID获取资源相关数量  机架-机位-IP-端口-带宽*/
    Map<String, Object> getIdcResourceCountByCustomerId(Long cusId);

    /*更新机房统计值*/
    void reloadRoomStatistics();

}

