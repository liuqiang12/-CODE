package com.idc.service.impl;


import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.IdcPowerBuidingInfoMapper;
import com.idc.model.IdcPowerBuidingInfo;
import com.idc.model.IdcPowerRoomInfo;
import com.idc.service.IdcPowerBuidingInfoService;
import com.idc.service.IdcPowerRoomInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service("idcPowerBuidingInfoService")
public class IdcPowerBuidingInfoServiceImpl extends SuperServiceImpl<IdcPowerBuidingInfo, String> implements IdcPowerBuidingInfoService {
    @Autowired
    private IdcPowerBuidingInfoMapper mapper;
    @Autowired
    private IdcPowerRoomInfoService idcPowerRoomInfoService;

    @Override
    public List<IdcPowerBuidingInfo> queryByTime(String localid, String startTime, String endTime, String cyc) {
        return mapper.queryByTime(localid, startTime, endTime, cyc);
    }

}
