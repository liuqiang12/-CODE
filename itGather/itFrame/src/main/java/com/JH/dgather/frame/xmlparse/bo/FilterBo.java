package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;

/**
 * filter标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class FilterBo implements ISplitBase {

	/**
	 * filter标签检查后，符合标准的数据string保存到全局参数中的mainkey=buffer,subkey=bufferName，
	 * object为VarBean,type=String,object=String
	 */
	private String bufferName;

	/**
	 * 必须与bufferName共存，当校验数据有多个时，以该符号将分别通过验证的数据连接起来
	 */
	private String connector;

	/**
	 * filter验证要求，正则
	 */
	private String filterValue;

	public String getBufferName() {
		return bufferName;
	}

	public void setBufferName(String bufferName) {
		this.bufferName = bufferName;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	@Override
	public String getTagNameByObject() {
		return "filter";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilterBo [bufferName属性=");
		builder.append(bufferName);
		builder.append(", connector属性=");
		builder.append(connector);
		builder.append(", filterValue属性=");
		builder.append(filterValue);
		builder.append("]");
		return builder.toString();
	}
}
