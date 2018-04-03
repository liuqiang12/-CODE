package com.idc.service.impl;

import com.alibaba.fastjson.JSON;
import com.idc.mapper.BusPortMapper;
import com.idc.mapper.NetPortFlowMapper;
import com.idc.model.*;
import com.idc.service.NetPortFlowService;
import com.idc.service.NetPortService;
import com.idc.vo.NetCapPort;
import com.idc.vo.NetCapPortDetail;
import com.idc.vo.PortCapSerial;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import utils.StringUtil;
import utils.typeHelper.MapHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author mylove
 * @date 2017/7/11
 */
@Service
public class NetPortFlowServiceImpl implements NetPortFlowService {
    @Autowired
    private NetPortFlowMapper flowMapper;
    @Autowired
    private BusPortMapper busPortMapper;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private getFlowHelp getFlowHelp;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    Logger logger = LoggerFactory.getLogger(NetPortFlowServiceImpl.class);

    public void sync(Long busiid) {
        List<String> busiarr = new ArrayList<>();
        if (busiid == null) {
            List<NetBusiPort> list = busPortMapper.queryList();
            for (NetBusiPort netBusiPort : list) {
                busiarr.add(netBusiPort.getId().toString());
            }
        } else {
            busiarr.add(busiid.toString());
        }
        for (String s : busiarr) {
            //getPortFlowDetail(null,1,Arrays.asList(s),)
        }

    }

    public List<String> getCurrString(List<String> ids) {
        List<String> list = null;
        try {
            redisTemplate.setDefaultSerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashValueSerializer(new StringRedisSerializer());
            list = redisTemplate.opsForHash().multiGet("net_port_curr", ids);
            if(list==null||list.size()==0){
                logger.info("从redis获取数据Null{}", StringUtil.list2String(ids,","));
            }
        } catch (Exception e) {
            logger.error("从redis获取数据失败", e);
        }
        return list;
    }

    @Override
    public List<NetPortFlow> getCurrList(List<String> ids) {
        List<String> list = getCurrString(ids);
        if (list == null) {
            logger.error("获取性能数据NULL");
            return null;
        }
        List<NetPortFlow> flows = new ArrayList<>(1000);
        Map<String, PortCapSerial> map = new HashMap<>(5000);
        for (String s : list) {
            if (s == null) {
                continue;
            }
            PortCapSerial portCapSerial = JSON.parseObject(s, PortCapSerial.class);
            NetPortFlow netPortFlow = new NetPortFlow();
            netPortFlow.setInflow(portCapSerial.getInputFlux());
            netPortFlow.setOutflow(portCapSerial.getOutputFlux());
            netPortFlow.setRecordTime(portCapSerial.getAddTime());
            netPortFlow.setPortid(portCapSerial.getPortid());
            netPortFlow.setDeviceid(portCapSerial.getRoutid());
            netPortFlow.setPortname(portCapSerial.getPortname());
            flows.add(netPortFlow);
        }
        logger.debug("获取性能数据大小{}",flows.size());
        return flows;
    }

    @Override
    public Map<String, NetPortFlow> getCurrMap(List<String> ids) {
        List<String> list = getCurrString(ids);
        if(list==null){
            logger.error("获取性能数据NULL");
            return null;
        }
        Map<String, NetPortFlow> map = new HashMap<>(5000);
        for (String s : list) {
            if (s == null) {
                continue;
            }
            PortCapSerial portCapSerial = JSON.parseObject(s, PortCapSerial.class);
            NetPortFlow netPortFlow = new NetPortFlow();
            netPortFlow.setInflow(portCapSerial.getInputFlux());
            netPortFlow.setOutflow(portCapSerial.getOutputFlux());
            netPortFlow.setRecordTime(portCapSerial.getAddTime());
            netPortFlow.setPortid(portCapSerial.getPortid());
            netPortFlow.setDeviceid(portCapSerial.getRoutid());
            netPortFlow.setBandwidth(portCapSerial.getBandwidth());
            netPortFlow.setPortname(portCapSerial.getPortname());
            netPortFlow.setAdminstatus(portCapSerial.getAdminStatus());
            map.put(portCapSerial.getPortid().toString(), netPortFlow);
        }
        logger.debug("获取性能Map数据大小{}",map.size());
        return map;
    }

    @Override
    public List<NetPortFlow> queryListJoinFlowByObject(NetPort netPort) {

        Calendar calendar = Calendar.getInstance();
        double v = Math.floor(calendar.get(Calendar.MINUTE) / 5) * 5;
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, (int) v);
        long timeInMillis = calendar.getTimeInMillis();

        List<NetPortFlow> flow = flowMapper.queryListJoinFlowByObject(netPort);
        List<String> ids = new ArrayList<>();
        for (NetPortFlow netPortFlow : flow) {
            ids.add(netPortFlow.getPortid().toString());
        }
        Map<String, NetPortFlow> flowMap = getCurrMap(ids);
        if(flowMap!=null){
            BeanCopier copy = BeanCopier.create(NetPortFlow.class, NetPortFlow.class, false);
            for (NetPortFlow netPortFlow : flow) {
                NetPortFlow portCapSerial = flowMap.get(netPortFlow.getPortid().toString());
                if (portCapSerial != null) {
                    if (portCapSerial.getAdminstatus() != 1) {
                        logger.info("port{}的状态down", netPortFlow.getPortid());
                        continue;
                    }
                    if (portCapSerial.getInflow() == -1 && portCapSerial.getOutflow() == -1) {
                        logger.info("port{}的采集数据异常",netPortFlow.getPortid());
                        continue;
                    }
                    //防止出现时间到了这个点然而采集还没有完成
                    if (portCapSerial.getRecordTime().getTime() != timeInMillis || portCapSerial.getRecordTime().getTime() < (timeInMillis - 5 * 60 * 1000)) {
                        logger.info("port{}采集中",netPortFlow.getPortid());
                        continue;
                    }
                    netPortFlow.setOutflow(portCapSerial.getOutflow());
                    netPortFlow.setInflow(portCapSerial.getInflow());
                    netPortFlow.setRecordTime(portCapSerial.getRecordTime());
                }else{
                    logger.error("找不到端口{}的性能数据",netPortFlow.getPortid());
                }
            }
        }
        return flow;
    }

    @Override
    public NetCapPortDetail queryFlowByWeek(long portid, String startTime, String endTime) {
        NetPort port = new NetPort();
        port.setPortid(Long.valueOf(portid + ""));
        NetCapPortDetail portFlowDetail = getPortFlowDetail(null, 0, Arrays.asList(portid + ""), startTime, endTime, null);
//        List<NetPortFlow> portFlowDetail = (List<NetPortFlow>) portFlowDetail1;
//        if(portFlowDetail!=null){
//            return  portFlowDetail.getDetails();
//        }
//        List<NetPortFlow> flow = flowMapper.queryFlowByWeek(portid,startTime,endTime);
        return portFlowDetail;
    }

    @Override
    public List<NetPortFlow> queryFlowBy(int type, long id) {
        List<NetPortFlow> flow = flowMapper.queryFlowBy(type, id);
        return flow;
    }

    @Override
    public Map<String, Object> getUsage(long id) {
        Map<String, Object> usage = flowMapper.getUsage(id);
        if (usage == null) {
            usage = new HashMap<>(3);
            usage.put("CPU", 0);
            usage.put("MEMORY", 0);
            usage.put("TEMPERATURE", 0);
        }
        return usage;
    }

//    @Override
//    public List<NetPortMaxFlow> getMaxFlow(String reportType, int portType, List<String> portids, String startTime, String endTime, String cyc) {
//        List<NetPortMaxFlow> maxFlow = flowMapper.getMaxFlow(reportType, portType, portids, startTime, endTime, cyc);
//        return maxFlow;
//    }

    //    @Override
//    public List<NetPortFlowCap> getPortFlowCap(String reportType, int portType, List<String> portids, String startTime, String endTime, String cyc) {
//        return flowMapper.getFlowCap(reportType, portType, portids, startTime, endTime, cyc);
//    }
    @Override
    public List<NetPortFlow> getBusiFlowDetailMonth(List<String> portids) {
        Calendar instance = Calendar.getInstance();
        String startTime = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd HH:mm:ss");
        instance.add(Calendar.MONTH, -1);
        String endTime = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd HH:mm:ss");
        return null;
//        return getPortFlowDetail("portcap", 1, portids, startTime, endTime, "mi");

    }

    /***
     * 获取物理口流量
     *
     * @param portids
     * @param startTime
     * @param endTime
     * @param cyc
     * @return
     * @TODO 考虑全部查询为5分钟 然后手动计算力度压缩
     */
    public List<NetPortFlow> getPortFlowDetail(String businame, Double boadWidth, List<String> portids, String startTime, String endTime, String cyc) {
        long usetime = System.currentTimeMillis();
        List<NetPortFlow> portFlowDetail = flowMapper.getPortFlowDetail(portids, startTime, endTime, cyc);
        logger.debug("查询耗时" + (System.currentTimeMillis() - usetime));
        usetime = System.currentTimeMillis();
        Map<Long, List<NetPortFlow>> portBeans = new HashMap<>(10);
        //转换为端口列表  用来计算丢失数据
        for (NetPortFlow netPortFlow : portFlowDetail) {
            Long portid = netPortFlow.getPortid();
            List<NetPortFlow> flows = portBeans.get(portid);
            if (flows == null) {
                flows = new ArrayList<>();
                portBeans.put(portid, flows);
            }
            flows.add(netPortFlow);
        }
        //转换为时间Map
        Map<Long, List<NetPortFlow>> tmap = new TreeMap<>();

        Map<Long, NetPortFlow> tBus = new TreeMap<>();
        for (Long aLong : portBeans.keySet()) {
            List<NetPortFlow> flows = DataUtil.checkData(portBeans.get(aLong), startTime, endTime, cyc);
            //用补充后的数据 转换为时间Map 用来合并结果为业务
            for (NetPortFlow flow : flows) {
                long time = flow.getRecordTime().getTime();
                NetPortFlow netPortFlow = tBus.get(time);
                if (netPortFlow == null) {
                    netPortFlow = new NetPortFlow();
                    netPortFlow.setPortname(businame);
                    netPortFlow.setBandwidth(new BigDecimal(boadWidth));
                    netPortFlow.setRecordTime(new Timestamp(time));
                    tBus.put(time, netPortFlow);
                }
                netPortFlow.setInflow(netPortFlow.getInflow() + flow.getInflow());
                netPortFlow.setOutflow(netPortFlow.getOutflow() + flow.getOutflow());
//                netPortFlow.setRecordTime(new Timestamp(time));
//                List<NetPortFlow> tlist = tmap.get(time);
//                if(tlist==null){
//                    tlist = new ArrayList<>();
//                    tmap.put(time, tlist);
//                }
//                tlist.add(flow);
            }
        }
        List<NetPortFlow> busi = new ArrayList<>();
        for (Long aLong : tBus.keySet()) {
            busi.add(tBus.get(aLong));
        }
        logger.debug("计算耗时" + (System.currentTimeMillis() - usetime));
//        for (Long time : tmap.keySet()) {
//            NetPortFlow netPortFlow= new NetPortFlow();
//            for (NetPortFlow portFlow : tmap.get(time)) {
//                netPortFlow.setInflow(netPortFlow.getInflow()+portFlow.getInflow());
//                netPortFlow.setOutflow(netPortFlow.getOutflow() + portFlow.getOutflow());
//                netPortFlow.setRecordTime(new Timestamp(time));
//            }
//            netPortFlow.setPortname(businame);
//            netPortFlow.setBandwidth(new BigDecimal(boadWidth));
//            busi.add(netPortFlow);
//        }
        return busi;
    }

    /***
     * 多线程
     * //            DataUtil.formatterData(portFlowDetail,)
     * //            Date startDate = DataUtil.Formatter(startTime);
     * //            Date endDate = DataUtil.Formatter(endTime);
     * //            List<Long> times = DataUtil.getTimes(startDate, endDate, "mi");
     * //           DataUtil.checkData(startTime, endTime, portFlowDetail, DataUtil.getTimes(startDate,endDate,cyc));
     * //            for (NetCapPort netCapPort : portFlowDetail) {
     * //
     * //            }
     * <p/>
     * //            ArrayBlockingQueue<Runnable> list = new ArrayBlockingQueue<Runnable>(5);
     * //            ExecutorService executorService = new ThreadPoolExecutor(1,3,10, TimeUnit.SECONDS,list);
     * //            executorService.submit(new getFlowData(portids,startTime,endTime,cyc));
     * //计算单个端口数据 并补仓丢失数据
     * //            ExecutorService executorService = Executors.newFixedThreadPool(2);
     * //            ArrayList<Future<List<NetPortFlow>>> al=new ArrayList<>();
     * //            List<String> tport = new ArrayList<>();
     * //           // String[] tport =  new String[4];
     * //            for (int i = 0; i < ports.size(); i++) {
     * //                //tport[i%4]=  ports.get(i);
     * //                tport.add(ports.get(i));
     * //                if((i>0&&i%4==0)||i==ports.size()-1){
     * //                    String[] strings = new String[tport.size()];
     * //                    al.add(executorService.submit(new getFlowData(flowMapper, Arrays.asList((String[]) tport.toArray(strings)), startTime, endTime, cyc)));
     * //                    tport.clear();
     * //                }
     * //            }
     * //            List<NetPortFlow> portFlowDetailttttt= new ArrayList<>();
     * //            for (Future<List<NetPortFlow>> listFuture : al) {
     * //                try {
     * //                    portFlowDetailttttt.addAll(listFuture.get());
     * //                } catch (Exception e) {
     * //                   logger.error("",e);
     * //                }
     * //            }
     * //            executorService.shutdown();
     * //            logger.info("共" + portFlowDetailttttt.size());
     * //            logger.info("多线程耗时"+(System.currentTimeMillis()-time));
     * //List<NetPortFlow> portFlowDetail = getPortFlowDetail(netbusiPort.getBusiportname(), netbusiPort.getBandwidth(), ports, startTime, endTime, cyc);
     * <p/>
     * // Map<String, List<NetCapPort>> listByEach = getListByForId(conn, portarr, startTime, endTime, cyc);
     * //Map<String, List<NetCapPort>> listByEach = getListByEach(conn, portarr, startTime, endTime,cyc);
     * <p/>
     * 多种尝试
     * //                time=System.currentTimeMillis();
     * //                Map<Long,NetCapPort> timeMap= new TreeMap<>();
     * //                for (Map.Entry<String, List<NetCapPort>> stringListEntry : listByEach.entrySet()) {
     * //                    List<NetCapPort> value = stringListEntry.getValue();
     * //                    List<NetCapPort> netCapPorts = DataUtil.checkAllNew(value, stringListEntry.getKey(), times, cyc);
     * //                    for (NetCapPort netCapPort : netCapPorts) {
     * //                        long timemi = netCapPort.getRecordTime().getTime();
     * //                        NetCapPort netCapPort1 = timeMap.get(timemi);
     * //                        if (netCapPort1 == null) {
     * //                            timeMap.put(timemi, netCapPort1);
     * //                        }else{
     * //                            netCapPort1.setInflow(netCapPort1.getInflow() + netCapPort.getInflow());
     * //                            netCapPort1.setOutflow(netCapPort1.getOutflow() + netCapPort.getOutflow());
     * //                        }
     * //                    }
     * ////                    List<NetCapPort> netCapPorts1 = DataUtil.checkData(value);
     * ////                    netCapPorts1 = DataUtil.Zip(netCapPorts1,cyc);
     * ////                    netCapPorts1 =  DataUtil.checkData(stringListEntry.getKey(), times, netCapPorts1);
     * //                    stringListEntry.setValue(netCapPorts);
     * //                }
     * //                for (Map.Entry<Long, NetCapPort> longNetCapPortEntry : timeMap.entrySet()) {
     * //                    portFlowDetail.add(longNetCapPortEntry.getValue());
     * //                }
     *
     * @param reportType
     * @param portType
     * @param portids
     * @param startTime
     * @param endTime
     * @param cyc
     * @return
     */

    @Override
    public NetCapPortDetail getPortFlowDetail(String reportType, int portType, List<String> portids, String startTime, String endTime, String cyc) {
        List<NetPortFlow> portFlowDetail = new ArrayList<>(5000);
        if (portids == null || portids.size() == 0) {
            return null;
        }
        String portid = portids.get(0);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
            if(calendar.get(Calendar.MINUTE)/5!=0||calendar.get(Calendar.SECOND)!=0){
                int ceil = (int) Math.ceil(calendar.get(Calendar.MINUTE) / 5);
                if(calendar.get(Calendar.SECOND)!=0){
                    ceil+=1;
                }
                int v = ceil * 5;
                calendar.set(Calendar.MINUTE,v);
                calendar.set(Calendar.SECOND,0);
                startTime=DateFormatUtils.format(calendar,"yyyy-MM-dd HH:mm:ss");
            }
            calendar.setTime(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
            if(calendar.get(Calendar.MINUTE)/5!=0||calendar.get(Calendar.SECOND)!=0){
                int ceil = (int) Math.ceil(calendar.get(Calendar.MINUTE) / 5);
                if(calendar.get(Calendar.SECOND)!=0){
                    ceil+=1;
                }
                int v = ceil * 5;
                calendar.set(Calendar.MINUTE,v);
                calendar.set(Calendar.SECOND,0);
                endTime=DateFormatUtils.format(calendar,"yyyy-MM-dd HH:mm:ss");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (portType == 1) {
            long time = System.currentTimeMillis();
            NetBusiPort netbusiPort = busPortMapper.getById(portid);
            List<String> portarr = new ArrayList<>();
            for (int i = 0; i < netbusiPort.getPortids().size(); i++) {
                portarr.add(netbusiPort.getPortids().get(i).toString());
            }
            try {
                //原始数据
                List<NetPortFlow> originalFlows = getBusiOriginalFlow(Arrays.asList(portid),startTime,endTime);
//                List<NetPortFlow> originalFlows = getOriginalFlow(portarr, startTime, endTime);
                for (NetPortFlow originalFlow : originalFlows) {
                    originalFlow.setBandwidth(new BigDecimal(netbusiPort.getBandwidth()));
                    originalFlow.setPortname(netbusiPort.getBusiportname());
                    originalFlow.setPortid(netbusiPort.getId());
                }
                NetCapPortDetail cap = getCap(originalFlows, cyc);
                return cap;
            } catch (Exception e) {
                logger.info("", e);
            }
            logger.info("耗时{}", (System.currentTimeMillis() - time));
            return null;
        } else {
            try {
                NetPort netPort = netPortService.getModelById(Long.parseLong(portid));
                List<NetPortFlow> originalFlows = getOriginalFlow(Arrays.asList(portid), startTime, endTime);
                for (NetPortFlow originalFlow : originalFlows) {
                    originalFlow.setBandwidth(netPort.getBandwidth());
                    originalFlow.setPortname(netPort.getPortname());
                }
                NetCapPortDetail cap = getCap(originalFlows, cyc);
                return cap;
            } catch (Exception e) {
                logger.info("", e);
            }
            return null;
        }
    }

    public NetCapPortDetail getCap(List<NetPortFlow> datas, String cyc) {
        if (datas == null || datas.size() == 0) {
            return null;
        }
        NetCapPortDetail capPortDetail = new NetCapPortDetail();
        double inmax = 0d, inmin = 0d, inavg = 0d, outmax = 0d, outmin = 0d, outavg = 0d, inall = 0d, outall = 0d, bandwidth = 0d;
        Calendar c = Calendar.getInstance();
        Map<Long, NetPortFlow> hmap = new TreeMap<>();
        NetPortFlowCap max = new NetPortFlowCap();
        for (NetPortFlow originalFlow : datas) {
            max.setBandwidth(originalFlow.getBandwidth());
            max.setPortname(originalFlow.getPortname());

            double inflow = originalFlow.getInflow();
            double outflow = originalFlow.getOutflow();
            inall += inflow;
            outall += outflow;
            inmax = getMaxOrMix(inflow, inmax, true);
            outmax = getMaxOrMix(outflow, outmax, true);
            inmin = getMaxOrMix(inflow, inmin, false);
            outmin = getMaxOrMix(outflow, outmin, false);
            bandwidth = originalFlow.getBandwidth().doubleValue();
            if (cyc == null || "mi".equals(cyc)) {
                c.setTimeInMillis(originalFlow.getRecordTime().getTime());
            } else {
                c.setTimeInMillis(originalFlow.getRecordTime().getTime());
                if ("hour".equals(cyc)) {
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                } else {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                }
            }
            long timeInMillis = c.getTimeInMillis();
            NetPortFlow countBean = hmap.get(timeInMillis);
            if (countBean == null) {
                originalFlow.getRecordTime().setTime(timeInMillis);
                hmap.put(timeInMillis, originalFlow);
            } else {
                countBean.setInflow(originalFlow.getInflow() + countBean.getInflow());
                countBean.setOutflow(originalFlow.getOutflow() + countBean.getOutflow());
            }
        }

        max.setMaxinflow(inmax);
        max.setMaxoutflow(outmax);
        max.setMininflow(inmin);
        max.setMinoutflow(outmin);
        max.setAvginflow(inall / datas.size());
        max.setAvgoutflow(outall / datas.size());
        //利用率
        max.setMaxinflowUR(inmax / bandwidth);
        max.setMaxoutflowUR(outmax / bandwidth);
        max.setAvginflowUR(max.getAvginflow() / bandwidth);
        max.setAvgoutflowUR(max.getAvgoutflow() / bandwidth);
        capPortDetail.setNetCapPort(max);
        //List<NetCapPort>
        //计算均值
        List<NetPortFlow> results = new ArrayList<>();
        for (Map.Entry<Long, NetPortFlow> longNetPortFlowEntry : hmap.entrySet()) {
            NetPortFlow value = longNetPortFlowEntry.getValue();
            if (cyc == null || "mi".equals(cyc)) {

            } else {
                if ("hour".equals(cyc)) {
                    double avg = value.getInflow() / 12;
                    value.setInflow(avg);
                    avg = value.getOutflow() / 12;
                    value.setOutflow(avg);
                } else {
                    double avg = value.getInflow() / 12 / 24;
                    value.setInflow(avg);
                    avg = value.getOutflow() / 12 / 24;
                    value.setOutflow(avg);
                }
            }
            results.add(value);
        }
        capPortDetail.setDetails(results);
        return capPortDetail;
    }

    public double getMaxOrMix(double inflow, double compflow, boolean getMax) {
        if (getMax) {
            if (inflow > compflow) {
                return inflow;
            }
        } else {
            if (compflow == 0 || inflow < compflow) {
                return inflow;
            }
        }
        return compflow;
    }

    /***
     * 获取原始数据
     *
     * @param portids
     * @param startTime
     * @param endTime
     * @return
     */
    public List<NetPortFlow> getOriginalFlow(List<String> portids, String startTime, String endTime) {

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
        Map<Long, NetPortFlow> countMap = new TreeMap<>();
        try {
            List<NetCapPort> listTTT = null;
            try {
                long exectime = System.currentTimeMillis();
                listTTT = getListTTT(conn, portids, startTime, endTime);
                logger.info("查询耗时{}", System.currentTimeMillis() - exectime);
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                JdbcUtils.closeConnection(conn);
            }
            Map<Long, List<NetCapPort>> tmap = new HashMap<>();
            for (NetCapPort netCapPort : listTTT) {
                List<NetCapPort> netCapPorts = tmap.get(netCapPort.getPortid());
                if (netCapPorts == null) {
                    netCapPorts = new ArrayList<>();
                    tmap.put(netCapPort.getPortid(), netCapPorts);
                }
                netCapPorts.add(netCapPort);
            }
            for (String portid : portids) {
                List<NetCapPort> netCapPorts1 = tmap.get(Long.parseLong(portid));
                if (netCapPorts1 == null) {
                    logger.error("{},全部数据缺失", portid);
                    netCapPorts1 = new ArrayList<>();
                }
                List<NetCapPort> netCapPorts = DataUtil.addLostData(portid.toString(), times, netCapPorts1);
                for (NetCapPort netCapPort : netCapPorts) {
                    long mapkey = netCapPort.getRecordTime().getTime();
                    NetPortFlow netPortFlow = countMap.get(mapkey);
                    if (netPortFlow == null) {
                        netPortFlow = new NetPortFlow();
                        netPortFlow.setRecordTime(netCapPort.getRecordTime());
                        countMap.put(mapkey, netPortFlow);
                    }
                    netPortFlow.setIsUserInsert(netCapPort.isUserInsert());
                    netPortFlow.setInflow(netPortFlow.getInflow() + netCapPort.getInflow());
                    netPortFlow.setOutflow(netPortFlow.getOutflow() + netCapPort.getOutflow());
                }
            }
//            for (Map.Entry<Long, List<NetCapPort>> stringListEntry : tmap.entrySet()) {
//                List<NetCapPort> netCapPorts = DataUtil.addLostData(stringListEntry.getKey().toString(),times,stringListEntry.getValue());
//                for (NetCapPort netCapPort : netCapPorts) {
//                    long mapkey = netCapPort.getRecordTime().getTime();
//                    NetPortFlow netPortFlow = countMap.get(mapkey);
//                    if (netPortFlow == null) {
//                        netPortFlow = new NetPortFlow();
//                        netPortFlow.setRecordTime(netCapPort.getRecordTime());
//                        countMap.put(mapkey, netPortFlow);
//                    }
//                    netPortFlow.setInflow(netPortFlow.getInflow() + netCapPort.getInflow());
//                    netPortFlow.setOutflow(netPortFlow.getOutflow() + netCapPort.getOutflow());
//                }
//            }
            List<NetPortFlow> portFlowDetails = new ArrayList<>(countMap.size());
            for (NetPortFlow netPortFlow : countMap.values()) {
                portFlowDetails.add(netPortFlow);
            }
            return portFlowDetails;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return new ArrayList<>();
    }

    @Override
    public List<NetPortFlow> getOutFlowTopN() {
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        Map<String, String> net_port_curr = redisTemplate.opsForHash().entries("net_port_curr");
        List<NetPortFlow> list = new ArrayList<>();
        NetPortFlow tmp;
        for (Map.Entry<String, String> stringStringEntry : net_port_curr.entrySet()) {
            String s = stringStringEntry.getValue();
            if (s == null) {
                continue;
            }
            PortCapSerial portCapSerial = JSON.parseObject(s, PortCapSerial.class);
            if (portCapSerial != null) {
                tmp = new NetPortFlow();
                tmp.setRecordTime(portCapSerial.getRecordTime());
                tmp.setInflow(portCapSerial.getInputFlux());
                tmp.setOutflow(portCapSerial.getOutputFlux());
                tmp.setPortid(portCapSerial.getPortid());
                tmp.setPortname(portCapSerial.getPortname());
                list.add(tmp);
            }
        }
        Collections.sort(list, new Comparator<NetPortFlow>() {
            @Override
            public int compare(NetPortFlow o1, NetPortFlow o2) {
                if (o1.getOutflow() > o2.getOutflow()) {
                    return 1;
                } else if (o1.getOutflow() < o2.getOutflow()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        if (list.size() < 10) {
            return list;
        } else {
            return list.subList(0, 10);
        }
        //return flowMapper.getOutFlowTopN();
    }

    @Override
    @Async
    public void ZipBusiFlow(String startTime, String endTime) {
        List<NetBusiPort> list = busPortMapper.queryList();
        logger.info("修正数据...");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
            if(calendar.get(Calendar.MINUTE)/5!=0||calendar.get(Calendar.SECOND)!=0){
                int ceil = (int) Math.ceil(calendar.get(Calendar.MINUTE) / 5);
                if(calendar.get(Calendar.SECOND)!=0){
                    ceil+=1;
                }
                int v = ceil * 5;
                calendar.set(Calendar.MINUTE,v);
                calendar.set(Calendar.SECOND,0);
                startTime=DateFormatUtils.format(calendar,"yyyy-MM-dd HH:mm:ss");
            }
            calendar.setTime(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
            if(calendar.get(Calendar.MINUTE)/5!=0||calendar.get(Calendar.SECOND)!=0){
                int ceil = (int) Math.ceil(calendar.get(Calendar.MINUTE) / 5);
                if(calendar.get(Calendar.SECOND)!=0){
                    ceil+=1;
                }
                int v = ceil * 5;
                calendar.set(Calendar.MINUTE,v);
                calendar.set(Calendar.SECOND,0);
                endTime=DateFormatUtils.format(calendar,"yyyy-MM-dd HH:mm:ss");
            }
        } catch (ParseException e) {
           logger.error("",e);
        }
        for (NetBusiPort netBusiPort : list) {
            ZipBusiFlow(netBusiPort.getId(), startTime, endTime);
        }
        logger.info("修正完成...");
    }

    @Override
    public void ZipBusiFlow(long busiid, String startTime, String endTime) {
        try {
            NetBusiPort netbusiPort = busPortMapper.getById(busiid + "");
            List<String> portarr = new ArrayList<>();
            for (int i = 0; i < netbusiPort.getPortids().size(); i++) {
                portarr.add(netbusiPort.getPortids().get(i).toString());
            }
            Connection conn = null;
            try {
                List<Long> times = DataUtil.getTimes(startTime, endTime, null);
//                times.remove(0);
//                times.remove(times.size()-1);
                conn = jdbcTemplate.getDataSource().getConnection();
                //获取该业务口所有数据
                List<NetCapPort> listTTT = getListTTT(conn, portarr, startTime, endTime);
                //把数据按端口 分时间轴
                Map<Long, List<NetCapPort>> tmap = new HashMap<>();
                for (NetCapPort netCapPort : listTTT) {
                    List<NetCapPort> netCapPorts = tmap.get(netCapPort.getPortid());
                    if (netCapPorts == null) {
                        netCapPorts = new ArrayList<>();
                        tmap.put(netCapPort.getPortid(), netCapPorts);
                    }
                    netCapPorts.add(netCapPort);
                }
                JdbcUtils.closeConnection(conn);
                //时间点Map
                Map<Long, NetCapPort> countMap = new TreeMap<>();
                for (Map.Entry<Long, List<NetCapPort>> stringListEntry : tmap.entrySet()) {
                    //补充丢失的数据
                    List<NetCapPort> netCapPorts = DataUtil.addLostData(stringListEntry.getKey().toString(), times, stringListEntry.getValue());
                    //将每个端口数据回合
                    for (NetCapPort netCapPort : netCapPorts) {
                        long mapkey = netCapPort.getRecordTime().getTime();
                        NetCapPort netPortFlow = countMap.get(mapkey);
                        if (netPortFlow == null) {
                            netPortFlow = new NetCapPort();
                            netPortFlow.setRecordTime(netCapPort.getRecordTime());
                            countMap.put(mapkey, netPortFlow);
                        }
                        netPortFlow.setInflow(netPortFlow.getInflow() + netCapPort.getInflow());
                        netPortFlow.setOutflow(netPortFlow.getOutflow() + netCapPort.getOutflow());
                    }
                }
                boolean isAuto = true;
                PreparedStatement ps = null;
                try {
                    conn = jdbcTemplate.getDataSource().getConnection();
                    String deletesql = "delete from net_cap_busi where busiid=? and recordtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and recordtime<to_date(?,'yyyy-mm-dd hh24:mi:ss')";
                    ps = conn.prepareStatement(deletesql);
                    ps.setLong(1, busiid);
                    ps.setString(2, startTime);
                    ps.setString(3, endTime);
                    ps.execute();
                    String sql = "insert into NET_CAP_BUSI (BUSIID ,RECORDTIME ,INPUTFLUX ,OUTPUTFLUX ,PORTNAME,BANDWIDTH) values (?,?,?,?,?,?)";
                    isAuto = conn.getAutoCommit();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                    final int batchSize = 1000;
                    int index = 0;
                    for (NetCapPort netCapPort : countMap.values()) {
                        ps.setLong(1, busiid);
                        ps.setTimestamp(2, netCapPort.getRecordTime());
                        ps.setDouble(3, netCapPort.getInflow());
                        ps.setDouble(4, netCapPort.getOutflow());
                        ps.setString(5, netbusiPort.getBusiportname());
                        ps.setDouble(6, netbusiPort.getBandwidth() == null ? 0 : netbusiPort.getBandwidth());
                        ps.addBatch();
                        index++;
                        if (index % batchSize == 0) {
                            ps.executeBatch();
                            conn.commit();
                            ps.clearBatch();
                        }
                    }
                    ps.executeBatch();
                    conn.commit();
                    ps.clearBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    logger.error("插入{}数据失败，回滚操作", busiid);
                }
                conn.setAutoCommit(isAuto);
                JdbcUtils.closeStatement(ps);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
            }
        } catch (Exception e) {
            logger.error("修正{}数据失败:{}", busiid, e);
        }
    }

    @Override
    public List<NetPortFlowCap> getBusiPortFlowCapWeek(String reportType, int portType, List<String> portids, String startTime, String endTime, String cyc) {
        //每天的原始数据,用来结算性能信息
        List<NetPortFlowCap> originalFlows = new ArrayList<>(1000);
        List<NetPortFlow>  originalFlowList = getBusiOriginalFlow(portids,startTime,endTime);
        Map<String,List<NetPortFlow>> mapDate=new HashMap<>(10);
        List<NetPortFlow> tmp;
        long time = System.currentTimeMillis();
        for (NetPortFlow netPortFlow : originalFlowList) {
            tmp = mapDate.get(netPortFlow.getPortid().toString());
            if(tmp==null){
                tmp=new ArrayList<>(100);
                mapDate.put(netPortFlow.getPortid().toString(),tmp);
            }
            tmp.add(netPortFlow);
        }
        for (String portid : portids) {
            logger.info("获取周报{}", portid);
//
            NetBusiPort netbusiPort = busPortMapper.getById(portid);
//            List<String> portarr = new ArrayList<>();
//            for (Long aLong : netbusiPort.getPortids()) {
//                portarr.add(aLong.toString());
//            }
//            List<NetPortFlow> originalFlowList = getOriginalFlow(portarr, startTime, endTime);

            tmp = mapDate.get(portid);//.get busPortMapper.getCap(Arrays.asList(portid), startTime, endTime);
            if(tmp!=null){
                List<NetPortFlowCap> weekCap = getWeekCap(netbusiPort, tmp, cyc);
                originalFlows.addAll(weekCap);
            }
        }
        logger.info("耗时{}", System.currentTimeMillis() - time);
        return originalFlows;//flowMapper.getBusiPortFlowCapWeek(reportType, portType, portids, startTime, endTime, cyc);
    }

    public List<NetPortFlow> getBusiOriginalFlow(List<String> ids, String s, String e) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        List<NetPortFlow> netPortFlows = new ArrayList<>(1000);
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            StringBuffer sql = new StringBuffer("SELECT s.busiid AS portid, s.RECORDTIME AS RECORDTIME, s.INPUTFLUX inFlow, s.OUTPUTFLUX outFlow,s.portname, s.bandwidth FROM net_Cap_busi s WHERE s.RECORDTIME >= TO_DATE (?, 'yyyy-mm-dd hh24:mi:ss') AND s.RECORDTIME < TO_DATE (?, 'yyyy-mm-dd hh24:mi:ss')");
            if(ids!=null&&ids.size()>0){
                sql.append(" AND s.busiid IN (");
                for (int i = 0; i < ids.size(); i++) {
                    if(i>0){
                        sql.append(",");
                    }
                    sql.append("?");
                }
            }
            sql.append(") ORDER BY s.portname, s.RECORDTIME ");
            preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
            int pindex = 1;
            preparedStatement.setString(pindex++, s);
            preparedStatement.setString(pindex++, e);
            if(ids!=null){
                for (String id : ids) {
                    preparedStatement.setString(pindex++, id);
                }
            }
            preparedStatement.setFetchSize(50000);
            rs = preparedStatement.executeQuery();
            NetPortFlow t;
            Long routid;
            while (rs.next()) {
                t = new NetPortFlow();
                t.setPortid(rs.getLong("portid"));
                t.setPortname(rs.getString("portname"));
                t.setRecordTime(rs.getTimestamp("recordtime"));
                t.setInflow(rs.getDouble("inflow"));
                t.setOutflow(rs.getDouble("outflow"));
                t.setBandwidth(rs.getBigDecimal("bandwidth"));
                netPortFlows.add(t);
            }
        } catch (Exception e1) {
            logger.error("",e);
        }finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(preparedStatement);
            JdbcUtils.closeConnection(conn);
        }
        return netPortFlows;
    }
    private List<NetPortFlowCap> getWeekCap(NetBusiPort netbusiPort, List<NetPortFlow> originalFlowList, String cyc) {
        List<NetPortFlowCap> result = new ArrayList<>();
        NetPortFlowCap netPortFlowCap = null;
        Map<String, List<NetPortFlow>> dayMap = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();
        for (NetPortFlow netPortFlow : originalFlowList) {
            calendar.setTimeInMillis(netPortFlow.getRecordTime().getTime());
            String format = DateFormatUtils.format(netPortFlow.getRecordTime(), "yyy-MM-dd");
            if ("1hour".equals(cyc)) {
                int v = (int) (Math.floor(calendar.get(Calendar.HOUR_OF_DAY) / 1) * 1);
                calendar.set(Calendar.HOUR_OF_DAY, v);
                format = DateFormatUtils.format(calendar, "yyy-MM-dd HH");
            } else if ("2hour".equals(cyc)) {
                int v = (int) (Math.floor(calendar.get(Calendar.HOUR_OF_DAY) / 2) * 2);
                calendar.set(Calendar.HOUR_OF_DAY, v);
                format = DateFormatUtils.format(calendar, "yyy-MM-dd HH");
            } else if ("3hour".equals(cyc)) {
                int v = (int) (Math.floor(calendar.get(Calendar.HOUR_OF_DAY) / 3) * 3);
                calendar.set(Calendar.HOUR_OF_DAY, v);
                format = DateFormatUtils.format(calendar, "yyy-MM-dd HH");
            }else if("all".equals(cyc)){
                format="all";
            }
            List<NetPortFlow> portFlows = dayMap.get(format);
            if (portFlows == null) {
                portFlows = new ArrayList<>();
                dayMap.put(format, portFlows);
            }
            portFlows.add(netPortFlow);
        }
        for (Map.Entry<String, List<NetPortFlow>> stringListEntry : dayMap.entrySet()) {
            List<NetPortFlow> values = stringListEntry.getValue();
            double inmax = 0d, outmax = 0d, inmin = 0d, outmin = 0d, inall = 0d, outall = 0d;
            int size = 0;
            for (NetPortFlow value : values) {
                double inflow = value.getInflow();
                double outflow = value.getOutflow();
                if (inflow > 0 || outflow > 0) {
                    size++;
                }
                if (inflow > 0) {
                    if (inmin <= 0D) {
                        inmin = inflow;

                    }
                    inall += inflow;
                    inmax = getMaxOrMix(inmax, inflow, true);
                    inmin = getMaxOrMix(inmin, inflow, false);
                }
                if (outflow > 0) {
                    if (outmin <= 0) {
                        outmin = outflow;
                    }
                    outall += outflow;
                    outmax = getMaxOrMix(outmax, outflow, true);
                    outmin = getMaxOrMix(outmin, outflow, false);
                }
            }
            netPortFlowCap = new NetPortFlowCap();
            netPortFlowCap.setMininflow(inmin);
            netPortFlowCap.setMaxinflow(inmax);
            netPortFlowCap.setAvginflow(size == 0 ? 0 : inall / size);

            netPortFlowCap.setMinoutflow(outmin);
            netPortFlowCap.setMaxoutflow(outmax);
            netPortFlowCap.setAvgoutflow(size == 0 ? 0 : outall / size);
            netPortFlowCap.setRecordtime(stringListEntry.getKey());
            netPortFlowCap.setPortname(netbusiPort.getBusiportname());
            netPortFlowCap.setBandwidth(new BigDecimal(netbusiPort.getBandwidth()));

            result.add(netPortFlowCap);
        }
        return result;
    }

    //    class Cal {
//        private double inmax;
//        private double inmin;
//        private double outmax;
//        private double outmin;
//        private double inall;
//        private double outall;
//        List<NetPortFlow> flows=new ArrayList<>();
//        public void add(NetPortFlow flow){
//            flows.add(flow);
//            double inflow = flow.getInflow();
//            double outflow = flow.getOutflow();
//            inall+=inflow;
//            outall+=outflow;
//            inmax=getMaxOrMix(inmax, inflow, true);
//            outmax=getMaxOrMix(outmax, outflow, true);
//            inmin=getMaxOrMix(inmin, inflow, false);
//            outmin=getMaxOrMix(outmin, outflow, false);
//        }
//    }
    @Override
    public List<NetPortFlow> getFlow(List<DevicePortQueryBean> querys) {
        return null;//flowMapper.getFlow(querys);
    }

    /*获取当前客户业务端口最近30天流量峰值*/
    @Override
    public List<Map<String, Object>> queryCustomerMaxPortFlowByCustomerId(String customerId) {
        long l = System.currentTimeMillis();
        Date curr = new Date();
        Calendar instance = Calendar.getInstance();
        String endTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        instance.add(Calendar.DAY_OF_MONTH, -1);
        String startTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        List<Object[]> times = DataUtil.getTimes(startTime, endTime);
        List<Map<String, Object>> list = new ArrayList<>(5000);
        for (Object[] time : times) {
            List<Map<String, Object>> tmp = flowMapper.queryCustomerMaxPortFlowByCustomerIdTable("MV_NET_CAP_PORT_" + time[0] + "_" + time[1], customerId);
            if (tmp != null) {
                list.addAll(tmp);
            }
        }
        return list;// flowMapper.queryCustomerMaxPortFlowByCustomerId(customerId);
    }

    @Override
    public List<Map<String, Object>> queryCustomerMaxPortFlowByMap(Map<String, Object> map) {
        Calendar instance = Calendar.getInstance();
        String endTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        instance.add(Calendar.DAY_OF_MONTH, -1);
        String startTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        List<Object[]> times = DataUtil.getTimes(startTime, endTime);
        List<Map<String, Object>> list = new ArrayList<>(5000);
        for (Object[] time : times) {
            map.put("table", "MV_NET_CAP_PORT_" + time[0] + "_" + time[1]);
            List<Map<String, Object>> tmp = flowMapper.queryCustomerMaxPortFlowByMap(map);
            if (tmp != null) {
                list.addAll(tmp);
            }
        }
        return list;
    }

    /*通过客户id 获取客户业务端口名称、ID*/
    @Override
    public List<Map<String, Object>> queryBusinessPortNameByCustomerId(PageBean<T> page, Map<String, Object> map) {
        page.setParams(MapHelper.queryCondition(map));
        return flowMapper.queryBusinessPortNameByCustomerId(page);
    }

    @Override
    public List<NetPortFlowCap> getBusiCap(String id, String startTime, String endTime, String cyc) {
        return null;
    }

    @Override
    public List<NetCapPort> getPortFlow(List<String> strings, String startTime, String endTime) {
        List<NetCapPort> t = flowMapper.test(0, 0);
        return t;
        //return flowMapper.getPortFlow(strings,startTime,endTime);
    }

    @Override
    public List<NetCapPort> test(int a, int b) {
        List<NetCapPort> t = flowMapper.test(a, b);
        return t;
        //return flowMapper.getPortFlow(strings,startTime,endTime);
    }

    public Map<String, List<NetCapPort>> getListByEach(Connection conn, String[] ids, String s, String e, String cyc) throws Exception {
        Map<String, List<NetCapPort>> results = new HashMap<>();
        List<Object[]> timeList = DataUtil.getTimes(s, e);
        List<Long> times = DataUtil.getTimes(s, e, cyc);
        for (Object[] objects : timeList) {
            String sqlstr = "SELECT id,portid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_" + objects[0] + "_" + objects[1] + " where  portid =?  and RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss') order by recordtime";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlstr.toString());
            preparedStatement.setFetchSize(50000);
            for (String id : ids) {
                int pindex = 1;
                preparedStatement.setString(pindex++, id);
                preparedStatement.setString(pindex++, objects[2].toString());
                preparedStatement.setString(pindex++, objects[3].toString());
                ResultSet rs = preparedStatement.executeQuery();
                List<NetCapPort> portMap = results.get(id);
                if (portMap == null) {
                    portMap = new ArrayList<>();
                }
                NetCapPort t;
                while (rs.next()) {
                    t = new NetCapPort();
                    t.setId(rs.getString("id"));
                    t.setPortid(rs.getLong("portid"));
                    t.setRecordTime(rs.getTimestamp("recordtime"));
                    t.setInflow(rs.getDouble("inflow"));
                    t.setOutflow(rs.getDouble("outflow"));
                    portMap.add(t);
                }
                results.put(id, portMap);
            }
            JdbcUtils.closeStatement(preparedStatement);
            preparedStatement = null;
        }
        for (Map.Entry<String, List<NetCapPort>> stringListEntry : results.entrySet()) {
            List<NetCapPort> netCapPorts = DataUtil.checkAllNew(stringListEntry.getValue(), stringListEntry.getKey(), times, cyc);
            netCapPorts = DataUtil.Zip(netCapPorts, cyc);
            stringListEntry.setValue(netCapPorts);
        }
//        for (String id : ids) {
//            results.put(id,new ArrayList<NetCapPort>());
//        }
//        for (NetCapPort netCapPort : listTTT) {
//            results.get(netCapPort.getPortid()+"").add(netCapPort);
//        }
        return results;
    }

    public List<NetCapPort> getData(int reportType, String portid, String s, String e) {
        List<NetCapPort> result = new ArrayList<>();
        if (reportType == 1) {
            NetBusiPort netbusiPort = busPortMapper.getById(portid);
            String[] portarr = new String[netbusiPort.getPortids().size()];
            for (int i = 0; i < netbusiPort.getPortids().size(); i++) {
                portarr[i] = netbusiPort.getPortids().get(i).toString();
            }
        } else {

        }
        return result;
    }

    @Override
    public Map<String, List<NetCapPort>> getListByForId(Connection conn, String[] ids, String s, String e, String cyc) throws Exception {
        Map<String, List<NetCapPort>> results = new HashMap<>();
        List<Long> times = DataUtil.getTimes(s, e, cyc);
//        List<NetCapPort> netCapPorts = new ArrayList<>();
        List<Object[]> times1 = DataUtil.getTimes(s, e);

        for (String id : ids) {
            long exectime = System.currentTimeMillis();
            StringBuffer sql = new StringBuffer();
            //每个端口单独查询
            for (Object[] objects : times1) {
                if (sql.length() > 0) {
                    sql.append(" UNION all ");
                }
                sql.append("SELECT portid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_").append(objects[0]).append("_").append(objects[1]).append(" where  portid =?  and RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            }
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
            int pindex = 1;
            for (Object[] objects : times1) {
                preparedStatement.setString(pindex++, id);
                preparedStatement.setString(pindex++, objects[2].toString());
                preparedStatement.setString(pindex++, objects[3].toString());
            }
            preparedStatement.setFetchSize(50000);
            ResultSet rs = preparedStatement.executeQuery();
            NetCapPort t;
            List<NetCapPort> tmp = new ArrayList<>();
            while (rs.next()) {
                t = new NetCapPort();
                t.setPortid(rs.getLong("portid"));
                t.setRecordTime(rs.getTimestamp("recordtime"));
                t.setInflow(rs.getDouble("inflow"));
                t.setOutflow(rs.getDouble("outflow"));
                tmp.add(t);
            }
            logger.info("查询耗时{}", System.currentTimeMillis() - exectime);
            tmp = DataUtil.checkAllNew(tmp, id, times, cyc);
            tmp = DataUtil.Zip(tmp, cyc);
            results.put(id, tmp);
        }
        return results;
    }

    public List<NetCapPort> getListTTT(Connection conn, List<String> ids, String s, String e) {
        List<NetCapPort> netCapPorts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            List<Object[]> times1 = DataUtil.getTimes(s, e);
            List<String> idsstr = new ArrayList<>();
            if (ids.size() > 1000) {
                List<String> strings = new ArrayList<>(1000);
                for (int i = 0; i < ids.size(); i++) {
                    strings.add(ids.get(i));
                    if ((i + 1) % 1000 == 0) {
                        String s2 = StringUtil.list2String(ids, ",");
                        idsstr.add(s2);
                        strings.clear();
                    }
                }
                String s2 = StringUtil.list2String(ids, ",");
                idsstr.add(s2);
                strings.clear();
            } else {
                String s2 = StringUtil.list2String(ids, ",");
                idsstr.add(s2);
            }

            StringBuffer sql = new StringBuffer();
            for (Object[] objects : times1) {
                for (String idss : idsstr) {
                    if (sql.length() > 0) {
                        sql.append(" UNION all ");
                    }
                    sql.append("SELECT id, portid,routid,recordtime,inputflux as inflow, outputflux as outflow FROM net_cap_port_").append(objects[0]).append("_").append(objects[1]).append(" where  portid in(").append(idss).append(")  and RECORDTIME >= to_date( ?,'yyyy-mm-dd hh24:mi:ss') and RECORDTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')");
                }
            }
            preparedStatement = conn.prepareStatement("SELECT * from (" + sql.toString() + ") order by portid,recordtime");
            int pindex = 1;
            for (Object[] objects : times1) {
                for (int i = 0; i < idsstr.size(); i++) {
//                    preparedStatement.setString(pindex++, ids.get(i));
                    preparedStatement.setString(pindex++, objects[2].toString());
                    preparedStatement.setString(pindex++, objects[3].toString());
                }
            }
            preparedStatement.setFetchSize(50000);
            try {
                rs = preparedStatement.executeQuery();
                NetCapPort t;
                Long routid;
                while (rs.next()) {
                    t = new NetCapPort();
                    t.setId(rs.getString("id"));
                    t.setPortid(rs.getLong("portid"));
                    t.setRecordTime(rs.getTimestamp("recordtime"));
                    routid = rs.getLong("routid");
                    if (routid != null && routid > 0) {
                        t.setIsUserInsert(true);
                    }
                    t.setInflow(rs.getDouble("inflow"));
                    t.setOutflow(rs.getDouble("outflow"));
                    netCapPorts.add(t);
                }
            } catch (SQLException e1) {
                logger.error("", e);
            }
        } catch (SQLException e1) {
            logger.error("", e1);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(preparedStatement);
        }
        return netCapPorts;
    }


    class Check implements Callable<List<NetCapPort>> {
        //        private String s;
//        private String e;
        private List<NetCapPort> ps;
        private List<Long> times;

        public Check(String s, String e, List<NetCapPort> ps, List<Long> time) {
//            this.s = s;
//            this.e = e;
            this.ps = ps;
            this.times = time;
        }

        @Override
        public List<NetCapPort> call() throws Exception {
            Map<Long, NetCapPort> timesMap = new HashMap<>();
            Calendar c = Calendar.getInstance();
            for (int i = 0; i < ps.size(); i++) {
                NetCapPort netCapPort = ps.get(i);
                Timestamp recordTime = netCapPort.getRecordTime();
                long time = recordTime.getTime();
                c.setTimeInMillis(time);
                double v = Math.floor(c.get(Calendar.MINUTE) / 5) * 5;
                c.set(Calendar.MINUTE, (int) v);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                recordTime.setTime(c.getTimeInMillis());

                NetCapPort timeBean = timesMap.get(c.getTimeInMillis());
                if (timeBean == null) {
                    timesMap.put(c.getTimeInMillis(), netCapPort);
                } else {
                    if (recordTime.before(timeBean.getRecordTime())) {
                        timesMap.put(c.getTimeInMillis(), timeBean);
                    }
                }
            }
            List<NetCapPort> lists = new ArrayList<>();
            NetCapPort prev = null;//前一个
            BeanCopier copy = BeanCopier.create(NetCapPort.class, NetCapPort.class, false);
            for (int i = 0; i < times.size(); i++) {
                Long time = times.get(i);
                NetCapPort netPortFlow = timesMap.get(time);
                if (netPortFlow == null) {
                    try {
                        netPortFlow = new NetCapPort();
                        copy.copy(prev, netPortFlow, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    NetCapPort next = null;
                    if (i < times.size() - 1) {
                        Long nexttime = times.get(i + 1);
                        next = timesMap.get(nexttime);
                    }
                    netPortFlow.setInflow(DataUtil.addAvg(prev.getInflow(), next == null ? null : next.getInflow()));
                    netPortFlow.setOutflow(DataUtil.addAvg(prev.getOutflow(), next == null ? null : next.getOutflow()));
                    netPortFlow.setRecordTime(new Timestamp(time));
                    if (logger.isDebugEnabled()) {
                        logger.debug("【" + netPortFlow.getId() + "(" + netPortFlow.getPortid() + ")】缺失数据，补仓" + DateFormatUtils.format(netPortFlow.getRecordTime(), "yyyy-MM-dd HH:mm:ss"), netPortFlow);
                    }
                }
                lists.add(netPortFlow);
                prev = netPortFlow;
            }
            return lists;
        }
    }

}
