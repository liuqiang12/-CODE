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
public class AddLostPrev {
    private static final Logger logger = LoggerFactory.getLogger(AddLostPrev.class);
    @Autowired
    public DataSource dataSource;

    @Scheduled(cron = "0/30 * * * * ? ")//每天5分种压缩一次 延时20===采集程序
    public void run() {
        URL resource = Bootstrap.class.getClassLoader().getResource("lost_prev.task");
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
                    String startTime = null, endTime = null, addTime = null, addTimes = null, addTimee = null;
                    while ((tempString = reader.readLine()) != null) {
                        // 显示行号
                        if (line == 1) {
                            String[] split = tempString.split("\\|");
                            if (split.length != 4) {
                                logger.error("时间错误[开始时间|结束时间|补充日期]");
                                throw new Exception("任务时间错误");
                            }
                            String startDate = split[0];
                            String endDate = split[2];
                            String[] start_Time = split[1].split("-");
                            startTime = startDate + " " + start_Time[0];
                            endTime = startDate + " " + start_Time[1];
                            String[] add_Time = split[3].split("-");
                            addTimes = endDate + " " + add_Time[0];
                            addTimee = endDate + " " + add_Time[1];
                            try {
                                java.util.Date date = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
                                java.util.Date date1 = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");

                                if (!(DateFormatUtils.format(date, "yyyy-MM-dd")).equals(DateFormatUtils.format(date1, "yyyy-MM-dd"))) {
                                    logger.error("开始结束不是一天");
                                    throw new Exception("开始结束不是一天");
                                }
                                DateUtils.parseDate(addTimes, "yyyy-MM-dd HH:mm:ss");
                                DateUtils.parseDate(addTimee, "yyyy-MM-dd HH:mm:ss");
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
                        new TaskRun(startTime, endTime, addTimes, addTimee, null).run();
                    } else {
                        new TaskRun(startTime, endTime, addTimes, addTimee, lost).run();
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
        private String addTime;
        private List<Long> lost;
        private String addTimeEnd;

        public TaskRun(String startTime, String endTime, String addTime, String addTimeEnd, List<Long> lost) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.addTime = addTime;
            this.addTimeEnd = addTimeEnd;
            this.lost = lost;
        }

        @Override
        public void run() {
            try {
                if (startTime == null || endTime == null) {
                    logger.error("没有时间");
                    return;
                }
                logger.info("开始补充数据");
                Calendar instance = Calendar.getInstance();
                Connection conn = dataSource.getConnection();
//                List<NetCapPort> lastweek = new ArrayList<>(5000);
//                try {
//                    instance.setTime(DateUtils.parseDate(addTime, "yyyy-MM-dd HH:mm:ss"));
//                    String strs = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
//                    instance.setTime(DateUtils.parseDate(addTimeEnd, "yyyy-MM-dd HH:mm:ss"));
//                    String stre = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
//                    logger.debug("时间{}-{}", strs, stre);
//                    lastweek = getListTTT(conn, strs, stre, lost);
//                } catch (ParseException e) {
//                    logger.error("", e);
//                }
                List<NetCapPort> listTTT = getListTTT(conn, startTime, endTime, lost);
                JdbcUtils.closeConnection(conn);
                List<Long> times = DataUtil.getTimes(startTime, endTime, null);

//                Map<Long, Map<String, NetCapPort>> portAtTimeMap = new HashMap<>(5000);
//                Map<String, NetCapPort> portMap;
                long portid = 0;
//                long time;
//                Map<Long, List<NetCapPort>> tmap = new HashMap<>();
//                Map<Long, Map<String, NetCapPort>> hasMap = new HashMap<>(5000);
//                String timestr;
//                NetCapPort tmp;
//                if (lastweek != null) {
//                    for (NetCapPort netCapPort : lastweek) {
//                        portid = netCapPort.getPortid();
//                        portMap = portAtTimeMap.get(portid);
//                        if (portMap == null) {
//                            portMap = new HashMap<>(1000);
//                            portAtTimeMap.put(portid, portMap);
//                        }
//                        time = netCapPort.getRecordTime().getTime();
//                        instance.setTimeInMillis(time);
//                        timestr = DateFormatUtils.format(instance,"HH:mm");
//                        tmp = portMap.get(timestr);
//                        if (tmp == null) {
//                            portMap.put(timestr, netCapPort);
//                        }
//                    }
//                }
                List<NetCapPort> t = null;
                Map<Long, List<NetCapPort>> hasListMap = new HashMap<>(5000);
                for (NetCapPort netCapPort : listTTT) {
                    portid = netCapPort.getPortid();
                    t = hasListMap.get(portid);
                    if (t == null) {
                        t = new ArrayList<>(1000);
                        hasListMap.put(portid, t);
                    }
                    t.add(netCapPort);
                }
                List<NetCapPort> lost = new ArrayList<>(5000);
                for (Map.Entry<Long, List<NetCapPort>> longListEntry : hasListMap.entrySet()) {
                    List<NetCapPort> netCapPortList = addLostData(longListEntry.getKey(), times, longListEntry.getValue());
                    lost.addAll(netCapPortList);
                }
                conn = dataSource.getConnection();
                insert(conn, lost);
                JdbcUtils.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public static List<NetCapPort> addLostData(Long portid, List<Long> times, List<NetCapPort> netCapPorts) {
        long exectime = System.currentTimeMillis();
        if (netCapPorts == null || netCapPorts.size() == 0) {
            return new ArrayList<>();
        }
        Map<Long, NetCapPort> timesMap = new TreeMap<>();
        Calendar c = Calendar.getInstance();
        NetCapPort value;
        Timestamp recordTime;
        for (int i = 0, length = netCapPorts.size(); i < length; i++) {
            value = netCapPorts.get(i);
            recordTime = value.getRecordTime();
            c.setTime(recordTime);
            //5分种向下取
            double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
            c.set(Calendar.MINUTE, (int) v);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            recordTime.setTime(c.getTimeInMillis());
            timesMap.put(c.getTimeInMillis(), value);
        }
        value = null;
        recordTime = null;
        BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
        NetCapPort prev = new NetCapPort();
        NetCapPort next = null;
//        List<NetCapPort> lists = new ArrayList<>(5000);
        //最后一个 如果时间超了后面的不补仓
        Collections.sort(netCapPorts, new Comparator<NetCapPort>() {
            @Override
            public int compare(NetCapPort o1, NetCapPort o2) {
                if (o1.getRecordTime().getTime() == o2.getRecordTime().getTime()) {
                    return 1;
                }
                if (o1.getRecordTime().getTime() > o2.getRecordTime().getTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        List<NetCapPort> lost = new ArrayList<>(5000);
        NetCapPort last = netCapPorts.get(netCapPorts.size() - 1);
        NetCapPort netPortFlow = null;
        int size = times.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            Long time = times.get(i);
//            if (time > last.getRecordTime().getTime()) {
//                break;
//            }
            netPortFlow = timesMap.get(time);
            if (netPortFlow == null) {
                netPortFlow = new NetCapPort();
                copy.copy(prev, netPortFlow, null);
                if (i < times.size() - 1) {
                    Long nexttime = times.get(i + 1);
                    next = timesMap.get(nexttime);
                } else {
                    next = null;
                }
                netPortFlow.setPortid(portid);
                netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
                netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
                netPortFlow.setRecordTime(new Timestamp(time));
                sb.append("[").append(netPortFlow.getRecordTime()).append("]");

                lost.add(netPortFlow);
            }
            prev = netPortFlow;
        }
        if (sb.length() > 0) {
            logger.warn("端口{}缺失数据，补仓{}", portid, sb);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("补仓耗时{}", System.currentTimeMillis() - exectime);
        }
        return lost;
    }


}
