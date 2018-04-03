package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * If标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class IfBo implements IStepBase {

	// 必须包含属性，定义的参数，真实的数据必须为条件表达式，验证表达式
	private String test;

	// 正则的表达式，属性regex存在时必须包含regexSource属性
	private String regex;

	// 正则表达式对应的源信息，${xxx.xxx}或${xxx}结构
	private String regexSource;

	// if标签下可能存在多个step标签（有序）
	private List<StepBo> stepBoList;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegexSource() {
		return regexSource;
	}

	public void setRegexSource(String regexSource) {
		this.regexSource = regexSource;
	}

	public List<StepBo> getStepBoList() {
		return stepBoList;
	}

	public void setStepBoList(List<StepBo> stepBoList) {
		this.stepBoList = stepBoList;
	}

	@Override
	public String getTagNameByObject() {
		return "if";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("if标签 [test属性=");
		builder.append(test);
		builder.append(", regex属性=");
		builder.append(regex);
		builder.append(", regexSource属性=");
		builder.append(regexSource);
		builder.append("]");
		return builder.toString();
	}
}
