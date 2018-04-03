package com.idc.service;

import com.idc.model.IdcPowerRoomInfo;
import system.data.supper.service.SuperService;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcPowerRoomInfoService extends SuperService<IdcPowerRoomInfo, String> {

    /****
     *
     * @param roomid
     * @param startTime 不传开始时间 就只获取一天的数据
     * @param endTime
     * @param cyc day or month
     * @return
     */
    List<IdcPowerRoomInfo> queryByTime(String roomid , String startTime, String endTime,String cyc);
}
