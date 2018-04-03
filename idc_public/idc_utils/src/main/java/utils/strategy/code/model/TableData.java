package utils.strategy.code.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 表相关信息
 * @author Administrator
 */
public class TableData {
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 表注释
	 */
	private String tableComment;
	/**
	 * 表中记录是否是自动增长
	 */
	private Boolean autoIncrement;
	/**
	 * 表的别名:表名+注释
	 */
	private String tableAlias;
	/**
	 * 关联表名称[一般是主表]
	 */
	private String refTableName;
	/**
	 * 关联表关联字段[一般是主表id]
	 */
	private String refColName;
	/**
	 * 外键[一般是本表的主键]
	 */
	private String foreignColumn;
	
	
	/**
	 * 表中对应的列信息
	 */
	List<ColumnData> columnDataList = Lists.newArrayList();
	/**
	 * 所关联的子表  或者多对多表【条件是主表】
	 */
	private List<TableData> refTables = Lists.newArrayList();
	/**
	 * 关联模式： 一对一  一对多  多对多
	 */
	private String refModel;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public Boolean getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	public List<ColumnData> getColumnDataList() {
		return columnDataList;
	}
	public void setColumnDataList(List<ColumnData> columnDataList) {
		this.columnDataList = columnDataList;
	}
	public void addColumnData(ColumnData columnData){
		if(columnDataList.isEmpty() || !columnDataList.contains(columnData)){
			this.columnDataList.add(columnData);
		}
	}
	public List<TableData> getRefTables() {
		return refTables;
	}
	public void setRefTables(List<TableData> refTables) {
		this.refTables = refTables;
	}
	public void addRefTables(TableData tableData){
		if(refTables.isEmpty() || !refTables.contains(tableData)){
			this.refTables.add(tableData);
		}
	}
	public String getRefTableName() {
		return refTableName;
	}
	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}
	public String getRefColName() {
		return refColName;
	}
	public void setRefColName(String refColName) {
		this.refColName = refColName;
	}
	@Deprecated
	public String getRefModel() {
		return refModel;
	}
	@Deprecated
	public void setRefModel(String refModel) {
		this.refModel = refModel;
	}
	public String getForeignColumn() {
		return foreignColumn;
	}
	public void setForeignColumn(String foreignColumn) {
		this.foreignColumn = foreignColumn;
	}
	
	
}
