package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IForeachBase;
import com.JH.dgather.frame.xmlparse.bo.iface.ISplitBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * split标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class SplitBo implements IForeachBase, IStepBase, ISplitBase {

	// 拆分的值
	private String value;

	// 拆分的条件
	private String regex;

	// 当统计不为空的时候，会统计拆分出来并通过校验的数量，存储在stat定义的变量中。存在stat的时候，不能存在mathcer子标签
	// stat: 统计split后，并通过validate的数据长度
	private String stat;

	// 定位，对拆分出来的数据进行定位，多个以';'分隔
	// fix与ignore不能同时出现
	private List<Integer> fix;

	// 忽略，对拆分出来的数据进行忽略，多个以';'分隔
	// fix与ignore不能同时出现
	private List<Integer> ignore;

	// 当fix/ignore存在时，result属性必须存在，存储拆分后的数据列表
	private String resultKey;

	// 保存split拆分结果内容
	private String resultValue;

	// split标签子标签业务对象(有序)
	private List<ISplitBase> splitBaseList;

	// 标识splitBaseList中是否存在split标签，如果存在则为true，不允许有重复该标签
	private boolean existSplit = false;

	// 标识splitBaseList中是否存在validate标签，如果存在则为true，不允许有重复该标签
	private boolean existValidate = false;

	// 标识splitBaseList中是否存在matcher标签，如果存在则为true，不允许有重复该标签
	private boolean existMatcher = false;

	// 标识splitBaseList中是否存在replace标签，如果存在则为true，不允许有重复该标签
	private boolean existReplace = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public List<Integer> getFix() {
		return fix;
	}

	public void setFix(List<Integer> fix) {
		this.fix = fix;
	}

	public List<Integer> getIgnore() {
		return ignore;
	}

	public void setIgnore(List<Integer> ignore) {
		this.ignore = ignore;
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

	public List<ISplitBase> getSplitBaseList() {
		return splitBaseList;
	}

	public void setSplitBaseList(List<ISplitBase> splitBaseList) {
		this.splitBaseList = splitBaseList;
	}
	
	public boolean isExistSplit() {
		return existSplit;
	}

	public void setExistSplit(boolean existSplit) {
		this.existSplit = existSplit;
	}

	public boolean isExistValidate() {
		return existValidate;
	}

	public void setExistValidate(boolean existValidate) {
		this.existValidate = existValidate;
	}

	public boolean isExistMatcher() {
		return existMatcher;
	}

	public void setExistMatcher(boolean existMatcher) {
		this.existMatcher = existMatcher;
	}

	public boolean isExistReplace() {
		return existReplace;
	}

	public void setExistReplace(boolean existReplace) {
		this.existReplace = existReplace;
	}

	@Override
	public String getTagNameByObject() {
		return "split";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("split标签 [value属性=");
		builder.append(value);
		builder.append(", regex属性=");
		builder.append(regex);
		builder.append(", stat属性=");
		builder.append(stat);
		builder.append(", fix属性=");
		builder.append(fix);
		builder.append(", ignore属性=");
		builder.append(ignore);
		builder.append(", resultKey(result属性)=");
		builder.append(resultKey);
		builder.append(", resultValue结果集=");
		builder.append(resultValue);
		builder.append("]");
		return builder.toString();
	}
}
