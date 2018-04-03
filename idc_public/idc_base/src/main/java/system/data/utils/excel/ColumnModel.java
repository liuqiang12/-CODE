package system.data.utils.excel;

public class ColumnModel implements Comparable<ColumnModel> {
	
	
	public ColumnModel(String property, String title, Integer sort, String value) {
		super();
		this.property = property;
		this.title = title;
		this.sort = sort;
		this.value = value;
	}
	public ColumnModel() {
		super();
	}
	private String property;
	private String title;
	private Integer sort;
	private String value;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int compareTo(ColumnModel o) {
		return this.sort.compareTo(o.sort);
	}
	
	
}
