package com.idc.service;

import com.idc.mapper.NetPortMapper;
import com.idc.model.IdcLocationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service
public class ReLoadPortImpl extends ReLoadInterface {
    @Autowired
    private NetPortMapper netPortMapper;

    @Override
    public void reloadCount(Long localId, IdcLocationCount count) {
        logger.info("核心/接入端口");
        //客户架总数及使用率
        Map<String, Object> map = netPortMapper.getNetPortNum(localId);
        Long husedPortNum = map.get("HUSEDPORTNUM") == null ? 0 : Long.parseLong(map.get("HUSEDPORTNUM").toString());
        Long hportNum = map.get("HPORTNUM") == null ? 0 : Long.parseLong(map.get("HPORTNUM").toString());
        float hportusge = map.get("HPORTUSGE") == null ? 0 : Float.parseFloat(map.get("HPORTUSGE").toString());
        Long jusedPortNum = map.get("JUSEDPORTNUM") == null ? 0 : Long.parseLong(map.get("JUSEDPORTNUM").toString());
        Long jportNum = map.get("JPORTNUM") == null ? 0 : Long.parseLong(map.get("JPORTNUM").toString());
        float jportusge = map.get("JPORTUSGE") == null ? 0 : Float.parseFloat(map.get("JPORTUSGE").toString());
        count.setCoreporttotal(hportNum);
        count.setUsedcoreport(husedPortNum);
        count.setCoreportusage(hportusge);
        count.setAccessporttotal(jportNum);
        count.setUsedaccessport(jusedPortNum);
        count.setAccessportusage(jportusge);
    }
}
