package com.JH.itBusi.business.deviceCap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.common.db.DataUtil;
import com.JH.dgather.frame.common.snmp.SnmpUserTargetBean;
import com.JH.dgather.frame.common.snmp.SnmpUtil;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.util.DateFormate;
import com.JH.dgather.frame.util.DateUtil;
import com.JH.itBusi.business.deviceCap.controller.bean.DeviceCapData;
import com.JH.itBusi.business.deviceCap.db.bean.FanCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.PwrCapBean;
import com.JH.itBusi.business.deviceCap.db.bean.TemperatureCapBean;

public class CapDataUtil {
	private Logger logger = Logger.getLogger(CapDataUtil.class);

	private DataUtil dataUtil = null;

	public CapDataUtil() {
		dataUtil = new DataUtil();
	}



	/**
	 * <code>getCapTaskObject</code> 根据任务ID，获取采集对象
	 * 
	 * @param taskId
	 *            任务ID
	 * @return void
	 */
	public void getCapTaskObject(int taskId, HashMap<Integer, TaskObjBean> hmTaskObjBean) {

		try {
			for (Entry<Integer, TaskObjBean> taskObjBean : hmTaskObjBean.entrySet()) {
				TaskObjBean bean = taskObjBean.getValue();
				bean.setGatherObj(dataUtil.getRoutById(bean.getGoid()));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 通过KPIID获取各种门限
	 * 
	 * @param kpiID
	 * @return
	 */

	public Map<String, Long> getBandWidthMap(int rout, List<String> portIdList) {
		if (portIdList == null || portIdList.size() == 0)
			return null;
		logger.info("rout id:" + rout + ";获取端口原始带宽时端口的id列表的大小：" + portIdList.size() + ";值是:" + portIdList.toString());
		String sql = "select portName,bandwidth from net_resportinfo where portName in " + portIdList.toString().replace("[", "(").replace("]", ") and delflag=0 and deviceid = " + rout);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = ConnectionManager.getConnection();
		Map<String, Long> res = new HashMap<String, Long>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				res.put(rs.getString("portName"), rs.getLong("bandwidth"));
			}
		} catch (Exception e) {
			logger.error("获取带宽失败，sql：" + sql, e);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return res;
	}

	/**
	 * 
	 * @param deviceCap
	 *            采集到的性能数据
	 * @return 是否保存成功
	 */
	public String saveCap(ArrayList<DeviceCapData> lsDeviceCapData, TaskBean tb) {
		Connection conn = ConnectionManager.getConnection();
		String time = null;
		if (conn == null) {
			logger.error("获取数据库连接失败！保存性能失败");
			return time;
		}
		try {
			if(tb.getKpiId()==19){
				time = saveCpuCap(lsDeviceCapData, conn);
			}else if(tb.getKpiId()==20){
				time = saveMemCap(lsDeviceCapData, conn);
			}else if(tb.getKpiId()==21){
				time = savePwrCap(lsDeviceCapData, conn);
			}else if(tb.getKpiId()==22){
				time = saveFanCap(lsDeviceCapData, conn);
			}else if(tb.getKpiId()==23){
				time = saveTemperature(lsDeviceCapData, conn);
			}
		} catch (Exception e) {
			logger.error("日常性能巡检保存分析后的性能数据失败:", e);
		} finally {
			ConnectionManager.closeConnection(conn);
		}

		return time;
	}

	/*
	 * 保存某设备的内存
	 */
	private String saveMemCap(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
		String sql = "insert into Net_cap_Mem(RecordTime,RoutId,UsedValue,id)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String sqlDelCurr = "delete from Net_cap_Mem_curr where RoutId=?";
		String sqlCurr = "insert into Net_cap_Mem_curr(RecordTime,RoutId,UsedValue)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?)";
		String time = null;
		String maxidsql = "select max(id) as id from Net_cap_mem";
		ResultSet rs = null;

		PreparedStatement ps = null, psCurr = null, psDelCurr = null,psMax = null;
		try {
			Iterator<DeviceCapData> it = lsDeviceCapData.iterator();
			DeviceCapData entry;
			ps = conn.prepareStatement(sql);
			psCurr = conn.prepareStatement(sqlCurr);
			psDelCurr = conn.prepareStatement(sqlDelCurr);
			psMax = conn.prepareStatement(maxidsql);
			rs = psMax.executeQuery();
			rs.next();
			long n = rs.getInt("id");
			while (it.hasNext()) {
				entry = it.next();
				if(entry.getExecTime()==null)
					continue;
				psDelCurr.setInt(1, entry.getDeviceId());
				psDelCurr.addBatch();

				time = entry.getExecTime();
				psCurr.setString(1, entry.getExecTime());
				psCurr.setInt(2, entry.getDeviceId());
				psCurr.setInt(3, entry.getMem());
				psCurr.addBatch();

				ps.setString(1, entry.getExecTime());
				ps.setInt(2, entry.getDeviceId());
				ps.setInt(3, entry.getMem());
				ps.setLong(4, ++n);
				ps.addBatch();

			}
			psDelCurr.executeBatch();
			ps.executeBatch();
			psCurr.executeBatch();
		} catch (SQLException e) {
			logger.error("插入Net_cap_Mem异常！", e);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeStatement(psCurr);
			ConnectionManager.closeStatement(psDelCurr);
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(psMax);
		}

		return time;
	}

	/**
	 * 保存某设备的CPU性能
	 * 
	 * @param deviceId
	 * @param cpu
	 */
	private String saveCpuCap(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
		String sql = "insert into Net_cap_Cpu(RecordTime,RoutId,ProcessValue,id)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String sqlDelCurr = "delete from Net_cap_Cpu_curr where RoutId=?";
		String sqlCurr = "insert into Net_cap_Cpu_curr(RecordTime,RoutId,ProcessValue)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?)";
		String time = null;
		String maxidsql = "select max(id) as id from Net_cap_Cpu";
		ResultSet rs = null;
		PreparedStatement ps = null, psCurr = null, psDelCurr = null,psMax = null;
		try {
			logger.info("CPU数据大小："+lsDeviceCapData.size());
			Iterator<DeviceCapData> it = lsDeviceCapData.iterator();
			DeviceCapData entry;
			psMax = conn.prepareStatement(maxidsql);
			rs = psMax.executeQuery();
			rs.next();
			long n = rs.getInt("id");
			ps = conn.prepareStatement(sql);
			psCurr = conn.prepareStatement(sqlCurr);
			psDelCurr = conn.prepareStatement(sqlDelCurr);
			while (it.hasNext()) {
				entry = it.next();
				logger.info("CPU数据："+entry.getCpu());
				if(entry.getExecTime()==null)
					continue;
				psDelCurr.setInt(1, entry.getDeviceId());
				psDelCurr.addBatch();

				psCurr.setString(1, entry.getExecTime());
				psCurr.setInt(2, entry.getDeviceId());
				psCurr.setInt(3, entry.getCpu());
				psCurr.addBatch();

				ps.setString(1, entry.getExecTime());
				ps.setInt(2, entry.getDeviceId());
				ps.setInt(3, entry.getCpu());
				ps.setLong(4, ++n);
				ps.addBatch();

			}
			psDelCurr.executeBatch();
			ps.executeBatch();
			psCurr.executeBatch();
		} catch (SQLException e) {
			logger.error("插入Net_cap_Cpu异常！异常原因：", e);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeStatement(psCurr);
			ConnectionManager.closeStatement(psDelCurr);
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(psMax);
		}

		return time;
	}

	/*
	 * 保存某设备的所有风扇模块信息
	 */
	private String saveFanCap(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
		String sql = "insert into Net_cap_fan(RecordTime,RoutId,FanCode,FanStatus)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String sqlDelCurr = "delete from Net_cap_fan_curr where RoutId=?";
		String sqlCurr = "insert into Net_cap_fan_curr(RecordTime,RoutId,FanCode,FanStatus)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String time = null;

		PreparedStatement ps = null, psCurr = null, psDelCurr = null;
		try {
			ps = conn.prepareStatement(sql);
			psCurr = conn.prepareStatement(sqlCurr);
			psDelCurr = conn.prepareStatement(sqlDelCurr);
			Iterator<DeviceCapData> itCap = lsDeviceCapData.iterator();
			DeviceCapData cData;
			ArrayList<FanCapBean> lsFanStatus;
			Iterator<FanCapBean> it;
			FanCapBean entry;
			// 将数据写入net_cap_fan
			while (itCap.hasNext()) {
				cData = itCap.next();
				lsFanStatus = cData.getFanStatus();
				if (lsFanStatus != null && lsFanStatus.size() != 0) {
					try {
						it = lsFanStatus.iterator();
						psDelCurr.setInt(1, cData.getDeviceId());
						psDelCurr.addBatch();

						while (it.hasNext()) {
							entry = it.next();

							time = cData.getExecTime();
							psCurr.setString(1, cData.getExecTime());
							psCurr.setInt(2, cData.getDeviceId());
							psCurr.setInt(3, entry.getIndex());
							psCurr.setInt(4, entry.getStatusValue());
							psCurr.addBatch();

							ps.setString(1, cData.getExecTime());
							ps.setInt(2, cData.getDeviceId());
							ps.setInt(3, entry.getIndex());
							ps.setInt(4, entry.getStatusValue());
							ps.addBatch();

						}
						psDelCurr.executeBatch();
						ps.executeBatch();
						psCurr.executeBatch();
					} catch (SQLException e) {
						logger.error("保存设备id=" + cData.getDeviceId() + "的风扇信息失败", e);
					}
				}
			}
		} catch (SQLException e1) {
			logger.error("存储风扇信息获取ps失败！", e1);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeStatement(psCurr);
			ConnectionManager.closeStatement(psDelCurr);
		}
		return time;
	}

	/*
	 * 保存某设备温度数据
	 */
	private String saveTemperature(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
		String sql = "insert into Net_cap_temperature(RecordTime,RoutId,ModuleIndex,Temperature,CurrVal,id)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)";
		String sqlDelCurr = "delete from Net_cap_temperature_curr where RoutId=?";
		String sqlCurr = "insert into Net_cap_temperature_curr(RecordTime,RoutId,ModuleIndex,Temperature,CurrVal)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?)";
		String time = null;
		String maxidsql = "select max(id) as id from Net_cap_temperature";
		ResultSet rs = null;
		PreparedStatement ps = null, psCurr = null, psDelCurr = null,psMax = null;

		try {
			psMax = conn.prepareStatement(maxidsql);
			rs = psMax.executeQuery();
			rs.next();
			long n = rs.getInt("id");
			ps = conn.prepareStatement(sql);
			psCurr = conn.prepareStatement(sqlCurr);
			psDelCurr = conn.prepareStatement(sqlDelCurr);

			Iterator<DeviceCapData> itCap = lsDeviceCapData.iterator();
			DeviceCapData cData;
			ArrayList<TemperatureCapBean> lsTemperature;
			Iterator<TemperatureCapBean> it;
			TemperatureCapBean entry;

			while (itCap.hasNext()) {
				cData = itCap.next();
				lsTemperature = cData.getTemperature();
				if (lsTemperature != null && lsTemperature.size() != 0) {
					try {
						it = lsTemperature.iterator();
						psDelCurr.setInt(1, cData.getDeviceId());
						psDelCurr.addBatch();

						while (it.hasNext()) {
							entry = it.next();

							time = cData.getExecTime();
							psCurr.setString(1, cData.getExecTime());
							psCurr.setInt(2, cData.getDeviceId());
							psCurr.setInt(3, entry.getIndex());
							psCurr.setInt(4, entry.getTemperatureValue());
							psCurr.setInt(5, entry.getTemperatureValue());
							psCurr.addBatch();

							ps.setString(1, cData.getExecTime());
							ps.setInt(2, cData.getDeviceId());
							ps.setInt(3, entry.getIndex());
							ps.setInt(4, entry.getTemperatureValue());
							ps.setInt(5, entry.getTemperatureValue());
							ps.setLong(6, ++n);
							ps.addBatch();

						}
						psDelCurr.executeBatch();
						ps.executeBatch();
						psCurr.executeBatch();
					} catch (SQLException e) {
						logger.error("保存设备id=" + cData.getDeviceId() + "温度性能失败", e);
					}
				}
			}
		} catch (SQLException e1) {
			logger.error("存储温度获取ps失败！", e1);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeStatement(psCurr);
			ConnectionManager.closeStatement(psDelCurr);
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(psMax);
		}
		return time;
	}

	/*
	 * 保存某设备电源性能数据
	 */
	private String savePwrCap(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
		String sql = "insert into Net_cap_PWR(RecordTime,RoutId,PWRCode,PWRStatus)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String sqlDelCurr = "delete from Net_cap_PWR_curr where RoutId=?";
		String sqlCurr = "insert into Net_cap_PWR_curr(RecordTime,RoutId,PWRCode,PWRStatus)values(TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		String time = null;

		PreparedStatement ps = null, psCurr = null, psDelCurr = null;

		try {
			ps = conn.prepareStatement(sql);
			psCurr = conn.prepareStatement(sqlCurr);
			psDelCurr = conn.prepareStatement(sqlDelCurr);

			// 将数据写入net_cap_pwr中
			Iterator<DeviceCapData> itCap = lsDeviceCapData.iterator();
			DeviceCapData cData;
			Iterator<PwrCapBean> it;
			PwrCapBean entry;
			ArrayList<PwrCapBean> lsPwrStatus;
			while (itCap.hasNext()) {
				cData = itCap.next();
				lsPwrStatus = cData.getPwrStatus();
				if (lsPwrStatus != null && lsPwrStatus.size() != 0) {
					try {
						it = lsPwrStatus.iterator();

						psDelCurr.setInt(1, cData.getDeviceId());
						psDelCurr.addBatch();

						while (it.hasNext()) {
							entry = it.next();

							time = cData.getExecTime();
							psCurr.setString(1, cData.getExecTime());
							psCurr.setInt(2, cData.getDeviceId());
							psCurr.setInt(3, entry.getIndex());
							psCurr.setInt(4, entry.getStatus());
							psCurr.addBatch();

							ps.setString(1, cData.getExecTime());
							ps.setInt(2, cData.getDeviceId());
							ps.setInt(3, entry.getIndex());
							ps.setInt(4, entry.getStatus());
							ps.addBatch();

						}
						psDelCurr.executeBatch();
						ps.executeBatch();
						psCurr.executeBatch();
					} catch (SQLException e) {
						logger.error("保存设备id=" + cData.getDeviceId() + "的电源性能数据失败", e);
					}
				}

			}
		} catch (SQLException e1) {
			logger.error("存储Net_cap_PWR获取ps失败！", e1);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeStatement(psCurr);
			ConnectionManager.closeStatement(psDelCurr);
		}
		return time;
	}

	//更新设备的运行时间
	public void updateDeviceSystime(ArrayList<DeviceCapData> lsDeviceCapData, Connection conn) {
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
	}

	public void updateDeviceSystime(HashMap<Integer, TaskObjBean> lsDeviceCapData) {
		String SQL = "update net_routinfo set sysuptime=? where routid=?";
		PreparedStatement ps = null;
		DeviceInfoBean device = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			for (Entry<Integer, TaskObjBean> dcd : lsDeviceCapData.entrySet()) {
				device = (DeviceInfoBean) dcd.getValue().getGatherObj();
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
		} catch (Exception e) {
			logger.error("更新设备: " + device.getRoutName() + " 运行时间时异常！", e);
		}  finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);
			device = null;
		}
	}


//提取临时任务设备，考虑放入公共方法
	public ArrayList<DeviceInfoBean> getCapTaskTmpdev(int taskid) {
		ArrayList<DeviceInfoBean> lsDevice = new ArrayList<DeviceInfoBean>();
		DeviceInfoBean device = null;
		String sql = "select a.GoId,a.id,b.deviceid,b.DeviceClass,b.RoutType,b.RoutName,b.IPAddress,b.modelname,b.LoginUser"
				+ ",b.LoginPsw,b.privilegePsw,b.factory"
				+ ",b.SnmpPort,b.SnmpCommunity,b.TelnetPort,b.telnetVPNparm,b.snmpversion,b.snmppassword,b.snmpscttype,b.snmpkey,b.SnmpKeyScttype "
				+ " from TASK_TEMPOBJ a join Net_device b  on a.goId=b.deviceid where a.taskid=? and b.status=0";
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
				device.setModelName(rs.getString("modelname"));
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



}
