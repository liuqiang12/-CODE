package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.model.NetPortFlow;
import com.idc.vo.NetCapPort;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import utils.PropertyUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * Created by mylove on 2017/11/21.
 */
public class DataUtil {
    static Logger logger = LoggerFactory.getLogger(DataUtil.class);

    public static void main(String[] args) {
        Date startDate = DataUtil.Formatter("2017-11-17 00:00:00");
        Date endDate = DataUtil.Formatter("2017-12-05 23:59:59");
        List<Long> times = DataUtil.getTimes(startDate, endDate, "mi");
        for (Long time : times) {
            System.out.println(time);
        }
    }

    public static List<NetPortFlow> checkData(List<NetPortFlow> maxFlows, String startTime, String endTime, String cyc) {
        NetPortFlow prev = null;//前一个
        List<NetPortFlow> lists = new ArrayList<>();
        if (maxFlows == null) {
            return lists;
        }
        if (maxFlows.size() < 2) {
            return maxFlows;
        }
        NetPortFlow first = maxFlows.get(0);
        NetPortFlow last = maxFlows.get(maxFlows.size() - 1);
        //Date startDate = Formatter(startTime);
        Date endDate = Formatter(endTime);
        if (endDate == null || endDate.after(last.getRecordTime())) {
            endDate = last.getRecordTime();
        }
        List<Long> times = getTimes(first.getRecordTime(), endDate, cyc);
        ImmutableMap<Long, NetPortFlow> longNetPortFlowImmutableMap = Maps.uniqueIndex(maxFlows, new Function<NetPortFlow, Long>() {
            @Override
            public Long apply(NetPortFlow netPortFlow) {
                return netPortFlow.getRecordTime().getTime();
            }
        });
        for (int i = 0; i < times.size(); i++) {
            Long time = times.get(i);
            NetPortFlow netPortFlow = longNetPortFlowImmutableMap.get(time);
            if (netPortFlow == null) {
                try {

                    netPortFlow = new NetPortFlow();
                    PropertyUtils.copyProperties(netPortFlow, prev);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NetPortFlow next = null;
                if (i < times.size() - 1) {
                    Long nexttime = times.get(i + 1);
                    next = longNetPortFlowImmutableMap.get(nexttime);
                }
                netPortFlow.setInflow(addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
                netPortFlow.setOutflow(addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
                netPortFlow.setRecordTime(new Timestamp(time));
                if (logger.isErrorEnabled()) {
                    logger.error("【" + netPortFlow.getPortname() + "(" + netPortFlow.getPortid() + ")】缺失数据，补仓" + DateFormatUtils.format(netPortFlow.getRecordTime(), "yyyy-MM-dd HH:mm:ss"), netPortFlow);
                }
            }
            lists.add(netPortFlow);
            prev = netPortFlow;
        }
        return lists;
    }

    public static Date Formatter(String start) {
        Date enddate = null;
        try {
            enddate = DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
        }
        return enddate;
    }

    public static List<Long> getTimes(String start, String end, String cyc) {
        Date startDate = DataUtil.Formatter(start);
        Date endDate = DataUtil.Formatter(end);
        return getTimes(startDate, endDate, cyc);
    }

    public static List<Long> getTimes(Date start, Date end, String cyc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        if (cyc == null || "mi".equals(cyc)) {
            int fix = (int) (Math.floor(calendar.get(Calendar.MINUTE) / 5) * 5);
            calendar.set(Calendar.MINUTE, fix);
//            if (fix != 0) {
//                calendar.add(Calendar.SECOND, -((fix) * 60));
//            }
        } else if ("hour".equals(cyc)) {
//            int fix = (int) (Math.floor(calendar.get(Calendar.HOUR_OF_DAY) / 5)*5);
            calendar.set(Calendar.MINUTE, 0);
        } else {
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Long> longs = new ArrayList<>();
        getLongTime(longs, calendar, end, cyc);
        return longs;
    }

    public static void getLongTime(List<Long> longs, Calendar calendar, Date end, String cyc) {
        try {
            while (calendar.getTime().before(end)) {
                longs.add(calendar.getTime().getTime());
                if (cyc == null || "mi".equals(cyc)) {
                    try {
                        calendar.add(Calendar.MINUTE, 5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if ("hour".equals(cyc)) {
                        calendar.add(Calendar.HOUR_OF_DAY, 1);
                    } else {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
            }
//            if (calendar.getTime().before(end)) {
//                longs.add(calendar.getTime().getTime());
//                if (cyc == null || "mi".equals(cyc)) {
//                    try {
//                        calendar.add(Calendar.MINUTE, 5);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    if ("hour".equals(cyc)) {
//                        calendar.add(Calendar.HOUR_OF_DAY, 1);
//                    } else {
//                        calendar.add(Calendar.DAY_OF_MONTH, 1);
//                    }
//                }
//                getLongTime(longs, calendar, end, cyc);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("");
        }

    }

    public static double avg(List<Double> ds) {
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

    public static double addAvg(Double d, Double d2) {
        double value = 0D;
        if (d != null && d2 != null) {
            value = avg(Arrays.asList(d, d2));
        }
        if (d == null || d2 == null) {
            Random ra =new Random();
            double v = ra.nextDouble() * (0.04 - 0.01) + 0.01;
            if (d == null) {
                value = ra.nextInt(2) > 0 ? d2 + d2 * v : d2 - d2 * v;
            } else {
                value = ra.nextInt(2) > 0 ? d + d * v : d - d * v;
            }
        }
        return value;
    }

    public static List<Object[]> getTimes(String s, String e) {
        List<Object[]> time = new ArrayList<>();
        Calendar sc = Calendar.getInstance();
        Calendar ec = Calendar.getInstance();
        Date sdate = null;
        Date edate = null;
        try {
            sdate = DateUtils.parseDate(s, "yyyy-MM-dd HH:mm:ss");
            edate = DateUtils.parseDate(e, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        sc.setTimeInMillis(sdate.getTime());
        ec.setTimeInMillis(edate.getTime());
        int syear = sc.get(Calendar.YEAR);
        int eyear = ec.get(Calendar.YEAR);
        if(syear==eyear){
            int smonth = sc.get(Calendar.MONTH);
            int emonth = ec.get(Calendar.MONTH);
            if (smonth == emonth) {
                int month = sc.get(Calendar.MONTH) + 1;
                String monthstr = "";
                if(month<10){
                    monthstr= "0"+month;
                }else{
                    monthstr = ""+month;
                }
                time.add(new Object[]{sc.get(Calendar.YEAR), monthstr, DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss"), DateFormatUtils.format(ec.getTime(), "yyyy-MM-dd HH:mm:ss")});
            } else {
                Object[] ts = new Object[4];
                ts[0] = sc.get(Calendar.YEAR);
                int month = sc.get(Calendar.MONTH) + 1;
                if(month<10){
                    ts[1] = "0"+month;
                }else{
                    ts[1] = ""+month;
                }
                ts[2] = DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss");
                while (sc.get(Calendar.MONTH) < ec.get(Calendar.MONTH)) {
                    sc.set(Calendar.DATE, sc.getActualMaximum(Calendar.DATE));
                    sc.set(Calendar.HOUR_OF_DAY, sc.getActualMaximum(Calendar.HOUR_OF_DAY));
                    sc.set(Calendar.MINUTE, sc.getActualMaximum(Calendar.MINUTE));
                    sc.set(Calendar.SECOND, sc.getActualMaximum(Calendar.SECOND));
                    sc.set(Calendar.MILLISECOND, sc.getActualMaximum(Calendar.MILLISECOND));
                    ts[3] = DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss");
                    time.add(ts);

                    ts = new Object[4];
                    sc.add(Calendar.MONTH, 1);
                    sc.set(Calendar.DATE, sc.getActualMinimum(Calendar.DATE));
                    sc.set(Calendar.HOUR_OF_DAY, sc.getActualMinimum(Calendar.HOUR_OF_DAY));
                    sc.set(Calendar.MINUTE, sc.getActualMinimum(Calendar.MINUTE));
                    sc.set(Calendar.SECOND, sc.getActualMinimum(Calendar.SECOND));
                    sc.set(Calendar.MILLISECOND, sc.getActualMinimum(Calendar.MILLISECOND));
                    ts[0] = sc.get(Calendar.YEAR);
                    month = sc.get(Calendar.MONTH) + 1;
                    if(month<10){
                        ts[1] = "0"+month;
                    }else{
                        ts[1] = ""+month;
                    }
                    ts[2] = DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss");
                }
                ts[3] = DateFormatUtils.format(ec.getTime(), "yyyy-MM-dd HH:mm:ss");
                time.add(ts);
            }
        }else{
            Object[] ts = new Object[4];
            ts[0] = sc.get(Calendar.YEAR);
            int month = sc.get(Calendar.MONTH) + 1;
            if(month<10){
                ts[1] = "0"+month;
            }else{
                ts[1] = ""+month;
            }
            ts[2] = DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss");
            sc.set(Calendar.DAY_OF_MONTH,1);
            sc.set(Calendar.HOUR_OF_DAY,23);
            sc.set(Calendar.MINUTE,59);
            sc.set(Calendar.SECOND,59);
            sc.set(Calendar.MILLISECOND,59);
            sc.add(Calendar.MONTH,1);
            sc.add(Calendar.DAY_OF_MONTH,-1);
            ts[3] = DateFormatUtils.format(sc.getTime(), "yyyy-MM-dd HH:mm:ss");
            time.add(ts);
            ts = new Object[4];
            ts[0] = ec.get(Calendar.YEAR);
             month = ec.get(Calendar.MONTH) + 1;
            if(month<10){
                ts[1] = "0"+month;
            }else{
                ts[1] = ""+month;
            }
//            ts[0] =  DateFormatUtils.format(ec, "yyyy_MM");
            ts[3] = DateFormatUtils.format(ec.getTime(), "yyyy-MM-dd HH:mm:ss");
            ec.set(Calendar.DAY_OF_MONTH,1);
            ec.set(Calendar.MINUTE,0);
            ec.set(Calendar.SECOND,0);
            ec.set(Calendar.MILLISECOND,0);
            ec.set(Calendar.HOUR_OF_DAY,0);
            ts[2] = DateFormatUtils.format(ec.getTime(), "yyyy-MM-dd HH:mm:ss");
            time.add(ts);
        }

        return time;
    }

    public static Map<Long, List<NetCapPort>> checkData(String s, String e, List<NetCapPort> ps, List<Long> times) {
        Map<Long, Map<Long, NetCapPort>> maps = new HashMap<>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < ps.size(); i++) {
            NetCapPort netCapPort = ps.get(i);
            long portid = netCapPort.getPortid();
            Map<Long, NetCapPort> pmap = maps.get(portid);
            if (pmap == null) {
                pmap = new HashMap<>();
                maps.put(portid, pmap);
            }
            Timestamp recordTime = netCapPort.getRecordTime();
            long time = recordTime.getTime();
            c.setTimeInMillis(time);
            double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
            c.set(Calendar.MINUTE, (int) v);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            recordTime.setTime(c.getTimeInMillis());
            NetCapPort timeBean = pmap.get(c.getTimeInMillis());
            if (timeBean == null) {
                pmap.put(c.getTimeInMillis(), netCapPort);
            } else {
                if (recordTime.before(timeBean.getRecordTime())) {
                    pmap.put(c.getTimeInMillis(), timeBean);
                }
            }
        }
        Map<Long, List<NetCapPort>> listMap = new HashMap<>();
        for (Map.Entry<Long, Map<Long, NetCapPort>> longMapEntry : maps.entrySet()) {
            Map<Long, NetCapPort> timesMap = longMapEntry.getValue();
            List<NetCapPort> lists = new ArrayList<>();
            NetCapPort prev = new NetCapPort();//前一个
            NetCapPort next = null;
            prev.setPortid(longMapEntry.getKey());
            BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
            for (int i = 0; i < times.size(); i++) {
                Long time = times.get(i);
                NetCapPort netPortFlow = timesMap.get(time);
                if (netPortFlow == null) {
                    netPortFlow = new NetCapPort();
                    copy.copy(prev, netPortFlow, null);
                    if (i < times.size() - 1) {
                        Long nexttime = times.get(i + 1);
                        next = timesMap.get(nexttime);
                    } else {
                        next = null;
                    }
                    netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
                    netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
                    netPortFlow.setRecordTime(new Timestamp(time));
                    if (logger.isErrorEnabled()) {
                        logger.error("【" + netPortFlow.getId() + "(" + netPortFlow.getPortid() + ")】缺失数据，补仓" + DateFormatUtils.format(netPortFlow.getRecordTime(), "yyyy-MM-dd HH:mm:ss"), netPortFlow);
                    }
                }
                lists.add(netPortFlow);
                prev = netPortFlow;
            }
            listMap.put(longMapEntry.getKey(), lists);
        }
        return listMap;
    }

    /***
     * 每5分钟只取一个
     *
     * @param ps 原始数据
     */
    public static List<NetCapPort> distinctData(List<NetCapPort> ps) {
        if (ps == null || ps.size() == 0) {
            return new ArrayList<>();
        }
        long exectime = System.currentTimeMillis();
        Map<Long, NetCapPort> pmap = new HashMap<>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < ps.size(); i++) {
            NetCapPort netCapPort = ps.get(i);
            Timestamp recordTime = netCapPort.getRecordTime();
            c.setTime(recordTime);
            //5分种向下取
            double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
            c.set(Calendar.MINUTE, (int) v);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            //取整后时间
            long currTimeMi = c.getTimeInMillis();
            recordTime.setTime(currTimeMi);
            //当前时间如果不存在 直接添加 存在取时间后面的一个
            NetCapPort timeBean = pmap.get(currTimeMi);
            if (timeBean == null) {
                pmap.put(currTimeMi, netCapPort);
            } else {
                if (netCapPort.getRecordTime().getTime() > timeBean.getRecordTime().getTime()) {
                    logger.warn("端口{}在{}时间点数据重复，舍弃{},保留{}", netCapPort.getPortid(), netCapPort.getRecordTime(), timeBean.getId(), netCapPort.getId());
                    pmap.put(currTimeMi, netCapPort);
                }
            }
        }
//        logger.debug("检查数据重复性,耗时{}", System.currentTimeMillis() - exectime);
        List<NetCapPort> result = new ArrayList<>();
        for (Map.Entry<Long, NetCapPort> tmap : pmap.entrySet()) {
            result.add(tmap.getValue());
        }
//        logger.debug("检查数据重复性,耗时{}", System.currentTimeMillis() - exectime);
        return result;
    }

    /***
     * 添加丢失的数据
     *
     * @param portid
     * @param times
     * @param netCapPorts
     * @return
     */
    public static List<NetCapPort> addLostData(String portid, List<Long> times, List<NetCapPort> netCapPorts) {
        long exectime = System.currentTimeMillis();
        NetCapPort netPortFlow = null;
        if (netCapPorts == null || netCapPorts.size() == 0) {
            long l = Long.parseLong(portid);
            netCapPorts=new ArrayList<>();
            for (Long time : times) {
                netPortFlow= new NetCapPort();
                netPortFlow.setRecordTime(new Timestamp(time));
                netPortFlow.setPortid(l);
                netCapPorts.add(netPortFlow);
            }
            return netCapPorts;
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
        List<NetCapPort> lists = new ArrayList<>(5000);
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

        NetCapPort last = netCapPorts.get(netCapPorts.size() - 1);
        int size = times.size();
        StringBuffer lostsb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            Long time = times.get(i);
            if (time > last.getRecordTime().getTime()) {
                break;
            }
            netPortFlow = timesMap.get(time);
            if (netPortFlow == null) {
//                netPortFlow = new NetCapPort();
//                copy.copy(prev, netPortFlow, null);
//                if (i < size - 1) {
//                    int i1=1;
//                    while (i1+i<size){
//                        Long nexttime = times.get(i1+i);
//                        next = timesMap.get(nexttime);
//                        if(next!=null){
//                            break;
//                        }
//                        i1++;
//                    }
//                    netPortFlow.setInflow(getflow(prev.getInflow(),next==null?null:next.getInflow(),i1));
//                    netPortFlow.setOutflow(getflow(prev.getOutflow(), next==null?null:next.getOutflow(), i1));
//                    netPortFlow.setRecordTime(new Timestamp(time));
//                    Long nexttime = times.get(i + 1);
//                    next = timesMap.get(nexttime);
//                } else {
//                    next = null;
//                    netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
//                    netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
//                    netPortFlow.setRecordTime(new Timestamp(time));
//                }
                netPortFlow = new NetCapPort();
                netPortFlow.setInflow(0);
                netPortFlow.setOutflow(0);
                netPortFlow.setRecordTime(new Timestamp(time));
                lists.add(netPortFlow);
                lostsb.append(",[").append(netPortFlow.getRecordTime()).append("]");
            }else{
                lists.add(netPortFlow);
            }
//            lists.add(netPortFlow);
//            prev = netPortFlow;
        }
        if(lostsb.length()>0){
            logger.warn("端口{}缺失数据，{}", portid,lostsb);
        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("补仓耗时{}", System.currentTimeMillis() - exectime);
//        }
        return lists;
    }

    private static double getflow(double inflow, Double nextflow, int i1) {
        Random ra = new Random();
        double v = ra.nextDouble() * (0.02 - 0.005) + 0.005;
        int i2 = ra.nextInt(2);
        if(nextflow==null){
            return i2>0?inflow+inflow*v:inflow-inflow*v;
        }
        double diff = inflow - nextflow;
        //均匀分布差
        double avgdiff = (diff / (i1+1));
        if(i2>0){
            avgdiff=avgdiff+(avgdiff*v);
        }else{
            avgdiff = avgdiff-(avgdiff*v);
        }
       return  inflow-avgdiff;
    }

    /***
     * 按规定时间压缩
     *
     * @param netCapPorts
     * @param cyc
     * @return
     */
    public static List<NetCapPort> Zip(List<NetCapPort> netCapPorts, String cyc) {
        //5分钟不在压缩
        if (cyc == null || "mi".equals(cyc)) {
            return netCapPorts;
        } else {
            //小时和天相加压缩
            if (netCapPorts == null || netCapPorts.size() == 0) {
                return new ArrayList<>();
            }
            long exectime = System.currentTimeMillis();
            Calendar c = Calendar.getInstance();
            Map<Long, NetCapPort> hmap = new TreeMap<>();
            for (NetCapPort netCapPort : netCapPorts) {
                c.setTimeInMillis(netCapPort.getRecordTime().getTime());
                //对当前时间计算 格式后判断在那一range
                if ("hour".equals(cyc)) {
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.MILLISECOND, 0);
                } else {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.MILLISECOND, 0);
                }
                long timeInMillis = c.getTimeInMillis();
                NetCapPort countBean = hmap.get(timeInMillis);
                if (countBean == null) {
                    netCapPort.getRecordTime().setTime(timeInMillis);
                    hmap.put(timeInMillis, netCapPort);
                } else {
                    countBean.setInflow(netCapPort.getInflow() + countBean.getInflow());
                    countBean.setOutflow(netCapPort.getOutflow() + countBean.getOutflow());
                }
            }
            List<NetCapPort> results = new ArrayList<>();
            for (Map.Entry<Long, NetCapPort> longNetCapPortEntry : hmap.entrySet()) {
                results.add(longNetCapPortEntry.getValue());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("压缩数据耗时{}", System.currentTimeMillis() - exectime);
            }
            return results;
        }
    }

    public static List<NetCapPort> checkAllNew(List<NetCapPort> ps, String portid, List<Long> times, String cyc) {
        //如果为空 直接返回
        if (ps == null || ps.size() == 0) {
            return new ArrayList<>();
        }
        long exectime = System.currentTimeMillis();
        //时间的Map 主要是用来查当前时间是否已经有了数据
        Map<Long, NetCapPort> timeMap = new TreeMap<>();
        //时间集合 方便快速迭代
        List<Long> baseTimes = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        // 排除重复的点
        for (int i = 0; i < ps.size(); i++) {
            NetCapPort netCapPort = ps.get(i);
            Timestamp recordTime = netCapPort.getRecordTime();
            c.setTime(recordTime);
            //5分种向下取
            double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
            c.set(Calendar.MINUTE, (int) v);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            //取整后时间
            long currTimeMi = c.getTimeInMillis();
            recordTime.setTime(currTimeMi);
            //当前时间如果不存在 直接添加 存在取时间后面的一个
            NetCapPort timeBean = timeMap.get(currTimeMi);
            if (timeBean == null) {
                baseTimes.add(currTimeMi);
                timeMap.put(currTimeMi, netCapPort);
            } else {
                if (netCapPort.getRecordTime().getTime() > timeBean.getRecordTime().getTime()) {
                    timeMap.put(currTimeMi, netCapPort);
                }
            }
        }
        NetCapPort netCapPort = null;
        //本身就是5分种 所以不压缩
        BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
        NetCapPort prev = new NetCapPort();
        NetCapPort first = timeMap.get(baseTimes.get(0));
        NetCapPort next = null;
        //最后一个的时间 防止超出最后时间产生无用点
        NetCapPort last = timeMap.get(baseTimes.get(baseTimes.size() - 1));
        List<NetCapPort> lists = new ArrayList<>(5000);
        //最后一个 如果时间超了后面的不补仓
        NetCapPort netPortFlow = null;
        Date CurrTime = new Date();
        for (int i = 0; i < times.size(); i++) {
            Long time = times.get(i);
            if (time > CurrTime.getTime()) {
                logger.error("超过当前时间不于计算");
                break;
            }
            if (time < first.getRecordTime().getTime()) {
                continue;
            }
            netPortFlow = timeMap.get(time);
            if (netPortFlow == null) {
                netPortFlow = new NetCapPort();
                copy.copy(prev, netPortFlow, null);
                if (i < times.size() - 1) {
                    Long nexttime = times.get(i + 1);
                    next = timeMap.get(nexttime);
                } else {
                    next = null;
                }
                netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
                netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
                netPortFlow.setRecordTime(new Timestamp(time));
                logger.warn("端口{}缺失数据，补仓{}", portid, netPortFlow.getRecordTime());
                timeMap.put(time, netPortFlow);
            }
            lists.add(netPortFlow);
            prev = netPortFlow;
        }
        return lists;
    }
}
