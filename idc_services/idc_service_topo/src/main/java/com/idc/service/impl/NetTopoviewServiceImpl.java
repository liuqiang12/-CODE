package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.NetPortFlowService;
import com.idc.service.NetTopoviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.EasyUIPageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.PropertyUtils;
import utils.typeHelper.MapHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_TOPOVIEW:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 25,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("netTopoviewService")
public class NetTopoviewServiceImpl extends SuperServiceImpl<NetTopoView, Long> implements NetTopoviewService {
    @Autowired
    private NetTopoviewMapper mapper;
    @Autowired
    private IdcDeviceMapper deviceMapper;
    @Autowired
    private NetTopoviewobjMapper netTopoviewobjMapper;
    @Autowired
    private NetTopoObjLinkMapper netTopoObjLinkMapper;
    @Autowired
    private NetPortFlowMapper flowMapper;
    @Autowired
    private NetPortMapper netPortMapper;
    @Autowired
    private NetPortFlowService netPortFlowService;
    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private NetTopoviewobjService netTopoviewobjService;
//    @Autowired
//    private NetTopoObjLinkService netTopoObjLinkService;

    @Override
    public List getObjs(EasyUIPageBean page, int type, String q) {
        if (type == 0) {
            IdcDevice idcDevice = new IdcDevice();
            idcDevice.setName(q);
            page.setParams(MapHelper.queryCondition(idcDevice));
            List<Map<String, Object>> maps = deviceMapper.queryListPageMap(page);
            page.getItems().clear();
            Map<String, Object> map;
            for (Map<String, Object> smap : maps) {
                map = new HashMap<>();
                map.put("ID", smap.get("DEVICEID"));
                map.put("NAME", smap.get("NAME"));
                page.getItems().add(map);
            }
        } else if (type == 2) {
            NetTopoView netTopoView = new NetTopoView();
            netTopoView.setViewname(q);
            page.setParams(MapHelper.queryCondition(netTopoView));
            List<NetTopoView> netTopoViews = mapper.queryListPage(page);
            page.getItems().clear();
            Map<String, Object> map;
            for (NetTopoView smap : netTopoViews) {
                map = new HashMap<>();
                map.put("ID", smap.getViewid());
                map.put("NAME", smap.getViewname());
                page.getItems().add(map);
            }
        }
        return page.getItems();
    }

    @Override
    public NetTopoView getModelDetailById(long viewid) {
        NetTopoView modelById = getModelById(viewid);

        NetTopoViewObj netTopoViewObj = new NetTopoViewObj();
        netTopoViewObj.setViewid(viewid);
        List<NetTopoViewObj> netTopoViewObjs = netTopoviewobjMapper.queryListByObject(netTopoViewObj);
        NetTopoObjLink netTopoObjLink = new NetTopoObjLink();

        netTopoObjLink.setViewid(viewid);
        List<NetTopoObjLink> netTopoObjLinks = netTopoObjLinkMapper.queryListByObject(netTopoObjLink);
        modelById.setViewLinks(netTopoObjLinks);
        modelById.setViewObjs(netTopoViewObjs);
        return modelById;
    }

    @Override
    @Transactional
    public ExecuteResult save(long viewId, List<NetTopoViewObj> nodes, List<NetTopoObjLink> links) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        NetTopoView modelDetailById = getModelDetailById(viewId);
        if (modelDetailById.getViewObjs().size() > 0) {
            netTopoviewobjMapper.deleteByList(modelDetailById.getViewObjs());
        }
        if (modelDetailById.getViewLinks().size() > 0) {
            netTopoObjLinkMapper.deleteByList(modelDetailById.getViewLinks());
        }
        Map<String, NetTopoViewObj> maps = new HashMap<>();
        for (NetTopoViewObj node : nodes) {
            node.setViewid(viewId);
            netTopoviewobjMapper.insert(node);
            maps.put(node.getName(), node);
        }
        for (NetTopoObjLink link : links) {
            NetTopoViewObj netTopoViewObjA = maps.get(link.getSrcid());
            NetTopoViewObj netTopoViewObjZ = maps.get(link.getDesid());
            if (netTopoViewObjA == null || netTopoViewObjZ == null) {
                logger.error("连线两头为空排除" + link.toString());
                continue;
            }
            link.setSrcid(netTopoViewObjA.getObjpid().toString());
            link.setDesid(netTopoViewObjZ.getObjpid().toString());
            link.setViewid(viewId);
            netTopoObjLinkMapper.insert(link);
        }
        return executeResult;
    }
    public List<NetTopoObjLinkFlow> getFlow(NetTopoView netTopoView,boolean isnew) {
        //最后返回的链路信息
        List<NetTopoObjLink> viewLinks = netTopoView.getViewLinks();//建立的链接 不代表最后返回 要去除冗余

        List<NetTopoViewObj> viewObjs = netTopoView.getViewObjs();//链接对象用来和流量信息转换

        List<NetTopoObjLinkFlow> linkFlowList = new ArrayList<>();
        ImmutableMap<Long, NetTopoViewObj> objMap = Maps.uniqueIndex(viewObjs, new Function<NetTopoViewObj, Long>() {
            @Override
            public Long apply(NetTopoViewObj netTopoViewObj) {
                return netTopoViewObj.getObjpid();
            }
        });


        NetPort Query = new NetPort();
        Query.setAdminstatus(1L);
        Query.setPortactive(1L);
        List<NetPort> netPorts = netPortMapper.queryListByObject(Query);
        Map<String,String> nameByIdMap=new HashMap<>();
        Map<String,Long> nameByDeviceMap=new HashMap<>();
        for (NetPort netPort : netPorts) {
            Long deviceid = netPort.getDeviceid();
            nameByIdMap.put(deviceid + "_" + netPort.getPortname(), netPort.getPortid().toString());
            nameByDeviceMap.put(netPort.getPortid().toString(),netPort.getDeviceid());
        }
        List<String> ids = new ArrayList<>();
        for (NetTopoObjLink viewLink : viewLinks) {
            String s = nameByIdMap.get(viewLink.getSrcdeviceid() + "_" + viewLink.getSrcportname());
            if(s!=null){
                ids.add(s);
            }
            s = nameByIdMap.get(viewLink.getDesdeviceid() + "_" + viewLink.getDesportname());
            if(s!=null){
                ids.add(s);
            }
        }

//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        List<NetPortFlow> currList = netPortFlowService.getCurrList(ids);
        List<NetPortFlow> flows = new ArrayList<>(1000);
        if(currList!=null){
            Long aLong;
            for (NetPortFlow netPortFlow : currList) {
                aLong = nameByDeviceMap.get(netPortFlow.getPortid().toString());
                if(aLong==null){
                    continue;
                }
                flows.add(netPortFlow);
            }
        }

//        List<String> net_port_curr = redisTemplate.opsForHash().multiGet("net_port_curr", ids);
//        List<NetPortFlow> flows = new ArrayList<>(1000);
//        Map<String,PortCapSerial> map = new HashMap<>(5000);
//        NetPortFlow tmp;
//        Long aLong;
//        for (String s : net_port_curr) {
//            if(s==null){
//                continue;
//            }
//            PortCapSerial portCapSerial = JSON.parseObject(s, PortCapSerial.class);
//            tmp = new NetPortFlow();
//            tmp.setPortid(portCapSerial.getPortid());
//            aLong = nameByDeviceMap.get(portCapSerial.getPortid().toString());
//            if(aLong==null){
//                continue;
//            }
//            tmp.setDeviceid(aLong);
//            tmp.setPortname(portCapSerial.getPortname());
//            tmp.setInflow(portCapSerial.getInputFlux());
//            tmp.setOutflow(portCapSerial.getOutputFlux());
//            tmp.setRecordTime(portCapSerial.getRecordTime());
//            flows.add(tmp);
////            map.put(portCapSerial.getPortid().toString(),portCapSerial);
//        }
//        //获取场景内所有流量
////        List<NetPortFlow> flows  = flowMapper.getAllFlowByViewId(netTopoView.getViewid());

        Map<String,List<NetTopoObjLink>> linkMap = linkMerge(viewLinks);

        Map<String, NetTopoObjLinkFlow> devToDevFlow = addDevToDevFlow(linkMap, flows, objMap);
        //子图
        for (NetTopoObjLink viewLink : viewLinks) {
            Long srcdeviceid = Long.parseLong(viewLink.getSrcid());
            Long desdeviceid = Long.parseLong(viewLink.getDesid());
            NetTopoViewObj sdViewObj = objMap.get(srcdeviceid);//通过链路关联ID 获取链路端点对象
            NetTopoViewObj dsViewObk = objMap.get(desdeviceid);
            //校验链接得两端
            boolean flag = checkObj(viewLink,sdViewObj,dsViewObk);
            if(flag){
                continue;
            }
            if (sdViewObj.getObjtype() == 0 && dsViewObk.getObjtype() == 0) {//设备端口 如果两端都是设备端口得话 直接添加计算流量
                continue;
            }else{//如果一端不是设备 表示子图 子图不统计流量
                NetTopoObjLinkFlow netTopoObjLinkFlow = new NetTopoObjLinkFlow();
                netTopoObjLinkFlow.setSrcid(viewLink.getSrcid());
                netTopoObjLinkFlow.setSrcdeviceid(viewLink.getSrcdeviceid());
                netTopoObjLinkFlow.setViewid(viewLink.getViewid());
                netTopoObjLinkFlow.setDesid(viewLink.getDesid());
                netTopoObjLinkFlow.setDesdeviceid(viewLink.getDesdeviceid());
                netTopoObjLinkFlow.setTopoLinkID(viewLink.getTopoLinkID());
                linkFlowList.add(netTopoObjLinkFlow);
            }
        }

        for (String s : devToDevFlow.keySet()) {
            linkFlowList.add(devToDevFlow.get(s));
        }

        return linkFlowList;
    }

    /***
     * 设备到设备流量
     * @param linkMap
     * @param flows
     * @param objMap
     * @return
     */
    private Map<String, NetTopoObjLinkFlow> addDevToDevFlow(Map<String, List<NetTopoObjLink>> linkMap, List<NetPortFlow> flows, ImmutableMap<Long, NetTopoViewObj> objMap) {
        //设备下端口流量信息 防止因为端口名字重复导致流量混乱
        Map<String, NetPortFlow> portFlowMap = new HashMap<>();
        for (NetPortFlow flow : flows) {
            portFlowMap.put(flow.getDeviceid()+"_"+flow.getPortname().trim().toString(),flow);
        }
//        ImmutableMap<String, NetPortFlow> portFlowMap = Maps.uniqueIndex(flows, new Function<NetPortFlow, String>() {
//            @Override
//            public String apply(NetPortFlow netPortFlow) {
//                return netPortFlow.getDeviceid()+"_"+netPortFlow.getPortname().trim().toString();//每个端口的流量
//            }
//        });
        Map<String,NetTopoObjLinkFlow> linkFlow = new HashMap<>();
        for (String linkname : linkMap.keySet()) {//链路名字代表方向 A-B 那就获取A的出A的入  如果A-B找不到就反向获取B入B出 ;A_input=B_output
            List<NetTopoObjLink> curr = linkMap.get(linkname);// 获取当前链路信息
            for (NetTopoObjLink netTopoObjLink : curr) {//该链路下的所有链接
                boolean isAsc = true;//标记顺序 默认顺序A-B
                Long srcid = Long.parseLong(netTopoObjLink.getSrcid());//当前链路对象的Srcid
                NetTopoViewObj sdObj = objMap.get(srcid);//当前对象信息
                Long desdeviceid = Long.parseLong(netTopoObjLink.getDesid());//当前链路对象的Srcid
                NetTopoViewObj dsObj = objMap.get(desdeviceid);//当前对象信息
                if(sdObj==null||dsObj==null){//链路对象信息必须有 找不到的不创建链接
                    continue;
                }
                if (sdObj.getObjtype() == 0 && dsObj.getObjtype() == 0) {//流量统计只统计设备-设备
                    String portindex =netTopoObjLink.getSrcportname();//先用A端端口获取流量
                    NetPortFlow netPortFlow = portFlowMap.get(netTopoObjLink.getSrcdeviceid()+"_"+portindex); //通过对象信息获取该链路本端端口
                    if (netPortFlow == null) {//如果A端端口流量找不到就反向获取B入B出 ;A_input=B_output
                        isAsc = false;
                        portindex = netTopoObjLink.getDesportname();
                        netPortFlow = portFlowMap.get(netTopoObjLink.getSrcdeviceid()+"_"+portindex); //通过对象信息获取该链路本端端口
                    }
                    if(netPortFlow==null){
                        netPortFlow = new NetPortFlow();
                    }
                    NetTopoObjLinkFlow flow = linkFlow.get(linkname);//单个链路的流量信息
                    if (flow == null) {//创建流量对象
                        flow = new NetTopoObjLinkFlow();
                        flow.setTopoLinkID(netTopoObjLink.getTopoLinkID());
                        flow.setSrcdeviceid(netTopoObjLink.getSrcdeviceid());
                        flow.setDesdeviceid(netTopoObjLink.getDesdeviceid());
                        flow.setSrcid(netTopoObjLink.getSrcid());
                        flow.setDesid(netTopoObjLink.getDesid());
                    }
                    addDevToDevFlow(flow, netPortFlow, isAsc);
                    linkFlow.put(linkname, flow);
                }
            }
        }
        return linkFlow;
    }

    /***
     * 按方向累加流量
     * @param flow
     * @param netPortFlow
     * @param isAsc
     */
    private void addDevToDevFlow(NetTopoObjLinkFlow flow, NetPortFlow netPortFlow, boolean isAsc) {
        flow.setInflow(flow.getInflow() + (isAsc ? netPortFlow.getInflow() : netPortFlow.getOutflow()));//累加流量
        flow.setOutflow(flow.getOutflow() + (isAsc? netPortFlow.getOutflow() : netPortFlow.getInflow()));
    }

    /***
     * 链路合并 如果src dst == dst-src  就是同一设备上的链路 合并掉
     * @param viewLinks
     * @return
     */
    private Map<String, List<NetTopoObjLink>> linkMerge(List<NetTopoObjLink> viewLinks) {
        Map<String, List<NetTopoObjLink>> links = new HashMap<>();
        for (NetTopoObjLink viewLink : viewLinks) {
            String srcdeviceid = viewLink.getSrcid();//获取Obj 对应信息 这里不是设备id
            String desdeviceid = viewLink.getDesid();
            String sd = srcdeviceid + "_" + desdeviceid;
            String ds = desdeviceid + "_" + srcdeviceid;
            List<NetTopoObjLink> netTopoObjLinks = links.get(sd);
            if(netTopoObjLinks==null){//如果src-des 没有找到  返回查找
                netTopoObjLinks = links.get(ds);
                if(netTopoObjLinks==null){//如果还是没有找到 那就是真的没有找到
                    netTopoObjLinks = new ArrayList<>();
                    links.put(sd, netTopoObjLinks);//手动创建一个容器存放
                }
            }
            netTopoObjLinks.add(viewLink);
        }
        return links;
    }

    /***
     * 是否清除了链接
     * @return
     */
    private boolean checkObj(NetTopoObjLink viewLink, NetTopoViewObj sdViewObj, NetTopoViewObj dsViewObj) {
        if(sdViewObj==null||dsViewObj==null){
            logger.debug("清除不正确的链接");
            try {
                netTopoObjLinkMapper.deleteById(viewLink.getTopoLinkID());
                return true;
            }catch (Exception e){
                logger.error("",e);
            }
        }
        return false;
    }

    @Override
    public List<NetTopoObjLinkFlow> getFlow(NetTopoView netTopoView) {
        return getFlow(netTopoView,false);
//        List<NetTopoObjLink> viewLinks = netTopoView.getViewLinks();
//        List<NetTopoViewObj> viewObjs = netTopoView.getViewObjs();
//        ImmutableMap<Long, NetTopoViewObj> objectNetTopoViewObjImmutableMap = Maps.uniqueIndex(viewObjs, new Function<NetTopoViewObj, Long>() {
//            @Override
//            public Long apply(NetTopoViewObj netTopoViewObj) {
//                return netTopoViewObj.getObjpid();
//            }
//        });
//        List<NetPortFlow> flows = new ArrayList<>();
//        List<NetTopoObjLinkFlow> linkFlowList = new ArrayList<>();
//        Map<String, List<NetTopoObjLink>> links = new HashMap<>();
//        Map<String,List<String>> portMaps= new HashMap<>();
//        for (NetTopoObjLink viewLink : viewLinks) {
//            NetTopoViewObj netTopoViewObj = objectNetTopoViewObjImmutableMap.get(Long.parseLong(viewLink.getSrcid()));
//            NetTopoViewObj desnetTopoViewObj = objectNetTopoViewObjImmutableMap.get(Long.parseLong(viewLink.getDesid()));
//            if(netTopoViewObj==null||desnetTopoViewObj==null){
//                logger.debug("清除不正确的链接");
//                try {
//                    netTopoObjLinkMapper.deleteById(viewLink.getTopoLinkID());
//                }catch (Exception e){
//                    logger.error("",e);
//                }
//                continue;
//            }
//            if (netTopoViewObj.getObjtype() == 0 && desnetTopoViewObj.getObjtype() == 0) {//设备端口
//                String srcid = viewLink.getSrcid();
//                String desid = viewLink.getDesid();//srcid+desid 表示一条链路 方便排除重复和统计链路大小
//                String s = srcid + "_" + desid;
//                String sr = desid + "_" + srcid;
//                List<NetTopoObjLink> netTopoObjLinks = links.get(s);
//                if (netTopoObjLinks == null) {//只要名字一样就认为在一条链路上 不分反正方向
//                    netTopoObjLinks = links.get(sr);
//                    if (netTopoObjLinks == null) {
//                        netTopoObjLinks = new ArrayList<>();
//                        links.put(sr, netTopoObjLinks);
//                    }
//                }
//                String srcportname = viewLink.getSrcportname();
//                if (srcportname != null){
//                    List<String> portstmp = portMaps.get(viewLink.getSrcid());
//                    if(portstmp==null){
//                        portstmp = new ArrayList<>();
//                    }
//                    portstmp.add(srcportname);
//                    portMaps.put(viewLink.getSrcid(),portstmp);
//                }
//                String desportname = viewLink.getDesportname();
//                if (desportname != null){
//                    List<String> portstmp = portMaps.get(viewLink.getDesid());
//                    if(portstmp==null){
//                        portstmp = new ArrayList<>();
//                    }
//                    portstmp.add(desportname);
//                    portMaps.put(viewLink.getDesid(),portstmp);
//                }
//                    //ports.add(desportname+"@@"+viewLink.getDesid());
//                netTopoObjLinks.add(viewLink);
//
//            } else {//子图 如果是子图直接添加连线但是不添加流量
//                NetTopoObjLinkFlow netTopoObjLinkFlow = new NetTopoObjLinkFlow();
//                netTopoObjLinkFlow.setSrcid(viewLink.getSrcid());
//                netTopoObjLinkFlow.setViewid(viewLink.getViewid());
//                netTopoObjLinkFlow.setDesid(viewLink.getDesid());
//                netTopoObjLinkFlow.setTopoLinkID(viewLink.getTopoLinkID());
//                linkFlowList.add(netTopoObjLinkFlow);
//            }
//        }
//        if(portMaps.size()>0){
//            List<DevicePortQueryBean> queryBeans = new ArrayList<>();
//            for (String deviceid : portMaps.keySet()) {
//                DevicePortQueryBean queryBean = new DevicePortQueryBean();
//                queryBean.setDeviceid(deviceid);
//                queryBean.addAllPorts(portMaps.get(deviceid));
//                queryBeans.add(queryBean);
//            }
//            flows = flowMapper.getFlow(queryBeans);
//        }
////         flows  = flowMapper.getAllFlowByViewId(netTopoView.getViewid());
//        ImmutableMap<Object, NetPortFlow> portFlowMap = Maps.uniqueIndex(flows, new Function<NetPortFlow, Object>() {
//            @Override
//            public Object apply(NetPortFlow netPortFlow) {
//                return netPortFlow.getDeviceid()+"_"+netPortFlow.getPortname();//每个端口的流量
//            }
//        });
//        for (String s : links.keySet()) {//通过链路获取连续端口并累加端口流量
//            NetTopoObjLinkFlow flow = new NetTopoObjLinkFlow();//创建一 个链路流量对象
//            List<NetTopoObjLink> netTopoObjLinks = links.get(s);//该链路关联的子链接
//            for (NetTopoObjLink netTopoObjLink : netTopoObjLinks) {//获取每个链接的流量
//                NetTopoViewObj netTopoViewObj = objectNetTopoViewObjImmutableMap.get(Long.parseLong(netTopoObjLink.getSrcid()));
//                NetPortFlow netPortFlow = portFlowMap.get(netTopoViewObj.getObjfid()+"_"+netTopoObjLink.getSrcportname()); //获取该链路本端端口
//                String[] split = s.split("_");
//                flow.setTopoLinkID(netTopoObjLink.getTopoLinkID());
//                flow.setSrcid(netTopoObjLink.getSrcid());
//                flow.setDesid(netTopoObjLink.getDesid());
//                if (netPortFlow == null){///如果没有获取到流量 设空
//                    continue;
//                }
//                flow.setInflow(flow.getInflow() + (split[0].equals(netTopoObjLink.getSrcportname()) ? netPortFlow.getInflow() : netPortFlow.getOutflow()));//累加流量
//                flow.setOutflow(flow.getOutflow() + (split[1].equals(netTopoObjLink.getSrcportname()) ? netPortFlow.getOutflow() : netPortFlow.getInflow()));
//            }
//            flow.setChildLinks(netTopoObjLinks.size());//该链路真实包含链路数
//            linkFlowList.add(flow);
//        }
//        return linkFlowList;


    }

    @Override
    public NetTopoObjLinkFlow getLinkFlowMegir(long srcdeviceid, long desdeviceid) {
        List<NetTopoObjLinkFlow> sdlinkFlow = getLinkFlow(srcdeviceid, desdeviceid);//A-B 链路设备方向
        List<NetTopoObjLinkFlow> dslinkFlow = getLinkFlow(desdeviceid, srcdeviceid);//B-A
        NetTopoObjLinkFlow flow = null;
        flow = getNetTopoObjLinkFlowAdd(sdlinkFlow, flow, true);
        flow = getNetTopoObjLinkFlowAdd(dslinkFlow, flow, false);
//        for (NetTopoObjLinkFlow netTopoObjLinkFlow : sdlinkFlow) {
//            if (flow == null) {
//                flow = netTopoObjLinkFlow;
//                flow.setChildLinks(1);
//            } else {
//                flow.setOutflow(flow.getOutflow() + netTopoObjLinkFlow.getInflow());
//                flow.setInflow(flow.getInflow() + netTopoObjLinkFlow.getOutflow());
//                flow.setChildLinks(flow.getChildLinks() + 1);
//            }
//        }

        return flow;
    }

    /***
     * 流量相加
     *
     * @param sdlinkFlow
     * @param flow
     * @param order      顺序
     * @return
     */
    private NetTopoObjLinkFlow getNetTopoObjLinkFlowAdd(List<NetTopoObjLinkFlow> sdlinkFlow, NetTopoObjLinkFlow flow, boolean order) {
        for (NetTopoObjLinkFlow netTopoObjLinkFlow : sdlinkFlow) {
            if (flow == null) {
                flow = netTopoObjLinkFlow;
                flow.setChildLinks(1);
            } else {
                flow.setOutflow(flow.getOutflow() + (order ? netTopoObjLinkFlow.getOutflow() : netTopoObjLinkFlow.getInflow()));
                flow.setInflow(flow.getInflow() + (order ? netTopoObjLinkFlow.getInflow() : netTopoObjLinkFlow.getOutflow()));
                flow.setChildLinks(flow.getChildLinks() + 1);
            }
        }
        return flow;
    }

    /***
     * 获取A-B流量 单一反向
     *
     * @param srcdeviceid
     * @param desdeviceid
     * @return
     */
    @Override
    public List<NetTopoObjLinkFlow> getLinkFlow(long srcdeviceid, long desdeviceid) {
        NetTopoObjLink netTopoObjLink = new NetTopoObjLink();
        netTopoObjLink.setSrcid(srcdeviceid + "");
        netTopoObjLink.setDesid(desdeviceid + "");
        List<NetTopoObjLink> sdnetTopoObjLinks = netTopoObjLinkMapper.queryListByObject(netTopoObjLink);//获取该起始节点的所有链接'
        NetPort Query = new NetPort();
        Query.setAdminstatus(1L);
        Query.setPortactive(1L);
        List<NetPort> netPorts = new ArrayList<>(500);
        for (NetTopoObjLink sdnetTopoObjLink : sdnetTopoObjLinks) {
            Query.setDeviceid(sdnetTopoObjLink.getSrcdeviceid());
            netPorts.addAll(netPortMapper.queryListByObject(Query));
            Query.setDeviceid(sdnetTopoObjLink.getDesdeviceid());
            netPorts.addAll(netPortMapper.queryListByObject(Query));
        }
        Map<String,String> nameByIdMap=new HashMap<>();
        Map<String,Long> nameByDeviceMap=new HashMap<>();
        for (NetPort netPort : netPorts) {
            Long deviceid = netPort.getDeviceid();
            nameByIdMap.put(deviceid + "_" + netPort.getPortname(), netPort.getPortid().toString());
            nameByDeviceMap.put(netPort.getPortid().toString(),netPort.getDeviceid());
        }
        List<String> ports = new ArrayList<>();
        Map<String, NetTopoObjLink> portByLink = new HashMap<>();
        for (NetTopoObjLink sdnetTopoObjLink : sdnetTopoObjLinks) {
            String psrcid = sdnetTopoObjLink.getSrcportname();
            String pdesid = sdnetTopoObjLink.getDesportname();
            if (psrcid == null || pdesid == null) {//排除非端口的链接
                continue;
            }
            portByLink.put(sdnetTopoObjLink.getSrcdeviceid()+"_"+psrcid, sdnetTopoObjLink);
            String s = nameByIdMap.get(sdnetTopoObjLink.getSrcdeviceid() + "_" + sdnetTopoObjLink.getSrcportname());
            if(s!=null){
                ports.add(s);//需要查询流量的端口
            }
        }
        List<NetTopoObjLinkFlow> result = new ArrayList<>();
        if (ports.size() == 0){
            return result;
        }
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());


//        List<String> net_port_curr = redisTemplate.opsForHash().multiGet("net_port_curr", ports);
        List<NetPortFlow> flow=new ArrayList<>(1000);
        NetPortFlow tmp;
        Long aLong;
//        Map<String,PortCapSerial> map = new HashMap<>(5000);
        List<NetPortFlow> currList = netPortFlowService.getCurrList(ports);
        if(currList!=null){
            for (NetPortFlow netPortFlow : currList) {
                aLong = nameByDeviceMap.get(netPortFlow.getPortid().toString());
                if(aLong==null){
                    continue;
                }
                flow.add(netPortFlow);
            }
        }

//        for (String s : net_port_curr) {
//            if(s==null){
//                continue;
//            }
//            PortCapSerial portCapSerial = JSON.parseObject(s, PortCapSerial.class);
//            tmp = new NetPortFlow();
//            aLong = nameByDeviceMap.get(portCapSerial.getPortid().toString());
//            if(aLong==null){
//                continue;
//            }
//            tmp.setDeviceid(aLong);
//            tmp.setPortname(portCapSerial.getPortname());
//            tmp.setInflow(portCapSerial.getInputFlux());
//            tmp.setOutflow(portCapSerial.getOutputFlux());
//            tmp.setRecordTime(portCapSerial.getRecordTime());
//            flow.add(tmp);
////            map.put(portCapSerial.getPortid().toString(),portCapSerial);
//        }

//        DevicePortQueryBean queryBean = new DevicePortQueryBean();
//        queryBean.setDeviceid(srcdeviceid+"");
//        queryBean.addAllPorts(ports);

//        List<NetPortFlow> flow = flowMapper.getFlow(Arrays.asList(queryBean));//获取这些端口的最新流量
//        List<IdcDevice> idcDevices = deviceMapper.queryList();
//        ImmutableMap<String, IdcDevice> deviceImmutableMap = Maps.uniqueIndex(deviceMapper.queryList(), new Function<IdcDevice, String>() {
//            @Override
//            public String apply(IdcDevice idcDevice) {
//                return idcDevice.getDeviceid().toString();
//            }
//        });
        NetTopoObjLinkFlow newLink = null;
        for (NetPortFlow netPortFlow : flow) {
            NetTopoObjLink srcLink = portByLink.get(netPortFlow.getDeviceid()+"_"+netPortFlow.getPortname().toString());
            if (srcLink != null) {//转换为流量对象
                newLink = new NetTopoObjLinkFlow();
                try {
                    PropertyUtils.copyProperties(newLink, srcLink);
                    newLink.setInflow(netPortFlow.getInflow());
                    newLink.setOutflow(netPortFlow.getOutflow());
                    result.add(newLink);
                } catch (Exception e) {
                    logger.error("转换失败:" + srcLink, e);
                }
            }
        }
        return result;
    }

    @Override
    public List<NetTopoObjLinkFlow> getFlowByLinks(List<NetTopoObjLink> viewLinks) {
        for (NetTopoObjLink viewLink : viewLinks) {
//            viewLink
        }
        return null;
    }

    /**
     *   Special code can be written here
     */
}
