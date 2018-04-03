/*
 * @(#)DataUtil.java 01/29/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.bean.NetSysArea;
import com.JH.dgather.frame.common.cmd.bean.FlowPointBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;

public class DataUtil {
	private Logger logger = Logger.getLogger(DataUtil.class);

	// private static DataUtil du = new DataUtil();

	public DataUtil() {
	}

	/*
	 * public static DataUtil getDataUtil() { if (du == null) { du = new
	 * DataUtil(); } return du; }
	 */
	/**
	 * <code>getRoutsByIdList</code> 根据一组ID列表获取一组设备
	 * 
	 * @param routIdList
	 *            设备ID列表
	 * @return HashMap<Integer, DeviceInfoBean> 设备列表, 以routId作为key
	 */
	public HashMap<Integer, DeviceInfoBean> getRoutsByIdList(
			ArrayList<Integer> routIdList) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer, DeviceInfoBean> deviceLs = new HashMap<Integer, DeviceInfoBean>();
		try {
			if (routIdList.size() > 0) {
				conn = ConnectionManager.getConnection();
				String SQL = "select * from Net_device where deviceid in (";
				for (int i = 0; i < routIdList.size(); i++) {
					if (i != 0) {
						SQL += "," + routIdList.get(i);
					} else {
						SQL += routIdList.get(i);
					}
				}
				SQL += ")";
				ps = conn.prepareStatement(SQL);
				rs = ps.executeQuery();
				while (rs.next()) {
					DeviceInfoBean deviceinfo = new DeviceInfoBean();
					deviceinfo.setIPAddress(rs.getString("IPAddress"));
					deviceinfo.setFactory(rs.getInt("factory"));
					deviceinfo.setLoginPsw(rs.getString("LoginPsw"));
					deviceinfo.setLoginUser(rs.getString("LoginUser"));
					deviceinfo.setLoginModel(rs.getInt("loginModel"));
					deviceinfo.setOwnerprovince(rs.getInt("OwnerProvince"));
					deviceinfo.setParentroutid(rs.getInt("ParentRoutID"));
					deviceinfo.setPrivilegePsw(rs.getString("privilegePsw"));
					deviceinfo.setRoutId(rs.getInt("deviceid"));
					deviceinfo.setRoutName(rs.getString("RoutName"));
					deviceinfo.setRoutType(rs.getInt("RoutType"));
					deviceinfo.setSnmpCommunity(rs.getString("SnmpCommunity"));
					deviceinfo.setSnmpPort(rs.getInt("SnmpPort"));
					deviceinfo.setTelnetPort(rs.getInt("telnetPort"));
					deviceinfo.setModelName(rs.getString("ModelName"));
					// add by xdwang 2013-1-30
					deviceinfo.setSysUpTime(rs.getDouble("sysUptime"));
					// 添加VPN跳转的参数
					deviceinfo.setTelnetVPNparm(rs.getString("telnetVPNparm"));

					deviceinfo.setSnmpVersion(rs.getInt("snmpversion"));
					deviceLs.put(deviceinfo.getRoutId(), deviceinfo);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return deviceLs;
	}

	/*提取所有可用设备信息
	 * */
	public ArrayList<DeviceInfoBean> getAllRout() {
		// 链接
		ArrayList<DeviceInfoBean> deviceLs = null;
		DeviceInfoBean deviceinfo = null;
		Connection conn = ConnectionManager.getConnection();
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select deviceid,RoutType,RoutName,IPAddress,modelname,"
					+ "LoginUser,LoginPsw,privilegePsw,factory,SnmpVersion,SnmpPort,SnmpCommunity,SnmpPassword,"
					+ "SnmpScttype,SnmpKeyScttype,SnmpKey,LoginModel,TelnetPort,telnetVPNparm,"
					+ "sysUptime,sysDescr from Net_device where status=0 and distributedNodeID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, GloableDataArea.distributedNode);
			rs = ps.executeQuery();
			if(rs!=null){ 
				deviceLs = new ArrayList<DeviceInfoBean>();
				while (rs.next()) {
					deviceinfo = new DeviceInfoBean();
					deviceLs.add(deviceinfo);
					deviceinfo.setRoutId(rs.getInt("deviceid"));
					deviceinfo.setRoutType(rs.getInt("RoutType"));
					deviceinfo.setRoutName(rs.getString("RoutName"));
					deviceinfo.setIPAddress(rs.getString("IPAddress"));
					deviceinfo.setModelName(rs.getString("modelname"));
					deviceinfo.setLoginUser(rs.getString("LoginUser"));
					deviceinfo.setLoginPsw(rs.getString("LoginPsw"));
					deviceinfo.setPrivilegePsw(rs.getString("privilegePsw"));
					deviceinfo.setFactory(rs.getInt("factory"));
					deviceinfo.setSnmpVersion(rs.getInt("SnmpVersion"));
					deviceinfo.setSnmpPort(rs.getInt("SnmpPort"));
					deviceinfo.setSnmpCommunity(rs.getString("SnmpCommunity"));
					deviceinfo.setLoginModel(rs.getInt("LoginModel"));
					deviceinfo.setTelnetPort(rs.getInt("TelnetPort"));
					// 添加VPN跳转的参数
					deviceinfo.setTelnetVPNparm(rs.getString("telnetVPNparm"));
					deviceinfo.setSysUpTime(rs.getDouble("sysUptime"));
					deviceinfo.setSysDescr(rs.getString("sysDescr"));
			
				}
			}
		} catch(SQLException e) {
			logger.error("获取所有设备异常：", e);
		} finally {// 关闭结果集,会话和链接
			ConnectionManager.release(conn, ps, rs);
		}
		return deviceLs;
	}
	
	
	/**
	 * <code>getRoutById</code> 按照ROUTID返回某一设备的全部信息
	 * 
	 * @param routid
	 *            设备ID
	 * @return DeviceInfoBean 设备信息BEAN
	 */
	public DeviceInfoBean getRoutById(int routid) {
		// 链接
		DeviceInfoBean deviceinfo = null;
		Connection conn = ConnectionManager.getConnection();
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select deviceid,RoutType,RoutName,OwnerProvince,ParentRoutId,IPAddress,LoginUser,LoginPsw,privilegePsw,factory,SnmpVersion,SnmpPort,SnmpCommunity,SnmpPassword,SnmpScttype,SnmpKeyScttype,SnmpKey,ModelName,LoginModel,TelnetPort,telnetVPNparm,sysUptime "
					+ "from Net_device where deviceid=?";
			/**
			 * 获取设备信息
			 */
			ps = conn.prepareStatement(sql);
			ps.setInt(1, routid);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				deviceinfo = new DeviceInfoBean();
				deviceinfo.setRoutId(rs.getInt("deviceid"));
				deviceinfo.setRoutType(rs.getInt("RoutType"));
				deviceinfo.setRoutName(rs.getString("RoutName"));
				deviceinfo.setOwnerprovince(rs.getInt("OwnerProvince"));
				deviceinfo.setParentroutid(rs.getInt("ParentRoutId"));
				deviceinfo.setIPAddress(rs.getString("IPAddress"));
				deviceinfo.setLoginUser(rs.getString("LoginUser"));
				deviceinfo.setLoginPsw(rs.getString("LoginPsw"));
				deviceinfo.setPrivilegePsw(rs.getString("privilegePsw"));
				deviceinfo.setFactory(rs.getInt("factory"));
				deviceinfo.setSnmpVersion(rs.getInt("SnmpVersion"));
				deviceinfo.setSnmpPort(rs.getInt("SnmpPort"));
				deviceinfo.setSnmpCommunity(rs.getString("SnmpCommunity"));
				deviceinfo.setModelName(rs.getString("ModelName"));
				deviceinfo.setLoginModel(rs.getInt("LoginModel"));
				deviceinfo.setTelnetPort(rs.getInt("TelnetPort"));
				// 添加VPN跳转的参数
				deviceinfo.setTelnetVPNparm(rs.getString("telnetVPNparm"));
				deviceinfo.setSysUpTime(rs.getDouble("sysUptime"));

			}
		} catch (SQLException e) {
			logger.error("日常巡检获取设备id为:" + routid + "的设备异常：", e);
		} finally {// 关闭结果集,会话和链接
			ConnectionManager.release(conn, ps, rs);;
		}
		return deviceinfo;
	}

	/**
	 * <code>getTelnetFlowPoint</code> 提取某设备的TELNET登录信息
	 * 
	 * @param conn
	 *            数据库连接
	 * @param deviceId
	 *            设备ID
	 * @return ArrayList<FlowPointBean> 登录流程步骤列表
	 */
	public ArrayList<FlowPointBean> getTelnetFlowPoint(int deviceId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<FlowPointBean> flowPointList = new ArrayList<FlowPointBean>();
		Connection conn = ConnectionManager.getConnection();
		try {
			String sql = "select f.SequenceNo,f.prompt,f.fillParam from net_telnetflow f,net_routinfo r where r.TelnetFlowId=f.TelnetFlowID and r.RoutId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deviceId);
			rs = ps.executeQuery();
			while ((rs != null) && (rs.next())) {
				FlowPointBean flowPoint = new FlowPointBean();
				flowPointList.add(flowPoint);
				flowPoint.setSequenceNo(rs.getInt("SequenceNo"));
				flowPoint.setPrompt(rs.getString("prompt"));
				flowPoint.setFillParam(rs.getString("fillParam"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取" + deviceId + "设备的TELNET登录流程信息失败");
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
		}
		return flowPointList;
	}

	/**
	 * modify by gamesdoa 2012/6/6 修改跳转设备的读取方式 <code>getTelnetJumpDevice</code>
	 * 获取跳转设备信息
	 * 
	 * @param conn
	 *            数据库连接
	 * @param deviceId
	 *            设备ID
	 * @return 路转设备信息
	 */
	public DeviceInfoBean getTelnetJumpDevice(int deviceId) {
		PreparedStatement ps = null;
		DeviceInfoBean device = null;
		ResultSet rs = null;
		Connection conn = ConnectionManager.getConnection();
		try {// 目前仅提取第一个跳转设备，也就是只支持一级跳
			String sql = "select b.deviceid,b.IPAddress,b.LoginUser,b.LoginPsw,b.privilegePsw,b.TelnetPort from net_device b where deviceid = (SELECT max(c.JumpDeviceId) from net_device b,net_telnetjump c  where b.deviceid ="
					+ deviceId
					+ " and b.TelnetJumpId=c.TelnetJumpId and  sequenceNo=1);";
			// logger.info("获取跳转设备信息sql:"+sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if ((rs != null) && (rs.next())) {
				device = new DeviceInfoBean();
				device.setRoutId(rs.getInt("deviceid"));
				device.setIPAddress(rs.getString("IPAddress"));
				device.setLoginUser(rs.getString("LoginUser"));
				device.setLoginPsw(rs.getString("LoginPsw"));
				device.setPrivilegePsw(rs.getString("privilegePsw"));
				device.setTelnetPort(rs.getInt("TelnetPort"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取" + deviceId + "设备的TELNET跳转设备信息失败");
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
		}
		return device;
	}

	/**
	 * 打包时候ROUT的归属地区查询，为打包做准备
	 */
	public NetSysArea getAreaByAreaID(int areaid) {
		NetSysArea nsa = null;
		Connection conn = ConnectionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select AreaId,AreaPid,AreaName,AreaRange,AreaSort,Description,DelFlag from Sys_Area where Areaid="
					+ areaid;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				nsa = new NetSysArea();
				nsa.setAreaid(rs.getInt("AreaId"));
				nsa.setAreapid(rs.getInt("AreaPid"));
				nsa.setAreaname(rs.getString("AreaName"));
				nsa.setArearange(rs.getInt("AreaRange"));
				nsa.setAreasort(rs.getInt("AreaSort"));
				nsa.setDescription(rs.getString("Description"));
				nsa.setDelflag(rs.getInt("delflag"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(stmt);
			ConnectionManager.closeConnection(conn);
		}
		return nsa;
	}

	/**
	 * 判断 该设备下不同指令 的最后一次巡检结果 是否还有处于告警状态的信息。
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param exctime
	 *            当天时间
	 * @author TangWei
	 * @return
	 */
	public boolean proAdvancedcruseAlarm(int deviceId, String exctime) {
		boolean flag = false;
		String sql = "select dictatevalue,cmdresult from net_plancmdresult where resultId in("
				+ "select max(resultId) from net_plancmdresult where deviceId=?  and exectime like "
				+ "'"
				+ exctime
				+ "%' group by dictatevalue) and cmdresult in(2,4)";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deviceId);
			res = ps.executeQuery();
			if (res.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, res);
		}
		return flag;
	}

	/**
	 * 根据源设备ID 判断 该设备下的所有链路的最后一次巡检结果 是否还有处于告警状态的信息。
	 * 
	 * @param deviceId
	 *            源设备ID
	 * @param exctime
	 *            当天时间
	 * @author TangWei
	 * @return
	 */
	public boolean proLineCheckAlarm(int deviceId, String exctime) {
		boolean flag = false;
		String sql = "select t.lineId,t.linecheckTime,t.warningFlag from net_linecheckresult t, (select  s.lineId, max(s.linecheckTime) maxchecktime"
				+ " from net_linecheckresult s where s.lineCheckTime like '"
				+ exctime
				+ "%'  and  s.lineId in(select lineId from net_lineinfo l where "
				+ "l.srcId=?) group by s.lineId) tab where t.lineId=tab.lineId and t.linecheckTime=tab.maxchecktime and  t.warningFlag not in (0,8)";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deviceId);
			res = ps.executeQuery();
			if (res.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, res);
		}
		return flag;
	}

	public void updateSnmpVersion(int routId, int snmpVersion) {
		String sql = "update net_device set snmpversion=" + snmpVersion
				+ " where deviceid=" + routId;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.execute();
				logger.info("设备ID: " + routId + " SNMP版本: " + snmpVersion);
			} catch (Exception e) {
				logger.error("更新设备ID: " + routId + " SNMP版本时异常！", e);
			} finally {
				ConnectionManager.closeStatement(ps);
			}
		} finally {
			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeStatement(ps);
		}
	}
}
