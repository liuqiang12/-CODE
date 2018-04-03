package com.idc.service;

import com.idc.model.RmDevInOutForm;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>RM_DEV_IN_OUT_FORM:设备出入申请单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmDevInOutFormService extends SuperService<RmDevInOutForm, String> {

    /*获取满足条件的设备出入申请单  map*/
    List<Map<String, Object>> queryListMap(Map<String, Object> map);

    /*获取满足条件的设备出入申请单  分页 map*/
    List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param);

    /*新增或修改设备出入申请单信息   id为0则新增  否则修改*/
    void saveOrUpdateRmDevInOutFormInfo(String id, RmDevInOutForm rmDevInOutForm, String userName) throws Exception;

    /*删除设备进场申请单信息，并一并删除与其对应的具体设备信息*/
    void deleteRmDevInOutFormAndRmDevListByRmDevInOutFormIds(List<String> list) throws Exception;

}
