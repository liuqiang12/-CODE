package com.commer.app.service.impl;



import com.commer.app.base.SuperServiceImpl;
import com.commer.app.mapper.IdcPowerRoomInfoMapper;
import com.commer.app.mode.IdcPowerBuidingInfo;
import com.commer.app.service.IdcPowerBuidingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 
 */
@Service("idcPowerBuidingInfoService")
public class IdcPowerBuidingInfoServiceImpl extends SuperServiceImpl<IdcPowerBuidingInfo, String> implements IdcPowerBuidingInfoService {
    @Autowired
    private IdcPowerRoomInfoMapper mapper;


}
