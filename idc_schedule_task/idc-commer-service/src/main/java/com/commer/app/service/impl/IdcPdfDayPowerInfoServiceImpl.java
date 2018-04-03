package com.commer.app.service.impl;



import com.commer.app.base.SuperServiceImpl;
import com.commer.app.mapper.IdcPdfDayPowerInfoMapper;
import com.commer.app.mode.IdcPdfDayPowerInfo;
import com.commer.app.service.IdcPdfDayPowerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_PDF_DAY_POWER_INFO <br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Aug 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcPdfDayPowerInfoService")
public class IdcPdfDayPowerInfoServiceImpl extends SuperServiceImpl<IdcPdfDayPowerInfo, String> implements IdcPdfDayPowerInfoService {
    @Autowired
    private IdcPdfDayPowerInfoMapper mapper;


}
