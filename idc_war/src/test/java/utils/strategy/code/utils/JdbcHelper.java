package utils.strategy.code.utils;

import org.apache.commons.lang.StringUtils;
import utils.plugins.cfg.CfgHelper;
import utils.strategy.code.model.ColumnData;
import utils.typeHelper.StringHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * jdbc帮助类 单例【线程安全】
 * 
 * @author Administrator
 * 
 */
public class JdbcHelper {

	private static utils.strategy.code.utils.JdbcHelper instance;

	private JdbcHelper() {
	}

    private String sql;// 预加载sql
	private String propertyPath;// 相对src的配置文件

	public static synchronized utils.strategy.code.utils.JdbcHelper getInstance() {
		if (instance == null) {
			

			instance = new utils.strategy.code.utils.JdbcHelper();
		}
		return instance;
	}

	public utils.strategy.code.utils.JdbcHelper load(String sql, String propertyPath) {
		this.sql = sql;
		this.propertyPath = propertyPath;
		return instance;
	}

	public static Connection conn = null;
	public static PreparedStatement pst = null;

	/**
	 * 预加载sql jdbc配置来自于配置文件
	 * 
	 * @param sql
	 * @param propertyPath
	 */
	public PreparedStatement getPst() {
		try {
			CfgHelper cfgHelper = CfgHelper.getInstance().load(propertyPath);

			String jdbc_driver = cfgHelper.getKeyValue("jdbc.driver");

			String jdbc_url_master = cfgHelper.getKeyValue("jdbc.url.master");
			String jdbc_username_master = cfgHelper
					.getKeyValue("jdbc.username.master");
			String jdbc_password_master = cfgHelper
					.getKeyValue("jdbc.password.master");

			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url_master,
					jdbc_username_master, jdbc_password_master);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pst;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * jdbc执行前的 预定义sql
	 * <p>
	 * 
	 * @param sql
	 * @param preChar
	 * @return
	 */
	public String preSql(String sql, String preChar) {
		StringHelper stringHelper = StringHelper.getInstance();
		List<String> braceContentList = stringHelper.regexBraceContentList(sql,
				preChar);
		for (String paramKey : braceContentList) {
			// 转义
			String escape = stringHelper.new EscapeUtils().escape(paramKey,
					null);
			// 替换
			sql = stringHelper.appendReplacement(sql, escape, " ? ");
		}
		return sql;
	}

	/**
	 * <p>
	 * jdbc执行前的 预定义参数配置
	 * <p>
	 * 
	 * @param sql
	 * @param preChar
	 * @return
	 * @throws SQLException
	 */
	public void preSqlParams(String sql, String preChar,
			Map<String, String> map, PreparedStatement pst) throws SQLException {
		StringHelper stringHelper = StringHelper.getInstance();
		List<String> braceContentList = stringHelper.regexBraceContent(sql,
				preChar);
		int pstIndx = 1;
		for (String paramKey : braceContentList) {
			String paramKeyTrim = StringHelper.getInstance().removeNullEmpty(
					paramKey);
			if (map.containsKey(paramKeyTrim)) {
				pst.setString(pstIndx, map.get(paramKeyTrim));// 对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
				pstIndx++;
			}
		}
	}

	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "java.lang.String";
		else if (dataType.contains("tinyint"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("bigint"))
			dataType = "java.lang.BigDecimal";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "java.lang.Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale))
					&& (Integer.parseInt(scale) > 0))
				dataType = "java.math.BigDecimal";
			else if ((StringUtils.isNotBlank(precision))
					&& (Integer.parseInt(precision) > 6))
				dataType = "java.lang.Long";
			else
				dataType = "java.lang.Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}

	public String getTypeName(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "String";
		else if (dataType.contains("tinyint"))
			dataType = "Integer";
		else if (dataType.contains("bigint"))
			dataType = "BigDecimal";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale))
					&& (Integer.parseInt(scale) > 0))
				dataType = "BigDecimal";
			else if ((StringUtils.isNotBlank(precision))
					&& (Integer.parseInt(precision) > 6))
				dataType = "Long";
			else
				dataType = "Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "Timestamp";
		else if (dataType.contains("clob"))
			dataType = "byte[]";
		else if (dataType.contains("blob"))
			dataType = "byte[]";
		else {
			dataType = "Object";
		}
		return dataType;
	}

	public Boolean isNull(String nullable) {
        return nullable != null && "YES".equals(nullable);
    }

	public String getTypeAnnoName(ColumnData columnData) {
		StringBuffer annosb = new StringBuffer();
		if (columnData.getColumnComment() != null
				&& !"".equals(columnData.getColumnComment())) {
			annosb.append("@Column(name = \"" + columnData.getColumnName()
					+ "\" , columnDefinition=\""
					+ columnData.getColumnComment() + "\" , nullable =  "
					+ isNull(columnData.getNullable()) + "");
		} else {
			annosb.append("@Column(name = \"" + columnData.getColumnName()
					+ "\" , nullable =  " + isNull(columnData.getNullable())
					+ "");
		}
		if (columnData.getCharmaxLength() != null
				&& !"".equals(columnData.getCharmaxLength())) {
			annosb.append(", length = " + columnData.getCharmaxLength() + ")");
		}
		if (columnData.getDataType().contains("float")) {
			// 如果是浮点类型, precision = 12, scale = 0
			annosb.append(", precision = " + columnData.getPrecision()
					+ ", scale = " + columnData.getScale() + "\")");
		} else if (columnData.getDataType().contains("Date")) {
			// 如果是日期类型
			annosb.append("\r\t @DateTimeFormat(pattern=\"yyyy-MM-dd\")")
					.append("\r\t  @Temporal(TemporalType.DATE)");
		} else {
			if (!annosb.toString().endsWith(")")) {
				annosb.append(")");
			}
		}
		return annosb.toString();
	}

	/**
	 * 
	 * @param stringList
	 * @return
	 */
	public String listToStringWithJdbc(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		 // 6个一组
        int i = 1;
        
		for (String string : stringList) {
			if (!flag) {
				string = string.replace("\",", "");
			}  
			flag = true;
			result.append(string);
			if(i % 5 == 0){
				result.append("\r\n\t\t");
			}
			i++;
		}
		return result.toString();
	}
}
