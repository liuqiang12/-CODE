package com.JH.dgather.frame.eventmanager.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.eventmanager.bean.UnwarnEventBean;
import com.JH.dgather.frame.eventmanager.bean.WarnEventBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.DateFormate;
import com.JH.dgather.frame.util.DateUtil;

public class DataUtil {
	public Logger logger = Logger.getLogger(DataUtil.class.toString());



	public void process(ConcurrentLinkedQueue<WarnEventBean> warnLs, ConcurrentLinkedQueue<UnwarnEventBean> unwarnls, int gc) throws Exception {
		Connection conn = null;
		List<WarnEventBean> curAlarm = null;
		try {
			conn = ConnectionManager.getConnection();
			if (conn == null) {
				throw new Exception("获取数据连接对象是异常！ conn == null");
			} else {
				curAlarm = this.getAllAlarms(conn);
				//处理当前告警事件
				processWarnEvent(conn, warnLs, curAlarm);
				//处理当前非告警事件
				processUnwarnEvent(conn, unwarnls, curAlarm);
			}
		} catch (Exception e) {
			logger.error("数据处理异常！", e);
		} finally {
			if (conn != null) {
				ConnectionManager.closeConnection(conn);
			}
			curAlarm = null;
		}
		logger.debug("process end");
	}

	/**
	 * <code>insertCurrentAlarmInfoTable</code> 向alarm_info_curr表中插件一条新数据
	 * 
	 * @param conn
	 *            数据连接对象
	 * @param ins
	 *            数据bean
	 */
	public void insertCurrentAlarmInfoTable(Connection conn, WarnEventBean ins) {
		String currentTime = DateUtil.getDateTime();
		PreparedStatement ps = null;
		try {
			String SQL_1 = "INSERT INTO alarm_info_curr( alarmObj,  alarmlevel, alarmtype, alarminfo, alarmTime) ";
			SQL_1 += "VALUES(?,?,?,?,?)";
			logger.info(SQL_1);
			ps = conn.prepareStatement(SQL_1,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, ins.getObjId());
			ps.setInt(2, ins.getAlarmlevel());
			ps.setInt(3, ins.getAlarmType());
			ps.setString(4, ins.getAlarmInfo());
			if (ins.getAlarmTime() == null || ins.getAlarmTime().equals("")) {
				ps.setString(5, currentTime);
				ins.setAlarmTime(currentTime);
			} else {
				ps.setString(5, ins.getAlarmTime());
			}

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				ins.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("向alarm_info_curr表中insert一条数据时，发生异常！", e);
		} finally {
			ConnectionManager.closeStatement(ps);
		}
	}


	private List<WarnEventBean> getAllAlarms(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WarnEventBean> rtn = new ArrayList<WarnEventBean>();
		try {
			String SQL_3 = "SELECT id,alarmObj, portid,alarmlevel, alarmtype, alarminfo,alarmsendflag,alarmtime FROM alarm_info_curr";
			ps = conn.prepareStatement(SQL_3);
			rs = ps.executeQuery();
			while (rs.next()) {
				WarnEventBean eb = new WarnEventBean(rs.getInt("alarmObj"), rs.getInt("alarmtype"),rs.getInt("portid"), rs.getInt("alarmlevel"), "",rs.getString("alarminfo"));
				eb.setAlarmSend(rs.getInt("alarmsendflag"));
				eb.setId(rs.getInt("id"));
				eb.setAlarmTime(rs.getString("alarmtime"));
				rtn.add(eb);
			}
		} catch (Exception e) {
			logger.error("事件在入库时异常！", e);
		} finally {
			if (rs != null) {
				ConnectionManager.closeResultSet(rs);
			}
			if (ps != null) {
				ConnectionManager.closeStatement(ps);
			}
		}

		return rtn;
	}

	/**
	 * <code>processWarnEvent</code> 处理告警事件
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param warnLs
	 *            告警事件列表
	 */
	private void processWarnEvent(Connection conn, ConcurrentLinkedQueue<WarnEventBean> warnLs, List<WarnEventBean> currls) {
		logger.info("准备执行告警事件 数据库处理!");
		if (warnLs == null || warnLs.peek() == null) {
			return;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<WarnEventBean> saveCurrls = null;
		List<Integer> delLs = new ArrayList<Integer>();
		try {
			saveCurrls = new ArrayList<WarnEventBean>();
			// 筛选出insert与update
			List<WarnEventBean> insertEventLs = new ArrayList<WarnEventBean>();
			List<WarnEventBean> updateEventLs = new ArrayList<WarnEventBean>();
			boolean isFind = false;
			for (WarnEventBean bean : warnLs) {
				logger.info("当前告警内容：" + bean.getAlarmInfo());
				isFind = false;
				for (WarnEventBean cLs : currls) {
					if (bean.getObjId() == cLs.getObjId() && bean.getAlarmType() == cLs.getAlarmType() ) {
						isFind = true;
						//告警等级升级的情况下，也要发送邮件。
						if (bean.getAlarmlevel() > cLs.getAlarmlevel()) {
							saveCurrls.add(cLs);
							bean.setSend(true);
							insertEventLs.add(bean);
							delLs.add(cLs.getId());
						} else {
							if (cLs.getAlarmSend() == 0) {
								bean.setSend(true);
							}
							updateEventLs.add(bean);
							if (bean.isSaveHis())
								saveCurrls.add(cLs);
						}
						break;
					}
				}
				if (!isFind) {
					bean.setSend(true);
					insertEventLs.add(bean);
					logger.info("需要新增告警：" + bean.getAlarmInfo());
				}
			}
			logger.error("需要执行insert的告警数量: " + insertEventLs.size());
			logger.error("需要执行update的告警数量: " + updateEventLs.size());
			logger.error("需要执行保留历史的告警数量: " + saveCurrls.size());

			// 执行insert
			for (WarnEventBean ins : insertEventLs) {
				insertCurrentAlarmInfoTable(conn, ins);
			}

			for (WarnEventBean ins : saveCurrls) {
				insertCurrentAlarmInfoHisTable(conn, ins);
			}

			// 将数据从curr表中删除
			if (delLs.size() > 0) {
				StringBuffer SQL_DEL = new StringBuffer("DELETE FROM alarm_info_curr WHERE id in(");
				int idx = 0;
				for (int i : delLs) {
					if (idx != 0) {
						SQL_DEL.append("," + i);
					} else {
						SQL_DEL.append(i);
					}
					idx++;
				}
				SQL_DEL.append(")");
				logger.info("DEL: " + SQL_DEL);
				ps = conn.prepareStatement(SQL_DEL.toString());
				ps.executeUpdate();
				ps.close();

			}
		} catch (Exception e) {
			logger.error("事件在入库时异常！", e);
		} finally {
			if (rs != null) {
				ConnectionManager.closeResultSet(rs);
			}
			if (ps != null) {
				ConnectionManager.closeStatement(ps);
			}

			delLs = null;
		}

	}

	/**
	 * <code>insertCurrentAlarmInfoHisTable</code> 向alarm_info_his表中插件一条新数据
	 * 
	 * @param conn
	 *            数据连接对象
	 * @param ins
	 *            数据bean
	 */
	public void insertCurrentAlarmInfoHisTable(Connection conn, WarnEventBean ins) {
		PreparedStatement ps = null;
		try {
			String SQL_1 = "INSERT INTO alarm_info_his(alarmlevel, alarmtype, alarmObj, alarminfo, alarmtime, alarmsendflag, AlarmSendTime, resumeTime, resumeUser) ";
			SQL_1 += "VALUES(?,?,?,?,?,?,?,?,?)";
			logger.info(SQL_1);
			ps = conn.prepareStatement(SQL_1);
			ps.setInt(1, ins.getAlarmlevel());
			ps.setInt(2, ins.getAlarmType());
			ps.setInt(3, ins.getObjId());
			ps.setString(4, ins.getAlarmInfo());
			ps.setString(5, ins.getAlarmTime());
			ps.setInt(6, ins.getAlarmSend());
			if (ins.getAlarmSendTime() == null || "".equals(ins.getAlarmSendTime())) {
				ps.setString(7, null);
			} else {
				ps.setString(7, DateFormate.dateToStr(ins.getAlarmSendTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			ps.setString(8, DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));
			ps.setString(9, "system");
			ps.execute();
		} catch (Exception e) {
			logger.error("向alarm_info_his表中insert一条数据时，发生异常！", e);
		} finally {
			ConnectionManager.closeStatement(ps);
		}
	}

	/**
	 * <code>processUnwarnEvent</code> 处理非告警事件
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param unwarnls
	 *            非告警事件列表
	 */
	private void processUnwarnEvent(Connection conn, ConcurrentLinkedQueue<UnwarnEventBean> unwarnls, List<WarnEventBean> currentAlarms) {
		if (unwarnls == null || unwarnls.peek() == null) {
			return;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> delLs = new ArrayList<Integer>();
		try {
			for (UnwarnEventBean unBean : unwarnls) {
				for (WarnEventBean curBean : currentAlarms) {
					if (curBean.getObjId() == unBean.getObjId() && unBean.getAlarmType() == curBean.getAlarmType()) {
						//由于恢复事件没有设置告警级别，因此发生短信规则中根据告警级别会过滤所有的恢复事件，导致用户不能收到恢复短信
						unBean.setAlarmlevel(curBean.getAlarmlevel());
						unBean.setId(curBean.getId());
						if (curBean.getAlarmSend() == 1) {
							unBean.setSend(true);
								unBean.setAlarmInfo("<上次事件: " + curBean.getAlarmInfo() + ">, 【本次正常】");
							logger.info("&&&&&&&&&&&& 产生一条恢复发送信息！！！内容：" + curBean.getAlarmInfo()+",告警ID:"+curBean.getId());
						}
						insertCurrentAlarmInfoHisTable(conn, curBean);
						delLs.add(curBean.getId());
				}
			}

			// 将数据从curr表中删除
			if (delLs.size() > 0) {
				StringBuffer SQL_DEL = new StringBuffer("DELETE FROM alarm_info_curr WHERE id in(");
				int idx = 0;
				for (int i : delLs) {
					if (idx != 0) {
						SQL_DEL.append("," + i);
					} else {
						SQL_DEL.append(i);
					}
					idx++;
				}
				SQL_DEL.append(")");
				logger.info("DEL: " + SQL_DEL);
				ps = conn.prepareStatement(SQL_DEL.toString());
				ps.executeUpdate();
				ps.close();

			}
		} 
		}catch (Exception e) {
			logger.error("处理非告警事件时异常！", e);
		} finally {
			if (rs != null) {
				ConnectionManager.closeResultSet(rs);
			}
			if (ps != null) {
				ConnectionManager.closeStatement(ps);
			}
			delLs = null;
		}
	}
}
