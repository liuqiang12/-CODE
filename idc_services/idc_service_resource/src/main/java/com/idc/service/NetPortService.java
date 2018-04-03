package com.idc.service;

import java.util.List;
import java.util.Map;

import com.idc.model.NetPort;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;
import utils.tree.TreeNode;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>NET_PROT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetPortService extends SuperService<NetPort, Long> {
    /**
	 *   Special code can be written here 
	 */
    List<Map<String, Object>> queryListByObjectMap(NetPort netPort);

	List<Map<String,Object>> queryListPageMap(PageBean<T> page, Object param);

	List<Map<String,Object>> getNetDeviceModel();

	List<Map<String, Object>> getNetPortListByRackId(String rackIds);

	/**
	 * 获取端口的id:选择工单的时候使用:liuqiang  add
	 * @param rackId
	 * @return
	 */
    List<Long> getNetPortListInfoIdsByRackId(Integer rackId);

    List<TreeNode> getTree(String roomIds,String locationId);

    List<Map<String, Object>> getNetportsByRoomId(Map<String,Object> map);

    /*修改端口状态 map中key:id-端口ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
    void updatePortStatusByPortIds(List<Map<String, Object>> list, Long deviceid) throws Exception;

    /*获取客户所有占用端口*/
    List<Map<String, Object>> queryUseredPortByCustomerId(Map map);

    /*获取客户所有占用端口  page*/
    List<Map<String, Object>> queryUseredPortByCustomerIdPage(PageBean page, Object param);

    /*获取当前节点已经绑定的端口*/
    List<NetPort> queryBindedPortList(PageBean page, Object param);

    /*修改端口分配带宽  流程上选择端口后填入分配带宽值   调用接口  key: assignation：分配带宽   portid：端口ID*/
    void updateNetPortAssignations(List<Map<String,Object>> list) throws Exception;

}
