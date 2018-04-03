package com.idc.service.impl;

import com.idc.mapper.RmDevInOutFormMapper;
import com.idc.mapper.RmDevListMapper;
import com.idc.model.RmDevInOutForm;
import com.idc.model.RmDevList;
import com.idc.service.RmDevInOutFormService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * <b>功能：业务表</b>RM_DEV_IN_OUT_FORM:设备出入申请单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Nov 03,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmDevInOutFormService")
public class RmDevInOutFormServiceImpl extends SuperServiceImpl<RmDevInOutForm, String> implements RmDevInOutFormService {

    @Autowired
    private RmDevInOutFormMapper rmDevInOutFormMapper;
    @Autowired
    private RmDevListMapper rmDevListMapper;

    /*获取满足条件的设备出入申请单  map*/
    @Override
    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return rmDevInOutFormMapper.queryListMap(map);
    }

    /*获取满足条件的设备出入申请单  分页 map*/
    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return rmDevInOutFormMapper.queryListPageMap(page);
    }

    /*新增或修改设备出入申请单信息   id为0则新增  否则修改*/
    @Override
    @Transactional
    public void saveOrUpdateRmDevInOutFormInfo(String id, RmDevInOutForm rmDevInOutForm, String userName) throws Exception {
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改
            rmDevInOutForm.setRmDevInOutId(id);
            rmDevInOutFormMapper.updateByObject(rmDevInOutForm);
            //先删除申请单原有设备，在新增对应设备
            delAndAddRmDevList(rmDevInOutForm);
        } else {//新增
            rmDevInOutForm.setRmDevInOutId(UUID.randomUUID().toString());
            rmDevInOutForm.setRmCreateTime(new Date());
            rmDevInOutForm.setRmCreateUser(userName);
            rmDevInOutFormMapper.insert(rmDevInOutForm);
            //先删除申请单原有设备，在新增对应设备
            delAndAddRmDevList(rmDevInOutForm);
        }
    }

    /*先删除申请单原有的详细进出人员，再新增进出人员*/
    public void delAndAddRmDevList(RmDevInOutForm rmDevInOutForm) throws Exception {
        //删除原有进出设备
        rmDevListMapper.deleteRmDevListByRmDevInOutFormId(rmDevInOutForm.getRmDevInOutId());
        //新增进出设备
        if (rmDevInOutForm.getRmDevLists() != null && rmDevInOutForm.getRmDevLists().size() > 0) {//具体进出设备信息
            for (RmDevList rmDevList : rmDevInOutForm.getRmDevLists()) {
                if (rmDevList.getRmDevName() != null && !"".equals(rmDevList.getRmDevName())) {
                    rmDevList.setRmDevListId(UUID.randomUUID().toString());
                    rmDevList.setRmDevInOutId(rmDevInOutForm.getRmDevInOutId());
                    //新增具体进出设备信息
                    rmDevListMapper.insert(rmDevList);
                }
            }
        }
    }

    /*删除设备进场申请单信息，并一并删除与其对应的具体设备信息*/
    @Override
    @Transactional
    public void deleteRmDevInOutFormAndRmDevListByRmDevInOutFormIds(List<String> list) throws Exception {
        //删除申请单信息
        rmDevInOutFormMapper.deleteByList(list);
        //删除其对应的设备信息
        rmDevListMapper.deleteRmDevListByRmDevInOutFormIds(list);
    }
}
