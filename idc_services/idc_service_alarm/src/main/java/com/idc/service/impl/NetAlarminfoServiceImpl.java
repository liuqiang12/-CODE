package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.mapper.NetAlarminfoMapper;
import com.idc.model.*;
import com.idc.service.NetAlarminfoService;
import com.idc.service.NetDeviceService;
import com.idc.service.NetKpibaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import system.data.page.EasyUIPageBean;
import utils.PropertyUtils;
import utils.StringUtil;
import utils.typeHelper.MapHelper;

import java.util.*;

/**
 * Created by mylove on 2017/8/2.
 */
@Service
public class NetAlarminfoServiceImpl implements NetAlarminfoService {
    private static final Logger logger = LoggerFactory.getLogger(NetAlarminfoServiceImpl.class);
    @Autowired
    private NetAlarminfoMapper netAlarminfoMapper;
    @Autowired
    private NetKpibaseService netKpibaseService;
    @Autowired
    private NetDeviceService netDeviceService;
    @Override
    public List<NetAlarmInfoVo> queryListPageByMap(EasyUIPageBean pageBean, String keyword, Integer regionid, Long objid, boolean isCurrAlarm, String starttime, String endtime, Integer alarmtype) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("regionid", regionid);
        params.put("objid", objid);
        params.put("iscurralarm", isCurrAlarm);
        params.put("alarmtype", alarmtype);
        params.put("starttime", starttime);
        params.put("endtime", endtime);
        pageBean.setParams(params);
        List<NetDevice> netDevices = netDeviceService.queryList();
        ImmutableMap<String, NetDevice> devicesMap = Maps.uniqueIndex(netDeviceService.queryList(), new Function<NetDevice, String>() {
            @Override
            public String apply(NetDevice netDevice) {
                return netDevice.getDeviceid().toString();
            }
        });
        List<NetAlarminfo> netAlarminfos = netAlarminfoMapper.queryListPage(pageBean);
        ImmutableMap<Long, NetKpibase> netKpibases = netKpibaseService.getKpiBaseMap();
        List<NetAlarmInfoVo> alarmInfoVos = new ArrayList<>();
        try {
            NetAlarmInfoVo netAlarmInfoVo;
            String objName="";
            for (NetAlarminfo netAlarminfo : netAlarminfos) {
                netAlarmInfoVo = new NetAlarmInfoVo();
                netAlarmInfoVo.setId(netAlarminfo.getId());
                NetDevice netDevice = devicesMap.get(netAlarminfo.getAlarmobj().toString());
                if(netDevice!=null){
                    objName=netDevice.getRoutname();
                }
                if(StringUtils.isNotEmpty(netAlarminfo.getPortName())){
                    objName+=":"+netAlarminfo.getPortName();
                }
                netAlarmInfoVo.setObjName(objName);
                netAlarmInfoVo.setPortid(netAlarminfo.getPortid());
                netAlarmInfoVo.setAlarmtime(netAlarminfo.getAlarmtime());
                netAlarmInfoVo.setAlarmtimeStr(netAlarminfo.getAlarmtime());
                netAlarmInfoVo.setAlarminfo(netAlarminfo.getAlarminfo());
                netAlarmInfoVo.setAlarmlevel(netAlarminfo.getAlarmlevel());
                netAlarmInfoVo.setNetKpibase(netKpibases.get(netAlarminfo.getAlarmtype()));
                netAlarmInfoVo.setAlarmsendflag(netAlarminfo.getAlarmsendflag());
                netAlarmInfoVo.setAlarmsendtime(netAlarminfo.getAlarmsendtime());
//                netAlarmInfoVo.(netAlarminfo.getAlarmsendtime());
                alarmInfoVos.add(netAlarmInfoVo);
            }
//            alarmInfoVos = PropertyUtils.copyListProperties(new NetAlarmInfoVo(), netAlarminfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pageBean.setItems(alarmInfoVos);
        return alarmInfoVos;
    }

    @Override
    public void deleteCurrAlarmById(int id) {
        NetAlarminfo curr = netAlarminfoMapper.getById(id,true);
        NetAlarminfo his  = new NetAlarminfo();
//        his.setAlarmclass(curr.getAlarmclass());
        his.setId(netAlarminfoMapper.getMaxId()+1);
        his.setAlarminfo(curr.getAlarminfo());
        his.setAlarmlevel(curr.getAlarmlevel());
//        his.setAlarmremark(curr.getAlarmremark());
        his.setAlarmtime(curr.getAlarmtime());
//        his.setCapvalue(curr.getCapvalue());
//        his.setKpiid(curr.getKpiid());
//        his.setObjid(curr.getObjid());
        his.setAlarmobj(curr.getAlarmobj());
        his.setAlarmtype(curr.getAlarmtype());
        his.setAlarmsendflag(curr.getAlarmsendflag());
        his.setPortid(curr.getPortid());
        his.setAlarmsendtime(curr.getAlarmsendtime());

//        his.setPortname(curr.getPortname());
        his.setResumetime(new Date());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        his.setResumeuser(userDetails.getUsername());
        netAlarminfoMapper.insertHis(his);
        netAlarminfoMapper.deleteCurrById(id);
//        netAlarminfoMapper.insertHis()
    }
}
