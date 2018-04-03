package com.idc.vo;

import com.idc.model.NetPortFlow;
import com.idc.model.NetPortFlowCap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/12/26.
 */
public class NetCapPortDetail implements Serializable {
    private NetPortFlowCap netCapPort;
    private List<NetPortFlow> details = new ArrayList<>();

    public NetPortFlowCap getNetCapPort() {
        return netCapPort;
    }

    public void setNetCapPort(NetPortFlowCap netCapPort) {
        this.netCapPort = netCapPort;
    }

    public List<NetPortFlow> getDetails() {
        return details;
    }

    public void setDetails(List<NetPortFlow> details) {
        this.details = details;
    }
}
