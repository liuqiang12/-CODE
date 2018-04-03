package com.idc.service;

import com.idc.model.RmTmpRegister;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>RM_TMP_REGISTER:登录人员表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmTmpRegisterService extends SuperService<RmTmpRegister, String> {

    /*获取满足条件的所有登录人员  map*/
    List<Map<String, Object>> queryListMap(Map<String, Object> map);

    /*获取满足条件的所有登录人员  分页 map*/
    List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param);

    /*通过人员进出申请单ID获取进出人员列表*/
    List<RmTmpRegister> queryRmTmpRegisterListByRmTmpInOutFormId(String rmTmpInOutFormId);

    /*通过申请单ID删除进出人员信息*/
    void deleteRmTmpRegistersByRmTmpInOutFormId(String rmTmpInOutFormId) throws Exception;

    /*通过申请单IDS删除进出人员信息*/
    void deleteByRmTmpInOutFormIds(List<String> list) throws Exception;

}
