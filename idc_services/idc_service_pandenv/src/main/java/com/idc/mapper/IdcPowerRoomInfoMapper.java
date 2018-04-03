package com.idc.mapper;

import com.idc.model.IdcPdfDayPowerInfo;
import com.idc.model.IdcPowerRoomInfo;
import org.apache.ibatis.annotations.Param;
import system.data.supper.mapper.SuperMapper;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface IdcPowerRoomInfoMapper extends SuperMapper<IdcPowerRoomInfo, String> {

    List<IdcPowerRoomInfo> queryByTime(@Param("room_code")String roomid, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("cyc")String cyc);
}
