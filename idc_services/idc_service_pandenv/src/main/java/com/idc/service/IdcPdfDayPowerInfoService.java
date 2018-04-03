package com.idc.service;

import com.idc.bo.IdcPdfDayPowerInfoVo;
import com.idc.model.IdcPdfDayPowerInfo;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcPdfDayPowerInfoService extends SuperService<IdcPdfDayPowerInfo, String> {


    List<IdcPdfDayPowerInfo> queryListByTime(String roomcode, String mcb, String pdf);

    List<IdcPdfDayPowerInfoVo> getRoomPowers(long room);

    List<IdcPdfDayPowerInfoVo> getHisMonth(long rackid);

    List<IdcPdfDayPowerInfoVo> getRoomPowers(Long roomid, Long userid, String dateTime);

    List<IdcPdfDayPowerInfoVo> getRoomPowers(Long roomid, Long userid, String startTime, String endTime);
}
