package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * source标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class SourceBo implements IStepBase {

	// 存在数据仓库中的键值，不得重复
	private String name;

	// source标签内容
	private String sourceValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	@Override
	public String getTagNameByObject() {
		return "source";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("source标签 [name属性=");
		builder.append(name);
		builder.append(", sourceValue标签内容值=");
		builder.append(sourceValue);
		builder.append("]");
		return builder.toString();
	}
}
