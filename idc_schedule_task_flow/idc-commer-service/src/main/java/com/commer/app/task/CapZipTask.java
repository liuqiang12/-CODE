package com.commer.app.task;

import com.alibaba.druid.util.JdbcUtils;
import com.comer.util.DateFormate;
import com.comer.util.DateUtil;
import com.commer.app.mapper.BusPortMapper;
import com.commer.app.mode.NetBusiPort;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by mylove on 2017/12/15.
 */
@Component
public class CapZipTask {
    private static final Logger logger = LoggerFactory.getLogger(CapZipTask.class);
    @Autowired
    DataSource dataSource;
    @Autowired
    private BusPortMapper mapper;

    public double avg(List<Double> ds) {
        if (ds == null || ds.size() == 0) {
            return 0;
        }
        Double all = 0D;
        for (Double d : ds) {
            if (d != null) {
                all += d;
            }

        }
        return all / ds.size();
    }

    public double addAvg(Double d, Double d2) {
        double value = 0D;
        if (d != null && d2 != null) {
            value = avg(Arrays.asList(d, d2));
        }
        if (d == null || d2 == null) {
            if (d == null) {
                value = Math.random() > 0.5 ? d2 + d2 * 0.01 : d2 - d2 * 0.01;
            } else {
                value = Math.random() > 0.5 ? d + d * 0.01 : d - d * 0.01;
            }
        }
        return value;
    }

//    @Scheduled(cron = "30 0/5 * * * ? ")//每天5分种压缩一次 延时20===采集程序
    public void run() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;
                    try {
                        conn = dataSource.getConnection();
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.MINUTE, -10);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        int fix = (int) (Math.floor(c.get(Calendar.MINUTE) / 5) * 5);
                        c.set(Calendar.MINUTE, fix);
                        String startTime = DateFormatUtils.format(c, "yyyy-MM-dd HH:mm:ss");
                        c.add(Calendar.MINUTE, 5);
                        String endTime = DateFormatUtils.format(c, "yyyy-MM-dd HH:mm:ss");
                        List<Long> times = DataUtil.getTimes(startTime, endTime, null);
                        logger.info("统计在{}的数据", new Date(times.get(times.size() - 1)));

                        List<Object[]> timesTable = DataUtil.getTimes(startTime, endTime);
                        List<NetCapPort> netCapPorts = getData(conn, timesTable);
                        logger.info("{}，{},{}", startTime, endTime, times);
                        Map<Long, NetCapPort> portMap = new HashMap<Long, NetCapPort>();
                        Map<Long, List<NetCapPort>> portTimeListMap = new HashMap<Long, List<NetCapPort>>();
                        List<NetCapPort> tmpList;
                        for (NetCapPort netCapPort : netCapPorts) {
                            tmpList = portTimeListMap.get(netCapPort.getPortid());
                            if (tmpList == null) {
                                tmpList = new ArrayList<NetCapPort>();
                                portTimeListMap.put(netCapPort.getPortid(), tmpList);
                            }
                            tmpList.add(netCapPort);
                        }
                        long currTime = times.get(times.size() - 1);
                        for (Map.Entry<Long, List<NetCapPort>> longListEntry : portTimeListMap.entrySet()) {
                            NetCapPort prev = null;
                            NetCapPort curr = null;
                            long portid = longListEntry.getKey();
                            List<NetCapPort> values = longListEntry.getValue();
                            for (NetCapPort capPort : values) {
                                if (capPort.getRecordTime().getTime() == currTime) {
                                    curr = capPort;
                                } else if (capPort.getRecordTime().getTime() < currTime) {
                                    prev = capPort;
                                }
                            }
                            if (curr == null) {
                                if (prev == null) {
                                    logger.error("端口{}在{}的数据丢失", portid, currTime);
                                    continue;
                                } else {
                                    logger.error("补充端口{}在{}丢失的数据", portid, currTime);
                                    curr = new NetCapPort();
                                    curr.setPortid(portid);
                                    curr.setInflow(addAvg(prev.getInflow(), null));
                                    curr.setOutflow(addAvg(prev.getOutflow(), null));
                                    curr.setRecordTime(new Timestamp(currTime));
                                }
                            }
                            portMap.put(portid, curr);
                        }

                        List<NetBusiPort> netBusiPorts = mapper.queryList();
                        ImmutableMap<Long, NetBusiPort> busiMap = Maps.uniqueIndex(netBusiPorts, new Function<NetBusiPort, Long>() {
                            @Override
                            public Long apply(NetBusiPort netBusiPort) {
                                return netBusiPort.getId();
                            }
                        });
                        NetCapPort netCapPort = null;
                        NetCapPort tmp;
                        List<NetCapPort> results = new ArrayList<NetCapPort>();
                        //按业务汇总
                        for (Map.Entry<Long, NetBusiPort> longListEntry : busiMap.entrySet()) {
                            netCapPort = new NetCapPort();
                            double inflow = 0.0D, outflow = 0.0D;
                            for (Long portid : longListEntry.getValue().getPortids()) {
                                tmp = portMap.get(portid);
                                if (tmp != null) {
                                    inflow += tmp.getInflow();
                                    outflow += tmp.getOutflow();
                                }
                            }
                            netCapPort.setPortid(longListEntry.getKey());
                            netCapPort.setRecordTime(new Timestamp(currTime));
                            netCapPort.setInflow(inflow);
                            netCapPort.setOutflow(outflow);
                            results.add(netCapPort);
                        }
                        logger.info("开始插入数据库,大小{}", results.size());
                        //insertOrUpdate(conn, results, currTime, busiMap);
                        JdbcUtils.close(conn);
                    } catch (Exception e) {
                        logger.error("{}", e);
                        if (conn != null) {
                            JdbcUtils.close(conn);
                        }
                    } finally {
                        JdbcUtils.close(conn);
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<NetCapPort> getData(Connection conn, List<Object[]> timesTable) {
        List<NetCapPort> netCapPorts = new ArrayList<NetCapPort>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            StringBuffer sql = new StringBuffer();
            for (Object[] table : timesTable) {
                if (sql.length() > 0) {
                    sql.append(" UNION all ");
                }
                sql.append("SELECT id, portid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_").append(table[0]).append("_").append(table[1]).append(" where  RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            }
            preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
            int pindex = 1;
            for (Object[] objects : timesTable) {
                preparedStatement.setString(pindex++, objects[2].toString());
                preparedStatement.setString(pindex++, objects[3].toString());
                preparedStatement.setFetchSize(50000);
                try {
                    rs = preparedStatement.executeQuery();
                    NetCapPort t;
                    while (rs.next()) {
                        t = new NetCapPort();
                        t.setId(rs.getLong("id"));
                        t.setPortid(rs.getLong("portid"));
                        t.setRecordTime(rs.getTimestamp("recordtime"));
                        t.setInflow(rs.getDouble("inflow"));
                        t.setOutflow(rs.getDouble("outflow"));
                        netCapPorts.add(t);
                    }
                } catch (SQLException e1) {
                    logger.error("", e1);
                } finally {
                    JdbcUtils.close(rs);
                    JdbcUtils.close(preparedStatement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(preparedStatement);
        }
        return netCapPorts;
    }

    private Map<Long, List<Long>> getBusi(Connection conn) {
        Map<Long, List<Long>> busiPortMap = new HashMap<Long, List<Long>>();
        ResultSet resultSet = null;
        PreparedStatement busips = null;
        try {
            busips = conn.prepareStatement("SELECT busiid,portid from net_busi_port");
            resultSet = busips.executeQuery();
            while (resultSet.next()) {
                Long busiid = resultSet.getLong(1);
                List<Long> portids = busiPortMap.get(busiid);
                if (portids == null) {
                    portids = new ArrayList<Long>();
                    busiPortMap.put(busiid, portids);
                }
                Long portid = resultSet.getLong(2);
                portids.add(portid);
            }
        } catch (SQLException e) {
            logger.error("获取业务信息失败", e);
        } finally {
            JdbcUtils.close(resultSet);
            JdbcUtils.close(busips);
        }
        return busiPortMap;
    }

    private void insertOrUpdate(Connection connection, List<NetCapPort> results, Long time, ImmutableMap<Long, NetBusiPort> busiMap) throws Exception {
        PreparedStatement ps = null;
        boolean isAuto = true;
        try {
            isAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            String sql = "insert into NET_CAP_BUSI (BUSIID ,RECORDTIME ,INPUTFLUX ,OUTPUTFLUX ,PORTNAME,BANDWIDTH) values (?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            final int batchSize = 1000;
            int index = 0;
            for (NetCapPort netCapPort : results) {
                long busiid = netCapPort.getPortid();
                NetBusiPort netBusiPort = busiMap.get(busiid);
                if (netBusiPort == null) {
                    continue;
                }
                ps.setLong(1, busiid);
                ps.setTimestamp(2, netCapPort.getRecordTime());
                ps.setDouble(3, netCapPort.getInflow());
                ps.setDouble(4, netCapPort.getOutflow());
                ps.setString(5, netBusiPort.getBusiportname());
                ps.setDouble(6, netBusiPort.getBandwidth() == null ? 0 : netBusiPort.getBandwidth());
                ps.addBatch();
                index++;
                if (index % batchSize == 0) {
                    ps.executeBatch();
                    connection.commit();
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            connection.commit();
            ps.clearBatch();
            logger.info("插入业务流量完成");
        } catch (Exception e) {
            logger.error("插入业务流量失败，即将回滚", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("回滚数据失败", e1);
            }
        } finally {
            try {
                connection.setAutoCommit(isAuto);
                JdbcUtils.close(ps);
            } catch (SQLException e) {
                logger.error("还原提交方式error", e);
            }
        }
    }

    public void Zip() {
        Connection conn = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
            conn = null;
        }
    }
}
