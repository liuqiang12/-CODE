package com.idc.mapper;

import java.util.List;
import java.util.Map;

import com.idc.model.NetPort;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>NET_PROT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface NetPortMapper extends SuperMapper<NetPort, Long> {
    /**
	 *   Special code can be written here 
	 */
    List<Map<String, Object>> queryListByObjectMap(NetPort netPort);

	List<Map<String, Object>> queryListPageMap(PageBean<T> page);

	List<Map<String, Object>> getNetDeviceModel();
	/**
	 * 工单使用 修改资源状态
	 * @param resourceId
	 * @param status
	 * @throws Exception
	 */
    void singleUpdateInfoById(@Param("resourceId") Long resourceId, @Param("status") Long status) throws  Exception;

    List<Map<String, Object>> getNetPortListByRackId(List<String> list);

    /**
	 * 获取端口的id:选择工单的时候使用:liuqiang  add
	 * @param rackId
	 * @return
	 */
    List<Long> getNetPortListInfoIdsByRackId(Integer rackId);

	List<Map<String, Object>> getNetportsByRoomId(Map<String,Object> map);

	/*修改端口状态 map中key:id-端口ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
	void updatePortStatusByPortIds(List<Map<String, Object>> list) throws Exception;

    Map<String, Object> getNetPortNum(Long locationId);

    /*获取客户所有占用端口*/
    List<Map<String, Object>> queryUseredPortByCustomerId(Map map);

    /*获取客户所有占用端口  page*/
    List<Map<String, Object>> queryUseredPortByCustomerIdPage(PageBean page);

    /*获取当前节点已经绑定的端口*/
    List<NetPort> queryBindedPortList(PageBean page);

	/*修改端口分配带宽  流程上选择端口后填入分配带宽值   调用接口*/
	void updateNetPortAssignations(List<Map<String,Object>> list) throws Exception;

	/*通过设备ID查看端口占用情况*/
	Map<String,Object> queryPortTotalAndFreePortByDeviceId(@Param("deviceId") Long deviceId);
}

 