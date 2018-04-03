package utils.plugins.excel.model;

import java.util.List;
import java.util.Map;


/**
* Excel头信息
*
*/
public class ExcelHead {
   /**
    * 列信息
    */
   private List<ExcelColumn> columns;
    
   /**
    * 需要转换的列
    */
   private Map<String, Map> columnsConvertMap;
    
   /**
    * 头部所占用的行数
    */
   private int rowCount;
    
   /**
    * 头部所占用的列数
    */
   private int columnCount;

   public List<ExcelColumn> getColumns() {
       return columns;
   }

   public int getRowCount() {
       return rowCount;
   }

   public int getColumnCount() {
       return columnCount;
   }

   public void setColumns(List<ExcelColumn> columns) {
       this.columns = columns;
   }

   public void setRowCount(int rowCount) {
       this.rowCount = rowCount;
   }

   public void setColumnCount(int columnCount) {
       this.columnCount = columnCount;
   }

   public Map<String, Map> getColumnsConvertMap() {
       return columnsConvertMap;
   }

   public void setColumnsConvertMap(Map<String, Map> columnsConvertMap) {
       this.columnsConvertMap = columnsConvertMap;
   }

   @Override
   public String toString() {
       return "ExcelHead [columnCount=" + columnCount + ", columns=" + columns
               + ", columnsConvertMap=" + columnsConvertMap + ", rowCount="
               + rowCount + "]";
   }

}
