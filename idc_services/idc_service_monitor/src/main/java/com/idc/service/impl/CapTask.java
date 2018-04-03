//package com.idc.service.impl;
//
//import com.idc.model.NetBusiPort;
//import com.idc.vo.NetCapPort;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.JdbcUtils;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created by mylove on 2017/12/14.
// */
//public class CapTask implements Runnable {
//    private static final Logger logger = LoggerFactory.getLogger(CapTask.class);
//    private final JdbcTemplate jdbcTemplate;
//    protected NetBusiPort netBusiPort;
//    protected String startTime;
//    protected String endTime;
//    protected String tableName;
//
//    public CapTask(JdbcTemplate jdbcTemplate, NetBusiPort netBusiPort, String startTime, String endTime, String tableName) {
//        this.netBusiPort = netBusiPort;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.tableName = tableName;
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void run() {
//        Connection connection = null;
//        try {
//            connection = jdbcTemplate.getDataSource().getConnection();
//            logger.info("业务{}:_________",netBusiPort.getBusiportname());
//            Comp(connection, netBusiPort.getPortids(), startTime, endTime);
//            connection.close();
//        } catch (Exception e) {
//            try {
//                connection.close();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            logger.error("", e);
//        }
//    }
//
//    public void Comp(Connection conn, List<Long> ids, String s, String e) throws Exception {
////        conn = DBUtil.getConnection(jdbcTemplate);
//        Map<Long, List<NetCapPort>> results = new HashMap<>();
//
//        List<Object[]> timeList = DataUtil.getTimes(s, e);
//        List<Long> times = DataUtil.getTimes(s, e, null);
//        for (Object[] objects : timeList) {
//
//            try {
//                String sqlstr = "SELECT id,portid,recordtime,inputflux as inflow, outputflux as outflow FROM " +
//                        "net_cap_port_" + objects[0] + "_" + objects[1] + " " +
//                        "where  portid =?  and RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') " +
//                        "and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss') order by recordtime";
//                PreparedStatement preparedStatement = conn.prepareStatement(sqlstr.toString());
//                preparedStatement.setFetchSize(50000);
//
//                for (Long id : ids) {
//                    int pindex = 1;
//                    preparedStatement.setLong(pindex++, id);
//                    preparedStatement.setString(pindex++, objects[2].toString());
//                    preparedStatement.setString(pindex++, objects[3].toString());
//                    ResultSet rs = preparedStatement.executeQuery();
////                    List<NetCapPort> portMap = results.get(id);
////                    if (portMap == null) {
////                        portMap = new ArrayList<>();
////                        results.put(id,portMap);
////                    }
//                    logger.info("______________端口{},时间{}",id,objects);
//                    NetCapPort t;
//                    while (rs.next()) {
//                        t = new NetCapPort();
//                        t.setId(rs.getLong("id"));
//                        t.setPortid(rs.getLong("portid"));
//                        t.setRecordTime(rs.getTimestamp("recordtime"));
//                        t.setInflow(rs.getDouble("inflow"));
//                        t.setOutflow(rs.getDouble("outflow"));
//                       // portMap.add(t);
//                    }
//                    rs.close();
//                    rs = null;
//                }
//                JdbcUtils.closeStatement(preparedStatement);
//                preparedStatement.close();
//                preparedStatement = null;
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//        }
//        Map<Long,NetCapPort> timevalue = new TreeMap<>();
//        NetCapPort captmp;
//        for (Map.Entry<Long, List<NetCapPort>> stringListEntry : results.entrySet()) {
//            logger.info("__________________________________________________检验缺失数据{}：",stringListEntry.getKey());
//            List<NetCapPort> netCapPorts = DataUtil.addLostData(stringListEntry.getKey().toString(), times, stringListEntry.getValue());
//            for (NetCapPort netCapPort : netCapPorts) {
//                 captmp = timevalue.get(netCapPort.getRecordTime().getTime());
//                if(captmp==null){
//                    timevalue.put(netCapPort.getRecordTime().getTime(),captmp);
//                }else{
//                    captmp.setInflow(captmp.getInflow()+netCapPort.getInflow());
//                    captmp.setOutflow(captmp.getOutflow() + netCapPort.getOutflow());
//                }
//            }
//        }
//        for (NetCapPort netCapPort : timevalue.values()) {
//            logger.info("{}",netCapPort);
//        }
//    }
//
////    private ArrayList<Object> checkAllPort(List<NetCapPort> ps, String s, String e) {
////        if (ps == null || ps.size() == 0) {
////            return new ArrayList<>();
////        }
////        Calendar c = Calendar.getInstance();
////        Map<Long, NetCapPort> timeMap = new TreeMap<>();
////        for (int i = 0; i < ps.size(); i++) {
////            NetCapPort netCapPort = ps.get(i);
////            Timestamp recordTime = netCapPort.getRecordTime();
////            c.setTime(recordTime);
////            //5分种向下取
////            double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
////            c.set(Calendar.MINUTE, (int) v);
////            c.set(Calendar.SECOND, 0);
////            c.set(Calendar.MILLISECOND, 0);
////            //取整后时间
////            long currTimeMi = c.getTimeInMillis();
////            recordTime.setTime(currTimeMi);
////            //当前时间如果不存在 直接添加 存在取时间后面的一个
////            NetCapPort timeBean = timeMap.get(currTimeMi);
////            if (timeBean == null) {
////                timeMap.put(currTimeMi, netCapPort);
////            } else {
////                if (netCapPort.getId() > timeBean.getId()) {
////                    timeMap.put(currTimeMi, netCapPort);
////                }
////            }
////        }
////        List<Long> times = DataUtil.getTimes(s, e, null);
////        NetCapPort netPortFlow = null;
////        BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
////        List<NetCapPort> lists = new ArrayList<>(5000);
////        NetCapPort prev = new NetCapPort();
////        NetCapPort next = null;
////        for (int i = 0; i < times.size(); i++) {
////            Long time = times.get(i);
////            netPortFlow = timeMap.get(time);
////            if (netPortFlow == null) {
////                netPortFlow = new NetCapPort();
////                copy.copy(prev, netPortFlow, null);
////                if (i < times.size() - 1) {
////                    Long nexttime = times.get(i + 1);
////                    next = timeMap.get(nexttime);
////                } else {
////                    next = null;
////                }
////                netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
////                netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
////                netPortFlow.setRecordTime(new Timestamp(time));
////                logger.warn("端口{}缺失数据，补仓{}", netPortFlow.getPortid(), netPortFlow.getRecordTime());
////                timeMap.put(time, netPortFlow);
////            }
////            lists.add(netPortFlow);
////            prev = netPortFlow;
////        }
////        return null;
////    }
//}
