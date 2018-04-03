package com.idc.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.inter.DataSource;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.DcimRackModelMapper;
import com.idc.model.DcimRackModel;
import com.idc.service.DcimRackModelService;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>DCIM_RACK_MODEL:机架规格<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("dcimRackModelService")
public class DcimRackModelServiceImpl extends SuperServiceImpl<DcimRackModel, Long> implements DcimRackModelService {
    @Autowired
    private DcimRackModelMapper mapper;
    /**
     *   Special code can be written here
     */
}
