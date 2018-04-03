package com.idc.service.impl;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.IdcConnectorService;
import com.idc.service.IdcLinkService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.ImageWatched;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_LINK:连接表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcLinkService")
public class IdcLinkServiceImpl extends SuperServiceImpl<IdcLink, Long> implements IdcLinkService {
    @Autowired
    private IdcLinkMapper mapper;
    @Autowired
    private IdcRackMapper rackMapper;
    @Autowired
    private IdcConnectorService idcConnectorService;
    @Autowired
    private IdcDeviceMapper idcDeviceMapper;
    @Autowired
    private NetPortMapper netPortMapper;
    @Autowired
    private NetDeviceMapper netDeviceMapper;

    @Override
    public List<LinkNode> getTree(String type, long id) {
        List<LinkNode> nodes = new ArrayList<>();
        calNode(id, nodes);
        return nodes;
    }
    public void calNode(long id, List<LinkNode> nodes) {
        LinkNode rooNode = getNode(0, id);
        addNodes(nodes,rooNode);
        calNode(true, id, rooNode, nodes);//获取他所有链接关系
        calNode(false, id, rooNode, nodes);
        removeDuplicate(nodes);
    }

    public List removeDuplicate(List<LinkNode> list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    public void calNode(boolean isZ, long id, LinkNode rooNode, List<LinkNode> nodes) {
        IdcLink query = new IdcLink();
        if (isZ) {
            query.setZendrackId(id);
        } else {
            query.setAendrackId(id);
        }
        List<IdcLink> idcLinks = mapper.queryListByObject(query);
        for (IdcLink idcLink : idcLinks) {
            LinkNode zrackNode = getNode(0, isZ ? idcLink.getAendrackId() : idcLink.getZendrackId());
            if (zrackNode == null) continue;
            addNodes(nodes, zrackNode);
            addNodes(nodes, new LinkNode(rooNode, zrackNode));
            Long zendDeviceId = isZ ? idcLink.getAenddeviceId() : idcLink.getZenddeviceId();//对端客户架端子设备==
            Long zendPortId = isZ ? idcLink.getAendportId() : idcLink.getZendportId();
            LinkNode portNode = null;
            LinkNode deviceNode = null;
            if (zendDeviceId != null) {
                deviceNode = getNode(1, zendDeviceId);
            }
            if (deviceNode != null) {
                addNodes(nodes, new LinkNode(zrackNode, deviceNode));
                addNodes(nodes, deviceNode);
                if (zendPortId != null) {
                    portNode = getNode(3, zendPortId);
                    if (portNode == null) continue;
                    addNodes(nodes, new LinkNode(deviceNode, portNode));
                    addNodes(nodes, portNode);
                }
                // nodes.add(new LinkNode(deviceNode, portNode));//链接端口和设备 网络贵
            } else {
                if (zendPortId != null) {
                    portNode = getNode(2, zendPortId);
                    if (portNode == null) continue;
                    addNodes(nodes, portNode);
                    addNodes(nodes, new LinkNode(zrackNode, portNode));
                }
            }
            getNextNode(zrackNode, nodes, 0L);//获取所有链接到改节点的机柜 但是排除开始节点引入的
        }
    }

    private void addNodes(List<LinkNode> nodes, LinkNode linkNode) {
        boolean isAdd = true;
        for (LinkNode node : nodes) {
            if (linkNode.getA() != null && linkNode.getZ() != null&&node.getA() != null && node.getZ() != null) {//链接
                if ((node.getA().getKey().equals(linkNode.getA().getKey()) && node.getZ().getKey().equals(linkNode.getZ().getKey())) || (node.getA().getKey().equals(linkNode.getZ().getKey()) && node.getZ().getKey().equals(linkNode.getA().getKey()))) {//已经存在
                    isAdd = false;
                    break;
                }
            } else {
                if (linkNode.getKey().equals(node.getKey())) {
                    isAdd = false;
                    break;
                }
            }
        }
        if (isAdd) {
            nodes.add(linkNode);
        }
    }

    private void getNextNode(LinkNode rackNode, List<LinkNode> nodes, long id) {
        String ids = rackNode.getId();
        getRackNode(true, Long.valueOf(ids), nodes);
        getRackNode(false, Long.valueOf(ids), nodes);
    }

    void getRackNode(boolean isZ, long id, List<LinkNode> linkNodes) {
        IdcLink query = new IdcLink();
        if (isZ) {
            query.setZendrackId(id);
        } else {
            query.setAendrackId(id);
        }
        List<IdcLink> idcLinks = mapper.queryListByObject(query);
        LinkNode startNode = getNode(0, id);//开始节点柜  对端的机柜或设备 端子直接链接
        linkNodes.add(startNode);
        addNodes(linkNodes,startNode);
//        new LinkNode(rooNode, zrackNode)
        for (IdcLink idcLink : idcLinks) {
            LinkNode zrackNode = getNode(0, isZ ? idcLink.getAendrackId() : idcLink.getZendrackId());
            addNodes(linkNodes,zrackNode);
            addNodes(linkNodes,new LinkNode(startNode, zrackNode));
//            linkNodes.add(zrackNode);
//            linkNodes.add(new LinkNode(startNode, zrackNode));//链接端口和设备 网络贵
        }
    }

    public void test(Long id, List<LinkNode> linkNodes) {
        IdcLink query = new IdcLink();
        //query.setAendrackId(2022781L);//获取机房A开始的节点
        query.setAendrackId(id);//获取机房A开始的节点
        List<IdcLink> idcLinks = mapper.queryListByObject(query);
        for (IdcLink idcLink : idcLinks) {
            LinkNode A = new LinkNode();
            A.setId(idcLink.getId().toString());
            A.setName(idcLink.getName());
            A.setKey("rack_" + A.getId());
            A.setType(0);
            linkNodes.add(A);
            Long zenddeviceId = idcLink.getZenddeviceId();
            Long zendportId = idcLink.getZendportId();
            Long zendrackId = idcLink.getZendrackId();

            addZendNode(zenddeviceId, 1, A, linkNodes);
            addZendNode(zendportId, 2, A, linkNodes);
            addZendNode(zendrackId, 0, A, linkNodes);

//            IdcRack idcRack = rackMapper.getModelById(Integer.valueOf(idcLink.getZendrackId().toString()));//获取对端机架
//            idcLink.setZrack(idcRack);
//
//            if (zenddeviceId != null && zenddeviceId > 0) {//网络设备
//                IdcDevice idcDevice = idcDeviceMapper.getModelById(zenddeviceId);
//                idcLink.setZdevice(idcDevice);
//            }
//            if (zendportId != null && zendportId > 0) {//O+DF
//                IdcConnector idcConnector = idcConnectorService.getModelById(zendportId);
//                idcLink.setZconn(idcConnector);
//            }
        }
    }

    private void addZendNodef(Long id, int starttype, LinkNode a, List<LinkNode> linkNodes) {
        IdcLink query = new IdcLink();
        LinkNode A = getNode(starttype, id);
        switch (starttype) {
            case 0:
                query.setAendrackId(id);//获取机房A开始的节点
                break;
            case 1:
                query.setAenddeviceId(id);//获取机房A开始的节点
                break;
            case 2:
                query.setAendportId(id);//获取机房A开始的节点
                break;
            default:
                break;
        }
        linkNodes.add(A);
        List<IdcLink> idcLinks = mapper.queryListByObject(query);
        for (IdcLink idcLink : idcLinks) {
            LinkNode rackNode = getNode(1, idcLink.getZendrackId());
            linkNodes.add(rackNode);
            Long zendDeviceId = idcLink.getZenddeviceId();
            Long zendPortId = idcLink.getZendportId();
            LinkNode deviceNode = getNode(1, zendDeviceId);
            if (deviceNode != null) {
                linkNodes.add(deviceNode);
                rackNode.addChild(deviceNode);
            }
            LinkNode portNode = getNode(2, zendPortId);
            if (portNode != null) {
                linkNodes.add(portNode);
                if (deviceNode != null) {
                    deviceNode.addChild(portNode);
                } else {
                    rackNode.addChild(deviceNode);
                }
            }

        }
    }

    private void addZendNode(Long zenddeviceId, int type, LinkNode a, List<LinkNode> linkNodes) {
        if (zenddeviceId == null || zenddeviceId < 1L) return;
        LinkNode A = new LinkNode();
        A.setType(type);
        A = getNode(type, zenddeviceId);
        if (A == null) return;
        addZendNodef(zenddeviceId, type, A, linkNodes);
//        addZendNodef(Long.valueOf(A.getId()), 1, A, linkNodes);
//        addZendNodef(Long.valueOf(A.getId()), 2, A, linkNodes);
        LinkNode link = new LinkNode();
        link.setType(3);
        link.setA(a);
        link.setZ(A);
        link.setId(a.getKey() + "_" + A.getKey());
        link.setName(a.getKey() + "_" + A.getKey());
        link.setKey(a.getKey() + "_" + A.getKey());
        linkNodes.add(link);
        linkNodes.add(A);
    }

    /*创建节点*/
    public LinkNode getNode(int type, long zenddeviceId) {
        LinkNode A = new LinkNode();
        if (zenddeviceId == 0L) return null;
        if (type == 0) {
            IdcRack modelById = rackMapper.getModelById(Integer.valueOf(zenddeviceId + ""));
            if (modelById == null) return null;
            A.setId(modelById.getId().toString());
            A.setName(modelById.getName());
            A.setRoomId(Long.parseLong(modelById.getRoomid()));
            A.setKey("rack_" + A.getId());
            A.setTypeStr("rack_"+modelById.getBusinesstypeId());
        }
        if (type == 1) {
            IdcDevice modelById = idcDeviceMapper.getModelById(zenddeviceId);
            IdcRack idcRack = null;
            if (modelById.getRackId() != null) {
                idcRack = rackMapper.getModelById(modelById.getRackId().intValue());
            }
            if (modelById == null) return null;
            A.setId(modelById.getDeviceid().toString());
            A.setName(modelById.getName());
            A.setTypeStr("equipment");
            A.setRoomId(Long.parseLong(idcRack.getRoomid()));
            A.setKey("device_" + A.getId());
        }
        if (type == 2) {
            IdcConnector modelById = idcConnectorService.getModelById(zenddeviceId);
            if (modelById == null) return null;
            A.setId(modelById.getId().toString());
            A.setName(modelById.getName());
            A.setKey("idcconn_" + A.getId());
            A.setTypeStr("idcconn");
        }
        if (type == 3) {
            NetPort modelById = netPortMapper.getModelById(zenddeviceId);
            if (modelById == null) return null;
            A.setId(modelById.getPortid().toString());
            A.setName(modelById.getPortname());
            A.setKey("idcport_" + A.getId());
            A.setTypeStr("idcport");
        }
        A.setType(type);
        return A;
    }

    /*资源分配-资源树*/
    @Override
    public Map<String,Object> getDistributionTree(String type, String ids, Long roomId) {
        List<String> rackIdList = Arrays.asList(ids.split("-"));
        Map<String,Object> resultMap = new HashMap<>();
        List<LinkNode> nodes = new ArrayList<>();
        List<LinkNode> links = new ArrayList<>();
        LinkNode rootNode = getRootNode(ids, roomId);
        nodes.add(rootNode);
        for (String id : rackIdList) {
            //加入资源分配相关节点
            getDistributionNode(id,rootNode,nodes,links);
        }
        resultMap.put("nodes",nodes);
        resultMap.put("links",links);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> queryListByObjectMap(IdcLink idcLink) {
        return mapper.queryListByObjectMap(idcLink);
    }

    /*获取根机架组*/
    public LinkNode getRootNode(String ids, Long roomId) {
        LinkNode rootNode = new LinkNode();
        rootNode.setId(ids);
        rootNode.setName("racks");
        rootNode.setRoomId(roomId);
        rootNode.setKey("racks_" + ids);
        rootNode.setTypeStr("racks_group");
        return rootNode;
    }

    /*添加节点或连线*/
    public void addLinkNode(List<LinkNode> list,LinkNode node,String type){
        if("node".equals(type)){//节点
            boolean eqflag = false;
            boolean likeflag = false;
            int index = 0;
            for(int i=0;i<list.size();i++){
                LinkNode oldnode = list.get(i);
                if(list.get(i).getName().equals(node.getName())){//节点名称相同
                    likeflag = true;
                    index = i;
                    if(oldnode.getNodePorts()!=null&&oldnode.getNodePorts().size()>0){
                        if(oldnode.getNodePorts().toString().indexOf(node.getPortId())>-1){
                            eqflag = true;//节点存在
                            break;
                        }
                    }else{
                        eqflag = true;//节点存在
                        break;
                    }
                }
            }
            if(!eqflag&&!likeflag){//表示不存在此节点
                list.add(node);
            }else if(!eqflag&&likeflag){//表示存在此节点，但是端口不一样
                list.get(index).addNotePorts(node.getPortId());
            }
        }else{//连线
            boolean eqflag = false;
            boolean likeflag = false;
            int index = 0;
            for(int i=0;i<list.size();i++){
                LinkNode link = list.get(i);
                if(link.getName().equals(node.getName())){//链路名称相同
                    likeflag = true;
                    index = i;
                    if(link.getPortStrList()!=null&&link.getPortStrList().size()>0){
                        if(link.getPortStrList().toString().indexOf(node.getPortId())>-1){
                            eqflag = true;//链路存在
                            break;
                        }
                    }else{
                        eqflag = true;//链路存在
                        break;
                    }
                }
            }
            if(!eqflag&&!likeflag){//表示没有此链路
                list.add(node);
            }else if(!eqflag&&likeflag){//表示名称相同   但是链路不同，及端口之间对应不一样
                list.get(index).addPortStr(node.getPortId());
            }
        }
    }

    /*添加资源分配相关节点及连接关系*/
    public void getDistributionNode(String rackId,LinkNode rootNode,List<LinkNode> nodeLists,List<LinkNode> linkLists) {
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("rackId", rackId);
        mapQ.put("deviceId", "");
        mapQ.put("portId", "");
        //获取此机架所有链路信息
        List<IdcLink> linkList = mapper.queryListByRackIdA(mapQ);
        for (IdcLink idcLink : linkList) {
            if (idcLink.getZenddeviceId() == null || idcLink.getZenddeviceId() == 0) { //Z端设备为空  机架--机架
                //获取Z端机架节点
                LinkNode rackNode = getNode(0, idcLink.getZendrackId());
                if (rackNode == null) {
                    continue;
                }
                rackNode.setPortId(idcLink.getZendportId() == null ? null : idcLink.getZendportId().toString());
                rackNode.addNotePorts(idcLink.getZendportId() == null ? null : idcLink.getZendportId().toString());
                addLinkNode(nodeLists,rackNode,"node");
                rootNode.addChild(rackNode);
                addLinkNode(linkLists,new LinkNode(rootNode, rackNode),"link");
                //继续向下级找节点
                wLRackToOdfRack(nodeLists,linkLists,rackNode,idcLink);
            } else {//机架--设备
                //获取Z端设备节点
                LinkNode deviceNode = getNode(1, idcLink.getZenddeviceId());
                if (deviceNode == null) {
                    continue;
                }
                deviceNode.setPortId(idcLink.getZendportId() == null ? null : idcLink.getZendportId().toString());
                deviceNode.addNotePorts(idcLink.getZendportId() == null ? null : idcLink.getZendportId().toString());
                addLinkNode(nodeLists,deviceNode,"node");
                rootNode.addChild(deviceNode);
                addLinkNode(linkLists,new LinkNode(rootNode, deviceNode),"link");
                //若当前设备的网络层次为“核心”，则不再向下找下级节点
                NetDevice netDevice = netDeviceMapper.getModelById(idcLink.getZenddeviceId());
                if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                    deviceToDevice(nodeLists,linkLists,deviceNode,idcLink);
                }
            }
        }
    }

    /*Z端机架 继续去寻找架*/
    public void wLRackToOdfRack(List<LinkNode> nodeLists,List<LinkNode> linkLists,LinkNode rackNode,IdcLink idcLink) {
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("rackId", idcLink.getZendrackId());
        mapQ.put("deviceId", idcLink.getZenddeviceId());
        mapQ.put("portId", idcLink.getZendportId());
        //获取此机架所有链路信息
        List<IdcLink> linkListR = mapper.queryListByRackIdA(mapQ);
        for (IdcLink idcLinkR : linkListR) {
            if (idcLinkR.getZenddeviceId() == null || idcLinkR.getZenddeviceId() == 0) {//机架--机架
                LinkNode odfNodeR = getNode(0, idcLinkR.getZendrackId());
                if (odfNodeR == null) {
                    continue;
                }
                odfNodeR.setPortId(idcLinkR.getZendportId() == null ? null : idcLinkR.getZendportId().toString());
                odfNodeR.addNotePorts(idcLinkR.getZendportId() == null ? null : idcLinkR.getZendportId().toString());
                addLinkNode(nodeLists,odfNodeR,"node");
                rackNode.addChild(odfNodeR);
                //创建链接关系
                addLinkNode(linkLists,new LinkNode(rackNode, odfNodeR),"link");
                //迭代
                wLRackToOdfRack(nodeLists,linkLists,odfNodeR, idcLinkR);
            } else {//机架--设备
                //获取Z端设备节点
                LinkNode deviceNode = getNode(1, idcLinkR.getZenddeviceId());
                if (deviceNode == null) {
                    continue;
                }
                deviceNode.setPortId(idcLinkR.getZendportId() == null ? null : idcLinkR.getZendportId().toString());
                deviceNode.addNotePorts(idcLinkR.getZendportId() == null ? null : idcLinkR.getZendportId().toString());
                addLinkNode(nodeLists,deviceNode,"node");
                rackNode.addChild(deviceNode);
                addLinkNode(linkLists,new LinkNode(rackNode, deviceNode),"link");
                //若当前设备的网络层次为“核心”，则不再向下找下级节点
                NetDevice netDevice = netDeviceMapper.getModelById(idcLinkR.getZenddeviceId());
                if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                    deviceToDevice(nodeLists,linkLists,deviceNode,idcLinkR);
                }
            }

        }
    }

    /*设备到设备或机架*/
    public void deviceToDevice(List<LinkNode> nodeLists,List<LinkNode> linkLists,LinkNode deviceNode,IdcLink idcLink) {
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("rackId", idcLink.getZendrackId());
        mapQ.put("deviceId", idcLink.getZenddeviceId());
        mapQ.put("portId", idcLink.getZendportId());
        //获取此机架所有链路信息
        List<IdcLink> linkListD = mapper.queryListByRackIdA(mapQ);
        for (IdcLink idcLinkD : linkListD) {
            if (idcLinkD.getZenddeviceId() == null || idcLinkD.getZenddeviceId() == 0) {//Z端为机架  设备-机架
                LinkNode rackNodeD = getNode(0, idcLinkD.getZendrackId());
                if (rackNodeD == null) {
                    continue;
                }
                rackNodeD.setPortId(idcLinkD.getZendportId() == null ? null : idcLinkD.getZendportId().toString());
                rackNodeD.addNotePorts(idcLinkD.getZendportId() == null ? null : idcLinkD.getZendportId().toString());
                addLinkNode(nodeLists,rackNodeD,"node");
                deviceNode.addChild(rackNodeD);
                //创建连线
                addLinkNode(linkLists,new LinkNode(deviceNode, rackNodeD),"link");
                //继续向下级找节点
                wLRackToOdfRack(nodeLists,linkLists,rackNodeD,idcLinkD);
            } else {//Z端为设备 设备-设备
                //获取Z端设备
                LinkNode deviceNodeZ = getNode(1, idcLinkD.getZenddeviceId());
                if (deviceNodeZ == null) {
                    continue;
                }
                deviceNodeZ.setPortId(idcLinkD.getZendportId() == null ? null : idcLinkD.getZendportId().toString());
                deviceNodeZ.addNotePorts(idcLinkD.getZendportId() == null ? null : idcLinkD.getZendportId().toString());
                addLinkNode(nodeLists,deviceNodeZ,"node");
                deviceNode.addChild(deviceNodeZ);
                addLinkNode(linkLists,new LinkNode(deviceNode, deviceNodeZ),"link");
                //若当前设备的网络层次为“核心”，则不再向下找下级节点
                NetDevice netDevice = netDeviceMapper.getModelById(idcLinkD.getZenddeviceId());
                if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                    deviceToDevice(nodeLists,linkLists,deviceNodeZ,idcLinkD);
                }
            }
        }
    }

    /*获取链路信息*/
    @Override
    public List<Map<String, Object>> queryLinksByAZ(String aKey, String zKey, String name, String portStr,Long wlRackId) {
        List<Map<String, Object>> idcLinkList = null;
        //获取A端类型和ID
        String aType = aKey.split("_")[0];
        String aId = aKey.split("_")[1];
        //获取Z端类型和ID
        String zType = zKey.split("_")[0];
        String zId = zKey.split("_")[1];
        //定义查询MAP
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        mapQ.put("type", aType+"_"+zType);
        //获取AZ端端口通过_链接集合  替换掉所有的null
        if(portStr!=null&&!"".equals(portStr)&&!"null".equals(portStr)){
            List<String> portAZStrs = Arrays.asList(portStr.replaceAll("null","").split(","));
            mapQ.put("portAZStrs", portAZStrs);
        }
        if(wlRackId!=null&&!"".equals(wlRackId)&&!"null".equals(wlRackId)){
            mapQ.put("wlRackId", wlRackId);
        }
        if(aType!=null&&"racks".equals(aType)){//A端为机架组
            //机架组Ids
            List<String> racksIdsList = Arrays.asList(aId.split("-"));
            mapQ.put("racksIdsList", racksIdsList);
        }else{
            mapQ.put("aId", aId);
        }
        mapQ.put("zId",zId);
        idcLinkList = mapper.queryIdcLinksByAZMap(mapQ);
        return idcLinkList;
    }

    /*清楚链路关系时  清除与端子的绑定*/
    @Override
    @Transactional
    public void unBindConnectToLink(List list) throws Exception {
        //清除端子或端口状态
        List portIdList = new ArrayList();
        List conneterIdList = new ArrayList();
        List<IdcLink> linkList = mapper.queryLinkModelByIds(list);
        for(IdcLink link:linkList){
            if(link.getAendportId()!=null&&link.getAenddeviceId()!=null){//有设备  是端口
                portIdList.add(link.getAendportId());
            }else if(link.getAendportId()!=null&&link.getAenddeviceId()==null){//端子
                conneterIdList.add(link.getAendportId());
            }
            if(link.getZendportId()!=null&&link.getZenddeviceId()!=null){//有设备  是端口
                portIdList.add(link.getZendportId());
            }else if(link.getZendportId()!=null&&link.getZenddeviceId()==null){//端子
                conneterIdList.add(link.getZendportId());
            }
        }
        //端口先不处理
        //处理端子
        Map<String,Object> param = new HashMap<>();
        param.put("status",20);
        param.put("list",conneterIdList);
        idcConnectorService.updateStatusByConnIds(param);
        //清除链路
        mapper.deleteByList(list);
    }

    /*通过AZ端端口删除链路   如：352352_352352*/
    @Override
    public void deleteLinkByAZPortStrList(List list) throws Exception {
        mapper.deleteLinkByAZPortStrList(list);
    }

    /*跟据端子ID，获取上行和下行端口信息*/
    @Override
    public Map<String, Object> queryUpAndDownPortInfo(Long id) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> paramUp = new HashMap<>();
        Map<String,Object> paramDown = new HashMap<>();
        //获取上行端口
        List<Map<String, Object>> idcLinksUp = mapper.queryUpPortInfoByPortIdZ(id);
        if (idcLinksUp != null && idcLinksUp.size() > 0) {
            Map<String, Object> obj = idcLinksUp.get(0);
            if (obj.get("ADEVICENAME") != null && !"".equals(obj.get("ADEVICENAME"))) {//端口
                paramUp.put("obj", obj);
                paramUp.put("type", "netPort");
            } else {//端子
                paramUp.put("obj", obj);
                paramUp.put("type", "idcConnector");
            }
        }else{
            paramUp.put("obj", "");
            paramUp.put("type", "");
        }
        //获取下行端口
        List<Map<String, Object>> idcLinksDown = mapper.queryDownPortInfoByPortIdA(id);
        if (idcLinksDown != null && idcLinksDown.size() > 0) {
            Map<String, Object> obj = idcLinksDown.get(0);
            if (obj.get("ZDEVICENAME") != null && !"".equals(obj.get("ZDEVICENAME"))) {//端口
                paramDown.put("obj", obj);
                paramDown.put("type", "netPort");
            } else {//端子
                paramDown.put("obj", obj);
                paramDown.put("type", "idcConnector");
            }
        }else{
            paramDown.put("obj", "");
            paramDown.put("type", "");
        }
        map.put("upPort",paramUp);
        map.put("downPort",paramDown);
        return map;
    }

    /*资源树*/
    @Override
    public Map<String, Object> getRackResourceTree(Long rackId) {
        Map<String,Object> resultMap = new HashMap<>();
        List<LinkNode> nodes = new ArrayList<>();
        List<LinkNode> links = new ArrayList<>();
        /*获取当前机架节点*/
        LinkNode rootRackNodeRoot = getNode(0, rackId);
        nodes.add(rootRackNodeRoot);
        /*获取上行节点*/
        appendUpNode(nodes,links,rootRackNodeRoot);
        /*获取下行节点*/
        appendDownNode(nodes,links,rootRackNodeRoot,null);
        resultMap.put("nodes",nodes);
        resultMap.put("links",links);
        return resultMap;
    }
    /*获取上行节点*/
    public void appendUpNode(List<LinkNode> nodes,List<LinkNode> links,LinkNode rootNode){
//        IdcLink query = new IdcLink();
//        query.setZendrackId(Long.valueOf(rootNode.getId()));
//        List<IdcLink> linkList = mapper.queryListByObject(query);
        List<IdcLink> linkList = mapper.queryListByZendrackId(Long.valueOf(rootNode.getId()));
        if(linkList!=null&&linkList.size()>0){
            for(IdcLink idcLink:linkList){
                if(idcLink.getAenddeviceId()!=null&&!"".equals(idcLink.getAenddeviceId())){//A端为设备
                    //获取设备节点
                    LinkNode deviceNode = getNode(1,idcLink.getAenddeviceId());
                    if (deviceNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,deviceNode,"node");
                    rootNode.addChild(deviceNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(deviceNode,rootNode),"link");
                    //若当前设备的网络层次为“核心”，则不再向下找下级节点
                    NetDevice netDevice = netDeviceMapper.getModelById(idcLink.getAenddeviceId());
                    if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                        appendUpNodeDevice(nodes,links,deviceNode);
                    }
                }else{//A端为机架
                    //获取机架节点
                    LinkNode rackNode = getNode(0,idcLink.getAendrackId());
                    if (rackNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,rackNode,"node");
                    rootNode.addChild(rackNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(rackNode,rootNode),"link");
                    appendUpNode(nodes,links,rackNode);
                }
            }
        }
    }
    /*获取下行节点*/
    public void appendDownNode(List<LinkNode> nodes,List<LinkNode> links,LinkNode rootNode,Long wlRackId){
//        IdcLink query = new IdcLink();
//        query.setAendrackId(Long.valueOf(rootNode.getId()));
//        List<IdcLink> linkList = mapper.queryListByObject(query);
        Map<String,Object> query = new HashMap<>();
        query.put("arackId",rootNode.getId());
        //当为ODF架时，只查询其对应WL柜对应的ODF柜下的设备
        if(rootNode.getTypeStr()!=null&&"rack_df".equals(rootNode.getTypeStr())&&wlRackId!=null){
            query.put("wlRackId",wlRackId);
        }
        List<IdcLink> linkList = mapper.queryListByAendrackId(query);
        if(linkList!=null&&linkList.size()>0){
            for(IdcLink idcLink:linkList){
                if(idcLink.getZenddeviceId()!=null&&!"".equals(idcLink.getZenddeviceId())){//Z端为设备
                    //获取设备节点
                    LinkNode deviceNode = getNode(1,idcLink.getZenddeviceId());
                    if (deviceNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,deviceNode,"node");
                    rootNode.addChild(deviceNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(rootNode,deviceNode),"link");
                    //若当前设备的网络层次为“核心”，则不再向下找下级节点
                    NetDevice netDevice = netDeviceMapper.getModelById(idcLink.getZenddeviceId());
                    if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                        appendDownNodeDevice(nodes,links,deviceNode,wlRackId);
                    }
                }else{//Z端为机架
                    //获取机架节点
                    LinkNode rackNode = getNode(0,idcLink.getZendrackId());
                    if (rackNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,rackNode,"node");
                    rootNode.addChild(rackNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(rootNode,rackNode),"link");
                    if(rackNode.getTypeStr()!=null&&"rack_df".equals(rackNode.getTypeStr())){
                        wlRackId = idcLink.getAendrackId();
                    }else{
                        wlRackId = null;
                    }
                    appendDownNode(nodes,links,rackNode,wlRackId);
                }
            }
        }
    }
    public void appendUpNodeDevice(List<LinkNode> nodes,List<LinkNode> links,LinkNode deviceNode){
//        IdcLink query = new IdcLink();
//        query.setZenddeviceId(Long.valueOf(deviceNode.getId()));
//        List<IdcLink> linkList = mapper.queryListByObject(query);
        List<IdcLink> linkList = mapper.queryListByZenddeviceId(Long.valueOf(deviceNode.getId()));
        if(linkList!=null&&linkList.size()>0){
            for(IdcLink idcLink:linkList){
                if(idcLink.getAenddeviceId()!=null&&!"".equals(idcLink.getAenddeviceId())){//A端为设备
                    //获取设备节点
                    LinkNode deviceNodeA = getNode(1,idcLink.getAenddeviceId());
                    if (deviceNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,deviceNodeA,"node");
                    deviceNode.addChild(deviceNodeA);
                    //添加连线
                    addLinkNode(links,new LinkNode(deviceNodeA,deviceNode),"link");
                    //若当前设备的网络层次为“核心”，则不再向下找下级节点
                    NetDevice netDevice = netDeviceMapper.getModelById(idcLink.getAenddeviceId());
                    if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                        appendUpNodeDevice(nodes,links,deviceNodeA);
                    }
                }else{//A端为机架
                    //获取机架节点
                    LinkNode rackNode = getNode(0,idcLink.getAendrackId());
                    if (rackNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,rackNode,"node");
                    deviceNode.addChild(rackNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(rackNode,deviceNode),"link");
                    appendUpNode(nodes,links,rackNode);
                }
            }
        }
    }
    public void appendDownNodeDevice(List<LinkNode> nodes,List<LinkNode> links,LinkNode deviceNode,Long wlRackId){
//        IdcLink query = new IdcLink();
//        query.setAenddeviceId(Long.valueOf(deviceNode.getId()));
//        List<IdcLink> linkList = mapper.queryListByObject(query);
        List<IdcLink> linkList = mapper.queryListByAenddeviceId(Long.valueOf(deviceNode.getId()));
        if(linkList!=null&&linkList.size()>0){
            for(IdcLink idcLink:linkList){
                if(idcLink.getZenddeviceId()!=null&&!"".equals(idcLink.getZenddeviceId())){//Z端为设备
                    //获取设备节点
                    LinkNode deviceNodeZ = getNode(1,idcLink.getZenddeviceId());
                    if (deviceNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,deviceNodeZ,"node");
                    deviceNode.addChild(deviceNodeZ);
                    //添加连线
                    addLinkNode(links,new LinkNode(deviceNode,deviceNodeZ),"link");
                    //若当前设备的网络层次为“核心”，则不再向下找下级节点
                    NetDevice netDevice = netDeviceMapper.getModelById(idcLink.getZenddeviceId());
                    if (netDevice == null || !"核心".equals(netDevice.getNetworklayer())) {
                        appendDownNodeDevice(nodes,links,deviceNodeZ,null);
                    }
                }else{//Z端为机架
                    //获取机架节点
                    LinkNode rackNode = getNode(0,idcLink.getZendrackId());
                    if (rackNode == null) {
                        continue;
                    }
                    //添加节点
                    addLinkNode(nodes,rackNode,"node");
                    deviceNode.addChild(rackNode);
                    //添加连线
                    addLinkNode(links,new LinkNode(deviceNode,rackNode),"link");
                    if(rackNode.getTypeStr()!=null&&"rack_df".equals(rackNode.getTypeStr())){
                        wlRackId = idcLink.getAendrackId();
                    }else{
                        wlRackId = null;
                    }
                    appendDownNode(nodes,links,rackNode,wlRackId);
                }
            }
        }
    }
}
