package com.idc.service.impl;

import com.idc.mapper.IdcDeviceMapper;
import com.idc.mapper.IdcHostMapper;
import com.idc.mapper.IdcRackMapper;
import com.idc.mapper.NetDeviceMapper;
import com.idc.model.IdcDevice;
import com.idc.model.IdcHost;
import com.idc.model.IdcRack;
import com.idc.model.NetDevice;
import com.idc.service.IdcDeviceService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.typeHelper.MapHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_DEVICE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 26,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcDeviceService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcDeviceServiceImpl extends SuperServiceImpl<IdcDevice, Long> implements IdcDeviceService {
    @Autowired
    private IdcDeviceMapper mapper;
    @Autowired
    private NetDeviceMapper netDeviceMapper;
    @Autowired
    private IdcRackMapper idcRackMapper;
    @Autowired
    private IdcHostMapper idcHostMapper;

    @Override
    public List<Map<String, Object>> queryListByObjectMap(Map<String, Object> map) {
        return mapper.queryListByObjectMap(map);
    }

    @Override
    public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
        //这里讲查询条件进行处理
        page.setParams(MapHelper.queryCondition(param));
        //真正执行查询分页
        return mapper.queryListPageMap(page);
    }

    @Override
    public List<Map<String, Object>> getAllDeviceInfos(Map map) {
        return mapper.getAllDeviceInfos(map);
    }

    @Override
    public List<Map<String, Object>> getRackModel() {
        return mapper.getRackModel();
    }

    //新增或修改设备
    @Override
    public void saveOrUpdateDeviceInfo(IdcDevice idcDevice, Long id, Integer deviceclass) throws Exception {
        if (id != null && id != 0) {
            idcDevice.setDeviceid(id);
            //修改基本设备信息
            if (deviceclass == 1) {//修改网络设备信息
                NetDevice netDevice = idcDevice.getNetDevice();
                netDevice.setDeviceid(id);
                idcDevice.setBusinesstypeId(String.valueOf(netDevice.getRouttype()));
                netDeviceMapper.updateByObject(netDevice);
            } else if (deviceclass == 2) {//修改主机设备
                IdcHost idcHost = idcDevice.getIdcHost();
                idcHost.setDeviceid(id);
                idcHostMapper.updateByObject(idcHost);
            }
            mapper.updateByObject(idcDevice);
        } else {
            idcDevice.setStatus(40L);
            //新增设备
            if (deviceclass == 1) {//新增网络设备信息
                //获取网络设备信息
                NetDevice netDevice = idcDevice.getNetDevice();
                //补充设备基本信息
                idcDevice.setDeviceclass(1);
                idcDevice.setBusinesstypeId(String.valueOf(netDevice.getRouttype()));
                //新增设备基本信息
                mapper.insert(idcDevice);
                //补充网络设备信息
                netDevice.setDeviceid(idcDevice.getDeviceid());
                netDevice.setFactory(Integer.parseInt(idcDevice.getVendor()));
                //新增网络设备
                netDeviceMapper.insert(netDevice);
            } else if (deviceclass == 2) {//新增主机设备
                idcDevice.setDeviceclass(2);
                mapper.insert(idcDevice);

                IdcHost idcHost = idcDevice.getIdcHost();
                idcHost.setDeviceid(idcDevice.getDeviceid());
                idcHostMapper.insert(idcHost);
            }
        }
    }

    //根据IDS删除设备信息
    @Override
    public void deleteDeviceByIds(String ids, Integer deviceclass) throws Exception {
        List<String> deviceIdList = Arrays.asList(ids.split(","));
        //删除设备基本信息
        mapper.deleteByList(deviceIdList);
        if (deviceclass == 1) {
            //删除网络设备详细信息
            netDeviceMapper.deleteByList(deviceIdList);
        } else if (deviceclass == 2) {
            //删除主机设备
            idcHostMapper.deleteByList(deviceIdList);
        }
    }

    //导入网络设备
    @Override
    @Transactional
    public void importNetDeviceByExcel(List<List<Object>> list) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            List<Object> obj = list.get(i);
            IdcDevice idcDevice = new IdcDevice();
            NetDevice netDevice = new NetDevice();
            //网络设备基本信息
            idcDevice.setCode(String.valueOf(obj.get(0)));
            idcDevice.setName(String.valueOf(obj.get(1)));
            idcDevice.setModel(String.valueOf(obj.get(2)));
            idcDevice.setStatus(40L);
            idcDevice.setOwnertype(String.valueOf(obj.get(3)));
            idcDevice.setUheight(Long.parseLong(String.valueOf(obj.get(4))));
            idcDevice.setUinstall(Long.parseLong(String.valueOf(obj.get(5))));
            idcDevice.setVendor(String.valueOf(obj.get(6)));
            idcDevice.setOwner(String.valueOf(obj.get(7)));
            idcDevice.setDeviceclass(1);
            idcDevice.setPwrPowertype(String.valueOf(obj.get(8)));
            idcDevice.setUplinedate(obj.get(9) == null ? null : new Timestamp(sdf.parse(String.valueOf(obj.get(9))).getTime()));
            idcDevice.setDescription(String.valueOf(obj.get(10)));
            idcDevice.setPower(Long.parseLong(String.valueOf(obj.get(11))));
            idcDevice.setPhone(String.valueOf(obj.get(12)));
            //设置机架
            if (obj.get(14) != null && !"".equals(String.valueOf(obj.get(13)))) {
                IdcRack idcRack = new IdcRack();
                idcRack.setName(String.valueOf(obj.get(13)));
                idcRack = idcRackMapper.getModelByObject(idcRack);
                idcDevice.setRackId(idcRack != null ? idcRack.getId().longValue() : null);
            }
            idcDevice.setTicketId(Long.parseLong(String.valueOf(obj.get(14))));

            //网络设备具体信息
            netDevice.setDeviceclass(Integer.parseInt(String.valueOf(obj.get(15))));
            netDevice.setRouttype(Integer.parseInt(String.valueOf(obj.get(16))));
            netDevice.setRoutname(String.valueOf(obj.get(17)));
            netDevice.setNetworklayer(String.valueOf(obj.get(18)));
            netDevice.setFactory(Integer.parseInt(idcDevice.getVendor()));

            IdcDevice oldIdcDevice = mapper.queryIdcDeviceInfoByName(idcDevice.getName());
            //若此设备存在，则修改，否则新增
            if (oldIdcDevice != null && oldIdcDevice.getName().equals(idcDevice.getName())) {
                //修改网络设备基本信息
                idcDevice.setDeviceid(oldIdcDevice.getDeviceid());
                idcDevice.setIsdelete(0);
                mapper.updateByObject(idcDevice);
                netDevice.setDeviceid(idcDevice.getDeviceid());
                //修改网络设备具体信息
                netDeviceMapper.updateByObject(netDevice);
            } else {
                //新增网络设备
                mapper.insert(idcDevice);
                netDevice.setDeviceid(idcDevice.getDeviceid());
                netDeviceMapper.insert(netDevice);
            }
        }
    }

    //资源分配
    @Override
    public List<Map<String, Object>> queryDistributionDeviceList(Map<String, Object> map) {
        return mapper.queryDistributionDeviceList(map);
    }

    @Override
    public List<Map<String, Object>> queryDistributionDeviceListPage(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryDistributionDeviceListPage(page);
    }

    /*修改设备状态 map中key:id-设备ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号,*/
    @Override
    public void updateDeviceStatusByDeviceIds(List<Map<String, Object>> list) throws Exception {
        if (list != null && list.size() > 0) {
            mapper.updateDeviceStatusByDeviceIds(list);
        }
    }

    @Override
    public List<Map<String, Object>> queryDeviceList(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryDeviceList(page);
    }

    /*删除设备  对设备进行软删除*/
    @Override
    public void updateDeviceToInvalidByIds(List<String> list) throws Exception {
        mapper.updateDeviceToInvalidByIds(list);
    }

    //通过设备ID获取各个设备是否上架的信息
    @Override
    public List<Map<String, Object>> getDeviceNumByIds(List<String> list) {
        return mapper.getDeviceNumByIds(list);
    }

    /*通过设备名称获取设备*/
    @Override
    public IdcDevice queryIdcDeviceInfoByName(String name) {
        return mapper.queryIdcDeviceInfoByName(name);
    }

    /*通过数据中心ID获取所有的设备*/
    @Override
    public List<IdcDevice> queryDeviceListByLocationId(Map<String, Object> map) {
        return mapper.queryDeviceListByLocationId(map);
    }

    /*可通过数据中心机房IDS查询对应设备*/
    @Override
    public List<Map<String, Object>> queryListByRoomIds(Map<String, Object> map) {
        return mapper.queryListByRoomIds(map);
    }
}
