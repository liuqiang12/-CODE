package com.idc.model;

import java.io.Serializable;

/**
 * Created by mylove on 2017/8/9.
 * 操作类型
 */
public enum OperaType implements Serializable {
    AllC(1, "分配"), RECOVERY(2, "回收");

    private OperaType(int _index, String _value) {
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

    public static OperaType getOpValue(int i) {
        switch (i) {
            case 1:
                return OperaType.AllC;
            case 2:
                return OperaType.RECOVERY;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static void main(String[] args) {

    }
}
