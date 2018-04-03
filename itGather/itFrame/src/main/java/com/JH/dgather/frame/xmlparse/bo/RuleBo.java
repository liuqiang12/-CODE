package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * rule标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class RuleBo implements IBase {

	// 必须包含属性，
	private String id;

	private String name;

	// rule标签下子标签集合 （有序）
	private List<StepBo> stepBoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StepBo> getStepBoList() {
		return stepBoList;
	}

	public void setStepBoList(List<StepBo> stepBoList) {
		this.stepBoList = stepBoList;
	}

	@Override
	public String getTagNameByObject() {
		return "rule";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("rule标签 [id属性=");
		builder.append(id);
		builder.append(", name属性=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
