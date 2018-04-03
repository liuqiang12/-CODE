package system.data.msg.ajax;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回的值
 * @author Administrator
 *
 */
public class ResultMsg {
	private Boolean success = ResponseType.FALSE.getType();
	
	private List<ResultData> items = new ArrayList<ResultData>();
	
	protected ResultData getResultData() { 
        return new ResultData(); 
    } 
	
	public class ResultData{
		private String attribute;
		private Object attrvaule;
		public String getAttribute() {
			return attribute;
		}
		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}
		public Object getAttrvaule() {
			return attrvaule;
		}
		public void setAttrvaule(Object attrvaule) {
			this.attrvaule = attrvaule;
		}
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public List<ResultData> getItems() {
		return items;
	}
	public void setItems(List<ResultData> items) {
		this.items = items;
	}
}
