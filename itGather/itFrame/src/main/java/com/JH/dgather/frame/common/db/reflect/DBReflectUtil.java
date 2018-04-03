/*
 * @(#)DBReflectUtil.java 12/26/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.db.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class DBReflectUtil {
	Logger logger = Logger.getLogger(DBReflectUtil.class.getName());
	
	/**
	 * 得到一组域的值
	 * @param obj
	 * @param fieldNames 需要取值域名称列表
	 * @return
	 */
	public Object[] getFieldsValues(Object obj, Object[] fieldNames) {
		if (obj == null || fieldNames == null || fieldNames.length <= 0)
			return null;
		Object[] result = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			result[i] = getFieldValue(obj, fieldNames[i]);
		}
		
		return result;
	}
	
	/**
	 * 得到域的值,如果不是域名，则直接返回该对象
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValue(Object obj, Object fieldName) {
		Class clazz = obj.getClass();
		Field f = null;
		String methodName = null;
		try {
			f = clazz.getDeclaredField(fieldName.toString());
			
			if (f.getType().getName().equals(Boolean.class.getName())) {
				methodName = "is" + formatFieldNameInAccessor(fieldName.toString());
			}
			else {
				methodName = "get" + formatFieldNameInAccessor(fieldName.toString());
			}
			Method m = clazz.getMethod(methodName, null);
			return m.invoke(obj, null);
		} catch (SecurityException e) {
			//				e.printStackTrace();
		} catch (NoSuchFieldException e) {
			//				System.out.println("warning:there is  no field named "+fieldName+" in "+ clazz.getName( )+",so it will be ignored" );
			//				e.printStackTrace();
			//				logger.warn( "warning:there is  no field named "+fieldName+" in "+ clazz.getName( )+",so it will be ignored"  );
		} catch (NoSuchMethodException e) {
			//				e.printStackTrace();
			//				logger.warn( "warning:there is  no getter method named "+methodName+" in "+ clazz.getName( )+",so it will be ignored"  );
		} catch (IllegalArgumentException e) {
			//				logger.warn( "warning:the getter method named "+methodName+" in "+ clazz.getName( )+" has parameter,and this is illegal based javabean rule,so it will be ignored"  );
		} catch (IllegalAccessException e) {
			//				logger.warn( "warning:the getter method named "+methodName+" in "+ clazz.getName( )+" can't access in this util,so it will be ignored"  );
			//				e.printStackTrace();
		} catch (InvocationTargetException e) {
			//				logger.warn( "error:some error happened when invoking the gettter named "+methodName+" in "+clazz.getName( ) );
			//				e.printStackTrace();
		}
		return fieldName;
	}
	
	/**
	 * 
	 * 返回域的值
	 * @param obj
	 * @param f
	 * @return
	 */
	public Object getFieldValue(Object obj, Field f) {
		if (f != null) {
			try {
				return f.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 打印bean信息
	 * 
	 * @param obj
	 */
	public void printJavaBean(Object obj) {
		Class clazz = obj.getClass();
		Field[] f = clazz.getDeclaredFields();
		
		for (Field fd : f) {
			fd.setAccessible(true);
			try {
				logger.error("Field:" + fd.getName() + " value:" + fd.get(obj));
			} catch (IllegalArgumentException e) {
				logger.error("", e);
			} catch (IllegalAccessException e) {
				logger.error("", e);
			}
		}
	}
	
	/**
	 * 返回列对应类型
	 * 
	 * @param rsmd
	 * @return
	 */
	private HashMap<String, ColumnModel> getColumnModelMapping(ResultSetMetaData rsmd) {
		if (rsmd == null)
			return null;
		HashMap<String, ColumnModel> columnMapping = new HashMap<String, ColumnModel>();
		int columns;
		try {
			columns = rsmd.getColumnCount();
			ColumnModel cm = null;
			for (int i = 1; i <= columns; i++) {
				cm = new ColumnModel(rsmd.getColumnLabel(i), rsmd.getColumnType(i));
				columnMapping.put(rsmd.getColumnLabel(i).toUpperCase(), cm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnMapping;
	}
	
	/**
	 * 找出类型兼容的field与setter
	 * 
	 * @param cModel
	 * @param fModel
	 * @param clazz
	 * @return
	 */
	private HashMap<String, Method> compatibleFieldsFilt(HashMap<String, ColumnModel> cModel,
			HashMap<String, FieldModel> fModel, Class clazz) {
		if (cModel == null || fModel == null)
			return null;
		HashMap<String, Method> setters = new HashMap<String, Method>();
		String key = null;
		ColumnModel cm = null;
		FieldModel fm = null;
		Class ctype = null, ftype = null;
		Iterator<Entry<String, ColumnModel>> it = cModel.entrySet().iterator();
		Entry<String, ColumnModel> entry;
		while(it.hasNext()){
			entry = it.next();
			key = entry.getKey();
			cm = entry.getValue();
			if (cm == null) {
				logger.error("Column '" + key + "' has no column model");
				continue;
			}
			ctype = cm.getColumnType();
			fm = fModel.get(key);
			if (fm == null) {
				logger.warn(" Column '" + key + "' has no mapping field in class :" + clazz.getSimpleName());
				continue;
			}
			ftype = fm.getFieldType();
			if (ctype != null && ftype != null) {
				setters.put(cm.getColumnName(), fm.getSetter());
			}
		}
		return setters;
	}
	
	/**
	 * 找出类型兼容的field与setter
	 * 
	 * @param cModel
	 * @param fModel
	 * @param clazz
	 * @return
	 */
	private HashMap<String, Field> compatibleFieldsFilt(HashMap<String, ColumnModel> cModel, Field[] fields, Class clazz) {
		if (cModel == null || fields == null)
			return null;
		String key = null;
		ColumnModel cm = null;
		
		HashMap<String, Field> result = new HashMap<String, Field>();
		Class ctype = null;
		for (Field f : fields) {
			f.setAccessible(true);
			key = f.getName().toUpperCase();
			cm = cModel.get(key);
			if (cm == null) {
				logger.warn("Field '" + f.getName() + "' can't match any column returned by your SQL");
				continue;
			}
			ctype = cm.getColumnType();
			result.put(cm.getColumnName(), f);
		}
		return result;
	}
	
	/**
	 * 从rsmd 与rs中实例化对象 返回集合
	 * 
	 * @param clazz
	 * @param rs
	 * @param rsmd
	 * @return
	 * @throws Exception
	 */
	public Collection getJavaBeanCollectionFromRSMDBySetter(Class clazz, ResultSet rs, ResultSetMetaData rsmd)
			throws Exception {
		if (rs == null || rsmd == null || clazz == null)
			throw new Exception("所需参数中有空");
		HashMap<String, FieldModel> methodMapping = getFieldsModel(clazz, 0);// java
		HashMap<String, ColumnModel> columnMapping = getColumnModelMapping(rsmd);
		HashMap<String, Method> setters = compatibleFieldsFilt(columnMapping, methodMapping, clazz);
		ArrayList result = new ArrayList();
		if (setters == null) {
			logger.error("Fields in javabean is not compatible with the column labels in your SQL");
			return null;
		}
		
		Method method = null;
		String columnName = null;
		Object instance = null;
		Object value = null;
		while (rs.next()) {// 遍历rs
			instance = clazz.newInstance();
			Iterator<Entry<String, Method>> it =  setters.entrySet().iterator();
			Entry<String, Method> entry;
			while(it.hasNext()){
				entry = it.next();
				columnName = entry.getKey();
				method = entry.getValue();
				value = rs.getObject(columnName);
				if (value == null) {
					logger.debug("The value Of '" + columnName + "' returned by your SQL is null,will be ignore");
					continue;
				}
				method.invoke(instance, value);
			}
			result.add(instance);
		}
		return result;
	}
	
	/**
	 * 根据field 进行初始化bean
	 * 
	 * @param clazz
	 * @param rs
	 * @param rsmd
	 * @return
	 * 
	 * @throws Exception
	 * 
	 */
	public Object getJavaBeanFromRSMDByField(Class clazz, ResultSet rs, ResultSetMetaData rsmd) throws Exception {
		if (rs == null || rsmd == null || clazz == null)
			throw new Exception("所需参数中有空");
		Field[] fields = clazz.getDeclaredFields();
		HashMap<String, ColumnModel> columnMapping = getColumnModelMapping(rsmd);
		HashMap<String, Field> fMapping = compatibleFieldsFilt(columnMapping, fields, clazz);
		Object instance = null;
		String columnName = null;
		Field field = null;
		
		try {
			if (rs.next()) {
				try {
					instance = clazz.newInstance();
					Entry<String, Field> entry;
					Iterator<Entry<String, Field>> it = fMapping.entrySet().iterator();
					while(it.hasNext()){
						entry = it.next();
						columnName = entry.getKey();
						field = entry.getValue();
						field.setAccessible(true);
						field.set(instance, rs.getObject(columnName));
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * 从rsmd 与rs中实例化对象
	 * 
	 * @param clazz
	 * @param rs
	 * @param rsmd
	 * @return
	 * @throws Exception
	 */
	public Object getJavaBeanFromRSMDBySetter(Class clazz, ResultSet rs, ResultSetMetaData rsmd) throws Exception {
		if (rs == null || rsmd == null || clazz == null)
			throw new Exception("所需参数中有空");
		HashMap<String, FieldModel> methodMapping = getFieldsModel(clazz, 0);// java
		HashMap<String, ColumnModel> columnMapping = getColumnModelMapping(rsmd);
		HashMap<String, Method> setters = compatibleFieldsFilt(columnMapping, methodMapping, clazz);
		if (setters == null) {
			logger.error("Fields in javabean is not compatible with the column labels in your SQL");
			return null;
		}
		
		Method method = null;
		String columnName = null;
		Object instance = null;
		if (rs.next()) {
			instance = clazz.newInstance();
			Object value = null;
			Entry<String, Method> entry;
			Iterator<Entry<String, Method>> it = setters.entrySet().iterator();
			while(it.hasNext()){
				entry = it.next();
				columnName = entry.getKey();
				method = entry.getValue();
				value = rs.getObject(columnName);
				if (value == null) {
					logger.debug("The value Of '" + columnName + "' returned by your SQL in null,will be ignore");
					continue;
				}
				method.invoke(instance, rs.getObject(columnName));
			}
			
			return instance;
		}
		else {
			logger.error("no result can return by your SQL");
			return null;
		}
		
	}
	
	/**
	 * 返回field名称与其类型的映射 key = field名称的大写
	 * 
	 * @return
	 */
	public HashMap<String, Class> getFieldType(Class clazz) {
		if (clazz == null)
			return null;
		HashMap<String, Class> mapping = null;
		Field[] fields = clazz.getDeclaredFields();
		
		if (fields.length > 0)
			mapping = new HashMap<String, Class>();
		
		for (int i = 0; i < fields.length; i++) {
			mapping.put(fields[i].getName().toUpperCase(), fields[i].getType());
		}
		
		return mapping;
	}
	
	/**
	 * 返回遵循javabean规范的Object属性对应的setter方法 key为属性的大写
	 * 
	 * @param clazz
	 * @param 0-setter 1-getter
	 * @return
	 */
	public HashMap<String, FieldModel> getFieldsModel(Class clazz, int flag) {
		
		if (clazz == null)
			return null;
		
		HashMap<String, FieldModel> methodMapping = new HashMap<String, FieldModel>();
		Field[] fields = clazz.getDeclaredFields();
		String name = null;
		String accessorName = null;
		Method m = null;
		FieldModel fm = null;
		for (int i = 0; i < fields.length; i++) {
			fm = new FieldModel();
			name = fields[i].getName();
			fm.setFieldName(name);
			fm.setFieldType(fields[i].getType());
			if (flag == 0) {
				accessorName = "set" + formatFieldNameInAccessor(name);
				try {
					m = clazz.getMethod(accessorName, fields[i].getType());
					fm.setSetter(m);
				} catch (SecurityException e) {
					e.printStackTrace();
					continue;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					continue;
				}
			}
			else {
				if (fields[i].getType().getName().equals(Boolean.class.getName())) {
					accessorName = "is" + formatFieldNameInAccessor(name);
				}
				else {
					accessorName = "get" + formatFieldNameInAccessor(name);
				}
				try {
					m = clazz.getMethod(accessorName, null);
					fm.setGetter(m);
				} catch (SecurityException e) {
					e.printStackTrace();
					continue;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					continue;
				}
			}
			methodMapping.put(name.toUpperCase(), fm);
		}
		
		return methodMapping;
	}
	
	/**
	 * 按照javabean规范格式化field名称
	 * 
	 * @param name
	 * @return
	 */
	public String formatFieldNameInAccessor(String name) {
		if (name == null || name.trim().length() == 0)
			return null;
		name = name.trim();
		String preffix = null;
		String subffix = null;
		preffix = name.substring(0, 1).toUpperCase();
		subffix = name.substring(1);
		
		return preffix + subffix;
	}
	
	class ColumnModel {
		private String columnName;
		private Class columnType;
		
		public ColumnModel(String columnName, int columnType) {
			setColumnName(columnName);
			setColumnType(columnType);
		}
		
		public ColumnModel(String columnName, Class columnType) {
			this.columnName = columnName;
			this.columnType = columnType;
		}
		
		public String getColumnName() {
			return columnName;
		}
		
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		
		public String getKey() {
			return this.columnName.toUpperCase();
		}
		
		public Class getColumnType() {
			return columnType;
		}
		
		public void setColumnType(int columnType) {
			this.columnType = SqlMappingJavaType.getJavaTypeFromSqlType(columnType);
		}
	}
	
	class FieldModel {
		private String fieldName;
		private Class fieldType;
		private Method setter;
		private Method getter;
		
		public Method getGetter() {
			return getter;
		}
		
		public void setGetter(Method getter) {
			this.getter = getter;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public Class getFieldType() {
			return fieldType;
		}
		
		public void setFieldType(Class fieldType) {
			this.fieldType = fieldType;
		}
		
		public Method getSetter() {
			return setter;
		}
		
		public void setSetter(Method setter) {
			this.setter = setter;
		}
	}
}
