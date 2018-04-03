package com.idc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mylove on 2017/8/8.
 */
public class DataView {
    Integer localId;
    String localName;
    /***
     * 值MAP
     */
    Map<String, Integer> dataMap = new HashMap<>();

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public void addKey(String key, Integer value) {
        this.dataMap.put(key, value);
    }

    public Integer getValue(String key) {
        return this.dataMap.get(key);
    }

    public Map<String, Integer> getDataMap() {
        return dataMap;
    }
}
