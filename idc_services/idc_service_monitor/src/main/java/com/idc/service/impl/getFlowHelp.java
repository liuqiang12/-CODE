package com.idc.service.impl;

import com.idc.mapper.BusPortMapper;
import com.idc.model.NetBusiPort;
import com.idc.model.NetPortFlow;
import com.idc.vo.NetCapPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by mylove on 2017/12/28.
 */
@Service
public class getFlowHelp {
    @Autowired
    private BusPortMapper busPortMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(getFlowHelp.class);


    public void getTest(String portid, String startTime, String endTime) {
        NetBusiPort netbusiPort = busPortMapper.getById(portid);
        List<String> portarr = new ArrayList<>();
        for (int i = 0; i < netbusiPort.getPortids().size(); i++) {
            portarr.add(netbusiPort.getPortids().get(i).toString());
        }
        long exectime = System.currentTimeMillis();
        List<NetCapPort> originalFlow = getOriginalFlow(portarr, startTime, endTime);
        logger.info("查询耗时{}", System.currentTimeMillis() - exectime);
    }

    /***
     * 获取原始流量
     *
     * @param portids
     * @param startTime
     * @param endTime
     * @return
     */
    public List<NetCapPort> getOriginalFlow(List<String> portids, String startTime, String endTime) {

        if (portids == null || portids.size() == 0) {
            return new ArrayList<>();
        }
        Connection conn = null;
        List<Long> times = DataUtil.getTimes(startTime, endTime, null);
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn == null) {
                return new ArrayList<>();
            }
            List<NetCapPort> OriginaiData = null;
            try {
                long exectime = System.currentTimeMillis();
                OriginaiData = getOriginalData(conn, portids, startTime, endTime);
                logger.info("查询耗时{}", System.currentTimeMillis() - exectime);
            } catch (Exception e) {
                logger.error("", e);
            }
            JdbcUtils.closeConnection(conn);
            //端口数据
            Map<Long, List<NetCapPort>> portListMap = new HashMap<>(portids.size());
            if (OriginaiData == null) {
                return new ArrayList<>();
            }
            //每个端口流量
            int size = times.size();
            List<NetCapPort> portFlowList;
            for (NetCapPort netCapPort : OriginaiData) {
                portFlowList = portListMap.get(netCapPort.getPortid());
                if (portFlowList == null) {
                    portFlowList = new ArrayList<>(size);
                    portListMap.put(netCapPort.getPortid(), portFlowList);
                }
                portFlowList.add(netCapPort);
            }
            // 指定时间那的对象
            Map<Long, NetCapPort> resultMap = new TreeMap<>();
            NetCapPort netCapPortinCountMap;
            for (Map.Entry<Long, List<NetCapPort>> portListEntry : portListMap.entrySet()) {
                portFlowList = DataUtil.addLostData(portListEntry.getKey().toString(), times, portListEntry.getValue());
                for (NetCapPort netCapPort : portFlowList) {
                    long mapkey = netCapPort.getRecordTime().getTime();
                    netCapPortinCountMap = resultMap.get(mapkey);
                    if (netCapPortinCountMap == null) {
                        resultMap.put(mapkey, netCapPort);
                    } else {
                        netCapPortinCountMap.setInflow(netCapPortinCountMap.getInflow() + netCapPort.getInflow());
                        netCapPortinCountMap.setOutflow(netCapPortinCountMap.getOutflow() + netCapPort.getOutflow());
                    }
                }
            }
            List<NetCapPort> portFlowDetails = new ArrayList<>(resultMap.size());
            for (NetCapPort netPortFlow : resultMap.values()) {
                portFlowDetails.add(netPortFlow);
            }
            return portFlowDetails;
        } catch (Exception e) {
            logger.error("", e);
        }
        return new ArrayList<>();
    }

    /***
     * 原始数据
     *
     * @param conn
     * @param ids
     * @param s
     * @param e
     * @return
     */
    public List<NetCapPort> getOriginalData(Connection conn, List<String> ids, String s, String e) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            List<Object[]> times1 = DataUtil.getTimes(s, e);
            StringBuffer sql = new StringBuffer();
            for (Object[] objects : times1) {
                for (String id : ids) {
                    if (sql.length() > 0) {
                        sql.append(" UNION all ");
                    }
                    sql.append("SELECT id, portid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_").append(objects[0]).append("_").append(objects[1]).append(" where  portid =?  and RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')");
                }
            }
            String sqlstr = "SELECT * from (" + sql.toString() + ") order by portid,recordtime";
            preparedStatement = conn.prepareStatement(sqlstr);
            logger.info("execsql:{}", sqlstr);
            sql = new StringBuffer();
            int pindex = 1;
            for (Object[] objects : times1) {
                for (int i = 0; i < ids.size(); i++) {
                    preparedStatement.setString(pindex++, ids.get(i));
                    preparedStatement.setString(pindex++, objects[2].toString());
                    preparedStatement.setString(pindex++, objects[3].toString());
                    sql.append(ids.get(i)).append(",").
                            append(objects[2].toString()).append(",").
                            append(objects[3].toString()).append(",");
                }
            }
            logger.info("params:{}", sql);
            preparedStatement.setFetchSize(50000);

            rs = preparedStatement.executeQuery();
            List<NetCapPort> netCapPorts = new ArrayList<>(5000);
            NetCapPort t;
            while (rs.next()) {
                t = new NetCapPort();
                t.setId(rs.getString("id"));
                t.setPortid(rs.getLong("portid"));
                t.setRecordTime(rs.getTimestamp("recordtime"));
                t.setInflow(rs.getDouble("inflow"));
                t.setOutflow(rs.getDouble("outflow"));
                netCapPorts.add(t);
            }
            return netCapPorts;
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error("", e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(preparedStatement);
        }
        return null;
    }
}
