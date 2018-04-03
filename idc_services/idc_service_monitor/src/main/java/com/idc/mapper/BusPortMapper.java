package com.idc.mapper;

import com.idc.model.NetBusiGroup;
import com.idc.model.NetBusiPort;
import com.idc.model.NetBusiPortFlow;
import com.idc.model.NetPortFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
public interface BusPortMapper {
//    List<Map<String, Object>> queryListPage(PageBean page);

    List<NetBusiPort> queryListPage(@Param("key")String key,@Param("groupids")List<String> groupid);

    NetBusiPort queryById(@Param("id") String id);

    void insertBusi(@Param("busiPortName") String busiPortName, @Param("typeid") String typeID, @Param("descr") String descr, @Param("ports") List<String> portids, @Param("customer") String customer);


//    List<NetPortFlow> deal(String busiPortNames);

    Long getTypeID();

    void deleteByTypeID(Long typeId);

//    public List<NetPortFlow> getCurrFlow(@Param("typeids")List<String> typeids);

//    List<NetPortFlow> getCurrFlowByUser(String userid);

    /*通过客户ID获取该客户所有业务端口名称*/
    List<String> queryPortNameListByCustomerId(@Param("customerId") String customerId);

    NetBusiPort getById(String id);
    List<NetBusiPort> getByBusiName(String name);

    List<NetBusiGroup> groupList();

    void insertGroup(NetBusiGroup netBusiGroup);

    void updateGroup(NetBusiGroup netBusiGroup);

    void delGroup(String id);

    void deleteGroupByBusiID(Long id);

    void deletePortByBusiID(Long id);

    void insertBusiByObj(NetBusiPort netBusiPort);

    void updateBusiByObj(NetBusiPort netBusiPort);

    void bindPort(@Param("busiid")Long id, @Param("portids")List<String> split);

    void bindGroup(@Param("busiid")Long id, @Param("groupids")List<String> split);

    List<NetBusiPort> queryList();

    List<NetPortFlow> getCap(@Param("busiids")List<String> portids, @Param("startTime")String startTime, @Param("endTime")String endTime);
}
