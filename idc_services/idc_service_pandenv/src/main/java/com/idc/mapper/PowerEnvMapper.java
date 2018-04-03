package com.idc.mapper;

import com.idc.bo.RoomPE;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mylove on 2017/7/17.
 */
public interface PowerEnvMapper {

    List<RoomPE> getPEByRoom(String startTime, String endTime, @Param("roomid") Long roomid);

    List<RoomPE> getLastPEByBuild(@Param("buildid") String buildid);

    List<RoomPE> getLastPEByRoom(@Param("roomid") String roomid);

    List<RoomPE> getPETopRoom(@Param("topN") Long topN);

    List<RoomPE> getRoomPEByBuildid(@Param("type")String type, @Param("buildid") String buildid);

    List<RoomPE> getHisPE(@Param("type")String type, @Param("startTime")String startTime,@Param("endTime") String endTime, @Param("roomid") Long roomid);

    List<RoomPE> getLastPeByRack();
}
