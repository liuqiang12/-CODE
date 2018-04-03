package com.idc.service;

import com.idc.mapper.IdcIpinfoMapper;
import com.idc.model.IdcLocation;
import com.idc.model.IdcLocationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/8/10.
 */
@Service
public class ReLoadIPImpl extends ReLoadInterface {
    @Autowired
    private IdcIpinfoMapper idcIpinfoMapper;

    @Override
    public void reloadCount(Long localId, IdcLocationCount count) {
        Map<String, Long> map = idcIpinfoMapper.countUsed(localId);
        if (map != null) {
            Long total = new BigDecimal(String.valueOf(map.get("TOTAL"))).longValue();
            Long used = new BigDecimal(String.valueOf(map.get("USED"))).longValue();
            if (total != null && used != null) {
                count.setIptotal(total);
                if(total>0){
                    count.setIpusage(used * 100 / Float.parseFloat(total.toString()));
                }
                count.setUsedipnum(used);
            }
        }
    }

    @Override
    public void reloadCount(Long localId) {
        Map<String, Long> map = idcIpinfoMapper.countUsed(localId);
        IdcLocationCount count = idcLocationCountMapper.getModelById(localId);
        if (map != null && count != null) {
            Long total = new BigDecimal(String.valueOf(map.get("TOTAL"))).longValue();
            Long used = new BigDecimal(String.valueOf(map.get("USED"))).longValue();
            if (total != null && used != null) {
                count.setIptotal(total);
                if(total>0){
                    count.setIpusage(Float.valueOf(used) / total * 100f);
                }
            }
            try {
                idcLocationCountMapper.updateByObject(count);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("更新数据中心" + count.getId() + "统计信息失败", e);
            }
        }
    }

    @Override
    public void reloadCount() {
        List<IdcLocation> idcLocations = idcLocationMapper.queryList();
        for (IdcLocation idcLocation : idcLocations) {
            reloadCount(Long.parseLong(idcLocation.getId().toString()));
        }
    }
}
