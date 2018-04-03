package utils.plugins.excel.model;

import java.util.List;


/**
 * excel行信息[行]
 */
public class RowInfo {
	private String desc;//描述
	private String id ;//id标识
	
	private List<CellInfo> cellInfos;//所有的单元格情况
	
	public List<CellInfo> getCellInfos() {
		return cellInfos;
	}
	public void setCellInfos(List<CellInfo> cellInfos) {
		this.cellInfos = cellInfos;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}