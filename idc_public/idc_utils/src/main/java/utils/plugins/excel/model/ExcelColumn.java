package utils.plugins.excel.model;

import java.util.List;
import java.util.Set;

/**
 * excel列信息
 */
public class ExcelColumn {
    /**
     * 索引
     */
    private int index;
     
    /**
     * 字段名称
     */
    private String fieldName;
     
    /**
     * 字段显示名称
     */
    private String fieldDispName;
     
    /**
     * 字段类型
     */
    private int type;//1int 、  2string  、   3date
    
    private int cols = 1;//占用列数

	public void setRows(int rows) {
		this.rows = rows;
	}

	private int rows = 1;//占用行数
    private Set<ExcelColumn> columns;//复杂列头【嵌套情况:目前只支持嵌套一层】
    
    
     
    public ExcelColumn() {
         
    }
     
    public ExcelColumn(int index, String fieldName, String fieldDispName) {
        super();
        this.index = index;
        this.fieldName = fieldName;
        this.fieldDispName = fieldDispName;
    }
     
    public ExcelColumn(int index, String fieldName, String fieldDispName, int type) {
        super();
        this.index = index;
        this.fieldName = fieldName;
        this.fieldDispName = fieldDispName;
        this.type = type;
    }
 
    public int getIndex() {
        return index;
    }
 
    public String getFieldName() {
        return fieldName;
    }
 
    public void setIndex(int index) {
        this.index = index;
    }
 
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
 
    public String getFieldDispName() {
        return fieldDispName;
    }
 
    public void setFieldDispName(String fieldDispName) {
        this.fieldDispName = fieldDispName;
    }
 
    public int getType() {
        return type;
    }
 
    public void setType(int type) {
        this.type = type;
    }
    public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}
 
    @Override
    public String toString() {
        return "ExcelColumn [fieldDispName=" + fieldDispName + ", fieldName="
                + fieldName + ", index=" + index + ", type=" + type + "]";
    }

	public Set<ExcelColumn> getColumns() {
		return columns;
	}

	public void setColumns(Set<ExcelColumn> columns) {
		this.columns = columns;
	}
 
}