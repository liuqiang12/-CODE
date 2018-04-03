package com.idc.service.impl;


import com.idc.mapper.IdcOuterCodeMapper;
import com.idc.model.IdcOuterCode;
import com.idc.service.IdcOuterCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.SuperServiceImpl;

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
@Service("idcOuterCodeService")
public class IdcOuterCodeServiceImpl extends SuperServiceImpl<IdcOuterCode, String> implements IdcOuterCodeService {
    @Autowired
    private IdcOuterCodeMapper mapper;


    @Override
    public IdcOuterCode getBySysCode(String s) {
        return mapper.getBySysCode(s);
    }

    @Override
    public IdcOuterCode getByOuterCode(String s) {
        return mapper.getByOuterCode(s);
    }
}
