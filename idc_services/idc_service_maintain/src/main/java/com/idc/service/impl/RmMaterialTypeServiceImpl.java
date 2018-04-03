package com.idc.service.impl;

import com.idc.mapper.RmMaterialTypeMapper;
import com.idc.model.RmMaterialType;
import com.idc.service.RmMaterialTypeService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>RM_MATERIAL_TYPE:物资类别表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmMaterialTypeService")
public class RmMaterialTypeServiceImpl extends SuperServiceImpl<RmMaterialType, String> implements RmMaterialTypeService {
    @Autowired
    private RmMaterialTypeMapper rmMaterialTypeMapper;

    /*获取满足条件的所有物资类别  map*/
    @Override
    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return rmMaterialTypeMapper.queryListMap(map);
    }

    /*获取满足条件的所有物资类别  分页 map*/
    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return rmMaterialTypeMapper.queryListPageMap(page);
    }

    /*新增或修改物资物资类别信息 id为空或0则新增  否则修改*/
    @Override
    public void saveOrUpdateRmMaterialTypeInfo(String id, RmMaterialType rmMaterialType) throws Exception {
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改
            rmMaterialType.setRmMaterialTypeId(id);
            rmMaterialTypeMapper.updateByObject(rmMaterialType);
        } else {//新增
            rmMaterialType.setRmMaterialTypeId(UUID.randomUUID().toString());
            rmMaterialTypeMapper.insert(rmMaterialType);
        }
    }

    /*获取所有物资类型*/
    @Override
    public List<Map<String, Object>> queryAllMaterialTypeModel() {
        return rmMaterialTypeMapper.queryAllMaterialTypeModel();
    }
}
