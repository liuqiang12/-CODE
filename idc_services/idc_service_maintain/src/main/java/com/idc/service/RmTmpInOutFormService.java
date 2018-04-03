package com.idc.service;

import com.idc.model.RmTmpInOutForm;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>RM_TMP_IN_OUT_FORM:临时出入申请单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 31,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmTmpInOutFormService extends SuperService<RmTmpInOutForm, String>{

	/*获取满足条件的所有临时出入申请单  map*/
	List<Map<String, Object>> queryListMap(Map<String, Object> map);

	/*获取满足条件的所有临时出入申请单  分页 map*/
	List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param);

    /*新增或修改申请单信息 id为空则新增  否则修改*/
	void saveOrUpdateRmInOutFormInfo(String id, RmTmpInOutForm rmTmpInOutForm, String userName) throws Exception;

    /*通过申请单IDS删除申请单，并删除其对应的出入人员信息*/
    void deleteRmTmpInOutFormAndRmTmpRegisterByRmTmpInOutFormIds(List<String> list) throws Exception;
}
