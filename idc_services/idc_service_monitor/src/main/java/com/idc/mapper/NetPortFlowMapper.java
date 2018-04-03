package com.idc.mapper;

import com.idc.model.*;
import com.idc.vo.NetCapPort;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/11.
 */
public interface NetPortFlowMapper {

    List<NetPortFlow> queryListJoinFlowByObject(NetPort netPort);


    List<NetPortFlow> queryFlowByWeek(@Param("portid") long portid, @Param("startTime")String startTime, @Param("endTime")String endTime);

    List<NetPortFlow> queryFlowBy(@Param("type") int type, @Param("portid") long portid);

    Map<String, Object> getUsage(@Param("deviceid") long id);

    List<NetPortMaxFlow> getMaxFlow(@Param("reportType") String reportType, @Param("portType") int portType,
                                    @Param("portids") List<String> portids, @Param("startTime") String startTime,
                                    @Param("endTime") String endTime, @Param("cyc") String cyc);

    List<NetPortFlowCap> getFlowCap(@Param("reportType") String reportType, @Param("portType") int portType,
                                    @Param("portids") List<String> portids, @Param("startTime") String startTime,
                                    @Param("endTime") String endTime, @Param("cyc") String cyc);

    //List<NetPortFlow> getFlow(@Param("deviceid")long deviceid,@Param("portids") List<String> ports);

//    List<NetPortFlow> getFlow(@Param("query")List<DevicePortQueryBean> querys);

    List<NetPortFlow> getFlowCapDetail(@Param("reportType") String reportType, @Param("portType") int portType,
                                       @Param("portids") List<String> portids, @Param("startTime") String startTime,
                                       @Param("endTime") String endTime, @Param("cyc") String cyc);

//    List<NetPortFlow> getOutFlowTopN();

    List<NetPortFlowCap> getBusiPortFlowCapWeek(@Param("reportType") String reportType, @Param("portType") int portType,
                                                @Param("portids") List<String> portids, @Param("startTime") String startTime,
                                                @Param("endTime") String endTime, @Param("cyc") String cyc);

    List<NetPortFlow> getAllFlowByViewId(@Param("viewid")Long viewid);

    /*获取当前客户业务端口最近30天流量峰值*/
    List<Map<String, Object>> queryCustomerMaxPortFlowByCustomerId(@Param("customerId") String customerId);

    List<NetPortFlow> getPortFlowDetail( @Param("portids") List<String> portids, @Param("startTime") String startTime,
                                         @Param("endTime") String endTime, @Param("cyc") String cyc);

    List<NetCapPort> getPortFlow( @Param("portids") List<String> portids, @Param("startTime") String startTime,@Param("endTime")String endTime);

    List<NetCapPort> test(@Param("a")int a,@Param("b")int b);

    List<Map<String,Object>> queryCustomerMaxPortFlowByCustomerIdTable(@Param("table")String s, @Param("customerId")String customerId);

    List<Map<String,Object>> queryCustomerMaxPortFlowByMap(Map<String,Object> map);
    /*通过客户id 获取客户业务端口名称、ID*/
    List<Map<String,Object>> queryBusinessPortNameByCustomerId(PageBean<T> page);
}
