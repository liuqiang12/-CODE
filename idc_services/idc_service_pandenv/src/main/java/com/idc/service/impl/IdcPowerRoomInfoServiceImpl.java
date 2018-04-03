package com.idc.service.impl;


import com.idc.mapper.IdcPdfDayPowerInfoMapper;
import com.idc.mapper.IdcPowerRoomInfoMapper;
import com.idc.model.IdcPdfDayPowerInfo;
import com.idc.model.IdcPowerRoomInfo;
import com.idc.service.IdcPdfDayPowerInfoService;
import com.idc.service.IdcPowerRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 *
 */
@Service("idcPowerRoomInfoService")
public class IdcPowerRoomInfoServiceImpl extends SuperServiceImpl<IdcPowerRoomInfo, String> implements IdcPowerRoomInfoService {
    @Autowired
    private IdcPowerRoomInfoMapper mapper;


    @Override
    public List<IdcPowerRoomInfo> queryByTime(String roomid , String startTime, String endTime,String cyc) {
        return mapper.queryByTime(roomid,startTime,endTime,cyc);
    }
}
