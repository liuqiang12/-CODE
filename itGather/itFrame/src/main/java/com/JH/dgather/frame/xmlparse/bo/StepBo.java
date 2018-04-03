package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IForeachBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

public class StepBo implements IForeachBase, IStepBase {
	
	// 标识stepBaseList中是否存在command标签，如果存在则为true，不允许有重复该标签
	private boolean existCommand = false;
	
	// 标识stepBaseList中是否存在matcher标签，如果存在则为true，不允许有重复该标签
	private boolean existMatcher = false;

	// 标识stepBaseList中是否存在split标签，如果存在则为true，不允许有重复该标签
	private boolean existSplit = false;
	
	// 标识stepBaseList中是否存在replace标签，如果存在则为true，不允许有重复该标签
	private boolean existReplace = false;
	
	// step标签下可以添加的标签集合（有序）
	private List<IStepBase> stepBaseList;

	public List<IStepBase> getStepBaseList() {
		return stepBaseList;
	}

	public void setStepBaseList(List<IStepBase> stepBaseList) {
		this.stepBaseList = stepBaseList;
	}
	
	public boolean isExistCommand() {
		return existCommand;
	}

	public void setExistCommand(boolean existCommand) {
		this.existCommand = existCommand;
	}

	public boolean isExistMatcher() {
		return existMatcher;
	}

	public void setExistMatcher(boolean existMatcher) {
		this.existMatcher = existMatcher;
	}

	public boolean isExistSplit() {
		return existSplit;
	}

	public void setExistSplit(boolean existSplit) {
		this.existSplit = existSplit;
	}

	public boolean isExistReplace() {
		return existReplace;
	}

	public void setExistReplace(boolean existReplace) {
		this.existReplace = existReplace;
	}

	@Override
	public String getTagNameByObject() {
		return "step";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("step标签 []");
		return builder.toString();
	}
}
