package com.idc.mapper;

import com.idc.model.RmMaterialDetail;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>RM_MATERIAL_DETAIL:物资出入库详情表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmMaterialDetailMapper extends SuperMapper<RmMaterialDetail, String> {

    /*获取满足条件的所有物资出入申请单  map*/
    List<Map<String, Object>> queryListMap(Map<String, Object> map);

    /*获取满足条件的所有物资出入申请单  分页 map*/
    List<Map<String, Object>> queryListPageMap(PageBean<T> page);
}

 