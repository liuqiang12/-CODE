package utils.strategy.code.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 列的相关信息[数据库列和实体类属性的结合]
 * @author Administrator
 *
 */
public class ColumnBeanDataVO {
	private static final Log LOG = LogFactory.getLog(ColumnBeanDataVO.class);
	  /**jdbc列名称**/ 
	  private String columnName;
	  /**字段次序**/
	  private Integer ordinalPosition;
	  /**jdbc数据类型**/ 
	  private String dataType;
	  /**列注释**/ 
	  private String columnComment;
	  /**列类型**/ 
	  private String columnType;
	  /**列的最大长度**/ 
	  private String charmaxLength = "";
	  /**是否可空**/
	  private String nullable;
	  /**有效位数**/
	  private String precision;
	  /**小数位数**/
	  private String scale;
	  /**默认值**/
	  private Object columnDefault;
	  /**PRI：主键**/
	  private String columnKey;
	  /**实体类的类型**/
	  private String classType = "";
	  /**所属table**/
	  private String belong_to_table;
	  /**  The above property belongs to the database property .  **/
	  /**  The following properties belong to the entity class.  **/
	  /**class类名称**/ 
	  private String className;
	  /**class类名称**/ 
	  private String modelComment;
	  /**model列名称**/ 
	  private String modelName;
	  /**数据类型**/ 
	  private String modelType;
	  /**列的最大长度**/ 
	  private String modelcharmaxLength = "";
	  /**是否可空**/
	  private String modelnullable;
	  /**有效位数**/
	  private String modelprecision;
	  /**小数位数**/
	  private String modelscale;
	  /**是主键吗**/
	  private Boolean isPri = false;
	  /**默认值**/
	  private Object modelcolumnDefault;
	  /**get方法**/
	  private String getMethod;
	  /**set方法**/
	  private String setMethod;
	  
	  
	  
	  
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getCharmaxLength() {
		return charmaxLength;
	}
	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getBelong_to_table() {
		return belong_to_table;
	}
	public void setBelong_to_table(String belong_to_table) {
		this.belong_to_table = belong_to_table;
	}
	public Object getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(Object columnDefault) {
		this.columnDefault = columnDefault;
	}
	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}
	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getModelComment() {
		return modelComment;
	}
	public void setModelComment(String modelComment) {
		this.modelComment = modelComment;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getModelcharmaxLength() {
		return modelcharmaxLength;
	}
	public void setModelcharmaxLength(String modelcharmaxLength) {
		this.modelcharmaxLength = modelcharmaxLength;
	}
	public String getModelnullable() {
		return modelnullable;
	}
	public void setModelnullable(String modelnullable) {
		this.modelnullable = modelnullable;
	}
	public String getModelprecision() {
		return modelprecision;
	}
	public void setModelprecision(String modelprecision) {
		this.modelprecision = modelprecision;
	}
	public String getModelscale() {
		return modelscale;
	}
	public void setModelscale(String modelscale) {
		this.modelscale = modelscale;
	}
	public Boolean getIsPri() {
		return isPri;
	}
	public void setIsPri(Boolean isPri) {
		this.isPri = isPri;
	}
	public Object getModelcolumnDefault() {
		return modelcolumnDefault;
	}
	public void setModelcolumnDefault(Object modelcolumnDefault) {
		this.modelcolumnDefault = modelcolumnDefault;
	}
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	public String getSetMethod() {
		return setMethod;
	}
	public void setSetMethod(String setMethod) {
		this.setMethod = setMethod;
	}
}
