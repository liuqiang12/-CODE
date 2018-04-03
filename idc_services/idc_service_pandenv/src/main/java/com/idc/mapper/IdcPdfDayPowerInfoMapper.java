package com.idc.mapper;

import com.idc.bo.IdcPdfDayPowerInfoVo;
import com.idc.model.IdcPdfDayPowerInfo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcPdfDayPowerInfoMapper extends SuperMapper<IdcPdfDayPowerInfo, String> {
    public List<IdcPdfDayPowerInfo> queryListByTime(@Param("idc_room_code") String idc_room_code, @Param("mcb") String mcb, @Param("mcb") String pdf);

    List<IdcPdfDayPowerInfoVo> getPowerDay(long roomid);

    List<IdcPdfDayPowerInfoVo> getHisMonth(long rackid);

    List<IdcPdfDayPowerInfoVo> getHisMonthBtTime(@Param("rackid")long rackid,@Param("startTime")String startTime,@Param("endTime")String endTime);

    List<IdcPdfDayPowerInfoVo> getPowerByDate(@Param("roomid")Long roomid, @Param("userid")Long userid, @Param("dateTime")String dateTime);

    List<IdcPdfDayPowerInfoVo> getPowerByDateRange(@Param("roomid")Long roomid, @Param("userid")Long userid, @Param("startTime")String startTime, @Param("endTime")String endTime);
}
