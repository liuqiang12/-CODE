package com.idc.service.impl;

import com.idc.mapper.IdcIspInfoMapper;
import com.idc.model.*;
import com.idc.service.IdcIspInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("idcIspInfoService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class IdcIspInfoServiceImpl implements IdcIspInfoService {
    private Logger logger = LoggerFactory.getLogger(IdcIspInfoServiceImpl.class);
    @Autowired
    private IdcIspInfoMapper idcIspInfoMapper;
    //mapper
    @Override
    public List<GatewayInfo> loadGatewayInfoList(){
        return idcIspInfoMapper.loadGatewayInfoList();
    }

    @Override
    public List<IpSegInfo> loadIpSegInfoList() {
        return idcIspInfoMapper.loadIpSegInfoList();
    }

    @Override
    public List<FrameInfo> loadFrameInfoList(Integer isSendIsp) {
        return idcIspInfoMapper.loadFrameInfoList(isSendIsp);
    }

    @Override
    public List<FrameInfo> loadFrameInfoListByTicketInstId(Integer isSendIsp, Long ticketInstId,Integer status) {
        return idcIspInfoMapper.loadFrameInfoListByTicketInstId(isSendIsp,ticketInstId,status);
    }

    @Override
    public IspSolidData loadIspSolidData() {
        return idcIspInfoMapper.loadIspSolidData(null);
    }

    @Override
    public List<IdcOfficer> loadIdcOfficerList() {
        return idcIspInfoMapper.loadIdcOfficerList();
    }

    @Override
    public List<UserInfo> loadReCustomerData() {
        return idcIspInfoMapper.loadReCustomerData();
    }

    @Override
    public List<ServiceInfo> loadUserServerListByUserId(String aid) {
        return idcIspInfoMapper.loadUserServerListByUserId(aid);
    }

    @Override
    public List<HousesHoldInfo> loadHousesHoldInfoList(Long ticketInstId) {
        return idcIspInfoMapper.loadHousesHoldInfoList(ticketInstId);
    }

    @Override
    public List<HousesHoldInfo> loadHousesHoldInfoListForDelUser(Long ticketInstId) {
        return idcIspInfoMapper.loadHousesHoldInfoListForDelUser(ticketInstId);
    }

    @Override
    public String getLoadFrameInfoId(Long ticketInstId) {
        return idcIspInfoMapper.getLoadFrameInfoId(ticketInstId);
    }

    @Override
    public List<IpAttr> loadIpAttrList(Long ticketInstId,Integer status) {
        return idcIspInfoMapper.loadIpAttrList(ticketInstId,status);
    }

    @Override
    public List<ServiceInfo> loadNetServiceinfoList(Long ticketInstId) {
        return idcIspInfoMapper.loadNetServiceinfoList(ticketInstId);
    }

    @Override
    public List<ServiceInfo> loadNetServiceinfoListByTicketId(Long ticketInstId) {
        return idcIspInfoMapper.loadNetServiceinfoListByTicketId(ticketInstId);
    }

    @Override
    public List<ServiceInfo> loadNetServiceinfoListByCustomerId(String customerId) {
        return idcIspInfoMapper.loadNetServiceinfoListByCustomerId(customerId);
    }

    @Override
    public List<IpSegInfo> loadIpSegInfoForIpList(Long ticketInstId) {
        return idcIspInfoMapper.loadIpSegInfoForIpList(ticketInstId);
    }

    @Override
    public UserInfo loadReCustomerDataById(Long customerId) {
        return idcIspInfoMapper.loadReCustomerDataById(customerId);
    }

    @Override
    public String getRandomSingleRackId(Long ticketInstId) {
        return idcIspInfoMapper.getRandomSingleRackId(ticketInstId);
    }
}
