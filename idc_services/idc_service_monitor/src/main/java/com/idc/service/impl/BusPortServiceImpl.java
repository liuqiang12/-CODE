package com.idc.service.impl;

import com.idc.mapper.BusPortMapper;
import com.idc.model.*;
import com.idc.service.BusPortService;
import com.idc.service.NetPortFlowService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;
import utils.tree.TreeBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
@Service
public class BusPortServiceImpl implements BusPortService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BusPortMapper busPortMapper;
    @Autowired
    private NetPortFlowService netPortFlowService;

    public final static String NET_CAP_PORT_CURR = "net_port_curr";

    @Override
    public List<Map<String, Object>> queryListPage(PageBean page, String key) {
//        page.getParams().put("search",key);
//        List<Map<String, Object>> maps = busPortMapper.queryListPage(page);
        return null;
    }

    @Override
    public List<NetBusiPortFlow> queryListPage(String key, List<String> groupids) {
        List<NetBusiPort> netBusiPorts = busPortMapper.queryListPage(key, groupids);
        long l = System.currentTimeMillis();
        List<String> ids =new ArrayList<>(100);
        for (NetBusiPort netBusiPort : netBusiPorts) {
            for (Long aLong : netBusiPort.getPortids()) {
                ids.add(aLong.toString());
            }
        }

        List<NetBusiPortFlow> netBusiPortFlows = new ArrayList<>(100);
        NetBusiPortFlow netBusiPortFlow;
        Map<String, NetPortFlow> flowMap = netPortFlowService.getCurrMap(ids);
        for (NetBusiPort netBusiPort : netBusiPorts) {
            netBusiPortFlow = new NetBusiPortFlow();
            netBusiPortFlow.setPortid(netBusiPort.getId());
            netBusiPortFlow.setPortname(netBusiPort.getBusiportname());
            netBusiPortFlow.setBandwidth(new BigDecimal(netBusiPort.getBandwidth()));
            List<Long> portids = netBusiPort.getPortids();
            Double input=0D,output=0D;
            for (Long portid : portids) {
                netBusiPortFlow.setCount(netBusiPortFlow.getCount()+1);
                if(flowMap!=null){
                    NetPortFlow portCapSerial = flowMap.get(portid.toString());
                    if(portCapSerial!=null){
                        output+=portCapSerial.getOutflow();
                        input+=portCapSerial.getInflow();
                    }
                }
            }
            netBusiPortFlow.setInflow(input);
            netBusiPortFlow.setOutflow(output);
            netBusiPortFlows.add(netBusiPortFlow);
        }
        logger.info("{}____________4", System.currentTimeMillis() - l);
        return netBusiPortFlows;
    }

//    @Override
//    public List<NetPortFlow> getCurrFlow(List<String> typeIds) {
//        return busPortMapper.getCurrFlow(typeIds);
//    }

//    @Override
//    public List<NetPortFlow> getCurrFlow(String userid) {
//        return busPortMapper.getCurrFlowByUser(userid);
//    }

    @Override
    public NetBusiPort queryById(String id) {
        return busPortMapper.queryById(id);
    }

//    @Override
//    public ExecuteResult bindPort(boolean isUpdate, Long typeId, String busiPortName, String descr, String customer, List<String> portids, String groupids) throws Exception {
//        ExecuteResult executeResult = new ExecuteResult();
//        List<NetBusiPort> objs = busPortMapper.getByBusiName(busiPortName);
//        if (objs.size() > 0) {
//            if (objs.size() > 2 || typeId == null) {
//                executeResult.setMsg("业务名重复");
//                return executeResult;
//            } else {
//                NetBusiPort byId = busPortMapper.getById(typeId.toString());
//                if (!byId.getBusiportname().equals(busiPortName)) {
//                    executeResult.setMsg("业务名重复");
//                    return executeResult;
//                }
//            }
//        }
//        if (typeId != null) {
//            busPortMapper.deleteByTypeID(typeId);
//        }
//        typeId = busPortMapper.getTypeID();
////        busPortMapper.insertBusi();
//        if (portids.size() > 0) {
//            busPortMapper.insertBusi(busiPortName, typeId.toString(), descr, portids, customer);
//        }
//        executeResult.setState(true);
//        return executeResult;
//    }

    @Override
    public ExecuteResult deleteBy(String[] split) {
        if (split.length > 0) {
            for (String s : split) {
                busPortMapper.deleteGroupByBusiID(Long.parseLong(s));
                busPortMapper.deletePortByBusiID(Long.parseLong(s));
                busPortMapper.deleteByTypeID(Long.parseLong(s));
            }
        }
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setState(true);
        return executeResult;
    }

    @Override
    public List<NetPortFlow> deal(String portid) {
//        NetBusiPort byTypeId = busPortMapper.getById(portid);
//        String portname =byTypeId.getBusiportname();
        Calendar instance = Calendar.getInstance();
        String endTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        instance.add(Calendar.DAY_OF_MONTH, -1);
        String startTime = DateFormatUtils.format(instance, "yyyy-MM-dd HH:mm:ss");
        //busPortMapper.deal(busiPortNames);
        List<NetPortFlow> portFlowDetail = null;//netPortFlowService.getPortFlowDetail(null, 1, Arrays.asList(new String[]{portid}), startTime, endTime, "mi");
        return portFlowDetail;
    }

    /*通过客户ID获取该客户所有业务端口名称*/
    @Override
    public List<String> queryPortNameListByCustomerId(String customerId) {
        return busPortMapper.queryPortNameListByCustomerId(customerId);
    }

    @Override
    public List<NetBusiGroup> groupList(boolean isSimple) {
        List<NetBusiGroup> netBusiGroups = busPortMapper.groupList();
        if (isSimple) {
            return TreeBuilder.formatTree(netBusiGroups);
        }
        return netBusiGroups;
    }

    @Override
    public ExecuteResult saveGroup(NetBusiGroup netBusiGroup) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (StringUtils.isEmpty(netBusiGroup.getId())) {
                busPortMapper.insertGroup(netBusiGroup);
            } else {
                busPortMapper.updateGroup(netBusiGroup);
            }
            executeResult.setMsg(netBusiGroup.getId());
            executeResult.setState(true);
        } catch (Exception e) {
            logger.error("保存业务分组失败", e);
        }
        return executeResult;
    }

    @Override
    public ExecuteResult delGroup(String id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            busPortMapper.delGroup(id);
            executeResult.setState(true);
        } catch (Exception e) {
            logger.error("删除业务分组失败", e);
        }
        return executeResult;
    }

    @Override
    @Transactional
    public ExecuteResult saveBusiInfo(NetBusiPort netBusiPort, List<String> portids, List<String> groupIds) throws Exception {
        ExecuteResult executeResult = new ExecuteResult();
        List<NetBusiPort> objs = busPortMapper.getByBusiName(netBusiPort.getBusiportname());
        if (objs.size() > 0) {
            if (objs.size() > 2 || netBusiPort.getId() == null || 0L == netBusiPort.getId()) {
                executeResult.setMsg("业务名重复");
                return executeResult;
            } else {
                NetBusiPort byId = busPortMapper.getById(netBusiPort.getId().toString());
                if (byId != null) {
                    if (!byId.getBusiportname().equals(netBusiPort.getBusiportname())) {
                        executeResult.setMsg("业务名重复");
                        return executeResult;
                    }
                }
            }
        }
        if (netBusiPort.getId() != null && netBusiPort.getId() != 0) {
            busPortMapper.deletePortByBusiID(netBusiPort.getId());
            busPortMapper.deleteGroupByBusiID(netBusiPort.getId());
            busPortMapper.updateBusiByObj(netBusiPort);
        } else {
            netBusiPort.setId(busPortMapper.getTypeID());
            busPortMapper.insertBusiByObj(netBusiPort);
        }
        if (portids.size() > 0) {
            busPortMapper.bindPort(netBusiPort.getId(), portids);
        }
        if (groupIds.size() > 0) {
            busPortMapper.bindGroup(netBusiPort.getId(), groupIds);
        }
        executeResult.setState(true);
        return executeResult;
    }

    @Override
    public List<NetBusiPort> queryList() {
        return busPortMapper.queryList();
    }
}
