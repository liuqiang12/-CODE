package com.idc.service;

import com.idc.model.*;
import com.idc.vo.NetCapPort;
import com.idc.vo.NetCapPortDetail;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/11.
 */
public interface NetPortFlowService {

    List<NetPortFlow> getCurrList(List<String> ids);

    Map<String, NetPortFlow> getCurrMap(List<String> ids);

    List<NetPortFlow> queryListJoinFlowByObject(NetPort netPort);

    NetCapPortDetail queryFlowByWeek(long portid, String startTime, String endTime);

    List<NetPortFlow> queryFlowBy(int type, long id);

    Map<String, Object> getUsage(long id);

//    List<NetPortMaxFlow> getMaxFlow(String reportType, int portType, List<String> strings, String startTime, String endTime, String cyc);

    //List<NetPortFlowCap> getPortFlowCap(String reportType, int portType, List<String> strings, String startTime, String endTime, String cyc);

    /***
     * 获取业务一个月的流量信息 5分粒度
     * @param portids
     * @return
     */
    List<NetPortFlow> getBusiFlowDetailMonth(List<String> portids);

    NetCapPortDetail getPortFlowDetail(String reportType, int portType, List<String> strings, String startTime, String endTime, String cyc);

    /***
     * 获取出流量TOP
     * @return
     */
    List<NetPortFlow> getOutFlowTopN();

    void ZipBusiFlow(String startTime, String endTime);

    void ZipBusiFlow(long busiid, String startTime, String endTime);

    List<NetPortFlowCap> getBusiPortFlowCapWeek(String reportType, int portType, List<String> strings, String startTime, String endTime, String cyc);

    List<NetPortFlow> getFlow(List<DevicePortQueryBean> querys);

    /*获取当前客户业务端口最近30天流量峰值*/
    List<Map<String, Object>> queryCustomerMaxPortFlowByCustomerId(String customerId);

    List<Map<String, Object>> queryCustomerMaxPortFlowByMap(Map<String,Object> map);

    List<NetPortFlowCap> getBusiCap(String id, String startTime, String endTime, String cyc);

    List<NetCapPort> getPortFlow(List<String> strings, String startTime, String endTime);

    List<NetCapPort> test(int a, int b);

    Map<String, List<NetCapPort>> getListByForId(Connection conn, String[] ids, String s, String e, String cyc) throws Exception;

    /*通过客户id 获取客户业务端口名称、ID*/
    List<Map<String,Object>> queryBusinessPortNameByCustomerId(PageBean<T> page, Map<String,Object> map);
}
