/*
 * @(#)DBTool.java 12/26/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.db.reflect.DBReflectUtil;

/**
 * <code>DBTool</code> DB与对象的互换简易工具
 * 
 * @author yangDS
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DBTool {
	Logger logger = Logger.getLogger(DBTool.class.getName());
	DBReflectUtil dBReflectUtil = null;
	public DBTool(){
		dBReflectUtil = new DBReflectUtil();
	}
	/**
	 * 
	 * 执行更新或插入 sql
	 * 
	 * @param sql
	 * @param params
	 */
	public void doSql(String sql, Object[] params) {
		if (sql == null)
			return;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			if (params != null) {
				int size = params.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, params[i - 1]);
				}
			}
			ps.execute();
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/**
	 * 保存对象集合
	 * 
	 * @param collection
	 *            对象集合
	 * @param sql
	 *            sql语句
	 * @param params
	 *            sql中出现的参数对应值
	 * @throws Exception
	 */
	public void saveObjCollection(Collection collection, String sql, Object[] params) throws Exception {
		if (sql == null || sql.length() <= 0 || params == null || params.length <= 0) {
			logger.error("the number of parameters is not complete");
			return;
		}
		if (collection == null || collection.size() <= 0) {
			logger.warn("there is no record in the collection ");
			return;
		}
		if (!checkCollection(collection)) {
			logger.warn("there are null-elements in the collection,will be ignored");
		}
		Object[] values = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				values = dBReflectUtil.getFieldsValues(it.next(), params);
				int size = values.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, values[i - 1]);
				}
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			logger.error(sql, e);
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            目标对象
	 * @param sql
	 *            sql语句
	 * @param paramField
	 *            sql语句中参数对应bean中的域名。如无法与域名无法对应，直接传入该变量
	 * @throws Exception
	 */
	public  void saveObj(Object obj, String sql, Object[] paramField) {
		if (obj == null || sql == null || sql.length() <= 0 || paramField == null || paramField.length <= 0) {
			logger.error("the number of parameters is not complete");
			return;
		}
		Object[] values = dBReflectUtil.getFieldsValues(obj, paramField);
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			int size = values.length;
			for (int i = 1; i <= size; i++) {
				ps.setObject(i, values[i - 1]);
			}
			ps.execute();
		} catch (SQLException e) {
			logger.error("执行sql" + sql + "出现异常:", e);
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/**
	 * 返回查询记录, 每个记录存放与map中, map的key值为 语句中字段名称的大写
	 * 
	 * @param sql
	 *            查询sql
	 * @param param
	 *            所需设置的参数
	 * @return
	 */
	public  HashMap getSingleRecordStoreInMap(String sql, Object[] param) {
		HashMap record = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				int size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			if (rs.next()) {
				record = new HashMap();
				for (int i = 1; i <= count; i++) {
					record.put(rsmd.getColumnLabel(i).toUpperCase(), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			ConnectionManager.release(conn, ps, rs);
		}
		return record;
	}

	/**
	 * 返回查询记录集合， 每个记录存放与map中， map的key值为 语句中字段名称的大写
	 * 
	 * @param sql
	 *            查询sql
	 * @param param
	 *            所需设置的参数
	 * @return
	 */
	public  ArrayList<HashMap> getRecordsStoreInMap(String sql, Object[] param) {
		ArrayList<HashMap> result = new ArrayList<HashMap>();
		HashMap record = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				int size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				record = new HashMap();
				for (int i = 1; i <= count; i++) {
					record.put(rsmd.getColumnLabel(i).toUpperCase(), rs.getObject(i));
				}
				result.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			ConnectionManager.release(conn, ps, rs);
		}
		return result;
	}

	/**
	 * 从数据库中读取单值
	 * 
	 * @param sql
	 *            查询sql
	 * @param param
	 *            需要设置的参数
	 * @return
	 */
	public  Object getSingleValueFromDB(String sql, Object[] param) {
		Object obj = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				int size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			if (rs.next())
				obj = rs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return obj;
	}

	/**
	 * 从数据库中读取单列,如果结果返回多列，只返回结果第一列
	 * 
	 * @param sql
	 *            查询sql
	 * @param param
	 *            需要设置的参数
	 * @return
	 */
	public  Object[] getSingleColumnFromDB(String sql, Object[] param) {
		Object[] objList = null;
		int size = -1;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			rs.last();// 将指针移动到结果末尾
			size = rs.getRow();// 得到末尾的row值
			if (size <= 0) {// 说明没有值
				return null;
			}
			objList = new Object[size];
			for (int i = 0; i < size; i++) {
				rs.absolute(i + 1);
				objList[i] = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return objList;
	}

	/**
	 * 返回sql查询的记录对应的java对象
	 * 
	 * @param clazz
	 * @param sql
	 *            查询sql
	 * @return
	 */
	public  Object getObjectFromDB(Class clazz, String sql) {
		Object obj = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			try {
				obj = dBReflectUtil.getJavaBeanFromRSMDBySetter(clazz, rs, rsmd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return obj;
	}

	/**
	 * 用需要设置参数的sql取得记录并映射为javabean
	 * 
	 * @param clazz
	 *            需要映射的类型
	 * @param prepareSql
	 *            查询sql
	 * @param param
	 *            参数列表
	 * @return
	 */
	public  Object getObjectFromDB(Class clazz, String prepareSql, Object[] param) {
		Object obj = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(prepareSql);
			if (param != null && param.length > 0) {
				int size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			try {
				obj = dBReflectUtil.getJavaBeanFromRSMDBySetter(clazz, rs, rsmd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return obj;
	}

	/**
	 * 从数据库中获取对象集合
	 * 
	 * @param clazz
	 *            需要映射的java类型
	 * @param sql
	 *            查询sql
	 * @return
	 */
	public  Collection getCollectionFromDB(Class clazz, String sql) {
		Collection list = new ArrayList();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			try {
				list = dBReflectUtil.getJavaBeanCollectionFromRSMDBySetter(clazz, rs, rsmd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 将返回集合存入HashMap中。从bean中提取keyField指定的属性域值作为map中key，以bean为value
	 * 
	 * @param clazz
	 *            bean的类型
	 * @param sql
	 *            执行的查询sql
	 * @param keyField
	 *            bean中将作为key的属性域名称
	 * @return
	 */
	public  HashMap getCollectionFromDB(Class clazz, String sql, String keyField) {
		HashMap hm = new HashMap(0);
		if (keyField == null) {
			logger.error("keyField is not nullable");
			return hm;
		}
		Collection c = getCollectionFromDB(clazz, sql);
		if (c.isEmpty()) {
			logger.warn("Nothing returned by your SQL");
			return hm;
		}
		for (Iterator it = c.iterator(); it.hasNext();) {
			Object value = it.next();
			Object key = dBReflectUtil.getFieldValue(value, keyField);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * 得到presql返回记录与之对应的javabean 列表
	 * 
	 * @param clazz
	 * @param sql
	 * @param param
	 * @return
	 */
	public  Collection getCollectionFromDB(Class clazz, String sql, Object[] param) {
		if (param == null)
			return getCollectionFromDB(clazz, sql);
		Collection list = null;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				int size = param.length;
				for (int i = 1; i <= size; i++) {
					ps.setObject(i, param[i - 1]);
				}
			}
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			try {
				list = dBReflectUtil.getJavaBeanCollectionFromRSMDBySetter(clazz, rs, rsmd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 检查集合中是否有空元素
	 * 
	 * @param collection
	 * @return
	 */
	private  boolean checkCollection(Collection collection) {
		boolean flag = true;
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			if (it.next() == null) {
				flag = false;
				it.remove();
			}
		}
		return flag;
	}
}
