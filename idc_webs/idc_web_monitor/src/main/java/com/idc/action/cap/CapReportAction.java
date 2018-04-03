package com.idc.action.cap;

import com.google.common.base.Function;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.idc.model.*;
import com.idc.service.BusPortService;
import com.idc.service.NetPortFlowService;
import com.idc.service.NetPortService;
import com.idc.utils.FlowUtil;
import com.idc.vo.NetCapPortDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.jeecgframework.poi.cache.manager.FileLoadeImpl;
import org.jeecgframework.poi.cache.manager.POICacheManager;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.export.template.ReportUtil;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import utils.PropertyUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by mylove on 2017/7/20.
 */
@Controller
@RequestMapping("/capreport")
public class CapReportAction {
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private BusPortService busPortService;
    @Autowired
    private NetPortFlowService netPortFlowService;
    private static LoadingCache<String, Map> loadingCache = DataCache.getSingleton().getCache();

    static {
//        loadingCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(10)
//                .build(new CacheLoader<String, Map>() {
//                    @Override
//                    public HashMap load(String name) throws Exception {
//                       return null;
//                    }
//                });
//        //设置默认实现
    }

    @RequestMapping("/busi/cap/{id}")
    public String busiPage(@PathVariable String id, ModelMap map) {
        NetBusiPort netBusiPort = busPortService.queryById(id);
        map.put("busi", netBusiPort);
        return "portcap/busicap";
    }

    @RequestMapping("/{mode}/{type}")
    public String busiPage(@PathVariable String mode, @PathVariable String type) {
        if ("portcapweek".equals(type)) {
            return "portcap/capweek";
        }
        if ("portcapweekCustomer".equals(type)) {
            return "portcap/capweekcustomer";
        }
//        if("busi".equals(mode)){
//            return "portcap/busicap";
//        }
        return "portcap/cap";
    }

    @RequestMapping("/")
    public String index() {
        return "portcap/cap";
    }

    @RequestMapping("/flowincap")
    public String PortFlowInCap() {
        return "portcap/flowincap";
    }

    @RequestMapping("/flowoutcap")
    public String PortFlowOutCap() {
        return "portcap/flowoutcap";
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<Map<String, Object>> tree(String id) {
        return null;
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String type, String q) {
        EasyUIData easyUIData = new EasyUIData();
        if ("busiport".equals(type)) {
            List<NetBusiPortFlow> maps = busPortService.queryListPage(q, null);
            easyUIData.setRows(maps);
        } else {
            NetPort netPort = new NetPort();
            if (q != null && StringUtils.isNumeric(q)) {
                netPort.setDeviceid(Long.parseLong(q));
            }
            netPortService.queryListPageMap(page, netPort);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
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

    @RequestMapping("/busicap")
    @ResponseBody
    public Map<String, Object> getBusiCap(String id, String startTime, String endTime, String cyc) {
        StringBuffer reportidbuf = new StringBuffer(id);
        reportidbuf
                .append("_").append(startTime)
                .append("_").append(endTime).append("_").append(cyc);
        if (startTime.length() == 10) {
            startTime += " 00:00:00";
            endTime += " 23:59:59";
        }
        //List<NetPortFlowCap> maxFlows = netPortFlowService.getPortFlowCap(null, 1, Arrays.asList(id), startTime, endTime, cyc);
        long l = System.currentTimeMillis();
//        netPortFlowService.ZipBusiFlow(112,"2017-11-15 00:00:00","2017-12-16 00:00:00");
        NetCapPortDetail portFlowDetail = netPortFlowService.getPortFlowDetail(null, 1, Arrays.asList(id), startTime, endTime, cyc);

        Map<String, Object> map = new HashMap<>();
        map.put("guid", reportidbuf.toString());

        List<String> times = new ArrayList<>();
        List<Double> inflows = new ArrayList<>();
        List<Double> outflows = new ArrayList<>();
        List<Map<String, Object>> details = new ArrayList<>();
        System.out.println(System.currentTimeMillis() - l);
        Map<String, Object> detail;
        if (portFlowDetail != null) {
            NetPortFlowCap max = portFlowDetail.getNetCapPort();

            for (NetPortFlow netPortFlow : portFlowDetail.getDetails()) {
//            max.setBandwidth(netPortFlow.getBandwidth());
//            double inflow = netPortFlow.getInflow();
//            double outflow = netPortFlow.getOutflow();
                detail = new HashMap<>();
                String recordTimeStr =DateFormatUtils.format(netPortFlow.getRecordTime(), "yy/MM/dd HH:mm");
                detail.put("portname", netPortFlow.getPortname());
                detail.put("recordTime", recordTimeStr);
                detail.put("recordTimestr", recordTimeStr);
                detail.put("inflow", new BigDecimal(netPortFlow.getInflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                detail.put("outflow", new BigDecimal(netPortFlow.getOutflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//            if("hour".equals(cyc)){
//                detail.put("inflowUR",FlowUtil.bandWidthUsage(netPortFlow.getInflowMbps()/12,netPortFlow.getBandwidth()));
//                detail.put("outflowUR",FlowUtil.bandWidthUsage(netPortFlow.getOutflowMbps()/12,netPortFlow.getBandwidth()));
//            }else if("day".equals(cyc)){
//                detail.put("inflowUR",FlowUtil.bandWidthUsage(netPortFlow.getInflowMbps()/12/24,netPortFlow.getBandwidth()));
//                detail.put("outflowUR",FlowUtil.bandWidthUsage(netPortFlow.getOutflowMbps()/12/24,netPortFlow.getBandwidth()));
//            }else{
                detail.put("inflowUR", FlowUtil.bandWidthUsage(netPortFlow.getInflowMbps(), netPortFlow.getBandwidth()));
                detail.put("outflowUR", FlowUtil.bandWidthUsage(netPortFlow.getOutflowMbps(), netPortFlow.getBandwidth()));
//            }
                details.add(detail);
                times.add(recordTimeStr);
                inflows.add(new BigDecimal(netPortFlow.getInflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                outflows.add(new BigDecimal(netPortFlow.getOutflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            map.put("max", max);
        }
        map.put("inflows", inflows);
        map.put("outflows", outflows);
        map.put("flows", details);
        map.put("times", times);
        System.out.println(System.currentTimeMillis() - l);
        loadingCache.put(reportidbuf.toString(), map);
        return map;
    }

    /***
     * @param reportType portinmax portoutmax portcap
     * @param portType   0 物理 1 业务端口
     * @param ports
     * @param startTime
     * @param endTime
     * @param cyc
     * @param map
     * @return
     */
    @RequestMapping("/report")
    public String report(final String reportType, int portType, String ports, String startTime, String endTime, String cyc, ModelMap map) {
        StringBuffer reportidbuf = new StringBuffer(reportType);
        reportidbuf.append("_").append(portType)
                .append("_").append(ports)
                .append("_").append(startTime)
                .append("_").append(endTime).append("_").append(cyc);
        map.put("reportType", reportType);
        map.put("portType", portType);
        map.put("cyc", cyc);
        map.put("ports", ports);
        if (startTime.length() == 10) {
            startTime += " 00:00:00";
            endTime += " 23:59:59";
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        String uuid = reportidbuf.toString().replace("\r", "").replace("\n", "");
        map.put("uuid", uuid);
        loadingCache.put(uuid, map);
        if ("portcap".equals(reportType)) {
            List<NetPortFlow> flows = new ArrayList<>();
            //List<NetPortFlowCap> maxFlows = netPortFlowService.getPortFlowCap(reportType, portType, Arrays.asList(ports.split(",")), startTime, endTime, cyc);
            NetCapPortDetail portFlowDetail = netPortFlowService.getPortFlowDetail(reportType, portType, Arrays.asList(ports.split(",")), startTime, endTime, cyc);
//            List<NetPortFlow> portFlowDetail = (List<NetPortFlow>) portFlowDetail1;
            NetPortFlowCap max = null;
            List<String> times = new ArrayList<>();
            List<Double> inflows = new ArrayList<>();
            List<Double> outflows = new ArrayList<>();
            if (portFlowDetail != null) {
                max = portFlowDetail.getNetCapPort();
                flows = portFlowDetail.getDetails();

//            NetPortFlowCap max = new NetPortFlowCap();
//            double inall = 0.0D;
//            double outall = 0.0D;
//            double inmin=0.0D;
//            double outmin=0.0D;
//            double inmax=0.0D;
//            double outmax=0.0D;


                for (NetPortFlow netPortFlow : flows) {
                    max.setBandwidth(netPortFlow.getBandwidth());
                    double inflow = netPortFlow.getInflow();
                    double outflow = netPortFlow.getOutflow();
//                inall += inflow;
//                outall+= outflow;
//                //if 当前值大于前一个值 最大为当前
//                inmax = getMaxOrMix(inflow,inmax,true);
//                outmax = getMaxOrMix(outflow,outmax,true);
//                inmin = getMaxOrMix(inflow,inmin,false);
//                outmin = getMaxOrMix(outflow,outmin,false);
                    times.add(DateFormatUtils.format(netPortFlow.getRecordTime().getTime(), DateFormatUtils.ISO_DATE_FORMAT.getPattern() + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern()));
                    inflows.add(new BigDecimal(netPortFlow.getInflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                    outflows.add(new BigDecimal(netPortFlow.getOutflowMbps()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
//            if(portFlowDetail.size()>0){
//                max.setAvginflow(inall/portFlowDetail.size());
//                max.setAvgoutflow(outall / portFlowDetail.size());
//            }
//            max.setMaxinflow(inmax);
//            max.setMaxoutflow(outmax);
//            max.setMininflow(inmin);
//            max.setMinoutflow(outmin);
            map.put("data", max);
            map.put("inflows", inflows);
            map.put("outflows", outflows);
            map.put("flows", flows);
            map.put("times", times);
            return "portcap/flowcap";
        } else if ("busicapweek".equals(reportType)) {
            List<NetPortFlowCap> maxFlows = netPortFlowService.getBusiPortFlowCapWeek(reportType, portType, Arrays.asList(ports.split(",")), startTime, endTime, cyc);
            Set<String> times = new LinkedHashSet<>();
            for (NetPortFlowCap maxFlow : maxFlows) {
                String recordtime = maxFlow.getRecordtime();
                if (!times.contains(recordtime)) {
                    times.add(recordtime);
                }
            }
            Map<String, Map<String, NetPortFlowCap>> data = getData(maxFlows);
            map.put("times", times);
            map.put("rows", data);
            map.put("flows", maxFlows);
            return "portcap/flowcapweek";
        } else {
            NetCapPortDetail portFlowDetail = netPortFlowService.getPortFlowDetail(reportType, portType, Arrays.asList(ports.split(",")), startTime, endTime, cyc);
//            List<NetPortFlow> lists = (List<NetPortFlow>) portFlowDetail;
            if (portFlowDetail != null) {
                map.put("data", portFlowDetail.getDetails());
            }

            return "portcap/flowmax";
        }

    }

    @RequestMapping("/reportCustomer")
    public String reportCustomer(final String reportType, int portType, String ports, String startTime, String endTime, String cyc, ModelMap map) {
        StringBuffer reportidbuf = new StringBuffer(reportType);
        reportidbuf.append("_").append(portType)
                .append("_").append(ports)
                .append("_").append(startTime)
                .append("_").append(endTime).append("_").append(cyc);
        map.put("reportType", reportType);
        map.put("portType", portType);
        map.put("cyc", cyc);
        map.put("ports", ports);
        if (startTime.length() == 10) {
            startTime += " 00:00:00";
            endTime += " 23:59:59";
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        String uuid = reportidbuf.toString().replace("\r", "").replace("\n", "");
        map.put("uuid", uuid);
        loadingCache.put(uuid, map);
        if ("busicapweek".equals(reportType)) {
            List<NetPortFlowCap> maxFlows = netPortFlowService.getBusiPortFlowCapWeek(reportType, portType, Arrays.asList(ports.split(",")), startTime, endTime, cyc);
            Set<String> times = new LinkedHashSet<>();
            for (NetPortFlowCap maxFlow : maxFlows) {
                String recordtime = maxFlow.getRecordtime();
                if (!times.contains(recordtime)) {
                    times.add(recordtime);
                }
            }
            Map<String, Map<String, NetPortFlowCap>> data = getData(maxFlows);
            map.put("times", times);
            map.put("rows", data);
            map.put("flows", maxFlows);
            return "portcap/flowcapweekcustomer";
        } else {
            return "error";
        }
    }

    private static void getLongTime(List<Long> longs, Calendar calendar, Date end) {
        if (calendar.getTime().before(end)) {
            longs.add(calendar.getTime().getTime());
            calendar.add(Calendar.MINUTE, 5);
            getLongTime(longs, calendar, end);
        }
    }

    private Map<String, Map<String, NetPortFlowCap>> getData(List<NetPortFlowCap> maxFlows) {
        Map<String, Map<String, NetPortFlowCap>> map = new LinkedHashMap<>();
        for (NetPortFlowCap maxFlow : maxFlows) {
            Map<String, NetPortFlowCap> flowDataBeanMap = map.get(maxFlow.getRecordtime());
            if (flowDataBeanMap == null) {
                flowDataBeanMap = new LinkedHashMap<>();
            }
            NetPortFlowCap flowDataBean = flowDataBeanMap.get(maxFlow.getPortname());
            if (flowDataBean == null) {
                flowDataBeanMap.put(maxFlow.getPortname(), maxFlow);
            }
            map.put(maxFlow.getRecordtime(), flowDataBeanMap);
        }
        return map;
    }

    @RequestMapping("/downreport")
    public void downPortCap(String reportid, String img, HttpServletResponse response) {
        try {
            Map datamap = loadingCache.get(reportid);
            if (datamap == null) {
                response.getWriter().write("<script>alert('没有找到报表数据')</script>");
            } else {
                TemplateExportParams params = null;
                Map<String, Object> data = new HashMap<>();
                int flowtype = 0;
                final String reportType = (String) datamap.get("reportType");
                if (reportType.contains("out")) {
                    flowtype = 1;
                }
                if (reportType.contains("max")) {
                    List<NetPortFlow> list = (List<NetPortFlow>) datamap.get("data");
                    params = new TemplateExportParams(
                            "/com/idc/action/cap/" + reportType + ".xls", true);
                    ArrayList<NetPortFlow> clones = (ArrayList<NetPortFlow>) ((ArrayList) list).clone();
                    Collections.sort(clones, new Comparator<NetPortFlow>() {
                        @Override
                        public int compare(NetPortFlow o1, NetPortFlow o2) {
                            if (reportType.contains("in")) {
                                if (o1.getInflow() == o2.getInflow()) {
                                    return 0;
                                }
                                return o1.getInflow() > o2.getInflow() ? -1 : 1;
                            } else {
                                if (o1.getInflow() == o2.getInflow()) {
                                    return 0;
                                }
                                return o1.getOutflow() > o2.getOutflow() ? -1 : 1;
                            }
                        }
                    });
                    JSONArray jsonArray = new JSONArray();
                    JSONObject json = null;
                    DecimalFormat df = new DecimalFormat("######0.0000");
                    for (NetPortFlow netPortFlow : list) {
                        json = new JSONObject();
                        String format = DateFormatUtils.format(new Date(netPortFlow.getRecordTime().getTime()), "yy/MM/dd HH:mm:ss");
                        json.put("recordTimestr", format);
                        double v = flowtype == 0 ? (netPortFlow.getInflowMbps()) : (netPortFlow.getOutflowMbps());
                        json.put("flows", df.format(v));
                        json.put("flowUR", df.format(FlowUtil.bandWidthUsage(v, netPortFlow.getBandwidth().doubleValue())));
                        jsonArray.add(json);
                    }
                    NetPortFlow Max = clones.get(0);
                    NetPortFlow Min = clones.get(clones.size() - 1);
                    FlowMaxBean flowMaxBean = new FlowMaxBean(Max, reportType);
                    FlowMaxBean flowMinBean = new FlowMaxBean(Min, reportType);
                    data.put("Max", flowMaxBean);
                    data.put("Min", flowMinBean);
                    data.put("flowtype", flowtype);
                    data.put("list", jsonArray);
                } else {
                    if (reportType.contains("week")) {//周报
                       if(reportid.endsWith("_all")){
                           params = new TemplateExportParams(
                                   "/com/idc/action/cap/busicapweecustomer.xls", true);
                           Map<String, Map<String, NetPortFlowCap>> rows = (Map<String, Map<String, NetPortFlowCap>>) datamap.get("rows");
                           POICacheManager.setFileLoder(new MyFileLoad());

                           Map<String, Object> map = new HashMap<String, Object>();
                           String[] split = reportid.split("_");
                           map.put("time", split[3]+"-"+split[4]);
                           List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
                           Map<String, Object> lm;
                           for (Map.Entry<String, Map<String, NetPortFlowCap>> stringMapEntry : rows.entrySet()) {
                               for (Map.Entry<String, NetPortFlowCap> stringNetPortFlowCapEntry : stringMapEntry.getValue().entrySet()) {
                                   NetPortFlowCap value = stringNetPortFlowCapEntry.getValue();
                                   lm = new HashMap<String, Object>();
                                   lm.put("maxoutflowMbps",value.getMaxoutflowMbps()/1024);
                                   lm.put("maxoutflowUR",value.getMaxoutflowUR());
                                   lm.put("maxinflowMbps",value.getMaxinflowMbps()/1024);
                                   lm.put("maxinflowUR",value.getMaxinflowUR());
                                   lm.put("portname",value.getPortname());
                                   listMap.add(lm);
                               }
                           }
                           map.put("list", listMap);
                           Workbook workbook = ExcelExportUtil.exportExcel(params, map);
                           response.setContentType("application/octet- stream;charset=UTF-8");
                           response.setHeader("Connection", "close");
                           response.setHeader("Content-Disposition", "attachment;filename="
                                   .concat(String.valueOf(URLEncoder.encode("节假日报表.xls", "UTF-8"))));
                           ServletOutputStream outputStream = response.getOutputStream();
                           workbook.write(outputStream);
                           outputStream.flush();
                           outputStream.close();
                       }else {
                           params = new TemplateExportParams(
                                   "/com/idc/action/cap/busicapweek26.xls", true);
                           Map<String, Map<String, NetPortFlowCap>> rows = (Map<String, Map<String, NetPortFlowCap>>) datamap.get("rows");
                           POICacheManager.setFileLoder(new MyFileLoad());

                           Workbook workbook = null;
                           Map<String, Object> map = new HashMap<String, Object>();
                           //sheetMap
                           Map<Integer, Map<String, Object>> sheetMap = new HashMap<Integer, Map<String, Object>>();
                           int index = 0;
                           Map<String, NetPortFlowCap> all = new HashMap<>();

                           //没行一个sheet  代表一天
                           final int dayLength = rows.size();
                           for (String timestr : rows.keySet()) {
                               map = new HashMap<String, Object>();
                               //代表这天的port
                               Map<String, NetPortFlowCap> stringNetPortFlowCapMap = rows.get(timestr);
                               ImmutableList<NetPortFlowCap> objects = FluentIterable.from(stringNetPortFlowCapMap.entrySet()).transform(
                                       new Function<Map.Entry<String, NetPortFlowCap>, NetPortFlowCap>() {
                                           @Override
                                           public NetPortFlowCap apply(Map.Entry<String, NetPortFlowCap> stringNetPortFlowCapEntry) {
                                               return stringNetPortFlowCapEntry.getValue();
                                           }
                                       }
                               ).toList();
                               for (Map.Entry<String, NetPortFlowCap> stringNetPortFlowCapEntry : stringNetPortFlowCapMap.entrySet()) {
                                   String portname = stringNetPortFlowCapEntry.getKey();
                                   NetPortFlowCap netPortFlowCap = all.get(portname);
                                   NetPortFlowCap curr = stringNetPortFlowCapEntry.getValue();

                                   if (netPortFlowCap == null) {
                                       netPortFlowCap = new NetPortFlowCap();
                                       PropertyUtils.copyProperties(netPortFlowCap, curr);
                                   } else {
                                       netPortFlowCap.setMaxinflow(getMaxOrMix(netPortFlowCap.getMaxinflow(), curr.getMaxinflow(), true));
                                       netPortFlowCap.setMaxoutflow(getMaxOrMix(netPortFlowCap.getMaxoutflow(), curr.getMaxoutflow(), true));
                                       netPortFlowCap.setMininflow(getMaxOrMix(netPortFlowCap.getMininflow(), curr.getMininflow(), false));
                                       netPortFlowCap.setMinoutflow(getMaxOrMix(netPortFlowCap.getMinoutflow(), curr.getMinoutflow(), false));
                                       netPortFlowCap.setAvginflow(netPortFlowCap.getAvginflow() + curr.getAvginflow());
                                       netPortFlowCap.setAvgoutflow(netPortFlowCap.getAvgoutflow() + curr.getAvgoutflow());
                                   }
                                   all.put(portname, netPortFlowCap);
                               }
                               map.put("list", objects);
                               sheetMap.put(index++, map);
                           }
                           ImmutableList<NetPortFlowCap> alllist = FluentIterable.from(all.entrySet()).transform(
                                   new Function<Map.Entry<String, NetPortFlowCap>, NetPortFlowCap>() {
                                       @Override
                                       public NetPortFlowCap apply(Map.Entry<String, NetPortFlowCap> stringNetPortFlowCapEntry) {
                                           NetPortFlowCap value = stringNetPortFlowCapEntry.getValue();
                                           value.setAvginflow(value.getAvginflow() / dayLength);
                                           value.setAvgoutflow(value.getAvgoutflow() / dayLength);
                                           return value;
                                       }
                                   }
                           ).toList();
                           map = new HashMap<>();
                           map.put("list", alllist);
                           sheetMap.put(index++, map);
                           workbook = new ReportUtil().createExcleByTemplateManySheet(params, sheetMap);
                           index = 0;
                           for (String timestr : rows.keySet()) {
                               workbook.setSheetName(index++, timestr);
                           }
                           CellStyle cellStyle = workbook.createCellStyle();
                           cellStyle.setFillBackgroundColor((short) 9);
                           for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                               Sheet sheetAt = workbook.getSheetAt(i);
                               int lastRowNum = sheetAt.getLastRowNum();
                               for (int i1 = 4; i1 <= lastRowNum; i1++) {
                                   if ((i1 % 3) == 2) {
                                       Iterator<Cell> cellIterator = sheetAt.getRow(i1).cellIterator();
                                       while (cellIterator.hasNext()) {
                                           Cell next = cellIterator.next();
                                           next.setCellStyle(cellStyle);
                                       }
                                   }

                               }
                           }
                           workbook.setSheetName(index++, "总计");
                           response.setContentType("application/octet- stream;charset=UTF-8");
                           response.setHeader("Connection", "close");
                           response.setHeader("Content-Disposition", "attachment;filename="
                                   .concat(String.valueOf(URLEncoder.encode("端口性能周报.xls", "UTF-8"))));
                           ServletOutputStream outputStream = response.getOutputStream();
                           workbook.write(outputStream);
                           outputStream.flush();
                           outputStream.close();
                       }

                        return;
                    }
                }
                if (params != null) {
                    export(img, response, params, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FlowMaxBean {
        private double flow;
        private double flowUR;
        private String recordTimestr;

        public double getFlow() {
            return flow;
        }

        public String getRecordTimestr() {
            return recordTimestr;
        }

        public double getFlowUR() {
            return flowUR;
        }

        public FlowMaxBean(NetPortFlow netPortFlow, String reportType) {
            if (reportType.contains("in")) {
                this.flow = netPortFlow.getInflowMbps();
                this.flowUR = FlowUtil.bandWidthUsage(netPortFlow.getInflowMbps(), netPortFlow.getBandwidth().doubleValue());
            } else {
                this.flow = netPortFlow.getOutflowMbps();
                this.flowUR = FlowUtil.bandWidthUsage(netPortFlow.getOutflowMbps(), netPortFlow.getBandwidth().doubleValue());
//                this.flowUR=netPortFlow.getOutflowMbps()/netPortFlow.getBandwidth().doubleValue()*100;
            }
            this.recordTimestr = DateFormatUtils.format(new Date(netPortFlow.getRecordTime().getTime()), "yy/MM/dd hh:mm:ss");
        }
    }

    private void export(String img, HttpServletResponse response, TemplateExportParams params, Map<String, Object> data) {
        POICacheManager.setFileLoder(new MyFileLoad());
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            if (img != null) {
                Sheet sheet = workbook.getSheetAt(0);
                sheet.shiftRows(1, sheet.getLastRowNum(), 15, true, false);
                CellRangeAddress cra = new CellRangeAddress(1, 15, 0, 10);
                sheet.addMergedRegion(cra);
                Drawing drawingPatriarch = sheet.createDrawingPatriarch();
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 0, 1, (short) 11, 15);
                String[] arr = img.split("base64,");
                byte[] buffer = decoder.decodeBuffer(arr[1]);
                drawingPatriarch.createPicture(anchor, workbook.addPicture(buffer, HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
            response.setContentType("application/octet- stream;charset=UTF-8");
            response.setHeader("Connection", "close");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("端口性能.xls", "UTF-8"))));
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/downbusicap")
    public void downBusiCap(String uuid, String reportType, int portType, String ports, String startTime, String endTime, String cyc, HttpServletResponse response, ModelMap map, String img) {
        try {
            Map<String, Object> datas = loadingCache.get(uuid);
            List<Map<String, Object>> portFlows = (List<Map<String, Object>>) datas.get("flows");
            NetPortFlowCap netPortFlowCap = (NetPortFlowCap) datas.get("max");
            JSONArray maxFlowsjsons = new JSONArray();
            if (netPortFlowCap != null) {
                JSONObject obj = JSONObject.fromObject(netPortFlowCap);
                maxFlowsjsons.add(obj);
            }
            TemplateExportParams params = new TemplateExportParams(
                    "/com/idc/action/cap/temp.xls", true);
            Map<String, Object> data = new HashMap<>();
            data.put("caplist", maxFlowsjsons);
            data.put("detaillist", portFlows);
            export(img, response, params, data);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/downcap")
    public void downPortCap(String uuid, String reportType, int portType, String ports, String startTime, String endTime, String cyc, HttpServletResponse response, ModelMap map, String img) {
        try {
            Map<String, Object> datas = loadingCache.get(uuid);
            List<NetPortFlow> portFlows = (List<NetPortFlow>) datas.get("flows");
            NetPortFlowCap netPortFlowCap = (NetPortFlowCap) datas.get("data");
            JSONArray detailFlowsjsons = new JSONArray();
            if (portFlows != null) {
                for (NetPortFlow portFlow : portFlows) {
                    JSONObject obj = JSONObject.fromObject(portFlow);
                    obj.put("recordTimestr", portFlow.getRecordTimeStr());
                    obj.put("inflowUR", (portFlow.getInflowMbps() / portFlow.getBandwidth().doubleValue() * 100));
                    obj.put("inflow", portFlow.getInflowMbps());
                    obj.put("outflowUR", (portFlow.getOutflowMbps() / portFlow.getBandwidth().doubleValue() * 100));
                    obj.put("outflow", portFlow.getOutflowMbps());
                    detailFlowsjsons.add(obj);
                }
            }
            JSONArray maxFlowsjsons = new JSONArray();
            if (netPortFlowCap != null) {
                JSONObject obj = JSONObject.fromObject(netPortFlowCap);
                maxFlowsjsons.add(obj);
            }
            TemplateExportParams params = new TemplateExportParams(
                    "/com/idc/action/cap/temp.xls", true);
            Map<String, Object> data = new HashMap<>();
            data.put("caplist", maxFlowsjsons);
            data.put("detaillist", detailFlowsjsons);
            export(img, response, params, data);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class MyFileLoad extends FileLoadeImpl {
        @Override
        public byte[] getFile(String url) {
            InputStream fileis = null;
            ByteArrayOutputStream baos = null;
            try {
                //先用绝对路径查询,再查询相对路径
                try {
                    fileis = new FileInputStream(url);
                } catch (FileNotFoundException e) {
                    try {
                        fileis = this.getClass().getResourceAsStream(url);
                    } catch (Exception e1) {
                        String path = PoiPublicUtil.getWebRootPath(url);
                        fileis = new FileInputStream(path);
                    }

                }
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fileis.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                return baos.toByteArray();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                IOUtils.closeQuietly(fileis);
                IOUtils.closeQuietly(baos);
            }
            return null;
        }
    }
}
