package com.JH.dgather.frame.xmlparse.bo;

import java.util.Map;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * factory标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class FactoryBo implements IBase {

	/**
	 * 厂家编号，必须包含属性
	 */
	private String value;
	
	/**
	 * 厂家名称
	 */
	private String name;
	
	/**
	 * factory标签的子标签model集合
	 */
	private Map<String, ModelBo> modelBoMap;
	
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Map<String, ModelBo> getModelBoMap() {
		return modelBoMap;
	}


	public void setModelBoMap(Map<String, ModelBo> modelBoMap) {
		this.modelBoMap = modelBoMap;
	}


	@Override
	public String getTagNameByObject() {
		return "factory";
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("factory标签 [value属性=");
		builder.append(value);
		builder.append(", name属性=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
