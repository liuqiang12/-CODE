package com.idc.service;

import com.idc.mapper.IdcRackMapper;
import com.idc.model.IdcLocationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mylove on 2017/8/10.
 */
@Service
public class ReLoadRackImpl extends ReLoadInterface {

    @Autowired
    private IdcRackMapper idcRackMapper;

    @Override
    public void reloadCount(Long localId, IdcLocationCount count) {
        logger.info("机架统计");
        //客户架\网络架 总数及使用率
        Map<String, Object> map = idcRackMapper.getCustomerRackNum(localId);
        Long rackNum = map.get("RACKNUM") == null ? 0 : Long.parseLong(map.get("RACKNUM").toString());
        float rackusage = Float.parseFloat(map.get("RACKUSAGE").toString());
        Long usedRackNum = map.get("USEDRACKNUM") == null ? 0 : Long.parseLong(map.get("USEDRACKNUM").toString());
        count.setRackusage(rackusage);
        count.setRacktotal(rackNum);
        count.setUsedracknum(usedRackNum);
    }
}
