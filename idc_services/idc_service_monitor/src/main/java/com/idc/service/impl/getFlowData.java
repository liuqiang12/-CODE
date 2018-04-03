package com.idc.service.impl;

import com.idc.mapper.NetPortFlowMapper;
import com.idc.model.NetPortFlow;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by mylove on 2017/11/21.
 */
public class getFlowData implements Callable<List<NetPortFlow>> {
    private NetPortFlowMapper flowMapper;
    private List<String> portids = new ArrayList<>();
    private String startTime;
    private String endTime;
    private String cyc;

    public getFlowData(NetPortFlowMapper flowMapper,List<String> portids, String startTime, String endTime, String cyc) {
        this.flowMapper=flowMapper;
        this.portids = portids;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cyc = cyc;
    }

    @Override
    public List<NetPortFlow> call() throws Exception {
        List<NetPortFlow> portFlowDetail = flowMapper.getPortFlowDetail(portids, startTime, endTime, cyc);
        return portFlowDetail;
    }
}
