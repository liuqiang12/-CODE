package com.idc.service;

import com.idc.model.ExecuteResult;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/8/28.
 */
public interface DeviceCapService {

    /**
     * @param type
     * @param deviceid
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getHis(String type, String deviceid, String startTime, String endTime);

    ExecuteResult syncDevice(Long deviceid);
}
