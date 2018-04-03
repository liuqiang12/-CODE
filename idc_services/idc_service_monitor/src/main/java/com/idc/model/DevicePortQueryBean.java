package com.idc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/9/19.
 */
public class DevicePortQueryBean implements Serializable {
    private String deviceid;
    private List<String> ports = new ArrayList<>();

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void addPorts(String port) {
        this.ports.add(port);
    }

    public void addAllPorts(List<String> ports) {
        this.ports.addAll(ports);
    }
}
