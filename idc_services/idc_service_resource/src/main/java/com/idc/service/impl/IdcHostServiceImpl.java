package com.idc.service.impl;

import com.idc.mapper.IdcDeviceMapper;
import com.idc.mapper.IdcHostMapper;
import com.idc.mapper.IdcRackMapper;
import com.idc.model.IdcDevice;
import com.idc.model.IdcHost;
import com.idc.model.IdcRack;
import com.idc.service.IdcHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.supper.service.impl.SuperServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_HOST:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcHostService")
public class IdcHostServiceImpl extends SuperServiceImpl<IdcHost, Long> implements IdcHostService {
	@Autowired
	private IdcHostMapper mapper;
    @Autowired
    private IdcDeviceMapper idcDeviceMapper;
    @Autowired
    private IdcRackMapper idcRackMapper;

    @Override
    @Transactional
    public void importIdcHostByExcel(List<List<Object>> list) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            List<Object> obj = list.get(i);
            IdcDevice idcDevice = new IdcDevice();
            IdcHost idcHost = new IdcHost();
            //主机设备基本信息
            idcDevice.setCode(String.valueOf(obj.get(0)));
            idcDevice.setName(String.valueOf(obj.get(1)));
            idcDevice.setModel(String.valueOf(obj.get(2)));
            idcDevice.setStatus(20L);
            idcDevice.setOwnertype(String.valueOf(obj.get(3)));
            idcDevice.setUheight(Long.parseLong(String.valueOf(obj.get(4))));
            idcDevice.setUinstall(Long.parseLong(String.valueOf(obj.get(5))));
            idcDevice.setVendor(String.valueOf(obj.get(6)));
            idcDevice.setOwner(String.valueOf(obj.get(7)));
            idcDevice.setDeviceclass(2);
            idcDevice.setPwrPowertype(String.valueOf(obj.get(8)));
            idcDevice.setUplinedate(obj.get(9) == null ? null : new Timestamp(sdf.parse(String.valueOf(obj.get(9))).getTime()));
            idcDevice.setDescription(String.valueOf(obj.get(10)));
            idcDevice.setPower(Long.parseLong(String.valueOf(obj.get(11))));
            idcDevice.setPhone(String.valueOf(obj.get(12)));
            //设置机架
            if (obj.get(13) != null && !"".equals(String.valueOf(obj.get(13)))) {
                IdcRack idcRack = new IdcRack();
                idcRack.setName(String.valueOf(obj.get(13)));
                idcRack = idcRackMapper.getModelByObject(idcRack);
                idcDevice.setRackId(idcRack != null ? idcRack.getId().longValue() : null);
            }
            idcDevice.setTicketId(Long.parseLong(String.valueOf(obj.get(14))));

            //主机设备具体信息
            idcHost.setOs(String.valueOf(obj.get(15)));
            idcHost.setCpusize(String.valueOf(obj.get(16)));
            idcHost.setMemsize(Long.parseLong(String.valueOf(obj.get(17))));
            idcHost.setDisksize(Long.parseLong(String.valueOf(obj.get(18))));
            idcHost.setUserid(String.valueOf(obj.get(19)));
            idcHost.setSysdescr(String.valueOf(obj.get(20)));

            IdcDevice oldIdcDevice = idcDeviceMapper.queryIdcDeviceInfoByName(idcDevice.getName());
            //若此设备已存在，则修改，否则就新增
            if (oldIdcDevice != null && oldIdcDevice.getName().equals(idcDevice.getName())) {
                //修改主机设备基本信息
                idcDevice.setDeviceid(oldIdcDevice.getDeviceid());
                idcDevice.setIsdelete(0);
                idcDeviceMapper.updateByObject(idcDevice);
                idcHost.setDeviceid(idcDevice.getDeviceid());
                //修改主机设备具体信息
                mapper.updateByObject(idcHost);
            } else {
                //新增主机设备
                idcDeviceMapper.insert(idcDevice);
                idcHost.setDeviceid(idcDevice.getDeviceid());
                mapper.insert(idcHost);
            }
        }
    }
}
