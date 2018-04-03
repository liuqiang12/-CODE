package com.idc.service.impl;


import com.idc.bo.IdcPdfDayPowerInfoVo;
import com.idc.mapper.IdcPdfDayPowerInfoMapper;
import com.idc.model.IdcPdfDayPowerInfo;
import com.idc.service.IdcPdfDayPowerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_PDF_DAY_POWER_INFO <br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcPdfDayPowerInfoService")
public class IdcPdfDayPowerInfoServiceImpl extends SuperServiceImpl<IdcPdfDayPowerInfo, String> implements IdcPdfDayPowerInfoService {
    @Autowired
    private IdcPdfDayPowerInfoMapper mapper;

    @Override
    public List<IdcPdfDayPowerInfo> queryListByTime(String roomcode, String mcb, String pdf) {
        return mapper.queryListByTime(roomcode, mcb, pdf);
    }

    @Override
    public List<IdcPdfDayPowerInfoVo> getRoomPowers(long roomid) {
        return mapper.getPowerDay(roomid);
    }

    @Override
    public List<IdcPdfDayPowerInfoVo> getHisMonth(long rackid) {
        return mapper.getHisMonth(rackid);
    }

    @Override
    public List<IdcPdfDayPowerInfoVo> getRoomPowers(Long roomid, Long userid, String dateTime) {
        return mapper.getPowerByDate(roomid, userid, dateTime);
    }

    @Override
    public List<IdcPdfDayPowerInfoVo> getRoomPowers(Long roomid, Long userid, String startTime, String endTime) {
        List<IdcPdfDayPowerInfoVo> powerByDateRange = mapper.getPowerByDateRange(roomid, userid, startTime, endTime);
        Map<String, IdcPdfDayPowerInfoVo> mcbinfo = new HashMap<>();
        IdcPdfDayPowerInfoVo tmp;
        for (IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo : powerByDateRange) {
            String key = idcPdfDayPowerInfoVo.getIdcPdfCode() + "_" + idcPdfDayPowerInfoVo.getIdcMcbCode();
            tmp = mcbinfo.get(key);
            if (tmp == null) {
                mcbinfo.put(key, idcPdfDayPowerInfoVo);
            } else {
                if (tmp.getIdcCreateTime().before(idcPdfDayPowerInfoVo.getIdcCreateTime())) {
                    idcPdfDayPowerInfoVo.setIdcBeforeDiff(tmp.getIdcBeforeDiff() + idcPdfDayPowerInfoVo.getIdcBeforeDiff());
                    mcbinfo.put(key, idcPdfDayPowerInfoVo);
                } else {
                    tmp.setIdcBeforeDiff(tmp.getIdcBeforeDiff() + idcPdfDayPowerInfoVo.getIdcBeforeDiff());
                }
            }
        }
        powerByDateRange=new ArrayList<>();
        for (Map.Entry<String, IdcPdfDayPowerInfoVo> stringIdcPdfDayPowerInfoVoEntry : mcbinfo.entrySet()) {
            powerByDateRange.add(stringIdcPdfDayPowerInfoVoEntry.getValue());
        }
        return powerByDateRange;
    }
}
