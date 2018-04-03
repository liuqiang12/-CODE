package com.idc.mapper;

import com.idc.model.RmMaterialType;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>RM_MATERIAL_TYPE:物资类别表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface RmMaterialTypeMapper extends SuperMapper<RmMaterialType, String> {

    /*获取满足条件的所有物资类别  map*/
    List<Map<String, Object>> queryListMap(Map<String, Object> map);

    /*获取满足条件的所有物资类别  分页 map*/
    List<Map<String, Object>> queryListPageMap(PageBean<T> page);

    /*获取所有物资类型*/
    List<Map<String, Object>> queryAllMaterialTypeModel();
}

 