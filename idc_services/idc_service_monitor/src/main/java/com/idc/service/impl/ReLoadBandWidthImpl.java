package com.idc.service.impl;

import com.idc.mapper.IdcLocationMapper;
import com.idc.model.IdcLocation;
import com.idc.model.IdcLocationCount;
import com.idc.model.NetBusiPortFlow;
import com.idc.service.BusPortService;
import com.idc.service.ReLoadInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReLoadBandWidthImpl extends ReLoadInterface {
    @Autowired
    private IdcLocationMapper idcLocationMapper;
    @Autowired
    private BusPortService busPortService;

    @Override
    public void reloadCount(Long localId, IdcLocationCount count) {
        logger.info("数据中心带宽");
        IdcLocation location = idcLocationMapper.getModelById(localId.intValue());
        List<NetBusiPortFlow> maps = busPortService.queryListPage("出口", null);
        if(maps!=null&&maps.size()>0){
            for(NetBusiPortFlow portFlow:maps){
                if("DC-280-04".equals(location.getCode())&&"西云总出口-2T".equals(portFlow.getPortname())){//西云：西云总出口-2T
                    count.setUsedbandwidth((float)portFlow.getOutflowMbps());
                    break;
                }else if("DC-280-03".equals(location.getCode())&&"西区IDC总出口 3000G(含国干，新省干)".equals(portFlow.getPortname())){//西区：西区IDC总出口 3000G(含国干，新省干)
                    count.setUsedbandwidth((float)portFlow.getOutflowMbps());
                    break;
                }else if("DC-280-02".equals(location.getCode())&&"东区IDC出口总量-2000G".equals(portFlow.getPortname())){//东区：东区IDC出口总量-2000G
                    count.setUsedbandwidth((float)portFlow.getOutflowMbps());
                    break;
                }
            }
        }
    }
}
