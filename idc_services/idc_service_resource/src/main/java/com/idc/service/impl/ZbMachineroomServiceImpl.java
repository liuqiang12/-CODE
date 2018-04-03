package com.idc.service.impl;

import com.idc.mapper.IdcBuildingMapper;
import com.idc.mapper.ZbMachineroomMapper;
import com.idc.model.IdcBuilding;
import com.idc.model.ZbMachineroom;
import com.idc.service.ZbMachineroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>zb_machineroom:IDC机房<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 17,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("zbMachineroomService")
public class ZbMachineroomServiceImpl  extends SuperServiceImpl<ZbMachineroom, Integer> implements ZbMachineroomService {
    @Autowired
    private ZbMachineroomMapper zbMachineroomMapper;
    @Autowired
    private IdcBuildingMapper idcBuildingMapper;

    @Override
    public List<Map<String, Object>> queryZbMachineroomgInfoListPage(PageBean page, Object param) {
        //这里讲查询条件进行处理
        page.setParams(MapHelper.queryCondition(param));
        return zbMachineroomMapper.queryZbMachineroomgInfoListPage(page);
    }

    @Override
    public List<Map<String, Object>> queryAllZbMachineroomInfoList() {
        return zbMachineroomMapper.queryAllZbMachineroomInfoList();
    }

    @Override
    public int updateByPrimaryKeySelective(ZbMachineroom record) {
        return zbMachineroomMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void saveZbMachineroom(ZbMachineroom zb) {
        zbMachineroomMapper.saveZbMachineroom(zb);
    }

    @Override
    public List<Map<String, Object>> getAllZbMachineroomInfo(Map<String,Object> map) {
        return zbMachineroomMapper.getAllZbMachineroomInfo(map);
    }

    @Override
    public Map<String, Object> getMapModelById(int id) {
        return zbMachineroomMapper.getMapModelById(id);
    }

    @Override
    public void importZbMachineroomByExcel(List<List<Object>> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            List<Object> obj = list.get(i);
            ZbMachineroom zbMachineroom = new ZbMachineroom();
            zbMachineroom.setSitename(String.valueOf(obj.get(0)));
            zbMachineroom.setSitenamesn(String.valueOf(obj.get(1)));
            //机楼
            if (obj.get(1) != null && !"".equals(String.valueOf(obj.get(2)))) {
                IdcBuilding idcBuilding = new IdcBuilding();
                idcBuilding.setName(String.valueOf(obj.get(2)));
                idcBuilding = idcBuildingMapper.getModelByObject(idcBuilding);
                zbMachineroom.setBuildingid(idcBuilding != null ? idcBuilding.getId().toString() : null);
            }
            zbMachineroom.setRoomcategory(String.valueOf(obj.get(3)));
            zbMachineroom.setGrade(Integer.parseInt(String.valueOf(obj.get(4))));
            zbMachineroom.setMaxracks(Integer.parseInt(String.valueOf(obj.get(5))));
            zbMachineroom.setDesignelectricity(String.valueOf(obj.get(6)));
            zbMachineroom.setArea(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get(7)))));
            zbMachineroom.setFloorheight(String.valueOf(obj.get(8)));
            zbMachineroom.setAirconditioningreserve(String.valueOf(obj.get(9)));
            zbMachineroom.setTotalbandwidth(Long.parseLong(String.valueOf(obj.get(10))));
            zbMachineroom.setWidthM(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get(11)))));
            zbMachineroom.setHeightM(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get(12)))));
            zbMachineroom.setUsefor(Integer.parseInt(String.valueOf(obj.get(13))));
            zbMachineroom.setResponsibleuserid(String.valueOf(obj.get(14)));
            zbMachineroom.setRemark(String.valueOf(obj.get(15)));

            ZbMachineroom oldZbMachineroom = zbMachineroomMapper.queryZbMachineroomInfoBySitename(zbMachineroom.getSitenamesn());
            //若此机房已存在就进行修改，否则就新增
            if (oldZbMachineroom != null && oldZbMachineroom.getSitename().equals(zbMachineroom.getSitename())) {
                zbMachineroom.setId(oldZbMachineroom.getId());
                zbMachineroom.setIsdelete(0);
                zbMachineroomMapper.updateByObject(zbMachineroom);
            } else {
                zbMachineroomMapper.insert(zbMachineroom);
            }
        }
    }

    @Override
    public Map<String, Object> getRoomStatistics(int roomId) {
        return zbMachineroomMapper.getRoomStatistics(roomId);
    }

    /*通过机楼IDS获取机房*/
    @Override
    public List<ZbMachineroom> queryListByBuildingIds(List<String> list) {
        return zbMachineroomMapper.queryListByBuildingIds(list);
    }

    /*获取当前机内所有模块*/
    @Override
    public List<Map<String, Object>> queryAllModuleByRoomId(int roomId) {
        return zbMachineroomMapper.queryAllModuleByRoomId(roomId);
    }

    /*根据机房ID获取各个机房的机架数量*/
    @Override
    public List<Map<String, Object>> getRackNumByIds(List<String> list) {
        return zbMachineroomMapper.getRackNumByIds(list);
    }

    /*删除机房    对机房进行软删除*/
    @Override
    public void updateRoomToInvalidByIds(List<String> list) throws Exception {
        zbMachineroomMapper.updateRoomToInvalidByIds(list);
    }

    /*通过机房名称获取机架*/
    @Override
    public ZbMachineroom queryZbMachineroomInfoBySitename(String sitename) {
        return zbMachineroomMapper.queryZbMachineroomInfoBySitename(sitename);
    }

    /*通过机房Ids获取机房数据*/
    @Override
    public List<ZbMachineroom> queryZbMachineroomListByIds(Map<String, Object> map) {
        return zbMachineroomMapper.queryZbMachineroomListByIds(map);
    }

    /*通过数据中心Id查询所有的机房*/
    @Override
    public List<ZbMachineroom> queryZbMachineroomListByLocationId(Integer locationId) {
        return zbMachineroomMapper.queryZbMachineroomListByLocationId(locationId);
    }
}
