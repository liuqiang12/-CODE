package com.JH.dgather.frame.globaldata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.db.ConnectionManager;

/**
 * 初始化全局数据
 * 
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2012-6-4
 */

public class GloableDataDB {
	private Logger logger = Logger.getLogger(GloableDataDB.class);

	public void selectEventScheduler() {
		Connection conn = ConnectionManager.getConnection();
		String sql = "SHOW VARIABLES LIKE '%sche%';";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String event_scheduler=null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				event_scheduler = rs.getString("value");
			}
			if(event_scheduler.toUpperCase().equals("OFF")){
				sql = "SET GLOBAL event_scheduler = 1;";
				pstmt = conn.prepareStatement(sql);
				pstmt.execute();
			}
			logger.info("设置数据库的GLOBAL event_scheduler信息成功！");
		} catch (Exception e) {
			logger.error("设置数据库的GLOBAL event_scheduler信息失败！", e);
		} finally {
			ConnectionManager.release(conn, pstmt, rs);
		}
	}
	public void initFTPInfo() {
		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT ip,FTPPort,FTPUser,FTPPwd from net_ftp_param where id=(SELECT max(id) from net_ftp_param)";
		//FTPInfo ftpInfo =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				GloableDataArea.ftpServerIp=rs.getString("ip");
				GloableDataArea.ftpServerPort=rs.getInt("FTPPort");
				GloableDataArea.ftpServerUser=rs.getString("FTPUser");
				GloableDataArea.ftpServerPassword=rs.getString("FTPPwd");
			}
			logger.info("初始化net_ftp_param信息成功！");
		} catch (Exception e) {
			logger.error("初始化net_ftp_param信息失败！", e);
		} finally {
			ConnectionManager.release(conn, pstmt, rs);
		}
	}
	/*public static void initAlarem() {
		List<String[]> res = new ArrayList<String[]>();
		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT al.oltid,al.onuid,al.timedelay,al.LineCheckTime,res.SN from (SELECT oltid,onuid,timedelay,LineCheckTime from pon_line_onu where id in (SELECT MAX(id) from pon_line_onu where timedelay>10 GROUP BY onuid,oltid) and timedelay>10 ) al LEFT JOIN pon_resonu res on al.onuid=res.ONUID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String[] s = new String[5];
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				s = new String[5];
				s[0]=rs.getString("oltid");
				s[1]=rs.getString("onuid");
				s[2]=rs.getString("timedelay");
				s[3]=rs.getString("LineCheckTime");
				s[4]=rs.getString("SN");
				res.add(s);
			}
			logger.info("初始化kpibase信息成功！");
		} catch (Exception e) {
			logger.error("初始化kpibase信息失败！", e);
		}
		
		

		sql = "INSERT  into  net_alarminfo_curr(alarmLevel,objId,KpiId,alarmLevelId,portId,portName,capValue,comparaValue,alarmInfo,alarmTime) VALUES(0,?,25,0,?,'',?,0,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			String[] s = new String[5];
				Iterator<String[]> it = res.iterator();
				while(it.hasNext()){
					s = it.next();
					ps.setInt(1, Integer.parseInt(s[0]));
					ps.setInt(2, Integer.parseInt(s[1]));
					ps.setInt(3, Integer.parseInt(s[2]));
					ps.setString(4, "SN为"+s[4]+"的ONU当前延时值："+s[2]+"{超过告警门限值10}");
					ps.setString(5, s[3]);
					ps.addBatch();
					
				}
				ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("链路测试信息保存出错", e);
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection(conn);

		}

	}*/

	public void initGloadbleKpiBase(HashMap<String, Integer> kpiBase,
			HashMap<Integer, String> kpiBaseIdKey,
			HashMap<String, String> kpiBaseName,
			HashMap<Integer, String> kpiBaseIdName,
			HashMap<Integer, Integer[]> kpiBaseGCC) {
		
		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT kpiid,kpikey,kpiname,gatherclass,KPIClass from net_kpibase";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int kpiid,gatherclass,kpiclass;
		String kpikey,kpiname;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				kpikey = rs.getString("kpikey").toLowerCase();
				kpiid = rs.getInt("kpiid");
				kpiname = rs.getString("kpiname");
				kpiBase.put(kpikey, kpiid);
				kpiBaseIdKey.put(kpiid, kpikey);
				kpiBaseName.put(kpikey,kpiname);
				kpiBaseIdName.put(kpiid, kpiname);
				kpiBaseGCC.put(rs.getInt("kpiid"), new Integer[]{rs.getInt("gatherclass"),rs.getInt("KPIClass")});				
			}
			logger.info("初始化kpibase信息成功！");
		} catch (Exception e) {
			logger.error("初始化kpibase信息失败！", e);
		} finally {
			ConnectionManager.release(conn, pstmt, rs);
		}
		
	}
}
