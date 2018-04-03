package com.JH.itBusi.business.resource.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.bean.PortInfoBean;
import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.common.snmp.SnmpUserTargetBean;
import com.JH.dgather.frame.common.snmp.SnmpUtil;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;



public class ResDataUtil {
	Logger logger = Logger.getLogger(ResDataUtil.class.getName());
	/**
	 * 提取资源采集临时任务的临时对象
	 * 
	 * @param taskid
	 * @return 设备信息 ，Integer = 设备ID
	 */
	public ArrayList<DeviceInfoBean> getResTaskTmpdev(int taskid){
		ArrayList<DeviceInfoBean> lsDevice = new ArrayList<DeviceInfoBean>();
		DeviceInfoBean device = null;
		String sql = "select a.GoId,a.id,b.deviceid,b.DeviceClass,b.RoutType,b.RoutName,b.IPAddress,b.LoginUser"
				+ ",b.LoginPsw,b.privilegePsw,b.factory"
				+ ",b.SnmpPort,b.SnmpCommunity,b.TelnetPort,b.telnetVPNparm,b.snmpversion,b.snmppassword,b.snmpscttype,b.snmpkey,b.SnmpKeyScttype "
				+ "from TASK_TEMPOBJ a join Net_device b  on a.goId=b.deviceid where a.taskid=? and b.status=0";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, taskid);
			rs = ps.executeQuery();
			SnmpUserTargetBean userTargetBean = null;
			while (rs.next()) {
				device = new DeviceInfoBean();
				device.setRoutId(rs.getInt("deviceid"));
				device.setFactory(rs.getInt("factory"));
				device.setIPAddress(rs.getString("IPAddress"));
				device.setLoginPsw(rs.getString("LoginPsw"));
				device.setLoginUser(rs.getString("LoginUser"));
				// device.setPriviledge(rs.getString(""));
				device.setPrivilegePsw(rs.getString("privilegePsw"));
				device.setRoutName(rs.getString("RoutName"));
				device.setRoutType(rs.getInt("RoutType"));
				device.setSnmpCommunity(rs.getString("SnmpCommunity"));
				device.setSnmpPort(rs.getInt("SnmpPort"));
				device.setTelnetPort(rs.getInt("TelnetPort"));
				//device.setModelName(rs.getString("ModelName"));
				device.setTelnetVPNparm(rs.getString("telnetVPNparm"));
				device.setDeviceClass(rs.getInt("DeviceClass"));
				
				device.setSnmpVersion(rs.getInt("snmpversion"));
				if(rs.getInt("snmpversion")==SnmpUtil.SNMP_V3){
					userTargetBean = new SnmpUserTargetBean();
					userTargetBean.setUserName(rs.getString("SnmpCommunity"));
					userTargetBean.setAuthProtocol(rs.getString("snmpscttype"));
					userTargetBean.setAuthPasshrase(rs.getString("snmppassword"));
					userTargetBean.setPrivProtocol(rs.getString("SnmpKeyScttype"));
					userTargetBean.setPrivPassphrase(rs.getString("snmpkey"));
					device.setUserTargetBean(userTargetBean);
				}else{
					device.setUserTargetBean(null);
				}
				lsDevice.add(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(taskid + "模型提取任务模型错误");
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return lsDevice;
		
	}
	
	public HashMap<Integer, TaskObjBean> getResTaskTmpObject(int taskid) {
		HashMap<Integer, TaskObjBean> lsDevice = new HashMap<Integer, TaskObjBean>();
		TaskObjBean gatherObj = null;
		DeviceInfoBean device = null;
		String sql = "select a.GoId,a.id,b.routid,b.DeviceClass,b.RoutType,b.RoutName,b.IPAddress,b.LoginUser"
				+ ",b.LoginPsw,b.privilegePsw,b.factory,b.StartFileGetOrder,b.RunFielGetOrder,b.ConfigFileName"
				+ ",b.SnmpPort,b.SnmpCommunity,b.ChassisSerial,b.TelnetPort,b.telnetVPNparm,b.ModelName,b.snmpversion,b.snmppassword,b.snmpscttype,b.snmpkey,b.SnmpKeyScttype "
				+ "from TASK_TEMPOBJ a join Net_RoutInfo b " + " on a.goId=b.routid " + "where a.taskid=" + taskid
				+ " and b.status=0";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			SnmpUserTargetBean userTargetBean = null;
			while (rs.next()) {
				
				gatherObj = new TaskObjBean();
				device = new DeviceInfoBean();
				gatherObj.setGatherObj(device);
				//xdwang 2012-5-22
				gatherObj.setGatherId(rs.getInt("id"));
				gatherObj.setGoid(rs.getInt("goid"));
				gatherObj.setTaskid(taskid);
				
				device.setRoutId(rs.getInt("routid"));
				device.setFactory(rs.getInt("factory"));
				device.setIPAddress(rs.getString("IPAddress"));
				device.setLoginPsw(rs.getString("LoginPsw"));
				device.setLoginUser(rs.getString("LoginUser"));
				// device.setPriviledge(rs.getString(""));
				device.setPrivilegePsw(rs.getString("privilegePsw"));
				device.setRoutName(rs.getString("RoutName"));
				device.setRoutType(rs.getInt("RoutType"));
				device.setSnmpCommunity(rs.getString("SnmpCommunity"));
				device.setSnmpPort(rs.getInt("SnmpPort"));
				device.setTelnetPort(rs.getInt("TelnetPort"));
				device.setModelName(rs.getString("ModelName"));
				device.setTelnetVPNparm(rs.getString("telnetVPNparm"));
				device.setDeviceClass(rs.getInt("DeviceClass"));
				
				device.setSnmpVersion(rs.getInt("snmpversion"));
				if(rs.getInt("snmpversion")==SnmpUtil.SNMP_V3){
					userTargetBean = new SnmpUserTargetBean();
					userTargetBean.setUserName(rs.getString("SnmpCommunity"));
					userTargetBean.setAuthProtocol(rs.getString("snmpscttype"));
					userTargetBean.setAuthPasshrase(rs.getString("snmppassword"));
					userTargetBean.setPrivProtocol(rs.getString("SnmpKeyScttype"));
					userTargetBean.setPrivPassphrase(rs.getString("snmpkey"));
					device.setUserTargetBean(userTargetBean);
				}else{
					device.setUserTargetBean(null);
				}
				
				lsDevice.put(device.getDeviceId(), gatherObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(taskid + "模型提取任务模型错误");
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return lsDevice;
	}
	
	/**
	 * 提取资源采集任务的设备
	 * 
	 * @param taskId
	 *            = 任务模型ID
	 * @return 任务对象
	 */
	public HashMap<Integer, TaskObjBean> getResTaskObject(int taskId) {
		HashMap<Integer, TaskObjBean> lsDevice = new HashMap<Integer, TaskObjBean>();
		TaskObjBean gatherObj = null;
		DeviceInfoBean device = null;
		String sql = "select a.GoId,a.id,b.routid,b.DeviceClass,b.RoutType,b.RoutName,b.IPAddress,b.LoginUser"
				+ ",b.LoginPsw,b.privilegePsw,b.factory,b.StartFileGetOrder,b.RunFielGetOrder,b.ConfigFileName"
				+ ",b.SnmpPort,b.SnmpCommunity,b.ChassisSerial,b.TelnetPort,b.telnetVPNparm,b.ModelName,b.snmpversion,b.snmppassword,b.snmpscttype,b.snmpkey,b.SnmpKeyScttype "
				+ "from TASK_PUBLICOBJ a join Net_RoutInfo b " + " on a.goId=b.routid " + "where a.taskId=" + taskId
				+ " and b.status=0";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			SnmpUserTargetBean userTargetBean = null;
			while (rs.next()) {
				
				gatherObj = new TaskObjBean();
				device = new DeviceInfoBean();
				gatherObj.setGatherObj(device);
				//xdwang 2012-5-22
				gatherObj.setGatherId(rs.getInt("id"));
				gatherObj.setGoid(rs.getInt("goid"));
				gatherObj.setTaskid(taskId);
				
				device.setRoutId(rs.getInt("routid"));
				device.setFactory(rs.getInt("factory"));
				device.setIPAddress(rs.getString("IPAddress"));
				device.setLoginPsw(rs.getString("LoginPsw"));
				device.setLoginUser(rs.getString("LoginUser"));
				// device.setPriviledge(rs.getString(""));
				device.setPrivilegePsw(rs.getString("privilegePsw"));
				device.setRoutName(rs.getString("RoutName"));
				device.setRoutType(rs.getInt("RoutType"));
				device.setSnmpCommunity(rs.getString("SnmpCommunity"));
				device.setSnmpPort(rs.getInt("SnmpPort"));
				device.setTelnetPort(rs.getInt("TelnetPort"));
				device.setModelName(rs.getString("modelname"));
				device.setTelnetVPNparm(rs.getString("telnetVPNparm"));
				device.setDeviceClass(rs.getInt("DeviceClass"));
				
				device.setSnmpVersion(rs.getInt("snmpversion"));
				if(rs.getInt("snmpversion")==SnmpUtil.SNMP_V3){
					userTargetBean = new SnmpUserTargetBean();
					userTargetBean.setUserName(rs.getString("SnmpCommunity"));
					userTargetBean.setAuthProtocol(rs.getString("snmpscttype"));
					userTargetBean.setAuthPasshrase(rs.getString("snmppassword"));
					userTargetBean.setPrivProtocol(rs.getString("SnmpKeyScttype"));
					userTargetBean.setPrivPassphrase(rs.getString("snmpkey"));
					device.setUserTargetBean(userTargetBean);
				}else{
					device.setUserTargetBean(null);
				}
				
				lsDevice.put(device.getDeviceId(), gatherObj);
			}
			
		} catch (Exception e) {
			logger.error(taskId + "模型提取任务模型错误");
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return lsDevice;
	}
	
	//获取制定端口的信息
		public HashMap<Integer, HashMap<Long, PortInfoBean>> getPortInfo(String devs) {
			HashMap<Integer, HashMap<Long, PortInfoBean>> devportHs = new HashMap<Integer, HashMap<Long, PortInfoBean>>();
			HashMap<Long, PortInfoBean> portHs;
			PortInfoBean port;
			int deviceid;
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select a.portId,a.deviceId,a.portActive,a.portName,a.portindex,a.alias,a.descr,a.bandwidth,a.adminStatus,a.porttype "
					+ " from net_port a,net_device b where a.deviceid=b.deviceid and ','||?||',' like '%,'||b.deviceid||',%' and distributedNodeID=?  order by b.deviceid";
			try{
				ps = conn.prepareStatement(sql);
				ps.setString(1, devs);
				ps.setInt(2,  GloableDataArea.distributedNode);//仅仅提取采集器管理范围的设备
				rs = ps.executeQuery();
				while(rs.next()){
					deviceid = rs.getInt("deviceid");
					portHs = devportHs.get(deviceid);
					if(portHs==null){
						portHs = new HashMap<Long,PortInfoBean>();
						devportHs.put(deviceid, portHs);
					}
					port = new PortInfoBean();
					portHs.put(rs.getLong("portIndex"), port);
					port.setPortid(rs.getInt("portId"));
					port.setDeviceId(deviceid);
					port.setPortActive(rs.getInt("portActive"));
					port.setPortName(rs.getString("portName"));
					port.setPortIndex(rs.getLong("portIndex"));
					port.setAlias(rs.getString("alias"));
					port.setDescr(rs.getString("descr"));
					port.setBandWidth(rs.getFloat("bandwidth"));//数据库中带宽单位是M
					port.setAdminStatus(rs.getInt("adminStatus"));
					port.setIfType(rs.getInt("portType"));
				}
			}catch(Exception e){
				logger.error("提取端口信息失败");
				e.printStackTrace();
			}finally{
				ConnectionManager.release(conn, ps, rs);
			}
			return devportHs;
		}

	
//获取当前的所有端口的信息
	public HashMap<Integer, HashMap<Long, PortInfoBean>> getPortInfo() {
		HashMap<Integer, HashMap<Long, PortInfoBean>> devportHs = new HashMap<Integer, HashMap<Long, PortInfoBean>>();
		HashMap<Long, PortInfoBean> portHs;
		PortInfoBean port;
		int deviceid;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.portId,a.deviceId,a.portActive,a.portName,a.portindex,a.alias,a.descr,a.bandwidth,a.adminStatus,a.portType "
				+ " from net_port a,net_device b where a.deviceid=b.deviceid and b.status=0 and distributedNodeID=? order by b.deviceid";
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,  GloableDataArea.distributedNode);//仅仅提取采集器管理范围的设备
			rs = ps.executeQuery();
			while(rs.next()){
				deviceid = rs.getInt("deviceid");
				portHs = devportHs.get(deviceid);
				if(portHs==null){
					portHs = new HashMap<Long,PortInfoBean>();
					devportHs.put(deviceid, portHs);
				}
				port = new PortInfoBean();
				portHs.put(rs.getLong("portIndex"), port);
				port.setPortid(rs.getInt("portId"));
				port.setDeviceId(deviceid);
				port.setPortActive(rs.getInt("portActive"));
				port.setPortName(rs.getString("portName"));
				port.setPortIndex(rs.getLong("portIndex"));
				port.setAlias(rs.getString("alias"));
				port.setDescr(rs.getString("descr"));
				port.setBandWidth(rs.getFloat("bandwidth"));//数据库中带宽单位是M
				port.setAdminStatus(rs.getInt("adminStatus"));
				port.setIfType(rs.getInt("porttype"));
			}
		}catch(Exception e){
			logger.error("提取端口信息失败");
			e.printStackTrace();
		}finally{
			ConnectionManager.release(conn, ps, rs);
		}
		return devportHs;
	}
//更新设备信息
	public void updateDevice(ArrayList<DeviceInfoBean> updateDevLs) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		String sql = "update net_device set RoutName=?,SysDescr=? where deviceid=?";
		try{
			ps = conn.prepareStatement(sql);
			for(DeviceInfoBean device:updateDevLs ){
				ps.setString(1, device.getRoutName());
				ps.setString(2, device.getSysDescr());
				ps.setInt(3, device.getRoutId());
				ps.addBatch();
			}
			ps.executeBatch();
			
		}catch(Exception e){
			logger.error("更新设备信息失败！");
			e.printStackTrace();
		}finally{
			ConnectionManager.release(conn, ps, null);
		}		
	}
//更新端口信息
	public void updatePort(ArrayList<PortInfoBean> updatePortLs) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		String sql = "update net_port set  portActive=?,alias=?,descr=?,bandwidth=?,adminStatus=?,portname=?,porttype=? ,delflag=0 "
					+" where portid=? and deviceid = ?";
		try{
			ps = conn.prepareStatement(sql);
			for(PortInfoBean port:updatePortLs ){
				ps.setInt(1, port.getPortActive());
				ps.setString(2, port.getAlias());
				ps.setString(3, port.getDescr());
				ps.setDouble(4, port.getBandWidth());
				ps.setInt(5,port.getAdminStatus());
				ps.setString(6, port.getPortName());
				ps.setInt(7,port.getIfType());
				ps.setInt(8, port.getPortid());
				ps.setInt(9, port.getDeviceId());
				ps.addBatch();
			}
			ps.executeBatch();
			
		}catch(Exception e){
			logger.error("更新设备端口信息失败！");
			e.printStackTrace();
		}finally{
			ConnectionManager.release(conn, ps, null);
		}		
		
	}
//新增端口
	public void insertPort(ArrayList<PortInfoBean> insertPortLs) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into net_port(deviceId,portindex,portname,alias,descr,portActive,adminStatus,bandwidth,porttype,portid)"
				+ " values(?,?,?,?,?,?,?,?,?,?) ";
		String maxidsql = "select max(portid) as id from net_port";
		ResultSet rs = null;
		PreparedStatement psmax = null;
		try{
			psmax = conn.prepareStatement(maxidsql);
			rs = psmax.executeQuery();
			rs.next();
			long n = rs.getInt("id");
			ps = conn.prepareStatement(sql);
			for(PortInfoBean port:insertPortLs ){
				ps.setInt(1, port.getDeviceId());
				ps.setLong(2, port.getPortIndex());
				ps.setString(3, port.getPortName());
				ps.setString(4, port.getAlias());
				ps.setString(5,port.getDescr());
				ps.setInt(6, port.getPortActive());
				ps.setInt(7, port.getAdminStatus());
				ps.setDouble(8, port.getBandWidth());
				ps.setInt(9, port.getIfType());
				ps.setLong(10, ++n);
				ps.addBatch();
			}
			ps.executeBatch();
			
		}catch(Exception e){
			logger.error("增加设备端口信息失败！",e);
			e.printStackTrace();
		}finally{
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(psmax);
			ConnectionManager.release(conn, ps, null);
		}		
	}
	
	//删除端口，仅仅是端口打标记
		public void delPort(ArrayList<PortInfoBean> delPortLs) {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = null;
			String sql = "update net_port set  delflag=?"
						+" where portid=? and deviceid = ?";
			try{
				ps = conn.prepareStatement(sql);
				for(PortInfoBean port:delPortLs ){
					ps.setInt(1, 1);
					ps.setInt(2, port.getPortid());
					ps.setInt(3, port.getDeviceId());
					ps.addBatch();
				}
				ps.executeBatch();
				
			}catch(Exception e){
				logger.error("更新设备端口信息失败！");
				e.printStackTrace();
			}finally{
				ConnectionManager.release(conn, ps, null);
			}		
			
		}
		
}
