package com.JH.dgather.frame.eventalarmsender.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.eventalarmsender.bean.UserBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.AlarmInfoBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.RuleBean;
import com.JH.dgather.frame.eventalarmsender.db.bean.WebServiceAlarmSentBean;
import com.JH.dgather.frame.eventalarmsender.exception.EventAlarmSenderException;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.eventmanager.service.EventHandle;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.DateFormate;

public class DataUtil {
	Logger logger = Logger.getLogger(DataUtil.class.getName());

	/**
	 * <code>getAllRules</code> 获取当前表中所有的告警发送规则
	 * @return 规则bean集合
	 */
	public HashMap<Integer, RuleBean> getAllRules() throws EventAlarmSenderException {
		HashMap<Integer, RuleBean> ruleHm = new HashMap<Integer, RuleBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (GloableDataArea.areaid != null){
				//从省公司获取规则
			    conn = ConnectionManager.getConnection("province");
			}else{
				conn = ConnectionManager.getConnection();
			}
			if (conn != null) {
				//String sql = "SELECT * FROM net_alarmsend_config AS a ORDER BY a.configId DESC";
				String sql = null;
				if (GloableDataArea.areaid != null) {
					// 只提取该区域下的sendtype为3的cmpp规则
					sql = "SELECT * FROM zy_alarmsend_config AS a WHERE a.status=0 and ((SendType = "
							+ GloableDataArea.ALARM_SENDTYPE_CMPP
							+ " and areaId = "
							+ GloableDataArea.areaid
							+ ") or SendType <> "
							+ GloableDataArea.ALARM_SENDTYPE_CMPP
							+ ") ORDER BY a.configId DESC";
				} else {
					sql = "SELECT * FROM zy_alarmsend_config AS a WHERE a.status=0 ORDER BY a.configId DESC";
				}
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				RuleBean rb = null;
				while (rs.next()) {
					rb = new RuleBean();
					int key = rs.getInt("configId");
					rb.setConfigId(key);
					rb.setAlarmLevel(rs.getString("AlarmLevel"));
					rb.setAlarmType(rs.getInt("AlarmType"));
					rb.setCreateUserName(rs.getString("CreatUserName"));
					rb.setDeviceIds(rs.getString("DeviceIds"));
					rb.setUserIds(rs.getString("UserIds"));
					rb.setSendType(rs.getInt("SendType"));
					rb.setStatus(rs.getInt("status"));
					if(GloableDataArea.areaid != null){
						rb.setTelnum(rs.getString("telnum"));
						if(rb.getDeviceIds()== null){
							rb.setDeviceIds("");
						}
						if(rb.getUserIds() == null){
							rb.setUserIds("");
						}
						String sendKPIId = rs.getString("sendKPIId");
						if(sendKPIId != null){
							String[] kpiStrings = sendKPIId.split(",");
							for (String str : kpiStrings) {
                                rb.putSendKPIId(str.trim());
							}
						}
					}
					ruleHm.put(key, rb);
				}
			} else {
				logger.error("获取数据连接对象失败!");
			}
		} catch (Exception e) {
			logger.error("获取规则列表发生异常！", e);
			throw new EventAlarmSenderException("获取规则列表发生异常！");
		} finally {
			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeResultSet(rs);
		}

		return ruleHm;
	}

	/**
	 * 如果规则是链路，则通过链路ID查找符合的源设备ID
	 * @param ruleBean
	 * @throws EventAlarmSenderException
	 */
	public void getDevicesByLines(RuleBean ruleBean) throws EventAlarmSenderException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sr = "";
		try {
			conn = ConnectionManager.getConnection();
			if (conn != null) {
				String lineids = ruleBean.getDeviceIds();
				if (lineids.endsWith(",")) {
					lineids = lineids.substring(0, lineids.length() - 1);
				}
				String sql = "select b.routid from net_routinfo b where b.routid in (select a.srcid from net_lineinfo a where a.lineid in (" + lineids + "))";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					sr += rs.getInt("routid") + ",";
				}
				ruleBean.setObjIds(sr);
			}
		} catch (Exception e) {
			logger.error("通过链路ID提取源设备列表异常！", e);
			throw new EventAlarmSenderException("通过链路ID提取源设备列表异常！");
		} finally {
			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeResultSet(rs);
		}
	}

	/**
	 * <code>getUserInfo</code> 根据用户ID获取用户信息列表
	 * @param userIdsAry
	 * @return
	 */
	public HashMap<String, UserBean> getUserInfo(String[] userIdsAry) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, UserBean> userHm = new HashMap<String, UserBean>();

		try {
			conn = ConnectionManager.getConnection();
			if (conn != null) {
				String sql = "SELECT * FROM sys_user a WHERE userId in (";
				int i = 0;
				for (String s : userIdsAry) {
					if (i > 0) {
						sql += ",'" + s + "'";
					} else {
						sql += "'" + s + "'";
					}
					i++;
				}
				sql += ")";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					UserBean userBean = new UserBean();
					userBean.setMail(rs.getString("Email"));
					userBean.setTel(rs.getString("telphone"));
					userHm.put(rs.getString("userId"), userBean);
				}
			} else {
				logger.error("获取数据连接对象失败!");
			}
		} catch (Exception e) {
			logger.error("获取用户邮件异常！", e);
		} finally {
			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeResultSet(rs);
		}

		return userHm;
	}

	
	public String getUserEmail(int ruleId) {
		String result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();
			if (conn != null) {
				String sql = "SELECT emailUser from net_email_receiver where ruleId="+ruleId;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					result = rs.getString("emailUser");
				}
			} else {
				logger.error("获取数据连接对象失败!");
			}
		} catch (Exception e) {
			logger.error("获取规则列表发生异常！", e);
		} finally {
			ConnectionManager.closeConnection(conn);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeResultSet(rs);
		}

		return result;
	}
	
	public void insertAlarmInfoList(List<AlarmInfoBean> aiLs) {
		Connection conn = null;
		conn = ConnectionManager.getConnection();
		try {
			if (conn != null) {
				for (AlarmInfoBean bean : aiLs) {
					try {
						insertAlarmInfo(conn, bean);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else {
				logger.error("获取数据连接对象失败!");
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
	}

	/**
	 * <code>insertAlarmInfo</code> 将发送的告警信息进行备份
	 * @param alarmInfoBean
	 */
	private void insertAlarmInfo(Connection conn, AlarmInfoBean alarmInfoBean) throws EventAlarmSenderException {
		PreparedStatement ps = null;
		try {
			//String sql = "INSERT INTO net_alarmsend_info(SendType, SendResult, SendTime, SendMembers, UnSendMembers, AlarmType, sendinfo) VALUES(?,?,?,?,?,?,?)";
			String sql = "INSERT INTO zy_alarmsendinfo(SendType, SendResult, SendTime, SendMembers, UnSendMembers, AlarmType, sendinfo, configid) VALUES(?,?,?,?,?,?,?,?)";
			String d = DateFormate.dateToStr(alarmInfoBean.getSendTime(), "yyyy-MM-dd HH:mm:ss");
			ps = conn.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, alarmInfoBean.getSendType());
			if (alarmInfoBean.getSendMembers() != null && !"".equals(alarmInfoBean.getSendMembers())) {
				alarmInfoBean.setSendResult(1);
			} else {
				alarmInfoBean.setSendResult(2);
			}
			ps.setInt(2, alarmInfoBean.getSendResult());
			ps.setString(3, d);
			ps.setString(4, alarmInfoBean.getSendMembers());
			ps.setString(5, alarmInfoBean.getUnSendMembers());
			ps.setInt(6, alarmInfoBean.getAlarmType());
			ps.setString(7, alarmInfoBean.getInfo());
			ps.setInt(8, alarmInfoBean.getConfigid());

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			int sendId = 0;
			if (rs.next()) {
				sendId = rs.getInt(1);
			}
			ps.close();
			//插入webservice接口发送结果
//			if (alarmInfoBean.getWsAlarmSentBeanList() != null
//					&& alarmInfoBean.getWsAlarmSentBeanList().size() > 0) {
//				insertWebServiceSentInfo(conn, sendId,
//						alarmInfoBean.getWsAlarmSentBeanList());
//			}
		} catch (Exception e) {
			logger.error("存储告警信息异常！", e);
			throw new EventAlarmSenderException("存储告警信息异常！");
		} finally {
			ConnectionManager.closeStatement(ps);
		}
	}

	/*	private void insertWebServiceSentInfo(Connection conn, int sendId,
			List<WebServiceAlarmSentBean> wsAlarmSentBeanList) throws Exception {
		PreparedStatement ps = null;
		boolean oldAutoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			ps = conn
					.prepareStatement("INSERT INTO `zy_alarmsendinfo_ws` (`alarmSendId`, `name`, `sentResult`, `wsdlLocation`, `errorMsg`) VALUES (?, ?, ?, ?, ?)");
			for (WebServiceAlarmSentBean info : wsAlarmSentBeanList) {
				int i = 1;
				ps.setInt(i++, sendId);
				ps.setString(i++, info.getName());
				ps.setInt(i++, info.getSentResult());
				ps.setString(i++, info.getWsdlLocation());
				ps.setString(i++, info.getErrorMsg());
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			ps.clearBatch();
		} finally {
			conn.setAutoCommit(oldAutoCommit);
		}
	}
*/
	/**
	 * <code>updateCurrAlarmInfo</code> 更新事件告警信息
	 * @param warnBeanLs
	 */
	public void updateCurrAlarmInfo(HashMap<Integer, EventHandle> bHandle) {
		Connection conn = null;
		conn = ConnectionManager.getConnection();
		try {
			if (conn != null) {
				for (Entry<Integer, EventHandle> h : bHandle.entrySet()) {
					EventHandle eh = h.getValue();
					for (WarnEventBean bean : eh.getWarnBeanLs()) {
						//logger.error("#######<<>>  " + bean.toString());
						if (bean.isSend() && bean.getAlarmSend() == 1) {
							updateCurrAlarmInfoTable(conn, bean);
							//logger.error("#######更新: " + bean.toString());
						}
					}
				}
			} else {
				logger.error("获取数据连接对象失败!");
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
	}

	/**
	 * <code>updateCurrAlarmInfoTable</code>更新告警事件
	 * @param conn
	 * @param warnEventBean
	 */
	private void updateCurrAlarmInfoTable(Connection conn, WarnEventBean warnEventBean) {
		PreparedStatement ps = null;
		try {
			String SQL = "UPDATE alarm_info_curr SET alarmSendTime=?,alarmsendflag=? WHERE alarmObj=? and alarmtype=? ";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));
			ps.setInt(2, warnEventBean.getAlarmSend());
			ps.setInt(3, warnEventBean.getObjId());
			ps.setInt(4, warnEventBean.getAlarmType());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新告警事件net_alarminfo_curr时异常!");
		} finally {
			ConnectionManager.closeStatement(ps);
		}
	}


	public static void main(String[] args) {
		String l = "309,310,";
		if (l.endsWith(",")) {
			l = l.substring(0, l.length() - 1);
		}
		System.out.println(l);
	}
}
