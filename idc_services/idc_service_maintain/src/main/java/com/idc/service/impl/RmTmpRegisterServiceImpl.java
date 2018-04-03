package com.idc.service.impl;

import com.idc.mapper.RmTmpRegisterMapper;
import com.idc.model.RmTmpRegister;
import com.idc.service.RmTmpRegisterService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>RM_TMP_REGISTER:登录人员表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmTmpRegisterService")
public class RmTmpRegisterServiceImpl extends SuperServiceImpl<RmTmpRegister, String> implements RmTmpRegisterService {
    @Autowired
    private RmTmpRegisterMapper rmTmpRegisterMapper;

    @Override
    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return rmTmpRegisterMapper.queryListMap(map);
    }

    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return rmTmpRegisterMapper.queryListPageMap(page);
    }

    /*通过人员进出申请单ID获取进出人员列表*/
    @Override
    public List<RmTmpRegister> queryRmTmpRegisterListByRmTmpInOutFormId(String rmTmpInOutFormId) {
        return rmTmpRegisterMapper.queryRmTmpRegisterListByRmTmpInOutFormId(rmTmpInOutFormId);
    }

    /*通过申请单ID删除进出人员信息*/
    @Override
    public void deleteRmTmpRegistersByRmTmpInOutFormId(String rmTmpInOutFormId) throws Exception {
        rmTmpRegisterMapper.deleteRmTmpRegistersByRmTmpInOutFormId(rmTmpInOutFormId);
    }

    /*通过申请单IDS删除进出人员信息*/
    @Override
    public void deleteByRmTmpInOutFormIds(List<String> list) throws Exception {
        rmTmpRegisterMapper.deleteByRmTmpInOutFormIds(list);
    }
}
