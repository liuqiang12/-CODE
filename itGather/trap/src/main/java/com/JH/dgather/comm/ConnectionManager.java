/*
 * @(#)ConnectionManager.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ConnectionManager {
	static Logger logger = Logger.getLogger(ConnectionManager.class.getName());
	static int connNum = 0;
	
	private ConnectionManager() {
		
	}
	
	/**
	 * 得到数据库的连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			connNum++;
			//logger.debug("++操作，当前连接数："+connNum);
			return JDBCHelper.getConnection();
		} catch (SQLException e) {
			logger.error("连接池获取出错", e);
		}
		
		return null;
	}
	
	public static Connection getConnection(String dbName){
		try{
			return JDBCHelper.getConnection(dbName);
		} catch (SQLException e) {
			logger.error(dbName+"连接池获取出错", e);
		}
		return null;
	}
	
	/**
	 *释放PreparedStatement
	 * 
	 * @param statement
	 */
	public static void closeStatement(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			logger.error("PreparedStatement释放出错", e);
		}
		
	}
	
	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			logger.error("Statement释放出错", e);
		}
		
	}
	
	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.error("ResultSet释放出错", e);
		}
		
	}
	
	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null){
				conn.close();
				connNum--;
			}
			//logger.debug("--操作，当前连接数："+connNum);
		} catch (SQLException e) {
			logger.error("连接释放出错", e);
		}
	}
	
	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void connRollback(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (SQLException e) {
			logger.error("连接回滚出错:", e);
		}
	}
	
	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void connSetAutoCommit(Connection conn, boolean autoCommit) {
		try {
			if (conn != null)
				conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			logger.error("连接回滚出错:", e);
		}
	}
	
	/**
	 * 得到数据库的连接
	 * 
	 * @return
	 */
	public static Connection getConnection(boolean autoCommit) {
		try {
			connNum++;
			Connection conn = JDBCHelper.getConnection();
			conn.setAutoCommit(autoCommit);
			return conn;
		} catch (SQLException e) {
			logger.error("连接池获取出错", e);
			System.out.println("getConnection error");
		}
		return null;
	}
	
	/**
	 * 释放资源
	 * 
	 * @param conn
	 *            Connection对象
	 * @param pstmt
	 *            PreparedStatement对象
	 * @param rs
	 *            ResultSet对象
	 */
	public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		//	try {
		if (rs != null) {
			closeResultSet(rs);
		}
		if (pstmt != null) {
			closeStatement(pstmt);
		}
		if (conn != null) {
			closeConnection(conn);
		}
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}
	}
	
}
