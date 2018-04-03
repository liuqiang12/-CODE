package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * replace标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class ReplaceBo implements IStepBase, ISplitBase {

	// replace标签属性，当type='regex'时，表示是正则匹配替换; 当type='string'时，表示是字符串匹配替换
	private String type;

	// replace标签属性，替换后的内容
	private String replacement;

	// replace标签内容
	private String replaceValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public String getReplaceValue() {
		return replaceValue;
	}

	public void setReplaceValue(String replaceValue) {
		this.replaceValue = replaceValue;
	}

	@Override
	public String getTagNameByObject() {
		return "replace";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("replace标签 [type属性=");
		builder.append(type);
		builder.append(", replacement属性=");
		builder.append(replacement);
		builder.append(", replaceValue标签内容值=");
		builder.append(replaceValue);
		builder.append("]");
		return builder.toString();
	}
}
