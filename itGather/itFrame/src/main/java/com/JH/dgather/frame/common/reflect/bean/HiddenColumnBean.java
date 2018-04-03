package com.JH.dgather.frame.common.reflect.bean;

public class HiddenColumnBean {
	
	private String columnName;//隐藏列的名称
	private String valueColumnName;//隐藏列进行取值的列
	private String operateEim;//隐藏列要对取值列的操作目标(oid或是value);
	private String operateName;//操作方法；last表示进行对目标按照eimFlag出现的倒数operateParam位置进行截取
	private String eimFlag;//要对操作目标flag进行操作。比如point 代表'.',string 代表目标的string代表
	private int operateParam1;//操作参数
	private int operateParam2;//操作参数
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getValueColumnName() {
		return valueColumnName;
	}
	
	public void setValueColumnName(String valueColumnName) {
		this.valueColumnName = valueColumnName;
	}
	
	public String getOperateEim() {
		return operateEim;
	}
	
	public void setOperateEim(String operateEim) {
		this.operateEim = operateEim;
	}
	
	public String getOperateName() {
		return operateName;
	}
	
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	public String getEimFlag() {
		return eimFlag;
	}
	
	public void setEimFlag(String eimFlag) {
		this.eimFlag = eimFlag;
	}
	
	public int getOperateParam1() {
		return operateParam1;
	}
	
	public void setOperateParam1(int operateParam1) {
		this.operateParam1 = operateParam1;
	}
	
	public int getOperateParam2() {
		return operateParam2;
	}
	
	public void setOperateParam2(int operateParam2) {
		this.operateParam2 = operateParam2;
	}
	
}
