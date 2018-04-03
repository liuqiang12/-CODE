package com.idc.model;

import java.io.Serializable;

/**
 * Created by mylove on 2017/8/9.
 */
public enum ResourceType implements Serializable {
    BUILDING(1, "机楼"), ROOM(2, "机房"),
    RACK(3, "机楼"), DEVICE(4, "设备"),
    BOADWIDTH(5, "带宽"), PORT(6, "端口"),
    SUBNET(7, "网段"), IP(8, "IP"),
    ODF(9, "ODF"), PDF(10, "PDF"), OTHER(11, "其他");

    private ResourceType(int _index, String _value) {
        this.value = _value;
        this.index = _index;
    }

    private String value;
    private int index;

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public static ResourceType getOpValue(int i) {
        switch (i) {
            case 1:
                return ResourceType.BUILDING;
            case 2:
                return ResourceType.ROOM;
            case 3:
                return ResourceType.RACK;
            case 4:
                return ResourceType.DEVICE;
            case 5:
                return ResourceType.BOADWIDTH;
            case 6:
                return ResourceType.PORT;
            case 7:
                return ResourceType.SUBNET;
            case 8:
                return ResourceType.IP;
            case 9:
                return ResourceType.ODF;
            case 10:
                return ResourceType.PDF;
            case 11:
                return ResourceType.OTHER;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static void main(String[] args) {
        String s = ResourceType.PDF.toString();
        System.out.println(s);
    }
}
