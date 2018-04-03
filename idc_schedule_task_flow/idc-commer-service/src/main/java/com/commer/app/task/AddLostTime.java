package com.commer.app.task;

import com.sun.jdi.Bootstrap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by mylove on 2018/1/2.
 */
@Component
public class AddLostTime {
    private static final Logger logger = LoggerFactory.getLogger(AddLostTime.class);
    @Autowired
    public DataSource dataSource;

    @Scheduled(cron = "0/30 * * * * ? ")//每天5分种压缩一次 延时20===采集程序
    public void run() {
        URL resource = Bootstrap.class.getClassLoader().getResource("lost_time.task");
        if (resource != null) {
            File file = new File(resource.getFile());
            if (file.exists() && file.isFile()) {
                BufferedReader reader = null;
                try {
                    // 一次读一个字符
                    reader = new BufferedReader(new FileReader(file));
                    String tempString = null;
                    int line = 1;
                    // 一次读入一行，直到读入null为文件结束
                    boolean isAll = false;
                    List<Long> lost = new ArrayList<>(100);
                    String taskid = null;
                    String startTime = null, endTime = null;
                    while ((tempString = reader.readLine()) != null) {
                        // 显示行号
                        if (line == 1) {
                            String[] split = tempString.split("\\|");
                            if (split.length != 2) {
                                logger.error("时间错误[开始时间|结束时间|补充日期]");
                                throw new Exception("任务时间错误");
                            }
                            String startDate = split[0];
                            String[] start_Time = split[1].split("-");
                            startTime = startDate + " " + start_Time[0];
                            endTime = startDate + " " + start_Time[1];
                            try {
                                java.util.Date date = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
                                java.util.Date date1 = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");

                                if (!(DateFormatUtils.format(date, "yyyy-MM-dd")).equals(DateFormatUtils.format(date1, "yyyy-MM-dd"))) {
                                    logger.error("开始结束不是一天");
                                    throw new Exception("开始结束不是一天");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                logger.error("日期格式错误，:yyyy-MM-dd HH:mm:ss");
                                try {
                                    reader.close();
                                } catch (Exception e1) {
                                    logger.error("关闭文件错误", e1);
                                }
                                try {
                                    file.delete();
                                } catch (Exception e1) {
                                    logger.error("删除文件错误");
                                }
                                throw new Exception("日期格式错误，:yyyy-MM-dd HH:mm:ss");
                            }
                        } else {
                            if (tempString == null || "".equals(tempString) || "all".equals(tempString.toLowerCase())) {
                                isAll = true;
                                break;
                            } else {
                                lost.add(Long.parseLong(tempString));
                            }
                        }
                        line++;
                    }
                    reader.close();
                    try {
                        file.delete();
                    } catch (Exception e1) {
                        logger.error("删除文件错误");
                    }
                    if (isAll) {
                        new TaskRun(startTime, endTime, null).run();
                    } else {
                        new TaskRun(startTime, endTime, lost).run();
                    }
                } catch (Exception e) {
                    logger.error("", e);
                } finally {
                    try {
                        reader.close();
                    } catch (Exception e1) {
                        logger.error("关闭文件错误", e1);
                    }
                }
            }
        }
    }

    public List<NetCapPort> getListTTT(Connection conn, String startTime, String endTime, List<Long> portids) {
        List<NetCapPort> netCapPorts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            List<Object[]> times1 = DataUtil.getTimes(startTime, endTime);
            StringBuffer sql = new StringBuffer();
            for (Object[] objects : times1) {
                if (sql.length() > 0) {
                    sql.append(" UNION all ");
                }
                sql.append("SELECT id, portid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_").append(objects[0]).append(" where  ");
                sql.append(" RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')");
                if (portids != null && portids.size() > 0) {
                    sql.append(" and portid in(");
                    for (int i = 0; i < portids.size(); i++) {
                        if (i > 0) {
                            sql.append(",");
                        }
                        sql.append("?");
                    }
                    sql.append(")");
                }

            }
            preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
            int pindex = 1;
            for (Object[] objects : times1) {
                preparedStatement.setString(pindex++, objects[1].toString());
                preparedStatement.setString(pindex++, objects[2].toString());
                if (portids != null && portids.size() > 0) {
                    for (Long portid : portids) {
                        preparedStatement.setLong(pindex++, portid);
                    }
                }
            }
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
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            logger.error("", e1);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(preparedStatement);
        }
        return netCapPorts;
    }


    class TaskRun implements Runnable {
        private String startTime;
        private String endTime;
        private List<Long> lost;


        public TaskRun(String startTime, String endTime, List<Long> lost) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.lost = lost;
        }

        @Override
        public void run() {
            try {
                if (startTime == null || endTime == null) {
                    logger.error("没有时间");
                    return;
                }
                logger.info("开始补充时间段数据 补充方式：前后趋势");
                Calendar instance = Calendar.getInstance();
                Connection conn = dataSource.getConnection();
                List<NetCapPort> listTTT = getListTTT(conn, startTime, endTime, lost);
                JdbcUtils.closeConnection(conn);
                List<Long> times = DataUtil.getTimes(startTime, endTime, null);
                long portid = 0;
                List<NetCapPort> tmp = null;
                Map<Long, List<NetCapPort>> hasListMap = new HashMap<>(5000);
                for (NetCapPort netCapPort : listTTT) {
                    portid = netCapPort.getPortid();
                    tmp = hasListMap.get(portid);
                    if (tmp == null) {
                        tmp = new ArrayList<>(1000);
                        hasListMap.put(portid, tmp);
                    }
                    tmp.add(netCapPort);
                }
                List<NetCapPort> lost = new ArrayList<>(5000);
                for (Map.Entry<Long, List<NetCapPort>> longListEntry : hasListMap.entrySet()) {
                    List<NetCapPort> netCapPortList = DataUtil.addLostData(longListEntry.getKey(), times, longListEntry.getValue());
                    lost.addAll(netCapPortList);
                }
                conn = dataSource.getConnection();
                for (NetCapPort netCapPort : lost) {
                    logger.info("{}-{}-{}-{}",netCapPort.getPortid(),netCapPort.getRecordTime(),netCapPort.getInflow(),netCapPort.getOutflow());
                }
//                insert(conn, lost);
                JdbcUtils.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<NetCapPort> addLostData(long portid, List<Long> times, Map<Long, Map<String, NetCapPort>> hasMap, Map<Long, Map<String, NetCapPort>> srcMap) {
            long exectime = System.currentTimeMillis();
            Map<Long, NetCapPort> timesMap = new TreeMap<>();
            Calendar c = Calendar.getInstance();
            NetCapPort value;
            Timestamp recordTime;
            value = null;
            recordTime = null;
            BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
            NetCapPort prev = new NetCapPort();
            NetCapPort next = null;
            List<NetCapPort> lost = new ArrayList<>(5000);
            NetCapPort netPortFlow = null;
            Calendar calendar = Calendar.getInstance();
            int size = times.size();
            Map<String, NetCapPort> portMap = hasMap.get(portid);
            StringBuffer sb = new StringBuffer();
            String timestr;
            NetCapPort cap = null;
            Map<String, NetCapPort> srcportMap = srcMap.get(portid);
            for (int i = 0; i < size; i++) {
                Long time = times.get(i);
                calendar.setTimeInMillis(time);
                timestr = DateFormatUtils.format(calendar, "HH:mm");
                if (portMap == null) {
                    if (srcportMap == null) {
                        sb.append("[").append(new Timestamp(time)).append("]");
                    } else {
                        //补充时间没有就用前后5分种的
                        cap = srcportMap.get(timestr);
                        if (cap == null) {
                            calendar.add(Calendar.MINUTE, -5);
                            timestr = DateFormatUtils.format(calendar, "HH:mm");
                            cap = srcportMap.get(timestr);
                        }
                        if (cap == null) {
                            calendar.add(Calendar.MINUTE, 10);
                            timestr = DateFormatUtils.format(calendar, "HH:mm");
                            cap = srcportMap.get(timestr);
                        }
                        if (cap == null) {
                            sb.append("[").append(new Timestamp(time)).append("]");
                        } else {
                            cap.getRecordTime().setTime(time);
                            cap.setInflow(DataUtil.getflow(cap.getInflow(), null, 1));
                            cap.setOutflow(DataUtil.getflow(cap.getOutflow(), null, 1));
                            lost.add(cap);
                        }
                    }
                } else {
                    cap = portMap.get(timestr);
                    if (cap == null) {
                        cap = new NetCapPort();
                        copy.copy(prev, cap, null);
                        if (cap.getRecordTime() == null) {
                            cap.setRecordTime(new Timestamp(time));//.setTime(time);
                        } else {
                            cap.getRecordTime().setTime(time);
                            cap.setInflow(DataUtil.getflow(cap.getInflow(), null, 1));
                            cap.setOutflow(DataUtil.getflow(cap.getOutflow(), null, 1));
                            lost.add(cap);
                        }
                    }
                }
                prev = cap;
            }
            if (sb.length() > 0) {
                logger.info("{}--{}", portid, sb);
            }
            return lost;
        }

        public NetCapPort getCap(long portid, Calendar ca, String time, Map<String, NetCapPort> timesMap, Map<Long, Map<String, NetCapPort>> srcMap) {
            NetCapPort netCapPort = null;
            if (timesMap != null) {
                netCapPort = timesMap.get(time);
                if (netCapPort != null) {
                    return netCapPort;
                }
            }
            Map<String, NetCapPort> portMap = srcMap.get(portid);
            String timestr;
            if (portMap != null) {
//                timestr = DateFormatUtils.format(ca,"HH:mm:ss");
                netCapPort = portMap.get(time);
                if (netCapPort == null) {
                    for (int i = 0; i < 20; i++) {
                        if (netCapPort == null) {
                            ca.add(Calendar.MINUTE, 5);
                            timestr = DateFormatUtils.format(ca, "HH:mm");
                            netCapPort = portMap.get(timestr);
                        } else {
                            break;
                        }
                    }
                }
                if (netCapPort == null) {
//                    ca.setTimeInMillis(time);
                    for (int i = 0; i < 20; i++) {
                        if (netCapPort == null) {
                            ca.add(Calendar.MINUTE, 5);
                            timestr = DateFormatUtils.format(ca, "HH:mm");
                            netCapPort = portMap.get(timestr);
                        } else {
                            break;
                        }
                    }
                }
            }
//            if (netCapPort != null) {
//                netCapPort.getRecordTime().setTime(time);
//            }
            return netCapPort;
        }

        private void insert(Connection connection, List<NetCapPort> netCapPorts) {
            try {
                String sql = "select max(id) from net_cap_port_2017_12";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                Long idindex = 0L;
                while (resultSet.next()) {
                    idindex = resultSet.getLong(1);
                }
                com.alibaba.druid.util.JdbcUtils.close(resultSet);
                com.alibaba.druid.util.JdbcUtils.close(ps);
                //sql="MERGE INTO  NET_CAP_BUSI T1 USING ( SELECT ? as busiid, to_date(?,'yyyy-mm-dd hh24:mi:ss') as recordtime,? as inputflux,? as outputflux,? as inputflux FROM dual ) T2 ON (T1. busiid = T2. busiid and t1.recordtime=t2.recordtime) WHEN MATCHED THEN UPDATE SET t1.inputflux=t2.inputflux WHEN NOT MATCHED THEN INSERT (busiid, recordtime,inputflux) VALUES (T2. busiid, T2.recordtime,t2.inputflux);";
                sql = "insert into net_cap_port_2017_12(id,recordtime,routid,portid,portname,inputflux,outputflux,portstatus) values(?,?,?,?,?,?,?,?)";
                boolean isAuto = connection.getAutoCommit();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(sql);
                final int batchSize = 10000;
                int index = 0;
                for (NetCapPort netCapPort : netCapPorts) {
                    ps.setLong(1, ++idindex);
                    ps.setTimestamp(2, netCapPort.getRecordTime());
                    ps.setDouble(3, 0);
                    ps.setDouble(4, netCapPort.getPortid());
                    ps.setString(5, "");
                    ps.setDouble(6, netCapPort.getInflow());
                    ps.setDouble(7, netCapPort.getOutflow());
                    ps.setString(8, "");
//                    ps.executeUpdate();
//                    connection.commit();
//                    logger.info("{}",index++);
                    ps.addBatch();
                    index++;
                    if (index % batchSize == 0) {
                        ps.executeBatch();
                        connection.commit();
                        ps.clearBatch();
                        logger.info("插入到{}条", index);
                    }
                }
                ps.executeBatch();
                connection.commit();
                ps.clearBatch();
                logger.info("插入业务流量完成");
                connection.setAutoCommit(isAuto);
                com.alibaba.druid.util.JdbcUtils.close(ps);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("", e);
            }
        }
    }


}
