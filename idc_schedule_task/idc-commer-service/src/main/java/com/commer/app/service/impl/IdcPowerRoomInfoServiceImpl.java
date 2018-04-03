package com.commer.app.service.impl;


import com.commer.app.base.SuperService;
import com.commer.app.base.SuperServiceImpl;
import com.commer.app.mapper.IdcPowerRoomInfoMapper;
import com.commer.app.mode.IdcPowerRoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service("idcPowerRoomInfoService")
public class IdcPowerRoomInfoServiceImpl extends SuperServiceImpl<IdcPowerRoomInfo, String> implements SuperService<IdcPowerRoomInfo, String> {
    @Autowired
    private IdcPowerRoomInfoMapper mapper;


}
