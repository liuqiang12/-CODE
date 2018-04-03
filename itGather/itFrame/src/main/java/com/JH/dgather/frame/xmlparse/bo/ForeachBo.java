package com.JH.dgather.frame.xmlparse.bo;

import java.util.List;

import com.JH.dgather.frame.xmlparse.bo.iface.IForeachBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;

/**
 * foreach业务对象
 * 
 * @author zhengbin
 * 
 */
public class ForeachBo implements IForeachBase, IStepBase {

	// 局部参数定义的名称，foreach标签内部可以使用当前的局部参数，使用规则如下：
	// 假设var="test"
	// 如果var指向的参数（即items属性）类型为HashMap, 那么${test.xxxx}, xxxx为键值
	// 如果var指向的参数类型为List，那么${test}，使用当前循环到的数据。
	// 注意：HashMap与List存储的数据类型只能为String
	private String var;

	// 指向的参数，必须为${xxxx.xxxx}格式
	private String items;

	// 需要使用循环递增值是，递增值参数的名称
	private String varStatus;

	// 递增值起始值，当startWith存在时，varStatus必须存在，表示索引的起始值
	// startWith默认值为0，必须大于等于0
	private String startWith;

	// 表示foreach标签可有实现foreachbase接口的子标签集合(有序的)
	private List<IForeachBase> foreachBaseList;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getVarStatus() {
		return varStatus;
	}

	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}

	public String getStartWith() {
		return startWith;
	}

	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}

	public List<IForeachBase> getForeachBaseList() {
		return foreachBaseList;
	}

	public void setForeachBaseList(List<IForeachBase> foreachBaseList) {
		this.foreachBaseList = foreachBaseList;
	}

	@Override
	public String getTagNameByObject() {
		return "foreach";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("foreach标签 [var属性=");
		builder.append(var);
		builder.append(", items属性=");
		builder.append(items);
		builder.append(", varStatus属性=");
		builder.append(varStatus);
		builder.append(", startWith属性=");
		builder.append(startWith);
		builder.append("]");
		return builder.toString();
	}
}
