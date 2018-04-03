package utils.plugins.excel.model;

import java.util.List;
/**
 * 工作簿
 * @author Administrator
 *
 */
public class WorkbookInfo {
	private List<SheetInfo> sheetList;
	private String name;//名称
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<SheetInfo> getSheetList() {
		return sheetList;
	}
	public void setSheetList(List<SheetInfo> sheetList) {
		this.sheetList = sheetList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
