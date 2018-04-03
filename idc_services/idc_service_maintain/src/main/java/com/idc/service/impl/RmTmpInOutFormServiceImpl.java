package com.idc.service.impl;

import com.idc.mapper.RmTmpInOutFormMapper;
import com.idc.mapper.RmTmpRegisterMapper;
import com.idc.model.RmTmpInOutForm;
import com.idc.model.RmTmpRegister;
import com.idc.service.RmTmpInOutFormService;
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
 * <b>功能：业务表</b>RM_TMP_IN_OUT_FORM:临时出入申请单<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Oct 31,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("rmTmpInOutFormService")
public class RmTmpInOutFormServiceImpl extends SuperServiceImpl<RmTmpInOutForm, String> implements RmTmpInOutFormService {
	@Autowired
    private RmTmpInOutFormMapper rmTmpInOutFormMapper;
    @Autowired
    private RmTmpRegisterMapper rmTmpRegisterMapper;

    /*获取满足条件的所有临时出入申请单  map*/
    @Override
    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return rmTmpInOutFormMapper.queryListMap(map);
    }

    /*获取满足条件的所有临时出入申请单  分页 map*/
    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return rmTmpInOutFormMapper.queryListPageMap(page);
    }

    /*新增或修改申请单信息  id为空则新增  否则修改*/
    @Override
    @Transactional
    public void saveOrUpdateRmInOutFormInfo(String id, RmTmpInOutForm rmTmpInOutForm, String userName) throws Exception {
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改
            rmTmpInOutForm.setRmTmpInOutId(id);
            //修改申请单基本信息
            rmTmpInOutFormMapper.updateByObject(rmTmpInOutForm);
            //先删除申请单原有的详细进出人员，再新增进出人员
            delAndAddRmTmpRegister(rmTmpInOutForm);
        } else {//新增
            rmTmpInOutForm.setRmTmpInOutId(UUID.randomUUID().toString());
            rmTmpInOutForm.setRmCreateTime(new Date());
            rmTmpInOutForm.setRmCreateUser(userName);
            //新增申请单信息
            rmTmpInOutFormMapper.insert(rmTmpInOutForm);
            //先删除申请单原有的详细进出人员，再新增进出人员
            delAndAddRmTmpRegister(rmTmpInOutForm);

        }
    }

    /*先删除申请单原有的详细进出人员，再新增进出人员*/
    public void delAndAddRmTmpRegister(RmTmpInOutForm rmTmpInOutForm) throws Exception {
        //删除原有进出人员
        rmTmpRegisterMapper.deleteRmTmpRegistersByRmTmpInOutFormId(rmTmpInOutForm.getRmTmpInOutId());
        //新增进出人员
        if (rmTmpInOutForm.getRmTmpRegisters() != null && rmTmpInOutForm.getRmTmpRegisters().size() > 0) {//具体进出人员信息
            for (RmTmpRegister rmTmpRegister : rmTmpInOutForm.getRmTmpRegisters()) {
                if (rmTmpRegister.getRmTmpRegisterName() != null && !"".equals(rmTmpRegister.getRmTmpRegisterName())) {
                    rmTmpRegister.setRmTmpRegisterId(UUID.randomUUID().toString());
                    rmTmpRegister.setRmTmpInOutId(rmTmpInOutForm.getRmTmpInOutId());
                    //新增具体进出人员信息
                    rmTmpRegisterMapper.insert(rmTmpRegister);
                }
            }
        }
    }

    /*通过申请单IDS删除申请单，并删除其对应的出入人员信息*/
    @Override
    @Transactional
    public void deleteRmTmpInOutFormAndRmTmpRegisterByRmTmpInOutFormIds(List<String> list) throws Exception {
        //删除申请单信息
        rmTmpInOutFormMapper.deleteByList(list);
        //删除申请单对应的所有进出人员信息
        rmTmpRegisterMapper.deleteByRmTmpInOutFormIds(list);
    }
}
