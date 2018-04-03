package system.data.utils.excel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MergeInfo {
	
	class HeadInfo{
		private String property;
		private String title;
		private Integer sort;
		private Integer cellColumnIndex;
		private Integer firstColumn;
		private Integer lastColumn;
		private Integer firstRow;
		private Integer lastRow;
		public HeadInfo() {
			super();
		}
		public HeadInfo(String property, String title, Integer sort,
				Integer cellColumnIndex, Integer firstColumn,
				Integer lastColumn, Integer firstRow, Integer lastRow) {
			super();
			this.property = property;
			this.title = title;
			this.sort = sort;
			this.cellColumnIndex = cellColumnIndex;
			this.firstColumn = firstColumn;
			this.lastColumn = lastColumn;
			this.firstRow = firstRow;
			this.lastRow = lastRow;
		}
		public String getProperty() {
			return property;
		}
		public void setProperty(String property) {
			this.property = property;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Integer getSort() {
			return sort;
		}
		public void setSort(Integer sort) {
			this.sort = sort;
		}
		public Integer getCellColumnIndex() {
			return cellColumnIndex;
		}
		public void setCellColumnIndex(Integer cellColumnIndex) {
			this.cellColumnIndex = cellColumnIndex;
		}
		public Integer getFirstColumn() {
			return firstColumn;
		}
		public void setFirstColumn(Integer firstColumn) {
			this.firstColumn = firstColumn;
		}
		public Integer getLastColumn() {
			return lastColumn;
		}
		public void setLastColumn(Integer lastColumn) {
			this.lastColumn = lastColumn;
		}
		public Integer getFirstRow() {
			return firstRow;
		}
		public void setFirstRow(Integer firstRow) {
			this.firstRow = firstRow;
		}
		public Integer getLastRow() {
			return lastRow;
		}
		public void setLastRow(Integer lastRow) {
			this.lastRow = lastRow;
		}
		
		
	}
	
	public MergeInfo(String mergeKey, String mergeName, Integer firstColumn,
			Integer lastColumn, Integer firstRow, Integer lastRow) {
		super();
		this.mergeKey = mergeKey;
		this.mergeName = mergeName;
		this.firstColumn = firstColumn;
		this.lastColumn = lastColumn;
		this.firstRow = firstRow;
		this.lastRow = lastRow;
	}
	
	public MergeInfo(Integer firstColumn, Integer lastColumn, Integer firstRow,
			Integer lastRow) {
		super();
		this.firstColumn = firstColumn;
		this.lastColumn = lastColumn;
		this.firstRow = firstRow;
		this.lastRow = lastRow;
	}

	public MergeInfo() {
		super();
	}
	private String mergeKey;
	private String mergeName;
	private Integer firstColumn;
	private Integer lastColumn;
	private Integer firstRow;
	private Integer lastRow;
	private Map<String,HeadInfo> propertyColumnMap=new HashMap<String, HeadInfo>();
	public String getMergeKey() {
		return mergeKey;
	}
	public void setMergeKey(String mergeKey) {
		this.mergeKey = mergeKey;
	}
	public String getMergeName() {
		return mergeName;
	}
	public void setMergeName(String mergeName) {
		this.mergeName = mergeName;
	}
	public Integer getFirstColumn() {
		return firstColumn;
	}
	public void setFirstColumn(Integer firstColumn) {
		this.firstColumn = firstColumn;
	}
	public Integer getLastColumn() {
		return lastColumn;
	}
	public void setLastColumn(Integer lastColumn) {
		this.lastColumn = lastColumn;
	}
	public Integer getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}
	public Integer getLastRow() {
		return lastRow;
	}
	public void setLastRow(Integer lastRow) {
		this.lastRow = lastRow;
	}

	public Map<String, HeadInfo> getPropertyColumnMap() {
		return propertyColumnMap;
	}

	public void setPropertyColumnMap(Map<String, HeadInfo> propertyColumnMap) {
		this.propertyColumnMap = propertyColumnMap;
	}
	
	public void putPropertyColumnMap(String property,String title,int cellColumnIndex,MergeInfo info){
		if(null!=info){
			HeadInfo head=new HeadInfo(property,title,(propertyColumnMap.size()+1), cellColumnIndex, info.firstColumn, info.lastColumn, info.firstRow, info.lastRow);
			this.propertyColumnMap.put(property, head);
		}
	}
	
	
	
	/**
	 * 根据cell行索引，cell列索引获取property
	 * @param cellColumnIndex
	 * @return
	 */
	public HeadInfo getHeadByColumnIndex(int cellRowIndex,int cellColumnIndex){
		HeadInfo result=null;
		if(null!=propertyColumnMap&&propertyColumnMap.size()>0){
			Iterator<String> it=propertyColumnMap.keySet().iterator();
			HeadInfo tempHead=null;
			while (it.hasNext()) {
				String property=it.next();
				HeadInfo head=propertyColumnMap.get(property);
				Integer idx=head.cellColumnIndex;
				if(cellRowIndex>head.lastRow&&idx.toString().equals(cellColumnIndex+"")){//当【cell行索引】>【head最大索引】，并且【cell列索引】=【head列索引】时
					if(tempHead!=null){
						if(tempHead.lastRow<head.lastRow){//若之前匹配的表头的最大rowIndex小于新匹配的，则覆盖之前的
							tempHead=head;
						}
					}else{
						tempHead=head;
					}
					result=tempHead;
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取合并列数
	 * @return
	 */
	public int getColspan(){
		return this.lastColumn-this.firstColumn+1;
	}
	
}
