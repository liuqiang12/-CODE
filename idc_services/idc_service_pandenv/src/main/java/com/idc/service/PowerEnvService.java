package com.idc.service;

import com.idc.bo.RoomPE;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
public interface PowerEnvService {
    /***
     * 获取机房用电信息
     *
     * @return
     */
    Map<Long, RoomPE> getPEByRoom(String startTime, String endTime);

    List<RoomPE> getPE(String startTime, String endTime);

    /***
     * 获取机楼的最近一天的能耗数据
     * 如果build 为空 获取所有
     *
     * @param buildid
     * @return
     */
    List<RoomPE> getLastPEByBuild(String buildid);

    Map<Long, RoomPE> getLastPEByBuildToMap(String buildid);

    /***
     * 获取机房的最近一天的能耗数据
     *
     * @param roomid
     * @return
     */
    List<RoomPE> getLastPEByRoom(String roomid);


    List<RoomPE> getPETopRoom(Long TopN);

    Map<Long, RoomPE> getLastPEByRoomToMap(String roomid);

    List<RoomPE> getRoomPEByBuildid(String type, String buildid);

    List<RoomPE> getHisPE(String type, String roomid);

    /***
     * 获取机架最新PE
     *
     * @return
     */
    Map<String, RoomPE> getLastPEMap();
}
