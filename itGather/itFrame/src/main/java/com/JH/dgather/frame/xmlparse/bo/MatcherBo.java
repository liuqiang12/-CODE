package com.JH.dgather.frame.xmlparse.bo;

import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * matcher标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class MatcherBo implements IStepBase, ISplitBase {

	// matcher标签的buffer属性
	private String buffer;

	// matcher标签的内容
	private String matcherValue;

	// 匹配matcher标签内容正则表达式后的结果
	private String result;

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getMatcherValue() {
		return matcherValue;
	}

	public void setMatcherValue(String matcherValue) {
		this.matcherValue = matcherValue;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String getTagNameByObject() {
		return "matcher";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("matcher标签 [buffer属性=");
		builder.append(buffer);
		builder.append(", matcherValue标签内容=");
		builder.append(matcherValue);
		builder.append(", result执行结果=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}
}
