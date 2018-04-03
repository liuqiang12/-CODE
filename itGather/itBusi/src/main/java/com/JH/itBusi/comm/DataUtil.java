package com.JH.itBusi.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.comm.db.KPIAlarmLevelConfig;
import com.JH.itBusi.comm.db.PortCapBean;
import com.JH.itBusi.util.DateFormate;

public class DataUtil {
	Logger logger = Logger.getLogger(DataUtil.class);
	//根据任务提供的对像ID获取设备的详细信息
	public void getTaskObject(HashMap<Integer, TaskObjBean> hmTaskObjBean) {
		com.JH.dgather.frame.common.db.DataUtil du = null;
		try {
			for (Entry<Integer, TaskObjBean> taskObjBean : hmTaskObjBean.entrySet()) {
				TaskObjBean bean = taskObjBean.getValue();
				bean.setGatherObj(du.getRoutById(bean.getGoid()));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
	}

	public Collection<KPIAlarmLevelConfig> getAlarmLevelConfig(int kpiId) {
		Collection<KPIAlarmLevelConfig> collection = new ArrayList<KPIAlarmLevelConfig>();
		//提取设备的阀值配置,设备的阀值放到前面，方便任务告警的判断
		//getAlarmLevelConfigOfDevice(kpiId,collection);
		//提取全局阀值配置
		String sql = "select id,KPIId,ThresholdLimit,ThresholdLowLimit,alarmlevel,description  from Net_KPIAlarmLevelConfig where kpiId=" + kpiId + " order by alarmlevel desc";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		KPIAlarmLevelConfig klc = null;
		logger.debug("日常性能巡检查询门限sql:"+sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				klc = new KPIAlarmLevelConfig();
				klc.setID(rs.getInt("id"));
				klc.setKpiID(rs.getInt("KPIId"));
				klc.setThresholdLimit(rs.getLong("thresholdlimit"));
				klc.setThresholdLowLimit(rs.getLong("ThresholdLowLimit"));
				klc.setAlarmLevel(rs.getInt("alarmlevel"));
				klc.setDescription(rs.getString("description"));
				collection.add(klc);
			}
		} catch (Exception e) {
			logger.error("日常性能巡检查询门限出现错误,sql=" + sql);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return collection;
	}
	//先废弃
	private void getAlarmLevelConfigOfDevice(int kpiId,Collection<KPIAlarmLevelConfig> collection) {
		String sql = "SELECT t.id, deviceId, KPIId, ThresholdLimit, ThresholdLowLimit, alarmlevel FROM net_kpi_schemeinfo_detail t JOIN net_kpi_schemeinfo s ON t.schemeId = s.schemeId JOIN net_kpi_scheme_device d ON d.schemeId = s.schemeId WHERE kpiId = "+kpiId+" ORDER BY alarmlevel DESC";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		KPIAlarmLevelConfig klc = null;
		logger.debug("日常性能巡检查询门限sql:"+sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				klc = new KPIAlarmLevelConfig();
				klc.setID(rs.getInt("id"));
				klc.setKpiID(rs.getInt("KPIId"));
				klc.setThresholdLimit(rs.getLong("thresholdlimit"));
				klc.setThresholdLowLimit(rs.getLong("ThresholdLowLimit"));
				klc.setAlarmLevel(rs.getInt("alarmlevel"));
				klc.setDeviceID(rs.getInt("deviceId"));
				collection.add(klc);
			}
		} catch (Exception e) {
			logger.error("日常性能巡检查询门限出现错误,sql=" + sql);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
	}
	
	
	/* 提取所有端口的curr流量
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
//保存net_cap_port_curr的数据
		public void savePortCurr(
				HashMap<Integer, HashMap<Long, PortCapBean>> portOctets_curr_Hs) {
			Connection conn = null;
			PreparedStatement updateCurrSQL_PS = null;
			String updateCurrSQL = "update net_cap_port_curr set RecordTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'), InputFlux=?, OutputFlux=?, InputFluxCount=?, OutputFluxCount=?, addTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'), PortStatus=?, ifInnucastPkts=? ,PortName=? ,RecordTimeL=? where RoutId=? and portindex=? ";
			try {
				conn = ConnectionManager.getConnection();
				updateCurrSQL_PS = conn.prepareStatement(updateCurrSQL);
	  			String addTime = DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
				for (Entry<Integer, HashMap<Long, PortCapBean>> portOctetsHm : portOctets_curr_Hs.entrySet()) {
					int deviceid = portOctetsHm.getKey();
					for (Entry<Long, PortCapBean> entry : portOctetsHm.getValue().entrySet()) {
						PortCapBean portCapBean = entry.getValue();
						updateCurrSQL_PS.setString(1, addTime);
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
					}
				}
				updateCurrSQL_PS.executeBatch();
				updateCurrSQL_PS.clearBatch();
		}catch(Exception e){
			logger.error("更新net_cap_port_curr表失败。"+e.getMessage());
		}finally {
			ConnectionManager.release(conn, updateCurrSQL_PS, null);
		}


		}
}
