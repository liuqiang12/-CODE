package com.idc.service.impl;

import com.idc.mapper.DeviceCapMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcDevice;
import com.idc.service.DeviceCapService;
import com.idc.service.IdcDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/8/28.
 */
@Service
public class DeviceCapImplservice implements DeviceCapService {
    @Autowired
    private IdcDeviceService idcDeviceService;
    @Autowired
    private DeviceCapMapper deviceCapMapper;
    /*@Autowired
    private DataSource dataSource;*/
    private static final Logger logger = LoggerFactory.getLogger(DeviceCapImplservice.class);

    @Override
    public List<Map<String, Object>> getHis(String type, String deviceid, String startTime, String endTime) {
        return deviceCapMapper.getHis(type, deviceid, startTime, endTime);
    }

    @Override
    @Transactional
    public ExecuteResult syncDevice(Long deviceid) {
        ExecuteResult executeResult = new ExecuteResult();
        if (deviceid == null || deviceid <= 0) {
            executeResult.setMsg("没有找到设备");
            return executeResult;
        }
        IdcDevice modelById = idcDeviceService.getModelById(deviceid);
        if (modelById == null) {
            executeResult.setMsg("没有找到设备");
            return executeResult;
        }
        try {
            long taskid = deviceCapMapper.getMaxTaskId();
            taskid += 1;
            deviceCapMapper.addTask(taskid, "资源采集" + deviceid);
            long proId = deviceCapMapper.getMaxTempTaskProId() + 1;
            deviceCapMapper.addTaskObj(proId, taskid, deviceid);
            executeResult.setState(true);
        } catch (Exception e) {
            logger.error("添加任务失败，{}", deviceid);
            executeResult.setMsg("添加失败");
        }
        return executeResult;
    }
}
