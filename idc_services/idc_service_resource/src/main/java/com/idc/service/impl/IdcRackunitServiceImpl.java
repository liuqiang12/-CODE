package com.idc.service.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.idc.mapper.IdcDeviceMapper;
import com.idc.mapper.IdcRackMapper;
import com.idc.model.IdcDevice;
import com.idc.model.IdcRack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import com.idc.mapper.IdcRackunitMapper;
import com.idc.model.IdcRackunit;
import com.idc.service.IdcRackunitService;
import utils.typeHelper.MapHelper;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_RACKUNIT:机位<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcRackunitService")
public class IdcRackunitServiceImpl extends SuperServiceImpl<IdcRackunit, Long> implements IdcRackunitService {
    @Autowired
    private IdcRackunitMapper mapper;
    @Autowired
    private IdcRackMapper rackMapper;
    @Autowired
    private IdcDeviceMapper idcDeviceMapper;

    @Override
    public List<Map<String, Object>> queryListByRackID(int id) {
        List<Map<String, Object>> maps = mapper.queryListByRackID(id);
        if(maps==null||maps.size()==0){//没有机位 生成默认
            IdcRack idcRack = rackMapper.getModelById(id);
            if(idcRack!=null){
                List<IdcRackunit> idcRackunits = Lists.newArrayList();
                Integer height = idcRack.getHeight();
                for (Integer i = 1; i < height+1; i++) {
                    IdcRackunit idcRackunit = new IdcRackunit();
                    idcRackunit.setUno(i);
                    idcRackunit.setStatus(20);
                    idcRackunit.setCode(idcRack.getCode()+"_"+i);
                    idcRackunit.setRackid(Long.parseLong(idcRack.getId().toString()));
                    idcRackunits.add(idcRackunit);
                }
                try {
                    logger.info("开始生成机位");
                    mapper.insertList(idcRackunits);
                    maps = mapper.queryListByRackID(id);
                    logger.info("已生成机位");
                } catch (Exception e) {
                    logger.error("生成机位失败",e);
                }
            }
        }
//        if (maps != null) {
//            Map<String, Map<String, Object>> deviceMap = new HashMap<>();
//            for (Map<String, Object> map : maps) {
//                String devicename = (String) map.get("DEVICENAME");
//                Map<String, Object> objectMap = deviceMap.get(devicename);
//                if (objectMap == null) {
//                    map.put("startuno", map.get("UNO"));
//                    map.put("dheight", 1);
//                    deviceMap.put(devicename, map);
//                } else {
//                    BigDecimal luno = (BigDecimal) objectMap.get("UNO");//上一个机位号
//                    BigDecimal cuno = (BigDecimal) map.get("UNO");//本次机位号
//                    if (cuno.intValue() < luno.intValue()) {
//                        objectMap.putAll(map);
//                        objectMap.put("startuno", cuno);
//                    }
//                    objectMap.put("dheight", (Integer) objectMap.get("dheight") + 1);
//                }
//            }
//            maps.clear();
//            for (String s : deviceMap.keySet()) {
//                maps.add(deviceMap.get(s));
//            }
//        }
        return maps;
    }

    @Override
    public void online(int startu, int uheight, long rackid, long deviceid) throws Exception {
        //将机架绑定到设备
        IdcDevice idcDevice = new IdcDevice();
        idcDevice.setDeviceid(deviceid);
        idcDevice.setRackId(rackid);
        idcDeviceMapper.updateByObject(idcDevice);
        //上架
        mapper.online(startu,startu+uheight,rackid,deviceid);
    }

    @Override
    public void down(int rackid, int deviceid) throws Exception {
        mapper.down(rackid,deviceid);
    }

    @Override
    public List<Map<String, Object>> queryIdcRackunitInfoListPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryIdcRackunitInfoListPage(page);
    }

    @Override
    public List<Map<String, Object>> queryIdcRackunitInfoList(IdcRackunit idcRackunit) {
        return mapper.queryIdcRackunitInfoList(idcRackunit);
    }

    /*修改机位状态 map中key:id-机位ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
    @Override
    public void updateRackunitStatusByIds(List<Map<String, Object>> list, Long rackId) throws Exception {
        if (list != null && list.size() > 0) {
            //修改机位状态
            mapper.updateRackunitStatusByIds(list);
            Long status = null;
            //查看当前机架空闲机位数
            if (rackId == null) {
                Long id = Long.parseLong(list.get(0).get("id").toString());
                IdcRackunit rackunit = mapper.getModelById(id);
                rackId = rackunit.getRackid();
                status = Long.parseLong(list.get(0).get("status").toString());
                if(status!=null && status==50){
                    status = 50L;
                }else if(status!=null && status==55){
                    status = 60L;
                }
            }
            updateRackStatusByRackId(rackId,status);
        }
    }

    /*解除客户与机位的绑定关系*/
    @Override
    public void unbindCustomerWithRackunit(Map<String, Object> map) throws Exception {
        mapper.unbindCustomerWithRackunit(map);
        Long rackId = map.get("rackId") == null ? null : Long.parseLong(map.get("rackId").toString());
        updateRackStatusByRackId(rackId,null);
    }

    /*修改机位状态后去判断是否要修改机架状态*/
    public void updateRackStatusByRackId(Long rackId,Long status) throws Exception {
        Map<String, Object> unitNumMap = mapper.queryUnitTotalAndFreeUnitByRackId(rackId);
        int unitTotal = unitNumMap.get("UNITTOTAL") == null ? 0 : Integer.parseInt(unitNumMap.get("UNITTOTAL").toString());
        int freeunitnum = unitNumMap.get("FREEUNITNUM") == null ? 0 : Integer.parseInt(unitNumMap.get("FREEUNITNUM").toString());
        if (freeunitnum > 0) {//当存在空闲机位时，将机架状态改为20-可用
            IdcRack idcRack = new IdcRack();
            idcRack.setId(rackId.intValue());
            if (freeunitnum < unitTotal) {
                idcRack.setStatus(20);
            } else {
                idcRack.setStatus(40);
            }
            rackMapper.updateByObject(idcRack);
        } else {//当不存在空闲机位时，将机架状态改为60-在服
            IdcRack idcRack = new IdcRack();
            idcRack.setId(rackId.intValue());
            idcRack.setStatus(status.intValue());
            rackMapper.updateByObject(idcRack);
        }
    }

    /*查看设备安装位置到高度之间的机位是否足够*/
    @Override
    public int getRackunitNumByUheightAndRack(Integer rackId, Integer uheight, Integer uinstall) {
        return mapper.getRackunitNumByUheightAndRack(rackId, uheight, uinstall);
    }

    @Override
    public void updateStatusByRackId(Long rackId, Integer status) throws Exception {
        mapper.updateStatusByRackId(rackId, status);
    }

    /*查看客户分配的机位*/
    @Override
    public List<Map<String, Object>> queryUseredRackunitinfoByCustomerIdPage(PageBean page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryUseredRackunitinfoByCustomerIdPage(page);
    }
}
