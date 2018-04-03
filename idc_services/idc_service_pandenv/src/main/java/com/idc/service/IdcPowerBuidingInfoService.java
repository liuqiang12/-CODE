package com.idc.service;

import com.idc.model.IdcPowerBuidingInfo;
import com.idc.model.IdcPowerRoomInfo;
import system.data.supper.service.SuperService;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcPowerBuidingInfoService extends SuperService<IdcPowerBuidingInfo, String> {


    public List<IdcPowerBuidingInfo> queryByTime(String localid, String startTime, String endTime, String cyc);
}
