package system.data.page.core;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import system.data.page.PageBean;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @类说明：
 * @版本 version 1.0
 */
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class,Integer.class }),  
@Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })    
public class PagePlugin implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(PagePlugin.class);

	public static final String MYSQL = "mysql";
	public static final String ORACLE = "oracle";

	protected String databaseType;// 数据库类型，不同的数据库有不同的分页方法

	@SuppressWarnings("rawtypes")
	protected ThreadLocal<PageBean> pageThreadLocal = new ThreadLocal<PageBean>();

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		if (!databaseType.equalsIgnoreCase(MYSQL) && !databaseType.equalsIgnoreCase(ORACLE)) {
			throw new PageNotSupportException("Page not support for the type of database, database type [" + databaseType + "]");
		}
		this.databaseType = databaseType;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String databaseType = properties.getProperty("databaseType");
		if (databaseType != null) {
			setDatabaseType(databaseType);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof StatementHandler) { // 控制SQL和查询总数的地方
			PageBean page = pageThreadLocal.get();
			if (page == null) { //不是分页查询
				return invocation.proceed();
			}

			RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
			StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
			BoundSql boundSql = delegate.getBoundSql();
//            String originalSql = boundSql.getSql().trim();
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//                    .getAuthentication()
//                    .getPrincipal();

            Connection connection = (Connection) invocation.getArgs()[0];
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT d.id, d.type FROM sys_userinfo a LEFT JOIN sys_ln_user_usergrp b ON a.id = b.user_id LEFT JOIN sys_ln_usergrp_role c ON c.ugrp_id = b.ugrp_id LEFT JOIN sys_roleinfo d ON c.role_id = d.id WHERE a.username =? UNION ( SELECT c.id, c.type FROM sys_userinfo a LEFT JOIN sys_ln_user_role b ON a.id = b.user_id LEFT JOIN sys_roleinfo c ON b.role_id = c.id WHERE a.username =? )");
//            preparedStatement.setString(1, userDetails.getUsername());
//            preparedStatement.setString(2, userDetails.getUsername());
//            ResultSet rs = preparedStatement.executeQuery();
//            Set<String> roleType = new HashSet<>();
//            List<Integer> roleId = new ArrayList<>();
//            String role=null;
//            if (rs.next()) {
//                role=rs.getString(1);
//                roleType.add(role);
//                roleId.add(rs.getInt(2));
//            }
//            rs.close();
//            preparedStatement.close();
//            if(roleType.contains("admin")){
//                //all
//            }else{
//               if(originalSql.toLowerCase().indexOf("regionid")!=-1){
//                   if(originalSql.toLowerCase().indexOf("where")!=-1){//已经有条件
//                       originalSql= originalSql.replace("where","where regionid in ("+StringUtils.ArrayToString(roleId)+") and");
//                   }else{//没有条件
//                       originalSql+=" where regionid in ("+ StringUtils.ArrayToString(roleId)+")";
//                   }
//               }
//            }
            prepareAndCheckDatabaseType(connection); // 准备数据库类型

			if (page.getTotalPage() > -1) {
				if (log.isTraceEnabled()) {
					log.trace("已经设置了总页数, 不需要再查询总数.");
				}
			} else {
				Object parameterObj = boundSql.getParameterObject();
				MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
				queryTotalRecord(page, parameterObj, mappedStatement, connection);
			}

			String sql = boundSql.getSql();
			String pageSql = buildPageSql(page, sql);
			if (log.isDebugEnabled()) {
				log.debug("分页时, 生成分页pageSql: " + pageSql);
			}
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);

			return invocation.proceed();
		} else { // 查询结果的地方
			// 获取是否有分页Page对象
			PageBean<?> page = findPageObject(invocation.getArgs()[1]);
			if (page == null) {
				if (log.isDebugEnabled()) {
					//log.info("没有Page对象作为参数, 不是分页查询.");
				}
				return invocation.proceed();
			} else {
				if (log.isDebugEnabled()) {
					log.info("检测到分页Page对象, 使用分页查询.");
				}
			}
			//设置真正参数是pageBean中的param
//			invocation.getArgs()[1] = invocation.getArgs()[1];

			pageThreadLocal.set(page);
			try {
				Object resultObj = invocation.proceed(); // Executor.query(..)
				if (resultObj instanceof List) {
					/* @SuppressWarnings({ "unchecked", "rawtypes" }) */
					page.setItems((List) resultObj);
				}
				return resultObj;
			} finally {
				pageThreadLocal.remove();
			}
		}
	}

	protected PageBean<?> findPageObject(Object parameterObj) {
		if (parameterObj instanceof PageBean<?>) {
			return (PageBean<?>) parameterObj;
		} else if (parameterObj instanceof Map) {
			for (Object val : ((Map<?, ?>) parameterObj).values()) {
				if (val instanceof PageBean<?>) {
					return (PageBean<?>) val;
				}
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * 把真正的参数对象解析出来
	 * </pre>
	 * @param parameterObj
	 * @return
	 */
	protected Object extractRealParameterObject(Object parameterObj) {
		//获取真正的参数[参数都放置在pageBean的params中]
		if(parameterObj instanceof PageBean<?>){
			return ((PageBean) parameterObj).getParams();
		}
		return parameterObj;
	}

	protected void prepareAndCheckDatabaseType(Connection connection) throws SQLException {
		if (databaseType == null) {
			String productName = connection.getMetaData().getDatabaseProductName();
			if (log.isTraceEnabled()) {
				log.trace("Database productName: " + productName);
			}
			productName = productName.toLowerCase();
			if (productName.indexOf(MYSQL) != -1) {
				databaseType = MYSQL;
			} else if (productName.indexOf(ORACLE) != -1) {
				databaseType = ORACLE;
			} else {
				throw new PageNotSupportException("Page not support for the type of database, database product name [" + productName + "]");
			}
			if (log.isInfoEnabled()) {
				log.info("自动检测到的数据库类型为: " + databaseType);
			}
		}
	}

	/**
	 * <pre>
	 * 生成分页SQL
	 * </pre>
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	protected String buildPageSql(PageBean<?> page, String sql) {
		if (MYSQL.equalsIgnoreCase(databaseType)) {
			return buildMysqlPageSql(page, sql);
		} else if (ORACLE.equalsIgnoreCase(databaseType)) {
			return buildOraclePageSql(page, sql);
		}
		return sql;
	}

	/**
	 * <pre>
	 * 生成Mysql分页查询SQL
	 * </pre>
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	protected String buildMysqlPageSql(PageBean<?> page, String sql) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNo()-1) * page.getPageSize();
		if(page.getPageNo()==-1&&page.getPageSize()==-1){
            return sql;
        }
		return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(page.getPageSize()).toString();
	}

	/**
	 * <pre>
	 * 生成Oracle分页查询SQL
	 * </pre>
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	protected String buildOraclePageSql(PageBean<?> page, String sql) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
		StringBuilder sb = new StringBuilder(sql);
		sb.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
		sb.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sb.toString();
	}

	/**
	 * <pre>
	 * 查询总数
	 * </pre>
	 * 
	 * @param page
	 * @param parameterObject
	 * @param mappedStatement
	 * @param connection
	 * @throws SQLException
	 */
	protected void queryTotalRecord(PageBean<?> page, Object parameterObject, MappedStatement mappedStatement, Connection connection) throws SQLException {
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.buildCountSql(sql);
		if (log.isDebugEnabled()) {
			log.debug("分页时, 生成countSql: " + countSql);
		}

		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				long totalRecord = rs.getLong(1);
				page.setTotalRecord(totalRecord);
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					if (log.isWarnEnabled()) {
						log.warn("关闭ResultSet时异常.", e);
					}
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					if (log.isWarnEnabled()) {
						log.warn("关闭PreparedStatement时异常.", e);
					}
				}
		}
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 */
	protected String buildCountSql(String sql) {
//		int index = sql.indexOf("from");
//		return "select count(1) " + sql.substring(index);
		String countSql = "select count(0) from (" + sql
				+ ") totalnum";
		return countSql;
		
	}

	/**
	 * 利用反射进行操作的一个工具类
	 * 
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj 目标对象
		 * @param fieldName 目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj 目标对象
		 * @param fieldName 目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj 目标对象
		 * @param fieldName 目标属性
		 * @param fieldValue 目标值
		 */
		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static class PageNotSupportException extends RuntimeException {

		/** serialVersionUID*/
		private static final long serialVersionUID = 1L;

		public PageNotSupportException() {
			super();
		}

		public PageNotSupportException(String message, Throwable cause) {
			super(message, cause);
		}

		public PageNotSupportException(String message) {
			super(message);
		}

		public PageNotSupportException(Throwable cause) {
			super(cause);
		}
	}
}
