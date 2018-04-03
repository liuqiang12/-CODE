package com.idc.service;

import com.idc.model.*;
import org.springframework.transaction.annotation.Transactional;
import system.data.page.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
public interface BusPortService {

    List<Map<String, Object>> queryListPage(PageBean page, String key);

    List<NetBusiPortFlow> queryListPage(String key,List<String> groupids);

//    /***
//     * 获取业务扣最新流量
//     *
//     * @param typeIds
//     * @return
//     */
//    List<NetPortFlow> getCurrFlow(List<String> typeIds);

//    List<NetPortFlow> getCurrFlow(String userid);

    NetBusiPort queryById(String id);

//    @Transactional
//    ExecuteResult bindPort(boolean isUpdate, Long typeId, String busiPortName, String descr, String customer, List<String> portids,String groupName) throws Exception;

    ExecuteResult deleteBy(String[] split);

    List<NetPortFlow> deal(String busiPortNames);

    /*通过客户ID获取该客户所有业务端口名称*/
    List<String> queryPortNameListByCustomerId(String customerId);

    List<NetBusiGroup> groupList(boolean isSimple);

    ExecuteResult saveGroup(NetBusiGroup netBusiGroup);

    ExecuteResult delGroup(String id);

    ExecuteResult saveBusiInfo(NetBusiPort netBusiPort, List<String> portids,  List<String> groupIds) throws Exception;

    List<NetBusiPort> queryList();
}
