package utils.strategy.code.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 列的相关信息
 * @author Administrator
 *
 */
public class ColumnData {
	private static final Log LOG = LogFactory.getLog(ColumnData.class);
	  /**jdbc列名称**/ 
	  private String columnName;//表和列信息作为唯一标示
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
}
