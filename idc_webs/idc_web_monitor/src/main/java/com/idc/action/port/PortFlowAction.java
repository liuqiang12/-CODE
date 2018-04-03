package com.idc.action.port;

import com.idc.action.cap.DataCache;
import com.idc.model.NetDevice;
import com.idc.model.NetPort;
import com.idc.model.NetPortFlow;
import com.idc.service.NetDeviceService;
import com.idc.service.NetPortFlowService;
import com.idc.service.NetPortService;
import com.idc.service.impl.CapZipTask;
import com.idc.utils.FlowUtil;
import com.idc.vo.NetCapPortDetail;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mylove on 2017/4/24.
 */
@Controller
public class PortFlowAction {
    @Autowired
    private NetPortFlowService netPortFlowService;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private NetDeviceService netDeviceService;
    @Autowired
    private CapZipTask capZipTask;
    @RequestMapping("/port/page/ports")
    public String page_Port() {
        return "port/ports";
    }
    @RequestMapping("/flow/zip/{zip}")
    public void setZip(@PathVariable int zip){
        capZipTask.setIsZip(zip==0);
    }
    @RequestMapping("/port/portsflow/{deviceId}")
    @ResponseBody
    public EasyUIData getPortFlow(EasyUIPageBean pageBean, @PathVariable long deviceId) {
        NetPort query = new NetPort();
        query.setDeviceid(deviceId);
        List<NetPortFlow> netPorts = netPortFlowService.queryListJoinFlowByObject(query);
        List<Map<String, Object>> result = new ArrayList<>();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object o, String s, Object o1) {
                return o instanceof NetPortFlow && (o1 == null || o1.equals("null"));
            }
        });
        for (NetPortFlow netPort : netPorts) {
            JSONObject jsonObject = JSONObject.fromObject(netPort, jsonConfig);
            if (netPort.getBandwidth() != null && netPort.getBandwidth().intValue() != 0) {
                jsonObject.put("inflowusage", netPort.getInflowMbps() / netPort.getBandwidth().doubleValue() * 100);
                jsonObject.put("outinflowusage", netPort.getOutflowMbps() / netPort.getBandwidth().doubleValue() * 100);
//                jsonObject.put("recordTime", netPort.getRecordTime() == null ? "----" : netPort.getRecordTime().toString());
            }
            result.add(jsonObject);
        }
//        List<Map<String, Object>> rows = new ArrayList<>();
//        int page = pageBean.getPage() - 1;
//        int pageSize = pageBean.getPageSize();
//        Map<String, Object> objectMap = new HashMap<>();
//        for (int i = page * pageSize; i < page * pageSize + pageSize; i++) {
//            objectMap = new HashMap<>();
//            objectMap.put("id", i);
//            objectMap.put("device", "设备01");
//            objectMap.put("name", "端口" + i);
//            objectMap.put("bandWidth", "100MB");
//            objectMap.put("ip", "192.168.0." + i);
//            objectMap.put("mac", "fe80::951c:2481:bc3d:4ea1");
//            objectMap.put("inflow", 6000);
//            objectMap.put("outflow", 4000);
//            objectMap.put("status", 1);
//            rows.add(objectMap);
//        }
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setRows(result);
        easyUIData.setTotal(result.size());
        return easyUIData;
    }

    @RequestMapping("/port/portinfo/{id}")
    public String portInfo(@PathVariable int id) {
        return "port/portinfo";
    }

    /***
     * 端口流量趋势
     *
     * @param id
     * @return
     */
    @RequestMapping("/port/flowchart/{id}")
    @ResponseBody
    public Map<String, Object> flowChart(@PathVariable long id, String startTime, String endTime) {
        Map<String, Object> result = new HashMap<>();

        NetCapPortDetail capPortDetail = netPortFlowService.queryFlowByWeek(id, startTime, endTime);
        List<NetPortFlow> flows = new ArrayList<>();
        if (capPortDetail != null) {
            flows = capPortDetail.getDetails();
            result.put("data",capPortDetail.getNetCapPort());
            result.put("flows",flows);
        }
        int size = flows.size();
        String[] labels = new String[size];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");
        double[] inflows = new double[size],
                outflows = new double[size],
                allflows = new double[size];
        for (int i = 0; i < size; i++) {
            NetPortFlow netPortFlow = flows.get(i);
            labels[i] = sdf.format(netPortFlow.getRecordTime());
            inflows[i] = FlowUtil.format3(netPortFlow.getInflowMbps());
            outflows[i] = FlowUtil.format3(netPortFlow.getOutflowMbps());
            allflows[i] = FlowUtil.format3(netPortFlow.getInflowMbps() + netPortFlow.getOutflowMbps());
        }

        String uuid = id+"_"+startTime+"_"+endTime;
        result.put("uuid", uuid);
        DataCache.getSingleton().getCache().put(uuid,result);
        result.put("labels", labels);
        result.put("inflows", inflows);
        result.put("outflows", outflows);
        result.put("allflows", allflows);
        return result;
    }

//    @RequestMapping("/port/flowchartMuti/{type}/{id}")
//    @ResponseBody
//    public Map<String, Object> flowchartMuti(@PathVariable int type, @PathVariable long id) {
//        Map<String, Object> result = new HashMap<>();
//        List<NetPortFlow> flows = netPortFlowService.queryFlowBy(type, id);
//        int size = flows.size();
//        String[] labels = new String[size];
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        double[] inflows = new double[size],
//                outflows = new double[size],
//                allflows = new double[size];
//        for (int i = 0; i < size; i++) {
//            NetPortFlow netPortFlow = flows.get(i);
//            labels[i] = sdf.format(netPortFlow.getRecordTime());
//            inflows[i] = FlowUtil.format3(netPortFlow.getInflowMbps());
//            outflows[i] = FlowUtil.format3(netPortFlow.getOutflowMbps());
//            allflows[i] = FlowUtil.format3(FlowUtil.ByteToKb(netPortFlow.getInflowMbps() + netPortFlow.getOutflowMbps()));
//        }
//        result.put("labels", labels);
//        result.put("inflows", inflows);
//        result.put("outflows", allflows);
//        result.put("allflows", new double[]{180D, 226.6D, 428D, 140D});
//        return result;
//    }
//
//    /***
//     * 资源监视
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("/port/ports")
//    @ResponseBody
//    public Map<String, Object> ports(@PathVariable int id) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("labels",new String[]{"2017-01-02:11:11:11","2017-01-02:12:12:11","2017-01-03:13:13:13","2017-01-03:14:13:13"});
//        result.put("inflows",new double[]{100D,123.3D,234D,82D});
//        result.put("outflows",new double[]{80D,103.3D,194D,60D});
//        result.put("allflows",new double[]{180D,226.6D,428D,140D});
//        return result;
//    }

    @RequestMapping("/deviceperforms/usage/{id}")
    @ResponseBody
    public Map<String, Object> usage(@PathVariable long id) {
        return netPortFlowService.getUsage(id);
    }

    @RequestMapping("/port/portBoard/{id}")
    public String portBoard(@PathVariable long id, ModelMap map) {
        NetPort query = new NetPort();
        query.setDeviceid(id);
        List<NetPort> netProts = netPortService.queryListByObject(query);
//        for (int i = 0; i < 100; i++) {
//            NetProt n=new NetProt();
//            n.setPortactive(1L);
//            n.setPortname(i+"");
//            if(i%20==0){
//                n.setPortactive(2L);
//            }
//            netProts.add(n);
//        }
        Map<String, List<NetPort>> portsname = new HashMap<>();
        for (NetPort netProt : netProts) {
            String portname = netProt.getPortname();
            String[] split = portname.split("/");
            if (split.length < 2) {
                List<NetPort> tmpPorts = portsname.get("other");
                if (tmpPorts == null) {
                    if (tmpPorts == null) {
                        tmpPorts = new ArrayList<>();
                    }
                    tmpPorts.add(netProt);
                    portsname.put("other", tmpPorts);
                }
                continue;
            }
            String s = split[1].toLowerCase();
            if (!s.equals("ge")) {
                String row = split[split.length - 2];
                List<NetPort> tmpPorts = portsname.get(row);
                if (tmpPorts == null) {
                    tmpPorts = new ArrayList<>();
                }
                tmpPorts.add(netProt);
                portsname.put(row, tmpPorts);
            } else {
                List<NetPort> tmpPorts = portsname.get("ge");
                if (tmpPorts == null) {
                    if (tmpPorts == null) {
                        tmpPorts = new ArrayList<>();
                    }
                    tmpPorts.add(netProt);
                    portsname.put("ge", tmpPorts);
                }
            }
        }
        for (String s : portsname.keySet()) {
            List<NetPort> tmp = portsname.get(s);
            Collections.sort(tmp, new Comparator<NetPort>() {
                @Override
                public int compare(NetPort o1, NetPort o2) {
                    return o1.getPortname().compareTo(o2.getPortname());
                }
            });
            portsname.put(s, tmp);
        }
        map.put("portMaps", portsname);
//        map.put("ports", netProts);
        NetDevice modelById = netDeviceService.getModelById(id);
        map.put("device", modelById);
        return "port/portBoard";
    }


}
