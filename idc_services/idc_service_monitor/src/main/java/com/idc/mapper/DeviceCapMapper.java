package com.idc.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/8/28.
 */
public interface DeviceCapMapper {

    public List<Map<String, Object>> getHis(@Param("type")String type, @Param("routid")String deviceid,@Param("startTime") String startTime, @Param("endTime")String endTime);

    long getMaxTaskId();

    void addTask(@Param("taskid")long taskid, @Param("taskname")String taskName);

    void addTaskObj(@Param("id")long id,@Param("taskid")long taskid, @Param("deviceid")Long deviceid);

    long getMaxTempTaskProId();
}
