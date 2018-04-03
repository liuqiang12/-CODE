package utils.strategy.code.model;

import java.util.List;

import utils.typeHelper.StringHelper;

import com.google.common.collect.Lists;

/**
 * 表相关信息
 * @author Administrator
 */
public class EffectClassData {
	/**
	 * 类名称
	 */
	private String className;
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
	private String refTablePro;
	/**
	 * 关联表关联字段[一般是主表id]
	 */
	private String refColPro;
	/**
	 * 外键[一般是本表的主键]
	 */
	private String foreignPro;
	
	/**
	 * 实体类中的toString方法
	 */
	private String toStringValue;
	
	
	/**
	 * 表中对应的列信息
	 */
	List<EffectModel> effectModelDataList = Lists.newArrayList();
	
	/**
	 * 表中对应的列信息[只是获取日期格式的数据]
	 */
	List<EffectModel> effectModelDataWithDateList = Lists.newArrayList();
	
	/**
	 * 实体类和数据库属性的综合体
	 */
	List<ColumnBeanDataVO> columnBeanDataVOList = Lists.newArrayList();
	
	/**
	 * 所关联的子表  或者多对多表【条件是主表】
	 */
	private List<EffectClassData> refClassDatas = Lists.newArrayList();
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
	public List<EffectModel> getEffectModelDataList() {
		return effectModelDataList;
	}
	public void setEffectModelDataList(List<EffectModel> effectModelDataList) {
		this.effectModelDataList = effectModelDataList;
	}
	public void addEffectModelData(EffectModel effectModel){
		if(effectModelDataList.isEmpty() || !effectModelDataList.contains(effectModel)){
			this.effectModelDataList.add(effectModel);
		}
	}
	public List<EffectClassData> getRefTables() {
		return refClassDatas;
	}
	public void setRefTables(List<EffectClassData> refTables) {
		this.refClassDatas = refTables;
	}
	public void addRefTables(EffectClassData effectClassData){
		if(refClassDatas.isEmpty() || !refClassDatas.contains(effectClassData)){
			this.refClassDatas.add(effectClassData);
		}
	}
	public String getRefTablePro() {
		return refTablePro;
	}
	public void setRefTablePro(String refTablePro) {
		this.refTablePro = StringHelper.getInstance().reEscapeName(refTablePro, true);
	}
	public String getRefColPro() {
		return refColPro;
	}
	public void setRefColPro(String refColPro) {
		this.refColPro = StringHelper.getInstance().reEscapeName(refColPro, true);
	}
	@Deprecated
	public String getRefModel() {
		return refModel;
	}
	@Deprecated
	public void setRefModel(String refModel) {
		this.refModel = refModel;
	}
	public String getForeignPro() {
		return foreignPro;
	}
	public void setForeignPro(String foreignPro) {
		this.foreignPro = StringHelper.getInstance().reEscapeName(refTablePro, true);
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String belong_to_table) {
		StringHelper helper = StringHelper.getInstance();
		this.className = helper.reEscapeName(belong_to_table, true);
	}
	public List<EffectClassData> getRefClassDatas() {
		return refClassDatas;
	}
	public void setRefClassDatas(List<EffectClassData> refClassDatas) {
		this.refClassDatas = refClassDatas;
	}
	public List<EffectModel> getEffectModelDataWithDateList() {
		return effectModelDataWithDateList;
	}
	public void setEffectModelDataWithDateList(
			List<EffectModel> effectModelDataWithDateList) {
		this.effectModelDataWithDateList = effectModelDataWithDateList;
	}
	public List<ColumnBeanDataVO> getColumnBeanDataVOList() {
		return columnBeanDataVOList;
	}
	public void setColumnBeanDataVOList(List<ColumnBeanDataVO> columnBeanDataVOList) {
		this.columnBeanDataVOList = columnBeanDataVOList;
	}
	public String getToStringValue() {
		return toStringValue;
	}
	public void setToStringValue(String toStringValue) {
		this.toStringValue = toStringValue;
	}
	
	
}
