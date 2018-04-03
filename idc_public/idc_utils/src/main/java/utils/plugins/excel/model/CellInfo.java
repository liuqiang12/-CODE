package utils.plugins.excel.model;


/**
 * excel列信息[列]
 */
public class CellInfo {
	private String fieldDispName;//描述
	private String id ;//id标识
	private String fieldName ;//属性名称
	private String cols ;//跨列数
	private String cellRangeAddress_rows ;//合并的行情况[合并列相同]
	private String cellIndex_from;//单元格开始的索引[通过xpath方式查找]
	private String foreach;//动态创建的单元格:[处理的时候，需要判断传递进来的是什么类型map list等]
	private String level;//层级：从3级开始计算
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCellIndex_from() {
		return cellIndex_from;
	}
	public void setCellIndex_from(String cellIndex_from) {
		this.cellIndex_from = cellIndex_from;
	}
	public String getForeach() {
		return foreach;
	}
	public void setForeach(String foreach) {
		this.foreach = foreach;
	}
	public String getFieldDispName() {
		return fieldDispName;
	}
	public void setFieldDispName(String fieldDispName) {
		this.fieldDispName = fieldDispName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getCellRangeAddress_rows() {
		return cellRangeAddress_rows;
	}
	public void setCellRangeAddress_rows(String cellRangeAddress_rows) {
		this.cellRangeAddress_rows = cellRangeAddress_rows;
	}
	 
	
}