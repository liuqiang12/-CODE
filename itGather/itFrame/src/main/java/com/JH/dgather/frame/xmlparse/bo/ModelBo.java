package com.JH.dgather.frame.xmlparse.bo;

import java.util.Map;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * model标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class ModelBo implements IBase {

	/**
	 * 型号编号(*表示通过所有型号)，必须包含属性
	 */
	private String value;

	/**
	 * model标签的子标签version集合
	 */
	private Map<String, VersionBo> versionBoMap;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, VersionBo> getVersionBoMap() {
		return versionBoMap;
	}

	public void setVersionBoMap(Map<String, VersionBo> versionBoMap) {
		this.versionBoMap = versionBoMap;
	}

	@Override
	public String getTagNameByObject() {
		return "model";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("model标签 [value属性=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}
