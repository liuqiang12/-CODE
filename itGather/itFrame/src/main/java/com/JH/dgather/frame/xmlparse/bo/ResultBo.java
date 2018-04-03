package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IForeachBase;
import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * result标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class ResultBo implements IForeachBase, IStepBase, ISplitBase {

	// 存储到全局数据仓库中的键值，必须唯一，不得重复
	private String name;

	// 操作符号，如果当前属性存在，那么result标签的内容根据操作符号的不同，验正规则也不同。
	// 操作符说明: 1. count: 求合操作符号. 2. size: 求长度操作符合. 3. list: 取出所有符合正则的数据
	private String operater;

	// 前缀，取出的结果必须加上此前缀
	private String prefix;

	// 包含，取出的结果必须包含指定的字符串，多个确定关系以|间隔，他们之间是'或'关系，只要满足一个即可。
	private List<String> includeList;

	// 不包换，取出的结果不得包含指定的字符串，多个确定关系以|间隔。
	private List<String> excludeList;

	// 当resultValue匹配的结果或结果集与step中该result标签之前的标签结果有归属关系时，必须配置该属性
	// 属性格式必须为${xxx.xxx}
	private String resultKey;

	// result标签内容
	private String resultValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<String> getIncludeList() {
		return includeList;
	}

	public void setIncludeList(List<String> includeList) {
		this.includeList = includeList;
	}

	public List<String> getExcludeList() {
		return excludeList;
	}

	public void setExcludeList(List<String> excludeList) {
		this.excludeList = excludeList;
	}
	
	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	@Override
	public String getTagNameByObject() {
		return "result";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("result标签 [name属性=");
		builder.append(name);
		builder.append(", operater属性=");
		builder.append(operater);
		builder.append(", prefix属性=");
		builder.append(prefix);
		builder.append(", resultKey结果集的归属=");
		builder.append(resultKey);
		builder.append(", resultValue标签内容值=");
		builder.append(resultValue);
		builder.append("]");
		return builder.toString();
	}
}
