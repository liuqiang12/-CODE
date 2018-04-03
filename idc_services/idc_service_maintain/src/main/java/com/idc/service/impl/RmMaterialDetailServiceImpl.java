package com.idc.service.impl;

import com.idc.mapper.RmMaterialDetailMapper;
import com.idc.model.RmMaterialDetail;
import com.idc.service.RmMaterialDetailService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>RM_MATERIAL_DETAIL:物资出入库详情表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmMaterialDetailService")
public class RmMaterialDetailServiceImpl extends SuperServiceImpl<RmMaterialDetail, String> implements RmMaterialDetailService {
    @Autowired
    private RmMaterialDetailMapper rmMaterialDetailMapper;

    /*获取满足条件的所有物资出入申请单  map*/
    @Override
    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return rmMaterialDetailMapper.queryListMap(map);
    }

    /*获取满足条件的所有物资出入申请单  分页 map*/
    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return rmMaterialDetailMapper.queryListPageMap(page);
    }

    /*新增或修改物资出入申请单信息 id为空则新增  否则修改*/
    @Override
    public void saveOrUpdateRmMaterialDatailInfo(String id, RmMaterialDetail rmMaterialDetail, String userName) throws Exception {
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改
            rmMaterialDetail.setRmMaterialDetailId(id);
            rmMaterialDetailMapper.updateByObject(rmMaterialDetail);
        } else {//新增
            rmMaterialDetail.setRmMaterialDetailId(UUID.randomUUID().toString());
            rmMaterialDetail.setRmCreateTime(new Date());
            rmMaterialDetail.setRmCreateUser(userName);
            rmMaterialDetailMapper.insert(rmMaterialDetail);
        }
    }
}
