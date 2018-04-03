package com.idc.model;

import utils.typeHelper.CharHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/6/2.
 */
public class LinkNode {
    private String Id;
    private String name;//节点名称
    private int type;//节点类型
    private String typeStr;//节点类型
    private String key;//one key
    private List<LinkNode> childs = new ArrayList<>();//type=='group'
    private LinkNode A;//type link
    private LinkNode Z;
    private String portId;//端子或端口ID
    private Long roomId;//节点所在机房ID
    private List<String> portStrList = new ArrayList<>();//这是链路端口的对应集合  如 ：234242_32424
    private List<String> nodePorts = new ArrayList<>();//这个是节点上所有的端口

    public LinkNode() {
    }

    public LinkNode(LinkNode a, LinkNode z) {
        this.A = a;
        this.Z = z;
        this.name = a.getName()+">>"+z.getName();
        this.key = a.getKey()+"_" +z.getKey();
        this.portId = a.getPortId()+"_"+z.getPortId();
        this.addPortStr(a.getPortId()+"_"+z.getPortId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
//        switch (type) {
//            case 0:
//                this.typeStr = "rack";
//                break;
//            case 1:
//                this.typeStr = "device";
//                break;
//            case 2:
//                this.typeStr = "idccconn";
//                break;
//            case 3:
//                this.typeStr = "idcport";
//                break;
//            case 4:
//                this.typeStr = "group";
//                break;
//            default:
//                this.typeStr = "";
//                break;
//        }
        this.type = type;
    }

    public String getTypeStr() {

        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<LinkNode> getChilds() {
        return childs;
    }

    public void setChilds(List<LinkNode> childs) {
        this.childs = childs;
    }

    public void addChild(LinkNode child) {
        this.childs.add(child);
    }

    public LinkNode getA() {
        return A;
    }

    public void setA(LinkNode a) {
        A = a;
    }

    public LinkNode getZ() {
        return Z;
    }

    public void setZ(LinkNode z) {
        Z = z;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<String> getPortStrList() {
        return portStrList;
    }

    public void setPortStrList(List<String> portStrList) {
        this.portStrList = portStrList;
    }

    public void addPortStr(String portStr){
        this.portStrList.add(portStr);
    }

    public List<String> getNodePorts() {
        return nodePorts;
    }

    public void setNodePorts(List<String> nodePorts) {
        this.nodePorts = nodePorts;
    }

    public void addNotePorts(String port){
        this.nodePorts.add(port);
    }
    @Override
    public int hashCode() {
        System.out.println(key + ":::::" + key.hashCode());
        return key.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof LinkNode))
            return false;
        LinkNode o = (LinkNode) other;
        return o.getKey().equals(getKey());
    }
}
