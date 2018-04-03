package com.idc.service.impl;

import java.util.*;

import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.NetPortService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;

import utils.tree.TreeBuilder;
import utils.tree.TreeNode;
import utils.typeHelper.MapHelper;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>NET_PROT:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> May 27,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("netPortService")
public class NetPortServiceImpl extends SuperServiceImpl<NetPort, Long> implements NetPortService {
	@Autowired
	private NetPortMapper mapper;
	@Autowired
	private ZbMachineroomMapper zbMachineroomMapper;
	@Autowired
	private IdcRackMapper idcRackMapper;
	@Autowired
	private IdcDeviceMapper idcDeviceMapper;

	@Override
	public List<Map<String, Object>> queryListByObjectMap(NetPort netPort) {
		return mapper.queryListByObjectMap(netPort);
	}

	@Override
	public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
		//这里讲查询条件进行处理
		page.setParams(MapHelper.queryCondition(param));
		//真正执行查询分页
		return mapper.queryListPageMap(page);
	}

	@Override
	public List<Map<String, Object>> getNetDeviceModel() {
		return mapper.getNetDeviceModel();
	}

    @Override
	public List<Map<String, Object>> getNetPortListByRackId(String rackIds) {
		List<String> list = new ArrayList<>();
		if (rackIds != null && !"".equals(rackIds)) {
			list = Arrays.asList(rackIds.split("_"));
		}
		return mapper.getNetPortListByRackId(list);
	}
	/**
	 * 获取端口的id:选择工单的时候使用:liuqiang  add
	 * @param rackId
	 * @return
	 */
	@Override
	public List<Long> getNetPortListInfoIdsByRackId(Integer rackId){
		return mapper.getNetPortListInfoIdsByRackId(rackId);
	}

	@Override
    public List<TreeNode> getTree(String roomIds,String locationId) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        List<ZbMachineroom> zbMachinerooms = null;
		List<Map<String, Object>> idcDevices = null;
		List<String> roomIdList = new ArrayList<>();
		Map<String,Object> mapQ = new HashMap<>();
		if (roomIds != null && !"".equals(roomIds) && !"null".equals(roomIds)) {
			roomIdList = Arrays.asList(roomIds.split("_"));
            mapQ.put("roomIdList",roomIdList);
		}
		if(locationId != null && !"".equals(locationId) && !"null".equals(locationId)){
            mapQ.put("locationId",locationId);
        }
		zbMachinerooms = zbMachineroomMapper.queryZbMachineroomListByIds(mapQ);
        //机房
        for (ZbMachineroom zbMachineroom : zbMachinerooms) {
            TreeNode room = new TreeNode();
            room.setId("room_" + zbMachineroom.getId());
            room.setName(zbMachineroom.getSitename());
            room.setSelf(zbMachineroom);
            room.setParentId(null);
            treeNodes.add(room);
        }
		//设备
		mapQ.put("isvirtualdevice",1);
        mapQ.put("deviceclass",1);
		idcDevices = idcDeviceMapper.queryListByRoomIds(mapQ);
		for (Map<String, Object> map : idcDevices) {
			TreeNode d = new TreeNode();
			d.setId("device_" + map.get("DEVICEID"));
			d.setName(map.get("NAME").toString());
			d.setSelf(map);
			d.setParentId("room_" + map.get("MID"));
			treeNodes.add(d);
		}
		removeDuplicate(treeNodes);
		List<TreeNode> treeNodes1 = TreeBuilder.buildListToTree(treeNodes);
		return treeNodes1;
	}

	public List removeDuplicate(List<TreeNode> list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	@Override
    public List<Map<String, Object>> getNetportsByRoomId(Map<String,Object> map) {
		return mapper.getNetportsByRoomId(map);
	}

	/*修改端口状态 map中key:id-端口ID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
	@Override
	public void updatePortStatusByPortIds(List<Map<String, Object>> list, Long deviceid) throws Exception {
		if (list != null && list.size() > 0) {
			//修改端口状态
			mapper.updatePortStatusByPortIds(list);
			Long status = null;
			//查看当前设备可用端口数
			if (deviceid == null) {
				Long id = Long.parseLong(list.get(0).get("id").toString());
				NetPort prot = mapper.getModelById(id);
				deviceid = prot.getDeviceid();
				status = Long.parseLong(list.get(0).get("status").toString());
			}
			//获取当前设备的端口的占用情况
			Map<String,Object> paramap = mapper.queryPortTotalAndFreePortByDeviceId(deviceid);
			int portTotal = paramap.get("PORTCOUNT") == null ? 0 : Integer.parseInt(paramap.get("PORTCOUNT").toString());
			int freeportnum = paramap.get("FREEPORTNUM") == null ? 0 : Integer.parseInt(paramap.get("FREEPORTNUM").toString());
			if (freeportnum  > 0) {//当存在空闲端口时，修改设备状态为20-可用
				IdcDevice idcDevice = new IdcDevice();
				idcDevice.setDeviceid(deviceid);
				if (freeportnum<portTotal) {//可用
					idcDevice.setStatus(20L);
				} else {//空闲
					idcDevice.setStatus(40L);
				}
				idcDeviceMapper.updateByObject(idcDevice);
			} else {//当不存在空闲端口时，修改设备状态为60-在服
				IdcDevice idcDevice = new IdcDevice();
				idcDevice.setDeviceid(deviceid);
				idcDevice.setStatus(status);
				idcDeviceMapper.updateByObject(idcDevice);
			}
		}
	}


	/*获取客户所有占用端口*/
	@Override
	public List<Map<String, Object>> queryUseredPortByCustomerId(Map map) {
		return mapper.queryUseredPortByCustomerId(map);
	}

	/*获取客户所有占用端口 page */
	@Override
	public List<Map<String, Object>> queryUseredPortByCustomerIdPage(PageBean page, Object param) {
		page.setParams(MapHelper.queryCondition(param));
		return mapper.queryUseredPortByCustomerIdPage(page);
	}

	/*获取当前节点已经绑定的端口*/
	@Override
	public List<NetPort> queryBindedPortList(PageBean page, Object param) {
		page.setParams(MapHelper.queryCondition(param));
		return mapper.queryBindedPortList(page);
	}

	/*修改端口分配带宽  流程上选择端口后填入分配带宽值   调用接口*/
	@Override
	public void updateNetPortAssignations(List<Map<String, Object>> list) throws Exception {
		mapper.updateNetPortAssignations(list);
	}
}
