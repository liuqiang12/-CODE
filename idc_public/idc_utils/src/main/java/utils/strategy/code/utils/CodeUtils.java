package utils.strategy.code.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import utils.plugins.cfg.CfgHelper;
import utils.plugins.xml.xpath.XpathHelper;
import utils.strategy.code.ReflectionHelper;
import utils.strategy.code.model.ColumnBeanDataVO;
import utils.strategy.code.model.ColumnData;
import utils.strategy.code.model.EffectClassData;
import utils.strategy.code.model.EffectModel;
import utils.strategy.code.model.TableData;
import utils.strategy.code.model.TablePrimary;
import utils.typeHelper.StringHelper;

import com.google.common.collect.Lists;

/**
 * 代码生成器工具类
 * 单例  线程安全
 * @author Administrator
 *
 */
@SuppressWarnings("unused")
public class CodeUtils {
	private static CodeUtils instance;
	private CodeUtils(){};

	private String sql;// 预加载sql
	private String propertyPath;// 相对src的配置文件[代码生成器配置路径]
	private String preChar;//如果有参数 取参数的前缀


	/**
	 * 单例初始化实体类
	 * @return
	 */
	public static synchronized CodeUtils getInstance(){
		if(instance == null){
			instance = new CodeUtils();
		}
		return instance;
	}
	/**
	 * <p>注入必须的参数<p>
	 * @param sql
	 * @param propertyPath
	 * @param preChar
	 * @return
	 */
	public CodeUtils load(String sql, String propertyPath,String preChar){
		this.sql = sql;
		this.propertyPath = propertyPath;
		this.preChar = preChar;
		return instance;
	}
	/**
	 * <p>注入必须的参数<p>
	 * @param sql
	 * @param propertyPath
	 * @param preChar
	 * @return
	 */
	public CodeUtils load(String sql, String propertyPath){
		this.sql = sql;
		this.propertyPath = propertyPath;
		return instance;
	}
	/**
	 * <p> query all table information <p>
	 * 获取数据库中的所有表
	 * @author Administrator
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<TableData> queryTablesAll(Map<String, String> map){

		List<TableData> list = Lists.newArrayList();
		/**
		 * 1:获取jdbc
		 */
		//获取执行 sql
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		try {
			//预定义的sql
			String sqlcontent = jdbcHelper.preSql(sql, preChar);

			PreparedStatement pst = jdbcHelper.load(sqlcontent, propertyPath).getPst();
			//预参数配置
			jdbcHelper.preSqlParams(sql, preChar, map, pst);

//			pst.setString(1, tableSchema); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			ResultSet rs=null;

			rs=pst.executeQuery();
			while(rs.next()){

				String tableName = rs.getString("tableName");
			    String tableCommnet = rs.getString("tableComment");
			    Boolean autoIncrement = rs.getBoolean("autoIncrement");
			    String tableAlias = rs.getString("tableAlias");

			    TableData tableData  = new TableData();
			    tableData.setTableName(tableName);
			    tableData.setTableComment(tableCommnet);
			    tableData.setAutoIncrement(autoIncrement);
			    tableData.setTableAlias(tableAlias);

			    //加载该表中的所有字段属性
				map.put("tableName", tableName);

				Map<String, List>  columnDataMaps = queryColumnWithTable(map,true);

				List<ColumnData> listColumnData = columnDataMaps.get("listColumnData");
				List<EffectModel> listEffectModel = columnDataMaps.get("listEffectModel");

				if (listColumnData != null && !listColumnData.isEmpty()) {
					tableData.setColumnDataList(listColumnData);
				}
			    // 组装数据
			    if(( list == null || list.isEmpty() ) ){
					list.add(tableData);
				}else{
					if( !list.contains(tableData) ){
						list.add(tableData);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			jdbcHelper.close();
		}
		return list;
	}
	/**
	 * <p> query single table information.
	 * <code>Map<code><p>
	 * 获取单张表信息
	 * @param paramTable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryTable(Map<String, String> map){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		TableData tableData  = new TableData();
		EffectClassData effectClassData  = new EffectClassData();
		/**
		 * 1:获取jdbc
		 */
		//获取执行 sql
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		try {
			//预定义的sql
			String sqlcontent = jdbcHelper.preSql(sql, preChar);

			PreparedStatement pst = jdbcHelper.load(sqlcontent, propertyPath).getPst();
			//预参数配置
			jdbcHelper.preSqlParams(sql, preChar, map, pst);
//			pst.setString(1, tableSchema); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
//			pst.setString(2, paramTable); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			ResultSet rs=null;

			rs=pst.executeQuery();
			StringHelper helper = StringHelper.getInstance();
			while(rs.next()){

				String tableName = rs.getString("tableName");
				String tableCommnet = rs.getString("tableComment");
				Boolean autoIncrement = rs.getBoolean("autoIncrement");
				String tableAlias = rs.getString("tableAlias");


				tableData.setTableName(tableName);
				tableData.setTableComment(tableCommnet);
				tableData.setAutoIncrement(autoIncrement);
				tableData.setTableAlias(tableAlias);
				// 有效的实体类 属性 EffectClassData

				effectClassData.setClassName(helper.reEscapeName(tableName.toLowerCase(), true));
				effectClassData.setTableName(tableName);
				effectClassData.setTableComment(tableCommnet);
				effectClassData.setAutoIncrement(autoIncrement);

				//加载该表中的所有字段属性

				Map<String, List>  columnDataMaps = queryColumnWithTable(map,true);

				List<ColumnData> listColumnData = columnDataMaps.get("listColumnData");
				List<EffectModel> listEffectModel = columnDataMaps.get("listEffectModel");//所有的实体类的属性【遍历实体类;拼接成toString()】
				List<EffectModel> listEffectModelWithDate = columnDataMaps.get("listEffectModelWithDate");
				List<ColumnBeanDataVO> listColumnBeanDataVO = columnDataMaps.get("listColumnBeanDataVO");


				if (listColumnData != null && !listColumnData.isEmpty()) {
					tableData.setColumnDataList(listColumnData);
				}
				if (listEffectModel != null && !listEffectModel.isEmpty()) {
					effectClassData.setToStringValue(makeToStr(listEffectModel));
					effectClassData.setEffectModelDataList(listEffectModel);
				}
				if (listEffectModelWithDate != null && !listEffectModelWithDate.isEmpty()) {
					effectClassData.setEffectModelDataWithDateList(listEffectModelWithDate);
				}
				if (listColumnBeanDataVO != null && !listColumnBeanDataVO.isEmpty()) {
					effectClassData.setColumnBeanDataVOList(listColumnBeanDataVO);
				}
				Map<String, List> refMaps = queryRefTables(map, true);
//				List<TableData> tableDataList  = Lists.newArrayList();
//				List<EffectClassData> effectClassDataList  = Lists.newArrayList();
//				List
				List<TableData> tableDataList = (List<TableData>) refMaps.get("tableDataList");
				List<EffectClassData> effectClassDataList = (List<EffectClassData>) refMaps.get("effectClassDataList");
				if (tableDataList != null && !tableDataList.isEmpty()) {
					tableData.setRefTables(tableDataList);
				}
				if (effectClassDataList != null && !effectClassDataList.isEmpty()) {
					effectClassData.setRefClassDatas(effectClassDataList);
				}

				resultMap.put("tableData", tableData);

				resultMap.put("effectClassData", effectClassData);
				// 获取日期类型的数据
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			jdbcHelper.close();
		}
		return resultMap;
	}
	/**
	 * 构建实体类的toString方法
	 * @param listEffectModel
	 * @return
	 */
	/**
	 * 				"id=" + id + ",""
	 * @return
	 */
	public String makeToStr(List<EffectModel> listEffectModel){
		StringBuffer toStringSb = new StringBuffer();
		toStringSb.append("\r\t").append("@Override").append("\r\t").append("public String toString() {\r\t");
		toStringSb.append("		return ");

		Iterator<EffectModel> it = listEffectModel.iterator();
		List<String> list = Lists.newArrayList();
//		toStringSb.append("\""+listEffectModel.get(0).getClassName()).append(" [");
		while (it.hasNext()) {
			EffectModel entity = (EffectModel) it.next();
			String modeName = entity.getModelName();
			list.add("\","+modeName+" = \"+this."+modeName+"+");
		}
		String toStringParam = " \""+listEffectModel.get(0).getClassName()+" ["+JdbcHelper.getInstance().listToStringWithJdbc(list)+"\" ]\";";
		toStringSb.append(toStringParam);
		toStringSb.append(" \r\t}");
		System.out.println(toStringSb);

		return toStringSb.toString();
	}




	/**
	 * <p>  Table information of fuzzy query database .
	 * <code>Map<code><p>
	 * 模糊查询数据库中表信息
	 * @param fuzzyCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TableData> queryFuzzyTable(Map<String, String> map){
		List<TableData> list  = Lists.newArrayList();
		/**
		 * 1:获取jdbc
		 */
		//获取执行 sql
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		try {
			//预定义的sql
			String sqlcontent = jdbcHelper.preSql(sql, preChar);

			PreparedStatement pst = jdbcHelper.load(sqlcontent, propertyPath).getPst();
			//预参数配置
			jdbcHelper.preSqlParams(sql, preChar, map, pst);
//			pst.setString(1, tableSchema); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
//			pst.setString(2, fuzzyCondition); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			ResultSet rs=null;

			rs=pst.executeQuery();
			while(rs.next()){

				String tableName = rs.getString("tableName");
				String tableCommnet = rs.getString("tableComment");
				Boolean autoIncrement = rs.getBoolean("autoIncrement");
				String tableAlias = rs.getString("tableAlias");

				TableData tableData = new TableData();
				tableData.setTableName(tableName);
				tableData.setTableComment(tableCommnet);
				tableData.setAutoIncrement(autoIncrement);
				tableData.setTableAlias(tableAlias);
				//加载该表中的所有字段属性
				map.put("params", tableName);

				Map<String, List>  columnDataMaps = queryColumnWithTable(map,true);

				List<ColumnData> listColumnData = columnDataMaps.get("listColumnData");
				List<EffectModel> listEffectModel = columnDataMaps.get("listEffectModel");

				if (listColumnData != null && !listColumnData.isEmpty()) {
					tableData.setColumnDataList(listColumnData);
				}
//				if (listEffectModel != null && !listEffectModel.isEmpty()) {
//					tableData.setColumnDataList(listColumnData);
//				}

				list.add(tableData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			jdbcHelper.close();
		}
		return list;
	}
	/**
	 * <p>   query columns information with tableName from database .
	 * <code>Map<code><p>
	 * 在数据库中根据表名查询列信息
	 * @param map
	 * @param isReSql:true：再次解析sql 默认是不再次解析
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List> queryColumnWithTable(Map<String, String> map,Boolean isReSql){
		Map<String, List> resultMap =  new HashMap<String, List>();


		List<EffectModel> listEffectModelWithDate  = Lists.newArrayList();//只是存放日期格式数据
		List<EffectModel> listEffectModel  = Lists.newArrayList();//实体类属性容器
		List<ColumnData> listColumnData  = Lists.newArrayList();//数据库列表容器
		List<ColumnBeanDataVO> listBeanDataVOs = Lists.newArrayList();// 实体类和数据库属性综合容器
		/**
		 * 1:获取jdbc
		 */
		//获取执行 sql
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		try {
			//预定义的sql
			//这里需要再次解析sql
			String reSql = sql;
			if (isReSql) {
				reSql = XpathHelper.getInstance().getXMLSqlByPath(CfgHelper.getInstance().getKeyValue("code_generator_sqlMapper"), "queryColumnWithTable");
			}
			String sqlcontent = jdbcHelper.preSql(reSql, preChar);
			PreparedStatement pst = jdbcHelper.load(sqlcontent, propertyPath).getPst();
			//预参数配置
			jdbcHelper.preSqlParams(reSql, preChar, map, pst);

			ResultSet rs=null;
			rs=pst.executeQuery();
			while(rs.next()){

				String belong_to_table = rs.getString("belong_to_table");
				String columnName = rs.getString("columnName");
				int ordinalPosition = rs.getInt("ordinalPosition");
				String columnDefault = rs.getString("columnDefault");
				String nullable = rs.getString("nullable");
				String dataType = rs.getString("dataType");
				String precision = rs.getString("precision");
				String scale = rs.getString("scale");
				String columnType = rs.getString("columnType");
				String columnKey = rs.getString("columnKey");
				String columnComment = rs.getString("columnComment");
				String charmaxLength = rs.getString("charmaxLength");

				ColumnData columnData = new ColumnData();
				columnData.setBelong_to_table(belong_to_table);
				columnData.setColumnName(columnName);
				columnData.setOrdinalPosition(ordinalPosition);
				columnData.setColumnDefault(columnDefault);
				columnData.setNullable(nullable);
				columnData.setDataType(dataType);
				columnData.setPrecision(precision);
				columnData.setScale(scale);
				columnData.setColumnType(columnType);
				columnData.setColumnKey(columnKey);
				columnData.setColumnComment(columnComment);
				columnData.setCharmaxLength(charmaxLength);

				EffectModel effectModel = new EffectModel();
				effectModel.setClassName(belong_to_table.toLowerCase());
				effectModel.setModelName(columnName.toLowerCase(),false);
				effectModel.setModelType(columnType, precision, scale);
				effectModel.setModelcharmaxLength(charmaxLength);
				effectModel.setModelnullable(nullable);
				effectModel.setModelprecision(precision);
				effectModel.setModelscale(scale);
				effectModel.setModelcolumnDefault(columnDefault);
				effectModel.setModelComment(columnComment);
				effectModel.setIsPri(columnKey!=null&&"PRI".equals(columnKey)?true:false);

				EffectModel effectModelWithDate = new EffectModel();
				if(
						effectModel.getModelType().toLowerCase().indexOf("timestamp") > -1
						||
						effectModel.getModelType().toLowerCase().indexOf("date") > -1
				){
					effectModelWithDate.setClassName(belong_to_table.toLowerCase());
					effectModelWithDate.setModelName(columnName.toLowerCase(),true);
					effectModelWithDate.setModelType(columnType, precision, scale);
					effectModelWithDate.setModelcharmaxLength(charmaxLength);
					effectModelWithDate.setModelnullable(nullable);
					effectModelWithDate.setModelprecision(precision);
					effectModelWithDate.setModelscale(scale);
					effectModelWithDate.setModelcolumnDefault(columnDefault);
					effectModelWithDate.setModelComment(columnComment);
					effectModelWithDate.setIsPri(columnKey!=null&&"PRI".equals(columnKey)?true:false);
					/**
					 * setget方法
					 */
					effectModelWithDate.setGetMethod(ReflectionHelper.createModelGetMethodName(columnName+"_str", "String"));
					effectModelWithDate.setSetMethod(ReflectionHelper.createModelSetMethodNameWithDateStr(columnName,effectModel.getModelType() ,columnData,false));
					listEffectModelWithDate.add(effectModelWithDate);
				}
				/**
				 * setget方法
				 */
				effectModel.setGetMethod(ReflectionHelper.createModelGetMethodName(columnName, effectModel.getModelType()));
				effectModel.setSetMethod(ReflectionHelper.createModelSetMethodName(columnName, effectModel.getModelType(),columnData,true));

				ColumnBeanDataVO columnBeanDataVO = new ColumnBeanDataVO();
				BeanUtils.copyProperties(effectModel, columnBeanDataVO);
				BeanUtils.copyProperties(columnData, columnBeanDataVO,"");

				listBeanDataVOs.add(columnBeanDataVO);
				listColumnData.add(columnData);
				listEffectModel.add(effectModel);
			}
			if (listColumnData != null && !listColumnData.isEmpty()) {
				resultMap.put("listColumnData", listColumnData);
			}

			if (listEffectModel != null && !listEffectModel.isEmpty()) {
				resultMap.put("listEffectModel", listEffectModel);
			}

			if (listEffectModelWithDate != null && !listEffectModelWithDate.isEmpty()) {
				resultMap.put("listEffectModelWithDate", listEffectModelWithDate);
			}
			if (listBeanDataVOs != null && !listBeanDataVOs.isEmpty()) {
				resultMap.put("listColumnBeanDataVO", listBeanDataVOs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			jdbcHelper.close();
		}
		return resultMap;
	}
	/**
	 * <p>    query the relational table information for the specified table  .
	 * <code>Map<code><p>
	 * 获取指定表的关联表信息
	 * @param map
	 * @param isReSql:是否重新加载sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List> queryRefTables(Map<String, String> map,Boolean isReSql){
		Map<String, List> resultMap = new HashMap<String, List>();

		List<TableData> tableDataList  = Lists.newArrayList();
		List<EffectClassData> effectClassDataList  = Lists.newArrayList();
		/**
		 * 1:获取jdbc
		 */
		//获取执行 sql
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		try {
			//预定义的sql
			String reSql = sql;
			if (isReSql) {
				reSql = XpathHelper.getInstance().getXMLSqlByPath(CfgHelper.getInstance().getKeyValue("code_generator_sqlMapper"), "queryRefTables");
			}
			String sqlcontent = jdbcHelper.preSql(reSql, preChar);

			PreparedStatement pst = jdbcHelper.load(sqlcontent, propertyPath).getPst();
			//预参数配置
			jdbcHelper.preSqlParams(reSql, preChar, map, pst);
			ResultSet rs=null;

			rs=pst.executeQuery();
			while(rs.next()){

				String tableName = rs.getString("tableName");
				String columnName = rs.getString("columnName");
				String refTableName = rs.getString("refTableName");
				String refColName = rs.getString("refColName");

				TableData tableData = new TableData();
				tableData.setTableName(tableName);
				tableData.setForeignColumn(columnName);
				tableData.setRefTableName(refTableName);
				tableData.setRefColName(refColName);

				EffectClassData effectClassData = new EffectClassData();
				effectClassData.setTableName(tableName);
				effectClassData.setClassName(tableName.toLowerCase());
				effectClassData.setForeignPro(columnName.toLowerCase());
				effectClassData.setRefTablePro(refTableName.toLowerCase());
				effectClassData.setRefColPro(refColName.toLowerCase());


				//加载该表中的所有字段属性
				map.put("tableName", refTableName);
				Map<String, List>  columnDataMaps = queryColumnWithTable(map,true);

				List<ColumnData> listColumnData = columnDataMaps.get("listColumnData");
				List<EffectModel> listEffectModel = columnDataMaps.get("listEffectModel");
				List<EffectModel> listEffectModelWithDate = columnDataMaps.get("listEffectModelWithDate");
				List<ColumnBeanDataVO> listColumnBeanDataVO = columnDataMaps.get("listColumnBeanDataVO");

				if (listColumnData != null && !listColumnData.isEmpty()) {
					tableData.setColumnDataList(listColumnData);
				}
				if (listEffectModel != null && !listEffectModel.isEmpty()) {
					effectClassData.setEffectModelDataList(listEffectModel);
				}
				if (listEffectModelWithDate != null && !listEffectModelWithDate.isEmpty()) {
					effectClassData.setEffectModelDataWithDateList(listEffectModelWithDate);
				}
				if (listColumnBeanDataVO != null && !listColumnBeanDataVO.isEmpty()) {
					effectClassData.setColumnBeanDataVOList(listColumnBeanDataVO);
				}
				tableDataList.add(tableData);
				effectClassDataList.add(effectClassData);
			}
			if(tableDataList != null && !tableDataList.isEmpty()){
				resultMap.put("tableDataList", tableDataList);
			}
			if(effectClassDataList != null && !effectClassDataList.isEmpty()){
				resultMap.put("effectClassDataList", effectClassDataList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			jdbcHelper.close();
		}
		return resultMap;
	}
	/**
	 * 查询所有的表信息
	 * 查询一张表信息
	 * 模糊查询表信息
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String propertyPath = "config/jdbc.properties";

		CodeUtils codeUtils = CodeUtils.getInstance() ;
		XpathHelper xpathHelper = XpathHelper.getInstance();

		CfgHelper cfgHelper = CfgHelper.getInstance().load(propertyPath);
		//表信息
		List<TableData> list = Lists.newArrayList();
		TableData tableData = new TableData();
		//列信息
		List<ColumnData> colList = Lists.newArrayList();
		ColumnData columnData = new ColumnData();

		/****改造 参数传递采用map形式:****/
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", "sys_userinfo");
		map.put("table_schema", "epc_oss");
//		map.put("params", "base_");
		/**
		 * 查询所有的表信息
		 */
//		 String sqlContent =xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryTablesAll");
//		codeUtils = codeUtils.load(sqlContent, propertyPath,cfgHelper.getKeyValue("param_prefix"));
//		list = codeUtils.queryTablesAll(map);
//		System.out.println(list);
		/**
		 * 查询一张表信息
		 */
		  String sqlContent_single = xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryTable");
		codeUtils = codeUtils.load(sqlContent_single, propertyPath,cfgHelper.getKeyValue("param_prefix"));
		Map<String, Object> codeMap = codeUtils.queryTable(map);

		System.out.println(codeMap.get("tableData"));
		System.out.println(codeMap.get("effectClassData"));
		System.out.println("111111111");
		 /**
		 * 模糊查询表信息
		 */
//		 String sqlContent_fuzzyQuery = xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryFuzzyTable");
//		codeUtils = codeUtils.load(sqlContent_fuzzyQuery, propertyPath,cfgHelper.getKeyValue("param_prefix"));
//		list= codeUtils.queryFuzzyTable(map);
//		System.out.println(list);
		 /**
		 * 获取某 表的列信息
		 */
//		String sqlContent_columnQuery = xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryColumnWithTable");
//		codeUtils = codeUtils.load(sqlContent_columnQuery, propertyPath,cfgHelper.getKeyValue("param_prefix"));
//		colList= codeUtils.queryColumnWithTable(map,false);
//		System.out.println(colList);
		/**
		 * 获取某表的关联表信息
		 */
//		String sqlContent_queryRefTables = xpathHelper.getXMLSqlByPath(cfgHelper.getKeyValue("code_generator_sqlMapper"), "queryRefTables");
//		codeUtils = codeUtils.load(sqlContent_queryRefTables, propertyPath,cfgHelper.getKeyValue("param_prefix"));
//		list= codeUtils.queryRefTables(map,false);
//		System.out.println(list);

		/**
		 * next week ：下周 写freemaker
		 */
	}
	/*工具转换类*/
	public class ToolsConvert{
		//是否是非空状态
		public String getNullAble(String nullable)
		  {
		    if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable))) {
		      return "Y";
		    }
		    if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable))) {
		      return "N";
		    }
		    return null;
		  }
	}
	public TablePrimary getTablePrimary(List<ColumnBeanDataVO> entityAndDatabase){
		Iterator<ColumnBeanDataVO> it = entityAndDatabase.iterator();
		TablePrimary tablePrimary = new TablePrimary();
		while (it.hasNext()) {
			ColumnBeanDataVO columnBeanDataVO = (ColumnBeanDataVO) it.next();
			if(columnBeanDataVO.getIsPri()){
				tablePrimary.setEntitypro(columnBeanDataVO.getModelName());
				tablePrimary.setDatabasepro(columnBeanDataVO.getColumnName());
				tablePrimary.setEntityproType(columnBeanDataVO.getModelType());
				return tablePrimary;
			}
		}
		return null;
	}
	public String getColumnToEntity(List<ColumnBeanDataVO> entityAndDatabase){
		Iterator<ColumnBeanDataVO> it = entityAndDatabase.iterator();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (it.hasNext()) {
			ColumnBeanDataVO columnBeanDataVO = (ColumnBeanDataVO) it.next();
			if(i == 0){
				sb.append(columnBeanDataVO.getColumnName()).append(" as ").append(columnBeanDataVO.getModelName()).append(",\r\t");
			}else{
				if (entityAndDatabase.size() - i - 1 == 0) {
					sb.append("\t\t").append(columnBeanDataVO.getColumnName()).append(" as ").append(columnBeanDataVO.getModelName());
				}else{
					sb.append("\t\t").append(columnBeanDataVO.getColumnName()).append(" as ").append(columnBeanDataVO.getModelName()).append(",\r\t");
				}
			}
			i++;
		}
		return sb.toString();
	}
	 public String getBeanFeilds(List<ColumnData> dataList)
			    throws SQLException
	  {
	    StringBuffer str = new StringBuffer();
	    StringBuffer getset = new StringBuffer();
	    for (ColumnData data : dataList) {
	    	String belong_to_table = data.getBelong_to_table();
			String columnName = data.getColumnName();
			int ordinalPosition = data.getOrdinalPosition();

			String nullable = data.getNullable();
			String dataType = data.getDataType();
			String precision = data.getPrecision();
			String scale = data.getScale();
			String columnType = data.getColumnType();
			String columnKey = data.getColumnKey();
			String columnComment = data.getColumnComment();
			String charmaxLength = data.getCharmaxLength();




//	      String maxChar = name.substring(0, 1).toUpperCase();
//	      str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
//	      String method = maxChar + name.substring(1, name.length());
//	      getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
//	      getset.append("    return this.").append(name).append(";\r\t}");
//	      getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
//	      getset.append("    this." + name + "=").append(name).append(";\r\t}");
	    }
//	    this.argv = str.toString();
//	    this.method = getset.toString();
//	    return this.argv + this.method;
	    return null;
	  }
	//获取表信息

	/**
	 * <p>   code.xml 文件中的配置  <p>
	 * @return
	 */
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	/**
	 * <p>   is the JDBC configuration file path  <p>
	 * <p>  e.g.
	 * 		value is config/jdbc.properties
	 * <p>
	 * @return
	 */
	public String getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	/**
	 * <p>  The prefix used for the parameter <p>
	 * <p>  e.g.
	 * 		#{ tableName } '#' characters in a word
	 * <p>
	 * @return
	 */
	public String getPreChar() {
		return preChar;
	}

	public void setPreChar(String preChar) {
		this.preChar = preChar;
	}
	public String getColumnInfo(List<ColumnBeanDataVO> entityAndDatabase) {
		Iterator<ColumnBeanDataVO> it = entityAndDatabase.iterator();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (it.hasNext()) {
			ColumnBeanDataVO columnBeanDataVO = (ColumnBeanDataVO) it.next();
			if(i == 0){
				sb.append("\t").append(columnBeanDataVO.getColumnName()+",");
			}else{
				if (entityAndDatabase.size() - i - 1 == 0) {
					sb.append(columnBeanDataVO.getColumnName());
				}else{
					sb.append(columnBeanDataVO.getColumnName()+",");
				}
			}
			i++;
		}
		return sb.toString();
	}
	public String getColumnInfoWithBatchInsert(List<ColumnBeanDataVO> entityAndDatabase) {
		Iterator<ColumnBeanDataVO> it = entityAndDatabase.iterator();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (it.hasNext()) {
			ColumnBeanDataVO columnBeanDataVO = (ColumnBeanDataVO) it.next();
			if(i == 0){
				sb.append("\t").append("#{item."+columnBeanDataVO.getModelName()+"},");
			}else{
				if (entityAndDatabase.size() - i - 1 == 0) {
					sb.append("#{item."+columnBeanDataVO.getModelName()+"}");
				}else{
					sb.append("#{item."+columnBeanDataVO.getModelName() + "},");
				}
			}
			i++;
		}
		return sb.toString();
	}
}
