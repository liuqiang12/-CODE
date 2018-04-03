package system.data.msg.ajax;


/**
 * 下拉框
 * @author Administrator
 *
 */
public class MulitCombobox {
	
	private String id;
	private String text;
	private String desc;
	private Boolean selected = false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	 
}
