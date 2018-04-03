package system.data.utils.excel.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import system.data.utils.excel.ColumnModel;

public class ImportErrorResult {
	
	public ImportErrorResult() {
		super();
	}
	
	public ImportErrorResult(String text,String ower, List<ColumnModel> row) {
		super();
		this.text = text;
		this.ower = ower;
		this.addRow(row);
	}

	private String text;
	private String ower;
	private List<List<ColumnModel>> rows=new ArrayList<>();
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getOwer() {
		return ower;
	}

	public void setOwer(String ower) {
		this.ower = ower;
	}

	public List<List<ColumnModel>> getRows() {
		return rows;
	}

	public void setRows(List<List<ColumnModel>> rows) {
		this.rows = rows;
	}

	public void addRow(List<ColumnModel> row) {
		Collections.sort(row);
		this.rows.add(row);
	}
	
}
