/*
 * @(#)JDBCHelper.java 12/23/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.comm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCHelper {
	public static Logger logger = Logger.getLogger(JDBCHelper.class.toString());
	private static final Map<String, DataSource> mapDS = new HashMap<String, DataSource>();
	private static final String CONNECTION_STR = "app_con_str";

	/**
	 * 获取java.sql.Connection连接
	 * 
	 * @return
	 * @throws SQLException
	 *             muyp modify
	 */
	protected static Connection getConnection() throws SQLException {
		DataSource ds = mapDS.get(CONNECTION_STR);
		//logger.info("ds start:" + ds);
		if (ds == null) {
			synchronized (JDBCHelper.class) {
				ds = mapDS.get(CONNECTION_STR);
				//logger.info("ds synchronized:" + ds);
				if (ds == null) {
					ds = initDS();
					mapDS.put(CONNECTION_STR, ds);
				}
			}
		}
		//logger.info("ds end:" + ds);
		return ds.getConnection();
	}

	protected static Connection getConnection(String dbName) throws SQLException {
		DataSource ds = mapDS.get(dbName);
		if (ds == null) {
			synchronized (JDBCHelper.class) {
				if((dbName==null)||(dbName.trim().isEmpty()))
					ds = mapDS.get(CONNECTION_STR);
				else
					ds = mapDS.get(dbName);
				//logger.info("ds synchronized:" + ds);
				if (ds == null) {
					ds = initDS(dbName);
					mapDS.put(dbName, ds);
				}
			}
		}
		//logger.info("ds end:" + ds);
		return ds.getConnection();
	}
	
	private static DataSource initDS(String dbName) {
		ComboPooledDataSource cbPoolDS = new ComboPooledDataSource();
		try {
			cbPoolDS.setDriverClass(PropertiesHandle
					.getResuouceInfo("itGather.DBMS.connection.driverClass"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("数据库驱动加载失败");
		}
		System.out.println("conn config ="+PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.url"));
		if((dbName==null)||(dbName.trim().isEmpty()))
			cbPoolDS.setJdbcUrl(PropertiesHandle
					.getResuouceInfo("itGather.DBMS.connection.url"));
		else
			cbPoolDS.setJdbcUrl(PropertiesHandle
					.getResuouceInfo("itGather.DBMS.connection.url."+dbName));
		cbPoolDS.setUser(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.user"));
		cbPoolDS.setPassword(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.password"));

		cbPoolDS.setInitialPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.initialPoolSize")));
		cbPoolDS.setMinPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.minPoolSize")));
		cbPoolDS.setMaxPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.maxPoolSize")));
		cbPoolDS.setMaxIdleTime(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.maxIdleTime")));
//		cbPoolDS.setMaxStatements(Integer.parseInt(PropertiesHandle
//				.getResuouceInfo("ipNet.DBMS.connection.maxStatements")));
		cbPoolDS.setAcquireIncrement(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.acquireIncrement")));
		cbPoolDS.setIdleConnectionTestPeriod(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.idleConnectionTestPeriod")));
//		cbPoolDS.setAcquireRetryAttempts(Integer.parseInt(PropertiesHandle
//				.getResuouceInfo("ipNet.DBMS.connection.acquireRetryAttempts")));
//		cbPoolDS.setAcquireRetryDelay(Integer.parseInt(PropertiesHandle
//				.getResuouceInfo("ipNet.DBMS.connection.acquireRetryDelay")));
//		cbPoolDS.setBreakAfterAcquireFailure(Boolean.parseBoolean(PropertiesHandle
//				.getResuouceInfo("ipNet.DBMS.connection.breakAfterAcquireFailure")));
//		cbPoolDS.setTestConnectionOnCheckout(Boolean.parseBoolean(PropertiesHandle
//				.getResuouceInfo("ipNet.DBMS.connection.testConnectionOnCheckout")));

		return (DataSource) cbPoolDS;

	}

	
	private static DataSource initDS() {
		ComboPooledDataSource cbPoolDS = new ComboPooledDataSource();
		try {
			cbPoolDS.setDriverClass(PropertiesHandle
					.getResuouceInfo("itGather.DBMS.connection.driverClass"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("数据库驱动加载失败");
		}
		System.out.println("conn config ="+PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.url"));
		cbPoolDS.setJdbcUrl(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.url"));
		cbPoolDS.setUser(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.user"));
		cbPoolDS.setPassword(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.password"));

		cbPoolDS.setInitialPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.initialPoolSize")));
		cbPoolDS.setMinPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.minPoolSize")));
		cbPoolDS.setMaxPoolSize(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.maxPoolSize")));
		cbPoolDS.setMaxIdleTime(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.maxIdleTime")));
		cbPoolDS.setAcquireIncrement(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.acquireIncrement")));
		cbPoolDS.setIdleConnectionTestPeriod(Integer.parseInt(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.idleConnectionTestPeriod")));

		return (DataSource) cbPoolDS;

	}
/*
	private static DataSource initDS() {
		DruidDataSource cbPoolDS = new DruidDataSource(); 
		
		try {
			cbPoolDS.setDriverClassName(PropertiesHandle
					.getResuouceInfo("itGather.DBMS.connection.driverClass"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("数据库驱动加载失败");
		}
		System.out.println("conn config ="+PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.url"));
		cbPoolDS.setUrl(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.url"));
		System.out.println(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.username"));
		System.out.println(PropertiesHandle
				.getResuouceInfo("itGather.DBMS.connection.passwords"));
		cbPoolDS.setUsername("dcim");
		cbPoolDS.setPassword("dcim");


		return (DataSource) cbPoolDS;

	}*/
	
	public static void main(String[] args) {
	System.out.print("开始运行");
	
}
}
