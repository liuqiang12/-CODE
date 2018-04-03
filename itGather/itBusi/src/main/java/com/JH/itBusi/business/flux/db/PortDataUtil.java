package com.JH.itBusi.business.flux.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.db.ConnMgr_redis;
import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.json.JsonUtil;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;
import com.JH.itBusi.comm.db.PortCapBean;
import com.JH.itBusi.util.DateFormate;
import com.JH.itBusi.util.DateUtil;
import com.JH.itBusi.util.StringUtil;


public class PortDataUtil {
	Logger logger = Logger.getLogger(PortDataUtil.class);
 //?muyp data storage needs to be optimized
/*	public void portFluxSave(
			HashMap<Integer, HashMap<Long, PortCapBean>> lsGatherPortOctets,Date execTime) {

		Connection conn = null;
		PreparedStatement insertHisSQL_PS = null;
		PreparedStatement insertCurrSQL_PS = null;
		PreparedStatement updateCurrSQL_PS = null;
		PreparedStatement updateCurrSQL_PS2 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i=0;
		String tablename = "net_cap_port_"+DateFormate.getCurrYear(execTime)+"_"+String.valueOf(DateFormate.getCurrMonth(execTime)+100).substring(1);;
		//String tablename = "net_cap_port_2018_02";
		String maxidsql = "select max(id) as id from "+tablename;
		String insertHisSQL = "insert into "+tablename+" (RecordTime,RoutId,portId,PortName,InputFlux,OutputFlux,ifInnucastPkts,PortStatus,id) values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?)";
		String insertCurrSQL = "insert into net_cap_port_curr (RecordTime,RoutId,portId,PortName,InputFlux,OutputFlux,ifInnucastPkts,PortStatus,InputFluxCount,OutputFluxCount,PortIndex,addTime,RecordTimeL) values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?)";
		String updateCurrSQL = "update net_cap_port_curr set RecordTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'), InputFlux=?, OutputFlux=?, InputFluxCount=?, OutputFluxCount=?, addTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'), PortStatus=?, ifInnucastPkts=? ,PortName=? ,RecordTimeL=? where RoutId=? and portindex=? ";
		String updateCurrSQL2 = "update net_cap_port_curr set InputFlux=-1, OutputFlux=-1, PortStatus=? where RoutId=? and portindex=?";
		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(maxidsql);
			rs = ps.executeQuery();
			rs.next();
			long n = rs.getInt("id");
			insertHisSQL_PS = conn.prepareStatement(insertHisSQL);
			insertCurrSQL_PS = conn.prepareStatement(insertCurrSQL);
			updateCurrSQL_PS = conn.prepareStatement(updateCurrSQL);
			updateCurrSQL_PS2 = conn.prepareStatement(updateCurrSQL2);
  			String addTime = DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
			for (Entry<Integer, HashMap<Long, PortCapBean>> portOctetsHm : lsGatherPortOctets.entrySet()) {
				int deviceid = portOctetsHm.getKey();
				for (Entry<Long, PortCapBean> entry : portOctetsHm.getValue().entrySet()) {
					++i;
					PortCapBean portCapBean = entry.getValue();
					if (portCapBean.getDaoFlag() == 0) {// 正常数据，既更新当前表，也插入历史表
						insertHisSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						insertHisSQL_PS.setInt(2, deviceid);
						insertHisSQL_PS.setInt(3, portCapBean.getPortId());
						insertHisSQL_PS.setString(4, portCapBean.getPortName());
						insertHisSQL_PS.setLong(5, portCapBean.getInputFlux());
						insertHisSQL_PS.setLong(6, portCapBean.getOutputFlux());
						insertHisSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
						insertHisSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
						insertHisSQL_PS.setLong(9,++n);
						insertHisSQL_PS.addBatch();
						updateCurrSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						updateCurrSQL_PS.setLong(2, portCapBean.getInputFlux());
						updateCurrSQL_PS.setLong(3, portCapBean.getOutputFlux());
						updateCurrSQL_PS.setDouble(4, portCapBean.getInputFluxCount());
						updateCurrSQL_PS.setDouble(5, portCapBean.getOutputFluxCount());
						updateCurrSQL_PS.setString(6, addTime);
						updateCurrSQL_PS.setInt(7, (int) portCapBean.getPortStatus());
						updateCurrSQL_PS.setLong(8, portCapBean.getIfInnucastPkts());
						updateCurrSQL_PS.setString(9, portCapBean.getPortName());
						updateCurrSQL_PS.setLong(10,portCapBean.getRecordTimeL());
						updateCurrSQL_PS.setInt(11, deviceid);
						updateCurrSQL_PS.setLong(12, entry.getKey());
						updateCurrSQL_PS.addBatch();

					} else if (portCapBean.getDaoFlag() == 1) { //表示为第一次数据，只insert当前表
						insertCurrSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						insertCurrSQL_PS.setInt(2, deviceid);
						insertCurrSQL_PS.setLong(3, portCapBean.getPortId());
						insertCurrSQL_PS.setString(4, portCapBean.getPortName());
						insertCurrSQL_PS.setLong(5, portCapBean.getInputFlux());
						insertCurrSQL_PS.setLong(6, portCapBean.getOutputFlux());
						insertCurrSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
						insertCurrSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
						insertCurrSQL_PS.setDouble(9, portCapBean.getInputFluxCount());
						insertCurrSQL_PS.setDouble(10, portCapBean.getOutputFluxCount());
						insertCurrSQL_PS.setLong(11, entry.getKey());
						insertCurrSQL_PS.setString(12, addTime);
						insertCurrSQL_PS.setLong(13,portCapBean.getRecordTimeL() );
						insertCurrSQL_PS.addBatch();
					} else if (portCapBean.getDaoFlag() == 2) { //表示数据异常，只update当前表的SNMP采集数据信息
						updateCurrSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						updateCurrSQL_PS.setLong(2, portCapBean.getInputFlux());
						updateCurrSQL_PS.setLong(3, portCapBean.getOutputFlux());
						updateCurrSQL_PS.setDouble(4, portCapBean.getInputFluxCount());
						updateCurrSQL_PS.setDouble(5, portCapBean.getOutputFluxCount());
						updateCurrSQL_PS.setString(6, addTime);
						updateCurrSQL_PS.setInt(7, (int) portCapBean.getPortStatus());
						updateCurrSQL_PS.setLong(8, portCapBean.getIfInnucastPkts());
						updateCurrSQL_PS.setString(9, portCapBean.getPortName());
						updateCurrSQL_PS.setLong(10,portCapBean.getRecordTimeL());
						updateCurrSQL_PS.setInt(11, deviceid);
						updateCurrSQL_PS.setLong(12, entry.getKey());
						updateCurrSQL_PS.addBatch();
						insertHisSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						insertHisSQL_PS.setInt(2, deviceid);
						insertHisSQL_PS.setInt(3, portCapBean.getPortId());
						insertHisSQL_PS.setString(4, portCapBean.getPortName());
						insertHisSQL_PS.setLong(5, -1);
						insertHisSQL_PS.setLong(6, -1);
						insertHisSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
						insertHisSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
						insertHisSQL_PS.setLong(9,++n);
						insertHisSQL_PS.addBatch();

					} else if(portCapBean.getDaoFlag() == 4) { // 表示端口DOWN， 只UPDATE相关数据
						updateCurrSQL_PS2.setInt(1, (int) portCapBean.getPortStatus());
						updateCurrSQL_PS2.setInt(2, deviceid);
						updateCurrSQL_PS2.setLong(3, entry.getKey());
						updateCurrSQL_PS2.addBatch();
						insertHisSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						insertHisSQL_PS.setInt(2, deviceid);
						insertHisSQL_PS.setInt(3, portCapBean.getPortId());
						insertHisSQL_PS.setString(4, portCapBean.getPortName());
						insertHisSQL_PS.setLong(5, -1);
						insertHisSQL_PS.setLong(6, -1);
						insertHisSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
						insertHisSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
						insertHisSQL_PS.setLong(9,++n);
						insertHisSQL_PS.addBatch();
					}
				}
			}
			insertCurrSQL_PS.executeBatch();
			logger.error("更新保存当前数据");
			updateCurrSQL_PS.executeBatch();
			updateCurrSQL_PS2.executeBatch();
			logger.error("保存历史数据");
			insertHisSQL_PS.executeBatch();
		} catch (Exception e) {
			logger.error("流量数据入库时异常！", e);
		} finally {
			ConnectionManager.closeStatement(insertHisSQL_PS);
			ConnectionManager.closeStatement(insertCurrSQL_PS);
			ConnectionManager.closeStatement(updateCurrSQL_PS);
			ConnectionManager.closeStatement(updateCurrSQL_PS2);
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
		}
			
	}*/
/*
	public void updateDeviceSystime(HashMap<Integer, TaskObjBean> hmObj) {
		String SQL = "update net_routinfo set sysuptime=? where routid=?";
		PreparedStatement ps = null;
		DeviceInfoBean device = null;
		try {
			for (DeviceCapData dcd : lsDeviceCapData) {
				device = dcd.getDevice();
				try {
					ps = conn.prepareStatement(SQL);
					ps.setDouble(1, device.getSysUpTime());
					ps.setInt(2, device.getDeviceId());
					ps.execute();
					logger.info("设备: " + device.getRoutName() + " 运行时间: " + device.getSysUpTime());
				} catch (Exception e) {
					logger.error("更新设备: " + device.getRoutName() + " 运行时间时异常！", e);
				} finally {
					ConnectionManager.closeStatement(ps);
					device = null;
				}
			}
		} finally {
			ConnectionManager.closeStatement(ps);
			device = null;
		}
		
	}*/
	
	public void portFluxSave(
			HashMap<Integer, HashMap<Long, PortCapBean>> lsGatherPortOctets,Date execTime) {
		Connection conn = null;
		PreparedStatement insertHisSQL_PS = null;
		PreparedStatement insertCurrSQL_PS = null;
		String tablename = "net_cap_port_"+DateFormate.getCurrYear(execTime)+"_"+String.valueOf(DateFormate.getCurrMonth(execTime)+100).substring(1);;
		String insertHisSQL = "insert into "+tablename+" (RecordTime,RoutId,portId,PortName,InputFlux,OutputFlux,ifInnucastPkts,PortStatus,id) values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?)";
		String insertCurrSQL = "insert into net_cap_port_curr (RecordTime,RoutId,portId,PortName,InputFlux,OutputFlux,ifInnucastPkts,PortStatus,InputFluxCount,OutputFluxCount,PortIndex,addTime,RecordTimeL) values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?)";
		try {
			conn = ConnectionManager.getConnection();
			insertHisSQL_PS = conn.prepareStatement(insertHisSQL);
			insertCurrSQL_PS = conn.prepareStatement(insertCurrSQL);
 			String addTime = DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
			for (Entry<Integer, HashMap<Long, PortCapBean>> portOctetsHm : lsGatherPortOctets.entrySet()) {
				int deviceid = portOctetsHm.getKey();
				for (Entry<Long, PortCapBean> entry : portOctetsHm.getValue().entrySet()) {
					PortCapBean portCapBean = entry.getValue();
					if(portCapBean.getDaoFlag() == 1){//新数据插入curr表
						insertCurrSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
						insertCurrSQL_PS.setInt(2, deviceid);
						insertCurrSQL_PS.setLong(3, portCapBean.getPortId());
						insertCurrSQL_PS.setString(4, portCapBean.getPortName());
						insertCurrSQL_PS.setLong(5, portCapBean.getInputFlux());
						insertCurrSQL_PS.setLong(6, portCapBean.getOutputFlux());
						insertCurrSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
						insertCurrSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
						insertCurrSQL_PS.setDouble(9, portCapBean.getInputFluxCount());
						insertCurrSQL_PS.setDouble(10, portCapBean.getOutputFluxCount());
						insertCurrSQL_PS.setLong(11, entry.getKey());
						insertCurrSQL_PS.setString(12, addTime);
						insertCurrSQL_PS.setLong(13,portCapBean.getRecordTimeL() );
						insertCurrSQL_PS.addBatch();
					}
					insertHisSQL_PS.setString(1, DateFormate.dateToStr(execTime, "yyyy-MM-dd HH:mm:ss"));
					insertHisSQL_PS.setInt(2, deviceid);
					insertHisSQL_PS.setInt(3, portCapBean.getPortId());
					insertHisSQL_PS.setString(4, portCapBean.getPortName());
					insertHisSQL_PS.setLong(5, portCapBean.getInputFlux());
					insertHisSQL_PS.setLong(6, portCapBean.getOutputFlux());
					insertHisSQL_PS.setLong(7, portCapBean.getIfInnucastPkts());
					insertHisSQL_PS.setInt(8, (int) portCapBean.getPortStatus());
					insertHisSQL_PS.setString(9, StringUtil.get32UUID());
					insertHisSQL_PS.addBatch();

				}
				}
			logger.error("开始保存流量数据");
			insertCurrSQL_PS.executeBatch();
			insertCurrSQL_PS.clearBatch();
			insertHisSQL_PS.executeBatch();
			insertHisSQL_PS.clearBatch();
			logger.error("保存历史表数据完成");
		} catch (Exception e) {
			logger.error("流量数据入库时异常！", e);
		} finally {
			ConnectionManager.closeStatement(insertHisSQL_PS);
			ConnectionManager.closeStatement(insertCurrSQL_PS);
			ConnectionManager.closeConnection(conn);
		}
			
	}
	
	
//提取需要采集流量的端口，要求管理状态和操作状态均为1=UP的才需要采集
	public HashMap<Integer, HashMap<Long, PortCapBean>> getPortResInfo() {
		HashMap<Integer, HashMap<Long, PortCapBean>> portcapHs = new HashMap<Integer, HashMap<Long, PortCapBean>>();
		PortCapBean port;
		int deviceid;
		HashMap<Long, PortCapBean> portHs;
		Connection conn = null;
		PreparedStatement ps= null;
		ResultSet rs  = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String sql = "select a.deviceid,a.portid,a.portname ,a.portindex,a.bandwidth from net_port a  join net_device b on a.deviceid=b.deviceid "
				+ "where b.status=0 and a.PortActive=1 and a.adminStatus=1   and distributedNodeID =? order by a.deviceid";
		String sql1 = "select a.deviceid,a.portid,a.portname ,a.portindex,a.bandwidth from net_port a  join net_busi_port b on a.portid=b.portid"
					+" where (a.PortActive<>1 or a.adminStatus<>1)  ";//and a.delflag=0";
		try{
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, GloableDataArea.distributedNode);
			rs = ps.executeQuery();
			ps1 = conn.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			while(rs.next()){
				port = new PortCapBean();
				deviceid = rs.getInt("deviceid");
				portHs = portcapHs.get(deviceid);
				if(portHs==null){
					portHs = new HashMap<Long, PortCapBean>();
					portcapHs.put(deviceid, portHs);
				}
				port.setPortId( rs.getInt("portid"));
				port.setPortIndex(rs.getLong("portindex"));
				port.setPortName(rs.getString("portname"));
				port.setBandWidth(rs.getDouble("bandwidth"));
				portHs.put(port.getPortIndex(), port);
			}
			while(rs1.next()){
				portHs = portcapHs.get(rs1.getInt("deviceid"));
				if(portHs==null)
					continue;
				port = new PortCapBean();
				port.setPortId(rs1.getInt("portid"));
				port.setPortIndex(rs1.getLong("portindex"));
				port.setPortName(rs1.getString("portname"));
				port.setBandWidth(rs1.getDouble("bandwidth"));
				portHs.put(port.getPortIndex(), port);
			}

		}catch(Exception e){
			logger.error("提取端口资源数据失败",e);
			e.printStackTrace();
		}finally{
			ConnectionManager.release(conn, ps, rs);
			ConnectionManager.release(null, ps1, rs1);
		}
			return portcapHs;
	}
/*
 * 提取所有端口的curr流量
 * */
	public HashMap<Integer, HashMap<Long, PortCapBean>> getPortOctets() {
		HashMap<Integer, HashMap<Long, PortCapBean>> portcapHs = new HashMap<Integer, HashMap<Long, PortCapBean>>();
		PortCapBean port;
		int deviceid;
		HashMap<Long, PortCapBean> portHs;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.routId,a.portid,a.portname ,a.portindex,a.inputfluxCount,a.outputfluxcount,"
				+ "a.recordTime,a.portstatus,a.recordTimeL from net_cap_port_curr a ,net_device b where a.routId=b.deviceid and b.status=0  and b.distributedNodeID =? order by b.deviceid";
		try{
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(sql);
		    ps.setInt(1, GloableDataArea.distributedNode);
			rs = ps.executeQuery();
			while(rs.next()){
				port = new PortCapBean();
				deviceid = rs.getInt("routid");
				portHs = portcapHs.get(deviceid);
				if(portHs==null){
					portHs = new HashMap<Long, PortCapBean>();
					portcapHs.put(deviceid, portHs);
				}
				port.setPortId(rs.getInt("portid"));
				port.setPortIndex(rs.getLong("portindex"));
				port.setPortName(rs.getString("portname"));
				port.setRecordTime((DateFormate.strToDate(rs.getString("recordTime"), "yyyy-MM-dd HH:mm:ss")));
				port.setOutputFluxCount(rs.getLong("outputfluxcount"));
				port.setInputFluxCount(rs.getLong("inputfluxCount"));
				port.setPortStatus(rs.getInt("portstatus"));
				port.setRecordTimeL(rs.getLong("recordTimeL"));
				portHs.put(port.getPortIndex(), port);
			}
		}catch(Exception e){
			logger.error("提取端口资源数据失败");
			e.printStackTrace();
		}finally{
			ConnectionManager.release(conn, ps, rs);
		}
			return portcapHs;
		
	}

	
	/**
	 * 
	 * @param updateStatusPortList
	 */
	public synchronized void updatePortStatus(List<PortCapBean> updateStatusPortList) {
		if(updateStatusPortList.size() == 0)
			return;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE net_port SET PortActive=?,AdminStatus=? where portid=? ";
		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(sql);
			for (PortCapBean portCapBean : updateStatusPortList) {
				int index = 1;
				ps.setInt(1, portCapBean.getPortStatus());
				ps.setInt(2, portCapBean.getAdminStatus());
				ps.setInt(3, portCapBean.getPortId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (Exception e) {
			logger.error("更新端口状态入库时异常！", e);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
		}
	}
//当前流量保存到redis
	public void portCurrFluxSave(
			HashMap<Integer, HashMap<Long, PortCapBean>> lsGatherPortOctets,
			Date exectime) {
		Jedis redisconn = null;
		try{
			redisconn  = ConnMgr_redis.getJedis();
			for (Entry<Integer, HashMap<Long, PortCapBean>> portOctetsHm : lsGatherPortOctets.entrySet()) {
				int deviceid = portOctetsHm.getKey();
				for (Entry<Long, PortCapBean> entry : portOctetsHm.getValue().entrySet()) {
					PortCapBean portCapBean = entry.getValue();
					portCapBean.setAddtime(DateFormate.dateToStr(exectime, null));
					portCapBean.setRoutId(deviceid);
					if(portCapBean!=null)
						redisconn.hset("net_port_curr", String.valueOf(portCapBean.getPortId()), JsonUtil.bean2json(portCapBean).toString());
				}
			}
			logger.info("success save to redis");

		}catch(Exception e){
			logger.error("save redis fail", e);
		}finally{
			ConnMgr_redis.returnResource(redisconn);
		}
	
	}
	

//修改端口状态
	public void portStatusSave(HashMap<Integer, List<PortCapBean>> updatePortHs) {
		
		if((updatePortHs==null)||(updatePortHs.size() == 0))
			return;
		Connection conn = null;
		PreparedStatement ps = null;
		List<PortCapBean> portls = null;
		String sql = "UPDATE net_port SET PortActive=?,AdminStatus=? where portid=? ";
		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(sql);
			for (Entry<Integer, List<PortCapBean>> portOctetsHm : updatePortHs.entrySet()) {
				portls = portOctetsHm.getValue();
				if((portls==null)||(portls.size()==0))
					continue;
				for (PortCapBean portCapBean : portls) {
					ps.setInt(1, portCapBean.getPortStatus());
					ps.setInt(2, portCapBean.getAdminStatus());
					ps.setInt(3, portCapBean.getPortId());
					ps.addBatch();
					logger.debug("端口状态变化："+portCapBean.getPortId()+" PortActive="+portCapBean.getPortStatus()+" AdminStatus="+portCapBean.getAdminStatus());
				}
			}
			ps.executeBatch();
		} catch (Exception e) {
			logger.error("更新端口状态入库时异常！", e);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
		}
		
	}
}
