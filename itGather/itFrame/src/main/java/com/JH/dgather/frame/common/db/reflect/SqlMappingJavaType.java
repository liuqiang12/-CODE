/*
 * @(#)SqlMappingJavaType.java 12/26/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.common.db.reflect;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;

/**
 * <code>SqlMappingJavaType</code> java.sql.type 与 java类型对应
 * 才用hibernate的对应关系ϵ
 * 
 * @author yangDS
 */
public class SqlMappingJavaType {
	
	public static HashMap<Integer, Class> mapping = new HashMap<Integer, Class>();
	static {
		mapping.put(Types.TINYINT, Byte.class);
		mapping.put(Types.SMALLINT, Short.class);
		mapping.put(Types.INTEGER, Integer.class);
		mapping.put(Types.BIGINT, Long.class);
		mapping.put(Types.FLOAT, Float.class);
		mapping.put(Types.DOUBLE, Double.class);
		mapping.put(Types.NUMERIC, BigDecimal.class);
		mapping.put(Types.CHAR, Character.class);
		mapping.put(Types.BIT, Boolean.class);
		mapping.put(Types.VARCHAR, String.class);
		mapping.put(Types.DATE, Date.class);
		mapping.put(Types.TIME, Date.class);
		mapping.put(Types.TIMESTAMP, Date.class);
		mapping.put(Types.BINARY, byte[].class);
		mapping.put(Types.BLOB, byte[].class);
		mapping.put(Types.VARBINARY, byte[].class);
		mapping.put(Types.CLOB, String.class);
		//		mapping.put( Types. , value )
	}
	
	/**
	 * 返回与sqlType对应的javatype
	 * @param sqlType
	 * @return
	 */
	public static Class getJavaTypeFromSqlType(int sqlType) {
		return mapping.get(sqlType);
	}
	
}
