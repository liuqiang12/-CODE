package com.idc.service.impl;

import com.idc.mapper.RmDevListMapper;
import com.idc.model.RmDevList;
import com.idc.service.RmDevListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>RM_DEV_LIST:设备清单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmDevListService")
public class RmDevListServiceImpl extends SuperServiceImpl<RmDevList, String> implements RmDevListService {
    @Autowired
    private RmDevListMapper rmDevListMapper;

    /*通过设备进出申请单ID获取具体的进出设备信息列表*/
    @Override
    public List<RmDevList> queryRmDevListListByRmDevInOutFormId(String rmDevInOutFormId) {
        return rmDevListMapper.queryRmDevListListByRmDevInOutFormId(rmDevInOutFormId);
    }

    /*通过设备出入申请单ID删除其对应的进出设备*/
    @Override
    public void deleteRmDevListByRmDevInOutFormId(String rmDevInOutFormId) throws Exception {
        rmDevListMapper.deleteRmDevListByRmDevInOutFormId(rmDevInOutFormId);
    }

    /*通过设备出入申请单IDS删除其对应的进出设备*/
    @Override
    public void deleteRmDevListByRmDevInOutFormIds(List<String> list) throws Exception {
        rmDevListMapper.deleteRmDevListByRmDevInOutFormIds(list);
    }
}
