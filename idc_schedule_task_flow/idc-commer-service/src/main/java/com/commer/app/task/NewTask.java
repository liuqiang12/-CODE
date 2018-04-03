package com.commer.app.task;

import com.alibaba.druid.util.JdbcUtils;
import com.commer.app.mapper.BusPortMapper;
import com.commer.app.mode.NetBusiPort;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by mylove on 2017/12/28.
 */
@Component
public class NewTask {
    private static final Logger logger = LoggerFactory.getLogger(NewTask.class);
    @Autowired
    public DataSource dataSource;
    @Autowired
    private BusPortMapper mapper;
    private Long lastExecTime = null;

//    @Scheduled(cron = "0/20 * * * * ? ")//每天5分种压缩一次 延时20===采集程序
    public void run() {
        new TaskRun().run();
    }

    class TaskRun implements Runnable {
        @Override
        public void run() {
            Connection conn = null;
            try {
                conn = dataSource.getConnection();
                String sql = "SELECT EXECTIME,RUNFLAG FROM TASK_MANAGER WHERE TASKID=3";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                //下一次执行时间和上一次执行时间
                Timestamp nextexecTime = null;
                Integer status = null;
                while (rs.next()) {
                    nextexecTime = rs.getTimestamp(1);
                    status = rs.getInt(2);
                    break;
                }
                JdbcUtils.close(rs);
                JdbcUtils.close(preparedStatement);
                JdbcUtils.close(conn);
                if (status != null && status == 0) {
                    //有结果的时候 ，如果都没有任务当然不执行
                    if (nextexecTime != null) {
                        Calendar tmpc = Calendar.getInstance();
                        //第一次执行 lastExecTime=null  获取将要执行的任务时间
                        if (lastExecTime == null) {
                            tmpc.setTime(nextexecTime);
                            int fix = (int) (Math.floor(tmpc.get(Calendar.MINUTE) / 5) * 5);
                            tmpc.set(Calendar.MINUTE, fix);
                            tmpc.set(Calendar.SECOND, 0);
                            tmpc.set(Calendar.MILLISECOND, 0);
                            lastExecTime = tmpc.getTimeInMillis();
                        } else {
                            //第N次执行 获取执行时间
                            tmpc.setTime(nextexecTime);
                            int fix = (int) (Math.floor(tmpc.get(Calendar.MINUTE) / 5) * 5);
                            tmpc.set(Calendar.MINUTE, fix);
                            tmpc.set(Calendar.SECOND, 0);
                            tmpc.set(Calendar.MILLISECOND, 0);
                            long next = tmpc.getTimeInMillis();
                            tmpc.add(Calendar.MINUTE, -5);
                            long prevtime = tmpc.getTimeInMillis();
                            //如果采集器任务执行了 那么下一次任务时间肯定更新,并且在上一次执行时间上加5分种
//                        getData(conn, tmpc);
                            if (prevtime == lastExecTime) {
                                logger.info("采集完成...");
                                try {
                                    getData(tmpc);
                                    logger.info("插入完毕...");
                                } catch (Exception e) {
                                    logger.error("", e);
                                }
                            }
                            lastExecTime = next;
                        }
                    } else {
                        logger.debug("等待采集完成");
                    }
                }

            } catch (Exception e) {
                logger.error("", e);
            }
        }

        public void getData(Calendar calender) throws Exception {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            List<Long> lostPortId = null;
            Map<Long, NetCapPort> portMap = null;
            try {
                conn = dataSource.getConnection();
                String sql = "select portid from NET_PORT where ADMINSTATUS=1 AND PORTACTIVE=1";
                ps = conn.prepareStatement(sql);
                ps.setFetchSize(5000);
                rs = ps.executeQuery();
                List<Long> portids = new ArrayList<>(5000);
                while (rs.next()) {
                    portids.add(rs.getLong(1));
                }
                sql = "SELECT B.PORTID,B.ROUTID,B.RECORDTIME,B.INPUTFLUX,B.OUTPUTFLUX FROM  NET_CAP_PORT_CURR B where RECORDTIMEL>=?";
                ps = conn.prepareStatement(sql);
                ps.setFetchSize(5000);
                ps.setLong(1, calender.getTimeInMillis());
                logger.debug("sql;{}", sql);
                logger.debug("参数;{}", calender.getTimeInMillis());
                rs = ps.executeQuery();
                Timestamp recordtime = null;
                lostPortId = new ArrayList<>();
                Long portid = null;
                Double inputflux = null, outputflux = null;
                NetCapPort netCapPort;
                List<NetCapPort> netCapPortList = new ArrayList<>(5000);
                portMap = new HashMap<>(5000);
                while (rs.next()) {
                    portid = rs.getLong("portid");
                    recordtime = rs.getTimestamp("recordtime");
                    netCapPort = new NetCapPort();
                    inputflux = rs.getDouble("inputflux");
                    outputflux = rs.getDouble("outputflux");
                    netCapPort.setInflow(inputflux);
                    netCapPort.setOutflow(outputflux);
                    netCapPort.setPortid(portid);
                    netCapPort.setRecordTime(recordtime);
                    portMap.put(portid, netCapPort);
                }
                for (Long aLong : portids) {
                    netCapPort = portMap.get(aLong);
                    if (netCapPort == null) {
                        lostPortId.add(aLong);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcUtils.close(rs);
                JdbcUtils.close(ps);
                JdbcUtils.close(conn);
            }
            addLost(lostPortId, calender, portMap);
            insertData(portMap, new Timestamp(calender.getTimeInMillis()));
        }

        private void insertData(Map<Long, NetCapPort> portMap, Timestamp time) {
            Map<Long, NetBusiPort> busiMap = getBusi();
            if (busiMap == null) {
                logger.error("没有找到业务信息");
                return;
            }
//            //按业务汇总
            NetCapPort netCapPort = null, tmp = null;
            List<Long> portids = null;
            List<NetCapPort> results = new ArrayList<>(50);
            double inflow = 0.0D, outflow = 0.0D;
            for (Map.Entry<Long, NetBusiPort> longListEntry : busiMap.entrySet()) {
                portids = longListEntry.getValue().getPortids();
                if (portids == null) {
                    continue;
                }
                inflow = 0.0D;
                outflow = 0.0D;
                netCapPort = new NetCapPort();
                for (Long portid : portids) {
                    tmp = portMap.get(portid);
                    if (tmp != null) {
                        inflow += tmp.getInflow();
                        outflow += tmp.getOutflow();
                    }
                }
                netCapPort.setPortid(longListEntry.getKey());
                netCapPort.setRecordTime(time);
                netCapPort.setInflow(inflow);
                netCapPort.setOutflow(outflow);
                results.add(netCapPort);
            }
            logger.info("插入数据库{}", results.size());
            insertOrUpdate(results, busiMap);

        }

        private void insertOrUpdate(List<NetCapPort> results, Map<Long, NetBusiPort> busiMap) {
            PreparedStatement ps = null;
            boolean isAuto = true;
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                isAuto = connection.getAutoCommit();
                connection.setAutoCommit(false);
                String sql = "insert into NET_CAP_BUSI (BUSIID ,RECORDTIME ,INPUTFLUX ,OUTPUTFLUX ,PORTNAME,BANDWIDTH) values (?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                final int batchSize = 1000;
                int index = 0;
                NetBusiPort netBusiPort;
                long busiid;
                for (NetCapPort netCapPort : results) {
                    busiid = netCapPort.getPortid();
                    netBusiPort = busiMap.get(busiid);
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
                } catch (SQLException e) {
                    logger.error("还原提交方式error", e);
                }
                JdbcUtils.close(ps);
                JdbcUtils.close(connection);
            }
        }

        private Map<Long, NetBusiPort> getBusi() {
            Connection conn = null;
            List<NetBusiPort> results = new ArrayList<>();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = dataSource.getConnection();
                String sql = "SELECT\n" +
                        "	b.busiid,\n" +
                        "	SUM (c.bandwidth) AS bandwidth\n" +
                        "FROM\n" +
                        "	net_busi_port b\n" +
                        "LEFT JOIN net_port c ON c.portid = b.portid\n" +
                        "GROUP BY\n" +
                        "	b.busiid";
                ps = conn.prepareStatement(sql);
                ps.setFetchSize(2000);
                rs = ps.executeQuery();
                Long busiid = null;
                Double bandwidth = 0D;
                Map<Long, Double> widthMap = new HashMap<>();
                while (rs.next()) {
                    busiid = rs.getLong(1);
                    bandwidth = rs.getDouble(2);
                    widthMap.put(busiid, bandwidth);
                }
                sql = "SELECT a.id as busiid,a.busiportname,b.portid from net_busiport a LEFT JOIN net_busi_port b on a.id=b.busiid";
                ps = conn.prepareStatement(sql);
                ps.setFetchSize(2000);
                rs = ps.executeQuery();
                Long portid = null;
                Map<Long, NetBusiPort> busiBeanMap = new HashMap<>();
                List<Long> ports;
                while (rs.next()) {
                    busiid = rs.getLong("busiid");
                    NetBusiPort netBusiPort = busiBeanMap.get(busiid);
                    if (netBusiPort == null) {
                        netBusiPort = new NetBusiPort();
                        Double aDouble = widthMap.get(busiid);
                        netBusiPort.setBusiportname(rs.getString("busiportname"));
                        netBusiPort.setId(busiid);
                        netBusiPort.setBandwidth(aDouble);
                        busiBeanMap.put(busiid, netBusiPort);
                    }
                    portid = rs.getLong("portid");
                    netBusiPort.addPortid(portid);
                }
                return busiBeanMap;
            } catch (SQLException e) {
                logger.info("", e);
            } finally {
                JdbcUtils.close(rs);
                JdbcUtils.close(ps);
                JdbcUtils.close(conn);
            }
            return null;
        }

        private void addLost(List<Long> lostPortId, Calendar time, Map<Long, NetCapPort> portbeans) {
            if (lostPortId.size() > 0) {
                Connection conn = null;
                PreparedStatement preparedStatement = null;
                ResultSet rs = null;
                try {
                    conn = dataSource.getConnection();
                    logger.error("补充端口数据{}", lostPortId);
                    String endTime = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");
                    time.add(Calendar.MINUTE, -5);
                    String startTime = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");
                    List<Object[]> timesTable = DataUtil.getTimes(startTime, endTime);
                    StringBuffer sql = new StringBuffer();
                    for (Object[] table : timesTable) {
                        if (sql.length() > 0) {
                            sql.append(" UNION all ");
                        }
                        sql.append("SELECT id, portid,recordtime,inputflux , outputflux  FROM net_cap_port_").append(table[0]).append(" where ");
                        if (lostPortId.size() > 0) {
                            sql.append(" portid in(");
                            for (int i = 0; i < lostPortId.size(); i++) {
                                if (i > 0) {
                                    sql.append(",");
                                }
                                sql.append("?");
                            }
                            sql.append(") and");
                        }
                        sql.append(" RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
                    }
                    preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
                    int pindex = 1;
                    for (Object[] objects : timesTable) {
                        for (Long aLong : lostPortId) {
                            preparedStatement.setLong(pindex++, aLong);
                        }
                        preparedStatement.setString(pindex++, objects[1].toString());
                        preparedStatement.setString(pindex++, objects[2].toString());
                    }
                    rs = preparedStatement.executeQuery();
                    Map<Long, NetCapPort> portMap = new HashMap<>(100);
                    NetCapPort netCapPort;
                    Long portid = null;
                    Timestamp recordtime = null;
                    double inputflux = 0D, outputflux = 0D;
                    while (rs.next()) {
                        netCapPort = new NetCapPort();
                        portid = rs.getLong("portid");
                        recordtime = rs.getTimestamp("recordtime");
                        netCapPort = new NetCapPort();
                        inputflux = rs.getDouble("inputflux");
                        outputflux = rs.getDouble("outputflux");
                        netCapPort.setInflow(inputflux);
                        netCapPort.setOutflow(outputflux);
                        netCapPort.setPortid(portid);
                        netCapPort.setRecordTime(recordtime);
                        portMap.put(portid, netCapPort);
                    }
                    StringBuffer lostsb = new StringBuffer();
                    for (Long aLong : lostPortId) {
                        netCapPort = portMap.get(aLong);
                        if (netCapPort == null) {
                            lostsb.append(",").append(aLong);
                        } else {
                            portbeans.put(aLong, netCapPort);
                        }
                    }
                    if (lostsb.length() > 0) {
                        logger.error("端口{}在{}的数据已经丢失", lostsb, endTime);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("补充数据错误", e);
                } finally {
                    JdbcUtils.close(rs);
                    JdbcUtils.close(preparedStatement);
                    JdbcUtils.close(conn);
                }
            }
        }
    }
}
