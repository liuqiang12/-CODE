package utils.plugins.excel.model;

import java.util.List;

/**
 * excel
 */
public class SheetInfo {
	
	private String name;//名称
	
	private String id;//id

	//所有的行信息
	private List<RowInfo> rowList;

	public List<RowInfo> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowInfo> rowList) {
		this.rowList = rowList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
